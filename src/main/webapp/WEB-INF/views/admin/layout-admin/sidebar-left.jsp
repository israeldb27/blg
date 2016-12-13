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
<spring:url var="urlAdmin" value="/admin"/>

<aside id="sidebar-left" class="sidebar-circle">

                <!-- Start left navigation - profile shortcut -->
                <div class="sidebar-content">
                    <div class="media">
                        <a class="pull-left has-notif avatar" href="page-profile.html">
                            <img src="${context}${usuario.imagemArquivo}" style="width: 50px; height: 50px; " alt="admin"/>
                            <i class="online"></i>                            
                        </a>
                        <div class="media-body">
                            <h4 class="media-heading"><span>${usuario.nome}</span></h4>                            
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
                            <li><a href="${urlAdmin}/prepararBuscaImoveis"><spring:message code="lbl.title.link.busca.imoveis"/> </a></li>                            
                            <li><a href="${urlAdmin}/prepararBuscaUsuarios"><spring:message code="lbl.title.link.busca.usuarios"/></a></li>
                            <li><a href="${urlAdmin}/prepararBuscaImoveisAnuncio"><spring:message code="lbl.title.link.busca.imoveis.anuncio"/></a></li>
							<li><a href="${urlAdmin}/prepararCadastroUsuarioAdmin"><spring:message code="lbl.title.cadastro.usuario.admin"/></a></li>
                            <li><a href="${urlAdmin}/prepararCadastroParamServico"><spring:message code="lbl.admin.link.parametros.servicos"/></a></li>
                            <li><a href="${urlAdmin}/prepararCadastroFormasPagto"><spring:message code="lbl.admin.link.formas.pagto"/></a></li>
                            <li><a href="${urlAdmin}/prepararCadastroPlanos"><spring:message code="lbl.admin.link.planos"/></a></li>
                            <li><a href="${urlAdmin}/listarParametrosIniciais"><spring:message code="lbl.admin.link.parametros.iniciais"/></a></li>                                                      
                        </ul>
                    </li>
                    <!--/ End navigation - Consultas -->					
					
					<!-- Start navigation - Relatórios -->
                    <li class="submenu">
                        <a href="javascript:void(0);">
                            <span class="icon"><i class="fa fa-search"></i></span>
                            <span class="text"><spring:message code="lbl.admin.link.relatorios"/></span>
                            <span class="arrow"></span>
                        </a>  
                        <ul> 
                            <li><a href="${urlAdmin}/prepararRelatorios/relatorioSistema"><spring:message code="lbl.admin.link.relatorios.sistema"/> </a></li>                            
                            <li><a href="${urlAdmin}/prepararRelatorios/relatoriosUsuarios"><spring:message code="lbl.admin.link.relatorios.usuarios"/></a></li>
                            <li><a href="${urlAdmin}/prepararRelatorios/relatoriosImoveis"><spring:message code="lbl.admin.link.relatorios.imoveis"/></a></li>
                            <li><a href="${urlAdmin}/prepararRelatorios/relatoriosPlanos"><spring:message code="lbl.admin.link.relatorios.planos"/></a></li>
                            <li><a href="${urlAdmin}/prepararRelatorios/relatoriosAssinatura"><spring:message code="lbl.admin.link.relatorios.assinaturas"/></a></li>
							<li><a href="${urlAdmin}/prepararRelatorios/relatoriosServicos"><spring:message code="lbl.admin.link.relatorios.servicos"/></a></li>
                        </ul>
                    </li>
                    <!--/ End navigation - Consultas -->	
					
                </ul><!-- /.sidebar-menu -->
                
                <!--/ End left navigation - menu -->


            </aside><!-- /#sidebar-left -->