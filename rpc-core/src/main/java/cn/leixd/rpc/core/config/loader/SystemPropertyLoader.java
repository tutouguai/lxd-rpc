package cn.leixd.rpc.core.config.loader;

import cn.leixd.rpc.core.config.loader.ConfigLoader;
import lombok.extern.slf4j.Slf4j;

/**
 * java 参数配置 -Dprefix.configField=xxx
 *
 */
@Slf4j
public class SystemPropertyLoader implements ConfigLoader {

    @Override
    public String loadConfigItem(String key) {
        return System.getProperty(key);
    }
}
