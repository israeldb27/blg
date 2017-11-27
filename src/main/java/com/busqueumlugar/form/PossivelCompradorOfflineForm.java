package com.busqueumlugar.form;

import java.io.Serializable;
import java.util.Date;
import com.busqueumlugar.model.Imovel;

public class PossivelCompradorOfflineForm extends BaseForm implements Serializable {
	

		private static final long serialVersionUID = 1950593605336220795L;
		private Long id;   
	    private Imovel imovel;    
	    private String observacao;
	    private Date dataCadastro; 
	    private Integer porcentagemChanceCompra;
	    private String chanceCompra; // /muito Baixa, Baixa, Media, Alta, Muito Alta
	    private String nomeComprador;
	    private String emailComprador;	    
	    private String telefoneComprador;
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
		public String getObservacao() {
			return observacao;
		}
		public void setObservacao(String observacao) {
			this.observacao = observacao;
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
		public String getNomeComprador() {
			return nomeComprador;
		}
		public void setNomeComprador(String nomeComprador) {
			this.nomeComprador = nomeComprador;
		}
		public String getEmailComprador() {
			return emailComprador;
		}
		public void setEmailComprador(String emailComprador) {
			this.emailComprador = emailComprador;
		}
		public String getTelefoneComprador() {
			return telefoneComprador;
		}
		public void setTelefoneComprador(String telefoneComprador) {
			this.telefoneComprador = telefoneComprador;
		}
		public Date getDataUltimaAtualizacao() {
			return dataUltimaAtualizacao;
		}
		public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
			this.dataUltimaAtualizacao = dataUltimaAtualizacao;
		}

}
