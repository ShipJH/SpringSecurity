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
		auth.inMemoryAuthentication().withUser("bae").password("{noop}1234").roles("USER")
			 					.and().withUser("kim").password("{noop}1234").roles("ADMIN")
			 					.and().withUser("hwang").password("{noop}1234").roles("NOAUTH");
		
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
		    .antMatchers("/loginForm").permitAll() /* #2 이렇게 설정하지 않으면 권한에 엑세스를 설정하려고한다. 
		    									          사실상 로그인 페이지나, 로그아웃, 회원가입 등 로그인이전에 처리되어야 할 것 (권한이 필요없는 페이지)
		    									         에서는 권한을 넣을 수 없는것이 당연하다.
		    									   */
		    .antMatchers("/loginGo").permitAll()   
			.antMatchers("/**").access("hasAnyRole('USER' , 'ADMIN')") //모든 유형의 url맵핑은  USER라는 권한을 부여받은 다음에 접근이 가능하도록 설정
			/* #2 antMatchers에 있어서 순서도 중요하다. 적은 단위로 먼저 위에 쪼개야한다. 예를들어. 
			 * antMatchers("/**").access("hasRole('USER')")
			 * antMatchers("/board").access("hasRole('ADMIN')")
			 * 이런 식으로 배치할 경우, /** 이기 때문에 /board의 ADMIN 규칙을 체크하지 않고 모두 통과시키게 된다.
			 * 
			 * 그 뒤에 hasAnyRole 이나 hasRole 의 차이등 표현식의 차이는 각자 공부해서 발표해보도록 하자.
			 */
			
			.and()
			.formLogin()
			.loginPage("/loginForm") /* #2 인증되지 않은 사용자가 접근하는경우 스프링시큐리티가 리다이렉션할 위치를 지정.
										    미지정시, DefaultLoginPageGeneratingFilter가 /spring_security_login으로 리다이렉션을 한다.
										  FilterChainProxy가 DefaultLoginPageGeneratingFilte를 선택해 기본 로그인 페이지를 보여준다.  
									  */
			.loginProcessingUrl("/loginGo") /* #2 /login/form 에서 로그인한 사용자가 입력한 ID와 PASSWORD를 POST방식으로 전달받아 
											       사용자 인증을 하는 부분.			
											*/
			.failureUrl("/loginForm?error") /* #2 로그인 시도(loginProcessingUrl) 에서 실패하거나 에러가 난 경우 이동할 페이지
											    여기서는 다시 로그인 페이지로 이동하며 메세지를 띄어줄 생각이므로, 다시 /login/form로 error를 들고 이동. */
			.usernameParameter("username") // #2 유저가 /login/form 에서 입력한 파라미터
			.passwordParameter("password") // #2 유저가 /login/form 에서 입력한 파라미터
			
			.defaultSuccessUrl("/defaultPage") // #2 로그인이 성공했을 시, 이동되는 페이지 ( 기본적인 설정은 welcome page가 됨. )
			
			
//			.and().httpBasic()
			.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/loginForm?logout=OK")
			
			
			
			.and().csrf().disable(); //책에서 추후에 다룬다고함
	}
	
	
	
}
