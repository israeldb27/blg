<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>


<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/nota" var="urlNota"/>
<spring:url value="/notificacao" var="urlNotificacao"/>

   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   

<link href="${context}/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"  href="${context}/css/smart-forms_det_imovel.css">
<link href="${context}/css/owl.carousel.css" rel="stylesheet">
<link href="${context}/css/owl.theme.css" rel="stylesheet">
<link href="${context}/css/style_det_imovel.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${context}/js/jquery-ui.js"></script>
<script type="text/javascript" src="${context}/js/additional-methods.js"></script>   

<style type="text/css">
#owl-demo .item img{
    display: block;
    width: 100%;
    height: auto;
}
div#map_container{
	width:70%;
	height:150px;
}
.modal-dialog {}
.thumbnail {margin-bottom:6px;}
.carousel-control.left,.carousel-control.right{
  background-image:none;
}
</style>

<!-- TABS -->
<script src="${context}/js/jquery-ui.js "></script>

<!-- SHADOWBOX -->
<link rel="stylesheet" type="text/css" href="${context}/includes/shadowbox/shadowbox.css">
<script type="text/javascript" src="${context}/includes/shadowbox/shadowbox.js"></script>
<script type="text/javascript">
Shadowbox.init();
</script>

<script defer src="${context}/js/jquery.flexslider.js"></script>
<script type="text/javascript">

function prepararModalMaisDetalhesUsuario(id){
	$("#modIdImovel").val(id);
	$('#msgRetornoConfirmExclusaoMaisDetalhesImovelOffline').html("");	
	$("#idModalMaisDetalhesImovelOffline").modal("show");	
}

function direcionarTelaCadastroUsuario(){

		var x = document.getElementById("modIdImovel");

		$.ajax({  
	         url: '${urlUsuario}/prepararCadastroUsuarioOffline/' + x.value,
	         dataType: 'json',
	         success: function(data){	        	 
	        	 if ( data == 'ok') {
	        		 location.reload();
	        	 }
	        	 else  {
		        	 $('#msgRetornoConfirmExclusaoMaisDetalhesImovelOffline').html(data);
		         }	
	         },	      
	     });
}


function mostrarModal(id){	
	if (id == 0){
		$('#msgModal').html("<spring:message code='lbl.modal.sobre.mim'/>");		
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.sobre.mim'/>");
	}
	else if ( id == 1){
		$('#msgModal').html("<spring:message code='lbl.modal.notas.usuario'/> ");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.notas.usuario'/>");
	}
	else if ( id == 2){
		$('#msgModal').html("<spring:message code='lbl.modal.pref.imoveis'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.pref.imoveis'/>");
	}
	else if ( id == 3){
		$('#msgModal').html("<spring:message code='lbl.modal.contatos'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.contatos'/>");
	}
	else if ( id == 4){
		$('#msgModal').html("<spring:message code='lbl.modal.lista.seguidores.detalhes.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.lista.seguidores.detalhes.usuario'/>");
	}
	else if ( id == 5){
		$('#msgModal').html("<spring:message code='lbl.modal.recomendacoes.detalhe.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.recomendacoes.detalhe.usuario'/>");
	}
	else if ( id == 6 ){
		$('#msgModal').html("<spring:message code='msg.aba.title.detalhes.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.detalhes.usuario'/>");
	}
	
	$("#idModalItem").modal("show");
}

</script>
		
