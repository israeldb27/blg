package com.busqueumlugar.form;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.busqueumlugar.enumerador.PerfilUsuarioEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Formapagamento;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Infoservico;
import com.busqueumlugar.model.MensagemAdmin;
import com.busqueumlugar.model.Nota;
import com.busqueumlugar.model.Paramservico;
import com.busqueumlugar.model.Plano;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.Preferencialocalidade;
import com.busqueumlugar.model.Recomendacao;
import com.busqueumlugar.model.Seguidor;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.Select;
import com.mysql.jdbc.StringUtils;

public class UsuarioForm extends BaseForm {
    
    private Long id;
    private String nome = "";
    private String login = "";
    private String password = ""; 
    private String perfil = "";
    private String confirmaPassword = "";    
    private String valorBusca = "";
    private String msgRetorno = "";
    private String habilitaBusca;
    
    private int idEstado; 
    private int idCidade ;  
    private int idBairro ; 
    
    private String cpf = "";
    private String cnpj = "";
    private String uf;  
	private String cidade = "";
    private String estado = "";
    private String bairro = "";
    private String email = "";
    private String telefone = "";
    private String codigoIdentificacao = "";
    private String habilitaDetalhesInfoUsuario;
    private String sexo;
    private Date dataNascimento;
    private String diaNascimento;
    private String mesNascimento;
    private String anoNascimento;
    private List<Select> listaDiaNascimento;
    private List<Select> listaMesNascimento;
    private List<Select> listaAnoNascimento;
    
    private String qtdQuartos = "" ;
    private String qtdGaragem = "" ;
    private String qtdSuites  = "" ;
    
    private String tipoImovel = "";
    private String perfilImovel = "";
    private String acao = "";
    private String statusUsuario = "" ;     
    private Date dataSuspensao; 
    private byte[] fotoPrincipal;    
    private String imagemArquivo = "";
    private String creci = "";
    private Date dataUltimoAcesso;
    private Date dataCadastro;
    private String descSobreMim;    
    private String faixaSalarial;
	private String motivoSuspensao; 
    
    private int quantCadastroImoveis; // indica quantidade maxima de imoveis que o usuario pode cadastrar
    private int quantMaxFotosPorImovel; // indica quantidade maxima de fotos de imoveis que o usuario pode cadastrar
    private int quantMaxIndicacoesImovel; // indica quantidade maxima indicacoes de imoveis que o usuario pode realizar    
    private int quantMaxIndicacoesImovelEmail; // indica quantidade maxima indicacoes por email de imoveis que o usuario pode realizar    
    private int pontosNegociacaoImovel; // indica a quantidade de negociacao com sucesso que um corretor ou imobiliaria realizaram

    private String habilitaEnvioSolParceria;    // habilita para o usuario enviar solicitacoes de parceria para imoveis do sistema
    private String habilitaEnvioMensagens;  // habilita usuario para enviar mensagens para outros usuarios do sistema    
    private String habilitaRecebeSeguidor;
    
    private String tipoVisualizar = "";
    private Date dataValidadeAcesso;    
    private String loginIndex = "";
    private String passwordIndex = "";
    private String ativado;
    
    private int quantUsuarioAprovServico; // indica a quantidade de usuarios que aprovaram o servico do corretor ou imobiliaria
    private int quantUsuarioDesaprovServico; // indica a quantidade de usuarios que desaprovaram o servico do corretor ou imobiliaria
    
    private List<Select> listaEstados;
    private List<Select> listaCidades;
    private List<Select> listaBairros;
    
    private String opcaoOrdenacao = "";
    
    private List<Imovel> listaImoveisUsuario;
    
    private List<Nota> listaNotasUsuario;
    private List<Contato> listaContatosUsuario;
    private List<Preferencialocalidade>listaPreferenciaImoveis;
    private List<Servico> listaServicos;
	private List<Planousuario> listaPlanos;
	private List<Plano> listaPlanosDisponiveis;
	private Long idPlanoSelecionado;
	private List<Paramservico> listaServicosDisponiveis;
	private List<Seguidor> listaSeguidores;
	private List<Recomendacao> listaRecomendacoes;
	private Long idServicoSelecionado;
	private int quantidadeTempo = 0;
	
