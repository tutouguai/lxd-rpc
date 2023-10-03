package cn.leixd.rpc.core.loadbalance;

import cn.hutool.core.util.RandomUtil;
import cn.leixd.rpc.core.dto.RpcRequest;
import cn.lxd.rpc.common.url.URL;

import java.util.List;

/**
 * 随机负载均衡
 *
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    @Override
    protected URL doSelect(List<URL> candidateUrls, RpcRequest request, String ... urls) {
        int size = candidateUrls.size();
        int index = RandomUtil.randomInt(size);
        return candidateUrls.get(index);
    }
}
