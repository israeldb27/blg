<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/imovelComparativo" var="urlImovelComparativo"/>
<spring:url var="urlImovelComentario" value="/imovelComentario"/>
<spring:url value="/imovel/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/imovel/buscarBairros" var="urlBuscarBairros"/>
<spring:url var="urlImovel" value="/imovel"/>
<spring:url value="/usuario" var="urlUsuario"/>

<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>

<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>

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
	$("#comentariosSobreMeusImoveisForm").submit();      
 });    		

   function limpaComboLinha(comboLinha) {
       $(comboLinha).empty();
       $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');        
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

function desmarcarCheck(id) {
    $.post("${urlImovelComentario}/desmarcarCheck", {'idImovelcomentario' : id}, function() { 
      $("#idCheckImovelDiv_"+id).hide();
    	$("#idCheckImovel_"+id).hide();
    });
  }
  
function adicionarComparativo(id) {    		
	var parametro1 = id;
    $.ajax({        
		 url: '${urlImovelComparativo}/adicionarImovelComparativo/' + parametro1,
		 dataType: 'json',
		 success: function(data){				 
			 if ( data == 'ok') {
				 $('#msgModalComparativo').html("<spring:message code='lbl.msg.sucesso.add.comparativo'/>");
				 $("#idModalConfirmarComparativo").modal("show");	
       	 }
       	 else  {
       		$("#idModalConfirmarComparativo").modal("show"); 
       		$('#msgModalComparativo').html(data);
	         }     	    
		 },
		 error: function(jqXHR, textStatus, errorThrown) {				 
			 $('#msgModalComparativo').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
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
                    <h2><i class="fa fa-commenting"></i> <spring:message code="lbl.title.link.comentarios.imoveis"/> <span><spring:message code="lbl.title.link.comentarios.imoveis.recebidos"/></span> </h2>                                   
                </div><!-- /.header-content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                     <div class="row">                  		 
                        <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
                        	<c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>                           
                             
                        <div class="col-lg-3 col-md-3 col-sm-4">                 		
                                <div class="panel-body no-padding">
                                    <form:form method="POST" id="imovelComentarioForm" modelAttribute="imovelComentarioForm" action="${urlImovelComentario}/filtrar" >	
                                       
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
		                                       <div class="clearfix"></div>
		                                 
		                                    <div class="panel-body">
		                                    		</br>	                                    		
		                                        	<spring:message code="lbl.hint.imovel.estado" var="hintEstado"/>  
		                                        	<span class="label label-default"><spring:message code="lbl.estado"/> </span>
		                                            <form:select id="idEstado" path="idEstado" class="chosen-select" tabindex="-1" style="display: none;" title="${hintEstado}">
		                                                    <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
		                                                    <form:options items="${imovelComentarioForm.listaEstados}" itemValue="key" itemLabel="label"/>
		                                            </form:select>
		                                            </br> </br>
		                                            <span class="label label-default"><spring:message code="lbl.cidade"/> </span>
		                                            <spring:message code="lbl.hint.imovel.cidade" var="hintCidade"/>	
		                                            <form:select id="idCidade" path="idCidade" class="chosen-select" tabindex="-1" style="display: none;" title="${hintCidade}">
		                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
		                                                <form:options items="${imovelComentarioForm.listaCidades}" itemValue="key" itemLabel="label"/>
		                                            </form:select>
		                                            </br> </br>
		                                            <span class="label label-default"><spring:message code="lbl.bairro"/> </span>
		                                            <spring:message code="lbl.hint.imovel.bairro" var="hintBairro"/>
		                                            <form:select id="idBairro" path="idBairro" class="chosen-select" tabindex="-1" style="display: none;" title="${hintBairro}">
		                                                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
		                                                <form:options items="${imovelComentarioForm.listaBairros}" itemValue="key" itemLabel="label"/>
		                                            </form:select>
		                                            <br>											  
		                                            	<spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>
														  <div class="pull-right">
															<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
														  </div><!-- /.pull-right --> 
		                                            <br>		                                        
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
										        <br> 
										      
										      <spring:message code="lbl.hint.aplicar.filtro" var="hintBtnFiltro"/>
								             	  <div class="pull-right">
														<button type="submit" class="btn btn-sm btn-primary btn-lg btn-expand" title="${hintBtnFiltro}"> <spring:message code="lbl.filtrar.geral"/></button>
											  </div><!-- /.pull-right -->            												   
													
		                                    </div><!-- /.panel-body -->
		                                </div>
                                       
                                    </form:form>        
                                    
                                </div><!-- /.panel-body -->
                        </div> 
                                             
					    <div class="col-lg-9 col-md-9 col-sm-8">
					    	<c:choose>
					    		<c:when test="${ empty listaComentariosRecebidos }">
					    			<div class="callout callout-warning">
	                                    <strong><spring:message code="msg.nenhum.comentario.existente.imovel"/></strong>                              
	                                </div>
					    		</c:when>
					    		
					    		<c:when test="${not empty listaComentariosRecebidos }">
					    				 <div class="pull-left col-lg-4" style="padding-top: 9px;">                        				
			                                 <span class="meta-level" style="font-size: 16px;"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: </span> &nbsp; ${imovelComentarioForm.quantRegistros}  
			                            </div>					    				
		                                <div class="pull-right" >
		                                		
		                                </div><!-- /.pull-right -->
		                                <div class="pull-right" style="padding-right:10px;">
		                                	<spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenar"/> 
							                   <form:form method="POST" id="comentariosSobreMeusImoveisForm" modelAttribute="imovelComentarioForm" action="${urlImovelComentario}/ordenar" >
							              		   <form:hidden  path="tipoLista" value="comentariosSobreMeusImoveis" />
							                        	<form:select id="opcaoOrdenacao1" path="opcaoOrdenacao" class="form-control" title="${hintOrdenar}">                                
									                         <form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>                      
															 <form:option value="maiorDataComentario" ><spring:message code="lbl.opcao.ordenacao.imovel.mais.recente"/></form:option>
															 <form:option value="menorDataComentario" ><spring:message code="lbl.opcao.ordenacao.imovel.mais.recente"/></form:option>
															 <form:option value="tituloImovelCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.crescente"/></form:option>
															 <form:option value="tituloImovelDeCrescente" ><spring:message code="lbl.opcao.ordenacao.imovel.titulo.decrescente"/></form:option>
															 <form:option value="maiorValorImovel" ><spring:message code="lbl.opcao.ordenacao.imovel.maior.valor.imovel"/></form:option>
															 <form:option value="menorValorImovel" ><spring:message code="lbl.opcao.ordenacao.imovel.menor.valor.imovel"/></form:option>										
									                  </form:select>                                       								                   
								              </form:form>		 	                                        	
		                                </div><!-- /.pull-left -->
		                                <c:if test="${imovelComentarioForm.isVisible() }">
			                                  <div class="pull-right" style="padding-right:20px;">
			                                    <form:form method="POST" id="imovelComentarioPageForm" modelAttribute="imovelComentarioForm" action="${urlImovelComentario}/paginarFiltrar" >
			                                     		<spring:message code="lbl.hint.opcao.paginacao" var="hintPaginacao"/>
		                                                <form:select id="opcaoPaginacao" path="opcaoPaginacao" class="form-control" title="${hintPaginacao}">
		                                                    <form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
		                                                    <form:options items="${imovelComentarioForm.listaPaginas}" itemValue="key" itemLabel="label"/>	                                                    	                                                    
		                                              </form:select>
			                                      </form:form>
			                                </div><!-- /.pull-left -->
		                                </c:if>
		                                <div class="clearfix"></div>
		
		                                <div class="media-list list-search">
		                                 		<c:forEach var="imovelComentario" items="${listaComentariosRecebidos}" varStatus="item">
		                                        <div class="media rounded shadow no-overflow">
		                                            <div class="media-left">
		                                                <a href="${urlImovel}/detalhesImovel/${imovelComentario.imovel.id}" >
		                                                   <span class="meta-provider" style="font-size:19px;">${imovelComentario.imovel.acaoFmt} <br>
		                                                   							<strong>  R$<fmt:formatNumber value="${imovelComentario.imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
		                                                   </span><br>                                                   
		                                                    <img src="${context}${imovelComentario.imovel.imagemArquivo}" class="img-responsive" style="width: 260px; height: 225px; alt="admin"/>
		                                                </a>
		                                            </div>
		                                            <div class="media-body">
		                                                <span class="label pull-right" style="background-color: #03A9F4; font-size: 12px">${imovelComentario.imovel.tipoImovelFmt}</span>
		                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlImovel}/detalhesImovel/${imovelComentario.imovel.id}" style="color : #03A9F4;">${imovelComentario.imovel.titulo}</a></h4>
		                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovelComentario.imovel.endereco} - ${imovelComentario.imovel.bairro} - ${imovelComentario.imovel.cidade} -${imovelComentario.imovel.uf} </h5>
		                                                
		                                                <div class="col-md-4" >                                                    
		                                                    
		                                                 	<div class="media-body" >
		                                                 			<br> <br>                                       
						                                            <em class="text-sm text-muted" ><font style="font-size:13px; font-style: normal;"><spring:message code="lbl.usuario.comentario" />: </font><span class="text-success"></span></em> </br>
						                                            <a href="${urlUsuario}/detalhesUsuario/${imovelComentario.usuarioComentario.id}">
						                                            	<img src="${context}${imovelComentario.usuarioComentario.imagemArquivo}" class="img-responsive" style="width: 60px; height: 65px; alt="admin"/>
						                                            </a>	                                            
					                                        </div>		                                              		
		                                                                                                   
		                                                </div>
		                                                
		                                                <div class="col-md-8">
		                                                    <table class="table table-condensed">
		                                                        <tbody style="font-size: 13px;">
		                                                        	<tr>
		                                                                <td class="text-left"><spring:message code="lbl.data.ult.comentario"/></td>
		                                                                <td class="text-right"><fmt:formatDate value='${imovelComentario.dataComentario}' pattern='dd/MM/yyyy'/></td>
		                                                            </tr>
		                                                   		                                                           
		                                                            <tr>
		                                                                <td class="text-left"><spring:message code="lbl.descricao.comentario.ultimo"/></td>
		                                                                <td class="text-right">${imovelComentario.comentarioResumido} </td>
		                                                            </tr>
		                                                            
		                                                            <tr>
		                                                                <td class="text-left"><spring:message code="lbl.total.comentarios"/></td>
		                                                                <td class="text-right">${imovelComentario.totalComentarios} </td>
		                                                            </tr>		                                                            	               
		                                                        </tbody>
		                                                    </table>
		                                                    
		                                                    <br>
		                                                    
		                                                    <% if ( request.getSession().getAttribute("acessoValido").equals("S") ) {%>
		                                                 			<spring:message code="lbl.link.lista.todos.comentarios" var="mensagemListaComentarios"/>
		                                                 			<a href="${urlImovelComentario}/visualizarTodosComentariosImovel/${imovelComentario.imovel.id}" style="font-size:x-large; color: rgb(99, 110, 123);"  class="dropdown-toggle" ><i class="fa fa-cog"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> ${mensagemListaComentarios} </font> &nbsp;&nbsp;</i> </a>
		                                                 			                                                 	                          
																	<spring:message code="lbl.title.link.comparar" var="mensagemComparar"/>                                                 			                                                 	                                                 	
			                                                        <a href="#a" onClick="adicionarComparativo(${imovelComentario.imovel.id})" style="font-size:x-large; color: rgb(99, 110, 123);"  class="dropdown-toggle" ><i class="fa fa-eye"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> ${mensagemComparar} </font> &nbsp;&nbsp;</i></a>
					                                        <% } %>	
		                                                    
		                                                </div>
		                                            </div>
		                                        </div>
		                                    </c:forEach>  
		                                </div>	
					    		</c:when>
					    	</c:choose>					
                        </div>                 
                                              
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                
            </section><!-- /#page-content -->  
            
            	<!-- Start content modal Usuario Detalhes-->
					<c:import url="../../ajuda/imovelDetalhesModal.jsp"></c:import>																				
				<!-- End content  modal Usuario Detalhes -->         
			
				<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
				<!-- End content  modal Ajuda - funcionalidade -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->
        
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