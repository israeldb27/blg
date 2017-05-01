package com.busqueumlugar.config;

import org.apache.commons.dbcp.BasicDataSource;

public class Teste {

	public static void main(String[] args) {
		try {			 			
 			System.out.println("Inicio");
 			int valor = 1 + (int) (Math.random() * 4);
 			System.out.println("Resultado" + valor);
		} catch (Exception e) {
			System.out.println("Message error: " + e.getMessage());
			e.printStackTrace();
		}

	}
	
	public static void m1(){
		try {
			BasicDataSource dataSource = new BasicDataSource();
 			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
 			dataSource.setUrl("jdbc:mysql://138.197.3.199:3306/home"); 		
 			dataSource.setUsername("root"); 		
 			dataSource.setPassword("Israel814245!"); 
 			System.out.println("Teste 1: " + dataSource.getTestOnReturn()); 
 			System.out.println("Teste 2: " + dataSource.isClosed());  			
 			System.out.println("Fim");
		} catch (Exception e) {
			System.out.println("Message error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
