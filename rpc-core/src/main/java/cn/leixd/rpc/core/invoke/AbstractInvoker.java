package cn.leixd.rpc.core.invoke;

import cn.hutool.core.collection.CollectionUtil;
import cn.leixd.rpc.core.config.ConfigManager;
import cn.leixd.rpc.core.dto.RpcRequest;
import cn.leixd.rpc.core.dto.RpcResult;
import cn.leixd.rpc.core.loadbalance.LoadBalance;
import cn.leixd.rpc.core.loadbalance.RandomLoadBalance;
import cn.leixd.rpc.core.registry.RegistryService;
import cn.leixd.rpc.core.registry.zk.ZkRegistryFactory;
import cn.lxd.rpc.common.constants.RpcException;
import cn.lxd.rpc.common.constants.URLKeyConst;
import cn.lxd.rpc.common.factory.SingletonFactory;
import cn.lxd.rpc.common.url.URL;
import cn.lxd.rpc.common.url.URLBuilder;

import java.util.List;
import java.util.Map;

/**
 * 执行者
 */
public abstract class AbstractInvoker implements Invoker{

    private final RegistryService registry = SingletonFactory.getInstance(ZkRegistryFactory.class)
            .getRegistry(ConfigManager.getInstant().getRegistryConfig().toURL());
    //TODO:优化为SPI拓展
    private final LoadBalance loadBalance = SingletonFactory.getInstance(RandomLoadBalance.class);

    @Override
    public RpcResult invoke(RpcRequest request) throws RpcException {
        Map<String, String> serviceParam = URLBuilder.getServiceParam(request.getInterfaceName(), request.getVersion());
        URL url = URL.builder().protocol(URLKeyConst.LXD_RPC_PROTOCOL).host(URLKeyConst.ANY_HOST).params(serviceParam).build();
        // 注册中心拿出所有服务的信息
        List<URL> urls = registry.lookup(url);
        if (CollectionUtil.isEmpty(urls)) {
            throw new RpcException("Not service Providers registered." + serviceParam);
        }
        URL selected = loadBalance.select(urls, request);
        return doInvoke(request, selected);
    }
    protected abstract RpcResult doInvoke(RpcRequest rpcRequest, URL selected) throws RpcException;
}
