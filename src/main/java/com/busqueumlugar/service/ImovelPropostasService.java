package com.busqueumlugar.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelPropostasForm;
import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.ImovelPropostas;
import com.busqueumlugar.model.Usuario;

public interface ImovelPropostasService {

	ImovelPropostas recuperarImovelImovelPropostasPorId(Long id);
	
	List<Imovel> relatorioImoveisMaisPropostadosPorPeriodo(RelatorioForm frm);
	
	void cadastrarProposta(Long idImovel, ImovelPropostasForm imovelPropostasForm);
	
	void cadastrarProposta(Long idImovel, Long idUsuario, String novaDescricao, BigDecimal valorProposta);
	
	void excluirProposta(Long id);
	
	List<ImovelPropostas> recuperarPropostasLancadasPorUsuario(Long idUsuario, ImovelPropostasForm imovelPropostasForm);
	
	List<ImovelPropostas> recuperarPropostasImovel(Long idImovel);
	
	List<ImovelPropostas> recuperarPropostasImovelPorUsuario(Long idUsuario, Long idImovel);
	
	List<ImovelPropostas> recuperarPropostasRecebidasPorUsuario(Long idUsuario, ImovelPropostasForm form);
	
	List<ImovelPropostas> recuperarImoveisPropostasNovas(List<ImovelPropostas> lista);
	
	List<ImovelPropostas> recuperarImoveisPropostasNovas(Long idUsuario);
	
	boolean atualizarStatusImovelProposta(ImovelPropostas imovelPropostas);
	
	List<Imovel> checarImoveisMaisReceberamPropostasPorPeriodo(AdministracaoForm form);
	
	EmailImovel notificarLancamentoPropostas(Long idImovel);
	
	List<ImovelPropostas> ordenarImoveisPropostas(Long idUsuario,  ImovelPropostasForm form);

	List<ImovelPropostas> agruparImoveis(List<ImovelPropostas> listaImoveisPropostasRecebidas);
	
	ImovelPropostas recuperarUltimaPropostaPorIdImovel(Long idImovel);
	
	String validarCadastroProposta(Long idImovel, ImovelPropostasForm imovelPropostasForm, UsuarioForm userSession);
	
	String validarCadastroProposta(Long idImovel, String valorProposta, UsuarioForm userSession);

	List<ImovelPropostas> filterPropostasRecebidas(Long idUsuario,ImovelPropostasForm form);

	List<ImovelPropostas> filterPropostasEnviadas(Long idUsuario, ImovelPropostasForm form);

	List<Imovel> agruparImoveis(Long idUsuario, ImovelPropostasForm form);

	List<Usuario> agruparUsuarios(Long idUsuario, ImovelPropostasForm form);

	List<ImovelPropostas> recuperarUsuarioPropostasImovelUsuarioSessaoRecebidas(Long idUsuario, Long idUsuarioSessao);

	List<Usuario> filtrarAgruparUsuarios(Long idUsuario, ImovelPropostasForm form);

	List<Imovel> filtrarAgruparImoveis(Long idUsuario, ImovelPropostasForm form);

	List<?> ordenarAgruparImoveisProposta(Long idUsuario, ImovelPropostasForm form);

	void atualizarStatusLeituraImoveisPropostas(Long idUsuario);

	long checarQuantidadesPropostasRecebidasPorUsuarioPorStatus(Long idUsuario, String status);	
}
