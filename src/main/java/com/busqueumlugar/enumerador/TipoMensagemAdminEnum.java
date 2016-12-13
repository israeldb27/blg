package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum TipoMensagemAdminEnum implements Serializable, ICodigoDescricao {
    
    D(MessageUtils.getMessage("lbl.tipo.mensagem.duvida")),
	R(MessageUtils.getMessage("lbl.tipo.mensagem.reclamacao")),
	S(MessageUtils.getMessage("lbl.tipo.mensagem.sugestao")),
	O(MessageUtils.getMessage("lbl.tipo.mensagem.outro"));	
    
    private String descricao;
    
    private TipoMensagemAdminEnum( final String descricao) {
    	
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
    
        return TipoMensagemAdminEnum.valueOf(key).getRotulo();
    }
    
}
