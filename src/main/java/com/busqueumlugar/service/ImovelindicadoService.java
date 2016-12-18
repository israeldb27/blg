package com.busqueumlugar.service;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelindicado;
import com.busqueumlugar.model.Usuario;

public interface ImovelindicadoService {
	
	Imovelindicado recuperarImovelindicadoPorId(Long id);

	void cadastrarIndicacao(Long idImovel, Long idUsuario, Long idUsuarioIndicador);
	
	void cadastrarIndicacaoPorEmail(Long idImovel, String email, Long idUsuarioIndicador);
	
	List<Imovelindicado> listarImovelIndicado(Long idUsuario, ImovelindicadoForm form);
	
	long quantImoveisIndicados(Long idUsuario);
	
	long checaQuantImoveisIndicados(Long idUsuario);
	
	List<Imovelindicado> listarImoveisIndicacoes(Long idUsuario, ImovelindicadoForm form);
	
	void excluirImovelIndicado(Imovel imovel);
	
	void excluirImovelIndicado(Long id);
	
	void excluirImovelIndicacao(Imovel imovel, Long idUsuario);
	
	void excluirImovelIndicado(Imovel imovel, Long idUsuario);
	
	Imovelindicado validarIndicavaoImovel(Imovel imovel, long idUsuario);
	
	boolean atualizarStatusIndicado(Long idImovel, Long idUsuario);
	
	List<Imovel> recuperarImoveisIndicadosNovos(Long idUsuario);
	
	long checarQuantidadeNovosImoveisIndicados(Long idUsuario);
	
	List<Imovel> checarImoveisMaisReceberamIndicacoesPorPeriodo(AdministracaoForm form);
	
	EmailImovel indicarImovelPorEmail(ImovelForm frm);
	
	boolean checarPermissaoIndicarImoveis(Long idUsuario, int quantSelecionados);
	
	List<Imovelindicado> ordenarImoveisIndicados(Long idUsuario, ImovelindicadoForm form);
	
	public List<Imovelindicado> filtrar(String tipoFiltro, Long idUsuario, ImovelindicadoForm form);

    public List<Usuario> agruparUsuariosImoveisIndicados(Long idUsuario, ImovelindicadoForm form);

    public List<Usuario> listarUsuariosImoveisIndicacoes(Long idUsuario, ImovelindicadoForm form);

	List<Imovel> agruparImoveis(Long idUsuario, ImovelindicadoForm form);

	public List<Imovelindicado> recuperarSolicitacoesIndicacoesPorImovel(Long idImovel);

	public List<Imovelindicado> recuperarSolicitacoesIndicadosParaMimPorIdUsuario(Long idUsuarioSessao,Long idUsuario);

	void cadastrarIndicacaoSelecionados(ImovelindicadoForm form, Long idUsuarioSessao);

	String validarIndicavaoImovel(Long idImovel, Long idUsuario);

	boolean atualizarStatusIndicado(Long idImovelIndicado);

	String checarUsuarioIndicacaoImovel(Long idUsuario, Long idImovel);

	String validarIndicavaoImovelUsuariosSelecionados(ImovelindicadoForm form,	Long idUsuario);
	
	List<Usuario> filtrarAgruparUsuario(Long idUsuario, ImovelindicadoForm form);		
	
	List<Imovel> filtrarAgruparImoveis(Long idUsuario, ImovelindicadoForm form);

	List<?> ordenarAgruparImoveisIndicados(Long idUsuario, ImovelindicadoForm form);

	List<Imovelindicado> recuperarSolicitacoesIndicacoesPorImovelPorUsuario(Long idImovel, Long idUsuario);

	void atualizarStatusImoveisIndicadosSolRecebidas(Long idUsuario);	
	
}
