package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum StatusAtividadesEnum implements Serializable, ICodigoDescricao{
	
	C(MessageUtils.getMessage("lbl.status.atividade.criado")),
	I(MessageUtils.getMessage("lbl.status.atividade.incompleto")),
	N(MessageUtils.getMessage("lbl.status.atividade.cancelado")),
	O(MessageUtils.getMessage("lbl.status.atividade.completo"));
	
	
    private String descricao;
    
    private StatusAtividadesEnum( final String descricao) {    
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
        return StatusAtividadesEnum.valueOf(key).getRotulo();
    }
	

	
}
