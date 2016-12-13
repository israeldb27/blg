package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum StatusLeituraEnum implements Serializable, ICodigoDescricao {
	
	NOVO("N"), //novo	
	LIDO("L"); // lido

	private String descricao;
	
	private StatusLeituraEnum( final String descricao) {    	
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
    
        return StatusLeituraEnum.valueOf(key).getRotulo();
    }

}
