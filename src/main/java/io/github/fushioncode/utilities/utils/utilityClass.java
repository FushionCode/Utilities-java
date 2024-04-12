package io.github.fushioncode.utilities.utils;

import java.util.Random;

public class utilityClass {
    public static void main(String[] args) {
        System.out.println(sayHello());
        System.out.println(sayHello("john"));
    }

    public static String sayHello(String name){
        return "Hello! "+name.toUpperCase();
    }

    public static String sayHello(){
        return "Hello! Somebody";
    }

    public static int generateRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(10) + 1;
    }
}
