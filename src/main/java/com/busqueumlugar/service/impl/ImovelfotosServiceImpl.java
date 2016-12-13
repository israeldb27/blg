package com.busqueumlugar.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.busqueumlugar.dao.ImovelfotosDao;
import com.busqueumlugar.model.Imovelfotos;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelfotosService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.MessageUtils;

@Service
public class ImovelfotosServiceImpl implements ImovelfotosService {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelfotosServiceImpl.class);
	
	@Autowired
	private ImovelfotosDao dao;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ServletContext context;
	
	public Imovelfotos recuperarImovelfotosPorId(Long id) {
		return dao.findImovelfotosById(id);
	}

	
	public List<Imovelfotos> recuperarFotosImovel(Long idImovel) {		
		List<Imovelfotos> lista = dao.findImovelfotosByIdImovel(idImovel);
        List<Imovelfotos>  listaFinal = new ArrayList();
        if ( lista != null ){
        	for (Imovelfotos imovel : lista){             
                imovel.setImagemArquivo(this.carregaImovelFoto(imovel));            
                listaFinal.add(imovel);
            }
        }        
        return listaFinal;
	}

	
	public String carregaImovelFoto(Imovelfotos imovel) {		
	
		 if (imovel != null && imovel.getImg() != null){
            String idImovel =  imovel.getId().toString();
            String titulo = "imovelFoto";
            String nomeArquivo = "/img/" + titulo + idImovel + ".jpg";            
          
			try {
				String arquivo = context.getRealPath(nomeArquivo);
	            FileOutputStream out;
				out = new FileOutputStream(arquivo);
				out.write(imovel.getImg());
	            File f = new File(arquivo);
	            InputStream in = new FileInputStream(f);                            
	            return nomeArquivo;
			} catch (Exception e){
				e.printStackTrace();
			}
             
			return null;	
		}
		else
			return null;
	}

	
	@Transactional
	public void adicionarFotos(Long idImovel, byte[] img, String descricao) {		
		Imovelfotos imovel = new Imovelfotos();
        imovel.setImovel(imovelService.recuperarImovelPorid(idImovel));
        imovel.setImg(img);
        imovel.setDescricao(descricao);
        imovel.setDataCadastro(new Date());
        dao.save(imovel);
	}

	
	@Transactional
	public void excluirFoto(Imovelfotos imovelFoto) {		
		dao.delete(imovelFoto);
	}

	
	public int checarQuantidadeFotosImovel(Long idImovel) {		
		return AppUtil.recuperarQuantidadeLista(dao.findImovelfotosByIdImovel(idImovel));		
	}


	
	@Transactional
	public void excluirFoto(Long idImovelFoto) {
		Imovelfotos imovelFoto = dao.findImovelfotosById(idImovelFoto);
		dao.delete(imovelFoto);
	}


	@Override
	public String validarAdicionarFotos(MultipartFile file) {
		
		String msg = "";
		if ( file.isEmpty() )
			msg = MessageUtils.getMessage("msg.nenhuma.foto.selecionada");
		
		if ( msg.equals("")) {
			if (! file.getContentType().equals("image/jpeg"))
				msg = MessageUtils.getMessage("msg.erro.tipo.foto.invalida");
		}	
		
		if ( msg.equals("")) {
			if ( file.getSize() > 120000 )
				msg = MessageUtils.getMessage("msg.erro.tamanho.foto.invalida");								   
		}
				
		return msg;
	}

}
