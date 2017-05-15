<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/imovel/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/imovel/buscarBairros" var="urlBuscarBairros"/>
<spring:url var="urlImovelFavoritos" value="/imovelFavoritos"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>
<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   
<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>
<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/>   

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script src="${context}/js/jquery.maskMoney.js"></script> 

   <script type="text/javascript">
    	$(document).ready(function() { 
    		    		
    		$('.datepicker').datepicker({
    		    format: 'dd/mm/yyyy',                
    		    language: 'pt-BR'
    		});
    	    		
    		(function ($) {
    			  $('.spinner .btn:first-of-type').on('click', function() {
    			    $('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);
    			  });
    			  $('.spinner .btn:last-of-type').on('click', function() {
    			    $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);
    			  });

    			})(jQuery);
    		
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
    			$("#imovelBuscarImoveisForm").submit();      
    		 });  
    		
   		    $('#opcaoPaginacao').change(function () {				
   				$("#imovelBuscarImoveisPageForm").submit();      
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
    	
    	function carregarMapa() {    	
    		$("#imovelForm").submit();   
    	}
    	
    	function adicionarInteresse(id) {    	
    		var parametro1 = id;
    	    $.ajax({                
                url: '${urlImovelFavoritos}/adicionarFavoritos/' + parametro1,                
                success: function(){
                	$("#idMeInteressei_"+id).hide();
                	$("#idNovoMeInteressei_"+id).hide();
	    	    	$("#idInteressado_"+id).show();	    	    	
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });   
    	}
    	
    	function retirarInteresse(id) {
    		var parametro1 = id;    		
    	    $.ajax({                
                url: '${urlImovelFavoritos}/retirarFavoritos/' + parametro1,                
                success: function(){
                	$("#idNovoMeInteressei_"+id).show();                	
	    	    	$("#idInteressado_"+id).hide();
	    	    	$("#idNovoInteressado_"+id).hide();	    	    	
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });   
    	}    	
    	    	        	
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

            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.busca.imoveis"/> </h2>
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                         <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
                            <c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        <div class="col-lg-3 col-md-3 col-sm-4">
                      
                            <form:form method="POST" id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/buscarImovel" >
                            
                                <div class="panel rounded shadow no-overflow">
                         			  <div class="panel-heading">
	                                       <div class="pull-left">
	                                           <h1 class="panel-title panel-titulo" > 
	                                        		<strong ><spring:message code="lbl.filtro.geral"/> </strong>
	                                           </h1>
	                                       </div><!-- /.pull-left -->   
	                                       <div class="pull-right">
	                                       		<a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>	                                       		
	                                       </div>
	                                                                            
	                                       <div class="clearfix"></div>
	                                 </div>  
                                    <div class="panel-body">
                                        <div class="form-group no-margin">
                                        	 </br>
                                        	<span class="label label-default" > <spring:message code="lbl.estado" /> </span>  
                                        	<spring:message code="lbl.hint.imovel.estado" var="hintEstado"/>
                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" title="${hintEstado}">
                                                    <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                    <form:options items="${imovelForm.listaEstados}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"> <spring:message code="lbl.cidade"/> </span>
                                            <spring:message code="lbl.hint.imovel.cidade" var="hintCidade"/>
                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;" title="${hintCidade}">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${imovelForm.listaCidades}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
                                            <spring:message code="lbl.hint.imovel.bairro" var="hintBairro"/>
                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;"  title="${hintBairro}">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${imovelForm.listaBairros}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            <br>		
                                            	<spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>									  
												  <div class="pull-right">
													<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
												  </div><!-- /.pull-right -->            												   
												<br>
                                            
                                        </div><!-- /.form-group -->
                                    </div><!-- /.panel -->
                                </div>
                                
                                <div class="panel rounded shadow no-overflow">                                    
                                    <br>                                    
                                    <div class="panel-body">
                                        <span class="label label-default"><spring:message code="lbl.tipo.imovel"/> </span>                                        
                                        <spring:message code="lbl.hint.imovel.tipo.imovel" var="hintTipoImovel"/>
								              <form:select id="tipoImovel" path="tipoImovel" class="chosen-select" tabindex="-1" style="display: none;" title="${hintTipoImovel}">                                
							                        <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                        <form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />								                        
							                 </form:select>	
									     <br> <br>
									     
								     	<span class="label label-default"><spring:message code="lbl.acao.imovel"/> </span>
								     	<spring:message code="lbl.hint.imovel.acao.imovel" var="hintAcaoImovel"/>
								              <form:select id="acao" path="acao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintAcaoImovel}">                                
							                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                    <form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />								                    
							                </form:select>
								        <br> <br>
								           
							            <span class="label label-default"><spring:message code="lbl.buscar.imovel.status.imovel"/> </span>
							            <spring:message code="lbl.hint.imovel.perfil.imovel" var="hintPerfilImovel"/>
								              <form:select id="perfilImovel" path="perfilImovel" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPerfilImovel}">                                
							                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                    	<form:options items="${listaStatusImovel}" itemValue="identificador" itemLabel="rotulo" />								                    	   
							                </form:select> 
							             <br>
						             	  <div class="pull-right">
												<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
											  </div><!-- /.pull-right -->            												   
											<br>
						
                                    </div><!-- /.panel-body -->
                                </div>
                                
                                <div class="panel rounded shadow no-overflow">
                                	 <div class="panel-body">
	                                	 <br>
	                                	 <span class="label label-default"><spring:message code="lbl.valor.imovel.minimo"/> </span>                                	                                 	  
		                                 <form:input  id="valorMin" path="valorMin" class="form-control" onkeypress="formatarMoeda(this);"   />		                                 
		                                 <form:errors id="valorMin" path="valorMin" cssClass="errorEntrada"  />
	
	                                	  <br> <br>
	                                	  
	                                	  <span class="label label-default"><spring:message code="lbl.valor.imovel.maximo"/> </span>
	                                	  <form:input  id="valorMax" path="valorMax" class="form-control" onkeypress="formatarMoeda(this);" />
	                                      <form:errors id="valorMax" path="valorMax" cssClass="errorEntrada"  />
	                                	  
	                                      <br>
						             	  <div class="pull-right">
												<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
											  </div><!-- /.pull-right -->            												   
											<br>		
                                	 </div>
                                </div>
                                
                                <div class="panel rounded shadow no-overflow">                                    
                                    <br>                                    
                                    <div class="panel-body">
                                    	<span class="label label-default"><spring:message code="lbl.buscar.imovel.quartos.dormitorios"/> </span>
                                    	<spring:message code="lbl.hint.imovel.quant.quartos" var="hintQuantQuartos"/>
								            <form:select id="quantQuartos" path="quantQuartos" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantQuartos}">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	                
							                </form:select>
							              <br> <br>
							         
							         	<span class="label label-default"><spring:message code="lbl.buscar.imovel.garagem"/> </span>
							         	<spring:message code="lbl.hint.imovel.quant.garagem" var="hintQuantGaragem"/>
								             <form:select id="quantGaragem" path="quantGaragem" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantGaragem}">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	    
							                </form:select>
							              <br> <br>    
							         
							        <span class="label label-default"><spring:message code="lbl.buscar.imovel.banheiros"/> </span>
							        	<spring:message code="lbl.hint.imovel.quant.banheiros" var="hintQuantBanheiros"/>
								             <form:select id="quantBanheiro" path="quantBanheiro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantBanheiros}">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	    
							                </form:select>
							              <br> <br>      
							             
							         <span class="label label-default"><spring:message code="lbl.buscar.imovel.suites"/> </span>
							         		<spring:message code="lbl.hint.imovel.quant.suites" var="hintQuantSuites"/>
								            <form:select id="quantSuites" path="quantSuites" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantSuites}">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	                 
							                </form:select>														
									 <br>
									  <div class="pull-right">
										<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
									  </div><!-- /.pull-right -->            												   
									  <br>
                                    </div>
                               </div>
                               
                               <div class="panel rounded shadow no-overflow">                                    
                                    <br>                                    
                                    <div class="panel-body">
                                    	 <span class="label label-default"><spring:message code="lbl.buscar.imovel.dono.imovel"/> </span>
                                    	 <spring:message code="lbl.hint.imovel.perfil.dono" var="hintPerfilDono"/>
								              <form:select id="perfilUsuario" path="perfilUsuario" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPerfilDono}">                                
							                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
							                </form:select>  
							              <br> <br>
							             
							         	<c:if test="${(usuario.perfil != 'P')}">
								             <span class="label label-default"><spring:message code="lbl.buscar.imovel.intermediacao.parceria"/> </span>
								             <spring:message code="lbl.hint.imovel.parceria.intermediacao" var="hintParceriaIntermediaca"/>
									             <form:select id="autorizacaoOutroUsuario" path="autorizacaoOutroUsuario" class="chosen-select" tabindex="-1" style="display: none;" title="${hintParceriaIntermediaca}">            
													<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                        
													<form:option value="S" ><spring:message code="lbl.sim"/></form:option>                        
													<form:option value="N" ><spring:message code="lbl.nao"/></form:option>                        
												 </form:select>  										         
								             <br>  
							             </c:if> 
							             <div class="pull-right">
											<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
										  </div><!-- /.pull-right -->            												   
										  <br>
                                    </div>
                               </div>  
                            </form:form>   
                                <!-- Buscar pelo codigo de identificação imóvel -->
                             
                             <form:form method="POST" id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/filtrarBuscaImoveisPorCodigoIdentificacao" >
	                             	<div class="panel rounded shadow no-overflow">
	                                	 <div class="panel-body">
		                                	 <br>
		                                	 <span class="label label-default"><spring:message code="lbl.codigo.identificacao.imovel.resum"/> </span>                                	                                 	  
			                                 <form:input  id="codigoIdentificacao" path="codigoIdentificacao" class="form-control"  />
			                                 <form:errors id="codigoIdentificacao" path="codigoIdentificacao" cssClass="errorEntrada"  />
		                                	  
		                                      <br>
							             	  <div class="pull-right">
													<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
												  </div><!-- /.pull-right -->            												   
												<br>		
	                                	 </div>
	                                </div>
                             
                             </form:form>   
								
                            
                        </div>
                        <div class="col-lg-9 col-md-9 col-sm-8">
                        	<c:choose>
                        		<c:when test="${ not empty listaBuscarImoveis }">
                        			<div class="pull-left col-lg-4" style="padding-top: 9px;">                        				
	                                    <span class="meta-level" style="font-size: 16px;"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: </span> &nbsp; ${imovelForm.quantRegistros}  
	                                </div>	                                
	                                <div class="pull-right" >
	                                     <form:form method="POST" id="imovelBuscarImoveisForm" modelAttribute="imovelForm" action="${urlImovel}/ordenaBuscarImoveis" >
	                                     	<spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenacao"/>
	                                                <form:select id="opcaoOrdenacao" path="opcaoOrdenacao" class="form-control" title="${hintOrdenacao}">
	                                                    <form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>
	                                                    <form:option value="maiorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.imovel.mais.recente"/></form:option>
	                                                    <form:option value="menorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.imovel.menos.recente"/></form:option>
	                                                    <form:option value="maiorDataAtualizado" ><spring:message code="lbl.opcao.ordenacao.imovel.atualizado.mais.recente"/></form:option>
	                                                    <form:option value="menorDataAtualizado" ><spring:message code="lbl.opcao.ordenacao.imovel.atualizado.menos.recente"/></form:option>	                                                    
	                                                    <form:option value="tituloImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.crescente"/></form:option>
	                                                    <form:option value="tituloImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.decrescente"/></form:option>
	                                                    <form:option value="maiorValor" ><spring:message code="lbl.opcao.ordenacao.imovel.maior.valor.imovel"/></form:option>
	                                                    <form:option value="menorValor" ><spring:message code="lbl.opcao.ordenacao.imovel.menor.valor.imovel"/></form:option>
	                                              </form:select>
	                                      </form:form>
	                                </div><!-- /.pull-right -->
	                                <div class="pull-right" style="padding-right:10px;">
	                                	<spring:message code="lbl.hint.imovel.ver.mapa" var="hintMapa"/>
	                                    <form:form method="POST" id="imovelBuscarImoveisMapaForm" modelAttribute="imovelForm" action="${urlImovel}/buscarImovelMapa" >
	                                        <button type="submit" class="btn btn-primary btn-block" title="${hintMapa}"><spring:message code="lbl.btn.ver.mapa.imovel" /></button>
	                                    </form:form>
	                                </div><!-- /.pull-left -->
	                                
	                                <c:if test="${imovelForm.isVisible() }">
	                                	<div class="pull-right" style="padding-right:10px;">
		                                    <form:form method="POST" id="imovelBuscarImoveisPageForm" modelAttribute="imovelForm" action="${urlImovel}/paginarBuscarImovel" >
		                                     	 <spring:message code="lbl.hint.opcao.paginacao" var="hintPaginacao"/>
	                                             <form:select id="opcaoPaginacao" path="opcaoPaginacao" class="form-control" title="${hintPaginacao}">
	                                                 <form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
	                                                 <form:options items="${imovelForm.listaPaginas}" itemValue="key" itemLabel="label"/>	                                                    	                                                    
	                                             </form:select>
		                                      </form:form>
		                                </div><!-- /.pull-left -->
	                                </c:if>
	                                
	                                <div class="clearfix"></div>
	
	                                <div class="media-list list-search">
	                                    <c:forEach var="imovel" items="${listaBuscarImoveis}" varStatus="item">
	                                        <div class="media rounded shadow no-overflow">
	                                        
	                                            <div class="media-left">
	                                                <a href="${urlImovel}/detalhesImovel/${imovel.id}" >
	                                                   <span class="meta-provider ${imovel.classePorAcao}" style="font-size:19px;">${imovel.acaoFmt} <br>
	                                                   							<strong>  R$<fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
	                                                   </span><br>                                                   
	                                                   
	                                                    <img src="data:image/jpeg;base64,${imovel.imagemArquivo}" style="width: 270px; height: 355px; "  />	                                           
	                                                </a>	
	                                            </div>
	                                            <div class="media-body">
	                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${imovel.tipoImovelFmt}</span>
	                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlImovel}/detalhesImovel/${imovel.id}"><strong>${imovel.titulo} </strong> </a></h4>
	                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovel.endereco} - ${imovel.bairro} - ${imovel.cidade} -${imovel.uf} </h1>
	                                                
	                                                <div class="col-md-5" >  	                                                
	                                                	<div class="media-body" >
				                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.ultima.imovel.atualizacao" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${imovel.dataUltimaAtualizacao}' pattern='dd/MM/yyyy'/></font></span></em> 
				                                            
				                                            <br> <br>
				                                            
				                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.imovel" />: </font><span class="text-success"><br>				                                            
				                                            <font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em>				                                            
				                                            
				                                        </div>                                                  
	                                                    <br/> <br/> <br/> 	                                                   
	                                                </div>
	                                                
	                                                <div class="col-md-6" style="margin-right: -9px;">
	                                                    <table class="table table-condensed">
	                                                        <tbody style="font-size: 13px;">	                                                        
	                                                        
	                                                        	<tr>
	                                                                <td class="text-left"><spring:message code="lbl.area.m2.resum"/></td>
	                                                                <td class="text-right"><fmt:formatNumber value="${imovel.area}" pattern="#,##0;-0"/>m<sup>2</sup></td>
	                                                            </tr>
	                                                            <tr>
	                                                                <td class="text-left"><spring:message code="lbl.quartos.dormitorios.resum"/></td>
	                                                                <td class="text-right">${imovel.quantQuartos}</td>
	                                                            </tr>	                                                            
	                                                            <tr>
	                                                                <td class="text-left"><spring:message code="lbl.buscar.imovel.banheiros"/></td>
	                                                                <td class="text-right">${imovel.quantBanheiro}</td>
	                                                            </tr>	                                                            
	                                                            <tr>
	                                                                <td class="text-left"><spring:message code="lbl.suites"/></td>
	                                                                <td class="text-right">${imovel.quantSuites}</td>
	                                                            </tr>
	                                                            <tr>
	                                                                <td class="text-left"><spring:message code="lbl.vagas.garagem.resum"/></td>
	                                                                <td class="text-right">${imovel.quantGaragem} <spring:message code="lbl.num.vagas"/></td>
	                                                            </tr>
	                                                            
	                                                            <tr>
	                                                                <td class="text-left"><spring:message code="lbl.codigo.identificacao.imovel.resum"/></td>
	                                                                <td class="text-right">${imovel.codigoIdentificacao}</td>
	                                                            </tr>
	                                                        </tbody>
	                                                    </table>
	                                                    
	                                                    <br>
	                                                    
	                                                     <% if ( request.getSession().getAttribute("acessoValido").equals("S") ) {%>   
	                                                    	<c:if test="${(imovel.interessadoImovel == 'N') && (imovel.usuario.id != usuario.id)}">
																<a href="#a" id="idMeInteressei_${imovel.id}" onClick="adicionarInteresse(${imovel.id})" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action"><i class="fa fa-star-o" title="<spring:message code="lbl.me.interessei"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> <spring:message code="lbl.me.interessei"/> &nbsp;&nbsp; </a> 
															</c:if>
															
															<c:if test="${imovel.interessadoImovel == 'S'}">
																<a href="#a" id="idNovoInteressado_${imovel.id}" onClick="retirarInteresse(${imovel.id})" style="font-size:x-medium; color: rgb(99, 110, 123);" class="meta-action"><i class="fa fa-star" style="color: rgb(99, 110, 123);" title="<spring:message code="lbl.interessado"/>"></i> &nbsp;&nbsp; </a>
															</c:if>
															
															<a href="#a" id="idNovoMeInteressei_${imovel.id}" onClick="adicionarInteresse(${imovel.id})" style="font-size:x-large; color: rgb(99, 110, 123); display: none;" class="meta-action"><i class="fa fa-star-o" title="<spring:message code="lbl.me.interessei"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> <spring:message code="lbl.me.interessei"/> &nbsp;&nbsp; </a> 
															<a href="#a" id="idInteressado_${imovel.id}" onClick="retirarInteresse(${imovel.id})" style="font-size:x-large; display: none;" class="meta-action"><i class="fa fa-star" style="color: rgb(99, 110, 123);" title="<spring:message code="lbl.interessado"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> <spring:message code="lbl.interessado"/> </font> &nbsp;&nbsp; </a> 
															
	                                                        <a href="#a" onClick="adicionarComparativo(${imovel.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-eye" style="color: rgb(99, 110, 123);" title="<spring:message code="lbl.title.link.comparar"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> <spring:message code="lbl.title.link.comparar"/> </font></a>
	                                                    <% } %>	 
	                                                </div>
	                                            </div>
	                                        </div>
	                                    </c:forEach>
	                                </div>
                        		</c:when>
                        		
                        		<c:when test="${ empty listaBuscarImoveis }">
                        			<div class="callout callout-warning">
	                                    <strong><spring:message code="lbl.rel.nenhum.registro"/></strong>
	                                </div>
                        		</c:when>
                        		
                        	</c:choose>                   
                        </div>
                    </div>
                </div><!-- /.body-content -->
            </section><!-- /#page-content -->
         </section><!-- /#wrapper -->
         
         <!-- Start content modal Usuario Detalhes-->
			<c:import url="../ajuda/imovelDetalhesModal.jsp"></c:import>																				
		<!-- End content  modal Usuario Detalhes -->
			
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
         
         

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->
    
    
