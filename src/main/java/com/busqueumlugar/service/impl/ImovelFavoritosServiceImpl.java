package com.busqueumlugar.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.ImovelfavoritosDao;
import com.busqueumlugar.dao.RecomendacaoDao;
import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelfavoritosForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelfavoritos;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ImovelFavoritosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.MessageUtils;

@Service
public class ImovelFavoritosServiceImpl implements ImovelFavoritosService {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelFavoritosServiceImpl.class);
	
	@Autowired(required = true)
	private ImovelfavoritosDao dao;
	
	@Autowired
	private ImovelDao imovelDao;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ContatoDao contatoDao;
	
	@Autowired
	private SeguidorDao seguidorDao;
	
	@Autowired
	private RecomendacaoDao recomendacaoDao;

	
	@Override
	public List<Imovel> relatorioImoveisMaisAdotadosInteressadosPorPeriodo(RelatorioForm frm) {

        List lista = dao.relatorioImovelMaisAdotadosInteressadosPeriodo(frm);		
        List<Imovel> listaFinal = new ArrayList<Imovel>();
        if (! CollectionUtils.isEmpty(lista)){
            Imovel imovel = null;       
            for (Iterator iter = lista.iterator();iter.hasNext();){                
                Object[] obj = (Object[]) iter.next();
                imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));                
                imovel.setQuantidade(Integer.parseInt(obj[1].toString()));
                listaFinal.add(imovel);
            }
        }
        return listaFinal;
	}

	@Override
	public List<Imovelfavoritos> listarImoveisPorUsuario(Long idUsuario, ImovelfavoritosForm form) {
		return dao.findImovelFavoritosByUsuario(idUsuario, form);
	}

	@Override
	@Transactional
	public void adicionarFavoritosPorUsuario(Long idUsuario, ImovelForm frm) {
		Imovelfavoritos imovelfavoritos = new Imovelfavoritos();
        imovelfavoritos.setImovel(imovelDao.findImovelById(frm.getId()));
        imovelfavoritos.setUsuario(usuarioDao.findUsuario(idUsuario));
        imovelfavoritos.setUsuarioDonoImovel(usuarioDao.findUsuario(frm.getIdUsuario()));        
        imovelfavoritos.setStatus(StatusLeituraEnum.NOVO.getRotulo());
        imovelfavoritos.setDataInteresse(new Date());        
        dao.save(imovelfavoritos);  		
	}

	@Override
	@Transactional
	public void adicionarFavoritosPorUsuario(Long idUsuario, Imovel imovel) {
		Imovelfavoritos imovelfavoritos = new Imovelfavoritos();
        imovelfavoritos.setImovel(imovel);
        imovelfavoritos.setUsuario(usuarioDao.findUsuario(idUsuario));
        imovelfavoritos.setUsuarioDonoImovel(imovelfavoritos.getImovel().getUsuario());
        imovelfavoritos.setStatus(StatusLeituraEnum.NOVO.getRotulo());
        imovelfavoritos.setDataInteresse(new Date());
        dao.save(imovelfavoritos);
	}
	
	@Override
	@Transactional
	public void retirarFavoritosPorUsuario(Long idUsuario, Long idImovel) {
		Imovelfavoritos imovelfavoritos = dao.findImovelFavoritosByidImovelbyIdUsuario(idUsuario, idImovel);
		dao.delete(imovelfavoritos);
	}
	
	@Override
	@Transactional
	public void retirarFavoritosPorIdImovelfavoritos(Long idImovelfavoritos){
		Imovelfavoritos imovelfavoritos = dao.findImovelfavoritosById(idImovelfavoritos);
		dao.delete(imovelfavoritos);
	}

	@Override
	public String validarImovelFavoritos(Long idUsuario, Long idImovel) {
		Imovelfavoritos imovelFav = dao.findImovelFavoritosByidImovelbyIdUsuario(idUsuario, idImovel);
        if ( imovelFav != null )        	
            return MessageUtils.getMessage("msg.sucesso.imovel.interesse.ja.adicionado.usuario");
        else
            return "";
	}

	@Override
	public List<Imovelfavoritos> listarUsuariosInteressadosMeusImoveis(Long idUsuario, ImovelfavoritosForm form) {
		return dao.findUsuariosInteressadosByIdDonoImovel(idUsuario, form);
	}

	@Override
	@Transactional
	public void excluirUsuarioInteressado(ImovelfavoritosForm imovelFavForm) {
		Imovelfavoritos imovelfavoritos = dao.findImovelfavoritosById(imovelFavForm.getId());
		dao.delete(imovelfavoritos);
		
	}
	
	@Override
	@Transactional
	public void excluirImovelFavoritos(Long idImovel, Long idUsuario) {
		Imovelfavoritos imovelfavoritos = dao.findImovelFavoritosByidImovelbyIdUsuario(idUsuario, idImovel);
		dao.delete(imovelfavoritos);
	}

	@Override
	@Transactional
	public void excluirImovelInteresse(Imovel imovel, UsuarioForm user) {
		Imovelfavoritos imovelFav = dao.findImovelFavoritosByidImovelbyIdUsuario(user.getId(), imovel.getId());
        dao.delete(imovelFav);		
	}

	@Override
	public int checarExisteNovoUsuarioInteressado(Long idUsuario) {		
        return AppUtil.recuperarQuantidadeLista(dao.findNovoUsuariosInteressadosByIdDonoImovel(idUsuario));
	}
	
	@Override
	public int checarExisteNovoUsuarioInteressado(List<Imovelfavoritos> lista) {
		
		if ( ! CollectionUtils.isEmpty(lista)){
			int i = 0;	
			for (Imovelfavoritos imovel : lista){
				if ( imovel.getStatus().equals(StatusLeituraEnum.NOVO.getRotulo()))
					i++;
			}
			return i;
		}
		else
			return 0;
	}

	@Override
	public List<ImovelfavoritosForm> recuperarUsuariosInteressadosNovos(List<ImovelfavoritosForm> lista) {
		List<ImovelfavoritosForm> listaFinal = new ArrayList<ImovelfavoritosForm>();
        for (ImovelfavoritosForm imovel : lista ){            
            if ( imovel.getStatus().equals(StatusLeituraEnum.NOVO.getRotulo()))
                listaFinal.add(imovel);
        }
        return listaFinal;
	}

	@Override
	public boolean atualizarStatusUsuarioInteressado(Long idImovelfavorito) {

		boolean resultado = false;
        Imovelfavoritos imovelfavoritos = dao.findImovelfavoritosById(idImovelfavorito);
        if ( imovelfavoritos.getStatus().equals(StatusLeituraEnum.NOVO.getRotulo())) {
            imovelfavoritos.setStatus(StatusLeituraEnum.LIDO.getRotulo());
            resultado = false;
        }
        else {
            imovelfavoritos.setStatus(StatusLeituraEnum.NOVO.getRotulo());
            resultado = true;
        }        
        dao.save(imovelfavoritos);
        return resultado;
	}

	@Override
	public List<Imovelfavoritos> recuperarUsuariosInteressadosPorIdImovel(Long idImovel) {	  
		return dao.findUsuariosInteressadosPorIdImovel(idImovel);
	}

	@Override
	public long checarQuantidadeUsuariosInteressadosPorIdImovel(Long idImovel) {		
		return dao.findQuantidadeNovosUsuariosInteressados(idImovel);
	}

	@Override
	public List<Imovel> checarImoveisMaisInteressadosPorPeriodo(AdministracaoForm form) {

		List lista = dao.relatorioImovelMaisAdotadosInteressadosPeriodo(form);
        List<Imovel> listaFinal = new ArrayList<Imovel>();        
        if ( ! CollectionUtils.isEmpty(lista)){
            Imovel imovel = null;           
            for (Iterator iter = lista.iterator();iter.hasNext();){
                Object[] obj = (Object[]) iter.next();
                imovel = imovelDao.findImovelById(new Long(obj[0].toString()));
                listaFinal.add(imovel);
            }
        }
        return listaFinal;
	}

	@Override
	public String checarUsuarioEstaInteressadoImovel(Long idUsuario, Long idImovel) {
		Imovelfavoritos imovel = dao.findImovelFavoritosByidImovelbyIdUsuario(idUsuario, idImovel);
        if ( imovel != null && imovel.getId().longValue() > 0 )
            return "S";
        else           
            return "N";
	}

	@Override
	public EmailImovel notificarInteresseImovel(Imovel imovel) {

		EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        Usuario usuarioDonoImovel = imovel.getUsuario();
        // assunto        
  //      email.setAssunto(getBundle().getString("msg.assunto.usuario.interessado"));                        
        // para
        email.setTo(usuarioDonoImovel.getEmail());
        
        // Texto do Email
  /*      texto.append(getBundle().getString("msg.inicio.corpo.email.usuario.interessado") + "\n");        
        texto.append(getBundle().getString("msg.corpo.email.titulo.imovel") + imovel.getTitulo() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.tipo.imovel") + imovel.getTipoImovel() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.estado.imovel") + imovel.getEstado() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.cidade.imovel") + imovel.getCidade() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.bairro.imovel") + imovel.getBairro() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.data.interesse") + new DateUtil().getStrDate() + "\n");
    */    
        email.setTextoEmail(texto.toString()); 
        email.setAcao("interesseImovel");
        return email;
	}

	@Override
	public EmailImovel notificarInteresseImovelFrm(ImovelForm frm) {

		Imovel imovel = imovelDao.findImovelById(frm.getId());
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        Usuario usuarioDonoImovel = imovel.getUsuario();
        // assunto        
     //  email.setAssunto(getBundle().getString("msg.assunto.usuario.interessado"));
        // para
        email.setTo(usuarioDonoImovel.getEmail());
        
        // Texto do Email
        
     /*   texto.append(getBundle().getString("msg.inicio.corpo.email.usuario.interessado") + "\n");        
        texto.append(getBundle().getString("msg.corpo.email.titulo.imovel") + imovel.getTitulo() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.tipo.imovel") + imovel.getTipoImovel() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.estado.imovel") + imovel.getEstado() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.cidade.imovel") + imovel.getCidade() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.bairro.imovel") + imovel.getBairro() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.data.interesse") + new DateUtil().getStrDate() + "\n");
      */  
        email.setTextoEmail(texto.toString());              
        email.setAcao("interesseImovel");
        return email;
	}
		

	@Override
	public Imovelfavoritos recuperarUltimoUsuarioInteressadoPorIdImovel(Long idImovel) {		
        return dao.findLastUsuarioInteressadoByIdImovel(idImovel);
	}


	@Override
	@Transactional
	public void adicionarFavoritosPorUsuario(Long idUsuario, Long idImovel) {
		Imovel imovel = imovelDao.findImovelById(idImovel);
		Imovelfavoritos imovelfavoritos = new Imovelfavoritos();
		imovelfavoritos.setImovel(imovelDao.findImovelById(idImovel));
		imovelfavoritos.setUsuario(usuarioDao.findUsuario(idUsuario));
		imovelfavoritos.setUsuarioDonoImovel(imovelfavoritos.getImovel().getUsuario());		
        imovelfavoritos.setStatus(StatusLeituraEnum.NOVO.getRotulo());
        imovelfavoritos.setDataInteresse(new Date());        
        dao.save(imovelfavoritos);  		
	}


	@Override
	public List<Imovelfavoritos> filtrarImoveisInteresse(Long idUsuario, ImovelfavoritosForm form) {
		return dao.filterImoveisInteresse(idUsuario, form);
	}


	@Override
	public List<Imovelfavoritos> filtrarUsuariosInteressado(Long idUsuario, ImovelfavoritosForm form) {
		return dao.filterUsuariosInteressados(idUsuario, form);	
	}
	
	@Override
	public List<Usuario> filtrarAgruparUsuario(Long idUsuario, ImovelfavoritosForm form) {		
		return this.agruparUsuarios(idUsuario, form);
	}
	
	@Override
	public List<Imovel> filtrarAgruparImoveis(Long idUsuario, ImovelfavoritosForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		List lista = dao.filtrarAgruparImoveis(idUsuario, form);		
		if (! CollectionUtils.isEmpty(lista) ){             
            Object[] obj = null;			
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();				
				imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));
				imovel.setQuantImoveisFavoritos(Integer.parseInt(obj[1].toString()));
				imovel.setQuantNovosImoveisFavoritos(dao.findQuantidadeNovosUsuariosInteressados(Long.parseLong(obj[0].toString())));
				listaFinal.add(imovel);
			}
        }
		return listaFinal;
	}


	@Override
	public List<Imovelfavoritos> ordenarImoveisFavoritos(Long idUsuario, ImovelfavoritosForm form) {

		List<Imovelfavoritos> lista = new ArrayList<Imovelfavoritos>();
		
		if ( form.getTipoLista().equals("usuariosInteressados"))
			lista = filtrarUsuariosInteressado(idUsuario, form);
		else 
			lista = filtrarImoveisInteresse(idUsuario, form);		
	
		return lista;
	}
	
	@Override
	public List<?> ordenarAgruparImoveisFavoritos(Long idUsuario, ImovelfavoritosForm form) {

		if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){			
			return this.agruparUsuarios(idUsuario, form);			
		}
		else if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){			
			return this.agruparImoveis(idUsuario, form);
		} 		
		return null;
	}

	
	@Override
	public List<Imovel> agruparImoveis(Long idUsuario, ImovelfavoritosForm form) {
		List<Imovel> listaFinal = new ArrayList<Imovel>();
		if ( form.getTipoLista().equals("usuariosInteressados")){
			// lista dos IdImovel cujo usuario sessao tenha recebido uma sinalizacao de interesse por parte de outro usuario do sistema
			List lista = dao.findImoveisFavoritosUsuariosInteressadosByIdUsuarioDistinct(idUsuario, form);
			Object[] obj = null;
			Long idImovel = null;
			long quantUsuariosInteressados = 0;
			long quantNovosUsuariosInteressados = 0;
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				idImovel = Long.parseLong(obj[0].toString());
				quantUsuariosInteressados = Long.parseLong(obj[1].toString());
				quantNovosUsuariosInteressados = dao.findQuantidadeNovosUsuariosInteressados(idImovel);
				imovel = imovelDao.findImovelById(idImovel);
				imovel.setQuantImoveisFavoritos(quantUsuariosInteressados);
				imovel.setQuantNovosImoveisFavoritos(quantNovosUsuariosInteressados);
				listaFinal.add(imovel);
			}
		}
		return listaFinal;
	}


	@Override
	public List<Usuario> agruparUsuarios(Long idUsuarioSessao,ImovelfavoritosForm form) {

		List<Usuario> listaFinal = new ArrayList<Usuario>();
		List lista = null;
		if ( form.getTipoLista().equals("usuariosInteressados")){
			// lista dos IdUsuario que sinalizou interesse em algum imovel do usuario sessao
			lista = dao.findUsuariosImoveisFavoritosUsuariosInteressadosByIdUsuario(idUsuarioSessao, form);						
		}
		else {
			// lista dos IdUsuario que o usuario sessao sinalizou algum interesse em algum imovel de outros usuarios da aplicacao
			lista = dao.findUsuariosImoveisFavoritosImoveisMeuInteresseByIdUsuario(idUsuarioSessao, form);
		}
		
		Object[] obj = null;			 
		Usuario usuario = null;
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next(); //[0] - usuarioRecep, [1] - usuarioEmissor, [2] - quantidade (count)				
			usuario = usuarioDao.findUsuario(Long.parseLong(obj[0].toString()));
			usuario.setQuantImovelFavoritos(Integer.parseInt(obj[1].toString())); // quantidade de Propostas que o usuario enviou para os imoveis do usuario sessao
			usuario.setQuantImovelVisitado(Integer.parseInt(obj[1].toString()));
			usuario.setQuantTotalContatos(contatoDao.findQuantidadeTotalContatosByIdUsuarioByStatus(usuario.getId(), ContatoStatusEnum.OK.getRotulo()));
			usuario.setQuantTotalRecomendacoes(recomendacaoDao.findQuantidadeRecomendacoesByUsuarioByStatusByStatusLeitura(usuario.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo(), null));
			usuario.setQuantTotalImoveis(imovelDao.findQuantMeusImoveis(usuario.getId()));
			usuario.setQuantTotalSeguidores(seguidorDao.findQuantSeguidoresByIdUsuarioSeguido(usuario.getId()));
			listaFinal.add(usuario);
		}
		return listaFinal;
	}


	@Override
	public List<Imovel> recuperarImoveisInteressadosPorIdUsuarioPorDonoImovel(Long idUsuario, Long idDonoImovel) {
		List lista = dao.findImoveisInteressadosByIdUsuarioByIdDonoImovel(idUsuario, idDonoImovel);
		Object[] obj = null;
		Imovel imovel = null;		
		List<Imovel> listaFinal = new ArrayList<Imovel>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next(); 
			imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));
			try {
				imovel.setDataInteresse(df.parse(obj[1].toString()));
			} catch (ParseException e) {				
			}
			listaFinal.add(imovel);
		}	
		return listaFinal;
	}

	@Override
	public List<Imovelfavoritos> listarUsuariosInteressadosMeusImoveis(
			Long idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long checarQuantidadeUsuariosInteressadosPorUsuario(Long idUsuario, String status) {
		return (long) dao.findQuantUsuariosInteressadosByIdUsuarioByStatus(idUsuario, status);
	}

	@Override
	@Transactional
	public void atualizarStatusLeitura(Long idUsuario) {
		dao.updateStatusLeitura(idUsuario);		
	}

	@Override
	public List<Imovelfavoritos> recuperarUsuariosInteressadosPorIdImovelPorQuant(Long idImovel, int quantMaxLista) {
		return dao.findUsuariosInteressadosPorIdImovelByQuant(idImovel, quantMaxLista);
	}

}
