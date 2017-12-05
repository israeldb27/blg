package com.busqueumlugar.enumerador;

import java.io.Serializable;

public enum QuemPodeEnviarSolicitacoesOpcaoEnum implements Serializable, ICodigoDescricao{
	
	 TODOS("T"),
	 CONTATOS("C"),
	 SEGUIDORES("S"),
	 SEGUINDO("G");	

	 private String descricao;
	    
     private QuemPodeEnviarSolicitacoesOpcaoEnum( final String descricao) {
    
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
    
        return QuemPodeEnviarSolicitacoesOpcaoEnum.valueOf(key).getRotulo();
     }

}
