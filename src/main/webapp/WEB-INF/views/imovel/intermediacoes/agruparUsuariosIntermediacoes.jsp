<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>

<spring:url value="/intermediacao" var="urlIntermediacao"/>
<spring:url var="urlImovelComentario" value="/imovelComentario"/>
<spring:url value="/imovelPropostas" var="urlImovelPropostas"/>
<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>
<spring:url value="/imovelFavoritos" var="urlImovelFavoritos"/>
<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>
<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario" var="urlUsuario"/>

<%@page import="com.busqueumlugar.enumerador.TipoContatoEnum"%>
<c:set var="listaTipoContato" value="<%= TipoContatoEnum.values() %>"/>

<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>
<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>


<script type="text/javascript">

$(document).ready(function() {
$('#opcaoOrdenacao1').change(function () {				
	$("#usuariosInteressadosForm").submit();      
 });

$('#opcaoOrdenacao2').change(function () {				
	$("#imoveisIntermediacaoForm").submit();      
 });
 
$('#opcaoVisualizacaoListaIntermediacao').change(function () {				
	$("#modVisualizaListaIntermediacaoForm").submit();      
 });
 
$('#opcaoPaginacao').change(function () {				
	$("#intermediacaoPageForm").submit();      
 });

});	

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
                    	<spring:message code="lbl.title.link.intermediacoes"/><span>
                    	<c:choose>
                    		<c:when test="${intermediacaoForm.tipoLista == 'intermediacaoAceita'}">
                    		  <spring:message code="lbl.title.link.aceita"/>
                    		</c:when>
                    		<c:when test="${intermediacaoForm.tipoLista == 'intermediacaoSolRecebida'}">
                    		  <spring:message code="lbl.title.link.solicitacoes.recebidas"/>
                    		</c:when>
                    		<c:when test="${intermediacaoForm.tipoLista == 'intermediacaoMinhasSol'}">
                    		  <spring:message code="lbl.title.link.solicitacoes.enviadas"/>
                    		</c:when>
                    	</c:choose>
                    	>> <spring:message code="lbl.agrupando.usuario.intermediacao"/></span> 
                    </h2> 					
                </div><!-- /.header-content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                         <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
                            <c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        <div class="col-lg-3 col-md-3 col-sm-4">
                            <form:form class="form-horizontal" role="form" method="POST" id="intermediacaoForm" modelAttribute="intermediacaoForm" action="${urlIntermediacao}/filtrarAgruparUsuariosIntermediacao" >
                            
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
                                        <div class="form-group no-margin">
                                        	 <span class="label label-default"><spring:message code="lbl.filtro.perfil.usuario"/> </span>
                                        	 	<spring:message code="lbl.hint.usuario.perfil.usuario" var="hintPerfilUsuario"/>
									             <form:select id="opcaoPerfilContatoAgruparUsuarios" path="opcaoPerfilContatoAgruparUsuarios" class="form-control" title="${hintPerfilUsuario}">                                
														<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
											   			<form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
												  </form:select> 
                                            </br> </br>
                                        	<span class="label label-default"><spring:message code="lbl.relatorio.tipo.contato"/></span>
                                        		<spring:message code="lbl.hint.usuario.contatos.usuario" var="hintContatosUsuario"/>
									            <form:select id="opcaoContatoAgruparUsuarios" path="opcaoContatoAgruparUsuarios" class="form-control" title="${hintContatosUsuario}">                                
													<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                  											
												    <form:options items="${listaTipoContato}" itemValue="identificador" itemLabel="rotulo" />
											  </form:select>
                                            </br> </br>
                                          
                                          	  <div class="pull-right">
                                          	  		<spring:message code="lbl.hint.aplicar.filtro" var="hintFiltrar"/>
													<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand"  title="${hintFiltrar}"> <spring:message code="lbl.filtrar.geral"/></button>
												  </div><!-- /.pull-right -->            												   
												<br>
                                            
                                        </div><!-- /.form-group -->
                                    </div><!-- /.panel -->
                                </div>
  		
                            </form:form>
                        </div>
                        <div class="col-lg-9 col-md-9 col-sm-8">
                                <div class="pull-left col-lg-4" style="padding-top: 9px;">                        				
	                                 <span class="meta-level" style="font-size: 16px;"><strong> <spring:message code="lbl.quant.total.usuarios"/> </strong>: </span> &nbsp; ${intermediacaoForm.quantRegistros}  
	                            </div>
                                <div class="pull-right" >
                                			<spring:message code="lbl.hint.tipo.agrupar" var="hintAgrupar"/>
                                      		<form:form method="POST" id="modVisualizaListaIntermediacaoForm" modelAttribute="intermediacaoForm" action="${urlIntermediacao}/modoVisualizarIntermediacao" >								                    
							                    <form:select id="opcaoVisualizacaoListaIntermediacao" path="opcaoVisualizacao" class="form-control" title="${hintAgrupar}">
														<form:option value="" disabled="true"><spring:message code="lbl.agrupar.por"/></form:option>                  											
														<form:option value="agruparUsuarios" ><spring:message code="lbl.agrupar.usuarios"/></form:option>   
														<form:option value="todos" ><spring:message code="lbl.agrupar.todos"/></form:option> 
			                                    </form:select>
								            </form:form>
                                </div><!-- /.pull-right -->
                                <div class="pull-right" style="padding-right:10px;">
                                			<spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenar"/>
                                    		<form:form method="POST" id="imoveisIntermediacaoForm" modelAttribute="intermediacaoForm" action="${urlIntermediacao}/ordenarAgruparIntermediacoes" >         		      	
					                        	<form:select id="opcaoOrdenacao2" path="opcaoOrdenacao" class="form-control" title="${hintOrdenar}">                                
							                        <form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>
							                        <form:option value="maiorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.usuario.cad.mais.recente"/></form:option>
													<form:option value="menorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.usuario.cad.menos.recente"/></form:option>
													<form:option value="nomeImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.usuario.nome.crescente"/></form:option>
													<form:option value="nomeImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.usuario.nome.decrescente"/></form:option>
							                   </form:select>
							              </form:form> 
                                </div><!-- /.pull-left -->
                                
                                <c:if test="${intermediacaoForm.isVisible() }">
                                	<div class="pull-right" style="padding-right:20px;">
	                                    <form:form method="POST" id="intermediacaoPageForm" modelAttribute="intermediacaoForm" action="${urlIntermediacao}/filtrarIntermediacao" >
	                                     	 <spring:message code="lbl.hint.opcao.paginacao" var="hintPaginacao"/>
                                             <form:select id="opcaoPaginacao" path="opcaoPaginacao" class="form-control" title="${hintPaginacao}">
                                                 <form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
                                                 <form:options items="${intermediacaoForm.listaPaginas}" itemValue="key" itemLabel="label"/>	                                                    	                                                    
                                             </form:select>
	                                      </form:form>
	                                </div><!-- /.pull-left -->
                                </c:if>
		                                
                                <div class="clearfix"></div>

                                <div class="media-list list-search">
                                	<c:choose>
                        				<c:when test="${not empty listaAgruposUsuarios }">
                        					<c:forEach var="usuarioIntermediacao" items="${listaAgruposUsuarios}" varStatus="item">
		                                        <div class="media rounded shadow no-overflow">
		                                            <div class="media-left">
		                                                <a href="${urlUsuario}/detalhesUsuario/${usuarioIntermediacao.id}" >                                                                                                     
		                                                    <img src="${context}${usuarioIntermediacao.imagemArquivo}" class="img-responsive" style="width: 260px; height: 195px; alt="admin"/>
		                                                </a>
		                                            </div>
		                                            <div class="media-body">
		                                                <span class="label pull-right" style="background-color: #03A9F4; font-size: 12px">${usuarioIntermediacao.perfilFmt}</span>
		                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlUsuario}/detalhesUsuario/${usuarioIntermediacao.id}" style="color : #03A9F4;">${usuarioIntermediacao.nome}</a></h4>
		                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${usuarioIntermediacao.cidade} - ${usuarioIntermediacao.uf}   </h1>
		                                                
		                                                <div class="col-md-5" >  
		                                                	<div class="media-body" >
		                                                		<em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.usuario" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${usuarioIntermediacao.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em> </br>
					                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.total.usuarios.intermediacoes" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;">${usuarioIntermediacao.quantImovelIntermediacao}</font></span></em> </br>			                                            		                                            	                                            
					                                        </div>			                                                                                       
		                                                    <br/> <br/>
		                                                </div>
		                                                
		                                                <div class="col-md-7">
		                                                    <table class="table table-condensed">
		                                                        <tbody style="font-size: 13px;">
		                                                        	<tr>
		                                                                <td class="text-left"> <spring:message code="lbl.total.imoveis"/> </td>
		                                                              	<td class="text-right">${usuarioIntermediacao.quantTotalImoveis}</td>
		                                                            </tr>  
		                                                            
		                                                             <tr>
					                                                 	<td class="text-left"><spring:message code="lbl.total.contato"/></td>
					                                                 	<td class="text-right">${usuarioIntermediacao.quantTotalContatos}</td>
					                                                </tr>  
					                                                
					                                                <tr>
					                                                 	<td class="text-left"><spring:message code="lbl.total.seguidores"/></td>
					                                                 	<td class="text-right">${usuarioIntermediacao.quantTotalSeguidores}</td>
					                                                </tr>
					                                                
					                                                <tr>
					                                                 	<td class="text-left"><spring:message code="lbl.total.recomendacoes"/></td>
					                                                 	<td class="text-right">${usuarioIntermediacao.quantTotalRecomendacoes}</td>
					                                                </tr>                                                  
		                                                        </tbody>
		                                                    </table>
		                                                    
		                                                    <br>
		                                                    <% if ( request.getSession().getAttribute("acessoValido").equals("S") ) {%> 
		                                                    	<spring:message code="lbl.aba.todos.imoveis.intermediacao.por.usuario" var="mensagemTodosImoveisIntermediacao"/>                                                    	
		                                                    	<a href="${urlIntermediacao}/todosImoveisIntermediacoesPorUsuario/${usuarioIntermediacao.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="dropdown-toggle" ><i class="fa fa-cog"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> ${mensagemTodosImoveisIntermediacao} </font> &nbsp;&nbsp; </i> </a>
		                                                    <% } %>
		                                                </div>
		                                            </div>
		                                        </div>
		                                    </c:forEach>
                        				</c:when>
                        				
                        				<c:when test="${empty listaAgruposUsuarios }">
                        					<div class="col-lg-9 col-md-9 col-sm-8">  
			                        			<div class="callout callout-warning">
				                                    <strong><spring:message code="lbl.rel.nenhum.registro"/></strong>
				                                </div>
			                        		</div>
                        				</c:when>
                        				
                        			</c:choose>
                                </div>                            
                        </div>
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
