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

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel/ordenaMeusImoveis" var="urlOrdenaMeusImoveis"/>
<spring:url value="/imovelComentario" var="urlImovelComentario"/>
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/mensagem" var="urlMensagem"/>
<spring:url var="urlAdmin" value="/admin"/>

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

<script type="text/javascript"    src="http://maps.googleapis.com/maps/api/js?sensor=false&key=12345"></script>

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
	 var longi = ${imovelForm.longitude};
	 var lat = ${imovelForm.latitude};
	 var latlng = new google.maps.LatLng(lat, longi);
	 var myOptions = {
	   zoom: 13,
	   center: latlng,
	   mapTypeId: google.maps.MapTypeId.ROADMAP
	 };
	var map = new google.maps.Map(document.getElementById("map"),myOptions);
	 var marker = new google.maps.Marker({
	   position: latlng,
	   map: map,
	   title:"my hometown, Malim Nawar!"
	 });
});

function prepararModalAnuncioImovel(){
	$("#msgErroDataInicioAnuncio").html("");	
	$("#msgErroDataFimAnuncio").html("");
	$("#msgAnuncioImovel").html("");
	$("#idModalConfAnuncioImovel").modal("show");	
}

function substituir(str){
	
	 var target = "/";
	 var replacement = "-";
	 var i = 0, length = str.length;
	 
	 for (i; i < length; i++) {	 
	   string = string.replace(target, replacement);
	 }
	 
	 return string;
}


function adicionarConfigAnuncioImovel(){	

	var x = document.getElementById("dataInicioAnuncio");
	var y = document.getElementById("dataFimAnuncio");
	
	if (x.value == ''){
		$('#msgAnuncioImovel').html('Campo deve ser obrigatorio');
	}
	
	if (y.value == ''){
		$('#msgAnuncioImovel').html('Campo deve ser obrigatorio');
	}
	
	$.ajax({  
	    type: 'GET',	
	    url: '${urlImovel}/adicionarConfiguracaoAnuncioImovel/' + x.value + '/' + y.value,
         dataType: 'json',
         success: function(data){	        	 
        	 if ( data == 'ok') {
        		 location.reload();
        	 }
        	 else  {
	        	 $('#msgAnuncioImovel').html(data);
	         }	
         },	      
     });			  
} 


