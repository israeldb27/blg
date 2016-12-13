<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/preferencia/adicionarPreferenciaInicioCadastroUsuario" var="urlPrefAdicionar"/>
<spring:url value="/preferencia/excluirPreferencia" var="urlPrefExcluir"/>
<spring:url value="/preferencia/direcionarMain" var="urlPrefDirecionarMain"/>

<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>

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
	
    function limpaComboLinha(comboLinha) {
        $(comboLinha).empty();
        $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');        
    }
});	

function recuperaCidades(){
    var parametro1 = $("#idEstado").val();
    $.get("${urlBuscarCidades}/"+parametro1, function(data){
        jQuery.each(data, function(key, value) {
            if(value.label == null){            	
                return;
            }            
            $("#idCidade").append("<option value='"+value.key+"'>"+value.label+"</option>");
        });
    });
}


function recuperaBairros(){
    var parametro1 = $("#idCidade").val();
    $.get("${urlBuscarBairros}/"+parametro1, function(data){
        jQuery.each(data, function(key, value) {
            if(value.label == null){            	
                return;
            }            
            $("#idBairro").append("<option value='"+value.key+"'>"+value.label+"</option>");
        });
    });
}

function enviarConvite(id) {
    $.post("${urlContato}/enviarConvite", {'idUsuario' : id}, function() {
      // selecionando o elemento html através da 
      // ID e alterando o HTML dele
    	$("#idConvite_"+id).show();    	
    });
  }
  
function direcionarMain() {
    $.post("${urlUsuario}/submitLogin", {}, function() {
         	
    });
  }  

