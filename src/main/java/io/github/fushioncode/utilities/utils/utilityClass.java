package io.github.fushioncode.utilities.utils;

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
}
