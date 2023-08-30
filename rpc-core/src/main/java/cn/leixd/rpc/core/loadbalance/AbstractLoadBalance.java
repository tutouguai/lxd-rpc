package cn.leixd.rpc.core.loadbalance;

import cn.hutool.core.collection.CollectionUtil;
import cn.leixd.rpc.core.dto.RpcRequest;
import cn.lxd.rpc.common.url.URL;

import java.util.List;

/**
 * 抽象的负载均衡
 *
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public URL select(List<URL> candidateUrls, RpcRequest request) {
        if (CollectionUtil.isEmpty(candidateUrls)) {
            return null;
        }
        if (candidateUrls.size() == 1) {
            return candidateUrls.get(0);
        }
        return doSelect(candidateUrls, request);
    }

    protected abstract URL doSelect(List<URL> candidateUrls, RpcRequest request);
}
