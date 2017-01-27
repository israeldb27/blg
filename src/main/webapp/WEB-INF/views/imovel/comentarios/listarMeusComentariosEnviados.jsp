<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/imovelComparativo" var="urlImovelComparativo"/>
<spring:url var="urlImovelComentario" value="/imovelComentario"/>
<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url var="urlImovel" value="/imovel"/>

<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>

<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
    
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

$('#opcaoOrdenacao1').change(function () {				
	$("#meusComentariosForm").submit();      
 });    		

function limpaComboLinha(comboLinha) {
    $(comboLinha).empty();  
    $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');
    $(comboLinha).trigger("chosen:updated");
}	


$('#opcaoVisualizacaoMeusComentarios').change(function () {
	$("#modoVisualizarMeusComentariosForm").submit();
 });

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

function desmarcarCheck(id) {
    $.post("${urlImovelComentario}/desmarcarCheck", {'idImovelcomentario' : id}, function() {
      // selecionando o elemento html atravÃ©s da 
      // ID e alterando o HTML dele   
    	$("#idCheckImovel_"+id).hide();
    });
  }
  
</script>
		
        <c:import url="../../layout/head-layout.jsp"></c:import>
    
    <!--/ END HEAD -->
   
    <body>

        <!--[if lt IE 9]>
        <p class="upgrade-browser">Upps!! You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/" target="_blank">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-commenting"></i> <spring:message code="lbl.title.link.comentarios.imoveis"/> <span><spring:message code="lbl.title.link.comentarios.imoveis.enviados"/></span> </h2>                                    
					
					 <!-- Start header modal Ajuda - funcionalidade -->
						<c:import url="../../ajuda/headerMenuModal.jsp"></c:import>																				
					<!-- End header  modal Ajuda - funcionalidade -->

                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row"> 
                    	 <div class="col-xs-5 col-sm-4">                        		

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
                                    <form:form method="POST" id="imovelComentarioForm" modelAttribute="imovelComentarioForm" action="${urlImovelComentario}/filtrar" >
                                      <div class="form-body">
                                        <div class="form-group">
                                        	<label for="idEstado" class="col-sm-4 control-label"><spring:message code="lbl.estado"/> :</label>
                                        	<div class="col-sm-7"> 
                                        		<spring:message code="lbl.hint.imovel.estado" var="hintEstado"/>                                         	
	                                            <form:select id="idEstado" path="idEstado" class="form-control" title="${hintEstado}">                                
													<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${imovelComentarioForm.listaEstados}" itemValue="key" itemLabel="label"/>
											  </form:select>
	                                        </div>
	                                    </div>  
	                                     
	                                    <div class="form-group">
	                                        <label for="idCidade" class="col-sm-4 control-label"><spring:message code="lbl.cidade"/>:</label>
	                                        <div class="col-sm-7">
	                                        	<spring:message code="lbl.hint.imovel.cidade" var="hintCidade"/>
	                                            <form:select id="idCidade" path="idCidade" class="form-control" title="${hintCidade}">                                
													<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${imovelComentarioForm.listaCidades}" itemValue="key" itemLabel="label"/>
											  </form:select>
                                            </div>
                                        </div>     
                                        <div class="form-group">
                                            <label for="idBairro" class="col-sm-4 control-label"><spring:message code="lbl.bairro"/>:</label>
	                                        <div class="col-sm-7">
	                                        	<spring:message code="lbl.hint.imovel.bairro" var="hintBairro"/>
	                                            <form:select id="idBairro" path="idBairro" class="form-control" title="${hintBairro}">                                
													<form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${imovelComentarioForm.listaBairros}" itemValue="key" itemLabel="label"/>
											  </form:select>
                                            </div>
                                       </div>  
                                       <div class="form-group">   
                                            <label for="acao" class="col-sm-4 control-label"><spring:message code="lbl.acao.imovel"/>:</label>
	                                        <div class="col-sm-7">
	                                        	<spring:message code="lbl.hint.imovel.acao.imovel" var="hintAcaoImovel"/>
	                                            <form:select id="acao" path="acao" class="form-control" title="${hintAcaoImovel}">                                
								                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
													<form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />
								                </form:select>
								                 <label for="acao" class="error"></label>
                                                    <input type="text" class="hide" id="acao"/>
                                            </div>
                                        </div>    
                                        <div class="form-group">    
                                            <label for="tipoImovel" class="col-sm-4 control-label"><spring:message code="lbl.tipo.imovel"/>:</label>
	                                        <div class="col-sm-7">
	                                        	<spring:message code="lbl.hint.imovel.perfil.imovel" var="hintPerfilImovel"/>	
	                                            <form:select id="tipoImovel" path="tipoImovel" class="form-control" title="${hintPerfilImovel}">                                
								                        <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
														<form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />
								                 </form:select> 
								                 
                                            </div>                                           
                                         </div> 
                                         <div class="form-group">    
                                            <label for="sv_position" class="col-sm-4 control-label"></label>
	                                        <div class="col-sm-7">
	                                              <input type="text" class="hide" id="sv_position"/>								                 
                                            </div>                                           
                                         </div>                                          	
                                            
                                        </div><!-- /.form-group -->
                                        <spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>
                                        <button type="submit" class="btn btn-primary btn-block" title="${hintBtnFiltro}"><spring:message code="lbl.btn.busca.geral"/></button>
                                    </form:form>                            
                                    
                                    
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->
                        </div> 
                        <!-- /.fim Filtro -->                                          
                        <div class="col-lg-8 col-md-9 col-sm-9"> 
                        	<c:if test="${ not empty imovelComentarioForm.listaComentariosEnviados}">
	                        	<div class="panel rounded shadow">                         
	                           	  <div class="panel-heading">
	                                    <div class="pull-left col-sm-5">
	                                    		<spring:message code="lbl.hint.tipo.agrupar" var="hintAgrupar"/>
								               <form:form method="POST" id="modoVisualizarMeusComentariosForm" modelAttribute="imovelComentarioForm" action="${urlImovelComentario}/modoVisualizar" >
						              		  	<form:hidden  path="tipoLista" value="meusComentarios" /> 
						                        	<form:select id="opcaoVisualizacaocomentariosSobreMeusImoveis" path="opcaoVisualizacao"  title="${hintAgrupar}">                                
								                        <form:option value="" disabled="true"><spring:message code="lbl.agrupar.por"/></form:option>              
								                        <form:option value="agruparImoveis" ><spring:message code="lbl.agrupar.imoveis"/></form:option>   
														<form:option value="agruparUsuarios" ><spring:message code="lbl.agrupar.usuarios"/></form:option>   										
								                    </form:select>
								               </form:form>
	                                    </div><!-- /.pull-left -->
	                                    <div class="pull-right col-sm-5">   
	                                    	  <spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenar"/>
							                   <form:form method="POST" id="meusComentariosForm" modelAttribute="imovelComentarioForm" action="${urlImovelComentario}/ordenar" >
							              		   <form:hidden  path="tipoLista" value="meusComentarios" /> 
							                        	<form:select id="opcaoOrdenacao1" path="opcaoOrdenacao" title="${hintOrdenar}">                                
									                         <form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>                      
															 <form:option value="maiorDataComentario" ><spring:message code="lbl.opcao.ordenacao.imovel.mais.recente"/></form:option>
															 <form:option value="menorDataComentario" ><spring:message code="lbl.opcao.ordenacao.imovel.menos.recente"/></form:option>
															 <form:option value="tituloImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.crescente"/></form:option>
															 <form:option value="tituloImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.decrescente"/></form:option>
															 <form:option value="maiorValorImovel" ><spring:message code="lbl.opcao.ordenacao.imovel.maior.valor.imovel"/></form:option>
															 <form:option value="menorValorImovel" ><spring:message code="lbl.opcao.ordenacao.imovel.menor.valor.imovel"/></form:option>										
									                  </form:select>                                       								                   
								                   </form:form>		 	                                        
	                                    </div><!-- /.pull-right -->
	                                    <div class="clearfix"></div>
	                                </div><!-- /.panel-heading -->
	                                <div class="panel-body no-padding">
										<c:forEach var="imovel" items="${imovelComentarioForm.listaComentariosEnviados}" varStatus="item">	                                
											<div class="media inner-all mt-10">
			                                   <div class="media-left">
			                                        <a href="#">                                            
			                                            <img src="${context}/${imovel.imagemImovel}" style="width: 230px; height: 150px; " alt="admin"/>	                                            
			                                        </a>
			                                    </div>
			                                  <div class="media-body">
			                                  	  <h4 class="media-heading"><a href="#">${imovel.titulo}</a></h4>
	                                        	  <p><strong>${imovel.bairro} - ${imovel.cidade} - ${imovel.estado}</strong></p>
	                                        	  
	                                        	 <div class="meta-search">
		                                        <label><spring:message code="lbl.data.comentario"/>: </label> <small><fmt:formatDate value='${imovel.dataComentario}' pattern='dd/MM/yyyy'/></small> </br>
		                                        <label><spring:message code="lbl.usuario.comentario"/>: </label> <small>${imovel.loginUsuario}</small> </br>		                                        
		                                        <label><spring:message code="lbl.descricao.comentario"/>: </label> <small>${imovel.comentario}</small> </br>		                                        		                                        
		                                        		                                        
		                                        <button id="editButton" class="btn btn-circle btn-default btn-stroke" onclick="populaModal()" data-toggle="modal" data-target=".bs-example-modal-form" ><i class="fa fa-eye"></i></button>
		                                        <a href="#" class="btn btn-circle btn-default btn-stroke" data-toggle="modal" data-target=".bs-example-modal-sm" data-toggle="tooltip" data-placement="top" data-original-title="View edit"><i class="fa fa-times"></i></a>		                                                                                                                                                                    
	                                          </div>		                                          
			                                  </div><!-- /.media-body -->
			                              </div><!-- /.media -->
		                              	  <div class="line"></div>
		                              	  <div class="line"></div>
	            					  </c:forEach>	
	                                </div><!-- /.panel-body -->
	                            </div>
                            </c:if>
                            <c:if test="${ empty imovelComentarioForm.listaComentariosEnviados }">
                            	<div class="panel">	                                
	                                <div class="panel-body">
	                                	<spring:message code="msg.nenhum.comentario.existente.imovel"/>	                                    
	                                </div><!-- /.panel-body -->
	                            </div><!-- /.panel -->                            
                            </c:if>
                                                        
                        </div>                        
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
                
                <!-- Fim Meus Imoveis -->
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->

          
            <!--/ END SIDEBAR RIGHT -->
             <!-- Start inside form layout -->
             
            <div class="modal fade bs-example-modal-form" tabindex="-1" role="dialog" aria-hidden="true">
              
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.detalhes.imovel"/></h4> 
                        </div>
                        <div class="modal-body no-padding">
                            <form name="userForm" class="form-horizontal form-bordered" role="form">
                                <div class="form-body">
                                	<div class="form-group">                                	
                                        <label for="idTituloImovelSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.titulo.imovel"/>: </label>
                                        <div class="col-sm-7">
                                            
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="idTipoImovel" class="col-sm-3 control-label"><spring:message code="lbl.tipo.imovel"/>: </label>
                                        <div class="col-sm-7">
                                            Apto
                                        </div>
                                    </div><!-- /.form-group -->
                                	
                                    <div class="form-group">
                                        <label for="idEstadoSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.estado"/>: </label>
                                        <div class="col-sm-7">
                                            
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="idCidadeSelecionada" class="col-sm-3 control-label"><spring:message code="lbl.cidade"/>: </label>
                                        <div class="col-sm-7">
                                        	<div id="resposta"></div>
                                            
                                        </div>
                                    </div><!-- /.form-group -->                                    
                                    <div class="form-group">
                                        <label for="idBairroSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.bairro"/>: </label>
                                        <div class="col-sm-7">
                                            ${imovel.dataCadastro}
                                        </div>
                                    </div><!-- /.form-group -->
                                     <div class="form-group">
                                        <label for="idDataCadastro" class="col-sm-3 control-label"><spring:message code="lbl.data.cadastro.imovel"/>: </label>
                                        <div class="col-sm-7">
                                            <div class="elemento"></div>
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="idAcao" class="col-sm-3 control-label"><spring:message code="lbl.acao.imovel"/>: </label>
                                        <div class="col-sm-7">
                                             <input type="text" class="form-control input-sm" id="firstname-1" >
                                        </div>
                                    </div><!-- /.form-group -->
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-success">Sign in</button>
                                    </div>
                                </div><!-- /.form-footer -->
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->               
            </div><!-- /.modal -->            
            <!--/ End inside form layout -->            
 
				<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
				<!-- End content  modal Ajuda - funcionalidade -->
            
			

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

    </body>
    <!--/ END BODY -->
