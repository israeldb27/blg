package com.busqueumlugar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.AtividadesDao;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Atividades;
import com.busqueumlugar.service.AtividadesService;

@Service
public class AtividadesServiceImpl implements AtividadesService{
	
	@Autowired
	private AtividadesDao dao;

	@Override
	public Atividades recuperarAtividadePorId(Long id) {
		return dao.findAtividadesById(id);
	}

	@Override
	public List<Atividades> recuperarAtividadesPorIdImovel(Long idImovel) {
		return dao.findAtividadesByIdImovel(idImovel);
	}

	@Override
	public List<Atividades> recuperarAtividadesPorIdImovelPorIdUsuario(Long idImovel, Long idUsuario) {
		return dao.findAtividadesByIdImovelByIdUsuario(idImovel, idUsuario);
	}

	@Override
	public void cadastrarAtividade(ImovelForm form, String novaAtividade,	String novaDescricaoAtividade, UsuarioForm user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluirAtividade(Long idAtividade) {
		// TODO Auto-generated method stub
		
	}

}
