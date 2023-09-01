package cn.leixd.rpc.core.remote.server;

import cn.leixd.rpc.core.config.ConfigManager;
import cn.leixd.rpc.core.config.RegistryConfig;
import cn.leixd.rpc.core.registry.RegistryFactory;
import cn.leixd.rpc.core.registry.Registry;
import cn.leixd.rpc.core.registry.zk.ZkRegistryFactory;
import cn.lxd.rpc.common.extension.ExtensionLoader;
import cn.lxd.rpc.common.factory.SingletonFactory;
import lombok.extern.slf4j.Slf4j;


/**
 * 关闭的钩子
 *
 */
@Slf4j
public class ShutdownHook {

    public static void addShutdownHook() {
        log.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //改成SPI拓展
//            RegistryFactory registryFactory = SingletonFactory.getInstance(ZkRegistryFactory.class);
            RegistryFactory registryFactory = ExtensionLoader.getLoader(RegistryFactory.class).getAdaptiveExtension();
            RegistryConfig registryConfig = ConfigManager.getInstant().getRegistryConfig();
            Registry registry = registryFactory.getRegistry(registryConfig.toURL());
            registry.unregisterAllMyService();
        }));
    }
}
