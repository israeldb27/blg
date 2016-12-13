package com.busqueumlugar.service.impl;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.dao.ParametrosIniciaisDao;
import com.busqueumlugar.form.ParametrosIniciaisForm;
import com.busqueumlugar.model.ParametrosIniciais;
import com.busqueumlugar.service.ParametrosIniciaisService;
import com.mysql.jdbc.StringUtils;

@Service
public class ParametrosIniciaisServiceImpl implements ParametrosIniciaisService {
	
	private static final Logger log = LoggerFactory.getLogger(ParametrosIniciaisServiceImpl.class);
	
	@Autowired
	private ParametrosIniciaisDao dao;

	@Override
	public ParametrosIniciais recuperarParamservicoPorId(Long id) {
		return dao.findParametrosIniciaisById(id);
	}

	@Override
	@Transactional
	public void cadastrarParametroInicial(ParametrosIniciaisForm form, Long idUsuarioAdmin) {
		ParametrosIniciais param = new ParametrosIniciais();
		BeanUtils.copyProperties(form, param);
		param.setDataCadastro(new Date());
		param.setDataUltimaAtualizacao(new Date());
		param.setIdUsuarioCadastro(idUsuarioAdmin);
		dao.save(param);
	}

	@Override
	public List<ParametrosIniciais> listarTodosParametrosIniciais() {
		return dao.listAll();
	}

	@Override
	@Transactional
	public void excluirParametroInicial(Long idParametro) {
		ParametrosIniciais param = dao.findParametrosIniciaisById(idParametro);
		dao.delete(param);		
	}

	@Override
	public Hashtable carregarParametrosIniciais() {
		Hashtable map = new Hashtable();
		List<ParametrosIniciais> lista = dao.listAll();
		for (ParametrosIniciais param : lista){
			if (param.getTipoParam().equals("N"))
				map.put(param.getLabel(), String.valueOf(param.getValorQuantidade()));
			else
				map.put(param.getLabel(), param.getValorHabilita());
		}
		return map;
	}

	@Override
	public String findParametroInicialPorNome(String nomeParametro) {
		ParametrosIniciais param = dao.findParametrosIniciaisByNome(nomeParametro);
		return param.getValorHabilita();
	}

	@Override
	public boolean validarCadastroParametrosIniciais( ParametrosIniciaisForm form, BindingResult result) {
		boolean valido = true;		
		
		if (StringUtils.isNullOrEmpty(form.getNome())){
			valido = false;
			result.rejectValue("nome", "msg.erro.campo.obrigatorio");
		}
		
		if (StringUtils.isNullOrEmpty(form.getLabel())){
			valido = false;
			result.rejectValue("label", "msg.erro.campo.obrigatorio");
		}
		
		if (StringUtils.isNullOrEmpty(form.getTipoParam())){
			valido = false;
			result.rejectValue("tipoParam", "msg.erro.campo.obrigatorio");
		}
		else {
			if ( form.getTipoParam().equals("N")){
				if ( form.getValorQuantidade().intValue() <= 0 ){
					valido = false;
					result.rejectValue("valorQuantidade", "msg.erro.valor.quantidade.zero");
					
				}
			}
			else if ( form.getTipoParam().equals("C")){
				if (StringUtils.isNullOrEmpty(form.getValorHabilita())){
					valido = false;
					result.rejectValue("valorHabilita", "msg.erro.campo.obrigatorio");
				}
			}
		}
		
		return valido;
	}

	@Override
	public boolean validarEdicaoParametrosIniciais(ParametrosIniciaisForm form, BindingResult result) {

		boolean valido = true;		
		
		if (StringUtils.isNullOrEmpty(form.getNome())){
			valido = false;
			result.rejectValue("nome", "msg.erro.campo.obrigatorio");
		}
		
		if (StringUtils.isNullOrEmpty(form.getLabel())){
			valido = false;
			result.rejectValue("label", "msg.erro.campo.obrigatorio");
		}
		
		if (StringUtils.isNullOrEmpty(form.getTipoParam())){
			valido = false;
			result.rejectValue("tipoParam", "msg.erro.campo.obrigatorio");
		}
		else {
			if ( form.getTipoParam().equals("N")){
				if ( form.getValorQuantidade().intValue() <= 0 ){
					valido = false;
					result.rejectValue("valorQuantidade", "msg.erro.valor.quantidade.zero");
					
				}
			}
			else if ( form.getTipoParam().equals("C")){
				if (StringUtils.isNullOrEmpty(form.getValorHabilita())){
					valido = false;
					result.rejectValue("valorHabilita", "msg.erro.campo.obrigatorio");
				}
			}
		}		
		return valido;	
	}

	@Override
	public ParametrosIniciaisForm carregarParametrosIniciaisPorIdParametro( Long idParametro) {
		ParametrosIniciaisForm form  = new ParametrosIniciaisForm();
		ParametrosIniciais param = dao.findParametrosIniciaisById(idParametro);
		BeanUtils.copyProperties(param, form);
		return form;
	}

	@Override
	public void editarParametroInicial(ParametrosIniciaisForm form, Long idUsuarioAdmin) {
		ParametrosIniciais param = new ParametrosIniciais();
		BeanUtils.copyProperties(form, param);
		param.setDataCadastro(new Date());
		//param.setDataUltimaAtualizacao(new Date());
		param.setIdUsuarioCadastro(idUsuarioAdmin);
		dao.save(param);	
	}

}
