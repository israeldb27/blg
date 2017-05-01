package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.List;



public class ParametrosTimeline implements Serializable {
   
	private static final long serialVersionUID = 1L;
    
    private List<Long> listaIds;
    private String existeIdUsuario = "";
    private int ultimoIndexIdsUsuario = 0;
    private int ultimoIndexIdsUsuarioCompartilhado = 0;
    private int idIndexPrefImovel = 0;			
    private int idIndexPrefImovelAleatoriamente = 0;
    private int regraPrefImovel = 0;
    private int idIndexImovelAleatoriamente = 0;
    private int idIndexSugestaoUsuario = 0;
    private int idIndexNota = 0;
    private long quantAnuncioImovel = 0;
    private int idUltimoAnuncioImovel = 0;    
    private int idIndexImovelVisitado = 0;
    
    private int inicioRegra = 0;
    private int fimRegra = 0;
	
    private boolean isEmptyImoveisIntermediacaoParceria = false;
    private boolean isEmptyNotas = false;
    private boolean isEmptyImoveisContatos = false;
    private boolean isEmptyImoveisAnuncios = false;
    private boolean isEmptyImoveisAleatorios = false;	
    private boolean isUsuarioTimeLine = false;
    private boolean isEmptyImoveisPrefImoveis = false;
    
    private boolean isEmptyImoveisIntermediacaoParceriaSemContato = false;   
    private boolean isEmptyImoveisPrefImoveisSemContato = false;
    

	public List<Long> getListaIds() {
		return listaIds;
	}

	public void setListaIds(List<Long> listaIds) {
		this.listaIds = listaIds;
	}

	public String getExisteIdUsuario() {
		return existeIdUsuario;
	}

	public void setExisteIdUsuario(String existeIdUsuario) {
		this.existeIdUsuario = existeIdUsuario;
	}

	public int getUltimoIndexIdsUsuario() {
		return ultimoIndexIdsUsuario;
	}

	public void setUltimoIndexIdsUsuario(int ultimoIndexIdsUsuario) {
		this.ultimoIndexIdsUsuario = ultimoIndexIdsUsuario;
	}

	public int getUltimoIndexIdsUsuarioCompartilhado() {
		return ultimoIndexIdsUsuarioCompartilhado;
	}

	public void setUltimoIndexIdsUsuarioCompartilhado(
			int ultimoIndexIdsUsuarioCompartilhado) {
		this.ultimoIndexIdsUsuarioCompartilhado = ultimoIndexIdsUsuarioCompartilhado;
	}

	public int getIdIndexPrefImovel() {
		return idIndexPrefImovel;
	}

	public void setIdIndexPrefImovel(int idIndexPrefImovel) {
		this.idIndexPrefImovel = idIndexPrefImovel;
	}

	public int getIdIndexPrefImovelAleatoriamente() {
		return idIndexPrefImovelAleatoriamente;
	}

	public void setIdIndexPrefImovelAleatoriamente(
			int idIndexPrefImovelAleatoriamente) {
		this.idIndexPrefImovelAleatoriamente = idIndexPrefImovelAleatoriamente;
	}

	public int getRegraPrefImovel() {
		return regraPrefImovel;
	}

	public void setRegraPrefImovel(int regraPrefImovel) {
		this.regraPrefImovel = regraPrefImovel;
	}

	public int getIdIndexImovelAleatoriamente() {
		return idIndexImovelAleatoriamente;
	}

	public void setIdIndexImovelAleatoriamente(int idIndexImovelAleatoriamente) {
		this.idIndexImovelAleatoriamente = idIndexImovelAleatoriamente;
	}

	public int getIdIndexSugestaoUsuario() {
		return idIndexSugestaoUsuario;
	}

	public void setIdIndexSugestaoUsuario(int idIndexSugestaoUsuario) {
		this.idIndexSugestaoUsuario = idIndexSugestaoUsuario;
	}

	public int getIdIndexNota() {
		return idIndexNota;
	}

	public void setIdIndexNota(int idIndexNota) {
		this.idIndexNota = idIndexNota;
	}

