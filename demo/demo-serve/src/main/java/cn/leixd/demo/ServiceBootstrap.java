package cn.leixd.demo;


import cn.leixd.rpc.core.annotaion.RpcScan;
import cn.leixd.rpc.core.remote.server.netty.NettyServerBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@RpcScan(basePackages = {"cn.leixd.demo.serve"})
public class ServiceBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ServiceBootstrap.class, args);
        NettyServerBootstrap serverBootstrap = (NettyServerBootstrap) run.getBean("nettyServerBootstrap");
        serverBootstrap.start();
    }
}
