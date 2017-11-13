package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum StatusUsuarioEnum implements Serializable, ICodigoDescricao {

	LIBERADO("L"), 
	CRIADO("C"),	
	PRE_ATIVADO("P"),
	SUSPENSO("S"); 	

	private String descricao;
	
	private StatusUsuarioEnum( final String descricao) {    	
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
    
        return StatusUsuarioEnum.valueOf(key).getRotulo();
    }
}
