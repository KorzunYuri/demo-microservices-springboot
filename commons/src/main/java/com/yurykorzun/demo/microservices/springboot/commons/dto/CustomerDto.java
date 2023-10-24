package com.yurykorzun.demo.microservices.springboot.commons.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerDto {

    private String id;

    private String firstName;

    private String lastName;

    @ToString.Exclude
    private String passportId;
}
