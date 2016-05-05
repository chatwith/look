package com.look.common.report;

import com.look.common.model.LoggerInfo;
import com.netflix.curator.RetryPolicy;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static com.look.common.report.ZKBasedLoggerInfoUtil.createData;
import static com.look.common.report.ZKBasedLoggerInfoUtil.createPath;
/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class ZKBasedLoggerInfoReporter implements LoggerInfoReporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZKBasedLoggerInfoReporter.class);
    private final String zkAddresses;
    private final String nameSpace;
    private final CuratorFramework curatorFramework;
    private final Set<String> paths = new HashSet<String>();

    public ZKBasedLoggerInfoReporter(String zkAddresses, String nameSpace) {
        this.zkAddresses = zkAddresses;
        this.nameSpace = nameSpace;
        this.curatorFramework = createAndStartClient(zkAddresses, nameSpace);
    }

    private CuratorFramework createAndStartClient(String zkAddresses, String nameSpace) {
        RetryPolicy rp = new ExponentialBackoffRetry(1000, 3);//重试机制
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder().connectString(zkAddresses)
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000)
                .retryPolicy(rp);
        builder.namespace(nameSpace);
        CuratorFramework curatorFramework = builder.build();
        curatorFramework.start();// 放在这前面执行
        curatorFramework.newNamespaceAwareEnsurePath(nameSpace);
        return curatorFramework;
    }

    @Override
    public void report(LoggerInfo var1) {
        String path = createPath(var1);
        byte[] data = createData(var1);
        try {
            if (this.curatorFramework.checkExists().forPath(path) != null) {
                LOGGER.info("path {} exists, delete node.", path);
                try {
                    this.curatorFramework.delete().guaranteed().forPath(path);
                    LOGGER.info("success to delete meta data node {}.", path);
                } catch (Exception e) {
                    LOGGER.error("failed to delete meta data node {}", path, e);
                }
            }
            this.curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, data);
            this.paths.add(path);
            LOGGER.info("success to report {} to {}", var1, path);
        } catch (Exception e) {
            LOGGER.error("failed to report logger meta data", e);
        }
    }


    @Override
    public void clean() {
        for (String path : this.paths) {
            try {
                this.curatorFramework.delete().guaranteed().forPath(path);
                LOGGER.info("success to delete meta data node {}.", path);
            } catch (Exception e) {
                LOGGER.error("failed to clean logger meta data", e);
//                e.printStackTrace();
            }
        }
    }
}
