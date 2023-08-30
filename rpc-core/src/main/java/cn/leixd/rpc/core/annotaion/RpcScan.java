package cn.leixd.rpc.core.annotaion;


import cn.leixd.rpc.core.spring.RpcScannerRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RpcScannerRegistry.class)
public @interface RpcScan {
    String[] basePackages();
}
