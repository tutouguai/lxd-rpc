package cn.leixd.rpc.common.test.extension;

import cn.lxd.rpc.common.extension.SPI;

@SPI("default")
public interface Extension {
    void doSomething();
}
