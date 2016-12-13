<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<spring:url var="urlUsuario" value="/usuario"/>
<spring:url var="urlImovelComentario" value="/imovelComentario"/>
<spring:url var="urlImovelCompartilhado" value="/imovelCompartilhado"/>
<spring:url var="urlImovel" value="/imovel"/>
<spring:url var="urlImovelIndicado" value="/imovelIndicado"/>
<spring:url var="urlImovelDestaque" value="/imovelDestaque"/>
<spring:url var="urlImovelFavoritos" value="/imovelFavoritos"/>
<spring:url var="urlImovelPropostas" value="/imovelPropostas"/>
<spring:url var="urlImovelVisualizado" value="/imovelVisualizado"/>
<spring:url var="urlImovelComparativo" value="/imovelComparativo"/>
<spring:url var="urlMensagem" value="/mensagem"/>
<spring:url var="urlMensagemAdmin" value="/mensagemAdmin"/>
<spring:url var="urlNota" value="/nota"/>
<spring:url var="urlNotificacao" value="/notificacao"/>
<spring:url var="urlPerfil" value="/perfil"/>
<spring:url var="urlPlano" value="/plano"/>
<spring:url var="urlPreferencia" value="/preferencia"/>
<spring:url var="urlRelatorio" value="/relatorio"/>
<spring:url var="urlServico" value="/servico"/>
<spring:url var="urlContato" value="/contato"/>
<spring:url var="urlEstado" value="/estado"/>
<spring:url var="urlCidade" value="/cidade"/>
<spring:url var="urlEstado" value="/estado"/>
<spring:url var="urlGmap" value="/gmap"/>


