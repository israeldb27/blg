package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.dao.ItemMensagemAdminDao;
import com.busqueumlugar.dao.MensagemAdminDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.form.MensagemAdminForm;
import com.busqueumlugar.model.ItemMensagemAdmin;
import com.busqueumlugar.model.MensagemAdmin;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ItemMensagemAdminService;
import com.busqueumlugar.service.MensagemAdminService;
import com.busqueumlugar.service.UsuarioService;
import com.mysql.jdbc.StringUtils;


@Service
public class ItemMensagemAdminServiceImpl implements ItemMensagemAdminService{
	
	private static final Logger log = LoggerFactory.getLogger(ItemMensagemAdminServiceImpl.class);
	
	@Autowired
	private ItemMensagemAdminDao dao;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private MensagemAdminService mensagemAdminService;
	
	@Autowired
	private MensagemAdminDao mensagemAdminDao;

	@Override
	public List<ItemMensagemAdmin> checarNovasMensagemAdminPorStatusLeitura(Long idUsuario, String status) {		
		return dao.findItemMensagemAdminByIdUsuarioByStatusLeitura(idUsuario, status);
	}

	@Override
	public int recuperarQuantNovasMensagensParaUsuarioPorIdMensagemAdmin(Long idMensagemAdmin) {
		return dao.findQuantNovasItensMensagensByIdMensagemAdmin(idMensagemAdmin);
	}

	@Override
	@Transactional
	public void cadastrarItemMensagem(ItemMensagemAdmin itemMensagem) {
		dao.save(itemMensagem);
		
	}

	@Override
	public List<ItemMensagemAdmin> recuperaItemMensagensPorIdMensagemAdmin(Long idMensagemAdmin) {
		return dao.findItemMensagensAdminByIdMensagemAdmin(idMensagemAdmin); 
	}

	@Override
	@Transactional
	public void adicionarNovoItemMensagemAdmin(MensagemAdminForm form, Long idUsuario) {
		
		MensagemAdmin mensagem = mensagemAdminService.recuperarMensagemAdminPorId(form.getId());
		mensagem.setDataUltimaMensagem(new Date());
		mensagem.setDescricaoUltimaMensagem(form.getNovaMensagem());
		mensagemAdminService.atualizarMensagemAdmin(mensagem);
		
		ItemMensagemAdmin itemMensagem = new ItemMensagemAdmin();
		itemMensagem.setDataMensagem(mensagem.getDataUltimaMensagem());
		itemMensagem.setDescricao(form.getNovaMensagem());
		itemMensagem.setUsuario(mensagem.getUsuario());
		itemMensagem.setRemetenteAdmin("N");
		itemMensagem.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
		itemMensagem.setMensagemAdmin(mensagem);		
		dao.save(itemMensagem);
	}

	@Override
	public List<ItemMensagemAdmin> recuperaItemMensagensPorIdMensagemAdminParaAdministrador(Long idMensagemAdmin) {		
		return dao.findItemMensagensAdminByIdMensagemAdmin(idMensagemAdmin);
	}

	@Override
	@Transactional
	public void adicionarNovoItemMensagemAdminPorAdministrador(	MensagemAdminForm form) {
		
		MensagemAdmin mensagem = mensagemAdminDao.findMensagemAdminById(form.getId());
		mensagem.setDataUltimaMensagem(new Date());
		mensagem.setDescricaoUltimaMensagem(form.getNovaMensagem());		
		mensagemAdminService.atualizarMensagemAdmin(mensagem);		
		
		ItemMensagemAdmin itemMensagem = new ItemMensagemAdmin();
		itemMensagem.setDataMensagem(mensagem.getDataUltimaMensagem());
		itemMensagem.setDescricao(form.getNovaMensagem());
		itemMensagem.setUsuario(mensagem.getUsuario());
		itemMensagem.setRemetenteAdmin("S");
		itemMensagem.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
		itemMensagem.setMensagemAdmin(mensagem);		
		dao.save(itemMensagem);		
	}
	