</script>
        <c:import url="../../layout/cadastroUsuario/head-layout.jsp"></c:import>
    <body>

        <!-- INICIO - Cadastro Usuario -->
            
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                		<form:form class="form-horizontal mt-10" method="POST" id="preferenciaLocalidadeForm" modelAttribute="preferenciaLocalidadeForm" action="${urlPrefDirecionarMain}" >
                   			<button type="submit" class="btn btn-primary btn-lg btn-slideright">Continuar</button>
                   		</form:form>
                   		
                   		<c:if test="${msgErro != null }">
		               		 <div class="panel panel-danger">
		                          <div class="panel-heading">
		                              <h3 class="panel-title"><spring:message code="msg.erro.lista.pref.imoveis.vazia"/></h3>
		                          </div><!-- /.panel-heading -->		                          
		                      </div><!-- /.panel -->                      
		                </c:if>
		                	
	                    <div class="row"> 	
	                   		<div class="col-md-12">                        		

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
                                    <form:form class="form-horizontal mt-10" method="POST" id="preferenciaLocalidadeForm" modelAttribute="preferenciaLocalidadeForm" action="${urlPrefAdicionar}" >
                                      <div class="form-body">
                                        <div class="form-group">
                                        	<label for="idEstado" class="col-sm-4 control-label"><spring:message code="lbl.estado"/> :</label>
                                        	<div class="col-sm-7">                                        	
	                                            <spring:message code="lbl.hint.imovel.estado" var="hintEstado"/>
	                                            <form:select id="idEstado" path="idEstado" class="form-control" title="${hintEstado}">                                
													<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${preferenciaLocalidadeForm.listaEstados}" itemValue="key" itemLabel="label"/>
											  </form:select>
	                                        </div>
	                                        <label for="idCidade" class="col-sm-4 control-label"><spring:message code="lbl.cidade"/>:</label>
	                                        <div class="col-sm-7">
	                                            <spring:message code="lbl.hint.imovel.cidade" var="hintCidade"/>
	                                            <form:select id="idCidade" path="idCidade" class="form-control" title="${hintCidade}">                                
													<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${preferenciaLocalidadeForm.listaCidades}" itemValue="key" itemLabel="label"/>
											  </form:select>
                                            </div>
                                             
                                            <label for="idBairro" class="col-sm-4 control-label"><spring:message code="lbl.bairro"/>:</label>
	                                        <div class="col-sm-7">
	                                            <spring:message code="lbl.hint.imovel.bairro" var="hintBairro"/>
	                                            <form:select id="idBairro" path="idBairro" class="form-control" title="${hintBairro}">                                
													<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${preferenciaLocalidadeForm.listaBairros}" itemValue="key" itemLabel="label"/>
											  </form:select>
                                            </div>
                                            
                                            <label for="acao" class="col-sm-4 control-label"><spring:message code="lbl.acao.imovel"/>:</label>
	                                        <div class="col-sm-7">
	                                            <spring:message code="lbl.hint.imovel.acao.imovel" var="hintAcaoImovel"/>
	                                            <form:select id="acao" path="acao" class="form-control" title="${hintAcaoImovel}">                                
								                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />
								                </form:select>
                                            </div>
                                            
                                            <label for="tipoImovel" class="col-sm-4 control-label"><spring:message code="lbl.tipo.imovel"/>:</label>
	                                        <div class="col-sm-7">
	                                            <spring:message code="lbl.hint.imovel.tipo.imovel" var="hintTipoImovel"/>
	                                            <form:select id="tipoImovel" path="tipoImovel" class="form-control" title="${hintTipoImovel}">                                
								                        <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
														<form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />
								                 </form:select> 
                                            </div>            

											<label for="quantQuartos" class="col-sm-3 control-label"><spring:message code="lbl.quartos.dormitorios"/></label>
											<div class="col-sm-7">
												<spring:message code="lbl.hint.pref.imovel.quant.quartos" var="hintQuartos"/>
												<form:input id="quantQuartos" path="quantQuartos" class="form-control" title="${hintQuartos}"/>
											</div>
                                            
                                            <label for="quantGaragem" class="col-sm-3 control-label"><spring:message code="lbl.vagas.garagem"/></label>
											<div class="col-sm-7">
												<spring:message code="lbl.hint.pref.imovel.quant.garagem" var="hintGaragem"/>
												<form:input id="quantGaragem" path="quantGaragem" class="form-control" title="${hintGaragem}"/>
											</div>
											
											  												
                                            <label for="quantBanheiro" class="col-sm-3 control-label"><spring:message code="lbl.banheiros"/></label>
                                            <div class="col-sm-7">
                                              	  <spring:message code="lbl.hint.pref.imovel.quant.banheiros" var="hintBanheiros"/>	
                                                  <form:input id="quantBanheiro" path="quantBanheiro" class="form-control" title="${hintBanheiros}"/>
                                            </div>
                                            
                                            <label for="quantSuites" class="col-sm-3 control-label"><spring:message code="lbl.suites"/></label>
											<div class="col-sm-7">
												<spring:message code="lbl.hint.pref.imovel.quant.suites" var="hintSuites"/>
												<form:input id="quantSuites" path="quantSuites" class="form-control" title="${hintSuites}"/>
											</div>  		
                                            
                                            <spring:message code="lbl.hint.adicionar.geral" var="hintAdicionar"/>	
                                            <button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" title="${hintAdicionar}" style="width: 20%;"><spring:message code="lbl.btn.adicionar.geral"/></button>
                                            
                                        </div><!-- /.form-group -->
                                       </div> 
                                    </form:form>  
                                    
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->
                        </div>          
					                    
                        <div class="col-md-12">

                            <!-- Start basic color table -->
                            <div class="panel">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.lista.pref.imoveis"/></h3>
                                    </div>
                                    <div class="pull-right">
                                        <div class="dropdown">
                                            <button class="btn btn-sm" data-toggle="dropdown">
                                                <i class="fa fa-navicon"></i>
                                            </button>
                                            <ul id="trigger-change-color" class="dropdown-menu pull-right">                                                
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                    <div class="table-responsive" style="margin-top: -1px;">
                                        <table class="table table-striped table-primary">
                                            <thead>
                                            <tr>
                                                <th class="text-center"><spring:message code="lbl.tipo.imovel"/></th>
                                                <th class="text-center"><spring:message code="lbl.acao.imovel"/></th>
                                                <th class="text-center"><spring:message code="lbl.estado"/></th>
                                                <th class="text-center"><spring:message code="lbl.cidade"/></th>
                                                <th class="text-center"><spring:message code="lbl.bairro"/></th>                                                
												<th class="text-center"><spring:message code="lbl.quartos.dormitorios"/></th>  
												<th class="text-center"><spring:message code="lbl.vagas.garagem"/></th>  
												<th class="text-center"><spring:message code="lbl.suites"/></th>  
                                                <th class="text-center" style="width: 12%;"></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="pref" items="${listaPreferenciaImoveis}" >
	                                            <tr>
	                                                <td class="text-center"> ${pref.tipoImovelFmt} </td>
	                                                <td class="text-center">${pref.acaoFmt}</td>
	                                                <td class="text-center">${pref.nomeEstado}</td>
	                                                <td class="text-center">${pref.nomeCidade}</td>
	                                                <td class="text-center">${pref.nomeBairro}</td>
													<td class="text-center">${pref.quantQuartos}</td>
													<td class="text-center">${pref.quantGaragem}</td>
													<td class="text-center">${pref.quantSuites}</td>
	                                                <td class="text-center">                                                    
	                                                    <a href="${urlPrefExcluir}/${pref.id}" class="btn btn-danger btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="Excluir"><i class="fa fa-times"></i></a>
	                                                </td>
	                                            </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div><!-- /.table-responsive -->
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End basic color table -->

                        </div><!-- /.col-md-12 -->  
	                   	
	                  </div><!-- /.row -->                      
                                               
                </div><!-- /.body-content -->
                <!--/ End body content -->    

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>