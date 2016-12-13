package com.busqueumlugar.config;

import java.sql.DriverManager;
import java.sql.Connection;
import java.util.Random;

import com.mysql.jdbc.StringUtils;

public class TesteConexao {

	public static void main(String[] args) {
		try {
			Random r = new Random();
			int randomInt = r.nextInt(3);	
			System.out.println("resultado: " + randomInt );
		} catch (Exception e) {
			System.out.println("Mensagem erro: " + e.getMessage());
		}

	}

}
