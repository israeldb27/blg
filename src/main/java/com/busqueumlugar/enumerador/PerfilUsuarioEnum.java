package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum PerfilUsuarioEnum implements Serializable, ICodigoDescricao {
    
    P(MessageUtils.getMessage("lbl.perfil.usuario.comum")),
	C(MessageUtils.getMessage("lbl.perfil.usuario.corretor")),
	A(MessageUtils.getMessage("lbl.perfil.usuario.administrador")),
	I(MessageUtils.getMessage("lbl.perfil.usuario.imobiliaria"));
    
    private String descricao;
    
    private PerfilUsuarioEnum( final String descricao) {
    
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
    
        return PerfilUsuarioEnum.valueOf(key).getRotulo();
    }
    
}
