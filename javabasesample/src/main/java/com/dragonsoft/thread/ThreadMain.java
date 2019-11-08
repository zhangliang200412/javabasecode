package com.dragonsoft.thread;

/**
 * Created by zhangliang on 2019-09-14.
 */
public class ThreadMain {

    public static void main(String args[]){




        for( int i =0 ; i<= 10; i++){
            final String name = "task" + i ;
            MyFutureTask<String> stringMyFutureTask = new MyFutureTask<String>(new Callable<String>() {
                public String call() {
                    return name;
                }
            });
            new Thread(stringMyFutureTask).start();

            String s = stringMyFutureTask.get();
            System.out.println("线程  "+ i + "获取结果：" + s);
        }

    }

}