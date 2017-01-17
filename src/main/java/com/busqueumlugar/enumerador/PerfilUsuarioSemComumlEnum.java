package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum PerfilUsuarioSemComumlEnum implements Serializable, ICodigoDescricao {
	
	C(MessageUtils.getMessage("lbl.perfil.usuario.corretor")),	
	I(MessageUtils.getMessage("lbl.perfil.usuario.imobiliaria"));
    
    private String descricao;
    
    private PerfilUsuarioSemComumlEnum( final String descricao) {
    
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
    
        return PerfilUsuarioSemComumlEnum.valueOf(key).getRotulo();
    }

}
