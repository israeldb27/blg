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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.relatorios.servicos"/> <span> ${administracaoForm.tipoRelatorioFmt}  </span> </h2> 
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                    <div class="row">     
                    	<form:form method="POST" class="form-horizontal form-bordered col-sm-3" id="administracaoForm" modelAttribute="administracaoForm" action="${urlAdmin}/voltarFiltroRelatorio" >
                    	 	<form:hidden id="tipoFiltro" path="tipoFiltro" value="servicos" />
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
																	<c:if test="${((administracaoForm.tipoRelatorio == 'volFinanceiroServicos') && (not empty administracaoForm.listaServicos))}">                    				
																		<th><spring:message code="lbl.relatorios.col.nome.servico"/> </th>
																		<th><spring:message code="lbl.relatorios.col.vol.financeiro.servico"/></th>	
																	</c:if>	
																	<c:if test="${administracaoForm.tipoRelatorio == 'totalServicos'}">
																		<th><spring:message code="lbl.relatorios.col.total.servicos"/></th>
																	</c:if>
																	<c:if test="${((administracaoForm.tipoRelatorio == 'usuariosVolFinancServicos') && (not empty administracaoForm.listaServicos))}">
																		<th> </th> 
																		<th><spring:message code="lbl.relatorios.col.nome.servico"/> </th>
																		<th><spring:message code="lbl.relatorios.col.perfil.servico"/></th>					                                                
																		<th><spring:message code="lbl.relatorios.col.vol.financeiro.servico"/></th>	
																	</c:if>			                                                				                                                
					                                            </tr>
					                                            </thead>
					                                            <tbody>
																	<c:if test="${administracaoForm.tipoRelatorio == 'volFinanceiroServicos'}">
																		<c:if test="${ not empty administracaoForm.listaServicos }">                    				
																			<c:forEach var="servico" items="${administracaoForm.listaServicos}" varStatus="item">
																				<tr class="border-primary">	
																					<td>${servico.labelServico}</td>
																					<td><fmt:formatNumber value="${servico.valorFinanceiro}" pattern="#,##0.00;-0"/></td>					                                                					                                               
																				</tr>					                                  
																			</c:forEach>
																		</c:if>	
																		<c:if test="${ empty administracaoForm.listaServicos }">																			
																						Nenhum dado gerado para o relatório	
																		</c:if>
																	</c:if>	
																	<c:if test="${administracaoForm.tipoRelatorio == 'totalServicos'}">
																		<tr class="border-primary">	
																			<td>${administracaoForm.quantTotal}</td>						                                                					                                                					                                               
																		</tr>
																	</c:if>
																	<c:if test="${administracaoForm.tipoRelatorio == 'usuariosVolFinancServicos'}">
																		<c:if test="${ not empty administracaoForm.listaServicos }">																		
																			<c:forEach var="servico" items="${administracaoForm.listaServicos}" varStatus="item">
																				<tr class="border-primary">					                                                
																					<td>
																						<a href="${urlAdmin}/detalhesUsuario/${servico.usuario.id}" > <img src="${context}${servico.usuario.imagemArquivo}" style="width: 100px; height: 80px; " alt="admin"/></a>						                                                    					                                                    
																					</td>
																					<td><a href="${urlAdmin}/detalhesUsuario/${servico.usuario.id}" > ${servico.usuario.nome} </a></td>
																					<td>${servico.usuario.perfilFmt}</td>																					
																					<td><fmt:formatNumber value="${servico.valorFinanceiro}" pattern="#,##0.00;-0"/></td>					                                                					                                               
																				</tr>					                                  
																			</c:forEach>
																		</c:if>
																		
																		<c:if test="${ empty administracaoForm.listaServicos }">																		
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
            <!--/ End inside form layout -->    

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