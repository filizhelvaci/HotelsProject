package com.flz;

import com.flz.model.Employees;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkerApp {

	public static void main(String[] args) {

		SpringApplication.run(WorkerApp.class, args);

		Employees emp1=new Employees();


	}

}
