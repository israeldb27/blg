package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum StatusImovelEnum implements Serializable, ICodigoDescricao {
    
    lancamento(MessageUtils.getMessage("lbl.status.imovel.lancamento")),
	novo(MessageUtils.getMessage("lbl.status.imovel.novo")),
	usado(MessageUtils.getMessage("lbl.status.imovel.usado"));
    
    private String descricao;
    
    private StatusImovelEnum( final String descricao) {
    
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
    
        return StatusImovelEnum.valueOf(key).getRotulo();
    }
    
}
