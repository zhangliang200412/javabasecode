package com.dragonsoft.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by zhangliang on 2019-09-14.
 */
public class MyFutureTask<T> implements Runnable {

    private Callable<T> callable;
    private T result = null;
    private boolean isDown =false;
    private Thread thread;

    public MyFutureTask(Callable<T> callable){
        this.callable = callable;
    }

    public T get(){
        if(isDown){
            return result;
        }

        thread = Thread.currentThread();

        while (!isDown){
            LockSupport.park();
        }

        return result;

//        while (result == null){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//        return null;
    }

    public void run() {
        result = callable.call();
        this.isDown = true;
        LockSupport.unpark(thread);
    }
}