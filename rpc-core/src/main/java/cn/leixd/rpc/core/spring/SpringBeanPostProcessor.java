package cn.leixd.rpc.core.spring;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.net.NetUtil;
import cn.leixd.rpc.core.annotaion.RpcReference;
import cn.leixd.rpc.core.annotaion.RpcService;
import cn.leixd.rpc.core.config.ConfigManager;
import cn.leixd.rpc.core.config.RegistryConfig;
import cn.leixd.rpc.core.config.ServiceConfig;
import cn.leixd.rpc.core.proxy.RpcClientProxy;
import cn.leixd.rpc.core.proxy.RpcServiceCache;
import cn.leixd.rpc.core.registry.RegistryService;
import cn.leixd.rpc.core.registry.zk.ZkRegistryFactory;
import cn.lxd.rpc.common.constants.URLKeyConst;
import cn.lxd.rpc.common.factory.SingletonFactory;
import cn.lxd.rpc.common.url.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;


/**
 * 实例化时。
 * 服务提供方：注册到注册中心
 * 服务调用方：生成 RPC 代理类
 *
 */

@Slf4j
@Component
public class SpringBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        //RPC服务注册到zk中
        if (rpcService != null) {
            RegistryConfig registryConfig = ConfigManager.getInstant().getRegistryConfig();
            RegistryService registry = SingletonFactory.getInstance(ZkRegistryFactory.class).getRegistry(registryConfig.toURL());
            registry.register(buildServiceURL(bean, rpcService));
            // 然后把服务放到缓存中，方便后续通过 rpcServiceName 获取服务
            RpcServiceCache.addService(rpcService.version(), bean);
        }
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //TODO:客户端获取服务端bean时生成代理对象
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                // 生成代理对象
                RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcReference);
                Object proxy = rpcClientProxy.getProxy(field.getType());
                field.setAccessible(true);
                try {
                    // 设置字段
                    field.set(bean, proxy);
                } catch (IllegalAccessException e) {
                    log.error("field.set error. bean={}, field={}", bean.getClass(), field.getName(), e);
                }
            }
        }
        return bean;
    }

    /**
     * 构建服务的 URL
     *
     * @param bean       类实例
     * @param rpcService 类上面的 @RpcService 注解
     * @return 带有服务信息的 URL
     */
    private URL buildServiceURL(Object bean, RpcService rpcService) {
        Map<String, String> param = MapUtil.<String, String>builder()
                .put(URLKeyConst.INTERFACE, bean.getClass().getInterfaces()[0].getCanonicalName())
                .put(URLKeyConst.VERSION, rpcService.version()).build();
        ServiceConfig serviceConfig = ConfigManager.getInstant().getServiceConfig();
        return URL.builder().protocol("lxd-rpc")
                .host(NetUtil.getLocalhostStr())
                .port(serviceConfig.getPort())
                .params(param).build();
    }
}
