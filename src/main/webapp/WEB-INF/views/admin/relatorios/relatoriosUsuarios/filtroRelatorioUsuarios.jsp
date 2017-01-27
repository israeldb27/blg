<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>
<c:set var="context" value="<%= request.getContextPath()%>"/>
<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/admin" var="urlAdmin"/>

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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.relatorios.usuarios"/> <span> ${administracaoForm.tipoRelatorioFmt} </span>  </h2>                                                                                                                                            
                </div>
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                    <div class="row">
                    	<form:form method="POST" class="form-horizontal form-bordered col-sm-3" id="administracaoForm" modelAttribute="administracaoForm" action="${urlAdmin}/voltarSelecaoRelatorio" >
                    	 	<form:hidden id="tipoFiltro" path="tipoFiltro" value="usuarios" />
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
                                    <form:form class="form-horizontal mt-12" method="POST" id="administracaoForm" modelAttribute="administracaoForm" action="${urlAdmin}/gerarRelatorioUsuarios" >                                    	
                                    <c:if test="${administracaoForm.tipoRelatorio == 'quantTotal'}">                    				
                                    	<form:hidden id="tipoRelatorio" path="tipoRelatorio" value="quantTotal"  />                    					                                  	
                                    </c:if>	
                                    <c:if test="${administracaoForm.tipoRelatorio == 'acessoUsuario'}">
                                    	<form:hidden id="tipoRelatorio" path="tipoRelatorio" value="acessoUsuario"  />                                    	
                                    </c:if>
                                    <c:if test="${administracaoForm.tipoRelatorio == 'quantUsuariosxServicos'}">
                                    	<form:hidden id="tipoRelatorio" path="tipoRelatorio" value="quantUsuariosxServicos"  />
                                    </c:if>
                                    <c:if test="${administracaoForm.tipoRelatorio == 'ultimoAcessoUsuarioPeriodo'}">                                    	
                                    	<form:hidden id="tipoRelatorio" path="tipoRelatorio" value="ultimoAcessoUsuarioPeriodo"  />
                                    </c:if>   
                                    
                                      <div class="form-body">
                                        	<div class="form-group">
	                                        	<label for="idEstado" class="col-sm-4 control-label"><spring:message code="lbl.estado"/> :</label>
	                                        	<div class="col-sm-7">                                        	
		                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" >                                
														<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
														<form:options items="${administracaoForm.listaEstados}" itemValue="key" itemLabel="label"/>
												  </form:select>
		                                        </div>
		                                    </div>  
		                                     
		                                    <div class="form-group">
		                                        <label for="idCidade" class="col-sm-4 control-label"><spring:message code="lbl.cidade"/>:</label>
		                                        <div class="col-sm-7">
		                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;">                                
														<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
														<form:options items="${administracaoForm.listaCidades}" itemValue="key" itemLabel="label"/>
												  </form:select>
	                                            </div>
	                                        </div>
	                                             
	                                        <div class="form-group">
	                                            <label for="idBairro" class="col-sm-4 control-label"><spring:message code="lbl.bairro"/>:</label>
		                                        <div class="col-sm-7">
		                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;">                                
														<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
														<form:options items="${administracaoForm.listaBairros}" itemValue="key" itemLabel="label"/>
												  </form:select>
	                                            </div>
	                                       </div>   
	                                       
	                                       <div class="form-group">	                                       	
	                                       		<label for="perfilUsuario" class="col-sm-4 control-label"><spring:message code="lbl.filtro.perfil.usuario"/>:</label>
		                                       		<div class="col-sm-7">
		                                       			<form:select id="perfilUsuario" path="perfilUsuario" class="chosen-select" tabindex="-1" style="display: none;">                                
									                       <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
														   <form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
									                    </form:select>
		                                       		</div>    										  
												<br>
											</div>
		                                    
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
            </section><!-- /#page-content -->
        </section><!-- /#wrapper -->

        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
       
  		<c:import url="../../layout-admin/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>