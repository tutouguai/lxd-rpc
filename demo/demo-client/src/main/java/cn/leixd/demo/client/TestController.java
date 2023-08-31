package cn.leixd.demo.client;


import cn.leixd.demo.serve.UserService;
import cn.leixd.rpc.core.annotaion.RpcReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RpcReference(version = "v1")
    private UserService userService;

    @RpcReference(version = "v2")
    private UserService userService2;

    @GetMapping("/{uid}")
    public void getUser(@PathVariable("uid") long uid) {
        System.out.println(userService.getUser(uid).toString());
    }

    @GetMapping("/v2/{uid}")
    public void getUserV2(@PathVariable("uid") long uid) {
        System.out.println(userService2.getUser(uid).toString());
    }
}
