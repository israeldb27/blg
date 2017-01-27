<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/imovel/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/imovel/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>

<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>

<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>

    
<script type="text/javascript">
    	$(document).ready(function() {  
    			
    		
    		$('.my-tooltip').tooltip({    			  
    		});
    		
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
    		
    		 $('#opcaoPaginacao').change(function () {				
   				$("#meusImoveisPageForm").submit();      
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.meus.imoveis"/> </h2>
					   
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                 <div class="body-content animated fadeIn">

                    <div class="row">
                         <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
                            <c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        <div class="col-lg-3 col-md-3 col-sm-4">
                            <form:form method="POST" id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/filtrarMeusImoveis" >
                            	<div class="panel rounded shadow no-overflow">
                           			    <div class="panel-heading">
		                                       <div class="pull-left">
		                                           <h1 class="panel-title panel-titulo"> 
		                                        		<strong ><spring:message code="lbl.filtro.geral"/> </strong>
		                                           </h1>
		                                       </div><!-- /.pull-left -->
		                                         
	                                           <div class="pull-right">
		                                 			<a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>	                                       		
		                                 		</div>                                       
		                                       <div class="clearfix"></div>
		                                 </div>  
                                    <div class="panel-body">
                                    	</br>
                                        <div class="form-group no-margin">                                        	
                                        	<span class="label label-default"><spring:message code="lbl.estado"/> </span>
                                        	<spring:message code="lbl.hint.imovel.estado" var="hintEstado"/>
                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" title="${hintEstado}">
                                                    <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                    <form:options items="${imovelForm.listaEstados}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.cidade"/> </span>
                                            <spring:message code="lbl.hint.imovel.cidade" var="hintCidade"/>
                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;" title="${hintCidade}">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${imovelForm.listaCidades}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
                                            <spring:message code="lbl.hint.imovel.bairro" var="hintBairro"/>
                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintBairro}">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${imovelForm.listaBairros}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            <br>											  
												  <div class="pull-right">
												    <spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>
													<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
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
								              <form:select id="tipoImovel" path="tipoImovel" class="chosen-select" tabindex="-1" style="display: none;"  title="${hintTipoImovel}">                                
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
						             	  		<spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>
												<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
											  </div><!-- /.pull-right -->            												   
											<br>						
                                    </div><!-- /.panel-body -->
                                </div>
                                
                                <div class="panel rounded shadow no-overflow">
                                	 <div class="panel-body">
	                                	 <br>
	                                	 <span class="label label-default"><spring:message code="lbl.valor.imovel.minimo"/> </span>                                	                                 	  
		                                 <form:input  id="valorMin" path="valorMin" class="form-control" onkeypress="formatarMoeda(this);" />
		                                 <form:errors id="valorMin" path="valorMin" cssClass="errorEntrada"  />
	
	                                	  <br> <br>
	                                	  
	                                	  <span class="label label-default"><spring:message code="lbl.valor.imovel.maximo"/> </span>
	                                	  <form:input  id="valorMax" path="valorMax" class="form-control" onkeypress="formatarMoeda(this);" />
	                                      <form:errors id="valorMax" path="valorMax" cssClass="errorEntrada"  />
	                                	  
	                                      <br>
						             	  <div class="pull-right">
												<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
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
										<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
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
								             <span class="label label-default">Parceria ou Intermediação </span>
									             <spring:message code="lbl.hint.imovel.parceria.intermediacao" var="hintParceriaIntermediaca"/>
									             <form:select id="autorizacaoOutroUsuario" path="autorizacaoOutroUsuario" class="chosen-select" tabindex="-1" style="display: none;" title="${hintParceriaIntermediaca}">            
													<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                        
													<form:option value="S" ><spring:message code="lbl.sim"/></form:option>                        
													<form:option value="N" ><spring:message code="lbl.nao"/></form:option>                        
												 </form:select>  										         
								             <br>  
							             </c:if> 
							             <div class="pull-right">
											<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
										  </div><!-- /.pull-right -->            												   
										  <br>
                                    </div>
                               </div> 
                                            
                             </form:form>                             
                             
                             <!-- Buscar pelo codigo de identificação imóvel -->
                             
                             <form:form method="POST" id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/filtrarMeusImoveisPorCodigoIdentificacao" >
	                             	<div class="panel rounded shadow no-overflow">
	                                	 <div class="panel-body">
		                                	 <br>
		                                	 <span class="label label-default"><spring:message code="lbl.codigo.identificacao.imovel.resum"/> </span>                                	                                 	  
			                                 <form:input  id="codigoIdentificacao" path="codigoIdentificacao" class="form-control"  />
			                                 <form:errors id="codigoIdentificacao" path="codigoIdentificacao" cssClass="errorEntrada"  />
		                                	  
		                                      <br>
							             	  <div class="pull-right">
													<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
												  </div><!-- /.pull-right -->            												   
												<br>		
	                                	 </div>
	                                </div>                             
                             </form:form>
                                             
                        </div>                    
					                   
					       <div class="col-lg-9 col-md-9 col-sm-8">
                            <c:if test="${ empty listaMeusImoveis }">
                            	 <form:form method="POST" id="imovelBuscarImoveisMapaForm" modelAttribute="imovelForm" action="${urlImovel}/prepararCadastroImovel" >
                                        <button type="submit" class="btn btn-primary btn-block" style="width: 15%;"><spring:message code="lbl.btn.novo.imovel"/></button>
                                 </form:form>
                                <div class="callout callout-warning">
                                    <strong><spring:message code="lbl.nenhum.imovel.retornado"/></strong>                              
                                </div>
                            </c:if>
                            <c:if test="${ not empty listaMeusImoveis }">
                                <div class="pull-left col-lg-4" style="padding-top: 9px;">                        				
	                                 <span class="meta-level" style="font-size: 16px;"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: </span> &nbsp; ${imovelForm.quantRegistros}  
	                            </div>
                                <div class="pull-right" style="padding-right:10px; width: 240px;">
                                	 <spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenar"/>
                                     <form:form method="POST" id="meusImoveisForm" modelAttribute="imovelForm" action="${urlImovel}/ordenaMeusImoveis"  title="${hintOrdenar}">
                                                <form:select id="opcaoOrdenacao" path="opcaoOrdenacao" class="chosen-select" tabindex="-1" style="display: none;">
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
                                	<c:if test="${imovelForm.isVisible() }">
	                                	<div class="pull-right" style="padding-right:20px;">
		                                    <form:form method="POST" id="meusImoveisPageForm" modelAttribute="imovelForm" action="${urlImovel}/filtrarMeusImoveis" >
		                                     	 <spring:message code="lbl.hint.opcao.paginacao" var="hintPaginacao"/>
	                                             <form:select id="opcaoPaginacao" path="opcaoPaginacao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPaginacao}">
	                                                 <form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
	                                                 <form:options items="${imovelForm.listaPaginas}" itemValue="key" itemLabel="label"/>	                                                    	                                                    
	                                             </form:select>
		                                      </form:form>
		                                </div><!-- /.pull-left -->
	                                </c:if>
                                
                                <div class="pull-right" style="padding-right:10px;">
                                	<spring:message code="lbl.hint.link.cadastrar.novo.imovel" var="hintNovoImovel"/>
                                    <form:form method="POST" id="imovelBuscarImoveisMapaForm" modelAttribute="imovelForm" action="${urlImovel}/prepararCadastroImovel" >
                                        <button type="submit" class="btn btn-primary btn-block" title="${hintNovoImovel}"><spring:message code="lbl.btn.novo.imovel"/></button>
                                    </form:form>
                                </div><!-- /.pull-left -->
                                <div class="clearfix"></div>

                                <div class="media-list list-search">
                                    <c:forEach var="imovel" items="${listaMeusImoveis}" varStatus="item">
                                        <div class="media rounded shadow no-overflow">
                                            <div class="media-left">
                                                <a href="${urlImovel}/detalhesImovel/${imovel.id}" >
                                                   <span class="meta-provider ${imovel.classePorAcao}" style="font-size:19px;">${imovel.acaoFmt} <br>
                                                   							<strong>  R$<fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
                                                   </span><br>                                                   
                                                    <img src="${context}${imovel.imagemArquivo}" class="img-responsive" style="width: 270px; height: 355px;  alt="admin"/>
                                                </a>
                                            </div>
                                            <div class="media-body">
                                                <span class="label pull-right" style="background-color: #03A9F4; font-size: 12px">${imovel.tipoImovelFmt}</span>
                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlImovel}/detalhesImovel/${imovel.id}" style="color : #03A9F4;">${imovel.titulo}</a></h4>
                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovel.endereco} - ${imovel.bairro} - ${imovel.cidade} -${imovel.uf} </h5>
                                        		
                                        		<br>
                                                
                                                <div class="col-md-11">
                                                    <table class="table table-condensed">
                                                        <tbody style="font-size: 13px;">
                                                        	
                                                        	<tr>
                                                                <td class="text-left"><spring:message code="lbl.area.m2.resum"/></td>
                                                                <td class="text-right"><fmt:formatNumber value="${imovel.area}" pattern="#,##0;-0"/>m<sup>2</sup></td>
                                                            </tr>
                                                            <tr>
                                                                <td class="text-left"><spring:message code="lbl.quartos.dormitorios.resum"/></td>
                                                                <td class="text-right">${imovel.quantQuartos} </td>
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td class="text-left"><spring:message code="lbl.vagas.garagem.resum"/></td>
                                                                <td class="text-right">${imovel.quantGaragem} <spring:message code="lbl.num.vagas"/></td>
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
                                                                <td class="text-left"><spring:message code="lbl.data.ultima.imovel.atualizacao" /></td>
                                                                <td class="text-right"><fmt:formatDate value='${imovel.dataUltimaAtualizacao}' pattern='dd/MM/yyyy'/></td>
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td class="text-left"><spring:message code="lbl.data.cadastro.imovel" /></td>
                                                                <td class="text-right"><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></td>
                                                            </tr>
                                                            
                                                             <tr>
                                                                <td class="text-left"><spring:message code="lbl.codigo.identificacao.imovel.resum"/></td>
                                                                <td class="text-right">${imovel.codigoIdentificacao}</td>
                                                            </tr>
                                                            
                                                        </tbody>
                                                    </table>
                                                    
                                                    <br>
                                                       	<% if ( request.getSession().getAttribute("acessoValido").equals("S") ) {%>
                                                 	
		                                                 	<spring:message code="lbl.title.editar" var="mensagemEditar"/>
					                                        <a href="${urlImovel}/prepararEditarImovel/${imovel.id}" style="font-size:x-large; color: rgb(99, 110, 123);"  class="dropdown-toggle" title="${mensagemEditar}"><i class="fa fa-pencil-square-o" > <font style="color: rgb(99, 110, 123); font-size: 12px; margin-top:  22px;"> ${mensagemEditar} </font> &nbsp;&nbsp; </i></a>  
					                                        
					                                        <spring:message code="lbl.title.link.alterar.galeria.foto.imovel" var="mensagemAlterarGaleriaFoto"/>
					                                        <a href="${urlImovel}/preparaAlteracaoGaleriaFotosImovel/${imovel.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="dropdown-toggle" ><i class="fa fa-file-image-o"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-top:  22px;"> ${mensagemAlterarGaleriaFoto} </font> &nbsp;&nbsp; </i> </a> 
					                                        			                                       			                                        
					                                        <spring:message code="lbl.title.link.editar.mapa.imovel.resum" var="mensagemAlterarMapaImovel"/>
					                                        <a href="${urlImovel}/prepararAtualizarMapaImovel/${imovel.id}" style="font-size:x-large; color: rgb(99, 110, 123);"  class="dropdown-toggle" ><i class="fa fa-map-o"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-top:  22px;"> ${mensagemAlterarMapaImovel} </font> &nbsp;&nbsp; </i> </a> 
					                                        
					                                        <spring:message code="lbl.acao.sugerir" var="mensagemAcaoSugerir"/>
					                                        <a href="${urlImovelIndicado}/selecionarParaIndicarImovel/${imovel.id}" style="font-size:x-large; color: rgb(99, 110, 123);"  class="dropdown-toggle"  ><i class="fa fa-share-alt"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-top:  22px;"> ${mensagemAcaoSugerir} </font> &nbsp;&nbsp; </i> </a> 
					                                        
				                                        <% } %>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </div>              
                                             
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
                
                <!-- Fim Meus Imoveis -->
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->
     
             <!-- Start inside form layout -->
            <div class="modal fade bs-example-modal-form" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title">Form title</h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form:form id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/alterarFotoPrincipalImovel" class="form-horizontal mt-10" enctype="multipart/form-data" method="POST">
								<form:hidden path="id" value="${imovelForm.id}"/>
								<div class="form-group">
                                             <label class="control-label"></label>
                                             <div class="fileinput fileinput-new" data-provides="fileinput">
                                                 <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px;"></div>
                                                 <div>
                                                     <span class="btn btn-info btn-file"><span class="fileinput-new">Selecionar Foto</span><span class="fileinput-exists">Selecionar Foto</span>	                                                        
                                                     <input type="text" name="name"/>
											<input type="file" name="file"/>
                                                     
                                                     </span>
                                                     <a href="#" class="btn btn-danger fileinput-exists" data-dismiss="fileinput">Remover</a>
                                                 </div>
                                             </div>
                                         </div><!-- /.form-group -->
                                         
                                         <button type="submit" class="btn btn-primary btn-block">Salvar Foto</button>    
							</form:form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ End inside form layout -->
			
					<!-- Start content modal Ajuda - funcionalidade -->
						<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
					<!-- End content  modal Ajuda - funcionalidade -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->