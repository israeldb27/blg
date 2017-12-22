package com.busqueumlugar.form;

import java.io.Serializable;
import java.util.Date;
import com.busqueumlugar.model.Imovel;

public class PossivelInteressadoOfflineForm extends BaseForm implements Serializable {
	

		private static final long serialVersionUID = 1950593605336220795L;
		private Long id;   
	    private Imovel imovel;    
	    private String observacao;
	    private Date dataCadastro; 
	    private Integer porcentagemChanceInteresse;
	    private String chanceInteresse; // /muito Baixa, Baixa, Media, Alta, Muito Alta
	    private String nomeInteressado;
	    private String emailInteressado;	    
	    private String telefoneInteressado;
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
		public Integer getPorcentagemChanceInteresse() {
			return porcentagemChanceInteresse;
		}
		public void setPorcentagemChanceInteresse(Integer porcentagemChanceInteresse) {
			this.porcentagemChanceInteresse = porcentagemChanceInteresse;
		}
		public String getChanceInteresse() {
			return chanceInteresse;
		}
		public void setChanceInteresse(String chanceInteresse) {
			this.chanceInteresse = chanceInteresse;
		}
		public String getNomeInteressado() {
			return nomeInteressado;
		}
		public void setNomeInteressado(String nomeInteressado) {
			this.nomeInteressado = nomeInteressado;
		}
		public String getEmailInteressado() {
			return emailInteressado;
		}
		public void setEmailInteressado(String emailInteressado) {
			this.emailInteressado = emailInteressado;
		}
		public String gettelefoneInteressado() {
			return telefoneInteressado;
		}
		public void settelefoneInteressado(String telefoneInteressado) {
			this.telefoneInteressado = telefoneInteressado;
		}
		public Date getDataUltimaAtualizacao() {
			return dataUltimaAtualizacao;
		}
		public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
			this.dataUltimaAtualizacao = dataUltimaAtualizacao;
		}

}
