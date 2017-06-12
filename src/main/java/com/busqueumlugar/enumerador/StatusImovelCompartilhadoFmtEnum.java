package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum StatusImovelCompartilhadoFmtEnum implements Serializable, ICodigoDescricao {
	
	A(MessageUtils.getMessage("lbl.status.aceita.fmt")),
	S(MessageUtils.getMessage("lbl.status.solicitado.fmt"));
	
    private String descricao;
    
    private StatusImovelCompartilhadoFmtEnum( final String descricao) {
    
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
    
        return StatusImovelCompartilhadoFmtEnum.valueOf(key).getRotulo();
    }
}
