<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/preferencia/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/preferencia/buscarBairros" var="urlBuscarBairros"/>
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
            $('#idCidade').trigger("chosen:updated");
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
          $('#idBairro').trigger("chosen:updated");
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });
}

function direcionarMain() {
    $.post("${urlUsuario}/submitLogin", {}, function() {
         	
    });
  } 

function carregaAvisoInicial(){		
		<% if (  request.getSession().getAttribute("firstTime").toString().equals("S") ) {%>			
			$('#msgModal').html("<spring:message code='lbl.header.modal.aviso.inicio.desc.pref.imoveis'/>");
			$('#msgModalFuncionalidade').html("<spring:message code='lbl.header.modal.aviso.inicio.pref.imoveis'/>");
			$("#idModalAvisoInicial").modal("show");		
		<% }  %>		
}

</script>
        <c:import url="../../layout/cadastroUsuario/head-layout.jsp"></c:import>
   
    <body onload="carregaAvisoInicial();">

                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                		<form:form class="form-horizontal mt-10" method="POST" id="preferenciaLocalidadeForm" modelAttribute="preferenciaLocalidadeForm" action="${urlPrefDirecionarMain}" >
                   			<button type="submit" class="btn btn-primary btn-lg btn-slideright"><spring:message code="lbl.header.btn.continuar.inicio.pref.imoveis"/></button>
                   		</form:form>
                   		
                   		 <c:if test="${msgSucesso != null }">
                     	 		 <div class="alert alert-success">
                                      <strong><spring:message code="msg.atualizado.imovel.sucesso"/></strong> 
                                 </div>	                     	 
			               </c:if>   
			               <c:if test="${msgErro != null }">			               		 
			                       <div class="alert alert-danger">
                                          <strong><spring:message code="msg.erro.lista.pref.imoveis.vazia"/></strong> 
                                   </div>                 
			               </c:if>
			               
			               <c:if test="${msgErroPrefExistente != null }">			               		 
			                       <div class="alert alert-danger">
                                          <strong>${msgErroPrefExistente}</strong> 
                                   </div>                 
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
                                    <input type="hidden" id="firstTime" value=""/> 
                                    
                                      <div class="form-body" style="vertical-align: middle;">
	                                      <div class="form-group no-margin">								       				   		
						       				 <div class="row" style="margin-left: 70px; margin-right: 0px;">
						       				 	 <div class="col-md-3">
                                                   	<span class="label label-default"><spring:message code="lbl.estado"/> </span>
                                                   	<form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" >                                
														<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
														<form:options items="${preferenciaLocalidadeForm.listaEstados}" itemValue="key" itemLabel="label"/>
												    </form:select>
												    <form:errors id="idEstado" path="idEstado" cssClass="errorEntrada"  />
                                                  </div>
                                                   
                                                  <div class="col-md-3">
                                                   	<span class="label label-default"><spring:message code="lbl.tipo.imovel"/> </span>
                                                   	<form:select id="tipoImovel" path="tipoImovel" class="chosen-select" tabindex="-1" style="display: none;" >                                
									                        <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
															<form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />
									                 </form:select>
									                 <form:errors id="tipoImovel" path="tipoImovel" cssClass="errorEntrada"  />
                                                  </div>
                                                  
                                                  <div class="col-md-3">
                                                   		<span class="label label-default"><spring:message code="lbl.quartos.dormitorios"/> </span>
                                                   		<spring:message code="lbl.hint.pref.imovel.quant.quartos" var="hintQuartos"/>
														<form:input id="quantQuartos" path="quantQuartos" class="form-control" title="${hintQuartos}"/>
									                 	<form:errors id="quantQuartos" path="quantQuartos" cssClass="errorEntrada"  />
                                                  </div>	
                                                  
                                                  <div class="col-md-3">
                                                   		<span class="label label-default"><spring:message code="lbl.suites"/> </span>
                                                   		<spring:message code="lbl.hint.pref.imovel.quant.suites" var="hintSuites"/>
														<form:input id="quantSuites" path="quantSuites" class="form-control" title="${hintSuites}"/>
									                 	<form:errors id="quantSuites" path="quantSuites" cssClass="errorEntrada"  />
                                                  </div>	
						       				 	
						       				 </div>
						       				 
						       				 <br> 
						       				 
						       				 <div class="row" style="margin-left: 70px; margin-right: 0px;">
						       				 		<div class="col-md-3">
                                                    	<span class="label label-default"><spring:message code="lbl.cidade"/> </span>
                                                    	<form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;">                                
															<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
															<form:options items="${preferenciaLocalidadeForm.listaCidades}" itemValue="key" itemLabel="label"/>
													    </form:select>
													    <form:errors id="idCidade" path="idCidade" cssClass="errorEntrada"  />
                                                    </div>
                                                    
                                                    <div class="col-md-3">
                                                    	<span class="label label-default"><spring:message code="lbl.acao.imovel"/> </span>
                                                    	<form:select id="acao" path="acao" class="chosen-select" tabindex="-1" style="display: none;">                                
										                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
															<form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />
										                </form:select> 			
										                <form:errors id="acao" path="acao" cssClass="errorEntrada"  />
                                                    </div>
                                                    
                                                     <div class="col-md-3">
	                                                   		<span class="label label-default"><spring:message code="lbl.vagas.garagem"/> </span>
	                                                   		<spring:message code="lbl.hint.pref.imovel.quant.garagem" var="hintGaragem"/>
															<form:input id="quantGaragem" path="quantGaragem" class="form-control" title="${hintGaragem}"/>
										                 	<form:errors id="quantGaragem" path="quantGaragem" cssClass="errorEntrada"  />
	                                                  </div>
						       				 </div>
						       				 
						       				 <br> 
						       				 
						       				 <div class="row" style="margin-left: 70px; margin-right: 0px;">
						       				 		<div class="col-md-3">
                                                    	<span class="label label-default"><spring:message code="lbl.bairro"/> </span>
                                                    	<form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;">                                
																<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${preferenciaLocalidadeForm.listaBairros}" itemValue="key" itemLabel="label"/>
														 </form:select>
														 <form:errors id="idBairro" path="idBairro" cssClass="errorEntrada"  />
                                                    </div>
                                                    
                                                    <div class="col-md-3">
                                                    	<span class="label label-default"><spring:message code="lbl.buscar.imovel.status.imovel"/> </span>
											            <spring:message code="lbl.hint.imovel.perfil.imovel" var="hintPerfilImovel"/>
												              <form:select id="perfilImovel" path="perfilImovel" class="form-control" title="${hintPerfilImovel}">                                
											                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
											                    	<form:options items="${listaStatusImovel}" itemValue="identificador" itemLabel="rotulo" />								                    	   
											                  </form:select>
											                  <form:errors id="perfilImovel" path="perfilImovel" cssClass="errorEntrada"  />
                                                     </div>
                                                     
                                                     <div class="col-md-3">
	                                                   		<span class="label label-default"><spring:message code="lbl.banheiros"/> </span>
	                                                   		<spring:message code="lbl.hint.pref.imovel.quant.banheiros" var="hintBanheiros"/>
															<form:input id="quantBanheiro" path="quantBanheiro" class="form-control" title="${hintBanheiros}"/>
										                 	<form:errors id="quantBanheiro" path="quantBanheiro" cssClass="errorEntrada"  />
	                                                  </div>
						       				 </div>
						       				 
						       				 <br> 						    
						       				 
						       			  </div>
						       		  </div>	
						       		  
						       		  <div class="form-group">             			
                                			 <label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
                                            <div class="col-sm-7">
                                            	<br>                                            	
                                            	<spring:message code="lbl.hint.adicionar.geral" var="hintAdicionar"/>	
                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" title="${hintAdicionar}" style="width: 20%;"><spring:message code="lbl.btn.adicionar.geral"/></button>
                                            </div>
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
                                        <table class="table table-striped">
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
	                                                <td class="text-center" style="font-size: 13px;">${pref.tipoImovelFmt} </td>
	                                                <td class="text-center" style="font-size: 13px;">${pref.acaoFmt}</td>
	                                                <td class="text-center" style="font-size: 13px;">${pref.nomeEstado}</td>
	                                                <td class="text-center" style="font-size: 13px;">${pref.nomeCidade}</td>
	                                                <td class="text-center" style="font-size: 13px;">${pref.nomeBairro}</td>
													<td class="text-center" style="font-size: 13px;">${pref.quantQuartos}</td>
													<td class="text-center" style="font-size: 13px;">${pref.quantGaragem}</td>
													<td class="text-center" style="font-size: 13px;">${pref.quantSuites}</td>
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
                
                 <div id="idModalAvisoInicial" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				          <h4 class="modal-title"><div id="msgModalFuncionalidade" > </div> </h4>
				        </div>
				        <div class="modal-body">  
				       	    <div id="msgModal" > </div>
				        </div>
				        <div class="modal-footer">			          
	                      <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>
				        </div>
				      </div>
				    </div>
				</div>
            </div><!-- /.modal -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>