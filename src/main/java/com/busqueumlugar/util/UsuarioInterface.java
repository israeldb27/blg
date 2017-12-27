/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.util;

/**
 *
 * @author Israel 
 */

// Esta interface possui os valores iniciais de campos quantidade referentes a alguns campos da tabela Usuario
public interface UsuarioInterface {
	
	String USUARIO_SESSAO = "usuarioSessao";
	String FUNCIONALIDADE = "funcionalidade";
    
	String QUANT_DIAS_FIM_SERVICO = "quantDiasFimServico"; // 30;
    String QUANT_CADASTRO_IMOVEIS_INICIAL = "quantCadastroImoveisInicial";// 3;
    String QUANT_FOTOS_IMOVEL_INICIAL = "quantFotosImovelInicial";//5;
    String QUANT_INDICACOES_IMOVEL = "quantIndicacoesImovel";//5;
    String QUANT_INDICACOES_IMOVEL_EMAIL = "quantIndicacoesImovelEmail";//10;
    String QUANT_DIAS_GRATUIDADE = "quantDiasGratuidade";//15; // serao inicialmente concedidas 15 dias gratuidade
    
    String HABILITA_ENVIAR_SOL_PARCERIA = "habilitaEnviarSolParceria";//"S";
    String HABILITA_ENVIAR_MENSAGENS = "habilitaEnviarMensagens";//"S";
	
	String ASSINATURA_PAGA = "assinaturaPaga";
	String ASSINATURA_NAO_PAGA = "assinaturaNaoPaga";
	String ASSINATURA_SOLICITADA = "assinaturaSolicitada";
	
	String ID_IMOVEL_OFFLINE = "idImovelOffline";
    
}
