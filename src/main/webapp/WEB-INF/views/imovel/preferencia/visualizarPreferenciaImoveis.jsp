<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>

<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>
<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/preferencia/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/preferencia/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/preferencia/adicionarPreferencia" var="urlPrefAdicionar"/>
<spring:url value="/preferencia/excluirPreferencia" var="urlPrefExcluir"/>
<spring:url value="/preferencia" var="urlPref"/>

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

function enviarConvite(id) {
    $.post("${urlContato}/enviarConvite", {'idUsuario' : id}, function() {
     	$("#idConvite_"+id).show();    	
    });
  }
  
  
function prepararModalConfirmaExclusao(id){
	$("#modIdPref").val(id);
	$('#msgRetornoConfirmExclusaoPref').html("");	
	$("#idModalConfirmacaoExclusaoPref").modal("show");	
}

function confirmarExclusaoPrefImoveis(){	
	var parametro = document.getElementById("modIdPref");	
	$.ajax({
			 url: '${urlPref}/confirmarExclusaoPreferncia/' + parametro.value,			 
			 success: function(){				 
				 location.reload();     	    
			 },
			 error: function(jqXHR, textStatus, errorThrown) {				 
				 $('#msgRetornoConfirmExclusaoPref').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
			 }
		 });
}

</script>
		
        <c:import url="../../layout/head-layout.jsp"></c:import>

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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.pref.imoveis"/> </h2>					
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                
                    <div class="row">		
                        <div class="col-md-12">                              
                         <div class="panel rounded shadow">                         			            
                                <div class="panel-heading">
                                        <div class="pull-left">
                                           <h1 class="panel-title" style="font-size: 14px;"> 
                                        		<strong ><spring:message code="lbl.filtro.geral"/> </strong>
                                           </h1>
                                       </div><!-- /.pull-left -->   
                                       <div class="pull-right">
                                 			<a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>	                                       		
                                 		</div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">                                	
                                    <form:form class="form-horizontal mt-10" method="POST" id="preferenciaLocalidadeForm" modelAttribute="preferenciaLocalidadeForm" action="${urlPrefAdicionar}" >                                                                          
                                      <div class="form-body">                                      	   
                                      	   <div class="form-group no-margin">								       				   		
						       				   		<div class="row">
						       				   			<div class="col-md-3">	                                                      
				                                			 <span class="label label-default"><spring:message code="lbl.estado"/> </span>
				                                			 <spring:message code="lbl.hint.imovel.estado" var="hintEstado"/>
					                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" title="${hintEstado}">                                
																	<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																	<form:options items="${preferenciaLocalidadeForm.listaEstados}" itemValue="key" itemLabel="label"/>
															  </form:select>
															  <form:errors id="idEstado" path="idEstado" cssClass="errorEntrada"  />
	                                                     </div>	                                                     
	                                                     
	                                                     <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.tipo.imovel"/> </span>
															<spring:message code="lbl.hint.imovel.tipo.imovel" var="hintTipoImovel"/>
				                                            <form:select id="tipoImovel" path="tipoImovel" class="chosen-select" tabindex="-1" style="display: none;" title="${hintTipoImovel}">                                
											                        <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
																	<form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />
											                 </form:select> 
															 <form:errors id="tipoImovel" path="tipoImovel" cssClass="errorEntrada"  />	
														 </div>
														 
														 <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.quartos.dormitorios"/> </span>
															<spring:message code="lbl.hint.pref.imovel.quant.quartos" var="hintQuartos"/>
															<form:input id="quantQuartos" path="quantQuartos" class="form-control" title="${hintQuartos}"/>														
														 </div>
														 
														  <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.suites"/> </span>
															<spring:message code="lbl.hint.pref.imovel.quant.suites" var="hintSuites"/>
															<form:input id="quantSuites" path="quantSuites" class="form-control" title="${hintSuites}"/>																  
														 </div>														
													</div>
													
													</br>
													
													<div class="row">
														<div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.cidade"/> </span>
															<spring:message code="lbl.hint.imovel.cidade" var="hintCidade"/>
				                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;" title="${hintCidade}">                                
																<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${preferenciaLocalidadeForm.listaCidades}" itemValue="key" itemLabel="label"/>
														    </form:select>																  
														 </div>	
														 
														  <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.acao.imovel"/> </span>
	                                        				<spring:message code="lbl.hint.imovel.acao.imovel" var="hintAcaoImovel"/>
				                                            <form:select id="acao" path="acao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintAcaoImovel}">                                
											                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />
											                </form:select>
															<form:errors id="acao" path="acao" cssClass="errorEntrada"  />
														 </div>
														 
														 <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.vagas.garagem"/> </span>														
															<spring:message code="lbl.hint.pref.imovel.quant.garagem" var="hintGaragem"/>
															<form:input id="quantGaragem" path="quantGaragem" class="form-control" title="${hintGaragem}"/>
														 </div>	
													</div>
													
													</br>
													
													<div class="row">
														 <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.bairro"/> </span>
															<spring:message code="lbl.hint.imovel.bairro" var="hintBairro"/>
				                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintBairro}">                                
																<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
																<form:options items="${preferenciaLocalidadeForm.listaBairros}" itemValue="key" itemLabel="label"/>
														    </form:select>															
														 </div>
														 
														 <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.status.imovel"/> </span>
															<spring:message code="lbl.hint.imovel.perfil.imovel" var="hintPerfilImovel"/>
				                                             <form:select id="perfilImovel" path="perfilImovel" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPerfilImovel}">                                
											                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
											                    	<form:options items="${listaStatusImovel}" itemValue="identificador" itemLabel="rotulo" />								                    	   
											                </form:select> 														
														 </div>	
														 
														 <div class="col-md-3">
															<span class="label label-default"><spring:message code="lbl.banheiros"/> </span>
															<spring:message code="lbl.hint.pref.imovel.quant.banheiros" var="hintBanheiros"/>
															<form:input id="quantBanheiro" path="quantBanheiro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintBanheiros}"/>				                                             
														 </div>														
													</div>
											</div>          
                                        
										</br>
										
                                        <div class="form-group">
                                            <label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
                                            <div class="col-sm-7">
                                            	<br>
                                            	<spring:message code="lbl.hint.adicionar.geral" var="hintAdicionar"/>	
                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" title="${hintAdicionar}" style="width: 20%;"><spring:message code="lbl.btn.adicionar.geral"/></button>
                                            </div>		
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
                                        <h3 class="panel-title"><spring:message code="lbl.lista.pref.imoveis"/> : ${msgError}</h3>
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
                                
                                <c:if test="${msgError != null }">			               		 
			                       <div class="alert alert-danger">
                                          <strong>${msgError}</strong> 
                                   </div>                 
			               	</c:if>
                                
                                <div class="panel-body no-padding">
                                    <div class="table-responsive" style="margin-top: -1px;">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <th class="text-center"><spring:message code="lbl.tipo.imovel"/></th>
                                                <th class="text-center"><spring:message code="lbl.acao.imovel"/></th>
                                                <th class="text-center"><spring:message code="lbl.status.imovel.resum"/></th>                                                
                                                <th class="text-center"><spring:message code="lbl.estado"/></th>
                                                <th class="text-center"><spring:message code="lbl.cidade"/></th>
                                                <th class="text-center"><spring:message code="lbl.bairro"/></th>
												<th class="text-center"><spring:message code="lbl.quartos.dormitorios.resum"/></th>  
												<th class="text-center"><spring:message code="lbl.vagas.garagem.resum"/></th>  
												<th class="text-center"><spring:message code="lbl.banheiros"/></th>
												<th class="text-center"><spring:message code="lbl.suites"/></th>  
                                                <th class="text-center" style="width: 12%;"></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="pref" items="${listaPreferenciaImoveis}" >
	                                            <tr>
	                                                <td class="text-center" style="font-size: 13px;">${pref.tipoImovelFmt}  </td>
	                                                <td class="text-center" style="font-size: 13px;">${pref.acaoFmt}</td>
	                                                <td class="text-center" style="font-size: 13px;">${pref.perfilImovelFmt}</td>
	                                                <td class="text-center" style="font-size: 13px;">${pref.estado}</td>
	                                                <td class="text-center" style="font-size: 13px;">${pref.nomeCidade}</td>
	                                                <td class="text-center" style="font-size: 13px;">${pref.nomeBairro}</td>													
													<td class="text-center" style="font-size: 13px;">${pref.quantQuartos}</td>
													<td class="text-center" style="font-size: 13px;">${pref.quantGaragem}</td>
													<td class="text-center" style="font-size: 13px;">${pref.quantBanheiro}</td>
													<td class="text-center" style="font-size: 13px;">${pref.quantSuites}</td>													
	                                                <td class="text-center">                                                    
	                                                    <a href="#" onClick="prepararModalConfirmaExclusao(${pref.id})" class="btn btn-danger btn-xs" title="Excluir"><i class="fa fa-times"></i></a>
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
         
            </section><!-- /#page-content -->
            
				<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
				<!-- End content  modal Ajuda - funcionalidade -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->
        
           <!-- Start optional size modal element - confirmacao exclusao preferencia -->
            <div id="idModalConfirmacaoExclusaoPref" class="modal fade bs-example-modal-lg-confirmacao-exclusao-preferencia-imovel" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdPref" readonly="readonly" name="modIdPref">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.exclusao.pref.imoveis"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.exclusao.pref.imoveis"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="confirmarExclusaoPrefImoveis();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoConfirmExclusaoPref" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao exclusao preferencia -->   

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>