<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.FaixaSalarialEnum"%>
<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>
<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioSemComumlEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoContatoEnum"%>

<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>
<c:set var="listaFaixaSalarial" value="<%= FaixaSalarialEnum.values() %>"/>
<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/> 
<c:set var="listaPerfilUsuarioSemComum" value="<%= PerfilUsuarioSemComumlEnum.values() %>"/>
<c:set var="listaTipoContato" value="<%= TipoContatoEnum.values() %>"/>


<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/relatorio/buscarRelatorio" var="urlBuscarRelatorios"/>
<spring:url value="/relatorio" var="urlRelatorio"/>
<spring:url value="/relatorio/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/relatorio/buscarBairros" var="urlBuscarBairros"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<script type="text/javascript">

$(document).ready(function() {
	$('#tipo').change(function () {
        var comboPai = '#tipo';
        var comboFilha = '#nome';        
        limpaComboLinha(comboFilha);        
        recuperaRelatorios();
    });
	
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

    function limpaComboLinha(comboLinha) {
        $(comboLinha).empty();
        $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');       
        $(comboLinha).trigger("chosen:updated");
    }
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

function recuperaRelatorios(){
    var parametro1 = $("#tipo").val();
    $.get("${urlBuscarRelatorios}/"+parametro1, function(data){
        jQuery.each(data, function(key, value) {
            if(value.label == null){            	
                return;            }            
            $("#nome").append("<option value='"+value.key+"'>"+value.label+"</option>");
        });
    });
}

function mostrarModal(campo){
	var p = $("#item").val();	
	
	if ( campo == 'dataInicio'){
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.relatorio.data.inicio'/>");
		
		if ((p == 'sobreEstados') || 
			(p == 'sobreCidades') || 
			(p == 'sobreBairros') ||
			(p == 'tipoImoveisMaisProcuradoPorLocalizacao') || 
	    	(p == 'variacaoPrecosPorTipoImovelPorLocalizacao') || 
	    	(p == 'quantImoveisPorLocalizacaoAcaoTipoImovel')){
			
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.inicio'/>");
		}
		else if (p == 'imoveisMaisVisualizados') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.inicio.imoveisMaisVisualizados'/>");
		}
		else if (p == 'imoveisMaisPropostados') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.inicio.imoveisMaisPropostados'/>");
		}
		else if (p == 'imoveisMaisComentados') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.inicio.imoveisMaisComentados'/>");
		}
		else if (p == 'imoveisMaisAdotadosInteressados') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.inicio.imoveisMaisAdotadosInteressados'/>");
		}
		else if (p == 'usuariosMaisParceriasAceitas') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.inicio.usuariosMaisParceriasAceitas'/>");
		}
		else if (p == 'usuariosMaisIntermediacoesAceitas') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.inicio.usuariosMaisIntermediacoesAceitas'/>");
		}
		else if (p == 'usuariosImoveisMaisVisualizados') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.inicio.usuariosImoveisMaisVisualizados'/>");
		}
		else if (p == 'usuariosImoveisMaisFavoritos') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.inicio.usuariosImoveisMaisFavoritos'/>");
		}
	}
	else if ( campo == 'dataFim'){
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.relatorio.data.fim'/>");
		
		if ((p == 'sobreEstados') || 
			(p == 'sobreCidades') || 
			(p == 'sobreBairros') ||
			(p == 'tipoImoveisMaisProcuradoPorLocalizacao') || 
	    	(p == 'variacaoPrecosPorTipoImovelPorLocalizacao') || 
	    	(p == 'quantImoveisPorLocalizacaoAcaoTipoImovel')){
			
				$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.fim'/>");
			}
		else if (p == 'imoveisMaisVisualizados') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.fim.imoveisMaisVisualizados'/>");
		}
		else if (p == 'imoveisMaisPropostados') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.fim.imoveisMaisPropostados'/>");
		}
		else if (p == 'imoveisMaisComentados') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.fim.imoveisMaisComentados'/>");
		}
		else if (p == 'imoveisMaisAdotadosInteressados') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.fim.imoveisMaisAdotadosInteressados'/>");
		}
		else if (p == 'usuariosMaisParceriasAceitas') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.fim.usuariosMaisParceriasAceitas'/>");
		}
		else if (p == 'usuariosMaisIntermediacoesAceitas') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.fim.usuariosMaisIntermediacoesAceitas'/>");
		}
		else if (p == 'usuariosImoveisMaisVisualizados') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.fim.usuariosImoveisMaisVisualizados'/>");
		}
		else if (p == 'usuariosImoveisMaisFavoritos') {
			$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.fim.usuariosImoveisMaisFavoritos'/>");
		}		 
	}
	else if ( campo == 'opcaoRelatorioSobreLocalidade'){
		
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.rel.opcao.relatorio.sobre.localidade'/>");	
		
		if ( p == 'sobreEstados'){
			$('#msgModal').html("<spring:message code='lbl.desc.modal.rel.opcao.relatorio.sobre.localidade.estados'/>");
		}
		else if ( p == 'sobreCidades'){
			$('#msgModal').html("<spring:message code='lbl.desc.modal.rel.opcao.relatorio.sobre.localidade.cidades'/>");
		}
		else if ( p == 'sobreBairros'){
			$('#msgModal').html("<spring:message code='lbl.desc.modal.rel.opcao.relatorio.sobre.localidade.bairros'/>");
		}
		
	}
	else if ( campo == 'perfilUsuario'){
		$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.data.fim'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.perfil.usuario'/>");	
	}
	else if ( campo == 'opcaoFiltroContato'){
		$('#msgModal').html("<spring:message code='lbl.modal.desc.relatorio.tipo.contato'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.relatorio.tipo.contato'/>");	
	}
	
	$("#idModalItem").modal("show");
}

