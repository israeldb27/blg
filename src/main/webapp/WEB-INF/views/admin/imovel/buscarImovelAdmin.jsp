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
<spring:url var="urlImovelComparativo" value="/imovelComparativo"/>
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
    	
    	function adicionarComparativo(id) {    	
    		var parametro1 = id;
    	    $.ajax({                
                url: '${urlImovelComparativo}/adicionarImovelComparativo/' + parametro1,                
                success: function(){
                	
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.busca.imoveis"/> </h2>				
					
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">                      
                        <div class="col-lg-3 col-md-3 col-sm-4">
                            <form:form method="POST" id="imovelForm" modelAttribute="imovelForm" action="${urlAdmin}/buscarImovel" >
                            
                                <div class="panel rounded shadow no-overflow">
                                	<div class="panel-heading">
	                                       <div class="pull-left">
	                                           <h1 class="panel-title panel-titulo" > 
	                                        		<strong ><spring:message code="lbl.filtro.geral"/> </strong>
	                                           </h1>
	                                       </div><!-- /.pull-left -->                                        
	                                       <div class="clearfix"></div>
	                                 </div>
	                                 
                           			  <br> 
                                    <div class="panel-body">
                                        <div class="form-group no-margin">
                                        	<span class="label label-default"><spring:message code="lbl.estado"/> </span>
                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;">
                                                    <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                    <form:options items="${imovelForm.listaEstados}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.cidade"/> </span>
                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${imovelForm.listaCidades}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            </br> </br>
                                            <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;">
                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
                                                <form:options items="${imovelForm.listaBairros}" itemValue="key" itemLabel="label"/>
                                            </form:select>
                                            <br>											  
												  <div class="pull-right">
													<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" > <spring:message code="lbl.filtrar.geral"/></button>
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
								              <form:select id="acao" path="acao" class="form-control">                                
							                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                    <form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />								                    
							                </form:select>
								        <br> <br>
								           
							            <span class="label label-default"><spring:message code="lbl.buscar.imovel.status.imovel"/> </span>
								              <form:select id="perfilImovel" path="perfilImovel" class="form-control">                                
							                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                    	<form:options items="${listaStatusImovel}" itemValue="identificador" itemLabel="rotulo" />								                    	   
							                </form:select> 
							             <br>
						             	  <div class="pull-right">
												<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" > <spring:message code="lbl.filtrar.geral"/></button>
											  </div><!-- /.pull-right -->            												   
											<br>
						
                                    </div><!-- /.panel-body -->
                                </div>
                                
                                <div class="panel rounded shadow no-overflow">                                    
                                    <br>                                    
                                    <div class="panel-body">
                                    	<span class="label label-default"><spring:message code="lbl.buscar.imovel.quartos.dormitorios"/> </span>
								            <form:select id="qtdQuartos" path="qtdQuartos" class="form-control">                                
							                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	                
							                </form:select>
							             <br>
							         
							         <span class="label label-default"><spring:message code="lbl.buscar.imovel.garagem"/> </span>
								             <form:select id="qtdGaragem" path="qtdGaragem" class="form-control">                                
							                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	    
							                </form:select>
							             <br>    
							         
							        <span class="label label-default"><spring:message code="lbl.buscar.imovel.banheiros"/> </span>
								             <form:select id="qtdBanheiro" path="qtdBanheiro" class="form-control">                                
							                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	    
							                </form:select>
							             <br>       
							             
							         <span class="label label-default"><spring:message code="lbl.buscar.imovel.suites"/> </span>
								            <form:select id="qtdSuites" path="qtdSuites" class="form-control">                                
							                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:option value="1" >1</form:option>	                        
												<form:option value="2" >2</form:option>
												<form:option value="3" >3</form:option>
												<form:option value="4" >4</form:option>
												<form:option value="5" >5</form:option>
												<form:option value="6" ><spring:message code="opcao.selecao.mais.seis"/></form:option>	                 
							                </form:select>														
									 <br>
									  <div class="pull-right">
										<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" > <spring:message code="lbl.filtrar.geral"/></button>
									  </div><!-- /.pull-right -->            												   
									  <br>
                                    </div>
                               </div>
                               
                               <div class="panel rounded shadow no-overflow">                                    
                                    <br>                                    
                                    <div class="panel-body">
                                    	 <span class="label label-default"><spring:message code="lbl.buscar.imovel.dono.imovel"/> </span>
								              <form:select id="perfilUsuario" path="perfilUsuario" class="form-control">                                
							                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
							                </form:select>  
							             <br>
							             
							         	<c:if test="${(usuario.perfil != 'P')}">
								             <span class="label label-default">Parceria ou Intermediação </span>
									             <form:select id="autorizacaoOutroUsuario" path="autorizacaoOutroUsuario" class="form-control">            
													<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                        
													<form:option value="S" ><spring:message code="lbl.sim"/></form:option>                        
													<form:option value="N" ><spring:message code="lbl.nao"/></form:option>                        
												 </form:select>  										         
								             <br>  
							             </c:if> 
							             <div class="pull-right">
											<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" > <spring:message code="lbl.filtrar.geral"/></button>
										  </div><!-- /.pull-right -->            												   
										  <br>
                                    </div>
                               </div>     
								
                            </form:form>
                        </div>
                        <div class="col-lg-9 col-md-9 col-sm-8">
                        	<c:choose>
                        		<c:when test="${ empty listaBuscarImoveis }">
                        			<div class="callout callout-warning">
	                                    <strong>Ooops! Não encontramos resultados para esta busca!</strong>
	                                    <p>Faça alguns ajustes nos filtros e tente novamente.
	                                </div>	
                        		</c:when>
                        		
                        		<c:when test="${ not empty listaBuscarImoveis }">
                        			<div class="pull-left" style="    margin-top: 7px;">
	                                    <span class="meta-level"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: ${quantTotalImoveis} </span>
	                                </div>
	                                <div class="pull-right" >
	                                      <form:form method="POST" id="imovelBuscarImoveisForm" modelAttribute="imovelForm" action="${urlAdmin}/ordenaBuscarImoveis" >							                        	
						                        	<form:select id="opcaoOrdenacao" path="opcaoOrdenacao" class="form-control">                                
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
	                                    <form:form method="POST" id="imovelBuscarImoveisMapaForm" modelAttribute="imovelForm" action="${urlAdmin}/buscarImovelMapa" >
	                                     	<button type="submit" class="btn btn-primary btn-block"><spring:message code="lbl.btn.ver.mapa.imovel"/></button>
	                                     </form:form>  
	                                </div><!-- /.pull-left -->
	                                
	                                <c:if test="${imovelForm.isVisible() }">
		                                	<div class="pull-right" style="padding-right:20px;">
			                                    <form:form method="POST" id="imovelBuscarImoveisPageForm" modelAttribute="imovelForm" action="${urlAdmin}/buscarImovel" >
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
	                                                <a href="${urlAdmin}/detalhesImovel/${imovel.id}" >
	                                                   <span class="meta-provider ${imovel.classePorAcao}" style="font-size:19px;">${imovel.acaoFmt} <br>
	                                                   							<strong>  R$<fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
	                                                   </span><br>                                                   
	                                                    <img src="${context}${imovel.imagemArquivo}" class="img-responsive" style="width: 260px; height: 240px; alt="admin"/>
	                                                </a>
	                                            </div>
	                                            <div class="media-body">
	                                                <span class="label pull-right" style="background-color: #03A9F4; font-size: 12px">${imovel.tipoImovelFmt}</span>
	                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlAdmin}/detalhesImovel/${imovel.id}" style="color : #03A9F4;">${imovel.titulo}</a></h4>
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
	                                                
	                                                <div class="col-md-7">
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
	                                                                <td class="text-right">${imovel.quantGaragem} vaga(s)</td>
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
         
         <!-- Start optional size modal element - comparativo de imoveis -->
            <div id="idModalConfirmarComparativo" class="modal fade bs-example-modal-lg-comparativo" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.comparativo"/></h4>
                        </div>
                        <div class="modal-body">
                            <p><div id="msgModalComparativo" cssClass="errorEntrada"  ></div>   </p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>                                                        
                        </div>						
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout-admin/head-bootstrap.jsp"></c:import>  
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->
