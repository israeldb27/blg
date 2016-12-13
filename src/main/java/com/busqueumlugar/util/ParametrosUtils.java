package com.busqueumlugar.util;


public interface ParametrosUtils {
    
    String STATUS_LIBERADO = "liberado";
    String STATUS_SUSPENSO_1_SEMANA = "suspenso1";
    String STATUS_SUSPENSO_2_SEMANA = "suspenso2";
    String STATUS_SUSPENSO_4_SEMANA = "suspenso4";
    String STATUS_SUSPENSO_12_SEMANA = "suspenso12";
    String STATUS_SUSPENSO_INDETERMINADO = "suspensoIndeterminado";
    
    
    String MSG_STATUS_SUSPENSO_1_SEMANA = MessageUtils.getMessage("msg.erro.suspensao.usuario.1.semana");
    String MSG_STATUS_SUSPENSO_2_SEMANA = MessageUtils.getMessage("msg.erro.suspensao.usuario.2.semana");
    String MSG_STATUS_SUSPENSO_4_SEMANA = MessageUtils.getMessage("msg.erro.suspensao.usuario.4.semana");
    String MSG_STATUS_SUSPENSO_12_SEMANA = MessageUtils.getMessage("msg.erro.suspensao.usuario.12.semana");
    String MSG_STATUS_SUSPENSO_INDETERMINADO = MessageUtils.getMessage("msg.erro.suspensao.usuario.indeterminado");
    
                            
    String TIPO_VISUALIZACAO = "tipoVisualizacao";                        
    
}
