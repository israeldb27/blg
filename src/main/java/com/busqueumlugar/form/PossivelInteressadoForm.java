package com.busqueumlugar.form;

import java.io.Serializable;
import java.util.Date;

import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Usuario;

public class PossivelInteressadoForm extends BaseForm implements Serializable {
	
		private static final long serialVersionUID = 4785625858763603333L;
		private Long id;    
	    private Imovel imovel; 
	    private Usuario usuarioInteressado;
	    private Date dataCadastro; 
	    private Integer porcentagemChanceInteresse;
	    private String chanceInteresse; // /muito Baixa, Baixa, Media, Alta, Muito Alta
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
		public Usuario getusuarioInteressado() {
			return usuarioInteressado;
		}
		public void setusuarioInteressado(Usuario usuarioInteressado) {
			this.usuarioInteressado = usuarioInteressado;
		}
		public Date getDataCadastro() {
			return dataCadastro;
		}
		public void setDataCadastro(Date dataCadastro) {
			this.dataCadastro = dataCadastro;
		}
		public Integer getporcentagemChanceInteresse() {
			return porcentagemChanceInteresse;
		}
		public void setporcentagemChanceInteresse(Integer porcentagemChanceInteresse) {
			this.porcentagemChanceInteresse = porcentagemChanceInteresse;
		}
		public String getchanceInteresse() {
			return chanceInteresse;
		}
		public void setchanceInteresse(String chanceInteresse) {
			this.chanceInteresse = chanceInteresse;
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
