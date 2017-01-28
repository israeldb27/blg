package com.busqueumlugar.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.ImoveldestaqueDao;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImoveldestaqueForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imoveldestaque;
import com.busqueumlugar.service.ImoveldestaqueService;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.MessageUtils;
import com.mysql.jdbc.StringUtils;

@Service
public class ImoveldestaqueServiceImpl implements ImoveldestaqueService {
	
	
	@Autowired
	private ImoveldestaqueDao dao;
	
	@Autowired
	private ImovelDao imovelDao;	
	
	
	public Imoveldestaque recuperarImoveldestaquePorId(Long id) {
		return dao.findImoveldestaqueById(id);
	}
	
	@Override
	@Transactional
	public void cadastrarImovelDestaque(Imoveldestaque imoveldestaque) {				
        dao.save(imoveldestaque);
	}

	
	public List<Imoveldestaque> listarTodosImoveisDestaque() {		
		return dao.listAll();
	}
	
	public List<Imoveldestaque> recuperarImoveisDestaquePorIdImovel(Long idImovel){
		return dao.findImoveldestaqueByIdImovel(idImovel);
	}
	
	@Override
	@Transactional	
	public void excluirImovelDestaque(Long idImovelDestaque){
		Imoveldestaque imovelDestaque = dao.findImoveldestaqueById(idImovelDestaque);
		dao.delete(imovelDestaque);
	}
	
	@Override
	public List<Imoveldestaque> recuperarImoveisDestaquePorDataDestaque(Date dataDestaque, int quantImoveis){
		return dao.findImoveisDestaqueByDataDestaqueByQuantidade(dataDestaque, quantImoveis);
	}
	
	@Override
	public List<Imoveldestaque> recuperarImoveisDestaqueAnuncioPorDataDestaque(int quantImoveis){
		return dao.findImoveisDestaqueAnuncioByDataDestaqueByQuantidade(quantImoveis);
	}

	@Override
	public long checarQuantidadeImoveisAnuncioNoDia() {
		return dao.findQuantImoveisAnunciosByDia();
	}

	@Override
	public Imovel recuperarImovelAnuncio(int posicao) {
		Imoveldestaque imoveldestaque = dao.findImovelAnuncioByDiaByPosicao(posicao);
		if ( imoveldestaque != null )
			return imoveldestaque.getImovel();
		else
			return null;
	}

	@Override
	public String validarCadastroAnuncioImovel(String dataInicio,	String dataFim, ImovelForm form) {
		
		String msgRetorno = "";
		boolean erro = false;
		
		if ( StringUtils.isNullOrEmpty(dataInicio)){
			msgRetorno = MessageUtils.getMessage("opcao.periodo.obrigatorio");
			erro = true;
		}
		else {
			String dtInicio = dataInicio.replaceAll("-","/");
			if ( ! DateUtil.formatoDataValido(dtInicio)){
				msgRetorno = MessageUtils.getMessage("msg.erro.data.invalida");
				erro = true;
			}
		}
		
		if (! erro ){
			if ( StringUtils.isNullOrEmpty(dataFim)){
				msgRetorno = MessageUtils.getMessage("opcao.periodo.obrigatorio");
				erro = true;
			}
			else {
				String dtFim = dataFim.replaceAll("-","/");
				if (! DateUtil.formatoDataValido(dtFim)){
					msgRetorno = MessageUtils.getMessage("msg.erro.data.invalida");
					erro = true;
				}
			}
		}
		
		if (! erro){
			Calendar dtIncio = Calendar.getInstance();
			dtIncio.setTime(DateUtil.formataDataBanco(dataInicio.replaceAll("-","/")));
			
			Calendar dtFim = Calendar.getInstance();
			dtFim.setTime(DateUtil.formataDataBanco(dataFim.replaceAll("-","/")));
			
			Calendar dtAtual = Calendar.getInstance();
			
			if ( dtFim.compareTo(dtIncio) < 0 ){				
				msgRetorno = MessageUtils.getMessage("msg.erro.data.inicio.maior.data.fim");
				erro = true;
			}
			
			if ( ! erro){
				if ( dtAtual.compareTo(dtIncio) < 0 ){				
					msgRetorno = MessageUtils.getMessage("msg.erro.data.inicio.menor.data.atual");
					erro = true;
				}
			}
			
			if (! erro){ 
				Imoveldestaque imoveldestaque = dao.findImovelAnuncioByData(DateUtil.formataDataBanco(dataInicio.replaceAll("-","/")), 
																		    DateUtil.formataDataBanco(dataFim.replaceAll("-","/")), 
																		    form.getId());
				if ( imoveldestaque != null ){
					msgRetorno = MessageUtils.getMessage("msg.erro.data.anuncio.ja.existente");
					erro = true;
				}
			}
		}		
		
		return msgRetorno;
	}

	@Override
	public List<Imoveldestaque> recuperarListaAnuncioPorImovel(Long idImovel) {
		return dao.findImoveisAnunciosPorImovel(idImovel);
	}

	@Override
	@Transactional
	public void cadastrarAnuncioImovel(String dataInicio, String dataFim, ImovelForm form) {

		DateUtil dtInicio = new DateUtil(dataInicio.replaceAll("-","/"));
		DateUtil dtFim = new DateUtil(dataFim.replaceAll("-","/"));
		
		int quantDias = dtFim.getDayRange(dtInicio);
		Imoveldestaque imovelDestaque = null;
		Imovel imovel = imovelDao.findImovelById(form.getId());
		for (int i = 0; i <= quantDias; i++){
			imovelDestaque = new Imoveldestaque();
			imovelDestaque.setDataCadastro(new Date());
			imovelDestaque.setDataDestaque(dtInicio.getTime());
			imovelDestaque.setStatus("criado");
			imovelDestaque.setTipo("A");
			imovelDestaque.setImovel(imovel);
			dao.save(imovelDestaque);
			dtInicio.add(DateUtil.DAY_OF_MONTH, 1);			
		}
		
	}

	@Override
	public List<Imoveldestaque> recuperarImoveisDestaqueAnuncioPorDataDestaque(ImoveldestaqueForm form) {
		return dao.findImoveisAnuncios(form);
	}

	@Override
	public List<Imoveldestaque> recuperarImoveisAnuncioPorImovel(Long idImovel) {
		return dao.findImoveisAnunciosPorImovel(idImovel);
	}

}
