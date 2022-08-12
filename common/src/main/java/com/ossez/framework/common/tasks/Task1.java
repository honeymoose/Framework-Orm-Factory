package com.ossez.framework.common.tasks;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;

public class Task1 {


    public static int getClosestToZero(int[] a) {
    /*
    Please implement this method to
	return the number in the array that is closest to zero.
	If there are two equally far from zero elements like 2 and -2,
	consider the positive element to be "closest" to zero.

	Please add a main method which evaluates and displays one or more test cases.
	*/

        int index = Math.abs(0 - a[0]);
        int result = a[0];
        for (int i : a) {
            int abs = Math.abs(0 - i);
            if (abs <= index) {
                index = abs;
                result = i;
            }
        }
        return result;


    }

    public static void main(String[] args) {
        int[] in = {};
        System.out.println(getClosestToZero(in));

    }


}
