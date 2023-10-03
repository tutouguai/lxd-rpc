package cn.leixd.rpc.core.dto;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RPC 请求实体
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest {
    /**
     * 接口名
     */
    private String interfaceName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数列表
     */
    private Object[] params;
    /**
     * 参数类型列表
     */
    private Class<?>[] paramTypes;
    /**
     * 接口版本
     */
    private String version;

    public String getRpcServiceForCache() {
        if (StrUtil.isBlank(version)) {
            return interfaceName;
        }
        return interfaceName + "_" + version;
    }
    public String getFullMethodName(){
        return interfaceName + "."+ methodName;
    }
}
