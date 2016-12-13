package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum TipoParamServicoOpcaoEnum implements Serializable, ICodigoDescricao{
	
	TIPO_PARAM_SERVICO_USUARIO("U"),
	TIPO_PARAM_SERVICO_IMOVEL("I"),
	TIPO_PARAM_SERVICO_ASSINATURA("A"),
	TIPO_PARAM_SERVICO_RELATORIO("R");

	private String descricao;
	
	private TipoParamServicoOpcaoEnum( final String descricao) {    	
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
    
        return TipoParamServicoOpcaoEnum.valueOf(key).getRotulo();
    }

}
