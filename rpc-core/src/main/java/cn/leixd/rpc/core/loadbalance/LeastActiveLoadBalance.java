package cn.leixd.rpc.core.loadbalance;

import cn.leixd.rpc.core.dto.RpcRequest;
import cn.lxd.rpc.common.url.URL;

import java.util.List;
import java.util.Random;

public class LeastActiveLoadBalance extends AbstractLoadBalance{

    private final Random random = new Random();

    @Override
    protected URL doSelect(List<URL> candidateUrls, RpcRequest request, String ... urls) {
        //TODO:调用此方法前，检查节点是否存活
        int length = candidateUrls.size(); // 总个数
        int leastActive = -1; // 最小的活跃数
        int leastCount = 0; // 相同最小活跃数的个数
        int[] leastIndexs = new int[length]; // 相同最小活跃数的下标
        for (int i = 0; i < length; i++) {
            URL url = candidateUrls.get(i);
            int active = ServiceStatus.getStatus(url.toFullString(), request.getFullMethodName()).getActive(); // 活跃数
            if (leastActive == -1 || active < leastActive) { // 发现更小的活跃数，重新开始
                leastActive = active; // 记录最小活跃数
                leastCount = 1; // 重新统计相同最小活跃数的个数
                leastIndexs[0] = i; // 重新记录最小活跃数下标
            } else if (active == leastActive) { // 累计相同最小的活跃数
                leastIndexs[leastCount ++] = i; // 累计相同最小活跃数下标
            }
        }
        if (leastCount == 1) {
            // 如果只有一个最小则直接返回
            return candidateUrls.get(leastIndexs[0]);
        }
        // 如果如果有多个则均等随机
        return candidateUrls.get(leastIndexs[random.nextInt(leastCount)]);
    }
}
