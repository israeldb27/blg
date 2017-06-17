<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>


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
 
<script type="text/javascript">

	function mostrarModal(id){	
		if (id == 0){
			$('#msgModal').html("<spring:message code='lbl.aba.desc.modal.todos.usuarios.parceria'/>");		
			$('#msgModalFuncionalidade').html("<spring:message code='lbl.aba.todos.usuarios.parceria'/>");
		}
	
		$("#idModalItem").modal("show");
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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.link.parcerias"/> </h2>		
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
		                                                        <span class="text-strong"><spring:message code="lbl.todas.parceria.info.imovel"/></span>                                                        
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
		                                        <h3 class="panel-title"><spring:message code="lbl.aba.todos.usuarios.parceria"/></h3>
		                                    </div>
		                                    <div class="pull-right">
		                                        <a href="#a" class="btn btn-sm" onClick="mostrarModal(0);"><i class="fa fa-question" ></i></a>
		                                    </div>
		                                    <div class="clearfix"></div>
		                                </div><!-- /.panel-heading -->
		                                <div class="panel-body no-padding">
		                                    <div class="table-responsive" style="margin-top: -1px;">
		                                        <table class="table table-striped">
		                                            <thead>
		                                            <tr>		                                                
		                                            	<th class="text-center"></th>
		                                                <th class="text-center"><spring:message code="lbl.nome.usuario.parceria"/></th>
		                                                <th class="text-center"><spring:message code="lbl.data.sol.parceria"/></th>  
		                                                <th class="text-center"><spring:message code="lbl.data.res.parceria"/></th>
		                                                <th class="text-center"><spring:message code="lbl.status.parceria"/></th>
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
			                                                <td class="text-center"><a href="${urlUsuario}/detalhesUsuario/${imovelIntermediacao.idUsuarioSolicitante}" >
																							${imovelIntermediacao.nomeUsuarioSolicitante}
																					</a>
															</td>
			                                                <td class="text-center"><fmt:formatDate value='${imovelIntermediacao.dataSolicitacao}' pattern='dd/MM/yyyy'/></td>
			                                                <td class="text-center"><fmt:formatDate value='${imovelIntermediacao.dataResposta}' pattern='dd/MM/yyyy'/></td>
			                                                <td class="text-center">${imovelIntermediacao.statusFmt}</td>			                                                			                                              	                                            
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
            
			 <!-- Start optional size modal element - item 1 -->
              <div id="idModalItem" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
               <div class="modal-dialog">
			      <div class="modal-content">
			        <div class="modal-header">
			          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			          <h4 class="modal-title"><div id="msgModalFuncionalidade" > </div> </h4>
			        </div>
			        <div class="modal-body">  
			       	   <strong> <spring:message code="lbl.descricao.geral"/>:  </strong> <div id="msgModal" > </div>
			        </div>
			        <div class="modal-footer">			          
                      <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>
			        </div>
			      </div>
			    </div>
			</div>

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