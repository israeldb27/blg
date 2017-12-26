package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum ChanceInteresseEnum implements Serializable, ICodigoDescricao{
	  
    MA(MessageUtils.getMessage("lbl.poss.interessado.chance.muito.alta")),
    AL(MessageUtils.getMessage("lbl.poss.interessado.chance.alta")),
    ME(MessageUtils.getMessage("lbl.poss.interessado.chance.media")),
    BA(MessageUtils.getMessage("lbl.poss.interessado.chance.baixa")),
    MB(MessageUtils.getMessage("lbl.poss.interessado.chance.muito.baixa"));
	
    private String descricao;
	    
    private ChanceInteresseEnum( final String descricao) {    
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
        return ChanceInteresseEnum.valueOf(key).getRotulo();
    }

}
