package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.dao.FormapagamentoDao;
import com.busqueumlugar.dao.ParamservicoDao;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.ServicoValueEnum;
import com.busqueumlugar.enumerador.TipoParamServicoOpcaoEnum;
import com.busqueumlugar.form.FormapagamentoForm;
import com.busqueumlugar.form.ParamservicoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Formapagamento;
import com.busqueumlugar.model.Paramservico;
import com.busqueumlugar.service.ParamservicoService;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.MessageUtils;
import com.mysql.jdbc.StringUtils;

@Service
public class ParamservicoServiceImpl implements ParamservicoService {
	
	private static final Logger log = LoggerFactory.getLogger(ParametrosIniciaisServiceImpl.class);
	
	@Autowired
	private ParamservicoDao dao;

	@Autowired
	private FormapagamentoDao formapagamentoDao;
	
	public Paramservico recuperarParamservicoPorId(Long id) {		
		return dao.findParamservicoById(id);
	}

	
	@Transactional
	public void cadastrarParametro(ParamservicoForm paramservicoForm) {		
		Paramservico p = new Paramservico();        
        BeanUtils.copyProperties(paramservicoForm, p);
        p.setDataCriacao(new Date());
        p.setStatusServico("criado");
        dao.save(p);   
	}

	
	@Override
	public List<Paramservico> recuperaTodosParametros() {		
		//return dao.findAllParametrosServicos();
		return dao.listAll();
	}

	@Override
	public List<Paramservico> recuperaTodosParametrosPorTipo(String tipo) {		
		return dao.findParamservicoPorTipo(tipo);
	}

	
	@Override
	public List<Paramservico> recuperaTodosParametrosPorTipoSemAssinatura(UsuarioForm user) {		
		return dao.findParametrosSemTipoAssinatura(user);
	}

	
	
