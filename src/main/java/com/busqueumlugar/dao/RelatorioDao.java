package com.busqueumlugar.dao;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Relatorio;
import com.busqueumlugar.model.RelatorioQuantAssinatura;

public interface RelatorioDao extends GenericDAO<Relatorio, Long> {
	
	Relatorio findRelatorioById(Long id);

	List<Relatorio> findRelatorioSistemaByNome(String nome);
	
	List listarQuantImoveisCriadosLocalizacaoAcaoTipoImovel(RelatorioForm form, List listaIds);
	
	List recuperarTiposImoveisQuantidade(RelatorioForm form, Long idUsuario);
	
	List recuperarVariacaoPrecoImovel(RelatorioForm form, List listaIds);
	
	List<Relatorio> findRelatorioUsuarioSistemaByItem(String itemRelatorio, String perfil);
	
	List findQuantidadeTotalAssinaturasByStatus(RelatorioForm form, String status);	
	
	List findAssinaturaVolFinanceiroByStatus(RelatorioForm form, String status);
	
	List<RelatorioQuantAssinatura> findAssinaturasByUsuario(String buscarUsuario);

	List recuperarTiposImoveisQuantidadesPorIdsUsuarios(RelatorioForm frm, List listaIds);
	
}