	public long getQuantAnuncioImovel() {
		return quantAnuncioImovel;
	}

	public void setQuantAnuncioImovel(long quantAnuncioImovel) {
		this.quantAnuncioImovel = quantAnuncioImovel;
	}

	public int getIdUltimoAnuncioImovel() {
		return idUltimoAnuncioImovel;
	}

	public void setIdUltimoAnuncioImovel(int idUltimoAnuncioImovel) {
		this.idUltimoAnuncioImovel = idUltimoAnuncioImovel;
	}

	public int getInicioRegra() {
		return inicioRegra;
	}

	public void setInicioRegra(int inicioRegra) {
		this.inicioRegra = inicioRegra;
	}

	public int getFimRegra() {
		return fimRegra;
	}

	public void setFimRegra(int fimRegra) {
		this.fimRegra = fimRegra;
	}

	public boolean isEmptyImoveisIntermediacaoParceria() {
		return isEmptyImoveisIntermediacaoParceria;
	}

	public void setEmptyImoveisIntermediacaoParceria(
			boolean isEmptyImoveisIntermediacaoParceria) {
		this.isEmptyImoveisIntermediacaoParceria = isEmptyImoveisIntermediacaoParceria;
	}

	public boolean isEmptyNotas() {
		return isEmptyNotas;
	}

	public void setEmptyNotas(boolean isEmptyNotas) {
		this.isEmptyNotas = isEmptyNotas;
	}

	public boolean isEmptyImoveisContatos() {
		return isEmptyImoveisContatos;
	}

	public void setEmptyImoveisContatos(boolean isEmptyImoveisContatos) {
		this.isEmptyImoveisContatos = isEmptyImoveisContatos;
	}

	public boolean isEmptyImoveisAnuncios() {
		return isEmptyImoveisAnuncios;
	}

	public void setEmptyImoveisAnuncios(boolean isEmptyImoveisAnuncios) {
		this.isEmptyImoveisAnuncios = isEmptyImoveisAnuncios;
	}

	public boolean isEmptyImoveisAleatorios() {
		return isEmptyImoveisAleatorios;
	}

	public void setEmptyImoveisAleatorios(boolean isEmptyImoveisAleatorios) {
		this.isEmptyImoveisAleatorios = isEmptyImoveisAleatorios;
	}

	public boolean isEmptyImoveisIntermediacaoParceriaSemContato() {
		return isEmptyImoveisIntermediacaoParceriaSemContato;
	}

	public void setEmptyImoveisIntermediacaoParceriaSemContato(
			boolean isEmptyImoveisIntermediacaoParceriaSemContato) {
		this.isEmptyImoveisIntermediacaoParceriaSemContato = isEmptyImoveisIntermediacaoParceriaSemContato;
	}

	public boolean isUsuarioTimeLine() {
		return isUsuarioTimeLine;
	}

	public void setUsuarioTimeLine(boolean isUsuarioTimeLine) {
		this.isUsuarioTimeLine = isUsuarioTimeLine;
	}

	public boolean isEmptyImoveisPrefImoveisSemContato() {
		return isEmptyImoveisPrefImoveisSemContato;
	}

	public void setEmptyImoveisPrefImoveisSemContato(
			boolean isEmptyImoveisPrefImoveisSemContato) {
		this.isEmptyImoveisPrefImoveisSemContato = isEmptyImoveisPrefImoveisSemContato;
	}

	public boolean isEmptyImoveisPrefImoveis() {
		return isEmptyImoveisPrefImoveis;
	}

	public void setEmptyImoveisPrefImoveis(boolean isEmptyImoveisPrefImoveis) {
		this.isEmptyImoveisPrefImoveis = isEmptyImoveisPrefImoveis;
	}

	public int getIdIndexImovelVisitado() {
		return idIndexImovelVisitado;
	}

	public void setIdIndexImovelVisitado(int idIndexImovelVisitado) {
		this.idIndexImovelVisitado = idIndexImovelVisitado;
	}
    
    
    
}
