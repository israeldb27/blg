package com.busqueumlugar.enumerador;

import java.io.Serializable;


public enum TipoContatoOpcaoEnum implements Serializable, ICodigoDescricao {
	
	TODOS_USUARIOS("todosUsuarios"),
	USUARIOS_SEGUIDORES("usuariosSeguidores"),
	USUARIOS_SEGUINDO("usuariosSeguindo"),
	MEUS_CONTATOS("meusContatos");
	
    private String descricao;
    
    private TipoContatoOpcaoEnum( final String descricao) {
    
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
    
        return TipoContatoOpcaoEnum.valueOf(key).getRotulo();
    }

}
