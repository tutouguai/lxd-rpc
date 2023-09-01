package cn.lxd.rpc.common.extension;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SPI {
    /**
     * 缺省扩展点名。
     */
    String value() default "";
}
