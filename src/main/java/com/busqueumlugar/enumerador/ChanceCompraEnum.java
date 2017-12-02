package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum ChanceCompraEnum implements Serializable, ICodigoDescricao{
	  
    MA(MessageUtils.getMessage("lbl.poss.comprador.chance.muito.alta")),
    AL(MessageUtils.getMessage("lbl.poss.comprador.chance.alta")),
    ME(MessageUtils.getMessage("lbl.poss.comprador.chance.media")),
    BA(MessageUtils.getMessage("lbl.poss.comprador.chance.baixa")),
    MB(MessageUtils.getMessage("lbl.poss.comprador.chance.muito.baixa"));
	
    private String descricao;
	    
    private ChanceCompraEnum( final String descricao) {    
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
        return ChanceCompraEnum.valueOf(key).getRotulo();
    }

}
