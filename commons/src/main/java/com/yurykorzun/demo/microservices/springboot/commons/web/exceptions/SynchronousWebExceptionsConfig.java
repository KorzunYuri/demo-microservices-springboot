package com.yurykorzun.demo.microservices.springboot.commons.web.exceptions;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static com.yurykorzun.demo.microservices.springboot.commons.web.exceptions.WebExceptionsConstants.CUSTOM_FIELD;

@Configuration
public class SynchronousWebExceptionsConfig {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Map<String, Object> defaultAttributes = super.getErrorAttributes(webRequest, options);
                defaultAttributes.put(CUSTOM_FIELD, "custom error field");
                return defaultAttributes;
            }
        };
    }

}
