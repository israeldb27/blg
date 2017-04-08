<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/mensagem" var="urlMensagem"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/nota" var="urlNota"/>
<spring:url value="/preferencia" var="urlPreferencia"/>
<spring:url var="urlImovelFavoritos" value="/imovelFavoritos"/>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   

<link href="${context}/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"  href="${context}/css/smart-forms_det_imovel.css">
<link href="${context}/css/owl.carousel.css" rel="stylesheet">
<link href="${context}/css/owl.theme.css" rel="stylesheet">
<link href="${context}/css/style_det_imovel.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${context}/js/jquery-ui.js"></script>
<script type="text/javascript" src="${context}/js/additional-methods.js"></script>   

<style type="text/css">
#owl-demo .item img{
    display: block;
    width: 100%;
    height: auto;
}
div#map_container{
	width:70%;
	height:150px;
}
.modal-dialog {}
.thumbnail {margin-bottom:6px;}
.carousel-control.left,.carousel-control.right{
  background-image:none;
}
</style>

<!-- TABS -->
<script src="${context}/js/jquery-ui.js "></script>

<!-- SHADOWBOX -->
<link rel="stylesheet" type="text/css" href="${context}/includes/shadowbox/shadowbox.css">
<script type="text/javascript" src="${context}/includes/shadowbox/shadowbox.js"></script>
<script type="text/javascript">
Shadowbox.init();
</script>

<script defer src="${context}/js/jquery.flexslider.js"></script>
<script type="text/javascript">


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


function prepararModalAddRecomendacao(){
	$("#msgErroNovaRecomendacao").html("");
	$("#idModalRecomendacao").modal("show");	
}

