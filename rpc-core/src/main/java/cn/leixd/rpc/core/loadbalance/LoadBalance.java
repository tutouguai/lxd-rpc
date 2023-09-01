package cn.leixd.rpc.core.loadbalance;


import cn.leixd.rpc.core.dto.RpcRequest;
import cn.lxd.rpc.common.extension.SPI;
import cn.lxd.rpc.common.url.URL;

import java.util.List;

/**
 * 负载均衡
 *

 */
@SPI("random")
public interface LoadBalance {

    /**
     * 选择
     *
     * @param candidateUrls 候选的 URL
     * @param request       请求
     * @return 选择的 URL
     */
    URL select(List<URL> candidateUrls, RpcRequest request);
}
