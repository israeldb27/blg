package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum StatusPagtoEnum implements Serializable, ICodigoDescricao {
    
    S(MessageUtils.getMessage("lbl.status.pagto.solicitado")),
	P(MessageUtils.getMessage("lbl.status.pagto.pago")),
	C(MessageUtils.getMessage("lbl.status.pagto.concedido")),
	N(MessageUtils.getMessage("lbl.status.pagto.cancelado")),	
	A(MessageUtils.getMessage("lbl.status.pagto.aguardando")),
	R(MessageUtils.getMessage("lbl.status.pagto.revogado"));
    
    private String descricao;
    
    private StatusPagtoEnum( final String descricao) {
    
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
    
        return StatusPagtoEnum.valueOf(key).getRotulo();
    }
}
