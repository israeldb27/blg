<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/mensagem" var="urlMensagem"/>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   
 
<script type="text/javascript">

$(document).ready(function() {

});	

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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.perfil.usuario"/> </h2>                                                                        				
                </div><!-- /.header-content -->                
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                    <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
						<c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                    <% } %>
                   		<!-- START Foto Perfil e Informações Contato -->
                   			<div class="col-lg-3 col-md-3 col-sm-4">

		                        <div class="panel rounded shadow">
		                            <div class="panel-body">
		                                <div class="inner-all">
		                                    <ul class="list-unstyled">
		                                        <li class="text-center">		                                        
		                                            <img class="img-circle img-bordered-primary" src="${context}${usuario.imagemArquivo}" style="width: 100px; height: 100px; " alt="Tol Lee">
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize">${usuario.nome}</h4>
		                                            <p class="text-muted text-capitalize">  ${usuario.perfilFmt} </p>
		                                        </li>
		                                    
		                                        <li><br/></li>
		                                        <li>   
		                                            <div class="btn-group-vertical btn-block">
		                                                <a href="#" class="btn btn-default"><i class="fa fa-cog pull-right"></i><spring:message code="lbl.title.editar.usuario"/></a>
		                                                <a href="${urlUsuario}/logout" class="btn btn-default"><i class="fa fa-sign-out pull-right"></i><spring:message code="lbl.title.logout"/></a>		                                                
		                                            </div>
		                                        </li>
		                                    </ul>
		                                </div>
		                            </div>
		                        </div><!-- /.panel -->
		
		                        <div class="panel panel-theme rounded shadow">
		                            <div class="panel-heading">
		                                <div class="pull-left">
		                                    <h3 class="panel-title"><spring:message code="lbl.title.perfil.usuario.info.contatos"/></h3>  
		                                </div>
		                                <div class="pull-right">
		                                    <a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>
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
                                            <li>
                                                <a href="#tab4-3" data-toggle="tab">
                                                    <i class="fa fa-credit-card"></i>
                                                    <div>
                                                        <span class="text-strong"><spring:message code="lbl.permissoes.usuario"/></span>                                                        
                                                    </div>
                                                </a>
                                            </li>
                                            
                                             <li>
                                                <a href="#tab4-4" data-toggle="tab">
                                                    <i class="fa fa-credit-card"></i>
                                                    <div>
                                                        <span class="text-strong"><spring:message code="lbl.estatiscas.usuario"/></span>                                                        
                                                    </div>
                                                </a>
                                            </li>                                            
                                        </ul>
                                    </div><!-- /.panel-heading -->                                    

                                    <!-- Start tabs content -->
                                    <div class="panel-body col-lg-9 col-md-9 col-sm-9">
                                        <div class="tab-content">
                                            <div class="tab-pane fade in active" id="tab4-1">
                                            	 <div class="form-group">
                                                		<strong class="col-sm-3"><spring:message code="lbl.nome.usuario"/>:</strong> ${usuario.nome}
                                            	 </div><!-- /.form-group -->
                                            	 
                                            	 <div class="line"></div>
                                            	 
                                            	 <div class="form-group">
                                                		<strong class="col-sm-3"><spring:message code="lbl.perfil.usuario"/>:  </strong> ${usuario.perfilFmt}
                                            	 </div><!-- /.form-group -->
                                            	 
                                            	 <div class="line"></div>
                                            	 
                                            	 <div class="form-group">																				            
					                              		<strong class="col-sm-3"><spring:message code="lbl.data.nascimento"/>: </strong> <fmt:formatDate value='${usuario.dataNascimento}' pattern='dd/MM/yyyy'/>
					                              </div><!-- /.form-group -->
					                              
					                              <div class="line"></div>
                                            	 
                                            	  <div class="form-group">																				            
					                              		<strong class="col-sm-3"><spring:message code="lbl.data.cadastro.usuario"/>: </strong> <fmt:formatDate value='${usuario.dataCadastro}' pattern='dd/MM/yyyy'/>
					                              </div><!-- /.form-group -->
					                              
					                              <div class="line"></div>
                                            	 
                                            	 <c:if test="${((usuario.perfil == 'P') || (usuario.perfil == 'C')) }">
	                                            	 <div class="form-group">																            
						                                  <strong class="col-sm-3"><spring:message code="lbl.cpf"/>: </strong>  ${usuario.cpf}						                                  
	                                            	 </div><!-- /.form-group -->
	                                            	 
	                                            	 <div class="line"></div>
                                            	 </c:if>
                                            	 
                                            	  <c:if test="${usuario.perfil == 'I'}">
                                            	  		<div class="form-group">																				            
					                                    	<strong class="col-sm-3"><spring:message code="lbl.cnpj"/> </strong>: ${usuario.cpf}
					                                    </div><!-- /.form-group -->
					                                    
					                                    <div class="line"></div>	
					                              </c:if>
					                               
					                              <c:if test="${usuario.perfil == 'C'}">	
                                            	  		<div class="form-group">																				            
					                                    	<strong class="col-sm-3"><spring:message code="lbl.creci"/>: </strong> ${usuario.creci}
					                                    </div><!-- /.form-group -->	
					                                    
					                                    <div class="line"></div>
					                              </c:if>
					                              
					                              <div class="form-group">
						                                  <strong class="col-sm-3"><spring:message code="lbl.sexo"/>: </strong>
										        	     		<c:if test="${usuario.sexo == 	'M'}"> <spring:message code="lbl.sexo.masculino"/></c:if>
										        	     		<c:if test="${usuario.sexo == 	'F'}"> <spring:message code="lbl.sexo.feminino"/></c:if>						                                  
	                                             </div><!-- /.form-group -->	
					                              
                                            </div>
                                            <div class="tab-pane fade" id="tab4-2">
                                                <div class="form-group">																            
						                                  <strong class="col-sm-3"><spring:message code="lbl.estado"/>: </strong>  ${usuario.estado}						                                  
	                                             </div><!-- /.form-group -->
	                                             
	                                             <div class="line"></div>
	                                             
	                                             <div class="form-group">																            
						                                  <strong class="col-sm-3"><spring:message code="lbl.cidade"/>: </strong>  ${usuario.cidade}						                                  
	                                             </div><!-- /.form-group -->
	                                             
	                                             <div class="line"></div>
	                                             
	                                             <div class="form-group">																            
						                                  <strong class="col-sm-3"><spring:message code="lbl.bairro"/>: </strong>  ${usuario.bairro}						                                  
	                                             </div><!-- /.form-group -->
	                                            
                                            </div>
                                            <div class="tab-pane fade" id="tab4-3">
                                                <div class="form-group">
						                                  <strong class="col-sm-7"><spring:message code="lbl.permissao.busca.usuario"/>: </strong>
										        	     		<c:if test="${usuario.habilitaBusca == 	'S'}"> <spring:message code="lbl.sim"/> </c:if>
										        	     		<c:if test="${usuario.habilitaBusca == 	'N'}"> <spring:message code="lbl.nao"/> </c:if>						                                  
	                                             </div><!-- /.form-group -->	
	                                             
	                                             <div class="line"></div>
	                                             
	                                             <div class="form-group">
						                                  <strong class="col-sm-7"><spring:message code="lbl.permissao.exibir.detalhes.usuario"/>: </strong>
											    					<c:if test="${usuario.habilitaDetalhesInfoUsuario == 	'S'}"> <spring:message code="lbl.sim"/> </c:if>
											        	     		<c:if test="${usuario.habilitaDetalhesInfoUsuario == 	'N'}"> <spring:message code="lbl.nao"/>  </c:if>						                                  
	                                             </div><!-- /.form-group -->	
	                                             
	                                             <div class="line"></div>	                                             
                                            </div> 
                                            
                                            <div class="tab-pane fade" id="tab4-4">
                                            	<div class="form-group">
						                              <strong class="col-sm-5"><spring:message code="lbl.total.imoveis"/>:</strong> ${usuario.quantTotalImoveis}						                                  
	                                             </div><!-- /.form-group -->
	                                             
	                                             <div class="line"></div>
	                                             
	                                             <div class="form-group">
						                              <strong class="col-sm-5"><spring:message code="lbl.total.contato"/>:</strong> ${usuario.quantTotalContatos}						                                  
	                                             </div><!-- /.form-group -->
	                                             
	                                             <div class="line"></div>
	                                             
	                                             <c:if test="${usuario.perfil != 'P'}">
	                                             	<div class="form-group">
	                                             		<strong class="col-sm-5"><spring:message code="lbl.total.parcerias"/>:</strong> ${usuario.quantTotalParcerias}
	                                             	</div><!-- /.form-group -->	
	                                             	<div class="line"></div>
	                                             </c:if>
	                                             
	                                             <div class="form-group">
	                                             		<strong class="col-sm-5"><spring:message code="lbl.total.interessados"/>:</strong> ${usuario.quantTotalInteressadosImoveis}
	                                             </div><!-- /.form-group -->	                                            
	                                             
	                                             <div class="line"></div>
	                                             
	                                             <c:if test="${usuarioForm.perfil != 'P'}">
	                                             	<div class="form-group">
	                                             		<strong class="col-sm-5"><spring:message code="lbl.total.intermediacoes"/>:</strong> ${usuario.quantTotalIntermediacoes}
	                                             	</div><!-- /.form-group -->
	                                             	
	                                             	<div class="line"></div>
	                                             </c:if>
	                                             
	                                             <div class="form-group">
	                                             	  <strong class="col-sm-5"><spring:message code="lbl.total.visualizacoes"/>:</strong> ${usuario.quantTotalVisitasImoveis}
	                                             </div><!-- /.form-group -->
	                                             
	                                             <div class="line"></div>
	                                             
	                                             <div class="form-group">
	                                             	  <strong class="col-sm-5"><spring:message code="lbl.total.aprovacoes.usuario"/>:</strong> ${usuario.quantTotalAprovacoesUsuario}
	                                             </div><!-- /.form-group -->
	                                             
	                                             <div class="line"></div>
	                                             
	                                             <div class="form-group">
	                                             	  <strong class="col-sm-5"><spring:message code="lbl.total.desaprovacoes.usuario"/>:</strong> ${usuario.quantTotalDesaprovacoesUsuario}
	                                             </div><!-- /.form-group -->                                             	
                                            </div>                               
                                        </div>                                       
                                    </div><!-- /.panel-body -->                                  
                                </div><!-- /.panel -->
                            </div>                            
	                    </div><!-- /.profile-cover -->	                    
	                    <div class="divider"></div>
	                    
	                    <div class="panel rounded shadow">
	                    
	                    	<div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.sobre.mim"/></h3>
                                    </div>                                    
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->	
	                        <form action="...">	                        	
	                            <textarea class="form-control input-lg no-border" rows="2" placeholder="${usuario.descSobreMim}"></textarea>
	                        </form>	                        
	                    </div><!-- /.panel -->
	                    
	                    	<c:if test="${usuario.perfil != 'P'}">
		                       <!-- /.START IMOVEIS EM DESTAQUE -->	
		                       <div class="row">
		                       		<c:if test="${ not empty usuario.listaImoveisDestaque }">
			                      		<!-- Start scrollable panel -->
				                            <div class="panel panel-scrollable panel-theme rounded shadow">
				                                <div class="panel-heading">
				                                    <h3 class="panel-title"><spring:message code="lbl.imoveis.destaque"/></h3>
				                                </div><!-- /.panel-heading -->
				                                <div class="panel-body panel panel-theme rounded shadow">
				                                    <c:forEach var="imovel" items="${usuario.listaImoveisDestaque}" varStatus="item">	                                
															<div class="media inner-all mt-10">
							                                   <div class="media-left">
							                                        <a href="#">                                            
							                                            <img src="${context}${imovel.imagemArquivo}" style="width: 230px; height: 150px; " alt="admin"/>	                                            
							                                        </a>
							                                    </div>
							                                  <div class="media-body">
							                                  	  <h4 class="media-heading"><a href="#">${imovel.titulo}</a></h4>
					                                        	  <p><strong>${imovel.bairro} - ${imovel.cidade} - ${imovel.estado}</strong></p>
					                                        	  <div class="meta-search">
																	 <label><spring:message code="lbl.data.cadastro.imovel"/>: </label> <small><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></small> </br>
																	 <label><spring:message code="lbl.tipo.imovel"/>:</label> <small>${imovel.tipoImovelFmt} </small> </br>
																	 
																	 
																	 <ul class="inner-all list-unstyled">								                                       
								                                        <li>  
								                                            <a href="#" onClick="carregaDetalhesImovel(${imovel.id})"  class="btn btn-success text-center btn-block" style="width: 40%">Detalhes</a>
								                                        </li>
								                                        
								                                    </ul><!-- /.list-unstyled -->																	 
																	 																																				
																  </div>		                                          
							                                  </div><!-- /.media-body -->
							                                </div><!-- /.media -->						                              	  
						                              	  <div class="line"></div>
					            					</c:forEach>
				                                </div><!-- /.panel-body -->
				                            </div><!-- /.panel -->
				                        <!--/ End scrollable panel -->
			                        </c:if> 
			                        
			                        <c:if test="${ empty usuario.listaImoveisDestaque }">
			                        	<div class="panel panel-scrollable panel-theme rounded shadow">
				                                <div class="panel-heading">
				                                    <h3 class="panel-title"><spring:message code="lbl.imoveis.destaque"/></h3>
				                                </div><!-- /.panel-heading -->
				                                <div class="panel-body">
				                                    <spring:message code="lbl.nenhum.imovel.retornado"/>  
				                                </div><!-- /.panel-body -->
				                            </div><!-- /.panel -->  
			                        </c:if>
		                       </div>	
		                       <!-- /.END IMOVEIS EM DESTAQUE -->
	                       </c:if>
	                      
	                      <!-- /.START OUTROS IMOVEIS -->	
	                      		<div class="row">
		                      		<!-- Start scrollable panel -->
			                            <div class="panel panel-scrollable panel-theme rounded shadow">
			                                <div class="panel-heading">
			                                	<c:if test="${usuario.perfil != 'P'}">
			                                    	<h3 class="panel-title"><spring:message code="lbl.outros.imoveis"/></h3>			                                    	
			                                    </c:if>
			                                    <c:if test="${usuario.perfil == 'P'}">
			                                    	<h3 class="panel-title"><spring:message code="lbl.imoveis.usuario"/></h3>
			                                    </c:if>
			                                </div><!-- /.panel-heading -->
			                                <c:if test="${ not empty usuario.listaOutrosImoveis }">
				                                <div class="panel-body panel panel-theme rounded shadow">
				                                    <c:forEach var="imovel" items="${usuario.listaOutrosImoveis}" varStatus="item">	                                
															<div class="media inner-all mt-10">
							                                   <div class="media-left">
							                                        <a href="#">                                            
							                                            <img src="${context}${imovel.imagemArquivo}" style="width: 230px; height: 150px; " alt="admin"/>	                                            
							                                        </a>
							                                    </div>
							                                  <div class="media-body">
							                                  	  <h4 class="media-heading"><a href="#">${imovel.titulo}</a></h4>
					                                        	  <p><strong>${imovel.bairro} - ${imovel.cidade} - ${imovel.estado}</strong></p>
					                                        	  
					                                        	  <div class="meta-search">
									                                        <label><spring:message code="lbl.data.cadastro.imovel"/>: </label> <small><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></small> </br>
									                                        <label><spring:message code="lbl.tipo.imovel"/>:</label> <small>${imovel.tipoImovelFmt} </small> </br>
									                                        <a href="#" onClick="carregaDetalhesImovel(${imovel.id})"  class="btn btn-success text-center btn-block" style="width: 40%">Detalhes</a>																			
																  </div>		                                          
										                      </div><!-- /.media-body -->
										                    </div><!-- /.media -->
									                              	  
									                       <div class="line"></div>
								            		 </c:forEach>
				                                </div><!-- /.panel-body -->
			                                </c:if>
			                                <c:if test="${ empty usuario.listaOutrosImoveis }">
			                                	  <div class="panel-body">
					                                    <spring:message code="lbl.nenhum.imovel.retornado"/>  
					                                </div><!-- /.panel-body -->  
			                                </c:if>
			                            </div><!-- /.panel -->
			                        <!--/ End scrollable panel --> 
		                       </div>
	                      <!-- /.END OUTROS IMOVEIS -->	   
	                      
	                      <!-- /.START CONTATOS DO USUARIO -->
	                      	   <div class="row">
		                      		<!-- Start scrollable panel -->
			                            <div class="panel panel-scrollable panel-theme rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title"><spring:message code="lbl.contatos"/></h3>
			                                </div><!-- /.panel-heading -->
			                                <div class="panel-body panel panel-theme rounded shadow">
			                                    <c:if test="${ not empty usuario.listaContatosUsuario }">
							                    	<c:forEach var="usuarioContato" items="${usuario.listaContatosUsuario}">
								                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
								                            <div class="panel rounded shadow">
								                                <div class="panel-body ">
								                                    <div class="ribbon-wrapper">
								                                        
								                                    </div><!-- /.ribbon-wrapper -->
								                                    <ul class="inner-all list-unstyled">
								                                        <li class="text-center">
								                                            <img class="img-circle img-bordered-success" src="${context}/${usuarioContato.imagemArquivo}" style="width: 100px; height: 100px; ">
								                                        </li>
								                                        <li class="text-center">
								                                            <h4 class="text-capitalize">${usuarioContato.nome}</h4>
								                                            <p class="text-muted text-capitalize">${usuarioContato.perfilFmt} </p>
								                                        </li>
								                                        <li>  
								                                            <a href="#" onClick="carregaDetalhesUsuario(${usuarioContato.id})"  class="btn btn-success text-center btn-block"><spring:message code="lbl.detalhes.usuario"/></a>
								                                        </li>
								                                        
								                                    </ul><!-- /.list-unstyled -->
								                                </div><!-- /.panel-body -->
								                            </div><!-- /.panel -->
								                        </div>
							                        </c:forEach>
							                    </c:if>
												
												<c:if test="${ empty usuario.listaContatosUsuario }">									                 		 
															<spring:message code="lbl.nenhum.contato.retornado"/>
												 </c:if> 
			                                </div><!-- /.panel-body -->
			                            </div><!-- /.panel -->
			                        <!--/ End scrollable panel --> 
		                       </div>	
	                      
	                      <!-- /.END CONTATOS DO USUARIO -->
	                      
	                        <!-- /.START NOTAS DO USUARIO -->
	                      	   <div class="row">
		                      		<!-- Start scrollable panel -->
			                            <div class="panel panel-scrollable panel-theme rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title"><spring:message code="lbl.notas.usuario"/></h3>
			                                </div><!-- /.panel-heading -->
			                                <div class="panel-body panel panel-theme rounded shadow">
			                                    <c:if test="${ not empty usuario.listaNotasUsuario }">
													  <c:forEach var="nota" items="${usuario.listaNotasUsuario}"> 		                                	
														<div class="media inner-all">
							                                  <div class="pull-left">
							                                         <span class="fa fa-stack fa-2x">
							                                         	<c:if test="${((nota.acao == 'parceria') || (nota.acao == 'preferencia') || (nota.acao == 'usuario') )}">                                         	
							                                              	<img class="img-circle img-bordered-success" src="${context}/${nota.usuario.imagemArquivo}" style="width: 60px; height: 60px; " alt="admin"/>
							                                            </c:if>                                               	 
							                                            <c:if test="${(nota.acao == 'imovel')}">
							                                            	<img src="${context}/${nota.imovel.imagemArquivo}" style="width: 60px; height: 60px; " alt="admin"/>
							                                            </c:if>
							                                         </span>
							                                  </div><!-- /.pull-left -->
							                                  <div class="media-body">
							                                  
							                                  	  <c:if test="${nota.acao == 'parceria'}">
							                                  	  	<a href="#" class="h4"><spring:message code="lbl.nota.parceria"/></a>
							                                  	  </c:if>
							                                  	  
							                                  	   <c:if test="${nota.acao == 'preferencia'}">
							                                  	  	<a href="#" class="h4"><spring:message code="lbl.nota.preferencia"/></a>
							                                  	  </c:if>
							                                  	  
							                                  	  <c:if test="${nota.acao == 'usuario'}">
							                                  	  	<a href="#" class="h4"><spring:message code="lbl.nota.info.usuario"/></a>
							                                  	  </c:if>
							                                  	 
							                                  	  <c:if test="${nota.acao == 'imovel'}">
							                                  	  	<a href="#" class="h4"><spring:message code="lbl.nota.imovel"/> </a>
							                                  	  </c:if> 
							                                      
							                                      <c:if test="${nota.acao == 'imovel'}">							                                  	  	
							                                  	  	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="#" onClick="carregaDetalhesImovel(${nota.imovel.id})"><strong>${nota.imovel.titulo}</strong></a></small>
							                                  	  </c:if>
							                                  	  
							                                  	  <c:if test="${nota.acao == 'usuario'}">							                                  	  	
							                                  	  	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="#" onClick="carregaDetalhesUsuario(${nota.usuario.id})"><strong>${nota.usuario.nome} </strong></a></small>
							                                  	  </c:if>
							                                  	  
							                                  	  <c:if test="${nota.acao == 'parceria'}">
							                                  	  	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="#" onClick="carregaDetalhesImovel(${nota.imovel.id})" ><strong>${nota.imovel.titulo} </strong></a></small>
							                                  	  </c:if>
							                                      
							                                      <em class="text-xs text-muted"><spring:message code="lbl.data.nota"/> <span class="text-danger"><fmt:formatDate value='${nota.dataNota}' pattern='dd/MM/yyyy'/></span></em>
							                                  </div><!-- /.media-body -->
							                              </div><!-- /.media -->
					                              		  <div class="line"></div>
					                              	  </c:forEach>	  
				                              	  </c:if>	
				                              	    <c:if test="${ empty usuario.listaNotasUsuario }">
										            	<p><spring:message code="lbl.nenhuma.nota"/></p>
										            </c:if>
			                                </div><!-- /.panel-body -->
			                            </div><!-- /.panel -->
			                        <!--/ End scrollable panel --> 
		                       </div>	
	                      
	                      <!-- /.END NOTAS DO USUARIO -->
	                      
	                      <c:if test="${usuario.perfil == 'P'}">
	                      <!-- /.START PREFERENCIAS DO USUARIO -->
	                      	   <div class="row">
		                      		<!-- Start scrollable panel -->
			                            <div class="panel panel-scrollable panel-theme rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title"><spring:message code="lbl.pref.imoveis"/></h3>
			                                </div><!-- /.panel-heading -->
			                                <div class="panel-body panel panel-theme rounded shadow">
			                                    <table class="table table-striped table-primary">
		                                            <thead>
		                                            <tr>
		                                                <th class="text-center"><spring:message code="lbl.tipo.imovel"/></th>
		                                                <th class="text-center"><spring:message code="lbl.acao.imovel"/></th>
		                                                <th class="text-center"><spring:message code="lbl.estado"/></th>
		                                                <th class="text-center"><spring:message code="lbl.cidade"/></th>
		                                                <th class="text-center"><spring:message code="lbl.bairro"/></th>
		                                            </tr>
		                                            </thead>
		                                            <tbody>
		                                            <c:forEach var="pref" items="${usuario.listaPreferenciaImoveis}" >
			                                            <tr>
			                                                <td class="text-center">${pref.tipoImovelFmt} </td>
			                                                <td class="text-center">${pref.acaoFmt}</td>
			                                                <td class="text-center">${pref.nomeEstado}</td>
			                                                <td class="text-center">${pref.nomeCidade}</td>
			                                                <td class="text-center">${pref.nomeBairro}</td>			                                              
			                                            </tr>
		                                            </c:forEach>
		                                            </tbody>
		                                        </table>
			                                </div><!-- /.panel-body -->
			                            </div><!-- /.panel -->
			                        <!--/ End scrollable panel --> 
		                       </div>		                      
	                      <!-- /.END PREFERENCIAS DO USUARIO -->	
						  
	                      </c:if>	                    	                    
	                    </div>                         
                    </div><!-- /.row -->

                </div><!-- /.body-content -->     
         
            </section><!-- /#page-content -->
			
			  <!-- Start content modal Usuario Detalhes-->
					<c:import url="../ajuda/usuarioDetalhesModal.jsp"></c:import>																				
			  <!-- End content  modal Usuario Detalhes -->	
			
			  <!-- Start content modal Imovel Detalhes-->
					<c:import url="../ajuda/imovelDetalhesModal.jsp"></c:import>																				
			  <!-- End content  modal Imovel Detalhes -->
			
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>