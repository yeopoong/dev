package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.domain.Customer;

@EnableAutoConfiguration
public class CustomerJdbcApp implements CommandLineRunner {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate; // (1)

    @Override
    public void run(String... strings) throws Exception {
        String sql = "SELECT 1"; // (2)
        SqlParameterSource param = new MapSqlParameterSource(); // (3)
        Integer result = jdbcTemplate.queryForObject(sql, param, Integer.class); // (4)

        System.out.println("result = " + result);
        
        sql = "SELECT :a + :b"; // (1)
        param = new MapSqlParameterSource()
                .addValue("a", 100)
                .addValue("b", 200); // (2)
        result = jdbcTemplate.queryForObject(sql, param, Integer.class);

        System.out.println("result = " + result);
        
        customerExam();
    }
    
    public void customerExam() {
    	String sql = "SELECT id, first_name, last_name FROM customers WHERE id = :id"; // (1)
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", 1); // (2)
        Customer result = jdbcTemplate.queryForObject(sql, param, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException { // (3)
                return new Customer(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
            }
        });

        System.out.println("result = " + result);

    }

    public static void main(String[] args) {
        SpringApplication.run(CustomerJdbcApp.class, args);
    }
}
