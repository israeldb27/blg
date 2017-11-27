package com.busqueumlugar.form;

import java.io.Serializable;
import java.util.Date;

import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Usuario;

public class PossivelCompradorForm extends BaseForm implements Serializable {
	
		private static final long serialVersionUID = 4785625858763603333L;
		private Long id;    
	    private Imovel imovel; 
	    private Usuario usuarioComprador;
	    private Date dataCadastro; 
	    private Integer porcentagemChanceCompra;
	    private String chanceCompra; // /muito Baixa, Baixa, Media, Alta, Muito Alta
	    private String observacao;
	    private Date dataUltimaAtualizacao;
	    
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Imovel getImovel() {
			return imovel;
		}
		public void setImovel(Imovel imovel) {
			this.imovel = imovel;
		}
		public Usuario getUsuarioComprador() {
			return usuarioComprador;
		}
		public void setUsuarioComprador(Usuario usuarioComprador) {
			this.usuarioComprador = usuarioComprador;
		}
		public Date getDataCadastro() {
			return dataCadastro;
		}
		public void setDataCadastro(Date dataCadastro) {
			this.dataCadastro = dataCadastro;
		}
		public Integer getPorcentagemChanceCompra() {
			return porcentagemChanceCompra;
		}
		public void setPorcentagemChanceCompra(Integer porcentagemChanceCompra) {
			this.porcentagemChanceCompra = porcentagemChanceCompra;
		}
		public String getChanceCompra() {
			return chanceCompra;
		}
		public void setChanceCompra(String chanceCompra) {
			this.chanceCompra = chanceCompra;
		}
		public String getObservacao() {
			return observacao;
		}
		public void setObservacao(String observacao) {
			this.observacao = observacao;
		}
		public Date getDataUltimaAtualizacao() {
			return dataUltimaAtualizacao;
		}
		public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
			this.dataUltimaAtualizacao = dataUltimaAtualizacao;
		}

}
