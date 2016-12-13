package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum AcaoNotificacaoEnum  implements Serializable, ICodigoDescricao{

	USUARIO("U"),
	CONVITE("C"),
	PLANO("P"),
	SERVICO("S"),	
	INTERMEDIACAO("I"), 	
	PARCERIA("P"); 

	private String descricao;
	
	private AcaoNotificacaoEnum( final String descricao) {    	
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
    
        return AcaoNotificacaoEnum.valueOf(key).getRotulo();
    }

}
