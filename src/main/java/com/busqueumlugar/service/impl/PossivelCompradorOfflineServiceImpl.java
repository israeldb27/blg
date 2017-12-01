package com.busqueumlugar.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.PossivelCompradorOfflineDao;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.PossivelCompradorOffline;
import com.busqueumlugar.service.PossivelCompradorOfflineService;

@Service
public class PossivelCompradorOfflineServiceImpl implements	PossivelCompradorOfflineService {
	
	private static final Logger log = LoggerFactory.getLogger(PossivelCompradorOfflineServiceImpl.class);
	
	@Autowired
	private PossivelCompradorOfflineDao dao;

	@Override
	public PossivelCompradorOffline recuperarPossivelCompradorOfflinePorId(	Long id) {
		return dao.findPossivelCompradorOfflineById(id);
	}

	@Override
	public List<PossivelCompradorOffline> recuperarListaPossivelCompradorOfflinePorIdImovel(Long idImovel) {	
		return dao.findPossivelCompradorOfflineByIdImovel(idImovel);
	}

	@Override
	public void cadastrarPossivelCompradorOffline(ImovelForm form, String nome,	String telefone, String email, String chanceCompra, String observacao) {
		PossivelCompradorOffline possivel = new PossivelCompradorOffline();
		possivel.setDataCadastro(new Date());
		possivel.setDataUltimaAtualizacao(new Date());
		possivel.setEmailComprador(email);
		possivel.setNomeComprador(nome);
		possivel.setTelefoneComprador(telefone);
		possivel.setChanceCompra(chanceCompra);
		possivel.setObservacao(observacao);
		Imovel imovel = new Imovel();
		BeanUtils.copyProperties(form, imovel); 
		possivel.setImovel(imovel);
		dao.save(possivel);
	}

}
