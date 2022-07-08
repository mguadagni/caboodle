package com.capstone.caboodle;

import CSVreader.CSVReader1;
import CSVreader.CSVReader2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaboodleApplication {

	public static void main(String[] args) {

		SpringApplication.run(CaboodleApplication.class, args);

		System.out.println("  ");

		try {
//			CSVReader1.dataReader();
			CSVReader2.CSVToJson();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
