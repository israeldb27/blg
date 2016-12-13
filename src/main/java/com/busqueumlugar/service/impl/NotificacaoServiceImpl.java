package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.NotificacaoDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.form.NotificacaoForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Notificacao;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ImovelFavoritosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.NotificacaoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {
	
	private static final Logger log = LoggerFactory.getLogger(NotificacaoServiceImpl.class);
	
	@Autowired
	private NotificacaoDao dao;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ImovelDao imovelDao;
	
	
	public Notificacao recuperarNotificacaoPorId(Long idNotificacao) {
		
		return dao.findNotificacaoById(idNotificacao);
	}
	
	public List<Notificacao> recuperarNotificacaoPorIdSelecionada(Long idNotificacao) {
		Notificacao notificacao = dao.findNotificacaoById(idNotificacao);	
		List<Notificacao> lista = new ArrayList<Notificacao>();
		lista.add(notificacao);
		return lista; 
	}


	public List<Notificacao> recuperarMinhasNotificacoes(Long idUsuario, NotificacaoForm form) {
		return dao.findNotificacoesByIdUsuario(idUsuario, form);
	}


	
	public List<Notificacao> recuperarNotificacoesNovas(List<Notificacao> lista) {		
		if (CollectionUtils.isEmpty(lista))
            return lista;
        else {
            List<Notificacao> listaFinal = new ArrayList<Notificacao>();
            for (Notificacao not : lista ){
                if ( not.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo()))
                    listaFinal.add(not);
            }
            return listaFinal;
        }
	}


	
	public int checarQuantidadeNovasNotificacoes(Long idUsuario) {
        return AppUtil.recuperarQuantidadeLista(dao.findQuantNovasNotificacoes(idUsuario));
	}


	@Transactional
	public void cadastrarNotificacao(Usuario usuario, String acao, String descricao, String tipoNotificacao, Long idUsuarioConvite) {		
		Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuario);
        notificacao.setDataNotificacao(new Date());
        notificacao.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
        notificacao.setAcao(acao); 
        notificacao.setDescricao(descricao);
        notificacao.setTipoNotificacao(tipoNotificacao); 
        if ( idUsuarioConvite != null && idUsuarioConvite.longValue() > 0l)
        	notificacao.setUsuarioConvite(usuarioDao.findUsuario(idUsuarioConvite));
        dao.save(notificacao);
	}
	
	@Transactional
	public void cadastrarNotificacao(Long idUsuario, String acao, String descricao, String tipoNotificacao, Long idUsuarioConvite) {		
		Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuarioDao.findUsuario(idUsuario));
        notificacao.setDataNotificacao(new Date());
        notificacao.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
        notificacao.setAcao(acao); 
        notificacao.setDescricao(descricao);
        notificacao.setTipoNotificacao(tipoNotificacao); 
        if ( idUsuarioConvite != null && idUsuarioConvite.longValue() > 0l)
        	notificacao.setUsuarioConvite(usuarioDao.findUsuario(idUsuarioConvite));
        dao.save(notificacao);
	}
	
	@Transactional
	public void cadastrarNotificacao(Long idUsuario, Long idImovel, String acao, String descricao, String tipoNotificacao, Long idUsuarioConvite) {		
		Notificacao notificacao = new Notificacao();
		notificacao.setUsuario(usuarioDao.findUsuario(idUsuario));
        notificacao.setImovel(imovelDao.findImovelById(idImovel));
        notificacao.setDataNotificacao(new Date());
        notificacao.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
        notificacao.setAcao(acao); 
        notificacao.setDescricao(descricao);
        notificacao.setTipoNotificacao(tipoNotificacao);
        notificacao.setUsuarioConvite(usuarioDao.findUsuario(idUsuarioConvite));
        dao.save(notificacao);
	}


	@Transactional
	public boolean atualizarStatus(Notificacao notificacao) {		
		Notificacao not = dao.findNotificacaoById(notificacao.getId());
        if ( not.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo()))
            not.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
        else 
            not.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());

        dao.save(not);
        return true;
	}
	
	public List<Notificacao> recuperarListaNotificacoesNovas(Long idUsuario) {			
		return dao.findNotificacoesByIdUsuarioByStatusLeitura(idUsuario, StatusLeituraEnum.NOVO.getRotulo());
	}


	
	public List<Notificacao> ordenarNotificacoes(Long idUsuario, NotificacaoForm form) {
        return dao.findNotificacoesByIdUsuario(idUsuario, form);
	}


	@Override
	public List<Notificacao> filtrarNotificacoes(Long idUsuario, NotificacaoForm form) {
		List<Notificacao> lista = null;
		
		if (form.getOpcaoFiltro().equals("todos"))
			lista = dao.findNotificacoesByIdUsuario(idUsuario, form);
		else
			lista = dao.filterNotificacoesByIdUsuario(idUsuario, form);
		
		return lista;		
	}

	@Override
	@Transactional
	public void atualizarStatusLeituraNotificacao(List<Notificacao> lista) {
		
		if (! CollectionUtils.isEmpty(lista)){
			for (Notificacao not : lista){
				if ( not.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo())){
					not.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
					dao.save(not);
				}
			}
		}		
	}

	@Override
	@Transactional
	public void atualizarStatusLeituraNotificacaoByIdUsuario(Long idUsuario) {
		dao.atualizarStatusLeituraNotificacaoByIdUsuario(idUsuario);
		
	}
}
