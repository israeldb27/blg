<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>
<spring:url value="/imovel" var="urlImovel"/>

<spring:url var="urlAdmin" value="/admin"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   

  
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

    	<script type="text/javascript">
    	$(document).ready(function() {
    		
    		(function ($) {
    			  $('.spinner .btn:first-of-type').on('click', function() {
    			    $('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);
    			  });
    			  $('.spinner .btn:last-of-type').on('click', function() {
    			    $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);
    			  });
    			})(jQuery);    		
    		
    	});	
 	
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.cadastro.imovel.destaque"/> </h2>                                                                        
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
					<div class="row">
						<!-- START  Informações basicas imovel -->
                   			<div class="col-lg-3 col-md-3 col-sm-4" >
		                        <div class="panel rounded shadow">
		                            <div class="panel-body">
		                                <div class="inner-all">
		                                    <ul class="list-unstyled">
		                                        <li class="text-center">
		                                        
		                                            <img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${imovelForm.imagemArquivo}" style="width: 100px; height: 100px; " alt="Tol Lee">
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize">${imovelForm.titulo}</h4>
		                                            <p class="text-muted text-capitalize"> ${imovelForm.tipoImovelFmt}  </p>
		                                             <p class="text-muted text-capitalize">
		                                             	${imovelForm.estado} - ${imovelForm.cidade} - ${imovelForm.bairro}
		                                             </p>		                                             
		                                        </li>
		                                      	
		                                        <li><br/></li>		                                       
		                                    </ul>
		                                </div>
		                            </div>
		                        </div><!-- /.panel -->		
		                    </div>
                   		<!-- END Informações basicas imovel -->	
                   		
                   		<!-- START Painel de Informações -->
                   			<div class="col-lg-9 col-md-9 col-sm-8">

				                    <div class="profile-cover">
				                         <!-- Start vertical tabs with pagination -->
			                            <div id="basic-wizard-vertical">
			                                <div class="panel panel-tab panel-tab-double panel-tab-vertical row no-margin mb-15 rounded shadow">
			                                    <!-- Start tabs heading -->
			                                    <div class="panel-heading no-padding col-lg-3 col-md-3 col-sm-3">
			                                        <ul class="nav nav-tabs">
			                                            <li class="active">
			                                                <a href="#tab4-1" data-toggle="tab">
			                                                    <i class="fa fa-user"></i>
			                                                    <div>
			                                                        <span class="text-strong"><spring:message code="lbl.title.aba.minhas.informacoes"/></span>                                                        
			                                                    </div>
			                                                </a>
			                                            </li>
			                                        </ul>
			                                     </div>
			                                     
			                                     <div class="panel-body col-lg-9 col-md-9 col-sm-9">
			                                        <div class="tab-content">
			                                            <div class="tab-pane fade in active" id="tab4-1">
			                                            
			                                                <div class="form-group">																            
									                                  <strong class="col-sm-3"><spring:message code="lbl.estado"/>: </strong>  RJ1						                                  
				                                             </div><!-- /.form-group -->
				                                             
				                                             <div class="form-group">																            
									                                  <strong class="col-sm-3"><spring:message code="lbl.cidade"/>: </strong>  RJ2						                                  
				                                             </div><!-- /.form-group -->
				                                             
				                                             <div class="form-group">																            
									                                  <strong class="col-sm-3"><spring:message code="lbl.bairro"/>: </strong>  RJ4						                                  
				                                             </div><!-- /.form-group -->
			                                            
			                                            </div>
			                                        </div>
			                                     </div>        
			                                     
			                                </div>
			                            </div>
			                       </div>
			                 </div>			                 
                   						
					</div>	

					<div class="row">
                    	<div class="panel">
                            <div class="panel-heading">
                                <div class="pull-left">
                                    <h3 class="panel-title"><spring:message code="lbl.aba.definicao.configuracao.destaque"/></h3>
                                </div>
                                <div class="pull-right">
                                    <div class="dropdown">                                                                                
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div><!-- /.panel-heading -->
                            <!-- INICIO  configuracao destaque imovel -->
								<form:form id="imovelForm" modelAttribute="imovelForm" action="${urlAdmin}/cadastrarAnuncioImovelDestaque" class="form-horizontal mt-10" >
									<div class="panel-body no-padding">		                 
											<div class="form-body">
											
												<div class="form-group">
			                                    	<label  class="col-sm-4 control-label"><spring:message code="lbl.imovel.destaque.data"/>:</label>
			                                    	<div class="col-sm-7">		                                		
				                                		<form:input id="dataDestaque" path="dataDestaque" class="form-control" value="02/10/2015" data-date-format="dd/mm/yyyy" />	
													</div>											
			                                    </div><!-- /.form-group -->
												
												<div class="form-group">
			                                    	<label  class="col-sm-4 control-label"><spring:message code="lbl.imovel.destaque.opcao"/>:</label>
			                                    	<div class="col-sm-7">		                                		
				                                		<form:select id="opcaoDestaque" path="opcaoDestaque" class="chosen-select" tabindex="-1" style="display: none;">            
															<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                        
															<form:option value="A" ><spring:message code="lbl.imovel.destaque.opcao.anuncio"/></form:option>                        
															<form:option value="D" ><spring:message code="lbl.imovel.destaque.opcao.destaque"/></form:option>                        
														 </form:select>
													</div>											
			                                    </div><!-- /.form-group -->
												
											</div>	
											<div class="form-footer">
												<button type="submit" class="btn btn-primary btn-block"><spring:message code="lbl.btn.confirmar.cadastro.geral"/></button>
										   </div>
									</div><!-- /.panel-body -->  
								</form:form>
								
                   		<!-- FIM configuracao destaque imovel -->                            
                        </div>                        
                    </div>
					
					<!-- START lista cadastros de destaques sobre o imovel-->
					<div class="row">
                        <div class="col-mt-10">                        		
                      
                      		<c:if test="${ empty imovelForm.listaImoveisDestaque }">                                 	
                                 	<div class="panel" align="center">	                                
		                                <div class="panel-body">
		                                    <spring:message code="msg.lista.cadastro.destaque.vazio"/>
		                                </div><!-- /.panel-body -->
		                            </div><!-- /.panel -->
                             </c:if>
                      
                            <!-- Start basic color table -->
                            
                           <c:if test="${ not empty imovelForm.listaImoveisDestaque }"> 
                            <div class="panel">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.lista.imoveis.destaque.cadastrados"/></h3>
                                    </div>
                                    <div class="pull-right">
                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                 
                                <div class="panel-body no-padding">                                 	
                                 
                                    <div class="table-responsive" style="margin-top: -1px;">
                                        <table class="table table-striped table-primary">
                                            <thead>
                                            <tr>                                                
                                                <th class="text-center"><spring:message code="lbl.table.data.cadastro.imovel.destaque"/></th>
                                                <th class="text-center"><spring:message code="lbl.table.data.destaque.imovel.destaque"/></th>                                                
                                                <th class="text-center"><spring:message code="lbl.table.data.status.imovel.destaque"/></th>                                                
												<th class="text-center"></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="imovel" items="${imovelForm.listaImoveisDestaque}" >
	                                            <tr>	                                                
	                                                <td class="text-center"><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></td>
	                                                <td class="text-center"><fmt:formatDate value='${imovel.dataDestaque}' pattern='dd/MM/yyyy'/></td>
													<td class="text-center">${imovel.status}</td>
													<td class="text-center">
														  <a href="${urlAdmin}/excluirImoveldestaque/${imovel.id}" class="btn btn-success btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="<spring:message code='lbl.table.data.excluir.imovel.destaque'/>"><i class="fa fa-eye"></i></a>														   
													</td>	                                            
	                                            </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div><!-- /.table-responsive -->
                                    
                                </div><!-- /.panel-body -->                               
                            </div><!-- /.panel -->
                            <!--/ End basic color table -->
							</c:if>
                        </div><!-- /.col-md-9 -->                                           
                    </div><!-- /.row -->
					
					<!-- END lista cadastros de destaques sobre o imovel-->
					
				</div>
			  <!--/ End body content -->
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

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