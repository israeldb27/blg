<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/admin" var="urlAdmin"/>


    <!-- START @HEAD -->
 <script type="text/javascript">

$(document).ready(function() {

});

</script>		
 
<c:import url="../layout-admin/head-layout.jsp"></c:import>
   
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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.parametros.servicos"/> </h2>                                                      
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                        <div class="col-md-12">                        		

                            <div class="panel rounded shadow">
                                
                                <div class="panel-body no-padding">
                                                                     
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->
                            <c:if test="${msgValidacaoListaMeusServicos != null }">
                             	<div class="alert alert-danger">
                                      <strong>${msgValidacaoListaMeusServicos}</strong> 
                                  </div>
                            </c:if>      
                      
                      		<c:if test="${ empty listaParamServico }">
                                 	
                                 	<div class="panel" align="center">	                                
		                                <div class="panel-body">
		                                    <spring:message code="msg.lista.servicos.vazio"/>
		                                </div><!-- /.panel-body -->
		                            </div><!-- /.panel -->
                             </c:if>
                      
                            <!-- Start basic color table -->
                            
                           <c:if test="${ not empty listaParamServico }"> 
                            <div class="panel">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.lista.servicos"/></h3>
                                    </div>
                                    <div class="pull-right">
                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                 
                                <div class="panel-body no-padding">                                 	
                                 
                                    <div class="table-responsive" style="margin-top: -1px;">
                                        <table class="table table-striped table-primary">
                                            <thead>
                                            <tr>
                                                <th class="text-center"><spring:message code="lbl.admin.label.servico"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.value.servico"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.tipo.param.servico"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.descricao.servico"/></th>                                                
                                                <th class="text-center"><spring:message code="lbl.admin.cobranca.servico"/></th>                                                
                                                <th class="text-center"><spring:message code="lbl.admin.col.acoes"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="parametro" items="${listaParamServico}" >
	                                            <tr>
	                                                <td class="text-center"> ${parametro.labelServico}</td>
	                                                <td class="text-center">${parametro.valueServico}</td>
	                                                <td class="text-center"><c:if test="${parametro.tipoParamServico == 'U'}">
												              					<spring:message code="lbl.opcao.tipo.servico.usuario"/>
												              				</c:if>
												              				<c:if test="${parametro.tipoParamServico == 'R'}">
												              					<spring:message code="lbl.opcao.tipo.servico.relatorio"/>
												              				</c:if>
												              				<c:if test="${parametro.tipoParamServico == 'I'}">
												              					<spring:message code="lbl.opcao.tipo.servico.imovel"/>
												              				</c:if>
												              				<c:if test="${parametro.tipoParamServico == 'A'}">
												              					<spring:message code="lbl.opcao.tipo.servico.assinatura"/>
												              				</c:if></td>
	                                                <td class="text-center">${parametro.descricao}</td>
	                                                <td class="text-center"><c:if test="${parametro.cobranca == 'S'}">
												              					<spring:message code="lbl.sim"/>
												              				</c:if>
												              				<c:if test="${parametro.cobranca == 'N'}">
												              					<spring:message code="lbl.nao"/>
												              				</c:if></td>
													
													<td class="text-center">														  	
														  <a href="${urlAdmin}/manterParamServico/atualizar/${parametro.id}" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> <spring:message code="lbl.table.param.servico.atualizar"/></a>			                      											                      											                      
													</td>	                                            
	                                            </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div><!-- /.table-responsive -->
                                    
                                </div><!-- /.panel-body -->                               
                            </div><!-- /.panel -->
                            <!--/ End basic color table -->
							</c:if>
                        </div><!-- /.col-md-9 -->
                                        
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
         
            </section><!-- /#page-content -->        
          
        </section><!-- /#wrapper -->
        
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
       
  		<c:import url="../layout-admin/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>