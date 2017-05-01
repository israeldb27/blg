package com.busqueumlugar.service;

public interface TimelineService {
	
	String LISTA_IDS_USUARIOS = "listaIdsUsuarios";
	String EXISTE_ID_USUARIO = "existeIdUsuario"; // atributo session para ajudar a saber se usuario da sessao tem um Id de contato ou de usuario seguindo
	String ULTIMA_REGRA_TIMELINE = "ultRegraTimeline";
	String ULTIMO_INDEX_IDS_USUARIO = "ultIndexIdsUsuarios"; // este campo auxilia para o controle Id usuario
	String ULTIMO_INDEX_IDS_USUARIO_COMPARTILHADO = "ultIndexIdsUsuariosCompartilhado"; // este campo auxilia para o controle Id usuario
	
	String QUANT_ANUNCIO_IMOVEL = "quantAnuncioImovel";
	String ID_ULTIMO_ANUNCIO_IMOVEL = "idUltimoAnuncioImovel";
	String ID_INDEX_PREF_IMOVEL = "idIndexPrefImoveis";
	String ID_INDEX_PREF_IMOVEL_ALEATORIAMENTE = "idIndexPrefImoveisAleatoriamente";
	String ID_INDEX_IMOVEL_ALEATORIAMENTE = "idIndexImovelAleatoriamente";
	String ID_INDEX_NOTA = "idIndexNota";
	String REGRA_PREF_IMOVEL = "regraPrefImovel";
	String ID_INDEX_SUGESTAO_USUARIO = "idIndexSugestaoUsuario";
	
	String PARAMETROS_TIMELINE = "parametrosTimeline";
}
