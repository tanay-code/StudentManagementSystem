package com.cts.sms.filter;


import java.util.function.Predicate;
 
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
 
@Component
public class RouteValidator {
 
 
	public static final String[] OPEN_API_ENDPOINTS = { "/auth/welcome","/auth/register", "/auth/new","/auth/authenticate"};
 
	public Predicate<ServerHttpRequest> isSecured = request -> {
		String path = request.getPath().toString();
		System.out.println("required :-"+ path);
		
		
		for (String endpoint : OPEN_API_ENDPOINTS) {
			
			System.out.println(endpoint);
			if (path.contains(endpoint)) {
				System.out.println("Not required :-"+ path);
				return false; // Endpoint does not require authorization
			}
		}
		return true; // Endpoint requires authorization
	};
 
	
	
 
}
 
 
