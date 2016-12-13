<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<%@page import="com.busqueumlugar.enumerador.StatusPagtoEnum"%>
<c:set var="listaStatusPagto" value="<%= StatusPagtoEnum.values() %>"/>   

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/admin" var="urlAdmin"/>

<!-- START @HEAD -->
 <script type="text/javascript">

$(document).ready(function() {

});

</script>		
 
<c:import url="../../layout-admin/head-layout.jsp"></c:import>
   
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../../layout-admin/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../../layout-admin/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.relatorios.assinaturas"/> <span> ${administracaoForm.tipoRelatorioFmt} </span> </h2>
                     </h2>                          
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                    	<form:form method="POST" class="form-horizontal form-bordered col-sm-3" id="administracaoForm" modelAttribute="administracaoForm" action="${urlAdmin}/voltarSelecaoRelatorio" >
                    	 	<form:hidden id="tipoFiltro" path="tipoFiltro" value="assinaturas" />
                        	<button type="submit" class="btn btn-primary btn-block"><spring:message code="btn.rel.voltar.filtro.relatorio"/></button>
                         </form:form>
                    
                        <div class="col-md-12">                        		

                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <div class="pull-left">  
                                        <h3 class="panel-title">Formulário</h3>
                                    </div><!-- /.pull-left -->
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>
                                    </div><!-- /.pull-right -->                                    
                                    
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                    <form:form class="form-horizontal mt-12" method="POST" id="administracaoForm" modelAttribute="administracaoForm" action="${urlAdmin}/gerarRelatorioAssinaturas" >
									
									<c:if test="${administracaoForm.tipoRelatorio == 'totalAssinaturas'}">                    				                    					                      	
										<form:hidden id="tipoRelatorio" path="tipoRelatorio" value="totalAssinaturas"  />
                                    </c:if>	
                                    <c:if test="${administracaoForm.tipoRelatorio == 'assinaturasVolFinanceiro'}">                                    	
										<form:hidden id="tipoRelatorio" path="tipoRelatorio" value="assinaturasVolFinanceiro"  />
                                    </c:if>                                    
                                    <c:if test="${administracaoForm.tipoRelatorio == 'buscaAssinaturasUsuario'}">                                    	
										<form:hidden id="tipoRelatorio" path="tipoRelatorio" value="buscaAssinaturasUsuario"  />
                                    </c:if>   
									
                                      <div class="form-body">                                        	
		                                    <c:if test="${administracaoForm.tipoRelatorio == 'totalAssinaturas'}">
												<div class="form-group">
													<label for="status" class="col-sm-4 control-label"><spring:message code="lbl.status.assinatura"/>:</label>
													<div class="col-sm-7">
														<form:select id="status" path="status" class="form-control">  
																<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${listaStatusPagto}" itemValue="identificador" itemLabel="rotulo" />														    
														</form:select>    
													</div>
												</div><!-- /.form-group -->
											</c:if>
											
											<c:if test="${administracaoForm.tipoRelatorio == 'assinaturasVolFinanceiro'}">
												<div class="form-group">
													<label for="status" class="col-sm-4 control-label"><spring:message code="lbl.status.assinatura"/>:</label>
													<div class="col-sm-7">
														<form:select id="status" path="status" class="form-control">                                
																<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>   
																<form:option value="assinaturaPadrao"> <spring:message code="lbl.status.assinatura.cliente"/></form:option>
																<form:option value="assinaturaCorretor"> <spring:message code="lbl.status.assinatura.corretor"/></form:option>
																<form:option value="assinaturaImobiliaria"> <spring:message code="lbl.status.assinatura.imobiliaria"/></form:option>
														</form:select>    
													</div>
												</div><!-- /.form-group -->
											</c:if>
		                                    
		                                    <div class="form-group">
		                                    	<label  class="col-sm-4 control-label"><spring:message code="lbl.relatorio.data.inicio"/>:</label>
		                                    	<div class="col-sm-7">		                                		
			                                		<form:input id="dataInicio" path="dataInicio" class="form-control" value="" data-date-format="dd/mm/yyyy" />
			                                		<form:errors id="dataInicio" path="dataInicio" cssClass="errorEntrada"  />		
												</div>											
		                                    </div><!-- /.form-group -->
		                                    
		                                    <div class="form-group">
		                                    	<label  class="col-sm-4 control-label"><spring:message code="lbl.relatorio.data.fim"/>:</label>
		                                    	<div class="col-sm-7">		                                		
			                                		<form:input path="dataFim" class="form-control" value="" data-date-format="dd/mm/yyyy" id="dataFim" />
			                                		<form:errors id="dataFim" path="dataFim" cssClass="errorEntrada"  />				                                			
												</div>											
		                                    </div><!-- /.form-group -->		
                                            
                                             <div class="form-group">             			
	                                			 <label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
	                                            <div class="col-sm-7">
	                                            	<br>
	                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 40%;"><spring:message code="btn.rel.gerar.relatorio"/></button>
	                                            </div>
	                                       </div>                                        
                                       </div> 
                                    </form:form>                                    
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->                          
                           
                        </div><!-- /.col-md-9 -->
                                        
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
                
                <!-- Fim Meus Imoveis -->
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->
          
        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
       
  		<c:import url="../../layout-admin/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>