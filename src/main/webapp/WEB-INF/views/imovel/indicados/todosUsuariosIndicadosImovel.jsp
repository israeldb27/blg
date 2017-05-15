<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>
<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario" var="urlUsuario"/>
 
<script type="text/javascript">

$(document).ready(function() {
});	

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
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.link.imoveis.indicacoes"/> </h2>					
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
													<a href="${urlImovel}/detalhesImovel/${imovel.id}" ><img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${imovel.imagemArquivo}"  style="width: 200px; height: 200px; "></a>		                                            
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize"> <a href="${urlImovel}/detalhesImovel/${imovel.id}" >${imovel.titulo}</a></h4>
		                                            <p class="text-muted text-capitalize"> ${imovel.tipoImovelFmt} </p>
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
		                                                        <span class="text-strong"><spring:message code="lbl.todas.indicados.info.imovel"/></span>                                                        
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
		                                <div class="panel-heading">
		                                    <div class="pull-left">
		                                        <h3 class="panel-title"><spring:message code="lbl.todos.usuario.indicados"/></h3>
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
		                                                <th class="text-center"><spring:message code="lbl.nome.usuario.indicado"/></th>
		                                                <th class="text-center"><spring:message code="lbl.data.indicacao"/></th>
		                                            </tr>
		                                            </thead>
		                                            <tbody>
		                                            <c:forEach var="imovelIndicado" items="${listaTodasIndicacoesUsuario}" >
			                                            <tr>
			                                                <td class="text-center">	
																<a href="${urlUsuario}/detalhesUsuario/${imovelIndicado.usuario.id}" ><img src="data:image/jpeg;base64,${imovelIndicado.usuario.imagemArquivo}" style="width: 60px; height: 50px; " /></a>									                     			                				
			                                                </td>			                                                
			                                                <td class="text-center" style="font-size: 13px;"><a href="${urlUsuario}/detalhesUsuario/${imovelIndicado.usuario.id}" >${imovelIndicado.usuario.nome}</a> </td>
			                                                <td class="text-center" style="font-size: 13px;"><fmt:formatDate value='${imovelIndicado.dataIndicacao}' pattern='dd/MM/yyyy'/></td>			                                                			                                                			                                              	                                            
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
			
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
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