<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>   
 
<script type="text/javascript">

$(document).ready(function() {

});	

function preparaExclusaoFoto(idFoto, idImovel){	
	$("#myModal").modal("show");
	$("#idFotoImovel").val(idFoto);
	$("#idImovel").val(idImovel);
}

function preparaInclusaoFoto(){	
	$("#modalIncluirFoto").modal("show");
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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.aba.alterar.galeria.fotos.imovel"/> </h2>                                                                        
	            </div><!-- /.header-content -->                
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">                   	
                   		
                   		<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.alterar.foto.imovel.principal"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                      <div class="form-body">
                                      	<div class="form-group">                                              
                                              <div align="center">
                                                  <ul class="list-unstyled">
				                                        <li class="text-center">				                                        
				                                            <img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${imovelForm.imagemArquivo}" style="width: 200px; height: 200px; " alt="Tol Lee">
				                                        </li>
				                                   </ul>     	
                                              </div>
                                          </div><!-- /.form-group -->
                                      </div>
                                      
                                        <div class="pull-left">		
	                                         <button onClick="preparaInclusaoFoto();" class="btn btn-primary" data-role="add">
	                                                    <span class="glyphicon glyphicon-plus"></span> Adicionar Foto Galeria
	                                         </button>	
										</div><!-- /.pull-left -->    
                               </div>
                           </div>
                       </div>              
                   </div>
                   
                   <div class="row">
                   		<div class="col-md-12"> 
                   		    <div class="panel-body">
                   		    	<c:choose>
	                        		<c:when test="${ empty imovelForm.listaImovelFotos }">
	                        			<div class="callout callout-warning">
		                                    <strong><spring:message code="lbl.rel.nenhum.registro"/></strong>                                    
		                                </div>
	                        		</c:when>
	                        		
	                        		<c:when test="${not empty imovelForm.listaImovelFotos }">
	                        			 <div class="table-responsive" style="margin-top: -1px;">
	                        			 		<table class="table table-striped table-primary">
		                                            <thead>
		                                            <tr>
		                                                <th class="text-center">Foto</th>
		                                                <th class="text-center">Data Cadastro</th>                                                                                                
		                                                <th class="text-center" style="width: 12%;"></th>
		                                            </tr>
		                                            </thead>
		                                            <tbody>				                                            
			                                            <c:forEach var="imovel" items="${imovelForm.listaImovelFotos}" >
				                                            <tr>						                                             
				                                                <td class="text-center">
				                                                	<img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${imovel.imagemArquivo}" style="width: 80px; height: 80px; " alt="Tol Lee">
				                                               	</td>
				                                                <td class="text-center"><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></td>
				                                                <td class="text-center">                                                    
				                                                    <a href="#" onClick="preparaExclusaoFoto(${imovel.id}, ${imovel.imovel.id});" class="btn btn-danger btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="Excluir"><i class="fa fa-times"></i></a>
				                                                </td>
				                                            </tr>
			                                            </c:forEach>				                                            
		                                            </tbody>
		                                        </table>	                        			 
	                        			 </div>
	                        		</c:when>
	                        		
	                        </c:choose>
                   		    </div>
	                   		
                        </div>                   
                   </div>
                   
                    
                </div><!-- /.body-content -->
         
            </section><!-- /#page-content -->
            
            <!-- Start optional size modal element - item 1 -->
            <div id="modalIncluirFoto" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <form:form id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/incluirFotoGaleriaImovel" class="form-horizontal mt-5" enctype="multipart/form-data" >
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><div id="msgModal"  ></h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                            	
                                    <label class="control-label"></label>
                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                        <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px; margin-left: 330px;"></div>
                                        <div style="margin-left: 330px;">
                                            <span class="btn btn-info btn-file"><span class="fileinput-new"><spring:message code="lbl.selecionar.foto.imovel"/></span><span class="fileinput-exists"><spring:message code="lbl.selecionar.foto.imovel"/></span>	                                                        
                                            <input type="text" name="name" id="name"/>
											<input type="file" name="file" id="file" />                                            
                                            </span>
                                            <a href="#" class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><spring:message code="lbl.remover.foto.imovel"/></a>
                                        </div>
                                    </div>
                                
                              </div><!-- /.form-group -->	
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success" ><spring:message code="lbl.btn.adicionar.geral"/></button> 
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>                                                                                  
                        </div>
						 </form:form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            
            <form name="imovelForm" class="form-horizontal form-bordered" role="form" action="${urlImovel}/excluirFotoGaleriaModal">
            <input type="hidden" id="idFotoImovel" readonly="readonly" name="idFotoImovel">
            <input type="hidden" id="idImovel" readonly="readonly" name="idImovel">
            
            	<div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								<h3 id="myModalLabel">Confirmação de exclusão de foto do imóvel</h3>
						</div>
						<div class="modal-body">
							<p>Você deseja realmente excluir a foto selecionada de sua galeria de fotos ?</p>
						</div>
						<div class="modal-footer">
							<button class="btn" data-dismiss="modal" aria-hidden="true">Não</button>
							<button class="btn btn-primary">Sim</button>
						</div>
					</div>
					</div>
				</div>
				
            </form>
			
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
			
        </section><!-- /#wrapper -->        

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>