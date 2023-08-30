package cn.leixd.rpc.core.registry.zk;

import cn.leixd.rpc.core.registry.RegistryFactory;
import cn.leixd.rpc.core.registry.RegistryService;
import cn.lxd.rpc.common.url.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * zk 注册中心工厂
 *
 */
public class ZkRegistryFactory implements RegistryFactory {

    private static final Map<URL, ZkRegistry> cache = new ConcurrentHashMap<>();

    @Override
    public RegistryService getRegistry(URL url) {
        if (cache.containsKey(url)) {
            return cache.get(url);
        }
        ZkRegistry zkRegistry = new ZkRegistry(url);
        cache.putIfAbsent(url, zkRegistry);
        return cache.get(url);
    }

}
