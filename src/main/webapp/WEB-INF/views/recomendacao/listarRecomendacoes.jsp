<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url var="urlRecomendacao" value="/recomendacao"/>
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
	
	function aceitarRecomendacao(id) {    	
		var parametro1 = id;
		$.ajax({  
		    type: 'GET',	
	         url: '${urlRecomendacao}/responderRecomendacao/aceitar/' + parametro1,
	         dataType: 'json',
	         success: function(data){	        	 
	        	 if ( data == 'ok') {
	        		 $("#idAceitaRecomendacao_"+id).hide();  
	        		 $("#idRecusaRecomendacao_"+id).hide();	        		 
	     	    	 $("#idMsgAceitarRecomendacao_"+id).show();
	        	 }
	        	 else  {
		        	 $('#msgRetornoPropostaErro').html(data);
		         }	
	         },	      
	     });   
	}

	function recusarRecomendacao(id) {    	
		var parametro1 = id;
		$.ajax({  
		    type: 'GET',	
	         url: '${urlRecomendacao}/responderRecomendacao/recusar/' + parametro1,
	         dataType: 'json',
	         success: function(data){	        	 
	        	 if ( data == 'ok') {
	        		 $("#idAceitaRecomendacao_"+id).hide();  
	        		 $("#idRecusaRecomendacao_"+id).hide();	        		 
	     	    	 $("#idMsgRecusarRecomendacao_"+id).show();
	        	 }
	        	 else  {
		        	 $('#msgRetornoPropostaErro').html(data);
		         }	
	         },	      
	     });   
	}
	
	function mostrarModal(id){		
		if (id == 0){
			$('#msgModalFunc').html("<spring:message code='msg.modal.lbl.title.link.recomendacao'/>");
			$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.link.recomendacao'/>");
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
                    <h2>
                    	<i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.recomendacao"/>
                    	<div class="pull-right">
	                         <a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" style="font-size: 15px;"></i></a>                                        
	                     </div>	
                    </h2>
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn container limit-form" style="width:1000px;">
                 <div class="row">
                 		
                    	<% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
		    		<c:import url="../../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        
                        <c:choose>
                        	<c:when test="${empty listaRecomendacoes }">
	                        	<div class="col-md-9">
	                        		<div class="callout callout-warning">
	                                    <strong><spring:message code="msg.nenhuma.recomendacao.recebida"/></strong>
	                                </div>            
	                             </div>               		
                        	</c:when>
                        	
                        	<c:when test="${not empty listaRecomendacoes }">
                        		    <div class="col-md-9">
			                            <!-- Start inline form -->
			                            <div class="panel rounded shadow">     
			                                <div class="panel-body no-padding">
			                                  <div class="panel-heading">
			                                    <div class="pull-left">
			                                        <h3 class="panel-title"><spring:message code="lbl.filtro.geral"/> <code></code></h3>
			                                    </div>
			                                    <div class="pull-right">
			                                        <form:form method="POST" id="recomendacaoForm" modelAttribute="recomendacaoForm" action="${urlRecomendacao}/ordenarRecomendacao" >
			                                        	<form:select id="opcaoOrdenacao1" path="opcaoOrdenacao" class="chosen-select" tabindex="-1" style="display: none;">                                
										                        <form:option value="" disabled="true"><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                        <form:option value="maisRecente"><spring:message code="lbl.opcao.recomendacao.mais.recente"/></form:option>
																<form:option value="menosRecente"><spring:message code="lbl.opcao.recomendacao.menos.recente"/></form:option>
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
			                                    <c:forEach var="recomendacao" items="${listaRecomendacoes}" varStatus="item">
			                                        <div class="media rounded shadow no-overflow">
			                                            <div class="media-left"> 
			                                                <a href="${urlUsuario}/detalhesUsuario/${recomendacao.usuario.id}" >                                                                                                     
			                                                    <img src="data:image/jpeg;base64,${recomendacao.usuario.imagemArquivo}" class="img-responsive" style="width: 260px; height: 195px; alt="admin"/>
			                                                </a>
			                                            </div>
			                                            <div class="media-body">
			                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${recomendacao.usuario.perfilFmt}</span>
			                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlUsuario}/detalhesUsuario/${recomendacao.usuario.id}" style="color : #9d2428;">${recomendacao.usuario.nome}</a></h4>
			                                                <span class="media-text"><strong><spring:message code="lbl.descricao.recomendacao" />:  </strong> <font style="font-style: italic;"> ${recomendacao.descricao} </font></span>	
			                                                
			                                                <div class="col-md-5" >     
			                                                
			                                                	<div class="media-body" >
						                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.usuario" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${recomendacao.usuario.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em> </br>			                                            		                                            	                                            
						                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.recomendacao" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${recomendacao.dataRecomendacao}' pattern='dd/MM/yyyy'/></font></span></em> </br>
						                                        </div>
						                                                                                       
			                                                    <br/> <br/>
			                                                     
			                                                   <spring:message code="lbl.acao.aceita.recomendacao" var="mensagemAceitaRecomendacao"/>
								                               <a href="#a" onClick="aceitarRecomendacao(${recomendacao.id})" id="idAceitaRecomendacao_${recomendacao.id}" style="font-size:x-large; "  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemAceitaRecomendacao}" ><i class="fa fa-check"></i></a>
				                                                                                                    
				                                               <spring:message code="lbl.acao.recusar.recomendacao" var="mensagemRecusarRecomendacao"/>	
				                                               <a href="#a" onClick="recusarRecomendacao(${recomendacao.id})" id="idRecusaRecomendacao_${recomendacao.id}" style="font-size:x-large; "  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemRecusarRecomendacao}" ><i class="fa fa-times"></i></a>
				                                                
				                                                
				                                               <div id="idMsgAceitarRecomendacao_${recomendacao.id}"  class="panel panel-success" style="display: none;">
										                            <div class="alert alert-success">
						                                                <strong><spring:message code="msg.recomendacao.aceita"/></strong> 
						                                            </div>		                          
												               </div><!-- /.panel -->                      
												                
											               	   <div id="idMsgRecusarRecomendacao_${recomendacao.id}" class="panel panel-danger" style="display: none;">
											                          <div class="alert alert-danger">
								                                            <strong><spring:message code="msg.recomendacao.recusado"/></strong> 
								                                       </div>		                                                    
											                   </div><!-- /.panel -->   
			                                                    
			                                                </div>
			                                              
			                                            </div>
			                                        </div>
			                                    </c:forEach>
			                                </div>	                   	   
				                    </div>	
                        	</c:when>
                        </c:choose>
                        
                   </div><!-- /.row -->  

                </div><!-- /.body-content -->  
         
            </section><!-- /#page-content -->          
		  
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
			
			 <div id="idModalItem" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
	             <div class="modal-dialog">
			      <div class="modal-content">
			        <div class="modal-header">
			          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			          <h4 class="modal-title"> <div id="msgModalFuncionalidade" > </div>  </h4>
			        </div>
			        <div class="modal-body">  
			       	   <strong> <spring:message code="lbl.descricao.geral"/>:  </strong> <div id="msgModalFunc" > </div>
			        </div>
			        <div class="modal-footer">			          
		                    <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>
			        </div>
			      </div>
			    </div>
		    </div>

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
