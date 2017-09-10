<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url var="urlNotificacao" value="/notificacao"/>
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/imovel" var="urlImovel"/>
 
<script>
	$(document).ready(function() {
		$('#opcaoOrdenacao1').change(function () {				
			$("#notificacaoOrdForm").submit();      
		 });	
		
		$('#opcaoFiltro1').change(function () {				
			$("#notificacaoFiltroForm").submit();      
		 });			
	});
	
	function desmarcarCheck(id) {
		$.post("${urlNotificacao}/desmarcarCheck", {'idNotificacao' : id}, function() {
		  // selecionando o elemento html atravÃ©s da 
		  // ID e alterando o HTML dele    
			$("#idCheckNotDiv_"+id).hide();
			$("#idCheckNot_"+id).hide();
			$("#idCheckNotNovo_"+id).hide();	
			$("#idCheckNotNovoLabel_"+id).hide();			
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.notificacoes"/> </h2>                                                      
					<!-- Start header modal Ajuda - funcionalidade -->
						<c:import url="../ajuda/headerMenuModal.jsp"></c:import>																				
					<!-- End header  modal Ajuda - funcionalidade -->
                </div><!-- /.header-content -->
          <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div id="blog-list">
                     <div class="row">
	                <% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
							<c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                    <% } %>
                        <div class="col-md-12">
                            <!-- Start inline form -->
                            <div class="panel rounded shadow">     
                                <div class="panel-body no-padding">
                                  <div class="panel-heading">
                                    <div class="pull-left">
                                        
                                    </div>
                                    <div class="pull-right">
                                        <a href="${urlNotificacao}/listarMinhasNotificacoes" class="btn btn-primary btn-block">Todas Notificações</a>
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->

                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End inline form -->

                        </div>	               
                    </div><!-- /.row -->                    
                    
                    <div class="row">
                        <div class="col-md-12">
                        	<c:choose>
                        		<c:when test="${ not empty listaNotificacoes }">
                        			<c:forEach var="notificacao" items="${listaNotificacoes}">
									
										<c:if test="${notificacao.statusLeitura == 'N' }">
											<input id="idCheckNot_${notificacao.id}"  checked="checked" type="checkbox" onclick="desmarcarCheck(${notificacao.id});">																		
										</c:if>
										
										<c:choose>
											<c:when test="${notificacao.tipoNotificacao == 'C' }">
												<div class="blog-item rounded shadow">
		 	                    					<div class="row">
		 		                    					 <div class="col-md-2 col-sm-3 col-xs-6"> 						                            
		 						                              <p class="inner-all no-margin">
		 						                              	   <a href="${urlUsuario}/detalhesUsuario/${notificacao.usuarioConvite.id}" >
		 						                              	   		<img src="data:image/jpeg;base64,${notificacao.usuarioConvite.imagemArquivo}" style="width: 120px; height: 100px; "  class="media-object img-circle img-bordered-success" alt="..."/>
		 						                              	   </a>	 
		 						                              </p>
		 						                     	</div>
		 				                               
		 				                                <div class="blog-details">
		 				                                	<c:if test="${notificacao.statusLeitura == 'N' }">
																<div id="idCheckNotNovo_${notificacao.id}" class="ribbon-wrapper">
			 				                                        <div id="idCheckNotNovoLabel_${notificacao.id}" class="ribbon ribbon-danger"><spring:message code="lbl.nofiticacao.novo"/></div>
			 				                                    </div>																		
															</c:if>
		 				                                
		 				                                    
		 				                                    <h4 class="blog-title"><a href=""><spring:message code="lbl.notificacao.title.convite"/></a></h4>
		 				                                    <ul class="blog-meta">			                                        
		 				                                        <li>&nbsp; <spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></li>
		 				                                          
		 				                                    </ul>
		 				                                    <div class="blog-summary">
		 				                                      <p>  
							                                      <i class="fa fa-pencil-square-o">&nbsp; </i><spring:message code="lbl.desc.notificacao"/> :
						                                      		<a href="${urlUsuario}/detalhesUsuario/${notificacao.usuarioConvite.id}"> 
						                                      			${notificacao.usuarioConvite.nome} <spring:message code="lbl.desc.aceitou.convite"/> 
						                                    		</a> 
		 				                                     </p>
		 				                                    </div>
		 				                                </div>
		 				                             
		 			                               </div> 
					                            </div><!-- /.blog-item -->
											</c:when>
											
											<c:when test="${notificacao.tipoNotificacao == 'I' }">
												<div class="blog-item rounded shadow">
			                    					<div class="row">			                    					
					                                	  <div class="col-md-2 col-sm-3 col-xs-6">						                            
							                                    <p class="inner-all no-margin">							                                    
						                                    		<a href="${urlUsuario}/detalhesUsuario/${notificacao.usuarioConvite.id}">
						                                    			<img src="data:image/jpeg;base64,${notificacao.usuarioConvite.imagemArquivo}" style="width: 120px; height: 100px; "  class="media-object img-circle img-bordered-success" alt="..."/>
						                                    		</a>						                                        
							                                    </p>									                           
									                     	</div>			                                
					                                
							                                <div class="blog-details">
							                                	<c:if test="${notificacao.statusLeitura == 'N' }">
																	<div id="idCheckNotNovo_${notificacao.id}" class="ribbon-wrapper">
				 				                                        <div id="idCheckNotNovoLabel_${notificacao.id}" class="ribbon ribbon-danger"><spring:message code="lbl.nofiticacao.novo"/></div>
				 				                                    </div>																		
																</c:if>
							                                    	<c:choose>
														   				 <c:when test="${notificacao.acao == 'intermediacao'}">	
														   				 	<h4 class="blog-title"><a href=""><spring:message code="lbl.notificacao.title.intermediacao"/></a></h4>
														   				 </c:when>
														   				 
														   				 <c:when test="${notificacao.acao == 'parceria'}">	
														   				 	<h4 class="blog-title"><a href=""><spring:message code="lbl.notificacao.title.parceria"/></a></h4>
														   				 </c:when>	
														   				  <c:when test="${notificacao.acao == 'F'}">	
														   				 	<h4 class="blog-title"><a href=""><spring:message code="lbl.notificacao.title.fechar.negocio"/></a></h4>
														   				 </c:when>
														   				 <c:when test="${notificacao.acao == 'M'}">	
														   				 	<h4 class="blog-title"><a href=""><spring:message code="lbl.notificacao.title.marcar.visita"/></a></h4>
														   				 </c:when>	
														   		    </c:choose>  
							                                    <ul class="blog-meta">			                                        
							                                        <li><i class="fa fa-calendar"></i>&nbsp;<spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></li>
							                                          
							                                    </ul>
							                                    <div class="blog-summary">
							                                        <p>         
						                                       			<i class="fa fa-pencil-square-o">&nbsp; </i><spring:message code="lbl.desc.notificacao"/> :
						                                       				<a href="${urlImovel}/detalhesImovel/${notificacao.imovel.id}"> 
						                                       					<c:choose>
																		   				 <c:when test="${notificacao.acao == 'I'}">	
																		   				 	${notificacao.imovel.usuario.nome} <spring:message code="lbl.desc.aceitou.intermediacao"/> : ${notificacao.imovel.titulo}
																		   				 </c:when>
																		   				 
																		   				 <c:when test="${notificacao.acao == 'P'}">	
																		   				 	${notificacao.imovel.usuario.nome} <spring:message code="lbl.desc.aceitou.parceria"/> : ${notificacao.imovel.titulo}
																		   				 </c:when>
																		   				  <c:when test="${notificacao.acao == 'F'}">	
																		   				 	${notificacao.usuarioConvite.nome} <spring:message code="lbl.desc.sol.fechar.negocio"/> : ${notificacao.imovel.titulo}
																		   				 </c:when>
																		   				 
																		   				 <c:when test="${notificacao.acao == 'M'}">	
																		   				 	${notificacao.usuarioConvite.nome} <spring:message code="lbl.desc.sol.marcar.visita"/> : ${notificacao.imovel.titulo}
																		   				 </c:when>
																		   		    </c:choose> 
						                                     				</a> 					                                     		
							                                        </p>			                                        
							                                    </div>
							                                </div>
					                               </div> 
					                            </div><!-- /.blog-item -->
											</c:when>
											
											<c:when test="${notificacao.tipoNotificacao == 'S' }">
												<div class="blog-item rounded shadow">
			                    					<div class="row">
					                                 <div class="col-md-2 col-sm-3 col-xs-6">
								                            <div class="panel panel-info rounded shadow">
								                                <div class="panel-heading text-center">
								                                    <p class="inner-all no-margin">
								                                        <i class="fa fa-building-o fa-5x"></i>
								                                    </p>
								                                </div><!-- /.panel-heading -->						                                
								                            </div><!-- /.panel -->
								                     </div>
		                        
					                                <div class="blog-details">
					                                    <c:if test="${notificacao.statusLeitura == 'N' }">
															<div id="idCheckNotNovo_${notificacao.id}" class="ribbon-wrapper">
		 				                                        <div id="idCheckNotNovoLabel_${notificacao.id}" class="ribbon ribbon-danger"><spring:message code="lbl.nofiticacao.novo"/></div>
		 				                                    </div>																		
														</c:if>
					                                    <h4 class="blog-title"><a href=""><spring:message code="lbl.notificacao.title.servico"/></a></h4>
					                                    <ul class="blog-meta">			                                        
					                                        <li><i class="fa fa-calendar"></i>&nbsp; <spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></li>
					                                          
					                                    </ul>
					                                    <div class="blog-summary">
					                                        <p><i class="fa fa-pencil-square-o">&nbsp; </i><spring:message code="lbl.desc.notificacao"/> : <spring:message code="lbl.desc.aceitou.servico"/> </p>					                                        
					                                    </div>
					                                </div>
					                             </div>   
					                            </div><!-- /.blog-item -->
											</c:when>
											
											<c:when test="${notificacao.tipoNotificacao == 'P' }">
												<div class="blog-item rounded shadow">
			                    				  	<div class="row">
			                    					<div class="col-md-2 col-sm-3 col-xs-6">
								                            <div class="panel panel-info rounded shadow">
								                                <div class="panel-heading text-center">
								                                    <p class="inner-all no-margin">
								                                        <i class="fa fa-university fa-5x"></i>
								                                    </p>
								                                </div><!-- /.panel-heading -->								                                
								                            </div><!-- /.panel -->
								                     </div>						                     
					                                
					                                <div class="blog-details">
					                                    <c:if test="${notificacao.statusLeitura == 'N' }">
															<div id="idCheckNotNovo_${notificacao.id}" class="ribbon-wrapper">
		 				                                        <div id="idCheckNotNovoLabel_${notificacao.id}" class="ribbon ribbon-danger"><spring:message code="lbl.nofiticacao.novo"/></div>
		 				                                    </div>																		
														</c:if>
					                                    <h4 class="blog-title"><a href=""><spring:message code="lbl.notificacao.title.plano"/></a></h4>
					                                    <ul class="blog-meta">			                                        
					                                        <li><i class="fa fa-calendar"></i>&nbsp; <spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></li>
					                                          
					                                    </ul>
					                                    <div class="blog-summary">
					                                        <p><i class="fa fa-pencil-square-o">&nbsp; </i><spring:message code="lbl.desc.notificacao"/> : <spring:message code="lbl.desc.aceitou.plano"/></p>
					                                        
					                                    </div>
					                                </div>
					                               </div> 
					                            </div><!-- /.blog-item -->
											</c:when>
											
											<c:when test="${notificacao.tipoNotificacao == 'U' }">
												<div class="blog-item rounded shadow">
		 	                    					<div class="row">
		 		                    					 <div class="col-md-2 col-sm-3 col-xs-6">
		 						                            <div class="panel panel-info rounded shadow">
		 						                                <div class="panel-heading text-center">
		 						                                    <p class="inner-all no-margin">
		 						                                        <i class="fa fa-suitcase fa-5x"></i>
		 						                                    </p>
		 						                                </div><!-- /.panel-heading -->
		 						                                
		 						                            </div><!-- /.panel -->
		 						                     	</div>
		 				                               
		 				                                <div class="blog-details">
		 				                                    <c:if test="${notificacao.statusLeitura == 'N' }">
																<div id="idCheckNotNovo_${notificacao.id}" class="ribbon-wrapper">
			 				                                        <div id="idCheckNotNovoLabel_${notificacao.id}" class="ribbon ribbon-danger"><spring:message code="lbl.nofiticacao.novo"/></div>
			 				                                    </div>																		
															</c:if>
		 				                                    <h4 class="blog-title"><a href=""><spring:message code="lbl.notificacao.title.usuario.cadastro"/></a></h4>
		 				                                    <ul class="blog-meta">			                                        
		 				                                        <li><i class="fa fa-calendar"></i>&nbsp; <spring:message code="lbl.data.notificacao"/>:  <fmt:formatDate value='${notificacao.dataNotificacao}' pattern='dd/MM/yyyy'/></li>
		 				                                          
		 				                                    </ul>
		 				                                    <div class="blog-summary">
		 				                                      <p>  
							                                      <i class="fa fa-pencil-square-o">&nbsp; </i><spring:message code="lbl.desc.notificacao"/> : <spring:message code="lbl.desc.seja.bemvindo"/>
		 				                                     </p>
		 				                                    </div>
		 				                                </div>
		 				                             
		 			                               </div> 
					                            </div><!-- /.blog-item -->
											</c:when>
										</c:choose>	
		                    		</c:forEach>
                        		</c:when>
                        		
                        		<c:when test="${empty listaNotificacoes }">
                        			<div class="panel-heading">
                               			 <div class="callout callout-warning">
		                                    <strong><spring:message code="lbl.nenhuma.nofiticacao"/></strong>			                                    
		                                </div>
		                            </div> 
                        		</c:when>
                        		
                        	</c:choose>
	                    		
                         </div> <!-- /.col -->
                      </div> <!-- /.row -->                      
                  </div> <!-- /.blog list -->       
                    
                   <!--/ End inline form -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
         
            </section><!-- /#page-content -->
        </section><!-- /#wrapper -->
        
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
    </body>
    <!--/ END BODY -->