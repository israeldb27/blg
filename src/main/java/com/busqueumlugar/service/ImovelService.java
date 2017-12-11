package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelMapaForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Estados;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Usuario;

import org.springframework.validation.BindingResult;

public interface ImovelService {
	
	String QUANT_IMOVEIS_COMPARATIVO = "quantImoveisComparativo";
	String LISTA_IMOVEL_COMPARATIVO = "listaImovelComparativo";
	String QUANT_LISTA_IMOVEL_COMPARATIVO = "quantListaImovelComparativo";
	String QUANT_IMOVEIS_PropostaS = "quantNovasImoveisPropostas";
	String QUANT_TOTAL_IMOVEIS_PropostaS = "quantTotalImoveisPropostas";
	String QUANT_TOTAL_IMOVEIS_COMENTARIOS = "quantTotalComentarios";
	String QUANT_NOVOS_IMOVEIS_COMENTARIOS = "quantNovoComentario";
	String QUANT_MEUS_IMOVEIS = "quantMeusImoveis";
	String QUANT_TOTAL_USUARIOS_INTERESSADOS = "quantTotalUsuarioInteressado";
	String QUANT_NOVOS_USUARIOS_INTERESSADOS = "quantNovoUsuarioInteressado";
	String QUANT_IMOVEIS_INDICADOS = "quantNovoImovelIndicado";
	String QUANT_NOVOS_VISITANTES = "quantNovosVisitantes";
	String QUANT_TOTAL_VISITANTES = "quantTotalVisitantes";	
	String LISTA_NOVAS_NOTIFICACOES = "listaNovasNotificacoes";
	String QUANT_NOVAS_NOTIFICACOES = "quantNovasNotificacoes";
	String QUANT_NOVAS_PARCERIAS = "quantNovasParcerias";
	String QUANT_NOVAS_INTERMEDIACOES = "quantNovasIntermediacoes";
	String LISTA_IMOVEL_ANUNCIO_DESTAQUE = "listaImovelAnuncioDestaque";
	
	int QUANT_MAX_LISTA = 3;
	
	String PATH_ERRO_IMOVEL = "/mensagemErro";
	String PATH_ERRO_GERAL  = "/mensagemErro";
	
	Imovel recuperarImovelPorid(Long idImovel);
	
	void atualizarStatusNegociacaoImovelCliente(Long idImovel, String novoStatus);
	
	ImovelForm cadastrar(ImovelForm frm);
	
	List<Imovel> buscar(ImovelForm imovelForm, Long idUsuario, String opcaoOrdenacao);
	
	List<Imovel> refinarBuscarPorUsuario(PerfilForm perfilForm, long idUsuario);
	
	List<Imovel> listarMeusImoveis(Long idUsuario, ImovelForm frm);
	
	List<Imovel> listarMeusImoveis(Long idUsuario);
	
	int quantMeusImoveis(Long idUsuario, ImovelForm form);
	
	void excluirImovel(Imovel imovel);
	
	void excluirImovel(Long idImovel);
	
	ImovelForm atualizarImovel(ImovelForm frm, UsuarioForm user);	
	
	boolean checaStatusLiberadoUsuarioPorImovel(Long idImovel);
	
	void adicionarFotoPrincipalImovel(Long idImovel, byte[] foto);
	
	int checarQuantCadastradaFotosImovel(Long idImovel);
	
	void adicionarMaisFotos(long idImovel, int quantFotoPaga);	
	
	
	String gerarCodigoIdentificacao(Imovel imovel, Estados estado);
	
	int checarQuantidadeTotalImoveis();
	
	int checarQuantidadeTotalImoveisPorPeriodo(AdministracaoForm form);
	
	void adicionarMaisCompartilhamento(long idImovel, int quantCompartilhamento);
	
	List<Imovel> recuperarImoveisDestaquePorIdUsuario(Long idUsuario);
	
	void atualizarMapaImovel(Long idImovelSelecionado, double latitude, double longitude);
	
	ImovelForm carregarImovelFormPorImovel(Long idImovel);
	
	boolean existeImovelComMapa(List<Imovel> listaImoveis);
	
	void reativaImoveisUsuario(long idUsuario);
	
	void desativarImoveisUsuario(Long idUsuario);
	
	List<Imovel> acessoVisualizacao(List<Imovel> listaImoveis, UsuarioForm user, Long idPerfil);
	
	List<Imovel> recuperarImovelPorCodigoIdentificacaoPorUsuario(ImovelForm form, Long idUsuario, boolean isUsuarioSessao);

	long checarQuantMeusImoveis(Long idUsuario);

	void prepararImovelParaAtualizacao(Long idImovel, ImovelForm form);

	void preparaCadastroImovel(ImovelForm form);
	
	void preparaImovelDestaqueFormAdmin(Long idImovel, ImovelForm form);

	List<Imovel> recuperarImoveisNaoDestaquePorIdUsuario(Long idUsuario);

	void preparaDetalhesImovelForm(Long idImovel, ImovelForm form, UsuarioForm usuarioForm);
	
	boolean validarDadosCadastroImovel(ImovelForm form, BindingResult result);

	boolean validarDadosEdicaoImovel(ImovelForm form, BindingResult result);

	void preparaDetalhesImovelFormAdmin(Long idImovel, ImovelForm form);

	ImovelForm carregaGaleriaFotosImovel(Long idImovel, boolean carregaFotoPrincipal);

	ImovelForm atualizarFotoImovel(ImovelForm form);

	List<Imovel> carregaListaImoveisComparativos(List<Long> lista);
	
	void cadastrarImovelDestaque(ImovelForm form);
	
	List<Imovel> recuperarImovelDestaqueParaAnuncio(int quant);

	List<ImovelMapaForm>  buscarImoveisMapa(ImovelForm form, Long idUsuario);
	
	List<Imovel> pesquisarTodosImoveis (String valor);
	
	List<Imovel> recuperarImovelDestaqueParaTelaInicial(int i);

	List<ImovelMapaForm> buscarImoveisMapaPorIdUsuario(ImovelForm form, Long idUsuario);

	String carregarTimeLine();
	
	String carregarTimeLine2();

	List<Imovel> recuperarImovelPorIdsUsuarioPorPosicao(List<Long> listaIds, int index);

	List<Imovel> recuperarImoveisAleatoriamente(UsuarioForm user, int index);

	List<Imovel> recuperarImovelPorIdsUsuarioPorPosicaoPorAceitaCompartilhado(List<Long> listaIds, int index, String aceitaCompartilhado);

	List<Imovel> recuperarImoveisAleatoriamente(List<Long> listaIds, int index);

	List<Imovel> recuperarImovelPorIdsUsuarioPorPosicaoPorAceitaCompartilhado(Long idUsuario, int index, String aceitaCompartilhado);

	List<Imovel> recuperarImovelPorIdsUsuarioPorPosicao(Long idUsuario, int index);

	String validarPrepararImovel(Long idImovel, UsuarioForm user);

	List<Imovel> buscarImovelParaTimeline(ImovelForm form, Long idUsuario);

	List<Usuario> pesquisarPossiveisCompradores(Long idUsuario, ImovelForm form);
	
		
}
