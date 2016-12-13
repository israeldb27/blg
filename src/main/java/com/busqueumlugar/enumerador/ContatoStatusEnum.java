package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum ContatoStatusEnum implements Serializable, ICodigoDescricao {
	
	OK("O"),
	RECUSADO("R"),
	CANCELADO("N"),
	CONVIDADO("C");	

	private String descricao;
	
	private ContatoStatusEnum( final String descricao) {    	
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
    
        return ContatoStatusEnum.valueOf(key).getRotulo();
    }

}
