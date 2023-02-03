package com.company;

public class Main {

    public static void main(String[] args){
        int [] arr = {1,4,2,1,6,9};
        int s = 0;
        for(int i: arr){
            s += i;
        }
        System.out.print(s);

    }
}
