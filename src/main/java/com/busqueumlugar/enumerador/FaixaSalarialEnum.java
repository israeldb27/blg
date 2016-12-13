package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum FaixaSalarialEnum implements Serializable, ICodigoDescricao {
	
	f1(MessageUtils.getMessage("lbl.faixa.salarial.1")),
	f2(MessageUtils.getMessage("lbl.faixa.salarial.2")),
	f3(MessageUtils.getMessage("lbl.faixa.salarial.3")),
	f4(MessageUtils.getMessage("lbl.faixa.salarial.4")),
	f5(MessageUtils.getMessage("lbl.faixa.salarial.5")),
	f6(MessageUtils.getMessage("lbl.faixa.salarial.6")),
	f7(MessageUtils.getMessage("lbl.faixa.salarial.7")),
	f8(MessageUtils.getMessage("lbl.faixa.salarial.8"));
	
	private String descricao;
	    
    private FaixaSalarialEnum( final String descricao) {
    	
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
    
        return FaixaSalarialEnum.valueOf(key).getRotulo();
    }

}
