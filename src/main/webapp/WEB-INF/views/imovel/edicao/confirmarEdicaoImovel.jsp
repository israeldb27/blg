<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>

    	<script type="text/javascript">
    	$(document).ready(function() {
    		
    		$('#idEstado').change(function () {    			
    	        var comboPai = '#idEstado';
    	        var comboFilha = '#idCidade';
    	        var comboFilha2 = '#idBairro';
    	        limpaComboLinha(comboFilha);
    	        limpaComboLinha(comboFilha2);
    	        recuperaCidades();
    	    });
    		
    		$('#idCidade').change(function () {
    			var comboPai   = '#idCidade';
    			var comboFilha = '#idBairro';
    			limpaComboLinha(comboFilha);
    			recuperaBairros();		
    		 });    		

    		$('#opcaoOrdenacao').change(function () {				
    			$("#meusImoveisForm").submit();      
    		 });    		

    		function limpaComboLinha(comboLinha) {
    		    $(comboLinha).empty();  
    		    $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');
    		    $(comboLinha).trigger("chosen:updated");
    		}
    	});	

    	
    	function recuperaCidades(){
    	    var parametro1 = $("#idEstado").val();
    	    $.ajax({
                type: 'GET',
                url: '${urlBuscarCidades}/' + parametro1,
                dataType: 'json',
                success: function(json){
                    var options = "";
                    $.each(json, function(key, value){
                       $("#idCidade").append("<option value='"+value.key+"'>"+value.label+"</option>");
                    }); 
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });    	   
    	}

    	function recuperaBairros(){   
    	    var parametro1 = $("#idCidade").val();
    	    $.ajax({
                type: 'GET',
                url: '${urlBuscarBairros}/' + parametro1,
                dataType: 'json',
                success: function(json){
                    var options = "";
                    $.each(json, function(key, value){
                    	$("#idBairro").append("<option value='"+value.key+"'>"+value.label+"</option>");
                    });  
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });
    	    
    	}
		</script>
		
        <c:import url="../../layout/head-layout.jsp"></c:import>

    <!--/ END HEAD -->
   
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
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.editar.imovel"/><span><spring:message code="lbl.confirma.dados.cadastro.imovel"/></span> </h2>                                                                        
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                   <form:form id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/confirmarEdicaoImovel" class="form-horizontal mt-10">
                     <div class="row"> 	
                    	<!--/ INICIO ABA LOCALIZACAO -->
                    	<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.imovel.localizacao"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="idEstado" class="col-sm-3 control-label"><spring:message code="lbl.estado"/></label>
                                                <div class="col-sm-7">
                                                     <input value="${imovelForm.estado}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="idCidade" class="col-sm-3 control-label"><spring:message code="lbl.cidade"/></label>
                                                <div class="col-sm-7">
                                                   <input value="${imovelForm.cidade}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                                                                      
                                            <div class="form-group">
                                                <label for="idBairro" class="col-sm-3 control-label"><spring:message code="lbl.bairro"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <input value="${imovelForm.bairro}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="endereco" class="col-sm-3 control-label"><spring:message code="lbl.endereco"/></label>
                                                <div class="col-sm-7">
                                                    <input value="${imovelForm.endereco}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->                                          
                                            
                                        </div><!-- /.form-body -->   

                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
						<!--/ FIM ABA LOCALIZACAO -->
						
						<!--/ INICIO ABA INFORMACOES BASICAS -->
						<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.imovel.info.basica"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                        <div class="form-body">
                                        
                                             <div class="form-group">
                                                <label for="titulo" class="col-sm-3 control-label"><spring:message code="lbl.titulo.imovel"/></label>
                                                <div class="col-sm-7">
                                                    <input value="${imovelForm.titulo}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->   
                                            
                                            <div class="form-group">
                                                <label for="tipoImovel" class="col-sm-3 control-label"><spring:message code="lbl.tipo.imovel"/></label>
                                                <div class="col-sm-7">                                                     
														<c:if test="${imovelForm.tipoImovel == 'quarto'}">							                     												
							                     			<input value='<spring:message code="lbl.tipo.imovel.quarto"/>' class="form-control" readonly="readonly" type="text">			
							                     		</c:if>
						                				<c:if test="${imovelForm.tipoImovel == 'apto'}">
						                					<input value='<spring:message code="lbl.tipo.imovel.apto"/>' class="form-control" readonly="readonly" type="text">							                     											
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'casaPadrao'}">							                     															
							                     			<input value='<spring:message code="lbl.tipo.imovel.casa.padrao"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'cobertura'}">
															<input value='<spring:message code="lbl.tipo.imovel.cobertura"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'pousada'}">							                     										
							                     			<input value='<spring:message code="lbl.tipo.imovel.pousada"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'terreno'}">				                     			
							                     			<input value='<spring:message code="lbl.tipo.imovel.terreno"/>' class="form-control" readonly="readonly" type="text">															
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'fazenda'}">															
															<input value='<spring:message code="lbl.tipo.imovel.fazenda"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'haras'}">															
															<input value='<spring:message code="lbl.tipo.imovel.haras"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'sitio'}">
							                     			<input value='<spring:message code="lbl.tipo.imovel.sitio"/>' class="form-control" readonly="readonly" type="text">															
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'loft'}">							                     			
							                     			<input value='<spring:message code="lbl.tipo.imovel.loft"/>' class="form-control" readonly="readonly" type="text">												
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'kitinete'}">
							                     			<input value='<spring:message code="lbl.tipo.imovel.kitinete"/>' class="form-control" readonly="readonly" type="text">							                     			
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'flat'}">							                     			
							                     			<input value='<spring:message code="lbl.tipo.imovel.flat"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'casaVila'}">															
															<input value='<spring:message code="lbl.tipo.imovel.casa.vila"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'loteamento'}">							                     			
							                     			<input value='<spring:message code="lbl.tipo.imovel.loteamento"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'hotel'}">
							                     			<input value='<spring:message code="lbl.tipo.imovel.hotel"/>' class="form-control" readonly="readonly" type="text">							                     			
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'casaCondominio'}">
															<input value='<spring:message code="lbl.tipo.imovel.condominio"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'galpao'}">															
															<input value='<spring:message code="lbl.tipo.imovel.galpao"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'predio'}">
							                     			<input value='<spring:message code="lbl.tipo.imovel.predio"/>' class="form-control" readonly="readonly" type="text">															
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'chacara'}">															
															<input value='<spring:message code="lbl.tipo.imovel.chacara"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'studio'}">				                     			
							                     			<input value='<spring:message code="lbl.tipo.imovel.studio"/>' class="form-control" readonly="readonly" type="text">															
							                     		</c:if>
							                     		<c:if test="${imovelForm.tipoImovel == 'comercial'}">
							                     			<input value='<spring:message code="lbl.tipo.imovel.comercial"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="acao" class="col-sm-3 control-label"><spring:message code="lbl.acao.imovel"/></label>
                                                <div class="col-sm-7">
                                                		${imovelForm.acaoFmt}		                     		
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="valorImovel" class="col-sm-3 control-label"><spring:message code="lbl.valor.imovel"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <input value='<fmt:formatNumber value="${imovelForm.valorImovel}" pattern="#,##0.00;-0"/>' class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group --> 
                                            
                                            <div class="form-group">
                                                <label for="descricao" class="col-sm-3 control-label"><spring:message code="lbl.descricao.imovel"/></label>
                                                <div class="col-sm-7">
                                                    	<input value="${imovelForm.descricao}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->  
                                                       
                                        </div><!-- /.form-body -->
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        <!--/ FIM ABA INFORMAÇÕES BASICAS -->
                        
                        <!--/ INICIO ABA CARACTERISTISCAS IMOVEL -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.imovel.caracteristicas"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>
                                        <button class="btn btn-sm" data-action="remove" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Remove"><i class="fa fa-times"></i></button>
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="perfilImovel" class="col-sm-3 control-label"><spring:message code="lbl.status.imovel"/></label>
                                                <div class="col-sm-7">
                                                		<c:if test="${imovelForm.perfilImovel == 'lancamento'}">
															<input value='<spring:message code="lbl.status.imovel.lancamento"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>
							                     		<c:if test="${imovelForm.perfilImovel == 'novo'}">				                     			
							                     			<input value='<spring:message code="lbl.status.imovel.novo"/>' class="form-control" readonly="readonly" type="text">															
							                     		</c:if>
							                     		<c:if test="${imovelForm.perfilImovel == 'usado'}">
							                     			<input value='<spring:message code="lbl.status.imovel.usado"/>' class="form-control" readonly="readonly" type="text">
							                     		</c:if>									                     		
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="area" class="col-sm-3 control-label"><spring:message code="lbl.area.m2"/></label>
                                                <div class="col-sm-7">
                                                    <input value="${imovelForm.area}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="quantQuartos" class="col-sm-3 control-label"><spring:message code="lbl.quartos.dormitorios"/></label>
                                                <div class="col-sm-7">
                                                	<input value="${imovelForm.quantQuartos}" class="form-control" readonly="readonly" type="text">                                                    
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="quantGaragem" class="col-sm-3 control-label"><spring:message code="lbl.vagas.garagem"/></label>
                                                <div class="col-sm-7">
                                                	 <input value="${imovelForm.quantGaragem}" class="form-control" readonly="readonly" type="text">                                                     
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="quantSuites" class="col-sm-3 control-label"><spring:message code="lbl.suites"/></label>
                                                <div class="col-sm-7">
                                                	<input value="${imovelForm.quantSuites}" class="form-control" readonly="readonly" type="text">                                                    
                                                </div>
                                            </div><!-- /.form-group -->
                                                      
                                        </div><!-- /.form-body -->    
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        <!--/ FIM ABA CARACTERISTISCAS IMOVEL -->
                        
                        <!--/ INICIO ABA PERMISSOES -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.imovel.permissoes"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>
                                        <button class="btn btn-sm" data-action="remove" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Remove"><i class="fa fa-times"></i></button>
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="destaque" class="col-sm-3 control-label"><spring:message code="lbl.permissao.imovel.destaque"/></label>
                                                <div class="col-sm-7">
                                                     <c:if test="${imovelForm.destaque == 'S'}">                                                     	
                                                     	<input value='<spring:message code="lbl.sim"/>' class="form-control" readonly="readonly" type="text"> 
                                                     </c:if>  
						        					 <c:if test="${imovelForm.destaque == 'N'}">						        					 	
						        					 	<input value='<spring:message code="lbl.nao"/>' class="form-control" readonly="readonly" type="text">
						        					</c:if>
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="habilitaInfoDonoImovel" class="col-sm-3 control-label"><spring:message code="lbl.permissao.visualizar.info.dono.imovel"/></label>
                                                <div class="col-sm-7">
						        					 <c:if test="${imovelForm.habilitaInfoDonoImovel == 'S'}">                                                     	
                                                     	<input value='<spring:message code="lbl.sim"/>' class="form-control" readonly="readonly" type="text"> 
                                                     </c:if>  
						        					 <c:if test="${imovelForm.habilitaInfoDonoImovel == 'N'}">						        					 	
						        					 	<input value='<spring:message code="lbl.nao"/>' class="form-control" readonly="readonly" type="text">
						        					</c:if>
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="autorizacaoPropostas" class="col-sm-3 control-label"><spring:message code="lbl.permissao.autoriza.propostas"/></label>
                                                <div class="col-sm-7">
						        					 <c:if test="${imovelForm.autorizacaoPropostas == 'S'}">                                                     	
                                                     	<input value='<spring:message code="lbl.sim"/>' class="form-control" readonly="readonly" type="text"> 
                                                     </c:if>  
						        					 <c:if test="${imovelForm.autorizacaoPropostas == 'N'}">						        					 	
						        					 	<input value='<spring:message code="lbl.nao"/>' class="form-control" readonly="readonly" type="text">
						        					</c:if>
                                                </div>
                                            </div><!-- /.form-group -->
                                                                                        
                                            
                                            <div class="form-group">
                                                <label for="autorizaComentario" class="col-sm-3 control-label"><spring:message code="lbl.permissao.autoriza.comentarios"/></label>
                                                <div class="col-sm-7">
						        					 <c:if test="${imovelForm.autorizaComentario == 'S'}">                                                     	
                                                     	<input value='<spring:message code="lbl.sim"/>' class="form-control" readonly="readonly" type="text"> 
                                                     </c:if>  
						        					 <c:if test="${imovelForm.autorizaComentario == 'N'}">						        					 	
						        					 	<input value='<spring:message code="lbl.nao"/>' class="form-control" readonly="readonly" type="text">
						        					</c:if>
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                             <div class="form-group">
                                                <label for="aceitaFinanciamento" class="col-sm-3 control-label"><spring:message code="lbl.permissao.aceita.financiamento"/></label>
                                                <div class="col-sm-7">                                                     
						        					 <c:if test="${imovelForm.aceitaFinanciamento == 'S'}">                                                     	
                                                     	<input value='<spring:message code="lbl.sim"/>' class="form-control" readonly="readonly" type="text"> 
                                                     </c:if>  
						        					 <c:if test="${imovelForm.aceitaFinanciamento == 'N'}">						        					 	
						        					 	<input value='<spring:message code="lbl.nao"/>' class="form-control" readonly="readonly" type="text">
						        					</c:if>
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="habilitaBusca" class="col-sm-3 control-label"><spring:message code="lbl.permissao.autoriza.busca.imoveis"/></label>
                                                <div class="col-sm-7">						        					 
						        					 <c:if test="${imovelForm.habilitaBusca == 'S'}">                                                     	
                                                     	<input value='<spring:message code="lbl.sim"/>' class="form-control" readonly="readonly" type="text"> 
                                                     </c:if>  
						        					 <c:if test="${imovelForm.habilitaBusca == 'N'}">						        					 	
						        					 	<input value='<spring:message code="lbl.nao"/>' class="form-control" readonly="readonly" type="text">
						        					</c:if>
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="acessoVisualizacao" class="col-sm-3 control-label"><spring:message code="lbl.permissao.autoriza.quem.visualiza"/></label>
                                                <div class="col-sm-7">
						        			   		 <c:if test="${imovelForm.acessoVisualizacao == 'todos'}">                                                     	
                                                     	<input value='<spring:message code="lbl.permissao.visualiza.todos"/>' class="form-control" readonly="readonly" type="text"> 
                                                     </c:if>  
						        					 <c:if test="${imovelForm.acessoVisualizacao == 'N'}">						        					 	
						        					 	<input value='<spring:message code="lbl.permissao.visualiza.ninguem"/>' class="form-control" readonly="readonly" type="text">
						        					</c:if>
						        					<c:if test="${imovelForm.acessoVisualizacao == 'C'}">						        					 	
						        					 	<input value='<spring:message code="lbl.permissao.visualiza.apenas.contatos"/>' class="form-control" readonly="readonly" type="text">
						        					</c:if>
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                        </div><!-- /.form-body -->     
                                         
                                        <div class="form-footer">  
                                            <div class="col-sm-offset-3">
                                                <button type="submit" class="btn btn-success"><spring:message code="lbl.btn.confirmar.edicao.geral"/></button>
                                            </div>
                                        </div><!-- /.form-footer -->                                

                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        
                        <!--/ FIM ABA PERMISSOES -->
                        </div><!-- /.row -->
                       </form:form> 
                            
                </div><!-- /.body-content -->
                <!--/ End body content -->
                
                <!-- Fim Editar Usuario -->          
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->     

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

        <!-- START GOOGLE ANALYTICS -->
        <script>
            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-55892530-1', 'auto');
            ga('send', 'pageview');

        </script>
        <!--/ END GOOGLE ANALYTICS -->

    </body>
    <!--/ END BODY -->

</html>