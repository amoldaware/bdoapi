package com.cgtmse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class SpringBootRestExampleApplication 
{
public static void main(String[] args) 
{
SpringApplication.run(SpringBootRestExampleApplication.class, args);
}
}
