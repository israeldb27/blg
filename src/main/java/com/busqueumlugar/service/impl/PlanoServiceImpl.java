package com.busqueumlugar.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.dao.PlanoDao;
import com.busqueumlugar.dao.PlanousuarioDao;
import com.busqueumlugar.form.PlanoForm;
import com.busqueumlugar.model.Plano;
import com.busqueumlugar.service.PlanoService;
import com.busqueumlugar.util.MessageUtils;
import com.mysql.jdbc.StringUtils;

@Service
public class PlanoServiceImpl implements PlanoService {
	
	private static final Logger log = LoggerFactory.getLogger(PlanoServiceImpl.class);
	
	@Autowired(required = true)
	private PlanoDao dao;

	@Autowired(required = true)
	private PlanousuarioDao planoUsuarioDao;
	

	public Plano recuperarPlanoPorId(Long id) {
		return dao.findPlanoById(id);
	}

	
	@Transactional
	public void cadastrarNovoPlano(PlanoForm planoForm) {		
		Plano plano = new Plano();
        BeanUtils.copyProperties(planoForm, plano );
        dao.save(plano);
	}

	
	
	public List<Plano> listarTodosDisponiveis() {		
		return dao.listAll();
	}

	
	@Transactional
	public void excluirPlano(Long idPlano) {		
		dao.delete(dao.findPlanoById(idPlano));
	}

	
	public PlanoForm preparaPlanoAdicionarServicos(Plano plano) {		
		PlanoForm planoForm = new PlanoForm();
        BeanUtils.copyProperties(planoForm, plano);
        return planoForm;
	}

	
	public PlanoForm preparaPlanoAdicionarRelatorio(Plano plano) {		
		PlanoForm planoForm = new PlanoForm();
        BeanUtils.copyProperties(planoForm, plano);
        return planoForm;
	}

	
	public String validarCadastroPlano(PlanoForm planoForm) {		
		 String msg = "";        
	        try {
	            Plano plano = dao.findPlanoPorNome(planoForm.getNome().trim());
	            if ( plano != null && plano.getId().intValue() > 0 )
	                msg = MessageUtils.getMessage("msg.erro.cadastro.plano.existente");        
	            
	        } catch (Exception e) {
	            msg = MessageUtils.getMessage("erro.generico.cadastro.plano.existente");
	            e.printStackTrace();;  
	        } 
	        return msg;
	}

	
	public Plano recuperarPlanoPorId(long idPlano) {		
		return dao.findPlanoById(idPlano);
	}

	
	@Override
	public void atualizarPlano(PlanoForm planoForm) {
		Plano plano = new Plano();
        BeanUtils.copyProperties(planoForm, plano);
        dao.save(plano);		
	}
	
	@Override
	public List<Plano> pesquisarTodosPlanos(String valor){
		return dao.findPlanoByValor(valor);
	}


	@Override
	public boolean validarCadastroPlano(PlanoForm form, BindingResult result) {
		boolean valido = true;
		
		if (StringUtils.isNullOrEmpty(form.getNome())){
			valido = false;
			result.rejectValue("nome", "msg.erro.campo.obrigatorio");
		}
		
		if (form.getDuracaoPlano() == null || form.getDuracaoPlano().intValue() <= 0){
			valido = false;
			result.rejectValue("duracaoPlano", "msg.erro.valor.quantidade.zero");			
		}
		
		if (form.getValorPlano() <= 0){
			valido = false;
			result.rejectValue("valorPlano", "msg.erro.valor.quantidade.zero");
		}
		
		if (form.getQuantCadastroImoveis() == null || form.getQuantCadastroImoveis() <= 0){
			valido = false;
			result.rejectValue("quantCadastroImoveis", "msg.erro.valor.quantidade.zero");
		}
				
		if (form.getQuantFotos() == null || form.getQuantFotos() <= 0){
			valido = false;
			result.rejectValue("quantFotos", "msg.erro.valor.quantidade.zero");
		}
		
		if (form.getQuantImoveisIndicacao() == null || form.getQuantImoveisIndicacao() <= 0){
			valido = false;
			result.rejectValue("quantImoveisIndicacao", "msg.erro.valor.quantidade.zero");
		}
		
		if (form.getQuantEmailsPorImovel() == null || form.getQuantEmailsPorImovel() <= 0){
			valido = false;
			result.rejectValue("quantEmailsPorImovel", "msg.erro.valor.quantidade.zero");
		}
		
		if (StringUtils.isNullOrEmpty(form.getHabilitaEnvioMensagens())){
			valido = false;
			result.rejectValue("habilitaEnvioMensagens", "msg.erro.campo.obrigatorio");
		}

		return valido;
	}


	@Override
	public boolean validarEdicaoPlano(PlanoForm form, BindingResult result) {
		boolean valido = true;
		
		if (StringUtils.isNullOrEmpty(form.getNome())){
			valido = false;
			result.rejectValue("nome", "msg.erro.campo.obrigatorio");
		}
		
		if (form.getDuracaoPlano() == null || form.getDuracaoPlano().intValue() <= 0){
			valido = false;
			result.rejectValue("duracaoPlano", "msg.erro.valor.quantidade.zero");			
		}
		
		if (form.getValorPlano() <= 0){
			valido = false;
			result.rejectValue("valorPlano", "msg.erro.valor.quantidade.zero");
		}
		
		if (form.getQuantCadastroImoveis() == null || form.getQuantCadastroImoveis() <= 0){
			valido = false;
			result.rejectValue("quantCadastroImoveis", "msg.erro.valor.quantidade.zero");
		}
				
		if (form.getQuantFotos() == null || form.getQuantFotos() <= 0){
			valido = false;
			result.rejectValue("quantFotos", "msg.erro.valor.quantidade.zero");
		}
		
		if (form.getQuantImoveisIndicacao() == null || form.getQuantImoveisIndicacao() <= 0){
			valido = false;
			result.rejectValue("quantImoveisIndicacao", "msg.erro.valor.quantidade.zero");
		}
		
		if (form.getQuantEmailsPorImovel() == null || form.getQuantEmailsPorImovel() <= 0){
			valido = false;
			result.rejectValue("quantEmailsPorImovel", "msg.erro.valor.quantidade.zero");
		}
		
		if (StringUtils.isNullOrEmpty(form.getHabilitaEnvioMensagens())){
			valido = false;
			result.rejectValue("habilitaEnvioMensagens", "msg.erro.campo.obrigatorio");
		}

		return valido;
	}


	@Override
	public PlanoForm carregaPlanoFormPorIdPlano(Long idPlano) {
		Plano plano = dao.findPlanoById(idPlano);
		PlanoForm form = new PlanoForm();
		BeanUtils.copyProperties(plano, form);
		return form;
	}

}
