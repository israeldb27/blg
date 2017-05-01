package com.busqueumlugar.dao;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;

public interface ImovelDao extends GenericDAO<Imovel, Long> {
	
	Imovel findImovelById(Long idImovel);	 
	
	List<Imovel> findImovel(String valorBusca);
	
	List<Imovel> findImovelByUsuario(Long idUsuario, ImovelForm form);
	
	List<Imovel> findImovelByUsuario(Long idUsuario);
	
	List<Imovel> buscarImoveis(AdministracaoForm admForm, Long idUsuario, String opcaoOrdenacao);
	
	List<Imovel> buscarImoveis(ImovelForm form, Long idUsuario, String opcaoOrdenacao);
	
	List<Imovel> buscarImoveis(PerfilForm form, Long idUsuario, String opcaoOrdenacao);	
	
	List<Imovel> findImovelByAutorizacaoOutroUsuario();
	
	Imovel findImovelByCodigoIdentificacao(String codigo);
	
	List<Imovel> findImovelByUsuarioOrdenacao(Long idUsuario, String opcaoOrdenacao);
	
	List<Imovel> findImoveisByDataCadastro(Date dataInicio, Date dataFim);	
	
	List<Imovel> findImoveisDestaqueByIdUsuario(Long idUsuario);
	
	Imovel findImovelByCodigoIdentificacaoBydIdUsuario(Long idUsuario, ImovelForm form);
	
	void reativaImoveisUsuario(long idUsuario);
	
	List<Imovel> findImoveisNaoDestaqueByIdUsuario(Long idUsuario);
	
	List<Imovel> findImoveisByDataCadastro(AdministracaoForm form);
	
	List<Imovel> findImoveisByValor(String valor);
	
	long findQuantMeusImoveis(Long idUsuario);

	List<Imovel> findImoveisByListaIdsByIndex(List<Long> listaIds, int index);

	List<Imovel> findImoveisAleatoriamenteByUsuarioByIndex(UsuarioForm user, int index);

	List<Imovel> findImoveisByListaIdsByIndexByAceitaCompartilhado(List<Long> listaIds, int index, String aceitaCompartilhado);

	List<Imovel> findImoveisAleatoriamenteByListaIdsByIndex(List<Long> listaIds, int index);

	List<Imovel> findImoveisByListaIdsByIndexByAceitaCompartilhado(Long idUsuario, int index, String aceitaCompartilhado);

	List<Imovel> findImoveisByListaIdsByIndex(Long idUsuario, int index);

	List<Imovel> findImoveisTimeLine(ImovelForm form, Long idUsuario);
}
