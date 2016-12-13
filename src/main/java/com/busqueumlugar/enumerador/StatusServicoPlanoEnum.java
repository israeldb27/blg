package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum StatusServicoPlanoEnum implements Serializable, ICodigoDescricao {
    
	//todos(MessageUtils.getMessage("lbl.status.servico.todos")),
    solicitado(MessageUtils.getMessage("lbl.status.servico.sol")),
	pago(MessageUtils.getMessage("lbl.status.servico.pago")),
	cancelado(MessageUtils.getMessage("lbl.status.pagto.cancelado")),	
	concedido(MessageUtils.getMessage("lbl.status.servico.concedido"));
	
    private String descricao;
    
    private StatusServicoPlanoEnum( final String descricao) {
    	
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
    
        return StatusServicoPlanoEnum.valueOf(key).getRotulo();
    }
    
}
