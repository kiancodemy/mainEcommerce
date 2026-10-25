package com.ecommerce.main.common.components;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
// loading the html templates
@Component
public class EmailTemplateLoader {

    public String load(String templateName) {
        try {
            ClassPathResource resource =
                    new ClassPathResource("templates/emails/" + templateName);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Could not load email template: " + templateName, e);
        }
    }
}