	public List<Paramservico> recuperaTodosParametrosPorPerfilUsuario(String perfilUsuario) {		
		List<Paramservico> lista1 = dao.findParamservicoPorTipo("I");
        //List<Paramservico> lista2 = dao.findParamservicoPorTipo("U");        
        Paramservico paramRelatorio = null;
        Paramservico paramAssinatura = null;
        Paramservico paramMensagens = dao.findParamservicoPorNome("mensagens");
        if ( perfilUsuario.equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
            paramRelatorio = dao.findParamservicoPorNome(ServicoValueEnum.RELATORIO_PADRAO.getRotulo());
            paramAssinatura = dao.findParamservicoPorNome(ServicoValueEnum.ASSINATURA_PADRAO.getRotulo());
        }
        else if ( perfilUsuario.equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo())){
            paramRelatorio = dao.findParamservicoPorNome(ServicoValueEnum.RELATORIO_CORRETOR.getRotulo());
            paramAssinatura = dao.findParamservicoPorNome(ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo());
        }
        else if ( perfilUsuario.equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo())){
            paramRelatorio = dao.findParamservicoPorNome(ServicoValueEnum.RELATORIO_IMOBILIARIA.getRotulo());
            paramAssinatura = dao.findParamservicoPorNome(ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo());
        }
        
        List<Paramservico> listaFinal =  new ArrayList<Paramservico>();
        listaFinal.add(paramRelatorio);
        listaFinal.add(paramMensagens);        
        listaFinal.add(paramAssinatura);
        listaFinal.addAll(lista1);
        return listaFinal;
	}

	
	@Transactional
	public void excluirParametro(Long  idParam) {		
		dao.delete(dao.findParamservicoById(idParam));
	}

	
	public Paramservico recuperarParamServicoPorId(Long idParam) {		
		return dao.findParamservicoById(idParam);
	}

	
	public ParamservicoForm recuperarParamServicoPorNome(String acao) {		
		Paramservico paramservico = dao.findParamservicoPorNome(acao);
        ParamservicoForm pf = new ParamservicoForm();
        BeanUtils.copyProperties(paramservico, pf);
        return pf;
	}

	
	public Paramservico recuperarParamServicoPorNome2(String acao) {		
		return dao.findParamservicoPorNome(acao);
	}

	
	public List<Formapagamento> listarTodasFormasPagamento() {		
		return formapagamentoDao.listAll();
	}

	@Transactional
	public void cadastrarTipoFormaPagamento(FormapagamentoForm formaPagtoForm) {		
		Formapagamento formaPagto = new Formapagamento();
        BeanUtils.copyProperties(formaPagtoForm, formaPagto);
        formaPagto.setDataCriacao(new Date());
        formapagamentoDao.save(formaPagto);
	}

	@Transactional
	public void excluirTipoFormaPagamento(Long idFormaPagto) {	
		Formapagamento formaPagto = this.recuperaTipoFormaPagtoPorId(idFormaPagto);
		formapagamentoDao.delete(formaPagto);
	}

	
	public Formapagamento recuperaTipoFormaPagtoPorId(long idFormaPagamento) {		
		return formapagamentoDao.findFormapagamentoById(idFormaPagamento);
	}

	
	@Transactional
	public void atualizarOpcaoCobranca(ParamservicoForm paramservicoForm, String opcaoCobranca) {		
		Paramservico p = dao.findParamservicoById(paramservicoForm.getId());
        p.setCobranca(opcaoCobranca);
        dao.save(p);
	}

	
	public boolean checarServicoCobrado(String descServico) {		
		  Paramservico p = dao.findParamservicoPorNome(descServico);
	        if (p.getCobranca().equals("S"))
	            return true;
	        else
	            return false;
	}


	@Override
	@Transactional
	public void atualizarParametroServico(ParamservicoForm form) {
		Paramservico param = new Paramservico();		 
		BeanUtils.copyProperties(form, param);
		param.setDataCriacao(DateUtil.formataDataBanco(form.getDataCriacaoFmt()) );		
		dao.save(param);		
	}
	
	@Override
	public List<Paramservico> pesquisarTodosServicos(String valor){		
		return dao.findParamServicoByValor(valor);
	}


	@Override
	public boolean validarAtualizacaoParamServic(ParamservicoForm form, BindingResult result) {
		boolean valido = true;
		
		if ( StringUtils.isNullOrEmpty(form.getLabelServico())){
			valido = false;
			result.rejectValue("labelServico", "msg.erro.campo.obrigatorio");
		}
		
		if ( StringUtils.isNullOrEmpty(form.getValueServico())){
			valido = false;
			result.rejectValue("valueServico", "msg.erro.campo.obrigatorio");
		}
		
		if ( StringUtils.isNullOrEmpty(form.getTipoParamServico())){
			valido = false;
			result.rejectValue("tipoParamServico", "msg.erro.campo.obrigatorio");
		}
		
		if ( StringUtils.isNullOrEmpty(form.getCobranca())){
			valido = false;
			result.rejectValue("cobranca", "msg.erro.campo.obrigatorio");
		}
		
		return valido;
	}


	@Override
	public boolean validarCadastroParamServico(ParamservicoForm form, BindingResult result) {
		boolean valido = true;
		
		if ( StringUtils.isNullOrEmpty(form.getLabelServico())){
			valido = false;
			result.rejectValue("labelServico", "msg.erro.campo.obrigatorio");
		}
		
		if ( StringUtils.isNullOrEmpty(form.getValueServico())){
			valido = false;
			result.rejectValue("valueServico", "msg.erro.campo.obrigatorio");
		}
		
		if ( StringUtils.isNullOrEmpty(form.getTipoParamServico())){
			valido = false;
			result.rejectValue("tipoParamServico", "msg.erro.campo.obrigatorio");
		}
		
		if ( StringUtils.isNullOrEmpty(form.getCobranca())){
			valido = false;
			result.rejectValue("cobranca", "msg.erro.campo.obrigatorio");
		}
		
		return valido;
	}


	@Override
	public boolean validarCadastroTipoFormaPagamento(FormapagamentoForm form,	BindingResult result) {
		boolean valido = true;
		
		if ( StringUtils.isNullOrEmpty(form.getNome())){
			valido = false;
			result.rejectValue("nome", "msg.erro.campo.obrigatorio");
		}
		
		if ( StringUtils.isNullOrEmpty(form.getLabel())){
			valido = false;
			result.rejectValue("label", "msg.erro.campo.obrigatorio");
		}
		
		if ( form.getTaxaValor() == null || form.getTaxaValor().doubleValue() <= 0.0){
			valido = false;
			result.rejectValue("taxaValor", "msg.erro.valor.quantidade.zero");
		}
		
		return valido;
	}


	@Override
	public boolean validarAtualizarTipoFormaPagamento(FormapagamentoForm form, BindingResult result) {

		boolean valido = true;
		
		if ( StringUtils.isNullOrEmpty(form.getNome())){
			valido = false;
			result.rejectValue("nome", "msg.erro.campo.obrigatorio");
		}
		
		if ( StringUtils.isNullOrEmpty(form.getLabel())){
			valido = false;
			result.rejectValue("label", "msg.erro.campo.obrigatorio");
		}
		
		if ( form.getTaxaValor() == null || form.getTaxaValor().doubleValue() <= 0.0){
			valido = false;
			result.rejectValue("label", "msg.erro.valor.quantidade.zero");
		}
		
		return valido;
	}


	@Override
	public void atualizarFormasPagamento(FormapagamentoForm form) {
		Formapagamento formaPagto = new Formapagamento();
        BeanUtils.copyProperties(form, formaPagto);
        formaPagto.setDataCriacao(DateUtil.formataDataBanco(form.getDataCriacaoFmt()));
        formapagamentoDao.save(formaPagto);
		
	}

}
