package com.securiry.study.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@MapperScan("com.securiry.study.dao")
@PropertySource(value = {
	    "classpath:application.properties"
	})
@ComponentScan(basePackages = "com.securiry.study*")
public class MybatisConfig {
	
	@Autowired
	private Environment environment;
	
	@Bean
	@Primary
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driver"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}
	

	 @Bean
	 public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
	  return new SqlSessionTemplate(sqlSessionFactory);
	 }

	@Bean
	@Primary
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setTypeAliasesPackage("com.securiry.study.vo");
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources(environment.getRequiredProperty("mapper.location")));
		sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		
		return sessionFactory.getObject();
	}

}
