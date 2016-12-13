package com.busqueumlugar.dao;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.RelatorioQuantPlano;

public interface PlanousuarioDao extends GenericDAO<Planousuario, Long> {
	
	Planousuario findPlanousuarioById(Long id);
	
	List<Planousuario> findPlanoUsuarioByIdUsuario(Long idUsuario);
	
	List<RelatorioQuantPlano> findPlanosByNomeUsuario(String buscarUsuario);
	
	List<RelatorioQuantPlano> findPlanosVolFinanceiro(AdministracaoForm form);

	Planousuario findLastPlanoGeradoByIdUsuario(Long idUsuario);
	
	public List findTotalQuantidadePlanos(AdministracaoForm form);

	Planousuario findPlanoUsuarioByIdPlanoByIdUsuario(Long idUsuario, Long idPlanoSelecionado);

	List findPlanosUsuariosVolFinanceiro(AdministracaoForm form);
	

}
