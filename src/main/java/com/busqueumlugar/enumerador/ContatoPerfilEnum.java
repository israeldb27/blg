package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum ContatoPerfilEnum implements Serializable, ICodigoDescricao {
	
	IMOBILIARIA("I"), 
	CORRETOR("C"),	
	PADRAO("P"); // usuario padrao == usuario comum	

	private String descricao;
	
	private ContatoPerfilEnum( final String descricao) {    	
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
    
        return ContatoPerfilEnum.valueOf(key).getRotulo();
    }

}
