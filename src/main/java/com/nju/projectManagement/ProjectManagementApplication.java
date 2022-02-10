package com.nju.projectManagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.nju.projectManagement.Mapper")
@SpringBootApplication
@MapperScan(basePackages = {"com.nju.projectManagement.Mapper"})
public class ProjectManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementApplication.class, args);
	}

}
