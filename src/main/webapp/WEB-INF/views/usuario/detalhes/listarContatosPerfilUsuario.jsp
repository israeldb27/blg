<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/imovel/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/imovel/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel/buscarImovelMapa" var="urlBuscarImoveisMapa"/>
<spring:url var="urlImovelFavoritos" value="/imovelFavoritos"/>
<spring:url value="/contato" var="urlContato"/>
<spring:url value="/usuario" var="urlUsuario"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>
<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioEnum"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   
<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>
<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioEnum.values() %>"/>

  
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

    	<script type="text/javascript">	
			
    	$(document).ready(function() {
    		(function ($) {
    			  $('.spinner .btn:first-of-type').on('click', function() {
    			    $('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);
    			  });
    			  $('.spinner .btn:last-of-type').on('click', function() {
    			    $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);
    			  });
    			})(jQuery);  
    	});	   
    	
    	function enviarConvite(id) {	
    		var parametro1 = id;
    	    $.ajax({        
    	        url: '${urlContato}/enviarConvite/' + parametro1,        
    	        success: function(){
    	        	$("#idConvite_"+id).hide();    	
    	        	$("#idCancelarConvite_"+id).show(); 
    	        	$("#idIniciarSeguidor_"+id).hide();
    	        	$("#idCancelarSeguidor_"+id).hide();
    	        },
    	        error: function(jqXHR, textStatus, errorThrown) {
    	            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
    	        }
    	    });	    
    	  }
    	  
    	function prepararModalCancelarConvite(id){
    		$("#modIdConvite").val(id);
    		$("#msgRetornoConfirmCancelConviteErro").html("");	
    		$("#idModalConfirmacaoCancelConvite").modal("show");	
    	}  
    	  
    	function cancelarConvite(){	
    		var id = document.getElementById("modIdConvite");	
    	    $.ajax({        
    	        url: '${urlContato}/cancelarConvite/' + id.value,        
    	        success: function(){
    	        	$("#idConvite_"+id.value).show();    	
    	        	$("#idCancelarConvite_"+id.value).hide(); 
    	        	$("#idIniciarSeguidor_"+id.value).show();
    	        	$("#idCancelarSeguidor_"+id.value).hide();
    	        	$("#idModalConfirmacaoCancelConvite").modal("hide");
    	        },
    	        error: function(jqXHR, textStatus, errorThrown) {
    	            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
    	        }
    	    });
    	}

    	function prepararModalCancelarContato(id){	
    		$("#modIdContato").val(id);
    		$("#msgRetornoConfirmCancelContatoErro").html("");	
    		$("#idModalConfirmacaoCancelContato").modal("show");	
    	}

    	function cancelarContato() {	
    		var id = document.getElementById("modIdContato");	
    	    $.ajax({                
    	        url: '${urlContato}/cancelarContato/' + id.value,                
    	        success: function(){
    	        	$("#idCancelarContato_"+id.value).hide();
    	        	$("#idConvite_"+id.value).show();
    	        	$("#idIniciarSeguidor_"+id.value).show();
    	        	$("#idCancelarSeguidor_"+id.value).hide();  
    	        	$("#idModalConfirmacaoCancelContato").modal("hide");
    	        },
    	        error: function(jqXHR, textStatus, errorThrown) {
    	            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
    	        }
    	    });
    	}

    	function iniciarSeguirUsuario(id) {
    		var parametro1 = id;	
    	    $.ajax({                
    	        url: '${urlUsuario}/iniciarSeguirUsuario/' + parametro1,                
    	        success: function(){
    	        	$("#idIniciarSeguidor_"+id).hide();        	
    	        	$("#idCancelarSeguidor_"+id).show();
    	        	$("#idConvite_"+id).hide();    	
    	        	$("#idConvidado_"+id).hide();
    	        },
    	        error: function(jqXHR, textStatus, errorThrown) {
    	            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
    	        }
    	    });
    	}

    	function cancelarSeguirUsuario(id) {	
    		var parametro1 = id;
    	    $.ajax({                
    	        url: '${urlUsuario}/cancelarSeguirUsuario/' + parametro1,                
    	        success: function(){
    	        	$("#idCancelarSeguidor_"+id).hide();
    	        	$("#idIniciarSeguidor_"+id).show(); 
    	        	$("#idConvite_"+id).show();
    	        },
    	        error: function(jqXHR, textStatus, errorThrown) {
    	            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
    	        }
    	    });
    	}
	
		</script>

        <c:import url="../../layout/head-layout.jsp"></c:import>

    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../../layout/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../../layout/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.contatos"/> </h2>
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                        <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
                        	<c:import url="../../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>		
                        
                         <div class="col-lg-13 col-md-13 col-sm-13">
							<div class="panel rounded shadow">
								<div class="panel-heading" align="center">
    								<span class="label pull-left"  style="font-size: 18px;margin-bottom:10px;background-color: black;font-size: 100%;">${usuarioForm.perfilFmt}</span>                                    
    								<span class="label pull-right" style="margin-left: 14px;color:gray; font-size: 12px;">Membro desde <fmt:formatDate value='${usuarioForm.dataCadastro}' pattern='dd/MM/yyyy'/></span>
									<h2 class="text-bold" style="margin-top: 0px;margin-bottom: 5px;width: 50%;"> <a href="${urlUsuario}/detalhesUsuario/${usuarioForm.id}" >${usuarioForm.nome}  </a> </h2>
									<h5 style="margin-top: 4px;margin-bottom: 0px;width: 50%;">${usuarioForm.cidade} - ${usuarioForm.estado}</h5>
									
                                    <div class="pull-right">
                                    	<c:if test="${(usuarioForm.id != usuario.id)}">
                                    		<a href="${urlMensagem}/maisMensagens/${usuarioForm.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.enviar.mensagem"/>' ><i class="fa fa-envelope-o pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"><spring:message code="lbl.enviar.mensagem"/> </font>&nbsp;&nbsp; </i> </a>
                                    	</c:if>                                    		
                                    	
                                    	<c:choose>
	                                       <c:when test="${((usuarioForm.id != usuario.id) && (usuarioForm.possuiContatoUsuarioSessao == 'N'))}" >
												<c:choose>
													<c:when test="${usuarioForm.isSeguindoUsuario == 'N'}">
														<spring:message code="lbl.enviar.convite" var="mensagemEnviarConvite"/>
	                                     					<a href="#a" id="idConvite_${usuarioForm.id}"  onClick="enviarConvite(${usuarioForm.id})" style="font-size:x-large; color: rgb(99, 110, 123);"  class="dropdown-toggle"  ><i class="fa fa-user-plus"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> ${mensagemEnviarConvite} </font> </a>				                                        					
	                                     					<a href="#a" id="idCancelarConvite_${usuarioForm.id}" onclick="prepararModalCancelarConvite(${usuarioForm.id})" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.canceler.enviar.convite"/> </font> &nbsp;&nbsp; </i> </a>
	                                     		
	                                     					<a href="#a" onClick="iniciarSeguirUsuario(${usuarioForm.id})" id="idIniciarSeguidor_${usuarioForm.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.iniciar.seguir.usuario"/>'> <i class="fa fa-list-ul pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.link.iniciar.seguir.usuario"/> </font> &nbsp; &nbsp; </i> </a> 
	                                   						<a href="#a" onClick="cancelarSeguirUsuario(${usuarioForm.id})" id="idCancelarSeguidor_${usuarioForm.id}" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.cancelar.seguir.usuario"/>'> <i class="fa fa-outdent pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.link.cancelar.seguir.usuario"/> </font> &nbsp; &nbsp; </i></a>		                                     		
													</c:when>
													
													<c:when test="${usuarioForm.isSeguindoUsuario == 'S'}">																																						
														<spring:message code="lbl.enviar.convite" var="mensagemEnviarConvite"/>
														<a href="#a" id="idConvite_${usuarioForm.id}"  onClick="enviarConvite(${usuarioForm.id})" style="font-size:x-large; color: rgb(99, 110, 123); display: none;"  class="dropdown-toggle"  ><i class="fa fa-user-plus"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> ${mensagemEnviarConvite} </font> </a>
														<a href="#a" id="idCancelarConvite_${usuarioForm.id}" onclick="prepararModalCancelarConvite(${usuarioForm.id})" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.canceler.enviar.convite"/> </font> &nbsp;&nbsp; </i> </a>
														
														<a href="#a" onClick="cancelarSeguirUsuario(${usuarioForm.id})" id="idCancelarSeguidor_${usuarioForm.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.cancelar.seguir.usuario"/>'> <i class="fa fa-outdent pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.link.cancelar.seguir.usuario"/> </font> &nbsp; &nbsp;</i> </a>   	                                    			
														<a href="#a" onClick="iniciarSeguirUsuario(${usuarioForm.id})" id="idIniciarSeguidor_${usuarioForm.id}" style="font-size:x-large;display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.iniciar.seguir.usuario"/>'> <i class="fa fa-list-ul pull-right" style="color:gray">  <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.link.iniciar.seguir.usuario"/> </font> &nbsp; &nbsp;  </i></a>														
													</c:when>
												</c:choose>														
	                                        </c:when>
	                                                		
	                                       <c:when test="${((usuarioForm.id != usuario.id) && (usuarioForm.possuiContatoUsuarioSessao == 'C'))}" >	                                                      			
												<a href="#a" onClick="iniciarSeguirUsuario(${usuarioForm.id})" id="idIniciarSeguidor_${usuarioForm.id}" style="font-size:x-large; color: rgb(99, 110, 123); display: none;" class="meta-action" title='<spring:message code="lbl.link.iniciar.seguir.usuario"/>'> <i class="fa fa-list-ul pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.link.iniciar.seguir.usuario"/> </font> &nbsp; &nbsp; </i> </a> 
		                                   		<a href="#a" onClick="cancelarSeguirUsuario(${usuarioForm.id})" id="idCancelarSeguidor_${usuarioForm.id}" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.cancelar.seguir.usuario"/>'> <i class="fa fa-outdent pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.link.cancelar.seguir.usuario"/> </font> &nbsp; &nbsp; </i></a>
												
												<a href="#a" id="idCancelarConvite_${usuarioForm.id}" onclick="prepararModalCancelarConvite(${usuarioForm.id})" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.canceler.enviar.convite"/> </font> &nbsp;&nbsp; </i> </a>
												<a href="#a" id="idConvite_${usuarioForm.id}" onclick="enviarConvite(${usuarioForm.id})" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.enviar.convite"/>' ><i class="fa fa-user-plus pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.enviar.convite"/> </font> &nbsp;&nbsp; </i></a>																							
	                                       </c:when>
	                                                		
	                                       <c:when test="${((usuarioForm.id != usuario.id) && (usuarioForm.possuiContatoUsuarioSessao == 'O'))}" >
	
                                               	<a href="#a" id="idCancelarContato_${usuarioForm.id}" onclick="prepararModalCancelarContato(${usuarioForm.id})" style="font-size:x-large; color: rgb(99, 110, 123); " class="meta-action" title='<spring:message code="lbl.canceler.contato"/>' ><i class="fa fa-user-times pull-right" style="color:gray"><font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.canceler.contato"/> </font> &nbsp; &nbsp; </i> </a>
                                               			
												<a href="#a" onClick="iniciarSeguirUsuario(${usuarioForm.id})" id="idIniciarSeguidor_${usuarioForm.id}" style="font-size:x-large; color: rgb(99, 110, 123); display: none;" class="meta-action" title='<spring:message code="lbl.link.iniciar.seguir.usuario"/>'> <i class="fa fa-list-ul pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.link.iniciar.seguir.usuario"/> </font> &nbsp; &nbsp; </i> </a> 
                                  					<a href="#a" onClick="cancelarSeguirUsuario(${usuarioForm.id})" id="idCancelarSeguidor_${usuarioForm.id}" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.cancelar.seguir.usuario"/>'> <i class="fa fa-outdent pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.link.cancelar.seguir.usuario"/> </font> &nbsp; &nbsp; </i></a>
                                  		
		                                   		<a href="#a" id="idCancelarConvite_${usuarioForm.id}" onclick="prepararModalCancelarConvite(${usuarioForm.id})" style="font-size:x-large; color: rgb(99, 110, 123); display: none;" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.canceler.enviar.convite"/> </font> &nbsp;&nbsp; </i> </a>
												<a href="#a" id="idConvite_${usuarioForm.id}" onclick="enviarConvite(${usuarioForm.id})" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.enviar.convite"/>' ><i class="fa fa-user-plus pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.enviar.convite"/> </font> &nbsp;&nbsp; </i></a>
	                                       </c:when>	                                                
	                                       
	                                       <c:when test="${(usuarioForm.id == usuario.id)}" >
	                                       		<a href="${urlImovel}/listarMeusImoveis" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.title.link.meus.imoveis"/>'> <i class="fa fa-home pull-right" style="color:gray"><font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.title.link.meus.imoveis"/> </font> &nbsp; &nbsp;</i> </a> 
												
												<c:if test="${usuario.perfil == 'P'}">
													<a href="${urlPreferencia}/listarPreferencias" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.title.link.pref.imoveis"/>'><i class="fa fa-bookmark pull-right" style="color:gray"><font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.title.link.pref.imoveis"/>  </font> &nbsp; &nbsp; </i> </a>  																	
											     </c:if>
												<a href="${urlNota}/minhasNotas" style="font-size:x-large;" class="meta-action" title='<spring:message code="lbl.title.link.minhas.notas"/>'><i class="fa fa-sticky-note pull-right" style="color:gray"><font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.title.link.minhas.notas"/> </font> &nbsp; &nbsp; </i> </a>	                                       
	                                       </c:when>
	                                    </c:choose>                                    	
                                    	
                                    </div>
                                    
                                    <br> <br>                                    

								</div>
								<div class="panel-body">
									<div class="pull-left">
										<div class="col-lg-6 col-md-6 col-sm-6"> 
	                                        <div id="owl-demo" class="owl-carousel owl-theme">    
	                                        	<a href="${urlUsuario}/detalhesUsuario/${usuarioForm.id}" >  
	                                        		<img class="img-circle" src="data:image/jpeg;base64,${usuarioForm.imagemArquivo}" style="margin-left:350px; width: 240px; height: 240px; ">
	                                        	</a>                                            	                                            	                                                                                        
	                                        </div>
	                                    </div>
                                    </div>
                                	
                                	<div class="pull-right">
                                		 <table class="table table-striped" style="margin-top:10px; font-size: 13px; width: 400px;">
                                         <tbody>
                                         
                                          <c:if test="${((usuarioForm.perfil == 'P') || (usuarioForm.perfil == 'C')) }">
                                          	 <tr>
                                                 <td class="text-left"><spring:message code="lbl.cpf"/></td>
                                                 <td class="text-right">${usuario.cpf}</td>
                                              </tr>                                                     
                                     	 </c:if>
                                        	 
		                                 <c:if test="${usuarioForm.perfil == 'I'}">
		                                 	  <tr>
                                                  <td class="text-left"><spring:message code="lbl.cnpj"/></td>
                                                  <td class="text-right">${usuario.cpf}</td>
                                               </tr>
	                 					</c:if>
	                 
                                          <c:if test="${usuarioForm.perfil == 'C'}">
                                          	   <tr>
                                                  <td class="text-left"><spring:message code="lbl.creci"/></td>
                                                  <td class="text-right">${usuarioForm.creci}</td>
                                              </tr>
                           				  </c:if>
                           
                           				  <c:if test="${usuarioForm.perfil == 'C'}">
                                          	   <tr>
                                                  <td class="text-left"><spring:message code="lbl.creci"/></td>
                                                  <td class="text-right">${usuarioForm.creci}</td>
                                              </tr>
                           				 </c:if>
                                         
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.imoveis"/></td>
                                                 <td class="text-right">${usuarioForm.quantTotalImoveis}</td>
                                             </tr>
                                             
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.contato"/></td>
                                                 <td class="text-right">${usuarioForm.quantTotalContatos}</td>
                                             </tr>
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.parcerias"/></td>
                                                 <td class="text-right">${usuarioForm.quantTotalParcerias}</td>
                                             </tr>
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.interessados"/></td>
                                                 <td class="text-right">${usuarioForm.quantTotalInteressadosImoveis}</td>
                                             </tr>
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.intermediacoes" /></td>
                                                 <td class="text-right">${usuarioForm.quantTotalIntermediacoes}</td>
                                             </tr>
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.visualizacoes" /></td>
                                                 <td class="text-right">${usuarioForm.quantTotalVisitasImoveis}</td>
                                             </tr>
                                            
                                         </tbody>

                                     </table>	
                                	</div>                                                        
                                 
							</div><!-- /.panel -->
						</div>
					</div>
                             
					    <!-- INICIO - LISTA CONTATOS USUARIO -->                
                        <div class="col-md-8">                         	
                        	<div class="panel panel-scrollable rounded shadow">
	                                <div class="panel-heading">			                                	
	                                    <h3 class="panel-title">
	                                    	<div class="pull-left">
	                              				<spring:message code="lbl.contatos"/> 
	                               			</div>
	                               				
	                              			<div class="pull-right">
	                              				<a href="#a" class="btn btn-sm" onClick="mostrarModal(3);" style=""><i class="fa fa-question" ></i></a>
	                              			</div>
	                              			<br>
                             			</h3>
                             			&nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.contato"/> </strong>: (${usuarioForm.quantTotalContatos}) </label>
                             			
	                                </div><!-- /.panel-heading -->
	                                	<c:choose>
	                                		<c:when test="${ not empty listarContatosPerfilUsuario }">
	                                			<div class="panel-body panel " style="height: 400px;">	                                    
							                    	<c:forEach var="contato" items="${listarContatosPerfilUsuario}">
								                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
								                            <div class="panel">
								                                <div class="panel-body" style="height: 220px;">						                                   
								                                    <ul class="">
								                                    	<c:choose>
								                                    		<c:when test="${contato.usuarioHost.id != usuarioForm.id}">
								                                    				<li class="text-center">
											                                        	<a href="${urlUsuario}/detalhesUsuario/${contato.usuarioHost.id}"> 
											                                            	<img class="img-circle img-bordered-success" src="data:image/jpeg;base64,${contato.usuarioHost.imagemArquivo}" style="width: 100px; height: 130px; ">
											                                            </a>	
											                                        </li>
											                                        <li class="text-center">
											                                            <h4 class="text-capitalize">
											                                            	<a href="${urlUsuario}/detalhesUsuario/${contato.usuarioHost.id}">
											                                            		${contato.usuarioHost.nome}
											                                            	</a>
											                                            </h4>
											                                            <p class="text-muted text-capitalize">${contato.usuarioHost.perfilFmt} </p>
											                                        </li>		
								                                    		</c:when>
								                                    		
								                                    		<c:when test="${contato.usuarioConvidado.id != usuarioForm.id}">
								                                    			<li class="text-center">
										                                        	<a href="${urlUsuario}/detalhesUsuario/${contato.usuarioConvidado.id}">
										                                            	<img class="img-circle img-bordered-success" src="data:image/jpeg;base64,${contato.usuarioConvidado.imagemArquivo}"  style="width: 100px; height: 130px; ">
										                                            </a>	
										                                        </li>
										                                        <li class="text-center">
										                                            <h4 class="text-capitalize">
										                                            	<a href="${urlUsuario}/detalhesUsuario/${contato.usuarioConvidado.id}">
										                                            		${contato.usuarioConvidado.nome}
										                                            	</a>
										                                            </h4>
										                                            <p class="text-muted text-capitalize">${contato.usuarioConvidado.perfilFmt} </p>
										                                        </li>	
								                                    		</c:when>
								                                    	</c:choose>
								                                        
								                                    </ul><!-- /.list-unstyled -->
								                                </div><!-- /.panel-body -->
								                            </div><!-- /.panel -->		
								                         </div>   				                        
							                        </c:forEach>
							                     </div>	
	                                		</c:when>
	                                		
	                                		<c:when test="${ empty listarContatosPerfilUsuario }">
	                                			<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
						                                 <div class="media-list list-search">
						                                 	<div class="callout callout-warning">
							                                    <strong><spring:message code="lbl.nenhum.contato.retornado"/></strong>				                                    
							                                </div>   
						                                </div>
				                                    <br/>
				                                </div>		
	                                		</c:when> 
	                                		
	                                	</c:choose>	                                     
	                            </div><!-- /.panel -->		                                        
		                </div>                         
		                <!-- FIM - LISTA CONTATOS USUARIO -->   
                                           
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->
			
				<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
				<!-- End content  modal Ajuda - funcionalidade -->

        </section><!-- /#wrapper -->

		   <!-- Start optional size modal element - confirmacao cancelamento de contato -->
            <div id="idModalConfirmacaoCancelContato" class="modal fade bs-example-modal-lg-confirmacao-cancel-contato" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdContato" readonly="readonly" name="modIdContato">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.cancel.contato"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.cancel.contato"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-theme" onClick="cancelarContato();"><spring:message code="lbl.sim"/></button>                            
	                      		<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                        </div>
							
							<div id="msgRetornoConfirmCancelContatoErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao cancelamento de contato -->
         
          <!-- Start optional size modal element - confirmacao cancelamento de convite -->
            <div id="idModalConfirmacaoCancelConvite" class="modal fade bs-example-modal-lg-confirmacao-cancel-convite" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdConvite" readonly="readonly" name="modIdConvite">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.cancel.convite"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.cancel.convite"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-theme" onClick="cancelarConvite();"><spring:message code="lbl.sim"/></button>                            
	                        	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                        </div>
							
							<div id="msgRetornoConfirmCancelContatoErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao cancelamento de convite -->	

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

