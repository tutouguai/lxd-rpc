package cn.leixd.demo.client;


import cn.leixd.demo.TestService;
import cn.leixd.rpc.core.annotaion.RpcReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RpcReference(version = "v1")
    private TestService testService;

    @RpcReference(version = "v2")
    private TestService testService2;

    @GetMapping("/{uid}")
    public String getUser(@PathVariable("uid") long uid) {
        String result=  this.testService.getUser(uid).toString();
        System.out.println(result);
        return result;
    }

    @GetMapping("/v2/{uid}")
    public String getUserV2(@PathVariable("uid") long uid) {
        String result=  this.testService2.getUser(uid).toString();
        System.out.println(result);
        return result;
    }
}
