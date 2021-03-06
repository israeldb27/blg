<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/servico" var="urlServico"/>
<spring:url var="urlImovelFavoritos" value="/imovelFavoritos"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/> 
   
 <script type="text/javascript">
    	$(document).ready(function() {    		
    		
    	}
    	
    	function adicionarInteresse(id) {    	
    		var parametro1 = id;
    	    $.ajax({                
                url: '${urlImovelFavoritos}/adicionarFavoritos/' + parametro1,                
                success: function(){
                	$("#idMeInteressei_"+id).hide();
                	$("#idNovoMeInteressei_"+id).hide();
	    	    	$("#idInteressado_"+id).show();	    	    	
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });   
    	}
    	
    	function retirarInteresse(id) {
    		var parametro1 = id;    		
    	    $.ajax({                
                url: '${urlImovelFavoritos}/retirarFavoritos/' + parametro1,                
                success: function(){
                	$("#idNovoMeInteressei_"+id).show();                	
	    	    	$("#idInteressado_"+id).hide();
	    	    	$("#idNovoInteressado_"+id).hide();	    	    	
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });   
    	}  
 </script>
   
<html> 

    <head>
        <c:import url="../layout/head-layout.jsp"></c:import>
    </head>
   
    <body class="page-session page-sound page-header-fixed page-sidebar-fixed demo-dashboard-session">

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->
            	<c:import url="../layout/header.jsp"></c:import>
            <!--/ END HEADER -->
         
         	<!-- START SIDEBAR LEFT -->
            	<c:import url="../layout/sidebar-left.jsp"></c:import>
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
                                                <i class="fa fa-home"></i>
                                                <div>
                                                    <span class="text-strong"><spring:message code="lbl.title.link.imoveis"/></span>
                                                    <span></span>
                                                </div>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#tab2-2" data-toggle="tab">
                                                <i class="fa fa-user"></i>
                                                <div>
                                                    <span class="text-strong"><spring:message code="lbl.title.link.usuarios"/></span>
                                                    <span></span>
                                                </div>
                                            </a>
                                        </li>
                                        <% if ( request.getSession().getAttribute("habilitaFuncServicos").equals("S") ) {%>
                                       		 <li>
	                                            <a href="#tab2-3" data-toggle="tab">
	                                                <i class="fa fa-credit-card"></i>
	                                                <div>
	                                                    <span class="text-strong"><spring:message code="lbl.title.link.servicos"/></span>
	                                                    <span></span>
	                                                </div>
	                                            </a>
	                                        </li> 
                                        <% } %>
                                        
                                       <% if ( request.getSession().getAttribute("habilitaFuncPlanos").equals("S") ) {%>
	                                        <li>
	                                            <a href="#tab2-4" data-toggle="tab">
	                                                <i class="fa fa-check-circle"></i>
	                                                <div>
	                                                    <span class="text-strong"><spring:message code="lbl.title.link.planos"/></span>
	                                                    <span></span>													
	                                                </div>
	                                            </a>
	                                        </li>
                                        <% } %>
                                    </ul>
                                </div><!-- /.panel-heading -->
                                <!--/ End tabs heading -->

                                <!-- Start tabs content -->
                                <div class="panel-body">
                                    <div class="tab-content">
										
										<!-- START aba Imóveis -->	
                                        <div class="tab-pane fade in active" id="tab2-1">
											<c:if test="${not empty listaImoveis }">
												<div class="media-list list-search">
													<c:forEach var="imovel" items="${listaImoveis}" varStatus="item">	                                
														<div class="media rounded shadow no-overflow">
															   <div class="media-left">
					                                                <a href="${urlImovel}/detalhesImovel/${imovel.id}" >
					                                                   <span class="meta-provider ${imovel.classePorAcao}" style="font-size:19px;">${imovel.acaoFmt} <br>
					                                                   							<strong>  R$<fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
					                                                   </span><br>                                                   
					                                                    <img src="data:image/jpeg;base64,${imovel.imagemArquivo}" class="img-responsive" style="width: 260px; height: 250px; alt="admin"/>
					                                                </a>
					                                            </div>
															  <div class="media-body"> 
																  <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${imovel.tipoImovelFmt}</span>
		                                                		  <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlImovel}/detalhesImovel/${imovel.id}" style="color : #9d2428;">${imovel.titulo}</a></h4>
		                                                		  <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovel.endereco} - ${imovel.bairro} - ${imovel.cidade} -${imovel.uf} </h1>
																  
																  <div class="col-md-5" >  	                                                
					                                                	<div class="media-body" >
								                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.ultima.imovel.atualizacao" />: </font> <br> 
								                                            <span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${imovel.dataUltimaAtualizacao}' pattern='dd/MM/yyyy'/></font></span></em> 
								                                            
								                                            <br> <br>
								                                            
								                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.imovel" />: </font><span class="text-success"><br>				                                            
								                                            <font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em>				                                            
								                                            
								                                        </div>                                                  
					                                                    <br/> <br/> <br/> 	                                                   
					                                              </div>
																  
																  <div class="meta-search">
																  	  <div class="col-md-6" >
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
					                                                    					                                                    
					                                                  </div> 	                                          			                                          
																  </div>
																  
															 </div><!-- /.media -->
														  <div class="line"></div>
													  </div> 
												  </c:forEach>
												</div>
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
												<div class="media-list list-search">
													<c:forEach var="usuarioBusca" items="${listaUsuarios}" >	                                
														<div id="idUsuarioBusca" class="media rounded shadow no-overflow">
														    <div class="media-left">
				                                                <a href="${urlUsuario}/detalhesUsuario/${usuarioBusca.id}" >                                                                                                     
				                                                    <img  src="data:image/jpeg;base64,${usuarioBusca.imagemArquivo}" class="img-responsive" style="width: 260px; height: 245px; alt="admin"/>
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
				                                                            
				                                                     <br>                                                   		                                                  
				                                              </div>
				                                              	                                        	  
															  <div class="meta-search">
																   <div class="col-md-6" >
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
						                                                      
						                                                         <c:if test="${usuarioBusca.id != usuario.id}">
						                                                      	 	<a href="${urlImovel}/visualizarImoveisPerfilUsuario/${usuarioBusca.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.visualizar.imoveis.perfil.usuario"/>' ><i class="fa fa-home pull-left" style="color:gray"><font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.visualizar.imoveis.perfil.usuario"/> </font> </i>  </a>
						                                                      	 </c:if>
					                                                      	 											                                         
						                                                    <% } %>
				                                                  </div>                                                                                                           
														  	 </div>		                                          
														  </div><!-- /.media-body -->
													  </div><!-- /.media -->
													                              	  
												   </c:forEach>	
												</div>	
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
										<% if ( request.getSession().getAttribute("habilitaFuncServicos").equals("S") ) {%>									
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
                                        <% }  %>
                                        
										<!-- END aba Serviços -->									
										
										<!-- START aba Planos -->	
										<% if ( request.getSession().getAttribute("habilitaFuncPlanos").equals("S") ) {%>
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
                                        <% }  %>
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
                            <c:import url="../layout/sidebar-right.jsp"></c:import>
                        </div>  
                        
                    </div><!-- /.row --> 
                </div><!-- /.body-content -->  
           </section> 
         </section>   

       <c:import url="../layout/head-bootstrap.jsp"></c:import>
    </body>

</html>
