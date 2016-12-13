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
                                    <form:form class="form-horizontal mt-10" method="POST" id="servicoForm" modelAttribute="servicoForm" action="${urlServico}/confirmarPagtoServicoPayPal" >
                                    <form:hidden path="id" />
                                      <div class="form-body">
                                        <div class="form-group"> 
	                                        <label  class="col-sm-4 control-label"><spring:message code="lbl.servico.selecionado"/>:</label>
	                                        <div class="col-sm-7"> 
													${servicoForm.acaoFmt}
                                            </div>          
                                        </div>    
                                        <div class="form-group">    
                                            <label class="col-sm-4 control-label"><spring:message code="lbl.servico.plano.forma.pagto"/>:</label>
	                                        <div class="col-sm-7">
	                                            ${servicoForm.formaPagtoSelecionado}
                                            </div>
                                        </div>    
                                        <div class="form-group">    
                                            <label class="col-sm-4 control-label"><spring:message code="lbl.servico.valor"/>:</label>
	                                        <div class="col-sm-7">
	                                            <fmt:formatNumber value="${servicoForm.valorServico}" pattern="#,##0.00;-0"/> 
                                            </div>                                                                              
                                        </div>
                                        <div class="form-group">    
                                            <label class="col-sm-4 control-label"><spring:message code="lbl.servico.quantidade"/>:</label>
                                            <div class="col-sm-7">
	                                            ${servicoForm.quantidadeServico} 
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
                            </div><!-- /.panel -->
                        </div>          
                                             
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->

         
            </section><!-- /#page-content -->
   
			<!-- Start inside form layout -->             
            <div class="modal fade bs-example-modal-form" tabindex="-1" role="dialog" aria-hidden="true">
              
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.detalhes.imovel"/></h4> 
                        </div>
                        <div class="modal-body no-padding">
                            <form name="userForm" class="form-horizontal form-bordered" role="form">
                                <div class="form-body">
                                	<div class="form-group">                                	
                                        <label for="idTituloImovelSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.titulo.imovel"/>: </label>
                                        <div class="col-sm-7">
                                            
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="idTipoImovel" class="col-sm-3 control-label"><spring:message code="lbl.tipo.imovel"/>: </label>
                                        <div class="col-sm-7">
                                            Apto
                                        </div>
                                    </div><!-- /.form-group -->
                                	
                                    <div class="form-group">
                                        <label for="idEstadoSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.estado"/>: </label>
                                        <div class="col-sm-7">
                                            
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="idCidadeSelecionada" class="col-sm-3 control-label"><spring:message code="lbl.cidade"/>: </label>
                                        <div class="col-sm-7">
                                        	<div id="resposta"></div>
                                            
                                        </div>
                                    </div><!-- /.form-group -->                                    
                                    <div class="form-group">
                                        <label for="idBairroSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.bairro"/>: </label>
                                        <div class="col-sm-7">
                                            ${imovel.dataCadastro}
                                        </div>
                                    </div><!-- /.form-group -->
                                     <div class="form-group">
                                        <label for="idDataCadastro" class="col-sm-3 control-label"><spring:message code="lbl.data.cadastro.imovel"/>: </label>
                                        <div class="col-sm-7">
                                            <div class="elemento"></div>
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="idAcao" class="col-sm-3 control-label"><spring:message code="lbl.acao.imovel"/>: </label>
                                        <div class="col-sm-7">
                                             <input type="text" class="form-control input-sm" id="firstname-1" >
                                        </div>
                                    </div><!-- /.form-group -->
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-success">Sign in</button>
                                    </div>
                                </div><!-- /.form-footer -->
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->               
            </div><!-- /.modal -->
            
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