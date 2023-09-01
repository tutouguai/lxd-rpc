package cn.leixd.demo.serve.impl;

import cn.leixd.demo.User;
import cn.leixd.demo.UserService;
import cn.leixd.rpc.core.annotaion.RpcService;
/**
 * 用户服务
 *
 */
@RpcService(version = "v2")
public class UserServiceImplV2 implements UserService {

    @Override
    public User getUser(Long id) {
        return User.builder().userId(id).userName("v2-user" + id).build();
    }
}
