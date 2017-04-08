<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>

<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>

<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
    
    	<script type="text/javascript">
    	
		    	function mostrarModal(id){
		    		
		    		if (id == 0){
		    			$('#msgModal').html("<spring:message code='lbl.header.tela.acesso.invalido.desc.modal'/>");
		    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.header.tela.acesso.invalido'/>");
		    		}
		    		
		    		$("#idModalItem").modal("show");
		    	}
    
		</script>
		
        <c:import url="./layout/cadastroUsuario/head-layout.jsp"></c:import>
	<%@ include file="/WEB-INF/views/layout/head-login.jsp"%>
    <!--/ END HEAD -->
   
    <body class="home">
    	 <section id="wrapper">
    	 	 <section id="wrapper">
    	 	     
				<div class="all">
					<div class="header">   
					    <div class="content">
						<h1 class="logo"><spring:message code="lbl.title.busque.um.lugar"/></h1>
					    </div>
					</div>
			    </div>
            
            <!-- Start header content -->
                <div class="header-content">
                  
                </div><!-- /.header-content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                	  
                   <form:form id="usuarioForm" modelAttribute="usuarioForm" action="${urlUsuario}/submitLogin" class="form-horizontal mt-10">
                        <div class="row"> 
                        	 <div class="col-md-6 col-md-offset-3">
	                        	 <c:if test="${msgError != null }">
				               		 <div class="alert alert-danger">
				                            <strong>${msgError}</strong> 
				                      </div>         
				                 </c:if>	
				                 
				                 <c:if test="${msgSucesso != null }">
		                    	 		<div class="alert alert-success">
		                                     <strong>${msgSucesso}</strong> 
		                                </div>	                     	 
				                 </c:if> 
			                 </div>	
              
	                        <div class="col-md-6 col-md-offset-3">
	                            <!-- Start horizontal form -->
	                            <div class="panel rounded shadow">
	                                <div class="panel-heading">  
	                                    <div class="pull-left">
	                                        <h3 class="panel-title"><spring:message code="lbl.header.tela.acesso.invalido"/>  <code></code></h3>
	                                    </div>
	                                    <div class="pull-right">
	                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);"><i class="fa fa-question" ></i></a>                                        
	                                    </div>
	                                    <div class="clearfix"></div>
	                                </div><!-- /.panel-heading -->
	                                <div class="panel-body no-padding">
	
	                                        <div class="form-body">
	                                        
	                                			<div class="form-group">
	                                                <label for="password" class="col-sm-3 control-label"><spring:message code="lbl.login"/> </label>
	                                                <div class="col-sm-7">    
	                                                	<spring:message code="lbl.digite.login" var="msgDigiteLogin"/>
                                						<form:input path="login" class="form-control" placeholder="${msgDigiteLogin}" />
	                                                    <form:errors id="login" path="login" cssClass="errorEntrada"  />
	                                                </div>
	                                            </div><!-- /.form-group -->
	                                            
	                                            <div class="form-group">
	                                                <label for="password" class="col-sm-3 control-label"><spring:message code="lbl.password"/> </label>
	                                                <div class="col-sm-7">
	                                                	<spring:message code="lbl.digite.senha" var="msgDigiteSenha"/>
                                						<form:password path="password" class="form-control" placeholder="${msgDigiteSenha}" />
	                                                </div>
	                                            </div><!-- /.form-group -->                        
	
	                                		</div><!-- /.panel-body -->
	                                		
	                                		<div class="form-footer">  
				                              <div class="col-sm-offset-3">
				                                  <button type="submit" class="btn btn-success" ><spring:message code="lbl.btn.entrar.geral"/></button>
				                              </div>
				                          </div><!-- /.form-footer -->
				                          
	                            	</div><!-- /.panel -->
	                            <!--/ End horizontal form -->
	                        </div>	                        
	                      <!--/ FIM ABA PASSWORD -->
	                      
	                      <div class="section colm colm5 spacer-b0" align="center">  
					                     <a href="${urlUsuario}/prepararEsqueceuSenha" ><spring:message code="lbl.esqueceu.sua.senha"/></a>
					      </div><!-- end section -->
					      
                        </div><!-- /.row -->
                   </form:form>                                 
                </div><!-- /.body-content -->
               </section>
              </section>
               
   
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
            </div><!-- /.modal -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="./layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->
    </body>

</html>