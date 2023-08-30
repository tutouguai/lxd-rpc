package cn.leixd.rpc.core.remote.client.netty;

import cn.hutool.json.JSONUtil;
import cn.leixd.rpc.core.dto.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 未处理的请求
 *
 */
public class UnprocessedRequests {
    private static final Map<Long, CompletableFuture<RpcResponse<?>>> FUTURE_MAP = new ConcurrentHashMap<>();

    public static void put(long requestId, CompletableFuture<RpcResponse<?>> future) {
        FUTURE_MAP.put(requestId, future);
    }

    /**
     * 完成响应
     *
     * @param rpcResponse 响应内容
     */
    public static void complete(RpcResponse<?> rpcResponse) {
        CompletableFuture<RpcResponse<?>> future = FUTURE_MAP.remove(rpcResponse.getRequestId());
        if (future != null) {
            future.complete(rpcResponse);
        } else {
            throw new IllegalStateException("future is null. rpcResponse=" + JSONUtil.toJsonStr(rpcResponse));
        }
    }
}
