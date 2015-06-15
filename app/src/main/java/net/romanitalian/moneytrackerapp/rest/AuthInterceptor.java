package net.romanitalian.moneytrackerapp.rest;

import net.romanitalian.moneytrackerapp.auth.SessionManager;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@EBean
public class AuthInterceptor implements ClientHttpRequestInterceptor {
    public static String authToken;

    @Bean
    SessionManager sessionManager;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add("authToken", authToken);
        return execution.execute(request, body);
    }
}
