<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.busqueumlugar.enumerador.AcaoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>

<c:set var="listaAcaoImovel" value="<%= AcaoImovelEnum.values() %>"/>
<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>
<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario" var="urlUsuario"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
 
<script type="text/javascript">

	function mostrarModal(id){	
		if (id == 0){
			$('#msgModal').html("<spring:message code='lbl.aba.modal.desc.lista.todas.propostas'/>");		
			$('#msgModalFuncionalidade').html("<spring:message code='lbl.link.todos.imoveis.proposta'/>");
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
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.link.propostas.imoveis"/> </h2>					
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
													<a href="${urlUsuario}/detalhesUsuario/${usuarioProposta.id}" >												
														<img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${usuarioProposta.imagemArquivo}" style="width: 200px; height: 200px; ">
													</a>
		                                            
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize">
														<a href="${urlUsuario}/detalhesUsuario/${usuarioProposta.id}" >
															${usuarioProposta.nome}
														</a>
													</h4>
		                                            <p class="text-muted text-capitalize">  ${usuarioProposta.perfilFmt} </p>
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
		                                                    <i class="fa fa-user"></i>
		                                                    <div>
		                                                        <span class="text-strong"><spring:message code="lbl.todas.lista.interesse.info.imovel"/></span>                                                        
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
		                                                		<strong class="col-sm-3"> <spring:message code="lbl.data.cadastro.usuario"/> </strong> <fmt:formatDate value='${usuarioProposta.dataCadastro}' pattern='dd/MM/yyyy'/>
		                                            	 </div><!-- /.form-group -->
		                                            
		                                            	 <div class="line"></div>
		                                            	 
		                                            	 <div class="form-group">
		                                                		<strong class="col-sm-3"><spring:message code="lbl.estado"/></strong> ${usuarioProposta.estado}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            	 
		                                            	 <div class="form-group">
		                                                		<strong class="col-sm-3"><spring:message code="lbl.cidade"/></strong> ${usuarioProposta.cidade}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>		                                            	 							                             
							                             
							                             <div class="form-group">
		                                                		<strong class="col-sm-3"><spring:message code="lbl.bairro"/></strong> ${usuarioProposta.bairro}
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
		                                <div class="panel-heading">
		                                    <div class="pull-left">
		                                        <h3 class="panel-title"><spring:message code="lbl.link.todos.imoveis.proposta"/></h3>
		                                        &nbsp;&nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: (${quantTotalImoveis}) </label>
		                                    </div>
		                                    <div class="pull-right">
		                                        <a href="#a" class="btn btn-sm" onClick="mostrarModal(0);" ><i class="fa fa-question" ></i></a>
		                                    </div>
		                                    <div class="clearfix"></div>
		                                </div><!-- /.panel-heading -->
		                                <div class="panel-body no-padding">
		                                    <div class="table-responsive" style="margin-top: -1px;">
		                                        <table class="table table-striped">
		                                            <thead>
		                                            <tr>		                                                
		                                            	<th class="text-center"></th>
		                                                <th class="text-center"><spring:message code="lbl.titulo.imovel"/></th>		                                                
		                                                <th class="text-center"><spring:message code="lbl.tipo.imovel"/></th>
		                                                <th class="text-center"><spring:message code="lbl.valor.proposta"/></th>
		                                                <th class="text-center"><spring:message code="lbl.data.proposta"/></th>  
		                                            </tr>
		                                            </thead>
		                                            <tbody>
		                                            <c:forEach var="imovelProposta" items="${listaTodosImoveisPropostasUsuario}" >
			                                            <tr>
			                                                <td class="text-center">	 
																<a href="${urlImovel}/detalhesImovel/${imovelProposta.imovel.id}" >
																	<img src="data:image/jpeg;base64,${imovelProposta.imovel.imagemArquivo}" style="width: 60px; height: 50px; " />	                				
																</a>
			                                                </td>			                                                
			                                                <td class="text-center" style="font-size: 13px;">
																<a href="${urlImovel}/detalhesImovel/${imovelProposta.imovel.id}" >
																	${imovelProposta.imovel.titulo}
																</a>  
															</td>
	                                            			 <td class="text-center" style="font-size: 13px;"> ${imovelProposta.imovel.tipoImovelFmt} </td>
													         <td class="text-center" style="font-size: 13px;"><fmt:formatNumber value="${imovelProposta.valorProposta}" pattern="#,##0.00;-0"/></td>	                     		
			                                                 <td class="text-center" style="font-size: 13px;"><fmt:formatDate value='${imovelProposta.dataCadastro}' pattern='dd/MM/yyyy'/></td>			                                                			                                              	                                            
			                                            </tr>
		                                            </c:forEach>
		                                            </tbody>
		                                        </table>
		                                    </div>
		                                </div>
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