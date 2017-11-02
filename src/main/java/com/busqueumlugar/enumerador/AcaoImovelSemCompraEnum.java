package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;
import com.mysql.jdbc.StringUtils;

public enum AcaoImovelSemCompraEnum implements Serializable, ICodigoDescricao {
    
	V(MessageUtils.getMessage("lbl.acao.imovel.venda"), "success"),
	A(MessageUtils.getMessage("lbl.acao.imovel.aluguel"), "warning"),	
	D(MessageUtils.getMessage("lbl.acao.imovel.dividir"), "info"),
	T(MessageUtils.getMessage("lbl.acao.imovel.temporada"), "primary");
    
    private String descricao;
    private String classe;

    private AcaoImovelSemCompraEnum( final String descricao) {
    	
        this.descricao = descricao;
    }

    AcaoImovelSemCompraEnum(String descricao, String classe) {
        this.descricao = descricao;
        this.classe = classe;
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
    	if (! StringUtils.isNullOrEmpty(key))
    		return AcaoImovelSemCompraEnum.valueOf(key).getRotulo();
    	else
    		return "";
    }

    public static String getClassePorAcao(String acao) {
        if (! StringUtils.isNullOrEmpty(acao))
            return AcaoImovelSemCompraEnum.valueOf(acao).getClasse();
        else
            return "";
    }

    public String getClasse() {
        return classe;
    }

}