function mostrarModal(id){
	
	if (id == 0){
		$('#msgModal').html("Descrição resumida sobre o imóvel");
	}
	else if ( id == 1){
		$('#msgModal').html("Exibição da localização do imóvel");
	}
	else if ( id == 2){
		$('#msgModal').html("Lista de todas propostas recebidas pelo imóvel");
	}
	else if ( id == 3){
		$('#msgModal').html("Lista de propostas que você lançou sobre o imóvel");
	}
	else if ( id == 4){
		$('#msgModal').html("Informações sobre a intermediação do imóvel");
	}
	else if ( id == 5){
		$('#msgModal').html("Informações sobre a parceria do imóvel");
	}
	else if ( id == 6){
		$('#msgModal').html("Lista de usuários que visitaram o imóvel. ");
	}
	else if ( id == 7){
		$('#msgModal').html("Lista de usuários interessados sobre o imóvel. ");
	}
	else if ( id == 8){
		$('#msgModal').html("Lista de comentários sobre o imóvel. ");
	}
	else if ( id == 9){
		$('#msgModal').html("Informações de contato de responsável pelo imóvel. ");
	}
	else if ( id == 10){
		$('#msgModal').html("Informações de contato de responsável pelo imóvel. ");
	}
	
	$("#idModalItem").modal("show");
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
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.detalhes.imovel"/> </h2>                                                                                                                                                
                </div><!-- /.header-content -->
                                
	                <!-- Start body content -->
	                <div class="body-content animated fadeIn">
	              		
	              		<div class="row">
						<!-- START  -->
						<div class="col-lg-12 col-md-12 col-sm-12">
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
                                    	<a href="#a" onClick="prepararModalGaleriaFotos()" style="font-size:x-large;" class="meta-action"><i class="fa fa-file-image-o" style="color:gray" title="<spring:message code="lbl.title.link.alterar.galeria.foto.imovel"/>"></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.title.link.alterar.galeria.foto.imovel"/> </font></a> &nbsp;&nbsp;
                                    </div>
                                    
                                    <br> <br>

								</div>
								
								<!-- Start Galeria Fotos -->
								<div class="panel-body">
									<div class="col-lg-7 col-md-8 col-sm-6">
                                      	<div id="owl-demo" class="owl-carousel owl-theme">                                             
	                                             <div class="item"><img class="responsive" src="data:image/jpeg;base64,${imovelForm.imagemArquivo}"  style="max-height:550px; height: 385px;"></div>                                            
	                                     </div>
	                				</div>
	                				
	                				<div class="col-lg-5 col-md-5 col-sm-5"> 
	                                     <table class="table table-striped" style="margin-top:10px;">
                                                <tbody>
                                                	<tr>  
                                                        <td class="text-left"><spring:message code="lbl.valor.imovel.resum" /></td>
                                                        <td class="text-right"><strong>R$<fmt:formatNumber value="${imovelForm.valorImovel}" pattern="#,##0.00;-0"/></strong></td>
                                                    </tr>
                                                    
                                                   	<tr>
                                                        <td class="text-left"><spring:message code="lbl.valor.condominio.resum" /></td>
                                                        <td class="text-right">R$<fmt:formatNumber value="${imovelForm.valorCondominio}" pattern="#,##0.00;-0"/></td>
                                                    </tr>
                                                
                                                   	<tr>
                                                        <td class="text-left"><spring:message code="lbl.valor.iptu.resum"/></td>
                                                        <td class="text-right">R$<fmt:formatNumber value="${imovelForm.valorIptu}" pattern="#,##0.00;-0"/></td>
                                                    </tr>
                                                    
                                                    <tr>
                                                        <td class="text-left"><spring:message code="lbl.area.m2.resum"/></td>
                                                        <td class="text-right"><fmt:formatNumber value="${imovelForm.area}" type="number"/> m<sup>2</sup></td>
	                                                </tr>
                                                  
                                                   	<tr>
                                                        <td class="text-left"><spring:message code="lbl.quartos.dormitorios.resum"/></td>
                                                        <td class="text-right">${imovelForm.quantQuartos}</td>
                                                    </tr>   
                                                    
                                                   	 <tr>
                                                        <td class="text-left"><spring:message code="lbl.vagas.garagem.resum"/></td>
                                                        <td class="text-right">${imovelForm.quantGaragem} <spring:message code="lbl.num.vagas"/></td>
                                                     </tr>
                                                    
                                                     <tr>
                                                        <td class="text-left"><spring:message code="lbl.buscar.imovel.banheiros"/></td>
                                                        <td class="text-right"> ${imovelForm.quantBanheiro} </td>
	                                                 </tr>
	                                                 
                                                   	 <tr>
                                                        <td class="text-left"><spring:message code="lbl.suites"/></td>
                                                        <td class="text-right">${imovelForm.quantSuites}</td>
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
						</div>
	              		
						<!-- INICIO ROW 1 -->
						
						<div class="row">
						<div class="col-lg-8 col-md-8 col-sm-12">
						
							<!-- /.START Descricao -->
							<div class="panel rounded shadow">
								<div class="panel-heading">
                                    <h3 class="panel-title">
                                    	<spring:message code="lbl.descricao.imovel"/>
                                     	<a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);" style="margin-left: 560px;"><i class="fa fa-question" ></i></a>
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
											<spring:message code="lbl.title.aba.det.mapa.imovel"/>
											<a href="#a" class="btn btn-sm"  onClick="mostrarModal(1);" style="margin-left: 540px;"><i class="fa fa-question" ></i></a>
										</h3>			                                    	
								</div><!-- /.panel-heading -->	
								
								<c:choose>
									<c:when test="${((imovelForm.latitude != 0) && (imovelForm.longitude != 0))}">
										<div class="panel-body panel panel-info rounded shadow">
											<div id="result"></div>
											<br/>
											<div style="width:700px;height:400px" id="map"></div>											
										</div>
									</c:when>
									
									<c:when test="${((imovelForm.latitude == 0) && (imovelForm.longitude == 0))}">
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
							
						<!-- /.START Configuração Anuncio Imóvel --> 
							 <div class="panel rounded shadow">
                                <div class="panel-heading">         		
                               			<h3 class="panel-title">
                               				<spring:message code="lbl.title.aba.det.configuracao.anuncio.imovel"/>
                               				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(2);" style="margin-left: 375px;"><i class="fa fa-question" ></i></a>
                               			</h3>	
                                </div>
                                <div class="panel-body">
                                	<c:choose>
                                		<c:when test="${ empty imovelForm.listaImovelAnuncio }">
                               				<div class="callout callout-warning">
			                                    <strong><spring:message code="msg.nenhum.cadastro.anuncio.imovel"/></strong>		                                    
			                                </div>
	                                      </br> </br>	
	                                      
	                                       <div class="form-group">
                                               <input type="button" class="btn btn-primary" onClick="prepararModalAnuncioImovel();" value='<spring:message code="btn.modal.adicionar.conf.anuncio.imovel"/> '>
                                            </div>
                                		</c:when>
                                		
                                		<c:when test="${not empty imovelForm.listaImovelAnuncio }">
                                			 <table class="table table-striped" >
		                                         <thead>
		                                            <tr>
		                                               <th style="width: 30%; text-align: center;"><strong><spring:message code="lbl.imovel.data.programada.anuncio"/></strong></th>
		                                               <th style="width: 30%; text-align: center;"><strong><spring:message code="lbl.table.data.cadastro.imovel.destaque"/></strong></th>
		                                               <th style="width: 20%; text-align: center;"><strong><spring:message code="lbl.table.data.status.imovel.destaque"/></strong></th>		                                               
		                                            </tr>
		                                         </thead>
		
		                                         <tbody>
		                                            <c:forEach var="imovel" items="${imovelForm.listaImovelAnuncio}">
		                                               <tr>
		                                                  <td style="text-align: center;"><small><fmt:formatDate value='${imovel.dataDestaque}' pattern='dd/MM/yyyy'/></small></td>
		                                                  <td style="text-align: center;"><small><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></small></td>
		                                                  <td style="text-align: center;"><small>${imovel.status}</small></td>		                                                  
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>
		                                      </br> </br> 
		                                      
		                                      <ul class="media-list comment-list">
			                                        <li class="media">                                                                                        
			                                            <div class="mb-20"></div>
			                                            <form class="form-horizontal mb-20" role="form">	                                                                                           
						                                         
				                                            <div class="form-group">
				                                               <input type="button" class="btn btn-primary" onClick="prepararModalAnuncioImovel();" value='<spring:message code="btn.modal.adicionar.conf.anuncio.imovel"/> '>
				                                            </div>
						                                      
			                                            </form>
			                                        </li>
			                                    </ul>
		                                      
		                                   </c:when>		                                   
                                	</c:choose>
                                </div>
                            </div>
						
						<!-- /.END Configuração Anuncio Imóvel --> 								
                         
                         <!-- /.START Propostas --> 
                            <div class="panel rounded shadow">
                                <div class="panel-heading">         		
                               			<h3 class="panel-title">
                               				<spring:message code="lbl.title.aba.det.propostas.imovel.dono.imovel"/>
                               				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(2);" style="margin-left: 555px;"><i class="fa fa-question" ></i></a>
                               			</h3>	
                                </div>
                                <div class="panel-body">
                                	<c:choose>
                                		<c:when test="${ empty imovelForm.listaPropostas }">
                               				<div class="callout callout-warning">
			                                    <strong><spring:message code="msg.nenhuma.proposta.detalhe.imovel"/></strong>		                                    
			                                </div>
	                                      </br> </br>	
                                		</c:when>
                                		
                                		<c:when test="${not empty imovelForm.listaPropostas }">
                                			 <table class="table table-striped" >
		                                         <thead>
		                                            <tr>
		                                               <th style="width: 15%;"><strong><spring:message code="lbl.data.proposta"/></strong></th>
		                                               <th style="width: 25%; text-align: center;"><strong><spring:message code="lbl.valor.proposta"/></strong></th>
		                                               <th style="width: 40%"><strong><spring:message code="lbl.observacao.proposta.imovel"/></strong></th>
		                                               <th style="width: 10%" class="text-center"><strong><spring:message code="lbl.table.data.acoes"/></strong></th>
		                                            </tr>
		                                         </thead>
		
		                                         <tbody>
		                                            <c:forEach var="imovel" items="${imovelForm.listaPropostas}">
		                                               <tr>
		                                                  <td><small><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></small></td>
		                                                  <td ><small>R$<fmt:formatNumber value="${imovel.valorProposta}" pattern="#,##0.00;-0"/></small></td>
		                                                  <td><small>${imovel.observacao}</small></td>		                                                  
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>
		                                      </br> </br> 
		                                   </c:when>		                                   
                                	</c:choose>
                                </div>
                            </div>
                          <!-- /.END Propostas -->
              				
                          <!-- /.START Intermediação --> 
                          <c:if test="${((imovelForm.perfilUsuario == 'P'))}">
                				<div class="panel rounded shadow">
		                                <div class="panel-heading">
		                                    <h3 class="panel-title">
		                                    	<spring:message code="lbl.title.aba.det.intermediacoes.imovel"/>
		                                    	<a href="#a" class="btn btn-sm"  onClick="mostrarModal(4);" style="margin-left: 510px;"><i class="fa fa-question" ></i></a>	
		                                    </h3>
		                                </div>
		                                <div class="panel-body">
		                                    <ul class="media-list comment-list">
		
		                                        <li class="media">
		                                            <div class="media-left">
		                                                
		                                            </div>
		                                            <div class="media-body">
		                                                <h4><spring:message code="lbl.condicao.intermediacao"/></h4>		                                                
		                                                <p><strong>Descrição2: </strong> ${imovelForm.descAceitaCorretagemParceria}</p>
		                                            </div>
		                                        </li><!-- media -->		
		                                    </ul>
		                                    <br/>
		                                    
		                                    <c:choose>
		                                    	<c:when test="${ empty imovelForm.listaIntermediacao}">
		                                    			<div class="callout callout-warning">
						                                    <strong> <spring:message code="lbl.nenhuma.intermediacao"/></strong>		                                    
						                                </div>
				                                      </br> </br>		                                    
		                                    	</c:when>
		                                    	
		                                    	<c:when test="${  not empty imovelForm.listaIntermediacao }">
		                                    		 <table class="table table-striped" >
				                                         <thead>
				                                            <tr>	                                               
				                                                <th class="text-center"></th>
																<th><small><spring:message code="lbl.nome.usuario"/></th>
																<th><small><spring:message code="lbl.data.sol.intermediacao"/></th>
																<th><small><spring:message code="lbl.data.res.intermediacao"/></th>
																<th><small><spring:message code="lbl.status.intermediacao"/></th>
				                                            </tr>
				                                         </thead>
				
				                                         <tbody>		                                            
				                                               <c:forEach var="imovel" items="${imovelForm.listaIntermediacao}">
																	<tr>
																		<td class="text-center">  
																			<img src="data:image/jpeg;base64,${imovel.usuarioSolicitante.imagemArquivo}" style="width: 60px; height: 50px;"  class="img-responsive img-thumbnail"/>
																		</td>								                                            
																		<td><small>${imovel.usuarioSolicitante.nome}</small></td>								                                          
																		<td><small><fmt:formatDate value='${imovel.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
																		<td><small><fmt:formatDate value='${imovel.dataResposta}' pattern='dd/MM/yyyy'/></small></td>								                                            
																		<td><small>${imovel.status}</small></td>								                                          
																	</tr>
																</c:forEach>	                                            
				                                         </tbody>
				                                      </table>				              
		                                    	</c:when>
		                                    </c:choose>		                                                      
		                                </div>
		                            </div>           		                     
                          </c:if>
                          <!-- /.END Intermediação -->
                          
                          <!-- /.START Parceria -->
                           <c:if test="${(imovelForm.perfilUsuario != 'padrao')}">
                          		<div class="panel rounded shadow">
		                                <div class="panel-heading">
		                                    <h3 class="panel-title">
		                                    	<spring:message code="lbl.title.aba.det.parcerias.imovel"/>
		                                    	<a href="#a" class="btn btn-sm"  onClick="mostrarModal(4);" style="margin-left: 565px;"><i class="fa fa-question" ></i></a>	
		                                    </h3>
		                                </div>
		                                <div class="panel-body">
		                                    <ul class="media-list comment-list">
		
		                                        <li class="media">
		                                            <div class="media-left">
		                                                
		                                            </div>
		                                            <div class="media-body">
		                                                <h4><spring:message code="lbl.condicao.intermediacao"/></h4>		                                                
		                                                <p><strong>Descrição2: </strong> ${imovelForm.descAceitaCorretagemParceria}</p>
		                                            </div>
		                                        </li><!-- media -->		
		                                    </ul>
		                                    <br/>
		                                    
		                                    <c:choose>
		                                    	<c:when test="${ empty imovelForm.listaParceria}">
		                                    			<div class="callout callout-warning">
						                                    <strong> <spring:message code="lbl.nenhuma.parceria"/></strong>		                                    
						                                </div>
				                                      </br> </br>		                                    
		                                    	</c:when>
		                                    	
		                                    	<c:when test="${  not empty imovelForm.listaParceria}">
		                                    		 <table class="table table-striped" >
				                                         <thead>
				                                            <tr>	                                               
				                                                <th class="text-center"></th>
																<th><small><spring:message code="lbl.nome.usuario"/></th>
																<th><small><spring:message code="lbl.data.sol.parceria"/></th>
																<th><small><spring:message code="lbl.data.res.parceria"/></th>
																<th><small><spring:message code="lbl.status.parceria"/></th>						                                            
				                                            </tr>
				                                         </thead>
				
				                                         <tbody>		                                            
				                                               <c:forEach var="imovel" items="${imovelForm.listaParceria}">
																	<tr>
																		<td class="text-center">
																			<img src="${context}/${imovel.usuarioSolicitante.imagemArquivo}" style="width: 60px; height: 50px;"  class="img-responsive img-thumbnail"/>
																		</td>								                                            
																		<td><small>${imovel.usuarioSolicitante.nome}</small></td>								                                          
																		<td><small><fmt:formatDate value='${imovel.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
																		<td><small><fmt:formatDate value='${imovel.dataResposta}' pattern='dd/MM/yyyy'/></small></td>								                                            
																		<td><small>${imovel.status}</small></td>								                                          
																	</tr>
																</c:forEach>	                                            
				                                         </tbody>
				                                      </table>				              
		                                    	</c:when>
		                                    </c:choose>		                                                      
		                                </div>
		                            </div>                          
                          </c:if>
                          <!-- /.END Parceria -->
                         
                          <!-- /.START Visitas -->
                          
                              <div class="panel rounded shadow">	
								<div class="panel-heading">                                   
                                      <h3 class="panel-title">
                                      	<spring:message code="lbl.title.aba.det.visualizacoes.imovel"/>
                                      	<a href="#a" class="btn btn-sm"  onClick="mostrarModal(6);" style="margin-left: 530px;"><i class="fa fa-question" ></i></a>
                                      </h3>
                                </div>
                                <div class="panel-body">                                	
                                	<c:choose>
                                		<c:when test="${ empty imovelForm.listaVisita }">                                			
                               			    <div class="callout callout-warning">
			                                    <strong><spring:message code="msg.nenhuma.visualizacao"/></strong>		                                    
			                                </div>
	                                      </br> </br>	
                                		</c:when>
                                		
                                		<c:when test="${ not empty imovelForm.listaVisita }">
                                			<table class="table table-striped" >
		                                         <thead>
		                                            <tr>
		                                               <th style="width: 5%;"> </th>
		                                               <th style="width: 10%;"><strong><spring:message code="lbl.visualizacao.nome.usuario"/></strong></th>
		                                               <th style="width: 25%"><strong><strong><spring:message code="lbl.data.visualizacao"/></strong></th>                                               
		                                            </tr>
		                                         </thead>
		
		                                         <tbody>
		                                            <c:forEach var="imovelVisita" items="${imovelForm.listaVisita}">
		                                               <tr>
		                                               	  <td class="text-center">
																<a href="${urlAdmin}/detalhesUsuarioAdmin/${imovelVisita.usuario.id}">
																	<img src="${context}/${imovelVisita.usuario.imagemArquivo}" style="width: 60px; height: 50px; " />	                				
																</a> 
														  </td>	
		                                                  <td><small><a href="${urlAdmin}/detalhesUsuarioAdmin/${imovelVisita.usuario.id}">
																		${imovelVisita.usuario.nome}	                				
																	</a>  
															  </small>
														  </td>
		                                                  <td ><small><fmt:formatDate value='${imovelVisita.dataVisita}' pattern='dd/MM/yyyy'/></small></td>
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>	
                                		</c:when>
                                	</c:choose>
                                </div>
                               </div> 
                            
                          <!-- /.END Visitas -->  
                          
                          <!-- /.START Usuarios Interessados-->
							  <div class="panel rounded shadow">	
								<div class="panel-heading">                                   
                                      <h3 class="panel-title">
                                      	<spring:message code="lbl.usuarios.interessados"/>
                                      	<a href="#a" class="btn btn-sm"  onClick="mostrarModal(7);" style="margin-left: 380px;"><i class="fa fa-question" ></i></a>
                                      </h3>
                                </div>
                                <div class="panel-body">
                                	<c:choose>
                                		<c:when test="${empty imovelForm.listaUsuariosInteressados }">
                                			    <div class="callout callout-warning">
				                                    <strong><spring:message code="msg.nenhum.usuario.interessado"/></strong>		                                    
				                                </div>
		                                      </br> </br>	
                                		</c:when>
                                		<c:when test="${ not empty imovelForm.listaUsuariosInteressados }">
                                			 <table class="table table-striped" >
		                                         <thead>
		                                            <tr>
		                                               <th style="width: 5%;"> </th>
		                                               <th style="width: 10%;"><strong><spring:message code="lbl.nome.usuario.lista.interesse"/></strong></th>
		                                               <th style="width: 25%"><strong><strong><spring:message code="lbl.data.interesse"/></strong></th>                                               
		                                            </tr>
		                                         </thead>
		
		                                         <tbody>
		                                            <c:forEach var="imovelFavorito" items="${imovelForm.listaUsuariosInteressados}">
		                                               <tr>
		                                               	  <td class="text-center">
																<a href="${urlAdmin}/detalhesUsuarioAdmin/${imovelFavorito.usuario.id}">
																	<img src="${context}/${imovelFavorito.usuario.imagemArquivo}" style="width: 60px; height: 50px; " />	                				
																</a> 
														  </td>	
		                                                  <td><small><a href="${urlAdmin}/detalhesUsuarioAdmin/${imovelFavorito.usuario.id}">
																		${imovelFavorito.usuario.nome}	                				
																	</a> 
															  </small>
														  </td>
		                                                  <td ><small><fmt:formatDate value='${imovelFavorito.dataInteresse}' pattern='dd/MM/yyyy'/></small></td>
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>
                                		</c:when>
                                	</c:choose>
                                   
                                </div>
                               </div>
						  <!-- /.END Usuarios Interessados-->	
						  
						  <!-- /.START Comentarios -->	
							<div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <h3 class="panel-title">
                                    	<spring:message code="lbl.title.aba.det.comentarios.imovel"/>
                                    	<a href="#a" class="btn btn-sm"  onClick="mostrarModal(8);" style="margin-left: 530px;"><i class="fa fa-question" ></i></a>
                                    </h3>
                                </div>
                                
                                <c:choose>
                                	<c:when test="${not empty imovelForm.listaComentario }">
                                		<div class="panel-body">
		                                    <ul class="media-list comment-list">
												<c:forEach var="imovel" items="${imovelForm.listaComentario}">
													<li class="media">
			                                            <div class="media-left">
			                                                <a href="#">
			                                                    <img class="media-object thumbnail" src="${context}${imovel.imovel.imagemArquivo}" style="width: 50px; height: 60px;"  />
			                                                </a>
			                                            </div>
			                                            <div class="media-body">
			                                                <h4>${imovel.usuarioComentario.nome}</h4>
			                                                <small class="text-muted"><fmt:formatDate value='${imovel.dataComentario}' pattern='dd/MM/yyyy HH:mm:ss'/></small>
			                                                <p>${imovel.comentario}</p>
			                                            </div>
			                                        </li><!-- media -->											
												</c:forEach>
		                                    </ul>
		                                    <br/>
		                                </div>	
                                	</c:when>
                                	
                                	<c:when test="${empty imovelForm.listaComentario }">
                               			<div class="panel-body">
		                                	 <div class="callout callout-warning">
				                                    <strong><spring:message code="msg.nenhum.comentario.imovel"/></strong>		                                    
				                              </div>
		                                    </br> </br>		              
	                                	</div>
                                	</c:when>
                                	
                                </c:choose>
                            </div>                           
                         <!-- /.END Comentarios -->  
						</div>						
						
						<div class="col-lg-4 col-md-4 col-sm-12">
							<!-- /.START Informação de Contato do Imóvel -- Dono Imovel -->
							<c:if test="${(((imovelForm.usuarioDonoImovel.perfil == 'P') && (imovelForm.usuarioIntermediador == null ) ) || (imovelForm.usuarioDonoImovel.perfil != 'P') )}">
								<div class="panel rounded shadow">
									<div class="panel-heading">
										<h3 class="panel-title">
											<spring:message code="lbl.title.contato.detalhe.imoveis"/>
											<a href="#a" class="btn btn-sm"  onClick="mostrarModal(9);" style="margin-left: 200px;"><i class="fa fa-question" ></i></a>
										</h3>
									</div> 
									<div class="panel-body">
									    <div class="inner-all">
	                                        <ul class="list-unstyled">
	                                            <li class="text-center">
	                                            	<a href="${urlUsuario}/detalhesUsuario/${imovelForm.usuarioDonoImovel.id}">
														<img data-no-retina="" class="img-square img-bordered-black" src="${context}${imovelForm.usuarioDonoImovel.imagemArquivo}" style="width: 90px;  ">	                				
													</a>
	                                            </li>
	                                            <li class="text-center">
	                                                <a href="${urlUsuario}/detalhesUsuario/${imovelForm.usuarioDonoImovel.id}">
														<h4 class="text-capitalize">${imovelForm.usuarioDonoImovel.nome}</h4> 	                				
													</a>
	                                                
	                                                <p class="text-muted text-capitalize"><i class="fa fa-phone"></i> ${imovelForm.usuarioDonoImovel.telefone}</p>
	                                                <p class="text-muted text-capitalize"><i class="fa fa-envelope"></i> ${imovelForm.usuarioDonoImovel.email}</p>
	                                                <c:if test="${((imovelForm.usuarioDonoImovel.perfil == 'C') || (imovelForm.usuarioDonoImovel.perfil == 'P'))}">
	                                                    <p class="text-muted text-capitalize"><spring:message code="lbl.cpf"/>:</strong> ${imovelForm.usuarioDonoImovel.cpf}</p>
	                                                </c:if>
	                                                <c:if test="${(imovelForm.usuarioDonoImovel.perfil == 'I')}">
	                                                    <p class="text-muted text-capitalize"><spring:message code="lbl.cnpj"/>: ${imovelForm.usuarioDonoImovel.cpf}</p>
	                                                </c:if>
	                                                <c:if test="${(imovelForm.usuarioDonoImovel.perfil == 'C')}">
	                                                    <p class="text-muted text-capitalize"><spring:message code="lbl.creci"/>: ${imovelForm.usuarioDonoImovel.creci}</p>
	                                                </c:if>
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
	                                                <img data-no-retina="" class="img-square img-bordered-black" src="${context}${imovelForm.usuarioIntermediador.imagemArquivo}" style="width: 90px;  ">
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
							
							<c:if test="${imovelForm.idUsuario == usuario.id && not  empty imovelForm.listaUsuariosInteressados}">
                                <div class="panel rounded shadow">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Interessados</h3>
                                    </div>
                                    <div class="panel-body no-padding">
                                        <c:forEach var="imovelFavorito" items="${imovelForm.listaUsuariosInteressados}">
                                            <div class="media inner-all no-margin">
                                                <div class="pull-left">
                                                    <img src="${context}${imovelFavorito.imagemUsuario}" style="width: 90px;">
                                                </div><!-- /.pull-left -->
                                                <div class="media-body">
                                                    <a href="${urlUsuario}/detalhesUsuario/${imovelFavorito.idUsuario}" class="h4">${imovelFavorito.nomeUsuarioInteressado}</a>
                                                    <br/><em class="text-xs text-muted">Interessado em <span class="text-danger"><fmt:formatDate value='${imovelFavorito.dataInteresse}' pattern='dd/MM/yyyy'/></span></em>
                                                </div><!-- /.media-body -->
                                            </div>
                                            <div class="line no-margin"></div>
                                        </c:forEach>
                                    </div>
                                </div>
                                
                            </c:if>
                            
						</div>
					</div>
				   <!-- FIM ROW 1 -->
				   
	                </div>
	                <!-- End body content -->
                
			</div>              
          </section><!-- /#page-content -->
        </section><!-- /#wrapper -->
        
        
        <!-- Start inside form layout -->
            <div id="idModalConfAnuncioImovel" class="modal fade bs-example-modal-form" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.anuncio.imovel"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form" id="input-mask">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.data.inicio.anuncio"/></label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" id="dataInicioAnuncio" >                                            
                                            <div id="msgErroDataInicioAnuncio" cssClass="errorEntrada"  ></div>                                            
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="password-1" class="col-sm-3 control-label"><spring:message code="lbl.data.fim.anuncio"/></label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control " id="dataFimAnuncio">
                                            <div id="msgErroDataFimAnuncio" cssClass="errorEntrada"  ></div>
                                        </div>
                                    </div><!-- /.form-group -->                                    
									<div class="form-group">                                        
                                        <div id="msgAnuncioImovel" class="col-sm-4"> </div>
                                    </div><!-- /.form-group -->                                    
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="adicionarConfigAnuncioImovel();"><spring:message code="lbl.btn.adicionar.geral"/></button>
                                    </div>
                                </div><!-- /.form-footer -->
                                
                                <div id="msgRetornoPropostaErro"> </div>
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ End inside form layout -->
	

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout-admin/head-bootstrap.jsp"></c:import> 			
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>