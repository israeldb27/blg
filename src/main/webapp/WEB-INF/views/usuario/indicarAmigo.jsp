[]<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/mensagem" var="urlMensagem"/>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

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
  
function mostrarModal(id){
	
	if (id == 0){
		$('#msgModal').html("Escolha uma foto de exibição para a sua conta");
	}
	else if ( id == 1){
		$('#msgModal').html("Repasse suas informações básicas");
	}
	else if ( id == 2){
		$('#msgModal').html("Informe o seu password");
	}
	else if ( id == 3){
		$('#msgModal').html("Repasse informações de sua localização");
	}
	else if ( id == 4){
		$('#msgModal').html("Repasse suas informações de contato");
	}
	else if ( id == 5){
		$('#msgModal').html("Selecione suas permissões sobre sua conta");
	}
	
	$("#idModalItem").modal("show");
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

            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.aba.indicar.amigo"/> </h2>                                                                        
                </div><!-- /.header-content -->
                
                <c:if test="${msgSucesso != null }">
                	 <div class="alert alert-success">
                           <strong>${msgSucesso}</strong> 
                      </div>    
               </c:if>   
               <c:if test="${msgErro != null }">
               		 <div class="alert alert-danger">
                             <strong>${msgErro}</strong> 
                      </div>         
               </c:if>	
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                   <form:form id="usuarioForm" modelAttribute="usuarioForm" action="${urlUsuario}/indicarAmigo" class="form-horizontal mt-10">
                        <div class="row"> 	
                  		   <!--/ INICIO ABA PASSWORD -->
	                        <div class="col-md-12">
	                            <!-- Start horizontal form -->
	                            <div class="panel rounded shadow">
	                                <div class="panel-heading">  
	                                    <div class="pull-left">
	                                        <h3 class="panel-title"><spring:message code="lbl.aba.indicar.amigo.email"/>  <code></code></h3>
	                                    </div>
	                                    <div class="pull-right">
	                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(2);"><i class="fa fa-question" ></i></a>                                        
	                                    </div>
	                                    <div class="clearfix"></div>
	                                </div><!-- /.panel-heading -->
	                                <div class="panel-body no-padding">
	
	                                        <div class="form-body">
	                                        
	                                			<div class="form-group">
	                                                <label for="emailIndicaAmigos" class="col-sm-3 control-label"><spring:message code="lbl.email"/> </label>
	                                                <div class="col-sm-7">    
	                                                	<spring:message code="lbl.hint.usuario.password" var="hintPassword"/>                                                
	                                                    <form:input id="emailIndicaAmigos" path="emailIndicaAmigos" class="form-control" title="${hintPassword}"/>
	                                                    <form:errors id="emailIndicaAmigos" path="emailIndicaAmigos" cssClass="errorEntrada"  />
	                                                </div>
	                                            </div><!-- /.form-group -->	                                                              
	
	                                		</div><!-- /.panel-body -->
	                                		
	                                		<div class="form-footer">  
				                              <div class="col-sm-offset-3">                              	
				                              	<spring:message code="lbl.hint.confirmar.geral" var="hintConfirmaDados"/>
				                                  <button type="submit" class="btn btn-success" title="${hintConfirmaDados}"><spring:message code="lbl.btn.confirmar.dados.geral"/></button>
				                              </div>
				                          </div><!-- /.form-footer -->
				                          
	                            	</div><!-- /.panel -->
	                            <!--/ End horizontal form -->
	                        </div>	                        
	                      <!--/ FIM ABA PASSWORD -->
                    	
                        </div><!-- /.row -->
                   </form:form> 
                            
                </div><!-- /.body-content -->
                <!--/ End body content -->        
         
            </section><!-- /#page-content -->
          
				<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
				<!-- End content  modal Ajuda - funcionalidade -->
				
			
			<!-- Start optional size modal element - item 1 -->
            <div id="idModalItem" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><div id="msgModal"  ></h4>
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

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>