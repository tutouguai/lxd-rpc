package cn.leixd.rpc.core.registry.local;


import cn.leixd.rpc.core.registry.RegistryFactory;
import cn.leixd.rpc.core.registry.RegistryService;
import cn.lxd.rpc.common.url.URL;

/**
 */
public class LocalRegistryFactory implements RegistryFactory {

    @Override
    public RegistryService getRegistry(URL url) {
        return new LocalRegistry();
    }
}
