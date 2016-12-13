package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum StatusPagtoOpcaoEnum implements Serializable, ICodigoDescricao {
		
	SOLICITADO("S"),
	PAGO("P"),
	CONCEDIDO("C"),
	CANCELADO("N"),	
	AGUARDANDO("A"),
	REVOGADO("R");
	
	
    private String descricao;
    
    private StatusPagtoOpcaoEnum( final String descricao) {
    
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
    
        return StatusPagtoOpcaoEnum.valueOf(key).getRotulo();
    }
	
	

}
