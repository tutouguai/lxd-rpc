package cn.leixd.rpc.core.annotaion;

import java.lang.annotation.*;


/**
 * RPC service annotation, marked on the service implementation class
 *
 */

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {
    /**
     * 版本，如果同样签名的接口参数不兼容，可以用版本区分
     *
     * @return 默认空
     */
    String version() default "";
}
