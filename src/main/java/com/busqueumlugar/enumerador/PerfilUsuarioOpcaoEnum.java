package com.busqueumlugar.enumerador;

import java.io.Serializable;



public enum PerfilUsuarioOpcaoEnum implements Serializable, ICodigoDescricao {
	
	PADRAO("P"),
	CORRETOR("C"),	
	IMOBILIARIA("I"),
	ADMIN("A");
    
    private String descricao;
    
    private PerfilUsuarioOpcaoEnum( final String descricao) {
    
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
    
        return PerfilUsuarioOpcaoEnum.valueOf(key).getRotulo();
    }

}
