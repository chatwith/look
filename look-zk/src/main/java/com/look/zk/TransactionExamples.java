package com.look.zk;

/**
 * user: xingjun.zhang
 * Date: 2016/1/10 0010
 */

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.Collection;

/**
 * 事务操作
 *
 * @author shencl
 */
public class TransactionExamples {
    private static CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.137.4:2181,192.168.137.5:2181"
            , new ExponentialBackoffRetry(1000, 3));

    public static void main(String[] args) {
        try {
            client.start();
            // 开启事务
            CuratorTransaction transaction = client.inTransaction();

            Collection<CuratorTransactionResult> results = transaction.create()
                    .forPath("/crud/path", "some data".getBytes())
                    .and().setData().forPath("/crud/path", "other data".getBytes())
                    .and().delete().forPath("/crud/path")
                    .and().commit();

            for (CuratorTransactionResult result : results) {
                System.out.println(result.getForPath() + " - " + result.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放客户端连接
            CloseableUtils.closeQuietly(client);
        }

    }
}
