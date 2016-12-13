package com.busqueumlugar.enumerador;

import java.io.Serializable;

import com.busqueumlugar.util.MessageUtils;

public enum AcaoServicoEnum implements Serializable, ICodigoDescricao {
    
    usuarioInteressado(MessageUtils.getMessage("lbl.servico.selecionado.usuario.interessado")),
	PropostasRecebidas(MessageUtils.getMessage("lbl.servico.selecionado.propostas.imoveis")),
	comentariosImoveis(MessageUtils.getMessage("lbl.servico.selecionado.comentarios.imoveis")),
	adicionarFotos(MessageUtils.getMessage("lbl.servico.selecionado.adicionar.fotos")),
	adicionarImoveis(MessageUtils.getMessage("lbl.servico.selecionado.adicionar.imoveis")),
	indicacoesImoveis(MessageUtils.getMessage("lbl.servico.selecionado.indicacoes.imoveis")),
	indicacoesEmail(MessageUtils.getMessage("lbl.servico.selecionado.indicacoes.imoveis.email")),
	usuarioVisitante(MessageUtils.getMessage("lbl.servico.selecionado.usuario.visualizado")),
	parceria(MessageUtils.getMessage("lbl.servico.selecionado.parceria")),
	intermediacao(MessageUtils.getMessage("lbl.servico.selecionado.intermediacao")),	
	mensagens(MessageUtils.getMessage("lbl.servico.selecionado.mensagens")),
	relatorioCliente(MessageUtils.getMessage("lbl.servico.selecionado.relatorio")),
	relatorioCorretor(MessageUtils.getMessage("lbl.servico.selecionado.relatorio")),
	relatorioImobiliaria(MessageUtils.getMessage("lbl.servico.selecionado.relatorio"));
    
    private String descricao;
    
    private AcaoServicoEnum( final String descricao) {
    	
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
    
        return AcaoServicoEnum.valueOf(key).getRotulo();
    }
    
}
