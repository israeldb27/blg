package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum TipoImovelCompartilhadoEnum implements Serializable, ICodigoDescricao{
	
	INTERMEDIACAO("I"),
	PARCERIA("P"); 

	private String descricao;
	
	private TipoImovelCompartilhadoEnum( final String descricao) {    	
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
    
        return TipoImovelCompartilhadoEnum.valueOf(key).getRotulo();
    }

}
