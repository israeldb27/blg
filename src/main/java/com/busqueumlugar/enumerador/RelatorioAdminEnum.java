package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum RelatorioAdminEnum implements Serializable, ICodigoDescricao {
    
	// inicio relatorios assinaturas
    totalAssinaturas(MessageUtils.getMessage("lbl.admin.opcao.rel.total.assinaturas")),
	assinaturasVolFinanceiro(MessageUtils.getMessage("lbl.admin.opcao.rel.tipo.assinaturas.vol.financeiro")),
	buscaAssinaturasUsuario(MessageUtils.getMessage("lbl.admin.opcao.rel.busca.assinaturas.usuario")),
	// ----------------------------
	// inicio relatorios imoveis
	maisComentarios(MessageUtils.getMessage("lbl.admin.rel.title.imoveis.mais.comentados")),	
	maisIndicacoes(MessageUtils.getMessage("lbl.admin.rel.title.imoveis.mais.indicados")),	
	maisInteressados(MessageUtils.getMessage("lbl.admin.rel.title.imoveis.mais.Favoritos")),	
	maisPropostas(MessageUtils.getMessage("lbl.admin.rel.title.imoveis.mais.propostas")),	
	maisIndicados(MessageUtils.getMessage("lbl.admin.rel.title.imoveis.mais.indicados")),	
	maisSolParceria(MessageUtils.getMessage("lbl.admin.rel.title.imoveis.mais.sol.parceria")),	
	maisSolIntermediacao(MessageUtils.getMessage("lbl.admin.rel.title.imoveis.mais.sol.intermediacao")),	
	maisVisitas(MessageUtils.getMessage("lbl.admin.rel.title.imoveis.mais.visitas")),	
	quantTotal(MessageUtils.getMessage("lbl.admin.rel.title.imoveis.quant.total")),	
	// ----------------------------
	//inicio relatorios plano
	totalPlano(MessageUtils.getMessage("lbl.admin.opcao.rel.total.planos")),
	planosVolFinanceiro(MessageUtils.getMessage("lbl.admin.opcao.rel.tipo.plano.vol.financeiro")),
	buscaPlanoUsuario(MessageUtils.getMessage("lbl.admin.opcao.rel.busca.planos.usuario")),	
	
	// ----------------------------
	//inicio relatorios servicos
	volFinanceiroServicos(MessageUtils.getMessage("lbl.admin.opcao.rel.vol.total.servicos")),
	totalServicos(MessageUtils.getMessage("lbl.admin.opcao.rel.total.servicos")),
	usuariosVolFinancServicos(MessageUtils.getMessage("lbl.admin.opcao.rel.usuario.vol.total.servicos")),
	// ----------------------------
	//inicio relatorios servicos	
	acessoUsuario(MessageUtils.getMessage("lbl.admin.rel.title.total.acessos.periodo")),
	quantUsuariosxServicos(MessageUtils.getMessage("lbl.admin.rel.title.total.usuarios.servicos")),
	ultimoAcessoUsuarioPeriodo(MessageUtils.getMessage("lbl.admin.rel.title.acesso.usuarios.periodo"));	
	// ----------------------------
	
	
	
    private String descricao;
    
    private RelatorioAdminEnum( final String descricao) {
    	
        this.descricao = descricao;
    }
    
    @Override
    public String getRotulo() {
    
        return this.descricao;
    }  
    
    @Override
    public String getIdentificador() {
    
        return this.name();
    }    

    public static String getLabel(String key) {
    
        return RelatorioAdminEnum.valueOf(key).getRotulo();
    }
    
}
