package cn.leixd.rpc.core.registry.local;


import cn.leixd.rpc.core.registry.RegistryFactory;
import cn.leixd.rpc.core.registry.Registry;
import cn.lxd.rpc.common.url.URL;

/**
 */
public class LocalRegistryFactory implements RegistryFactory {

    @Override
    public Registry getRegistry(URL url) {
        return new LocalRegistry();
    }
}
