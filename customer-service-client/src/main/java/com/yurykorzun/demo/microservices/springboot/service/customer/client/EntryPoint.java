package com.yurykorzun.demo.microservices.springboot.service.customer.client;

public class EntryPoint {

    public static void main(String[] args) {
        System.out.printf(
                "%s.%s entrypoint. Should`ve never been entered but here we are",
                EntryPoint.class.getPackage().getName(),
                EntryPoint.class
        );
    }

}
