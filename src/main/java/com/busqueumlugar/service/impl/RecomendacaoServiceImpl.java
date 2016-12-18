package com.busqueumlugar.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.RecomendacaoDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.model.Recomendacao;
import com.busqueumlugar.service.RecomendacaoService;
import com.busqueumlugar.service.UsuarioService;
import com.mysql.jdbc.StringUtils;

@Service
public class RecomendacaoServiceImpl implements RecomendacaoService {
	
	private static final Logger log = LoggerFactory.getLogger(RecomendacaoServiceImpl.class);
	
	@Autowired
	private RecomendacaoDao dao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public Recomendacao recuperarRecomendacaoPorId(Long idRecomendacao) {
		return dao.findRecomendacaoById(idRecomendacao);
	}

	@Override
	public List<Recomendacao> recuperarRecomendacoesPorIdUsuarioRecomendado(Long idUsuario) {		
		return dao.findListRecomendacaoByIdUsuario(idUsuario);
	}

	@Override
	@Transactional
	public void cadastrarRecomendacao(Long idUsuario, Long idUsuarioSessao, String novaRecomendacao) {
		Recomendacao rec = new Recomendacao();
		rec.setUsuarioRecomendado(usuarioDao.findUsuario(idUsuario));
		rec.setUsuario(usuarioDao.findUsuario(idUsuarioSessao));
		rec.setDataRecomendacao(new Date());
		rec.setStatus(RecomendacaoStatusEnum.ENVIADO.getRotulo());
		rec.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
		rec.setDescricao(novaRecomendacao);
		dao.save(rec);	
	}

	@Override
	public void respostaRecomendacao(Long idUsuario, Long idRecomendacao, String respostaRecomendacao) {
		
		if (! StringUtils.isNullOrEmpty(respostaRecomendacao)){
			if ( respostaRecomendacao.equals("aceitar")){
				Recomendacao rec = dao.findRecomendacaoById(idRecomendacao);
				rec.setDataResposta(new Date());
				rec.setStatus(RecomendacaoStatusEnum.ACEITO.getRotulo());
				rec.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
				dao.save(rec);				
			}
			else if ( respostaRecomendacao.equals("recusar")){
				Recomendacao rec = dao.findRecomendacaoById(idRecomendacao);
				dao.delete(rec);
			}
		}
	}

	@Override
	public long checarNovasRecomendacoesRecebidas(Long idUsuario) {
		return dao.findQuantidadeRecomendacoesByUsuarioByStatusByStatusLeitura(idUsuario, RecomendacaoStatusEnum.ENVIADO.getRotulo(), StatusLeituraEnum.NOVO.getRotulo());
		
	}

	@Override
	public List<Recomendacao> recuperarRecomendacoesPorIdUsuarioRecomendadoPorStatus(Long idUsuarioRecomendado, String status) {		
		return dao.findListRecomendacaoByIdUsuarioByStatus(idUsuarioRecomendado, status);		
	}

	@Override
	@Transactional
	public void atualizarStatusLeituraRecomendacoesPorIdUsuario(List<Recomendacao> lista) {

		if (! CollectionUtils.isEmpty(lista)){
			for (Recomendacao rec : lista){
				rec.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
				dao.save(rec);
			}
		}		
	}

	@Override
	@Transactional
	public void atualizarStatusLeituraRecomendacaoSelecionada(Recomendacao rec) {
		rec.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
		dao.save(rec);		
	}

	@Override
	public long checarQuantidadeTotalRecomendacaoRecebidaPorStatus(Long idUsuario, String status) {
		return dao.findQuantidadeRecomendacoesByUsuarioByStatusByStatusLeitura(idUsuario, status, null);
	}

}
