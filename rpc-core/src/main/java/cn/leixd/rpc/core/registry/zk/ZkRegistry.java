package cn.leixd.rpc.core.registry.zk;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.net.URLEncoder;
import cn.leixd.rpc.core.registry.AbstractRegistry;
import cn.lxd.rpc.common.constants.RegistryConst;
import cn.lxd.rpc.common.url.URL;
import cn.lxd.rpc.common.url.URLParser;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * zk 注册中心。<br>
 * 这里面会引入 curator
 *
 */
@Slf4j
public class ZkRegistry extends AbstractRegistry {

    private final CuratorZkClient zkClient;
    private static final URLEncoder urlEncoder = URLEncoder.createPathSegment();
    private static final Charset charset = Charset.defaultCharset();

    public ZkRegistry(URL url) {
        zkClient = new CuratorZkClient(url);
    }

    @Override
    public void doRegister(URL url) {
        zkClient.createEphemeralNode(toUrlPath(url));
        watch(url);
    }

    @Override
    public void doUnregister(URL url) {
        zkClient.removeNode(toUrlPath(url));
        watch(url);
    }

    @Override
    public List<URL> doLookup(URL condition) {
        List<String> children = zkClient.getChildren(toServicePath(condition));
        List<URL> urls = children.stream()
                .map(s -> URLParser.toURL(URLDecoder.decode(s, charset)))
                .collect(Collectors.toList());
        // 获取到的每个都添加监听
        for (URL url : urls) {
            watch(url);
        }
        return urls;
    }

    /**
     * 转成全路径，包括节点内容
     */
    private String toUrlPath(URL url) {
        return toServicePath(url) + "/" + urlEncoder.encode(url.toFullString(), charset);
    }

    /**
     * 转成服务的路径，例如：/lxd-rpc/com.leixd.rpc.demo.service.api.UserService/providers
     */
    private String toServicePath(URL url) {
        return getServiceNameFromUrl(url) + "/" + RegistryConst.PROVIDERS_CATEGORY;
    }

    /**
     * 监听
     */
    private void watch(URL url) {
        String path = toServicePath(url);
        zkClient.addListener(path, (type, oldData, data) -> {
            log.info("watch event. type={}, oldData={}, data={}", type, oldData, data);
            reset(url);
        });
    }

}
