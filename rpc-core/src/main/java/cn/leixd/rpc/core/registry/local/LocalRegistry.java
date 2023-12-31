package cn.leixd.rpc.core.registry.local;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.leixd.rpc.core.registry.AbstractRegistry;
import cn.lxd.rpc.common.url.URL;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 本地注册中心，测试用
 *
 */
@Slf4j
public class LocalRegistry extends AbstractRegistry {

    private static final Set<URL> urls = new ConcurrentHashSet<>();

    @Override
    public void doRegister(URL url) {
        urls.add(url);
        log.info("doRegister:" + url);
    }

    @Override
    public void doUnregister(URL url) {
        urls.remove(url);
        log.info("doUnregister:" + url);
    }

    @Override
    public List<URL> doLookup(URL condition) {
        log.info("doLookup:" + urls);
        return new ArrayList<>(urls);
    }
}
