<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>

<spring:url value="/parceria" var="urlParceria"/>
<spring:url var="urlImovelComentario" value="/imovelComentario"/>
<spring:url value="/imovelPropostas" var="urlImovelPropostas"/>
<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>
<spring:url value="/imovelFavoritos" var="urlImovelFavoritos"/>
<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>
<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario" var="urlUsuario"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>
<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/parceria/buscarCidadesParceria" var="urlBuscarCidades"/>
<spring:url value="/parceria/buscarBairrosParceria" var="urlBuscarBairros"/>

<script type="text/javascript">

$(document).ready(function() {
$('#opcaoOrdenacao1').change(function () {				
	$("#usuariosInteressadosForm").submit();      
 });

$('#opcaoOrdenacao2').change(function () {				
	$("#imoveisParceriaForm").submit();      
 });
 
$('#opcaoVisualizacaoListaParceria').change(function () {				
	$("#modVisualizaListaParceriaForm").submit();      
 });
 
$('#opcaoPaginacao').change(function () {				
	$("#parceriaPageForm").submit();      
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
                    	<spring:message code="lbl.title.link.parcerias"/><span>
                    	<c:choose>
                    		<c:when test="${parceriaForm.tipoLista == 'parceriaAceita'}">
                    		  <spring:message code="lbl.title.link.aceita"/>
                    		</c:when>
                    		<c:when test="${parceriaForm.tipoLista == 'parceriaSolRecebida'}">
                    		  <spring:message code="lbl.title.link.solicitacoes.recebidas"/>
                    		</c:when>
                    		<c:when test="${parceriaForm.tipoLista == 'parceriaMinhasSol'}">
                    		  <spring:message code="lbl.title.link.solicitacoes.enviadas"/>
                    		</c:when>
                    	</c:choose>
                    	>> <spring:message code="lbl.agrupando.imovel.parceria"/></span> 
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
                                    <form:form class="form-horizontal" role="form" method="POST" id="parceriaForm" modelAttribute="parceriaForm" action="${urlImovelFavoritos}/filtrar" >
                                       
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
		                                                    <form:options items="${parceriaForm.listaEstados}" itemValue="key" itemLabel="label"/>
		                                            </form:select>
		                                            </br> </br>
		                                            <span class="label label-default"><spring:message code="lbl.cidade"/> </span>
		                                            <spring:message code="lbl.hint.imovel.cidade" var="hintCidade"/>	
		                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;" title="${hintCidade}">
		                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
		                                                <form:options items="${parceriaForm.listaCidades}" itemValue="key" itemLabel="label"/>
		                                            </form:select>
		                                            </br> </br>
		                                            <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
		                                            <spring:message code="lbl.hint.imovel.bairro" var="hintBairro"/>
		                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintBairro}">
		                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
		                                                <form:options items="${parceriaForm.listaBairros}" itemValue="key" itemLabel="label"/>
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
	                                 <span class="meta-level" style="font-size: 16px;"><strong> <spring:message code="lbl.quant.total.parcerias"/> </strong>: </span> &nbsp; ${parceriaForm.quantRegistros}  
	                            </div>
	                         
                                <div class="pull-right" >
                                		<spring:message code="lbl.hint.tipo.agrupar" var="hintAgrupar"/>
                                       <form:form method="POST" id="modVisualizaListaParceriaForm" modelAttribute="parceriaForm" action="${urlParceria}/modoVisualizarParceria" >								                    
						                    <form:select id="opcaoVisualizacaoListaParceria" path="opcaoVisualizacao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintAgrupar}">
													<form:option value="" disabled="true"><spring:message code="lbl.agrupar.por"/></form:option>                  											
													<form:option value="agruparUsuarios" ><spring:message code="lbl.agrupar.usuarios"/></form:option>   
													<form:option value="todos" ><spring:message code="lbl.agrupar.todos"/></form:option> 
		                                    </form:select>
						               </form:form>
                                </div><!-- /.pull-right -->
                                <div class="pull-right" style="padding-right:10px;">
                                			<spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenar"/>
                                    		 <form:form method="POST" id="imoveisParceriaForm" modelAttribute="parceriaForm" action="${urlParceria}/ordenarAgruparParcerias" >						              		   								               
						                        	<form:select id="opcaoOrdenacao2" path="opcaoOrdenacao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintOrdenar}"> 
														<form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>
								                        <form:option value="maiorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.imovel.mais.recente"/></form:option>
														<form:option value="menorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.imovel.menos.recente"/></form:option>
														<form:option value="tituloImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.crescente"/></form:option>
													    <form:option value="tituloImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.decrescente"/></form:option>
													    <form:option value="maiorValor" ><spring:message code="lbl.opcao.ordenacao.imovel.maior.valor.imovel"/></form:option>
													    <form:option value="menorValor" ><spring:message code="lbl.opcao.ordenacao.imovel.menor.valor.imovel"/></form:option>											
								                  </form:select>
						                    </form:form> 
                                </div><!-- /.pull-left -->
                                
                                <c:if test="${parceriaForm.isVisible() }">
	                                	<div class="pull-right" style="padding-right:20px;">
		                                    <form:form method="POST" id="parceriaPageForm" modelAttribute="parceriaForm" action="${urlImovel}/filtrarParceria" >
		                                     	 <spring:message code="lbl.hint.opcao.paginacao" var="hintPaginacao"/>
	                                             <form:select id="opcaoPaginacao" path="opcaoPaginacao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPaginacao}">
	                                                 <form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
	                                                 <form:options items="${parceriaForm.listaPaginas}" itemValue="key" itemLabel="label"/>	                                                    	                                                    
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
                                                    <img src="${context}${imovel.imagemArquivo}" class="img-responsive" style="width: 260px; height: 195px; alt="admin"/>
                                                </a>
                                            </div>
                                            <div class="media-body">
                                                <span class="label pull-right" style="background-color: #03A9F4; font-size: 12px">${imovel.tipoImovelFmt}</span>
                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlImovel}/detalhesImovel/${imovel.id}" style="color : #03A9F4;">${imovel.titulo}</a></h4>
                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovel.endereco} - ${imovel.bairro} - ${imovel.cidade} -${imovel.uf} </h5>
                                                
                                                <div class="col-md-5" >                                                    
                                                    
                                                 	<div class="media-body" >
			                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.total.parcerias" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"> ${imovel.quantImovelParceria} </font></span></em> </br>			                                            													 
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
                                                 	
                                                 			<c:if test="${usuario.id == imovel.idDonoImovel}">														    															
																<a href="${urlParceria}/analisarSolicitacoesParceriasRecebidas/${imovel.idImovel}" ><i class="fa fa-gavel""></i> </a>
															</c:if> 
															
															<div class="btn-group dropup">                                            
																<a href="#a"  class="dropdown-toggle" data-toggle="dropdown">
																	<i class="fa fa-cogs"></i> 	
																</a>																
																	<ul class="dropdown-menu pull-bottom">      
																		<li><a href="${urlImovelVisualizado}/todosUsuariosVisitantesCompartilhado/${imovel.idImovel}"><spring:message code="lbl.link.imovel.compartilhado.visualizacoes"/></a></li>
																		<li><a href="${urlImovelIndicado}/todosImoveisIndicadosCompartilhado/${imovel.idImovel}"><spring:message code="lbl.link.imovel.compartilhado.indicacoes"/></a></li>
																		<li><a href="${urlImovelComentario}/visualizarTodosComentariosImovelCompartilhado/${imovel.idImovel}"><spring:message code="lbl.link.imovel.compartilhado.comentarios"/></a></li>				                                                
																		<li><a href="${urlImovelFavoritos}/visualizarTodosFavoritosImovelCompartilhado/${imovel.idImovel}"><spring:message code="lbl.link.imovel.compartilhado.Favoritos"/></a></li>
																		<li><a href="${urlImovelPropostas}/visualizarPropostasPorImovelCompartilhado/${imovel.idImovel}"><spring:message code="lbl.link.imovel.compartilhado.propostas"/></a></li>
																		<li><a href="${urlImovelIndicado}/selecionarParaIndicarImovel/${imovel.idImovel}"><spring:message code="lbl.link.imovel.compartilhado.indicar.imovel"/></a></li>			                                                				                                                
																	</ul>																	
															</div>	
                                                  			
			                                        <% } %>
                                                    
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>  
                                </div>
                        </div>                 
                                              
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->      
         
            </section><!-- /#page-content -->   

			<!-- Start content modal Usuario Detalhes-->
				<c:import url="../../ajuda/imovelDetalhesModal.jsp"></c:import>																				
			<!-- End content  modal Usuario Detalhes -->        
            	 		
	 		<!-- Start content modal Usuario Detalhes-->
				<c:import url="../../ajuda/usuarioDetalhesModal.jsp"></c:import>																				
			<!-- End content  modal Usuario Detalhes -->

           	<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
			
        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

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