<aside id="sidebar-left" class="sidebar-circle" >

                <!-- Start left navigation - profile shortcut -->
                <div class="sidebar-content">
                    <div class="media">
                        <a class="pull-left has-notif avatar" href="${urlUsuario}/detalhesUsuario/${usuario.id}">
                            <img src="${context}${usuario.imagemArquivo}" style="width: 50px; height: 50px; " alt="admin"/>
                            <i class="online"></i>                            
                        </a>
                        <div class="media-body">
                            <h4 class="media-heading">
								<span><a href="${urlUsuario}/detalhesUsuario/${usuario.id}"> ${usuario.nome} </a> </span>
							</h4>    
							<small>${usuario.perfilFmt} </small>
                        </div>
                    </div>
                </div><!-- /.sidebar-content -->
                <!--/ End left navigation -  profile shortcut -->

                <!-- Start left navigation - menu -->
                <ul class="sidebar-menu">                

                    <!-- Start category apps -->
                    <li class="sidebar-category">
                        <span>Menu</span>
                        <span class="pull-right"><i class="fa fa-trophy"></i></span>
                    </li>
                    <!--/ End category apps -->

                    <!-- Start navigation - Consultas -->
                    <li class="submenu">
                        <a href="javascript:void(0);">
                            <span class="icon"><i class="fa fa-search"></i></span>
                            <span class="text"><spring:message code="lbl.title.link.consultas"/></span>
                            <span class="arrow"></span>
                        </a>  
                        <ul> 
                        	<li><a href="${urlImovel}/listarMeusImoveis"><spring:message code="lbl.title.link.meus.imoveis"/><span class="label label-primary pull-right rounded"><%= request.getSession().getAttribute("quantMeusImoveis") %></span></a></li>                            
                            <li><a href="${urlImovel}/prepararBuscaImoveis"><spring:message code="lbl.title.link.busca.imoveis"/> </a></li>							
                            <li><a href="${urlUsuario}/prepararBuscaUsuarios"><spring:message code="lbl.title.link.busca.usuarios"/></a></li>           
                            <li><a href="${urlRelatorio}/prepararRelatorios"><spring:message code="lbl.title.link.relatorios"/></a></li>
                            <c:if test="${usuario.perfil == 'P' }">
                             		<li><a href="${urlPreferencia}/listarPreferencias"><spring:message code="lbl.title.link.pref.imoveis"/></a></li>						          	
					          </c:if>
					        <% if ( Long.parseLong(request.getSession().getAttribute("quantListaImovelComparativo").toString()) > 0l ) {%>
					        	<li><a href="${urlImovelComparativo}/listarImoveisComparativos"><spring:message code="lbl.title.link.comparativo"/><span class="label label-danger pull-right rounded">&nbsp;&nbsp; <%= request.getSession().getAttribute("quantListaImovelComparativo") %></span></a></li>
					        <% } else { %>
					        	<li><a href="${urlImovelComparativo}/listarImoveisComparativos"><spring:message code="lbl.title.link.comparativo"/></a></li> 
					        <% }  %>  					        
					                      
					        <% if ( request.getSession().getAttribute("habilitaFuncPlanos").equals("S") ) {%>
	                        	<li><a href="${urlServico}/listarPlanosUsuario"><spring:message code="lbl.title.link.planos"/></a></li>
	                        <% }  %>
	                        
	                        <% if ( request.getSession().getAttribute("habilitaFuncServicos").equals("S") ) {%>
	                        	<li><a href="${urlServico}/listarServicosUsuario"><spring:message code="lbl.title.link.servicos"/></a></li>
	                        <% }  %>                            
                            
                            
                        </ul>
                    </li>
                    <!--/ End navigation - Consultas -->
                    
                    <!-- Start navigation - Favoritos -->
                    <li class="submenu">  
                        <a href="javascript:void(0);">
                            <span class="icon"><i class="fa fa-television"></i></span>                            
                            <span class="text"><spring:message code="lbl.title.link.lista.Favoritos"/></span>                                                        
                            <% if ( Long.parseLong(request.getSession().getAttribute("quantNovoUsuarioInteressado").toString()) > 0l ) {%>
                            	<span class="label label-danger pull-right rounded">&nbsp;&nbsp; <spring:message code="lbl.opcao.geral.novo"/> </span>
                            <% } else { %>
                            	<span class="arrow"></span>
                             <% }  %>                             
                        </a>
                        <ul>
                        	<li><a href="${urlImovelFavoritos}/listarInteresse/meuInteresse"><spring:message code="lbl.title.link.lista.Favoritos.meus.Favoritos"/></a></li>
                        
                        	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovoUsuarioInteressado").toString()) > 0l ) {%>
                        		<li><a href="${urlImovelFavoritos}/listarInteresse/usuariosInteressados"><spring:message code="lbl.title.link.lista.Favoritos.usuarios.interessados"/><span class="label label-danger pull-right rounded">&nbsp;&nbsp; <%= request.getSession().getAttribute("quantNovoUsuarioInteressado") %></span></a></li>
                        	<% } else { %>
                            	<li><a href="${urlImovelFavoritos}/listarInteresse/usuariosInteressados"><spring:message code="lbl.title.link.lista.Favoritos.usuarios.interessados"/></a></li>
                             <% }  %>                  
                                                        
                        </ul>
                    </li>
                    <!--/ End navigation - Favoritos -->  
                    
                    <!-- Start navigation - Visitas Imóveis -->
                    <li class="submenu">
                        <a href="javascript:void(0);">
                            <span class="icon"><i class="fa fa-user"></i></span>  
                            <span class="text"><spring:message code="lbl.title.link.imoveis.visualizacoes"/></span>
                            <% if ( Long.parseLong(request.getSession().getAttribute("quantNovosVisitantes").toString()) > 0l ) {%>
                            	<span class="label label-danger pull-right rounded">&nbsp;&nbsp; Novo</span>
                           	 <% } else { %>
                            	<span class="arrow"></span>
                             <% }  %>                            
                        </a>
                        <ul>
                        	<li><a href="${urlImovelVisualizado}/listarUsuariosVisitantes/imoveisVisitados"><spring:message code="lbl.title.link.imoveis.visualizacoes.minhas"/></a></li>
                          
                        	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovosVisitantes").toString()) > 0l ) {%>
                        		<li><a href="${urlImovelVisualizado}/listarUsuariosVisitantes/meusImoveisVisitados"><spring:message code="lbl.title.link.imoveis.visualizacoes.recebidas"/><span class="label label-danger pull-right rounded">&nbsp;&nbsp; <%= request.getSession().getAttribute("quantNovosVisitantes") %></span></a></li>
                        	<% } else { %>
                            	<li><a href="${urlImovelVisualizado}/listarUsuariosVisitantes/meusImoveisVisitados"><spring:message code="lbl.title.link.imoveis.visualizacoes.recebidas"/></a></li>
                             <% }  %>                        	                            
                                                        
                        </ul>
                    </li>
                    <!--/ End navigation - Visitas Imóveis -->
                    
                  <!-- Start navigation - Intermediações -->
                    <li class="submenu">
                        <a href="javascript:void(0);">
                            <span class="icon"><i class="fa fa-industry"></i></span>
                            <span class="text"><spring:message code="lbl.title.link.intermediacoes"/></span>
                            <c:if test="${usuario.perfil == 'P'}">
                            	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasIntermediacoes").toString()) > 0l ) {%>
	                              	<span class="label label-danger pull-right rounded">&nbsp;&nbsp; Novo</span>
	                            <% } else { %>
	                            	<span class="arrow"></span>                            	
	                            <% }  %>
                            </c:if>  
                            <c:if test="${usuario.perfil == 'P'}">
                            	<span class="arrow"></span>
                            </c:if>
                        </a>
                        <ul>
                        	<li><a href="${urlImovelCompartilhado}/listarImoveisIntermediacoes/intermediacaoAceita"><spring:message code="lbl.title.link.aceita"/></a></li> 
                        	
                        	<c:if test="${usuario.perfil == 'P'}">
                        		<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasIntermediacoes").toString()) > 0l ) {%>
	                        		<li><a href="${urlImovelCompartilhado}/listarImoveisIntermediacoes/intermediacaoSolRecebida"><spring:message code="lbl.title.link.solicitacoes.recebidas"/><span class="label label-danger pull-right rounded">&nbsp;&nbsp; <%= request.getSession().getAttribute("quantNovasIntermediacoes") %></span></a></li>
	                        	<% } else { %>
	                            	<li><a href="${urlImovelCompartilhado}/listarImoveisIntermediacoes/intermediacaoSolRecebida"><spring:message code="lbl.title.link.solicitacoes.recebidas"/></a></li>	                            	
	                            <% }  %>
                        	</c:if>
                        	<c:if test="${usuario.perfil != 'P'}">
                        		<li><a href="${urlImovelCompartilhado}/listarImoveisIntermediacoes/intermediacaoMinhasSol"><spring:message code="lbl.title.link.solicitacoes.enviadas"/></a></li>
                        	</c:if>                            
                            
                        </ul>
                    </li>
                    <!--/ End navigation - Intermediações-->
                    
                    <c:if test="${usuario.perfil != 'P'}">
	                    <!-- Start navigation - Parcerias -->
	                    <li class="submenu">
	                        <a href="javascript:void(0);">
	                            <span class="icon"><i class="fa fa-users"></i></span>
	                            <span class="text"><spring:message code="lbl.title.link.parcerias"/></span>   
	                            <% if ( Long.parseLong(request.getSession().getAttribute("quantNovasParcerias").toString()) > 0l ) {%>
	                               	<span class="label label-danger pull-right rounded">&nbsp;&nbsp; Novo</span>
	                            <% } else { %>
	                            	<span class="arrow"></span>                            	
	                            <% }  %> 
	                        </a>
	                        <ul>
	                        	<li><a href="${urlImovelCompartilhado}/listarImoveisParcerias/parceriaAceita"><spring:message code="lbl.title.link.aceita"/></a></li> 
	                        	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasParcerias").toString()) > 0l ) {%>
	                        		<li><a href="${urlImovelCompartilhado}/listarImoveisParcerias/parceriaSolRecebida"><spring:message code="lbl.title.link.solicitacoes.recebidas"/><span class="label label-danger pull-right rounded">&nbsp;&nbsp; <%= request.getSession().getAttribute("quantNovasParcerias") %></span></a></li>
	                        	<% } else { %>
	                            	<li><a href="${urlImovelCompartilhado}/listarImoveisParcerias/parceriaSolRecebida"><spring:message code="lbl.title.link.solicitacoes.recebidas"/></a></li>                            	
	                            <% }  %>                                                        
	                                                        
	                            <li><a href="${urlImovelCompartilhado}/listarImoveisParcerias/parceriaMinhasSol"><spring:message code="lbl.title.link.solicitacoes.enviadas"/></a></li>
	                        </ul>
	                    </li>
	                    <!--/ End navigation - Parcerias-->
                    </c:if>
                    
                    <!-- Start navigation - Notas -->
                    <li class="submenu">
                        <a href="javascript:void(0);">  
                            <span class="icon"><i class="fa fa-sticky-note-o"></i></span>
                            <span class="text"><spring:message code="lbl.title.link.notas"/></span>
                            <span class="arrow"></span>            
                        </a>
                        <ul>  
                        	<li><a href="${urlNota}/minhasNotas"><spring:message code="lbl.title.link.minhas.notas"/></a></li>                            
                            <li><a href="${urlNota}/notasContatos"><spring:message code="lbl.title.link.notas.contatos"/></a></li>
                        </ul>
                    </li>
                    <!--/ End navigation - Notas-->
                    
                     <!-- Start navigation - Propostas -->
                    <li class="submenu">
                        <a href="javascript:void(0);">
                            <span class="icon"><i class="fa fa-money"></i></span>
                            <span class="text"><spring:message code="lbl.title.link.propostas.imoveis"/></span>
                            <% if ( Long.parseLong(request.getSession().getAttribute("quantNovasImoveisPropostas").toString()) > 0l ) {%>
                              	<span class="label label-danger pull-right rounded">&nbsp;&nbsp; Novo</span>
                            <% } else { %>
                            	<span class="arrow"></span>                            	
                            <% }  %>  
                        </a>
                        <ul>
                        	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasImoveisPropostas").toString()) > 0l ) {%>
                        		<li><a href="${urlImovelPropostas}/listarImoveisPropostas/propostasRecebidas"><spring:message code="lbl.title.link.propostas.imoveis.recebidas2"/><span class="label label-danger pull-right rounded">&nbsp;&nbsp; <%= request.getSession().getAttribute("quantNovasImoveisPropostas") %></span></a></li>
                        	<% } else { %>
                            	<li><a href="${urlImovelPropostas}/listarImoveisPropostas/propostasRecebidas"><spring:message code="lbl.title.link.propostas.imoveis.recebidas2"/></a></li>                            	
                            <% }  %>
                        	                            
                            <li><a href="${urlImovelPropostas}/listarImoveisPropostas/propostasLancadas"><spring:message code="lbl.title.link.propostas.imoveis.enviadas2"/></a></li>                            
                        </ul>
                    </li>
                    <!--/ End navigation - Propostas -->
                    
                      <!-- Start navigation - Indicações -->
                    <li class="submenu">
                        <a href="javascript:void(0);">
                            <span class="icon"><i class="fa fa-arrows"></i></span>
                            <span class="text"><spring:message code="lbl.title.link.imoveis.indicacoes"/> </span>
                            <% if ( Long.parseLong(request.getSession().getAttribute("quantNovoImovelIndicado").toString()) > 0l ) {%>
                              	<span class="label label-danger pull-right rounded">&nbsp;&nbsp; Novo</span>
                            <% } else { %>
                            	<span class="arrow"></span>                            	
                            <% }  %>
                        </a>
                        <ul>
                        	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovoImovelIndicado").toString()) > 0l ) {%>
                        		<li><a href="${urlImovelIndicado}/listarImoveisIndicados/indicado"><spring:message code="lbl.title.link.imoveis.indicacoes.recebidas2"/><span class="label label-danger pull-right rounded">&nbsp;&nbsp; <%= request.getSession().getAttribute("quantNovoImovelIndicado") %></span></a></li>
                        	<% } else { %>
                        		<li><a href="${urlImovelIndicado}/listarImoveisIndicados/indicado"><spring:message code="lbl.title.link.imoveis.indicacoes.recebidas2"/></a></li>
                        	<% }  %>
                        	                            
                            <li><a href="${urlImovelIndicado}/listarImoveisIndicados/indicacoes"><spring:message code="lbl.title.link.imoveis.indicacoes.enviadas2"/></a></li>                            
                        </ul>
                    </li>
                    <!--/ End navigation - Indicações-->

					 <!-- Start navigation - Comentários -->
                    <li class="submenu">
                        <a href="javascript:void(0);"> 
                            <span class="icon"><i class="fa fa-commenting"></i></span>
                            <span class="text"><spring:message code="lbl.title.link.comentarios.imoveis"/></span>
                            <% if ( Long.parseLong(request.getSession().getAttribute("quantNovoComentario").toString()) > 0l ) {%>
                              	<span class="label label-danger pull-right rounded">&nbsp;&nbsp; Novo</span>
                            <% } else { %>
                            	<span class="arrow"></span>                            	
                            <% }  %> 
                        </a>
                        <ul>
                        	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovoComentario").toString()) > 0l ) {%>
                        		<li><a href="${urlImovelComentario}/listarMeusComentarios/comentariosSobreMeusImoveis"><spring:message code="lbl.title.link.comentarios.imoveis.recebidos2"/><span class="label label-danger pull-right rounded">&nbsp;&nbsp; <%= request.getSession().getAttribute("quantNovoComentario") %></span></a></li>
                        	<% } else { %>
                            	<li><a href="${urlImovelComentario}/listarMeusComentarios/comentariosSobreMeusImoveis"><spring:message code="lbl.title.link.comentarios.imoveis.recebidos2"/></a></li>                            	
                            <% }  %>
                        	                            
                        </ul>
                    </li>
                    <!--/ End navigation - Comentários-->  					
                </ul><!-- /.sidebar-menu -->  

            </aside><!-- /#sidebar-left -->