package cn.leixd.demo.serve.impl;

import cn.leixd.demo.User;
import cn.leixd.demo.TestService;
import cn.leixd.rpc.core.annotaion.RpcService;


@RpcService(version = "v1")
public class TestServiceImpl implements TestService {

    @Override
    public User getUser(Long id) {
        return User.builder().userId(id).userName("user" + id).build();
    }
}
