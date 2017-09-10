package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;


public enum TipoContatoOpcao2Enum implements Serializable, ICodigoDescricao {
	
	usuariosSeguidores(MessageUtils.getMessage("lbl.relatorio.usuarios.seguidores")),
	usuariosSeguindo(MessageUtils.getMessage("lbl.relatorio.usuarios.seguindo")),
	meusContatos(MessageUtils.getMessage("lbl.relatorio.meus.contatos"));
	
    private String descricao;
    
    private TipoContatoOpcao2Enum( final String descricao) {
    
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
    
        return TipoContatoOpcao2Enum.valueOf(key).getRotulo();
    }

}
