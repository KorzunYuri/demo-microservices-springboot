package com.yurykorzun.demo.microservices.springboot.commons.config;

import com.yurykorzun.demo.microservices.springboot.commons.exceptions.MessageExposedException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static com.yurykorzun.demo.microservices.springboot.commons.config.WebExceptionsConstants.CUSTOM_FIELD;

@Configuration
public class SynchronousWebExceptionsConfig {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                //  preserve error message
                ErrorAttributeOptions actualOptions = options;
                if (this.getError(webRequest) instanceof MessageExposedException me) {
                    actualOptions = ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE);
                }
                Map<String, Object> attributes = super.getErrorAttributes(webRequest, actualOptions);
                //  add custom info
                attributes.put(CUSTOM_FIELD, "custom error field");
                return attributes;
            }
        };
    }

}
