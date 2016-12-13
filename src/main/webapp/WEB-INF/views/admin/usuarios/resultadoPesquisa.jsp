<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/servico" var="urlServico"/>
<spring:url value="/admin" var="urlAdmin"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/> 
   
<html> 

    <head>
        <c:import url="../layout-admin/head-layout.jsp"></c:import>
        
    </head>
   
    <body class="page-session page-sound page-header-fixed page-sidebar-fixed demo-dashboard-session">

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->
            	<c:import url="../layout-admin/header.jsp"></c:import>
            <!--/ END HEADER -->
         
         	<!-- START SIDEBAR LEFT -->
            	<c:import url="../layout-admin/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->
			
			<section id="page-content">	   
                <div class="body-content animated fadeIn">
					  <div class="row">
                        <div class="col-md-9">
                        
                          <c:if test="${msgErroPesquisarTudo == null }">                                    	
                            <!-- Start double tabs -->
                            <div class="panel panel-tab panel-tab-double rounded shadow">
                                <!-- Start tabs heading -->
                                <div class="panel-heading no-padding">
                                    <ul class="nav nav-tabs">
                                        <li class="active">
                                            <a href="#tab2-1" data-toggle="tab">
                                                <i class="fa fa-user"></i>
                                                <div>
                                                    <span class="text-strong"><spring:message code="lbl.title.link.imoveis"/></span>
                                                    <span></span>
                                                </div>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#tab2-2" data-toggle="tab">
                                                <i class="fa fa-file-text"></i>
                                                <div>
                                                    <span class="text-strong"><spring:message code="lbl.title.link.usuarios"/></span>
                                                    <span></span>
                                                </div>
                                            </a>
                                        </li>
                                       
                                       		 <li>
	                                            <a href="#tab2-3" data-toggle="tab">
	                                                <i class="fa fa-credit-card"></i>
	                                                <div>
	                                                    <span class="text-strong"><spring:message code="lbl.title.link.servicos"/></span>
	                                                    <span></span>
	                                                </div>
	                                            </a>
	                                        </li> 
                                       
	                                        <li>
	                                            <a href="#tab2-4" data-toggle="tab">
	                                                <i class="fa fa-check-circle"></i>
	                                                <div>
	                                                    <span class="text-strong"><spring:message code="lbl.title.link.planos"/></span>
	                                                    <span></span>													
	                                                </div>
	                                            </a>
	                                        </li>
                                       
                                    </ul>
                                </div><!-- /.panel-heading -->
                                <!--/ End tabs heading -->

                                <!-- Start tabs content -->
                                <div class="panel-body">
                                    <div class="tab-content">
										
										<!-- START aba Imóveis -->	
                                        <div class="tab-pane fade in active" id="tab2-1">
											<c:if test="${not empty listaImoveis }">
												<c:forEach var="imovel" items="${listaImoveis}" varStatus="item">	                                
													<div class="media inner-all mt-10">
														   <div class="media-left">
																<a href="${urlAdmin}/detalhesImovel/${imovel.id}">                                            
																	<img src="${context}${imovel.imagemArquivo}" style="width: 230px; height: 150px; " alt="admin"/>	                                            
																</a>
															</div>
														  <div class="media-body">
															  <h4 class="media-heading"><a href="${urlAdmin}/detalhesImovel/${imovel.id}">${imovel.titulo}</a></h4>
															  <p><strong>${imovel.bairro} - ${imovel.cidade} - ${imovel.estado}</strong></p>
															  
															  <div class="meta-search">
																	<label><spring:message code="lbl.data.cadastro.imovel"/>: </label> <small><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></small> </br>
																	<label><spring:message code="lbl.tipo.imovel"/>:</label> <small>${imovel.tipoImovelFmt}  </small> </br>
																	
																	<a href="${urlAdmin}/detalhesImovel/${imovel.id}" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> <spring:message code="lbl.btn.ver.imovel.geral"/></a>
	                                          			                                          
															  </div><!-- /.media-body -->
														 </div><!-- /.media -->
													  <div class="line"></div>
												  </div> 
											  </c:forEach>
										  </c:if>	  
										  
										  <c:if test="${ empty listaImoveis }">
												<div class="panel">	                                
													<div class="panel-body">
														<spring:message code="lbl.nenhum.imovel.retornado"/> 
													</div><!-- /.panel-body -->
												</div><!-- /.panel -->                            
											</c:if>
                                        </div>										
										<!-- END aba Imóveis -->	
										
										<!-- START aba Usuários -->
                                        <div class="tab-pane fade" id="tab2-2">
											<c:if test="${ not empty listaUsuarios }">
                                            	<c:forEach var="usuarioBusca" items="${listaUsuarios}" >	                                
													<div class="media inner-all mt-10">
													   <div class="media-left">
															<a href="${urlAdmin}/detalhesUsuario/${usuarioBusca.id}">                                            
																<img src="${context}/${usuarioBusca.imagemArquivo}" style="width: 230px; height: 150px; " alt="admin"/>	                                            
															</a>
														</div>
													  <div class="media-body">
														  <h4 class="media-heading"><a href="${urlAdmin}/detalhesUsuario/${usuarioBusca.id}">${usuarioBusca.nome}</a></h4>
														  <p><strong>${usuarioBusca.bairro} - ${usuarioBusca.cidade} - ${usuarioBusca.estado}</strong></p>	                                        	  
														  <div class="meta-search">
															<label><spring:message code="lbl.data.cadastro.usuario"/>: </label> <small><fmt:formatDate value='${usuarioBusca.dataCadastro}' pattern='dd/MM/yyyy'/></small> </br>
															<label><spring:message code="lbl.perfil.usuario"/>:</label> <small> ${usuarioBusca.perfilFmt} </small> </br>
															<a href="${urlAdmin}/detalhesUsuario/${usuarioBusca.id}" class="btn btn-sm btn-primary btn-xs btn-push" ><i class="fa fa-eye"></i><spring:message code="lbl.detalhes.usuario"/></a>                                
													  </div>		                                          
													  </div><!-- /.media-body -->
												  </div><!-- /.media -->
												  <div class="line"></div>		                              	  
											   </c:forEach>	
											 </c:if>
											
											<c:if test="${ empty listaUsuarios }">
												<div class="panel">	                                
													<div class="panel-body">
														<spring:message code="msg.sem.retorno.busca.usuario"/>
													</div><!-- /.panel-body -->
												</div><!-- /.panel -->                            
											</c:if>	
                                        </div>
										<!-- END aba Usuários -->										
										
										<!-- START aba Serviços -->																			
                                        <div class="tab-pane fade" id="tab2-3">
                                            <div class="row">
												<div class="col-md-12">
													<c:if test="${ not empty listaServicos }">	                  
														<c:forEach var="servico" items="${listaServicos}">
															<div class="blog-item rounded shadow">
																<div class="row">
																	 <div class="col-md-2 col-sm-3 col-xs-6">
																			<div class="panel panel-info rounded shadow">
																				<div class="panel-heading text-center">
																					<p class="inner-all no-margin">
																						<i class="fa fa-building-o fa-5x"></i>
																					</p>
																				</div><!-- /.panel-heading -->																			
																			</div><!-- /.panel -->
																	  </div>
																	
																	   <div class="col-md-10">
																			 <div class="panel-body no-padding">
										                                        <div class="media-body">
																					  <h4 class="media-heading"><a href="#">${servico.labelServico}</a></h4>																			  
																					  <div class="meta-search">
																							<a href="${urlServico}/selecionarServicoDisponivelPesquisa/${servico.id}" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> Adquirir Serviço</a>																																				                                          			                                          
																					  </div><!-- /.media-body -->
																				 </div><!-- /.media -->
																			</div>
																		</div>																																																	 
															 </div>   
														  </div><!-- /.blog-item -->
														</c:forEach>
													</c:if>
													<c:if test="${ empty listaServicos }">	                  
														<div class="panel">	                                
															<div class="panel-body">
																<spring:message code="msg.lista.servicos.encontrado"/>
															</div><!-- /.panel-body -->
														</div><!-- /.panel -->   
													</c:if>
												</div>
											</div>	
                                        </div>
                                        
										<!-- END aba Serviços -->									
										
										<!-- START aba Planos -->	
										
                                        <div class="tab-pane fade" id="tab2-4">
                                           <div class="row">
												<div class="col-md-12">
													<c:if test="${ not empty listaPlanos }">	                  
														<c:forEach var="plano" items="${listaPlanos}">
															<div class="blog-item rounded shadow">
																<div class="row">
																	 <div class="col-md-2 col-sm-3 col-xs-6">
																			<div class="panel panel-info rounded shadow">
																				<div class="panel-heading text-center">
																					<p class="inner-all no-margin">
																						<i class="fa fa-university fa-5x"></i>
																					</p>
																				</div><!-- /.panel-heading -->																			
																			</div><!-- /.panel -->
																	  </div>
																	
																	   <div class="col-md-10">
																			 <div class="panel-body no-padding">
										                                        <div class="media-body">
																					  <h4 class="media-heading"><a href="#">${plano.nome}</a></h4>																			  
																					  <div class="meta-search">
																							<a href="${urlServico}/selecionarPlanoDisponivelPesquisa/${plano.id}" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> Adquirir Plano</a>																																				                                          			                                          
																					  </div><!-- /.media-body -->
																				 </div><!-- /.media -->
																			</div>
																		</div>																																																	 
															   </div>   
														  </div><!-- /.blog-item -->														
														</c:forEach>
													</c:if>
													<c:if test="${ empty listaPlanos }">	                  
														<div class="panel">	                                
															<div class="panel-body">
																<spring:message code="msg.lista.plano.encontrado"/>
															</div><!-- /.panel-body -->
														</div><!-- /.panel -->   
													</c:if>
												</div>
											</div>	
                                        </div>                                       
										<!-- END aba Planos -->	
                                    </div>
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->
                            </c:if>
                            
                            <c:if test="${msgErroPesquisarTudo != null }">   
                            	<div class="panel panel-danger">
			                          <div class="panel-heading">
			                              <h3 class="panel-title">${msgErroPesquisarTudo}</h3>
			                          </div><!-- /.panel-heading -->			                         
			                      </div><!-- /.panel -->     
                            </c:if>
                            
                        </div>
                        
                          <div class="col-md-3">
                            <c:import url="../layout-admin/sidebar-right.jsp"></c:import>
                        </div>  
                        
                    </div><!-- /.row --> 
                </div><!-- /.body-content -->  
           </section> 
         </section>   

       <c:import url="../layout-admin/head-bootstrap.jsp"></c:import>
    </body>

</html>
