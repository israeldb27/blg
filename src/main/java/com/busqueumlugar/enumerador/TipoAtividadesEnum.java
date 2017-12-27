package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum TipoAtividadesEnum implements Serializable, ICodigoDescricao{
	
	MARCAR_VISITA("MA"),
	FECHAR_NEGOCIO("FE"),
	OUTROS("OU");
	
	
    private String descricao;
    
    private TipoAtividadesEnum( final String descricao) {    
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
        return TipoAtividadesEnum.valueOf(key).getRotulo();
    }
}
