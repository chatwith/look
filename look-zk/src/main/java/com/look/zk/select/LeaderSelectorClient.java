package com.look.zk.select;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * user: xingjun.zhang
 * Date: 2016/1/10 0010
 */
public class LeaderSelectorClient extends LeaderSelectorListenerAdapter implements Closeable {

    private String name;
    private LeaderSelector leaderSelector;
    private String PATH = "/leaderselector";

    public LeaderSelectorClient(CuratorFramework curatorFramework, String name) {
        this.name = name;
        leaderSelector = new LeaderSelector(curatorFramework, PATH, this);
        leaderSelector.autoRequeue();
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        super.stateChanged(client, newState);
    }

    public void start() throws IOException {
        leaderSelector.start();
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    /**
     * client成为leader后，会调用此方法
     */
    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
        int waitSeconds = (int) (5 * Math.random()) + 1;
        System.out.println(name + "是当前的leader");
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(name + " 让出领导权\n");
        }
    }
}
