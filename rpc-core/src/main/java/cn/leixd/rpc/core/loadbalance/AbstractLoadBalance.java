package cn.leixd.rpc.core.loadbalance;

import cn.hutool.core.collection.CollectionUtil;
import cn.leixd.rpc.core.config.ConfigManager;
import cn.leixd.rpc.core.dto.RpcRequest;
import cn.leixd.rpc.core.registry.Registry;
import cn.leixd.rpc.core.registry.RegistryFactory;
import cn.leixd.rpc.core.registry.zk.ZkRegistry;
import cn.lxd.rpc.common.extension.ExtensionLoader;
import cn.lxd.rpc.common.url.URL;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 抽象的负载均衡
 *
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public URL select(List<URL> candidateUrls, RpcRequest request, String ... urls) {

        if (CollectionUtil.isEmpty(candidateUrls)) {
            return null;
        }
        if (candidateUrls.size() == 1) {
            return candidateUrls.get(0);
        }
        return doSelect(candidateUrls, request, urls);
    }

    protected abstract URL doSelect(List<URL> candidateUrls, RpcRequest request, String...urls);

}
