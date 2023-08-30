package cn.leixd.rpc.core.proxy;

import cn.leixd.rpc.core.annotaion.RpcReference;
import cn.leixd.rpc.core.dto.RpcRequest;
import cn.leixd.rpc.core.dto.RpcResponse;
import cn.leixd.rpc.core.dto.RpcResult;
import cn.leixd.rpc.core.invoke.Invoker;
import cn.leixd.rpc.core.invoke.NettyInvoker;
import cn.lxd.rpc.common.factory.SingletonFactory;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Rpc 调用方的代理
 *
 */
public class RpcClientProxy implements InvocationHandler {

    private final RpcReference rpcReference;

    public RpcClientProxy(RpcReference rpcReference) {
        this.rpcReference = rpcReference;
    }

    /**
     * 获取代理类
     *
     * @param clazz 需要代理的接口类
     * @param <T>   类型
     * @return 代理类
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        // TODO: 缓存，不然会生成很多代理类
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }
    @Override
    @SneakyThrows
    public Object invoke(Object proxy, Method method, Object[] args) {
        // 调用 nettyClient 发请求
        RpcRequest request = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getCanonicalName())
                .methodName(method.getName())
                .params(args)
                .paramTypes(method.getParameterTypes())
                .version(rpcReference.version())
                .build();
        // invoke 发送请求，获取结果
        Invoker invoker = SingletonFactory.getInstance(NettyInvoker.class);
        RpcResult rpcResult = invoker.invoke(request);
        return ((RpcResponse<?>) rpcResult.getData()).getData();
    }
}
