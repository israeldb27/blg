<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>

<spring:url value="/imovelFavoritos" var="urlImovelFavoritos"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>  
 
<script type="text/javascript">

$(document).ready(function() {
$('#opcaoOrdenacao1').change(function () {				
	$("#usuariosInteressadosForm").submit();      
 });

$('#opcaoOrdenacao2').change(function () {				
	$("#listaInteresseForm").submit();      
 });
 
$('#opcaoVisualizacaoListaInteresse').change(function () {				
	$("#modVisualizaListaInteresseForm").submit();      
 });
 
$('#opcaoPaginacao').change(function () {				
	$("#imovelFavoritoPageForm").submit();      
 });
 
$('#idEstadoAgruparImoveis').change(function () {
    var comboPai = '#idEstadoAgruparImoveis';
    var comboFilha = '#idCidadeAgruparImoveis';
    var comboFilha2 = '#idBairroAgruparImoveis';
    limpaComboLinha(comboFilha);
    limpaComboLinha(comboFilha2);
    recuperaCidades();
});

$('#idCidadeAgruparImoveis').change(function () {
	var comboPai   = '#idCidadeAgruparImoveis';
	var comboFilha = '#idBairroAgruparImoveis';
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
    var parametro1 = $("#idEstadoAgruparImoveis").val();
    $.ajax({
        type: 'GET',
        url: '${urlBuscarCidades}/' + parametro1,
        dataType: 'json',
        success: function(json){
            var options = "";
            $.each(json, function(key, value){
               $("#idCidadeAgruparImoveis").append("<option value='"+value.key+"'>"+value.label+"</option>");
            });  
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });    	   
}

function recuperaBairros(){
    var parametro1 = $("#idCidadeAgruparImoveis").val();
    $.ajax({
        type: 'GET',
        url: '${urlBuscarBairros}/' + parametro1,
        dataType: 'json',
        success: function(json){
            var options = "";
            $.each(json, function(key, value){
            	$("#idBairroAgruparImoveis").append("<option value='"+value.key+"'>"+value.label+"</option>");
            });  
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
                    <h2><i class="fa fa-pencil"></i> 
                    	<spring:message code="lbl.title.lista.Favoritos"/><span>
                    	<c:choose>
                    		<c:when test="${imovelFavoritoForm.tipoLista == 'usuariosInteressados'}">
                    		  <spring:message code="lbl.title.usuarios.interessados"/>
                    		</c:when>
                    		<c:when test="${imovelFavoritoForm.tipoLista == 'meuInteresse'}">
                    		  <spring:message code="lbl.title.meus.Favoritos"/>
                    		</c:when>
                    	</c:choose>
                    	>> <spring:message code="lbl.lista.interesse.agrupar.imoveis"/></span> 
                    </h2>
					
                </div><!-- /.header-content -->
                
                 <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">                  		 
                        <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
                        	<c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>                           
                             
                        <div class="col-lg-3 col-md-3 col-sm-4">                 		
                                <div class="panel-body no-padding">
                                     <form:form class="form-horizontal" role="form" method="POST" id="imovelFavoritoForm" modelAttribute="imovelFavoritoForm" action="${urlImovelFavoritos}/filtrarAgruparImoveis" >
                                    <form:hidden path="tipoLista" value="usuariosInteressados"/>	
                                       
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
		                                        	</br>
		                                        	<span class="label label-default"><spring:message code="lbl.estado"/> </span>
		                                        	<spring:message code="lbl.hint.imovel.estado" var="hintEstado"/>  
		                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" title="${hintEstado}">
		                                                    <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
		                                                    <form:options items="${imovelFavoritoForm.listaEstados}" itemValue="key" itemLabel="label"/>
		                                            </form:select>
		                                            </br> </br>
		                                            <span class="label label-default"><spring:message code="lbl.cidade"/> </span>
		                                            <spring:message code="lbl.hint.imovel.cidade" var="hintCidade"/>
		                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;" title="${hintCidade}">
		                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
		                                                <form:options items="${imovelFavoritoForm.listaCidades}" itemValue="key" itemLabel="label"/>
		                                            </form:select>
		                                            </br> </br>
		                                            <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
		                                            <spring:message code="lbl.hint.imovel.bairro" var="hintBairro"/>
		                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintBairro}">
		                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
		                                                <form:options items="${imovelFavoritoForm.listaBairros}" itemValue="key" itemLabel="label"/>
		                                            </form:select>
		                                            <br>											  
		                                            	  <spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>	
														  <div class="pull-right">
															<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
														  </div><!-- /.pull-right --> 
		                                            <br>
		                                        
		                                    </div><!-- /.panel -->
		                                </div>
		                                
		                                <div class="panel rounded shadow no-overflow">                                    
	                                        <br>                        
		                                    <div class="panel-body">
		                                        <span class="label label-default"><spring:message code="lbl.tipo.imovel"/> </span>
		                                        	  <spring:message code="lbl.hint.imovel.tipo.imovel" var="hintTipoImovel"/>	
										              <form:select id="tipoImovel" path="tipoImovel" class="chosen-select" tabindex="-1" style="display: none;" title="${hintTipoImovel}">                                
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

										        <spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>
								             	  <div class="pull-right">
														<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
												  </div><!-- /.pull-right -->            												   
													
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
														<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
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
												<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
											  </div><!-- /.pull-right -->            												   
											  <br>
		                                    </div>
		                               </div>	
                                       
                                    </form:form>        
                                    
                                </div><!-- /.panel-body -->
                        </div> 
                                             
					    <div class="col-lg-9 col-md-9 col-sm-8">					    	
					    			<div class="pull-left col-lg-4" style="padding-top: 9px;">                        				
		                                 <span class="meta-level" style="font-size: 16px;"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: </span> &nbsp; ${imovelFavoritoForm.quantRegistros}  
		                            </div>
	                                <div class="pull-right" >
	                                		<spring:message code="lbl.hint.tipo.agrupar" var="hintAgrupar"/>
	                                       <form:form method="POST" id="modVisualizaListaInteresseForm" modelAttribute="imovelFavoritoForm" action="${urlImovelFavoritos}/modoVisualizar" >								                    
							                    <form:select id="opcaoVisualizacaoListaInteresse" path="opcaoVisualizacao" class="form-control" title="${hintAgrupar}">
														<form:option value="" disabled="true"><spring:message code="lbl.agrupar.por"/></form:option>                  											
														<form:option value="agruparUsuarios" ><spring:message code="lbl.agrupar.usuarios"/></form:option>   
														<form:option value="todos" ><spring:message code="lbl.agrupar.todos"/></form:option> 
			                                    </form:select>
							               </form:form>
	                                </div><!-- /.pull-right -->
	                                <div class="pull-right" style="padding-right:10px;">
	                                	<spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenar"/>
	                                   	<form:form method="POST" id="listaInteresseForm" modelAttribute="imovelFavoritoForm" action="${urlImovelFavoritos}/ordenarAgrupar" >					              		  							               
					                        	<form:select id="opcaoOrdenacao2" path="opcaoOrdenacao" class="form-control" title="${hintOrdenar}">                                							                        
													 <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                      
													 <form:option value="maiorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.imovel.mais.recente"/></form:option>
													 <form:option value="menorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.imovel.menos.recente"/></form:option>
													 <form:option value="tituloImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.crescente"/></form:option>
													 <form:option value="tituloImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.decrescente"/></form:option>
													 <form:option value="maiorValor" ><spring:message code="lbl.opcao.ordenacao.imovel.maior.valor.imovel"/></form:option>
													 <form:option value="menorValor" ><spring:message code="lbl.opcao.ordenacao.imovel.menor.valor.imovel"/></form:option>														
					                  			</form:select>                                       
					                   </form:form>
	                                </div><!-- /.pull-left -->
	                                
	                                <c:if test="${imovelFavoritoForm.isVisible() }">
	                                	<div class="pull-right" style="padding-right:20px;">
		                                    <form:form method="POST" id="imovelFavoritoPageForm" modelAttribute="imovelFavoritoForm" action="${urlImovelFavoritos}/filtrarAgruparImoveis" >
		                                     	 <spring:message code="lbl.hint.opcao.paginacao" var="hintPaginacao"/>
	                                             <form:select id="opcaoPaginacao" path="opcaoPaginacao" class="form-control" title="${hintPaginacao}">
	                                                 <form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
	                                                 <form:options items="${imovelFavoritoForm.listaPaginas}" itemValue="key" itemLabel="label"/>	                                                    	                                                    
	                                             </form:select>
		                                      </form:form>
		                                </div><!-- /.pull-left -->
	                                </c:if>
	                                
	                                <div class="clearfix"></div>
	
	                                <div class="media-list list-search">
	                                 		<c:forEach var="imovel" items="${listaAgruposImoveis}" varStatus="item">
	                                        <div class="media rounded shadow no-overflow">
	                                            <div class="media-left">
	                                                <a href="${urlImovel}/detalhesImovel/${imovel.id}" >
	                                                   <span class="meta-provider" style="font-size:19px;">${imovel.acaoFmt} <br>
	                                                   							<strong>  R$<fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
	                                                   </span><br>                                                   
	                                                    <img src="${context}${imovel.imagemArquivo}" class="img-responsive" style="width: 260px; height: 240px; alt="admin"/>
	                                                </a>
	                                            </div>
	                                            <div class="media-body">
	                                                <span class="label pull-right" style="background-color: #03A9F4; font-size: 12px">${imovel.tipoImovelFmt}</span>
	                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlImovel}/detalhesImovel/${imovel.id}" style="color : #03A9F4;">${imovel.titulo}</a></h4>
	                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovel.endereco} - ${imovel.bairro} - ${imovel.cidade} -${imovel.uf} </h5>
	                                                
	                                                <div class="col-md-5" >                                                    
	                                                    
	                                                 	<div class="media-body" >
	                                                 		 <br>
				                                             <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.total.usuarios.interessados" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"> ${imovel.quantImoveisFavoritos} </font></span></em>
				                                        </div>
	                                              		
	                                                </div>	                                                
	                                                <div class="col-md-7">
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
	                                                        </tbody>
	                                                    </table>
	                                                    
	                                                    <br>
	                                                    
	                                                    <% if ( request.getSession().getAttribute("acessoValido").equals("S") ) {%>  
	                                                    		<spring:message code="lbl.link.todos.usuarios.lista.interesse" var="mensagemMeuInteresse"/>
	                                                 			<a href="${urlImovelFavoritos}/visualizarTodosFavoritosImovel/${imovel.id}" style="font-size:x-large; color: rgb(99, 110, 123);"  class="dropdown-toggle" ><i class="fa fa-cog"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> ${mensagemMeuInteresse} </font> &nbsp;&nbsp; </i></a>	                                                    
	                                                             
																<spring:message code="lbl.title.link.comparar" var="mensagemComparar"/>                                                 			                                                 	                                                 	
		                                                        <a href="#a" onClick="adicionarComparativo(${imovel.id})" style="font-size:x-large; color: rgb(99, 110, 123);"  class="dropdown-toggle" ><i class="fa fa-eye"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> ${mensagemComparar} </font></i></a>
				                                        <% } %>
	                                                </div>
	                                            </div>
	                                        </div>
	                                    </c:forEach>  
	                                </div>	
                        </div>                 
                                              
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
 
         
            </section><!-- /#page-content -->   

			
           	<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
			
        </section><!-- /#wrapper -->
  	 

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

</html>