<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/servico" var="urlServico"/>
<spring:url value="/relatorio" var="urlRelatorio"/>

 <script type="text/javascript">

$(document).ready(function() {

});

</script>		
        <c:import url="../../layout/head-layout.jsp"></c:import>
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.relatorios"/> </h2>                                                      
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                <div class="row">
                        <div class="col-md-9">

                            <!-- Start inline form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <div class="pull-left">  
                                        <h3 class="panel-title"><spring:message code="lbl.title.link.relatorio.cobranca.servico"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">                                   
										<div class="callout callout-info mb-20">												
			                                <p><spring:message code="lbl.relatorio.aviso.cobranca.servico"/></p>
			                            </div>
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End inline form -->
                        
                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.relatorio.title.selecao.servico"/></h3>
                                    </div><!-- /.pull-left -->
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>
                                    </div><!-- /.pull-right -->                                    
                                    
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                
                                    <form:form method="POST" class="form-horizontal mt-10" id="relatorioForm" modelAttribute="relatorioForm" action="${urlRelatorio}/selecionarRelatorioCobranca" >
                                      <div class="form-body">
                                        <div class="form-group"> 
		                                        <label for="idInfoServicoPagamentoSelecionada" class="col-sm-4 control-label"><spring:message code="lbl.relatorio.valores.disponiveis"/>:</label>
		                                        <div class="col-sm-7">											  
												  <form:select id="idInfoServicoPagamentoSelecionada" path="idInfoServicoPagamentoSelecionada" class="form-control">
										                <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${relatorioForm.listaInfoServicoPagamento}" itemValue="id" itemLabel="labelDuracao"/>
										         </form:select>
										         <form:errors id="idInfoServicoPagamentoSelecionada" path="idInfoServicoPagamentoSelecionada" cssClass="errorEntrada"  />							         
                                               </div>             
                                       </div>
                                       <div class="form-group">
                                            <label for="idFormapagamentoSelecionada" class="col-sm-4 control-label"><spring:message code="lbl.mensagem.forma.pagamento"/>:</label>
	                                        <div class="col-sm-7">
											    <form:select id="idFormapagamentoSelecionada" path="idFormapagamentoSelecionada" class="form-control">
										                <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${relatorioForm.listaOpcoesFormaPagamento}" itemValue="id" itemLabel="label"/>
										         </form:select>
										         <form:errors id="idFormapagamentoSelecionada" path="idFormapagamentoSelecionada" cssClass="errorEntrada"  />
                                            </div>
                                       </div>                                                                              
                                       <div class="form-group">     
                                            <button type="submit" class="btn btn-primary btn-block"><spring:message code="lbl.btn.selecionar.geral"/></button>
                                        </div><!-- /.form-group -->
                                       </div> 
                                    </form:form>                            
                                    
                                    
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->
                            <!--  START SIDEBAR RIGHT -->
                        <div class="col-md-3">
                            <c:import url="../../layout/sidebar-right.jsp"></c:import>
                        </div>                        
                        <!--  END SIDEBAR RIGHT -->
                        </div>        
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
           
            </section><!-- /#page-content -->
        </section><!-- /#wrapper -->
        
		<!--/ Start - modal Ajuda - informações funcionalidade -->            	
            <div class="modal fade bs-modal-ajuda-informacoes" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="panel panel-tab panel-tab-double">
                            <!-- Start tabs heading -->
                            <div class="panel-heading no-padding">
                                <ul class="nav nav-tabs">
                                    <li class="active">
                                        <a href="#tab2-1" data-toggle="tab">
                                            <i class="fa fa-file-text"></i>
                                            <div>
                                                <span class="text-strong"><spring:message code="lbl.title.modal.descricao"/></span>                                                
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#tab2-2" data-toggle="tab">
                                            <i class="fa fa-file-text"></i>
                                            <div>
                                                <span class="text-strong"><spring:message code="lbl.title.modal.regras.basicas"/></span>                                                
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#tab2-3" data-toggle="tab">
                                            <i class="fa fa-file-text"></i>
                                            <div>
                                                <span class="text-strong"><spring:message code="lbl.title.modal.objetivo"/></span>                                                
                                            </div>
                                        </a>
                                    </li>                                    
                                </ul>
                            </div>
                            <!--/ End tabs heading -->

                            <!-- Start tabs content -->
                            <div class="panel-body">
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="tab2-1">
                                        <h4><spring:message code="lbl.title.modal.descricao"/></h4>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    </div>
                                    <div class="tab-pane fade" id="tab2-2">
                                        <h4><spring:message code="lbl.title.modal.regras.basicas"/></h4>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    </div>
                                    <div class="tab-pane fade" id="tab2-3">
                                        <h4><spring:message code="lbl.title.modal.objetivo"/></h4>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    </div>                                    
                                </div>
                            </div>
                            <!--/ End tabs content -->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.title.modal.fechar"/></button>                            
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->            
			<!--/ End - modal Ajuda - informações funcionalidade -->

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