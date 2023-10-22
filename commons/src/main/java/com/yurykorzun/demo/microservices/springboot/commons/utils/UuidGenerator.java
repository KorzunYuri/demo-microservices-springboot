package com.yurykorzun.demo.microservices.springboot.commons.utils;

import java.util.Random;
import java.util.UUID;

public class UuidGenerator {

    public static String randomUuid() {
        Random random = new Random();
        return new UUID(random.nextLong(), random.nextLong()).toString();
    }

}
