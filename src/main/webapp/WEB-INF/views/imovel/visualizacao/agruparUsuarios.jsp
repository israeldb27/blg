<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="context" value="<%= request.getContextPath()%>"/>
<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoContatoEnum"%>

<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>
<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/>
<c:set var="listaTipoContato" value="<%= TipoContatoEnum.values() %>"/>

<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>
<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url var="urlImovelFavoritos" value="/imovelFavoritos"/>
<spring:url value="/usuario" var="urlUsuario"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
    
<script type="text/javascript">

$(document).ready(function() {	
	
	$('#opcaoOrdenacao2').change(function () {				
		$("#imoveisVisitadosForm").submit();      
	 });
	
	$('#opcaoPaginacao').change(function () {				
		$("#imoveisVisitadosPageForm").submit();      
	 });
	
	$('#opcaoVisualizacaoImoveisVisitados').change(function () {				
		$("#modVisualizaImoveisVisitadosForm").submit();      
	 });   
});	


function desmarcarCheck(id) {
    $.post("${urlImovelVisualizado}/desmarcarCheck", {'idImovelvisitado' : id}, function() {      
    	$("#idCheckImovel_"+id).hide();
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
                    	<spring:message code="lbl.title.link.imoveis.visualizacoes"/><span>
                    	<c:choose>
                    		<c:when test="${imovelvisualizadoForm.tipoLista == 'meusImoveisVisitados'}">
                    		  <spring:message code="lbl.title.link.imoveis.visualizacoes.recebidas"/>
                    		</c:when>
                    		<c:when test="${imovelvisualizadoForm.tipoLista == 'imoveisVisitados'}">
                    		  <spring:message code="lbl.title.link.imoveis.visualizacoes.minhas"/>
                    		</c:when>
                    	</c:choose>
                    	>> <spring:message code="lbl.agrupando.usuarios.imovel.visualizacao"/></span> 
                    </h2> 
					
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                         <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
                            <c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        <div class="col-lg-3 col-md-3 col-sm-4">
                            <form:form method="POST" id="meusImoveisVisitadosForm" modelAttribute="imovelvisualizadoForm" action="${urlImovelVisualizado}/filtrarAgruparUsuarios" >
                            
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
                                    	</br>
                                        <div class="form-group no-margin">
                                        	 <span class="label label-default"><spring:message code="lbl.filtro.perfil.usuario"/> </span>
                                        	 	<spring:message code="lbl.hint.usuario.perfil.usuario" var="hintPerfilUsuario"/>
									             <form:select id="opcaoPerfilContatoAgruparUsuarios" path="opcaoPerfilContatoAgruparUsuarios" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPerfilUsuario}">                                
														<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
											   			<form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
												  </form:select> 
                                            </br> </br>
                                        	<span class="label label-default"><spring:message code="lbl.relatorio.tipo.contato"/></span>
                                        		<spring:message code="lbl.hint.usuario.contatos.usuario" var="hintContatosUsuario"/>
									            <form:select id="opcaoContatoAgruparUsuarios" path="opcaoContatoAgruparUsuarios" class="chosen-select" tabindex="-1" style="display: none;" title="${hintContatosUsuario}">                                
													<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                  											
												    <form:options items="${listaTipoContato}" itemValue="identificador" itemLabel="rotulo" />
											  </form:select>
                                            </br> </br>
                                          
                                          	  <div class="pull-right">
                                          	  		<spring:message code="lbl.hint.aplicar.filtro" var="hintFiltrar"/>
													<button type="submit" class="button btn-primary" title="${hintFiltrar}"> <spring:message code="lbl.filtrar.geral"/></button>
												  </div><!-- /.pull-right -->            												   
												<br>
                                            
                                        </div><!-- /.form-group -->
                                    </div><!-- /.panel -->
                                </div>
  		
                            </form:form>
                        </div>
                        
                        <c:choose>
                        	<c:when test="${not empty listaAgruposUsuarios}">
                        		<div class="col-lg-9 col-md-9 col-sm-8">                        	                     			 
			                                <div class="pull-left col-lg-4" style="padding-top: 9px;">                        				
				                                 <span class="meta-level" style="font-size: 16px;"><strong> <spring:message code="lbl.quant.total.usuarios"/> </strong>: </span> &nbsp; ${imovelvisualizadoForm.quantRegistros}  
				                            </div>
			                                <div class="pull-right" >
			                                      		<form:form method="POST" id="modVisualizaImoveisVisitadosForm" modelAttribute="imovelvisualizadoForm" action="${urlImovelVisualizado}/modoVisualizar" >					                             
							                                     <form:select id="opcaoVisualizacaoImoveisVisitados" path="opcaoVisualizacao" class="form-control">				                                         	
							                                         <form:option value="" disabled="true"><spring:message code="lbl.agrupar.por"/></form:option>
							                                         <c:if test="${imovelvisualizadoForm.tipoLista == 'meusImoveisVisitados'}">
							                                         	<form:option value="agruparImoveis"><spring:message code="lbl.agrupar.imoveis"/></form:option>
							                                         </c:if>                                  
							                                         <form:option value="agruparUsuarios"><spring:message code="lbl.agrupar.usuarios"/></form:option>
							                                         <form:option value="todos"><spring:message code="lbl.agrupar.todos"/></form:option>
							                                     </form:select>
								                         </form:form>	
			                                </div><!-- /.pull-right -->
			                                <div class="pull-right" style="padding-right:10px;">
			                                    		<form:form method="POST" id="imoveisVisitadosForm" modelAttribute="imovelvisualizadoForm" action="${urlImovelVisualizado}/ordenarAgrupar" >         		      	
									                        	<form:select id="opcaoOrdenacao2" path="opcaoOrdenacao" class="form-control">                                
											                        <form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>
											                        <form:option value="maiorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.usuario.cad.mais.recente"/></form:option>
																	<form:option value="menorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.usuario.cad.menos.recente"/></form:option>
																	<form:option value="nomeImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.usuario.nome.crescente"/></form:option>
																	<form:option value="nomeImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.usuario.nome.decrescente"/></form:option>
											                   </form:select>
											              </form:form>
			                                </div><!-- /.pull-left -->
			                                
			                                <c:if test="${imovelvisualizadoForm.isVisible() }">
			                                	<div class="pull-right" style="padding-right:20px;">
				                                    <form:form method="POST" id="imoveisVisitadosPageForm" modelAttribute="imovelvisualizadoForm" action="${urlImovelVisualizado}/filtrarAgruparUsuarios" >
				                                     		<spring:message code="lbl.hint.opcao.paginacao" var="hintPaginacao"/>
			                                                <form:select id="opcaoPaginacao" path="opcaoPaginacao" class="form-control" title="${hintPaginacao}">
			                                                    <form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
			                                                    <form:options items="${imovelvisualizadoForm.listaPaginas}" itemValue="key" itemLabel="label"/>	                                                    	                                                    
			                                              </form:select>
				                                      </form:form>
				                                </div><!-- /.pull-left -->
			                                </c:if>
			                                
			                                <div class="clearfix"></div>
			
			                                <div class="media-list list-search">
			                                    <c:forEach var="usuarioVisita" items="${listaAgruposUsuarios}" varStatus="item">
			                                        <div class="media rounded shadow no-overflow">
			                                            <div class="media-left">
			                                                <a href="${urlUsuario}/detalhesUsuario/${usuarioVisita.id}" >                                                                                                     
			                                                    <img src="data:image/jpeg;base64,${usuarioVisita.imagemArquivo}" class="img-responsive" style="width: 260px; height: 215px; alt="admin"/>
			                                                </a>
			                                            </div>
			                                            <div class="media-body">
			                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${usuarioVisita.perfilFmt}</span>
			                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlUsuario}/detalhesUsuario/${usuarioVisita.id}" style="color : #9d2428;">${usuarioVisita.nome}</a></h4>
			                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${usuarioVisita.cidade} - ${usuarioVisita.uf}   </h1>
			                                                
			                                                <div class="col-md-5" >  
			                                                	<br>
			                                                	<div class="media-body" >
						                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.total.visualizacoes" />: </font> &nbsp;&nbsp;&nbsp;&nbsp;
						                                            <span class="text-success"> <font style="font-size:11px; font-style: normal;">${usuarioVisita.quantImovelVisitado}</font></span></em> </br>			                                            		                                            	                                            
						                                        </div>						                                     
			                                                </div>
			                                                
			                                                <div class="col-md-7">
			                                                    <table class="table table-condensed">
			                                                        <tbody style="font-size: 13px;">
			                                                        	<tr>
			                                                                <td class="text-left"> <spring:message code="lbl.total.imoveis"/> </td>
	                                                                		<td class="text-right">${usuarioVisita.quantTotalImoveis}</td>
			                                                            </tr>  
			                                                            
			                                                             <tr>
						                                                 	<td class="text-left"><spring:message code="lbl.total.contato"/></td>
						                                                 	<td class="text-right">${usuarioVisita.quantTotalContatos}</td>
						                                                </tr>  
						                                                
						                                                <tr>
						                                                 	<td class="text-left"><spring:message code="lbl.total.seguidores"/></td>
						                                                 	<td class="text-right">${usuarioVisita.quantTotalSeguidores}</td>
						                                                </tr>
						                                                
						                                                <tr>
						                                                 	<td class="text-left"><spring:message code="lbl.total.recomendacoes"/></td>
						                                                 	<td class="text-right">${usuarioVisita.quantTotalRecomendacoes}</td>
						                                                </tr>                                                  
			                                                        </tbody>
			                                                    </table>
			                                                    
			                                                    <br>  
			                                                    
			                                                    <% if ( request.getSession().getAttribute("acessoValido").equals("S") ) {%> 
			                                                    	<spring:message code="lbl.link.todas.visualizacoes.usuario" var="mensagemTodasVisitas"/>                                                    	
			                                                    	<a href="${urlImovelVisualizado}/todasVisitasUsuario/${usuarioVisita.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="dropdown-toggle" ><i class="fa fa-cog"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> ${mensagemTodasVisitas} </font> &nbsp;&nbsp;</i> </a>
			                                                    <% } %>
			                                                </div>
			                                            </div>
			                                        </div>
			                                    </c:forEach>
			                                </div>                        	         
	                        </div>
                        	</c:when>
                        	
                        	<c:when test="${empty listaAgruposUsuarios}">
                        		<div class="col-lg-9 col-md-9 col-sm-8">  
                        			<div class="callout callout-warning">
	                                    <strong><spring:message code="lbl.rel.nenhum.registro"/></strong>
	                                </div>
                        		</div>
                        	</c:when>
                        </c:choose>
                        
                        
                    </div>
                </div><!-- /.body-content -->
            </section><!-- /#page-content -->
         </section><!-- /#wrapper -->
         
         <!-- Start content modal Usuario Detalhes-->
			<c:import url="../../ajuda/imovelDetalhesModal.jsp"></c:import>																				
		<!-- End content  modal Usuario Detalhes -->
			
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
         

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->
