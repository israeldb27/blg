package com.busqueumlugar.form;

import java.util.List;

import com.busqueumlugar.util.Select;

public class BaseForm {
	
	private String imagemArquivo = "";
	private int pageNumber = 0; // campo usado para realizar paginacao
	private String opcaoPaginacao = "";
	private int quantRegistros = 0;
	private int quantMaxRegistrosPerPage;
	private List<Select> listaPaginas;
	private boolean isVisible;
	int MAX_NUMBER_PER_PAGE = 5;
	
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getOpcaoPaginacao() {
		return opcaoPaginacao;
	}
	public void setOpcaoPaginacao(String opcaoPaginacao) {
		this.opcaoPaginacao = opcaoPaginacao;
	}
	public int getQuantRegistros() {
		return quantRegistros;
	}
	public void setQuantRegistros(int quantRegistros) {
		this.quantRegistros = quantRegistros;
	}
	public int getQuantMaxRegistrosPerPage() {
		return MAX_NUMBER_PER_PAGE;
	}
	public void setQuantMaxRegistrosPerPage(int quantMaxRegistrosPerPage) {
		this.quantMaxRegistrosPerPage = MAX_NUMBER_PER_PAGE;
	}
	public List<Select> getListaPaginas() {
		return listaPaginas;
	}
	public void setListaPaginas(List<Select> listaPaginas) {
		this.listaPaginas = listaPaginas;
	}
	public boolean isVisible() {
		if (this.getQuantRegistros() > MAX_NUMBER_PER_PAGE)
			return true;
		else
			return false;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public String getImagemArquivo() {
		return imagemArquivo;
	}
	public void setImagemArquivo(String imagemArquivo) {
		this.imagemArquivo = imagemArquivo;
	}

}
