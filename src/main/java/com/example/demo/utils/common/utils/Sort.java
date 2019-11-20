package com.example.demo.utils.common.utils;

public class Sort {
	

	public static String chooseSort(String arr){
		String sortedStr = "";
		String[] arrs = arr.split(",");
		for(int i = 0; i < arrs.length-1; i++){
			int k = i;
			for(int j = i+1; j < arrs.length; j++){
				if(arrs[i].compareTo(arrs[j])>0){
					k = j;
				}
			}
			String temp = arrs[i];
			arrs[i] = arrs[k];
			arrs[k] = temp;
		}
		for (int k = 0; k < arrs.length; k++){
			if (k == arrs.length-1){
				sortedStr += arrs[k];
				return sortedStr;
			}
			sortedStr += arrs[k] + "&";
		}
		return sortedStr;
	}
}
