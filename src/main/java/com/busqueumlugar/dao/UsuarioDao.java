package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Usuario;

public interface UsuarioDao extends GenericDAO<Usuario, Long> {

	List<Usuario> listaUsuarios();
	
	List<Usuario> findUsuarios(UsuarioForm form);
	
	List<Usuario> findUsuarios(AdministracaoForm form);
	
	Usuario findUsuarioByCampo(UsuarioForm form, String nomeCampo);
	
	List<Usuario> findUsuariosByCampo(UsuarioForm form, String nomeCampo, boolean isAdmin);	
	
	Usuario findUsuario(Long idUsuario);	
	
	Usuario findUsuarioByCodigoIdentificacao(String codigo);
	
	void updateStatusAtivadoByIdUsuario(Long idUsuario);
	
	List recuperarUsuariosComMaisCompartilhamentoAceitos(RelatorioForm form, String tipoCompart);

	List<Usuario> findUsuariosByDataCadastro(AdministracaoForm form);

	List<Usuario> findUsuariosByDataVisita(AdministracaoForm form);
	
	List<Usuario> findUsuarioByValorAdmin(String valor);

	Usuario findUsuarioByUsuarioByIndex(List<Long> listaIds, UsuarioForm user, int index);

	long findQuantidadeTotalUsuarioPorPeriodo(AdministracaoForm form);

	long findQuantidadeUsuariosByDataVisita(AdministracaoForm form);

	List findUsuariosImoveisMaisVisualizados(RelatorioForm form);

	List findUsuariosImoveisMaisFavoritos(RelatorioForm form);

	List findUsuariosImoveisMaisPropostas(RelatorioForm form);		
}
