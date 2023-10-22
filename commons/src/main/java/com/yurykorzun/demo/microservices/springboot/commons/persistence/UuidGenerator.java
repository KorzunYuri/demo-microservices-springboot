package com.yurykorzun.demo.microservices.springboot.commons.persistence;

import java.util.Random;
import java.util.UUID;

public class UuidGenerator {

    static String randomUuid() {
        Random random = new Random();
        return new UUID(random.nextLong(), random.nextLong()).toString();
    }

}
