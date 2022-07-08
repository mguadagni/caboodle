package com.capstone.caboodle;

import CSVreader.CSVReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaboodleApplication {

	public static void main(String[] args) {

		SpringApplication.run(CaboodleApplication.class, args);

		try {
			CSVReader.dataReader();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
