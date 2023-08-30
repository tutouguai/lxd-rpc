package cn.leixd.rpc.core.config.loader;


/**
 * 配置加载器
 *
 */
public interface ConfigLoader {

    /**
     * 加载配置项
     *
     * @param key 配置的 key
     * @return 配置项的值，如果不存在，返回 null
     */
    String loadConfigItem(String key);

}
