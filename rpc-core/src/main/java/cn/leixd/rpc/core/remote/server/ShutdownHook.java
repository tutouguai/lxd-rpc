package cn.leixd.rpc.core.remote.server;

import cn.leixd.rpc.core.config.ConfigManager;
import cn.leixd.rpc.core.config.RegistryConfig;
import cn.leixd.rpc.core.registry.RegistryFactory;
import cn.leixd.rpc.core.registry.RegistryService;
import cn.leixd.rpc.core.registry.zk.ZkRegistryFactory;
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
            RegistryFactory registryFactory = SingletonFactory.getInstance(ZkRegistryFactory.class);
            RegistryConfig registryConfig = ConfigManager.getInstant().getRegistryConfig();
            RegistryService registry = registryFactory.getRegistry(registryConfig.toURL());
            registry.unregisterAllMyService();
        }));
    }
}
