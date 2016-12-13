package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum StatusImovelCompartilhadoEnum implements Serializable, ICodigoDescricao {

	ACEITA("A"),
	SOLICITADO("S");
	
    
    private String descricao;
    
    private StatusImovelCompartilhadoEnum( final String descricao) {
    
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
    
        return StatusImovelEnum.valueOf(key).getRotulo();
    }
}
