package com.securiry.study.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// AbstractSecurityWebApplicationInitializer 를 적용하여, 모든 요청을 먼저 인터셉트하도록 springSecurityFilterChain을 구현 한 것. (지금은 구현된그대로{default상태}로 사용)
@Order(1)
public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer{

	public SecurityWebAppInitializer() {
		super();
	}
}
