<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/mensagem" var="urlMensagem"/>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>
<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioEnum"%>

<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioEnum.values() %>"/>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   

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
    			$("#buscarUsuariosForm").submit();      
    		 });    		

    	   function limpaComboLinha(comboLinha) {
    		    $(comboLinha).empty();  
    		    $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');
    		    $(comboLinha).trigger("chosen:updated");
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
			
			var parametro1 = id;
		    $.ajax({        
		        url: '${urlContato}/enviarConvite/' + parametro1,        
		        success: function(){
		        	$("#idConvite_"+id).hide();    	
		        	$("#idConvidado_"+id).show();      
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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.link.busca.pref.imoveis"/> </h2>                                                                        
					
					<!-- Start header modal Ajuda - funcionalidade -->
						<c:import url="../ajuda/headerMenuModal.jsp"></c:import>																				
					<!-- End header  modal Ajuda - funcionalidade -->	
					
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                    <div class="row">
                    	<% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
							<c:import url="../../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        <div class="col-xs-5 col-sm-4">                        		

                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title panel-titulo"><spring:message code="lbl.filtro.geral"/></h3>
                                    </div><!-- /.pull-left -->
                                    <div class="pull-right">
                                        <label><strong> <spring:message code="lbl.quant.total.usuarios"/> </strong>: (${usuarioForm.quantRegistros}) </label>
                                    </div><!-- /.pull-right -->                                    
                                    
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                    <form:form method="POST" id="formBuscarUsuarios" modelAttribute="usuarioForm" action="${urlUsuario}/buscarUsuariosPreferenciaImoveis" >
                                    
                                    <ul class="list-group">
								          <li class="list-group-item">
								            <span class="label label-default"><spring:message code="lbl.estado"/> </span>
								            <spring:message code="lbl.hint.usuario.estado" var="hintEstado"/>
								            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" title="${hintEstado}">                                
													<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${usuarioForm.listaEstados}" itemValue="key" itemLabel="label"/>
											  </form:select>										   	  
										   <br> 	  
										   
											<span class="label label-default"><spring:message code="lbl.cidade"/> </span>
											<spring:message code="lbl.hint.usuario.cidade" var="hintCidade"/>
								            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;" title="${hintCidade}">                                
												<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:options items="${usuarioForm.listaCidades}" itemValue="key" itemLabel="label"/>
										  </form:select>										  
										   <br>
										   
										   <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
										   <spring:message code="lbl.hint.usuario.bairro" var="hintBairro"/>
								             <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintBairro}">                                
												<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
												<form:options items="${usuarioForm.listaBairros}" itemValue="key" itemLabel="label"/>
										  </form:select>											   
										   <br>
										
							         	 <span class="label label-default"><spring:message code="lbl.acao.imovel"/></span>
							         	 <spring:message code="lbl.hint.lbl.hint.usuario.perfil.usuario" var="hintPerfilUsuario"/>
								            <form:select id="perfil" path="perfil" class="chosen-select" tabindex="-1" style="display: none;" title="${hintPerfilUsuario}">                                
						                       <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
											   <form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
						                  </form:select>						                  
						                  										  
										 <br>											  
											<div class="pull-right">
												<button type="submit" class="btn btn-sm btn-primary btn-xs btn-push" ><i class="fa fa-eye"></i> <spring:message code="lbl.filtrar.geral"/></button>
											</div><!-- /.pull-right -->                                    												   
										 <br>
								         
								         </li>
								         
								         <li class="list-group-item">
									         	
									         	<span class="label label-default"><spring:message code="lbl.buscar.imovel.quartos.dormitorios"/> </span>
									         			<spring:message code="lbl.hint.imovel.quant.quartos.pref" var="hintQuantQuartos"/>								            			
											            <form:select id="qtdQuartos" path="qtdQuartos" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantQuartos}">                                
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
										         <spring:message code="lbl.hint.imovel.quant.garagem.pref" var="hintQuantGaragem"/>
											             <form:select id="qtdGaragem" path="qtdGaragem" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantGaragem}">                                
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
										         <spring:message code="lbl.hint.imovel.quant.suites.pref" var="hintQuantSuites"/>
											            <form:select id="qtdSuites" path="qtdSuites" class="chosen-select" tabindex="-1" style="display: none;" title="${hintQuantSuites}">                                
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
													    <spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>
														<button type="submit" class="btn btn-sm btn-primary btn-xs btn-push" title="${hintBtnFiltro}"><i class="fa fa-eye"></i> <spring:message code="lbl.filtrar.geral"/></button>
													  </div><!-- /.pull-right -->                                    												   
												 <br> 		
									         </li>								         								         
                               		</ul>
                                    </form:form>                                        
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->
                        </div>      
					                    
                        <div class="col-lg-8 col-md-9 col-sm-9"> 
                        	<c:if test="${ not empty listaBuscaUsuarios }">
	                        	<div class="panel rounded shadow">                         
	                           	  <div class="panel-heading">
	                                    <div class="pull-left col-sm-5">	                                        
                                            
	                                    </div><!-- /.pull-left -->
	                                    <div class="pull-right col-sm-5">	
	                                    	  <spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenacao"/>                                    	 
							                  <form:form method="POST" id="buscarUsuariosForm" modelAttribute="usuarioForm" action="${urlUsuario}/ordenarBuscarUsuariosPreferenciaImoveis" >         		      	
						                        	<form:select id="opcaoOrdenacao" path="opcaoOrdenacao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintOrdenacao}">                                
								                        <form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>
								                        <form:option value="maiorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.usuario.cad.mais.recente"/></form:option>
														<form:option value="menorDataCadastrado" ><spring:message code="lbl.opcao.ordenacao.usuario.cad.menos.recente"/></form:option>
														<form:option value="nomeImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.usuario.nome.crescente"/></form:option>
														<form:option value="nomeImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.usuario.nome.decrescente"/></form:option>
								                  </form:select>
								              </form:form> 	                                        
	                                    </div><!-- /.pull-right -->
	                                    <div class="clearfix"></div>
	                                </div><!-- /.panel-heading -->
	                                <div class="panel-body no-padding">
										<c:forEach var="usuarioBusca" items="${listaBuscaUsuarios}" varStatus="item">	                                
											<div class="media inner-all mt-10">
			                                   <div class="media-left">
			                                        <a href="#">                                            
			                                            <img src="${context}/${usuarioBusca.imagemArquivo}" style="width: 230px; height: 150px; " alt="admin"/>	                                            
			                                        </a>
			                                    </div>
			                                  <div class="media-body">
			                                  	  <h4 class="media-heading"><a href="#">${usuarioBusca.nome}</a></h4>
	                                        	  <p><strong>${usuarioBusca.bairro} - ${usuarioBusca.cidade} - ${usuarioBusca.estado}</strong></p>	                                        	  
	                                        	  <div class="meta-search">
			                                        <label><spring:message code="lbl.data.cadastro.usuario"/>: </label> <small><fmt:formatDate value='${usuarioBusca.dataCadastro}' pattern='dd/MM/yyyy'/></small> </br>
			                                        <label><spring:message code="lbl.perfil.usuario"/>:</label> <small> ${usuarioBusca.perfilFmt}  </small> </br>
			                                        <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%> 
														<button type="button" class="btn btn-sm btn-primary btn-xs btn-push" data-toggle="modal" data-target=".bs-example-modal-form"><i class="fa fa-eye"></i> Detalhes</button>
														<c:if test="${((usuarioBusca.isContato == 'N') && (usuarioBusca.id != usuario.id))}">
															<button id="idConvite_${usuarioBusca.id}"  onClick="enviarConvite(${usuarioBusca.id})" type="button" class="btn btn-sm btn-primary btn-xs btn-push" ><i class="fa fa-circle-thin"></i> <spring:message code="lbl.enviar.convite"/></button>
														</c:if>
														
														<c:if test="${((usuarioBusca.isContato == 'C') && (usuarioBusca.id != usuario.id))} ">
															<button type="button" class="btn btn-sm btn-primary btn-xs btn-push" ><i class="fa fa-check-circle-o"></i> <spring:message code="lbl.enviado.convite"/></button>
														</c:if>	

														<c:if test="${usuarioBusca.id != usuario.id}">
															<button id="idConvidado_${usuarioBusca.id}" type="button" class="btn btn-sm btn-primary btn-xs btn-push" style="display: none;"><i class="fa fa-check-circle-o"></i> <spring:message code="lbl.enviado.convite"/></button>
															<a href="${urlMensagem}/prepararEnviarMensagem/${usuarioBusca.id}" class="btn btn-sm btn-primary btn-xs btn-push" ><i class="fa fa-envelope-o"></i> <spring:message code="lbl.enviar.mensagem"/></a>
														</c:if>			           
													<% } %>					
			                                                                                                                                                      
	                                          </div>		                                          
			                                  </div><!-- /.media-body -->
			                              </div><!-- /.media -->
		                              	  <div class="line"></div>
		                              	  <div class="line"></div>
	            					  </c:forEach>	
	                                </div><!-- /.panel-body -->
	                            </div>
                            </c:if>
                            <c:if test="${ empty listaBuscaUsuarios }">
                            	<div class="panel">	                                
	                                <div class="panel-body">
	                                	<spring:message code="lbl.rel.nenhum.registro"/>
	                                </div><!-- /.panel-body -->
	                            </div><!-- /.panel -->                            
                            </c:if>
                                                        
                        </div>                        
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
 
            </section><!-- /#page-content -->
     
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->

        </section><!-- /#wrapper -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>