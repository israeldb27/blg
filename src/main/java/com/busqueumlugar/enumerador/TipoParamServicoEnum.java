package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum TipoParamServicoEnum implements Serializable, ICodigoDescricao{
	
	U(MessageUtils.getMessage("lbl.opcao.tipo.servico.usuario")),
	I(MessageUtils.getMessage("lbl.opcao.tipo.servico.imovel")),
	A(MessageUtils.getMessage("lbl.opcao.tipo.servico.assinatura")),
	R(MessageUtils.getMessage("lbl.opcao.tipo.servico.relatorio"));

	private String descricao;
	
	private TipoParamServicoEnum( final String descricao) {    	
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
    
        return TipoParamServicoEnum.valueOf(key).getRotulo();
    }

}
