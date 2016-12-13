<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/mensagem" var="urlMensagem"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/admin" var="urlAdmin"/>
<spring:url value="/mensagemAdmin" var="urlMensagemAdmin"/>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   

    <!-- START @HEAD -->
 
<script type="text/javascript">

$(document).ready(function() {

});	

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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.detalhes.usuario.admin"/> </h2>                                                                        
                </div><!-- /.header-content -->
                                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                    <div class="row">
                   		<!-- START Foto Perfil e Informações Contato -->
                   			<div class="col-lg-3 col-md-3 col-sm-4">

		                        <div class="panel rounded shadow">
		                            <div class="panel-body">
		                                <div class="inner-all">
		                                    <ul class="list-unstyled">
		                                        <li class="text-center">		                                        
		                                            <img class="img-circle img-bordered-primary" src="${context}/${usuarioForm.imagemArquivo}" style="width: 100px; height: 100px; " alt="Tol Lee">
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize">${usuarioForm.nome}</h4>
		                                            <p class="text-muted text-capitalize">  <c:if test="${usuarioForm.perfil == 'P'}">
																				            	<spring:message code="lbl.perfil.usuario.cliente"/>
																				            </c:if>
																				            <c:if test="${usuarioForm.perfil == 'C'}">
																				            	<spring:message code="lbl.perfil.usuario.corretor"/>
																				            </c:if>
																				             <c:if test="${usuarioForm.perfil == 'I'}">
																				            	<spring:message code="lbl.perfil.usuario.imobiliaria"/>
																				            </c:if> </p>
		                                        </li>
		                                    
		                                        <li><br/></li>
		                                        <li>   
		                                            <div class="btn-group-vertical btn-block">		                                           
		                                              	<a href="${urlMensagemAdmin}/prepararNovaMensagemPorAdmin/${usuarioForm.id}" class="btn btn-default" ><i class="fa fa-cog pull-right"></i><spring:message code="lbl.enviar.mensagem"/></a>	                                                
		                                            </div>
		                                        </li>
		                                    </ul>
		                                </div>
		                            </div>
		                        </div><!-- /.panel -->
		
		                        <div class="panel panel-info rounded shadow">
		                            <div class="panel-heading">
		                                <div class="pull-left">
		                                    <h3 class="panel-title"><spring:message code="lbl.title.perfil.usuario.info.contatos"/></h3>  
		                                </div>
		                                <div class="pull-right">
		                                   
		                                </div>
		                                <div class="clearfix"></div>
		                            </div><!-- /.panel-heading -->
		                            <div class="panel-body no-padding rounded">
		                                <ul class="list-group no-margin">
		                                    <li class="list-group-item"><i class="fa fa-envelope mr-5"></i> ${usuario.email}</li>
		                                    <li class="list-group-item"><i class="fa fa-globe mr-5"></i> www.djavaui.com</li>
		                                    <li class="list-group-item"><i class="fa fa-phone mr-5"></i> ${usuario.telefone}</li>
		                                </ul>
		                            </div><!-- /.panel-body -->
		                        </div><!-- /.panel -->		
		                    </div>
                   		<!-- END Foto Perfil e Informações Contato-->
                   		
                   		<!-- START Painel de Informações -->
                   			<div class="col-lg-9 col-md-9 col-sm-8">

				                    <div class="profile-cover">
				                         <!-- Start vertical tabs with pagination -->
			                            <div id="basic-wizard-vertical">
			                                <div class="panel panel-tab panel-tab-double panel-tab-vertical row no-margin mb-15 rounded shadow">
			                                    <!-- Start tabs heading -->
			                                    <div class="panel-heading no-padding col-lg-3 col-md-3 col-sm-3">
			                                        <ul class="nav nav-tabs">
			                                            <li class="active">
			                                                <a href="#tab4-1" data-toggle="tab">
			                                                    <i class="fa fa-user"></i>
			                                                    <div>
			                                                        <span class="text-strong"><spring:message code="lbl.title.aba.minhas.informacoes"/></span>                                                        
			                                                    </div>
			                                                </a>
			                                            </li>
			                                            <li>
			                                                <a href="#tab4-2" data-toggle="tab">
			                                                    <i class="fa fa-file-text"></i>
			                                                    <div>
			                                                        <span class="text-strong"><spring:message code="lbl.localizacao.usuario"/></span>                                                        
			                                                    </div>
			                                                </a>
			                                            </li>                                        
			                                            			                                                                                        
			                                        </ul>
			                                    </div><!-- /.panel-heading -->
			                                    <!--/ End tabs heading -->
			
			                                    <!-- Start tabs content -->
			                                    <div class="panel-body col-lg-9 col-md-9 col-sm-9">
			                                        <div class="tab-content">
			                                            <div class="tab-pane fade in active" id="tab4-1">
			                                            	 <div class="form-group">
			                                                		<strong class="col-sm-3"><spring:message code="lbl.nome.usuario"/>:</strong> ${usuarioForm.nome}
			                                            	 </div><!-- /.form-group -->
			                                            	 
			                                            	 <div class="form-group">
			                                                		<strong class="col-sm-3"><spring:message code="lbl.perfil.usuario"/>:  </strong> <c:if test="${usuarioForm.perfil == 'P'}">
																																		            	<spring:message code="lbl.perfil.usuario.cliente"/>
																																		            </c:if>
																																		            <c:if test="${usuarioForm.perfil == 'C'}">
																																		            	<spring:message code="lbl.perfil.usuario.corretor"/>
																																		            </c:if>
																																		             <c:if test="${usuarioForm.perfil == 'I'}">
																																		            	<spring:message code="lbl.perfil.usuario.imobiliaria"/>
																																		            </c:if>																																					
			                                            	 </div><!-- /.form-group -->
															 
															 <div class="form-group">																				            
								                              		<strong class="col-sm-3"><spring:message code="lbl.data.nascimento"/>: </strong> <fmt:formatDate value='${usuarioForm.dataNascimento}' pattern='dd/MM/yyyy'/>
								                              </div><!-- /.form-group -->
			                                            	 
			                                            	  <div class="form-group">																				            
								                              		<strong class="col-sm-3"><spring:message code="lbl.data.cadastro.usuario"/>: </strong> <fmt:formatDate value='${usuarioForm.dataCadastro}' pattern='dd/MM/yyyy'/>
								                              </div><!-- /.form-group -->
															  
															  <div class="form-group">																				            
								                              		<strong class="col-sm-3"><spring:message code="lbl.data.ultimo.acesso"/>: </strong> <fmt:formatDate value='${usuarioForm.dataUltimoAcesso}' pattern='dd/MM/yyyy'/>
								                              </div><!-- /.form-group -->
			                                            	 
															 <div class="form-group">																            
																  <strong class="col-sm-3"><spring:message code="lbl.cpf"/>: </strong>  ${usuario.cpf}																  
															 </div><!-- /.form-group -->			                                            	
			                                            	 
			                                            </div>
			                                            <div class="tab-pane fade" id="tab4-2">
			                                                <div class="form-group">																            
									                                  <strong class="col-sm-3"><spring:message code="lbl.estado"/>: </strong>  ${usuarioForm.estado}						                                  
				                                             </div><!-- /.form-group -->
				                                             
				                                             <div class="form-group">																            
									                                  <strong class="col-sm-3"><spring:message code="lbl.cidade"/>: </strong>  ${usuarioForm.cidade}						                                  
				                                             </div><!-- /.form-group -->
				                                             
				                                             <div class="form-group">																            
									                                  <strong class="col-sm-3"><spring:message code="lbl.bairro"/>: </strong>  ${usuarioForm.bairro}						                                  
				                                             </div><!-- /.form-group -->
				                                            
			                                            </div>                                            
			                                                                         
			                                        </div>
			                                       
			                                    </div><!-- /.panel-body -->
			                                    <!--/ End tabs content -->
			                                </div><!-- /.panel -->
			                            </div>
			                            <!--/ End vertical tabs with pagination -->
				                    </div><!-- /.profile-cover -->	                    
				                    <div class="divider"></div>
				           
				                <!--/ End type panel -->
			                </div>
	                </div>
					
					<!-- START --- lista dos servicos concedidos/revogados e seus respectivos usuarios envolvidos dados por este usuario administrador --> 					 
					<div class="row">
						<!-- Start scrollable panel -->
							<div class="panel panel-scrollable panel-info rounded shadow">
								<div class="panel-heading">			                                	
									<h3 class="panel-title"><spring:message code="lbl.admin.lista.servicos.concedidos"/></h3>
								</div><!-- /.panel-heading -->
								<div class="panel-body panel panel-info rounded shadow">
								
									<table class="table table-striped table-primary">
										<thead>
										<tr>
											<th class="text-center"><spring:message code="lbl.table.nome.servico"/></th>
											<th class="text-center"><spring:message code="lbl.table.status.servico"/></th>
											<th class="text-center"><spring:message code="lbl.table.data.sol.servico"/></th>
											<th class="text-center"><spring:message code="lbl.table.data.pagto.servico"/></th>                                                
											<th class="text-center"><spring:message code="lbl.table.concedido.revogado.nome.usuario"/></th>   
										</tr>
										</thead>
										<tbody>
											<c:if test="${ empty usuarioForm.listaServicos }"><
												<div class="panel" align="center">	                                
													<div class="panel-body">
														<spring:message code="msg.lista.servicos.vazio"/>
													</div><!-- /.panel-body -->
												</div><!-- /.panel -->
											</c:if>
											
											<c:if test="${ not empty usuarioForm.listaServicos }">
												<c:forEach var="servico" items="${usuarioForm.listaServicos}" >
													<tr>
														<td class="text-center">	                                                    
															<c:if test="${(servico.descServico == 'relatorioCorretor' || servico.descServico == 'relatorioCliente' || servico.descServico == 'relatorioImobiliaria')}" >
																<spring:message code="lbl.table.result.relatorio"/>
															</c:if>
															<c:if test="${(servico.descServico == 'assinaturaCorretor' || servico.descServico== 'assinaturaPadrao' || servico.descServico == 'assinaturaImobiliaria')}" >
																<spring:message code="lbl.table.result.assinatura"/>
															</c:if>				
															 <c:if test="${(servico.tipoServicoComum == 'N')}" >
																	${servico.labelServico}
															</c:if>		                				
														</td>
														<td class="text-center"><c:if test="${servico.statusPgto == 'aguardando'}"> <spring:message code="lbl.status.pagto.aguardando"/> </c:if>
																				<c:if test="${servico.statusPgto == 'pago'}"> <spring:message code="lbl.status.pagto.pago"/> </c:if>
																				<c:if test="${servico.statusPgto == 'solicitado'}"> <spring:message code="lbl.status.pagto.solicitado"/> </c:if>
																				<c:if test="${servico.statusPgto == 'concedido'}"> <spring:message code="lbl.status.pagto.concedido"/> </c:if></td>
														<td class="text-center"><fmt:formatDate value='${servico.dataSolicitacao}' pattern='dd/MM/yyyy'/></td>
														<td class="text-center"><fmt:formatDate value='${servico.dataPagto}' pattern='dd/MM/yyyy'/></td>		                                   
														<td class="text-center">
															 <a href="#">                                            
																<img src="${context}/${servico.imagemArquivo}" style="width: 100px; height: 80px; " alt="admin"/>	                                            
															</a>
														</td>           
													</tr>
												</c:forEach>
											</c:if>	
										</tbody>
									</table>
								</div><!-- /.panel-body -->
							</div><!-- /.panel -->
						<!--/ End scrollable panel --> 
				   </div>
					<!-- FIM --- lista dos servicos concedidos/revogados e seus respectivos usuarios envolvidos dados por este usuario administrador -->
					
					<!-- START --- lista dos planos concedidos/revogados e seus respectivos usuarios envolvidos dados por este usuario administrador -->
					<div class="row">
						  				
		                      		<!-- Start scrollable panel -->
			                            <div class="panel panel-scrollable panel-info rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title"><spring:message code="lbl.admin.lista.planos.concedidos.revogados"/></h3>
			                                </div><!-- /.panel-heading -->			                                
			                                <div class="panel-body panel panel-info rounded shadow">							                    
			                                			                                				                               	
			                                    <table class="table table-striped table-primary">
		                                            <thead>
		                                            <tr>
		                                                 <th class="text-center"><spring:message code="lbl.table.nome.servico"/></th>
														 <th class="text-center"><spring:message code="lbl.table.status.servico"/></th>
														 <th class="text-center"><spring:message code="lbl.table.data.sol.servico"/></th>
														 <th class="text-center"><spring:message code="lbl.table.data.pagto.servico"/></th>                                                
														 <th class="text-center"><spring:message code="lbl.table.concedido.revogado.nome.usuario"/></th>     
		                                            </tr>
		                                            </thead>
		                                            <tbody>
		                                            	<c:if test="${not  empty usuarioForm.listaPlanos }">
															<c:forEach var="plano" items="${usuarioForm.listaPlanos}" >
																<tr>
																	<td class="text-center">${plano.nomePlano}</td>
																	<td class="text-center"><c:if test="${plano.status== 'aguardando'}"> <spring:message code="lbl.status.pagto.aguardando"/> </c:if>
																							<c:if test="${plano.status == 'pago'}"> <spring:message code="lbl.status.pagto.pago"/> </c:if>
																							<c:if test="${plano.status == 'solicitado'}"> <spring:message code="lbl.status.pagto.solicitado"/> </c:if>
																							<c:if test="${plano.status == 'concedido'}"> <spring:message code="lbl.status.pagto.concedido"/> </c:if>
																							<c:if test="${plano.status == 'revogado'}"> <spring:message code="lbl.status.pagto.revogado"/> </c:if></td>
																	<td class="text-center"><fmt:formatDate value='${plano.dataSolicitacao}' pattern='dd/MM/yyyy'/></td>
																	<td class="text-center"><fmt:formatDate value='${plano.dataPagto}' pattern='dd/MM/yyyy'/></td>
																	<td class="text-center">																		  
																		   <a href="#">                                            
																				<img src="${context}/${servico.imagemArquivo}" style="width: 100px; height: 80px; " alt="admin"/>	                                            
																			</a>	
																	</td>
																</tr>
															</c:forEach>
														</c:if>
														
														<c:if test="${ empty usuarioForm.listaPlanos }">
															<div class="panel" align="center">	                                
								                                <div class="panel-body">
								                                    <spring:message code="msg.lista.plano.vazio"/>
								                                </div><!-- /.panel-body -->
								                            </div><!-- /.panel -->
							                            </c:if>														
		                                            </tbody>		                                            
		                                        </table>		                                        	
		                                        	
			                                </div><!-- /.panel-body -->			                                
			                            </div><!-- /.panel -->
			                        <!--/ End scrollable panel -->
		                       </div>
					
					<!-- END --- lista dos planos concedidos/revogados e seus respectivos usuarios envolvidos dados por este usuario administrador -->					
	            </div>
                 <!-- END  Painel de Informações -->

            </section><!-- /#page-content -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->  			
  			<c:import url="../layout-admin/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>