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
<spring:url value="/mensagemAdmin" var="urlMensagemAdmin"/>

<%@page import="com.busqueumlugar.enumerador.TipoMensagemAdminEnum"%>
<c:set var="listaTipoMensagemAdminEnum" value="<%= TipoMensagemAdminEnum.values() %>"/>

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
		</script>
		
		<c:import url="../layout-admin/head-layout.jsp"></c:import>
    </head>
    <!--/ END HEAD -->
   
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
  
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.mensagens.admin"/>  </h2>                                                      
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                	<div class="row">                		
                		<form:form method="POST" id="mensagemAdminForm" modelAttribute="mensagemAdminForm" action="${urlMensagemAdmin}/enviarMensagemPorAdmin" class="form-horizontal mt-10">
	                        <div class="col-md-12">
	                        	<div class="panel-body no-padding">
	                                <div class="form-body">
	                                
	                                	<div class="form-group">
	                                		<label for="titulo" class="col-sm-3 control-label"><spring:message code="lbl.mensagem.usuario.de"/>:</label>
	                                		<div class="col-sm-7">                                                    
                                                    <spring:message code="lbl.perfil.usuario.administrador"/>
                                             </div>
	                                    </div><!-- /.form-group -->
	                                    
	                                    <div class="form-group">
	                                		<label for="titulo" class="col-sm-3 control-label"><spring:message code="lbl.mensagem.usuario.para"/>:</label>
	                                		<div class="col-sm-7">                                                    
                                                    ${mensagemAdminForm.nomeUsuarioPara}
                                             </div>
	                                    </div><!-- /.form-group -->
	                                	
	                                	<div class="form-group">
                                             <label for="tipoMensagem" class="col-sm-3 control-label"><spring:message code="lbl.tipo.mensagem"/></label>
                                             <div class="col-sm-7">
									                   <form:select id="tipoMensagem" path="tipoMensagem" class="chosen-select" tabindex="-1" style="display: none;" title="${hintTipoImovel}">                                
									                        <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
									                        <form:options items="${listaTipoMensagemAdminEnum}" itemValue="identificador" itemLabel="rotulo" />								                        
									                 </form:select>
									                 	
											<form:errors id="tipoMensagem" path="tipoMensagem" cssClass="errorEntrada"  />
                                             </div>
                                         </div><!-- /.form-group -->
                                         
                                         <div class="form-group">
                                              <label for="titulo" class="col-sm-3 control-label"><spring:message code="lbl.titulo.mensagem"/></label>
                                              <div class="col-sm-7">                                                    
                                                  <form:input  id="titulo" path="titulo" class="form-control"/>
                                                  <form:errors id="titulo" path="titulo" cssClass="errorEntrada"  />
                                              </div>
                                         </div><!-- /.form-group --> 
                                         
                                         <div class="form-group">
                                              <label for="descricaoUltimaMensagem" class="col-sm-3 control-label"><spring:message code="lbl.descricao.mensagem"/></label>
                                              <div class="col-sm-7">
                                                  <form:textarea  id="descricaoUltimaMensagem" path="descricaoUltimaMensagem" class="form-control" rows="5"/>
                                                  <form:errors id="descricaoUltimaMensagem" path="descricaoUltimaMensagem" cssClass="errorEntrada"  />
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
                    
                    
                    
                    <!-- /Sinalizacao de que a mensagem foi enviada com sucesso -->
                    <div class="row">
                     		<c:if test="${msgSucesso != null }">
			               		 <div class="panel panel-success">
			                          <div class="panel-heading">
			                              <h3 class="panel-title">${msgSucesso}</h3>
			                          </div><!-- /.panel-heading -->			                                                    
			                      </div><!-- /.panel -->                      
			                </c:if>	
			                
			                 <c:if test="${msgErro != null }">
		               		 <div class="panel panel-danger">
		                          <div class="panel-heading">
		                              <h3 class="panel-title"><spring:message code="msg.envio.erro.para.admin"/></h3>
		                          </div><!-- /.panel-heading -->		                          
		                      </div><!-- /.panel -->                      
		                </c:if>		
			         </div>  
       
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