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

<spring:url var="urlParceria" value="/parceria"/>
<spring:url var="urlIntermediacao" value="/intermediacao"/>

<spring:url var="urlImovel" value="/imovel"/>
<spring:url var="urlImovelIndicado" value="/imovelIndicado"/>

<spring:url var="urlGmap" value="/gmap"/>
<spring:url var="urlAjuda" value="/ajuda"/>



<aside id="sidebar-left" class="sidebar-circle" >

                <!-- Start left navigation - profile shortcut -->
                <div class="sidebar-content">
                    <div class="media">
                      
                        <div class="media-body">
                            <h4 class="media-heading" style="">
								<span>
									 
								</span>
							</h4>    
							<small></small>
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
                    
                    
                    <!-- Start navigation - Cadastrar Usuario -->
                    <li class="submenu">
                        <a href="${urlUsuario}/prepararCadastroUsuario">
                            <span class="icon"><i class="fa fa-user"></i></span>
                            <span class="text"><spring:message code="msg.texto.13.apresentacao.tela.login"/></span>
                           
                        </a>
                    </li>                    
                    
                    <!-- End navigation - Cadastrar Usuario -->

                    <!-- Start navigation - Sobre -->
                     <li class="submenu">
                        <a href="${urlUsuario}/prepararCadastroUsuario">
                            <span class="icon"><i class="fa fa-search"></i></span>
                            <span class="text">Sobre</span>
                           
                        </a>
                    </li> 
                    <!--/ End navigation - Sobre-->
                    
                    <!-- Start navigation - Funcionalidades -->
                     <li class="submenu">
                        <a href="${urlAjuda}/conhecendoFuncionalidadesOffline">
                            <span class="icon"><i class="fa fa-question"></i></span>
                            <span class="text"><spring:message code="lbl.title.link.ajuda.conhecendo.funcionalidades"/></span>                           
                        </a>
                    </li> 
                    <!--/ End navigation - Funcionalidades-->
                    
                    <!-- Start navigation - Faq -->
                     <li class="submenu">
                        <a href="${urlAjuda}/faqOffline">
                            <span class="icon"><i class="fa fa-question"></i></span>
                            <span class="text"><spring:message code="lbl.title.link.ajuda.faq"/></span>                           
                        </a>
                    </li> 
                    <!--/ End navigation - Faq-->
                    
                    				
                </ul><!-- /.sidebar-menu -->  

            </aside><!-- /#sidebar-left -->