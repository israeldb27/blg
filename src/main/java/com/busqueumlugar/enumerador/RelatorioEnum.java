package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum RelatorioEnum implements Serializable, ICodigoDescricao {
	
	relatorioTeste1("Relatorio Teste 1"),
    
    estadosMaisCaros(MessageUtils.getMessage("lbl.rel.title.estados.mais.caros")),
	cidadesMaisCaras(MessageUtils.getMessage("lbl.rel.title.cidades.mais.caras")),
	bairrosMaisCaros(MessageUtils.getMessage("lbl.rel.title.bairros.mais.caros")),
	
	sobreEstados(MessageUtils.getMessage("lbl.rel.title.sobre.estados")),
	sobreCidades(MessageUtils.getMessage("lbl.rel.title.sobre.cidades")),
	sobreBairros(MessageUtils.getMessage("lbl.rel.title.sobre.bairros")),
	
	estadosMaisBaratos(MessageUtils.getMessage("lbl.rel.title.estados.mais.baratos")),
	cidadesMaisBaratas(MessageUtils.getMessage("lbl.rel.title.cidades.mais.baratas")),
	bairrosMaisBaratos(MessageUtils.getMessage("lbl.rel.title.bairros.mais.baratos")),
	
	imoveisMaisVisualizados(MessageUtils.getMessage("lbl.rel.title.imoveis.mais.visualizados")),	
	imoveisMaisPropostados(MessageUtils.getMessage("lbl.rel.title.imoveis.mais.propostados")),	
	imoveisMaisComentados(MessageUtils.getMessage("lbl.rel.title.imoveis.mais.comentados")),	
	imoveisMaisAdotadosInteressados(MessageUtils.getMessage("lbl.rel.title.imoveis.mais.adotados")),	
	imobiliariasMaisParceriasAceitas(MessageUtils.getMessage("lbl.rel.title.imobiliarias.mais.parcerias")),	
	imobiliariaMaisIntermediacoesAceitas(MessageUtils.getMessage("lbl.rel.title.imobiliarias.mais.intermediacoes")),	
	corretoresMaisParceriasAceitas(MessageUtils.getMessage("lbl.rel.title.corretores.mais.parcerias")),	
	corretoresMaisIntermediacoesAceitas(MessageUtils.getMessage("lbl.rel.title.corretores.mais.intermediacoes")),	
	quantImoveisPorLocalizacaoAcaoTipoImovel(MessageUtils.getMessage("lbl.rel.title.quant.imoveis.localizacao")),	
	tipoImoveisMaisProcuradoPorLocalizacao(MessageUtils.getMessage("lbl.rel.title.tipo.imoveis.localizacao")),
	variacaoPrecosPorTipoImovelPorLocalizacao(MessageUtils.getMessage("lbl.rel.title.variacao.preco.imoveis"));
    
    private String descricao;
    
    private RelatorioEnum( final String descricao) {
    	
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
    
        return RelatorioEnum.valueOf(key).getRotulo();
    }
    
}
