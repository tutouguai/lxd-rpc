package cn.leixd.rpc.core.config.loader;

import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.setting.Setting;
import cn.hutool.setting.SettingUtil;
import cn.leixd.rpc.core.config.loader.ConfigLoader;
import lombok.extern.slf4j.Slf4j;

/**
 * lxd-rpc.properties 文件配置。prefix.configField=xxx
 *
 */
@Slf4j
public class PropertiesConfigLoader implements ConfigLoader {

    private Setting setting = null;

    public PropertiesConfigLoader() {
        try {
            setting = SettingUtil.get("lxd-rpc.properties");
        } catch (NoResourceException ex) {
            log.info("Config file 'lxd-rpc.properties' not exist!");
        }
    }


    @Override
    public String loadConfigItem(String key) {
        if (setting == null) {
            return null;
        }
        return setting.getStr(key);
    }
}
