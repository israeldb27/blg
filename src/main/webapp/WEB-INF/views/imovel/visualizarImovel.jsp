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

<spring:url value="/imovel/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/imovel/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel/ordenaMeusImoveis" var="urlOrdenaMeusImoveis"/>
<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>
<spring:url value="/imovelComparativo" var="urlImovelComparativo"/>
<spring:url value="/imovelComentario" var="urlImovelComentario"/>
<spring:url value="/imovelFavoritos" var="urlImovelFavoritos" />
<spring:url value="/contato" var="urlContato"/>
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/mensagem" var="urlMensagem"/>

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

function adicionarInteresse(id) {    	
	var parametro1 = id;
    $.ajax({                
        url: '${urlImovelFavoritos}/adicionarFavoritos/' + parametro1,                
        success: function(){
        	$("#idMeInteressei").hide();
	    	$("#idInteressado").show();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });   
}

function retirarInteresse(id) {
	var parametro1 = id;    		
    $.ajax({                
        url: '${urlImovelFavoritos}/retirarFavoritos/' + parametro1,                
        success: function(){
        	$("#idMeInteressei").show();                	
	    	$("#idInteressado").hide();	    		    	
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });   
}

function prepararModalProposta(){
	$("#msgRetornoPropostaErro").html("");	
	$("#idModalProposta").modal("show");
	$('#msgErroNovaProposta2').html("");
}
 
function adicionarProposta(){		
	var x = document.getElementById("novaProposta2");
	var y = document.getElementById("novaObsProposta2");
	
	if ($("#novaProposta2").val() == ''){
		$('#msgErroNovaProposta2').html('Campo deve ser obrigatorio');
	}
	else {
		if ( ! $.isNumeric( $("#novaProposta2").val() ) ){
			$('#msgErroNovaProposta2').html('Valor Proposta invalido');
		}
	}
	
	if ($("#novaObsProposta2").val() == ''){
		$('#msgErroNovaObsProposta2').html('Campo deve ser obrigatorio');
	}
	
	if (($("#novaProposta2").val() != '') && ($("#novaObsProposta2").val() != '')) { 
		$.ajax({  
		    type: 'GET',	
	         url: '${urlImovel}/adicionarPropostaDetalheImovel/' + x.value + '/' + y.value,
	         dataType: 'json',
	         success: function(data){	        	 
	        	 if ( data == 'ok') {
	        		 location.reload();
	        	 }
	        	 else  {
		        	 $('#msgRetornoPropostaErro').html(data);
		         }	
	         },	      
	     });
	}	  
} 

function prepararModalConfirmaExclusaoProposta(id){
	$("#modIdProposta").val(id);
	$('#msgRetornoConfirmExclusaoPropostaErro').html("");	
	$("#idModalConfirmacaoExclusaoProposta").modal("show");	
}

function confirmarExclusaoPropostaImovel(){	
	var parametro = document.getElementById("modIdProposta");	
	$.ajax({
		 url: '${urlImovel}/confirmarExclusaoPropostaImovel/' + parametro.value,			 
		 success: function(){				 
			 location.reload();     	    
		 },
		 error: function(jqXHR, textStatus, errorThrown) {				 
			 $('#msgRetornoConfirmExclusaoPropostaErro').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		 }
	 });
} 

function prepararModalComentario(){
	$("#msgRetornoComentarioErro").html("");
	$("#idModalComentario").modal("show");
	$('#msgErroNovoComentario').html("");
}
function adicionarComentario(){	
	var x = document.getElementById("novoComentario2");	
	
	if ($("#novoComentario2").val() == ''){
		$('#msgErroNovoComentario').html('Campo deve ser obrigatorio');
	}
	
	if ($("#novoComentario2").val() != '') {
		$.post("${urlImovel}/adicionarComentarioDetalheImovel", {'novoComentario' : x.value}, function() {		
			location.reload();
		});
	}	
}
function prepararModalSolIntermediacao(){
	$("#msgRetornoSolIntermediacaoErro").html("");	
	$("#msgRetornoSolIntermediacaoErroForm").html("");	
	$("#idModalSolIntermediacao").modal("show");	
}
function adicionarSolIntermediacao(){	
	if ($("#novaSolIntermediacao").val() == ''){
		$('#msgRetornoSolIntermediacaoErro').html('Campo deve ser obrigatorio');
	}
	else {
		var parametro1=document.getElementById("novaSolIntermediacao");	
			$.ajax({        
			 url: '${urlImovel}/adicionarSolIntermediacaoDetalheImovel/' + parametro1.value,
			 dataType: 'json',
			 success: function(data){				 
				 if ( data == 'ok') {
	        		 location.reload();
	        	 }
	        	 else  {
		        	 $('#msgRetornoSolIntermediacaoErroForm').html(data);
		         }     	    
			 },
			 error: function(jqXHR, textStatus, errorThrown) {				 
				 $('#msgRetornoSolIntermediacaoErroForm').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
			 }
		 });
	}	 
} 
function prepararModalCancelSolIntermediacao(){
	$("#msgRetornoCancelSolIntermediacaoErro").html("");	
	$("#idModalCancelIntermediacao").modal("show");	
}
function cancelarSolIntermediacao(){	
	$.ajax({      
			type: 'POST',
			 url: '${urlImovel}/cancelarSolIntermediacaoDetalheImovel',
			 dataType: 'json',
			 success: function(data){				 
				 if ( data == 'ok') {
	        		 location.reload();
	        	 }
	        	 else  {
		        	 $('#msgRetornoCancelSolIntermediacaoErro').html(data);
		         }     	    
			 },
			 error: function(jqXHR, textStatus, errorThrown) {				 
				 $('#msgRetornoSolIntermediacaoErro').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
			 }
		 });
} 	
function prepararModalSolParceria(){
	$("#msgRetornoSolParceriaErro").html("");
	$("#msgRetornoSolParceriaErroForm").html("");	
	$("#idModalSolParceria").modal("show");	
}
function adicionarSolParceria(){
	
	if ($("#novaSolParceria").val() == ''){
		$('#msgRetornoSolParceriaErro').html('Campo deve ser obrigatorio');
	}
	else {
		var parametro1=document.getElementById("novaSolParceria");	
			$.ajax({        
			 url: '${urlImovel}/adicionarSolParceriaDetalheImovel/' + parametro1.value,
			 dataType: 'json',
			 success: function(data){				 
				 if ( data == 'ok') {
	        		 location.reload();
	        	 }
	        	 else  {
		        	 $('#msgRetornoSolParceriaErroForm').html(data);
		         }     	    
			 },
			 error: function(jqXHR, textStatus, errorThrown) {				 
				 $('#msgRetornoSolParceriaErroForm').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
			 }
		 });
	}
} 
function prepararModalCancelSolParceria(){
	$("#msgRetornoCancelSolParceriaErro").html("");	
	$("#idModalCancelSolParceria").modal("show");	
}
function cancelarSolParceria(){	
	$.ajax({      
			type: 'POST',
			 url: '${urlImovel}/cancelarSolParceriaDetalheImovel',
			 dataType: 'json',
			 success: function(data){				 
				 if ( data == 'ok') {
	        		 location.reload();
	        	 }
	        	 else  {
		        	 $('#msgRetornoCancelSolParceriaErro').html(data);
		         }     	    
			 },
			 error: function(jqXHR, textStatus, errorThrown) {				 
				 $('#msgRetornoCancelSolParceriaErro').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
			 }
		 });
}
function enviarConvite(id) {	
	var parametro1 = id;
    $.ajax({        
        url: '${urlContato}/enviarConvite/' + parametro1,        
        success: function(){
        	$("#idConvite").hide();    	
        	$("#idConvidado").show();      
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });	    
  }
  
function adicionarComparar(id) {
	var parametro1 = id;
    $.ajax({        
		 url: '${urlImovelComparativo}/adicionarImovelComparativo/' + parametro1,
		 dataType: 'json',
		 success: function(data){				 
			 if ( data == 'ok') {
				 $('#msgModalComparativo').html('Imóvel foi adicionado a lista de comparativos');
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

function mostrarModal(id){
	
	if (id == 0){
		$('#msgModal').html("Descrição resumida sobre o imóvel");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.descricao.imovel'/>");
	}
	else if ( id == 1){
		$('#msgModal').html("Exibição da localização do imóvel");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.mapa.imovel'/>");
	}
	else if ( id == 2){
		$('#msgModal').html("Lista de todas propostas recebidas pelo imóvel");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.propostas.imovel'/>");
	}
	else if ( id == 3){
		$('#msgModal').html("Lista de propostas que você lançou sobre o imóvel");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.propostas.imovel.dono.imovel'/>");
	}
	else if ( id == 4){
		$('#msgModal').html("Informações sobre a intermediação do imóvel");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.intermediacoes.imovel'/>");
	}
	else if ( id == 5){
		$('#msgModal').html("Informações sobre a parceria do imóvel");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.parcerias.imovel'/>");
	}
	else if ( id == 6){
		$('#msgModal').html("Lista de usuários que visitaram o imóvel. ");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.visualizacoes.imovel'/>");
	}
	else if ( id == 7){
		$('#msgModal').html("Lista de usuários interessados sobre o imóvel. ");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.usuarios.interessados'/>");
	}
	else if ( id == 8){
		$('#msgModal').html("Lista de comentários sobre o imóvel. ");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.det.comentarios.imovel'/>");
	}
	else if ( id == 9){
		$('#msgModal').html("Informações de contato de responsável pelo imóvel. ");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.contato.detalhe.imoveis'/>");
	}
	else if ( id == 10){
		$('#msgModal').html("Informações de contato de responsável pelo imóvel. ");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.contato.detalhe.imoveis'/>");
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
            	<c:import url="../layout/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../layout/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">     
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.detalhes.imovel"/> </h2>				 
                </div><!-- /.header-content -->
                                
                <!-- Start body content -->
                <div class="body-content animated fadeIn" style="min-height: 500px;">
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
                                    	<c:choose>
                                    		<c:when test="${(imovelForm.interessadoImovel == 'N')}">
                                    			<a href="#a" id="idMeInteressei" onClick="adicionarInteresse(${imovelForm.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-star-o" style="color:gray" title="<spring:message code="lbl.me.interessei"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.me.interessei"/> </font></a> &nbsp; &nbsp;
                                            	<a href="#a" id="idInteressado" onClick="retirarInteresse(${imovelForm.id})" style="font-size:x-large;display:none;" class="meta-action"><i class="fa fa-star" style="color:gray" title="<spring:message code="lbl.interessado"/>"></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.interessado"/> </font></a> &nbsp;&nbsp;
                                    		</c:when>
                                    		
                                    		<c:when test="${imovelForm.interessadoImovel == 'S'}">
                                    			<a href="#a" id="idMeInteressei" onClick="adicionarInteresse(${imovelForm.id})" style="font-size:x-large;display:none;" class="meta-action"><i class="fa fa-star-o" style="color:gray" title="<spring:message code="lbl.me.interessei"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.me.interessei"/> </font></a> &nbsp; &nbsp;</a>
                                            	<a href="#a" id="idInteressado" onClick="retirarInteresse(${imovelForm.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-star" style="color:gray" title="<spring:message code="lbl.interessado"/>"></i> <spring:message code="lbl.interessado"/> </a>
                                    		</c:when>
                                    	</c:choose>  

                                        <a href="#a" onClick="adicionarComparar(${imovelForm.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-eye" style="color:gray" title="<spring:message code="lbl.title.link.comparar"/>"></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.title.link.comparar"/> </font></a> &nbsp;&nbsp;
                                        <a href="${urlImovelIndicado}/selecionarParaIndicarImovel/${imovelForm.id}" style="font-size:x-large;" lass="meta-action"><i class="fa fa-share-alt" style="color:gray" title="<spring:message code="lbl.acao.sugerir"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.acao.sugerir"/> </font></a> &nbsp;&nbsp;
                                    </div>
                                    
                                    <br> <br>

								</div>
								
								<!-- Start Galeria Fotos -->
								<div class="panel-body">
									<div class="col-lg-7 col-md-8 col-sm-6">
                                      	<div id="owl-demo" class="owl-carousel owl-theme">                                             
	                                             <div class="item"><img class="responsive" src="${context}${imovelForm.imagemArquivo}" style="max-height:320px; height: 500px;"></div>                                            
	                                     </div>
	                				</div>
	                				
	                				<div class="col-lg-5 col-md-5 col-sm-5"> 
	                                     <table class="table table-striped" style="margin-top:10px;">
                                                <tbody>
                                                	<tr>  
                                                        <td class="text-left"><spring:message code="lbl.valor.imovel.resum" /></td>
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
	                                                        <td class="text-right">${imovelForm.quantGaragem} vaga(s)</td>
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
                                                           <td class="text-left"><spring:message code="lbl.data.cadastro.imovel" /></td>
                                                           <td class="text-right"><fmt:formatDate value='${imovelForm.dataCadastro}' pattern='dd/MM/yyyy'/></td>
                                                       </tr>
                                                    
                                                </tbody>
                                            </table>
                                        </div> 
                                    </div>
									
                                 </div>
                                 <!-- End Galeria Fotos -->
                         
							</div><!-- /.panel -->
						</div>
					</div>
					<div class="row" style="margin-left: 10px;">
						<div class="col-lg-8 col-md-8 col-sm-8">
						
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
                         
                         <!-- /.START Propostas --> 
                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                	<c:choose>	
                                		<c:when test="${imovelForm.usuarioDonoImovel.id == usuario.id}">
                                			<h3 class="panel-title">
                                				<div class="pull-left">
                                					<spring:message code="lbl.title.aba.det.propostas.imovel"/>
	                                			</div>	
                                				<div class="pull-right">                                					
                                					<a href="#a" class="btn btn-sm"  onClick="mostrarModal(2);" style=""><i class="fa fa-question" ></i></a>
                                				</div>
                                				<br>
                                			</h3>	
                                		</c:when>
                                		
                                		<c:when test="${imovelForm.usuarioDonoImovel.id != usuario.id}">
											<h3 class="panel-title">
												<div class="pull-left">
													<spring:message code="lbl.title.aba.det.propostas.imovel.dono.imovel"/>
												</div>
												<div class="pull-right">													
													<a href="#a" class="btn btn-sm"  onClick="mostrarModal(3);" style=""><i class="fa fa-question" ></i></a>
												</div>
												<br>														
											</h3>	                                		
                                		</c:when> 
                                	</c:choose>

                                </div>
                                <div class="panel-body">
                                	<c:choose>
                                		<c:when test="${  empty imovelForm.listaPropostas }">
                               				<div class="callout callout-warning">
			                                    <strong><spring:message code="msg.nenhuma.proposta.detalhe.imovel"/></strong>		                                    
			                                </div>
	                                      </br> </br>	
                                		</c:when>
                                		
                                		<c:when test="${not  empty imovelForm.listaPropostas }">
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
		                                                  <td class="text-center">
		                                                     <a href="#" onClick="prepararModalConfirmaExclusaoProposta(${imovel.id});" data-toggle="tooltip" data-placement="top" title="" data-original-title="delete"><i class="fa fa-trash-o"></i></a>
		                                                  </td>
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>
		                                      </br> </br> 
		                                   </c:when>		                                   
                                	</c:choose>
                                	
                                	<c:if test="${imovelForm.usuarioDonoImovel.id != usuario.id}">                                      
                                          <div class="form-group">
                                             <input type="button" class="btn btn-primary" onClick="prepararModalProposta();" value="<spring:message code="btn.modal.adicionar.proposta"/>">
                                          </div>
                                   </c:if>
                                	 
                                </div>
                            </div>
                          <!-- /.END Propostas -->
              				
                          <!-- /.START Intermediação --> 
                          <c:if test="${((imovelForm.usuarioDonoImovel.perfil == 'P') && (usuario.perfil != 'P') && (imovelForm.autorizacaoOutroUsuario == 'S') && (usuario.id != imovelForm.idUsuario))}">
                          		<div class="panel rounded shadow">
		                                <div class="panel-heading">
		                                    <h3 class="panel-title">	
		                                    	<div class="pull-left">
		                              				<spring:message code="lbl.title.aba.det.intermediacoes.imovel"/>
		                               			</div>
		                               				
		                              			<div class="pull-right">
		                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(4);" style=""><i class="fa fa-question" ></i></a>
		                              			</div>
		                              			<br>	
		                                    </h3>
		                                </div>
		                                <div class="panel-body">
		                                    <ul class="media-list comment-list">
		
		                                        <li class="media">
		                                            <div class="media-left">
		                                                
		                                            </div>
		                                            <div class="media-body">  
		                                                <h4><spring:message code="lbl.condicao.intermediacao"/></h4>		                                                
		                                                <p><strong><spring:message code="lbl.descricao.imovel"/>: </strong> ${imovelForm.descAceitaCorretagemParceria}</p>
		                                            </div>
		                                        </li><!-- media -->		
		                                    </ul>
		                                    <br/>
		                                    
		                                    <c:choose>
		                                    	<c:when test="${  imovelForm.intermediacaoEnviada == null }">
		                                    		<ul class="media-list comment-list">
				                                        <li class="media">                                                                                        
				                                            <div class="mb-20"></div>
				                                            <form class="form-horizontal mb-20" role="form">
					                                            <div class="form-group">
					                                               <input type="button" class="btn btn-primary" onClick="prepararModalSolIntermediacao();" value='Solicitar Intermediação'>
					                                            </div>
				                                            </form>
				                                        </li>
				                                   </ul>
		                                    	</c:when>
		                                    	
		                                    	<c:when test="${  imovelForm.intermediacaoEnviada != null }">
		                                    		 <table class="table table-striped" >
				                                         <thead>
				                                            <tr>	                                               
				                                               <th style="width: 25%"><strong><strong><spring:message code="lbl.data.sol"/></strong></th>
				                                               <th style="width: 60%;"><strong><spring:message code="lbl.desc.sol.parceria"/></strong></th>
				                                            </tr>
				                                         </thead>
				
				                                         <tbody>		                                            
				                                               <tr>
				                                                  <td ><small><fmt:formatDate value='${imovelForm.intermediacaoEnviada.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
				                                                  <td><small>${imovelForm.intermediacaoEnviada.descricaoCompartilhamento} </small> </td>
				                                               </tr>		                                            
				                                         </tbody>
				                                      </table>
				                                      
				                                      <ul class="media-list comment-list">
					                                        <li class="media">                                                                                        
					                                            <div class="mb-20"></div>
					                                            <form class="form-horizontal mb-20" role="form">
						                                            <div class="form-group">
						                                               <input type="button" class="btn btn-primary" onClick="prepararModalCancelSolIntermediacao();" value='Cancelar Solicitação Intermediação '>
						                                            </div>						                                      
					                                            </form>
					                                        </li>
					                                    </ul>
		                                    	</c:when>
		                                    </c:choose>		                                                      
		                                </div>
		                            </div>                          
                          </c:if>
                          <!-- /.END Intermediação -->
                          
                          <!-- /.START Parceria -->
                           <c:if test="${((imovelForm.usuarioDonoImovel.perfil != 'P') && (usuario.perfil != 'P') && (imovelForm.autorizacaoOutroUsuario == 'S') && (usuario.id != imovelForm.idUsuario))}">
                          		<div class="panel rounded shadow">
		                                <div class="panel-heading">
		                                    <h3 class="panel-title">
		                                    	<div class="pull-left">
		                              				<spring:message code="lbl.title.aba.det.parcerias.imovel"/>
		                               			</div>
		                               				
		                              			<div class="pull-right">
		                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(5);" style=""><i class="fa fa-question" ></i></a>
		                              			</div>
		                              			<br>
		                                    </h3>
		                                </div>
		                                <div class="panel-body">
		                                    <ul class="media-list comment-list">
		
		                                        <li class="media">
		                                            <div class="media-left">
		                                                
		                                            </div>
		                                            <div class="media-body">
		                                                <h4><spring:message code="lbl.condicao.parceria"/></h4>		                                                
		                                                <p><strong><spring:message code="lbl.descricao.imovel"/>: </strong> ${imovelForm.descAceitaCorretagemParceria}</p>
		                                            </div>
		                                        </li><!-- media -->		
		                                    </ul>
		                                    <br/>
		                                    
		                                    <c:choose>
		                                    	<c:when test="${ imovelForm.parceriaEnviada == null }">		                                      
				                                      <ul class="media-list comment-list">
					                                        <li class="media">                                                                                        
					                                            <div class="mb-20"></div>
					                                            <form class="form-horizontal mb-20" role="form">
						                                            <div class="form-group">
						                                               <input type="button" class="btn btn-primary" onClick="prepararModalSolParceria();" value='<spring:message code="btn.modal.solicitar.parceria"/>'>
						                                            </div>
					                                            </form>
					                                        </li>
					                                   </ul>
					                           </c:when>   					                                
					                                    
					                           <c:when test="${ imovelForm.parceriaEnviada != null }">
					                           		 <div class="panel">
						                                <div class="panel-heading">
						                                    <div class="pull-left">
						                                    	</br>
						                                        <h7 class="panel-title">Minha Solicitação Parceria</h7>
						                                        
						                                    </div>
						                                </div>
						                               	
					                           		<table class="table table-striped" >
				                                         <thead>
				                                            <tr>	                                               
				                                               <th style="width: 25%"><strong><strong><spring:message code="lbl.data.sol"/></strong></th>
				                                               <th style="width: 60%;"><strong><spring:message code="lbl.desc.sol.parceria"/></strong></th>
				                                            </tr>
				                                         </thead>
				
				                                         <tbody>		                                            
				                                               <tr>
				                                                  <td ><small><fmt:formatDate value='${imovelForm.parceriaEnviada.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
				                                                  <td><small>${imovelForm.parceriaEnviada.descricaoCompartilhamento} </small> </td>
				                                               </tr>		                                            
				                                         </tbody>
				                                      </table>
				                                      
				                                      <ul class="media-list comment-list">
					                                        <li class="media">                                                                                        
					                                            <div class="mb-20"></div>
					                                            <form class="form-horizontal mb-20" role="form">			                                                                                              
								                                         
						                                            <div class="form-group">
						                                               <input type="button" class="btn btn-primary" onClick="prepararModalCancelSolParceria();" value='<spring:message code="lbl.cancelar.parceria"/>'>
						                                            </div>
								                                      
					                                            </form>
					                                        </li>
					                                    </ul>
					                                  </div>    
					                           </c:when>         
		                                    </c:choose>		                          		                                                                        
		                                </div>
		                            </div>                          
                          </c:if>
                          <!-- /.END Parceria -->
                         
                          <!-- /.START Visitas -->
                          <c:if test="${imovelForm.idUsuario == usuario.id && not empty imovelForm.listaVisita }">
                              <div class="panel rounded shadow">	
								<div class="panel-heading">                                   
                                      <h3 class="panel-title">
                                      	<div class="pull-left">
                              				<spring:message code="lbl.title.aba.det.visualizacoes.imovel"/>
                               			</div>
                               				
                              			<div class="pull-right">
                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(6);" style=""><i class="fa fa-question" ></i></a>
                              			</div>
                              			<br>	
                                      </h3>
                                </div>
                                <div class="panel-body">                                	
                                	<c:choose>
                                		<c:when test="${  empty imovelForm.listaVisita }">
                                			<h5 class="text-danger"> <spring:message code="msg.nenhuma.visualizacao"/> </h5>
                                		</c:when>
                                		
                                		<c:when test="${ not  empty imovelForm.listaVisita }">
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
																<a href="${urlUsuario}/detalhesUsuario/${imovelVisita.idUsuario}">
																	<img src="${context}/${imovelVisita.imagemUsuario}" style="width: 60px; height: 50px; " />	                				
																</a> 
														  </td>	
		                                                  <td><small><a href="${urlUsuario}/detalhesUsuario/${imovelVisita.idUsuario}">
																		${imovelVisita.nomeVisitante}	                				
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
                            </c:if>
                          <!-- /.END Visitas -->  
                          
                          <!-- /.START Usuarios Interessados-->	
							<c:if test="${imovelForm.idUsuario == usuario.id}">	
							  <div class="panel rounded shadow">	
								<div class="panel-heading">                                   
                                      <h3 class="panel-title">
                                      	<div class="pull-left">
                              				<spring:message code="lbl.usuarios.interessados"/>
                               			</div>
                               				
                              			<div class="pull-right">
                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(7);" style=""><i class="fa fa-question" ></i></a>
                              			</div>
                              			<br>
                                      </h3>
                                </div>
                                <div class="panel-body">
                                	<c:choose>
                                		<c:when test="${ empty imovelForm.listaUsuariosInteressados }">
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
																<a href="${urlUsuario}/detalhesUsuario/${imovelFavorito.idUsuario}">
																	<img src="${context}/${imovelFavorito.imagemUsuario}" style="width: 60px; height: 50px; " />	                				
																</a> 
														  </td>	
		                                                  <td><small><a href="${urlUsuario}/detalhesUsuario/${imovelFavorito.idUsuario}">
																		${imovelFavorito.nomeUsuarioInteressado}	                				
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
							</c:if>
						  <!-- /.END Usuarios Interessados-->	
						  
						  <!-- /.START Comentarios -->	
						<c:if test="${(((imovelForm.autorizaComentario == 'S') && ( imovelForm.idUsuario != usuario.id )) || (imovelForm.idUsuario == usuario.id ))  }">									
							<div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <h3 class="panel-title">
                                    	<div class="pull-left">
                              				<spring:message code="lbl.title.aba.det.comentarios.imovel"/>
                               			</div>
                               				
                              			<div class="pull-right">
                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(8);" style=""><i class="fa fa-question" ></i></a>
                              			</div>
                              			<br>	
                                    </h3>
                                </div>
                                
                                <c:choose>
                                	<c:when test="${not  empty imovelForm.listaComentario }">
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
		
		                                    <ul class="media-list comment-list">
		                                        <li class="media">                                                                                        
		                                            <div class="mb-20"></div>
		                                            <form class="form-horizontal mb-20" role="form">	                                                                                           
					                                         
			                                            <div class="form-group">
			                                               <input type="button" class="btn btn-primary" onClick="prepararModalComentario();" value='<spring:message code="btn.modal.adicionar.comentario"/> '>
			                                            </div>
					                                      
		                                            </form>
		                                        </li>
		                                    </ul>
		                                </div>	
                                	</c:when>
                                	
                                	<c:when test="${empty imovelForm.listaComentario }">
                               			<div class="panel-body">
		                                	 <div class="callout callout-warning">
				                                    <strong><spring:message code="msg.nenhum.comentario.imovel"/></strong>		                                    
				                              </div>
		                                    </br> </br>
		                                    
											   <div class="form-group">
	                                               <input type="button" class="btn btn-primary" onClick="prepararModalComentario();" value='<spring:message code="btn.modal.adicionar.comentario"/> '>
	                                            </div> 
	                                	</div>
                                	</c:when>
                                	
                                </c:choose>
                            </div>
                           </c:if> 
                         <!-- /.END Comentarios -->  
						</div>						
						
						<div class="col-lg-4 col-md-4 col-sm-12">
							<!-- /.START Informação de Contato do Imóvel -- Dono Imovel -->
							<c:if test="${(((imovelForm.usuarioDonoImovel.perfil == 'P') && (imovelForm.usuarioIntermediador == null ) ) || (imovelForm.usuarioDonoImovel.perfil != 'P') || (imovelForm.usuarioDonoImovel.id == usuario.id) )}">
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
														<img data-no-retina="" class="img-square img-bordered-black" src="${context}${imovelForm.usuarioDonoImovel.imagemArquivo}" style="width: 90px;  ">	                				
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
                </div><!-- /.body-content -->
			</div>              
            </section><!-- /#page-content -->

        </section><!-- /#wrapper -->
        
		<!-- Start inside form layout -->
            <div id="idModalProposta" class="modal fade bs-example-modal-form" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.cadastrar.proposta"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form" id="input-mask">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.valor.proposta.imovel"/></label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" id="novaProposta2" onkeypress="formatarMoeda(this);">                                            
                                            <div id="msgErroNovaProposta2" cssClass="errorEntrada"  ></div>                                            
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="password-1" class="col-sm-3 control-label"><spring:message code="lbl.observacao.proposta.imovel"/></label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control " id="novaObsProposta2"  >
                                            <div id="msgErroNovaObsProposta2" cssClass="errorEntrada"  ></div>
                                        </div>
                                    </div><!-- /.form-group -->                                    
									<div class="form-group">                                        
                                        <div id="msgPropostaErro" class="col-sm-4"> </div>
                                    </div><!-- /.form-group -->                                    
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="adicionarProposta();"><spring:message code="lbl.btn.adicionar.geral"/></button>
                                    </div>
                                </div><!-- /.form-footer -->
                                
                                <div id="msgRetornoPropostaErro"> </div>
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ End inside form layout -->
            
             <!-- Start inside form layout - comentario -->
            <div id="idModalComentario" class="modal fade bs-example-modal-form-comentario" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.cadastrar.comentario"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.nova.descricao.comentario"/></label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="novoComentario2">
                                            <div id="msgErroNovoComentario"> </div>
                                        </div>
                                        
                                    </div><!-- /.form-group -->
                                   
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="adicionarComentario();"><spring:message code="lbl.btn.adicionar.geral"/></button>
                                    </div>
                                </div><!-- /.form-footer -->								
																
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ End inside form layout - comentario -->
            
            <!-- Start inside form layout - intermediacao -->
            <div id="idModalSolIntermediacao" class="modal fade bs-example-modal-form-intermediacao" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.cadastrar.sol.intermediacao"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.desc.nova.intermediacao"/></label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="novaSolIntermediacao">
                                            <div id="msgRetornoSolIntermediacaoErro" cssClass="errorEntrada"  ></div>											
                                        </div>
                                    </div><!-- /.form-group -->
                                   
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="adicionarSolIntermediacao();"><spring:message code="lbl.btn.confirmar.geral"/></button>
                                    </div>
                                    
                                    <div id="msgRetornoSolIntermediacaoErroForm" cssClass="errorEntrada"  ></div>
                                                                        
                                </div><!-- /.form-footer -->																   
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ End inside form layout - intermediacao -->            
            
            <!-- Start inside form layout - parceria -->
            <div id="idModalSolParceria" class="modal fade bs-example-modal-form-parceria" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.cadastrar.sol.parceria"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.desc.nova.parceria"/></label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="novaSolParceria">
                                            <div id="msgRetornoSolParceriaErro" cssClass="errorEntrada"  ></div>
                                        </div>
                                        
                                    </div><!-- /.form-group -->
                                   
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="adicionarSolParceria();"><spring:message code="lbl.btn.confirmar.geral"/></button>
                                    </div>                                    
                                </div><!-- /.form-footer -->
								
								<div id="msgRetornoSolParceriaErroForm" cssClass="errorEntrada"  ></div>   
                            </form>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ End inside form layout - intermediacao -->            
            
            <!-- Start optional size modal element - cancelamento solicitação parceria -->
            <div id="idModalCancelSolParceria"  class="modal fade bs-example-modal-lg-cancel-sol-parceria" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.cancelar.sol.parceria"/></h4>
                        </div>
                        <div class="modal-body">
                            <p><spring:message code="lbl.modal.pergunta.cancelar.sol.parceria"/></p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.modal.pergunta.cancelar.sol.parceria.nao"/></button>
                            <button type="button" class="btn btn-theme" onClick="cancelarSolParceria();"><spring:message code="lbl.modal.pergunta.cancelar.sol.parceria.sim"/></button>                            
                        </div>
						
						<div id="msgRetornoCancelSolParceriaErro" cssClass="errorEntrada"  ></div>   
						
						
                    </div><!-- /.modal-content -->					
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->            
            
            <!-- Start optional size modal element - cancelamento solicitação intermediacao -->
            <div id="idModalCancelIntermediacao" class="modal fade bs-example-modal-lg-cancel-sol-intermediacao" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.cancelar.sol.intermediacao"/></h4>
                        </div>
                        <div class="modal-body">
                            <p><spring:message code="lbl.modal.pergunta.cancelar.sol.intermediacao"/></p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.modal.pergunta.cancelar.sol.intermediacao.nao"/></button>
                            <button type="button" class="btn btn-theme" onClick="cancelarSolIntermediacao();"><spring:message code="lbl.modal.pergunta.cancelar.sol.intermediacao.sim"/></button>                            
                        </div>
						
						<div id="msgRetornoCancelSolIntermediacaoErro" cssClass="errorEntrada"  ></div>   
						
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
           
            
             <!-- Start optional size modal element - confirmacao exclusao proposta imovel -->
            <div id="idModalConfirmacaoExclusaoProposta" class="modal fade bs-example-modal-lg-confirmacao-exclusao-proposta-imovel" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdProposta" readonly="readonly" name="modIdProposta">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.exclusao.minha.proposta"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.exclusao.minha.proposta"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="confirmarExclusaoPropostaImovel();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoConfirmExclusaoPropostaErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao exclusao imovel interesse  --> 
            
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
																            <img class="responsive" src="${context}${foto.imagemArquivo}" style="width: 80px; height: 60px;" >		            
																          </a></li>
														               </c:if>
														              
														              <c:if test="${passo.count > 1}">
															              <li> <a id="carousel-selector-${((passo.count) - 1)}">
																            <img class="responsive" src="${context}${foto.imagemArquivo}" style="width: 80px; height: 60px;" >
																            
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
															                               <img class="responsive" src="${context}${foto.imagemArquivo}" style="width: 1200px; height: 380px;" >
															                            </div>
												                                   </c:if>
												                                  
												                                  <c:if test="${passo.count > 1}">
													                                  	<div class="item" data-slide-number="${((passo.count) - 1)}">
															                               <img class="responsive" src="${context}${foto.imagemArquivo}" style="width: 1200px; height: 380px;" >
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