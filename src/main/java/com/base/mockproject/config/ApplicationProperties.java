package com.base.mockproject.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private CorsConfiguration cors = new CorsConfiguration();

}
