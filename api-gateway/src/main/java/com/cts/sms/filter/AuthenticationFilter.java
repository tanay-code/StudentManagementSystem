package com.cts.sms.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
 
import com.cts.sms.util.JwtUtil;
import com.google.common.net.HttpHeaders;
 
import reactor.core.publisher.Mono;
 
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
 
    @Autowired
    private RouteValidator validator;
 
    @Autowired
    private JwtUtil util;
 
    public static class Config {
    }
 
    public AuthenticationFilter() {
        super(Config.class);
    }
 
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
			System.out.println("test");
 
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return handleUnauthorized(exchange.getResponse(), "Missing authorization header");
                }
 
                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
 
                try {
                    String role = util.extractRolesFromToken(authHeader);
                    String requestedPath = exchange.getRequest().getPath().toString();
                    String method = exchange.getRequest().getMethod().name();
 
                    if (!isAuthorized(role, requestedPath, method)) {
                        return handleUnauthorized(exchange.getResponse(), "Unauthorized access");
                    }
 
                } catch (Exception e) {
                    return handleUnauthorized(exchange.getResponse(), "Invalid token");
                }
            }
            return chain.filter(exchange);
        };
    }
 
    private boolean isAuthorized(String role, String path, String method) {
//        if ("USER".equalsIgnoreCase(role)) {
//            return path.startsWith("/stocks") || path.startsWith("/inventory") || path.startsWith("/order");
//        }
        return true;
    }
 
    private Mono<Void> handleUnauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}