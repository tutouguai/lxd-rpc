package cn.leixd.rpc.core.config;


import cn.leixd.rpc.core.annotaion.Config;
import cn.leixd.rpc.core.consts.CompressType;
import cn.leixd.rpc.core.consts.SerializeType;
import lombok.Data;

/**
 * 协议相关配置
 *
 */
@Data
@Config(prefix = "protocol")
public class ProtocolConfig {

    /**
     * 序列化类型 {@link SerializeType}
     */
    private String serializeType;

    /**
     * 压缩类型 {@link CompressType}
     */
    private String compressType;

    public String getSerializeType() {
        return serializeType != null ? serializeType : SerializeType.PROTOSTUFF.getName();
    }

    public String getCompressType() {
        return compressType != null ? compressType : CompressType.DUMMY.getName();
    }
}
