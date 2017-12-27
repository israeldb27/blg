<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<c:set var="contextURL" value="<%= request.getRequestURL().toString()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
<script src="${context}/assets/admin/js/pages/blankon.gallery.type4.js"></script>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>


<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>

<spring:url value="/imovelComentario" var="urlImovelComentario"/>

<spring:url value="/contato" var="urlContato"/>
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/notificacao" var="urlNotificacao"/>
<spring:url value="/imovelPropostas" var="urlImovelPropostas"/>
<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>


<script src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyBC9ter9LUNs4kWEqVoQUFy6UthDBQYuXw&callback"></script>

<script src="//maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=YOUR_API_KEY" async="" defer="defer" type="text/javascript"></script>

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
$(window).load(function() {	
	
});
$(document).ready(function() {
	$('#myCarousel').carousel({
	    interval: 4000
	});
	// handles the carousel thumbnails
	$('[id^=carousel-selector-]').click( function(){		
	  var id_selector = $(this).attr("id");	  
	  var id = id_selector.substr(id_selector.length -1);
	  id = parseInt(id);	  
	  $('#myCarousel').carousel(id);
	  $('[id^=carousel-selector-]').removeClass('selected');
	  $(this).addClass('selected');
	});
	// when the carousel slides, auto update
	$('#myCarousel').on('slid', function (e) {
	  var id = $('.item.active').data('slide-number');
	  id = parseInt(id);
	  $('[id^=carousel-selector-]').removeClass('selected');
	  $('[id=carousel-selector-'+id+']').addClass('selected');
	});
});	

function prepararModalMaisDetalhesImovel(id){
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
		$('#msgModal').html("<spring:message code='lbl.modal.descricao.imovel'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.descricao.imovel'/>");
	}
	else if ( id == 1){
		$('#msgModal').html("<spring:message code='lbl.modal.det.mapa.imovel'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.mapa.imovel'/>");
	}
	else if ( id == 2){
		$('#msgModal').html("<spring:message code='lbl.modal.det.propostas.imovel'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.propostas.imovel'/>");
	}
	else if ( id == 3){
		$('#msgModal').html("<spring:message code='lbl.modal.det.propostas.imovel.dono.imovel'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.propostas.imovel.dono.imovel'/>");
	}
	else if ( id == 4){
		$('#msgModal').html("<spring:message code='lbl.modal.det.intermediacoes.imovel'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.intermediacoes.imovel'/>");
	}
	else if ( id == 5){
		$('#msgModal').html("<spring:message code='lbl.modal.det.parcerias.imovel'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.parcerias.imovel'/>");
	}
	else if ( id == 6){
		$('#msgModal').html("<spring:message code='lbl.modal.det.visualizacoes.imovel'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.visualizacoes.imovel'/>");
	}
	else if ( id == 7){
		$('#msgModal').html("<spring:message code='lbl.modal.usuarios.interessados'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.usuarios.interessados'/>");
	}
	else if ( id == 8){
		$('#msgModal').html("<spring:message code='lbl.modal.det.comentarios.imovel'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.comentarios.imovel'/>");
	}
	else if ( id == 9){
		$('#msgModal').html("<spring:message code='lbl.modal.contato.detalhe.imoveis'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.contato.detalhe.imoveis'/>");
	}
	else if ( id == 10){
		$('#msgModal').html("<spring:message code='lbl.modal.contato.detalhe.imoveis'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.contato.detalhe.imoveis'/>");
	}
	else if ( id == 11){
		$('#msgModal').html("<spring:message code='lbl.modal.atividades.detalhe.imoveis'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.imovel.atividades'/>");
	}
	else if ( id == 12){
		$('#msgModal').html("<spring:message code='lbl.modal.possivel.interessado.detalhe.imoveis'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.imovel.possivel.interessado'/>");
	}
	else if ( id == 13){
		$('#msgModal').html("<spring:message code='lbl.modal.possivel.interessado.offline.detalhe.imoveis'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.imovel.possivel.interessado.offline'/>");
	}
	
	$("#idModalItem").modal("show");
}
function prepararModalGaleriaFotos(){		
	$("#idModalGaleriaFotos").modal("show");	
}
</script>

