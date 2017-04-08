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
	    			$('#msgModal').html("<spring:message code='lbl.header.tela.exception.erro.generico.desc.modal'/>");
	    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.header.tela.exception.erro.generico'/>");
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
                	  
                   <div class="container">
						<div class="row">
							<div class="col-md-6 col-md-offset-3">
					            <div class="panel panel-primary">
					                <div class="panel-heading">
					                    <h3 class="panel-title"> <spring:message code="lbl.header.tela.exception.erro.generico"/></h3>
					                    <span class="pull-right">
					                        <!-- Tabs -->
					                        <ul class="nav panel-tabs">
					                            <li class="active"><a href="#tab1" data-toggle="tab"></a></li>                           
					                        </ul>
					                    </span>
					                </div>
					                <div class="panel-body">
					                    <div class="tab-content">
					                        <div class="tab-pane active" id="tab1"> <spring:message code="msg.principal.tela.exception"/> </div>                       
					                    </div> 
					                </div>                 
					            </div>
					            
					             <div class="section colm colm5 spacer-b0" align="center">  
					                     <a href="${urlUsuario}/telaInicial" ><spring:message code="lbl.voltar.tela.login"/></a>
					             </div><!-- end section -->
					                               
					        </div>
						</div>
					</div>
                            
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
         <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="./layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->
    </body>

</html>