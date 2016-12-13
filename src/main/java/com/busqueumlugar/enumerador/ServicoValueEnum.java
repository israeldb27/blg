package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum ServicoValueEnum implements Serializable, ICodigoDescricao{

	RELATORIO_PADRAO("relatorioCliente"),	
	RELATORIO_CORRETOR("relatorioCorretor"),
	RELATORIO_IMOBILIARIA("relatorioImobiliaria"),
	RELATORIOS("relatorios"),
	ASSINATURA_PADRAO("assinaturaPadrao"),
	ASSINATURA_CORRETOR("assinaturaCorretor"),
	ASSINATURA_IMOBILIARIA("assinaturaImobiliaria"),
	ADICIONAR_FOTOS("adicionarFotos"),
	ADICIONAR_IMOVEIS("adicionarImoveis"),
	INDICACOES_IMOVEIS("indicacoesImoveis"),
	INDICACOES_EMAIL("indicacoesEmail"),
	PARCERIA("P"); 

	private String descricao;
	
	private ServicoValueEnum( final String descricao) {    	
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
    
        return ServicoValueEnum.valueOf(key).getRotulo();
    }
}
