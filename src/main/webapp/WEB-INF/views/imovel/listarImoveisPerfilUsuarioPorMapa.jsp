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

<style type="text/css">
div#map_container{
	width:70%;
	height:150px;
}
</style>
<script type="text/javascript"  src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
  
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

    	<script type="text/javascript">		
		
		$(window).load(function() {
		
			  var list = ${listaImoveis};			  
			  if ( list.length > 0  ){
				  
				    var lat1 = JSON.stringify(list, ['latitude']);
	            	var longi1 = JSON.stringify(list, ['longitude']);
	            	var titulo = JSON.stringify(list, ['titulo']);
	            	var parsedLat = JSON.parse(lat1);
	            	var parsedLongi = JSON.parse(longi1);
	            	var parsedTitulo = JSON.parse(titulo);
				  
				  var map = new google.maps.Map(document.getElementById('map'), {
		                zoom: 10,
		                center: new google.maps.LatLng(parsedLat[0].latitude, parsedLongi[0].longitude),
		                mapTypeId: google.maps.MapTypeId.ROADMAP
		              });
		
		              var infowindow = new google.maps.InfoWindow();
		              var marker, i;	
		              
		              for (i = 0; i < list.length; i++) {  
		                marker = new google.maps.Marker({
		                  position: new google.maps.LatLng(parsedLat[i].latitude, parsedLongi[i].longitude),
		                  map: map
		                });

		                google.maps.event.addListener(marker, 'click', (function(marker, i) {
		                  return function() {
		                    infowindow.setContent(parsedTitulo[i].titulo);
		                    infowindow.open(map, marker);
		                  }
		                })(marker, i));
		              }				  
			  }			
		});		
			
    	$(document).ready(function() {
    		
    		(function ($) {
    			  $('.spinner .btn:first-of-type').on('click', function() {
    			    $('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);
    			  });
    			  $('.spinner .btn:last-of-type').on('click', function() {
    			    $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);
    			  });
    			})(jQuery);
    		
    		$('#idEstado').change(function () {    			
    	        var comboPai = '#idEstado';
    	        var comboFilha = '#idCidade';
    	        var comboFilha2 = '#idBairro';
    	        limpaComboLinha(comboFilha);
    	        limpaComboLinha(comboFilha2);
    	        recuperaCidades();
    	    });
    		
    		$('#idCidade').change(function () {
    			var comboPai   = '#idCidade';
    			var comboFilha = '#idBairro';
    			limpaComboLinha(comboFilha);
    			recuperaBairros();		
    		 });    		

    		$('#opcaoOrdenacao').change(function () {				
    			$("#imovelBuscarImoveisForm").submit();      
    		 });    		

    		function limpaComboLinha(comboLinha) {
    		    $(comboLinha).empty();  
    		    $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');
    		    $(comboLinha).trigger("chosen:updated");
    		}
    	});	
		
		function buscarImoveisMapa(obj){
			alert(obj);
    	    $.ajax({
                type: 'POST',
                url: '${urlBuscarImoveisMapa}',
                dataType: 'json',
                success: function(json){
                    var options = "";
                    $.each(json, function(key, value){                    	
					   var latlng = new google.maps.LatLng(value.latitude, value.longitude);					   
						 var myOptions = {
							  zoom: 10,
							  center: latlng,
							  mapTypeId: google.maps.MapTypeId.ROADMAP
							};
							var map = new google.maps.Map(document.getElementById("map"),myOptions);							
							var marker = new google.maps.Marker({
							  position: latlng, 
							  map: map, 
							  title:"my hometown, Malim Nawar!"
							});
					});  
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });    	   
    	}
    	
    	function recuperaCidades(){
    	    var parametro1 = $("#idEstado").val();
    	    $.ajax({
                type: 'GET',
                url: '${urlBuscarCidades}/' + parametro1,
                dataType: 'json',
                success: function(json){
                    var options = "";
                    $.each(json, function(key, value){
                       $("#idCidade").append("<option value='"+value.key+"'>"+value.label+"</option>");
                    }); 
                    $('#idCidade').trigger("chosen:updated");
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });    	   
    	}

    	function recuperaBairros(){   
    	    var parametro1 = $("#idCidade").val();
    	    $.ajax({
                type: 'GET',
                url: '${urlBuscarBairros}/' + parametro1,
                dataType: 'json',
                success: function(json){
                    var options = "";
                    $.each(json, function(key, value){
                    	$("#idBairro").append("<option value='"+value.key+"'>"+value.label+"</option>");
                    });  
                    $('#idBairro').trigger("chosen:updated");
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });
    	}

    	function adicionarInteresse(id) {    	
    		var parametro1 = id;
    	    $.ajax({                
                url: '${urlImovelFavoritos}/adicionarFavoritos/' + parametro1,                
                success: function(){
                	$("#idMeInteressei_"+id).hide();
	    	    	$("#idInteressado_"+id).show();
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });   
    	}
    	
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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.busca.imoveis.mapa.por.usuario"/> </h2>
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                        <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
                        	<c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
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
	                                        		<img class="img-circle" src="${context}/${usuarioForm.imagemArquivo}" style="margin-left:350px; width: 240px; height: 240px; ">
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
                        
                        
                       <div class="col-lg-3 col-md-3 col-sm-4">
                            <form:form method="POST" id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/listarImoveisPerfilUsuarioPorMapa" >
                            
                                <div class="panel rounded shadow no-overflow">
                           			   <div class="panel-heading">
	                                       <div class="pull-left">
	                                           <h1 class="panel-title panel-titulo"> 
	                                        		<strong ><spring:message code="lbl.filtro.geral"/> </strong>
	                                           </h1>
	                                       </div><!-- /.pull-left -->   
	                                       <div class="pull-right">
	                                 			<a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>	                                       		
	                                 		</div>                                       
	                                       <div class="clearfix"></div>
	                                 	</div> 
                                    <div class="panel-body">
                                        <div class="form-group no-margin">
                                        	<span class="label label-default"><spring:message code="lbl.estado"/> </span>
                                        	<spring:message code="lbl.hint.imovel.estado" var="hintEstado"/>
                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" title="${hintEstado}">
                                                    <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                    <form:options items="${imovelForm.listaEstados}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.cidade"/> </span>
                                            <spring:message code="lbl.hint.imovel.cidade" var="hintCidade"/>
                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;" title="${hintCidade}">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${imovelForm.listaCidades}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
                                            <spring:message code="lbl.hint.imovel.bairro" var="hintBairro"/>
                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintBairro}">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${imovelForm.listaBairros}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            <br>											  
												  <div class="pull-right">
												  	<spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>
													<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
												  </div><!-- /.pull-right -->            												   
												<br>
                                            
                                        </div><!-- /.form-group -->
                                    </div><!-- /.panel -->
                                </div>
                                
                                <div class="panel rounded shadow no-overflow">                                    
                                    <br>                                    
                                    <div class="panel-body">
                                        <span class="label label-default"><spring:message code="lbl.tipo.imovel"/> </span>
                                        	<spring:message code="lbl.hint.imovel.tipo.imovel" var="hintTipoImovel"/>
								              <form:select id="tipoImovel" path="tipoImovel" class="chosen-select" tabindex="-1" style="display: none;"  title="${hintTipoImovel}">                                
							                        <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                        <form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />								                        
							                 </form:select>	
									     <br> <br>
									     
								     	<span class="label label-default"><spring:message code="lbl.acao.imovel"/> </span>
								     	<spring:message code="lbl.hint.imovel.acao.imovel" var="hintAcaoImovel"/>
								              <form:select id="acao" path="acao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintAcaoImovel}">                                
							                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                    <form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />								                    
							                </form:select>
								        <br> <br>
								           
							            <span class="label label-default"><spring:message code="lbl.buscar.imovel.status.imovel"/> </span>
							            	  <spring:message code="lbl.hint.imovel.perfil.imovel" var="hintPerfilImovel"/>	 
								              <form:select id="perfilImovel" path="perfilImovel" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPerfilImovel}">                                
							                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                    	<form:options items="${listaStatusImovel}" itemValue="identificador" itemLabel="rotulo" />								                    	   
							                </form:select> 
							             <br>
						             	  <div class="pull-right">
						             	  		<spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>
												<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
											  </div><!-- /.pull-right -->            												   
											<br>
						
                                    </div><!-- /.panel-body -->
                                </div>
                                
                                <div class="panel rounded shadow no-overflow">
                                	 <div class="panel-body">
	                                	 <br>
	                                	 <span class="label label-default"><spring:message code="lbl.valor.imovel.minimo"/> </span>                                	                                 	  
		                                 <form:input  id="valorMin" path="valorMin" class="form-control" onkeypress="formatarMoeda(this);" />
		                                 <form:errors id="valorMin" path="valorMin" cssClass="errorEntrada"  />
	
	                                	  <br> <br>
	                                	  
	                                	  <span class="label label-default"><spring:message code="lbl.valor.imovel.maximo"/> </span>
	                                	  <form:input  id="valorMax" path="valorMax" class="form-control" onkeypress="formatarMoeda(this);" />
	                                      <form:errors id="valorMax" path="valorMax" cssClass="errorEntrada"  />
	                                	  
	                                      <br>
						             	  <div class="pull-right">
												<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
											  </div><!-- /.pull-right -->            												   
											<br>		
                                	 </div>
                                </div>  
                                
                                <div class="panel rounded shadow no-overflow">                                    
                                    <br>                                    
                                    <div class="panel-body">
                                    	<span class="label label-default"><spring:message code="lbl.buscar.imovel.quartos.dormitorios"/> </span>
								            <spring:message code="lbl.hint.imovel.quant.quartos" var="hintQuantQuartos"/>
								            <form:select id="quantQuartos" path="quantQuartos" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantQuartos}">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	                
							                </form:select>
							             <br> <br>
							         
							         <span class="label label-default"><spring:message code="lbl.buscar.imovel.garagem"/> </span>
								             <spring:message code="lbl.hint.imovel.quant.garagem" var="hintQuantGaragem"/>
								             <form:select id="quantGaragem" path="quantGaragem" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantGaragem}">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	    
							                </form:select>
							              <br> <br>   
							         
							        <span class="label label-default"><spring:message code="lbl.buscar.imovel.banheiros"/> </span>
								             <spring:message code="lbl.hint.imovel.quant.banheiros" var="hintQuantBanheiros"/>
								             <form:select id="quantBanheiro" path="quantBanheiro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantBanheiros}">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	    
							                </form:select>
							              <br> <br>       
							             
							         <span class="label label-default"><spring:message code="lbl.buscar.imovel.suites"/> </span>
								            <spring:message code="lbl.hint.imovel.quant.suites" var="hintQuantSuites"/>
								            <form:select id="quantSuites" path="quantSuites" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantSuites}">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	                 
							                </form:select>														
									 <br>
									  <div class="pull-right">
										<button type="submit" class="button btn-primary" title="${hintBtnFiltro}" > <spring:message code="lbl.filtrar.geral"/></button>
									  </div><!-- /.pull-right -->            												   
									  <br>
                                    </div>
                               </div>
                      
                            </form:form>
                            
                             <!-- Buscar pelo codigo de identificação imóvel -->
                             
                             <form:form method="POST" id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/filtrarImoveisPerfilUsuarioMapaPorCodigoIdentificacao" >
	                             	<div class="panel rounded shadow no-overflow">
	                                	 <div class="panel-body">
		                                	 <br>
		                                	 <span class="label label-default"><spring:message code="lbl.codigo.identificacao.imovel.resum"/> </span>                                	                                 	  
			                                 <form:input  id="codigoIdentificacao" path="codigoIdentificacao" class="form-control"  />
			                                 <form:errors id="codigoIdentificacao" path="codigoIdentificacao" cssClass="errorEntrada"  />
		                                	  
		                                      <br>
							             	  <div class="pull-right">
													<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
												  </div><!-- /.pull-right -->            												   
												<br>		
	                                	 </div>
	                                </div>
                             
                             </form:form>
                        </div>                      
					                    
                        <div class="col-md-8"> 
                        	
                        	<c:if test="${ empty listaImoveis }">
                            	<div class="panel">	                                
	                                <div class="panel-body">
	                                    <spring:message code="lbl.nenhum.imovel.retornado"/> 
	                                </div><!-- /.panel-body -->
	                            </div><!-- /.panel -->                            
                            </c:if>
                        	
                        	<c:if test="${ not empty listaImoveis }">
		                        <div class="panel">
	                                <div class="panel-heading">
	                                	 <div class="pull-left" style="    margin-top: 7px;">
		                                    <span class="meta-level"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: ${imovelForm.quantRegistros} </span>
		                                </div>
	                                
	                                    <div class="pull-right" style="padding-right:10px;">
	                                    	<spring:message code="lbl.hint.imovel.lista.imoveis" var="hintListaImoveis"/>
                                            <form:form method="POST" id="imovelBuscarImoveiForm" modelAttribute="imovelForm" action="${urlImovel}/filtrarImoveisPerfilUsuario" >
                                            	<button type="submit" class="btn btn-primary btn-block" title="${hintListaImoveis}"><spring:message code="lbl.btn.ver.lista.buscar.imovel"/></button>
                                            </form:form> 
	                                    </div>
	                                    <div class="pull-right">
	                                        
	                                    </div>
	                                    <div class="clearfix"></div>
	                                </div><!-- /.panel-heading -->
	                                 
	                                <div class="panel-body no-padding">
										<div class="form-body">
											 <div class="form-group">
												<div id="result"></div>
												<br/>
												
												<div style="width:700px;height:400px" id="map"></div>
											 </div>
										</div> 									
	                                </div>
	                            </div> 
                            </c:if>     
                                                         
                        </div>                    
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->
			
				<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
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

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

