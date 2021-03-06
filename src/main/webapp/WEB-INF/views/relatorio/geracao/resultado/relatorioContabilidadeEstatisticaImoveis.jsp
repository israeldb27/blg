<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/relatorio/buscarRelatorio" var="urlBuscarRelatorios"/>
<spring:url value="/relatorio" var="urlRelatorio"/>

<script type="text/javascript">

$(window).load(function() {
	 
	 var menuItem = "${relatorioForm.item}";
	 var listaRelatorio = ${relatorioContabilidadeEstatistica};	 
	 var dataArray = null;	
	 if ( listaRelatorio.length > 0 ){
		 
		 if ( menuItem == 'quantImoveisPorLocalizacaoAcaoTipoImovel') {			 
	     	 dataArray = [['Tipo Imóvel', 'Quantidade']];  
		   	 $.each(listaRelatorio, function (index, table) {  
		   	        dataArray.push([table.tipoImovelFmt, table.quantidade]);  
		   	 });
		 }
		 else if ( menuItem == 'tipoImoveisMaisProcuradoPorContato') {			 
	     	 dataArray = [['Tipo Imóvel', 'Quantidade']];  
		   	 $.each(listaRelatorio, function (index, table) {  
		   	        dataArray.push([table.tipoImovelFmt, table.quantidade]);  
		   	 });
		 }
		 else if ( menuItem == 'tipoImoveisMaisProcuradoPorLocalizacao') {			 
	     	 dataArray = [['Tipo Imóvel', 'Quantidade']];  
		   	 $.each(listaRelatorio, function (index, table) {  
		   	        dataArray.push([table.tipoImovelFmt, table.quantidade]);  
		   	 });
		 }
		 else if ( menuItem == 'variacaoPrecosPorTipoImovelPorLocalizacao') {			 
	     	 dataArray = [['Tipo Imóvel', 'Variação Preço']];  
		   	 $.each(listaRelatorio, function (index, table) {  
		   	        dataArray.push([table.tipoImovelFmt, table.variacaoPreco]);  
		   	 });
		 }
	 }	  
	 
  var bardata = dataArray ;
  $.plot("#flot-bar-chart", [ bardata ], {
      series: {
          lines: {
              lineWidth: 1
          },
          bars: {
              show: true,
              barWidth: 0.5,
              align: "center",
              lineWidth: 0,
              fillColor: "#03A9F4"
          }
      },
      grid: {
          borderColor: '#ddd',
          borderWidth: 5,
          labelMargin: 5
      },
      xaxis: {
          mode: "categories",
          tickLength: 0
      }
  }); 
});

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
        <c:import url="../../../layout/head-layout.jsp"></c:import>   
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../../../layout/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../../../layout/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">
            
            	 <!-- Start header content -->
                <div class="header-content">						
						<h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.relatorios"/><span>${relatorioForm.itemFmt}</span></h2>				                                                    
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                    	<% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
							<c:import url="../../../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        
                          <form:form method="POST" class="form-horizontal form-bordered col-sm-3" id="relatorioForm" modelAttribute="relatorioForm" action="${urlRelatorio}/voltarFiltroRelatorio" >
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
                                                <a href="#tab2-3" data-toggle="tab">
                                                    <i class="fa fa-credit-card"></i>
                                                    <div>
                                                        <span class="text-strong"><spring:message code="lbl.relatorios.aba.graficos"/></span>                                                        
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
                                            	<c:if test="${not empty relatorioContabilidadeEstatisticaImoveis}">
                                            		<h4 class="page-header"><spring:message code="lbl.relatorio.lista"/></h4>
	                                                <div class="form-group">
	                                                    	<div class="table-responsive" style="margin-top: -1px;">
						                                        <table class="table table-default">
						                                            <thead>
						                                            <tr>
						                                            	<th> </th>
																		
																				<c:if test="${relatorioForm.idEstado > 0 }">	
																					<th><spring:message code="lbl.estado"/></th>
																					<c:if test="${relatorioForm.idCidade > 0 }">																						
																						<th><spring:message code="lbl.cidade"/></th>																					
																					</c:if>
																					<c:if test="${relatorioForm.idBairro > 0 }">																							
																						<th><spring:message code="lbl.bairro"/></th>																					
																					</c:if>																					
																				</c:if>	
																				<th><spring:message code="lbl.acao.imovel"/> </th> 
																				<th><spring:message code="lbl.tipo.imovel"/></th>
																				<th><spring:message code="lbl.rel.quantidade"/></th>
																				<c:if test="${relatorioForm.item == 'variacaoPrecosPorTipoImovelPorLocalizacao'}">
																					<th><spring:message code="lbl.variacao.precos.imovel"/> </th>
																				</c:if>																																			 				                                                     
						                                            </tr>
						                                            </thead>
						                                            <tbody>
																
																			<c:forEach var="relatorio" items="${relatorioContabilidadeEstatisticaImoveis}" varStatus="item">
																				<tr class="border-primary">					                                                
																					<td></td>
																					<c:if test="${relatorioForm.idEstado > 0 }">	
																						<td>${relatorio.estado}</td>
																						<c:if test="${relatorioForm.idCidade > 0 }">																							
																							<td>${relatorio.cidade}</td>																					
																						</c:if>
																						<c:if test="${relatorioForm.idBairro > 0 }">																							
																							<td>${relatorio.bairro}</td>																					
																						</c:if>																					
																					</c:if>
																					<td>${relatorio.acaoFmt}</td>
																					<td>${relatorio.tipoImovelFmt}</td>
																					<td>${relatorio.quantidade}</td>
																					<c:if test="${relatorioForm.item == 'variacaoPrecosPorTipoImovelPorLocalizacao'}">
																						<td><fmt:formatNumber value="${relatorio.variacaoPreco}" pattern="#,##0.00;-0"/> </td>	<th><spring:message code="lbl.variacao.precos.imovel"/> </th>
																					</c:if>			                                                						                                                				                                                					                                               
																				</tr>					                                  
																			</c:forEach>
						                                            </tbody>
						                                        </table>
						                                    </div>       
	                                                </div>  
                                            	</c:if>
                                            	
                                            	<c:if test="${empty relatorioContabilidadeEstatisticaImoveis}">
				                            		<div class="form-group">
                                                    	<div class="table-responsive" style="margin-top: -1px;">
                                                    		<spring:message code="msg.sem.registro.relatorio"/>
                                                    	</div>
		                                             </div>       	
				                            	</c:if> 
				                            	                                                                                     
                                            </div>
                                            
                                            <div class="tab-pane fade inner-all" id="tab2-3">
                                            	<c:if test="${not empty relatorioContabilidadeEstatisticaImoveis}">
                                            		<div class="form-group">                                                    
	                                                    <div class="col-sm-13">
	                                                         <!-- Start bar chart -->
									                            <div class="panel rounded shadow panel-lilac">
									                                <div class="panel-heading">
									                                    <div class="pull-left">
									                                        <h3 class="panel-title"></h3>
									                                    </div>
									                                    <div class="pull-right">
									                                        <button class="btn btn-sm" data-action="expand" data-toggle="tooltip" data-placement="top" data-title="Expand"><i class="fa fa-expand"></i></button>								                                        
									                                        								                                        
									                                    </div>
									                                    <div class="clearfix"></div>
									                                </div><!-- /.panel-heading -->
									                                <div class="panel-body">
									                                    <div id="flot-bar-chart" class="chart"></div>
									                                </div><!-- /.panel-body -->
									                            </div><!-- /.panel -->
									                            <!--/ End bar chart -->
	                                                    </div>
	                                                </div>
                                            	</c:if>
                                            	
                                            	   
	                                            <c:if test="${empty relatorioContabilidadeEstatisticaImoveis}">
				                            		<div class="form-group">
                                                    	<div class="table-responsive" style="margin-top: -1px;">
                                                    		<spring:message code="msg.sem.registro.relatorio.grafico"/>
                                                    	</div>
		                                             </div>       	
				                            	</c:if>                                                                                                                                             
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
        </section><!-- /#wrapper -->
        
				<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../../../ajuda/contentMenuModal.jsp"></c:import>																				
				<!-- End content  modal Ajuda - funcionalidade -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->
