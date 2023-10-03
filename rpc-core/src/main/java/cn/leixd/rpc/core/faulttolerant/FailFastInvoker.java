package cn.leixd.rpc.core.faulttolerant;

import cn.leixd.rpc.core.dto.RpcRequest;
import cn.leixd.rpc.core.dto.RpcResult;
import cn.leixd.rpc.core.invoke.Invoker;

public class FailFastInvoker extends AbstractFaultTolerantInvoker{
    @Override
    protected RpcResult doInvoke(RpcRequest request, Invoker invoker) {
        return invoker.invoke(request);
    }
}
