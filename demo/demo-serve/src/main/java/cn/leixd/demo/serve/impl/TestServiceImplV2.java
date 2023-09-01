package cn.leixd.demo.serve.impl;

import cn.leixd.demo.User;
import cn.leixd.demo.TestService;
import cn.leixd.rpc.core.annotaion.RpcService;


@RpcService(version = "v2")
public class TestServiceImplV2 implements TestService {

    @Override
    public User getUser(Long id) {
        return User.builder().userId(id).userName("v2-user" + id).build();
    }
}
