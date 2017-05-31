<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/mensagem" var="urlMensagem"/>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>
<c:set var="context" value="<%= request.getContextPath()%>"/>
   
<html> 
    <head> 
    	<script>
			$(document).ready(function() {
				$("editButton").click( function() {
					 $("firstname-1").val('teste');
				 });					
			});
			
			function populaModal(){	
				$("firstname-1").val("testando");
				$("resposta").html('teste');				
			}
			
			function mostrarModal(id){		
				if (id == 0){
					$('#msgModalFunc').html("<spring:message code='msg.modal.title.link.mensagens'/>");
					$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.link.mensagens'/>");
				}		
				$("#idModalItem").modal("show");
			}
		</script>
		
        <c:import url="../layout/head-layout.jsp"></c:import>
    </head>
   
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
                	<h2>
                    	<i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.mensagens"/> 
                    	 <div class="pull-right">
	                         <a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);"><i class="fa fa-question" style="font-size: 12px;"></i></a>                                        
	                     </div>	
                    </h2>                            
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
					<div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-success shadow">
                            	<c:choose>
                            		<c:when test="${not empty listaMinhasMensagens}">
                            			 <div class="panel-heading">
			                                    <div class="pull-left">
			                                        <h3 class="panel-title"><spring:message code="lbl.lista.mensagens"/></h3>
			                                    </div>
			                                    
			                                    <div class="pull-right">
			                                        <button class="btn btn-sm" data-action="collapse" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>
			                                    </div>
			                                    <div class="clearfix"></div>
			                                </div><!-- /.panel-heading -->
			                                <div class="panel-body no-padding">
			
			                                    <!-- Start list message -->
			                                    <div class="media-list">                                    	
													<c:forEach var="mensagem" items="${listaMinhasMensagens}">
														<c:choose>
															<c:when test="${mensagem.usuarioDe.id == usuario.id}">  
																<a href="${urlMensagem}/maisMensagens/${mensagem.usuarioPara.id}" class="media">														
																	<div class="pull-left"> <img src="data:image/jpeg;base64,${mensagem.usuarioPara.imagemArquivo}" class="media-object img-circle" alt="..."/> </div>
							                                            <div class="media-body">
							                                                <span class="media-heading"> ${mensagem.usuarioPara.nome} </span>
																			                    	    
							                                                <span class="media-text">${mensagem.descricao} </span>
							                                                <!-- Start meta icon -->	                                                              	                                                
							                                                <span class="media-meta"><i class="fa fa-plus-square"></i> <spring:message code="lbl.mais.mensagens"/></span>	                                                
							                                                
							                                                <span class="media-meta pull-right">	                                                		                                  
							                                                	<fmt:formatDate value="${mensagem.dataMensagem}" pattern='dd/MM/yyyy'/></span>
							                                                	
							                                                	<c:if test="${(mensagem.status == 'N')}">
							                                                		<spring:message code="lbl.nova.mensagem.resum"/>	                                                	
							                                                	</c:if>
							                                                <!--/ End meta icon -->
							                                            </div><!-- /.media-body -->
							                                       </a> 
															</c:when>
															
															<c:when test="${mensagem.usuarioPara.id == usuario.id}">
																<a href="${urlMensagem}/maisMensagens/${mensagem.usuarioDe.id}" class="media">
																	 <div class="pull-left"><img src="data:image/jpeg;base64,${mensagem.usuarioDe.imagemArquivo}" class="media-object img-circle" alt="..."/></div>
							                                            <div class="media-body">
							                                                <span class="media-heading"> ${mensagem.usuarioDe.nome} </span>
																			                    	    
							                                                <span class="media-text">${mensagem.descricao} </span>
							                                                <!-- Start meta icon -->	                                                              	                                                
							                                                <span class="media-meta"><i class="fa fa-plus-square"></i><spring:message code="lbl.mais.mensagens"/></span>	                                                
							                                                
							                                                <span class="media-meta pull-right">	                                                		                                  
							                                                	<fmt:formatDate value="${mensagem.dataMensagem}" pattern='dd/MM/yyyy'/></span>
							                                                	
							                                                	<c:if test="${(mensagem.status == 'N')}">
							                                                		<spring:message code="lbl.nova.mensagem.resum"/>	                                                	
							                                                	</c:if>
							                                                <!--/ End meta icon -->
							                                            </div><!-- /.media-body -->
							                                    </a>													
															</c:when>												
														</c:choose>
			                                        </c:forEach>
			                                        
			                                    </div>
			                                    <!--/ End list message -->
			
			                                </div><!-- /.panel-body -->
                            		</c:when>
                            	
                            		<c:when test="${empty listaMinhasMensagens}">
                            			<div class="callout callout-warning">
		                                    <strong><spring:message code="lbl.nenhuma.mensagem"/> </strong>		                                    
		                                </div>
                            		</c:when>
                            	</c:choose>
                               
                            </div>
                        </div>
                </div>       
       		</div>
         
            </section><!-- /#page-content -->
			
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
        
         <div id="idModalItem" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				          <h4 class="modal-title"> <div id="msgModalFuncionalidade" > </div>  </h4>
				        </div>
				        <div class="modal-body">  
				       	   <strong> <spring:message code="lbl.descricao.geral"/>:  </strong> <div id="msgModalFunc" > </div>
				        </div>
				        <div class="modal-footer">			          
	                      <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>
				        </div>
				      </div>
				  </div>
			</div>
        
    </body>
    <!--/ END BODY -->

</html>