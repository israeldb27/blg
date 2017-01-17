<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${context}/js/jquery-ui.js"></script>
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>
<spring:url var="urlImovelFavoritos" value="/imovelFavoritos"/>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

   <script type="text/javascript">
   
	   $(document).ready(function() {		   
		   carregarTimeline();		   
		   $('#quote-carousel').carousel({
			    pause: true,
			    interval: 4000,
			  });	    
	  });
	   
	   var parametro1 = $("#idEstado").val();
	    $.get("${urlBuscarCidades}/"+parametro1, function(data){
	        jQuery.each(data, function(key, value) {
	            if(value.label == null){            	
	                return;
	            }            
	            $("#idCidade").append("<option value='"+value.key+"'>"+value.label+"</option>");
	        });
	    });
	   
	  function carregarTimeline(){
			$.ajax({  
			    type: 'GET',	
		         url: '${urlUsuario}/carregarTimeLine',
		         dataType: 'json',
		         success: function(data){	        	 
		        	 jQuery.each( data, function( i, val ) {
		        		 $('#idTimeLine').append(val);
		        	  });
		         },	      
		     }); 
	  } 
	  
	  function enviarConvite(id) {	
			var parametro1 = id;
		    $.ajax({                
		        url: '${urlContato}/enviarConvite/' + parametro1,                
		        success: function(){
		        	$("#idEnviarConvite").hide();        	
			    	$("#idCancelarConvite").show();	    	    	
		        },
		        error: function(jqXHR, textStatus, errorThrown) {
		            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		        }
		    });
		}

		function prepararModalCancelarContato(id){	
			$("#modIdContato").val(id);
			$("#msgRetornoConfirmCancelContatoErro").html("");	
			$("#idModalConfirmacaoCancelContato").modal("show");	
		}

		function cancelarContato() {	
			var parametro1 = document.getElementById("modIdContato");	
		    $.ajax({                
		        url: '${urlContato}/cancelarContato/' + parametro1.value,                
		        success: function(){
		        	$("#idCancelarContato").hide();
		        	$("#idEnviarConvite").show();  
		        	$("#idModalConfirmacaoCancelContato").modal("hide");
		        },
		        error: function(jqXHR, textStatus, errorThrown) {
		            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		        }
		    });
		}

		function prepararModalCancelarConvite(id){
			$("#modIdConvite").val(id);
			$("#msgRetornoConfirmCancelConviteErro").html("");	
			$("#idModalConfirmacaoCancelConvite").modal("show");	
		}

		function cancelarConvite() {	
			var parametro1 = document.getElementById("modIdConvite");
		    $.ajax({                
		        url: '${urlContato}/cancelarConvite/' + parametro1.value,                
		        success: function(){
		        	$("#idCancelarConvite").hide();
		        	$("#idEnviarConvite").show();
		        	$("#idModalConfirmacaoCancelConvite").modal("hide");
		        },
		        error: function(jqXHR, textStatus, errorThrown) {
		            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		        }
		    });
		}

		function iniciarSeguirUsuario(id) {
			var parametro1 = id;	
		    $.ajax({                
		        url: '${urlUsuario}/iniciarSeguirUsuario/' + parametro1,                
		        success: function(){
		        	$("#idIniciarSeguidor").hide();        	
		        	$("#idCancelarSeguidor").show();
		        },
		        error: function(jqXHR, textStatus, errorThrown) {
		            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		        }
		    });
		}

		function cancelarSeguirUsuario(id) {	
			var parametro1 = id;
		    $.ajax({                
		        url: '${urlUsuario}/cancelarSeguirUsuario/' + parametro1,                
		        success: function(){
		        	$("#idCancelarSeguidor").hide();
		        	$("#idIniciarSeguidor").show(); 
		        },
		        error: function(jqXHR, textStatus, errorThrown) {
		            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		        }
		    });
		}
		
		function adicionarInteresse(id) {    	
    		var parametro1 = id;
    	    $.ajax({                
                url: '${urlImovelFavoritos}/adicionarFavoritos/' + parametro1,                
                success: function(){
                	$("#idMeInteressei_"+id).hide();
                	$("#idNovoMeInteressei_"+id).hide();
	    	    	$("#idInteressado_"+id).show();	    	    	
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
                	$("#idNovoMeInteressei_"+id).show();                	
	    	    	$("#idInteressado_"+id).hide();
	    	    	$("#idNovoInteressado_"+id).hide();	    	    	
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });   
    	}

		
	  
    </script>	
    
    <style type="text/css">
    
.carousel-control.left, .carousel-control.right {
	background-image:none;
}

.img-responsive{
	width:100%;
	height:auto;
}

@media (min-width: 992px ) {
	.carousel-inner .active.left {
		left: -25%;
	}
	.carousel-inner .next {
		left:  25%;
	}
	.carousel-inner .prev {
		left: -25%;
	}
}

@media (min-width: 768px) and (max-width: 991px ) {
	.carousel-inner .active.left {
		left: -33.3%;
	}
	.carousel-inner .next {
		left:  33.3%;
	}
	.carousel-inner .prev {
		left: -33.3%;
	}
	.active > div:first-child {
		display:block;
	}
	.active > div:first-child + div {
		display:block;
	}
	.active > div:last-child {
		display:none;
	}
}

@media (max-width: 767px) {
	.carousel-inner .active.left {
		left: -100%;
	}
	.carousel-inner .next {
		left:  100%;
	}
	.carousel-inner .prev {
		left: -100%;
	}
	.active > div {
		display:none;
	}
	.active > div:first-child {
		display:block;
	}
}
    
    
    body{padding-top:20px;}
.carousel {
    margin-bottom: 0;
    padding: 0 40px 30px 40px;
}
/* The controlsy */
.carousel-control {
	left: -12px;
    height: 40px;
	width: 40px;
    background: none repeat scroll 0 0 #222222;
    border: 4px solid #FFFFFF;
    border-radius: 23px 23px 23px 23px;
    margin-top: 90px;
}
.carousel-control.right {
	right: -12px;
}
/* The indicators */
.carousel-indicators {
	right: 50%;
	top: auto;
	bottom: -10px;
	margin-right: -19px;
}
/* The colour of the indicators */
.carousel-indicators li {
	border-color: green;
}
.carousel-indicators .active {
border-color: black;
}
	</style>
   
<html> 

    <!-- START @HEAD -->
    <head>
        <c:import url="./layout/head-layout.jsp"></c:import>
    </head>
    <!--/ END HEAD -->

   
    <body class="page-session page-sound page-header-fixed page-sidebar-fixed demo-dashboard-session">

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->
            	<c:import url="./layout/header.jsp"></c:import>
            <!--/ END HEADER -->
         
         	<!-- START SIDEBAR LEFT -->
            	<c:import url="./layout/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">

                <!-- Start body content -->
                <div class="body-content animated fadeIn">  

                    <div class="row">
                        <div class="col-md-10">
							
                            <!-- START estimativas gerais -->                            	
                            	<c:import url="./telaInicial/estimativasGerais.jsp"></c:import>
                            <!-- END estimativas gerais -->                            
                           	
                           	<div class="row">
                        		<div class="col-md-15">
                           		<!-- INICIO TIMELINE -->
                           	
                                <div class="profile-body">
                                    <div class="timeline">                                
                                        
                                        <div id="idTimeLine" >
                                        	
                                        </div>                                     
                                        
                                        <div id="loadMore-container" class="cbp-l-loadMore-button">
				                            <a href="#a" onClick="carregarTimeline()" class="cbp-l-loadMore-link">
				                                <span class="cbp-l-loadMore-defaultText">Carregar Mais</span>
				                                <span class="cbp-l-loadMore-loadingText">Carregando...</span>
				                                <span class="cbp-l-loadMore-noMoreLoading">NO MORE WORKS</span>
				                            </a>
				                        </div>
                                    </div>
                                </div><!-- /.profile-body -->                           		
                           	<!-- FIM TIMELINE -->
                        </div>
                        
                        <!--  START SIDEBAR RIGHT -->
                        <!-- 
                        <div class="col-md-3">
                            <c:import url="./layout/sidebar-right.jsp"></c:import>
                        </div>
                         -->                        
                        <!--  END SIDEBAR RIGHT -->
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->

            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->

          
        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->
        
        <!-- Start optional size modal element - confirmacao cancelamento de contato -->
            <div id="idModalConfirmacaoCancelContato" class="modal fade bs-example-modal-lg-confirmacao-cancel-contato" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdContato" readonly="readonly" name="modIdContato">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.cancel.contato"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.cancel.contato"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="cancelarContato();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoConfirmCancelContatoErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao cancelamento de contato -->
         
          <!-- Start optional size modal element - confirmacao cancelamento de convite -->
            <div id="idModalConfirmacaoCancelConvite" class="modal fade bs-example-modal-lg-confirmacao-cancel-convite" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdConvite" readonly="readonly" name="modIdConvite">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.cancel.convite"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.cancel.convite"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="cancelarConvite();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoConfirmCancelContatoErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao cancelamento de convite -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

       <c:import url="./layout/head-bootstrap.jsp"></c:import>

    </body>
    <!--/ END BODY -->

</html>
