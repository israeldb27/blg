package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum TipoNotificacaoEnum implements Serializable, ICodigoDescricao{
	
	IMOVEL("I"), 	
	CONVITE("C"),
	PLANO("P"),
	SERVICO("S"),	
	USUARIO("U"); 

	private String descricao;
	
	private TipoNotificacaoEnum( final String descricao) {    	
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
    
        return TipoNotificacaoEnum.valueOf(key).getRotulo();
    }

}
