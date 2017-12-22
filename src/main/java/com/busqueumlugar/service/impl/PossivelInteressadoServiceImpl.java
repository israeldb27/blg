package com.busqueumlugar.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.PossivelInteressadoDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.chanceInteresseEnum;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.PossivelInteressado;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.PossivelInteressadoService;

@Service
public class PossivelInteressadoServiceImpl implements PossivelInteressadoService {
	
	@Autowired
	private PossivelInteressadoDao dao;

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ImovelDao imovelDao;
	
	
	@Override
	public PossivelInteressado recuperarPossivelInteressadoPorId(Long id) {
		return dao.findPossivelInteressadoById(id);
	}

	@Override
	public void cadastrarPossivelInteressado(Long idUsuario, Long idImovel) {
		Usuario usuario = usuarioDao.findUsuario(idUsuario);
		Imovel imovel = imovelDao.findImovelById(idImovel);
		
		PossivelInteressado possivel = new PossivelInteressado();
		possivel.setDataCadastro(new Date());
		possivel.setDataUltimaAtualizacao(new Date());
		possivel.setImovel(imovel);
		possivel.setUsuarioInteressado(usuario);
		possivel.setchanceInteresse(chanceInteresseEnum.MA.getIdentificador());
		dao.save(possivel);
	}

	@Override
	public List<PossivelInteressado> recuperarListaPossivelInteressadoPorIdImovel(Long idImovel) {	
		return dao.findPossivelInteressadoByIdImovel(idImovel);
	}

	@Override
	public boolean checarUsuarioPossivelInteressadoImovel(Long idUsuario,	Long idImovel) {
		PossivelInteressado possivel = dao.findPossivelInteressadoByIdUsuarioByIdImovel(idUsuario, idImovel);
		if ( possivel != null)
			return true;
		else
			return false;
	}

	@Override
	public void editarPossivelInteressado(Long id, String chanceInteresse, String observacao) {
		PossivelInteressado possivel = dao.findPossivelInteressadoById(id);
		possivel.setDataUltimaAtualizacao(new Date());
		possivel.setChanceInteresse(chanceInteresse);
		possivel.setObservacao(observacao);
		dao.update(possivel);
	}

	@Override
	public List<PossivelInteressado> recuperarListaPossivelInteressadoPorIdImovelPorQuant(Long idImovel, int quantMaxLista) {
		return dao.findPossivelInteressadoByIdImovelByQuant(idImovel, quantMaxLista);
	}

}
