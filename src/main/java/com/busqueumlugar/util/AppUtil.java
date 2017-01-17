package com.busqueumlugar.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.model.Cidades;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Usuario;

public class AppUtil {
	
	@Autowired
	private static ServletContext context;	
	
	
	public static BigDecimal formatarMoeda(String valor){
		 String vl = valor.replace(".", "");		 
		 vl = vl.replace(",", ".");
		return  BigDecimal.valueOf(Double.parseDouble(vl));

	}
	
	public static boolean formatarMoedaValido(String valor){
		
		try {
			String vl = valor.replace(".", "");		 
			 vl = vl.replace(",", ".");
			BigDecimal.valueOf(Double.parseDouble(vl));
			return true;
		} catch (Exception e) {
			return false;
		}
		 

	}
	
	public static String formataMoedaString(BigDecimal vl){
		
		if ( vl != null && vl.intValue() > 0 ){
			DecimalFormat decFormat = new DecimalFormat("#,###,##0.00");
			return decFormat.format(vl);
		}
		else
			return "";
		
	}
	
	public static String md5(String senha){  
        String sen = "";  
        MessageDigest md = null;  
        try {  
            md = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
        sen = hash.toString(16);      
        return sen;  
    }
	
	public static int recuperarQuantidadeLista(List lista){
		if ( ! CollectionUtils.isEmpty(lista))
			return lista.size();
		else
			return 0;
	}
	
	public static boolean getRandomBoolean() {
	    return Math.random() < 0.5;
	    //I tried another approaches here, still the same result

	}
	
	public static String carregaFotoPrincipalImovel(Imovel imovel)  {	
		
		if (imovel != null && imovel.getFotoPrincipal() != null && imovel.getFotoPrincipal().length > 0 ){
        String idImovel =  imovel.getId().toString();
        String titulo = "imovel";
        String nomeArquivo = "/img/" + titulo + idImovel + ".jpg";  
		try {
			//
			InputStream in = AppUtil.class.getResourceAsStream(nomeArquivo);
			File f = new File(nomeArquivo);
			FileUtils.writeByteArrayToFile(f, imovel.getFotoPrincipal());			
            FileOutputStream out;
			out = new FileOutputStream(nomeArquivo);
			out.write(imovel.getFotoPrincipal());         
            return nomeArquivo;
		} catch (Exception e){
			e.printStackTrace();
		}
         
		return null;	
	}
	else
		return null;
	}
	
	public static String carregaFotoPrincipalImovel(ImovelForm imovel)  {	
		
		if (imovel != null && imovel.getFotoPrincipal() != null && imovel.getFotoPrincipal().length > 0 ){
        String idImovel =  imovel.getId().toString();
        String titulo = "imovel";
        String nomeArquivo = "/img/" + titulo + idImovel + ".jpg";  
		try {
			//
			InputStream in = AppUtil.class.getResourceAsStream(nomeArquivo);
			File f = new File(nomeArquivo);
			FileUtils.writeByteArrayToFile(f, imovel.getFotoPrincipal());			
            FileOutputStream out;
			out = new FileOutputStream(nomeArquivo);
			out.write(imovel.getFotoPrincipal());         
            return nomeArquivo;
		} catch (Exception e){
			e.printStackTrace();
		}
         
		return null;	
	}
	else
		return null;
	}
	
	public static String carregaFotoPrincipalUsuario(Usuario usuario) {	
		
		if  ( usuario != null && usuario.getFotoPrincipal() != null ){
			
			String login = usuario.getLogin();
	        String idUsuario = usuario.getId().toString();            
	        String nomeArquivo = "/img/" + login + idUsuario + ".jpg";
	        FileOutputStream out;
			try {
				
				InputStream in = AppUtil.class.getResourceAsStream(nomeArquivo);
				File f = new File(nomeArquivo);
				FileUtils.writeByteArrayToFile(f, usuario.getFotoPrincipal());
				out = new FileOutputStream(nomeArquivo);
				out.write(usuario.getFotoPrincipal());         
	            return nomeArquivo;	            
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
			return null;
		}
		else
			return null;
	}

	public static List<Select> carregarQuantidadePaginas(int quantRegistros, int quantMaxRegistrosPerPage) {
		
		List<Select> listaFinal = new ArrayList<Select>();
		if (quantMaxRegistrosPerPage > 0 ) {
			int quantPages = (quantRegistros / quantMaxRegistrosPerPage);
			if (( quantRegistros % quantMaxRegistrosPerPage) != 0 )
				quantPages++; 
			for (int i = 1; i <= quantPages;i++){
				listaFinal.add(new Select(String.valueOf(i), String.valueOf(i)));
			}
		}
		return listaFinal;
	}
	
}
