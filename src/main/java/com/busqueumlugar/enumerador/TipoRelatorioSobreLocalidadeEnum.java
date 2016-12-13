package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum TipoRelatorioSobreLocalidadeEnum implements Serializable, ICodigoDescricao {
	
	B(MessageUtils.getMessage("lbl.rel.opcao.relatorio.sobre.localidade.mais.baratos")),
	C(MessageUtils.getMessage("lbl.rel.opcao.relatorio.sobre.localidade.mais.caros"));
	
    private String descricao;
    
    private TipoRelatorioSobreLocalidadeEnum( final String descricao) {
    
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
    
        return TipoRelatorioSobreLocalidadeEnum.valueOf(key).getRotulo();
    }

}