    private long  quantTotalImoveis  		   	   = 0;
    private long  quantTotalContatos 			   = 0;
    private long  quantNegociacoesSucesso 	   	   = 0;
    private long  quantTotalParcerias 		   	   = 0;
    private long  quantTotalIntermediacoes         = 0;
    private long  quantTotalInteressadosImoveis    = 0;
    private long  quantTotalVisitasImoveis         = 0;
   	private long  quantTotalAprovacoesUsuario      = 0;
    private long  quantTotalDesaprovacoesUsuario   = 0;
    private long  quantTotalSeguidores 		       = 0;
    private long  quantTotalRecomendacoes 	       = 0;
    private long  quantTotalPrefImoveis            = 0;
    private long  quantTotalNotas            	   = 0;
    public String possuiContatoUsuarioSessao = "N";    
    
    private Long idAssinaturaSelecionada;
    private Long idFormapagamentoSelecionada;
    
	private List<Infoservico> listaAssinaturasDisponiveis;
	private List<Formapagamento> listaOpcoesFormaPagamento;	
	private List<MensagemAdmin> listaMensagemAdmin;    
    private List<Usuario> listaBuscaUsuarios;    
	
    private ServicoForm servicoForm = null;    
    private String acessoValido = ""; 	
	private String perfilFmt;
	private String opcaoTipoBuscaUsuarios;
	private String isSeguindoUsuario = "";
	
	private String emailEsqueceu = "";
	private String senhaTemporariaEsqueceu = "";
	private String novaSenhaEsqueceu = "";
	private String confirmarNovaSenhaEsqueceu = "";
	private String cpfCnpjEsqueceuSenha = "";
	
	private String emailIndicaAmigos = "";
	
	
    public String getAcessoValido() {
		return acessoValido;
	}

	public void setAcessoValido(String acessoValido) {
		this.acessoValido = acessoValido;
	}

	public ServicoForm getServicoForm() {
		return servicoForm;
	}

	public void setServicoForm(ServicoForm servicoForm) {
		this.servicoForm = servicoForm;
	}

	public List<Formapagamento> getListaOpcoesFormaPagamento() {
		return listaOpcoesFormaPagamento;
	}

	public void setListaOpcoesFormaPagamento(
			List<Formapagamento> listaOpcoesFormaPagamento) {
		this.listaOpcoesFormaPagamento = listaOpcoesFormaPagamento;
	}
    
    public Long getIdFormapagamentoSelecionada() {
		return idFormapagamentoSelecionada;
	}

	public void setIdFormapagamentoSelecionada(Long idFormapagamentoSelecionada) {
		this.idFormapagamentoSelecionada = idFormapagamentoSelecionada;
	}

    
    public Long getIdAssinaturaSelecionada() {
		return idAssinaturaSelecionada;
	}

	public void setIdAssinaturaSelecionada(Long idAssinaturaSelecionada) {
		this.idAssinaturaSelecionada = idAssinaturaSelecionada;
	}
	
    public List<Infoservico> getListaAssinaturasDisponiveis() {
		return listaAssinaturasDisponiveis;
	}

	public void setListaAssinaturasDisponiveis(
			List<Infoservico> listaAssinaturasDisponiveis) {
		this.listaAssinaturasDisponiveis = listaAssinaturasDisponiveis;
	}
    
    public List<Usuario> getListaBuscaUsuarios() {
		return listaBuscaUsuarios;
	}

	public void setListaBuscaUsuarios(List<Usuario> listaBuscaUsuarios) {
		this.listaBuscaUsuarios = listaBuscaUsuarios;
	}

	public List<MensagemAdmin> getListaMensagemAdmin() {
		return listaMensagemAdmin;
	}

	public void setListaMensagemAdmin(List<MensagemAdmin> listaMensagemAdmin) {
		this.listaMensagemAdmin = listaMensagemAdmin;
	}
    
	public String getPossuiContatoUsuarioSessao() {
		return possuiContatoUsuarioSessao;
	}

	public void setPossuiContatoUsuarioSessao(String possuiContatoUsuarioSessao) {
		this.possuiContatoUsuarioSessao = possuiContatoUsuarioSessao;
	}

	public long getQuantTotalImoveis() {
		return quantTotalImoveis;
	}

	public void setQuantTotalImoveis(long quantTotalImoveis) {
		this.quantTotalImoveis = quantTotalImoveis;
	}


	public void setQuantNegociacoesSucesso(int quantNegociacoesSucesso) {
		this.quantNegociacoesSucesso = quantNegociacoesSucesso;
	}

