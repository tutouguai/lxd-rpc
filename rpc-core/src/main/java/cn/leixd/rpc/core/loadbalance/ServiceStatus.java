package cn.leixd.rpc.core.loadbalance;

import cn.lxd.rpc.common.url.URL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceStatus {


    private static final ConcurrentMap<String, ServiceStatus> SERVICE_STATISTICS = new ConcurrentHashMap<String, ServiceStatus>();

    private static final ConcurrentMap<String, ConcurrentMap<String, ServiceStatus>> METHOD_STATISTICS = new ConcurrentHashMap<String, ConcurrentMap<String, ServiceStatus>>();


    public static ServiceStatus getStatus(String uri) {
        ServiceStatus serviceStatus = SERVICE_STATISTICS.get(uri);
        if(serviceStatus == null){
            SERVICE_STATISTICS.putIfAbsent(uri, new ServiceStatus());
            serviceStatus = SERVICE_STATISTICS.get(uri);
        }
        return serviceStatus;
    }

    public static void removeStatus(String uri) {
        SERVICE_STATISTICS.remove(uri);
    }
    public static ServiceStatus getStatus(String uri, String methodName) {
        ConcurrentMap<String, ServiceStatus> map = METHOD_STATISTICS.get(uri);
        if(map==null){
            METHOD_STATISTICS.putIfAbsent(uri, new ConcurrentHashMap<String, ServiceStatus>());
            map = METHOD_STATISTICS.get(uri);
        }
        ServiceStatus status = map.get(methodName);
        if (status == null) {
            map.putIfAbsent(methodName, new ServiceStatus());
            status = map.get(methodName);
        }
        return status;
    }

    public static void removeStatus(String uri, String methodName) {
        ConcurrentMap<String, ServiceStatus> map = METHOD_STATISTICS.get(uri);
        if (map != null) {
            map.remove(methodName);
        }
    }
    public static void beginCount(String uri, String methodName) {
        beginCount(getStatus(uri));
        beginCount(getStatus(uri, methodName));
    }
    private static void beginCount(ServiceStatus status) {
        status.active.incrementAndGet();
    }

    public static void endCount(String uri, String methodName, long elapsed, boolean succeeded) {
        endCount(getStatus(uri), elapsed, succeeded);
        endCount(getStatus(uri, methodName), elapsed, succeeded);
    }

    private static void endCount(ServiceStatus status, long elapsed, boolean succeeded) {
        status.active.decrementAndGet();
        status.total.incrementAndGet();
        status.totalElapsed.addAndGet(elapsed);
        if (status.maxElapsed.get() < elapsed) {
            status.maxElapsed.set(elapsed);
        }
        if (succeeded) {
            if (status.succeededMaxElapsed.get() < elapsed) {
                status.succeededMaxElapsed.set(elapsed);
            }
        } else {
            status.failed.incrementAndGet();
            status.failedElapsed.addAndGet(elapsed);
            if (status.failedMaxElapsed.get() < elapsed) {
                status.failedMaxElapsed.set(elapsed);
            }
        }
    }


    private final AtomicInteger active = new AtomicInteger();

    private final AtomicLong total = new AtomicLong();

    private final AtomicInteger failed = new AtomicInteger();

    private final AtomicLong totalElapsed = new AtomicLong();

    private final AtomicLong failedElapsed = new AtomicLong();

    private final AtomicLong maxElapsed = new AtomicLong();

    private final AtomicLong failedMaxElapsed = new AtomicLong();

    private final AtomicLong succeededMaxElapsed = new AtomicLong();


    /**
     * get active.
     *
     * @return active
     */
    public int getActive() {
        return active.get();
    }

    /**
     * get total.
     *
     * @return total
     */
    public long getTotal() {
        return total.longValue();
    }

    /**
     * get total elapsed.
     *
     * @return total elapsed
     */
    public long getTotalElapsed() {
        return totalElapsed.get();
    }

    /**
     * get average elapsed.
     *
     * @return average elapsed
     */
    public long getAverageElapsed() {
        long total = getTotal();
        if (total == 0) {
            return 0;
        }
        return getTotalElapsed() / total;
    }

    /**
     * get max elapsed.
     *
     * @return max elapsed
     */
    public long getMaxElapsed() {
        return maxElapsed.get();
    }

    /**
     * get failed.
     *
     * @return failed
     */
    public int getFailed() {
        return failed.get();
    }

    /**
     * get failed elapsed.
     *
     * @return failed elapsed
     */
    public long getFailedElapsed() {
        return failedElapsed.get();
    }

    /**
     * get failed average elapsed.
     *
     * @return failed average elapsed
     */
    public long getFailedAverageElapsed() {
        long failed = getFailed();
        if (failed == 0) {
            return 0;
        }
        return getFailedElapsed() / failed;
    }

    /**
     * get failed max elapsed.
     *
     * @return failed max elapsed
     */
    public long getFailedMaxElapsed() {
        return failedMaxElapsed.get();
    }

    /**
     * get succeeded.
     *
     * @return succeeded
     */
    public long getSucceeded() {
        return getTotal() - getFailed();
    }

    /**
     * get succeeded elapsed.
     *
     * @return succeeded elapsed
     */
    public long getSucceededElapsed() {
        return getTotalElapsed() - getFailedElapsed();
    }

    /**
     * get succeeded average elapsed.
     *
     * @return succeeded average elapsed
     */
    public long getSucceededAverageElapsed() {
        long succeeded = getSucceeded();
        if (succeeded == 0) {
            return 0;
        }
        return getSucceededElapsed() / succeeded;
    }

    /**
     * get succeeded max elapsed.
     *
     * @return succeeded max elapsed.
     */
    public long getSucceededMaxElapsed() {
        return succeededMaxElapsed.get();
    }

    /**
     * Calculate average TPS (Transaction per second).
     *
     * @return tps
     */
    public long getAverageTps() {
        if (getTotalElapsed() >= 1000L) {
            return getTotal() / (getTotalElapsed() / 1000L);
        }
        return getTotal();
    }


    /**
     * 服务url
     */
    private URL url;
    /**
     * 服务响应时间
     */
    private Integer responseTime;
}
