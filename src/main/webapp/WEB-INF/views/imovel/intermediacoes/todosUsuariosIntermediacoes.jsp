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

<c:set var="context" value="<%= request.getContextPath()%>"/>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
		
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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.link.intermediacoes"/> </h2>
					
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
													<a href="${urlImovel}/detalhesImovel/${imovel.id}" >
														<img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${imovel.imagemArquivo}" style="width: 200px; height: 200px; ">
													</a>
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize">
														<a href="${urlImovel}/detalhesImovel/${imovel.id}" >
															${imovel.titulo}
														</a>
													</h4>
		                                            <p class="text-muted text-capitalize"> ${imovel.tipoImovelFmt} </p>
		                                        </li>		                                    
		                                        <li><br/></li>		                                      
		                                    </ul>
		                                </div>
		                            </div>
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
		                                                        <span class="text-strong"><spring:message code="lbl.todas.intermediacao.info.imovel"/></span>                                                        
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
		                                                		<strong class="col-sm-3"><spring:message code="lbl.titulo.imovel"/></strong> ${imovel.titulo}
		                                            	 </div><!-- /.form-group -->
		                                            
		                                            	 <div class="line"></div>
		                                            	 
		                                            	 <div class="form-group">
		                                                		<strong class="col-sm-3"><spring:message code="lbl.estado"/></strong> ${imovel.estado}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            	 
		                                            	 <div class="form-group">
		                                                		<strong class="col-sm-3"><spring:message code="lbl.cidade"/></strong> ${imovel.cidade}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            	 
		                                            	 <div class="form-group">
		                                                		<strong class="col-sm-3"><spring:message code="lbl.bairro"/></strong> ${imovel.bairro}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            	 
		                                            	 <div class="form-group">																				            
							                              		<strong class="col-sm-3"><spring:message code="lbl.data.cadastro.imovel"/> </strong> <fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/>
							                             </div><!-- /.form-group -->							                             
							                              
		                                            </div>                          
		                                        </div>                                       
		                                    </div><!-- /.panel-body -->
		                                    <!--/ End tabs content -->
		                                </div><!-- /.panel -->
		                            </div>
		                         
			                    </div>                   
			                    <div class="divider"></div>	
	                    
	                      	   <div class="row">
		                      		<div class="panel">
		                                <div class="panel-heading">
		                                    <div class="pull-left">
		                                        <h3 class="panel-title"><spring:message code="lbl.aba.todos.usuarios.intermediacao"/></h3>
		                                    </div>
		                                    <div class="pull-right">
		                                        <a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>
		                                    </div>
		                                    <div class="clearfix"></div>
		                                </div><!-- /.panel-heading -->
		                                <div class="panel-body no-padding">
		                                    <div class="table-responsive" style="margin-top: -1px;">
		                                        <table class="table table-striped table-primary">
		                                            <thead>
		                                            <tr>		                                                
		                                            	<th class="text-center"></th>
		                                                <th class="text-center"><spring:message code="lbl.nome.usuario.intermediacao"/></th>
		                                                <th class="text-center"><spring:message code="lbl.data.sol.intermediacao"/></th>  
		                                                <th class="text-center"><spring:message code="lbl.data.res.intermediacao"/></th>
		                                                <th class="text-center"><spring:message code="lbl.status.intermediacao"/></th>
		                                            </tr>
		                                            </thead>
		                                            <tbody>
		                                            <c:forEach var="imovelIntermediacao" items="${listaTodasIntermediacoes}" >
			                                            <tr>
			                                                <td class="text-center">	
																<a href="${urlUsuario}/detalhesUsuario/${imovelIntermediacao.idUsuarioSolicitante}" >
																	<img src="data:image/jpeg;base64,${usuario.imagemArquivo}" style="width: 60px; height: 50px; " />	                				
																</a>
			                                                </td>			                                                
			                                                <td class="text-center">
																<a href="${urlUsuario}/detalhesUsuario/${imovelIntermediacao.idUsuarioSolicitante}" >
																	${imovelIntermediacao.nomeUsuarioSolicitante}
																</a>
															</td>
			                                                <td class="text-center"><fmt:formatDate value='${imovelIntermediacao.dataSolicitacao}' pattern='dd/MM/yyyy'/></td>
			                                                <td class="text-center"><fmt:formatDate value='${imovelIntermediacao.dataResposta}' pattern='dd/MM/yyyy'/></td>
			                                                <td class="text-center">${imovelIntermediacao.status}</td>			                                                			                                              	                                            
			                                            </tr>
		                                            </c:forEach>
		                                            </tbody>
		                                        </table>
		                                    </div>
		                                </div>
                            		</div><!-- /.panel -->
		                       </div>		  
	                    </div>                   		
                		                               
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
         
            </section><!-- /#page-content -->
			
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
       
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
    </body>
    <!--/ END BODY -->

</html>