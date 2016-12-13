package com.busqueumlugar.form;

import java.util.Date;

/**
 *
 * @author Israel
 */
public class PerfilForm {
    
    private Long idPerfil;
    private String login;
    private String nome;
    private String perfil;
    private Date dataCadastroPerfil;
    private Date dataUltimoAceso;
    private String estado;
    private String cidade;
    private String bairro;
    private String telefone;
    private String email;
    private String imagemArquivo;
    private String statusNegociacao;
    private String aceitaParceria = "";
    private String cpf;
    private String creci;
    private String codigoIdentificacao;
    private String habilitaDetalhesInfoUsuario = "";
    
    private String opcaoServicosDisponivelImovel = "";
    private String opcaoServicosDisponivelImovelTempo = "";
    private String opcaoQuantidadeServicosDisponivel = "";
    private String habilitaBotaoBuscar = "";
    
    private Long idParamServico;
    
    private String opcaoOrdenacao = "";
    
    private int idEstado;  
    private int idCidade;
    private int idBairro;
    
    private String estadoImovel = "";
    private String cidadeImovel = "";
    private String bairroImovel = "";
    
    
    private String tipoImovel = "";
    private String acao = "";
    private String perfilImovel = "";
    private String valorCodIdentificacao = "";
    
    private int quantQuartos;      
    private int quantGaragem;      
    private int quantSuites;
    
    private String qtdQuartos = "";
    private String qtdGaragem = "";
    private String qtdSuites = "";
    
    private int valorImovelMin = 0 ; // campos usados para filtrar por valor
    private int valorImovelMax = 0 ;
    private String acaoMenu = "";
    private String descSobreMim = "";

    
    // campos usados apenas como estatistica ao visualizar o perfil de usuario
    private int quantTotalImoveisCadastrados = 0; 
    private int quantPropostasRecebidasImoveis = 0;    
    private int quantVisitasRecebidasImoveis = 0;
    private int quantUsuariosInteressados = 0;
    private int quantContatosEstabelecidos = 0;
    private int pontosNegociacaoImovel = 0;
    private int quantIntermediacoesSucesso = 0;
    private int quantParceriasSucesso = 0;
    
    // este campo indica se o usuario poderá aprovar ou desaprovar o servviço do corretor ou imobiliaria
    // o usuario poderia aprovar ou desaprovar desde que tenha fechado negocio imovel com o corretor ou imobiliaria
    private String habilitaAprovarUsuarioServico = ""; 
    private int quantUsuarioAprovServico = 0; 
    private int quantUsuarioDesaprovServico = 0;
    private String opcaoAvaliaUsuarioServico = "";   
    
    
    /**
     * @return the idPerfil
     */
    public Long getIdPerfil() {
        return idPerfil;
    }

    /**
     * @param idPerfil the idPerfil to set
     */
    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
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
     * @return the dataCadastroPerfil
     */
    public Date getDataCadastroPerfil() {
        return dataCadastroPerfil;
    }

