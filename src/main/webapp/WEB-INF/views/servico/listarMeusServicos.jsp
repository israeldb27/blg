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
                        <div class="col-md-9">                        		

                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.filtro.geral"/></h3>
                                    </div><!-- /.pull-left -->
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>
                                    </div><!-- /.pull-right -->                                    
                                    
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                    <form:form class="form-horizontal mt-10" method="POST" id="servicoForm" modelAttribute="servicoForm" action="${urlServico}/selecionarServicoDisponivel" >
                                      <div class="form-body">
                                        <div class="form-group">                                        	
                                        	
	                                        <label for="idServicoSelecionado" class="col-sm-4 control-label"><spring:message code="lbl.servicos.disponiveis"/>:</label>
	                                        <div class="col-sm-7">
	                                            <form:select id="idServicoSelecionado" path="idServicoSelecionado" class="form-control">                                
													<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${servicoForm.listaServicosDisponiveis}" itemValue="id" itemLabel="labelServico"/>
											  </form:select>
                                            </div>  
                                            
                                            <label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
                                            <div class="col-sm-7">
                                            	<br>
                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 40%;"><spring:message code="lbl.btn.adicionar.geral"/></button>                                            	
                                            </div>                                                                                    
                                            
                                            
                                        </div><!-- /.form-group -->
                                       </div> 
                                    </form:form>                                    
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->
                            <c:if test="${msgValidacaoListaMeusServicos != null }">
                             	<div class="alert alert-danger">
                                      <strong>${msgValidacaoListaMeusServicos}</strong> 
                                  </div>
                            </c:if>      
                      
                      		<c:choose>
                      			<c:when test="${ empty listaMeusServicos }">
                      				<div class="panel" align="center">	                                
		                                <div class="panel-body">
		                                    <spring:message code="msg.lista.servicos.vazio"/>
		                                </div><!-- /.panel-body -->
		                            </div><!-- /.panel -->
                      			</c:when>
                      			
                      			<c:when test="${ not empty listaMeusServicos }">
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
		                                                <th class="text-center"><spring:message code="lbl.table.nome.servico"/></th>
		                                                <th class="text-center"><spring:message code="lbl.table.status.servico"/></th>
		                                                <th class="text-center"><spring:message code="lbl.table.data.sol.servico"/></th>
		                                                <th class="text-center"><spring:message code="lbl.table.data.pagto.servico"/></th>                                                
		                                                <th class="text-center"><spring:message code="lbl.table.data.acoes"/></th>                                                
		                                            </tr>
		                                            </thead>
		                                            <tbody>
		                                            <c:forEach var="servico" items="${listaMeusServicos}" >
			                                            <tr>
			                                                <td class="text-center">	
			                                                	<c:choose>
			                                                		<c:when test="${(servico.descServico == 'relatorioCorretor' || servico.descServico == 'relatorioCliente' || servico.descServico == 'relatorioImobiliaria')}" >
										                    			<spring:message code="lbl.table.result.relatorio"/>
											                    	</c:when>
											                    	<c:when test="${(servico.descServico == 'assinaturaCorretor' || servico.descServico== 'assinaturaPadrao' || servico.descServico == 'assinaturaImobiliaria')}" >
											                    		<spring:message code="lbl.table.result.assinatura"/>
											                    	</c:when>				
											                    	 <c:when test="${(servico.tipoServicoComum == 'N')}" >
											                    		 	${servico.paramServico.labelServico}
											                    	</c:when>			                                                	
			                                                	</c:choose>             				
			                                                </td>
			                                                <td class="text-center">${servico.statusPgtoFmt} </td>
			                                                <td class="text-center"><fmt:formatDate value='${servico.dataSolicitacao}' pattern='dd/MM/yyyy'/></td>
			                                                <td class="text-center"><fmt:formatDate value='${servico.dataPagto}' pattern='dd/MM/yyyy'/></td>	                                                
															
															<td class="text-center">
																  <c:choose>
																  	<c:when test="${servico.statusPgto == 'pago'}">
																  		<a href="#" class="btn btn-success btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="<spring:message code='lbl.table.result.servico.detalhes'/>"><i class="fa fa-eye"></i></a>
																  	</c:when>
																  	
																  	<c:when test="${servico.statusPgto == 'solicitado'}">  
																		<a href="#" class="btn btn-success btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="<spring:message code='lbl.table.result.servico.detalhes'/>"><i class="fa fa-eye"></i></a>
																		<a href="${urlServico}/simularRetornoPagto/${servico.id}" class="btn btn-primary btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="Simular Retorno Pagto"><i class="fa fa-pencil"></i></a>																 																
																		<a href="${urlServico}/efetuarPagtoSolServico/${servico.id}" class="btn btn-primary btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="<spring:message code='lbl.table.result.servico.efetivar.pagto'/>"><i class="fa fa-pencil"></i></a>									                       											                      		
											                      		<a href="${urlServico}/excluirSolServico/${servico.id}" class="btn btn-danger btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="<spring:message code='lbl.table.result.servico.exclui.sol'/>"><i class="fa fa-times"></i></a>
											                      	</c:when>
																  	
																  	<c:when test="${servico.statusPgto == 'aguardando'}">
																		<a href="#" class="btn btn-success btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="<spring:message code='lbl.table.result.servico.detalhes'/>"><i class="fa fa-eye"></i></a>												
																		<a href="${urlServico}/simularRetornoPagto/${servico.id}" class="btn btn-primary btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="Simular Retorno Pagto"><i class="fa fa-pencil"></i></a>																				
											                      	</c:when>
											                      	
																  </c:choose>          	
															</td>	                                            
			                                            </tr>
		                                            </c:forEach>
		                                            </tbody>
		                                        </table>
		                                    </div><!-- /.table-responsive -->
		                                    
		                                </div><!-- /.panel-body -->                               
		                            </div><!-- /.panel -->
                      			</c:when>
                      		</c:choose>                 
                        </div><!-- /.col-md-9 -->
                        <!--  START SIDEBAR RIGHT -->
                        <div class="col-md-3">
                            <c:import url="../layout/sidebar-right.jsp"></c:import>
                        </div>                        
                        <!--  END SIDEBAR RIGHT -->                       
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
                
                <!-- Fim Meus Imoveis -->
         
            </section><!-- /#page-content -->
        
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
          
        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
       
  		<c:import url="../layout/head-bootstrap.jsp"></c:import> 
    </body>
    <!--/ END BODY -->

</html>