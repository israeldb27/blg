<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>
<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/mensagem" var="urlMensagem"/>
<spring:url value="/admin" var="urlAdmin"/>
<spring:url value="/mensagemAdmin" var="urlMensagemAdmin"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>
<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioEnum"%>

<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioEnum.values() %>"/>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   

    <!-- START @HEAD -->
 
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
        	$("#idConvidado_"+id).show();      
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });	
    
  }

</script>
	
		<c:import url="../layout-admin/head-layout.jsp"></c:import>
		   
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../layout-admin/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../layout-admin/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">   
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.busca.usuarios"/> </h2>
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">                    
                        <div class="col-lg-3 col-md-3 col-sm-4">
                            <form:form method="POST" id="formBuscarUsuarios" modelAttribute="usuarioForm" action="${urlAdmin}/buscarUsuarios" >
                            
                                <div class="panel rounded shadow no-overflow">
                                	<div class="panel-heading">
                                        <div class="pull-left">
                                            <h1 class="panel-title panel-titulo" > 
                                         		<strong ><spring:message code="lbl.filtro.geral"/> </strong>
                                            </h1>
                                        </div><!-- /.pull-left -->                                        
                                        <div class="clearfix"></div>
                                    </div> 
                                
                           			  <br> 
                                    <div class="panel-body">
                                        <div class="form-group no-margin">
                                        	 <span class="label label-default"><spring:message code="lbl.opcao.tipo.busca.usuario"/></span>
												 <form:select id="opcaoTipoBuscaUsuarios" path="opcaoTipoBuscaUsuarios" class="chosen-select" tabindex="-1" style="display: none;">
													<form:option value="infoPessoais" ><spring:message code="lbl.opcao.tipo.busca.usuario.dados.usuario"/></form:option>                        
													<form:option value="infoPreferenciais" ><spring:message code="lbl.opcao.tipo.busca.usuario.preferencias.imoveis.usuario"/></form:option>                        
												 </form:select> 
                                            </br> </br>
                                        	<span class="label label-default"><spring:message code="lbl.estado"/> </span>
                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;">
                                                    <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                    <form:options items="${usuarioForm.listaEstados}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.cidade"/> </span>
                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${usuarioForm.listaCidades}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${usuarioForm.listaBairros}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            
                                            <span id="idLabelPerfil" class="label label-default"><spring:message code="lbl.filtro.perfil.usuario"/></span>
									            <form:select id="perfil" path="perfil" class="chosen-select" tabindex="-1" style="display: none;">                                
							                       <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												   <form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
							                  </form:select>
									     	<br>
									     	 									  
											  <div class="pull-right">
												<button type="submit" class="button btn-primary" > <spring:message code="lbl.filtrar.geral"/></button>
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
                        		
                        		<c:when test="${not empty listaBuscaUsuarios }">
                        			<div class="pull-left" style="    margin-top: 7px;">
	                                    <span class="meta-level"><strong> <spring:message code="lbl.quant.total.usuarios"/> </strong>: ${quantTotalUsuarios} </span>
	                                </div>
	                                <div class="pull-right" >
	                                     <form:form method="POST" id="buscarUsuariosForm" modelAttribute="usuarioForm" action="${urlAdmin}/ordenarBuscarUsuarios" >         		      	
					                        	<form:select id="opcaoOrdenacao" path="opcaoOrdenacao" class="chosen-select" tabindex="-1" style="display: none;">                                
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
		                                    <form:form method="POST" id="buscarUsuariosPageForm" modelAttribute="usuarioForm" action="${urlAdmin}/buscarUsuarios" >
		                                     	<spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenacao"/>
		                                                <form:select id="opcaoPaginacao" path="opcaoPaginacao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPaginacao}">
		                                                    <form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
		                                                    <form:options items="${usuarioForm.listaPaginas}" itemValue="key" itemLabel="label"/>	                                                    	                                                    
		                                              </form:select>
		                                      </form:form>
		                                </div><!-- /.pull-left -->
	                                </c:if>
	                                <div class="clearfix"></div>
	
	                                <div class="media-list list-search">
	                                    <c:forEach var="usuarioBusca" items="${listaBuscaUsuarios}" varStatus="item">
	                                        <div class="media rounded shadow no-overflow">
	                                            <div class="media-left">
	                                                <a href="${urlAdmin}/detalhesUsuarioAdmin/${usuarioBusca.id}" >                                                                                                     
	                                                    <img src="${context}${usuarioBusca.imagemArquivo}" class="img-responsive" style="width: 260px; height: 195px; alt="admin"/>
	                                                </a>
	                                            </div>
	                                            <div class="media-body">
	                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${usuarioBusca.perfilFmt}</span>
	                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlAdmin}/detalhesUsuarioAdmin/${usuarioBusca.id}" style="color : #9d2428;">${usuarioBusca.nome}</a></h4>
	                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${usuarioBusca.cidade} - ${usuarioBusca.uf}   </h1>
	                                                
	                                                <div class="col-md-5" >     
	                                                
	                                                	<div class="media-body" >
				                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.usuario" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><br>
				                                            <fmt:formatDate value='${usuarioBusca.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em> </br>			                                            		                                            	                                            
				                                        </div>
	                                                   												
	                                                </div>
	                                                
	                                                <div class="col-md-7">
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
	                                                    
	                                                    <a href="${urlAdmin}/visualizarImoveisPerfilUsuarioAdmin/${usuarioBusca.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.visualizar.imoveis.perfil.usuario"/>' ><i class="fa fa-home pull-left" style="color:gray"><font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.visualizar.imoveis.perfil.usuario"/> </font> &nbsp; &nbsp; </i>  </a>
													
													    &nbsp;<a href="${urlMensagemAdmin}/prepararNovaMensagemPorAdmin/${usuarioBusca.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.enviar.mensagem"/>' ><i class="fa fa-envelope-o pull-left" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"><spring:message code="lbl.enviar.mensagem"/> </font>&nbsp;&nbsp; </i> </a>                                                  
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
   

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout-admin/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->
