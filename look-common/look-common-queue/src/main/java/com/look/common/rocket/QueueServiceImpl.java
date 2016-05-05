package com.look.common.rocket;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * user: xingjun.zhang
 * Date: 2016/1/2 0002
 */
public abstract class QueueServiceImpl implements QueueService {
    private final AtomicBoolean started = new AtomicBoolean(false);

    protected abstract void doStart() throws RuntimeException;

    protected abstract void doStop() throws RuntimeException;

    @Override
    public void start() throws RuntimeException {
        if (started.compareAndSet(false, true)) {
            doStart();
        }
    }

    @Override
    public void stop() throws RuntimeException {
        if (started.compareAndSet(true, false)) {
            doStop();
        }
    }
}
