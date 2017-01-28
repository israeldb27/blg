package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.ImovelindicadoDao;
import com.busqueumlugar.dao.RecomendacaoDao;
import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelindicado;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ImovelindicadoService;
import com.busqueumlugar.util.EnviaEmailHtml;
import com.busqueumlugar.util.MessageUtils;

@Service
public class ImovelindicadoServiceImpl implements ImovelindicadoService {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelindicadoServiceImpl.class);
	
	@Autowired
	private ImovelindicadoDao dao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ImovelDao imovelDao;
	
	@Autowired
	private ContatoDao contatoDao;
	
	@Autowired
	private SeguidorDao seguidorDao;
	
	@Autowired
	private RecomendacaoDao recomendacaoDao;


	
	public Imovelindicado recuperarImovelindicadoPorId(Long id) {
		return dao.findImovelindicadoById(id);
	}

	@Override
	@Transactional
	public void cadastrarIndicacao(Long idImovel, Long idUsuario,Long idUsuarioIndicador) {
		Imovelindicado imovelindicado = new Imovelindicado();
		Usuario usuario = usuarioDao.findUsuario(idUsuario);
		imovelindicado.setImovel(imovelDao.findImovelById(idImovel));
		imovelindicado.setUsuario(usuario);
        imovelindicado.setStatusIndicado(StatusLeituraEnum.NOVO.getRotulo());
        imovelindicado.setDataIndicacao(new Date());        
        imovelindicado.setUsuarioIndicador(usuarioDao.findUsuario(idUsuarioIndicador));                
        imovelindicado.setIndicacaoEmail("N");
        dao.save(imovelindicado);
        
        //atualizando a quantidade de indicacoes do usuario
        usuario.setQuantMaxIndicacoesImovel(usuario.getQuantMaxIndicacoesImovel() - 1);
        usuarioDao.save(usuario);		
	}

	@Override
	@Transactional
	public void cadastrarIndicacaoPorEmail(Long idImovel, String email,	Long idUsuarioIndicador) {
		Imovelindicado imovelindicado = new Imovelindicado();
		Usuario usuario = usuarioDao.findUsuario(idUsuarioIndicador);
		imovelindicado.setImovel(imovelDao.findImovelById(idImovel));	       
        imovelindicado.setStatusIndicado(StatusLeituraEnum.NOVO.getRotulo());
        imovelindicado.setDataIndicacao(new Date());        
        imovelindicado.setUsuarioIndicador(usuario);
        imovelindicado.setIndicacaoEmail("S");
        imovelindicado.setEmailImovelIndicado(email);
        dao.save(imovelindicado);
        
        //atualizando a quantidade de indicacoes do usuario
        usuario.setQuantMaxIndicacoesImovel(usuario.getQuantMaxIndicacoesImovel() - 1);
        usuarioDao.save(usuario);	        
        
        try {
        	// enviar email
            EnviaEmailHtml enviaEmail = new EnviaEmailHtml();
            enviaEmail.setSubject(MessageUtils.getMessage("msg.email.subject.imovel.indicado.email"));
            enviaEmail.setTo(email);
            enviaEmail.setTexto(MessageUtils.getMessage("msg.email.texto.imovel.indicado.email"));		            	
            enviaEmail.enviaEmail(enviaEmail.getEmail());
		} catch (Exception e) {		
			e.printStackTrace();
		}
        
	}

	@Override
	public List<Imovelindicado> listarImovelIndicado(Long idUsuario, ImovelindicadoForm form) {
        return dao.findImoveisPorUsuario(idUsuario, form);
	}

	@Override
	public long quantImoveisIndicados(Long idUsuario) {		
	    return dao.findQuantImoveisIndicadosByIdUsuarioByStatusLeitura(idUsuario, null);
	}

	@Override
	public long checaQuantImoveisIndicados(Long idUsuario) {
		return dao.findQuantImoveisIndicadosByIdUsuarioByStatusLeitura(idUsuario, null);				
	}

	@Override
	public List<Imovelindicado> listarImoveisIndicacoes(Long idUsuario, ImovelindicadoForm form) {		
        return dao.findImoveisIndicacoesPorUsuario(idUsuario, form);
	}

	@Override
	@Transactional
	public void excluirImovelIndicado(Imovel imovel) {
		Imovelindicado imovelIndicado = dao.findImovelIndicadoByIdImovel(imovel.getId());
        dao.delete(imovelIndicado);		
	}
	
	@Override
	@Transactional
	public void excluirImovelIndicado(Long id) {
		Imovelindicado imovelIndicado = dao.findImovelindicadoById(id);
        dao.delete(imovelIndicado);		
	}

	@Override
	@Transactional
	public void excluirImovelIndicacao(Imovel imovel, Long idUsuario) {
		Imovelindicado imovelIndicado = dao.findImovelIndicacaoByIdImovelIdUsuario(imovel.getId(), idUsuario);
		dao.delete(imovelIndicado); 		
	}

	@Override
	@Transactional
	public void excluirImovelIndicado(Imovel imovel, Long idUsuario) {
		 Imovelindicado imovelIndicado = dao.findImovelIndicadoByIdImovelIdUsuario(imovel.getId(), idUsuario);
		 dao.delete(imovelIndicado); 		
		
	}

	@Override
	public Imovelindicado validarIndicavaoImovel(Imovel imovel, long idUsuario) {
		return dao.findImovelIndicadoByIdImovelIdUsuario(imovel.getId(), idUsuario);
	}

	@Override
	public boolean atualizarStatusIndicado(Long idImovel, Long idUsuario) {
		Imovelindicado imovelIndicado = dao.findImovelIndicadoByIdImovelIdUsuario(idImovel, idUsuario);
        if ( imovelIndicado.getStatusIndicado().equals(StatusLeituraEnum.NOVO.getRotulo()))
            imovelIndicado.setStatusIndicado(StatusLeituraEnum.LIDO.getRotulo());
        else 
            imovelIndicado.setStatusIndicado(StatusLeituraEnum.NOVO.getRotulo());
        
        dao.save(imovelIndicado);
        return true; 
	}

	@Override
	public List<Imovel> recuperarImoveisIndicadosNovos(Long idUsuario) {
		List<Imovelindicado> listaIndicado = dao.findImoveisIndicadosNovos(idUsuario);
        List<Imovel> listaFinal = new ArrayList<Imovel>();
        Imovel imovel = null;
        for (Imovelindicado imovelindicado : listaIndicado ){
            listaFinal.add(imovelindicado.getImovel());
        }        
        return listaFinal;
	}

	@Override
	public long checarQuantidadeNovosImoveisIndicados(Long idUsuario) {
		return dao.findQuantImoveisIndicadosByIdUsuarioByStatusLeitura(idUsuario, StatusLeituraEnum.NOVO.getRotulo());		
	}

	@Override
	public List<Imovel> checarImoveisMaisReceberamIndicacoesPorPeriodo(AdministracaoForm form) {
		List lista = dao.checarImoveisComMaisIndicacoesPeriodo(form);
        List<Imovel> listaFinal = new ArrayList<Imovel>();
        
        if ( ! CollectionUtils.isEmpty(lista) ){
            Imovel imovel = null;
            for (Iterator iter = lista.iterator();iter.hasNext();){
                Object[] obj = (Object[]) iter.next();
                imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));                
                listaFinal.add(imovel);
            }
        }
        return listaFinal;
	}

	@Override
	public EmailImovel indicarImovelPorEmail(ImovelForm frm) {
		
		EmailImovel email = new EmailImovel();
        StringBuilder texto = new StringBuilder();       
        
        texto.append(MessageUtils.getMessage("msg.email.indicado.texto.email") + "\n");
        texto.append(MessageUtils.getMessage("lbl.titulo.imovel") + " :" + frm.getTitulo() + "\n");
        texto.append(MessageUtils.getMessage("lbl.estado") + " :" + frm.getEstado() + "\n" );
        texto.append(MessageUtils.getMessage("lbl.cidade") + " :" + frm.getCidade() + "\n");
        texto.append(MessageUtils.getMessage("lbl.bairro") + " :" + frm.getBairro() + "\n");
        
        email.setTo(frm.getEmailTo());
        email.setAssunto(MessageUtils.getMessage("msg.email.indicacao.assunto"));
        email.setTextoEmail(texto.toString());
        email.setAcao("indicarImovel");

        return email;
	}

	@Override
	public boolean checarPermissaoIndicarImoveis(Long idUsuario, int quantSelecionados) {
		boolean ultrapassou = false;
        Usuario usuario = usuarioDao.findUsuario(idUsuario);
        if ( usuario.getQuantMaxIndicacoesImovel() < quantSelecionados)        
            ultrapassou = true; 
        
        return ultrapassou;
	}

	@Override
	public List<Imovelindicado> ordenarImoveisIndicados(Long idUsuario, ImovelindicadoForm form) {		
		return filtrar(form.getTipoLista(), idUsuario, form);        
	}
	
	@Override
	public List<?> ordenarAgruparImoveisIndicados(Long idUsuario, ImovelindicadoForm form) {

		if ( form.getOpcaoVisualizacao().equals("agruparUsuarios"))
			return this.agruparUsuariosImoveisIndicados(idUsuario, form);
		else if ( form.getOpcaoVisualizacao().equals("agruparImoveis"))			
			return this.agruparImoveis(idUsuario, form);
		else	
			return null;
	}
	
	

    @Override
    public List<Imovelindicado> filtrar(String tipoFiltro, Long idUsuario, ImovelindicadoForm form){
    	List<Imovelindicado> listaFinal  = new ArrayList<Imovelindicado>();
    	
    	if ( form.getTipoLista().equals("indicacoes")){
    		listaFinal = dao.filterImoveisIndicacoes(idUsuario, form);
    	}	
    	else if ( form.getTipoLista().equals("indicado")){
    		listaFinal = dao.filterImoveisIndicados(idUsuario, form);    		
    	}
    	 
        return listaFinal;        
    }
	
	@Override
	public List<Usuario> filtrarAgruparUsuario(Long idUsuario, ImovelindicadoForm form) {		
		return this.agruparUsuariosImoveisIndicados(idUsuario, form);		
	}

    @Override
    public List<Usuario> agruparUsuariosImoveisIndicados(Long idUsuario, ImovelindicadoForm form){

    	List<Usuario> listaUsuario = new ArrayList<Usuario>();
    	if ( form.getTipoLista().equals("indicado")){
    		// lista do idUsuario em que algum momento indicaram para o usuario sessao algum imovel
    		List lista = dao.findImoveisPorUsuarioDistinct(idUsuario, form);        
            Object[] obj = null;
            Usuario usuario = null;        
            for ( Iterator iter = lista.iterator(); iter.hasNext();){
            	obj = (Object[]) iter.next();
                usuario = usuarioDao.findUsuario(Long.parseLong(obj[0].toString()));                        
                usuario.setQuantImoveisIndicado(Integer.parseInt(obj[1].toString()));
                usuario.setQuantImovelVisitado(Integer.parseInt(obj[1].toString()));
    			usuario.setQuantTotalContatos(contatoDao.findQuantidadeTotalContatosByIdUsuarioByStatus(usuario.getId(), ContatoStatusEnum.OK.getRotulo()));
    			usuario.setQuantTotalRecomendacoes(recomendacaoDao.findQuantidadeRecomendacoesByUsuarioByStatusByStatusLeitura(usuario.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo(), null));
    			usuario.setQuantTotalImoveis(imovelDao.findQuantMeusImoveis(usuario.getId()));
    			usuario.setQuantTotalSeguidores(seguidorDao.findQuantSeguidoresByIdUsuarioSeguido(usuario.getId()));
            	listaUsuario.add(usuario);
            }
    	}
    	else {
    		// lista do idUsuario em que algum momento o usuario sessao indicou  algum imovel
    		List lista = dao.findImoveisIndicacoesPorUsuarioDistinct(idUsuario, form);        
            Object[] obj = null;
            Usuario usuario = null;        
            for ( Iterator iter = lista.iterator(); iter.hasNext();){
            	obj = (Object[]) iter.next();
                usuario = usuarioDao.findUsuario(Long.parseLong(obj[0].toString()));                        
                usuario.setQuantImoveisIndicado(Integer.parseInt(obj[1].toString()));
                usuario.setQuantImovelVisitado(Integer.parseInt(obj[1].toString()));
    			usuario.setQuantTotalContatos(contatoDao.findQuantidadeTotalContatosByIdUsuarioByStatus(usuario.getId(), ContatoStatusEnum.OK.getRotulo()));
    			usuario.setQuantTotalRecomendacoes(recomendacaoDao.findQuantidadeRecomendacoesByUsuarioByStatusByStatusLeitura(usuario.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo(), null));
    			usuario.setQuantTotalImoveis(imovelDao.findQuantMeusImoveis(usuario.getId()));
    			usuario.setQuantTotalSeguidores(seguidorDao.findQuantSeguidoresByIdUsuarioSeguido(usuario.getId()));
            	listaUsuario.add(usuario);
            }
    	}
        
        return listaUsuario;
    }

    @Override
    public List<Usuario> listarUsuariosImoveisIndicacoes(Long idUsuario, ImovelindicadoForm form){

        List<Imovelindicado> listaIndicacoes = dao.findImoveisIndicacoesPorUsuario(idUsuario, form);
        List<Usuario> listaUsuario = new ArrayList<Usuario>();
        TreeSet set = new TreeSet();
        for (Imovelindicado imovelindicacao : listaIndicacoes){
            set.add(imovelindicacao.getUsuarioIndicador().getId());
        }

        Long idUsuarioIndicacao = null;
        Usuario usuario = null;
        Object[] obj = null;
        for ( Iterator iter = set.iterator(); iter.hasNext();){
        	idUsuarioIndicacao = (Long) iter.next();
        	usuario = usuarioDao.findUsuario(idUsuarioIndicacao);        	
        	List lista = dao.findInfoAgruparUsuariosImoveisIndicacoes(idUsuarioIndicacao);        	
            usuario.setQuantImoveisIndicacoes(Integer.parseInt(obj[1].toString()));            
            listaUsuario.add(usuario);
        }
        

        return listaUsuario;
    }

	@Override
	public List<Imovel> agruparImoveis(Long idUsuario, ImovelindicadoForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		if ( form.getTipoLista().equals("indicacoes")){
			// lista dos IdImovel cujo usuario sessao tenha recebido pelo menos uma indicacao
			List lista = dao.findImoveisIndicacoesByIdUsuarioDistinct(idUsuario, form);
			Object[] obj = null;			
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();				
				imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));				
				imovel.setQuantImoveisIndicados(Integer.parseInt(obj[1].toString()));
				imovel.setQuantNovosImoveisIndicados(dao.findQuantidadeNovosImoveisIndicacoes(Long.parseLong(obj[0].toString())));
				listaFinal.add(imovel);
			}
		}
		return listaFinal;
	}
	
	@Override
	public List<Imovel> filtrarAgruparImoveis(Long idUsuario, ImovelindicadoForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		List lista = dao.filtrarAgruparImoveis(idUsuario, form);		
		if (! CollectionUtils.isEmpty(lista) ){             
            Object[] obj = null;			
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();				
				imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));				
				imovel.setQuantImoveisIndicados(Integer.parseInt(obj[1].toString()));
				imovel.setQuantNovosImoveisIndicados(dao.findQuantidadeNovosImoveisIndicacoes(Long.parseLong(obj[0].toString())));
				listaFinal.add(imovel);
			}
        }
		return listaFinal;
	}

	@Override
	public List<Imovelindicado> recuperarSolicitacoesIndicacoesPorImovel(Long idImovel) {		
		return dao.findImoveisIndicadosByIdImovel(idImovel);
	}
	
	@Override
	public List<Imovelindicado> recuperarSolicitacoesIndicacoesPorImovelPorUsuario(Long idImovel, Long idUsuario) {		
		return dao.findImoveisIndicadosByIdImovelPorIdUsuarioIndicador(idImovel, idUsuario);
	}

	@Override
	public List<Imovelindicado> recuperarSolicitacoesIndicadosParaMimPorIdUsuario(Long idUsuarioSessao, Long idUsuario) {		
		return dao.findImoveisIndicadosParaMimByIdUsuarioSessaoByIdUsuario(idUsuarioSessao, idUsuario);
	}

	@Override
	public void cadastrarIndicacaoSelecionados(ImovelindicadoForm form, Long idUsuarioSessao) {
		
		int tam = form.getIdUsuariosSelecionados().length;
		String str[] = form.getIdUsuariosSelecionados();
		for (int i = 0; i < tam;i++)
			cadastrarIndicacao(form.getIdImovel(), Long.parseLong(str[i]) ,idUsuarioSessao);	
	
	}

	@Override
	public String validarIndicavaoImovel(Long idImovel, Long idUsuario) {
		
		Imovelindicado imovelindicado = dao.findImovelIndicadoByIdImovelIdUsuario(idImovel, idUsuario);
		if ( imovelindicado != null )  
			return MessageUtils.getMessage("msg.erro.indicacao.ja.realizada"); 			
		
		return "";
	}
	
	@Override
	public String validarIndicavaoImovelUsuariosSelecionados(ImovelindicadoForm form, Long idUsuario){
		
		String [] usuariosSelecionados = form.getIdUsuariosSelecionados();
		Usuario usuario = usuarioDao.findUsuario(idUsuario);
		
		if ( usuariosSelecionados.length == 0)
			return MessageUtils.getMessage("msg.erro.indicacoes.nenhum.usuario.selecionado");		
		
		if ( usuariosSelecionados.length > usuario.getQuantMaxIndicacoesImovel())
			return MessageUtils.getMessage("msg.erro.indicacoes.quant.usuarios.selecionados");
		
		/*if ( msg.equals("")){ // checando se algum usuario selecionado ja recebeu a indicacao do imovel anteriormente
			String idUsuarioSel = null;
			Usuario usuarioSel = null;  
			StringBuffer msgRet = new StringBuffer(MessageUtils.getMessage("msg.erro.indicacoes.usuarios.selecionados.indicacao.anterior"));
			for (int i = 0; i < usuariosSelecionados.length ; i++){
				if ( dao.findImovelIndicadoByIdImovelIdUsuario(form.getIdImovel(), Long.valueOf(usuariosSelecionados[i])) != null ){
					usuarioSel = usuarioService.recuperarUsuarioPorId(Long.valueOf(usuariosSelecionados[i]));
					msgRet.append(usuarioSel.getNome() + ",");
				}				
			}
		}	*/	
		
		return "";
	}

	@Override
	public boolean atualizarStatusIndicado(Long idImovelIndicado) {

		Imovelindicado imovelIndicado = dao.findImovelindicadoById(idImovelIndicado);
        if ( imovelIndicado.getStatusIndicado().equals(StatusLeituraEnum.NOVO.getRotulo()))
            imovelIndicado.setStatusIndicado(StatusLeituraEnum.LIDO.getRotulo());
        else 
            imovelIndicado.setStatusIndicado(StatusLeituraEnum.NOVO.getRotulo());
        
        dao.save(imovelIndicado);
        return true; 
		
	}

	@Override
	public String checarUsuarioIndicacaoImovel(Long idUsuario, Long idImovel) {		 
		if (dao.findImovelIndicadoByIdImovelIdUsuario(idImovel, idUsuario) != null)
			return "S";
		else
			return "N";
	}
	
	@Override
	@Transactional
	public void atualizarStatusImoveisIndicadosSolRecebidas(Long idUsuario) {
		dao.updateStatusLeituraByIdUsuarioIndicado(idUsuario);
	}


}
