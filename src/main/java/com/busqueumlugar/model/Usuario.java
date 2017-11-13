package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.busqueumlugar.enumerador.PerfilUsuarioEnum;
import com.busqueumlugar.util.AppUtil;
import com.mysql.jdbc.StringUtils;
import com.paypal.base.codec.binary.Base64;

@Entity
@Table(name = "usuario")
public class Usuario extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1102689137761823434L;

	@Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;	
    
    @Column(name = "nome")
    private String nome;    
    
    @Column(name = "login")
    private String login;    
    
    @Column(name = "password")
    private String password;    
    
    @Column(name = "perfil")
    private String perfil;    
    
    @Column(name = "cpf")
    private String cpf;    
    
    @Column(name = "telefone")
    private String telefone;    
    
    @Column(name = "idEstado")
    private int idEstado;    
    
    @Column(name = "idCidade")
    private int idCidade;   
    
    @Column(name = "uf")
    private String uf;  
    
    @Column(name = "estado")
    private String estado;    
    
    @Column(name = "cidade")
    private String cidade;    
      
    @Column(name = "bairro")
    private String bairro;
  
    @Column(name = "email")
    private String email;    
    
    @Column(name = "idBairro")
    private int idBairro;    
    
    @Column(name = "statusUsuario")
    private String statusUsuario;    
    
    @Column(name = "dataSuspensao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSuspensao;    
    
    @Lob
    @Column(name = "fotoPrincipal")
    private byte[] fotoPrincipal;    
    
    @Column(name = "creci")
    private String creci;    
    
    @Column(name = "dataUltimoAcesso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimoAcesso; 
    
    @Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;    
            
    @Column(name = "descSobreMim")
    private String descSobreMim;    
    
    @Column(name = "dataValidadeAcesso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataValidadeAcesso; // campo usado para o controle assinatura    
        
    @Column(name = "pontosNegociacaoImovel")
    private int pontosNegociacaoImovel;    
        
    @Column(name = "quantUsuarioAprovServico")
    private int quantUsuarioAprovServico;    
        
    @Column(name = "quantUsuarioDesaprovServico")
    private int quantUsuarioDesaprovServico; 
    
    @Column(name = "codigoIdentificacao")
    private String codigoIdentificacao; 
        
    @Column(name = "ativado")
    private String ativado;
        
    @Column(name = "habilitaBusca")
    private String habilitaBusca;  
        
    @Column(name = "habilitaDetalhesInfoUsuario")
    private String habilitaDetalhesInfoUsuario;    
    
    @Column(name = "quantCadastroImoveis", nullable = true)
    private int quantCadastroImoveis ; // indica quantidade maxima de imoveis que o usuario pode cadastrar
    
    @Column(name = "quantMaxFotosPorImovel", nullable = false)
    private int quantMaxFotosPorImovel; // indica quantidade maxima de fotos de imoveis que o usuario pode cadastrar

    @Column(name = "quantMaxIndicacoesImovel", nullable = true)
    private int quantMaxIndicacoesImovel; // indica quantidade maxima indicacoes de imoveis que o usuario pode realizar    
    
    @Column(name = "quantMaxIndicacoesImovelEmail", nullable = true)
    private int quantMaxIndicacoesImovelEmail; // indica quantidade maxima indicacoes por email de imoveis que o usuario pode realizar
    
    @Column(name = "habilitaEnvioSolParceria")
    private String habilitaEnvioSolParceria;    
    
    @Column(name = "habilitaEnvioMensagens")
    private String habilitaEnvioMensagens; 
    
    @Column(name = "habilitaRecebeSeguidor")
    private String habilitaRecebeSeguidor;
    
    @Column(name = "sexo")
    private String sexo;
    
    @Column(name = "faixaSalarial")
    private String faixaSalarial;
    
    @Column(name = "dataNascimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;
	
	@Column(name = "motivoSuspensao")
    private String motivoSuspensao; 
	
	@Column(name = "codConfirmacaoAtivacao")
	private String codConfirmacaoAtivacao = "";
    
    @Transient
    private int quantNegociacoesSucesso;
    
    @Transient
    private int quantCompartilhamentoAceitos;
    
	// inicio campos - Imoveis Indicados
   
    @Transient
    private int quantImoveisIndicado;

    @Transient
    private int quantImoveisIndicacoes;
    
    // fim campos - Imoveis Indicados
    
    @Transient
    private int quantPropostas;
    
    @Transient
    private int quantComentarios;
    
    @Transient
    private int quantImovelFavoritos;
    
    @Transient
    private int quantImovelVisitado;
    
    @Transient
    private int quantImovelParceria;
    
    @Transient
    private int quantImovelIntermediacao;
    
    @Transient
    private String statusLeitura;
    
    @Transient
    private Long idContatoConvite;
    
    @Transient
    private String isContato; //{ N - contato nao formado, S - contato formado, C - convite realizado (enviado ou recebido)
    
    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataConvite;   
    
    @Transient
    private String acessoValido = "";  // {S, N}
    
    @Transient
    private String indicadoImovel = ""; // {S, N}
	
	@Transient
	private String perfilFmt; 
	
	@Transient
    private String isSeguindo = "N";	
	
	@Transient
	private long quantTotalImoveis = 0;
	
	@Transient
	private long quantTotalContatos = 0;
	
	@Transient
	private long quantTotalSeguidores = 0;
	
	@Transient
	private long quantTotalRecomendacoes = 0;
	
	
	public String getAcessoValido() {
		return acessoValido;
	}

	public void setAcessoValido(String acessoValido) {
		this.acessoValido = acessoValido;
	}

	public Date getDataConvite() {
		return dataConvite;
	}

	public void setDataConvite(Date dataConvite) {
		this.dataConvite = dataConvite;
	}

	public String getIsContato() {
		return isContato;
	}

	public void setIsContato(String isContato) {
		this.isContato = isContato;
	}

	public Long getIdContatoConvite() {
		return idContatoConvite;
	}

	public void setIdContatoConvite(Long idContatoConvite) {
		this.idContatoConvite = idContatoConvite;
	}

	public String getStatusLeitura() {
		return statusLeitura;
	}

	public void setStatusLeitura(String statusLeitura) {
		this.statusLeitura = statusLeitura;
	}


	public int getQuantImovelIntermediacao() {
		return quantImovelIntermediacao;
	}

	public void setQuantImovelIntermediacao(int quantImovelIntermediacao) {
		this.quantImovelIntermediacao = quantImovelIntermediacao;
	}

	public int getQuantImovelParceria() {
		return quantImovelParceria;
	}

	public void setQuantImovelParceria(int quantImovelParceria) {
		this.quantImovelParceria = quantImovelParceria;
	}

	public int getQuantImovelVisitado() {
		return quantImovelVisitado;
	}

	public void setQuantImovelVisitado(int quantImovelVisitado) {
		this.quantImovelVisitado = quantImovelVisitado;
	}

	public int getQuantImovelFavoritos() {
		return quantImovelFavoritos;
	}

	public void setQuantImovelFavoritos(int quantImovelFavoritos) {
		this.quantImovelFavoritos = quantImovelFavoritos;
	}

	public int getQuantComentarios() {
		return quantComentarios;
	}

	public void setQuantComentarios(int quantComentarios) {
		this.quantComentarios = quantComentarios;
	}

	public int getQuantPropostas() {
		return quantPropostas;
	}

	public void setQuantPropostas(int quantPropostas) {
		this.quantPropostas = quantPropostas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public int getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(int idCidade) {
		this.idCidade = idCidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(int idBairro) {
		this.idBairro = idBairro;
	}

	public String getStatusUsuario() {
		return statusUsuario;
	}

	public void setStatusUsuario(String statusUsuario) {
		this.statusUsuario = statusUsuario;
	}

	public Date getDataSuspensao() {
		return dataSuspensao;
	}

	public void setDataSuspensao(Date dataSuspensao) {
		this.dataSuspensao = dataSuspensao;
	}

	public byte[] getFotoPrincipal() {
		return fotoPrincipal;
	}

	public void setFotoPrincipal(byte[] fotoPrincipal) {
		this.fotoPrincipal = fotoPrincipal;
	}

	public String getCreci() {
		return creci;
	}

	public void setCreci(String creci) {
		this.creci = creci;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getDescSobreMim() {
		return descSobreMim;
	}

	public void setDescSobreMim(String descSobreMim) {
		this.descSobreMim = descSobreMim;
	}

	public Date getDataValidadeAcesso() {
		return dataValidadeAcesso;
	}

	public void setDataValidadeAcesso(Date dataValidadeAcesso) {
		this.dataValidadeAcesso = dataValidadeAcesso;
	}

	public int getPontosNegociacaoImovel() {
		return pontosNegociacaoImovel;
	}

	public void setPontosNegociacaoImovel(int pontosNegociacaoImovel) {
		this.pontosNegociacaoImovel = pontosNegociacaoImovel;
	}

	public int getQuantUsuarioAprovServico() {
		return quantUsuarioAprovServico;
	}

	public void setQuantUsuarioAprovServico(int quantUsuarioAprovServico) {
		this.quantUsuarioAprovServico = quantUsuarioAprovServico;
	}

	public int getQuantUsuarioDesaprovServico() {
		return quantUsuarioDesaprovServico;
	}

	public void setQuantUsuarioDesaprovServico(int quantUsuarioDesaprovServico) {
		this.quantUsuarioDesaprovServico = quantUsuarioDesaprovServico;
	}

	public String getCodigoIdentificacao() {
		return codigoIdentificacao;
	}

	public void setCodigoIdentificacao(String codigoIdentificacao) {
		this.codigoIdentificacao = codigoIdentificacao;
	}

	public String getAtivado() {
		return ativado;
	}

	public void setAtivado(String ativado) {
		this.ativado = ativado;
	}

	public String getHabilitaBusca() {
		return habilitaBusca;
	}

	public void setHabilitaBusca(String habilitaBusca) {
		this.habilitaBusca = habilitaBusca;
	}

	public String getHabilitaDetalhesInfoUsuario() {
		return habilitaDetalhesInfoUsuario;
	}

	public void setHabilitaDetalhesInfoUsuario(String habilitaDetalhesInfoUsuario) {
		this.habilitaDetalhesInfoUsuario = habilitaDetalhesInfoUsuario;
	}

	public int getQuantCadastroImoveis() {
		return quantCadastroImoveis;
	}

	public void setQuantCadastroImoveis(int quantCadastroImoveis) {
		this.quantCadastroImoveis = quantCadastroImoveis;
	}

	public int getQuantMaxFotosPorImovel() {
		return quantMaxFotosPorImovel;
	}

	public void setQuantMaxFotosPorImovel(int quantMaxFotosPorImovel) {
		this.quantMaxFotosPorImovel = quantMaxFotosPorImovel;
	}

	public int getQuantMaxIndicacoesImovel() {
		return quantMaxIndicacoesImovel;
	}

	public void setQuantMaxIndicacoesImovel(int quantMaxIndicacoesImovel) {
		this.quantMaxIndicacoesImovel = quantMaxIndicacoesImovel;
	}

	public int getQuantMaxIndicacoesImovelEmail() {
		return quantMaxIndicacoesImovelEmail;
	}

	public void setQuantMaxIndicacoesImovelEmail(int quantMaxIndicacoesImovelEmail) {
		this.quantMaxIndicacoesImovelEmail = quantMaxIndicacoesImovelEmail;
	}

	public String getHabilitaEnvioSolParceria() {
		return habilitaEnvioSolParceria;
	}

	public void setHabilitaEnvioSolParceria(String habilitaEnvioSolParceria) {
		this.habilitaEnvioSolParceria = habilitaEnvioSolParceria;
	}

	public String getHabilitaEnvioMensagens() {
		return habilitaEnvioMensagens;
	}

	public void setHabilitaEnvioMensagens(String habilitaEnvioMensagens) {
		this.habilitaEnvioMensagens = habilitaEnvioMensagens;
	}

	public int getQuantNegociacoesSucesso() {
		return quantNegociacoesSucesso;
	}

	public void setQuantNegociacoesSucesso(int quantNegociacoesSucesso) {
		this.quantNegociacoesSucesso = quantNegociacoesSucesso;
	}

	public int getQuantCompartilhamentoAceitos() {
		return quantCompartilhamentoAceitos;
	}

	public void setQuantCompartilhamentoAceitos(int quantCompartilhamentoAceitos) {
		this.quantCompartilhamentoAceitos = quantCompartilhamentoAceitos;
	}
    

	public int getQuantImoveisIndicado() {
		return quantImoveisIndicado;
	}

	public void setQuantImoveisIndicado(int quantImoveisIndicado) {
		this.quantImoveisIndicado = quantImoveisIndicado;
	}

	public int getQuantImoveisIndicacoes() {
		return quantImoveisIndicacoes;
	}

	public void setQuantImoveisIndicacoes(int quantImoveisIndicacoes) {
		this.quantImoveisIndicacoes = quantImoveisIndicacoes;
	}

	public String getIndicadoImovel() {
		return indicadoImovel;
	}

	public void setIndicadoImovel(String indicadoImovel) {
		this.indicadoImovel = indicadoImovel;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getFaixaSalarial() {
		return faixaSalarial;
	}

	public void setFaixaSalarial(String faixaSalarial) {
		this.faixaSalarial = faixaSalarial;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getMotivoSuspensao() {
		return motivoSuspensao;
	}

	public void setMotivoSuspensao(String motivoSuspensao) {
		this.motivoSuspensao = motivoSuspensao;
	}
	
	public String getPerfilFmt() {
		if (! StringUtils.isNullOrEmpty(this.perfil))
			return PerfilUsuarioEnum.valueOf(this.perfil).getRotulo();
		else
			return "";
	}

	public void setPerfilFmt(String perfilFmt) {
		this.perfilFmt = perfilFmt;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getHabilitaRecebeSeguidor() {
		return habilitaRecebeSeguidor;
	}

	public void setHabilitaRecebeSeguidor(String habilitaRecebeSeguidor) {
		this.habilitaRecebeSeguidor = habilitaRecebeSeguidor;
	}

	public String getIsSeguindo() {
		return isSeguindo;
	}

	public void setIsSeguindo(String isSeguindo) {
		this.isSeguindo = isSeguindo;
	}

	public long getQuantTotalContatos() {
		return quantTotalContatos;
	}

	public void setQuantTotalContatos(long quantTotalContatos) {
		this.quantTotalContatos = quantTotalContatos;
	}

	public long getQuantTotalSeguidores() {
		return quantTotalSeguidores;
	}

	public void setQuantTotalSeguidores(long quantTotalSeguidores) {
		this.quantTotalSeguidores = quantTotalSeguidores;
	}

	public long getQuantTotalRecomendacoes() {
		return quantTotalRecomendacoes;
	}

	public void setQuantTotalRecomendacoes(long quantTotalRecomendacoes) {
		this.quantTotalRecomendacoes = quantTotalRecomendacoes;
	}

	public long getQuantTotalImoveis() {
		return quantTotalImoveis;
	}

	public void setQuantTotalImoveis(long quantTotalImoveis) {
		this.quantTotalImoveis = quantTotalImoveis;
	}
	
    /**
     * @return the imagemArquivo
     */
    public String getImagemArquivo() {
    	if ( this != null && this.getFotoPrincipal() != null)
    		return Base64.encodeBase64String(this.getFotoPrincipal());
    	else
    		return "";
    }

	public String getCodConfirmacaoAtivacao() {
		return codConfirmacaoAtivacao;
	}

	public void setCodConfirmacaoAtivacao(String codConfirmacaoAtivacao) {
		this.codConfirmacaoAtivacao = codConfirmacaoAtivacao;
	}
	
	
}
