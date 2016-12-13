<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/servico" var="urlServico"/>

 <script type="text/javascript">

$(document).ready(function() {

});

</script>
		
        <c:import url="../layout/head-layout.jsp"></c:import>
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.servicos"/> </h2>                                                      
					
					<!-- Start header modal Ajuda - funcionalidade -->
						<c:import url="../ajuda/headerMenuModal.jsp"></c:import>																				
					<!-- End header  modal Ajuda - funcionalidade -->
					
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                        <div class="col-md-12">                        		

                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <div class="pull-left"> 
                                        <h3 class="panel-title"><spring:message code="lbl.selecao.pagto.servico"/></h3>
                                    </div><!-- /.pull-left -->
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>
                                    </div><!-- /.pull-right -->                                    
                                    
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                    <form:form class="form-horizontal mt-10" method="POST" id="servicoForm" modelAttribute="servicoForm" action="${urlServico}/selecionarPagamentoServico" >
                                      <div class="form-body">
                                        <div class="form-group"> 
	                                        <label for="idServicoSelecionado" class="col-sm-4 control-label"><spring:message code="lbl.servico.selecionado"/>:</label>
	                                        <div class="col-sm-7">
												${servicoForm.acaoFmt}												
                                            </div>          
                                        </div>    
                                        <div class="form-group">    
                                            <label for="idFormapagamentoSelecionada" class="col-sm-4 control-label"><spring:message code="lbl.servico.plano.forma.pagto"/>:</label>
	                                        <div class="col-sm-7">
	                                            <form:select id="idFormapagamentoSelecionada" path="idFormapagamentoSelecionada" class="form-control">
										                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${servicoForm.listaOpcoesFormaPagamento}" itemValue="id" itemLabel="label"/>
										         </form:select>
												 <form:errors id="idFormapagamentoSelecionada" path="idFormapagamentoSelecionada" cssClass="errorEntrada"  />
                                            </div>
                                        </div>    
                                        <div class="form-group">    
                                            <label for="idInfoServicoPagamentoSelecionada" class="col-sm-4 control-label"><spring:message code="lbl.servico.plano.valores.disponiveis"/>:</label>
	                                        <div class="col-sm-7">
	                                            <form:select id="idInfoServicoPagamentoSelecionada" path="idInfoServicoPagamentoSelecionada" class="form-control">										                
										                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${servicoForm.listaInfoServicoPagamento}" itemValue="id" itemLabel="labelDuracao"/>
										         </form:select>
												 <form:errors id="idInfoServicoPagamentoSelecionada" path="idInfoServicoPagamentoSelecionada" cssClass="errorEntrada"  />
                                            </div>                                                                              
                                        </div>
                                        <div class="form-group">  
                                        	<label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
                                            <div class="col-sm-7">
                                            	<br>
                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 40%;"><spring:message code="lbl.btn.confirmar.geral"/></button>                                            	
                                            </div>
                                          
                                        </div><!-- /.form-group -->
                                       </div> 
                                    </form:form>      
                                </div><!-- /.panel-body -->
                                
                                     <c:if test="${msgValidacaoCadastroPagtoServico != null }">
			                             	<div class="alert alert-danger">
			                                      <strong>${msgValidacaoCadastroPagtoServico}</strong> 
			                                  </div>
			                         </c:if>
                                                                
                            </div><!-- /.panel -->
                        </div>          
                                             
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->

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