package com.tarasantoniuk.random_user_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient randomUserRestClient(
            RestClient.Builder builder,
            @Value("${randomuser.base-url}") String baseUrl,
            @Value("${randomuser.connect-timeout}") int connectTimeout,
            @Value("${randomuser.read-timeout}") int readTimeout) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);

        return builder
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }
}