<style>
.selected img {
	opacity:0.5;
}	
</style>
		
<c:import url="../layout/head-layout.jsp"></c:import>   
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../layout-offline/header-offline.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../layout-offline/sidebar-left-offline.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">     
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.detalhes.imovel"/> </h2>				 
                </div><!-- /.header-content -->
                                
                <!-- Start body content -->
                <div class="body-content animated fadeIn container limit-container" style="min-height: 500px; width: 920px;" >
					<div class="col-lg-12 col-md-12 col-sm-12">					
						<!-- START  -->
						  <div class="row">		
							<div class="panel rounded shadow">
								<div class="panel-heading" align="center">
    								<span class="label pull-left label-${imovelForm.classePorAcao}"  style="font-size: 100%;margin-bottom:10px;">${imovelForm.acaoFmt}</span>
                                    <span class="label pull-left" style="margin-left:10px;background-color: lightgray;font-size: 100%;">${imovelForm.tipoImovelFmt}</span>
    								<span class="label pull-right" style="margin-left: 10px;color:gray; font-size: 15px;"><spring:message code="lbl.data.ultima.imovel.atualizacao.resum"/> <fmt:formatDate value='${imovelForm.dataUltimaAtualizacao}' pattern='dd/MM/yyyy'/></span>    								
									<h2 class="text-bold" style="margin-top: 0px;margin-bottom: 5px;width: 50%;">${imovelForm.titulo}</h2>
									<h5 style="margin-top: 4px;margin-bottom: 0px;width: 50%;">${imovelForm.endereco} </h5>
									<h5 style="margin-top: 4px;margin-bottom: 0px;width: 50%;">${imovelForm.bairro}, ${imovelForm.cidade} - ${imovelForm.estado}</h5>
									<br>
                                    <div class="pull-right">                                    	
                                    	<a href="#a" onClick="prepararModalGaleriaFotos()" style="font-size:x-large;" class="meta-action"><i class="fa fa-file-image-o" style="color:gray" title="<spring:message code="lbl.title.link.alterar.galeria.foto.imovel"/>"></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.title.link.alterar.galeria.foto.imovel"/> </font></a>
                                    	&nbsp;&nbsp;&nbsp;&nbsp;                                     	 

                                        <a href="${urlImovelIndicado}/selecionarParaIndicarImovel/${imovelForm.id}" style="font-size:x-large;" class="meta-action"><i class="fa fa-share-alt" style="color:gray" title="<spring:message code="lbl.acao.sugerir"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.acao.sugerir"/> </font></a>
                         
	                                   	<a href="#a" onClick="prepararModalMaisDetalhesImovel(${imovelForm.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-home" style="color:gray" ></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.title.mais.detalhes"/> </font></a> 

                                   		 &nbsp;&nbsp;&nbsp;&nbsp;       
                                    </div>
                                    
                                    <br> <br>
								</div>
								
								<!-- Start Galeria Fotos -->
								<div class="panel-body">
									<div class="col-lg-7 col-md-8 col-sm-6">
                                      	<div id="owl-demo" class="owl-carousel owl-theme">                                             
	                                             <div class="item"><img class="responsive" src="data:image/jpeg;base64,${imovelForm.imagemArquivo}"  style="max-height:395px; height: 500px; width: 450px;"></div>                                            
	                                     </div>
	                				</div>
	                				
	                				<div class="col-lg-5 col-md-5 col-sm-5"> 
	                                     <table class="table table-striped" style="margin-top:10px;">
                                                <tbody>
                                                	<tr>  
                                                		<td class="text-left"><spring:message code="lbl.valor.imovel" /></td>	
                                                        <td class="text-right"><strong>R$<fmt:formatNumber value="${imovelForm.valorImovel}" pattern="#,##0.00;-0"/></strong></td>
                                                    </tr>
                                                    
                                                    <c:if test="${imovelForm.valorCondominio > 0}">
                                                    	<tr>
	                                                        <td class="text-left"><spring:message code="lbl.valor.condominio.resum" /></td>
	                                                        <td class="text-right">R$<fmt:formatNumber value="${imovelForm.valorCondominio}" pattern="#,##0.00;-0"/></td>
	                                                    </tr>
                                                    </c:if>
                                                    
                                                    <c:if test="${imovelForm.valorIptu > 0}">
                                                    	<tr>
	                                                        <td class="text-left"><spring:message code="lbl.valor.iptu.resum"/></td>
	                                                        <td class="text-right">R$<fmt:formatNumber value="${imovelForm.valorIptu}" pattern="#,##0.00;-0"/></td>
	                                                    </tr>
                                                    </c:if>
                                                    
                                                    <c:if test="${imovelForm.area > 0}">
                                                    	<tr>
	                                                        <td class="text-left"><spring:message code="lbl.area.m2.resum"/></td>
	                                                        <td class="text-right"><fmt:formatNumber value="${imovelForm.area}" type="number"/> m<sup>2</sup></td>
	                                                    </tr>
                                                    </c:if>
                                                    
                                                    
                                                    <c:if test="${imovelForm.quantQuartos > 0}">
                                                    	<tr>
	                                                        <td class="text-left"><spring:message code="lbl.quartos.dormitorios.resum"/></td>
	                                                        <td class="text-right">${imovelForm.quantQuartos}</td>
	                                                    </tr>  
                                                    </c:if>
                                                    
                                                    <c:if test="${imovelForm.quantGaragem > 0}">
                                                    	<tr>
	                                                        <td class="text-left"><spring:message code="lbl.vagas.garagem.resum"/></td>
	                                                        <td class="text-right">${imovelForm.quantGaragem} <spring:message code="lbl.num.vagas"/></td>
	                                                    </tr>
                                                    </c:if>
                                                    
                                                    <c:if test="${imovelForm.quantBanheiro > 0}">
                                                    	<tr>
	                                                        <td class="text-left"><spring:message code="lbl.buscar.imovel.banheiros"/></td>
	                                                        <td class="text-right"> ${imovelForm.quantBanheiro} </td>
	                                                    </tr>
                                                    </c:if>                                                    
                                                    
                                                    <c:if test="${imovelForm.quantSuites > 0}">
                                                    	<tr>
	                                                        <td class="text-left"><spring:message code="lbl.suites"/></td>
	                                                        <td class="text-right">${imovelForm.quantSuites}</td>
	                                                    </tr>
                                                    </c:if>
                                                    
                                                    	<tr>
	                                                        <td class="text-left"><spring:message code="lbl.total.visualizacoes"/></td>
	                                                        <td class="text-right">${imovelForm.quantVisualizacoesImovel}</td>
	                                                    </tr>
	                                                    
	                                                    <tr>
	                                                        <td class="text-left"><spring:message code="lbl.total.imoveis.interessados"/></td>
	                                                        <td class="text-right">${imovelForm.quantUsuariosInteressados}</td>
	                                                    </tr>
	                                                    
	                                                     <tr>
	                                                        <td class="text-left"><spring:message code="lbl.quant.total.tipo.atividade.marcar.visita"/></td>
	                                                        <td class="text-right">${imovelForm.quantTotalVisitasMarcadas}</td>
	                                                    </tr>
	                                                    
	                                                    <tr>
	                                                        <td class="text-left"><spring:message code="lbl.quant.total.tipo.atividade.sol.fechar.negocio"/></td>
	                                                        <td class="text-right">${imovelForm.quantTotalSolFechamentoNegocio}</td>
	                                                    </tr>
                                                    
                                                      <tr>
                                                           <td class="text-left"><spring:message code="lbl.data.cadastro.imovel" /></td>
                                                           <td class="text-right"><fmt:formatDate value='${imovelForm.dataCadastro}' pattern='dd/MM/yyyy'/></td>
                                                       </tr>
                                                       
                                                        <tr>
                                                            <td class="text-left"><spring:message code="lbl.codigo.identificacao.imovel.resum"/></td>
                                                            <td class="text-right">${imovelForm.codigoIdentificacao}</td>
                                                        </tr>                                                    
                                                </tbody>
                                            </table>
                                        </div> 
                                    </div>
									
                                 </div>
                                 <!-- End Galeria Fotos -->
                         
							</div><!-- /.panel -->
							
							
							<!-- /.START Descricao -->
							<div class="panel rounded shadow">
								<div class="panel-heading">
                                    <h3 class="panel-title">
                                    	<div class="pull-left">
                              				<spring:message code="lbl.descricao.imovel"/>
                               			</div>
                               				
                              			<div class="pull-right">
                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);" style=""><i class="fa fa-question" ></i></a>
                              			</div>
                              			<br>
                                     </h3>                                       
								</div> 
								
								<c:choose>
									<c:when test="${imovelForm.descricao != ''}">
										<div class="panel-body">
		                                    <p>${imovelForm.descricao}</p>
										</div>
									</c:when>
									
									<c:when test="${imovelForm.descricao == ''}">
										<div class="panel-body panel panel-info rounded shadow">
											<div class="callout callout-warning">
			                                    <strong><spring:message code="msg.descricao.vazio.detalhe.imovel"/></strong>		                                    
			                                </div>											
										</div>	
									</c:when>
								</c:choose>   								
							</div>
							<!-- /.END Descricao -->
							
							<!-- /.START Mapa -->
							<div class="panel rounded shadow">
								<div class="panel-heading">			                                	
										<h3 class="panel-title">
											<div class="pull-left">
	                              				<spring:message code="lbl.title.aba.det.mapa.imovel"/>
	                               			</div>
	                               				
	                              			<div class="pull-right">
	                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(1);" style=""><i class="fa fa-question" ></i></a>
	                              			</div>
	                              			<br>	
										</h3>			                                    	
								</div><!-- /.panel-heading -->	
							
								<c:choose>
								
									<c:when test="${((imovelForm.latitude != null) && (imovelForm.longitude != null))}">
										<div class="panel-body panel panel-info rounded shadow">
											<div id="result"></div>
											<br/>
											<div style="width:830px;height:400px" id="map"></div>		
											
											<script>
											function initMap(){
												var longi = ${imovelForm.longitude};
												 var lat = ${imovelForm.latitude};
												 var latlng = new google.maps.LatLng(lat, longi);
												 var myOptions = {
												   zoom: 14,
												   center: latlng,
												   mapTypeId: google.maps.MapTypeId.ROADMAP
												 };
												var map = new google.maps.Map(document.getElementById("map"),myOptions);
												 var marker = new google.maps.Marker({
												   position: latlng,
												   map: map,
												   title:"my hometown, Malim Nawar!"
												 });
											}
											</script>
											
											<script async defer
											    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAoIntYq8CHlVWThpYtElySNBKyXRpZ9M0&callback=initMap">
											    </script>
											
										</div>
									</c:when>
									
									<c:when test="${imovelForm.latitude == null}">
										<div class="panel-body panel panel-info rounded shadow">
											<div class="callout callout-warning">
			                                    <strong><spring:message code="msg.mapa.vazio.detalhe.imovel"/></strong>		                                    
			                                </div>											
										</div>
									
									     </br> </br> 
									</c:when>
								</c:choose>														   
							</div>							
							<!-- /.END Mapa -->			
						
                            
                            <!-- /.START Informação de Contato do Imóvel -- Dono Imovel -->
							<c:if test="${(((imovelForm.usuarioDonoImovel.perfil == 'P') && 
											(imovelForm.usuarioIntermediador == null ) ) || 
											(imovelForm.usuarioDonoImovel.perfil != 'P') || 
											(imovelForm.usuarioDonoImovel.id == usuario.id) )}">
								<div class="panel rounded shadow">
									<div class="panel-heading">
										<h3 class="panel-title">
											<div class="pull-left">
                               					<spring:message code="lbl.title.contato.detalhe.imoveis"/>
                                			</div>	
                               				<div class="pull-right">
                               					<a href="#a" class="btn btn-sm"  onClick="mostrarModal(10);" style=""><i class="fa fa-question" ></i></a>
                               				</div>
                               				<br>
										</h3>
									</div> 
									<div class="panel-body">
									    <div class="inner-all">
	                                        <ul class="list-unstyled">
	                                            <li class="text-center">
	                                            	<a href="${urlUsuario}/detalhesUsuario/${imovelForm.usuarioDonoImovel.id}">  
														<img data-no-retina="" class="img-square img-bordered-black" src="data:image/jpeg;base64,${imovelForm.usuarioDonoImovel.imagemArquivo}" style="width: 400px;  ">	                				
													</a>
	                                            </li>
	                                            <li class="text-center">
	                                                <a href="${urlUsuario}/detalhesUsuario/${imovelForm.usuarioDonoImovel.id}">
														<h4 class="text-capitalize">${imovelForm.usuarioDonoImovel.nome}</h4> 	                				
													</a>
													
													<p class="text-muted text-capitalize">${imovelForm.usuarioDonoImovel.perfilFmt} </p>
													
													<c:choose>
														<c:when test="${imovelForm.usuarioDonoImovel.telefone != ''}">
															<p class="text-muted text-capitalize"><i class="fa fa-phone"></i> ${imovelForm.usuarioDonoImovel.telefone}</p>
														</c:when>
														
														<c:when test="${imovelForm.usuarioDonoImovel.telefone == ''}">  
															<p class="text-muted text-capitalize"><i class="fa fa-phone"></i> <spring:message code="lbl.nao.informado.geral"/></p>
														</c:when>
													</c:choose>
													
													<c:choose>
														<c:when test="${imovelForm.usuarioDonoImovel.email != ''}">
															<p class="text-muted text-capitalize"><i class="fa fa-envelope"></i> ${imovelForm.usuarioDonoImovel.email}</p>
														</c:when>
														
														<c:when test="${imovelForm.usuarioDonoImovel.email == ''}">
															<p class="text-muted text-capitalize"><i class="fa fa-envelope"></i> <spring:message code="lbl.nao.informado.geral"/></p>
														</c:when>
													</c:choose>
	                                                
	                                                <c:choose>	                                               
	                                                	
	                                                	<c:when test="${(imovelForm.usuarioDonoImovel.perfil == 'I')}">
	                                                		<p class="text-muted text-capitalize"><spring:message code="lbl.cnpj"/>: ${imovelForm.usuarioDonoImovel.cpf}</p>
	                                                	</c:when>
	                                                	
	                                                	<c:when test="${(imovelForm.usuarioDonoImovel.perfil == 'C')}">
	                                                		<p class="text-muted text-capitalize"><spring:message code="lbl.creci"/>: ${imovelForm.usuarioDonoImovel.creci}</p>
	                                                	</c:when>
	                                                	
	                                                </c:choose>	                                                
	                                            </li>
	                                        </ul>
	                                    </div>
									</div>
								</div>
							</c:if>							
							<!-- /.END Informação de Contato do Imóvel -- Dono Imovel  -->
							
							<!-- /.START Informação de Contato do Imóvel -- Intermediador (Corretor ou Imobiliaria)-->
							<c:if test="${( (imovelForm.usuarioDonoImovel.perfil == 'P') && (imovelForm.usuarioIntermediador != null) )}">
							
							<div class="panel rounded shadow">
								<div class="panel-heading">
										<h3 class="panel-title">
											<spring:message code="lbl.title.contato.detalhe.imoveis"/>
											<a href="#a" class="btn btn-sm"  onClick="mostrarModal(10);" style="margin-left: 200px;"><i class="fa fa-question" ></i></a>
										</h3>
									</div>
									<div class="panel-body">
									    <div class="inner-all">
	                                        <ul class="list-unstyled">  
	                                            <li class="text-center">
	                                                <img data-no-retina="" class="img-square img-bordered-black" src="data:image/jpeg;base64,${imovelForm.usuarioIntermediador.imagemArquivo}" style="width: 90px;  ">
	                                            </li>
	                                            <li class="text-center">
	                                                <h4 class="text-capitalize">${imovelForm.usuarioIntermediador.nome}</h4>
	                                                <p class="text-muted text-capitalize"><i class="fa fa-phone"></i> ${imovelForm.usuarioIntermediador.telefone}</p>
	                                                <p class="text-muted text-capitalize"><i class="fa fa-envelope"></i> ${imovelForm.usuarioIntermediador.email}</p>
	                                                <c:if test="${((imovelForm.usuarioIntermediador.perfil == 'C') || (imovelForm.usuarioIntermediador.perfil == 'P'))}">
	                                                    <p class="text-muted text-capitalize"><spring:message code="lbl.cpf"/>:</strong> ${imovelForm.usuarioIntermediador.cpf}</p>
	                                                </c:if>
	                                                <c:if test="${(imovelForm.usuarioIntermediador.perfil == 'I')}">
	                                                    <p class="text-muted text-capitalize"><spring:message code="lbl.cnpj"/>: ${imovelForm.usuarioIntermediador.cpf}</p>
	                                                </c:if>
	                                                <c:if test="${(imovelForm.usuarioIntermediador.perfil == 'C')}">
	                                                    <p class="text-muted text-capitalize"><spring:message code="lbl.creci"/>: ${imovelForm.usuarioIntermediador.creci}</p>
	                                                </c:if>
	                                            </li>
	                                        </ul>
	                                    </div>
									</div>
								</div>
							</c:if>							
							<!-- /.END Informação de Contato do Imóvel -- Intermediador (Corretor ou Imobiliaria)-->
							
						</div>
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
           
            
          <!-- Start optional size modal element - Modal Galeria Fotos -->
            <div id="idModalGaleriaFotos" class="modal fade bs-modal-galeria-fotos" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="panel panel-tab panel-tab-double">
                            <!-- Start tabs heading -->
                            <div class="panel-heading no-padding">
                                <ul class="nav nav-tabs">
                                    <li class="active">
                                        <a href="#tab2-1" data-toggle="tab">
                                            <i class="fa fa-file-image-o"></i>
                                            <div>
                                                <span class="text-strong"><spring:message code="lbl.title.link.alterar.galeria.foto.imovel" /></span>                                                
                                            </div>
                                        </a>
                                    </li>                                                                       
                                </ul>
                            </div>
                            <!--/ End tabs heading -->

                            <!-- Start tabs content -->
                            <div class="panel-body">
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="tab2-1">
                                    	<c:choose>
                                    		<c:when test="${not empty imovelForm.listaFotos}">
                                    			<!-- thumb navigation carousel -->
												    <div class="col-md-12 hidden-sm hidden-xs" id="slider-thumbs">
												        
												            <!-- thumb navigation carousel items -->
												          <ul class="list-inline">										          
														          <c:forEach var="foto" items="${imovelForm.listaFotos}" varStatus="passo">		                                     
														               <c:if test="${passo.count == 1}">
																           <li> <a id="carousel-selector-${((passo.count) - 1)}"  class="selected">
																            <img class="responsive" src="data:image/jpeg;base64,${foto.imagemArquivo}" style="width: 80px; height: 60px;" >		            
																          </a></li>  
														               </c:if>
														              
														              <c:if test="${passo.count > 1}">
															              <li> <a id="carousel-selector-${((passo.count) - 1)}">
																            <img class="responsive" src="data:image/jpeg;base64,${foto.imagemArquivo}" style="width: 80px; height: 60px;" >
																            
																          </a></li>
														              </c:if>
														         </c:forEach>										        
												            </ul>
												    </div>
		  
												    <!-- main slider carousel -->
												    <div class="row">
												        <div class="col-md-12" id="slider">										            
												                <div class="col-md-12" id="carousel-bounding-box">
												                    <div id="myCarousel" class="carousel slide">
												                        <!-- main slider carousel items -->
												                        <div class="carousel-inner">
												                        	 <c:forEach var="foto" items="${imovelForm.listaFotos}" varStatus="passo" >                                   
												                                   <c:if test="${passo.count == 1}">
												                                   		<div class="active item" data-slide-number="${((passo.count) - 1)}">
															                               <img class="responsive" src="data:image/jpeg;base64,${foto.imagemArquivo}" style="width: 1200px; height: 380px;" >
															                            </div>
												                                   </c:if>
												                                  
												                                  <c:if test="${passo.count > 1}">
													                                  	<div class="item" data-slide-number="${((passo.count) - 1)}">
															                               <img class="responsive" src="data:image/jpeg;base64,${foto.imagemArquivo}" style="width: 1200px; height: 380px;" >
															                            </div>
												                                  </c:if>
												                                  
												                             </c:forEach>
												                        </div>
												                        <!-- main slider carousel nav controls --> <a class="carousel-control left" href="#myCarousel" data-slide="prev">‹</a>
												
												                        <a class="carousel-control right" href="#myCarousel" data-slide="next">›</a>
												                    </div>
												                </div>										
												        </div>
												    </div>
												    <!--/main slider carousel-->
                                    		</c:when>
                                    		
                                    		<c:when test="${empty imovelForm.listaFotos}">
                                    			<div class="callout callout-warning">
			                                    <strong><spring:message code="msg.nenhuma.foto.galeria.foto.imoveis"/></strong>
			                                </div>
                                    		</c:when>
                                    		
                                    	</c:choose>                                            
                                    </div>                                                           
                                </div>
                            </div>
                            <!--/ End tabs content -->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.title.modal.fechar"/></button>                            
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->            
            
         <!-- End optional size modal element - Modal Galeria Fotos -->   
         
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
            
            <!-- Start optional size modal element - fechar negocio -->
            <div id="idModalFecharNegocio" class="modal fade bs-example-modal-lg-confirmacao-fechar-negocio" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdImovel" readonly="readonly" name="modIdImovel">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.fechar.negocio"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.fechar.negocio"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="notificarFecharNegocio();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoFecharNegocioErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - fechar negocio -->
         
          <!-- Start optional size modal element - marcar visita -->
            <div id="idModalMarcarVisita" class="modal fade bs-example-modal-lg-confirmacao-fechar-negocio" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdImovel" readonly="readonly" name="modIdImovel">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.marcar.visita"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.marcar.visita"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="notificarMarcarVisita();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoMarcarVisitaErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - fechar negocio -->         
         
         
          <!-- Start optional size modal element - procurar possiveis compradores-->
            <div id="idModalprocurarInteressados"  class="modal fade bs-example-modal-lg-procura-compradores" tabindex="-1" role="dialog" aria-hidden="true">
                <input type="hidden" id="modIdImovel" readonly="readonly" name="modIdImovel">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.procurar.interessados"/></h4>
                        </div>
                        <div class="modal-body">
                            <p><spring:message code="lbl.modal.pergunta.procurar.interessados"/></p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
                            <button type="button" class="btn btn-theme" onClick="procurarInteressados();"><spring:message code="lbl.sim"/></button>                            
                        </div>
						
						<div id="msgRetornoprocurarInteressados" cssClass="errorEntrada"  ></div>  
						
                    </div><!-- /.modal-content -->					
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->   
           <!-- End optional size modal element - procurar possiveis compradores-->
         

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