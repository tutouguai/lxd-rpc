# lxd-rpc

#### 介绍
lxd-rpc是一款基于 Netty + Zookeeper + Protostuff 的简易 RPC 框架。
在对在线测评系统重构过程中，使用到了feign，对另一种远程调用方式产生了好奇，重复造轮子主要是为了学习和了解其内部运作机制。

Github:https://github.com/tutouguai/lxd-rpc

#### 目录
以下是重要的包的简介：
```
|- lxd-rpc-common：基础的代码
  |- extendsion：SPI扩展
  |- factory: 单例对象工厂，用于产生单例对象
  |- url: 参考 dubbo 的 URL，构建参数用的
|- lxd-rpc-core: rpc 核心逻辑
  |- annotation：里面包含了一些自定义的注解，例如 @RpcService(服务提供)、@RpcReference(服务引用)
  |- compress: 压缩，网络传输需要压缩数据
  |- config: 定义了一套配置的接口，例如配置服务绑定的端口，zookeeper的地址等
  |- loadbalance: 负载均衡，多个服务应该如何选择。有随机策略、轮询策略等，目前固定为随机策略
  |- faulttolareant: 集群容错，目前主要有快速失败、失败重试策略，默认为快速失败策略
  |- proxy: 代理，用于客户端代理，客户端调用服务接口，实际上是一个网络请求的过程
  |- registry: 注册中心，例如Nacos 注册中心、 zookeeper注册中心
  |- remote: 网络相关的东西，例如自定义协议、Netty 收发请求等,协议后续需要改进
  |- serialize: 序列化，网络传输，序列化是必不可少了
  |- spring: 一些 spring 相关的东西，例如，将服务提供者注入spring容器中，客户端获取bean是生成代理对象
|- lxd-rpc-demo: 框架的使用例子
  |- lxd-rpc-demo-client: 客户端，服务引用方
  |- lxd-rpc-demo-service: 服务提供方
```

#### 功能列表
- [x] 动态代理
    - [x] JDK Proxy
    - [ ] Javassist 生成代码，直接调用
    
- [x] 注册中心
    - [x] Zookeeper
    - [ ] Eureka
    - [ ] Nacos
    - [ ] Consul
    - [ ] ...
    
- [x] 序列化
    - [x] Protostuff
    - [ ] Kryo
    - [ ] ...
    
- [x] 压缩
    - [x] gzip
    - [ ] ...
    
- [x] 远程通信
    - [x] 自定义通信协议
    - [x] 使用 Netty 框架
    
- [x] 配置
    - [x] properties 文件配置
    - [ ] Apollo 动态配置

- [x] 负载均衡
    - [x] 随机策略
    - [x] 轮询策略
    - [x] 最小活跃数
    
- [x] 多版本

- [x] 自定义 SPI 扩展

- [x] 集群容错
    - [x] 重试策略
    - [x] 快速失败策略
      - [x] 重试幂等
    
- [ ] 优雅停机

- [ ] 监控后台

    

    

#### 运行
1. 环境要求：JDK8 以上、Lombok 插件
2. 需要安装 `Zookeeper`3.5.x及以上版本 并运行
3. 修改配置文件 `lxd-rpc.properties` 中的 zookeeper地址、监听端口，启动服务 `cn.leixd.rpc.demo.service.ServiceBootstrap`
4. 修改配置文件 `lxd-rpc.properties` 中的 zk 地址，启动客户端 `cn.leixd.rpc.demo.client.ClientBootstrap`
5. 访问客户端地址 `http://localhost:1234/user/v1/1` 就可以啦. `http://localhost:1234/user/v2/1` 可以访问另一个实现，这是多版本功能。
