package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;


public enum TipoImovelEnum implements Serializable, ICodigoDescricao {
    
    A(MessageUtils.getMessage("lbl.tipo.imovel.apto")),
	C(MessageUtils.getMessage("lbl.tipo.imovel.casa.padrao")),
	Q(MessageUtils.getMessage("lbl.tipo.imovel.quarto")),	
	B(MessageUtils.getMessage("lbl.tipo.imovel.cobertura")),
	P(MessageUtils.getMessage("lbl.tipo.imovel.pousada")),
	T(MessageUtils.getMessage("lbl.tipo.imovel.terreno")),
	F(MessageUtils.getMessage("lbl.tipo.imovel.fazenda")),
	H(MessageUtils.getMessage("lbl.tipo.imovel.haras")),
	S(MessageUtils.getMessage("lbl.tipo.imovel.sitio")),
	L(MessageUtils.getMessage("lbl.tipo.imovel.loft")),
	K(MessageUtils.getMessage("lbl.tipo.imovel.kitinete")),
	N(MessageUtils.getMessage("lbl.tipo.imovel.flat")),
	V(MessageUtils.getMessage("lbl.tipo.imovel.casa.vila")),
	O(MessageUtils.getMessage("lbl.tipo.imovel.loteamento")),
	E(MessageUtils.getMessage("lbl.tipo.imovel.hotel")),
	D(MessageUtils.getMessage("lbl.tipo.imovel.casa.condominio")),
	G(MessageUtils.getMessage("lbl.tipo.imovel.galpao")),
	I(MessageUtils.getMessage("lbl.tipo.imovel.predio")),
	R(MessageUtils.getMessage("lbl.tipo.imovel.chacara")),
	U(MessageUtils.getMessage("lbl.tipo.imovel.studio")),
	M(MessageUtils.getMessage("lbl.tipo.imovel.comercial"));
	
    
    private String descricao;
    
    private TipoImovelEnum( final String descricao) {
    
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
    
        return TipoImovelEnum.valueOf(key).getRotulo();
    }
    
}
