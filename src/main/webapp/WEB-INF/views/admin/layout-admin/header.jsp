<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<spring:url var="urlContato" value="/contato"/>
<spring:url var="urlNotificacao" value="/notificacao"/>
<spring:url var="urlMensagem" value="/mensagem"/>
<spring:url var="urlUsuario" value="/usuario"/>
<spring:url var="urlImovel" value="/imovel"/>
<spring:url value="/admin" var="urlAdmin"/>			
<spring:url var="urlMensagemAdmin" value="/mensagemAdmin"/>
<spring:url var="urlImovelComparativo" value="/imovelComparativo"/>

<header id="header">

                <!-- Start header left -->
                <div class="header-left">
                    <!-- Start offcanvas left: This menu will take position at the top of template header (mobile only). Make sure that only #header have the `position: relative`, or it may cause unwanted behavior -->
                    <div class="navbar-minimize-mobile left">
                        <i class="fa fa-bars"></i>
                    </div>
                    <!--/ End offcanvas left -->

                    <!-- Start navbar header -->
                    <div class="navbar-header">

                        <!-- Start brand -->
                        <a class="navbar-brand" href="dashboard.html">                            
                            <img src="${context}/images/logo_bul.png"  style="width: 175px; height: 50px; " alt="brand logo">
                                                        
                        </a><!-- /.navbar-brand -->
                        <!--/ End brand -->

                    </div><!-- /.navbar-header -->
                    <!--/ End navbar header -->

                    <!-- Start offcanvas right: This menu will take position at the top of template header (mobile only). Make sure that only #header have the `position: relative`, or it may cause unwanted behavior -->
                    <div class="navbar-minimize-mobile right">
                        <i class="fa fa-cog"></i>
                    </div>
                    <!--/ End offcanvas right -->

                    <div class="clearfix"></div>
                </div><!-- /.header-left -->
                <!--/ End header left -->

                <!-- Start header right -->
                <div class="header-right">
                    <!-- Start navbar toolbar -->
                    <div class="navbar navbar-toolbar">

                        <!-- Start left navigation -->
                        <ul class="nav navbar-nav navbar-left">

                            <!-- Start sidebar shrink -->
                            <li class="navbar-minimize">
                                <a href="javascript:void(0);" title="Minimize sidebar">
                                    <i class="fa fa-bars"></i>
                                </a>
                            </li>
                            <!--/ End sidebar shrink -->

                            <!-- Start form search -->
                            <li class="navbar-search">
                                <!-- Just view on mobile screen-->
                                <a href="#" class="trigger-search"><i class="fa fa-search"></i></a>
                                
                               	<form class="navbar-form" action="${urlAdmin}/pesquisarTudo" method="post">
                                    <div class="form-group has-feedback"> 
                                        <input id="valorBusca" type="text" name="valorBusca" class="form-control typeahead rounded" placeholder='<spring:message code="lbl.title.busque.pessoas.imoveis"/>'>                                        
                                        <button type="submit" class="btn btn-theme fa fa-search form-control-feedback rounded"   ></button>                                        
                                    </div>                                   
                                </form>
                 
                            </li>
                            <!--/ End form search -->

                        </ul><!-- /.nav navbar-nav navbar-left -->
                        <!--/ End left navigation -->

                        <!-- Start right navigation -->
                        <ul class="nav navbar-nav navbar-right"><!-- /.nav navbar-nav navbar-right -->                        
                                   
                        <!-- Start messages -->
                        <li class="dropdown navbar-message">
							<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasMensagensAdmin").toString()) > 0l ) {%>
								<a href="${urlMensagemAdmin}/listarMensagensAdmin" class="dropdown-toggle"><i class="fa fa-envelope-o"></i><span class="count label label-danger rounded"><%= request.getSession().getAttribute("quantNovasMensagensAdmin") %></span></a>
							<% } else { %>
								<a href="${urlMensagemAdmin}/listarMensagensAdmin" class="dropdown-toggle"><i class="fa fa-envelope-o"></i></a>							
							<% }  %>                            

                            <!-- Start dropdown menu -->
                            <div class="dropdown-menu animated flipInX">
                                <div class="dropdown-header">  
                                	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasMensagensAdmin").toString()) > 0l ) {%>
                                		<a href="${urlMensagemAdmin}/listarMensagensAdmin" class="media" >
	                                    	<span class="title"><spring:message code="lbl.title.link.mensagens"/> <strong>(<%= request.getSession().getAttribute("quantNovasMensagensAdmin") %>)</strong></span>
	                                    </a>
                                	<% } else { %>
		                            	<a href="${urlMensagemAdmin}/listarMensagensAdmin" class="media" >
	                                    	<span class="title"><spring:message code="lbl.title.link.mensagens"/> <strong></strong></span>
	                                    </a>
		                             <% }  %>
                                	
                                </div> 
                                <div class="dropdown-body">

                                </div>
                                <div class="dropdown-footer"> 
                                    <a href="page-messages.html"><spring:message code="lbl.title.see.all"/></a>
                                </div>
                            </div>
                            <!--/ End dropdown menu -->

                        </li><!-- /.dropdown navbar-message -->
                        <!--/ End messages -->

                        <!-- Start profile -->
                        <li class="dropdown navbar-profile">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <span class="meta">
                                    <span class="avatar"><img src="data:image/jpeg;base64,${usuario.imagemArquivo}" style="width: 35px; height: 35px; " class="img-circle" alt="admin"/></span>
                                    <span class="text hidden-xs hidden-sm text-muted">${usuario.nome}</span>
                                    <span class="caret"></span>
                                </span>
                            </a>
                            <!-- Start dropdown menu -->
                            <ul class="dropdown-menu animated flipInX">
                                <li class="dropdown-header"><spring:message code="lbl.title.conta"/></li>  
                                <li><a href="${urlUsuario}/meuPerfil"><i class="fa fa-user"></i><spring:message code="lbl.title.link.perfil"/></a></li>
                                <li><a href="${urlUsuario}/prepararEdicaoUsuario"><i class="fa fa-user"></i><spring:message code="lbl.title.aba.editar.perfil"/></a></li>
                                <li><a href="${urlUsuario}/prepararAlterarFotoUsuario"><i class="fa fa-user"></i><spring:message code="lbl.title.aba.alterar.foto.perfil"/></a></li>
                                <li><a href="mail-inbox.html"><i class="fa fa-envelope-square"></i><spring:message code="lbl.title.link.mensagens"/> <span class="label label-info pull-right">30</span></a></li>                                                                
                                <li class="divider"></li>
                                <li><a href="${urlUsuario}/logout"><i class="fa fa-sign-out"></i><spring:message code="lbl.title.logout"/></a></li>  
                            </ul>
                            <!--/ End dropdown menu -->
                        </li><!-- /.dropdown navbar-profile -->
                        <!--/ End profile -->

                        <!-- Start settings -->
                        <li class="navbar-setting pull-right">
                            <a href="javascript:void(0);"><i class="fa fa-cog fa-spin"></i></a>
                        </li><!-- /.navbar-setting pull-right -->
                        <!--/ End settings -->

                        </ul>
                        <!--/ End right navigation -->

                    </div><!-- /.navbar-toolbar -->
                    <!--/ End navbar toolbar -->
                </div><!-- /.header-right -->
                <!--/ End header left -->

            </header> <!-- /#header -->
            
            <!-- Start optional size modal element - comparativo de imoveis -->
            <div id="idModalConfirmarComparativo" class="modal fade bs-example-modal-lg-comparativo" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.comparativo"/></h4>
                        </div>
                        <div class="modal-body">
                            <p><div id="msgModalComparativo" cssClass="errorEntrada"  ></div>   </p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>                                                        
                        </div>						
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->