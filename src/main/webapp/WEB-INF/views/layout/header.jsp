<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<spring:url var="urlMensagemAdmin" value="/mensagemAdmin"/>
<spring:url var="urlAjuda" value="/ajuda"/>
<spring:url var="urlRecomendacao" value="/recomendacao"/>
<spring:url var="urlImovelComparativo" value="/imovelComparativo"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	
	$('.my-tooltip').tooltip({
		  
	});
});


function pesquisarTudo(){ 

	var parametro1=document.getElementById("valorPesquisa");
	alert(parametro1.value);
	$.post("${urlUsuario}/pesquisarTudo/"+ parametro1.value , {}, function() {		
		 
    }); 
} 

function adicionarComparativo(id) {    		
	var parametro1 = id;
    $.ajax({        
		 url: '${urlImovelComparativo}/adicionarImovelComparativo/' + parametro1,
		 dataType: 'json',
		 success: function(data){				 
			 if ( data == 'ok') {
				 $('#msgModalComparativo').html("<spring:message code='lbl.msg.sucesso.add.comparativo'/>");
				 $("#idModalConfirmarComparativo").modal("show");	
       	 }
       	 else  {
       		$("#idModalConfirmarComparativo").modal("show"); 
       		$('#msgModalComparativo').html(data);
	         }     	    
		 },
		 error: function(jqXHR, textStatus, errorThrown) {				 
			 $('#msgModalComparativo').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		 }
	 });
}
</script>

