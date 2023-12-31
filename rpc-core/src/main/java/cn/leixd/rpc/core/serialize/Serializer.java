package cn.leixd.rpc.core.serialize;


import cn.lxd.rpc.common.extension.SPI;

/**
 * 序列化器
 *
 */
@SPI("protostuff")
public interface Serializer {

    /**
     * 序列化
     *
     * @param object 要序列化的对象
     * @return 字节数组
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @param clazz 要反序列化的类
     * @param <T>   类型
     * @return 反序列化的对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
