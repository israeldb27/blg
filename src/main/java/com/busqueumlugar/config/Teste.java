package com.busqueumlugar.config;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.StringUtils;

import com.busqueumlugar.util.AppUtil;

public class Teste {

	public static void main(String[] args) {
		try {			 			
 			System.out.println("Inicio");
 			
 			String valor = "120,00"; 		
 			System.out.println("subst:" + AppUtil.isValidoFormatoMoeda(valor));
 			
 			System.out.println("Resultado" + valor);
 			System.out.println("Resultado double" + Double.parseDouble(valor));
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
