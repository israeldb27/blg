package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.dao.ItemMensagemAdminDao;
import com.busqueumlugar.dao.MensagemAdminDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.form.MensagemAdminForm;
import com.busqueumlugar.form.MensagemForm;
import com.busqueumlugar.model.ItemMensagemAdmin;
import com.busqueumlugar.model.MensagemAdmin;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ItemMensagemAdminService;
import com.busqueumlugar.service.MensagemAdminService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.mysql.jdbc.StringUtils;


@Service
public class MensagemAdminServiceImpl implements MensagemAdminService {
	
	private static final Logger log = LoggerFactory.getLogger(MensagemAdminServiceImpl.class);
	
	@Autowired
	private MensagemAdminDao dao;

	
	@Autowired
	private ItemMensagemAdminService itemMensagemAdminService;
	
	@Autowired
	private ItemMensagemAdminDao itemMensagemAdminDao;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	
	@Override
	public int checarQuantidadeNovasMensagensFromAdmin(Long idUsuario) {
		return itemMensagemAdminService.checarQuantidadeMensagensAdminPorStatusLeitura(idUsuario, StatusLeituraEnum.NOVO.getRotulo());
	}

	 
	 
	 @Override
		public List<MensagemAdmin> recuperaTodasMensagensNovasPorUsuario(Long idUsuario) {			
			List<MensagemAdmin> lista = dao.findMensagemAdminByIdUsuario(idUsuario);
			List<MensagemAdmin> listaFinal = new ArrayList<MensagemAdmin>();		
			int quantNovaMensagens = 0;
			for (MensagemAdmin mensagem : lista){
				quantNovaMensagens = itemMensagemAdminService.recuperarQuantNovasMensagensParaUsuarioPorIdMensagemAdmin(mensagem.getId());
				if ( quantNovaMensagens > 0 ) {
					mensagem.setPossuiNovaMensagem("S");
					listaFinal.add(mensagem);
				}
			}				
			return listaFinal;
		}
	
	@Override
	public List<MensagemAdmin> recuperaTodasMensagensPorUsuario(Long idUsuario) {		
		List<MensagemAdmin> lista = dao.findMensagemAdminByIdUsuario(idUsuario);
		List<MensagemAdmin> listaFinal = new ArrayList<MensagemAdmin>();		
		int quantNovaMensagens = 0;
		for (MensagemAdmin mensagem : lista){
			quantNovaMensagens = itemMensagemAdminService.recuperarQuantNovasMensagensParaUsuarioPorIdMensagemAdmin(mensagem.getId());
			if ( quantNovaMensagens > 0 ) {
				mensagem.setPossuiNovaMensagem("S");			
			}
		}				
		return listaFinal;

	}

	@Override
	@Transactional
	public void criarMensagemParaAdmin(MensagemAdminForm form, Long idUsuario) {

		MensagemAdmin mensagem = new MensagemAdmin();
		BeanUtils.copyProperties(form, mensagem);
		mensagem.setDataUltimaMensagem(new Date());
		mensagem.setUsuario(usuarioDao.findUsuario(idUsuario));
		mensagem.setId(null);
		dao.save(mensagem);
		
		ItemMensagemAdmin itemMensagem = new ItemMensagemAdmin();
		itemMensagem.setDataMensagem(mensagem.getDataUltimaMensagem());
		itemMensagem.setDescricao(form.getDescricaoUltimaMensagem());
		itemMensagem.setUsuario(usuarioDao.findUsuario(idUsuario));
		itemMensagem.setRemetenteAdmin("N");
		itemMensagem.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
		itemMensagem.setMensagemAdmin(mensagem);		
		itemMensagemAdminService.cadastrarItemMensagem(itemMensagem);		
		form.setId(mensagem.getId());
	}
	
