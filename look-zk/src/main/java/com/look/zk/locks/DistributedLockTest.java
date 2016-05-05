package com.look.zk.locks;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实例
 *
 * @author shencl
 */
public class DistributedLockTest {
    private static CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.137.4:2181,192.168.137.5:2181"
            , new ExponentialBackoffRetry(1000, 3));
    private static final String PATH = "/locks";
    // 锁等待时间
    private static final int wait_time = 10;


    public static void main(String[] args) {
        try {
            // 进程内部（可重入）读写锁
            final InterProcessReadWriteLock lock;
            // 读锁
            final InterProcessLock readLock;
            // 写锁
            final InterProcessLock writeLock;
            client.start();
            lock = new InterProcessReadWriteLock(client, PATH);
            readLock = lock.readLock();
            writeLock = lock.writeLock();
            final CountDownLatch latch = new CountDownLatch(20);
            ExecutorService service = Executors.newCachedThreadPool();
            for (int i = 0; i < 10; i++) {
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            doWork();
                        } catch (Exception e) {
                            // ingore;
                        }
                    }

                    public void doWork() throws Exception {
                        try {
                            if (!readLock.acquire(5, TimeUnit.SECONDS)) {
                                System.err.println(Thread.currentThread().getName() + "等待" + wait_time + "秒，仍未能获取到lock,准备放弃。");
                            }
                            // 模拟job执行时间0-2000毫秒
                            int exeTime = new Random().nextInt(5000);
                            System.out.println(Thread.currentThread().getName() + " read开始执行,预计执行时间= " + exeTime + "毫秒----------");
                            Thread.sleep(exeTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            readLock.release();
                        }
                    }
                });
            }

            for (int i = 0; i < 5; i++) {
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            doWork();
                        } catch (Exception e) {
                            // ingore;
                        }
                    }

                    public void doWork() throws Exception {
                        try {
                            if (!writeLock.acquire(10, TimeUnit.SECONDS)) {
                                System.err.println(Thread.currentThread().getName() + "等待" + wait_time + "秒，仍未能获取到lock,准备放弃。");
                            }
                            // 模拟job执行时间0-2000毫秒
                            int exeTime = new Random().nextInt(2000);
                            System.out.println(Thread.currentThread().getName() + " write开始执行,预计执行时间= " + exeTime + "毫秒----------");
                            Thread.sleep(exeTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            writeLock.release();
                        }
                    }
                });
            }
            latch.await(10, TimeUnit.SECONDS);
            System.out.println("go start------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }
}

