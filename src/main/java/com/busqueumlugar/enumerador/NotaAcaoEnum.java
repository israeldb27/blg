package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum NotaAcaoEnum implements Serializable, ICodigoDescricao {

	IMOVEL("I"), 
	USUARIO("U"),
	PREFERENCIA("R"),
	PARCERIA("P");	

	private String descricao;
	
	private NotaAcaoEnum( final String descricao) {    	
        this.descricao = descricao;
    }
	
    @Override
    public String getRotulo() {
    
        return this.descricao;
    }  
    
    @Override
    public String getIdentificador() {
    
        return this.name();
    }
    

    public static String getLabel(String key) {
    
        return NotaAcaoEnum.valueOf(key).getRotulo();
    }
}
