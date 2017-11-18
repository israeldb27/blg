<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/mensagem" var="urlMensagem"/>
<spring:url value="/contato" var="urlContato"/>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>
<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>
<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>

<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/>
<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   

<script type="text/javascript">

$(document).ready(function() {
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
		$("#buscarUsuariosForm").submit();      
	 });
   
   $('#opcaoPaginacao').change(function () {				
		$("#buscarUsuariosPageForm").submit();      
	 });
   
   $("#btn1").click(function(){
	   alert('clicou');
       $("#idUsuarioBusca").append(" <b>Appended text</b>. </br>");
   });	

    function limpaComboLinha(comboLinha) {
        $(comboLinha).empty();
        $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');
        $(comboLinha).trigger("chosen:updated");
    }
    
    var opcaoBusca = document.getElementById("opcaoTipoBuscaUsuarios").value;
	if ( opcaoBusca == 'infoPreferenciais' ){			
		$('#idCaracateristicasImoveis').show();
		$('#idPerfil').hide();	
	}
	else {
		$('#idCaracateristicasImoveis').hide();
		$('#idPerfil').show();	
	}
    
    $('#opcaoTipoBuscaUsuarios').change(function () {				
		var opcao = document.getElementById("opcaoTipoBuscaUsuarios").value;
		if ( opcao == 'infoPreferenciais' ){			
			$('#idCaracateristicasImoveis').show();
			$('#idPerfil').hide();		
		}
		else {
			$('#idCaracateristicasImoveis').hide();
			$('#idPerfil').show();		
		}
    });	
    
});	

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

            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.busca.usuarios"/> </h2>
                </div><!-- /.header-content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                         <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
                            <c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        <div class="col-lg-3 col-md-3 col-sm-4">                        		
                            <form:form method="POST" id="formBuscarUsuarios" modelAttribute="usuarioForm" action="${urlUsuario}/buscarUsuarios" >                          
                                <div class="panel rounded shadow no-overflow">
                                
                                 <div class="panel-heading">
                                        <div class="pull-left">
                                            <h1 class="panel-title panel-titulo" > 
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
                                        	 </br>
                                        	                                        	 
                                        	<span class="label label-default"><spring:message code="lbl.estado"/> </span>
                                        	<spring:message code="lbl.hint.usuario.estado" var="hintEstado"/>                                            
                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" title="${hintEstado}">
                                                    <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                    <form:options items="${usuarioForm.listaEstados}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.cidade"/> </span>
                                            <spring:message code="lbl.hint.usuario.cidade" var="hintCidade"/>                                            
                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;" title="${hintCidade}">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${usuarioForm.listaCidades}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
                                            <spring:message code="lbl.hint.usuario.bairro" var="hintBairro"/>                                            
                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintBairro}">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${usuarioForm.listaBairros}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            
                                            <span id="idLabelPerfil" class="label label-default"><spring:message code="lbl.filtro.perfil.usuario"/></span>
                                            	<spring:message code="lbl.hint.usuario.perfil.usuario" var="hintPerfilUsuario"/>
									            <form:select id="perfil" path="perfil" class="form-control" title="${hintPerfilUsuario}">                                
							                       <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												   <form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
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
  		
                            </form:form>
                        </div>
                        <div class="col-lg-9 col-md-9 col-sm-8">
                        	<c:choose>
                        		<c:when test="${ empty listaBuscaUsuarios }">
                        			<div class="callout callout-warning">
	                                    <strong><spring:message code="lbl.rel.nenhum.registro"/></strong>                                    
	                                </div>
                        		</c:when>
                        		
                        		<c:when test="${ not empty listaBuscaUsuarios }">
                        			<div class="pull-left col-lg-6" style="padding-top: 9px;">                        				
	                                    <span class="meta-level" style="font-size: 16px;"><strong> <spring:message code="lbl.quant.total.usuarios"/> </strong>: </span> &nbsp; ${usuarioForm.quantRegistros}  
	                                </div>
	                                <div class="pull-right" >
	                                	<spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenacao"/>                                     
	                                     <form:form method="POST" id="buscarUsuariosForm" modelAttribute="usuarioForm" action="${urlUsuario}/ordenarBuscarUsuarios" >         		      	
					                        	<form:select id="opcaoOrdenacao" path="opcaoOrdenacao" class="form-control" title="${hintOrdenacao}">                                
							                        <form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>
							                        <form:option value="maiorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.usuario.cad.mais.recente"/></form:option>
													<form:option value="menorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.usuario.cad.menos.recente"/></form:option>
													<form:option value="nomeImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.usuario.nome.crescente"/></form:option>
													<form:option value="nomeImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.usuario.nome.decrescente"/></form:option>
							                  </form:select>
							              </form:form> 
	                                </div><!-- /.pull-right -->
	                                
	                                <c:if test="${usuarioForm.isVisible()}">
	                                	<div class="pull-right" style="padding-right:10px;">
		                                    <form:form method="POST" id="buscarUsuariosPageForm" modelAttribute="usuarioForm" action="${urlUsuario}/paginarBuscaUsuarios" >
		                                     	<spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenacao"/>
		                                                <form:select id="opcaoPaginacao" path="opcaoPaginacao" class="form-control" title="${hintPaginacao}">
		                                                    <form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
		                                                    <form:options items="${usuarioForm.listaPaginas}" itemValue="key" itemLabel="label"/>	                                                    	                                                    
		                                              </form:select>
		                                      </form:form>
		                                </div><!-- /.pull-left -->
	                                </c:if>
	                                
	                                <div class="clearfix"></div>
	
	                                <div class="media-list list-search">
	                                    <c:forEach var="usuarioBusca" items="${listaBuscaUsuarios}" varStatus="item">
	                                        <div id="idUsuarioBusca" class="media rounded shadow no-overflow">
	                                            <div class="media-left">
	                                                <a href="${urlUsuario}/detalhesUsuario/${usuarioBusca.id}" >                                                                                                     
	                                                    <img src="data:image/jpeg;base64,${usuarioBusca.imagemArquivo}" class="img-responsive" style="width: 260px; height: 225px;" alt="admin"/>
	                                                </a>
	                                            </div>
	                                            <div class="media-body">
	                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${usuarioBusca.perfilFmt}</span>
	                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlUsuario}/detalhesUsuario/${usuarioBusca.id}" style="color : #9d2428;">${usuarioBusca.nome}</a></h4>
	                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${usuarioBusca.cidade} - ${usuarioBusca.uf}   </h1>
	                                                
	                                                <div class="col-md-3" > 
	                                                	<div class="media-body" >
				                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.usuario" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><br>
				                                            <fmt:formatDate value='${usuarioBusca.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em> </br>			                                            		                                            	                                            
				                                        </div>				                                                                                       
	                                                    <br/> <br/>	                                                    		                                                  
	                                                </div>
	                                                
	                                                <div id="teste" class="col-md-8">	                                                	
	                                                
	                                                    <table class="table table-condensed">
	                                                        <tbody style="font-size: 13px;">
	                                                        	<tr>
	                                                                <td class="text-left"> <spring:message code="lbl.total.imoveis"/> </td>
	                                                                <td class="text-right">${usuarioBusca.quantTotalImoveis}</td>
	                                                            </tr>
	                                                            
	                                                            <tr>
				                                                 	<td class="text-left"><spring:message code="lbl.total.contato"/></td>
				                                                 	<td class="text-right">${usuarioBusca.quantTotalContatos}</td>
				                                                </tr>
				                                                
				                                                <tr>
				                                                 	<td class="text-left"><spring:message code="lbl.total.seguidores"/></td>
				                                                 	<td class="text-right">${usuarioBusca.quantTotalSeguidores}</td>
				                                                </tr>
				                                                
				                                                <tr>
				                                                 	<td class="text-left"><spring:message code="lbl.total.recomendacoes"/></td>
				                                                 	<td class="text-right">${usuarioBusca.quantTotalRecomendacoes}</td>
				                                                </tr>				                                                                                                                                          
	                                                        </tbody>
	                                                    </table>
	                                                    
	                                                      <br>	
	                                                      <% if ( request.getSession().getAttribute("acessoValido").equals("S") ) {%>
	                                                      
	                                                      <a href="${urlImovel}/visualizarImoveisPerfilUsuario/${usuarioBusca.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.visualizar.imoveis.perfil.usuario"/>' ><i class="fa fa-home pull-left" style="color:gray"><font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.visualizar.imoveis.perfil.usuario"/> </font> </i>  </a>
	                                                      &nbsp;&nbsp;&nbsp;&nbsp; 	
	                                                      
	                                                      <c:choose>
	                                                      		<c:when test="${((usuarioBusca.id != usuario.id) && (usuarioBusca.isContato == 'N'))}" >   
																	<c:choose>
																		<c:when test="${usuarioBusca.isSeguindo == 'N'}">
																			<spring:message code="lbl.enviar.convite" var="mensagemEnviarConvite"/>
				                                        					<a href="#a" id="idConvite_${usuarioBusca.id}"  onClick="enviarConvite(${usuarioBusca.id})" style="font-size:x-large; color: rgb(99, 110, 123);"  class="dropdown-toggle"  ><i class="fa fa-user-plus"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; "> ${mensagemEnviarConvite} </font> </a>	&nbsp;&nbsp;&nbsp;&nbsp;			                                        					
				                                        					<a href="#a" id="idCancelarConvite_${usuarioBusca.id}" onclick="prepararModalCancelarConvite(${usuarioBusca.id})" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.canceler.enviar.convite"/> </font> </i> </a> &nbsp;&nbsp;&nbsp;&nbsp;
				                                        									                                        		
				                                        					<a href="#a" onClick="iniciarSeguirUsuario(${usuarioBusca.id})" id="idIniciarSeguidor_${usuarioBusca.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.iniciar.seguir.usuario"/>'> <i class="fa fa-list-ul " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.link.iniciar.seguir.usuario"/> </font>  </i> </a> &nbsp;&nbsp;&nbsp; &nbsp;
						                                    				<a href="#a" onClick="cancelarSeguirUsuario(${usuarioBusca.id})" id="idCancelarSeguidor_${usuarioBusca.id}" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.cancelar.seguir.usuario"/>'> <i class="fa fa-outdent " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.link.cancelar.seguir.usuario"/> </font>  </i></a>				                                        		
																		</c:when>
																		
																		<c:when test="${usuarioBusca.isSeguindo == 'S'}">																																						
																			<spring:message code="lbl.enviar.convite" var="mensagemEnviarConvite"/>
																			<a href="#a" id="idConvite_${usuarioBusca.id}"  onClick="enviarConvite(${usuarioBusca.id})" style="font-size:x-large; color: rgb(99, 110, 123); display: none;"  class="dropdown-toggle"  ><i class="fa fa-user-plus"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; "> ${mensagemEnviarConvite} </font> </a> &nbsp;&nbsp;&nbsp;&nbsp;
																			<a href="#a" id="idCancelarConvite_${usuarioBusca.id}" onclick="prepararModalCancelarConvite(${usuarioBusca.id})" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.canceler.enviar.convite"/> </font> </i> </a> &nbsp;&nbsp;&nbsp;&nbsp;
																			
																			<a href="#a" onClick="cancelarSeguirUsuario(${usuarioBusca.id})" id="idCancelarSeguidor_${usuarioBusca.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.cancelar.seguir.usuario"/>'> <i class="fa fa-outdent " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.link.cancelar.seguir.usuario"/> </font> </i> </a>   &nbsp;&nbsp;&nbsp;&nbsp;	                                    			
																			<a href="#a" onClick="iniciarSeguirUsuario(${usuarioBusca.id})" id="idIniciarSeguidor_${usuarioBusca.id}" style="font-size:x-large;display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.iniciar.seguir.usuario"/>'> <i class="fa fa-list-ul " style="color:gray">  <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.link.iniciar.seguir.usuario"/> </font>   </i></a>	&nbsp;&nbsp;&nbsp;&nbsp;																		
																		</c:when>
																	</c:choose>		                                                      			
	                                                      		</c:when>
	                                                      		
	                                                      		<c:when test="${((usuarioBusca.id != usuario.id) && (usuarioBusca.isContato == 'C'))}" >	                                                      			
																	<a href="#a" onClick="iniciarSeguirUsuario(${usuarioBusca.id})" id="idIniciarSeguidor_${usuarioBusca.id}" style="font-size:x-large; color: rgb(99, 110, 123); display: none;" class="meta-action" title='<spring:message code="lbl.link.iniciar.seguir.usuario"/>'> <i class="fa fa-list-ul " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.link.iniciar.seguir.usuario"/> </font> </i> </a> &nbsp;&nbsp;&nbsp;&nbsp;
						                                    		<a href="#a" onClick="cancelarSeguirUsuario(${usuarioBusca.id})" id="idCancelarSeguidor_${usuarioBusca.id}" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.cancelar.seguir.usuario"/>'> <i class="fa fa-outdent " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.link.cancelar.seguir.usuario"/> </font>  </i></a>  &nbsp;&nbsp;&nbsp;&nbsp;
																	
																	<a href="#a" id="idCancelarConvite_${usuarioBusca.id}" onclick="prepararModalCancelarConvite(${usuarioBusca.id})" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.canceler.enviar.convite"/> </font>  </i> </a>
																	<a href="#a" id="idConvite_${usuarioBusca.id}" onclick="enviarConvite(${usuarioBusca.id})" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.enviar.convite"/>' ><i class="fa fa-user-plus " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.enviar.convite"/> </font> </i></a>																	
	                                                      		</c:when>
	                                                      		
	                                                      		<c:when test="${((usuarioBusca.id != usuario.id) && (usuarioBusca.isContato == 'O'))}" >
	                                                      			<a href="#a" id="idCancelarContato_${usuarioBusca.id}" onclick="prepararModalCancelarContato(${usuarioBusca.id})" style="font-size:x-large; color: rgb(99, 110, 123); " class="meta-action" title='<spring:message code="lbl.canceler.contato"/>' ><i class="fa fa-user-times " style="color:gray"><font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.canceler.contato"/> </font> </i> </a>  &nbsp;&nbsp;&nbsp;&nbsp;
	                                                      			
																	<a href="#a" onClick="iniciarSeguirUsuario(${usuarioBusca.id})" id="idIniciarSeguidor_${usuarioBusca.id}" style="font-size:x-large; color: rgb(99, 110, 123); display: none;" class="meta-action" title='<spring:message code="lbl.link.iniciar.seguir.usuario"/>'> <i class="fa fa-list-ul " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.link.iniciar.seguir.usuario"/> </font>  </i> </a>   &nbsp;&nbsp;&nbsp;&nbsp;
						                                    		<a href="#a" onClick="cancelarSeguirUsuario(${usuarioBusca.id})" id="idCancelarSeguidor_${usuarioBusca.id}" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.link.cancelar.seguir.usuario"/>'> <i class="fa fa-outdent " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.link.cancelar.seguir.usuario"/> </font> </i></a>  &nbsp;&nbsp;&nbsp;&nbsp;
						                                    		
						                                    		<a href="#a" id="idCancelarConvite_${usuarioBusca.id}" onclick="prepararModalCancelarConvite(${usuarioBusca.id})" style="font-size:x-large; color: rgb(99, 110, 123); display: none;" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.canceler.enviar.convite"/> </font>  </i> </a>
																	<a href="#a" id="idConvite_${usuarioBusca.id}" onclick="enviarConvite(${usuarioBusca.id})" style="font-size:x-large; display: none; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.enviar.convite"/>' ><i class="fa fa-user-plus " style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; "> <spring:message code="lbl.enviar.convite"/> </font> </i></a>																	
	                                                      		</c:when>	                                                      
	                                                      </c:choose>	                                                      					                                         
	                                                    <% } %>
	                                                </div>
	                                            </div>
	                                        </div>
	                                    </c:forEach>
	                                </div>
                        		</c:when>
                        	</c:choose>                       
                        </div>
                    </div>
                </div><!-- /.body-content -->
            </section><!-- /#page-content -->
         </section><!-- /#wrapper -->
         
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
         
         
         <!-- Start content modal Usuario Detalhes-->
			<c:import url="../ajuda/imovelDetalhesModal.jsp"></c:import>																				
		<!-- End content  modal Usuario Detalhes -->
			
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
         

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->
