<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url var="urlContato" value="/contato"/>
<spring:url var="urlUsuario" value="/usuario"/>

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
		                       <c:when test="${empty listaConvitesRecebidos }"> 
		                       		<div class="col-md-9">
			                       		<div class="panel">	                                
				                                <div class="panel-body">
				                                	<spring:message code="msg.sem.convite.recebido"/>
				                                </div><!-- /.panel-body -->
				                        </div><!-- /.panel -->     
				                    </div>
		                       </c:when> 
		                        
		                     <c:when test="${not empty listaConvitesRecebidos }"> 
		                        <div class="col-md-9">
		                            <!-- Start inline form -->
		                            <div class="panel rounded shadow">     
		                                <div class="panel-body no-padding">		                                  
		
		                                </div><!-- /.panel-body -->
		                            </div><!-- /.panel -->
		                            <!--/ End inline form -->
		                        </div>	               
		                    
			                   	<div class="col-lg-9 col-md-9 col-sm-8">
			                   	   
			                   	    <div class="media-list list-search">
		                                    <c:forEach var="usuarioContato" items="${listaConvitesRecebidos}" varStatus="item">
		                                        <div class="media rounded shadow no-overflow">
		                                            <div class="media-left">
		                                                <a href="${urlUsuario}/detalhesUsuario/${usuarioContato.id}" >                                                                                                     
		                                                    <img src="data:image/jpeg;base64,${usuarioContato.imagemArquivo}" class="img-responsive" style="width: 260px; height: 195px; alt="admin"/>
		                                                </a>
		                                            </div>
		                                            <div class="media-body">
		                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${usuarioContato.perfilFmt}</span>
		                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlUsuario}/detalhesUsuario/${usuarioContato.id}" style="color : #9d2428;">${usuarioContato.nome}</a></h4>
		                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${usuarioContato.cidade} - ${usuarioContato.uf}   </h1>
		                                                
		                                                <div class="col-md-5" >     
		                                                
		                                                	<div class="media-body" >
					                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.usuario" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${usuarioContato.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em> </br>			                                            		                                            	                                            
					                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.convite" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${usuarioContato.dataConvite}' pattern='dd/MM/yyyy'/></font></span></em> </br>
					                                        </div>
					                                                                                       
		                                                    <br/> <br/> 
		
															<c:choose>
																<c:when test="${((msgConviteAceito == null) && (msgConviteRecusado == null)) }">
			                                                    	<spring:message code="lbl.acao.aceita.convite" var="mensagemAceitaConvite"/>
								                                    <a href="${urlContato}/responderConviteSelecionado/${usuarioContato.id}/O" style="font-size:x-large;"  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemAceitaConvite}" ><i class="fa fa-check"></i></a>
				                                                                                                    
				                                                  	<spring:message code="lbl.acao.recusa.convite" var="mensagemRecusarConvite"/>	
				                                                  	<a href="${urlContato}/responderConviteSelecionado/${usuarioContato.id}/R" style="font-size:x-large;"  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemRecusarConvite}" ><i class="fa fa-times"></i></a>
			                                                    	
			                                                    </c:when>
			                                                    
			                                                    <c:when test="${msgConviteAceito != null }">
												               		   <div class="alert alert-success">
							                                                <strong><spring:message code="msg.convite.aceito.sucesso"/></strong> 
							                                            </div>	                     
												                </c:when>	
												                
												                <c:when test="${msgConviteRecusado != null }">
													               		 <div class="alert alert-danger">
									                                                <strong><spring:message code="msg.convite.recusado.sucesso"/></strong> 
									                                     </div>								                    
													             </c:when>
															</c:choose>	
		                                                </div>
		                                                
		                                                <div class="col-md-7">
		                                                    <table class="table table-condensed">
		                                                        <tbody style="font-size: 13px;">
		                                                        	<tr>
		                                                                <td class="text-left">Total Imóveis</td>
		                                                                <td class="text-right">10</td>
		                                                            </tr>                                                      
		                                                        </tbody>
		                                                    </table>
		                                                </div>
		                                            </div>
		                                        </div>
		                                    </c:forEach>
		                                </div>	                   	   
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