	@Override
	@Transactional
	public void adicionarNovaMaisMensagemEnviadoPorAdmin(MensagemAdminForm form) {
		
		MensagemAdmin mensagem = mensagemAdminDao.findMensagemAdminById(form.getId());
		mensagem.setDataUltimaMensagem(new Date());
		mensagem.setDescricaoUltimaMensagem(form.getNovaMensagem());
		mensagemAdminService.atualizarMensagemAdmin(mensagem);
		
		ItemMensagemAdmin itemMensagem = new ItemMensagemAdmin();
		itemMensagem.setDataMensagem(mensagem.getDataUltimaMensagem());
		itemMensagem.setDescricao(form.getNovaMensagem());
		itemMensagem.setUsuario(mensagem.getUsuario());
		itemMensagem.setRemetenteAdmin("S");
		itemMensagem.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
		itemMensagem.setMensagemAdmin(mensagem);		
		dao.save(itemMensagem);		
	}

	@Override
	public int checarQuantidadeNovasMensagensFromAdmin(Long idUsuario) {
		return dao.findItemMensagensNovasByIdUsuario(idUsuario);
	}

	@Override
	public void adicionarNovoItemMensagemAdminEnviadoPorAdministrador(MensagemAdminForm form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validarAdicionarNovoItemMensagemAdminEnviadoPorAdministrador(MensagemAdminForm form, BindingResult result) {
		
		boolean valido = true;
		if (StringUtils.isNullOrEmpty(form.getNovaMensagem())){
			valido = false;
			result.rejectValue("novaMensagem", "msg.erro.campo.obrigatorio");
		}

		return valido;
	}

	@Override
	public boolean validarAdicionarNovoItemMensagemAdmin(MensagemAdminForm form, BindingResult result) {
		boolean valido = true;
		if (StringUtils.isNullOrEmpty(form.getNovaMensagem())){
			valido = false;
			result.rejectValue("novaMensagem", "msg.erro.campo.obrigatorio");
		}
		return valido;
	}

	@Override
	public List<ItemMensagemAdmin> recuperarQuantidadesNovasMensagensEnviadasParaAdmin() {
		return dao.findItemMensagensNovasEnviadasParaAdmin();
	}

	@Override
	@Transactional
	public void atualizarStatusLeituraItemMensagensPorIdMensagemAdminParaAdministrador(Long idMensagemAdmin) {
		List<ItemMensagemAdmin> lista = dao.findItemMensagensAdminByIdMensagemAdmin(idMensagemAdmin);
		for (ItemMensagemAdmin item : lista){
			if ( item.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo()) && item.getRemetenteAdmin().equals("N")){
				item.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
				dao.save(item);
			}
		}
		
	}

	@Override
	public String checarExisteNovoItemMensagemByIdMensagem(Long idMensagemAdmin) {
		List<ItemMensagemAdmin> lista = dao.findItemMensagensAdminNovoByIdMensagemAdmin(idMensagemAdmin);
		if (CollectionUtils.isEmpty(lista))
			return "N";
		else
			return "S";
	}

	@Override
	@Transactional
	public void atualizarItemMensagensPorId(Long idMensagemAdmin) {
		List<ItemMensagemAdmin> lista = dao.findItemMensagensAdminByIdMensagemAdmin(idMensagemAdmin);
		for (ItemMensagemAdmin item : lista){
			if ( item.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo()) && item.getRemetenteAdmin().equals("S")){
				item.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
				dao.save(item);
			}
		}
		
	}

	@Override
	public List<ItemMensagemAdmin> recuperarItemMensagemAdminPorIdUsuarioStatusLeitura(Long idUsuario, String status) {
		return dao.findItemMensagemAdminByStatusLeituraByIdUsuario(idUsuario, status);
	}

	@Override
	public int checarQuantidadeMensagensAdminPorStatusLeitura(Long idUsuario, String statusLeitura) {
		return dao.findQuantItemMensagemAdminByIdUsuarioByStatusLeitura(idUsuario, statusLeitura);
	}	
}
