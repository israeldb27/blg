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
	      // selecionando o elemento html através da 
	      // ID e alterando o HTML dele
	      	$("#idCheckImovelDiv_"+id).hide();
	    	$("#idCheckImovel_"+id).hide();
	    	
	    });
	  }
	
	function aceitarRecomendacao(id) {    	
		var parametro1 = id;
		$.ajax({  
		    type: 'GET',	
	         url: '${urlRecomendacao}/responderRecomendacaoSelecionada/aceitar/' + parametro1,
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
	         url: '${urlRecomendacao}/responderRecomendacaoSelecionada/recusar/' + parametro1,
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.recomendacao"/>  </h2>                                                      
					
					<!-- Start header modal Ajuda - funcionalidade -->
						<c:import url="../ajuda/headerMenuModal.jsp"></c:import>																				
					<!-- End header  modal Ajuda - funcionalidade -->
					
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                 <div class="row">
                 		
                    	<% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
		    		<c:import url="../../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                       
                       <c:if test="${empty listaRecomendacoes }"> 
                       		<div class="col-md-9">
	                       		<div class="panel">	                                
		                                <div class="panel-body">
		                                	<spring:message code="msg.nenhuma.recomendacao.recebida"/>
		                                </div><!-- /.panel-body -->
		                        </div><!-- /.panel -->     
		                    </div>
                       </c:if> 
                        
                     <c:if test="${not empty listaRecomendacoes }"> 
                        <div class="col-md-9">
                            <!-- Start inline form -->
                            <div class="panel rounded shadow">     
                                <div class="panel-body no-padding">
                                  <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->

                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End inline form -->
                        </div>	               
                    
                   	<div class="col-lg-9 col-md-9 col-sm-8">
	                   	   
	                   	    <div class="media-list list-search">
                                    
                                        <div class="media rounded shadow no-overflow">
                                            <div class="media-left">
                                                <a href="${urlUsuario}/detalhesUsuario/${recomendacaoSelecionada.usuario.id}" >                                                                                                     
                                                    <img src="${context}${recomendacaoSelecionada.imagemUsuario}" class="img-responsive" style="width: 260px; height: 195px; alt="admin"/>
                                                </a>
                                            </div>
                                            <div class="media-body">
                                                <span class="label pull-right" style="background-color: #03A9F4; font-size: 12px">${recomendacaoSelecionada.usuario.perfilFmt}</span>
                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlUsuario}/detalhesUsuario/${recomendacaoSelecionada.usuario.id}" style="color : #03A9F4;">${recomendacaoSelecionada.usuario.nome}</a></h4>
                                                <span class="media-text"><strong><spring:message code="lbl.descricao.recomendacao" />:  </strong> <font style="font-style: italic;"> ${recomendacaoSelecionada.descricao} </font></span>	
                                                
                                                <div class="col-md-5" >     
                                                
                                                	<div class="media-body" >
			                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.cadastro.usuario" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${recomendacaoSelecionada.usuario.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em> </br>			                                            		                                            	                                            
			                                            <em class="text-xs text-muted"> <font style="font-size:13px; font-style: normal;"><spring:message code="lbl.data.recomendacao" />: </font><span class="text-success"><font style="font-size:11px; font-style: normal;"><fmt:formatDate value='${recomendacaoSelecionada.dataRecomendacao}' pattern='dd/MM/yyyy'/></font></span></em> </br>
			                                        </div>
			                                                                                       
                                                    <br/> <br/>  
                                                    
                                                   <c:if test="${recomendacaoSelecionada.isStatusEnviado()}">
                                                   		<spring:message code="lbl.acao.aceita.recomendacao" var="mensagemAceitaRecomendacao"/>
					                               		<a href="#a" onClick="aceitarRecomendacao(${recomendacaoSelecionada.id})" id="idAceitaRecomendacao_${recomendacaoSelecionada.id}" style="font-size:x-large; "  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemAceitaRecomendacao}" ><i class="fa fa-check"></i></a>
	                                                                                                    
	                                               		<spring:message code="lbl.acao.recusar.recomendacao" var="mensagemRecusarRecomendacao"/>	
	                                               		<a href="#a" onClick="recusarRecomendacao(${recomendacaoSelecionada.id})" id="idRecusaRecomendacao_${recomendacaoSelecionada.id}" style="font-size:x-large; "  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemRecusarRecomendacao}" ><i class="fa fa-times"></i></a>
                                                   </c:if> 
                                                   
                                                   <c:if test="${recomendacaoSelecionada.isStatusAceito()}">
                                                  		 <div id="idMsgAceitarRecomendacao"  class="panel panel-success" >
									                          <div class="panel-heading">
									                              <h3 class="panel-title"><spring:message code="msg.recomendacao.aceita.anteriormente"/></h3>
									                          </div><!-- /.panel-heading -->		                          
											               </div><!-- /.panel -->   
                                                   </c:if> 
	                                                
	                                               <div id="idMsgAceitarRecomendacao_${recomendacaoSelecionada.id}"  class="panel panel-success" style="display: none;">
							                          <div class="panel-heading">
							                              <h3 class="panel-title"><spring:message code="msg.recomendacao.aceita"/></h3>
							                          </div><!-- /.panel-heading -->		                          
									               </div><!-- /.panel -->                      
									                
								               	   <div id="idMsgRecusarRecomendacao_${recomendacaoSelecionada.id}" class="panel panel-danger" style="display: none;">
								                          <div class="panel-heading">
								                              <h3 class="panel-title"><spring:message code="msg.recomendacao.recusado"/></h3>
								                          </div><!-- /.panel-heading -->			                                                    
								                   </div><!-- /.panel -->   
                                                    
                                                </div>
                                              
                                            </div>
                                        </div>                                    
                                </div>	                   	   
	                    </div>
	                    
                    </c:if>  
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
