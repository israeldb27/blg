/*
 * Copyright (c) 2010, Oracle. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Oracle nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.busqueumlugar.util;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;




/**
 *
 * @author mbohm
 */
public class JsfUtil {
    
     public static String emailPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
     
      /* 
 * as contas de CPF e CNPJ são baseadas no número de ordem do dígito na string 
 * os arrays baixo armazenam a ordem para cada dígito 
 */ 
     private static final int[] pesosCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2}; 
     private static final int[] pesosCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}; 
     
     
 /* 
  * rotina genérica para calcular qual o dígito verificador 
  * @PARAM str   string que contem o CPF ou CNPJ 
  * @PARAM peso  array dos pesos, podem ser pesoCPF ou pesoCNPJ 
  */ 
     
     
     
  private static int calcularDigitoVerificador(String str, int[] peso) { 
     int soma = 0; 
     for (int indice=str.length()-1, digito; indice >= 0; indice-- ) { 
       digito = Integer.parseInt(str.substring(indice,indice+1)); 
       soma += digito*peso[peso.length-str.length()+indice]; 
     } 
     soma = 11 - soma % 11; 
     return soma > 9 ? 0 : soma; 
  } 
  
/* 
 * @PARAM cpf no formato 99999999999 ou 999.999.999-99 
 * @RETURN volta true se o CPF é válido 
 */ 
 public static boolean isValidoCPF(String cpf) { 
   if (cpf==null) {  
       return false;  
   } 
   // remove "." e "-" 
   cpf.replace(".",""); 
   cpf.replace("-",""); 
   if (cpf.length()!=11) { 
       return false; // sai se não tem o tamanho esperado 
   } 

   // passo 1 - calcula somente para a string sem o digito verificador 
   Integer digito1 = calcularDigitoVerificador(cpf.substring(0,9), pesosCPF);  
   // passo 2 - calculo novamente com o dígito obtido no passo 1 
   Integer digito2 = calcularDigitoVerificador(cpf.substring(0,9) + digito1,
                                               pesosCPF); 
   // retorna indicando se o CPF fornecido é igual o CPF com os 
   // digitos verificadores calculados  
   return cpf.equals(cpf.substring(0,9) + digito1.toString() +
                     digito2.toString()); 
 } 
 
 
  /* 
 * @PARAM cnpj no formato 99999999999999 ou 99.999.999/9999-99 
 * @RETURN volta true se o CNPJ é válido 
 */ 
 public static boolean isValidoCNPJ(String cnpj) { 
   if (cnpj==null) { 
       return false; 
   } 
   // remove "." e "-" 
   cnpj.replace(".",""); 
   cnpj.replace("-","");  
   cnpj.replace("/","");    
   
   if (cnpj.length() !=14) { 
       return false; 
   } 
   // passo 1 - calcula somente para a string sem o digito verificador 
   Integer digito1 = calcularDigitoVerificador(cnpj.substring(0,12), pesosCNPJ); 
   // passo 2 - calculo novamente com o dígito obtido no passo 1 
   Integer digito2 = calcularDigitoVerificador(cnpj.substring(0,12) + digito1,
                                               pesosCNPJ); 
   return cnpj.equals(cnpj.substring(0,12) + digito1.toString() +
                      digito2.toString()); 
 } 
 
    
    public static <T> Collection<T> arrayToCollection(T[] arr) {
        if (arr == null) {
            return new ArrayList<T>();
        }
        return Arrays.asList(arr);
    }
    
    public static Object[] collectionToArray(Collection<?> c) {
        if (c == null) {
            return new Object[0];
        }
        return c.toArray();
    }
    
    public static String getAsString(Object object) {
        if (object instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>)object;
            if (collection.size() == 0) {
                return "(No Items)";
            }
            StringBuffer sb = new StringBuffer();
            int i = 0;
            for (Object item : collection) {
                if (i > 0) {
                    sb.append("<br />");
                }
                sb.append(item);
                i++;
            }
            return sb.toString();
        }
        return String.valueOf(object);
    }
    
    
 /*    public  UsuarioForm getUserSession(){        
       /* FacesContext facesContext = FacesContext.getCurrentInstance();
        UsuarioBean usuarioBean = (UsuarioBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioBean");
        if ( usuarioBean != null )
            return usuarioBean.getFrm();
        else*/
    	  
   /*      return new com.busqueumlugar.form.UsuarioForm();
    }*/
     
  //   public static void setUserSession(Usuario usuario){        
        /*FacesContext facesContext = FacesContext.getCurrentInstance();
        UsuarioBean usuarioBean = (UsuarioBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioBean");
        UsuarioForm frm = new UsuarioForm();
        try {
            BeanUtils.copyProperties(frm, usuario);
            usuarioBean.setFrm(frm);
        } catch (Exception ex) {
            
        }*/
   // } */
     
  
     
    public static byte[] getBytesFromFile(InputStream file) {
		
		InputStream inputStream = null; 
		FileOutputStream out = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream(); 
			 
		byte[] tbuff = null;   
		try {   
		
		    long length = file.available();   
		    inputStream = file;
		    if (length > Integer.MAX_VALUE) {   
		    // File is too large   
		    }  
		       		  
		    // Read in the bytes   
		    int offset = 0;   
		    int numRead = 0;
		    
		    int bytesread = 0;
		    tbuff = new byte[(int)length];
		    while (true){
		    	bytesread = inputStream.read(tbuff);   
                if (bytesread == -1) // if EOF   
                    break;   
                buffer.write(tbuff, 0, bytesread);   
		    }
		    
		    return buffer.toByteArray();

		}catch (IOException e) {   
		    e.printStackTrace();
		    return null;
		}
	}    
 
public static boolean isEmail(String email) {
  Pattern p = Pattern.compile(emailPattern); // Set the email pattern string
  Matcher m = p.matcher(email); // Match the given string with the pattern
  return m.matches();
}
}
