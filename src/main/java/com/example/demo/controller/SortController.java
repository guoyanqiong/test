package com.example.demo.controller;

/**
 * Created by gyq on 2019/8/2.
 */
public class SortController {

    /**
     *  排序：
     *      冒泡排序
     *      选择排序
     *      插入排序
     *      对象排序
     */

    public static void main(String[] args) {
        int[] list = {3,2,6,7,4,1};//1,2,3,4,6,7
//        bubbleSort_2(list);
        selectionSort(list);// 1,2,6,7,4,3  -->  1,2,6,7,4,3  -->  1,2,3,7,4,6  -->  1,2,3,4,7,6  --> 1,2,3,4,6,7
    }

    //冒泡排序
    public static void bubbleSort_2(int[] list) {
        int temp = 0; // 用来交换的临时数
        boolean bChange = false; // 交换标志
        // 要遍历的次数
        for (int i = 0; i < list.length - 1; i++) {
            bChange = false;
            // 从后向前依次的比较相邻两个数的大小，遍历一次后，把数组中第i小的数放在第i个位置上
            for (int j = list.length - 1; j > i; j--) {
                // 比较相邻的元素，如果前面的数大于后面的数，则交换
                if (list[j - 1] > list[j]) {
                    temp = list[j - 1];
                    list[j - 1] = list[j];
                    list[j] = temp;
                    bChange = true;
                }
            }
            // 如果标志为false，说明本轮遍历没有交换，已经是有序数列，可以结束排序
            if (false == bChange)
                break;
            System.out.format("第 %d 趟：\t", i);
            for (int res:list) {
                System.out.println(res);
            }
        }
    }

    //选择排序
    public static void selectionSort(int[] list) {
        int out,in,min;
        for (out = 0; out < list.length-1; out++) {
            min = out;
            for (in = out+1; in < list.length; in++) {
                if (list[in] < list[min]) {
                    min = in;
                }
            }
            int temp = list[out];
            list[out] = list[min];
            list[min] = temp;
            System.out.format("第 %d 趟：\t", out);
            for (int res:list) {
                System.out.println(res);
            }
        }
    }
}