function mostrarModalInfo(){
	var p = $("#item").val();	
	var pFmt = $("#itemFmt").val();	
	
	$('#msgModalFuncionalidade').html(pFmt);
	
	if (p == 'imoveisMaisVisualizados') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.imoveisMaisVisualizados'/>");
	}
	else if (p == 'imoveisMaisPropostados') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.imoveisMaisPropostados'/>");
	}
	else if (p == 'imoveisMaisComentados') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.imoveisMaisComentados'/>");
	}
	else if (p == 'imoveisMaisAdotadosInteressados') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.imoveisMaisAdotadosInteressados'/>");
	}
	else if (p == 'usuariosMaisParceriasAceitas') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.usuariosMaisParceriasAceitas'/>");
	}
	else if (p == 'usuariosMaisIntermediacoesAceitas') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.usuarioMaisIntermediacoesAceitas'/>");
	}
	else if (p == 'usuariosImoveisMaisVisualizados') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.usuariosImoveisMaisVisualizados'/>");
	}
	else if (p == 'usuariosImoveisMaisFavoritos') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.usuariosImoveisMaisFavoritos'/>");
	}
	else if (p == 'sobreEstados') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.sobreEstados'/>");
	}
	else if (p == 'sobreCidades') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.sobreCidades'/>");
	}
	else if (p == 'sobreBairros') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.sobreBairros'/>");
	}
	else if (p == 'tipoImoveisMaisProcuradoPorLocalizacao') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.tipoImoveisMaisProcuradoPorLocalizacao'/>");
	}
	else if (p == 'variacaoPrecosPorTipoImovelPorLocalizacao') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.variacaoPrecosPorTipoImovelPorLocalizacao'/>");
	}
	else if (p == 'quantImoveisPorLocalizacaoAcaoTipoImovel') {
		$('#msgModal').html("<spring:message code='msg.ajuda.modal.funcionalidades.descricao.rel.quantImoveisPorLocalizacaoAcaoTipoImovel'/>");
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
					<h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.relatorios"/><span>${relatorioForm.itemFmt}</span></h2>					
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->                
                <div class="body-content animated fadeIn">

                    <div class="row">
                    	<% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
							<c:import url="../../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        
                         <div class="col-md-9" >
	                        <form:form method="POST" class="form-horizontal form-bordered col-sm-3" id="relatorioForm" modelAttribute="relatorioForm" action="${urlRelatorio}/voltarInicioRelatorio" >
	                        	<button type="submit" class="btn btn-primary btn-block" style="width: 40%;"><spring:message code="btn.rel.voltar.filtro.relatorio"/></button>                        	
	                        </form:form> 
                        </div>
                        
                        <div class="col-md-8" >
                        		                	
                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.filtro.geral"/> </h3>
                                    </div><!-- /.pull-left -->
                                    <div class="pull-right">
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModalInfo()" style=""><i class="fa fa-question" ></i></a>
                                    </div><!-- /.pull-right -->    
                                    
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                <br>
                                    <form:form method="POST" class="form-horizontal" id="relatorioForm" modelAttribute="relatorioForm" action="${urlRelatorio}/gerarRelatorio" >
                                      <div class="form-body">
                                    	<form:hidden id="path" path="path" value="${relatorioForm.path}"  />
                                        <form:hidden id="item" path="item" value="${menu}"  />
                                        <form:hidden id="itemFmt" path="item" value="${relatorioForm.itemFmt}"  />
                                        
                                     <!-- INICIO PRIMEIRO GRUPO DE RELATORIOS -->
                                     	  <c:choose>
	                                     	<c:when test="${((menu == 'sobreEstados') || (menu == 'sobreCidades') || (menu == 'sobreBairros')) }">
	                                     		 <div class="form-group no-margin">								       				   		
						       				   		<div class="row">
						       				   		
						       				   		    <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.acao.imovel"/> </span>
															<form:select id="acao" path="acao" class="chosen-select" tabindex="-1" style="display: none;">                                
															    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />
															</form:select> 			
															<form:errors id="acao" path="acao" cssClass="errorEntrada"  />
														 </div>						       				   			
						       				   			
						       				   			<div class="col-md-3">
                                        					     <span class="label label-default"><spring:message code="lbl.relatorio.data.inicio"/> &nbsp;
                                        					      	<a href="#a" onClick="mostrarModal('dataInicio')" class="btn btn-sm"   style="">
                                        					   		   	<i class="fa fa-question" ></i>
                                        					      	</a>
                                        					      </span>
					                                        	 <form:input id="dataInicio" path="dataInicio" class="form-control" onKeyUp="mascaraData(this);" maxlength="10" />						                                			 
					                                			 <form:errors id="dataInicio" path="dataInicio" cssClass="errorEntrada"  />
	                                                     </div>	    
														 
														 <c:if test="${((menu == 'sobreCidades') || (menu == 'sobreBairros'))}">        	
			                                       			<div class="col-md-3">
																<span class="label label-default"><spring:message code="lbl.estado"/> </span>
																<form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" >                                
																	<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																	<form:options items="${relatorioForm.listaEstados}" itemValue="key" itemLabel="label"/>
															    </form:select>
															 </div>
			                                       		</c:if>	
			                                       		
			                                       		 <div class="col-md-3">  
	                                                    	<span class="label label-default"><spring:message code="lbl.relatorio.quant.registros"/> </span>
	                                                    	<form:select id="quantMaxRegistrosResultado" path="quantMaxRegistrosResultado" class="chosen-select" tabindex="-1" style="display: none;">								                                		
																<form:option value="10">10</form:option>
																<form:option value="20">20</form:option>																		
																<form:option value="30">30</form:option>																																	      
											                </form:select>							                                			
											                <form:errors id="quantMaxRegistrosResultado" path="quantMaxRegistrosResultado" cssClass="errorEntrada"  />
			                                             </div>													 
	                                                </div>   
	                                                
	                                                <br>
	                                                
	                                                <div class="row">	                                                
	                                                   <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.tipo.imovel"/> </span>
															<form:select id="tipoImovel" path="tipoImovel" class="chosen-select" tabindex="-1" style="display: none;" >                                
																<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
																<form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />
															 </form:select>
															 <form:errors id="tipoImovel" path="tipoImovel" cssClass="errorEntrada"  />
														</div>
	                                                	
	                                                	<div class="col-md-3">
	                                                         <span class="label label-default"><spring:message code="lbl.relatorio.data.fim"/> &nbsp;
	                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('dataFim')"  style="">
                                       					   		   	<i class="fa fa-question" ></i>
                                       					      	</a>
	                                                         </span>
				                                        	 <form:input id="dataFim" path="dataFim" class="form-control" onKeyUp="mascaraData(this);"  maxlength="10" />
				                                			 <form:errors id="dataFim" path="dataFim" cssClass="errorEntrada"  />
	                                                     </div>                                                
	                                                
														<c:if test="${menu == 'sobreBairros'}">        	
			                                       			<div class="col-md-3">
																<span class="label label-default"><spring:message code="lbl.cidade"/> </span>
																<form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;">                                
																	<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																	<form:options items="${relatorioForm.listaCidades}" itemValue="key" itemLabel="label"/>
															    </form:select>
														    </div>
			                                       		</c:if>	                                                
	                                                </div>
	                                                
	                                                <br>
	                                                
	                                                <div class="row">
                                                	  <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.status.imovel"/> </span>
															<form:select id="perfilImovel" path="perfilImovel" class="chosen-select" tabindex="-1" style="display: none;">                                
																<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>   
																<form:options items="${listaStatusImovel}" itemValue="identificador" itemLabel="rotulo" />
															</form:select> 			
															<form:errors id="acao" path="acao" cssClass="errorEntrada"  />
													    </div>
	                                                
	                                                	<div class="col-md-3">
	                                                         <span class="label label-default"><spring:message code="lbl.rel.opcao.relatorio.sobre.localidade"/>  &nbsp;
	                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('opcaoRelatorioSobreLocalidade')"  style="">
                                       					   		   	<i class="fa fa-question" ></i>
                                       					      	</a>
	                                                         </span>
				                                        	 <form:select id="opcaoRelatorioSobreLocalidade" path="opcaoRelatorioSobreLocalidade" class="chosen-select" tabindex="-1" style="display: none;">
																		<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                       
																		<form:option value="B"><spring:message code="lbl.rel.opcao.relatorio.sobre.localidade.mais.baratos"/></form:option>                       
																		<form:option value="C"><spring:message code="lbl.rel.opcao.relatorio.sobre.localidade.mais.caros"/></form:option>
																</form:select>    
																<form:errors id="opcaoRelatorioSobreLocalidade" path="opcaoRelatorioSobreLocalidade" cssClass="errorEntrada"  />
	                                                     </div>	                                                      	                                            
	                                                </div>
	                                     	</c:when>	   
	                                     	
	                                     	<c:when test="${((menu == 'variacaoPrecosPorTipoImovelPorLocalizacao') ||
                                                             (menu == 'quantImoveisPorLocalizacaoAcaoTipoImovel'))}">
                                                    
                                                    <div class="row">
						       				   			 <div class="col-md-3">
	                                                    	<span class="label label-default"><spring:message code="lbl.estado"/>  </span>
	                                                    	<form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" >                                
																<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${relatorioForm.listaEstados}" itemValue="key" itemLabel="label"/>
														    </form:select>
	                                                    </div>
	                                                    
	                                                    <div class="col-md-3">
		                                                  	 <span class="label label-default"><spring:message code="lbl.tipo.imovel"/> </span>
		                                                  	 <form:select id="tipoImovel" path="tipoImovel" class="chosen-select" tabindex="-1" style="display: none;" >                                
											                        <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
																	<form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />
											                 </form:select>
											                 <form:errors id="tipoImovel" path="tipoImovel" cssClass="errorEntrada"  />
		                                                </div>
		                                                
		                                                <div class="col-md-3">
	                                                         <span class="label label-default"><spring:message code="lbl.relatorio.data.inicio"/> &nbsp;
	                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('dataInicio')" style="">
                                       					   		   	<i class="fa fa-question" ></i>
                                       					      	</a>	                                                         
	                                                          </span>
				                                        	 <form:input id="dataInicio" path="dataInicio" class="form-control" onKeyUp="mascaraData(this);"  maxlength="10"/>
				                                			 <form:errors id="dataInicio" path="dataInicio" cssClass="errorEntrada"  />
	                                                     </div>		                                                 
			                                             
						       				   		</div>
						       				   		</br>
						       				   		
						       				   		<div class="row">
						       				   			<div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.cidade"/> </span>
															<form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;">                                
																<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${relatorioForm.listaCidades}" itemValue="key" itemLabel="label"/>
														    </form:select>
													    </div>
													    
													    <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.status.imovel"/> </span>
															<form:select id="perfilImovel" path="perfilImovel" class="chosen-select" tabindex="-1" style="display: none;">                                
																<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>   
																<form:options items="${listaStatusImovel}" itemValue="identificador" itemLabel="rotulo" />
															</form:select> 			
															<form:errors id="acao" path="acao" cssClass="errorEntrada"  />
													    </div>
													    
													    <div class="col-md-3">			                                                    	
	                                                    	<span class="label label-default"><spring:message code="lbl.relatorio.data.fim"/> &nbsp;
	                                                         	<a href="#a" class="btn btn-sm"  onClick="mostrarModal('dataFim')" style="">
                                       					   		   	<i class="fa fa-question" ></i>
                                       					      	</a>
	                                                    	</span>
	                                                    	<form:input path="dataFim" class="form-control"  id="dataFim" onKeyUp="mascaraData(this);"  maxlength="10"/>
				                                			<form:errors id="dataFim" path="dataFim" cssClass="errorEntrada"  />			
		                                                </div>	
						       				   		</div>
						       				   		</br>
						       				   		
						       				   		<div class="row">
						       				   			<div class="col-md-3">
	                                                    	<span class="label label-default"><spring:message code="lbl.bairro"/> </span>
	                                                    	<form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;">                                
																	<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																	<form:options items="${relatorioForm.listaBairros}" itemValue="key" itemLabel="label"/>
															 </form:select>
	                                                    </div>	
	                                                    
	                                                    <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.acao.imovel"/> </span>
															<form:select id="acao" path="acao" class="chosen-select" tabindex="-1" style="display: none;">                                
															    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />
															</form:select> 			
															<form:errors id="acao" path="acao" cssClass="errorEntrada"  />
														 </div>
	                                                    
	                                                    <div class="col-md-3">  
	                                                    	<span class="label label-default"><spring:message code="lbl.relatorio.quant.registros"/> </span>
	                                                    	<form:select id="quantMaxRegistrosResultado" path="quantMaxRegistrosResultado" class="chosen-select" tabindex="-1" style="display: none;">								                                		
																<form:option value="10">10</form:option>
																<form:option value="20">20</form:option>																		
																<form:option value="30">30</form:option>																																	      
											                </form:select>							                                			
											                <form:errors id="quantMaxRegistrosResultado" path="quantMaxRegistrosResultado" cssClass="errorEntrada"  />
			                                             </div>
						       				   		</div>
                                             
                                            </c:when>  
                                            
                                            <c:when test="${(menu == 'tipoImoveisMaisProcuradoPorLocalizacao')}">                                  	
	                                     	 	 <div class="row">
						       				   			 <div class="col-md-3">
	                                                    	<span class="label label-default"><spring:message code="lbl.estado"/>  </span>
	                                                    	<form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" >                                
																<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${relatorioForm.listaEstados}" itemValue="key" itemLabel="label"/>
														    </form:select>
	                                                    </div>
	                                                    
	                                                    <div class="col-md-3">
	                                                    	<span class="label label-default"><spring:message code="lbl.status.imovel"/> </span>
	                                                    	<form:select id="perfilImovel" path="perfilImovel" class="chosen-select" tabindex="-1" style="display: none;">                                
																<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>   
																<form:options items="${listaStatusImovel}" itemValue="identificador" itemLabel="rotulo" />
															</form:select> 			
											                <form:errors id="perfilImovel" path="perfilImovel" cssClass="errorEntrada"  />
	                                                    </div>
	                                                    
	                                                    <div class="col-md-3">
	                                                    	<span class="label label-default"><spring:message code="lbl.relatorio.tipo.contato"/> &nbsp;
	                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('opcaoFiltroContato')" style="">
                                       					   		   	<i class="fa fa-question" ></i>
                                       					      	</a> 
                                       					      </span>
	                                                    	<form:select id="opcaoFiltroContato" path="opcaoFiltroContato" class="chosen-select" tabindex="-1" style="display: none;">
	                                                    		<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
	                                                    		<form:options items="${listaTipoContato}" itemValue="identificador" itemLabel="rotulo" />										      
											                </form:select>							                                			
											                <form:errors id="opcaoFiltroContato" path="opcaoFiltroContato" cssClass="errorEntrada"  />
			                                             </div>
	                                                    
		                                                <div class="col-md-3">
	                                                         <span class="label label-default"><spring:message code="lbl.relatorio.data.inicio"/> &nbsp;
	                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('dataInicio')" style="">
                                       					   		   	<i class="fa fa-question" ></i>
                                       					      	</a>
	                                                         </span>
				                                        	 <form:input id="dataInicio" path="dataInicio" class="form-control" onKeyUp="mascaraData(this);"  maxlength="10"/>
				                                			 <form:errors id="dataInicio" path="dataInicio" cssClass="errorEntrada"  />
	                                                     </div>				                                             
						       				   	  </div>
						       				      </br>
						       				      
						       				      <div class="row">
						       				      		<div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.cidade"/> </span>
															<form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;">                                
																<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${relatorioForm.listaCidades}" itemValue="key" itemLabel="label"/>
														    </form:select>
													    </div>
													    
													    <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.acao.imovel"/> </span>
															<form:select id="acao" path="acao" class="chosen-select" tabindex="-1" style="display: none;">                                
															    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />
															</form:select> 			
															<form:errors id="acao" path="acao" cssClass="errorEntrada"  />
														 </div>
														 
														  <div class="col-md-3">
			                                                  	<span class="label label-default"><spring:message code="lbl.perfil.usuario"/> &nbsp;
		                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('perfilUsuario')" style="">
	                                       					   		   	<i class="fa fa-question" ></i>
	                                       					      	</a>
			                                                  	</span>
			                                                  	  <form:select id="perfilUsuario" path="perfilUsuario" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPerfilDono}">                                
												                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																		<form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
												                </form:select> 
															     <form:errors id="perfilUsuario" path="perfilUsuario" cssClass="errorEntrada"  />
			                                              </div>
			                                              
			                                              <div class="col-md-3">			                                                    	
		                                                    	<span class="label label-default"><spring:message code="lbl.relatorio.data.fim"/> &nbsp;
		                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('dataFim')" style="">
	                                       					   		   	<i class="fa fa-question" ></i>
	                                       					      	</a>
		                                                    	</span>
		                                                    	<form:input path="dataFim" class="form-control"  id="dataFim" onKeyUp="mascaraData(this);"  maxlength="10"/>
					                                			<form:errors id="dataFim" path="dataFim" cssClass="errorEntrada"  />			
		                                                  </div>						       				      
						       				        </div>
						       				        </br>
						       				        
						       				        <div class="row">
						       				        	<div class="col-md-3">
	                                                    	<span class="label label-default"><spring:message code="lbl.bairro"/> </span>
	                                                    	<form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;">                                
																	<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																	<form:options items="${relatorioForm.listaBairros}" itemValue="key" itemLabel="label"/>
															 </form:select>
	                                                    </div>	
	                                                    
	                                                    <div class="col-md-3">  
	                                                    	<span class="label label-default"><spring:message code="lbl.relatorio.quant.registros"/> </span>
	                                                    	<form:select id="quantMaxRegistrosResultado" path="quantMaxRegistrosResultado" class="chosen-select" tabindex="-1" style="display: none;">								                                		
																<form:option value="10">10</form:option>
																<form:option value="20">20</form:option>																		
																<form:option value="30">30</form:option>																																	      
											                </form:select>							                                			
											                <form:errors id="quantMaxRegistrosResultado" path="quantMaxRegistrosResultado" cssClass="errorEntrada"  />
			                                             </div>
						       				        </div>
			                                
			                                </c:when> 			                                        	
	                                     	
	                                     	 <c:when test="${((menu == 'imoveisMaisVisualizados') 				    || 
											       			  (menu == 'imoveisMaisPropostados') 				    || 
											       			  (menu == 'imoveisMaisComentados') 			        ||
											       			  (menu == 'imoveisMaisAdotadosInteressados') 	        ||									       			  
											       			  (menu == 'usuariosMaisParceriasAceitas') 	            ||
											       			  (menu == 'usuariosMaisIntermediacoesAceitas')         ||
											       			  (menu == 'usuariosImoveisMaisVisualizados')           ||
											       			  (menu == 'usuariosImoveisMaisFavoritos'))}">
										       				  
										       		<div class="form-group no-margin">								       				   		
								       				   		<div class="row">
								       				   			 <div class="col-md-3">
			                                                    	<span class="label label-default"><spring:message code="lbl.estado"/>  </span>
			                                                    	<form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" >                                
																		<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																		<form:options items="${relatorioForm.listaEstados}" itemValue="key" itemLabel="label"/>
																    </form:select>
			                                                    </div>
			                                                    
			                                                    <div class="col-md-3">
			                                                    	<span class="label label-default"><spring:message code="lbl.relatorio.tipo.contato"/> &nbsp;
			                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('opcaoFiltroContato')" style="">
		                                       					   		   	<i class="fa fa-question" ></i>
		                                       					      	</a>
			                                                    	</span>
			                                                    	<form:select id="opcaoFiltroContato" path="opcaoFiltroContato" class="chosen-select" tabindex="-1" style="display: none;">
			                                                    		<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
			                                                    		<form:options items="${listaTipoContato}" itemValue="identificador" itemLabel="rotulo" />										      
													                </form:select>							                                			
													                <form:errors id="opcaoFiltroContato" path="opcaoFiltroContato" cssClass="errorEntrada"  />
					                                             </div>
					                                             
					                                             <div class="col-md-3">
			                                                         <span class="label label-default"><spring:message code="lbl.relatorio.data.inicio"/> &nbsp;
			                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('dataInicio')" style="">
		                                       					   		   	<i class="fa fa-question" ></i>
		                                       					      	</a>
		                                       					      </span>
						                                        	 <form:input id="dataInicio" path="dataInicio" class="form-control" onKeyUp="mascaraData(this);"  maxlength="10"/>
						                                			 <form:errors id="dataInicio" path="dataInicio" cssClass="errorEntrada"  />
			                                                     </div>	
					                                          
								       				   		</div>
								       				   		</br>
								       				   		<div class="row">
								       				   		
								       				   			<div class="col-md-3">
			                                                    	<span class="label label-default"><spring:message code="lbl.cidade"/> </span>
			                                                    	<form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;">                                
																		<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																		<form:options items="${relatorioForm.listaCidades}" itemValue="key" itemLabel="label"/>
																    </form:select>
			                                                    </div>
											       			  	     
										       			  	     <div class="col-md-3">
			                                                         <span id="idLabelPerfil" class="label label-default"><spring:message code="lbl.filtro.perfil.usuario"/> &nbsp;
			                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('perfilUsuario')" style="">
		                                       					   		   	<i class="fa fa-question" ></i>
		                                       					      	</a>			                                                         
			                                                         </span>
					                                            	 <spring:message code="lbl.hint.usuario.perfil.usuario" var="hintPerfilUsuario"/>
														             <form:select id="perfilUsuario" path="perfilUsuario" class="chosen-select" tabindex="-1" style="display: none;">                                
												                       <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												                       	
												                       	<c:choose>
																     		<c:when test="${((menu == 'usuariosMaisIntermediacoesAceitas') ||
																     		                 (menu == 'usuariosMaisParceriasAceitas')) }">																	       			             
																       			     <form:options items="${listaPerfilUsuarioSemComum}" itemValue="identificador" itemLabel="rotulo" />        
																     		</c:when> 

																			<c:otherwise>																	       			             
																       			     <form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />        
																     		</c:otherwise> 
												                        </c:choose>	
												                    </form:select>
			                                                     </div>															       	
															  
															   <div class="col-md-3">			                                                    	
				                                                    	<span class="label label-default"><spring:message code="lbl.relatorio.data.fim"/> &nbsp;
				                                                         	<a href="#a" class="btn btn-sm" onClick="mostrarModal('dataFim')" style="">
			                                       					   		   	<i class="fa fa-question" ></i>
			                                       					      	</a>				                                                    	
				                                                    	</span>
				                                                    	<form:input path="dataFim" class="form-control"  id="dataFim" onKeyUp="mascaraData(this);"  maxlength="10"/>
							                                			<form:errors id="dataFim" path="dataFim" cssClass="errorEntrada"  />			
				                                                </div>		                                                    		                                                    
								       				   	  </div>
								       				   	
								       				   	  </br>
								       				   	  
						                                	<div class="row">								                                	
						                                		<div class="col-md-3">
			                                                    	<span class="label label-default"><spring:message code="lbl.bairro"/> </span>
			                                                    	<form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;">                                
																			<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																			<form:options items="${relatorioForm.listaBairros}" itemValue="key" itemLabel="label"/>
																	 </form:select>
			                                                    </div>					                                                    
			                                                    											       			  	
											       			  	 <div class="col-md-3">  
			                                                    	<span class="label label-default"><spring:message code="lbl.relatorio.quant.registros"/> </span>
			                                                    	<form:select id="quantMaxRegistrosResultado" path="quantMaxRegistrosResultado" class="chosen-select" tabindex="-1" style="display: none;">								                                		
																		<form:option value="10">10</form:option>
																		<form:option value="20">20</form:option>																		
																		<form:option value="30">30</form:option>																																	      
													                </form:select>							                                			
													                <form:errors id="quantMaxRegistrosResultado" path="quantMaxRegistrosResultado" cssClass="errorEntrada"  />
					                                             </div>
			                                                    	                                                    					                                                    								                                	
						                                	</div>        				   		
								       				   	</div> 
	                                     	  </c:when>	                                     	
	                                     </c:choose>                                  
                                       </div>                                        
                                       
                                       <div class="form-group">             			
                                			 <label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
                                            <div class="col-sm-7">
                                            	<br>                                            	
                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 30%;"><spring:message code="btn.rel.gerar.relatorio"/></button>
                                            </div>
                                       </div>	
                                       
                                    </form:form> 
                                                                        
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->         
                        </div>                         
                        
                         <!--  START SIDEBAR RIGHT -->
                        <div class="col-md-3">
                            <c:import url="../../layout/sidebar-right.jsp"></c:import>
                        </div>                        
                        <!--  END SIDEBAR RIGHT -->      
                                             
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
         
            </section><!-- /#page-content -->
			
				<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
				<!-- End content  modal Ajuda - funcionalidade -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

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

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->
    </body>
    <!--/ END BODY -->
