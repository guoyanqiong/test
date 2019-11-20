package com.example.demo.controller;

/**
 * Created by gyq on 2019/7/19.
 */
public class TestController {

    public static volatile int a = 0;

    public static void main(String[] args) {
        new Thread(()->{
            //第一种情况：输出0
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            a = 1;
        },"thread1111").start();


        //第二种情况：没有输出
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        if (0 == a) {
            //第三种情况：输出1
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(a + "========");
        }
    }
}
