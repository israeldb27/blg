<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/mensagemAdmin" var="urlMensagemAdmin"/>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>
<c:set var="context" value="<%= request.getContextPath()%>"/>
   
<html> 

    <!-- START @HEAD -->
    <head> 
    	<script>
			$(document).ready(function() {
				$("editButton").click( function() {
					 $("firstname-1").val('teste');
				 });					
			});
			
			function populaModal(){
				alert('chegou');				
				$("firstname-1").val("testando");
				$("resposta").html('teste');				
			}
			
			function mostrarModal(id){		
				if (id == 0){
					$('#msgModalFunc').html("<spring:message code='msg.modal.title.link.mensagens.admin.mais'/>");
					$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.link.mensagens.admin'/>");
				}		
				$("#idModalItem").modal("show");
			}
			
		</script>
		
        <c:import url="../../layout/head-layout.jsp"></c:import>
    </head>
    <!--/ END HEAD -->
   
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
                   <h2>
                    	<i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.mensagens.admin"/> 
                    	 <div class="pull-right">
	                        <a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" style="font-size: 15px;"></i></a>                                        
	                     </div>	
                    </h2>                                                   
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                	<div class="row">                		
                		<form:form method="POST" id="mensagemAdminForm" modelAttribute="mensagemAdminForm" action="${urlMensagemAdmin}/adicionarNovaMaisMensagem" class="form-horizontal mt-10">
	                        <div class="col-md-12">
	                        	<div class="panel-body no-padding">
	                                <div class="form-body">
	                                	
	                                	<div class="form-group">
	                                		<label for="titulo" class="col-sm-3 control-label"><spring:message code="lbl.mensagem.assunto"/>:</label>
	                                		<div class="col-sm-7">                                                    
                                                    ${mensagemAdminForm.titulo}
                                             </div>
	                                    </div><!-- /.form-group -->
	                                    
	                                    <div class="form-group">
	                                		<label for="titulo" class="col-sm-3 control-label"><spring:message code="lbl.tipo.mensagem"/>:</label>
	                                		<div class="col-sm-7"> ${mensagemAdminForm.tipoMensagemFmt}</div>
	                                    </div><!-- /.form-group -->
	        
	                                    <div class="form-group">
	                                        <label for="novaMensagem" class="col-sm-3 control-label"><spring:message code="lbl.descricao.imovel"/></label>
	                                        <div class="col-sm-7">                                                    
	                                            <form:textarea  id="novaMensagem" path="novaMensagem" class="form-control" rows="5"/>
	                                            <form:errors id="novaMensagem" path="novaMensagem" cssClass="errorEntrada"  />
	                                        </div>
	                                    </div><!-- /.form-group -->  	
	                                	
	                                </div>
	                            </div>    
	                        </div>
	                        <div class="form-footer">  
                              <div class="col-sm-offset-3">
                                  <button type="submit" class="btn btn-success"><spring:message code="lbl.btn.enviar.dados.geral"/></button>
                              </div>
                          </div><!-- /.form-footer -->   
	                        
                        </form:form>
                    </div>     
                
                
					<div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-success shadow">                                
                                <div class="panel-body no-padding">
                                    <!-- Start list message -->
                                    <div class="media-list">
										<c:forEach var="mensagem" items="${listaMinhasMensagens}">
	                                        <a href="#" class="media">
	                                        	<c:if test="${mensagem.remetenteAdmin == 'S'}">
                      								<div class="pull-left"></div>
                      							</c:if>
                      							<c:if test="${mensagem.remetenteAdmin == 'N'}"> 
                      									 <div class="pull-left"><img src="data:image/jpeg;base64,${usuario.imagemArquivo}" class="media-object img-circle" alt="..."/></div> 
                      							 </c:if>	                                            
	                                            <div class="media-body">
	                                                <span class="media-heading">
	                                                	 <c:if test="${mensagem.remetenteAdmin == 'S'}">
		                      									 <spring:message code="lbl.perfil.usuario.administrador"/> 
		                      							 </c:if>
		                      							 
		                      							 <c:if test="${mensagem.remetenteAdmin == 'N'}">
		                      									${usuario.nome} 
		                      							 </c:if>
		                      						</span>
	                                                <span class="media-text">${mensagem.descricao} </span>
	                                                <!-- Start meta icon -->
	                                                <span class="media-meta"></span>
	                                                <span class="media-meta pull-right"><fmt:formatDate value="${mensagem.dataMensagem}" pattern='dd/MM/yyyy'/></span>
	                                                <!--/ End meta icon -->
	                                            </div><!-- /.media-body -->
	                                        </a><!-- /.media -->
                                        </c:forEach>
                                        
                                    </div>
                                    <!--/ End list message -->

                                </div><!-- /.panel-body -->
                            </div>
                        </div>
                </div>
       		</div>               
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->

          
            <!--/ END SIDEBAR RIGHT -->
             <!-- Start inside form layout -->        
 

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->
        
        <!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
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