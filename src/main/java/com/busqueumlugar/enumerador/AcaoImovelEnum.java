package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;
import com.mysql.jdbc.StringUtils;

public enum AcaoImovelEnum implements Serializable, ICodigoDescricao {
    
	C(MessageUtils.getMessage("lbl.acao.imovel.compra"), "success"),
	V(MessageUtils.getMessage("lbl.acao.imovel.venda"), "success"),
	A(MessageUtils.getMessage("lbl.acao.imovel.aluguel"), "warning"),	
	D(MessageUtils.getMessage("lbl.acao.imovel.dividir"), "info"),
	T(MessageUtils.getMessage("lbl.acao.imovel.temporada"), "primary");
    
    private String descricao;
    private String classe;

    private AcaoImovelEnum( final String descricao) {
    	
        this.descricao = descricao;
    }

    AcaoImovelEnum(String descricao, String classe) {
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
    		return AcaoImovelEnum.valueOf(key).getRotulo();
    	else
    		return "";
    }

    public static String getClassePorAcao(String acao) {
        if (! StringUtils.isNullOrEmpty(acao))
            return AcaoImovelEnum.valueOf(acao).getClasse();
        else
            return "";
    }

    public String getClasse() {
        return classe;
    }

}