	public long getQuantTotalParcerias() {
		return quantTotalParcerias;
	}

	public void setQuantTotalParcerias(long quantTotalParcerias) {
		this.quantTotalParcerias = quantTotalParcerias;
	}

	public long getQuantTotalIntermediacoes() {
		return quantTotalIntermediacoes;
	}

	public void setQuantTotalIntermediacoes(long quantTotalIntermediacoes) {
		this.quantTotalIntermediacoes = quantTotalIntermediacoes;
	}
	

	public void setQuantTotalDesaprovacoesUsuario(int quantTotalDesaprovacoesUsuario) {
		this.quantTotalDesaprovacoesUsuario = quantTotalDesaprovacoesUsuario;
	}

	public List<Nota> getListaNotasUsuario() {
		return listaNotasUsuario;
	}

	public void setListaNotasUsuario(List<Nota> listaNotasUsuario) {
		this.listaNotasUsuario = listaNotasUsuario;
	}

	public List<Contato> getListaContatosUsuario() {
		return listaContatosUsuario;
	}

	public void setListaContatosUsuario(List<Contato> listaContatosUsuario) {
		this.listaContatosUsuario = listaContatosUsuario;
	}

	public List<Preferencialocalidade> getListaPreferenciaImoveis() {
		return listaPreferenciaImoveis;
	}

	public void setListaPreferenciaImoveis(
			List<Preferencialocalidade> listaPreferenciaImoveis) {
		this.listaPreferenciaImoveis = listaPreferenciaImoveis;
	}

	public String getOpcaoOrdenacao() {
		return opcaoOrdenacao;
	}

	public void setOpcaoOrdenacao(String opcaoOrdenacao) {
		this.opcaoOrdenacao = opcaoOrdenacao;
	}

		/**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmaPassword
     */
    public String getConfirmaPassword() {
        return confirmaPassword;
    }

    /**
     * @param confirmaPassword the confirmaPassword to set
     */
    public void setConfirmaPassword(String confirmaPassword) {
        this.confirmaPassword = confirmaPassword;
    }

    /**
     * @return the valorBusca
     */
    public String getValorBusca() {
        return valorBusca;
    }

    /**
     * @param valorBusca the valorBusca to set
     */
    public void setValorBusca(String valorBusca) {
        this.valorBusca = valorBusca;
    }

    /**
     * @return the msgRetorno
     */
    public String getMsgRetorno() {
        return msgRetorno;
    }

    /**
     * @param msgRetorno the msgRetorno to set
     */
    public void setMsgRetorno(String msgRetorno) {
        this.msgRetorno = msgRetorno;
    }

    /**
     * @return the perfil
     */
    public String getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the idEstado
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * @param idEstado the idEstado to set
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * @return the idCidade
     */
    public int getIdCidade() {
        return idCidade;
    }

    /**
     * @param idCidade the idCidade to set
     */
    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    /**
     * @return the idBairro
     */
    public int getIdBairro() {
        return idBairro;
    }

    /**
     * @param idBairro the idBairro to set
     */
    public void setIdBairro(int idBairro) {
        this.idBairro = idBairro;
    }

    /**
     * @return the tipoImovel
     */
    public String getTipoImovel() {
        return tipoImovel;
    }

    /**
     * @param tipoImovel the tipoImovel to set
     */
    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    /**
     * @return the acao
     */
    public String getAcao() {
        return acao;
    }

