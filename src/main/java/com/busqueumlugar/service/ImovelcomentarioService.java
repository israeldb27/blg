package com.busqueumlugar.service;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelcomentarioForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelcomentario;
import com.busqueumlugar.model.Usuario;

public interface ImovelcomentarioService {
	
	Imovelcomentario recuperarImovelcomentarioPorId(Long id);
	
	List<Imovel> relatorioImoveisMaisComentadosPorPeriodo(RelatorioForm frm);
	
	List<Imovel> relatorioImoveisMaisComentadosPorPeriodo(AdministracaoForm form);
	
	List<Imovelcomentario> listarComentarios(Long idImovel, ImovelcomentarioForm form);
	
	void cadastrarComentario(Long idImovel, UsuarioForm usuarioForm, String comentario);
	
	void cadastrarComentario(Long idImovel, Long idUsuario,	String comentario);
	
	void excluirComentarioImovel(Imovelcomentario comentario);
	
	List<Imovelcomentario> listarComentariosPorUsuario(Long idUsuario);
	
	List<Imovelcomentario> listarComentariosSobreMeuImovelOutros(ImovelcomentarioForm form, Long idUsuario);
	
	long checaQuantidadeNovoComentario(Long idUsuario);
	
	List<Imovelcomentario> recuperarNovosComentarios(List<Imovelcomentario> lista);
	
	boolean atualizarStatusImovelComentario(Imovelcomentario imovelcomentario) ;
	
	long checaQuantidadeNovoComentarioPorImovel(Long idImovel);
	
	List<Imovel> checarImoveisMaisReceberamComentariosPorPeriodo(Date dataInicio, Date dataFim, int quant) ;
	
	EmailImovel notificarComentarioImovel(Long idImovel);	
	
	List<Imovelcomentario> ordenarImoveisComentarios(Long idUsuario, String opcaoOrdenacao, String tipoLista);
	
	List<Imovelcomentario> ordenarImoveisComentarios(List<Imovelcomentario> lista, String opcaoOrdenacao);
	
	List<Imovelcomentario> agruparImoveis(List<Imovelcomentario> listaImoveisComentariosOutros);
	
	List<Imovelcomentario> agruparImoveis(Long idImovel);
	
	Imovelcomentario recuperarUltimoComentarioPorIdImovel(Long idImovel);
	
	List<Imovelcomentario> recuperarTodosComentariosPorImovel(Long idImovel);
	
	List<Imovelcomentario> agruparImoveisMeusComentarios(List<Imovelcomentario> listaImoveisComentarios, Long idUsuario);

	List<Imovel> agruparImoveis(Long idUsuario, ImovelcomentarioForm form);

	List<Usuario> agruparUsuarios(Long idUsuario, ImovelcomentarioForm form);

	List<Imovelcomentario> listarComentariosSobreMeusImoveisPorusuario(Long idUsuarioSessao, Long idUsuario);

	List<Imovelcomentario> filtrarComentariosSobreMeusImoveis(Long idUsuario, ImovelcomentarioForm form);

	List<Imovelcomentario> filtrarrMeusComentarios(Long idUsuario, ImovelcomentarioForm form);

	void atualizarStatusComentariosMeusImoveis(Long idUsuario);

	long checarQuantidadeTotalImoveisComentariosRecebidos(Long idUsuario);

	List<Imovelcomentario> listarComentariosPorQuant(Long idImovel, int quantMaxLista);
	
}
