package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum QuemPodeEnviarSolicitacoesEnum implements Serializable, ICodigoDescricao {	
	
	 T(MessageUtils.getMessage("lbl.permissao.visualiza.todos")),
	 C(MessageUtils.getMessage("lbl.permissao.visualiza.apenas.contatos")),
	 S(MessageUtils.getMessage("lbl.permissao.visualiza.seguidores")),
	 G(MessageUtils.getMessage("lbl.permissao.visualiza.seguindo"));

	 private String descricao;
	    
     private QuemPodeEnviarSolicitacoesEnum( final String descricao) {
    
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
    
        return QuemPodeEnviarSolicitacoesEnum.valueOf(key).getRotulo();
     }

}
