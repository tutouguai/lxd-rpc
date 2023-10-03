package cn.leixd.rpc.core.config;

import cn.leixd.rpc.core.annotaion.Config;
import lombok.Data;

@Data
@Config(prefix = "cluster")
public class ClusterConfig {
    /**
     * 负载均衡策略
     */
    private String loadBalance;

    /**
     * 容错策略
     */
    private String faultTolerant;

    /**
     * 重试次数，只有容错策略是 'retry' 的时候才有效
     */
    private Integer retryTimes;

    /**
     * 重试间隔
     */
    private Integer retryInterval;
}
