package cn.leixd.demo;

import cn.leixd.rpc.core.annotaion.RpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 */
@SpringBootApplication
@RpcScan(basePackages = {"cn.leixd.demo.clent"})
public class ClientBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(ClientBootstrap.class, args);
    }

}
