package cn.leixd.rpc.core.registry;

import cn.lxd.rpc.common.url.URL;

import java.util.List;

/**
 * 注册中心
 */
public interface Registry {
    /**
     * 注册数据，比如：提供者地址，消费者地址，路由规则，覆盖规则，等数据。
     *
     */
    void register(URL url);

    /**
     * 向注册中心取消注册服务
     *
     * @param url 注册者的信息
     */
    void unregister(URL url);

    /**
     * 查询符合条件的已注册数据，与订阅的推模式相对应，这里为拉模式，只返回一次结果。
     *
     */
    List<URL> lookup(URL url);

    /**
     * 取消所有本机的服务，用于关机的时候
     */
    void unregisterAllMyService();

}
