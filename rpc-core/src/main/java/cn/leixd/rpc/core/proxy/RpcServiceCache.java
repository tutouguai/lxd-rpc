package cn.leixd.rpc.core.proxy;

import cn.hutool.core.util.StrUtil;
import cn.lxd.rpc.common.constants.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务缓存
 *
 */
@Slf4j
public class RpcServiceCache {

    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    public static void addService(String version, Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException("add service error. service not implements interface. service=" + service.getClass().getName());
        }
        String rpcServiceName;
        if (StrUtil.isBlank(version)) {
            rpcServiceName = interfaces[0].getCanonicalName();
        } else {
            rpcServiceName = interfaces[0].getCanonicalName() + "_" + version;
        }
        serviceMap.putIfAbsent(rpcServiceName, service);
        log.info(StrUtil.format("add service. rpcServiceName={}, class={}", rpcServiceName, service.getClass()));
    }

    public static Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        if (service == null) {
            throw new RpcException("rpcService not found." + rpcServiceName);
        }
        return service;
    }
}
