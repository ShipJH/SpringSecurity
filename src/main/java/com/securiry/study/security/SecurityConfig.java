package com.securiry.study.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration //�� ������̼��� �پ������� ��Ĺ�� ����Ǹ鼭 �������ϵ��� bean�� �÷��ش�.
@EnableWebSecurity //����ť��Ƽ�� ����ϰ���.
public class SecurityConfig extends WebSecurityConfigurerAdapter { //�����������ӿ�ũ�� ��ť��Ƽ�� �����ϴ� �߻�Ŭ������ ��� �޾� �������̵��Ͽ� ����ü�� ���� �� ��.
   
   @Override
   public void configure(final AuthenticationManagerBuilder auth) throws Exception{
      // �θ޸� ������� ������ �����Ѵ�.
      //TODO: ���Ŀ��� DB�� ����� ������ ������ ���ǿ� �ø� �� ��.
      auth.inMemoryAuthentication().withUser("bae").password("{noop}1234").roles("USER")
                         .and().withUser("kim").password("{noop}1234").roles("ADMIN")
                         .and().withUser("hwang").password("{noop}1234").roles("NOAUTH");
      
      /*
       * spring-security-core : 5.0.0.RC1���� �⺻ PasswordEncoder�� DelegatingPasswordEncoder�� ����˴ϴ�. 
       * ����ڸ� �޸𸮿� �����ϸ� �Ϲ� �ؽ�Ʈ�� ��ȣ�� �����ϰ� DelegatingPasswordEncoder���� 
       * ��ȣ�� Ȯ���Ϸ��� ���ڴ��� �˻� �� �� ��ȣ�� �����ϴ� ����� ��ġ�ϴ� ��ȣ�� ã�� �� �����ϴ�.
       * ����URL : https://cnpnote.tistory.com/entry/SPRING-%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-PasswordEncoder-%EC%98%A4%EB%A5%98
       * TODO : ���Ŀ� ��ȣȭ �����Ѱ��� DB���� ��������� �ڿ������� �ذ�� �� ����. (������ �θ޸𸮹���̶� �ϴ��� �ǳʶڴ�.)
       */
   }
   
   
   @Override
   protected void configure(final HttpSecurity http) throws Exception {
      http.authorizeRequests()
          .antMatchers("/loginForm").permitAll() /* #2 �̷��� �������� ������ ���ѿ� �������� �����Ϸ����Ѵ�. 
                                               ��ǻ� �α��� ��������, �α׾ƿ�, ȸ������ �� �α��������� ó���Ǿ�� �� �� (������ �ʿ���� ������)
                                              ������ ������ ���� �� ���°��� �翬�ϴ�.
                                        */
          .antMatchers("/loginGo").permitAll()   
         .antMatchers("/**").access("hasAnyRole('USER' , 'ADMIN')") //��� ������ url������  USER��� ������ �ο����� ������ ������ �����ϵ��� ����
         /* #2 antMatchers�� �־ ������ �߿��ϴ�. ���� ������ ���� ���� �ɰ����Ѵ�. �������. 
          * antMatchers("/**").access("hasRole('USER')")
          * antMatchers("/board").access("hasRole('ADMIN')")
          * �̷� ������ ��ġ�� ���, /** �̱� ������ /board�� ADMIN ��Ģ�� üũ���� �ʰ� ��� �����Ű�� �ȴ�.
          * 
          * �� �ڿ� hasAnyRole �̳� hasRole �� ���̵� ǥ������ ���̴� ���� �����ؼ� ��ǥ�غ����� ����.
          */
         
         .and()
         .formLogin()
         .loginPage("/loginForm") /* #2 �������� ���� ����ڰ� �����ϴ°�� ��������ť��Ƽ�� �����̷����� ��ġ�� ����.
                                  ��������, DefaultLoginPageGeneratingFilter�� /spring_security_login���� �����̷����� �Ѵ�.
                                FilterChainProxy�� DefaultLoginPageGeneratingFilte�� ������ �⺻ �α��� �������� �����ش�.  
                             */
         .loginProcessingUrl("/loginGo") /* #2 /login/form ���� �α����� ����ڰ� �Է��� ID�� PASSWORD�� POST������� ���޹޾� 
                                        ����� ������ �ϴ� �κ�.         
                                 */
         .failureUrl("/loginForm?error") /* #2 �α��� �õ�(loginProcessingUrl) ���� �����ϰų� ������ �� ��� �̵��� ������
                                     ���⼭�� �ٽ� �α��� �������� �̵��ϸ� �޼����� ����� �����̹Ƿ�, �ٽ� /login/form�� error�� ��� �̵�. */
         .usernameParameter("username") // #2 ������ /login/form ���� �Է��� �Ķ����
         .passwordParameter("password") // #2 ������ /login/form ���� �Է��� �Ķ����
         
         .defaultSuccessUrl("/defaultPage") // #2 �α����� �������� ��, �̵��Ǵ� ������ ( �⺻���� ������ welcome page�� ��. )
         
         
//         .and().httpBasic()
         .and()
         .logout()
         .logoutUrl("/logout")
         .logoutSuccessUrl("/loginForm?logout=OK")
         
         
         
         .and().csrf().disable(); //å���� ���Ŀ� �ٷ�ٰ���
   }
   
   
   
}