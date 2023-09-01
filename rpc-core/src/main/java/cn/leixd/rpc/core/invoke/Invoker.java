package cn.leixd.rpc.core.invoke;

import cn.leixd.rpc.core.dto.RpcRequest;
import cn.leixd.rpc.core.dto.RpcResult;
import cn.lxd.rpc.common.constants.RpcException;
import cn.lxd.rpc.common.extension.SPI;

@SPI("netty")
public interface Invoker {
    /**
     * 执行
     *
     * @param request 请求
     * @return result
     * @throws RpcException 执行异常会抛出
     */
    RpcResult invoke(RpcRequest request) throws RpcException;
}
