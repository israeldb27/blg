package com.busqueumlugar.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.PossivelCompradorDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.ChanceCompraEnum;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.PossivelComprador;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.PossivelCompradorService;

@Service
public class PossivelCompradorServiceImpl implements PossivelCompradorService {
	
	@Autowired
	private PossivelCompradorDao dao;

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ImovelDao imovelDao;
	
	
	@Override
	public PossivelComprador recuperarPossivelCompradorPorId(Long id) {
		return dao.findPossivelCompradorById(id);
	}

	@Override
	public void cadastrarPossivelComprador(Long idUsuario, Long idImovel) {
		Usuario usuario = usuarioDao.findUsuario(idUsuario);
		Imovel imovel = imovelDao.findImovelById(idImovel);
		
		PossivelComprador possivel = new PossivelComprador();
		possivel.setDataCadastro(new Date());
		possivel.setDataUltimaAtualizacao(new Date());
		possivel.setImovel(imovel);
		possivel.setUsuarioComprador(usuario);
		possivel.setChanceCompra(ChanceCompraEnum.MA.getIdentificador());
		dao.save(possivel);
	}

	@Override
	public List<PossivelComprador> recuperarListaPossivelCompradorPorIdImovel(Long idImovel) {	
		return dao.findPossivelCompradorByIdImovel(idImovel);
	}

	@Override
	public boolean checarUsuarioPossivelCompradorImovel(Long idUsuario,	Long idImovel) {
		PossivelComprador possivel = dao.findPossivelCompradorByIdUsuarioByIdImovel(idUsuario, idImovel);
		if ( possivel != null)
			return true;
		else
			return false;
	}

	@Override
	public void editarPossivelComprador(Long id, String chanceCompra, String observacao) {
		PossivelComprador possivel = dao.findPossivelCompradorById(id);
		possivel.setDataUltimaAtualizacao(new Date());
		possivel.setChanceCompra(chanceCompra);
		possivel.setObservacao(observacao);
		dao.update(possivel);
	}

}
