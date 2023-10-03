package cn.leixd.rpc.core.faulttolerant;

import cn.leixd.rpc.core.invoke.Invoker;
import cn.lxd.rpc.common.extension.SPI;

@SPI("fail-fast")
public interface FaultTolerantInvoker extends Invoker {
}
