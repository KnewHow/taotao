package com.taotao.common.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;
/**
 * 一些连接可能失效了，就需要每隔一段时间去释放一些连接
 * @author Administrator
 *
 */
public class IdleConnectionEvictor extends Thread{

        private final HttpClientConnectionManager connMgr;

        private volatile boolean shutdown;

        public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
            this.connMgr = connMgr;
            this.start();
        }
        
        

        @Override
        public void run() {
            try {
                while(!shutdown){
                    synchronized (this) {
                        wait(5000);
                        //关闭失效的连接
                        connMgr.closeExpiredConnections();
                    }
                }
                    
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                // 唤醒所有在等待的线程
                notifyAll();
            }
        }

}
