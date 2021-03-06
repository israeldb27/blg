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
	else if ( id == 6 ){
		$('#msgModal').html("<spring:message code='msg.aba.title.detalhes.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.detalhes.usuario'/>");
	}
	
	$("#idModalItem").modal("show");
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
                    <h2>
	                    <i class="fa fa-pencil"></i><spring:message code="lbl.title.detalhes.usuario"/>
	                    <div class="pull-right">
	                         <a href="#a" class="btn btn-sm"  onClick="mostrarModal(6);"><i class="fa fa-question" style="font-size: 12px;"></i></a>                                        
	                     </div>		 
	                 </h2>
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
	                                     					&nbsp;&nbsp;
	                                     					<a href="#a" onClick="iniciarSeguirUsuario(${usuarioForm.id})" id="idIniciarSeguidor_${usuarioForm.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.iniciar.seguir.usuario"/>'> <i class="fa fa-list-ul pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.link.iniciar.seguir.usuario"/> </font> &nbsp; &nbsp; </i> </a> 
	                                   						<a href="#a" onClick="cancelarSeguirUsuario(${usuarioForm.id})" id="idCancelarSeguidor_${usuarioForm.id}" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.cancelar.seguir.usuario"/>'> <i class="fa fa-outdent pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.link.cancelar.seguir.usuario"/> </font> &nbsp; &nbsp; </i></a>		                                     		
													
													</c:when>
													
													<c:when test="${usuarioForm.isSeguindoUsuario == 'S'}">																																						
														<spring:message code="lbl.enviar.convite" var="mensagemEnviarConvite"/>
														<a href="#a" id="idConvite_${usuarioForm.id}"  onClick="enviarConvite(${usuarioForm.id})" style="font-size:x-large; color: rgb(99, 110, 123); display: none;"  class="dropdown-toggle"  ><i class="fa fa-user-plus"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> ${mensagemEnviarConvite} </font> </a>
														<a href="#a" id="idCancelarConvite_${usuarioForm.id}" onclick="prepararModalCancelarConvite(${usuarioForm.id})" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.canceler.enviar.convite"/> </font> &nbsp;&nbsp; </i> </a>
														&nbsp;&nbsp;
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
	                                        <div id="owl-demo" class="owl-carousel owl-theme" >                                             
	                                            
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
                             			&nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.contato"/> </strong>: (${quantTotalContatos}) </label>
                             			
	                                </div><!-- /.panel-heading -->
	                                	
	                                			<div class="panel-body panel " style="height: 470px;">	                                    
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
	                            </div><!-- /.panel -->
	                       <!-- /.END CONTATOS DO USUARIO -->
	                       
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
			<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
		<!-- End content  modal Ajuda - funcionalidade -->		
		
        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
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