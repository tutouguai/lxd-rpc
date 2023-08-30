package cn.leixd.rpc.core.annotaion;


import java.lang.annotation.*;

/**
 * Rpc 引用，服务调用方用
 *
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RpcReference {
    /**
     * 版本，没有特殊要求不用填写
     *
     * @return 版本，默认""
     */
    String version() default "";
}
