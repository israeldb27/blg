package com.busqueumlugar.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.AtividadesDao;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Atividades;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Usuario;
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
	public void cadastrarAtividade(ImovelForm form, String statusAtividade,	String novaDescricaoAtividade, UsuarioForm user) {
		Atividades atividades = new Atividades();
		atividades.setDataAtividade(new Date());
		atividades.setDataUltimaAtualizacao(new Date());
		atividades.setStatus(statusAtividade);
		atividades.setDescricao(novaDescricaoAtividade);
		Imovel imovel = new Imovel();
		BeanUtils.copyProperties(form, imovel);  
		atividades.setImovel(imovel);
		Usuario usuario = new Usuario();
		BeanUtils.copyProperties(user, usuario);  
		atividades.setUsuario(usuario);
		dao.save(atividades);
	}

	@Override
	public void excluirAtividade(Long idAtividade) {
		dao.delete(Atividades.class, idAtividade);		
	}

	@Override
	public void editarAtividade(ImovelForm form, Long idAtividade,	String novaAtividade, String novaDescricaoAtividade, UsuarioForm user) {
		Atividades atividades = dao.findAtividadesById(idAtividade);
		atividades.setDataUltimaAtualizacao(new Date());
		atividades.setStatus(novaAtividade);
		atividades.setDescricao(novaDescricaoAtividade);
		dao.update(atividades);
	}

	@Override
	public List<Atividades> recuperarAtividadesPorIdImovelPorQuant(	Long idImovel, int quantMaxLista) {
		return dao.findAtividadesByIdImovelByQuant(idImovel, quantMaxLista );
	}

}
