package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum PerfilUsuarioNormalEnum implements Serializable, ICodigoDescricao {
	
	P(MessageUtils.getMessage("lbl.perfil.usuario.comum")),
	C(MessageUtils.getMessage("lbl.perfil.usuario.corretor")),	
	I(MessageUtils.getMessage("lbl.perfil.usuario.imobiliaria"));
    
    private String descricao;
    
    private PerfilUsuarioNormalEnum( final String descricao) {
    
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
    
        return PerfilUsuarioNormalEnum.valueOf(key).getRotulo();
    }

}
