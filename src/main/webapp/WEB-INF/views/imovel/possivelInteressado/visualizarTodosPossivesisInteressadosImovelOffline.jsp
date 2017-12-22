<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovelPropostas" var="urlImovelPropostas"/>
<spring:url value="/imovel" var="urlImovel"/> 
<spring:url value="/usuario" var="urlUsuario"/>
 
<script type="text/javascript">

	function mostrarModal(id){	
		if (id == 0){
			$('#msgModal').html("<spring:message code='lbl.aba.modal.desc.lista.todos.possiveis.interessados.offline'/>");		
			$('#msgModalFuncionalidade').html("<spring:message code='lbl.lista.todos.possiveis.interessados.offline'/>");
		}
		$("#idModalItem").modal("show");
	}
	
</script>
		
<c:import url="../../layout/head-layout.jsp"></c:import>

    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../../layout/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../../layout/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.aba.det.imovel.possivel.interessado.offline"/> </h2>     
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
													<a href="${urlImovel}/detalhesImovel/${imovel.id}" >
														<img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${imovel.imagemArquivo}" style="width: 200px; height: 200px; ">
													</a>		                                            
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize"><a href="${urlImovel}/detalhesImovel/${imovel.id}" >
																					${imovel.titulo}
																				</a>		                                            
													</h4>
		                                            <p class="text-muted text-capitalize">  ${imovel.tipoImovelFmt} </p>
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
		                                                        <span class="text-strong"><spring:message code="lbl.title.cad.informacoes.basicas"/></span>                                                        
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
		                                                		<strong class="col-sm-5"><spring:message code="lbl.valor.imovel"/></strong> <fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/>
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            		                                            	 
		                                            	 <div class="form-group"> 
		                                                		<strong class="col-sm-5"><spring:message code="lbl.localidade"/> </strong> ${imovel.bairro} - ${imovel.cidade}, ${imovel.uf}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	  <div class="line"></div>
		                                            		                                            	 
		                                            	 <div class="form-group"> 
		                                                		<strong class="col-sm-5"><spring:message code="lbl.acao.imovel"/> </strong> ${imovel.acaoFmt} 
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            		                                            	 
		                                            	 <div class="form-group"> 
		                                                		<strong class="col-sm-5"><spring:message code="lbl.status.imovel"/> </strong> ${imovel.perfilImovelFmt} 
		                                            	 </div><!-- /.form-group -->		
		                                            	 
		                                            	 <div class="line"></div>		                                            	                        	 
		                                            	 
		                                            	 <div class="form-group">																				            
							                              		<strong class="col-sm-5"><spring:message code="lbl.data.ultima.imovel.atualizacao.resum"/> </strong> <fmt:formatDate value='${imovel.dataUltimaAtualizacao}' pattern='dd/MM/yyyy'/>
							                             </div><!-- /.form-group -->	
		                                            	 
		                                            	 <div class="line"></div>		                                            	 
		                                            	 
		                                            	 <div class="form-group">																				            
							                              		<strong class="col-sm-5"><spring:message code="lbl.data.cadastro.imovel"/> </strong> <fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/>
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
											<c:when test="${not empty listaPossivelInteressadoOffline}">
												<div class="panel-heading">				                       
		                                			 <div class="pull-left">	
		                                					<h3 class="panel-title"><spring:message code="lbl.lista.todas.atividades"/></h3>
		                                        			&nbsp;&nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.quant.total.atividades"/> </strong>: (${quantTotalAtividades}) </label>
		                                			 </div>
		                                			 <div class="pull-right">
				                                        <a href="#a" class="btn btn-sm" onClick="mostrarModal(0);" ><i class="fa fa-question" ></i></a>	
				                                    </div>
				                                    <div class="clearfix"></div>
				                                </div><!-- /.panel-heading -->
				                                <div class="panel-body no-padding">
				                                    <div class="table-responsive" style="margin-top: -1px;">
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
					                                            <c:forEach var="interessado" items="${listaPossivelInteressadoOffline}">
					                                               <tr>
					                                               	  <td style="font-size: 13px;" class="text-center"> <small><fmt:formatDate value='${interessado.dataCadastro}' pattern='dd/MM/yyyy'/></small> </td>	
					                                               	  <td style="font-size: 13px;" class="text-center">${interessado.nomeInteressado} </td>
					                                               	  <td style="font-size: 13px;" class="text-center">${interessado.emailInteressado} </td>
					                                               	  <td style="font-size: 13px;" class="text-center">${interessado.telefoneInteressado} </td>
					                                                  <td style="font-size: 13px;" class="text-center"><small> ${interessado.chanceInteresseFmt} </small></td>		                          
					                                                  <td style="font-size: 13px;" class="text-center"><small> ${interessado.observacao} </small></td>
					                                                  <td class="text-center" >
					                                                     <a href="#a" onClick="prepararModalConfirmaEdicaoPossivelComprOffline('${interessado.id}', '${interessado.nomeInteressado}', '${interessado.emailInteressado}', '${interessado.telefoneInteressado}', '${comprador.chanceInteresse}', '${comprador.observacao}');" ><i class="fa fa-pencil-square-o"></i></a>
					                                                  </td>		                                                  
					                                                  <td class="text-center" >
					                                                     <a href="#a" onClick="prepararModalConfirmaExclusaoPossivelComprOffline(${interessado.id});" ><i class="fa fa-trash-o"></i></a>
					                                                  </td>
					                                               </tr>
					                                            </c:forEach>
					                                         </tbody>
					                                      </table>
				                                    </div>
				                                </div>	
											</c:when>
											<c:when test="${empty listaPossivelInteressadoOffline}">
												<div class="callout callout-warning">                                		
				                                    <strong><spring:message code="msg.nenhum.possivel.interessado"/></strong>		                                    
				                                </div>  
											</c:when>
										</c:choose>
                            		</div><!-- /.panel -->
		                       </div>		  
	                    </div>                   		
                		                               
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
         
            </section><!-- /#page-content -->
			
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