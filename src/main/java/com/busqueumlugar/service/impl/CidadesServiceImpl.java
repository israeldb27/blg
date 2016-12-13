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

import com.busqueumlugar.dao.CidadesDao;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Cidades;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.util.Select;

@Service
public class CidadesServiceImpl implements CidadesService{
	
	private static final Logger log = LoggerFactory.getLogger(CidadesServiceImpl.class);

	@Autowired(required = true)
	private CidadesDao dao;
	
	public Cidades recuperarCidadesPorId(int id) {
		return dao.findCidadesById(id);
	}

	
	public List<Cidades> relatorioSobreCidades(RelatorioForm frm) {	
        List lista = dao.relatorioSobreCidades(frm);	  
        List<Cidades> listaFinal = new ArrayList<Cidades>();
        if (! CollectionUtils.isEmpty(lista)){
            Cidades cidade = null;       
            for (Iterator iter = lista.iterator();iter.hasNext();){                
                Object[] obj = (Object[]) iter.next();
                cidade = this.selecionarCidadesPorId(Integer.parseInt(obj[0].toString()));    
                cidade.setValorTotal(Double.parseDouble(obj[1].toString()));
                cidade.setQuantidade(Integer.parseInt(obj[2].toString()));                
                cidade.setValorMedio(cidade.getValorTotal() / cidade.getQuantidade());
                listaFinal.add(cidade);
            }
        }		
	return listaFinal;
	}

	
	public Cidades selecionarCidadesPorId(int idCidade) {		
		 return dao.findCidadesById(idCidade);
	}

	
	public List<Cidades> selecionarCidadesPorIdEstado(int idEstado) {		
		return dao.findCidadesPorIdEstado(idEstado);
	}
	
	public List<Select> selecionarCidadesPorIdEstadoSelect(int idEstado) {
		List<Cidades> lista = dao.findCidadesPorIdEstado(idEstado);
		List<Select> listaFinal = new ArrayList<Select>(); 
		for (Cidades cidades : lista){
			listaFinal.add(new Select(String.valueOf(cidades.getId()), cidades.getNome()));
		}
		
		return listaFinal;
	}
	

}