function adicionarRecomendacao(){	
	var x = document.getElementById("novaRecomendacao");	
	
	if ($("#novaRecomendacao").val() == ''){
		$('#msgErroNovaRecomendacao').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	if ($("#novaRecomendacao").val() != '') {
		$.post("${urlUsuario}/adicionarRecomendacaoDetalheUsuario", {'novaRecomendacao' : x.value}, function() {		
			location.reload();
		});
	}	
}

function adicionarInteresse(id) {    	
	var parametro1 = id;
    $.ajax({                
        url: '${urlImovelFavoritos}/adicionarFavoritos/' + parametro1,                
        success: function(){
        	$("#idMeInteressei_"+id).hide();
        	$("#idNovoMeInteressei_"+id).hide();
	    	$("#idInteressado_"+id).show();	    	    	
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });   
}

function retirarInteresse(id) {
	var parametro1 = id;    		
    $.ajax({                
        url: '${urlImovelFavoritos}/retirarFavoritos/' + parametro1,                
        success: function(){
        	$("#idNovoMeInteressei_"+id).show();                	
	    	$("#idInteressado_"+id).hide();
	    	$("#idNovoInteressado_"+id).hide();	    	    	
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });   
}

function aceitarRecomendacao(id) {    	
	var parametro1 = id;
	$.ajax({  
	    type: 'GET',	
         url: '${urlUsuario}/responderRecomendacao/aceitar/' + parametro1,
         dataType: 'json',
         success: function(data){	        	 
        	 if ( data == 'ok') {
        		 $("#idAceitaRecomendacao_"+id).hide();  
        		 $("#idRecusaRecomendacao_"+id).hide();	
        		 $("#spanRecomendacao_"+id).hide();	
     	    	 $("#idMsgAceitarRecomendacao_"+id).show();
        	 }
        	 else  {
	        	 $('#msgRetornoPropostaErro').html(data);
	         }	
         },	      
     });   
}

function recusarRecomendacao(id) {    	
	var parametro1 = id;
	$.ajax({  
	    type: 'GET',	
         url: '${urlUsuario}/responderRecomendacao/recusar/' + parametro1,
         dataType: 'json',
         success: function(data){	        	 
        	 if ( data == 'ok') {
        		 $("#idAceitaRecomendacao_"+id).hide();  
        		 $("#idRecusaRecomendacao_"+id).hide();	
        		 $("#spanRecomendacao_"+id).hide();	        		 
     	    	 $("#idMsgRecusarRecomendacao_"+id).show();
        	 }
        	 else  {
	        	 $('#msgRetornoPropostaErro').html(data);
	         }	
         },	      
     });   
}

function mostrarModal(id){	
	if (id == 0){
		$('#msgModal').html("<spring:message code='lbl.modal.sobre.mim'/>");		
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.sobre.mim'/>");
	}
	else if ( id == 1){
		$('#msgModal').html("<spring:message code='lbl.modal.notas.usuario'/> ");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.notas.usuario'/>");
	}
	else if ( id == 2){
		$('#msgModal').html("<spring:message code='lbl.modal.pref.imoveis'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.pref.imoveis'/>");
	}
	else if ( id == 3){
		$('#msgModal').html("<spring:message code='lbl.modal.contatos'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.contatos'/>");
	}
	else if ( id == 4){
		$('#msgModal').html("<spring:message code='lbl.modal.lista.seguidores.detalhes.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.lista.seguidores.detalhes.usuario'/>");
	}
	else if ( id == 5){
		$('#msgModal').html("<spring:message code='lbl.modal.recomendacoes.detalhe.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.recomendacoes.detalhe.usuario'/>");
	}
	
	$("#idModalItem").modal("show");
}

</script>
		
<c:import url="../layout/head-layout.jsp"></c:import>   
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../layout/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../layout/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">     
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.detalhes.usuario"/> </h2>		 
                </div><!-- /.header-content -->
                                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
					<div class="row">
						<!-- START  -->
						<div class="col-lg-9 col-md-9 col-sm-13">
							<div class="panel rounded shadow">
								<div class="panel-heading" align="center">
    								<span class="label pull-left"  style="font-size: 18px;margin-bottom:10px;background-color: black;font-size: 100%;">${usuarioForm.perfilFmt}</span>                                    
    								<span class="label pull-right" style="margin-left: 14px;color:gray; font-size: 12px;"><spring:message code="lbl.data.cadastro.usuario"/> <fmt:formatDate value='${usuarioForm.dataCadastro}' pattern='dd/MM/yyyy'/></span>
									<h2 class="text-bold" style="margin-top: 0px;margin-bottom: 5px;width: 50%;">${usuarioForm.nome}</h2>
									<h5 style="margin-top: 4px;margin-bottom: 0px;width: 50%;">${usuarioForm.cidade} - ${usuarioForm.estado}</h5>
									<br>
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
	                                            <img class="img-circle"  src="data:image/jpeg;base64,${usuarioForm.imagemArquivo}" style="margin-left:30px; width: 240px; height: 240px; ">	                                            	                                            	                                                                                        
	                                        </div>
	                                    </div>
                                    </div>
                                	
                                	<div class="pull-right">
                                		 <table class="table table-striped" style="margin-top:10px; font-size: 13px;">
                                         <tbody>
                                         
                                         <c:choose>
                                         	<c:when test="${((usuarioForm.perfil == 'P') || (usuarioForm.perfil == 'C')) }">
                                         		  <tr>
	                                                 <td class="text-left"><spring:message code="lbl.cpf"/></td>
	                                                 <td class="text-right">${usuario.cpf}</td>
	                                              </tr>                                                     
                                         	</c:when>
                                         	
                                        	<c:when test="${usuarioForm.perfil == 'I'}">
                                       			<tr>
                                                  <td class="text-left"><spring:message code="lbl.cnpj"/></td>
                                                  <td class="text-right">${usuario.cpf}</td>
                                               </tr>  	
                                         	</c:when>
                                         	
                                         	<c:when test="${usuarioForm.perfil == 'C'}">
                                         		<tr>
                                                   <td class="text-left"><spring:message code="lbl.creci"/></td>
                                                   <td class="text-right">${usuarioForm.creci}</td>
                                              	</tr>
                                         	</c:when>                                                                              
                                         </c:choose>                     
                                         
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
					<div class="row">
						<div class="col-lg-9 col-md-9 col-sm-13">
						
							<!-- /.START Descricao -->
							<div class="panel rounded shadow">
								<div class="panel-heading">
									<h3 class="panel-title">
										<div class="pull-left">
                              				<spring:message code="lbl.sobre.mim"/>
                               			</div>
                               				
                              			<div class="pull-right">
                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);" style=""><i class="fa fa-question" ></i></a>
                              			</div>
                              			<br>
									</h3>
								</div>
								<div class="panel-body">
                                    <p>${usuarioForm.descSobreMim}"</p>
								</div>
							</div>
							<!-- /.END Descricao -->
							
						<!-- /.START Imóveis Usuario -->
							<div class="panel panel-scrollable rounded shadow">
                                <div class="panel-heading">
                                	<div align="left" style="margin-top: 5px;">                                
                              			<h3 class="panel-title"> <spring:message code="lbl.imoveis.usuario"/> &nbsp;
                              				<c:if test="${not empty usuarioForm.listaImoveisUsuario}">
                              					<a href="${urlImovel}/visualizarImoveisPerfilUsuario/${usuarioForm.id}" style="font-size:x-large; margin-left: 510px;" class="meta-action" title='<spring:message code="lbl.visualizar.imoveis.perfil.usuario"/>' ><i class="fa fa-home" style="color:gray"></i><font style="font-size: 12px; color: black;" >&nbsp; Filtrar Imóveis </font></a>
                              				</c:if> 
                              			</h3>
                              			
                              				<c:if test="${usuarioForm.quantTotalImoveis > 0}">                              					
                              					 &nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: (${usuarioForm.quantTotalImoveis}) </label>
                              				</c:if> 
                              		</div>                           	
                                </div>
                                
                                <c:choose>
                                	<c:when test="${ empty usuarioForm.listaImoveisUsuario }">
                                		<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
				                                 <div class="media-list list-search">
				                                 	<div class="callout callout-warning">
					                                    <strong><spring:message code="msg.nenhum.imovel.detalhes.usuario"/></strong>				                                    
					                                </div>   
				                                </div>
		                                    <br/>
		                                </div>
                                	</c:when>
                                	
                                	<c:when test="${not empty usuarioForm.listaImoveisUsuario }">
                                			<div class="panel-body panel panel-scrollable rounded shadow" style="height: 455px;">
					                                   <div class="media-list list-search">
					                                    <c:forEach var="imovel" items="${usuarioForm.listaImoveisUsuario}" varStatus="item">
					                                        <div class="media rounded shadow no-overflow">
					                                        	<c:if test="${imovel.destaque == 'S'}">
					                                                	<div class="ribbon-wrapper top-left">
								                                            <div class="ribbon ribbon-shadow">Destaque</div>
								                                        </div>
					                                            </c:if>
					                                            <div class="media-left">
					                                                <a href="${urlImovel}/detalhesImovel/${imovel.id}" >
					                                                   <span class="meta-provider ${imovel.classePorAcao}" style="font-size:19px;">${imovel.acaoFmt} <br>
					                                                   							<strong>  R$<fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
					                                                   </span><br>                                                   
					                                                    <img src="data:image/jpeg;base64,${imovel.imagemArquivo}"  class="img-responsive" style="width: 270px; height: 300px; alt="admin"/>
					                                                </a>
					                                            </div>
					                                            <div class="media-body">
					                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${imovel.tipoImovelFmt}</span>			                                                 
					                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlImovel}/detalhesImovel/${imovel.id}" style="color : #9d2428;">${imovel.titulo}</a></h4>
					                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovel.endereco} - ${imovel.bairro} - ${imovel.cidade} -${imovel.uf} </h1>
					                                                
					                                                  <div class="col-md-5" >  	                                                
						                                                	<div class="media-body" >
									                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.ultima.imovel.atualizacao" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${imovel.dataUltimaAtualizacao}' pattern='dd/MM/yyyy'/></font></span></em> 
									                                            
									                                            <br> <br>
									                                            
									                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.imovel" />: </font><span class="text-success"><br>				                                            
									                                            <font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em>				                                            
									                                            
									                                        </div>                                                  
						                                                    <br/> <br/> <br/> 	                                                   
						                                              </div>
						                                                
						                                               <div class="col-md-6" style="margin-right: -9px;">
						                                                    <table class="table table-condensed">
						                                                        <tbody style="font-size: 13px;">	                                                        
						                                                        
						                                                        	<tr>
						                                                                <td class="text-left"><spring:message code="lbl.area.m2.resum"/></td>
						                                                                <td class="text-right"><fmt:formatNumber value="${imovel.area}" pattern="#,##0;-0"/>m<sup>2</sup></td>
						                                                            </tr>
						                                                            <tr>
						                                                                <td class="text-left"><spring:message code="lbl.quartos.dormitorios.resum"/></td>
						                                                                <td class="text-right">${imovel.quantQuartos}</td>
						                                                            </tr>	                                                            
						                                                            <tr>
						                                                                <td class="text-left"><spring:message code="lbl.buscar.imovel.banheiros"/></td>
						                                                                <td class="text-right">${imovel.quantBanheiro}</td>
						                                                            </tr>	                                                            
						                                                            <tr>
						                                                                <td class="text-left"><spring:message code="lbl.suites"/></td>
						                                                                <td class="text-right">${imovel.quantSuites}</td>
						                                                            </tr>
						                                                            <tr>
						                                                                <td class="text-left"><spring:message code="lbl.vagas.garagem.resum"/></td>
						                                                                <td class="text-right">${imovel.quantGaragem} <spring:message code="lbl.num.vagas"/></td>
						                                                            </tr>
						                                                            
						                                                            <tr>
						                                                                <td class="text-left"><spring:message code="lbl.codigo.identificacao.imovel.resum"/></td>
						                                                                <td class="text-right">${imovel.codigoIdentificacao}</td>
						                                                            </tr>
						                                                        </tbody>
						                                                    </table>
						                                                    
						                                                    <br>
						                                                    
						                                                      <% if ( request.getSession().getAttribute("acessoValido").equals("S") ) {%>   
						                                                    	<c:if test="${(imovel.interessadoImovel == 'N') && (imovel.usuario.id != usuario.id)}">
																					<a href="#a" id="idMeInteressei_${imovel.id}" onClick="adicionarInteresse(${imovel.id})" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action"><i class="fa fa-star-o" title="<spring:message code="lbl.me.interessei"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> <spring:message code="lbl.me.interessei"/> &nbsp;&nbsp; </a> 
																				</c:if>
																				
																				<c:if test="${imovel.interessadoImovel == 'S'}">
																					<a href="#a" id="idNovoInteressado_${imovel.id}" onClick="retirarInteresse(${imovel.id})" style="font-size:x-medium; color: rgb(99, 110, 123);" class="meta-action"><i class="fa fa-star" style="color: rgb(99, 110, 123);" title="<spring:message code="lbl.interessado"/>"></i> &nbsp;&nbsp; </a>
																				</c:if>
																				
																				<a href="#a" id="idNovoMeInteressei_${imovel.id}" onClick="adicionarInteresse(${imovel.id})" style="font-size:x-large; color: rgb(99, 110, 123); display: none;" class="meta-action"><i class="fa fa-star-o" title="<spring:message code="lbl.me.interessei"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> <spring:message code="lbl.me.interessei"/> &nbsp;&nbsp; </a> 
																				<a href="#a" id="idInteressado_${imovel.id}" onClick="retirarInteresse(${imovel.id})" style="font-size:x-large; display: none;" class="meta-action"><i class="fa fa-star" style="color: rgb(99, 110, 123);" title="<spring:message code="lbl.interessado"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> <spring:message code="lbl.interessado"/> </font> &nbsp;&nbsp; </a> 
																				
						                                                        <a href="#a" onClick="adicionarComparativo(${imovel.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-eye" style="color: rgb(99, 110, 123);" title="<spring:message code="lbl.title.link.comparar"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> <spring:message code="lbl.title.link.comparar"/> </font></a>
						                                                    <% } %>	 
						                                                    <br> <br>
						                                                </div>  
					                                            </div>
					                                        </div>
					                                    </c:forEach>
					                                </div>
			                                    <br/>
			                                </div>
                                	</c:when>
                                </c:choose>
                            </div>                      
                         <!-- /.END Imóveis Usuário  --> 
 
 						<!-- /.START Notas Usuario  -->  
 						 <div class="panel panel-scrollable rounded shadow">
                                <div class="panel-heading">			                                	
                                    <h3 class="panel-title">
                                    	<div class="pull-left">
                              				<spring:message code="lbl.notas.usuario"/>
                               			</div>
                               				
                              			<div class="pull-right">
                              				<a href="#a" class="btn btn-sm" onClick="mostrarModal(1);" style=""><i style="" class="fa fa-question" ></i></a>
                              			</div>
                              			<br>                                    	
                                    </h3>
                                   		&nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.notas"/> </strong>: (${usuarioForm.quantTotalNotas}) </label>
                                </div><!-- /.panel-heading -->
                                
                                <div class="profile-body">
										<c:choose>
                                			<c:when test="${not empty usuarioForm.listaNotasUsuario}">
                                				<div class="timeline">
                                					<c:forEach var="nota" items="${usuarioForm.listaNotasUsuario}"> 
                                						<div class="timeline-item last-timeline">
															<div class="timeline-badge">
																  <c:choose>
						                                         		<c:when test="${((nota.acao == 'R') || (nota.acao == 'U') || (nota.acao == 'E'))}">
						                                         			<img class="timeline-badge-userpic" src="data:image/jpeg;base64,${nota.usuario.imagemArquivo}" style="width: 80px; height: 90px; " >
						                                         		</c:when>
						                                         		
						                                         		<c:when test="${((nota.acao == 'I') || (nota.acao == 'P')) }">					                                         			
						                                         			<img  class="timeline-badge-userpic" src="data:image/jpeg;base64,${nota.imovel.imagemArquivo}" style="width: 80px; height: 90px; " >
						                                         		</c:when>
						                                         	</c:choose>	
															</div>
															<div class="timeline-body">
																<div class="timeline-body-arrow">
																</div>
																
																<c:choose>
																	    <c:when test="${nota.acao == 'P'}">
																	    	<div class="timeline-body-head">
																				<div class="timeline-body-head-caption">
																					<a href="#" onClick="carregaDetalhesImovel(${nota.imovel.id})" class="timeline-body-title font-blue-madison"><spring:message code="lbl.nota.parceria"/></a>
																					<span class="timeline-body-time font-grey-cascade"> </span>
																				</div>
																			</div>
																			<div class="timeline-body-content">
																				<p>
																					<small class="block text-muted"><label><strong style="font-size: 13px;">  <spring:message code="lbl.descricao.nota"/>: </strong></label>  ${nota.descricao} <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a></small>
																				</p>
																			</div>
																	    													    			    	
																	    </c:when>
																	    
																	    <c:when test="${nota.acao == 'R'}">
																	    	
																	    	<div class="timeline-body-head">
																				<div class="timeline-body-head-caption">
																					<a href="#" class="timeline-body-title font-blue-madison"><spring:message code="lbl.nota.preferencia"/></a>
																					<span class="timeline-body-time font-grey-cascade"> </span>
																				</div>
																			</div>
																			<div class="timeline-body-content">
																				<p>
																					<small class="block text-muted"><label><strong style="font-size: 13px;">  <spring:message code="lbl.descricao.nota"/>: </strong> </label>  ${nota.descricao}</small>
																				</p>
																			</div>																	    	
																	    	
																	    </c:when>
																	    
																	    <c:when test="${(nota.acao == 'U')}">																	    	
																	    	<div class="timeline-body-head">
																				<div class="timeline-body-head-caption">
																					<a href="${urlUsuario}/detalhesUsuario/${usuario.id}"  class="timeline-body-title font-blue-madison"><spring:message code="lbl.nota.info.usuario"/></a>
																					<span class="timeline-body-time font-grey-cascade"> </span>
																				</div>
																			</div>
																			<div class="timeline-body-content">
																				<p>
																					<small class="block text-muted"><label><strong style="font-size: 13px;">  <spring:message code="lbl.descricao.nota"/>: </strong></label>  ${nota.descricao}</small>
																				</p>																				
																			</div>
																	    </c:when>
																	    
																	      <c:when test="${(nota.acao == 'E')}">																	    	
																	    	<div class="timeline-body-head">
																				<div class="timeline-body-head-caption">
																					<a href="${urlUsuario}/detalhesUsuario/${usuario.id}"  class="timeline-body-title font-blue-madison"> ${nota.usuario.nome}</a>
																					<span class="timeline-body-time font-grey-cascade"> </span>
																				</div>
																			</div>
																			<div class="timeline-body-content">
																				<p>
																					<small class="block text-muted"><label><strong style="font-size: 13px;">  <spring:message code="lbl.nota.escreveu.nota"/>: </strong></label>  ${nota.descricao}</small>
																				</p>																				
																			</div>
																	    </c:when>
																	    
																	    <c:when test="${nota.acao == 'I'}">
																	    	
																	    	<div class="timeline-body-head">
																				<div class="timeline-body-head-caption">
																					<a href="#" onClick="carregaDetalhesImovel(${nota.imovel.id})" class="timeline-body-title font-blue-madison"><spring:message code="lbl.nota.imovel"/></a>
																					<span class="timeline-body-time font-grey-cascade"> </span>
																				</div>
																			</div>
																			<div class="timeline-body-content">
																				<p>
																					<small class="block text-muted"><label><strong style="font-size: 13px;">  <spring:message code="lbl.descricao.nota"/>: </strong></label>  ${nota.descricao} <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a></small>
																				</p>
																			</div>	
																	    </c:when>															    
															  </c:choose>  
															     <em class="text-xs text-muted"><strong style="font-size: 13px;"> <spring:message code="lbl.data.nota"/>: </strong> <span class="text-danger"><fmt:formatDate value='${nota.dataNota}' pattern='dd/MM/yyyy'/></span></em>
															</div>
														</div>
                                					</c:forEach>								
												</div>
                                			</c:when>
                                			
                                			<c:when test="${ empty usuarioForm.listaNotasUsuario }">
	                                			 <div class="callout callout-warning">
				                                    <strong><spring:message code="lbl.nenhuma.nota"/></strong>			                                    
				                                </div>                                		
	                                		</c:when>
	                                		
                                		</c:choose>		
									</div>
                                                            
                                      
                         </div><!-- /.panel -->
 						
 						<!-- /.END Notas Usuario -->  						
 						
 						  <c:if test="${usuarioForm.perfil == 'P'}">
		                      <!-- /.START PREFERENCIAS DO USUARIO -->		                      	   
			                      		<!-- Start scrollable panel -->
				                            <div class="panel panel-scrollable  rounded shadow">
				                                <div class="panel-heading">			                                	
				                                    <h3 class="panel-title">
				                                    	<div class="pull-left">
				                              				<spring:message code="lbl.pref.imoveis"/>
				                               			</div>
				                               				
				                              			<div class="pull-right">
				                              				<a href="#a" class="btn btn-sm" onClick="mostrarModal(2);" style=""><i class="fa fa-question" ></i></a>
				                              			</div>
				                              			<br>	
				                                    </h3>
				                                    &nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.pref.imoveis"/> </strong>: (${usuarioForm.quantTotalPrefImoveis}) </label>
				                                </div><!-- /.panel-heading -->
				                                
				                                <c:choose>
				                                	<c:when test="${not empty usuarioForm.listaPreferenciaImoveis}">
				                                		<div class="panel-body panel rounded shadow">
						                                    <table class="table table-striped" >
					                                            <thead>
					                                            <tr>
					                                                <th class="text-center"><spring:message code="lbl.tipo.imovel"/></th>
					                                                <th class="text-center"><spring:message code="lbl.acao.imovel"/></th>
					                                                <th class="text-center"><spring:message code="lbl.estado"/></th>
					                                                <th class="text-center"><spring:message code="lbl.cidade"/></th>
					                                                <th class="text-center"><spring:message code="lbl.bairro"/></th>
					                                            </tr>
					                                            </thead>
					                                            <tbody>
					                                            <c:forEach var="pref" items="${usuarioForm.listaPreferenciaImoveis}" >
						                                            <tr>
						                                                <td class="text-center" style="font-size: 13px;"> ${pref.tipoImovelFmt}</td>
						                                                <td class="text-center" style="font-size: 13px;">${pref.acaoFmt}</td>
						                                                <td class="text-center" style="font-size: 13px;">${pref.siglaEstado}</td>
						                                                <td class="text-center" style="font-size: 13px;">${pref.nomeCidade}</td>
						                                                <td class="text-center" style="font-size: 13px;">${pref.nomeBairro}</td>			                                              
						                                            </tr>
					                                            </c:forEach>
					                                            </tbody>
					                                        </table>
						                                </div><!-- /.panel-body -->
				                                	</c:when>
				                                	
				                                	<c:when test="${ empty usuarioForm.listaPreferenciaImoveis}">
				                                		<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
								                                 <div class="media-list list-search">
								                                 	<div class="callout callout-warning">
									                                    <strong><spring:message code="msg.nenhuma.pref.imovel.cadastrada"/></strong>				                                    
									                                </div>   
								                                </div>
						                                    <br/>
						                                </div>			
				                                	</c:when>
				                                </c:choose>
				                             
				                            </div><!-- /.panel -->
				                        <!--/ End scrollable panel --> 			                       	                      
		                      <!-- /.END PREFERENCIAS DO USUARIO -->	
	                      </c:if>	 	
	                      
	                       <!-- /.START CONTATOS DO USUARIO -->	                       
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
	                                		<c:when test="${ not empty usuarioForm.listaContatosUsuario }">
	                                			<div class="panel-body panel " style="height: 400px;">	                                    
							                    	<c:forEach var="contato" items="${usuarioForm.listaContatosUsuario}">
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
	                                		
	                                		<c:when test="${ empty usuarioForm.listaContatosUsuario }">
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
	                       <!-- /.END CONTATOS DO USUARIO -->
	                       
	                        <!-- /.START SEGUIDORES DO USUARIO -->	                       
	                            <div class="panel panel-scrollable rounded shadow">
	                                <div class="panel-heading">	                                				                                	
	                                    <h3 class="panel-title">
	                                    	<div class="pull-left">
	                              				<spring:message code="lbl.title.lista.seguidores.detalhes.usuario"/>
	                               			</div>
	                               				
	                              			<div class="pull-right">
	                              				<a href="#a" class="btn btn-sm" onClick="mostrarModal(4);" style=""><i class="fa fa-question" ></i></a>
	                              			</div>
	                              			<br>
                             			</h3>
                             			&nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.seguidores"/> </strong>: (${usuarioForm.quantTotalSeguidores}) </label>
                             			
	                                </div><!-- /.panel-heading -->
	                                	<c:choose>
	                                		<c:when test="${ not empty usuarioForm.listaSeguidores }">
                                				<div class="panel-body panel " style="height: 400px;">	                                    
							                    	<c:forEach var="usuarioSeguidor" items="${usuarioForm.listaSeguidores}">
								                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
								                            <div class="panel">
								                                <div class="panel-body" style="height: 220px;">						                                   
								                                    <ul class="">
								                                        <li class="text-center">
								                                        	<a href="${urlUsuario}/detalhesUsuario/${usuarioSeguidor.usuario.id}">  
								                                            	<img class="img-circle img-bordered-success" src="data:image/jpeg;base64,${usuarioSeguidor.usuario.imagemArquivo}" style="width: 100px; height: 130px; ">
								                                            </a>	
								                                        </li>
								                                        <li class="text-center">
								                                            <h4 class="text-capitalize">
								                                            	<a href="${urlUsuario}/detalhesUsuario/${usuarioSeguidor.usuario.id}">
								                                            		${usuarioSeguidor.usuario.nome}
								                                            	</a>
								                                            </h4>
								                                            <p class="text-muted text-capitalize">${usuarioSeguidor.usuario.perfilFmt} </p>
								                                        </li>						                                        
								                                        
								                                    </ul><!-- /.list-unstyled -->
								                                </div><!-- /.panel-body -->
								                            </div><!-- /.panel -->		
								                         </div>   				                        
							                        </c:forEach>
							                        </div>
	                                		</c:when>
	                                		<c:when test="${empty usuarioForm.listaSeguidores }">
	                                			<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
						                                 <div class="media-list list-search">
						                                 	<div class="callout callout-warning">
							                                    <strong><spring:message code="lbl.nenhum.usuario.seguindo"/></strong>				                                    
							                                </div>   
						                                </div>
				                                    <br/>
				                                </div>	
	                                		</c:when>
	                                	</c:choose>	
	                                     
	                            </div><!-- /.panel -->
	                       <!-- /.END SEGUIDORES DO USUARIO -->	                       
	                       
	                      <!-- /.START Recomendacoes -->	
															
							<div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <h3 class="panel-title">
                                    	<div class="pull-left">
                              				<spring:message code="lbl.title.aba.recomendacoes.detalhe.usuario"/>
                               			</div>
                               				
                              			<div class="pull-right">
                              				<a href="#a" class="btn btn-sm" onClick="mostrarModal(5);" style=""><i class="fa fa-question" ></i></a>
                              			</div>
                              			<br>	
                                    </h3>
                                    &nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.recomendacoes"/> </strong>: (${usuarioForm.quantTotalRecomendacoes}) </label>
                                </div>
                                
                                <c:choose>
                                	<c:when test="${not  empty usuarioForm.listaRecomendacoes }">
                                		<div class="panel-body">
		                                    <ul class="media-list comment-list">
												<c:forEach var="recomendacao" items="${usuarioForm.listaRecomendacoes}">
													<li class="media">		                                            
			                                            <c:choose>		                                            	
			                                            	<c:when test="${((recomendacao.isStatusEnviado()) && (recomendacao.usuario.id == usuario.id))}">
			                                            		<div class="media-left">
					                                                <a href="#">
					                                                    <img class="media-object thumbnail" src="data:image/jpeg;base64,${recomendacao.usuario.imagemArquivo}" style="width: 50px; height: 60px;"  />
					                                                </a>
					                                            </div>
			                                            		<div class="media-body">
			                                            			<span class="label pull-right" style="background-color: #9d2428; font-size: 12px">Aguardando Aprovação</span>
					                                                <h4>${recomendacao.usuario.nome}</h4>
					                                                <small class="text-muted"><fmt:formatDate value='${recomendacao.dataResposta}' pattern='dd/MM/yyyy HH:mm:ss'/></small>
					                                                <p>${recomendacao.descricao}</p>
					                                            </div>	
			                                            	</c:when>
			                                            	
			                                            	<c:when test="${(recomendacao.isStatusAceito())}">
			                                            		<div class="media-left">
					                                                <a href="#">
					                                                    <img class="media-object thumbnail" src="data:image/jpeg;base64,${recomendacao.usuario.imagemArquivo}"  style="width: 50px; height: 60px;"  />
					                                                </a>
					                                            </div>
			                                            		<div class="media-body">		                                            			
					                                                <h4>${recomendacao.usuario.nome}</h4>
					                                                <small class="text-muted"><fmt:formatDate value='${recomendacao.dataResposta}' pattern='dd/MM/yyyy HH:mm:ss'/></small>
					                                                <p>${recomendacao.descricao}</p>
					                                            </div>	
			                                            	</c:when>
			                                            	
			                                            	<c:when test="${((recomendacao.isStatusEnviado()) && (recomendacao.usuarioRecomendado.id == usuario.id))}">
			                                            		<div class="media-left">
					                                                <a href="#">
					                                                    <img class="media-object thumbnail" src="data:image/jpeg;base64,${recomendacao.usuario.imagemArquivo}" style="width: 50px; height: 60px;"  />
					                                                </a>
					                                            </div>
			                                            		<div class="media-body">	
			                                            			<span id="spanRecomendacao_${recomendacao.id}" class="label pull-right" style="background-color: #9d2428; font-size: 12px">Aguardando Minha Aprovação</span>	                                            			
					                                                <h4>${recomendacao.usuario.nome}</h4>
					                                                <small class="text-muted"><fmt:formatDate value='${recomendacao.dataResposta}' pattern='dd/MM/yyyy HH:mm:ss'/></small>
					                                                <p>${recomendacao.descricao}</p>
					                                                
				                                                    <spring:message code="lbl.acao.aceita.recomendacao" var="mensagemAceitaRecomendacao"/>
									                                <a href="#a" onClick="aceitarRecomendacao(${recomendacao.id})" id="idAceitaRecomendacao_${recomendacao.id}" style="font-size:x-large; "  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemAceitaRecomendacao}" ><i class="fa fa-check"></i></a>
					                                                                                                    
					                                                <spring:message code="lbl.acao.recusar.recomendacao" var="mensagemRecusarRecomendacao"/>	
					                                                <a href="#a" onClick="recusarRecomendacao(${recomendacao.id})" id="idRecusaRecomendacao_${recomendacao.id}" style="font-size:x-large; "  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemRecusarRecomendacao}" ><i class="fa fa-times"></i></a>
					                                                
					                                                
					                                                <div id="idMsgAceitarRecomendacao_${recomendacao.id}"  class="panel panel-success" style="display: none;">												                   
												                          <div class="alert alert-success">
								                                                <strong><spring:message code="msg.recomendacao.aceita"/></strong> 
								                                           </div>	                          
													                </div><!-- /.panel -->                      
													                
												               		 <div id="idMsgRecusarRecomendacao_${recomendacao.id}" class="panel panel-danger" style="display: none;">
												               		 	   <div class="alert alert-danger">
								                                                <strong><spring:message code="msg.recomendacao.recusado"/></strong> 
								                                            </div>												                   			                                                    
												                      </div><!-- /.panel -->   
					                                            </div>
			                                            	</c:when>
			                                            </c:choose>
			                                            
			                                        </li><!-- media -->											
												</c:forEach>
		                                    </ul>
		                                    <br/>
											
											<c:if test="${usuarioForm.id != usuario.id}">
												 <ul class="media-list comment-list">
				                                        <li class="media">                                                                                        
				                                            <div class="mb-20"></div>
				                                            <form class="form-horizontal mb-20" role="form">	
					                                            <div class="form-group">
					                                               <input type="button" class="btn btn-primary" onClick="prepararModalAddRecomendacao();" value='<spring:message code="btn.modal.adicionar.recomendacao"/> '>
					                                            </div>
							                                      
				                                            </form>
				                                        </li>
				                                    </ul>
											</c:if>	
		                                    
		                                </div>	
                                	</c:when>
                                	
                                	<c:when test="${empty usuarioForm.listaRecomendacoes }">
                                		<div class="panel-body">
		                                	 <div class="callout callout-warning">
				                                    <strong><spring:message code="msg.nenhuma.recomendacao.recebida"/></strong>		                                    
				                              </div>
		                                    </br> </br>
		                                    
											    <div class="form-group">
	                                               <input type="button" class="btn btn-primary" onClick="prepararModalAddRecomendacao();" value='<spring:message code="btn.modal.adicionar.recomendacao"/> '>
	                                            </div> 
	                                	</div>		
                                	</c:when>
                                </c:choose>                                                                 
                            </div>
                         <!-- /.END Recomendacoes --> 
	                      
					</div>
                </div><!-- /.body-content -->
			</div>              
           
            </section><!-- /#page-content -->

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
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="cancelarContato();"><spring:message code="lbl.sim"/></button>                            
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
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="cancelarConvite();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoConfirmCancelContatoErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao cancelamento de convite -->
         
         
          <!-- Start inside form layout - Recomendacao -->
            <div id="idModalRecomendacao" class="modal fade bs-example-modal-form-recomendacao" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.cadastrar.recomendacao"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.nova.descricao.recomendacao"/></label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="novaRecomendacao">
                                            <div id="msgErroNovaRecomendacao"> </div>
                                        </div>
                                        
                                    </div><!-- /.form-group -->
                                   
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="adicionarRecomendacao();"><spring:message code="lbl.btn.adicionar.geral"/></button>
                                    </div>
                                </div><!-- /.form-footer -->								
																
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ End inside form layout - Recomendacao -->
            
           <!-- Start optional size modal element - item 1 -->
            <div id="idModalItem" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				          <h4 class="modal-title"><div id="msgModalFuncionalidade" > </div> </h4>
				        </div>
				        <div class="modal-body">  
				       	   <strong> <spring:message code="lbl.descricao.geral"/>:  </strong> <div id="msgModal" > </div>
				        </div>
				        <div class="modal-footer">			          
	                      <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>
				        </div>
				      </div>
				    </div>
				</div>
            </div><!-- /.modal -->   
         
        
		<!-- Start content modal Ajuda - funcionalidade -->
			<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
		<!-- End content  modal Ajuda - funcionalidade -->		
		
        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->
        
    <script src="${context}/js/owl.carousel.js"></script>
    <script>
        $(document).ready(function() {
          $("#owl-demo").owlCarousel({
              navigation : true, // Show next and prev buttons
              slideSpeed : 300,
              paginationSpeed : 400,
              singleItem:true,
              autoplay:true,
              stopOnHover:true,
              navigationText: ["Anterior", "Proximo"],
              navigation:true,
              responsive:true,
              pagination:false,
              thumbs: true
          });
        });
        </script>
    </body>
    <!--/ END BODY -->

</html>