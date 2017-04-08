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

function enviarConvite(id) {
    $.post("${urlContato}/enviarConvite", {'idUsuario' : id}, function() {
      // selecionando o elemento html através da 
      // ID e alterando o HTML dele    	
    });
}


function concederPlano(){	
	var x=document.getElementById("idPlanoSelecionado");	
	$.post("${urlAdmin}/concederPlano", {'idPlanoSelecionado' : x.value}, function() {		
		location.reload();
    });
}

function concederServico(){	
	var x=document.getElementById("servicoSelecionado");	
	var y=document.getElementById("quantidadeTempo");
	$.post("${urlAdmin}/concederServico", {'servicoSelecionado' : x.value, 'quantidadeTempo' : y.value}, function() {		
		location.reload();
    });
}

function adicionarSuspensaoUsuario(){	
	var x=document.getElementById("tempoSuspensao");	
	var y=document.getElementById("motivoSuspensao");
	$.post("${urlAdmin}/suspenderUsuario", {'tempoSuspensao' : x.value, 'motivoSuspensao' : y.value}, function() {		
		location.reload();
    });
	
}

function cancelarSuspensaoUsuario(){	
	
	$.post("${urlAdmin}/cancelarSuspensaoUsuario", {}, function() {		
		location.reload();
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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.detalhes.usuario"/> </h2>                                                                        
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
		                                            <p class="text-muted text-capitalize"> ${usuarioForm.perfilFmt} </p>
		                                        </li>
		                                    
		                                        <li><br/></li>
		                                        <li>   
		                                            <div class="btn-group-vertical btn-block">		                                           
		                                              	<a href="${urlMensagemAdmin}/prepararNovaMensagemPorAdmin/${usuarioForm.id}" class="btn btn-default" ><i class="fa fa-cog pull-right"></i><spring:message code="lbl.enviar.mensagem"/></a>
		                                              	<c:if test="${(usuarioForm.statusUsuario == 'L') || (usuarioForm.statusUsuario == 'C')}">	                                                
															<button type="button" class="btn btn-default" data-toggle="modal" data-target=".bs-example-modal-lg-suspender-usuario">  <spring:message code="lbl.admin.suspender.usuario"/></button>
														</c:if>
														<c:if test="${(usuarioForm.statusUsuario == 'S') }">
															<button type="button" class="btn btn-default" data-toggle="modal" data-target=".bs-example-modal-lg-revogar-suspensao-usuario">  <spring:message code="lbl.admin.cancelar.suspensao.usuario"/></button>
														</c:if>
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
			                                    <!--/ End tabs heading -->
			
			                                    <!-- Start tabs content -->
			                                    <div class="panel-body col-lg-9 col-md-9 col-sm-9">
			                                        <div class="tab-content">
			                                            <div class="tab-pane fade in active" id="tab4-1">
			                                            	 <div class="form-group">
			                                                		<strong class="col-sm-5"><spring:message code="lbl.nome.usuario"/>:</strong> ${usuarioForm.nome}
			                                            	 </div><!-- /.form-group -->
			                                            	 
			                                            	 <div class="form-group">
			                                                		<strong class="col-sm-5"><spring:message code="lbl.perfil.usuario"/>:  </strong> ${usuarioForm.perfilFmt}
			                                            	 </div><!-- /.form-group -->
			                                            	 
			                                            	 <c:if test="${((usuarioForm.perfil == 'P') || (usuarioForm.perfil == 'C')) }">
				                                            	 <div class="form-group">																            
									                                  <strong class="col-sm-5"><spring:message code="lbl.cpf"/>: </strong>  ${usuario.cpf}
									                                  
				                                            	 </div><!-- /.form-group -->
			                                            	 </c:if>
			                                            	 
			                                            	  <c:if test="${usuarioForm.perfil == 'I'}">
			                                            	  		<div class="form-group">																				            
								                                    	<strong class="col-sm-5"><spring:message code="lbl.cnpj"/> </strong>: ${usuarioForm.cpf}
								                                    </div><!-- /.form-group -->	
								                              </c:if>
								                               
								                              <c:if test="${usuarioForm.perfil == 'C'}">	
			                                            	  		<div class="form-group">																				            
								                                    	<strong class="col-sm-5"><spring:message code="lbl.creci"/>: </strong> ${usuarioForm.creci}
								                                    </div><!-- /.form-group -->	
								                              </c:if>
															 
															 <div class="form-group">																				            
								                              		<strong class="col-sm-5"><spring:message code="lbl.data.nascimento"/>: </strong> <fmt:formatDate value='${usuarioForm.dataNascimento}' pattern='dd/MM/yyyy'/>
								                              </div><!-- /.form-group -->
			                                            	 
			                                            	  <div class="form-group">																				            
								                              		<strong class="col-sm-5"><spring:message code="lbl.data.cadastro.usuario"/>: </strong> <fmt:formatDate value='${usuarioForm.dataCadastro}' pattern='dd/MM/yyyy'/>
								                              </div><!-- /.form-group -->
								                              
								                              <div class="form-group">																				            
								                              		<strong class="col-sm-5"><spring:message code="lbl.data.ultimo.acesso"/>: </strong> <fmt:formatDate value='${usuarioForm.dataUltimoAcesso}' pattern='dd/MM/yyyy'/>
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
			                                            
			                                            <div class="tab-pane fade" id="tab4-4">
			                                            	<div class="form-group">
									                              <strong class="col-sm-5"><spring:message code="lbl.total.imoveis"/>:</strong> ${usuarioForm.quantTotalImoveis}						                                  
				                                             </div><!-- /.form-group -->
				                                             
				                                             <div class="form-group">
									                              <strong class="col-sm-5"><spring:message code="lbl.total.contato"/>:</strong> ${usuarioForm.quantTotalContatos}						                                  
				                                             </div><!-- /.form-group -->
				                                             
				                                             <c:if test="${usuarioForm.perfil != 'P'}">  
				                                             	<div class="form-group">
				                                             		<strong class="col-sm-5"><spring:message code="lbl.total.parcerias"/>:</strong> ${usuarioForm.quantTotalParcerias}
				                                             	</div><!-- /.form-group -->	
				                                             </c:if>
				                                             
				                                             <div class="form-group">
				                                             		<strong class="col-sm-5"><spring:message code="lbl.total.interessados"/>:</strong> ${usuarioForm.quantTotalInteressadosImoveis}
				                                             </div><!-- /.form-group -->
				                                             
				                                              <div class="form-group">
				                                             		<strong class="col-sm-5"><spring:message code="lbl.total.interessados"/>:</strong> ${usuarioForm.quantTotalInteressadosImoveis}
				                                             </div><!-- /.form-group -->
				                                             
				                                             <c:if test="${usuarioForm.perfil != 'P'}">
				                                             	<div class="form-group">
				                                             		<strong class="col-sm-5"><spring:message code="lbl.total.intermediacoes"/>:</strong> ${usuarioForm.quantTotalIntermediacoes}
				                                             	</div><!-- /.form-group -->
				                                             </c:if>
				                                             
				                                             <div class="form-group">
				                                             	  <strong class="col-sm-5"><spring:message code="lbl.total.visualizacoes"/>:</strong> ${usuarioForm.quantTotalVisitasImoveis}
				                                             </div><!-- /.form-group -->
				                                             
				                                             <div class="form-group">
				                                             	  <strong class="col-sm-5"><spring:message code="lbl.total.aprovacoes.usuario"/>:</strong> ${usuarioForm.quantTotalAprovacoesUsuario}
				                                             </div><!-- /.form-group -->
				                                             
				                                             <div class="form-group">
				                                             	  <strong class="col-sm-5"><spring:message code="lbl.total.desaprovacoes.usuario"/>:</strong> ${usuarioForm.quantTotalDesaprovacoesUsuario}
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
				                    
				                    <div class="panel panel-info rounded shadow">
				                    
				                    	<div class="panel-heading">
			                                    <div class="pull-left">
			                                        <h3 class="panel-title"><spring:message code="lbl.sobre.mim"/></h3>
			                                    </div>
			                                    
			                                    <div class="clearfix"></div>
			                                </div><!-- /.panel-heading -->	
				                        <form action="...">	                        	
				                            <textarea class="form-control input-lg no-border" rows="5" placeholder="${usuarioForm.descSobreMim}"></textarea>
				                        </form>	                        
				                    </div><!-- /.panel -->
				                <!--/ End type panel -->
			                </div>
	                   </div>
	                    
	                    <c:if test="${usuarioForm.perfil != 'P'}">
		                       <!-- /.START IMOVEIS EM DESTAQUE -->	
		                       <div class="row">
		                       		<div class="col-md-12">
		                       		<c:if test="${ not empty usuarioForm.listaImoveisDestaque }">
			                      		<!-- Start scrollable panel -->
				                            <div class="panel panel-scrollable panel-info rounded shadow">
				                                <div class="panel-heading">
				                                    <h3 class="panel-title"><spring:message code="lbl.imoveis.destaque"/></h3>
				                                </div><!-- /.panel-heading -->
				                                <div class="panel-body panel panel-info rounded shadow">
				                                    <c:forEach var="imovel" items="${usuarioForm.listaImoveisDestaque}" varStatus="item">	                                
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
						                                        <a href="${urlImovel}/detalhesImovel/${imovel.id}" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> Detalhes</a>
						                                        		                                                                                                                                                                    
					                                          </div>		                                          
							                                  </div><!-- /.media-body -->
							                              </div><!-- /.media -->						                              	  
						                              	  <div class="line"></div>
					            					  </c:forEach>
				                                </div><!-- /.panel-body -->
				                            </div><!-- /.panel -->
				                            </c:if> 
				                         </div>  
				                        <!--/ End scrollable panel -->
			                        
			                        
			                        <c:if test="${ empty usuarioForm.listaImoveisDestaque }">
			                        	<div class="panel panel-scrollable panel-info rounded shadow">
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
			                            <div class="panel panel-scrollable panel-info rounded shadow">
			                                <div class="panel-heading">
			                                	<c:if test="${usuarioForm.perfil != 'P'}">
			                                    	<h3 class="panel-title"><spring:message code="lbl.outros.imoveis"/></h3>			                                    	
			                                    </c:if>
			                                    <c:if test="${usuarioForm.perfil == 'P'}">
			                                    	<h3 class="panel-title"><spring:message code="lbl.imoveis.usuario"/></h3>
			                                    </c:if>
			                                </div><!-- /.panel-heading -->
			                                <c:if test="${ not empty usuarioForm.listaOutrosImoveis }">
				                                <div class="panel-body panel panel-info rounded shadow">
				                                    <c:forEach var="imovel" items="${usuarioForm.listaOutrosImoveis}" varStatus="item">	                                
															<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
							                                   <ul class="inner-all list-unstyled">
							                                   		<li class="text-center">
							                                   			<img src="${context}${imovel.imagemArquivo}" style="width: 230px; height: 150px; " alt="admin"/>
							                                   		</li>
							                                   		<li class="text-center">
							                                   			<div class="media-body">
							                                  	  <h4 class="media-heading"><a href="#">${imovel.titulo}</a></h4>
					                                        	  <p><strong>${imovel.bairro} - ${imovel.cidade} - ${imovel.estado}</strong></p>
					                                        	  
					                                        	  <div class="meta-search">
									                                        
									                                        <small> ${imovel.tipoImovelFmt} </small> </br>		                                        																												                     		
									                                        <a href="${urlImovel}/detalhesImovel/${imovel.id}" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> Detalhes</a>									                                        
									                                        		                                                                                                                                                                    
								                                          </div>		                                          
										                                </div><!-- /.media-body -->
							                                   		</li>
							                                   </ul>
							                                </div>
							                        </c:forEach>
							                      </div>
							                 </c:if>             
							                                  
							                <div class="line"></div>
								          
			                             
			                                <c:if test="${ empty usuarioForm.listaOutrosImoveis }">
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
			                            <div class="panel panel-scrollable panel-info rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title"><spring:message code="lbl.contatos"/></h3>
			                                </div><!-- /.panel-heading -->
			                                <div class="panel-body panel panel-info rounded shadow">
			                                    <c:if test="${ not empty usuarioForm.listaContatosUsuario }">
							                    	<c:forEach var="contato" items="${usuarioForm.listaContatosUsuario}">
								                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
								                            <div class="panel rounded shadow">
								                                <div class="panel-body ">								                                    
								                                    
								                                    <ul class="inner-all list-unstyled">
								                                    	<c:choose>
											                    			<c:when test="${contato.usuarioHost.id != usuarioForm.id}">
											                    				<li class="text-center">
										                                            <img class="img-circle img-bordered-success" src="${context}${contato.usuarioHost.imagemArquivo}" style="width: 100px; height: 100px; ">
										                                        </li>
										                                        <li class="text-center">
										                                            <h4 class="text-capitalize">${contato.usuarioHost.nome}</h4>
										                                            <p class="text-muted text-capitalize">${contato.usuarioHost.perfilFmt}</p>
										                                        </li>
										                                        <li>  
										                                            <a href="${urlUsuario}/detalhesUsuario/${contato.usuarioHost.id}" class="btn btn-primary text-center btn-block"><spring:message code="lbl.detalhes.usuario"/></a>								                                            
										                                        </li>	
											                    			</c:when>
											                    			
											                    			<c:when test="${contato.usuarioConvidado.id != usuarioForm.id}">
											                    				<li class="text-center">
										                                            <img class="img-circle img-bordered-success" src="${context}${contato.usuarioConvidado.imagemArquivo}" style="width: 100px; height: 100px; ">
										                                        </li>
										                                        <li class="text-center">
										                                            <h4 class="text-capitalize">${contato.usuarioConvidado.nome}</h4>
										                                            <p class="text-muted text-capitalize">${contato.usuarioConvidado.perfilFmt}</p>
										                                        </li>
										                                        <li>  
										                                            <a href="${urlUsuario}/detalhesUsuario/${contato.usuarioConvidado.id}" class="btn btn-primary text-center btn-block"><spring:message code="lbl.detalhes.usuario"/></a>								                                            
										                                        </li>
											                    			</c:when>
											                    		</c:choose>
											                    		    
								                                    </ul><!-- /.list-unstyled -->
								                                </div><!-- /.panel-body -->
								                            </div><!-- /.panel -->
								                        </div>
							                        </c:forEach>
							                        </c:if>
							                          <c:if test="${ empty usuarioForm.listaContatosUsuario }">									                 		 
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
			                            <div class="panel panel-scrollable panel-info rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title"><spring:message code="lbl.notas.usuario"/></h3>
			                                </div><!-- /.panel-heading -->
			                                <div class="panel-body panel panel-info rounded shadow">
			                                    <c:if test="${ not empty usuarioForm.listaNotasUsuario }">
													  <c:forEach var="nota" items="${usuarioForm.listaNotasUsuario}"> 		                                	
														<div class="media inner-all">
							                                  <div class="pull-left">
							                                         <span class="fa fa-stack fa-2x">
							                                      
							                                         	<c:choose>
							                                         		<c:when test="${((nota.acao == 'P') || (nota.acao == 'R') || (nota.acao == 'U') || (nota.acao == 'E'))}">
							                                         			<a href="#" onClick="carregaDetalhesUsuario(${nota.usuario.id})" >                                         	
								                                              		<img class="img-circle img-bordered-success" src="${context}${nota.usuario.imagemArquivo}" style="width: 60px; height: 60px; " alt="admin"/>
								                                              	</a>	
							                                         		</c:when>
							                                         		
							                                         		<c:when test="${(nota.acao == 'I')}">
							                                         			<a href="#" onClick="carregaDetalhesImovel(${nota.imovel.id})" >
								                                            		<img src="${context}${nota.imovel.imagemArquivo}" style="width: 60px; height: 60px; " alt="admin"/>
								                                            	</a>	
							                                         		</c:when>
							                                         	</c:choose>	
							                                         </span>
							                                  </div><!-- /.pull-left -->
							                                  <div class="media-body">
							                                  	<c:choose>
							                                  		<c:when test="${nota.acao == 'P'}">
																    	<a href="#" class="h4"><spring:message code="lbl.nota.parceria"/></a>
																    	
																    	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="#" onClick="carregaDetalhesImovel(${nota.imovel.id})" ><strong>${nota.imovel.titulo} </strong></a></small>		
																    </c:when>
																    
																    <c:when test="${nota.acao == 'R'}">
																    	<a href="#" class="h4"><spring:message code="lbl.nota.preferencia"/></a>
																    	
																    	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao}</small>
																    </c:when>
																    
																    <c:when test="${nota.acao == 'U'}">
																    	<a href="#" class="h4"><spring:message code="lbl.nota.info.usuario"/></a>
																    	
																    	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="#" onClick="carregaDetalhesUsuario(${nota.usuario.id})"><strong>${nota.usuario.nome} </strong></a></small>
																    </c:when>
																    
																    <c:when test="${nota.acao == 'E'}">
																    	<a href="#" class="h4"><spring:message code="lbl.nota.pessoal"/></a>
																    	
																    	<small class="block text-muted"><label> <spring:message code="lbl.nota.escreveu.nota"/>: </label>  ${nota.descricao} </small>
																    </c:when>
																    
																    <c:when test="${nota.acao == 'I'}">
																    	<a href="#"  class="h4"><spring:message code="lbl.nota.imovel"/></a>
																    	
																    	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="#" onClick="carregaDetalhesImovel(${nota.imovel.id})" ><strong>${nota.imovel.titulo} </strong></a></small>
																    </c:when>
							                                  	</c:choose>
							                                  	   												  
							                                      
							                                      <em class="text-xs text-muted"><spring:message code="lbl.data.nota"/> <span class="text-danger"><fmt:formatDate value='${nota.dataNota}' pattern='dd/MM/yyyy'/></span></em>
							                                  </div><!-- /.media-body -->
							                              </div><!-- /.media -->
					                              		  <div class="line"></div>
					                              	  </c:forEach>	  
				                              	  </c:if>	
				                              	    <c:if test="${ empty usuarioForm.listaNotasUsuario }">
										            	<p><spring:message code="lbl.nenhuma.nota"/></p>
										            </c:if>
			                                </div><!-- /.panel-body -->
			                            </div><!-- /.panel -->
			                        <!--/ End scrollable panel --> 
		                       </div>	
	                      
	                      <!-- /.END NOTAS DO USUARIO -->
	                      
	                      <c:if test="${usuarioForm.perfil == 'P'}">
	                      <!-- /.START PREFERENCIAS DO USUARIO -->
	                      	   <div class="row">
		                      		<!-- Start scrollable panel -->
			                            <div class="panel panel-scrollable panel-info rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title"><spring:message code="lbl.pref.imoveis"/></h3>
			                                </div><!-- /.panel-heading -->
			                                <div class="panel-body panel panel-info rounded shadow">
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
		                                            <c:forEach var="pref" items="${usuarioForm.listaPreferenciaImoveis}" >
			                                            <tr>
			                                                <td class="text-center"> ${pref.tipoImovelFmt} </td>
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

						  <!-- /.START SERVIÇOS DO USUARIO -->
								<div class="row">
		                      		<!-- Start scrollable panel -->
			                            <div class="panel panel-scrollable panel-info rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title"><spring:message code="lbl.lista.servicos"/></h3>
			                                </div><!-- /.panel-heading -->
			                                <div class="panel-body panel panel-info rounded shadow">
			                                	<div class="form-group">
														<div class="col-sm-3">
															<button type="button" class="btn btn-primary text-center btn-block" data-toggle="modal" data-target=".bs-example-modal-lg-cadastra-servico">  <spring:message code="btn.admin.modal.conceder.servico"/></button> 		
														</div> 
												</div>												 
												
												<div class="form-group"> &nbsp;&nbsp;&nbsp;&nbsp; </div>
												
												<div id="msgConcedeServico" ></div>
			                                
			                                    <table class="table table-striped table-primary">
		                                            <thead>
		                                            <tr>
		                                                <th class="text-center"><spring:message code="lbl.table.nome.servico"/></th>
														<th class="text-center"><spring:message code="lbl.table.status.servico"/></th>
														<th class="text-center"><spring:message code="lbl.table.data.sol.servico"/></th>
														<th class="text-center"><spring:message code="lbl.table.data.pagto.servico"/></th>                                                
														<th class="text-center"><spring:message code="lbl.table.data.acoes"/></th>   
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
																	<td class="text-center">${servico.statusPgtoFmt}</td>
																	<td class="text-center"><fmt:formatDate value='${servico.dataSolicitacao}' pattern='dd/MM/yyyy'/></td>
																	<td class="text-center"><fmt:formatDate value='${servico.dataPagto}' pattern='dd/MM/yyyy'/></td>		                                   
																	<td class="text-center">
																		<c:if test="${servico.statusPgto == 'concedido'}"> 
																				<a href="${urlAdmin}/revogarServico/${servico.id}" class="btn btn-danger btn-xs" data-toggle="tooltip" data-original-title="<spring:message code='lbl.admin.table.result.servico.revogar'/>"><i class="fa fa-times"></i></a> 
																		</c:if>
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

						  <!-- /.END SERVIÇOS DO USUARIO -->						  
						  
						  <!-- /.START PLANOS DO USUARIO -->							
						  		<div class="row">
						  				
		                      		<!-- Start scrollable panel -->
			                            <div class="panel panel-scrollable panel-info rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title"><spring:message code="lbl.lista.planos"/></h3>
			                                </div><!-- /.panel-heading -->			                                
			                                <div class="panel-body panel panel-info rounded shadow">
							                              
												<div class="form-group">
														<div class="col-sm-3">
															<button type="button" class="btn btn-primary text-center btn-block" data-toggle="modal" data-target=".bs-example-modal-lg-cadastra-plano">  <spring:message code="btn.admin.modal.conceder.plano"/></button> 		
														</div> 
												</div>												 
												
												<div class="form-group"> &nbsp;&nbsp;&nbsp;&nbsp; </div>
												
												<div id="msgConcedePlano" ></div>
			                                			                                				                               	
			                                    <table class="table table-striped table-primary">
		                                            <thead>
		                                            <tr>
		                                                 <th class="text-center"><spring:message code="lbl.table.nome.servico"/></th>
														 <th class="text-center"><spring:message code="lbl.table.status.servico"/></th>
														 <th class="text-center"><spring:message code="lbl.table.data.sol.servico"/></th>
														 <th class="text-center"><spring:message code="lbl.table.data.pagto.servico"/></th>                                                
														 <th class="text-center"><spring:message code="lbl.table.data.acoes"/></th>  
		                                            </tr>
		                                            </thead>
		                                            <tbody>
		                                            	<c:if test="${not  empty usuarioForm.listaPlanos }">
															<c:forEach var="plano" items="${usuarioForm.listaPlanos}" >
																<tr>
																	<td class="text-center">${plano.nomePlano}</td>
																	<td class="text-center">${plano.statusFmt}</td>
																	<td class="text-center"><fmt:formatDate value='${plano.dataSolicitacao}' pattern='dd/MM/yyyy'/></td>
																	<td class="text-center"><fmt:formatDate value='${plano.dataPagto}' pattern='dd/MM/yyyy'/></td>
																	<td class="text-center">																		  
																		  <c:if test="${plano.status == 'concedido'}">
																				<a href="${urlAdmin}/revogarPlano/${plano.id}" class="btn btn-danger btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="<spring:message code='lbl.admin.table.result.plano.revogar'/>"><i class="fa fa-times"></i></a>																
																		  </c:if>	
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
						  
						  <!-- /.END PLANOS DO USUARIO -->	                    
	                    </div>
                   		<!-- END  Painel de Informações -->
                		                               
                    </div><!-- /.row -->
                    
                          <!-- Start inside form layout - cadastro plano -->
				            <div class="modal fade bs-example-modal-lg-cadastra-plano" tabindex="-1" role="dialog" aria-hidden="true">
				                <div class="modal-dialog modal-lg modal-photo-viewer">
				                    <div class="modal-content">
				                        <div class="modal-header">
				                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				                            <h4 class="modal-title"><spring:message code="lbl.modal.concessao.plano"/></h4>
				                        </div>
				                        <div class="modal-body no-padding">
				                            <form class="form-horizontal form-bordered" role="form">
				                                <div class="form-body">
				                                    <div class="form-group">
				                                        <label for="idPlanoSelecionado" class="col-sm-4 control-label"><spring:message code="lbl.planos.disponiveis"/>:</label>
					                                        <div class="col-sm-7">
															  
															  <select name="idPlanoSelecionado" id="idPlanoSelecionado">
															  			<option value="-1"><spring:message code="opcao.selecao.uma.opcao"/></option>
															  	  <c:forEach var="plano" items="${usuarioForm.listaPlanosDisponiveis}" >
															  	  		<option value="${plano.id}">${plano.nome}</option> 
															  	  </c:forEach>
															  </select>
				                                            </div> 
				                                    </div><!-- /.form-group -->
				                                   
				                                </div><!-- /.form-body -->
				                                <div class="form-footer">
				                                    <div class="col-sm-offset-3">
				                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
				                                        <button type="button" class="btn btn-success" onClick="concederPlano();"><spring:message code="lbl.btn.confirmar.geral"/></button>
				                                    </div>
				                                </div><!-- /.form-footer -->
				                            </form>
				                        </div>
				
				                    </div><!-- /.modal-content -->
				                </div><!-- /.modal-dialog -->
				            </div><!-- /.modal -->
				       <!--/ End inside form layout - cadastro plano -->
				       
				       
				       <!-- Start inside form layout - cadastro servicos -->
				            <div class="modal fade bs-example-modal-lg-cadastra-servico" tabindex="-1" role="dialog" aria-hidden="true">
				                <div class="modal-dialog modal-lg modal-photo-viewer">
				                    <div class="modal-content">
				                        <div class="modal-header">
				                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				                            <h4 class="modal-title"><spring:message code="lbl.modal.concessao.servico"/></h4>
				                        </div>
				                        <div class="modal-body no-padding">
				                            <form class="form-horizontal form-bordered" role="form">
				                                <div class="form-body">
				                                    <div class="form-group">
				                                        <label for="servicoSelecionado" class="col-sm-4 control-label"><spring:message code="lbl.servicos.disponiveis"/>:</label>
					                                        <div class="col-sm-4">
															  
															  <select name="servicoSelecionado" id="servicoSelecionado" class="chosen-select" tabindex="-1" style="display: none;">
															  			<option value="-1"><spring:message code="opcao.selecao.uma.opcao"/></option>
															  	  <c:forEach var="servico" items="${usuarioForm.listaServicosDisponiveis}" >
															  	  		<option value="${servico.valueServico}">${servico.labelServico}</option> 
															  	  </c:forEach>
															  </select>
				                                            </div> 
				                                    </div><!-- /.form-group -->
				                                    
				                                    <div class="form-group">
				                                        <label for="password-1" class="col-sm-3 control-label">Quantidade/Tempo</label>
				                                        <div class="col-sm-4">
				                                            <input type="text" class="form-control input-sm" id="quantidadeTempo">
				                                        </div>
				                                    </div><!-- /.form-group -->
				                                   
				                                </div><!-- /.form-body -->
				                                <div class="form-footer">
				                                    <div class="col-sm-offset-3">
				                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
				                                        <button type="button" class="btn btn-success" onClick="concederServico();"><spring:message code="lbl.btn.confirmar.geral"/></button>
				                                    </div>
				                                </div><!-- /.form-footer -->
				                            </form>
				                        </div>
				
				                    </div><!-- /.modal-content -->
				                </div><!-- /.modal-dialog -->
				            </div><!-- /.modal -->
				            <!--/ End inside form layout - cadastro servicos -->
							
							<!-- Start optional size modal element - suspender usuario -->
							<div class="modal fade bs-example-modal-lg-suspender-usuario" tabindex="-1" role="dialog" aria-hidden="true">
				                <div class="modal-dialog modal-lg modal-photo-viewer">
				                    <div class="modal-content">
				                        <div class="modal-header">
				                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				                            <h4 class="modal-title"><spring:message code="lbl.modal.confirma.suspensao.usuario"/></h4>
				                        </div>
				                        <div class="modal-body no-padding">
				                            <form class="form-horizontal form-bordered" role="form">
				                                <div class="form-body">
				                                    <div class="form-group">
				                                        <label for="servicoSelecionado" class="col-sm-4 control-label"><spring:message code="lbl.modal.confirma.suspensao.usuario.tempo"/>:</label>
					                                        <div class="col-sm-4">
															  
															  <select name="tempoSuspensao" id="tempoSuspensao" class="chosen-select" tabindex="-1" style="display: none;">
															  		<option value=""><spring:message code="opcao.selecao.uma.opcao"/></option>
																	<option value="1">1</option>
																	<option value="7">7</option>
																	<option value="15">15</option>
																	<option value="30">30</option>															  	  
															  </select>
				                                            </div> 
				                                    </div><!-- /.form-group -->
				                                    
				                                    <div class="form-group">
				                                        <label for="password-1" class="col-sm-3 control-label"><spring:message code="lbl.modal.confirma.suspensao.usuario.motivo"/></label>
				                                        <div class="col-sm-4">
				                                            <input type="text" class="form-control input-sm" id="motivoSuspensao">
				                                        </div>
				                                    </div><!-- /.form-group -->
				                                   
				                                </div><!-- /.form-body -->
				                                <div class="form-footer">
				                                    <div class="col-sm-offset-3">
				                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
				                                        <button type="button" class="btn btn-success" onClick="adicionarSuspensaoUsuario();"><spring:message code="lbl.btn.confirmar.geral"/></button>
				                                    </div>
				                                </div><!-- /.form-footer -->
				                            </form>
				                        </div>
				
				                    </div><!-- /.modal-content -->
				                </div><!-- /.modal-dialog -->
				            </div><!-- /.modal -->
				            <!--/ End inside form layout - suspender usuario -->
				            
				            
				            <!-- Start optional size modal element - cancelar suspensao usuario -->
							<div class="modal fade bs-example-modal-lg-revogar-suspensao-usuario" tabindex="-1" role="dialog" aria-hidden="true">
				                <div class="modal-dialog modal-lg modal-photo-viewer">
				                    <div class="modal-content">
				                        <div class="modal-header">
				                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				                            <h4 class="modal-title"><spring:message code="lbl.modal.confirma.cancelar.suspensao.usuario"/></h4>
				                        </div>
				                        <div class="modal-body no-padding">
				                            <form class="form-horizontal form-bordered" role="form">
				                                
				                                <div class="form-footer">
				                                    <div class="col-sm-offset-3">
				                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
				                                        <button type="button" class="btn btn-success" onClick="cancelarSuspensaoUsuario();"><spring:message code="lbl.btn.confirmar.geral"/></button>
				                                    </div>
				                                </div><!-- /.form-footer -->
				                            </form>
				                        </div>
				
				                    </div><!-- /.modal-content -->
				                </div><!-- /.modal-dialog -->
				            </div><!-- /.modal -->
				            <!--/ End inside form layout - cancelar suspensao usuario -->
				            

           </div><!-- /.body-content -->           
            
		</div> 
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