<header id="header">

                <!-- Start header left -->
                <div class="header-left">
                    <!-- Start offcanvas left: This menu will take position a the top of template header (mobile only). Make sure that only #header have the `position: relative`, or it may cause unwanted behavior -->
                    <div class="navbar-minimize-mobile left">
                        <i class="fa fa-bars"></i>
                    </div>
                    <!--/ End offcanvas left -->

                    <!-- Start navbar header -->
                    <div class="navbar-header">

                        <!-- Start brand -->
                        <a class="navbar-brand" href="${urlUsuario}/inicio">                            
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
                                <a href="#" class="trigger-search"></a>
                                <form class="navbar-form" action="${urlUsuario}/pesquisarTudo" method="post">
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
 
                        <!-- Start convites -->
                        <li class="dropdown navbar-message">
							
							<% if ( Long.parseLong(request.getSession().getAttribute("quantNovosConviteRecebidos").toString()) > 0l ) {%>
						    	<spring:message code="lbl.contato.hint.header" var="mensagemContato"/>
                           		<a href="#" class="dropdown-toggle my-tooltip" data-toggle="dropdown" data-placement="bottom" data-original-title="${mensagemContato}"><i class="fa fa-user"></i><span class="count label label-danger rounded"><%= request.getSession().getAttribute("quantNovosConviteRecebidos") %></span></a>
                            		      
                            <% } else if ( Long.parseLong(request.getSession().getAttribute("quantConviteRecebidos").toString()) > 0l ) {%>
							    <spring:message code="lbl.contato.hint.header" var="mensagemContato"/>
                            	<a href="#" class="dropdown-toggle my-tooltip" data-toggle="dropdown" data-placement="bottom" data-original-title="${mensagemContato}"><i class="fa fa-user"></i></a>
                            			                            		     
                            <% } else { %>
                            	<spring:message code="lbl.contato.hint.header" var="mensagemContato"/>                            	
								<a href="${urlContato}/listarContatos" class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="bottom" data-original-title="${mensagemContato}"><i class="fa fa-user"></i></a>								
									
							<% }  %>
                            <!-- Start dropdown menu -->
                            <div class="dropdown-menu animated flipInX">
                                <div class="dropdown-header">  
                                	<% if ( Long.parseLong(request.getSession().getAttribute("quantConviteRecebidos").toString()) > 0l ) {%>
                                		<a href="${urlContato}/convites" class="media">
	                                    	<span class="title"><spring:message code="lbl.title.link.convites"/> </span>
	                                    </a>	                                    
	                                    <span class="option text-right"><a href="${urlContato}/listarContatos">+ <spring:message code="lbl.contatos"/></a></span>                                	
                                	<% } %>                                		
                                </div>
                                <div class="dropdown-body">           

                                    <!-- Start message list -->
                                    <div class="media-list niceScroll">
                                    	<c:if test="${!empty sessionScope.listaConviteRecebidos}">
                                    		<c:forEach var="usuario" items="${sessionScope.listaConviteRecebidos}">                                    		
	                                    		<a href="${urlContato}/visualizarConvite/${usuario.id}" class="media">
		                                            <div class="pull-left"><img src="${context}${usuario.imagemArquivo}" style="width: 50px; height: 50px; "  class="media-object img-circle" alt="..."/></div>
		                                            <div class="media-body">
		                                                <span class="media-heading">${usuario.nome}</span>
		                                                <span class="media-text">${usuario.perfilFmt}</span>		                                                		                                                
		                                                <span class="media-meta pull-right"><fmt:formatDate value="${usuario.dataConvite}" pattern='dd/MM/yyyy'/></span>		                                                
		                                            </div><!-- /.media-body -->
		                                        </a><!-- /.media -->
                                    		</c:forEach>		
                                    	</c:if>
                                    </div>
                                    <!--/ End message list -->

                                </div>
                                <div class="dropdown-footer">
                                    <a href="${urlContato}/convites"><spring:message code="lbl.title.see.all"/></a>
                                </div>
                            </div>
                            <!--/ End dropdown menu -->

                        </li><!-- /.dropdown navbar-message -->
                        <!--/ End convites -->
                        
                        
                          <!-- Start Recomendacoes -->
                        <li class="dropdown navbar-message">
							<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasRecomendacoes").toString()) > 0l ) {%>
								<spring:message code="lbl.recomendacao.hint.header" var="recomendacao"/>
								<a href="#" class="dropdown-toggle my-tooltip" data-toggle="dropdown" data-placement="bottom" data-original-title="${recomendacao}"><i class="fa fa-flag"></i><span class="count label label-danger rounded"><%= request.getSession().getAttribute("quantNovasRecomendacoes") %></span></a>
							<% } else { %>
								<spring:message code="lbl.recomendacao.hint.header" var="recomendacao"/>
								<a href="${urlRecomendacao}/listarRecomendacoesRecebidas" class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="bottom" data-original-title="${recomendacao}"><i class="fa fa-flag"></i></a>							
							<% }  %>                            


                            <!-- Start dropdown menu -->
                            <div class="dropdown-menu animated flipInX">
                                <div class="dropdown-header">
                               		<a href="${urlRecomendacao}/listarRecomendacoesRecebidas" class="media" >
                                    	<span class="title"><spring:message code="lbl.title.link.recomendacao"/> </span>
                                    </a>
                                </div> 
                                
                                <div class="dropdown-body">
                                  <!-- Start message list -->
                                    <div class="media-list niceScroll">
                                    	<c:if test="${!empty sessionScope.listaRecomendacoes}">
                                    		<c:forEach var="recomendacao" items="${sessionScope.listaRecomendacoes}">
                                    			<a href="${urlRecomendacao}/visualizarRecomendacaoSelecionado/${recomendacao.id}" class="media">
                                    			
		                                            <div class="pull-left"><img src="${context}${recomendacao.imagemUsuario}" style="width: 50px; height: 50px; "  class="media-object img-circle" alt="..."/></div>
		                                            <div class="media-body">
		                                                <span class="media-heading">${recomendacao.usuario.nome}  </span>
		                                                <span class="media-text">${recomendacao.descricao}</span>		                                                		                                                
		                                                <span class="media-meta pull-right"><fmt:formatDate value="${recomendacao.dataRecomendacao}" pattern='dd/MM/yyyy'/></span>		                                                
		                                            </div><!-- /.media-body -->
		                                        </a><!-- /.media -->
                                    		</c:forEach>		
                                    	</c:if>                                            
                                    </div>
                                    <!--/ End message list -->
                                </div>
                                <div class="dropdown-footer"> 
                                    <a href="${urlRecomendacao}/listarRecomendacoesRecebidas"><spring:message code="lbl.title.see.all"/></a>
                                </div>
                            </div>
                            <!--/ End dropdown menu -->

                        </li><!-- /.dropdown navbar-message -->
                        <!--/ End Recomendacoes -->
                                                                                               
                        <!-- Start messages -->
                        <li class="dropdown navbar-message">
							<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasMensagens").toString()) > 0l ) {%>
								<spring:message code="lbl.mensagens.hint.header" var="mensagem"/>
								<a href="#" class="dropdown-toggle my-tooltip" data-toggle="dropdown" data-placement="bottom" data-original-title="${mensagem}"><i class="fa fa-envelope-o"></i><span class="count label label-danger rounded"><%= request.getSession().getAttribute("quantNovasMensagens") %></span></a>
							<% } else { %>
								<spring:message code="lbl.mensagens.hint.header" var="mensagem"/>
								<a href="${urlMensagem}/minhasMensagens" class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="bottom" data-original-title="${mensagem}"><i class="fa fa-envelope-o"></i></a>							
							<% }  %>                            

                            <!-- Start dropdown menu -->
                            <div class="dropdown-menu animated flipInX">
                                <div class="dropdown-header">  
                                	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasMensagens").toString()) > 0l ) {%>
                                		<a href="${urlMensagem}/minhasNovasMensagens" class="media" >
	                                    	<span class="title"><spring:message code="lbl.title.link.novas.mensagens"/> <strong>(<%= request.getSession().getAttribute("quantNovasMensagens") %>)</strong></span>
	                                    </a>
                                	<% } %>	
                                </div> 
                                
                                <div class="dropdown-body">
                                  <!-- Start message list -->
                                    <div class="media-list niceScroll">
                                    	<c:if test="${!empty sessionScope.listaMensagens}">
                                    		<c:forEach var="mensagem" items="${sessionScope.listaMensagens}">
                                    			<c:choose>
                                    				<c:when test="${mensagem.usuarioDe.id == usuario.id}">
                                    					<a href="${urlMensagem}/maisMensagens/${mensagem.usuarioPara.id}" class="media">
                                    						<div class="pull-left"><img src="${context}/${mensagem.usuarioPara.imagemArquivo}" style="width: 50px; height: 50px; "  class="media-object img-circle" alt="..."/></div>
                                    						<div class="media-body">
				                                                <span class="media-heading"> ${mensagem.usuarioPara.nome} </span>
				                                                <span class="media-text">${mensagem.descricao}</span>		                                                		                                                
				                                                <span class="media-meta pull-right"><fmt:formatDate value="${mensagem.dataMensagem}" pattern='dd/MM/yyyy'/></span>		                                                
				                                            </div><!-- /.media-body -->                                    							
                                    					</a>
                                    				</c:when>
                                    				
                                    				<c:when test="${mensagem.usuarioPara.id == usuario.id}">
                                    					<a href="${urlMensagem}/maisMensagens/${mensagem.usuarioDe.id}" class="media">
                                    						<div class="pull-left"><img src="${context}/${mensagem.usuarioDe.imagemArquivo}" style="width: 50px; height: 50px; "  class="media-object img-circle" alt="..."/></div>
                                    						<div class="media-body">
				                                                <span class="media-heading"> ${mensagem.usuarioDe.nome} </span>
				                                                <span class="media-text">${mensagem.descricao}</span>		                                                		                                                
				                                                <span class="media-meta pull-right"><fmt:formatDate value="${mensagem.dataMensagem}" pattern='dd/MM/yyyy'/></span>		                                                
				                                            </div><!-- /.media-body -->                                    							
                                    					</a>
                                    				</c:when>                                    			
                                    			</c:choose>                                    	
                                    		</c:forEach>		
                                    	</c:if>                                            
                                    </div>
                                    <!--/ End message list -->
                                </div>
                                <div class="dropdown-footer"> 
                                    <a href="${urlMensagem}/minhasMensagens"><spring:message code="lbl.title.see.all"/></a>
                                </div>
                            </div>
                            <!--/ End dropdown menu -->

                        </li><!-- /.dropdown navbar-message -->
                        <!--/ End messages -->

                        <!-- Start notifications -->
                        <li class="dropdown navbar-notification">
							<% if ( Long.parseLong(session.getAttribute("quantNovasNotificacoes").toString()) > 0l ) {%>
								<spring:message code="lbl.notificacao.hint.header" var="mensagemNotificacao"/>
								<a href="#" class="dropdown-toggle my-tooltip" data-toggle="dropdown" data-placement="bottom" data-original-title="${mensagemNotificacao}"><i class="fa fa-bell-o"></i><span class="count label label-danger rounded"><%= session.getAttribute("quantNovasNotificacoes") %></span></a>
							<% } else { %>
								<spring:message code="lbl.notificacao.hint.header" var="mensagemNotificacao"/>
								<a href="${urlNotificacao}/listarMinhasNotificacoes" class="dropdown-toggle my-tooltip" data-placement="bottom" data-original-title="${mensagemNotificacao}" ><i class="fa fa-bell-o"></i></a>							
							<% }  %>

                            <!-- Start dropdown menu -->
                            <div class="dropdown-menu animated flipInX">
                                <div class="dropdown-header">  
                                	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasNotificacoes").toString()) > 0l ) {%>
                                		<a href="${urlNotificacao}/listarMinhasNotificacoes" class="media">
	                                    	<span class="title"><spring:message code="lbl.title.link.notificacoes"/> <strong>(<%= request.getSession().getAttribute("quantNovasNotificacoes") %>)</strong></span>
	                                    </a>
                                	<% }  %>                           
                                </div>
                                <div class="dropdown-body niceScroll">
                                    <!-- Start notification list -->
                                    <div class="media-list small">
									<!-- aparecerÃ¡ apenas novas NotificaÃ§Ãµes no pop-up-->
										<c:if test="${!empty sessionScope.listaNovasNotificacoes}">
                                    		<c:forEach var="notificacao" items="${sessionScope.listaNovasNotificacoes}">
												<c:choose>
													<c:when test="${notificacao.tipoNotificacao == 'C' }"> <!--notificacao de convite aceito, disponibilizar link para ver detalhes do usuÃ¡rio -->
														<a href="${urlNotificacao}/selecionarNotificacao/${notificacao.id}" class="media">
															<div class="pull-left"><img src="${context}/${notificacao.usuarioConvite.imagemArquivo}" style="width: 50px; height: 50px; "  class="media-object img-circle" alt="..."/></div>
															<div class="media-body">
																<span class="media-heading"> ${notificacao.descricao}</span>
																<span class="media-text"></span>		                                                		                                                
																<span class="media-meta pull-right"><spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></span>		                                                
															</div><!-- /.media-body -->
														</a><!-- /.media -->
													</c:when>
													
													<c:when test="${notificacao.tipoNotificacao == 'I' }">
														<a href="${urlNotificacao}/selecionarNotificacao/${notificacao.id}" class="media">
															<div class="pull-left"><img src="${context}/${notificacao.imagemUsuario}" style="width: 50px; height: 50px; "  class="media-object img-circle" alt="..."/></div>
															<div class="media-body">
																<span class="media-heading"> ${notificacao.descricao}</span>
																<span class="media-text"></span>		                                                		                                                
																<span class="media-meta pull-right"><spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></span>		                                                
															</div><!-- /.media-body -->
														</a><!-- /.media -->												
													</c:when>
													
													<c:when test="${notificacao.tipoNotificacao == 'U' }">
														<a href="${urlNotificacao}/selecionarNotificacao/${notificacao.id}" class="media">
															<div class="pull-left"><img src="${context}/${notificacao.imagemUsuario}" style="width: 50px; height: 50px; "  class="media-object img-circle" alt="..."/></div>
															<div class="media-body">
																<span class="media-heading"> ${notificacao.descricao}</span>
																<span class="media-text"></span>		                                                		                                                
																<span class="media-meta pull-right"><spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></span>		                                                
															</div><!-- /.media-body -->
														</a><!-- /.media -->												
													</c:when>
													
													<c:when test="${notificacao.tipoNotificacao == 'S' }">
														<a href="${urlNotificacao}/selecionarNotificacao/${notificacao.id}" class="media">
															<div class="pull-left"><i class="fa fa-building-o fa-5x"></i></div>
															<div class="media-body">
																<span class="media-heading"> ${notificacao.descricao}</span>
																<span class="media-text"></span>		                                                		                                                
																<span class="media-meta pull-right"><spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></span>		                                                
															</div><!-- /.media-body -->
														</a><!-- /.media -->												
													</c:when>												
													
													<c:when test="${notificacao.tipoNotificacao == 'S' }">
														<a href="${urlNotificacao}/selecionarNotificacao/${notificacao.id}" class="media">
															<div class="pull-left"><i class="fa fa-building-o fa-5x"></i></div>
															<div class="media-body">
																<span class="media-heading"> ${notificacao.descricao}</span>
																<span class="media-text"></span>		                                                		                                                
																<span class="media-meta pull-right"><spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></span>		                                                
															</div><!-- /.media-body -->
														</a><!-- /.media -->												
													</c:when>
													
													<c:when test="${notificacao.tipoNotificacao == 'P' }">
														<a href="${urlNotificacao}/selecionarNotificacao/${notificacao.id}" class="media">
															<div class="pull-left"><i class="fa fa-university fa-5x"></i></i></div>
															<div class="media-body">
																<span class="media-heading"> ${notificacao.descricao}</span>
																<span class="media-text"></span>		                                                		                                                
																<span class="media-meta pull-right"><spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></span>		                                                
															</div><!-- /.media-body -->
														</a><!-- /.media -->												
													</c:when>													
												</c:choose>											
                                    		</c:forEach>		
                                    	</c:if>
	                                  
                                    </div>
                                    <!--/ End notification list -->
                                </div>
                                <div class="dropdown-footer">
                                    <a href="${urlNotificacao}/listarMinhasNotificacoes"><spring:message code="lbl.title.see.all"/></a>
                                </div>
                            </div>
                            <!--/ End dropdown menu -->

                        </li><!-- /.dropdown navbar-notification -->
                        <!--/ End notifications -->                        
                        
                         <!-- Start messages -->
                        <li class="dropdown navbar-message">
							<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasMensagensAdmin").toString()) > 0l ) {%>
								<spring:message code="lbl.mensagens.admin.hint.header" var="mensagemAdmin"/>
								<a href="#" class="dropdown-toggle my-tooltip" data-toggle="dropdown" data-placement="bottom" data-original-title="${mensagemAdmin}"><i class="fa fa-envelope-square"></i><span class="count label label-danger rounded"><%= request.getSession().getAttribute("quantNovasMensagensAdmin") %></span></a>
							<% } else { %>
								<spring:message code="lbl.mensagens.admin.hint.header" var="mensagemAdmin"/>
								<a href="#" class="dropdown-toggle my-tooltip" data-toggle="dropdown" data-placement="bottom" data-original-title="${mensagemAdmin}"><i class="fa fa-envelope-square"></i><span class="count label label-danger rounded"></span></a>															
							<% }  %>                            

                            <!-- Start dropdown menu -->
                            <div class="dropdown-menu animated flipInX">
                                <div class="dropdown-header">  
                                	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasMensagensAdmin").toString()) > 0l ) {%>
                                		<a href="${urlMensagemAdmin}/minhasMensagensAdmin" class="media" >
	                                    	<span class="title"><spring:message code="lbl.title.link.mensagens.admin"/> <strong>(<%= request.getSession().getAttribute("quantNovasMensagensAdmin") %>)</strong></span>
	                                    </a>
                                	<% }  %>	                            	
                                	
                                    <span class="option text-right"><a href="${urlMensagemAdmin}/prepararNovaMensagem">+ <spring:message code="lbl.nova.mensagem"/></a></span>
                                </div> 
                                
                                <% if ( Long.parseLong(request.getSession().getAttribute("quantNovasMensagensAdmin").toString()) > 0l ) {%>
	                                <div class="dropdown-body">                           
	
	                                    <!-- Start message list -->
	                                    <div class="media-list niceScroll">										
											<c:forEach var="mensagem" items="${sessionScope.listaNovasMensagensAdmin}">
												<a href="${urlMensagemAdmin}/prepararMaisMensagensAdmin/${mensagem.id}" class="media">
													<div class="pull-left"></div>
													<div class="media-body">
														<span class="media-heading">${mensagem.tipoMensagemFmt}</span>
														<span class="media-text">${mensagem.descricaoUltimaMensagem}</span>
														<!-- Start meta icon -->										
														<span class="media-meta pull-right"><fmt:formatDate value="${mensagem.dataUltimaMensagem}" pattern='dd/MM/yyyy'/></span>
														<!--/ End meta icon -->
													</div><!-- /.media-body -->
												</a><!-- /.media -->
											</c:forEach>	
	                                    </div>
	                                    <!--/ End message list -->
	                                </div>
	                                <div class="dropdown-footer"> 
	                                    <a href="${urlMensagemAdmin}/minhasMensagensAdmin"><spring:message code="lbl.title.see.all"/></a>
	                                </div>
                                <% }  else { %>
                                	
                                	
                                	<div class="dropdown-body niceScroll">
                                    <!-- Start notification list -->
                                    
                                    <br>
                                    <div class="media-list small">	
		                                   <div class="callout callout-info">
			                                    <strong>Nenhuma mensagem Recebida</strong>                                    
			                                </div>
	                                 </div>
	                                    
	
	                                </div>
                                <% }  %>
                            </div>
                            <!--/ End dropdown menu -->

                        </li><!-- /.dropdown navbar-message -->
                        <!--/ End messages -->                        

                        <!-- Start profile -->
                        <li class="dropdown navbar-profile">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <span class="meta">                                    
                                    <span class="avatar"><img src="${context}${usuario.imagemArquivo}" style="width: 35px; height: 35px; " class="img-circle" alt="admin"/></span>
                                    <span class="text hidden-xs hidden-sm text-muted">${usuario.nome}</span>                                    
                                </span>
                            </a>
                            <!-- Start dropdown menu -->
                            <ul class="dropdown-menu animated flipInX">
                                <li class="dropdown-header"><spring:message code="lbl.title.conta"/></li>  
                                <li><a href="${urlUsuario}/detalhesUsuario/${usuario.id}"><i class="fa fa-user"></i><spring:message code="lbl.title.link.perfil"/></a></li>
                                <li><a href="${urlUsuario}/prepararEdicaoUsuario"><i class="fa fa-user"></i><spring:message code="lbl.title.aba.editar.perfil"/></a></li>
                                <li><a href="${urlUsuario}/prepararEditarSenha"><i class="fa fa-user"></i><spring:message code="lbl.title.aba.editar.senha"/></a></li>
                                <li><a href="${urlUsuario}/prepararAlterarFotoUsuario"><i class="fa fa-user"></i><spring:message code="lbl.title.aba.alterar.foto.perfil"/></a></li>
                                <li><a href="${urlUsuario}/prepararIndicarAmigo"><i class="fa fa-user"></i><spring:message code="lbl.title.aba.indicar.amigo"/></a></li>                                                                                                
                                <li class="divider"></li>
                                <li><a href="${urlUsuario}/logout"><i class="fa fa-sign-out"></i><spring:message code="lbl.title.logout"/></a></li>  
                            </ul>
                            <!--/ End dropdown menu -->
                        </li><!-- /.dropdown navbar-profile -->
                        <!--/ End profile -->
                        
                        <!-- Start menu Ajuda -->
                          <li class="dropdown navbar-profile">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <span class="meta">
                                    <span class="avatar"><spring:message code="lbl.title.ajuda"/></span>                                
                                </span>
                            </a>
                            <!-- Start dropdown menu -->
                            <ul class="dropdown-menu animated flipInX">
                                <li class="dropdown-header"><spring:message code="lbl.title.ajuda"/></li>
                                <li><a href="${urlAjuda}/conhecendoFuncionalidades"><i class="fa fa-question"></i><spring:message code="lbl.title.link.ajuda.conhecendo.funcionalidades"/></a></li>
                                <li><a href="${urlAjuda}/faq"><i class="fa fa-question"></i><spring:message code="lbl.title.link.ajuda.faq"/></a></li>
                                <!-- <li><a href="${urlAjuda}/sobre"><i class="fa fa-question"></i><spring:message code="lbl.title.link.ajuda.sobre"/></a></li>  -->
                            </ul>
                            <!--/ End dropdown menu -->
                        </li><!-- /.dropdown navbar-profile -->

						<!-- End menu Ajuda -->
						
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