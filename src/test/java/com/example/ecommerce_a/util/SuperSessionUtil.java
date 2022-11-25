package com.example.ecommerce_a.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.mock.web.MockHttpSession;

public class SuperSessionUtil {
	
	public static MockHttpSession createEmailSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("email", "curryshop.test@gmail.com");
		return createMockHttpSession(sessionMap);
	}
	
	public static MockHttpSession nullEmailSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("email", null);
		return createMockHttpSession(sessionMap);
	}
	
	
	
	private static MockHttpSession createMockHttpSession(Map<String, Object> sessions) {
		MockHttpSession mockHttpSession = new MockHttpSession();
		for (Map.Entry<String, Object> session : sessions.entrySet()) {
			mockHttpSession.setAttribute(session.getKey(), session.getValue());
		}
		return mockHttpSession;
	}
}
