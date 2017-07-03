<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>
<spring:url value="/parceria" var="urlParceria"/>
<spring:url value="/imovel" var="urlImovel"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
<spring:url value="/mensagem" var="urlMensagem"/>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   

<script type="text/javascript">

$(document).ready(function() {

});	

function prepararModalConfirmaExclusao(id, idImovel){
	$("#modIdParametro").val(id);
	$("#modIdImovel").val(idImovel);	
	$('#msgRetornoConfirmExclusaoParamErro').html("");	
	$("#idModalConfirmacaoExclusaoParam").modal("show");	
}

function confirmarExclusaoSolParceriaAnaliseSol(){	
	var parametro = document.getElementById("modIdParametro");
	var idImovel = document.getElementById("modIdImovel");	
	$.ajax({
		 url: '${urlParceria}/confirmarExclusaoSolParceriaAnaliseSol/' + parametro.value + "/" + idImovel.value,			 
		 success: function(){				 
			 location.reload();     	    
		 },
		 error: function(jqXHR, textStatus, errorThrown) {				 
			 $('#msgRetornoConfirmExclusaoParamErro').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		 }
	 });
}

function prepararModalConfirmaAdicao(id, idImovel){
	$("#modIdParametro").val(id);
	$("#modIdImovel").val(idImovel);	
	$('#msgRetornoConfirmAdicaoParamErro').html("");	
	$("#idModalConfirmacaoAdicaoParam").modal("show");	
}

function confirmarAdicaoSolParceriaAnaliseSol(){	
	var parametro = document.getElementById("modIdParametro");
	var idImovel = document.getElementById("modIdImovel");	
	$.ajax({
		 url: '${urlParceria}/aceitarSolImovelParceria/' + parametro.value + "/" + idImovel.value,			 
		 success: function(){				 
			 location.reload();     	    
		 },
		 error: function(jqXHR, textStatus, errorThrown) {				 
			 $('#msgRetornoConfirmAdicaoParamErro').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		 }
	 });
}


function prepararModalConfirmaRemocao(id, idImovel){
	$("#modIdParametro").val(id);
	$("#modIdImovel").val(idImovel);	
	$('#msgRetornoConfirmRemocaoParamErro').html("");	
	$("#idModalConfirmacaoRemocaoParam").modal("show");	
}

