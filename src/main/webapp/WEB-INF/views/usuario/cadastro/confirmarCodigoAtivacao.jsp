<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {	
	
});	

</script>
		
<c:import url="../../layout/cadastroUsuario/head-layout.jsp"></c:import>

   
    <body>

        <!-- INICIO - Cadastro Usuario -->
            
            <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.codigo.ativacao"/> </h2>                                                                        
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn container limit-form" style="width: 800px; height: 200px; min-height: 200px;">
                
                      <!-- <div class="row">
                     		
                     	 		 <div class="alert alert-success">
                                      <strong><spring:message code="msg.cadastro.usuario.sucesso"/></strong> 
                                 </div>                                 	                     	 
			                 
                        </div>--><!-- /.row -->
                         
                         <div class="row">                     		 
                     	 		 <div class="alert alert-success">
                                      <strong> <spring:message code="msg.aviso.codigo.ativacao"/> </strong> 
                                 </div> 
                        </div><!-- /.row -->
                        
                        
                  
                    <form:form id="usuarioForm" modelAttribute="usuarioForm" action="${urlUsuario}/confirmarCodigoAtivacaoPos" class="form-horizontal mt-10">    
                        <div class="row">
                        		<div class="panel panel-info">
	                                <div class="panel-heading"> 
	                                    <h3 class="panel-title"><spring:message code="lbl.aviso.codigo.ativacao"/></h3>
	                                </div><!-- /.panel-heading -->
	                                <div class="panel-body">
	                                    <div class="form-body">
                                        
                                			<div class="form-group">  
                                                <label for="codConfirmacaoAtivacao" class="col-sm-3 control-label"><spring:message code="lbl.codigo.ativacao"/></label>
                                                <div class="col-sm-7">                        
                                                    <form:input id="codConfirmacaoAtivacao" path="codConfirmacaoAtivacao" class="form-control" />
                                                    <form:errors id="codConfirmacaoAtivacao" path="codConfirmacaoAtivacao" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->                                            

                                		</div><!-- /.panel-body -->
                                		
                                		 <div class="form-footer">  
                                            <div class="col-sm-offset-3">
                                            	
                                                <button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 30%;"><spring:message code="lbl.btn.confirmar.dados.geral"/></button>
                                            </div>
                                        </div><!-- /.form-footer --> 
                                        
	                                </div><!-- /.panel-body -->
	                            </div><!-- /.panel -->       
                        </div>
                      
                   </form:form> 
                   
                    <c:if test="${msgErro != null }">
	               		 <div class="alert alert-danger">
	                            <strong>${msgErro}</strong> 
	                      </div>         
	                 </c:if>
                              
                </div><!-- /.body-content -->
                <!--/ End body content -->
                
                	
               		
                
             <!-- FIM - Cadastro Usuario -->       
         
        
        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>