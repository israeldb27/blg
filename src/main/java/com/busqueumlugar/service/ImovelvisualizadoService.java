package com.busqueumlugar.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelvisualizadoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.model.Usuario;

public interface ImovelvisualizadoService {
	
	Imovelvisualizado recuperarImovelvisitadoPorId(Long id);
	
	List<Imovel> relatorioImoveisMaisVisitadosPorPeriodo(RelatorioForm frm);
	
	List<Imovel> relatorioImoveisMaisVisitadosPorPeriodo(AdministracaoForm form);
	
	List<Imovel> filtrarImovelMaisVisitado(String opcaoPeriodo, ImovelForm imovelForm);
	
	List<Imovel> recuperarImoveisMaisVisitados();
	
	void adicionarVisitaImovel(Long idImovel, Long idUsuario);
	
	List<Imovelvisualizado> recuperarUsuariosVisitantesPorImovel(Long idUsuario, ImovelvisualizadoForm form);
	
	List<Imovelvisualizado> carregaListaNovosUsuariosVisitantes(List<Imovelvisualizado> lista);
	
	boolean atualizarStatus(Imovelvisualizado imovelVisitado);
	
	List<Imovelvisualizado> recuperarUsuariosVisitantesPorImovelNovos(Long idUsuario);
	
	List<Imovelvisualizado> recuperarUsuariosVisitantesPorIdImovel(Long idImovel);
	
	List<Imovel> checarImoveisMaisAcessadosPorPeriodo(Date dataInicio, Date dataFim, int quantImoveis);
	
	List<Imovel> checarImoveisMaisReceberamVisitasPorPeriodo(Date dataInicio, Date dataFim, int quant);
	
	int checarQuantidadeVisitarsPorImovel(Long idImovel);
	
	EmailImovel notificarVisitaImovel(Long idImovel);
	
	Imovelvisualizado recuperarUltimaVisitaPorIdImovel(Long idImovel);
	
	List<Imovelvisualizado> agruparImoveis(List<Imovelvisualizado> listaUsuariosVisitantes);
	
	Imovelvisualizado recuperarUltimoUsuarioVisitantePorIdImovel(Long idImovel);

	List<Imovelvisualizado> recuperarImoveisVisitadorPorIdUsuario(Long idUsuario, ImovelvisualizadoForm form);

	List<Imovelvisualizado> ordenarImoveis(Long idUsuario, ImovelvisualizadoForm form, String tipoLista);
	
	List<Imovel> agruparImoveis(Long idUsuario, ImovelvisualizadoForm form);

	List<Usuario> agruparUsuarios(Long idUsuario, ImovelvisualizadoForm form);

	List<Imovelvisualizado> recuperarImoveisVisitadosPorUsuario(Long idUsuario, Long id);

	List<Imovelvisualizado> filtrarUsuariosVisitantes(Long idUsuario, ImovelvisualizadoForm form);

	List<Imovelvisualizado> filtrarImoveisVisitados(Long idUsuario, ImovelvisualizadoForm form);

	List<Usuario> filtrarAgruparUsuarios(Long idUsuario, ImovelvisualizadoForm form);

	List<Imovel> filtrarAgruparImoveis(Long idUsuario, ImovelvisualizadoForm form);

	List<?> ordenarAgruparImoveisVisitados(Long idUsuario, ImovelvisualizadoForm form);

	void atualizarStatusLeituraMeusImoveisVisitados(Long idUsuario);

	long checarQuantidadeVisitantesPorUsuarioPorStatus(Long idUsuario, String status);		
	
}
