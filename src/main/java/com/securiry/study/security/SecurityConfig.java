package com.securiry.study.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration //이 어노테이션이 붙어있으면 톰캣이 실행되면서 설정파일들을 bean에 올려준다.
@EnableWebSecurity //웹시큐리티를 사용하겠음.
public class SecurityConfig extends WebSecurityConfigurerAdapter { //스프링프레임워크의 시큐리티가 제공하는 추상클래스를 상속 받아 오버라이드하여 구현체를 만들 것 임.
	
	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception{
		// 인메모리 방식으로 유저를 설정한다.
		//TODO: 추후에는 DB에 저장된 유저를 가져와 세션에 올릴 것 임.
		auth.inMemoryAuthentication()
			.withUser("bae")
			.password("{noop}1234") // 시큐리티 5버전 이상부터는 
			.roles("USER");
		
		/*
		 * spring-security-core : 5.0.0.RC1에서 기본 PasswordEncoder는 DelegatingPasswordEncoder로 빌드됩니다. 
		 * 사용자를 메모리에 저장하면 일반 텍스트로 암호를 제공하고 DelegatingPasswordEncoder에서 
		 * 암호를 확인하려고 엔코더를 검색 할 때 암호를 저장하는 방법과 일치하는 암호를 찾을 수 없습니다.
		 * 참고URL : https://cnpnote.tistory.com/entry/SPRING-%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-PasswordEncoder-%EC%98%A4%EB%A5%98
		 * TODO : 추후에 암호화 적용한것을 DB에서 가지고오면 자연스럽게 해결될 것 같다. (지금은 인메모리방식이라 일단은 건너뛴다.)
		 */
	}
	
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/**").access("hasRole('USER')") //모든 유형의 url맵핑은  USER라는 권한을 부여받은 다음에 접근이 가능하도록 설정
			.and().formLogin() 
			.and().httpBasic()//시큐리티에서 기본적으로 제공하는 로그인폼 ( TODO: 추후에는 로그인페이지를 직접 지정할 것 . )
			.and().logout()
			.and().csrf().disable(); //책에서 추후에 다룬다고함
	}
	
	
	
}
