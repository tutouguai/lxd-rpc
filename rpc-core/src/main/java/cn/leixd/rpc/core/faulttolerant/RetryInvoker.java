package cn.leixd.rpc.core.faulttolerant;

import cn.leixd.rpc.core.dto.RpcRequest;
import cn.leixd.rpc.core.dto.RpcResult;
import cn.leixd.rpc.core.invoke.Invoker;
import cn.lxd.rpc.common.constants.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class RetryInvoker extends AbstractFaultTolerantInvoker{

    private static final Integer DEFAULT_RETRY_TIMES = 3;
    private static final Integer DEFAULT_RETRY_INTERVAL = 500;

    @Override
    protected RpcResult doInvoke(RpcRequest request, Invoker invoker) {
        int retryTimes = Optional.ofNullable(clusterConfig.getRetryTimes()).orElse(DEFAULT_RETRY_TIMES);
        int retryInterval = Optional.ofNullable(clusterConfig.getRetryInterval()).orElse(DEFAULT_RETRY_INTERVAL);
        RpcException rpcException = null;
        for(int i = 0; i < retryTimes; i++){
            try {
                RpcResult rpcResult = invoker.invoke(request);
                if(rpcResult.isSuccess()){
                    return rpcResult;
                }
            }catch (RpcException e){
                log.error("invoke error in retry times:"+i, e);
                rpcException = e;
            }
            try {
                Thread.sleep(retryInterval);
            } catch (InterruptedException e) {
                log.error("retry thread sleep error in retry times:"+i, e);
            }
        }
        if (rpcException == null) {
            rpcException = new RpcException("invoker error. request=" + request);
        }
        throw rpcException;
    }
}
