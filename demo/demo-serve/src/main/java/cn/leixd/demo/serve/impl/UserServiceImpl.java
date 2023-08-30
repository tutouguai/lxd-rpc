package cn.leixd.demo.serve.impl;

import cn.leixd.demo.serve.User;
import cn.leixd.demo.serve.UserService;
import cn.leixd.rpc.core.annotaion.RpcService;

/**
 * 用户服务
 *
 */
@RpcService(version = "v1")
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(Long id) {
        return User.builder().userId(id).userName("user" + id).build();
    }
}
