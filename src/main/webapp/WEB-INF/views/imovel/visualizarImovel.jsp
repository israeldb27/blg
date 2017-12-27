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
<spring:url value="/intermediacao" var="urlIntermediacao"/>
<spring:url value="/parceria" var="urlParceria"/>
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
		$('#msgErroNovaProposta2').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	else {
		if ( ! $.isNumeric( $("#novaProposta2").val() ) ){ 
			$('#msgErroNovaProposta2').html("<spring:message code='msg.erro.valor.proposta.invalido'/>");
		}
	}
	
	if ($("#novaObsProposta2").val() == ''){
		$('#msgErroNovaObsProposta2').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
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
		$('#msgErroNovoComentario').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
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
	$("#msgAguardeMomento").html("Aguarde um momento ...");
	
	if ($("#novaSolIntermediacao").val() == ''){
		$('#msgRetornoSolIntermediacaoErro').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
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
		$('#msgRetornoSolParceriaErro').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
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
function prepararModalFecharNegocio(id){
	$("#msgRetornoFecharNegocioErro").html("");	
	$("#idModalFecharNegocio").modal("show");	
}
function notificarFecharNegocio(){	
	$.ajax({      
		type: 'POST',
		 url: '${urlImovel}/notificarFecharNegocio',
		 dataType: 'json',
		 success: function(data){				 
			 if ( data == 'ok') {
				 $("#idModalFecharNegocio").modal("hide");
        	 }
        	 else  {
	        	 $('#msgRetornoFecharNegocioErro').html(data);
	         }     	    
		 },
		 error: function(jqXHR, textStatus, errorThrown) {				 
			 $('#msgRetornoCancelSolParceriaErro').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		 }
	 });
}

function prepararModalMarcarVisita(id){
	$("#msgRetornoMarcarVisitaErro").html("");	
	$("#idModalMarcarVisita").modal("show");	
}

function notificarMarcarVisita() {	
	alert('chamou marcar visita');
    $.ajax({  
    	type: 'POST',
        url: '${urlImovel}/notificarMarcarVisita',   
        dataType: 'json',
        success: function(data){
        	if ( data == 'ok') {
        		$("#idModalMarcarVisita").modal("hide");
	       	 }
	       	 else  {
	        	 $('#idModalMarcarVisita').html(data);
	         } 
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });
}
function prepararModalprocurarInteressados(idImovel){
	$("#msgRetornoprocurarInteressados").html("");	
	$("#idModalprocurarInteressados").modal("show");		
}
function procurarInteressados(){
 $.ajax({  
    	type: 'POST',
        url: '${urlImovel}/procurarInteressados',   
        success: function(){
        	$("#idModalprocurarInteressados").modal("hide");
	        $('#msgRetornoprocurarInteressados').html(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });
}
function prepararModalAtividade(){
	$("#novaAtividade").val('');
	$("#novaDescricaoAtividade").val('');
	$("#msgRetornoAtividadeErro").html("");	
	$("#idModalAtividades").modal("show");
	$('#msgErroNovaDescricaoAtividade').html("");
}
function adicionarAtividade(){
	
	var x = document.getElementById("novaAtividade");
	var y = document.getElementById("novaDescricaoAtividade");
	
	if ($("#novaAtividade").val() == ''){  
		$('#msgErroAtividade').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	if ($("#novaDescricaoAtividade").val() == ''){
		$('#msgErroNovaDescricaoAtividade').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	if (($("#novaAtividade").val() != '') && ($("#novaDescricaoAtividade").val() != '')) { 
		$.ajax({  
	         url: '${urlImovel}/adicionarAtividadeDetalhesImovel/' + x.value + '/' + y.value,
	         dataType: 'json',
	         success: function(data){	        	 
	        	 if ( data == 'ok') {
	        		 location.reload();
	        	 }
	        	 else  {
		        	 $('#msgRetornoAtividadeErro').html(data);
		         }	
	         },	      
	     });
	}
}

function prepararModalConfirmaEdicaoAtividade(id, status, descricao){
	$("#modIdAtividadeEdicao").val(id);
	$("#novaAtividadeEdicao").val(status);
	$("#novaDescricaoAtividadeEdicao").val(descricao);
	$("#idModalEdicaoAtividades").modal("show");
}

function editarAtividade(){
	
	var x = document.getElementById("novaAtividadeEdicao");
	var y = document.getElementById("novaDescricaoAtividadeEdicao");
	var z = document.getElementById("modIdAtividadeEdicao");
	
	if ($("#novaAtividadeEdicao").val() == ''){  
		$('#msgErroAtividade').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	if ($("#novaDescricaoAtividadeEdicao").val() == ''){
		$('#msgErroNovaDescricaoAtividade').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	if (($("#novaAtividadeEdicao").val() != '') && ($("#novaDescricaoAtividadeEdicao").val() != '')) { 
		$.ajax({  
	         url: '${urlImovel}/editarAtividadeDetalhesImovel/' + x.value + '/' + y.value + '/' + z.value,
	         dataType: 'json',
	         success: function(data){	        	 
	        	 if ( data == 'ok') {
	        		 location.reload();
	        	 }
	        	 else  {
		        	 $('#msgRetornoAtividadeErro').html(data);
		         }	
	         },	      
	     });
	}	
}

function prepararModalConfirmaExclusaoAtividade(id){
	$("#modIdAtividade").val(id);
	$('#msgRetornoConfirmExclusaoAtividadeErro').html("");	
	$("#idModalConfirmacaoExclusaoAtividade").modal("show");	
}

function confirmarExclusaoAtividadeImovel(){	
	var parametro = document.getElementById("modIdAtividade");	
	$.ajax({
		 url: '${urlImovel}/confirmarExclusaoAtividadeImovel/' + parametro.value,			 
		 success: function(){				 
			 location.reload();     	    
		 },
		 error: function(jqXHR, textStatus, errorThrown) {				 
			 $('#msgRetornoConfirmExclusaoAtividadeErro').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		 }
	 });
}
 
function prepararModalPossivelInteressadoOffline() {
	$("#idModalPossivelInteressadoOffline").modal("show");
	
}

function adicionarPossivelInteressadoOffline(){
	var nome = document.getElementById("nomeInteressado");
	var email = document.getElementById("emailInteressado");
	var telefone = document.getElementById("telefoneInteressado");	
	var chanceInteresse = document.getElementById("chanceInteresseOffline");
	var observacao = document.getElementById("observacaoPossInteressado");
	
	if ($("#nomeInteressado").val() == ''){  
		$('#msgErronomeInteressado').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	if ($("#telefoneInteressado").val() == '')  { 
		$('#msgErrotelefoneInteressado').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	if ($("#chanceInteresseOffline").val() == '')  { 
		$('#msgErrochanceInteresse').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	$.ajax({  
         url: '${urlImovel}/adicionarPossivelInteressadoOfflineDetalhesImovel/' + nome.value + '/' + telefone.value  + '/' + email.value + '/' +  chanceInteresse.value + '/' + observacao.value,
         dataType: 'json',
         success: function(data){	        	 
        	 if ( data == 'ok') {
        		 location.reload();
        	 }
        	 else  {
	        	 $('#msgRetornoAtividadeErro').html(data);
	         }	
         },	      
     });
}

function prepararModalConfirmaEdicaoPossivelCompr(id, nome, chanceInteresse, observacao){
	
	$("#modIdPossivelInteressadoEdicao").val(id);
	$("#nomeComprEdicao").val(nome);
	$("#obsPossCompradorEdicao").val(observacao);
	$("#chanceInteresseEdicao").val(chanceInteresse);
	$("#idModalPossivelInteressadoEdicao").modal("show");
}

function editarPossivelInteressado(){

	var id = document.getElementById("modIdPossivelInteressadoEdicao");
	var nome = document.getElementById("nomeInteressadoEdicao");
	var chanceInteresse = document.getElementById("chanceInteresseEdicao");
	var observacao = document.getElementById("obsPossCompradorEdicao");
	
	$.ajax({  
        url: '${urlImovel}/editarPossivelInteressadoDetalhesImovel/' + id.value + '/' +  chanceInteresse.value + '/' + observacao.value,
        dataType: 'json',
        success: function(data){	        	 
       	 if ( data == 'ok') {
       		 location.reload();
       	 }
       	 else  {
	        	 $('#msgRetornoAtividadeErro').html(data);
	         }	
        },	      
    });
}

function prepararModalConfirmaEdicaoPossivelInteressadoOffline(id, nomeInteressado, emailInteressado, telefoneInteressado, chanceInteresse, observacao){
	
	$("#modIdPossivelInteressadoOfflineEdicao").val(id);
	$("#nomeInteressadoEdicao").val(nomeInteressado);
	$("#emailInteressadoEdicao").val(emailInteressado);
	$("#telefoneInteressadoEdicao").val(telefoneInteressado);
	$("#chanceInteresseOfflineEdicao").val(chanceInteresse);
	$("#observacaoPossInteressadoEdicao").val(observacao);	
	$("#idModalPossivelInteressadoOfflineEdicao").modal("show");	
}

function editarPossivelInteressadoOffline(){
	
	var id = document.getElementById("modIdPossivelInteressadoOfflineEdicao");
	var nome = document.getElementById("nomeInteressadoEdicao");
	var email = document.getElementById("emailInteressadoEdicao");
	var telefone = document.getElementById("telefoneInteressadoEdicao");	
	var chanceInteresse = document.getElementById("chanceInteresseOfflineEdicao");
	var observacao = document.getElementById("observacaoPossInteressadoEdicao");
	
	if ($("#nomeInteressadoEdicao").val() == ''){  
		$('#msgErronomeInteressado').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	if ($("#telefoneInteressadoEdicao").val() == '')  { 
		$('#msgErrotelefoneInteressado').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	if ($("#chanceInteresseOfflineEdicao").val() == '')  { 
		$('#msgErrochanceInteresse').html("<spring:message code='msg.erro.campo.obrigatorio'/>");
	}
	
	$.ajax({  
         url: '${urlImovel}/editarPossivelInteressadoOfflineDetalhesImovel/' + id.value +  '/' + nome.value + '/' + telefone.value  + '/' + email.value + '/' +  chanceInteresse.value + '/' + observacao.value,
         dataType: 'json',
         success: function(data){	        	 
        	 if ( data == 'ok') {
        		 location.reload();
        	 }
        	 else  {
	        	 $('#msgRetornoAtividadeErro').html(data);
	         }	
         },	      
     });
}

function prepararModalConfirmaExclusaoPossivelInteressadoOffline(id){
	$("#modIdPossivelInteressadiOffline").val(id);
	$("#idModalConfirmacaoExclusaoPossivelInteressadiOffline").modal("show");	
}

function confirmarExclusaoPossivelInteressadoOfflineImovel(){
	
	var parametro = document.getElementById("modIdPossivelInteressadoOffline");	
	$.ajax({
		 url: '${urlImovel}/confirmarExclusaoPossivelInteressadoOfflineImovel/' + parametro.value,			 
		 success: function(){				 
			 location.reload();     	    
		 },
		 error: function(jqXHR, textStatus, errorThrown) {				 
			 $('#msgRetornoConfirmExclusaoPossivelInteressadoOfflineErro').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		 }
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
                                    	<c:choose>
                                    		<c:when test="${((imovelForm.interessadoImovel == 'N') && (imovelForm.usuarioDonoImovel.id != usuario.id))}">
                                    			<a href="#a" id="idMeInteressei" onClick="adicionarInteresse(${imovelForm.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-star-o" style="color:gray" title="<spring:message code="lbl.me.interessei"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.me.interessei"/> </font></a> 
                                            	<a href="#a" id="idInteressado" onClick="retirarInteresse(${imovelForm.id})" style="font-size:x-large;display:none;" class="meta-action"><i class="fa fa-star" style="color:gray" title="<spring:message code="lbl.interessado"/>"></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.interessado"/> </font></a> 
                                    		</c:when>
                                    		
                                    		<c:when test="${((imovelForm.interessadoImovel == 'S') && (imovelForm.usuarioDonoImovel.id == usuario.id))}">
                                    			<a href="#a" id="idMeInteressei" onClick="adicionarInteresse(${imovelForm.id})" style="font-size:x-large;display:none;" class="meta-action"><i class="fa fa-star-o" style="color:gray" title="<spring:message code="lbl.me.interessei"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.me.interessei"/> </font></a></a>
                                            	<a href="#a" id="idInteressado" onClick="retirarInteresse(${imovelForm.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-star" style="color:gray" title="<spring:message code="lbl.interessado"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.interessado"/> </font> </a>
                                    		</c:when>
                                    	</c:choose>  
 										 &nbsp;&nbsp;&nbsp;&nbsp; 								
 										 
                                        <a href="#a" onClick="adicionarComparar(${imovelForm.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-eye" style="color:gray" title="<spring:message code="lbl.title.link.comparar"/>"></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.title.link.comparar"/> </font></a>&nbsp;&nbsp;&nbsp;&nbsp; 
                                        <a href="${urlImovelIndicado}/selecionarParaIndicarImovel/${imovelForm.id}" style="font-size:x-large;" class="meta-action"><i class="fa fa-share-alt" style="color:gray" title="<spring:message code="lbl.acao.sugerir"/>"></i> <font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.acao.sugerir"/> </font></a>
                                   		<c:if test="${imovelForm.usuarioDonoImovel.id != usuario.id}">                                      
                                        	&nbsp;&nbsp;&nbsp;&nbsp;
	                                   		<a href="#a" onClick="prepararModalFecharNegocio(${imovelForm.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-shopping-cart" style="color:gray" title="<spring:message code="lbl.title.fechar.negocio"/>"></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.title.fechar.negocio"/> </font></a>
	                                        &nbsp;&nbsp;&nbsp;&nbsp;
	                                   		<a href="#a" onClick="prepararModalMarcarVisita(${imovelForm.id})" style="font-size:x-large;" class="meta-action"><i class="fa fa-calendar-check-o" style="color:gray" title="<spring:message code="lbl.title.marcar.visita"/>"></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.title.marcar.visita"/> </font></a> 
                                        </c:if>
                                   		
                                   		 &nbsp;&nbsp;&nbsp;&nbsp; 
                                   		 
                                   		<a href="${urlImovel}/procurarInteressados/${imovelForm.id}" style="font-size:x-large;" class="meta-action"><i class="fa  fa-money" style="color:gray" title="<spring:message code="lbl.title.link.procurar.interessados"/>"></i><font style="color: rgb(99, 110, 123); font-size: 12px;"> <spring:message code="lbl.title.link.procurar.interessados"/> </font></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
                         
                         <!-- /.START Propostas --> 
                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                	<c:choose>	
                                		<c:when test="${imovelForm.usuarioDonoImovel.id == usuario.id}">
                                			<h3 class="panel-title">
                                				<div class="pull-left">
                                					<spring:message code="lbl.title.aba.det.propostas.imovel.dono.imovel"/>
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
													<spring:message code="lbl.title.aba.det.propostas.imovel"/>
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
                                		<c:when test="${empty imovelForm.listaPropostas }">
                                			<c:choose>	
                                				<c:when test="${imovelForm.usuarioDonoImovel.id == usuario.id}">
                                					<div class="callout callout-warning">
					                                    <strong><spring:message code="msg.nenhuma.proposta.imovel"/></strong>		                                    
					                                </div>
                                				</c:when>
                                				
                                				<c:when test="${imovelForm.usuarioDonoImovel.id != usuario.id}">
                                					<div class="callout callout-warning">
					                                    <strong><spring:message code="msg.nenhuma.proposta.detalhe.imovel"/></strong>		                                    
					                                </div>
                                				</c:when>
                                			</c:choose>                                			    
			                                
	                                      </br> </br>	
                                		</c:when>
                                		
                                		<c:when test="${not  empty imovelForm.listaPropostas }">
                                			 <table class="table table-striped" >
		                                         <thead>
		                                            <tr>
		                                               <th style="width: 15%;" class="text-center"></th>
		                                               <th style="width: 15%;" class="text-center"><strong><spring:message code="lbl.usuario.proposta"/></strong></th>
		                                               <th style="width: 15%;" class="text-center"><strong><spring:message code="lbl.data.proposta"/></strong></th>
		                                               <th style="width: 25%; text-align: center;"><strong><spring:message code="lbl.valor.proposta"/></strong></th>
		                                               <th style="width: 40%"><strong><spring:message code="lbl.observacao.proposta.imovel"/></strong></th>
		                                               <th style="width: 10%" class="text-center"><strong><spring:message code="lbl.table.data.acoes"/></strong></th>
		                                            </tr>
		                                         </thead>
		
		                                         <tbody>
		                                            <c:forEach var="imovel" items="${imovelForm.listaPropostas}">
		                                               <tr>
		                                               	  <td style="font-size: 13px;" class="text-center">
		                                               	  		<a href="${urlUsuario}/detalhesUsuario/${imovel.usuarioLancador.id}" >
																	<img src="data:image/jpeg;base64,${imovel.usuarioLancador.imagemArquivo}" style="width: 60px; height: 50px; " />
																</a>
		                                               	  </td>	
		                                               	  
		                                               	  <td style="font-size: 13px;" class="text-center">
												  				<a href="${urlUsuario}/detalhesUsuario/${imovel.usuarioLancador.id}" >
																		${imovel.usuarioLancador.nome}
																</a>			                                               	  
		                                               	  </td>
		                                                  <td style="font-size: 13px;" class="text-center"><small><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></small></td>
		                                                  <td style="font-size: 13px;" class="text-center"><small>R$<fmt:formatNumber value="${imovel.valorProposta}" pattern="#,##0.00;-0"/></small></td>
		                                                  <td style="font-size: 13px;"><small>${imovel.observacao}</small></td>
		                                                  <td class="text-center" >
		                                                     <a href="#" onClick="prepararModalConfirmaExclusaoProposta(${imovel.id});" ><i class="fa fa-trash-o"></i></a>
		                                                  </td>
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>
		                                      </br> </br> 
		                                      
		                                       <!-- botao ver mais  -->
				                                <c:if test="${imovelForm.isExibeMaisListaPropostas() }">
				                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;">				                                	
					                                    <a href="${urlImovelPropostas}/listaTodasPropostasImovel/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>
					                               		<br> <br>
					                                </div>
				                                </c:if>	
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
                          
                          <!-- /.START Atividades --> 
                          
                           <div class="panel rounded shadow">
                                <div class="panel-heading">                              
	                     			<h3 class="panel-title">
	                     				<div class="pull-left">
	                     					<spring:message code="lbl.title.aba.det.imovel.atividades"/>
	                      				</div>	
	                     				<div class="pull-right">                                					
	                     					<a href="#a" class="btn btn-sm"  onClick="mostrarModal(11);" style=""><i class="fa fa-question" ></i></a>
	                     				</div>
	                     				<br>
	                     			</h3>
                                </div>
                                <div class="panel-body">
                                	<c:choose>
                                		<c:when test="${empty imovelForm.listaAtividades }">
                                			<div class="callout callout-warning">
			                                    <strong><spring:message code="msg.nenhuma.atividade.detalhe.imovel"/></strong>		                                    
			                                </div>                             			    
			                                
	                                      </br> </br>	
                                		</c:when>
                                		
                                		<c:when test="${not  empty imovelForm.listaAtividades }">
                                			 <table class="table table-striped" >
		                                         <thead>
		                                            <tr>		                                              
		                                               <th style="width: 15%;" class="text-center"><strong><spring:message code="lbl.data.atividade"/></strong></th>
		                                               <th style="width: 40%;" class="text-center"><strong><spring:message code="lbl.descricao.atividade"/></strong></th>
		                                               <th style="width: 15%; text-align: center;"><strong><spring:message code="lbl.status.atividade"/></strong></th>
		                                          	   <th style="width: 5%;" class="text-center"></th>	
		                                          	   <th style="width: 5%;" class="text-center"></th>
		                                            </tr>
		                                         </thead>
		
		                                         <tbody>
		                                            <c:forEach var="atividade" items="${imovelForm.listaAtividades}">
		                                               <tr>
		                                               	  <td style="font-size: 13px;" class="text-center"> <small><fmt:formatDate value='${atividade.dataAtividade}' pattern='dd/MM/yyyy'/></small> </td>	
		                                               	  <td style="font-size: 13px;" class="text-center">${atividade.descricao} </td>
		                                                  <td style="font-size: 13px;" class="text-center"><small> ${atividade.statusFmt} </small></td>
		                                                  <td class="text-center" >
		                                                     <a href="#a" onClick="prepararModalConfirmaEdicaoAtividade('${atividade.id}', '${atividade.status}', '${atividade.descricao}');" ><i class="fa fa-pencil-square-o"></i></a>
		                                                  </td>
		                                                  <td class="text-center" >
		                                                     <a href="#a" onClick="prepararModalConfirmaExclusaoAtividade(${atividade.id});" ><i class="fa fa-trash-o"></i></a>
		                                                  </td>
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>
		                                      </br> </br>
		                                      <!-- botao ver mais  -->
				                                <c:if test="${imovelForm.isExibeMaisListaAtividades() }">
				                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;"> 
					                                    <a href="${urlImovel}/visualizarTodasAtividadesImovel/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>
					                               		<br> <br>
					                                </div>
				                                </c:if>	 
		                                   </c:when>		                                   
                                	</c:choose>
                                	
                                	<c:if test="${imovelForm.usuarioDonoImovel.id == usuario.id}">                                      
                                          <div class="form-group">
                                             <input type="button" class="btn btn-primary" onClick="prepararModalAtividade();" value="<spring:message code="btn.modal.adicionar.atividade"/>">
                                          </div>
                                   </c:if>                                	 
                                </div>
                            </div>      
                          <!-- /.END Atividades --> 
                          
                          <!-- /.START Possivel Interessado -->                           
                           <div class="panel rounded shadow">
                                <div class="panel-heading">                              
	                     			<h3 class="panel-title">
	                     				<div class="pull-left">
	                     					<spring:message code="lbl.title.aba.det.imovel.possivel.interessado"/>
	                      				</div>	
	                     				<div class="pull-right">                                					
	                     					<a href="#a" class="btn btn-sm"  onClick="mostrarModal(12);" style=""><i class="fa fa-question" ></i></a>
	                     				</div>
	                     				<br>
	                     			</h3>
                                </div>
                                <div class="panel-body">
                                	<c:choose>
                                		<c:when test="${empty imovelForm.listaPossivelInteressado }">
                                			<div class="callout callout-warning">
			                                    <strong><spring:message code="msg.nenhum.possivel.interessado"/></strong>		                                    
			                                </div>                             			    
			                                
	                                      </br> </br>	
                                		</c:when>
                                		
                                		<c:when test="${not  empty imovelForm.listaPossivelInteressado }">
                                			 <table class="table table-striped" >
		                                         <thead>
		                                            <tr>		                                              
		                                               <th style="width: 25%;" class="text-center"><strong><spring:message code="lbl.data.cadastro.poss.interessado"/></strong></th>
		                                               <th style="width: 40%;" class="text-center"><strong><spring:message code="lbl.nome.poss.interessado"/></strong></th>
		                                               <th style="width: 25%; text-align: center;"><strong><spring:message code="lbl.chance.interesse.poss.interessado"/></strong></th>		                                              
		                                               <th style="width: 25%; text-align: center;"><strong><spring:message code="lbl.observacao.poss.interessado"/></strong></th>
		                                               <th style="width: 15%;" class="text-center"></th>
		                                               <th style="width: 15%;" class="text-center"></th>
		                                            </tr>
		                                         </thead>
		
		                                         <tbody>
		                                            <c:forEach var="interessado" items="${imovelForm.listaPossivelInteressado}">
		                                               <tr>
		                                               	  <td style="font-size: 13px;" class="text-center"> <small><fmt:formatDate value='${interessado.dataCadastro}' pattern='dd/MM/yyyy'/></small> </td>	
		                                               	  <td style="font-size: 13px;" class="text-center">${interessado.usuarioInteressado.nome} </td>
		                                                  <td style="font-size: 13px;" class="text-center"><small> ${interessado.chanceInteresseFmt} </small></td>		                                               
		                                                  <td style="font-size: 13px;" class="text-center"><small> ${interessado.observacao} </small></td>
		                                                   <td class="text-center" >
		                                                     <a href="#a" onClick="prepararModalConfirmaEdicaoPossivelInteressado('${interessado.id}', '${interessado.usuarioInteressado.nome}', '${interessado.chanceInteresse}','${interessado.observacao}');" ><i class="fa fa-pencil-square-o"></i></a>
		                                                  </td>		                                                  
		                                                  <td class="text-center" >
		                                                     <a href="#a" onClick="prepararModalConfirmaExclusaoPossivelInteressado(${interessado.id});" ><i class="fa fa-trash-o"></i></a>
		                                                  </td>
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>
		                                      </br> </br> 
		                                       <!-- botao ver mais  -->
				                                <c:if test="${imovelForm.isExibeMaisListaPossivelInteressado() }">
				                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;">
					                                    <a href="${urlImovel}/visualizarTodosPossivelInteressadoImovel/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>
					                               		<br> <br>
					                                </div>
				                                </c:if>	
		                                   </c:when>		                                   
                                	</c:choose>
                                	
                                	<c:if test="${imovelForm.usuarioDonoImovel.id == usuario.id}">                                      
                                          <div class="form-group">
                                             <input type="button" class="btn btn-primary" onClick="prepararModalPossivelInteressado();" value="<spring:message code="btn.modal.adicionar.possivel.interessado"/>">
                                          </div>
                                   </c:if>                                	 
                                </div>
                            </div>    
                          <!-- /.END Possivel Interessado --> 
                          
                          <!-- /.START Possivel Interessado Offline -->                           
                            <div class="panel rounded shadow">
                                <div class="panel-heading">                              
	                     			<h3 class="panel-title">
	                     				<div class="pull-left">
	                     					<spring:message code="lbl.title.aba.det.imovel.possivel.interessado.offline"/>
	                      				</div>	
	                     				<div class="pull-right">                                					
	                     					<a href="#a" class="btn btn-sm"  onClick="mostrarModal(13);" style=""><i class="fa fa-question" ></i></a>
	                     				</div>
	                     				<br>
	                     			</h3>
                                </div>
                                <div class="panel-body">
                                	<c:choose>
                                		<c:when test="${empty imovelForm.listaPossivelInteressadoOffline}">
                                			<div class="callout callout-warning">                                		
			                                    <strong><spring:message code="msg.nenhum.possivel.interessado"/></strong>		                                    
			                                </div>                             			    
			                                
	                                      </br> </br>	
                                		</c:when>
                                		
                                		<c:when test="${not empty imovelForm.listaPossivelInteressadoOffline}">
                                			 <table class="table table-striped" >
		                                         <thead>
		                                            <tr>		                                           
		                                               <th style="width: 15%;" class="text-center"><strong><spring:message code="lbl.data.cadastro.poss.interessado"/></strong></th>
		                                               <th style="width: 25%;" class="text-center"><strong><spring:message code="lbl.nome.poss.interessado"/></strong></th>
		                                               <th style="width: 15%;" class="text-center"><strong><spring:message code="lbl.email.poss.interessado"/></strong></th>
		                                               <th style="width: 10%;" class="text-center"><strong><spring:message code="lbl.telefone.poss.interessado"/></strong></th>                    
		                                               <th style="width: 15%; text-align: center;"><strong><spring:message code="lbl.chance.interesse.poss.interessado"/></strong></th>
		                                               <th style="width: 15%; text-align: center;"><strong><spring:message code="lbl.observacao.poss.interessado"/></strong></th>
		                                           	   <th style="width: 15%;" class="text-center"></th>	
		                                               <th style="width: 15%;" class="text-center"></th>	
		                                            </tr>
		                                         </thead>
		
		                                         <tbody>
		                                            <c:forEach var="interessado" items="${imovelForm.listaPossivelInteressadoOffline}">
		                                               <tr>
		                                               	  <td style="font-size: 13px;" class="text-center"> <small><fmt:formatDate value='${interessado.dataCadastro}' pattern='dd/MM/yyyy'/></small> </td>	
		                                               	  <td style="font-size: 13px;" class="text-center">${interessado.nomeInteressado} </td>
		                                               	  <td style="font-size: 13px;" class="text-center">${interessado.emailInteressado} </td>
		                                               	  <td style="font-size: 13px;" class="text-center">${interessado.telefoneInteressado} </td>
		                                                  <td style="font-size: 13px;" class="text-center"><small> ${interessado.chanceInteresseFmt} </small></td>		                          
		                                                  <td style="font-size: 13px;" class="text-center"><small> ${interessado.observacao} </small></td>
		                                                  <td class="text-center" >
		                                                     <a href="#a" onClick="prepararModalConfirmaEdicaoPossivelInteressadoOffline('${interessado.id}', '${interessado.nomeInteressado}', '${interessado.emailInteressado}', '${interessado.telefoneInteressado}', '${interessado.chanceInteresse}', '${interessado.observacao}');" ><i class="fa fa-pencil-square-o"></i></a>
		                                                  </td>		                                                  
		                                                  <td class="text-center" >
		                                                     <a href="#a" onClick="prepararModalConfirmaExclusaoPossivelInteressadoOffline(${interessado.id});" ><i class="fa fa-trash-o"></i></a>
		                                                  </td>
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>
		                                      </br> </br> 
		                                       <!-- botao ver mais  -->
				                                <c:if test="${imovelForm.isExibeMaisListaPossivelInteressadoOffline() }">
				                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;"> 
					                                     <a href="${urlImovel}/visualizarTodosPossivelInteressadoImovelOffline/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>
					                               		<br> <br>
					                                </div>
				                                </c:if>
		                                   </c:when>		                                   
                                	</c:choose>
                                	
                                	<c:if test="${imovelForm.usuarioDonoImovel.id == usuario.id}">                                      
                                          <div class="form-group">
                                             <input type="button" class="btn btn-primary" onClick="prepararModalPossivelInteressadoOffline();" value="<spring:message code="btn.modal.adicionar.possivel.interessado"/>">
                                          </div>
                                   </c:if>
                                	 
                                </div>
                            </div>                           
                           
                          <!-- /.END Possivel Interessado Offline -->  
              				
                          <!-- /.START Intermediação --> 
                          <c:if test="${((imovelForm.usuarioDonoImovel.perfil == 'P') && 
                          				 (usuario.perfil != 'P') && 
                          				 (imovelForm.autorizacaoOutroUsuario == 'S') && 
                          				 (usuario.id != imovelForm.usuarioDonoImovel.id))}">
                          				 
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
		                                   
		                               </div>
		                          </div>
		                          
		                          <div class="panel rounded shadow">
		                                <div class="panel-heading">
		                                    <h3 class="panel-title">	
		                                    	<div class="pull-left">		                                    	
		                              				<spring:message code="lbl.title.aba.det.intermediacoes.imovel.solicitacoes"/>
		                               			</div>
		                               				
		                              			<div class="pull-right">
		                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(4);" style=""><i class="fa fa-question" ></i></a>
		                              			</div>
		                              			<br>	
		                                    </h3>
		                                </div>
		                                <div class="panel-body">
		                                    <c:choose>
		                                    	<c:when test="${imovelForm.intermediacaoEnviada == null }">
		                                    	
		                                    		<div class="callout callout-warning">
					                                    <strong><spring:message code="lbl.nenhuma.sol.intermediacao.aceita"/></strong>		                                    
					                                </div>
				                                    
		                                    		<br>
		                                    		
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
		                                    	
		                                    	<c:when test="${imovelForm.intermediacaoEnviada != null }">
		                                    		 <table class="table table-striped" >
				                                         <thead>
				                                            <tr>	                                               
				                                               <th style="width: 25%"><strong><strong><spring:message code="lbl.data.sol"/></strong></th>
				                                               <th style="width: 60%;"><strong><spring:message code="lbl.desc.sol.parceria"/></strong></th>
				                                            </tr>
				                                         </thead>
				
				                                         <tbody>		                                            
				                                               <tr>				                                               	  	
				                                                  <td style="font-size: 13px;"><small><fmt:formatDate value='${imovelForm.intermediacaoEnviada.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
				                                                  <td style="font-size: 13px;"><small>${imovelForm.intermediacaoEnviada.descricaoCompartilhamento} </small> </td>
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
                          
                          <!-- /.START Intermediação - na visao do Dono Imóvel--> 
                            <c:if test="${((imovelForm.usuarioDonoImovel.perfil == 'P') && 
                          				   (usuario.perfil == 'P') &&      
                          				   (imovelForm.autorizacaoOutroUsuario == 'S') &&                      				   
                          				   (usuario.id == imovelForm.usuarioDonoImovel.id))}">
                          				                             			
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
		               		                                    
		                                    <c:if test="${imovelForm.intermediacaoEnviada != null }">
	                                    		<br>
	                                    		 <table class="table table-striped" >
			                                         <thead>
			                                            <tr>	                                               
			                                               <th style="width: 25%"><strong><strong><spring:message code="lbl.data.sol"/></strong></th>
			                                               <th style="width: 60%;"><strong><spring:message code="lbl.desc.sol.parceria"/></strong></th>
			                                            </tr>
			                                         </thead>
			
			                                         <tbody>		                                            
			                                               <tr>
			                                                  <td style="font-size: 13px;"><small><fmt:formatDate value='${imovelForm.intermediacaoEnviada.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
			                                                  <td style="font-size: 13px;"><small>${imovelForm.intermediacaoEnviada.descricaoCompartilhamento} </small> </td>
			                                               </tr>		                                            
			                                         </tbody>
			                                      </table>				                                      
		                                    </c:if>	
		                                   </div>
		                                </div>
		                        </c:if>
		                        
		                          <c:if test="${((imovelForm.usuarioDonoImovel.perfil == 'P') && 
		                          				   (usuario.perfil == 'P') &&      
		                          				   (imovelForm.autorizacaoOutroUsuario == 'S') &&                      				   
		                          				   (usuario.id == imovelForm.usuarioDonoImovel.id))}">
                          				   
                          				 <div class="panel rounded shadow">
			                                <div class="panel-heading">
			                                    <h3 class="panel-title">	
			                                    	<div class="pull-left">
			                              				<spring:message code="lbl.title.aba.det.intermediacoes.imovel.aceitas"/>
			                               			</div>
			                               				
			                              			<div class="pull-right">
			                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(4);" style=""><i class="fa fa-question" ></i></a>
			                              			</div>
			                              			<br>	
			                                    </h3>
			                                </div>
		                                 <div class="panel-body">		                                  
		                                    
		                                    	<!-- INICIO :: lista de intermediacao ACEITAS --> 
				                                    <c:choose>
				                                    	<c:when test="${not empty imovelForm.listaIntermediacaoAceitas}">
				                                    		<div class="panel-heading">
							                                    <div class="pull-left">
							                                       			                                       
							                                    </div>	
							                                    <div class="pull-right">
							                                        <spring:message code="lbl.link.analisar.sol.intermediacoes" var="mensagemAnalisarSol"/>
					                                                <a href="${urlIntermediacao}/analisarSolicitacoesIntermediacoesRecebidas/${imovelForm.id}" style="font-size:19px; color: rgb(99, 110, 123);" class="dropdown-toggle"><i class="fa fa-gavel"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> ${mensagemAnalisarSol} </font> &nbsp;&nbsp;</i> </a>	 			                                       
							                                    </div>
							                                    <div class="clearfix"></div>
							                                </div><!-- /.panel-heading -->
				                                    		 <table class="table table-striped" >
						                                         <thead>
						                                            <tr>				                                               
						                                               <th style="width: 15%"class="text-center"></th>
				                                               		   <th style="width: 15%"class="text-center"><spring:message code="lbl.nome.usuario.intermediacao"/></th>	                                               
						                                               <th style="width: 25%" class="text-center"><strong><spring:message code="lbl.data.sol"/></strong></th>
						                                               <th style="width: 60%;" class="text-center"><strong><spring:message code="lbl.desc.sol.parceria"/></strong></th>
						                                            </tr>
						                                         </thead>
						
						                                         <tbody>
						                                         	   <c:forEach items="${imovelForm.listaIntermediacaoAceitas}" var="imovelIntermediacao">
						                                         	   		<tr>
						                                         	   		  <td style="font-size: 13px;" class="text-center">
							                                               	  		<a href="${urlUsuario}/detalhesUsuario/${imovelIntermediacao.usuarioSolicitante.id}" >
																						<img src="data:image/jpeg;base64,${imovelIntermediacao.usuarioSolicitante.imagemArquivo}" style="width: 60px; height: 50px; " />
																					</a>
							                                               	  </td>	
							                                               	  
							                                               	  <td style="font-size: 13px;" class="text-center">
																	  				<a href="${urlUsuario}/detalhesUsuario/${imovelIntermediacao.usuarioSolicitante.id}" >
																							${imovelIntermediacao.usuarioSolicitante.nome}
																					</a>			                                               	  
							                                               	  </td>
							                                             
							                                                  <td style="font-size: 13px;" class="text-center"><small><fmt:formatDate value='${imovelIntermediacao.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
							                                                  <td style="font-size: 13px;" class="text-center"><small>${imovelIntermediacao.descricaoCompartilhamento} </small> </td>
							                                               </tr>		                                         	   
						                                         	   </c:forEach>	                                   
						                                         </tbody>
						                                      </table>	
						                                      <br></br>
						                                       <!-- botao ver mais  -->
								                                <c:if test="${imovelForm.isExibeMaisListaIntermediacaoAceita() }">
								                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;">
									                                    <a href="${urlIntermediacao}/analisarSolicitacoesIntermediacoesRecebidas/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>							                                    
									                               		<br> <br>
									                                </div>
								                                </c:if>
				                                    	</c:when>
				                                    	
				                                    	<c:when test="${empty imovelForm.listaIntermediacaoAceitas}">
				                                    		<div class="callout callout-warning">
							                                    <strong><spring:message code="lbl.nenhuma.sol.intermediacao"/></strong>		                                    
							                                </div>
				                                    	</c:when>		                                    	
				                                    </c:choose> 
				                                  </div>
				                                </div>
				                              <!-- FIM :: lista de intermediacao ACEITAS --> 
		                                <br>
		                                
		                                <!-- INICIO :: lista de intermediacao ACEITAS --> 
		                                <div class="panel rounded shadow">
			                                <div class="panel-heading">
			                                    <h3 class="panel-title">	
			                                    	<div class="pull-left">
			                              				<spring:message code="lbl.title.aba.det.intermediacoes.imovel.solicitadas"/>
			                               			</div>
			                               				
			                              			<div class="pull-right">
			                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(4);" style=""><i class="fa fa-question" ></i></a>
			                              			</div>
			                              			<br>	
			                                    </h3>
			                                </div>
		                                <div class="panel-body">  
		                                    	<!-- INICIO :: lista de intermediacao SOLICITADAS --> 
				                                    <c:choose>
				                                    	<c:when test="${not empty imovelForm.listaIntermediacao}">
				                                    		<div class="panel-heading">
							                                    <div class="pull-left">
							                                        			                                       
							                                    </div>	
							                                    <div class="pull-right">
							                                        <spring:message code="lbl.link.analisar.sol.intermediacoes" var="mensagemAnalisarSol"/>
					                                                <a href="${urlIntermediacao}/analisarSolicitacoesIntermediacoesRecebidas/${imovelForm.id}" style="font-size:19px; color: rgb(99, 110, 123);" class="dropdown-toggle"><i class="fa fa-gavel"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> ${mensagemAnalisarSol} </font> &nbsp;&nbsp;</i> </a>	 			                                       
							                                    </div>
							                                    <div class="clearfix"></div>
							                                </div><!-- /.panel-heading -->
				                                    		 <table class="table table-striped" >
						                                         <thead>
						                                            <tr>				                                               
						                                               <th style="width: 15%"class="text-center"></th>
				                                               		   <th style="width: 15%"class="text-center"><spring:message code="lbl.nome.usuario.intermediacao"/></th>	                                               
						                                               <th style="width: 25%" class="text-center"><strong><spring:message code="lbl.data.sol"/></strong></th>
						                                               <th style="width: 60%;" class="text-center"><strong><spring:message code="lbl.desc.sol.parceria"/></strong></th>
						                                            </tr>
						                                         </thead>
						
						                                         <tbody>
						                                         	   <c:forEach items="${imovelForm.listaIntermediacao}" var="imovelIntermediacao">
						                                         	   		<tr>
						                                         	   		  <td style="font-size: 13px;" class="text-center">
							                                               	  		<a href="${urlUsuario}/detalhesUsuario/${imovelIntermediacao.usuarioSolicitante.id}" >
																						<img src="data:image/jpeg;base64,${imovelIntermediacao.usuarioSolicitante.imagemArquivo}" style="width: 60px; height: 50px; " />
																					</a>
							                                               	  </td>	
							                                               	  
							                                               	  <td style="font-size: 13px;" class="text-center">
																	  				<a href="${urlUsuario}/detalhesUsuario/${imovelIntermediacao.usuarioSolicitante.id}" >
																							${imovelIntermediacao.usuarioSolicitante.nome}
																					</a>			                                               	  
							                                               	  </td>
							                                             
							                                                  <td style="font-size: 13px;" class="text-center"><small><fmt:formatDate value='${imovelIntermediacao.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
							                                                  <td style="font-size: 13px;" class="text-center"><small>${imovelIntermediacao.descricaoCompartilhamento} </small> </td>
							                                               </tr>		                                         	   
						                                         	   </c:forEach>	                                   
						                                         </tbody>
						                                      </table>	
						                                      <br></br>
						                                       <!-- botao ver mais  -->
								                                <c:if test="${imovelForm.isExibeMaisListaIntermediacao() }">
								                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;">
									                                    <a href="${urlIntermediacao}/analisarSolicitacoesIntermediacoesRecebidas/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>							                                    
									                               		<br> <br>
									                                </div>
								                                </c:if>
				                                    	</c:when>
				                                    	
				                                    	<c:when test="${empty imovelForm.listaIntermediacao}">
				                                    		<div class="callout callout-warning">
							                                    <strong><spring:message code="lbl.nenhuma.sol.intermediacao"/></strong>		                                    
							                                </div>
				                                    	</c:when>		                                    	
				                                    </c:choose> 				                              
		                                   </div>
		                                </div>
		                                 <!-- FIM :: lista de intermediacao SOLICITADAS --> 		                                
		                                	                                                           				   
                          		  </c:if>    
                          
                          <!-- /.END Intermediação - na visao do Dono Imóvel--> 
                          
                          <!-- /.START Parceria -->                          
                           <c:if test="${((imovelForm.usuarioDonoImovel.perfil != 'P') && 
                           				  (usuario.perfil != 'P') && 
                           				  (imovelForm.autorizacaoOutroUsuario == 'S') && 
                           				  (usuario.id != imovelForm.usuarioDonoImovel.id))}">
                           				  
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
		                                    	
					                       </div>
					                     </div>
					                     
					                     <div class="panel rounded shadow">
				                                <div class="panel-heading">
				                                    <h3 class="panel-title">
				                                    	<div class="pull-left">
				                              				<spring:message code="lbl.title.aba.det.parcerias.imovel.solicitacao"/>
				                               			</div>
				                               				
				                              			<div class="pull-right">
				                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(5);" style=""><i class="fa fa-question" ></i></a>
				                              			</div>
				                              			<br>
				                                    </h3>
				                                </div>
				                                <div class="panel-body">	
				                                
				                                <c:if test="${ imovelForm.parceriaEnviada == null }">	
				                                		
				                                      <div class="callout callout-warning">
														    <strong><spring:message code="lbl.nenhuma.intermediacao.solicitada"/></strong>		                                    
													 </div>	
													 	
														<br>
															                                      
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
					                           </c:if> 			                                		
				                                
				                                
				                                
				                              <c:if test="${ imovelForm.parceriaEnviada != null }">
							                          <div class="panel">							                              
								                               	
							                           		<table class="table table-striped" >
						                                         <thead>
						                                            <tr>	                                               
						                                               <th style="width: 25%"><strong><spring:message code="lbl.data.sol"/></strong></th>
						                                               <th style="width: 60%;"><strong><spring:message code="lbl.desc.sol.parceria"/></strong></th>
						                                            </tr>
						                                         </thead>
						
						                                         <tbody>		                                            
						                                               <tr>
						                                                  <td style="font-size: 13px;"><small><fmt:formatDate value='${imovelForm.parceriaEnviada.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
						                                                  <td style="font-size: 13px;"><small>${imovelForm.parceriaEnviada.descricaoCompartilhamento} </small> </td>
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
							                           </c:if>   
							                       </div>
							                    </div>					                                             
		                               </c:if>
		                          <!-- /.END Parceria -->
                          
                           <!-- /.START Parceria - na visao do Dono Imóvel--> 
                                <c:if test="${((imovelForm.usuarioDonoImovel.perfil != 'P') && 
                           				        (usuario.perfil != 'P') &&     
                           				        (imovelForm.autorizacaoOutroUsuario == 'S') &&                        				  
                           				        (usuario.id == imovelForm.usuarioDonoImovel.id))}">
                           			
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
		                                    
		                                      <c:if test="${ imovelForm.parceriaEnviada != null }">
		                                      		<br>
					                           		 <div class="panel">
						                                <div class="panel-heading">
						                                    <div class="pull-left">
						                                    	</br>
						                                        <h7 class="panel-title"><spring:message code="lbl.title.aba.parceria.det.imovel"/></h7>						                                        
						                                    </div>
						                                </div>
						                               	
					                           		<table class="table table-striped" >
				                                         <thead>
				                                            <tr>	                                               
				                                               <th style="width: 25%"><strong><spring:message code="lbl.data.sol"/></strong></th>
				                                               <th style="width: 60%;"><strong><spring:message code="lbl.desc.sol.parceria"/></strong></th>
				                                            </tr>
				                                         </thead>
				
				                                         <tbody>		                                            
				                                               <tr>
				                                                  <td style="font-size: 13px;"><small><fmt:formatDate value='${imovelForm.parceriaEnviada.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
				                                                  <td style="font-size: 13px;"><small>${imovelForm.parceriaEnviada.descricaoCompartilhamento} </small> </td>
				                                               </tr>		                                            
				                                         </tbody>
				                                      </table>				                                      
					                                  </div>    
					                           </c:if> 
					                        </div>
					                      </div>
					                   </c:if>
					                   
					                    <c:if test="${( (imovelForm.usuarioDonoImovel.perfil != 'P') && 
		                           				        (usuario.perfil != 'P') &&     
		                           				        (imovelForm.autorizacaoOutroUsuario == 'S') &&                        				  
		                           				        (usuario.id == imovelForm.usuarioDonoImovel.id))}">
		                           			<div class="panel rounded shadow">
				                                <div class="panel-heading">
				                                    <h3 class="panel-title">
				                                    	<div class="pull-left">
				                              				<spring:message code="lbl.title.aba.det.parcerias.imovel.aceitas"/>
				                               			</div>
				                               				
				                              			<div class="pull-right">
				                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(5);" style=""><i class="fa fa-question" ></i></a>
				                              			</div>
				                              			<br>
				                                    </h3>
				                                </div>
				                                <div class="panel-body">		                                
				                                    
				                                    <!-- INICIO :: lista de parcerias ACEITAS --> 
							                            <c:choose>
				                                  	   		<c:when test="${not empty imovelForm.listaParceriaAceitas}">
				                                  	   			<div class="panel">
									                                <div class="panel-heading">
																	    <div class="pull-left">
																					                                       
																	    </div>	
																	    <div class="pull-right">																	
																			<spring:message code="lbl.link.analisar.sol.intermediacoes" var="mensagemAnalisarSol"/>
			                                                 			    <a href="${urlParceria}/analisarSolicitacoesParceriasRecebidas/${imovelForm.id}" style="font-size:19px; color: rgb(99, 110, 123);" class="dropdown-toggle" ><i class="fa fa-gavel"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> ${mensagemAnalisarSol} </font>&nbsp;&nbsp;</i> </a>                                                 					                                       
																	    </div>
																	    <div class="clearfix"></div>
																	</div><!-- /.panel-heading -->
									                               	
								                           		<table class="table table-striped" >
							                                         <thead>
							                                            <tr>	                                               
							                                               <th style="width: 10%"></th>
							                                               <th style="width: 10%" class="text-center"><spring:message code="lbl.nome.usuario.parceria"/></th>
							                                               <th style="width: 25%" class="text-center"><strong><spring:message code="lbl.data.sol"/></strong></th>
							                                               <th style="width: 60%;" class="text-center"><strong><spring:message code="lbl.desc.sol.parceria"/></strong></th>
							                                            </tr>
							                                         </thead>
							
							                                         <tbody>
							                                         	   <c:forEach items="${imovelForm.listaParceriaAceitas}" var="imovelParceria" >
							                                         	   		<tr>
							                                         	   		  <td style="font-size: 13px;" class="text-center">
								                                               	  		<a href="${urlUsuario}/detalhesUsuario/${imovelParceria.usuarioSolicitante.id}" >
																							<img src="data:image/jpeg;base64,${imovelParceria.usuarioSolicitante.imagemArquivo}" style="width: 60px; height: 50px; " />
																						</a>
								                                               	  </td>	
								                                               	  
								                                               	  <td style="font-size: 13px;" class="text-center">
																		  				<a href="${urlUsuario}/detalhesUsuario/${imovelParceria.usuarioSolicitante.id}" >
																								${imovelParceria.usuarioSolicitante.nome}
																						</a>			                                               	  
								                                               	  </td>						                                         	   		 
								                                                  <td style="font-size: 13px;" class="text-center"><small><fmt:formatDate value='${imovelParceria.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
								                                                  <td style="font-size: 13px;" class="text-center"><small>${imovelParceria.descricaoCompartilhamento} </small> </td>
								                                                </tr>
							                                         	   </c:forEach>		                                            
							                                         </tbody>
							                                      </table>	
							                                      <br></br>
							                                       <!-- botao ver mais  -->
									                                <c:if test="${imovelForm.isExibeMaisListaParceria() }">
									                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;"> 
										                                    <a href="${urlParceria}/analisarSolicitacoesParceriasRecebidas/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>
										                               		<br> <br>
										                                </div>
									                                </c:if>			                                     
									                           </div>
				                                  	   		</c:when>
				                                  	   		
				                                  	   		<c:when test="${empty imovelForm.listaParceriaAceitas}">
				                                  	   			<div class="callout callout-warning">
																    <strong><spring:message code="lbl.nenhuma.sol.parceria.aceita"/></strong>		                                    
																</div>
				                                  	   		</c:when>
				                                  	   </c:choose>
							                          </div>
							                         </div> 
							                            
							                           <!-- FIM :: lista de parcerias ACEITAS -->
				                                    
				                                       <br>		                                    
				                                  	   
				                                  	   <!-- INICIO :: lista de parcerias SOLICITADAS -->
				                                  	   <div class="panel rounded shadow">
							                                <div class="panel-heading">
							                                    <h3 class="panel-title">
							                                    	<div class="pull-left">
							                              				<spring:message code="lbl.title.aba.det.parcerias.imovel.solicitadas"/>
							                               			</div>
							                               				
							                              			<div class="pull-right">
							                              				<a href="#a" class="btn btn-sm"  onClick="mostrarModal(5);" style=""><i class="fa fa-question" ></i></a>
							                              			</div>
							                              			<br>
							                                    </h3>
							                                </div>
							                                <div class="panel-body">	
				                                  	   <c:choose>
				                                  	   		<c:when test="${not empty imovelForm.listaParceria}">
				                                  	   			<div class="panel">
									                                <div class="panel-heading">
																	    <div class="pull-left">
																						                                       
																	    </div>	
																	    <div class="pull-right">																	
																			<spring:message code="lbl.link.analisar.sol.intermediacoes" var="mensagemAnalisarSol"/>
			                                                 			    <a href="${urlParceria}/analisarSolicitacoesParceriasRecebidas/${imovelForm.id}" style="font-size:19px; color: rgb(99, 110, 123);" class="dropdown-toggle" ><i class="fa fa-gavel"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;"> ${mensagemAnalisarSol} </font>&nbsp;&nbsp;</i> </a>                                                 					                                       
																	    </div>
																	    <div class="clearfix"></div>
																	</div><!-- /.panel-heading -->
									                               	
								                           		<table class="table table-striped" >
							                                         <thead>
							                                            <tr>	                                               
							                                               <th style="width: 10%"></th>
							                                               <th style="width: 10%" class="text-center"><spring:message code="lbl.nome.usuario.parceria"/></th>
							                                               <th style="width: 25%" class="text-center"><strong><spring:message code="lbl.data.sol"/></strong></th>
							                                               <th style="width: 60%;" class="text-center"><strong><spring:message code="lbl.desc.sol.parceria"/></strong></th>
							                                            </tr>
							                                         </thead>
							
							                                         <tbody>
							                                         	   <c:forEach items="${imovelForm.listaParceria}" var="imovelParceria" >
							                                         	   		<tr>
							                                         	   		  <td style="font-size: 13px;" class="text-center">
								                                               	  		<a href="${urlUsuario}/detalhesUsuario/${imovelParceria.usuarioSolicitante.id}" >
																							<img src="data:image/jpeg;base64,${imovelParceria.usuarioSolicitante.imagemArquivo}" style="width: 60px; height: 50px; " />
																						</a>
								                                               	  </td>	
								                                               	  
								                                               	  <td style="font-size: 13px;" class="text-center">
																		  				<a href="${urlUsuario}/detalhesUsuario/${imovelParceria.usuarioSolicitante.id}" >
																								${imovelParceria.usuarioSolicitante.nome}
																						</a>			                                               	  
								                                               	  </td>						                                         	   		 
								                                                  <td style="font-size: 13px;" class="text-center"><small><fmt:formatDate value='${imovelParceria.dataSolicitacao}' pattern='dd/MM/yyyy'/></small></td>
								                                                  <td style="font-size: 13px;" class="text-center"><small>${imovelParceria.descricaoCompartilhamento} </small> </td>
								                                                </tr>
							                                         	   </c:forEach>		                                            
							                                         </tbody>
							                                      </table>	
							                                      <br></br>
							                                       <!-- botao ver mais  -->
									                                <c:if test="${imovelForm.isExibeMaisListaParceria() }">
									                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;"> 
										                                    <a href="${urlParceria}/analisarSolicitacoesParceriasRecebidas/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>
										                               		<br> <br>
										                                </div>
									                                </c:if>			                                     
									                           </div>
				                                  	   		</c:when>
				                                  	   		
				                                  	   		<c:when test="${empty imovelForm.listaParceria}">
				                                  	   			<div class="callout callout-warning">
																    <strong><spring:message code="lbl.nenhuma.sol.parceria"/></strong>		                                    
																</div>
				                                  	   		</c:when>
				                                  	   </c:choose>
				                                  	   </div>
				                                  	   </div>
				                                  	    <!-- FIM :: lista de parcerias solicitadas -->
				                                      
							                              		                           				        
				                           </c:if>
					                                         
                           <!-- /.END Parceria - na visao do Dono Imóvel--> 
                         
                          <!-- /.START Visitas -->
                          <c:if test="${imovelForm.usuarioDonoImovel.id == usuario.id}">
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
                                		<c:when test="${empty imovelForm.listaVisita }"> 
			                                	 <div class="callout callout-warning">
					                                    <strong><spring:message code="msg.nenhuma.visualizacao"/></strong>		                                    
					                              </div>
                                		</c:when>
                                		
                                		<c:when test="${not empty imovelForm.listaVisita }">
                                			<table class="table table-striped" >
		                                         <thead>
		                                            <tr>
		                                               <th style="width: 5%;" class="text-center"> </th>
		                                               <th style="width: 10%;" class="text-center"><strong><spring:message code="lbl.visualizacao.nome.usuario"/></strong></th>
		                                               <th style="width: 25%" class="text-center"><strong><strong><spring:message code="lbl.data.visualizacao"/></strong></th>                                               
		                                            </tr>
		                                         </thead>
		
		                                         <tbody>
		                                            <c:forEach var="imovelVisita" items="${imovelForm.listaVisita}">
		                                               <tr>
		                                               	  <td class="text-center">
																<a href="${urlUsuario}/detalhesUsuario/${imovelVisita.usuario.id}">
																	<img src="data:image/jpeg;base64,${imovelVisita.usuario.imagemArquivo}" style="width: 60px; height: 50px; " />	                				
																</a> 
														  </td>	
		                                                  <td style="font-size: 13px;" class="text-center"><small><a href="${urlUsuario}/detalhesUsuario/${imovelVisita.id}">
																		${imovelVisita.usuario.nome}	                				
																	</a>  
															  </small>
														  </td>
		                                                  <td class="text-center"><small><fmt:formatDate value='${imovelVisita.dataVisita}' pattern='dd/MM/yyyy'/></small></td>
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>	
		                                      <br></br>
		                                       <!-- botao ver mais  -->
				                                <c:if test="${imovelForm.isExibeMaisListaVisita() }">
				                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;"> 
					                                    <a href="${urlImovelVisualizado}/listarTodosUsuariosVisitantes/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>
					                               		<br> <br>
					                                </div>
				                                </c:if>	
                                		</c:when>
                                	</c:choose>                                   
                                </div>
                               </div> 
                            </c:if>
                          <!-- /.END Visitas -->  
                          
                          <!-- /.START Usuarios Interessados-->	
							<c:if test="${imovelForm.usuarioDonoImovel.id == usuario.id}">	
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
		                                               <th style="width: 5%;" class="text-center"> </th>
		                                               <th style="width: 10%;" class="text-center"><strong><spring:message code="lbl.nome.usuario.lista.interesse"/></strong></th>
		                                               <th style="width: 25%" class="text-center"><strong><strong><spring:message code="lbl.data.interesse"/></strong></th>                                               
		                                            </tr>
		                                         </thead>
		
		                                         <tbody>
		                                            <c:forEach var="imovelFavorito" items="${imovelForm.listaUsuariosInteressados}">
		                                               <tr>
		                                               	  <td class="text-center">
																<a href="${urlUsuario}/detalhesUsuario/${imovelFavorito.usuario.id}">
																	<img src="data:image/jpeg;base64,${imovelFavorito.usuario.imagemArquivo}" style="width: 60px; height: 50px; " />	                				
																</a> 
														  </td>	
		                                                  <td style="font-size: 13px;" class="text-center"><small><a href="${urlUsuario}/detalhesUsuario/${imovelFavorito.usuario.id}">
																		${imovelFavorito.usuario.nome}	                				
																	</a> 
															  </small>
														  </td>
		                                                  <td style="font-size: 13px;" class="text-center"><small><fmt:formatDate value='${imovelFavorito.dataInteresse}' pattern='dd/MM/yyyy'/></small></td>
		                                               </tr>
		                                            </c:forEach>
		                                         </tbody>
		                                      </table>
		                                      <br></br>
		                                       <!-- botao ver mais  -->
				                                <c:if test="${imovelForm.isExibeMaisListaUsuariosInteressados() }">
				                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;"> 
					                                    <a href="${urlImovelFavoritos}/listarTodosFavoritosUsuario/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>
					                               		<br> <br>
					                                </div>
				                                </c:if>	
                                		</c:when>
                                	</c:choose>                                   
                                </div>
                               </div> 
							</c:if>
						  <!-- /.END Usuarios Interessados-->	
						  
						  <!-- /.START Comentarios -->																
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
			                                                    <img class="media-object thumbnail" src="data:image/jpeg;base64,${imovel.usuarioComentario.imagemArquivo}" style="width: 50px; height: 60px;"  />
			                                                </a>
			                                            </div>
			                                            <div class="media-body">
			                                                <h4>${imovel.usuarioComentario.nome}</h4>
			                                                <br>
			                                                <p>${imovel.comentario}</p>			                                                
			                                                <small class="text-muted"><fmt:formatDate value='${imovel.dataComentario}' pattern='dd/MM/yyyy HH:mm:ss'/></small>
			                                            </div>
			                                        </li><!-- media -->											
												</c:forEach>
		                                    </ul>
		                                    <br><br/>
		                                      <!-- botao ver mais  -->
				                                <c:if test="${imovelForm.isExibeMaisListaComentarios() }">
				                                	<div class="dropdown-footer" align="center" style="font-size: 14px; font-style: inherit;"> 
					                                    <a href="${urlImovelComentario}/listarTodosComentariosImovel/${imovelForm.id}"><strong><spring:message code="lbl.title.see.all"/></strong></a>
					                               		<br> <br>
					                                </div>
				                                </c:if>
												<br>
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
                                 <!-- /.END Comentarios -->
                            </div>
                            
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
          
          <!-- START Modal - Adicionar Possivel Comprador Offline -->
            <div id="idModalPossivelInteressadoOffline" class="modal fade bs-example-modal-form-comprador-offline" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.cadastrar.possivel.interessado.offline"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form" id="input-mask">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.nome.poss.interessado"/></label>
                                        <div class="col-sm-5">
                                        	<input type="text" class="form-control" id="nomeInteressado"  > 
                                            <div id="msgErronomeInteressado" cssClass="errorEntrada"  ></div>                                
                                        </div>
                                    </div><!-- /.form-group -->
                                    
                                     <div class="form-group">
	                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.email.poss.interessado"/></label>
	                                        <div class="col-sm-5">
	                                        	<input type="text" class="form-control" id="emailInteressado"  > 
	                                            <div id="msgErroemailInteressado" cssClass="errorEntrada"  ></div>                                
	                                        </div>
	                                  </div><!-- /.form-group -->
	                                  
	                                  <div class="form-group">
	                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.telefone.poss.interessado"/></label>
	                                        <div class="col-sm-5">
	                                        	<input type="text" class="form-control" id="telefoneInteressado"  > 
	                                            <div id="msgErrotelefoneInteressado" cssClass="errorEntrada"  ></div>                                
	                                        </div>
	                                   </div><!-- /.form-group -->  
	                                   
	                                       <div class="form-group">
	                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.chance.interesse.poss.interessado"/></label>
	                                        <div class="col-sm-5">
	                                        	<select class="form-control" id="chanceInteresseOffline" >
												  <option value="MA" selected="selected"><spring:message code="lbl.poss.interessado.chance.muito.alta"/></option>
												  <option value="AL"><spring:message code="lbl.poss.interessado.chance.alta"/></option>
												  <option value="ME"><spring:message code="lbl.poss.interessado.chance.media"/></option>
												  <option value="BA"><spring:message code="lbl.poss.interessado.chance.baixa"/></option>
												  <option value="MB"><spring:message code="lbl.poss.interessado.chance.muito.baixa"/></option>
												</select>   
	                                            <div id="msgErrochanceInteresse" cssClass="errorEntrada"  ></div>                                 
	                                        </div>
	                                    </div><!-- /.form-group -->  
	                                    
	                                    <div class="form-group">
	                                        <label for="password-1" class="col-sm-3 control-label"><spring:message code="lbl.observacao.poss.interessado"/></label>
	                                        <div class="col-sm-5">                                            
	                                            <textarea rows="5" cols="20" class="form-control " id="observacaoPossInteressado"></textarea>
	                                            <div id="msgErroNovaDescricaoAtividade" cssClass="errorEntrada"  ></div>
	                                        </div>
	                                    </div><!-- /.form-group -->                                  
                                                                                                         
									<div class="form-group">                                        
                                        <div id="msgObservacaoPossCompradorErro" class="col-sm-4"> </div>
                                    </div><!-- /.form-group -->
                                                                        
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="adicionarPossivelInteressadoOffline();"><spring:message code="lbl.btn.adicionar.geral"/></button>
                                    </div>
                                </div><!-- /.form-footer -->
                                
                                <div id="msgRetornoPossCompradorOfflineErro"> </div>
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ END Modal - Adicionar Possivel Comprador Offline -->  
            
            <!-- START Modal - Editar Possivel Comprador Offline -->
            <div id="idModalPossivelInteressadoOfflineEdicao" class="modal fade bs-example-modal-form-comprador-offline-edicao" tabindex="-1" role="dialog" aria-hidden="true">
              	<input type="hidden" id="modIdPossivelInteressadoOfflineEdicao" readonly="readonly" name="modIdPossivelInteressadoOfflineEdicao">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.editar.possivel.interessado.offline"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form" id="input-mask">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.nome.poss.interessado"/></label>
                                        <div class="col-sm-5">
                                        	<input type="text" class="form-control" id="nomeInteressadoEdicao"  > 
                                            <div id="msgErronomeInteressado" cssClass="errorEntrada"  ></div>                                
                                        </div>
                                    </div><!-- /.form-group -->
                                    
                                     <div class="form-group">
	                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.email.poss.interessado"/></label>
	                                        <div class="col-sm-5">
	                                        	<input type="text" class="form-control" id="emailInteressadoEdicao"  > 
	                                            <div id="msgErroemailInteressado" cssClass="errorEntrada"  ></div>                                
	                                        </div>
	                                  </div><!-- /.form-group -->
	                                  
	                                  <div class="form-group">
	                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.telefone.poss.interessado"/></label>
	                                        <div class="col-sm-5">
	                                        	<input type="text" class="form-control" id="telefoneInteressadoEdicao"  > 
	                                            <div id="msgErrotelefoneInteressado" cssClass="errorEntrada"  ></div>                                
	                                        </div>
	                                   </div><!-- /.form-group -->  
	                                   
	                                       <div class="form-group">
	                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.chance.interesse.poss.interessado"/></label>
	                                        <div class="col-sm-5">
	                                        	<select class="form-control" id="chanceInteresseOfflineEdicao" >
												  <option value="MA" selected="selected"><spring:message code="lbl.poss.interessado.chance.muito.alta"/></option>
												  <option value="AL"><spring:message code="lbl.poss.interessado.chance.alta"/></option>
												  <option value="ME"><spring:message code="lbl.poss.interessado.chance.media"/></option>
												  <option value="BA"><spring:message code="lbl.poss.interessado.chance.baixa"/></option>
												  <option value="MB"><spring:message code="lbl.poss.interessado.chance.muito.baixa"/></option>
												</select>   
	                                            <div id="msgErrochanceInteresse" cssClass="errorEntrada"  ></div>                                 
	                                        </div>
	                                    </div><!-- /.form-group -->  
	                                    
	                                    <div class="form-group">
	                                        <label for="password-1" class="col-sm-3 control-label"><spring:message code="lbl.observacao.poss.interessado"/></label>
	                                        <div class="col-sm-5">                                            
	                                            <textarea rows="5" cols="20" class="form-control " id="observacaoPossInteressadoEdicao"></textarea>
	                                            <div id="msgErroNovaDescricaoAtividade" cssClass="errorEntrada"  ></div>
	                                        </div>
	                                    </div><!-- /.form-group -->                                  
                                                                                                         
									<div class="form-group">                                        
                                        <div id="msgObservacaoPossInteressadoErro" class="col-sm-4"> </div>
                                    </div><!-- /.form-group -->
                                                                        
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="editarPossivelInteressadoOffline();"><spring:message code="lbl.btn.editar.geral"/></button>
                                    </div>
                                </div><!-- /.form-footer -->
                                
                                <div id="msgRetornoPossInteressadoOfflineErro"> </div>
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ END Modal - Editar Possivel Interessado Offline -->  
            
             <!-- START - confirmacao exclusao possivel interessado offline -->
            <div id="idModalConfirmacaoExclusaoPossivelInteressadoOffline" class="modal fade bs-example-modal-lg-confirmacao-exclusao-poss-interessado-offline" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdPossivelInteressadoOffline" readonly="readonly" name="modIdPossivelInteressadoOffline">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.exclusao.possivel.interessado.offline"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.exclusao.possivel.interessado.offline"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="confirmarExclusaoPossivelInteressadoOfflineImovel();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoConfirmExclusaoPossivelInteressadoOfflineErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- END - confirmacao exclusao possivel interessado offline   -->   
         
          <!-- START Modal - Editar Possivel Interessado -->
            <div id="idModalPossivelInteressadoEdicao" class="modal fade bs-example-modal-form-interessado-offline-edicao" tabindex="-1" role="dialog" aria-hidden="true">
              	<input type="hidden" id="modIdPossivelInteressadoEdicao" readonly="readonly" name="modIdPossivelInteressadoEdicao">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.editar.possivel.interessado"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form" id="input-mask">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.nome.poss.interessado"/></label>
                                        <div class="col-sm-5">
                                        	<input type="text" class="form-control" id="nomeInteressadoEdicao" readonly="true"> 
                                            <div id="msgErronomeInteressado" cssClass="errorEntrada"  ></div>                                
                                        </div>
                                    </div><!-- /.form-group -->                                    
	                                                                    
	                                   <div class="form-group">
	                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.chance.interesse.poss.interessado"/></label>
	                                        <div class="col-sm-5">
	                                        	<select class="form-control" id="chanceInteresseEdicao" >
												  <option value="MA" selected="selected"><spring:message code="lbl.poss.interessado.chance.muito.alta"/></option>
												  <option value="AL"><spring:message code="lbl.poss.interessado.chance.alta"/></option>
												  <option value="ME"><spring:message code="lbl.poss.interessado.chance.media"/></option>
												  <option value="BA"><spring:message code="lbl.poss.interessado.chance.baixa"/></option>
												  <option value="MB"><spring:message code="lbl.poss.interessado.chance.muito.baixa"/></option>
												</select>   
	                                            <div id="msgErrochanceInteresse" cssClass="errorEntrada"  ></div>                                 
	                                        </div>
	                                    </div><!-- /.form-group -->  
	                                    
	                                    <div class="form-group">
	                                        <label for="password-1" class="col-sm-3 control-label"><spring:message code="lbl.observacao.poss.interessado"/></label>
	                                        <div class="col-sm-5">                                            
	                                            <textarea rows="5" cols="20" class="form-control " id="obsPossInteressadoEdicao"></textarea>
	                                            <div id="msgErroNovaDescricaoAtividade" cssClass="errorEntrada"  ></div>
	                                        </div>
	                                    </div><!-- /.form-group -->                                  
                                                                                                         
									<div class="form-group">                                        
                                        <div id="msgObservacaoPossInteressadoErro" class="col-sm-4"> </div>
                                    </div><!-- /.form-group -->
                                                                        
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="editarPossivelInteressado();"><spring:message code="lbl.btn.editar.geral"/></button>
                                    </div>
                                </div><!-- /.form-footer -->
                                
                                <div id="msgRetornoPossInteressadoOfflineErro"> </div>
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ END Modal - Editar Possivel Comprador -->  
          

        <!-- START Modal - Adicionar Atividades -->
            <div id="idModalAtividades" class="modal fade bs-example-modal-form-atividades" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.cadastrar.atividades"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form" id="input-mask">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.status.atividade"/></label>
                                        <div class="col-sm-5">
                                        	<select class="form-control" id="novaAtividade" >
											  <option value="C" selected="selected"><spring:message code="lbl.status.atividade.criado"/></option>
											  <option value="I"><spring:message code="lbl.status.atividade.incompleto"/></option>
											  <option value="N"><spring:message code="lbl.status.atividade.cancelado"/></option>
											  <option value="O"><spring:message code="lbl.status.atividade.completo"/></option>
											</select>   
                                            <div id="msgErroAtividade" cssClass="errorEntrada"  ></div>                                            
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="password-1" class="col-sm-3 control-label"><spring:message code="lbl.descricao.atividade"/></label>
                                        <div class="col-sm-5">                                            
                                            <textarea rows="5" cols="20" class="form-control " id="novaDescricaoAtividade"></textarea>
                                            <div id="msgErroNovaDescricaoAtividade" cssClass="errorEntrada"  ></div>
                                        </div>
                                    </div><!-- /.form-group -->                                    
									<div class="form-group">                                        
                                        <div id="msgAtividadeErro" class="col-sm-4"> </div>
                                    </div><!-- /.form-group -->                                    
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="adicionarAtividade();"><spring:message code="lbl.btn.adicionar.geral"/></button>
                                    </div>
                                </div><!-- /.form-footer -->
                                
                                <div id="msgRetornoAtividadeErro"> </div>
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ END Modal - Adicionar Atividades -->
            
              <!-- START Modal - Edicao Atividades -->
            <div id="idModalEdicaoAtividades" class="modal fade bs-example-modal-form-edicao-atividades" tabindex="-1" role="dialog" aria-hidden="true">
                <input type="hidden" id="modIdAtividadeEdicao" readonly="readonly" name="modIdAtividadeEdicao">
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.editar.atividades"/></h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="form-horizontal form-bordered" role="form" id="input-mask">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label for="firstname-1" class="col-sm-3 control-label"><spring:message code="lbl.status.atividade"/></label>
                                        <div class="col-sm-5">
                                        	<select class="form-control" id="novaAtividadeEdicao" >
											  <option value="C" selected="selected"><spring:message code="lbl.status.atividade.criado"/></option>
											  <option value="I"><spring:message code="lbl.status.atividade.incompleto"/></option>
											  <option value="N"><spring:message code="lbl.status.atividade.cancelado"/></option>
											  <option value="O"><spring:message code="lbl.status.atividade.completo"/></option>
											</select>   
                                            <div id="msgErroAtividade" cssClass="errorEntrada"  ></div>                                            
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="password-1" class="col-sm-3 control-label"><spring:message code="lbl.descricao.atividade"/></label>
                                        <div class="col-sm-5">                                            
                                            <textarea rows="5" cols="20" class="form-control " id="novaDescricaoAtividadeEdicao"></textarea>
                                            <div id="msgErroNovaDescricaoAtividade" cssClass="errorEntrada"  ></div>
                                        </div>
                                    </div><!-- /.form-group -->                                    
									<div class="form-group">                                        
                                        <div id="msgAtividadeErro" class="col-sm-4"> </div>
                                    </div><!-- /.form-group -->                                    
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="editarAtividade();"><spring:message code="lbl.btn.editar.geral"/></button>
                                    </div>
                                </div><!-- /.form-footer -->
                                
                                <div id="msgRetornoAtividadeErro"> </div>
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!--/ END Modal - Edicao Atividades -->
            
            <!-- START - confirmacao exclusao atividade imovel -->
            <div id="idModalConfirmacaoExclusaoAtividade" class="modal fade bs-example-modal-lg-confirmacao-exclusao-proposta-imovel" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdAtividade" readonly="readonly" name="modIdAtividade">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.exclusao.atividade"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.exclusao.atividade"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="confirmarExclusaoAtividadeImovel();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoConfirmExclusaoAtividadeErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- START - confirmacao exclusao atividade imovel   --> 
            
        
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
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="novaProposta2" onkeypress="formatarMoeda(this);" > 
                                            <div id="msgErroNovaProposta2" cssClass="errorEntrada"  ></div>                                            
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="password-1" class="col-sm-3 control-label"><spring:message code="lbl.observacao.proposta.imovel"/></label>
                                        <div class="col-sm-5">                                            
                                            <textarea rows="5" cols="20" class="form-control " id="novaObsProposta2"></textarea>
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
                                            <textarea rows="5" cols="20" class="form-control " id="novoComentario2"></textarea>
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
                                            <textarea rows="5" cols="20" class="form-control " id="novaSolIntermediacao"></textarea>
                                            <div id="msgRetornoSolIntermediacaoErro" cssClass="errorEntrada"  ></div>											
                                        </div>
                                    </div><!-- /.form-group -->
                                   
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.cancelar.geral"/></button>
                                        <button type="button" class="btn btn-success" onClick="adicionarSolIntermediacao();" ><spring:message code="lbl.btn.confirmar.geral"/></button>
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
                                            <textarea rows="5" cols="20" class="form-control " id="novaSolParceria"></textarea>
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