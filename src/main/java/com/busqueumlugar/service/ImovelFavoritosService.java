package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelfavoritosForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelfavoritos;
import com.busqueumlugar.model.Usuario;

public interface ImovelFavoritosService {
	
	List<Imovel> relatorioImoveisMaisAdotadosInteressadosPorPeriodo(RelatorioForm frm);
	
	List<Imovelfavoritos> listarImoveisPorUsuario (Long idUsuario, ImovelfavoritosForm form);
	
	void adicionarFavoritosPorUsuario(Long idUsuario, ImovelForm frm);
	
	void adicionarFavoritosPorUsuario(Long idUsuario, Imovel imovel);
	
	String validarImovelFavoritos(Long idUsuario, Long idImovel);
	
	List<Imovelfavoritos> listarUsuariosInteressadosMeusImoveis(Long idUsuario, ImovelfavoritosForm form);
	
	List<Imovelfavoritos> listarUsuariosInteressadosMeusImoveis(Long idUsuario);
	
	void excluirUsuarioInteressado(ImovelfavoritosForm imovelFavForm);
	
	void excluirImovelInteresse(Imovel imovel, UsuarioForm user);
	
	void excluirImovelFavoritos(Long idImovel, Long idUsuario);
	
	int checarExisteNovoUsuarioInteressado(Long idUsuario);
	
	int checarExisteNovoUsuarioInteressado(List<Imovelfavoritos> listaImovelFavoritos);
	
	List<ImovelfavoritosForm> recuperarUsuariosInteressadosNovos(List<ImovelfavoritosForm> lista);
	
	boolean atualizarStatusUsuarioInteressado(Long idImovelfavorito);
	
	List<Imovelfavoritos> recuperarUsuariosInteressadosPorIdImovel(Long idImovel);
	
	long checarQuantidadeUsuariosInteressadosPorIdImovel(Long idImovel);
		
	List<Imovel> checarImoveisMaisInteressadosPorPeriodo(AdministracaoForm form);
	
	String checarUsuarioEstaInteressadoImovel(Long idUsuario, Long idImovel);
	
	EmailImovel notificarInteresseImovel(Imovel imovel);
	
	EmailImovel notificarInteresseImovelFrm(ImovelForm frm);
	
	Imovelfavoritos recuperarUltimoUsuarioInteressadoPorIdImovel(Long idImovel);

	void adicionarFavoritosPorUsuario(Long idUsuario, Long idImovel);

	List<Imovelfavoritos> filtrarImoveisInteresse(Long idUsuario, ImovelfavoritosForm form);

	List<Imovelfavoritos> filtrarUsuariosInteressado(Long idUsuario, ImovelfavoritosForm form);

	//List<Imovelfavoritos> ordenarImoveisFavoritos(Long idUsuario, String opcaoOrdenacao, String tipoLista);
	
	List<Imovelfavoritos> ordenarImoveisFavoritos(Long idUsuario, ImovelfavoritosForm form);
	
	List<Imovel> agruparImoveis(Long idUsuario, ImovelfavoritosForm form);

	List<Usuario> agruparUsuarios(Long idUsuario, ImovelfavoritosForm form);

	List<Imovel> recuperarImoveisInteressadosPorIdUsuarioPorDonoImovel(Long idUsuario, Long idDonoImovel);

	List<Usuario> filtrarAgruparUsuario(Long idUsuario, ImovelfavoritosForm form);

	List<Imovel> filtrarAgruparImoveis(Long idUsuario, ImovelfavoritosForm form);

	List<?> ordenarAgruparImoveisFavoritos(Long idUsuario, ImovelfavoritosForm form);

	void retirarFavoritosPorUsuario(Long idUsuario, Long idImovel);

	void retirarFavoritosPorIdImovelfavoritos(Long idImovelfavoritos);

	long checarQuantidadeUsuariosInteressadosPorUsuario(Long idUsuario, String status);

	void atualizarStatusLeitura(Long idUsuario);

	List<Imovelfavoritos> recuperarUsuariosInteressadosPorIdImovelPorQuant(Long idImovel, int quantMaxLista);			
}
