<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
				alert('chegou');				
				$("firstname-1").val("testando");
				$("resposta").html('teste');				
			}
			
			function desmarcarCheck(id) {
				$.post("${urlMensagem}/desmarcarCheck", {'idMensagem' : id}, function() {
				  // selecionando o elemento html através da 
				  // ID e alterando o HTML dele    
					$("#idCheckMensagemDiv_"+id).hide();
					$("#idCheckMensagem_"+id).hide();
				});
			  }
		</script>
		
        <c:import url="../layout/head-layout.jsp"></c:import>
    </head>
    <!--/ END HEAD -->
   
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.mensagens"/>  </h2>                                                      					
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
						
                   		<c:if test="${msgErro != null }">
		               		 <div class="panel panel-danger">
		                          <div class="panel-heading">
		                              <h3 class="panel-title">${msgErro}</h3>
		                          </div><!-- /.panel-heading -->		                          
		                      </div><!-- /.panel -->                      
		                </c:if>
				
                	<div class="row">
                		<form:form id="mensagemForm" modelAttribute="mensagemForm" action="${urlMensagem}/adicionarMensagem" class="form-horizontal mt-10" >
	                        <div class="col-md-12">
	                        	<div class="panel-body no-padding">
	                                <div class="form-body">
	                                	
	                                	<div class="form-group">
	                                		<label for="titulo" class="col-sm-3 control-label"><spring:message code="lbl.mensagem.usuario.de"/>:</label>
	                                		<div class="col-sm-7">                                                    
                                                    ${mensagemForm.nomeUsuarioDe}
                                             </div>
	                                    </div><!-- /.form-group -->
	                                    
	                                    <div class="form-group">
	                                		<label for="titulo" class="col-sm-3 control-label"><spring:message code="lbl.mensagem.usuario.para"/>:</label>
	                                		<div class="col-sm-7">                                                    
                                                    ${mensagemForm.nomeUsuarioPara}
                                             </div>
	                                    </div><!-- /.form-group -->
	        
	                                    <div class="form-group">
	                                        <label for="entradaMensagem" class="col-sm-3 control-label"><spring:message code="lbl.descricao.imovel"/></label>
	                                        <div class="col-sm-7">                                                    
	                                            <form:textarea  id="entradaMensagem" path="entradaMensagem" class="form-control" rows="5"/>
	                                            <form:errors id="entradaMensagem" path="entradaMensagem" cssClass="errorEntrada"  />
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
										<c:forEach var="mensagem" items="${listaMensagens}">							
										
	                                        <a href="#" class="media">
	                                            <div class="pull-left"><img src="${context}/${mensagem.usuarioDe.imagemArquivo}" class="media-object img-circle" alt="..."/></div>
	                                            <div class="media-body">
	                                                <span class="media-heading">${mensagem.usuarioDe.nome}</span>
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