function confirmarRemocaoSolParceriaAnaliseSol(){	
	var parametro = document.getElementById("modIdParametro");
	var idImovel = document.getElementById("modIdImovel");	
	$.ajax({
		 url: '${urlParceria}/limparUsuarioImovelParceria/' + parametro.value + "/" + idImovel.value,			 
		 success: function(){				 
			 location.reload();     	    
		 },
		 error: function(jqXHR, textStatus, errorThrown) {				 
			 $('#msgRetornoConfirmRemocaoParamErro').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
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
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.link.parcerias"/> </h2>
                </div><!-- /.header-content -->                
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                    	<% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
							<c:import url="../../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                   		<!-- START Foto Perfil e Informações Contato -->
                   			<div class="col-lg-4 col-md-4 col-sm-4" >
		                        <div class="panel rounded shadow">
		                            <div class="panel-body">
		                                <div class="inner-all">
		                                    <ul class="list-unstyled">
		                                        <li class="text-center">
													<a href="${urlImovel}/detalhesImovel/${imovel.id}" >
														<img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${imovel.imagemArquivo}" style="width: 100px; height: 100px; " alt="Tol Lee">
													</a>
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize">${imovel.tipoImovelFmt} - ${imovel.acaoFmt}</h4>
		                                            <h4 class="text-capitalize" style="font-size: 12px">${imovel.titulo}</h4>
		                                            <p class="text-muted text-capitalize">
		                                             	${imovel.uf} - ${imovel.cidade} - ${imovel.bairro}
		                                             </p>
		                                            <p>		                                             
			                                             <c:choose> 
															  	<c:when test="${imovel.acao == 'V'}">
															  		<strong><spring:message code="lbl.valor.venda.imovel"/> <fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
															  	</c:when>
															  	<c:when test="${imovel.acao == 'A'}">
															  		<strong><spring:message code="lbl.valor.aluguel.imovel"/> <fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
															  	</c:when>
															  	<c:when test="${imovel.acao == 'T'}">
															  		<strong><spring:message code="lbl.valor.temporada.imovel"/> <fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
															  	</c:when>
															  	<c:when test="${imovel.acao == 'D'}">
															  		<strong><spring:message code="lbl.valor.dividir.imovel"/> <fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
															  	</c:when>
														  </c:choose>
													 </p> 		                                             
		                                        </li>
		                                      	
		                                        <li><br/></li>		                                       
		                                    </ul>
		                                </div>
		                            </div>
		                        </div><!-- /.panel -->
		
		                    </div>
                   		<!-- END Foto Perfil e Informações Contato-->
                   		
                   		<!-- START Painel de Informações -->
                   	<div class="col-lg-8 col-md-8 col-sm-9">                            
                            <!-- Start default tabs -->
                            <div class="panel panel-tab rounded shadow">
                                <!-- Start tabs heading -->
                                <div class="panel-heading no-padding">
                                    <ul class="nav nav-tabs">
                                        <li class="active">
                                            <a href="#tab1-1" data-toggle="tab">
                                                <i class="fa fa-check-circle"></i>
                                                <span> <spring:message code="lbl.parceria.selecionada"/>  </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#tab1-2" data-toggle="tab">
                                                <i class="fa fa-file-text"></i>
                                                <span> <spring:message code="lbl.parceria.outras.informacoes.imovel"/> </span>
                                            </a>
                                        </li>
                                        
                                    </ul>
                                </div><!-- /.panel-heading -->
                                <!--/ End tabs heading -->

                                <!-- Start tabs content -->
                                <div class="panel-body">
                                    <div class="tab-content">
                                        <div class="tab-pane fade in active" id="tab1-1">                                        	
                                        	<c:choose>
                                        		<c:when test="${ not empty listaImovelParceriaSelecionados }">
                                        				<div class="form-group"> &nbsp;&nbsp;	 </div><!-- /.form-group -->                                        		
		                                        			<div class="table-responsive">
		                                                        <table class="table table-middle">
		                                                        	<thead>
						                                            <tr>
						                                            	<th class="text-center"> </th>
						                                                <th class="text-center"><spring:message code="lbl.nome.usuario.parceria"/></th>
						                                                <th class="text-center"><spring:message code="lbl.data.resposta"/></th>
						                                                <th class="text-center"><spring:message code="lbl.data.sol"/></th>				                                                                                                
						                                                <th class="text-center"><spring:message code="lbl.table.data.acoes"/></th>                                                
						                                            </tr>
						                                            </thead>
		                                                            <tbody>
		                                                            <c:forEach var="parceriaSelecionada" items="${listaImovelParceriaSelecionados}" varStatus="item">                                                            
			                                                            <tr class="border-primary">
			                                                                <td> 
																				<a href="${urlUsuario}/detalhesUsuario/${parceriaSelecionada.usuarioSolicitante.id}" >
																					<img src="data:image/jpeg;base64,${parceriaSelecionada.usuarioSolicitante.imagemArquivo}" style="width: 35px; height: 35px; " />
																				</a>
			                                                                </td>
			                                                                <td class="text-center"><b><a href="${urlUsuario}/detalhesUsuario/${parceriaSelecionada.usuarioSolicitante.id}" >
																											${parceriaSelecionada.usuarioSolicitante.nome}
																									   </a></b>
																			</td>
			                                                                <td class="text-center"><fmt:formatDate value='${parceriaSelecionada.dataResposta}' pattern='dd/MM/yyyy'/></td>
			                                                                <td class="text-center"><fmt:formatDate value='${parceriaSelecionada.dataSolicitacao}' pattern='dd/MM/yyyy'/></td>                                                                
			                                                                <td class="text-center"><a href="#" onClick="prepararModalConfirmaRemocao(${parceriaSelecionada.id},${parceriaSelecionada.imovel.id})" data-toggle="tooltip" data-placement="top" data-original-title="Excluir Solicitação"><i class="fa fa-times"></i></a> 
		                                          							</td>
			                                                           		
			                                                           			
			                                                            </tr>
		                                                            </c:forEach>
		                                                            </tbody>
		                                                        </table>
		                                                    </div>
                                        		</c:when>
                                        		
                                        		<c:when test="${ empty listaImovelParceriaSelecionados }">
                                        			 <div class="form-group"> &nbsp;&nbsp;	 </div><!-- /.form-group -->
		                                         		  <div align="center">	                                         		  
		                                         		  		<spring:message code="lbl.nenhuma.parceria.selecionada"/>			                                         	
				                                         </div>	                                         			                                         
		                                         	 <div class="form-group"> &nbsp;&nbsp;	 </div><!-- /.form-group --> 	
                                        		</c:when>
                                        	</c:choose>                            
 
                                        </div>
                                        <div class="tab-pane fade" id="tab1-2" >                                            
                                           
                                            <div class="form-group">																            
						                         <strong class="col-sm-3"><spring:message code="lbl.estado"/> </strong>  ${imovel.estado}						                                  
	                                        </div><!-- /.form-group -->
	                                         
                                            <div class="form-group">																            
						                         <strong class="col-sm-3"><spring:message code="lbl.cidade"/> </strong>  ${imovel.cidade}						                                  
	                                        </div><!-- /.form-group -->
	                                        
	                                        <div class="form-group">																            
						                         <strong class="col-sm-3"><spring:message code="lbl.bairro"/> </strong>  ${imovel.bairro}						                                  
	                                        </div><!-- /.form-group -->
	                                        
	                                        <div class="form-group">																            
						                         <strong class="col-sm-3"><spring:message code="lbl.valor.imovel"/> </strong>  <fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/>						                                  
	                                        </div><!-- /.form-group -->
                                            
                                            <div class="form-group">																            
						                         <strong class="col-sm-3"><spring:message code="lbl.data.cadastro.imovel"/> </strong>  <fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/>						                                  
	                                        </div><!-- /.form-group -->                                            
                                            
                                        </div>                                   
                                    </div>
                                </div><!-- /.panel-body -->
                                <!--/ End tabs content -->
                            </div><!-- /.panel -->
                            <!--/ End default tabs -->
                        </div>
                   		<!-- END  Painel de Informações -->
                   		                               
                    </div><!-- /.row -->
                    
                    <div class="row">
                    	<div class="panel">
                            <div class="panel-heading">
                                <div class="pull-left">
                                    <h3 class="panel-title"><spring:message code="lbl.aba.parceria.lista.sol"/></h3>
                                </div>
                                <div class="pull-right">
                                    <a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>
                                </div>
                                <div class="clearfix"></div>
                            </div><!-- /.panel-heading -->
                            <!-- INICIO solicitações parceria -->
                   		
		                   		<div class="panel-body no-padding">		                 
		                             <div class="table-responsive" style="margin-top: -1px;">
		                                 <table class="table table-striped">
		                                     <thead>
		                                     <tr>
		                                         <th class="text-center"><spring:message code="lbl.data.sol"/></th>
		                                         <th class="text-center"><spring:message code="lbl.nome.usuario"/></th>
		                                         <th class="text-center"><spring:message code="lbl.desc.solicitacao"/></th>
		                                         <th class="text-center"><spring:message code="lbl.table.data.acoes"/></th>                                                                                         
		                                     </tr>
		                                     </thead>
		                                     <tbody>
		                                     <c:forEach var="parceria" items="${listaSolImovelParceria}" >
		                                      <tr>
		                                          <td class="text-center" style="font-size: 13px;"> <fmt:formatDate value='${parceria.dataSolicitacao}' pattern='dd/MM/yyyy'/>  </td>
		                                          <td class="text-center" style="font-size: 13px;"> ${parceria.usuarioSolicitante.nome} </td>
		                                          <td class="text-center" style="font-size: 13px;"> ${parceria.descricaoCompartilhamento} </td>              
		                                          <td class="text-center" style="font-size: 13px;"> 
		                                                <a href="#" onClick="prepararModalConfirmaAdicao(${parceria.id}, ${parceria.imovel.id})"  data-toggle="tooltip" data-placement="top" data-original-title="<spring:message code='lbl.link.selecionar.sol.parceria'/>"><i class="fa fa-check"></i></a> 
		                                          		<a href="#" onClick="prepararModalConfirmaExclusao(${parceria.id}, ${parceria.imovel.id} )" data-toggle="tooltip" data-placement="top" data-original-title="Excluir Solicitação"><i class="fa fa-times"></i></a> 
		                                          </td>
											                      		
		                                      </tr>
		                                     </c:forEach>
		                                     </tbody>
		                                 </table>
		                             </div><!-- /.table-responsive -->
		                         </div><!-- /.panel-body -->                       
                   		<!-- FIM solicitações parceria -->                            
                        </div>                        
                    </div>

                </div><!-- /.body-content -->       
                  
            </section><!-- /#page-content -->		
            
       <!-- Start optional size modal element - confirmacao exclusao parceria solicitada -->
            <div id="idModalConfirmacaoExclusaoParam" class="modal fade bs-example-modal-lg-confirmacao-exclusao-sol-intermediacao" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdParametro" readonly="readonly" name="modIdParametro">
	            <input type="hidden" id="modIdImovel" readonly="readonly" name="modIdImovel">	            
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.exclusao.sol.parceria"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.exclusao.sol.parceria"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-theme" onClick="confirmarExclusaoSolParceriaAnaliseSol();"><spring:message code="lbl.sim"/></button>                            
	                       		<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                        </div>
							
							<div id="msgRetornoConfirmExclusaoParamErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao exclusao parceria solicitada  -->          
         
          <!-- Start optional size modal element - confirmacao selecao parceria -->
            <div id="idModalConfirmacaoAdicaoParam" class="modal fade bs-example-modal-lg-confirmacao-adicao-sol-intermediacao" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdParametro" readonly="readonly" name="modIdParametro">
	            <input type="hidden" id="modIdImovel" readonly="readonly" name="modIdImovel">	            
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.adicao.sol.parceria"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.adicao.sol.parceria"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-theme" onClick="confirmarAdicaoSolParceriaAnaliseSol();"><spring:message code="lbl.sim"/></button>                            
	                      		<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                        </div>							
							<div id="msgRetornoConfirmAdicaoParamErro" cssClass="errorEntrada"  ></div>  
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao  selecao parceria  --> 
         
         
           <!-- Start optional size modal element - remover usuario da parceria -->
            <div id="idModalConfirmacaoRemocaoParam" class="modal fade bs-example-modal-lg-confirmacao-remocao-parceria-intermediacao" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdParametro" readonly="readonly" name="modIdParametro">
	            <input type="hidden" id="modIdImovel" readonly="readonly" name="modIdImovel">	            
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.remocao.sol.parceria"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.remocao.sol.parceria"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-theme" onClick="confirmarRemocaoSolParceriaAnaliseSol();"><spring:message code="lbl.sim"/></button>                            
	                       		<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>	                   
	                        </div>							
							<div id="msgRetornoConfirmRemocaoParamErro" cssClass="errorEntrada"  ></div>  
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - remover usuario da parceria   --> 
				
			
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->
       
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
              
  		<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
 
    </body>
    <!--/ END BODY -->

</html>