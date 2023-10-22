package com.yurykorzun.demo.microservices.springboot.commons.web.exceptions;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

import static com.yurykorzun.demo.microservices.springboot.commons.web.exceptions.WebExceptionsConstants.CUSTOM_FIELD;

@Configuration
public class ReactiveWebExceptionsConfig {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
                Map<String, Object> defaultAttributes = super.getErrorAttributes(request, options);
                defaultAttributes.put(CUSTOM_FIELD, "custom error field");
                return defaultAttributes;
            }
        };
    }
}