    /**
     * @param dataCadastroPerfil the dataCadastroPerfil to set
     */
    public void setDataCadastroPerfil(Date dataCadastroPerfil) {
        this.dataCadastroPerfil = dataCadastroPerfil;
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
     * @return the opcaoServicosDisponivelImovel
     */
    public String getOpcaoServicosDisponivelImovel() {
        return opcaoServicosDisponivelImovel;
    }

    /**
     * @param opcaoServicosDisponivelImovel the opcaoServicosDisponivelImovel to set
     */
    public void setOpcaoServicosDisponivelImovel(String opcaoServicosDisponivelImovel) {
        this.opcaoServicosDisponivelImovel = opcaoServicosDisponivelImovel;
    }

    /**
     * @return the opcaoServicosDisponivelImovelTempo
     */
    public String getOpcaoServicosDisponivelImovelTempo() {
        return opcaoServicosDisponivelImovelTempo;
    }

    /**
     * @param opcaoServicosDisponivelImovelTempo the opcaoServicosDisponivelImovelTempo to set
     */
    public void setOpcaoServicosDisponivelImovelTempo(String opcaoServicosDisponivelImovelTempo) {
        this.opcaoServicosDisponivelImovelTempo = opcaoServicosDisponivelImovelTempo;
    }

    /**
     * @return the idParamServico
     */
    public Long getIdParamServico() {
        return idParamServico;
    }

    /**
     * @param idParamServico the idParamServico to set
     */
    public void setIdParamServico(Long idParamServico) {
        this.idParamServico = idParamServico;
    }

    /**
     * @return the opcaoOrdenacao
     */
    public String getOpcaoOrdenacao() {
        return opcaoOrdenacao;
    }

    /**
     * @param opcaoOrdenacao the opcaoOrdenacao to set
     */
    public void setOpcaoOrdenacao(String opcaoOrdenacao) {
        this.opcaoOrdenacao = opcaoOrdenacao;
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
     * @return the perfilImovel
     */
    public String getPerfilImovel() {
        return perfilImovel;
    }

    /**
     * @param perfilImovel the perfilImovel to set
     */
    public void setPerfilImovel(String perfilImovel) {
        this.perfilImovel = perfilImovel;
    }

    /**
     * @return the quantQuartos
     */
    public int getQuantQuartos() {
        return quantQuartos;
    }

    /**
     * @param quantQuartos the quantQuartos to set
     */
    public void setQuantQuartos(int quantQuartos) {
        this.quantQuartos = quantQuartos;
    }

    /**
     * @return the quantGaragem
     */
    public int getQuantGaragem() {
        return quantGaragem;
    }

    /**
     * @param quantGaragem the quantGaragem to set
     */
    public void setQuantGaragem(int quantGaragem) {
        this.quantGaragem = quantGaragem;
    }

    /**
     * @return the quantSuites
     */
    public int getQuantSuites() {
        return quantSuites;
    }

    /**
     * @param quantSuites the quantSuites to set
     */
    public void setQuantSuites(int quantSuites) {
        this.quantSuites = quantSuites;
    }

    /**
     * @return the valorCodIdentificacao
     */
    public String getValorCodIdentificacao() {
        return valorCodIdentificacao;
    }

    /**
     * @param valorCodIdentificacao the valorCodIdentificacao to set
     */
    public void setValorCodIdentificacao(String valorCodIdentificacao) {
        this.valorCodIdentificacao = valorCodIdentificacao;
    }

    /**
     * @return the valorImovelMin
     */
    public int getValorImovelMin() {
        return valorImovelMin;
    }

    /**
     * @param valorImovelMin the valorImovelMin to set
     */
    public void setValorImovelMin(int valorImovelMin) {
        this.valorImovelMin = valorImovelMin;
    }

    /**
     * @return the valorImovelMax
     */
    public int getValorImovelMax() {
        return valorImovelMax;
    }

    /**
     * @param valorImovelMax the valorImovelMax to set
     */
    public void setValorImovelMax(int valorImovelMax) {
        this.valorImovelMax = valorImovelMax;
    }

    /**
     * @return the dataUltimoAceso
     */
    public Date getDataUltimoAceso() {
        return dataUltimoAceso;
    }

    /**
     * @param dataUltimoAceso the dataUltimoAceso to set
     */
    public void setDataUltimoAceso(Date dataUltimoAceso) {
        this.dataUltimoAceso = dataUltimoAceso;
    }

    /**
     * @return the acaoMenu
     */
    public String getAcaoMenu() {
        return acaoMenu;
    }

    /**
     * @param acaoMenu the acaoMenu to set
     */
    public void setAcaoMenu(String acaoMenu) {
        this.acaoMenu = acaoMenu;
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
     * @return the quantTotalImoveisCadastrados
     */
    public int getQuantTotalImoveisCadastrados() {
        return quantTotalImoveisCadastrados;
    }

    /**
     * @param quantTotalImoveisCadastrados the quantTotalImoveisCadastrados to set
     */
    public void setQuantTotalImoveisCadastrados(int quantTotalImoveisCadastrados) {
        this.quantTotalImoveisCadastrados = quantTotalImoveisCadastrados;
    }

    /**
     * @return the quantPropostasRecebidasImoveis
     */
    public int getQuantPropostasRecebidasImoveis() {
        return quantPropostasRecebidasImoveis;
    }

    /**
     * @param quantPropostasRecebidasImoveis the quantPropostasRecebidasImoveis to set
     */
    public void setQuantPropostasRecebidasImoveis(int quantPropostasRecebidasImoveis) {
        this.quantPropostasRecebidasImoveis = quantPropostasRecebidasImoveis;
    }

    /**
     * @return the quantVisitasRecebidasImoveis
     */
    public int getQuantVisitasRecebidasImoveis() {
        return quantVisitasRecebidasImoveis;
    }

    /**
     * @param quantVisitasRecebidasImoveis the quantVisitasRecebidasImoveis to set
     */
    public void setQuantVisitasRecebidasImoveis(int quantVisitasRecebidasImoveis) {
        this.quantVisitasRecebidasImoveis = quantVisitasRecebidasImoveis;
    }

    /**
     * @return the quantUsuariosInteressados
     */
    public int getQuantUsuariosInteressados() {
        return quantUsuariosInteressados;
    }

    /**
     * @param quantUsuariosInteressados the quantUsuariosInteressados to set
     */
    public void setQuantUsuariosInteressados(int quantUsuariosInteressados) {
        this.quantUsuariosInteressados = quantUsuariosInteressados;
    }

    /**
     * @return the quantContatosEstabelecidos
     */
    public int getQuantContatosEstabelecidos() {
        return quantContatosEstabelecidos;
    }

    /**
     * @param quantContatosEstabelecidos the quantContatosEstabelecidos to set
     */
    public void setQuantContatosEstabelecidos(int quantContatosEstabelecidos) {
        this.quantContatosEstabelecidos = quantContatosEstabelecidos;
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
     * @return the habilitaAprovarUsuarioServico
     */
    public String getHabilitaAprovarUsuarioServico() {
        return habilitaAprovarUsuarioServico;
    }

    /**
     * @param habilitaAprovarUsuarioServico the habilitaAprovarUsuarioServico to set
     */
    public void setHabilitaAprovarUsuarioServico(String habilitaAprovarUsuarioServico) {
        this.habilitaAprovarUsuarioServico = habilitaAprovarUsuarioServico;
    }

    /**
     * @return the opcaoAvaliaUsuarioServico
     */
    public String getOpcaoAvaliaUsuarioServico() {
        return opcaoAvaliaUsuarioServico;
    }

    /**
     * @param opcaoAvaliaUsuarioServico the opcaoAvaliaUsuarioServico to set
     */
    public void setOpcaoAvaliaUsuarioServico(String opcaoAvaliaUsuarioServico) {
        this.opcaoAvaliaUsuarioServico = opcaoAvaliaUsuarioServico;
    }

    /**
     * @return the opcaoQuantidadeServicosDisponivel
     */
    public String getOpcaoQuantidadeServicosDisponivel() {
        return opcaoQuantidadeServicosDisponivel;
    }

    /**
     * @param opcaoQuantidadeServicosDisponivel the opcaoQuantidadeServicosDisponivel to set
     */
    public void setOpcaoQuantidadeServicosDisponivel(String opcaoQuantidadeServicosDisponivel) {
        this.opcaoQuantidadeServicosDisponivel = opcaoQuantidadeServicosDisponivel;
    }

    /**
     * @return the quantIntermediacoesSucesso
     */
    public int getQuantIntermediacoesSucesso() {
        return quantIntermediacoesSucesso;
    }

    /**
     * @param quantIntermediacoesSucesso the quantIntermediacoesSucesso to set
     */
    public void setQuantIntermediacoesSucesso(int quantIntermediacoesSucesso) {
        this.quantIntermediacoesSucesso = quantIntermediacoesSucesso;
    }

    /**
     * @return the quantParceriasSucesso
     */
    public int getQuantParceriasSucesso() {
        return quantParceriasSucesso;
    }

    /**
     * @param quantParceriasSucesso the quantParceriasSucesso to set
     */
    public void setQuantParceriasSucesso(int quantParceriasSucesso) {
        this.quantParceriasSucesso = quantParceriasSucesso;
    }

    /**
     * @return the statusNegociacao
     */
    public String getStatusNegociacao() {
        return statusNegociacao;
    }

    /**
     * @param statusNegociacao the statusNegociacao to set
     */
    public void setStatusNegociacao(String statusNegociacao) {
        this.statusNegociacao = statusNegociacao;
    }

    /**
     * @return the habilitaBotaoBuscar
     */
    public String getHabilitaBotaoBuscar() {
        return habilitaBotaoBuscar;
    }

    /**
     * @param habilitaBotaoBuscar the habilitaBotaoBuscar to set
     */
    public void setHabilitaBotaoBuscar(String habilitaBotaoBuscar) {
        this.habilitaBotaoBuscar = habilitaBotaoBuscar;
    }

    /**
     * @return the estadoImovel
     */
    public String getEstadoImovel() {
        return estadoImovel;
    }

    /**
     * @param estadoImovel the estadoImovel to set
     */
    public void setEstadoImovel(String estadoImovel) {
        this.estadoImovel = estadoImovel;
    }

    /**
     * @return the cidadeImovel
     */
    public String getCidadeImovel() {
        return cidadeImovel;
    }

    /**
     * @param cidadeImovel the cidadeImovel to set
     */
    public void setCidadeImovel(String cidadeImovel) {
        this.cidadeImovel = cidadeImovel;
    }

    /**
     * @return the bairroImovel
     */
    public String getBairroImovel() {
        return bairroImovel;
    }

    /**
     * @param bairroImovel the bairroImovel to set
     */
    public void setBairroImovel(String bairroImovel) {
        this.bairroImovel = bairroImovel;
    }

    /**
     * @return the qtdQuartos
     */
    public String getQtdQuartos() {
        return qtdQuartos;
    }

    /**
     * @param qtdQuartos the qtdQuartos to set
     */
    public void setQtdQuartos(String qtdQuartos) {
        this.qtdQuartos = qtdQuartos;
    }

    /**
     * @return the qtdGaragem
     */
    public String getQtdGaragem() {
        return qtdGaragem;
    }

    /**
     * @param qtdGaragem the qtdGaragem to set
     */
    public void setQtdGaragem(String qtdGaragem) {
        this.qtdGaragem = qtdGaragem;
    }

    /**
     * @return the qtdSuites
     */
    public String getQtdSuites() {
        return qtdSuites;
    }

    /**
     * @param qtdSuites the qtdSuites to set
     */
    public void setQtdSuites(String qtdSuites) {
        this.qtdSuites = qtdSuites;
    }

    /**
     * @return the aceitaParceria
     */
    public String getAceitaParceria() {
        return aceitaParceria;
    }

    /**
     * @param aceitaParceria the aceitaParceria to set
     */
    public void setAceitaParceria(String aceitaParceria) {
        this.aceitaParceria = aceitaParceria;
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
    
}
