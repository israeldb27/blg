package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.EstadosDao;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Estados;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.util.Select;

@Service
public class EstadosServiceImpl implements EstadosService {
	
	private static final Logger log = LoggerFactory.getLogger(EstadosServiceImpl.class);

	@Autowired(required = true)
	private EstadosDao dao;
	
	
	public Estados rescuperarEstadosPorId(int id) {
		return dao.findEstadosById(id);
	}

	
	public List<Estados> relatorioSobreEstados(RelatorioForm frm) {			
        List lista = dao.relatorioSobreEstados(frm);				
        List<Estados> listaFinal = new ArrayList<Estados>();
        if (! CollectionUtils.isEmpty(lista)){
            Estados estado = null;       
            for (Iterator iter = lista.iterator();iter.hasNext();){                
                Object[] obj = (Object[]) iter.next();
                estado = this.selecionaEstadoPorId(Integer.parseInt(obj[0].toString()));                
                estado.setValorTotal(Double.parseDouble(obj[1].toString()));
                estado.setQuantidade(Integer.parseInt(obj[2].toString()));                
                estado.setValorMedio(estado.getValorTotal() / estado.getQuantidade());
                listaFinal.add(estado);
            }       
        }	         
        return listaFinal;
	}

	
	public List<Estados> listarTodosEstados() {		
		return dao.listAll();
	}
	
	public List<Select> listarTodosEstadosSelect() {
		List<Estados> lista = dao.listAll();
		List<Select> listaFinal = new ArrayList<Select>();
		for (Estados estados : lista){
			listaFinal.add(new Select(String.valueOf(estados.getId()), estados.getNome()));
		}
		return listaFinal;
	}

	
	public Estados selecionaEstadoPorId(int idEstado) {		
		return dao.findEstadosById(idEstado); 
	}

	
	public List<Estados> selecionaEstadosPorId(int idEstado) {		
		return (List<Estados>) dao.findEstadosPorIdEstado(idEstado);
	}

}