	@Override
	@Transactional
	public void criarMensagemAdminEnviadoPorAdministrador(MensagemAdminForm form) {

		MensagemAdmin mensagem = new MensagemAdmin();
		BeanUtils.copyProperties(form, mensagem);
		mensagem.setDataUltimaMensagem(new Date());
		mensagem.setUsuario(usuarioDao.findUsuario(form.getIdUsuario()));
		mensagem.setId(null);
		dao.save(mensagem);
		
		ItemMensagemAdmin itemMensagem = new ItemMensagemAdmin();
		itemMensagem.setDataMensagem(mensagem.getDataUltimaMensagem());
		itemMensagem.setDescricao(form.getDescricaoUltimaMensagem());
		itemMensagem.setUsuario(usuarioDao.findUsuario(form.getIdUsuario()));
		itemMensagem.setRemetenteAdmin("S");
		itemMensagem.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
		itemMensagem.setMensagemAdmin(dao.findMensagemAdminById(mensagem.getId()));		
		itemMensagemAdminService.cadastrarItemMensagem(itemMensagem);		
		form.setId(mensagem.getId());
	}

	@Override
	public MensagemAdminForm carregaFormPorIdMensagemAdmin(Long idMensagemAdmin) {
		MensagemAdminForm form = new MensagemAdminForm();
		MensagemAdmin mensagem = dao.findMensagemAdminById(idMensagemAdmin);
		BeanUtils.copyProperties(mensagem, form);
		return form;
	}

	@Override
	public List<MensagemAdmin> listarTodasMensagensOrdenadasPorData() {
		return dao.findAllMensagensAdminOrderByDataMensagem();
	}

	@Override
	public MensagemAdmin recuperarMensagemAdminPorId(Long idMensagemAdmin) {
		return dao.findMensagemAdminById(idMensagemAdmin);
	}

	@Override
	@Transactional
	public void atualizarMensagemAdmin(MensagemAdmin mensagem) {
		dao.save(mensagem);			
	}

	@Override
	public List<MensagemAdmin> filtrarMensagensPorTipo(String tipoMensagem) {
		List<MensagemAdmin> lista =  null;
		
		if ( tipoMensagem.equals("T"))
			lista = dao.findAllMensagensAdminOrderByDataMensagem();
		else
			lista = dao.findAllMensagensAdminOrderByDataMensagemByTipoMensagem(tipoMensagem);

		return lista;
	}

	@Override
	public boolean validarCriarMensagemAdmin(MensagemAdminForm form, BindingResult result) {		
		boolean valido = true;
		
		if (StringUtils.isNullOrEmpty(form.getTipoMensagem())){
			valido = false;			
			result.rejectValue("tipoMensagem", "msg.erro.campo.obrigatorio");
		}
		
		if (StringUtils.isNullOrEmpty(form.getTitulo())){
			valido = false;
			result.rejectValue("titulo", "msg.erro.campo.obrigatorio");
		}
		
		if (StringUtils.isNullOrEmpty(form.getDescricaoUltimaMensagem())){
			valido = false;
			result.rejectValue("descricaoUltimaMensagem", "msg.erro.campo.obrigatorio");
		}
		
		return valido;
	}

	@Override
	public boolean validarCriarMensagemParaAdmin(MensagemAdminForm form , BindingResult result) {
		
		boolean valido = true;
		
		if (StringUtils.isNullOrEmpty(form.getTipoMensagem())){
			valido = false;
			result.rejectValue("tipoMensagem", "msg.erro.campo.obrigatorio");
		}
		
		if (StringUtils.isNullOrEmpty(form.getTitulo())){
			valido = false;
			result.rejectValue("titulo", "msg.erro.campo.obrigatorio");
		}
		
		if (StringUtils.isNullOrEmpty(form.getDescricaoUltimaMensagem())){
			valido = false;
			result.rejectValue("descricaoUltimaMensagem", "msg.erro.campo.obrigatorio");
		}
		
		return valido;
	}

	@Override
	public List<MensagemAdmin> listarNovasMensagensOrdenadasPorDataPorQuantidade(int quant) {
		return dao.findAllMensagensNovasAdminOrderByDataMensagemBYQuant(quant);
	}	

}
