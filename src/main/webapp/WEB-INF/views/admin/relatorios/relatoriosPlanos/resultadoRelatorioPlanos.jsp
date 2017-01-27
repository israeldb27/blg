<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/relatorio/buscarRelatorio" var="urlBuscarRelatorios"/>
<spring:url value="/relatorio" var="urlRelatorio"/>
<spring:url value="/admin" var="urlAdmin"/>

    <!-- START @HEAD -->
    
   <script type="text/javascript">

$(document).ready(function() {
	$('#tipo').change(function () {
        var comboPai = '#tipo';
        var comboFilha = '#nome';        
        limpaComboLinha(comboFilha);        
        recuperaRelatorios();
    });

	function limpaComboLinha(comboLinha) {
	    $(comboLinha).empty();  
	    $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');
	    $(comboLinha).trigger("chosen:updated");
	}
});	

function recuperaRelatorios(){
    var parametro1 = $("#tipo").val();
    $.get("${urlBuscarRelatorios}/"+parametro1, function(data){
        jQuery.each(data, function(key, value) {
            if(value.label == null){            	
                return;            }            
            $("#nome").append("<option value='"+value.key+"'>"+value.label+"</option>");
        });
    });
}

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
                	<h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.relatorios.planos"/> <span> ${administracaoForm.tipoRelatorioFmt} </span> </h2>                    
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                    <div class="row">  
                    	 <form:form method="POST" class="form-horizontal form-bordered col-sm-3" id="administracaoForm" modelAttribute="administracaoForm" action="${urlAdmin}/voltarFiltroRelatorio" >
                    	 	<form:hidden id="tipoFiltro" path="tipoFiltro" value="planos" />
                        	<button type="submit" class="btn btn-primary btn-block"><spring:message code="btn.rel.voltar.filtro.relatorio"/></button>
                         </form:form>
                                 
                        <div class="col-md-12" >
                            <div id="basic-wizard-vertical">                                
                                <div class="panel panel-tab panel-tab-double panel-tab-vertical row no-margin rounded shadow">
                                    <!-- Start tabs heading -->
                                    <div class="panel-heading no-padding col-md-3">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab2-2" data-toggle="tab">
                                                    <i class="fa fa-file-text"></i>
                                                    <div>
                                                        <span class="text-strong"><spring:message code="lbl.relatorios.aba.dados"/></span>                                                        
                                                    </div>
                                                </a>
                                            </li>   
											<li>
                                                <a href="#tab2-4" data-toggle="tab">
                                                    <i class="fa fa-credit-card"></i>
                                                    <div>
                                                        <span class="text-strong"><spring:message code="lbl.relatorios.aba.filtros"/></span>                                                        
                                                    </div>
                                                </a>
                                            </li>	
                                        </ul>
                                    </div><!-- /.panel-heading -->
                                    <!--/ End tabs heading -->

                                    <!-- Start tabs content -->
                                    <div class="panel-body col-md-9">
                                        <form action="#" class="tab-content form-horizontal">                                            			
                                            <div class="tab-pane fade in active" id="tab2-2">                                                
                                                <div class="form-group">
                                                    	<div class="table-responsive" style="margin-top: -1px;">
					                                        <table class="table table-default">
					                                            <thead>
					                                            <tr>
																	<c:if test="${administracaoForm.tipoRelatorio == 'totalPlano'}">                    				
																		<th><spring:message code="lbl.admin.total.planos"/></th>
																	</c:if>	
																	<c:if test="${((administracaoForm.tipoRelatorio == 'planosVolFinanceiro') && (not empty administracaoForm.listaRelatorioPlano))}">
																		<th><spring:message code="lbl.admin.nome.plano"/></th>					                                                
																		<th><spring:message code="lbl.admin.rel.vol.financeiro"/></th>
																	</c:if>
																	<c:if test="${((administracaoForm.tipoRelatorio == 'buscaPlanoUsuario') && (not empty administracaoForm.listaPlanoUsuario ))}">
																		<th> </th> 
																		<th><spring:message code="lbl.nome.usuario.plano"/> </th> 
																		<th><spring:message code="lbl.perfil.usuario"/> </th> 
																		<th><spring:message code="lbl.nome.plano"/> </th> 
																		<th><spring:message code="lbl.valor.plano"/> </th> 
																		<th><spring:message code="lbl.status.plano"/> </th> 
																	</c:if>
																	<c:if test="${((administracaoForm.tipoRelatorio == 'usuariosPlanosvolFinanceiro') && (not empty administracaoForm.listaRelatorioPlano))}">
																		<th><spring:message code="lbl.admin.nome.usuario.plano"/></th>					                                            	
																		<th><spring:message code="lbl.admin.nome.plano"/></th>					                                                
																		<th><spring:message code="lbl.admin.rel.vol.financeiro"/></th>
																	</c:if>																			                                                				                                                
					                                            </tr>
					                                            </thead>
					                                            <tbody>
																	<c:if test="${administracaoForm.tipoRelatorio == 'totalPlano'}">                    				
																		<tr class="border-primary">	
																			<td>${administracaoForm.quantTotal}</td>
																		</tr>	
																	</c:if>	
																	<c:if test="${administracaoForm.tipoRelatorio == 'planosVolFinanceiro'}">
																		<c:if test="${ not empty administracaoForm.listaRelatorioPlano }">
																			<c:forEach var="plano" items="${administracaoForm.listaRelatorioPlano}" varStatus="item">
																				<tr class="border-primary">	
																					<td>${plano.nomePlano}</td>
																					<td><fmt:formatNumber value="${plano.valorFinanceiro}" pattern="#,##0.00;-0"/></td>					                                                					                                               
																				</tr>					                                  
																			</c:forEach>
																			
																		</c:if>
																		
																		<c:if test="${ empty administracaoForm.listaRelatorioPlano }">																			
																					Nenhum dado gerado para o relatório	
																		</c:if>	
																		
																	</c:if>
																	
																	
																	<c:if test="${administracaoForm.tipoRelatorio == 'buscaPlanoUsuario'}">
																		<c:if test="${ not empty administracaoForm.listaPlanoUsuario }">
																			<c:forEach var="plano" items="${administracaoForm.listaPlanoUsuario}" varStatus="item">
																				<tr class="border-primary">
																					<td>
																						<a href="${urlAdmin}/detalhesUsuario/${plano.id}" > <img src="${context}${plano.usuario.imagemArquivo}" style="width: 100px; height: 80px; " alt="admin"/></a>						                                                    					                                                    
																					</td>	
																					<td><a href="${urlAdmin}/detalhesUsuario/${plano.id}" > ${plano.usuario.nome} </a></td>
																					<td> ${plano.usuario.perfilFmt}	</td>						                                                					                                                					                                               
																					<td>${plano.nomePlano} </td>
																					<td><fmt:formatNumber value="${plano.valorPlano}" pattern="#,##0.00;-0"/> </td>
																					<td>${plano.status} </td>
																				</tr>
																			</c:forEach>
																		</c:if>
																		
																		<c:if test="${ empty administracaoForm.listaPlanoUsuario }">																			
																					Nenhum dado gerado para o relatório	
																		</c:if>		
																	</c:if>
																	
																	
																	<c:if test="${administracaoForm.tipoRelatorio == 'usuariosPlanosvolFinanceiro'}">
																		<c:if test="${not empty administracaoForm.listaRelatorioPlano }">
																			<c:forEach var="plano" items="${administracaoForm.listaRelatorioPlano}" varStatus="item">
																				<tr class="border-primary">	
																					<td>${plano.nomeUsuario}</td>
																					<td>${plano.nomePlano}</td>
																					<td><fmt:formatNumber value="${plano.valorFinanceiro}" pattern="#,##0.00;-0"/></td>					                                                					                                               
																				</tr>					                                  
																			</c:forEach>
																		</c:if>
																		
																		<c:if test="${ empty administracaoForm.listaRelatorioPlano }">																			
																					Nenhum dado gerado para o relatório	
																		</c:if>		 
																	</c:if>	
					                                            </tbody>
					                                        </table>
					                                    </div>       
                                                </div>                                                                                              
                                            </div>
                                            
													
											<div class="tab-pane fade inner-all" id="tab2-4">
                                                <h4 class="page-header"><spring:message code="lbl.relatorio.lista.filtros.utilizados"/></h4>
                                                <div class="form-group">
                                                    <label class="col-sm-2"></label>
                                                    <div class="col-sm-7">
                                                        <div class="table-responsive">
                                                        <table class="table table-middle">
                                                            <tbody> 
                                                            <c:forEach var="filtro" items="${filtrosUsados}" varStatus="item">                                                        
	                                                            <tr class="border-primary">                                                                
	                                                                <td><b>${filtro.nomeFiltro}</b></td>
	                                                                <td>${filtro.valorFiltro}</td>	                                                                
	                                                            </tr>
                                                            </c:forEach> 
                                                            </tbody>
                                                         </table>
                                                         </div>
                                                    </div>
                                                </div>                                                                                            
                                            </div>
											
                                        </form>
                                     
                                    </div><!-- /.panel-body -->
                                    <!--/ End tabs content -->
                                </div><!-- /.panel -->
                            </div><!-- /#basic-wizard-vertical -->
                            <!--/ End basic wizard vertical-->
						</div>
					</div>		
                </div><!-- /.body-content -->
         
            </section><!-- /#page-content -->
            
        </section><!-- /#wrapper -->
        

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout-admin/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->
