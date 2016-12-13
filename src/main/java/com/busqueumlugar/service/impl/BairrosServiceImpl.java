package com.busqueumlugar.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.BairrosDao;
import com.busqueumlugar.dao.CidadesDao;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Bairros;
import com.busqueumlugar.model.Cidades;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.util.Select;

@Service
public class BairrosServiceImpl implements BairrosService {
	
	private static final Logger log = LoggerFactory.getLogger(BairrosServiceImpl.class);
	
	@Autowired
	private BairrosDao dao;
	
	@Autowired
	private CidadesService cidadesService;
	
	public Bairros recuperarBairrosPorId(int id) {
		return dao.findBairrosById(id);
	}

	
	public List<Bairros> relatorioSobreBairros(RelatorioForm frm) {				
        List lista = dao.relatorioSobreBairros(frm);	  
        List<Bairros> listaFinal = new ArrayList<Bairros>();
        if (! CollectionUtils.isEmpty(lista)){
            Bairros bairro = null;       
            for (Iterator iter = lista.iterator();iter.hasNext();){                
                Object[] obj = (Object[]) iter.next();
                bairro = this.selecionarBairroPorId(Integer.parseInt(obj[0].toString()));                
                bairro.setNomeCidade(cidadesService.selecionarCidadesPorId(Integer.parseInt(bairro.getCidade())).getNome());
                bairro.setQuantidade(Integer.parseInt(obj[2].toString()));              
                bairro.setValorTotal(Double.parseDouble(obj[1].toString()));
                bairro.setValorMedio(bairro.getValorTotal() / bairro.getQuantidade());
                listaFinal.add(bairro);
            } 
        }        
        return listaFinal;
	}

	
	public Bairros selecionarBairroPorId(int idBairro) {		
		return dao.findBairrosById(idBairro);
	}

	
	public List<Bairros> selecionarBairrosPorIdCidade(int idCidade) {		
		return dao.findBairrosPorIdCidade(idCidade);
	}


	@Override
	public List<Select> selecionarBairrosPorIdCidadeSelect(Integer idCidade) {
		List<Bairros> lista = dao.findBairrosPorIdCidade(idCidade);
		List<Select> listaFinal = new ArrayList<Select>(); 
		for (Bairros bairros: lista){
			listaFinal.add(new Select(String.valueOf(bairros.getId()), bairros.getNome()));
		}		
		return listaFinal;
	}

}
