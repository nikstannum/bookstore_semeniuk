package com.belhard;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.belhard.dao.connection.ConfigurationManager;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan
public class ContextConfiguration {

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		ConfigurationManager configurationManager = new ConfigurationManager();
		dataSource.setJdbcUrl(configurationManager.getProperty("db.url"));
		dataSource.setPassword(configurationManager.getProperty("db.password"));
		dataSource.setUsername(configurationManager.getProperty("db.user"));
		return dataSource;
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}

}
