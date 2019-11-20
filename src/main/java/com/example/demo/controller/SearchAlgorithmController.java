package com.example.demo.controller;

/**
 * Created by gyq on 2019/8/1.
 */
public class SearchAlgorithmController {

    /**
     *  二分查找：
     *     针对有序数组，如果数组不是有序的，查找之前先进行排序。
     */
    public static void main(String[] args) {

        int search = 7;
        int[] arrayInt = {1,3,5,6,7,9};
        //起始下标
        int start = 0;
        //终止下标
        int end = arrayInt.length - 1;
        //中间下标
        int mid;
        while (true) {
            mid = (start + end)/2;
            //如果中间值为要查找的值，则返回中间下标
            if (arrayInt[mid] == search) {
                System.out.println(mid + "--");
//                return mid;
                break;
            }
            //如果起始下标比终止下标大，则返回失败，没有找到该值
            else if (start > end) {
                System.out.println("error -------");
//                return error;
                break;
            }else {
                //如果中间值比要查找的值小，把起始下标设置为中间下标加一
                if (arrayInt[mid] < search) {
                    start = mid + 1;
                }
                //如果中间值比要查找的值大，把终止下标设置为中间下标减一
                else {
                    end = mid - 1;
                }
            }
        }

    }
}
