package cn.leixd.rpc.core.invoke;

import cn.hutool.core.collection.CollectionUtil;
import cn.leixd.rpc.core.config.ConfigManager;
import cn.leixd.rpc.core.dto.RpcRequest;
import cn.leixd.rpc.core.dto.RpcResult;
import cn.leixd.rpc.core.loadbalance.LoadBalance;
import cn.leixd.rpc.core.loadbalance.RandomLoadBalance;
import cn.leixd.rpc.core.loadbalance.ServiceStatus;
import cn.leixd.rpc.core.registry.Registry;
import cn.leixd.rpc.core.registry.RegistryFactory;
import cn.leixd.rpc.core.registry.zk.ZkRegistryFactory;
import cn.lxd.rpc.common.constants.RpcException;
import cn.lxd.rpc.common.constants.URLKeyConst;
import cn.lxd.rpc.common.extension.ExtensionLoader;
import cn.lxd.rpc.common.factory.SingletonFactory;
import cn.lxd.rpc.common.url.URL;
import cn.lxd.rpc.common.url.URLBuilder;

import java.util.List;
import java.util.Map;

/**
 * 执行者
 */
public abstract class AbstractInvoker implements Invoker{

    private final RegistryFactory registryFactory = ExtensionLoader.getLoader(RegistryFactory.class)
            .getAdaptiveExtension();

    private final Registry registry = registryFactory.getRegistry(ConfigManager.getInstant().getRegistryConfig().toURL());
//    private final Registry registry = SingletonFactory.getInstance(ZkRegistryFactory.class)
//            .getRegistry(ConfigManager.getInstant().getRegistryConfig().toURL());
//    private final LoadBalance loadBalance = SingletonFactory.getInstance(RandomLoadBalance.class);
    protected final  LoadBalance loadBalance = ExtensionLoader.getLoader(LoadBalance.class)
            .getExtension(ConfigManager.getInstant().getClusterConfig().getLoadBalance());

    @Override
    public RpcResult invoke(RpcRequest request) throws RpcException {
        Map<String, String> serviceParam = URLBuilder.getServiceParam(request.getInterfaceName(), request.getVersion());
        URL url = URL.builder().protocol(URLKeyConst.LXD_RPC_PROTOCOL).host(URLKeyConst.ANY_HOST).params(serviceParam).build();
        // 注册中心拿出所有服务的信息
        List<URL> urls = registry.lookup(url);
        if (CollectionUtil.isEmpty(urls)) {
            throw new RpcException("Not service Providers registered." + serviceParam);
        }
        URL selected = loadBalance.select(urls, request, url.toFullString());
        ServiceStatus.beginCount(selected.toFullString(), request.getFullMethodName());
        return doInvoke(request, selected);
    }
    protected abstract RpcResult doInvoke(RpcRequest rpcRequest, URL selected) throws RpcException;
}
