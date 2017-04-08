<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url var="urlContato" value="/contato"/>
<spring:url var="urlUsuario" value="/usuario"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<script type="text/javascript">
	$(document).ready(function() {
		$('#opcaoOrdenacao1').change(function () {				
			$("#contatoOrdForm").submit();      
		 });					
	});
	
	function desmarcarCheck(id) {
	    $.post("${urlContato}/desmarcarCheck", {'idUsuarioHost' : id}, function() {
	      	$("#idCheckImovelDiv_"+id).hide();
	    	$("#idCheckImovel_"+id).hide();
	    	
	    });
	  }
	
	function aceitarConvite(id) {    	
		var parametro1 = id;
		$.ajax({  
		    type: 'GET',	
	         url: '${urlContato}/responderConvite/O/' + parametro1,
	         dataType: 'json',
	         success: function(data){	        	 
	        	 if ( data == 'ok') {
	        		 $("#idConviteAceitoUsuario_"+id).hide();  
	        		 $("#idConviteRejeitarUsuario_"+id).hide();	        		 
	     	    	 $("#idMsgConviteAceitoUsuario_"+id).show();
	        	 }
	        	 else  {
		        	 $('#msgRetornoPropostaErro').html(data);
		         }	
	         },	      
	     });   
	}
	
	
	function rejeitarConvite(id) {    	
		var parametro1 = id;
		$.ajax({  
		    type: 'GET',	
	         url: '${urlContato}/responderConvite/R/' + parametro1,
	         dataType: 'json',
	         success: function(data){	        	 
	        	 if ( data == 'ok') {
	        		 $("#idConviteAceitoUsuario_"+id).hide();  
	        		 $("#idConviteRejeitarUsuario"+id).hide();            	
	     	    	 $("#idMsgConviteAceitoUsuario_"+id).show();
	        	 }
	        	 else  {
		        	 $('#msgRetornoPropostaErro').html(data);
		         }	
	         },	      
	     });   
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.convites"/>  </h2>                                                      				
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                 <div class="row">
                 		
                    	<% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
		    		<c:import url="../../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                       
                       <c:choose>                       
                       		<c:when test="${not empty listaConvitesRecebidos }">
                       			 <div class="col-md-9">
			                            <!-- Start inline form -->
			                            <div class="panel rounded shadow">     
			                                <div class="panel-body no-padding">
			                                  <div class="panel-heading">
			                                    <div class="pull-left">
			                                        <h3 class="panel-title"><spring:message code="lbl.filtro.geral"/> <code></code></h3>
			                                    </div>
			                                    <div class="pull-right">
			                                    	<spring:message code="lbl.hint.tipo.ordenacao" var="hintOrdenar"/>
			                                        <form:form method="POST" id="contatoOrdForm" modelAttribute="contatoForm" action="${urlContato}/ordenarConvites" >
			                                        	<form:select id="opcaoOrdenacao1" path="opcaoOrdenacao" class="chosen-select" tabindex="-1" style="display: none;" title="${hintOrdenar}">                                
										                        <form:option value="" disabled="true"><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                        <form:option value="maisRecente"><spring:message code="lbl.opcao.contato.mais.recente"/></form:option>
																<form:option value="menosRecente"><spring:message code="lbl.opcao.contato.menos.recente"/></form:option>
										                  </form:select>  
			                                        </form:form> 
			                                    </div>
			                                    <div class="clearfix"></div>
			                                </div><!-- /.panel-heading -->
			
			                                </div><!-- /.panel-body -->
			                            </div><!-- /.panel -->
			                            <!--/ End inline form -->
			                        </div>	               
			                    
			                   	<div class="col-lg-9 col-md-9 col-sm-8">
				                   	   
				                   	    <div class="media-list list-search">
			                                    <c:forEach var="usuarioContato" items="${listaConvitesRecebidos}" varStatus="item">
			                                    
			                                    	<c:choose>
			                                    		<c:when test="${usuarioContato.usuarioConvidado.id != usuario.id }">
		                                    				<div class="media rounded shadow no-overflow">
					                                            <div class="media-left"> 
					                                                <a href="${urlUsuario}/detalhesUsuario/${usuarioContato.usuarioConvidado.id}" >                                                                                                     
					                                                    <img src="data:image/jpeg;base64,${usuarioContato.usuarioConvidado.imagemArquivo}" class="media-object img-circle" style="width: 260px; height: 195px;" />
					                                                </a>
					                                            </div>
					                                            <div class="media-body">
					                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${usuarioContato.usuarioConvidado.perfilFmt}</span>
					                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlUsuario}/detalhesUsuario/${usuarioContato.usuarioConvidado.id}" style="color : #9d2428;">${usuarioContato.usuarioConvidado.nome}</a></h4>
					                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${usuarioContato.usuarioConvidado.cidade} - ${usuarioContato.usuarioConvidado.uf}   </h1>
					                                                
					                                                <div class="col-md-5" > 
					                                                	<div class="media-body" >
								                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.usuario" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${usuarioContato.usuarioConvidado.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em> </br>			                                            		                                            	                                            
								                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.convite" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${usuarioContato.dataConvite}' pattern='dd/MM/yyyy'/></font></span></em> </br>
								                                        </div>
								                                                                                       
					                                                    <br/> <br/>                                                      
					                                                     
					                                                    <spring:message code="lbl.acao.aceita.convite" var="mensagemAceitaConvite"/>
										                                <a href="#a" onClick="aceitarConvite(${usuarioContato.usuarioConvidado.id})" id="idConviteAceitoUsuario_${usuarioContato.usuarioConvidado.id}" style="font-size:x-large;"  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemAceitaConvite}" ><i class="fa fa-check"></i></a>
						                                                                                                    
						                                                <spring:message code="lbl.acao.recusa.convite" var="mensagemRecusarConvite"/>	
						                                                <a href="#a" onClick="rejeitarConvite(${usuarioContato.usuarioConvidado.id})" id="idConviteRejeitarUsuario_${usuarioContato.usuarioConvidado.id}" style="font-size:x-large;"  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemRecusarConvite}" ><i class="fa fa-times"></i></a>                                                   	
					                                              
														          		<div id="idMsgConviteAceitoUsuario_${usuarioContato.usuarioConvidado.id}"  style="display: none;">
													                           <div class="alert alert-success">
									                                                <strong><spring:message code="msg.convite.aceito.sucesso"/></strong> 
									                                            </div>											                          		                          
														                </div><!-- /.panel -->                      
														                
													               		 <div id="idMsgConviteAceitoUsuario_${usuarioContato.usuarioConvidado.id}"  style="display: none;">
													               		   <div class="alert alert-danger">
								                                                <strong><spring:message code="msg.convite.recusado.sucesso"/></strong> 
								                                            </div>											                          			                                                    
													                      </div><!-- /.panel -->  
					                                                </div>			                                                
					                                                <div class="col-md-7">
					                                                    <table class="table table-condensed">
					                                                        <tbody style="font-size: 13px;">
					                                                        	<tr>
					                                                                <td class="text-left">Total Imóveis</td>
					                                                                <td class="text-right">110</td>
					                                                            </tr>                                                      
					                                                        </tbody>
					                                                    </table>
					                                                </div>
					                                            </div>
					                                        </div>
			                                    		</c:when>
			                                    		
			                                    		<c:when test="${usuarioContato.usuarioHost.id != usuario.id }">
															<div class="media rounded shadow no-overflow">
					                                            <div class="media-left">
					                                                <a href="${urlUsuario}/detalhesUsuario/${usuarioContato.usuarioHost.id}" >                                                                                                     
					                                                    <img src="data:image/jpeg;base64,${usuarioContato.usuarioHost.imagemArquivo}" class="img-responsive" style="width: 260px; height: 195px; alt="admin"/>
					                                                </a>
					                                            </div>
					                                            <div class="media-body">
					                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${usuarioContato.usuarioHost.perfilFmt}</span>
					                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlUsuario}/detalhesUsuario/${usuarioContato.usuarioHost.id}" style="color : #9d2428;">${usuarioContato.usuarioHost.nome}</a></h4>
					                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${usuarioContato.usuarioHost.cidade} - ${usuarioContato.usuarioHost.uf}   </h1>
					                                                
					                                                <div class="col-md-5" > 
					                                                	<div class="media-body" >
								                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.usuario" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${usuarioContato.usuarioHost.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em> </br>			                                            		                                            	                                            
								                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.convite" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${usuarioContato.dataConvite}' pattern='dd/MM/yyyy'/></font></span></em> </br>
								                                        </div>
								                                                                                       
					                                                    <br/> <br/>                                                      
					                                                     
					                                                    <spring:message code="lbl.acao.aceita.convite" var="mensagemAceitaConvite"/>
										                                <a href="#a" onClick="aceitarConvite(${usuarioContato.usuarioHost.id})" id="idConviteAceitoUsuario_${usuarioContato.usuarioHost.id}" style="font-size:x-large;"  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemAceitaConvite}" ><i class="fa fa-check"></i></a>
						                                                                                                    
						                                                <spring:message code="lbl.acao.recusa.convite" var="mensagemRecusarConvite"/>	
						                                                <a href="#a" onClick="rejeitarConvite(${usuarioContato.usuarioHost.id})" id="idConviteRejeitarUsuario_${usuarioContato.usuarioHost.id}" style="font-size:x-large;"  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemRecusarConvite}" ><i class="fa fa-times"></i></a>                                                   	
					                                              
														          		<div id="idMsgConviteAceitoUsuario_${usuarioContato.usuarioHost.id}"  style="display: none;">
													                           <div class="alert alert-success">
									                                                <strong><spring:message code="msg.convite.aceito.sucesso"/></strong> 
									                                            </div>											                          		                          
														                </div><!-- /.panel -->                      
														                
													               		 <div id="idMsgConviteAceitoUsuario_${usuarioContato.usuarioHost.id}"  style="display: none;">
													               		   <div class="alert alert-danger">
								                                                <strong><spring:message code="msg.convite.recusado.sucesso"/></strong> 
								                                            </div>											                          			                                                    
													                      </div><!-- /.panel -->  
					                                                </div>			                                                
					                                                <div class="col-md-7">
					                                                    <table class="table table-condensed">
					                                                        <tbody style="font-size: 13px;">
					                                                        	<tr>
					                                                                <td class="text-left">Total Imóveis</td>
					                                                                <td class="text-right">110</td>
					                                                            </tr>                                                      
					                                                        </tbody>
					                                                    </table>
					                                                </div>
					                                            </div>
					                                        </div>	
			                                    		</c:when>
			                                    	
			                                    	</c:choose>
			                                        
			                                    </c:forEach>
			                                </div>	                   	   
				                    </div>
                       		</c:when>
                       
                       		<c:when test="${empty listaConvitesRecebidos }">
                       			<div class="col-md-9">
		                       		<div class="panel">	                                
			                                <div class="panel-body">
			                                	<spring:message code="msg.sem.convite.recebido"/>
			                                </div><!-- /.panel-body -->
			                        </div><!-- /.panel -->     
			                    </div>
                       		</c:when>                       		
                       </c:choose>
    
                       <div class="col-md-3">
                            <c:import url="../layout/sidebar-right.jsp"></c:import>
                        </div>    
                        
                   </div><!-- /.row -->  

                </div><!-- /.body-content -->  
         
            </section><!-- /#page-content -->          
		  
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->

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
