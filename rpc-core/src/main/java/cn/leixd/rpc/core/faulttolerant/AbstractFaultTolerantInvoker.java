package cn.leixd.rpc.core.faulttolerant;

import cn.leixd.rpc.core.config.ClusterConfig;
import cn.leixd.rpc.core.config.ConfigManager;
import cn.leixd.rpc.core.dto.RpcRequest;
import cn.leixd.rpc.core.dto.RpcResult;
import cn.leixd.rpc.core.invoke.Invoker;
import cn.leixd.rpc.core.loadbalance.LoadBalance;
import cn.lxd.rpc.common.constants.RpcException;
import cn.lxd.rpc.common.extension.ExtensionLoader;

public abstract class AbstractFaultTolerantInvoker implements FaultTolerantInvoker{

    protected final ClusterConfig clusterConfig = ConfigManager.getInstant().getClusterConfig();
//    private final LoadBalance loadBalance = ExtensionLoader.getLoader(LoadBalance.class)
//            .getExtension(clusterConfig.getLoadBalance());
    //nettyInvoker
    private final Invoker invoker = ExtensionLoader.getLoader(Invoker.class).getDefaultExtension();


    @Override
    public RpcResult invoke(RpcRequest request) throws RpcException {
        return doInvoke(request, invoker);
    }

    protected abstract RpcResult doInvoke(RpcRequest request, Invoker invoker);
}
