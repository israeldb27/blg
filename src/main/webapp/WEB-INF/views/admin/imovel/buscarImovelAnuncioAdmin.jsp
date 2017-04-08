<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url var="urlImovelFavoritos" value="/imovelFavoritos"/>
<spring:url var="urlAdmin" value="/admin"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>
<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioEnum"%>

<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>
<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioEnum.values() %>"/>


<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   
  
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

    	<script type="text/javascript">
    	$(document).ready(function() {
    		
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
   				$("#imoveldestaquePageForm").submit();      
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
				
			<c:import url="../layout-admin/head-layout.jsp"></c:import>
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../layout-admin/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../layout-admin/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">

            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.busca.imoveis.anuncio"/> </h2>				
					
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">                      
                        <div class="col-lg-3 col-md-3 col-sm-4">
                            <form:form method="POST" id="imoveldestaqueForm" modelAttribute="imoveldestaqueForm" action="${urlAdmin}/buscaImoveisAnuncio" >
                            
                                <div class="panel rounded shadow no-overflow">
                           			  <br> 
                                    <div class="panel-body">
                                        <div class="form-group no-margin">
                                        	<span class="label label-default"><spring:message code="lbl.estado"/> </span>
                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;">
                                                    <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                    <form:options items="${imoveldestaqueForm.listaEstados}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.cidade"/> </span>
                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${imoveldestaqueForm.listaCidades}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${imoveldestaqueForm.listaBairros}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            <br>											  
												  <div class="pull-right">
													<button type="submit" class="button btn-primary" > <spring:message code="lbl.filtrar.geral"/></button>
												  </div><!-- /.pull-right -->            												   
												<br>
                                            
                                        </div><!-- /.form-group -->
                                    </div><!-- /.panel -->
                                </div>
                                
                                <div class="panel rounded shadow no-overflow">                                    
                                    <br>                                    
                                    <div class="panel-body">
                                        <span class="label label-default"><spring:message code="lbl.tipo.imovel"/> </span>
								              <form:select id="tipoImovel" path="tipoImovel" class="chosen-select" tabindex="-1" style="display: none;" >                                
							                        <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                        <form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />								                        
							                 </form:select>	
									     <br> <br>
									     
								     	<span class="label label-default"><spring:message code="lbl.acao.imovel"/> </span>
								              <form:select id="acao" path="acao" class="chosen-select" tabindex="-1" style="display: none;">                                
							                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                    <form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />								                    
							                </form:select>
								        <br> <br>
								           
							            <span class="label label-default"><spring:message code="lbl.buscar.imovel.status.imovel"/> </span>
								              <form:select id="perfilImovel" path="perfilImovel" class="chosen-select" tabindex="-1" style="display: none;">                                
							                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                    	<form:options items="${listaStatusImovel}" itemValue="identificador" itemLabel="rotulo" />								                    	   
							                </form:select> 
							             <br>
						             	  <div class="pull-right">
												<button type="submit" class="button btn-primary" > <spring:message code="lbl.filtrar.geral"/></button>
											  </div><!-- /.pull-right -->            												   
											<br>
						
                                    </div><!-- /.panel-body -->
                                </div>
                                
                                <div class="panel rounded shadow no-overflow">                                    
                                    <br>                                    
                                    <div class="panel-body">
                                    	<span class="label label-default"><spring:message code="lbl.buscar.imovel.quartos.dormitorios"/> </span>
								            <form:select id="quantQuartos" path="quantQuartos" class="chosen-select" tabindex="-1" style="display: none;">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	                
							                </form:select>
							             <br>
							         
							         <span class="label label-default"><spring:message code="lbl.buscar.imovel.garagem"/> </span>
								             <form:select id="quantGaragem" path="quantGaragem" class="chosen-select" tabindex="-1" style="display: none;">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	    
							                </form:select>
							             <br>    
							         
							        <span class="label label-default"><spring:message code="lbl.buscar.imovel.banheiros"/> </span>
								             <form:select id="quantBanheiro" path="quantBanheiro" class="chosen-select" tabindex="-1" style="display: none;">                                
							                    <form:option value="0" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	    
							                </form:select>
							             <br>       
							             
							         <span class="label label-default"><spring:message code="lbl.buscar.imovel.suites"/> </span>
								            <form:select id="quantSuites" path="quantSuites" class="chosen-select" tabindex="-1" style="display: none;">                                
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
										<button type="submit" class="button btn-primary" > <spring:message code="lbl.filtrar.geral"/></button>
									  </div><!-- /.pull-right -->            												   
									  <br>
                                    </div>
                                    
                               </div>
                               
                                 <div class="panel rounded shadow no-overflow">
                                	 <div class="panel-body">
	                                	 <br>
	                                	 <span class="label label-default"><spring:message code="lbl.filtro.data.inicio.anuncio"/> </span>                                	                                 	  
		                                 <form:input  id="dataInicioAnuncio" path="dataInicioAnuncio" class="chosen-select" tabindex="-1" style="display: none;"    />		                                 
		                                 <form:errors id="dataInicioAnuncio" path="dataInicioAnuncio" cssClass="errorEntrada"  />
	
	                                	  <br> <br>
	                                	  
	                                	  <span class="label label-default"><spring:message code="lbl.filtro.data.fim.anuncio"/> </span>
	                                	  <form:input  id="dataFimAnuncio" path="dataFimAnuncio" class="chosen-select" tabindex="-1" style="display: none;"  />
	                                      <form:errors id="dataFimAnuncio" path="dataFimAnuncio" cssClass="errorEntrada"  />
	                                	  
	                                      <br>
						             	  <div class="pull-right">
												<button type="submit" class="button btn-primary" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
											  </div><!-- /.pull-right -->            												   
											<br>		
                                	 </div>
                                </div>
                                
                                 <div class="panel rounded shadow no-overflow">
                                	 <div class="panel-body">
	                                	 <br>
	                                	 <span class="label label-default"><spring:message code="lbl.filtro.data.inicio.cadastro"/> </span>                                	                                 	  
		                                 <form:input  id="dataInicioCadastro" path="dataInicioCadastro" class="chosen-select" tabindex="-1" style="display: none;"    />		                                 
		                                 <form:errors id="dataInicioCadastro" path="dataInicioCadastro" cssClass="errorEntrada"  />
	
	                                	  <br> <br>
	                                	  
	                                	  <span class="label label-default"><spring:message code="lbl.filtro.data.fim.cadastro"/> </span>
	                                	  <form:input  id="dataFimCadastro" path="dataFimCadastro" class="chosen-select" tabindex="-1" style="display: none;"  />
	                                      <form:errors id="dataFimCadastro" path="dataFimCadastro" cssClass="errorEntrada"  />
	                                	  
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
                        		<c:when test="${ empty listaImoveisAnuncios }">
                        			<div class="callout callout-warning">
	                                    <strong>Ooops! Não encontramos resultados para esta busca!</strong>
	                                    <p>Faça alguns ajustes nos filtros e tente novamente.
	                                </div>	
                        		</c:when>
                        		
                        		<c:when test="${ not empty listaImoveisAnuncios }">
                        			<div class="pull-left" style="    margin-top: 7px;">
	                                    <span class="meta-level"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: ${imoveldestaqueForm.quantRegistros} </span>
	                                </div>
	                                <div class="pull-right" >
	                                      <form:form method="POST" id="imoveldestaqueOrdForm" modelAttribute="imoveldestaqueForm" action="${urlAdmin}/ordenaBuscarImoveis" >							                        	
						                        	<form:select id="opcaoOrdenacao" path="opcaoOrdenacao" class="chosen-select" tabindex="-1" style="display: none;">                                
								                        <form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>                      
								                        <form:option value="maiorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.imovel.mais.recente"/></form:option>
														<form:option value="menorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.imovel.menos.recente"/></form:option>
														<form:option value="tituloImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.crescente"/></form:option>
														<form:option value="tituloImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.decrescente"/></form:option>
														<form:option value="maiorValor" ><spring:message code="lbl.opcao.ordenacao.imovel.maior.valor.imovel"/></form:option>
														<form:option value="menorValor" ><spring:message code="lbl.opcao.ordenacao.imovel.menor.valor.imovel"/></form:option>
								                  </form:select>							                        
						                  </form:form>	
	                                </div><!-- /.pull-right -->
	                                <div class="pull-right" style="padding-right:10px;">
	                                     
	                                </div><!-- /.pull-left -->
	                                
	                                <c:if test="${imoveldestaqueForm.isVisible() }">
		                                	<div class="pull-right" style="padding-right:20px;">
			                                    <form:form method="POST" id="imoveldestaquePageForm" modelAttribute="imoveldestaqueForm" action="${urlAdmin}/buscaImoveisAnuncio" >
			                                     	 <spring:message code="lbl.hint.opcao.paginacao" var="hintPaginacao"/>
		                                             <form:select id="opcaoPaginacao" path="opcaoPaginacao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPaginacao}">
		                                                 <form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
		                                                 <form:options items="${imoveldestaqueForm.listaPaginas}" itemValue="key" itemLabel="label"/>	                                                    	                                                    
		                                             </form:select>
			                                      </form:form>
			                                </div><!-- /.pull-left -->
		                                </c:if>
	                                <div class="clearfix"></div>
	
	                                <div class="media-list list-search">
	                                    <c:forEach var="imovelAnuncio" items="${listaImoveisAnuncios}" varStatus="item">
	                                        <div class="media rounded shadow no-overflow">
	                                            <div class="media-left">
	                                                <a href="${urlAdmin}/detalhesImovel/${imovelAnuncio.imovel.id}" >
	                                                   <span class="meta-provider ${imovelAnuncio.imovel.classePorAcao}" style="font-size:19px;">${imovelAnuncio.imovel.acaoFmt} <br>
	                                                   							<strong>  R$<fmt:formatNumber value="${imovelAnuncio.imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
	                                                   </span><br>                                                   
	                                                    <img src="data:image/jpeg;base64,${imovelAnuncio.imovel.imagemArquivo}" class="img-responsive" style="width: 260px; height: 195px; alt="admin"/>
	                                                </a>
	                                            </div>
	                                            <div class="media-body">
	                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${imovelAnuncio.imovel.tipoImovelFmt}</span>
	                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlAdmin}/detalhesImovel/${imovelAnuncio.imovel.id}" style="color : #9d2428;">${imovelAnuncio.imovel.titulo}</a></h4>
	                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovelAnuncio.imovel.endereco} - ${imovelAnuncio.imovel.bairro} - ${imovelAnuncio.imovel.cidade} -${imovelAnuncio.imovel.uf} </h1>
	                                                
	                                                <div class="col-md-5" >                                                    
	                                                    <div class="media-body" >
				                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.table.data.destaque.imovel.anuncio" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${imovelAnuncio.dataDestaque}' pattern='dd/MM/yyyy'/></font></span></em>
				                                        </div>                                                  
	                                                    <br/> <br/> <br/>                                                      
	                                                </div>
	                                                
	                                                <div class="col-md-7">
	                                                    <table class="table table-condensed">
	                                                        <tbody style="font-size: 13px;">
	                                                        	<tr>
	                                                                <td class="text-left"><spring:message code="lbl.area.m2.resum"/></td>
	                                                                <td class="text-right"><fmt:formatNumber value="${imovelAnuncio.imovel.area}" pattern="#,##0;-0"/>m<sup>2</sup></td>
	                                                            </tr>
	                                                            <tr>
	                                                                <td class="text-left"><spring:message code="lbl.quartos.dormitorios.resum"/></td>
	                                                                <td class="text-right"><c:if test="${imovelAnuncio.imovel.quantQuartos > 0}">${imovelAnuncio.imovel.quantQuartos}</c:if></td>
	                                                            </tr>
	                                                            <tr>
	                                                                <td class="text-left"><spring:message code="lbl.suites"/></td>
	                                                                <td class="text-right"><c:if test="${imovelAnuncio.imovel.quantSuites > 0}">${imovelAnuncio.imovel.quantSuites}</c:if></td>
	                                                            </tr>
	                                                            <tr>
	                                                                <td class="text-left"><spring:message code="lbl.vagas.garagem.resum"/></td>
	                                                                <td class="text-right"><c:if test="${imovelAnuncio.imovel.quantGaragem > 0}">${imovelAnuncio.imovel.quantGaragem}</c:if> <spring:message code="lbl.num.vagas"/></td>
	                                                            </tr>
	                                                        </tbody>
	                                                    </table>
	                                                </div>
	                                            </div>
	                                        </div>
	                                    </c:forEach>
	                                </div>
                        		</c:when>
                        	</c:choose>
                        </div>
                    </div>
                </div><!-- /.body-content -->
            </section><!-- /#page-content -->
         </section><!-- /#wrapper -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout-admin/head-bootstrap.jsp"></c:import>  
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->