<c:import url="../layout/head-layout.jsp"></c:import>   
    <body style="">

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
                    <h2>
	                    <i class="fa fa-pencil"></i><spring:message code="lbl.title.detalhes.usuario"/>
	                    <div class="pull-right">
	                         <a href="#a" class="btn btn-sm"  onClick="mostrarModal(6);"><i class="fa fa-question" style="font-size: 12px;"></i></a>                                        
	                     </div>		 
	                 </h2>
                </div><!-- /.header-content -->
                                
                <!-- Start body content -->
                <div class="body-content animated fadeIn container"  style="min-height: 500px; width: 920px;">
					<div class="col-lg-12 col-md-12 col-sm-12">	
						<div class="row">
						<!-- START  -->						
							<div class="panel rounded shadow">
								<div class="panel-heading" align="center">
    								<span class="label pull-left"  style="font-size: 18px;margin-bottom:10px;background-color: black;font-size: 100%;">${usuarioForm.perfilFmt}</span>                                    
    								<span class="label pull-right" style="margin-left: 14px;color:gray; font-size: 12px;"><spring:message code="lbl.data.cadastro.usuario"/> <fmt:formatDate value='${usuarioForm.dataCadastro}' pattern='dd/MM/yyyy'/></span>
									<h2 class="text-bold" style="margin-top: 0px;margin-bottom: 5px;width: 50%;">${usuarioForm.nome}</h2>
									<h5 style="margin-top: 4px;margin-bottom: 0px;width: 50%;">${usuarioForm.cidade} - ${usuarioForm.estado}</h5>
									<br>
                                    <div class="pull-right">
                                    
                                    	<a href="${urlImovelIndicado}/selecionarParaIndicarImovel/${imovelForm.id}" style="font-size:x-large;" class="meta-action"><i class="fa fa-share-alt" style="color:gray" title="<spring:message code="lbl.acao.sugerir"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.acao.sugerir"/> </font></a>
                         
	                                   	<a href="#a" onClick="prepararModalMaisDetalhesImovel(${imovelForm.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-home" style="color:gray" ></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.title.mais.detalhes"/> </font></a>                                    		

                                    </div>
                                    <br> <br>                                                                        

								</div>
								<div class="panel-body">
									<div class="pull-left">
										<div class="col-lg-6 col-md-6 col-sm-6"> 
	                                        <div id="owl-demo" class="owl-carousel owl-theme" >
	                                            <img class="img-circle"  src="data:image/jpeg;base64,${usuarioForm.imagemArquivo}" style="margin-left:30px; width: 240px; height: 240px; ">	                                            	                                            	                                                                                        
	                                        </div>
	                                    </div>
                                    </div>
                                	
                                	<div class="pull-right">
                                		 <table class="table table-striped" style="margin-top:10px; font-size: 13px;">
                                         <tbody>
                                         
                                         <c:choose>
                                         	<c:when test="${((usuarioForm.perfil == 'P') || (usuarioForm.perfil == 'C')) }">
                                         		  <tr>
	                                                 <td class="text-left"><spring:message code="lbl.cpf"/></td>
	                                                 <td class="text-right">${usuario.cpf}</td>
	                                              </tr>                                                     
                                         	</c:when>
                                         	
                                        	<c:when test="${usuarioForm.perfil == 'I'}">
                                       			<tr>
                                                  <td class="text-left"><spring:message code="lbl.cnpj"/></td>
                                                  <td class="text-right">${usuario.cpf}</td>
                                               </tr>  	
                                         	</c:when>
                                         	
                                         	<c:when test="${usuarioForm.perfil == 'C'}">
                                         		<tr>
                                                   <td class="text-left"><spring:message code="lbl.creci"/></td>
                                                   <td class="text-right">${usuarioForm.creci}</td>
                                              	</tr>
                                         	</c:when>                                                                              
                                         </c:choose>                     
                                         
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
                                                 <td class="text-left"><spring:message code="lbl.quant.total.tipo.atividade.marcar.visita" /></td>
                                                 <td class="text-right">${usuarioForm.quantTotalVisitasMarcadas}</td>
                                             </tr>
                                             
                                             <tr>
                                                 <td class="text-left"><spring:message code="lbl.quant.total.tipo.atividade.sol.fechar.negocio" /></td>
                                                 <td class="text-right">${usuarioForm.quantTotalSolFechamento}</td>
                                             </tr>
                                           
                                         </tbody>
                                     </table>	
                                	</div>       	
							</div><!-- /.panel -->
						</div>
						
							<!-- /.START Descricao -->
							<div class="panel rounded shadow">
								<div class="panel-heading">
									<h3 class="panel-title">
										<div class="pull-left">
                              				<spring:message code="lbl.sobre.mim"/>
                               			</div>
                               				
                              			<div class="pull-right">
                              				<a href="#a" class="btn btn-sm" onClick="mostrarModal(0);"  style=""><i class="fa fa-question" ></i></a>
                              			</div>
                              			<br>
									</h3>
								</div>
								<div class="panel-body">
                                    <p>${usuarioForm.descSobreMim}"</p>
								</div>
							</div>
							<!-- /.END Descricao -->
							
						<!-- /.START Imóveis Usuario -->
							<div class="panel panel-scrollable rounded shadow">
                                <div class="panel-heading">
                                	<div align="left" style="margin-top: 5px;">                                
                              			<h3 class="panel-title"> <spring:message code="lbl.imoveis.usuario"/> &nbsp;
                              				<c:if test="${not empty usuarioForm.listaImoveisUsuario}">
                              					<c:if test="${usuarioForm.id != usuario.id}">
                              						<a href="${urlImovel}/visualizarImoveisPerfilUsuario/${usuarioForm.id}" style="font-size:x-large; margin-left: 510px;" class="meta-action" title='<spring:message code="lbl.visualizar.imoveis.perfil.usuario"/>' >
	                              						<div class="pull-right">
	                              							<i class="fa fa-home" style="color:gray"></i>                              						
	                              							<font style="font-size: 12px; color: black;" >&nbsp; <spring:message code="lbl.visualizar.imoveis.perfil.usuario"/></font>	                              						
	                              						</div>	                              						
	                              					</a>                              					
                              					</c:if>
                              					
                              					<c:if test="${usuarioForm.id == usuario.id}">
                              						<a href="${urlImovel}/listarMeusImoveis" style="font-size:x-large; margin-left: 510px;" class="meta-action" title='<spring:message code="lbl.visualizar.imoveis.perfil.usuario"/>' >
                              							<div class="pull-right">
                              								<i class="fa fa-home" style="color:gray"></i>                              						
	                              							<font style="font-size: 12px; color: black;" >&nbsp; <spring:message code="lbl.title.link.meus.imoveis"/></font>
                              							</div>	                              						
	                              					</a>                              					
                              					</c:if>   
                              				</c:if> 
                              			</h3>
                              			
                              				<c:if test="${usuarioForm.quantTotalImoveis > 0}">                              					
                              					 &nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: (${usuarioForm.quantTotalImoveis}) </label>
                              				</c:if> 
                              		</div>                           	
                                </div>
                                
                                <c:choose>
                                	<c:when test="${ empty usuarioForm.listaImoveisUsuario }">
                                		<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
				                                 <div class="media-list list-search">
				                                 	<div class="callout callout-warning">
					                                    <strong><spring:message code="msg.nenhum.imovel.detalhes.usuario"/></strong>				                                    
					                                </div>   
				                                </div>
		                                    <br/>
		                                </div>
                                	</c:when>
                                	
                                	<c:when test="${not empty usuarioForm.listaImoveisUsuario }">
                                			<div class="panel-body panel panel-scrollable rounded shadow" style="height: 710px;">
					                                   <div class="media-list list-search">
					                                    <c:forEach var="imovel" items="${usuarioForm.listaImoveisUsuario}" varStatus="item">
					                                        <div class="media rounded shadow no-overflow">
					                                        	<c:if test="${imovel.destaque == 'S'}">
					                                                	<div class="ribbon-wrapper top-left">
								                                            <div class="ribbon ribbon-shadow">Destaque</div>
								                                        </div>
					                                            </c:if>
					                                            <div class="media-left">
					                                                <a href="${urlImovel}/detalhesImovel/${imovel.id}" >
					                                                   <span class="meta-provider ${imovel.classePorAcao}" style="font-size:19px;">${imovel.acaoFmt} <br>
					                                                   							<strong>  R$<fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
					                                                   </span><br>                                                   
					                                                    <img src="data:image/jpeg;base64,${imovel.imagemArquivo}"  class="img-responsive" style="width: 270px; height: 300px;"  />
					                                                </a>
					                                            </div>
					                                            <div class="media-body">
					                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${imovel.tipoImovelFmt}</span>			                                                 
					                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlImovel}/detalhesImovel/${imovel.id}" style="color : #9d2428;">${imovel.titulo}</a></h4>
					                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovel.endereco} - ${imovel.bairro} - ${imovel.cidade} -${imovel.uf} </h5>
					                                                
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
						                                                    <br> <br>
						                                                </div>  
					                                            </div>
					                                        </div>
					                                    </c:forEach>
					                                </div>
			                                    <br/>
			                                </div>
                                	</c:when>
                                </c:choose>
                            </div>                      
                         <!-- /.END Imóveis Usuário  --> 
 
 						<!-- /.START Notas Usuario  -->  
 						 <div class="panel panel-scrollable rounded shadow">
                                <div class="panel-heading">			                                	
                                    <h3 class="panel-title">
                                    	<div class="pull-left">
                              				<spring:message code="lbl.notas.usuario"/>
                               			</div>
                               				
                              			<div class="pull-right">
                              				<a href="#a" class="btn btn-sm" onClick="mostrarModal(1);" style=""><i style="" class="fa fa-question" ></i></a>
                              			</div>
                              			<br>                                    	
                                    </h3>
                                   		&nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.notas"/> </strong>: (${usuarioForm.quantTotalNotas}) </label>
                                </div><!-- /.panel-heading -->
                                
                                <div class="profile-body" >
									<c:choose>
                                			<c:when test="${not empty usuarioForm.listaNotasUsuario}">
                                				<div class="timeline">
                                					<c:forEach var="nota" items="${usuarioForm.listaNotasUsuario}"> 
                                						<div class="timeline-item last-timeline">
															<div class="timeline-badge">
																<br>
																  <img class="timeline-badge-userpic" src="data:image/jpeg;base64,${nota.usuario.imagemArquivo}" style="width: 80px; height: 90px; " >
															</div>
															<div class="timeline-body">
																<div class="timeline-body-arrow">
																</div>
																
																<div class="timeline-body-head">
																	<div class="timeline-body-head-caption">
																		<a href="${urlUsuario}/detalhesUsuario/${usuario.id}"  class="timeline-body-title font-blue-madison"> ${nota.usuario.nome}</a>
																		<span class="timeline-body-time font-grey-cascade"> </span>
																	</div>
																</div>
																<div class="timeline-body-content">
																	<p>
																		<c:choose>
																			<c:when test="${nota.acao == 'E'}">
																				<small class="block text-muted"> <font size="3px;"> ${nota.descricao} </font></small>
																			</c:when>
																			
																			<c:when test="${nota.acao == 'I'}">
																				<small class="block text-muted"> <font size="3px;"> <spring:message code="lbl.minha.nota.informacoes.imovel.p1"/> <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a> <spring:message code="lbl.minha.nota.informacoes.imovel.p2"/> </font></small>
																			</c:when>
																			
																			<c:when test="${nota.acao == 'U'}">
																				<small class="block text-muted"> <font size="3px;"> <spring:message code="lbl.minha.nota.informacoes.pessoais"/> </font></small>
																			</c:when>
																			
																			<c:when test="${nota.acao == 'R'}">
																				<small class="block text-muted"> <font size="3px;"> <spring:message code="lbl.minha.nota.add.preferencia.imovel"/> </font></small>
																			</c:when>	
																			
																			<c:when test="${nota.acao == 'T'}">
																				<small class="block text-muted"> <font size="3px;"> <spring:message code="lbl.notas.contato.intermediacao"/> <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a></font></small>
																			</c:when>
																			
																			<c:when test="${nota.acao == 'P'}">
																				<small class="block text-muted"> <font size="3px;"> <spring:message code="lbl.notas.contato.parceria"/> <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a> </font></small>
																			</c:when>																		
																		</c:choose>																		
																	</p>																				
																</div>	
																 </br>																			  
															     <em class="text-xs text-muted"><spring:message code="lbl.data.nota"/>: <span class="text-danger"><fmt:formatDate value='${nota.dataNota}' pattern='dd/MM/yyyy'/></span></em>															
															</div>
														</div>
                                					</c:forEach>								
												</div>
												<c:if test="${usuarioForm.isExibeMaisListaNotas() }">
													<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;"> 
					                                    <a href="${urlUsuario}/listarNotasPerfilUsuario/${usuarioForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>
					                               		<br> <br>
					                                </div>
												</c:if>												 
                                			</c:when>
                                			
                                			<c:when test="${ empty usuarioForm.listaNotasUsuario }">
	                                			 <div class="callout callout-warning">
				                                    <strong><spring:message code="lbl.nenhuma.nota"/></strong>			                                    
				                                </div>                                		
	                                		</c:when>	                                		
                                		</c:choose>		
								</div>         
                         </div><!-- /.panel -->
 						
 						<!-- /.END Notas Usuario -->  
						
					</div>
					<div class="row">
						<div class="col-lg-9 col-md-9 col-sm-13">		
						
	                      
					</div>
                </div><!-- /.body-content -->
			</div>              
           
            </section><!-- /#page-content -->

        </section><!-- /#wrapper -->  
        
        	       
          <!-- START - Modal Mais Detalhes Imovel  -->
            <div id="idModalMaisDetalhesImovelOffline" class="modal fade bs-example-modal-lg-mais-detalhes-imovel-offline" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdImovel" readonly="readonly" name="modIdImovel">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.mais.detalhes.imovel.offline"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.mais.detalhes.imovel.offline"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="direcionarTelaCadastroUsuario();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoConfirmExclusaoMaisDetalhesImovelOffline" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- END - Modal Mais Detalhes Imovel    -->     
               
            
           <!-- Start optional size modal element - item 1 -->
            <div id="idModalItem" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				          <h4 class="modal-title"><div id="msgModalFuncionalidade" > </div> </h4>
				        </div>
				        <div class="modal-body">  
				       	   <strong> <spring:message code="lbl.descricao.geral"/>:  </strong> <div id="msgModal" > </div>
				        </div>
				        <div class="modal-footer">			          
	                      <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>
				        </div>
				      </div>
				    </div>
				</div>
            </div><!-- /.modal -->   
         
        
		<!-- Start content modal Ajuda - funcionalidade -->
			<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
		<!-- End content  modal Ajuda - funcionalidade -->		
		
        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->
        
    <script src="${context}/js/owl.carousel.js"></script>
    <script>
        $(document).ready(function() {
          $("#owl-demo").owlCarousel({
              navigation : true, // Show next and prev buttons
              slideSpeed : 300,
              paginationSpeed : 400,
              singleItem:true,
              autoplay:true,
              stopOnHover:true,
              navigationText: ["Anterior", "Proximo"],
              navigation:true,
              responsive:true,
              pagination:false,
              thumbs: true
          });
        });
        </script>
    </body>
    <!--/ END BODY -->

</html>