package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum RecomendacaoStatusEnum  implements Serializable, ICodigoDescricao{
	
	ACEITO("A"),	
	ENVIADO("E"); 

	private String descricao;
	
	private RecomendacaoStatusEnum( final String descricao) {    	
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
    
        return RecomendacaoStatusEnum.valueOf(key).getRotulo();
    }

}
