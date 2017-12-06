package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum StatusAtividadeOpcaoEnum implements Serializable, ICodigoDescricao{
	
	CRIADO("C"),
	INCOMPLETO("I"),
	CANCELADO("N"),
	COMPLETO("O");
	
	
    private String descricao;
    
    private StatusAtividadeOpcaoEnum( final String descricao) {    
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
        return StatusAtividadeOpcaoEnum.valueOf(key).getRotulo();
    }

}
