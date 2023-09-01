package cn.leixd.rpc.core.registry;


import cn.lxd.rpc.common.constants.URLKeyConst;
import cn.lxd.rpc.common.extension.Adaptive;
import cn.lxd.rpc.common.extension.SPI;
import cn.lxd.rpc.common.url.URL;

/**
 * 注册中心工厂
 *
 */
@SPI
public interface RegistryFactory {

    /**
     * 获取注册中心
     *
     * @param url 注册中心的配置，例如注册中心的地址。会自动根据协议获取注册中心实例
     * @return 如果协议类型跟注册中心匹配上了，返回对应的配置中心实例
     */
    @Adaptive(URLKeyConst.PROTOCOL)
    Registry getRegistry(URL url);
}