    /**
     * @param acao the acao to set
     */
    public void setAcao(String acao) {
        this.acao = acao;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the statusUsuario
     */
    public String getStatusUsuario() {
        return statusUsuario;
    }

    /**
     * @param statusUsuario the statusUsuario to set
     */
    public void setStatusUsuario(String statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    /**
     * @return the dataSuspensao
     */
    public Date getDataSuspensao() {
        return dataSuspensao;
    }

    /**
     * @param dataSuspensao the dataSuspensao to set
     */
    public void setDataSuspensao(Date dataSuspensao) {
        this.dataSuspensao = dataSuspensao;
    }

    /**
     * @return the fotoPrincipal
     */
    public byte[] getFotoPrincipal() {
        return fotoPrincipal;
    }

    /**
     * @param fotoPrincipal the fotoPrincipal to set
     */
    public void setFotoPrincipal(byte[] fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    /**
     * @return the imagemArquivo
     */
    public String getImagemArquivo() {
        return imagemArquivo;
    }

    /**
     * @param imagemArquivo the imagemArquivo to set
     */
    public void setImagemArquivo(String imagemArquivo) {
        this.imagemArquivo = imagemArquivo;
    }

    /**
     * @return the creci
     */
    public String getCreci() {
        return creci;
    }

    /**
     * @param creci the creci to set
     */
    public void setCreci(String creci) {
        this.creci = creci;
    }

    /**
     * @return the dataUltimoAcesso
     */
    public Date getDataUltimoAcesso() {
        return dataUltimoAcesso;
    }

    /**
     * @param dataUltimoAcesso the dataUltimoAcesso to set
     */
    public void setDataUltimoAcesso(Date dataUltimoAcesso) {
        this.dataUltimoAcesso = dataUltimoAcesso;
    }

    /**
     * @return the dataCadastro
     */
    public Date getDataCadastro() {
        return dataCadastro;
    }

    /**
     * @param dataCadastro the dataCadastro to set
     */
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    /**
     * @return the descSobreMim
     */
    public String getDescSobreMim() {
        return descSobreMim;
    }

    /**
     * @param descSobreMim the descSobreMim to set
     */
    public void setDescSobreMim(String descSobreMim) {
        this.descSobreMim = descSobreMim;
    }

    /**
     * @return the emailEsqueceu
     */
    public String getEmailEsqueceu() {
        return emailEsqueceu;
    }

    /**
     * @param emailEsqueceu the emailEsqueceu to set
     */
    public void setEmailEsqueceu(String emailEsqueceu) {
        this.emailEsqueceu = emailEsqueceu;
    }

    /**
     * @return the quantCadastroImoveis
     */
    public int getQuantCadastroImoveis() {
        return quantCadastroImoveis;
    }

    /**
     * @param quantCadastroImoveis the quantCadastroImoveis to set
     */
    public void setQuantCadastroImoveis(int quantCadastroImoveis) {
        this.quantCadastroImoveis = quantCadastroImoveis;
    }

    /**
     * @return the quantMaxFotosPorImovel
     */
    public int getQuantMaxFotosPorImovel() {
        return quantMaxFotosPorImovel;
    }

    /**
     * @param quantMaxFotosPorImovel the quantMaxFotosPorImovel to set
     */
    public void setQuantMaxFotosPorImovel(int quantMaxFotosPorImovel) {
        this.quantMaxFotosPorImovel = quantMaxFotosPorImovel;
    }

    /**
     * @return the quantMaxIndicacoesImovel
     */
    public int getQuantMaxIndicacoesImovel() {
        return quantMaxIndicacoesImovel;
    }

    /**
     * @param quantMaxIndicacoesImovel the quantMaxIndicacoesImovel to set
     */
    public void setQuantMaxIndicacoesImovel(int quantMaxIndicacoesImovel) {
        this.quantMaxIndicacoesImovel = quantMaxIndicacoesImovel;
    }

    /**
     * @return the quantMaxIndicacoesImovelEmail
     */
    public int getQuantMaxIndicacoesImovelEmail() {
        return quantMaxIndicacoesImovelEmail;
    }

    /**
     * @param quantMaxIndicacoesImovelEmail the quantMaxIndicacoesImovelEmail to set
     */
    public void setQuantMaxIndicacoesImovelEmail(int quantMaxIndicacoesImovelEmail) {
        this.quantMaxIndicacoesImovelEmail = quantMaxIndicacoesImovelEmail;
    }

    /**
     * @return the habilitaEnvioMensagens
     */
    public String getHabilitaEnvioMensagens() {
        return habilitaEnvioMensagens;
    }

    /**
     * @param habilitaEnvioMensagens the habilitaEnvioMensagens to set
     */
    public void setHabilitaEnvioMensagens(String habilitaEnvioMensagens) {
        this.habilitaEnvioMensagens = habilitaEnvioMensagens;
    }

    /**
     * @return the habilitaEnvioSolParceria
     */
    public String getHabilitaEnvioSolParceria() {
        return habilitaEnvioSolParceria;
    }

    /**
     * @param habilitaEnvioSolParceria the habilitaEnvioSolParceria to set
     */
    public void setHabilitaEnvioSolParceria(String habilitaEnvioSolParceria) {
        this.habilitaEnvioSolParceria = habilitaEnvioSolParceria;
    }

    /**
     * @return the tipoVisualizar
     */
    public String getTipoVisualizar() {
        return tipoVisualizar;
    }

    /**
     * @param tipoVisualizar the tipoVisualizar to set
     */
    public void setTipoVisualizar(String tipoVisualizar) {
        this.tipoVisualizar = tipoVisualizar;
    }

    /**
     * @return the dataValidadeAcesso
     */
    public Date getDataValidadeAcesso() {
        return dataValidadeAcesso;
    }

    /**
     * @param dataValidadeAcesso the dataValidadeAcesso to set
     */
    public void setDataValidadeAcesso(Date dataValidadeAcesso) {
        this.dataValidadeAcesso = dataValidadeAcesso;
    }

    /**
     * @return the loginIndex
     */
    public String getLoginIndex() {
        return loginIndex;
    }

    /**
     * @param loginIndex the loginIndex to set
     */
    public void setLoginIndex(String loginIndex) {
        this.loginIndex = loginIndex;
    }

    /**
     * @return the passwordIndex
     */
    public String getPasswordIndex() {
        return passwordIndex;
    }

    /**
     * @param passwordIndex the passwordIndex to set
     */
    public void setPasswordIndex(String passwordIndex) {
        this.passwordIndex = passwordIndex;
    }

    /**
     * @return the pontosNegociacaoImovel
     */
    public int getPontosNegociacaoImovel() {
        return pontosNegociacaoImovel;
    }

    /**
     * @param pontosNegociacaoImovel the pontosNegociacaoImovel to set
     */
    public void setPontosNegociacaoImovel(int pontosNegociacaoImovel) {
        this.pontosNegociacaoImovel = pontosNegociacaoImovel;
    }

    /**
     * @return the quantUsuarioAprovServico
     */
    public int getQuantUsuarioAprovServico() {
        return quantUsuarioAprovServico;
    }

    /**
     * @param quantUsuarioAprovServico the quantUsuarioAprovServico to set
     */
    public void setQuantUsuarioAprovServico(int quantUsuarioAprovServico) {
        this.quantUsuarioAprovServico = quantUsuarioAprovServico;
    }

    /**
     * @return the quantUsuarioDesaprovServico
     */
    public int getQuantUsuarioDesaprovServico() {
        return quantUsuarioDesaprovServico;
    }

    /**
     * @param quantUsuarioDesaprovServico the quantUsuarioDesaprovServico to set
     */
    public void setQuantUsuarioDesaprovServico(int quantUsuarioDesaprovServico) {
        this.quantUsuarioDesaprovServico = quantUsuarioDesaprovServico;
    }

    /**
     * @return the codigoIdentificacao
     */
    public String getCodigoIdentificacao() {
        return codigoIdentificacao;
    }

    /**
     * @param codigoIdentificacao the codigoIdentificacao to set
     */
    public void setCodigoIdentificacao(String codigoIdentificacao) {
        this.codigoIdentificacao = codigoIdentificacao;
    }

    /**
     * @return the ativado
     */
    public String getAtivado() {
        return ativado;
    }

    /**
     * @param ativado the ativado to set
     */
    public void setAtivado(String ativado) {
        this.ativado = ativado;
    }

    /**
     * @return the habilitaBusca
     */
    public String getHabilitaBusca() {
        return habilitaBusca;
    }

    /**
     * @param habilitaBusca the habilitaBusca to set
     */
    public void setHabilitaBusca(String habilitaBusca) {
        this.habilitaBusca = habilitaBusca;
    }

    /**
     * @return the habilitaDetalhesInfoUsuario
     */
    public String getHabilitaDetalhesInfoUsuario() {
        return habilitaDetalhesInfoUsuario;
    }

    /**
     * @param habilitaDetalhesInfoUsuario the habilitaDetalhesInfoUsuario to set
     */
    public void setHabilitaDetalhesInfoUsuario(String habilitaDetalhesInfoUsuario) {
        this.habilitaDetalhesInfoUsuario = habilitaDetalhesInfoUsuario;
    }

    /**
     * @return the emailIndicaAmigos
     */
    public String getEmailIndicaAmigos() {
        return emailIndicaAmigos;
    }

    /**
     * @param emailIndicaAmigos the emailIndicaAmigos to set
     */
    public void setEmailIndicaAmigos(String emailIndicaAmigos) {
        this.emailIndicaAmigos = emailIndicaAmigos;
    }
    
    public List<Select> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<Select> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public List<Select> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<Select> listaCidades) {
		this.listaCidades = listaCidades;
	}

	public List<Select> getListaBairros() {
		return listaBairros;
	}

	public void setListaBairros(List<Select> listaBairros) {
		this.listaBairros = listaBairros;
	}
	
	public String getCnpj() {
		return cnpj;
    }

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

	public String getDiaNascimento() {
		return diaNascimento;
	}

	public void setDiaNascimento(String diaNascimento) {
		this.diaNascimento = diaNascimento;
	}

	public String getMesNascimento() {
		return mesNascimento;
	}

	public void setMesNascimento(String mesNascimento) {
		this.mesNascimento = mesNascimento;
	}

	public String getAnoNascimento() {
		return anoNascimento;
	}

	public void setAnoNascimento(String anoNascimento) {
		this.anoNascimento = anoNascimento;
	}

	public List<Select> getListaDiaNascimento() {
		return listaDiaNascimento;
	}

	public void setListaDiaNascimento(List<Select> listaDiaNascimento) {
		this.listaDiaNascimento = listaDiaNascimento;
	}

	public List<Select> getListaMesNascimento() {
		return listaMesNascimento;
	}

	public void setListaMesNascimento(List<Select> listaMesNascimento) {
		this.listaMesNascimento = listaMesNascimento;
	}

	public List<Select> getListaAnoNascimento() {
		return listaAnoNascimento;
	}

	public void setListaAnoNascimento(List<Select> listaAnoNascimento) {
		this.listaAnoNascimento = listaAnoNascimento;
	}
	
	public List<Servico> getListaServicos() {
		return listaServicos;
	}

	public void setListaServicos(List<Servico> listaServicos) {
		this.listaServicos = listaServicos;
	}
	
	public List<Planousuario> getListaPlanos() {
		return listaPlanos;
	}

	public void setListaPlanos(List<Planousuario> listaPlanos) {
		this.listaPlanos = listaPlanos;
	}

	public List<Plano> getListaPlanosDisponiveis() {
		return listaPlanosDisponiveis;
	}

	public void setListaPlanosDisponiveis(List<Plano> listaPlanosDisponiveis) {
		this.listaPlanosDisponiveis = listaPlanosDisponiveis;
	}

	public Long getIdPlanoSelecionado() {
		return idPlanoSelecionado;
	}

	public void setIdPlanoSelecionado(Long idPlanoSelecionado) {
		this.idPlanoSelecionado = idPlanoSelecionado;
	}

	public List<Paramservico> getListaServicosDisponiveis() {
		return listaServicosDisponiveis;
	}

	public void setListaServicosDisponiveis(
			List<Paramservico> listaServicosDisponiveis) {
		this.listaServicosDisponiveis = listaServicosDisponiveis;
	}

	public Long getIdServicoSelecionado() {
		return idServicoSelecionado;
	}

	public void setIdServicoSelecionado(Long idServicoSelecionado) {
		this.idServicoSelecionado = idServicoSelecionado;
	}

	public int getQuantidadeTempo() {
		return quantidadeTempo;
	}

	public void setQuantidadeTempo(int quantidadeTempo) {
		this.quantidadeTempo = quantidadeTempo;
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

	public String getQtdQuartos() {
		return qtdQuartos;
	}

	public void setQtdQuartos(String qtdQuartos) {
		this.qtdQuartos = qtdQuartos;
	}

	public String getQtdGaragem() {
		return qtdGaragem;
	}

	public void setQtdGaragem(String qtdGaragem) {
		this.qtdGaragem = qtdGaragem;
	}

	public String getQtdSuites() {
		return qtdSuites;
	}

	public void setQtdSuites(String qtdSuites) {
		this.qtdSuites = qtdSuites;
	}
	
	public String getOpcaoTipoBuscaUsuarios() {
		return opcaoTipoBuscaUsuarios;
	}

	public void setOpcaoTipoBuscaUsuarios(String opcaoTipoBuscaUsuarios) {
		this.opcaoTipoBuscaUsuarios = opcaoTipoBuscaUsuarios;
	}

	public String getPerfilImovel() {
		return perfilImovel;
	}

	public void setPerfilImovel(String perfilImovel) {
		this.perfilImovel = perfilImovel;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<Imovel> getListaImoveisUsuario() {
		return listaImoveisUsuario;
	}

	public void setListaImoveisUsuario(List<Imovel> listaImoveisUsuario) {
		this.listaImoveisUsuario = listaImoveisUsuario;
	}

	public String getIsSeguindoUsuario() {
		return isSeguindoUsuario;
	}

	public void setIsSeguindoUsuario(String isSeguindoUsuario) {
		this.isSeguindoUsuario = isSeguindoUsuario;
	}

	public List<Seguidor> getListaSeguidores() {
		return listaSeguidores;
	}

	public void setListaSeguidores(List<Seguidor> listaSeguidores) {
		this.listaSeguidores = listaSeguidores;
	}
	

	public List<Recomendacao> getListaRecomendacoes() {
		return listaRecomendacoes;
	}

	public void setListaRecomendacoes(List<Recomendacao> listaRecomendacoes) {
		this.listaRecomendacoes = listaRecomendacoes;
	}	

	public String getHabilitaRecebeSeguidor() {
		return habilitaRecebeSeguidor;
	}

	public void setHabilitaRecebeSeguidor(String habilitaRecebeSeguidor) {
		this.habilitaRecebeSeguidor = habilitaRecebeSeguidor;
	}

	public long getQuantTotalContatos() {
		return quantTotalContatos;
	}

	public void setQuantTotalContatos(long quantTotalContatos) {
		this.quantTotalContatos = quantTotalContatos;
	}

	public long getQuantNegociacoesSucesso() {
		return quantNegociacoesSucesso;
	}

	public void setQuantNegociacoesSucesso(long quantNegociacoesSucesso) {
		this.quantNegociacoesSucesso = quantNegociacoesSucesso;
	}

	public long getQuantTotalInteressadosImoveis() {
		return quantTotalInteressadosImoveis;
	}

	public void setQuantTotalInteressadosImoveis(long quantTotalInteressadosImoveis) {
		this.quantTotalInteressadosImoveis = quantTotalInteressadosImoveis;
	}

	public long getQuantTotalVisitasImoveis() {
		return quantTotalVisitasImoveis;
	}

	public void setQuantTotalVisitasImoveis(long quantTotalVisitasImoveis) {
		this.quantTotalVisitasImoveis = quantTotalVisitasImoveis;
	}

	public long getQuantTotalAprovacoesUsuario() {
		return quantTotalAprovacoesUsuario;
	}

	public void setQuantTotalAprovacoesUsuario(long quantTotalAprovacoesUsuario) {
		this.quantTotalAprovacoesUsuario = quantTotalAprovacoesUsuario;
	}

	public long getQuantTotalDesaprovacoesUsuario() {
		return quantTotalDesaprovacoesUsuario;
	}

	public void setQuantTotalDesaprovacoesUsuario(
			long quantTotalDesaprovacoesUsuario) {
		this.quantTotalDesaprovacoesUsuario = quantTotalDesaprovacoesUsuario;
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

	public long getQuantTotalPrefImoveis() {
		return quantTotalPrefImoveis;
	}

	public void setQuantTotalPrefImoveis(long quantTotalPrefImoveis) {
		this.quantTotalPrefImoveis = quantTotalPrefImoveis;
	}

	public long getQuantTotalNotas() {
		return quantTotalNotas;
	}

	public void setQuantTotalNotas(long quantTotalNotas) {
		this.quantTotalNotas = quantTotalNotas;
	}

	public String getSenhaTemporariaEsqueceu() {
		return senhaTemporariaEsqueceu;
	}

	public void setSenhaTemporariaEsqueceu(String senhaTemporariaEsqueceu) {
		this.senhaTemporariaEsqueceu = senhaTemporariaEsqueceu;
	}

	public String getNovaSenhaEsqueceu() {
		return novaSenhaEsqueceu;
	}

	public void setNovaSenhaEsqueceu(String novaSenhaEsqueceu) {
		this.novaSenhaEsqueceu = novaSenhaEsqueceu;
	}

	public String getConfirmarNovaSenhaEsqueceu() {
		return confirmarNovaSenhaEsqueceu;
	}

	public void setConfirmarNovaSenhaEsqueceu(String confirmarNovaSenhaEsqueceu) {
		this.confirmarNovaSenhaEsqueceu = confirmarNovaSenhaEsqueceu;
	}

	public String getCpfCnpjEsqueceuSenha() {
		return cpfCnpjEsqueceuSenha;
	}

	public void setCpfCnpjEsqueceuSenha(String cpfCnpjEsqueceuSenha) {
		this.cpfCnpjEsqueceuSenha = cpfCnpjEsqueceuSenha;
	}    
}
