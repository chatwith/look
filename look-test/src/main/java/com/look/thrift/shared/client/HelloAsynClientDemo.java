package com.look.thrift.shared.client;

/**
 * user: xingjun.zhang
 * Date: 2016/7/17 0017
 */
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.look.thrift.shared.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;


/**
 * blog http://www.micmiu.com
 *
 * @author Michael
 *
 */
public class HelloAsynClientDemo {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    /**
     *
     * @param userName
     */
    public void startClient(String userName) {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP,
                    SERVER_PORT, TIMEOUT);

            TProtocolFactory tprotocol = new TCompactProtocol.Factory();
            HelloWorldService.AsyncClient asyncClient = new HelloWorldService.AsyncClient(
                    tprotocol, clientManager, transport);
            System.out.println("Client start .....");

            CountDownLatch latch = new CountDownLatch(1);
            AsynCallback callBack = new AsynCallback(latch);
            System.out.println("call method sayHello start ...");
            asyncClient.sayHello(userName, callBack);
            System.out.println("call method sayHello .... end");
            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =:" + wait);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("startClient end.");
    }

    public class AsynCallback implements AsyncMethodCallback<HelloWorldService.AsyncClient.sayHello_call>{
        private CountDownLatch latch;

        public AsynCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onComplete(HelloWorldService.AsyncClient.sayHello_call response) {
            System.out.println("onComplete");
            try {
                // Thread.sleep(1000L * 1);
                System.out.println("AsynCall result =:"
                        + response.getResult().toString());
            } catch (TException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }

        @Override
        public void onError(Exception exception) {
            System.out.println("onError :" + exception.getMessage());
            latch.countDown();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloAsynClientDemo client = new HelloAsynClientDemo();
        client.startClient("Michael");

    }

}
