package cn.leixd.rpc.core.spring;

import cn.hutool.core.util.StrUtil;
import cn.leixd.rpc.core.annotaion.RpcScan;
import cn.leixd.rpc.core.annotaion.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


/**
 * rpc 扫描注册
 *
 */
@Slf4j
public class RpcScannerRegistry implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    /**
     * 服务扫描的基础包，是 @RpcScan 的哪个属性
     */
    private static final String SERVER_SCANNER_BASE_PACKAGE_FIELD = "basePackages";

    /**
     * 内部扫描的基础包列表
     */
    private static final String[] INNER_SCANNER_BASE_PACKAGES = {"cn.leixd.rpc.common", "cn.leixd.rpc.core"};

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader  = resourceLoader;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //扫描注解
        Map<String, Object> annotationAttributes = importingClassMetadata
                .getAnnotationAttributes(RpcScan.class.getName());
        RpcScanner serviceScanner = new RpcScanner(registry, RpcService.class);
        // leixd-rpc 内部的类
        RpcScanner innerScanner = new RpcScanner(registry, Component.class, Service.class, Resource.class);
        if (resourceLoader != null) {
            serviceScanner.setResourceLoader(resourceLoader);
            innerScanner.setResourceLoader(resourceLoader);
        }
        String[] serviceBasePackages = (String[]) annotationAttributes.get(SERVER_SCANNER_BASE_PACKAGE_FIELD);
        int serviceCount = serviceScanner.scan(serviceBasePackages);
        log.info(StrUtil.format("服务提供方. packages={}, count={}", serviceBasePackages, serviceCount));
        int innerCount = innerScanner.scan(INNER_SCANNER_BASE_PACKAGES);
        log.info(StrUtil.format("框架内部. packages={}, count={}", INNER_SCANNER_BASE_PACKAGES, innerCount));
    }
}
