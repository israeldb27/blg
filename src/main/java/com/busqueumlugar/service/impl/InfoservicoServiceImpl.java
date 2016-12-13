package com.busqueumlugar.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import org.springframework.transaction.annotation.Transactional;

import com.busqueumlugar.dao.InfoservicoDao;
import com.busqueumlugar.form.InfoservicoForm;
import com.busqueumlugar.model.Infoservico;
import com.busqueumlugar.model.Paramservico;
import com.busqueumlugar.service.InfoservicoService;
import com.busqueumlugar.service.ParamservicoService;

@Service
public class InfoservicoServiceImpl implements InfoservicoService {
	
	private static final Logger log = LoggerFactory.getLogger(InfoservicoServiceImpl.class);
	
	@Autowired
	private InfoservicoDao dao;

	@Autowired
	private ParamservicoService paramservicoService;
	

	public Infoservico recuperarInfoservicoPorId(Long id) {
		return dao.findInfoservicoById(id);
	}


	
	public List<Infoservico> recuperaDetalhesParamServico(Long idParametro) {		
		return dao.findInfoServicoByIdParametroServico(idParametro);
	}


	@Transactional
	public void cadastrarInfoServico(InfoservicoForm infoservicoForm) {		
		Infoservico i = new Infoservico();         
        infoservicoForm.setItemInfoServico(String.valueOf(infoservicoForm.getValorServico()));
        BeanUtils.copyProperties(i, infoservicoForm);        
        Paramservico paramservico = paramservicoService.recuperarParamServicoPorId(infoservicoForm.getIdParamServico());
        i.setTipoInfoServico(paramservico.getTipoParamServico());
        dao.save(i);
	}


	@Transactional
	public void excluirInfoServico(Long idInfoServico) {
		Infoservico i = dao.findInfoservicoById(idInfoServico);
		dao.delete(i);	
	}


	@Transactional
	public void atualizarInfoServico(Infoservico infoServicoSelecionado) {		
		dao.save(infoServicoSelecionado);
	}


	
	public List<Infoservico> filtrarDetalhesParamServico(Long idParam) {		
		return dao.findInfoServicoByIdParametroServico(idParam);
	}


	
	public Infoservico recuperarInfoServico(Long idParam, String opcaoTempoPagto, String formaPagto) {		
		return dao.findInfoservicoByIdParamByOpcaoTempoByFormaPagto(idParam, opcaoTempoPagto);
	}


	
	public Infoservico recuperarInfoServico(Long idParam, String opcaoTempoPagto) {		
		return dao.findInfoservicoByIdParamByOpcaoTempoByFormaPagto(idParam, opcaoTempoPagto);
	}


	
	public Infoservico recuperarInfoServicoAdicionarFoto(Long idParam,String opcaoQuantFoto) {		
		return dao.findInfoservicoByIdParamByOpcaoTempoByFormaPagto(idParam, opcaoQuantFoto);
	}


	
	public List<Infoservico> recuperarInfoServicoPagamentoPorNomeServico(String nome) {
		Paramservico paramServico = paramservicoService.recuperarParamServicoPorNome2(nome);
        return dao.findInfoServicoByIdParametroServico(paramServico.getId());
	}


	
	public Infoservico recuperarInfoServicoPorId(long idInfoServico) {		
		return dao.findInfoservicoById(idInfoServico);
	}

}
