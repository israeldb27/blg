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
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>

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
    		
    		 $('#opcaoPaginacao').change(function () {				
   				$("#meusImoveisPageForm").submit();      
   			 });

    		$('#opcaoOrdenacao').change(function () {				
    			$("#imoveisPerfilUsuario").submit();      
    		 });    		

    	    function limpaComboLinha(comboLinha) {
    	        $(comboLinha).empty();
    	        $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');        
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.imoveis.perfil.usuario"/> </h2>				
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">                			

                    <div class="row">
                         <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
                            <c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        
                        <div class="col-lg-13 col-md-13 col-sm-13">
							<div class="panel rounded shadow">
								<div class="panel-heading" align="center">
    								<span class="label pull-left"  style="font-size: 18px;margin-bottom:10px;background-color: black;font-size: 100%;">${usuarioForm.perfilFmt}</span>                                    
    								<span class="label pull-right" style="margin-left: 14px;color:gray; font-size: 12px;">Membro desde <fmt:formatDate value='${usuarioForm.dataCadastro}' pattern='dd/MM/yyyy'/></span>
									<h2 class="text-bold" style="margin-top: 0px;margin-bottom: 5px;width: 50%;"> <a href="${urlUsuario}/detalhesUsuario/${usuarioForm.id}" >${usuarioForm.nome}  </a> </h2>
									<h5 style="margin-top: 4px;margin-bottom: 0px;width: 50%;">${usuarioForm.cidade} - ${usuarioForm.estado}</h5>
									
                                    <div class="pull-right">
                                    	<c:choose>
											<c:when test="${usuario.id == usuarioForm.id}">
												<a href="${urlImovel}/listarMeusImoveis" style="font-size:x-large;" class="meta-action" title='<spring:message code="lbl.title.link.meus.imoveis"/>'> <i class="fa fa-home pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.title.link.meus.imoveis"/> </font> &nbsp; &nbsp;</i> </a>
												
												<c:if test="${usuario.perfil == 'P' }">
													<a href="${urlPreferencia}/listarPreferencias" style="font-size:x-large;" class="meta-action" title='<spring:message code="lbl.title.link.pref.imoveis"/> '><i class="fa fa-bookmark pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.title.link.pref.imoveis"/> </font> &nbsp; &nbsp;</i></a>																	
											     </c:if>
												<a href="${urlNota}/minhasNotas" style="font-size:x-large;" class="meta-action" title='<spring:message code="lbl.title.link.minhas.notas"/> '><i class="fa fa-sticky-note pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.title.link.minhas.notas"/> </font></i></a>																	
											</c:when>
											
											<c:when test="${usuario.id != usuarioForm.id}">
												<a href="${urlImovel}/visualizarImoveisPerfilUsuario/${usuarioForm.id}" style="font-size:x-large;" class="meta-action" title='<spring:message code="lbl.visualizar.imoveis.perfil.usuario"/>' ><i class="fa fa-home pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.visualizar.imoveis.perfil.usuario"/> </font> &nbsp; &nbsp;</i></a>  

												<c:choose>													
													<c:when test="${usuarioForm.possuiContatoUsuarioSessao == 'S'}"> <!-- usuarios tem contato entao o usuarioSessao pode pedir para cancelar contato (e futuramente bloquear contato) -->
														<a href="#" id="idCancelarContato" onclick="cancelarContato(${usuarioForm.id})" style="font-size:x-large; " class="meta-action" title='<spring:message code="lbl.canceler.contato"/>' ><i class="fa fa-user-times pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.canceler.contato"/> </font> &nbsp; &nbsp;</i></a>
														
														<a href="#" id="idEnviarConvite" onclick="enviarConvite(${usuarioForm.id})" style="font-size:x-large; display: none;" class="meta-action" title='<spring:message code="lbl.enviar.convite"/>' ><i class="fa fa-user-plus pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.enviar.convite"/> </font>&nbsp; &nbsp; </i></a>
														<a href="#" id="idCancelarConvite" onclick="cancelarConvite(${usuarioForm.id})" style="font-size:x-large; display: none;" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.canceler.enviar.convite"/> </font> &nbsp; &nbsp;</i></a>
													</c:when>
													
													<c:when test="${usuarioForm.possuiContatoUsuarioSessao == 'C'}"> <!-- usuarioSessao apenas enviou convite, mas nao teve resposta do usuarioConvidado entao ele pode cancelar o convite -->
														<a href="#" id="idCancelarConvite" onclick="cancelarConvite(${usuarioForm.id})" style="font-size:x-large; " class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.canceler.enviar.convite"/> </font> &nbsp; &nbsp;</i></a>	
														<a href="#" id="idEnviarConvite" onclick="enviarConvite(${usuarioForm.id})" style="font-size:x-large; display: none;" class="meta-action" title='<spring:message code="lbl.enviar.convite"/>' ><i class="fa fa-user-plus pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.enviar.convite"/> </font> &nbsp; &nbsp;</i></a>													
													</c:when>
													
													<c:when test="${usuarioForm.possuiContatoUsuarioSessao == 'N'}"> <!-- usuarioSessao nem sequer mandou um convite para o usuarioConvidado entao ele pode enviar um convite -->
														<a href="#" id="idEnviarConvite" onclick="enviarConvite(${usuarioForm.id})" style="font-size:x-large;" class="meta-action" title='<spring:message code="lbl.enviar.convite"/>' ><i class="fa fa-user-plus pull-right" style="color:gray"><font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"><spring:message code="lbl.enviar.convite"/> </font></i></a>
														<a href="#" id="idCancelarConvite" onclick="cancelarConvite(${usuarioForm.id})" style="font-size:x-large; display: none;" class="meta-action" title='<spring:message code="lbl.canceler.enviar.convite"/>' ><i class="fa fa-user-times pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.canceler.enviar.convite"/> </font> &nbsp; &nbsp;</i></a>
													</c:when>													
												</c:choose>
												
												<a href="${urlMensagem}/maisMensagens/${usuarioForm.id}" style="font-size:x-large;" class="meta-action" title='<spring:message code="lbl.enviar.mensagem"/>' ><i class="fa fa-envelope-o pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.enviar.mensagem"/>  </font> &nbsp; &nbsp;</i></a>   
											</c:when>
										</c:choose>
                                    </div>
                                    
                                    <br> <br>                                    

								</div>
								<div class="panel-body">
									<div class="pull-left">
										<div class="col-lg-6 col-md-6 col-sm-6"> 
	                                        <div id="owl-demo" class="owl-carousel owl-theme">    
	                                        	<a href="${urlUsuario}/detalhesUsuario/${usuarioForm.id}" >  
	                                        		<img class="img-circle" src="${context}/${usuarioForm.imagemArquivo}" style="margin-left:350px; width: 240px; height: 240px; ">
	                                        	</a>                                            	                                            	                                                                                        
	                                        </div>
	                                    </div>
                                    </div>
                                	
                                	<div class="pull-right">
                                		 <table class="table table-striped" style="margin-top:10px; font-size: 13px; width: 400px;">
                                         <tbody>
                                         
                                          <c:if test="${((usuarioForm.perfil == 'P') || (usuarioForm.perfil == 'C')) }">
                                          	 <tr>
                                                 <td class="text-left"><spring:message code="lbl.cpf"/></td>
                                                 <td class="text-right">${usuario.cpf}</td>
                                              </tr>                                                     
                                     	 </c:if>
                                        	 
		                                 <c:if test="${usuarioForm.perfil == 'I'}">
		                                 	  <tr>
                                                  <td class="text-left"><spring:message code="lbl.cnpj"/></td>
                                                  <td class="text-right">${usuario.cpf}</td>
                                               </tr>
	                 					</c:if>
	                 
                                          <c:if test="${usuarioForm.perfil == 'C'}">
                                          	   <tr>
                                                  <td class="text-left"><spring:message code="lbl.creci"/></td>
                                                  <td class="text-right">${usuarioForm.creci}</td>
                                              </tr>
                           				  </c:if>
                           
                           				  <c:if test="${usuarioForm.perfil == 'C'}">
                                          	   <tr>
                                                  <td class="text-left"><spring:message code="lbl.creci"/></td>
                                                  <td class="text-right">${usuarioForm.creci}</td>
                                              </tr>
                           				 </c:if>
                                         
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.imoveis"/></td>
                                                 <td class="text-right">${usuarioForm.quantTotalImoveis}</td>
                                             </tr>
                                             
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.contato"/></td>
                                                 <td class="text-right">${usuarioForm.quantTotalContatos}</td>
                                             </tr>
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.parcerias"/></td>
                                                 <td class="text-right">${usuarioForm.quantTotalParcerias}</td>
                                             </tr>
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.interessados"/></td>
                                                 <td class="text-right">${usuarioForm.quantTotalInteressadosImoveis}</td>
                                             </tr>
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.intermediacoes" /></td>
                                                 <td class="text-right">${usuarioForm.quantTotalIntermediacoes}</td>
                                             </tr>
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.visualizacoes" /></td>
                                                 <td class="text-right">${usuarioForm.quantTotalVisitasImoveis}</td>
                                             </tr>
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.total.aprovacoes.usuario" /></td>
                                                 <td class="text-right">${usuarioForm.quantTotalAprovacoesUsuario}</td>
                                             </tr>
                                         </tbody>

                                     </table>	
                                	</div>                                                        
                                 
							</div><!-- /.panel -->
						</div>
					</div>
                        
                        <div class="col-lg-3 col-md-3 col-sm-4">
                            <form:form method="POST" id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/filtrarImoveisPerfilUsuario" >
                            
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
								              <form:select id="tipoImovel" path="tipoImovel" class="chosen-select" tabindex="-1" style="display: none;" title="${hintTipoImovel}">                                
							                        <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                        <form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />								                        
							                 </form:select>	
									     <br> <br>
									     
								     	<span class="label label-default"><spring:message code="lbl.acao.imovel"/> </span>
								     		  <spring:message code="lbl.hint.imovel.acao.imovel" var="hintAcaoImovel"/>	
								              <form:select id="acao" path="acao" class="form-control" title="${hintAcaoImovel}">                                
							                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
							                    <form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />								                    
							                </form:select>
								        <br> <br>
								           
							            <span class="label label-default"><spring:message code="lbl.buscar.imovel.status.imovel"/> </span>
							            	  <spring:message code="lbl.hint.imovel.perfil.imovel" var="hintPerfilImovel"/>	
								              <form:select id="perfilImovel" path="perfilImovel" class="form-control" title="${hintPerfilImovel}">                                
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
								            <form:select id="quantQuartos" path="quantQuartos" class="form-control" title="${hintQuantQuartos}">                                
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
								             <spring:message code="lbl.hint.imovel.quant.garagem" var="hintQuantGaragem"/>
								             <form:select id="quantGaragem" path="quantGaragem" class="form-control" title="${hintQuantGaragem}">                                
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
								             <spring:message code="lbl.hint.imovel.quant.banheiros" var="hintQuantBanheiros"/>
								             <form:select id="quantBanheiro" path="quantBanheiro" class="form-control" title="${hintQuantBanheiros}">                                
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
								            <spring:message code="lbl.hint.imovel.quant.suites" var="hintQuantSuites"/>
								            <form:select id="quantSuites" path="quantSuites" class="form-control" title="${hintQuantSuites}">                                
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
                      
                            </form:form>
                        </div>
                        <div class="col-lg-9 col-md-9 col-sm-8">
                            <c:if test="${ empty listaImoveisPerfilUsuario }">
                                <div class="callout callout-warning">
                                    <strong>Ooops! Não encontramos resultados para esta busca!</strong>
                                    <p>Faça alguns ajustes nos filtros e tente novamente.
                                </div>
                            </c:if>
                            <c:if test="${ not empty listaImoveisPerfilUsuario }">
                                <div class="pull-left" style="    margin-top: 7px;">
                                    <span class="meta-level"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: ${imovelForm.quantRegistros} </span>
                                </div>
                                <div class="pull-right" >
                                	  <spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenar"/>	
                                      <form:form method="POST" id="imoveisPerfilUsuario" modelAttribute="imovelForm" action="${urlImovel}/ordenarImoveisPerfilUsuario" >							                        	
					                        	<form:select id="opcaoOrdenacao" path="opcaoOrdenacao" class="form-control" title="${hintOrdenar}">                                
							                        <form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>                      
							                        <form:option value="maiorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.imovel.mais.recente"/></form:option>
													<form:option value="menorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.imovel.mais.recente"/></form:option>
													<form:option value="tituloImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.crescente"/></form:option>
													<form:option value="tituloImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.decrescente"/></form:option>
													<form:option value="maiorValor" ><spring:message code="lbl.opcao.ordenacao.imovel.maior.valor.imovel"/></form:option>
													<form:option value="menorValor" ><spring:message code="lbl.opcao.ordenacao.imovel.menor.valor.imovel"/></form:option>
							                  </form:select>							                        
					                  </form:form>	
                                </div><!-- /.pull-right -->
                                <div class="pull-right" style="padding-right:10px;">
                                	<spring:message code="lbl.hint.imovel.ver.mapa" var="hintMapa"/>
                                    <form:form method="POST" id="imovelBuscarImoveisMapaForm" modelAttribute="imovelForm" action="${urlImovel}/listarImoveisPerfilUsuarioPorMapa" >
                                        <button type="submit" class="btn btn-primary btn-block" title="${hintMapa}"><spring:message code="lbl.btn.ver.mapa.imovel"/></button>
                                    </form:form>
                                </div><!-- /.pull-left -->
                                
                                <c:if test="${imovelForm.isVisible() }">
	                                	<div class="pull-right" style="padding-right:20px;">
		                                    <form:form method="POST" id="meusImoveisPageForm" modelAttribute="imovelForm" action="${urlImovel}/filtrarImoveisPerfilUsuario" >
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
                                    <c:forEach var="imovel" items="${listaImoveisPerfilUsuario}" varStatus="item">
                                        <div class="media rounded shadow no-overflow">
                                        	<c:if test="${imovel.destaque == 'S'}">
                                                	<div class="ribbon-wrapper top-left">  
			                                            <div class="ribbon ribbon-shadow"><spring:message code="lbl.ribbon.imovel.destaque" /></div>
			                                        </div>
                                            </c:if>                                        	
                                            <div class="media-left">
                                                <a href="${urlImovel}/detalhesImovel/${imovel.id}" >
                                                   <span class="meta-provider ${imovel.classePorAcao}" style="font-size:19px;">${imovel.acaoFmt} <br>
                                                   							<strong>  R$<fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
                                                   </span><br>                                                   
                                                    <img src="${context}${imovel.imagemArquivo}" class="img-responsive" style="width: 260px; height: 195px; alt="admin"/>
                                                </a>
                                            </div>
                                            <div class="media-body">                                            	
                                                <span class="label pull-right" style="background-color: #03A9F4; font-size: 12px">${imovel.tipoImovelFmt}</span>                                                                                              
                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlImovel}/detalhesImovel/${imovel.id}" style="color : #03A9F4;">${imovel.titulo}</a></h4>
                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovel.endereco} - ${imovel.bairro} - ${imovel.cidade} -${imovel.uf} </h1>
                                                
                                                <div class="col-md-5" >
                                                 
                                                	<div class="media-body" >
			                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.imovel" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em>
			                                        </div>
                                                	                                                     
                                                    <br/> <br/> <br/>  
                                                    
                                                    <% if ( request.getSession().getAttribute("acessoValido").equals("S") ) {%>
                                                    	<c:if test="${(imovel.interessadoImovel == 'N') && (imovel.usuario.id != usuario.id)}">
															<a href="#a" id="idMeInteressei_${imovel.id}" onClick="adicionarInteresse(${imovel.id})" style="font-size:x-large; color: #03A9F4;" class="meta-action"><i class="fa fa-star-o" title="<spring:message code="lbl.me.interessei"/>"></i></a>
														</c:if>
														
														<c:if test="${imovel.interessadoImovel == 'S'}">
															<a href="#a" id="idNovoInteressado_${imovel.id}" onClick="retirarInteresse(${imovel.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-star" title="<spring:message code="lbl.interessado"/>"></i></a>
														</c:if>
														
														<a href="#a" id="idNovoMeInteressei_${imovel.id}" onClick="adicionarInteresse(${imovel.id})" style="font-size:x-large; color: #03A9F4; display: none;" class="meta-action"><i class="fa fa-star-o" title="<spring:message code="lbl.me.interessei"/>"></i></a>
														<a href="#a" id="idInteressado_${imovel.id}" onClick="retirarInteresse(${imovel.id})" style="font-size:x-large; display: none;" class="meta-action"><i class="fa fa-star" title="<spring:message code="lbl.interessado"/>"></i></a>
														
                                                        <a href="#a" onClick="adicionarComparativo(${imovel.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-eye" style="color: #03A9F4;" title="<spring:message code="lbl.title.link.comparar"/>"></i></a>
                                                    <% } %>
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
                                                                <td class="text-right"><c:if test="${imovel.quantQuartos > 0}">${imovel.quantQuartos}</c:if></td>
                                                            </tr>
                                                            <tr>
                                                                <td class="text-left"><spring:message code="lbl.suites"/></td>
                                                                <td class="text-right"><c:if test="${imovel.quantSuites > 0}">${imovel.quantSuites}</c:if></td>
                                                            </tr>
                                                            <tr>
                                                                <td class="text-left"><spring:message code="lbl.vagas.garagem.resum"/></td>
                                                                <td class="text-right"><c:if test="${imovel.quantGaragem > 0}">${imovel.quantGaragem}</c:if> <spring:message code="lbl.num.vagas"/></td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:if>
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
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->
