<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/servico" var="urlServico"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

    
<script type="text/javascript">
	$(document).ready(function() {

	    
	});	

</script>

      <c:import url="../../layout/cadastroUsuario/head-layout.jsp"></c:import>
   
    <body>

        <!-- INICIO - Cadastro ASsinatura -->
            
            <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.status.sol.assinatura.usuario"/> </h2>                                                                        
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                   
                     <div class="row">
						
						<!--/ INICIO ABA PASSWORD -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.selecao.pagto.assinatura"/>  <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                
                                <div class="panel-body no-padding">
                                        
		                                    <form:form class="form-horizontal mt-10" method="POST" id="usuarioForm" modelAttribute="usuarioForm" action="${urlUsuario}/confirmarPagtoAssinaturaPayPal" >
			                                    <form:hidden path="id" />
			                                      <div class="form-body">
			                                        <div class="form-group"> 
				                                        <label  class="col-sm-4 control-label"><spring:message code="lbl.servico.selecionado"/>:</label>
				                                        <div class="col-sm-7">				                                            
				                                            <spring:message code="lbl.assinatura.confirmacao.pagto"/>
			                                            </div>          
			                                        </div>    
			                                        <div class="form-group">    
			                                            <label class="col-sm-4 control-label"><spring:message code="lbl.servico.plano.forma.pagto"/>:</label>
				                                        <div class="col-sm-7">
				                                        	<c:if test="${usuarioForm.servicoForm.formaPgto == 'payPal'}">
				                                        		PayPal
				                                        	</c:if>
				                                        	<c:if test="${usuarioForm.servicoForm.formaPgto == 'pagSeguro'}">
				                                        		PagSeguro
				                                        	</c:if>
				                                            
			                                            </div>
			                                        </div>    
			                                        <div class="form-group">    
			                                            <label class="col-sm-4 control-label"><spring:message code="lbl.servico.valor"/>:</label>
				                                        <div class="col-sm-7">
				                                            <fmt:formatNumber value="${usuarioForm.servicoForm.valorServico}" pattern="#,##0.00;-0"/> 
			                                            </div>                                                                              
			                                        </div>
			                                        <div class="form-group">    
			                                            <label class="col-sm-4 control-label"><spring:message code="lbl.servico.duracao.assinatura"/>:</label>
				                                        	${usuarioForm.servicoForm.tempoDuracao}                                                                              
			                                        </div>
			                                        
			                                        <div class="form-group">    
			                                            <label class="col-sm-4 control-label"><spring:message code="lbl.servico.status.assinatura"/>:</label>
				                                        	
				                                        	<c:if test="${usuarioForm.servicoForm.statusPgto == 'pago'}">
				                                        		Pago
				                                        	</c:if>
				                                        	<c:if test="${usuarioForm.servicoForm.statusPgto == 'aguardando'}">
				                                        		Aguardando Pagamento
				                                        	</c:if>
				                                        	<c:if test="${usuarioForm.servicoForm.statusPgto == 'solicitado'}">
				                                        		Solicitado
				                                        	</c:if>                                                                                                
			                                        </div>
			                                        
			                                        <div class="form-group">    
			                                            <button type="submit" class="btn btn-primary btn-block"><spring:message code="lbl.btn.confirmar.geral"/></button>
			                                        </div><!-- /.form-group -->
			                                       </div> 
			                                </form:form>
                            
                        </div>
                		
                        </div><!-- /.row -->
                      </div>                 
                            
                  </div><!-- /.body-content -->
                </div>               
        
		        <div id="back-top" class="animated pulse circle">
		            <i class="fa fa-angle-up"></i>
		        </div><!-- /#back-top -->
		
		  		<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
            </body>
    

</html>