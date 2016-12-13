package com.busqueumlugar.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.busqueumlugar.form.NotaForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Nota;
import com.busqueumlugar.model.Usuario;

public interface NotaService {
	
	Nota recuperarNotaPorId(Long id);
	
	void cadastrarNota(String descricao, Usuario usuario, Imovel imovel, Date dataAtual, String acao);
	
	void cadastrarNota(String descricao, Long idUsuario, Date dataAtual, String acao);
	
	void cadastrarNota(String descricao, Usuario usuario, Date dataAtual, String acao);
	
	void cadastrarNotaPreferenciaLocalidade(String descricao, Long idUsuario, long idPrefLocalidade, Date dataAtual, String acao);
	
	List<Nota> recuperarNotasContatosUsuario(Long idUsuario, NotaForm form);
	
	List<Nota> recuperarNotasContatosUsuario(Long idUsuario, int quant);
	
	List<Nota> listarTodasNotasPorPerfil(Long idUsuario, NotaForm form);
	
	void excluirNotasImovel(Long idImovel);

	public List<Nota> ordenarNotas(String tipoLista, Long idUsuario, String nomeUsuario, NotaForm form);
	
	List<Nota> filtrarMinhasNotas(Long idUsuario, NotaForm form);

	List<Nota> filtrarNotasContatos(Long idUsuario, NotaForm form);

	Nota recuperarNotaByUsuarioByIndex(List<Long> listaIds, int index);

	
	
	

}
