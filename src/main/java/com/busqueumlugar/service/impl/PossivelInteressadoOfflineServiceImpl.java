package com.busqueumlugar.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.PossivelInteressadoOfflineDao;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Parceria;
import com.busqueumlugar.model.PossivelInteressadoOffline;
import com.busqueumlugar.service.PossivelInteressadoOfflineService;

@Service
public class PossivelInteressadoOfflineServiceImpl implements	PossivelInteressadoOfflineService {
	
	private static final Logger log = LoggerFactory.getLogger(PossivelInteressadoOfflineServiceImpl.class);
	
	@Autowired
	private PossivelInteressadoOfflineDao dao;

	@Override
	public PossivelInteressadoOffline recuperarPossivelInteressadoOfflinePorId(	Long id) {
		return dao.findPossivelInteressadoOfflineById(id);
	}

	@Override
	public List<PossivelInteressadoOffline> recuperarListaPossivelInteressadoOfflinePorIdImovel(Long idImovel) {	
		return dao.findPossivelInteressadoOfflineByIdImovel(idImovel);
	}

	@Override
	public void cadastrarPossivelInteressadoOffline(ImovelForm form, String nome, String telefone, String email, String chanceInteresse, String observacao) {
		PossivelInteressadoOffline possivel = new PossivelInteressadoOffline();
		possivel.setDataCadastro(new Date());
		possivel.setDataUltimaAtualizacao(new Date());
		possivel.setEmailInteressado(email);
		possivel.setNomeInteressado(nome);
		possivel.setTelefoneInteressado(telefone);
		possivel.setChanceInteresse(chanceInteresse);
		possivel.setObservacao(observacao);
		Imovel imovel = new Imovel();
		BeanUtils.copyProperties(form, imovel); 
		possivel.setImovel(imovel);
		dao.save(possivel);
	}

	@Override
	public void excluirPossivelInteressadoOffline(Long id) {
		dao.delete(PossivelInteressadoOffline.class, id);
	}

	@Override
	public void editarPossivelInteressadoOffline(Long id, String nome,String telefone, String email, String chanceInteresse, String observacao) {
		PossivelInteressadoOffline possivel = dao.findPossivelInteressadoOfflineById(id);
		possivel.setDataUltimaAtualizacao(new Date());
		possivel.setEmailInteressado(email);
		possivel.setNomeInteressado(nome);
		possivel.setTelefoneInteressado(telefone);
		possivel.setChanceInteresse(chanceInteresse);
		possivel.setObservacao(observacao);
		dao.update(possivel);
	}

	@Override
	public List<PossivelInteressadoOffline> recuperarListaPossivelInteressadoOfflinePorIdImovelPorQuant(Long idImovel, int quantMaxLista) {
		return dao.findPossivelInteressadoOfflineByIdImovelByQuant(idImovel, quantMaxLista);
	}
	
	

}
