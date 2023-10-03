package cn.leixd.rpc.core.loadbalance;



import cn.leixd.rpc.core.dto.RpcRequest;
import cn.lxd.rpc.common.url.URL;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * 轮询
 *
 */
public class RoundRobinLoadBalance extends AbstractLoadBalance {

    private final LongAdder curIndex = new LongAdder();

    @Override
    protected URL doSelect(List<URL> candidateUrls, RpcRequest request, String ... urls) {
        int index = (int) (curIndex.longValue() % candidateUrls.size());
        curIndex.increment();
        return candidateUrls.get(index);
    }
}
