package cn.leixd.rpc.core.invoke;

import cn.leixd.rpc.core.config.ConfigManager;
import cn.leixd.rpc.core.config.ProtocolConfig;
import cn.leixd.rpc.core.consts.CompressType;
import cn.leixd.rpc.core.consts.MessageFormatConst;
import cn.leixd.rpc.core.consts.MessageType;
import cn.leixd.rpc.core.consts.SerializeType;
import cn.leixd.rpc.core.dto.*;
import cn.leixd.rpc.core.loadbalance.ServiceStatus;
import cn.leixd.rpc.core.remote.client.netty.NettyClient;
import cn.leixd.rpc.core.remote.client.netty.UnprocessedRequests;
import cn.lxd.rpc.common.constants.RpcException;
import cn.lxd.rpc.common.url.URL;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * netty 执行者，相当于发请求
 *
 */
@Slf4j
public class NettyInvoker extends AbstractInvoker {

    private final NettyClient nettyClient = NettyClient.getInstance();

    @Override
    protected RpcResult doInvoke(RpcRequest request, URL selected) throws RpcException {
        InetSocketAddress socketAddress = new InetSocketAddress(selected.getHost(), selected.getPort());
        Channel channel = nettyClient.getChannel(socketAddress);
        if (channel.isActive()) {
            CompletableFuture<RpcResponse<?>> resultFuture = new CompletableFuture<>();
            // 构建 RPC 消息，此处会构建 requestId
            RpcMessage rpcMessage = buildRpcMessage(request);
            UnprocessedRequests.put(rpcMessage.getRequestId(), resultFuture);
            long start = System.currentTimeMillis();
            channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info("client send message: [{}]", rpcMessage);
                    long elapsed = System.currentTimeMillis() - start;
                    ServiceStatus.endCount(selected.toFullString(), request.getFullMethodName(), elapsed, true);
                } else {
                    future.channel().close();
                    long elapsed = System.currentTimeMillis() - start;
                    ServiceStatus.endCount(selected.toFullString(), request.getFullMethodName(), elapsed, false);
                    resultFuture.completeExceptionally(future.cause());
                    log.error("send failed:", future.cause());
                }
            });
            return new AsyncResult(resultFuture);
        } else {
            throw new RpcException("channel is not active. address=" + socketAddress);
        }
    }

    /**
     * 根据请求数据，构建 Rpc 通用信息结构
     *
     * @param request 请求
     * @return RpcMessage
     */
    private RpcMessage buildRpcMessage(RpcRequest request) {
        ProtocolConfig protocolConfig = ConfigManager.getInstant().getProtocolConfig();

        // 压缩类型
        String compressTypeName = protocolConfig.getCompressType();
        CompressType compressType = CompressType.fromName(compressTypeName);

        // 序列化类型
        String serializeTypeName = protocolConfig.getSerializeType();
        SerializeType serializeType = SerializeType.fromName(serializeTypeName);
        if (serializeType == null) {
            throw new IllegalStateException("serializeType " + serializeTypeName + " not support.");
        }

        return RpcMessage.builder()
                .messageType(MessageType.REQUEST.getValue())
                .compressTye(compressType.getValue())
                .serializeType(serializeType.getValue())
                .requestId(MessageFormatConst.REQUEST_ID.getAndIncrement())
                .data(request)
                .build();
    }
}
