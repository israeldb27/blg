package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.busqueumlugar.dao.ImovelcomentarioDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelcomentarioForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelcomentario;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelcomentarioService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.DateUtil;

@Service
public class ImovelcomentarioServiceImpl implements ImovelcomentarioService {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelcomentarioServiceImpl.class);
	
	@Autowired
	private ImovelcomentarioDao dao;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Override
	public Imovelcomentario recuperarImovelcomentarioPorId(Long id) {
		return dao.findImovelcomentarioById(id);
	}

	@Override
	public List<Imovel> relatorioImoveisMaisComentadosPorPeriodo(RelatorioForm frm) {

        List lista = dao.relatorioImovelMaisComentadosPeriodo(frm);																				
        List<Imovel> listaFinal = new ArrayList<Imovel>();
        if (! CollectionUtils.isEmpty(lista)){
            Imovel imovel = null;       
            for (Iterator iter = lista.iterator();iter.hasNext();){                
                Object[] obj = (Object[]) iter.next();
                imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));                
                imovel.setQuantidade(Integer.parseInt(obj[1].toString()));
                listaFinal.add(imovel);
            }
        }              
        return listaFinal;	
	}
	
	@Override
	public List<Imovel> relatorioImoveisMaisComentadosPorPeriodo(AdministracaoForm form) {
		
        List lista = dao.relatorioImovelMaisComentadosPeriodo(form);																				
        List<Imovel> listaFinal = new ArrayList<Imovel>();
        if (! CollectionUtils.isEmpty(lista)){
            Imovel imovel = null;       
            for (Iterator iter = lista.iterator();iter.hasNext();){                
                Object[] obj = (Object[]) iter.next();
                imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));                
                imovel.setQuantidade(Integer.parseInt(obj[1].toString()));
                listaFinal.add(imovel);
            }
        }             
        return listaFinal;
	}

	@Override
	public List<Imovelcomentario> listarComentarios(Long idImovel, ImovelcomentarioForm form) {
        return dao.findImovelcomentariosByIdImovel(idImovel, form);
	}

	@Override
	@Transactional
	public void cadastrarComentario(Long idImovel, UsuarioForm usuarioForm,	String comentario) {
		Imovelcomentario imovelcomentario = new Imovelcomentario();
        imovelcomentario.setComentario(comentario);
        imovelcomentario.setImovel(imovelService.recuperarImovelPorid(idImovel));        
        imovelcomentario.setDataComentario(new Date());
        imovelcomentario.setUsuarioComentario(usuarioService.recuperarUsuarioPorId(usuarioForm.getId()));   
        imovelcomentario.setUsuario(imovelcomentario.getImovel().getUsuario());
        imovelcomentario.setStatus(StatusLeituraEnum.NOVO.getRotulo());
        dao.save(imovelcomentario);  
	}
	
	@Override
	@Transactional
	public void cadastrarComentario(Long idImovel, Long idUsuario,	String comentario) {		
		Imovelcomentario imovelcomentario = new Imovelcomentario();
        imovelcomentario.setComentario(comentario);
        imovelcomentario.setImovel(imovelService.recuperarImovelPorid(idImovel));
        imovelcomentario.setDataComentario(new Date());
        imovelcomentario.setUsuarioComentario(usuarioService.recuperarUsuarioPorId(idUsuario));   
        imovelcomentario.setUsuario(imovelcomentario.getImovel().getUsuario());   
        imovelcomentario.setStatus(StatusLeituraEnum.NOVO.getRotulo());
        dao.save(imovelcomentario);  
	}

	@Override
	@Transactional
	public void excluirComentarioImovel(Imovelcomentario comentario) {
		dao.delete(comentario);  		
	}

	@Override
	public List<Imovelcomentario> listarComentariosPorUsuario(Long idUsuario) {		
        return dao.findImovelcomentariosByIdUsuario(idUsuario);
	}

	@Override
	public List<Imovelcomentario> listarComentariosSobreMeuImovelOutros(ImovelcomentarioForm form,  Long idUsuario) {
		
		List lista = dao.findImoveisComentariosSobreMeusImoveisInfoTotais(form, idUsuario);
		List<Imovelcomentario> listaFinal = new ArrayList<Imovelcomentario>();
		if ( ! CollectionUtils.isEmpty(lista)){
			Imovelcomentario imovelComentario = null;			
			Object[] obj = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				imovelComentario = dao.findLastImovelcomentarioByIdImovel(Long.parseLong(obj[0].toString()));
				imovelComentario.setTotalComentarios(Integer.parseInt(obj[1].toString()));
				listaFinal.add(imovelComentario);				
			}
		}		
		
		return listaFinal;
		
	}

	@Override
	public long checaQuantidadeNovoComentario(Long idUsuario) {
		return dao.findQuantImoveisComentariosRecebidos(idUsuario, "novo");
	}

	@Override
	public List<Imovelcomentario> recuperarNovosComentarios(List<Imovelcomentario> lista) {
		List<Imovelcomentario> listaFinal = new ArrayList<Imovelcomentario>();
        for (Imovelcomentario imovelcomentario : lista){            
            if ( imovelcomentario.getStatus().equals("novo"))
                listaFinal.add(imovelcomentario);
        }
        return listaFinal;
	}

	@Override
	@Transactional
	public boolean atualizarStatusImovelComentario(Imovelcomentario imovelcomentario) {
		boolean resultado = false;
        if ( imovelcomentario.getStatus().equals(StatusLeituraEnum.NOVO.getRotulo())){
             imovelcomentario.setStatus(StatusLeituraEnum.LIDO.getRotulo());
             resultado = false;
        }
        else {
             imovelcomentario.setStatus(StatusLeituraEnum.NOVO.getRotulo());
             resultado = false;
        }
        dao.save(imovelcomentario);
        return resultado;
	}

	@Override
	public long checaQuantidadeNovoComentarioPorImovel(Long idImovel) {
		return  dao.findQuantImovelcomentariosByIdImovelStatus(idImovel, StatusLeituraEnum.NOVO.getRotulo());        
	}
	
	@Override
	public List<Imovel> checarImoveisMaisReceberamComentariosPorPeriodo(Date dataInicio, Date dataFim, int quant) {

		List lista = dao.checarImoveisComMaisComentariosPeriodo(dataInicio, dataFim, quant);
        List<Imovel> listaFinal = new ArrayList<Imovel>();        
        if ( lista != null && lista.size() > 0){
            Imovel imovel = null;
            for (Iterator iter = lista.iterator();iter.hasNext();){
                Object[] obj = (Object[]) iter.next();
                imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));                
                listaFinal.add(imovel);
            }
        }
        return listaFinal;
	}

	@Override
	public EmailImovel notificarComentarioImovel(Long idImovel) {

		Imovel imovel = imovelService.recuperarImovelPorid(idImovel);
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        Usuario usuarioDonoImovel = imovel.getUsuario();
        // assunto        
        email.setAssunto("Coment�rio sobre um de seus im�veis - BusqueUmLugar.com.br");
        // para
        email.setTo(usuarioDonoImovel.getEmail());
        
        // Texto do Email
        texto.append("Um usu�rio lan�ou um coment�rio sobre um de seus im�veis. Segue abaixo: " + "\n");
        texto.append("T�tulo im�vel: " + imovel.getTitulo() + "\n");
        texto.append("Tipo Im�vel: " + imovel.getTipoImovel() + "\n");
        texto.append("Estado: " + imovel.getEstado() + "\n");
        texto.append("Cidade: " + imovel.getCidade() + "\n");
        texto.append("Bairro: " + imovel.getBairro() + "\n");
        texto.append("Data coment�rio: " + new DateUtil().getStrDate() + "\n");
        email.setTextoEmail(texto.toString());      
        
        email.setAcao("comentarioImovel");
        return email;
	}

	@Override
	public List<Imovelcomentario> ordenarImoveisComentarios(Long idUsuario,
															String opcaoOrdenacao,
															String tipoLista) {

			List<Imovelcomentario> lista = new ArrayList<Imovelcomentario>();		
			if ( tipoLista.equals("meusComentarios"))
				lista = dao.findImovelcomentariosByIdUsuario(idUsuario);
			else
				lista = listarComentariosSobreMeuImovelOutros(null, idUsuario);
    
	        
	         if (opcaoOrdenacao.equals("maiorValorImovel")){
	            Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getImovel().getValorImovel().doubleValue() > imovel2.getImovel().getValorImovel().doubleValue() ? -1 : (imovel1.getImovel().getValorImovel().doubleValue() < imovel2.getImovel().getValorImovel().doubleValue() ? +1 : 0);  
	                }  
	            }); 
	        }
	        else if (opcaoOrdenacao.equals("menorValorImovel")){
	             Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getImovel().getValorImovel().doubleValue() < imovel2.getImovel().getValorImovel().doubleValue() ? -1 : (imovel1.getImovel().getValorImovel().doubleValue() > imovel2.getImovel().getValorImovel().doubleValue() ? +1 : 0);  
	                }  
	            }); 
	        }    
	        else if (opcaoOrdenacao.equals("maiorDataComentario")){
	            Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getDataComentario().compareTo(imovel2.getDataComentario()) > 0 ? -1 : (imovel1.getDataComentario().compareTo(imovel2.getDataComentario()) < 0 ? +1 : 0);  
	                }  
	            }); 
	        }
	        else if (opcaoOrdenacao.equals("menorDataComentario")){
	            Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getDataComentario().compareTo(imovel2.getDataComentario()) < 0 ? -1 : (imovel1.getDataComentario().compareTo(imovel2.getDataComentario()) > 0 ? +1 : 0);  
	                }  
	            }); 
	        }
	        else if (opcaoOrdenacao.equals("tituloImovelCrescente")){
	            Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getImovel().getTitulo().compareTo(imovel2.getImovel().getTitulo()) < 0 ? -1 : (imovel1.getImovel().getTitulo().compareTo(imovel2.getImovel().getTitulo()) > 0 ? +1 : 0);  
	                }  
	            }); 
	        }
	         else if (opcaoOrdenacao.equals("tituloImovelDeCrescente")){
	            Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getImovel().getTitulo().compareTo(imovel2.getImovel().getTitulo()) > 0 ? -1 : (imovel1.getImovel().getTitulo().compareTo(imovel2.getImovel().getTitulo()) < 0 ? +1 : 0);  
	                }  
	            }); 
	        }        
	        return lista;
	}
	
	@Override
	public List<Imovelcomentario> ordenarImoveisComentarios(List<Imovelcomentario> lista, String opcaoOrdenacao) {			
	        
	         if (opcaoOrdenacao.equals("maiorValorImovel")){
	            Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getImovel().getValorImovel().doubleValue() > imovel2.getImovel().getValorImovel().doubleValue() ? -1 : (imovel1.getImovel().getValorImovel().doubleValue() < imovel2.getImovel().getValorImovel().doubleValue() ? +1 : 0);  
	                }  
	            }); 
	        }
	        else if (opcaoOrdenacao.equals("menorValorImovel")){
	             Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getImovel().getValorImovel().doubleValue() < imovel2.getImovel().getValorImovel().doubleValue() ? -1 : (imovel1.getImovel().getValorImovel().doubleValue() > imovel2.getImovel().getValorImovel().doubleValue() ? +1 : 0);  
	                }  
	            }); 
	        }    
	        else if (opcaoOrdenacao.equals("maiorDataComentario")){
	            Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getDataComentario().compareTo(imovel2.getDataComentario()) > 0 ? -1 : (imovel1.getDataComentario().compareTo(imovel2.getDataComentario()) < 0 ? +1 : 0);  
	                }  
	            }); 
	        }
	        else if (opcaoOrdenacao.equals("menorDataComentario")){
	            Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getDataComentario().compareTo(imovel2.getDataComentario()) < 0 ? -1 : (imovel1.getDataComentario().compareTo(imovel2.getDataComentario()) > 0 ? +1 : 0);  
	                }  
	            }); 
	        }
	        else if (opcaoOrdenacao.equals("tituloImovelCrescente")){
	            Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getImovel().getTitulo().compareTo(imovel2.getImovel().getTitulo()) < 0 ? -1 : (imovel1.getImovel().getTitulo().compareTo(imovel2.getImovel().getTitulo()) > 0 ? +1 : 0);  
	                }  
	            }); 
	        }
	         else if (opcaoOrdenacao.equals("tituloImovelDeCrescente")){
	            Collections.sort (lista, new Comparator() {  
	                public int compare(Object o1, Object o2) {  
	                    Imovelcomentario imovel1 = (Imovelcomentario) o1;  
	                    Imovelcomentario imovel2 = (Imovelcomentario) o2;  
	                    return imovel1.getImovel().getTitulo().compareTo(imovel2.getImovel().getTitulo()) > 0 ? -1 : (imovel1.getImovel().getTitulo().compareTo(imovel2.getImovel().getTitulo()) < 0 ? +1 : 0);  
	                }  
	            }); 
	        }        
	        return lista;
	}

	@Override
	public List<Imovelcomentario> agruparImoveis(List<Imovelcomentario> listaImoveisComentariosOutros) {

		TreeSet<Long> set = new TreeSet();
       // agrupando por idImovel e eliminando duplicidades
       for (Imovelcomentario imovel : listaImoveisComentariosOutros){
           set.add(imovel.getImovel().getId());
       }
       
       List<Imovelcomentario> lista = new ArrayList<Imovelcomentario>();
       Imovelcomentario imovelcomentario = null;
       for(Long idImovel : set){
           imovelcomentario = recuperarUltimoComentarioPorIdImovel(idImovel);
           lista.add(imovelcomentario);
       }
       
       List<Imovelcomentario> listaFinal = new ArrayList<Imovelcomentario>(lista);
       return listaFinal;
	}
	
	@Override
	public List<Imovelcomentario> agruparImoveis(Long idImovelSelecionado) {

	   List<Imovelcomentario> listaImoveisComentariosOutros = dao.findImovelcomentariosByIdImovel(idImovelSelecionado, null);
		TreeSet<Long> set = new TreeSet();
       // agrupando por idImovel e eliminando duplicidades
       for (Imovelcomentario imovel : listaImoveisComentariosOutros){
           set.add(imovel.getImovel().getId());
       }
       
       List<Imovelcomentario> lista = new ArrayList<Imovelcomentario>();
       Imovelcomentario imovelcomentario = null;
       for(Long idImovel : set){
           imovelcomentario = recuperarUltimoComentarioPorIdImovel(idImovel);
           lista.add(imovelcomentario);
       }
       
       List<Imovelcomentario> listaFinal = new ArrayList<Imovelcomentario>(lista);
       return listaFinal;
	}

	@Override
	public Imovelcomentario recuperarUltimoComentarioPorIdImovel(Long idImovel) {
		Imovelcomentario imovel = dao.findLastImovelcomentarioByIdImovel(idImovel);
        return imovel;
	}

	@Override
	public List<Imovelcomentario> recuperarTodosComentariosPorImovel(Long idImovel) {

		 List<Imovelcomentario> lista = dao.findImovelcomentariosByIdImovel(idImovel, null);
	        List<Imovelcomentario> listaFinal =  new ArrayList<Imovelcomentario>();
	        for (Imovelcomentario imovel : lista){
	            listaFinal.add(imovel);
	        }
	        return listaFinal;
	}

	@Override
	public List<Imovelcomentario> agruparImoveisMeusComentarios(List<Imovelcomentario> listaImoveisComentarios, Long idUsuario) {
			TreeSet<Long> set = new TreeSet();
	       // agrupando por idImovel e eliminando duplicidades
	       for (Imovelcomentario imovel : listaImoveisComentarios){
	           if ( imovel.getUsuarioComentario().getId() == idUsuario.longValue() )
	              set.add(imovel.getImovel().getId());
	       }
	       
	       List<Imovelcomentario> lista = new ArrayList<Imovelcomentario>();
	       Imovelcomentario imovelcomentario = null;
	       for(Long idImovel : set){
	           imovelcomentario = recuperarUltimoComentarioPorIdImovel(idImovel);
	           lista.add(imovelcomentario);
	       }
	       
	       List<Imovelcomentario> listaFinal = new ArrayList<Imovelcomentario>(lista);
	       return listaFinal;
	}

	@Override
	public List<Imovel> agruparImoveis(Long idUsuario, ImovelcomentarioForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		if ( form.getTipoLista().equals("comentariosSobreMeusImoveis")){
			// lista dos IdImovel cujo usuario sessao tenha recebido pelo menos um comentario em seu imovel
			List lista = dao.findImoveisComentariosSobreMeusImoveisDistinct(idUsuario);
			Object[] obj = null;
			Long idImovel = null;
			int quantComentariosRecebidos      = 0;
			int quantNovosComentariosRecebidos = 0;
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				idImovel = Long.parseLong(obj[0].toString());
				quantComentariosRecebidos = Integer.parseInt(obj[1].toString());
				quantNovosComentariosRecebidos = (int)dao.findQuantImovelcomentariosByIdImovelStatus(idImovel, StatusLeituraEnum.NOVO.getRotulo());
				imovel = imovelService.recuperarImovelPorid(idImovel);
				imovel.setQuantComentarios(quantComentariosRecebidos);				
				imovel.setQuantNovosComentarios(quantNovosComentariosRecebidos);
				listaFinal.add(imovel);
			}
		}
		else if ( form.getTipoLista().equals("meusComentarios")){
			// lista dos IdImovel cujo usuario sessao tenha enviado algum comentario de algum imóvel do sistema
			List lista = dao.findMeusComentariosByIdUsuarioDistinct(idUsuario);
			Object[] obj = null;
			Long idImovel = null;
			int quantMeusComentarios = 0;					
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				idImovel = Long.parseLong(obj[0].toString());
				quantMeusComentarios = Integer.parseInt(obj[1].toString());				
				imovel = imovelService.recuperarImovelPorid(idImovel);
				imovel.setQuantComentarios(quantMeusComentarios);
				listaFinal.add(imovel);
			}
		}
		
		return listaFinal;
	}

	@Override
	public List<Usuario> agruparUsuarios(Long idUsuarioSessao, ImovelcomentarioForm form) {
		List<Usuario> listaFinal = new ArrayList<Usuario>();
		if ( form.getTipoLista().equals("comentariosSobreMeusImoveis")){
			// lista dos IdUsuario cujo usuario sessao tenha recebido pelo menos um comentario em seu imovel
			List lista = dao.findUsuariosImoveisComentariosSobreMeusImoveisDistinct(idUsuarioSessao);
			Object[] obj = null;
			Long idUsuario = null;
			int quantComentarios = 0;
			Usuario usuario = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				idUsuario = Long.parseLong(obj[0].toString());
				quantComentarios = Integer.parseInt(obj[1].toString());
				usuario = usuarioService.recuperarUsuarioPorId(idUsuario);
				usuario.setQuantComentarios(quantComentarios);
				listaFinal.add(usuario);
			}			
		}
		else if ( form.getTipoLista().equals("meusComentarios")){
			// lista dos IdUsuario cujo usuario sessao tenha recebido pelo menos um comentario em seu imovel
			List lista = dao.findUsuariosMeusComentariosDistinct(idUsuarioSessao);
			Object[] obj = null;
			Long idUsuario = null;
			int quantComentarios = 0;
			Usuario usuario = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				idUsuario = Long.parseLong(obj[0].toString());
				quantComentarios = Integer.parseInt(obj[1].toString());
				usuario = usuarioService.recuperarUsuarioPorId(idUsuario);
				usuario.setQuantComentarios(quantComentarios);				
				listaFinal.add(usuario);
			}	
		}
		return listaFinal;
	}

	@Override
	public List<Imovelcomentario> listarComentariosSobreMeusImoveisPorusuario(Long idUsuarioSessao, Long idUsuario) {		
		return dao.findImoveisComentariosSobreMeusImoveisPorUsuario(idUsuarioSessao, idUsuario);
	}

	@Override
	public List<Imovelcomentario> filtrarComentariosSobreMeusImoveis(Long idUsuario , ImovelcomentarioForm form) {

		List<Imovelcomentario> listaFinal = new ArrayList<Imovelcomentario>();
		List lista = dao.findImoveisComentariosSobreMeusImoveisInfoTotais(form, idUsuario);
		if ( ! CollectionUtils.isEmpty(lista)){
			Imovelcomentario imovel = null;
			Object[] obj = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				//imovel = dao.findImovelcomentarioById(Long.parseLong(obj[0].toString()));
				imovel = dao.findLastImovelcomentarioByIdImovel(Long.parseLong(obj[0].toString()));
				imovel.setTotalComentarios(Integer.parseInt(obj[1].toString()));				
				listaFinal.add(imovel);				
			}
		}	

		return listaFinal;
	}

	@Override
	public List<Imovelcomentario> filtrarrMeusComentarios(Long idUsuario,  ImovelcomentarioForm form) {
		return dao.filterMeusComentarios(form,  idUsuario);
	}
	


	@Override
	@Transactional
	public void atualizarStatusComentariosMeusImoveis(Long idUsuario) {		
		dao.updateStatusLeitura(idUsuario);
	}

	@Override
	public long checarQuantidadeTotalImoveisComentariosRecebidos(Long idUsuario) {
		return dao.findQuantImoveisComentariosRecebidos(idUsuario, null);
	}

}
