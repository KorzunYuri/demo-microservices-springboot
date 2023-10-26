package com.yurykorzun.demo.microservices.springboot.commons.config;

import com.yurykorzun.demo.microservices.springboot.commons.exceptions.MessageExposedException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

import static com.yurykorzun.demo.microservices.springboot.commons.config.WebExceptionsConstants.CUSTOM_FIELD;

@Configuration
public class ReactiveWebExceptionsConfig {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
                //  preserve error message
                ErrorAttributeOptions actualOptions = options;
                if (this.getError(request) instanceof MessageExposedException me) {
                    actualOptions = ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE);
                }
                Map<String, Object> attributes = super.getErrorAttributes(request, actualOptions);
                //  add custom info
                attributes.put(CUSTOM_FIELD, "custom error field");
                return attributes;
            }
        };
    }
}
