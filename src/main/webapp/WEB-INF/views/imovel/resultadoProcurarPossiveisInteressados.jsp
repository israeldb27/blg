<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/imovel" var="urlImovel"/> 
<spring:url value="/usuario" var="urlUsuario"/>
 
<script type="text/javascript">

$(document).ready(function() {

});

function adicionarPossivelInteressado(idUsuario, idImovel){
	
	$.ajax({  
        url: '${urlImovel}/adicionarPossivelInteressadoDetalhesImovel/' + idUsuario +  '/' + idImovel,
        dataType: 'json',
        success: function(data){	        	 
       	 if ( data == 'ok') {
       		$("#adicionar_"+idUsuario).hide();  
       		$("#adicionado_"+idUsuario).show();  
       	 }
       	 else  {
	        	 $('#msgRetornoAtividadeErro').html(data);
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
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.link.resultado.procurar.interessados"/> </h2>     
                </div><!-- /.header-content -->                
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
        
                   		<!-- START Foto Perfil e Informações Contato -->
                   			<div class="col-lg-3 col-md-3 col-sm-4">

		                        <div class="panel rounded shadow">
		                            <div class="panel-body">
		                                <div class="inner-all">
		                                    <ul class="list-unstyled">
		                                        <li class="text-center">	
													<a href="${urlImovel}/detalhesImovel/${imovelForm.id}" >
														<img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${imovelForm.imagemArquivo}" style="width: 200px; height: 200px; ">
													</a>		                                            
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize"><a href="${urlImovel}/detalhesImovel/${imovelForm.id}" >
																					${imovelForm.titulo}
																				</a>		                                            
													</h4>
		                                            <p class="text-muted text-capitalize">  ${imovelForm.tipoImovelFmt} </p>
		                                        </li>
		                                    
		                                        <li><br/></li>		                                      
		                                    </ul>
		                                </div>
		                            </div>
		                        </div><!-- /.panel -->				                        
		
		                    </div>
                   		<!-- END Foto Perfil e Informações Contato-->
                   		
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
		                                                    <i class="fa fa-home"></i>
		                                                    <div>
		                                                        <span class="text-strong"><spring:message code="lbl.todas.propostas.info.imovel"/></span>                                                        
		                                                    </div>
		                                                </a>
		                                            </li>
		                                                                                       
		                                        </ul>
		                                    </div><!-- /.panel-heading -->
		                                    <!--/ End tabs heading -->
		
		                                    <!-- Start tabs content -->
		                                    <div class="panel-body col-lg-9 col-md-9 col-sm-9">
		                                        <div class="tab-content">
		                                            <div class="tab-pane fade in active" id="tab4-1">
		                                            	 
		                                            	 <div class="form-group">
		                                                		<strong class="col-sm-5"><spring:message code="lbl.valor.imovel"/></strong> <fmt:formatNumber value="${imovelForm.valorImovel}" pattern="#,##0.00;-0"/>
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            		                                            	 
		                                            	 <div class="form-group"> 
		                                                		<strong class="col-sm-5"><spring:message code="lbl.localidade"/> </strong> ${imovelForm.bairro} - ${imovelForm.cidade}, ${imovelForm.uf}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	  <div class="line"></div>
		                                            		                                            	 
		                                            	 <div class="form-group"> 
		                                                		<strong class="col-sm-5"><spring:message code="lbl.acao.imovel"/> </strong> ${imovelForm.acaoFmt} 
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            		                                            	 
		                                            	 <div class="form-group"> 
		                                                		<strong class="col-sm-5"><spring:message code="lbl.status.imovel"/> </strong> ${imovelForm.perfilImovelFmt} 
		                                            	 </div><!-- /.form-group -->		
		                                            	 
		                                            	 <div class="line"></div>		                                            	                        	 
		                                            	 
		                                            	 <div class="form-group">																				            
							                              		<strong class="col-sm-5"><spring:message code="lbl.data.ultima.imovel.atualizacao.resum"/> </strong> <fmt:formatDate value='${imovelForm.dataUltimaAtualizacao}' pattern='dd/MM/yyyy'/>
							                             </div><!-- /.form-group -->	
		                                            	 
		                                            	 <div class="line"></div>		                                            	 
		                                            	 
		                                            	 <div class="form-group">																				            
							                              		<strong class="col-sm-5"><spring:message code="lbl.data.cadastro.imovel"/> </strong> <fmt:formatDate value='${imovelForm.dataCadastro}' pattern='dd/MM/yyyy'/>
							                             </div><!-- /.form-group -->	
		                                            </div>                          
		                                        </div>                                       
		                                    </div><!-- /.panel-body -->
		                                    <!--/ End tabs content -->
		                                </div><!-- /.panel -->
		                            </div>
		                         
			                    </div>                   
			                    <div class="divider"></div>	
	                    
	                      	   <div class="row">
		                      		<div class="panel">
		                      			<c:choose>
		                                  <c:when test="${not empty listaUsuarios}">
				                                <div class="panel-heading">
				                                    <div class="pull-left">		                                    
				                                    			<h3 class="panel-title"><spring:message code="lbl.analise.usuarios.lista.usuarios"/></h3>		                                    
				                                    	&nbsp; <label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.quant.total.usuarios"/> </strong>: (${quantTotalUsuarios}) </label>		                                        
				                                    </div>
				                                    <div class="pull-right">
				                                        <a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>
				                                    </div>
				                                    <div class="clearfix"></div>
				                                </div><!-- /.panel-heading -->
				                                <div class="panel-body no-padding">
				                                    <div class="table-responsive" style="margin-top: -1px;">
				                                    	
				                                            		 <table class="table table-striped">
							                                            <thead>			                                           
							                                            		<tr>		                                                
									                                            	<th class="text-center"></th>
									                                                <th class="text-center"><spring:message code="lbl.nome.usuario"/></th>
									                                                <th class="text-center"><spring:message code="lbl.perfil.usuario"/></th>
									                                                <th class="text-center"><spring:message code="lbl.estado"/></th>
									                                                <th class="text-center"></th>
									                                            </tr>			                                           
							                                            </thead>
							                                            <tbody>
							                                            
							                                            		<c:forEach var="usuario" items="${listaUsuarios}" >
									                                            	<tr>
										                                                <td class="text-center">	
																							<a href="${urlUsuario}/detalhesUsuario/${usuario.id}" >
																								<img src="data:image/jpeg;base64,${usuario.imagemArquivo}" style="width: 60px; height: 50px; " />
																							</a>									                     			                				
										                                                </td>			                                                
										                                                <td class="text-center">
										                                                	<a href="${urlUsuario}/detalhesUsuario/${usuario.id}" >
																									${usuario.nome}
																							</a>
																						</td>	
																						<td class="text-center">${usuario.perfilFmt} </td>		
																						<td class="text-center">${usuario.estado} </td>	  
																						<td>  
																							<button id="adicionar_${usuario.id}" type="button" onClick="adicionarPossivelInteressado('${usuario.id}', '${imovelForm.id}');" class="button btn-primary"> <spring:message code="lbl.btn.adicionar.geral"/> </button>
																							<button id="adicionado_${usuario.id}" style="display: none;" type="button"  class="button btn-primary"> <spring:message code="lbl.btn.adicionado.geral"/> </button>
																						</td>                                            			                                              	                                            
										                                            </tr>                            
									                                            </c:forEach>  
							                                            </tbody>
							                                        </table>	                                            	
				                                       
				                                    </div>
				                                </div>
		                                </c:when>
                                           	<c:when test="${empty listaUsuarios}">
                                           		<div class="callout callout-warning">
				                                    <strong><spring:message code="lbl.nenhum.usuario.procurar.interessados"/></strong>
				                                </div>
                                           	</c:when>
                                        </c:choose>
                            		</div><!-- /.panel -->
		                       </div>		  
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
       
  		<c:import url="../../layout/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>