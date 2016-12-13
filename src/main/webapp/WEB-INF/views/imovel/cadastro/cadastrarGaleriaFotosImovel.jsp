<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="context" value="<%= request.getContextPath()%>"/>
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/imovel/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/imovel/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>   
 
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
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.galeria.foto.imovel.selecao.fotos"/> <code></code></h3>
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
				                                            <img class="img-circle img-bordered-primary" src="${context}/${imovelForm.imagemArquivo}" style="width: 200px; height: 200px; " alt="Tol Lee">
				                                        </li>
				                                   </ul>     	
                                              </div>
                                          </div><!-- /.form-group -->
                                      </div>
                               </div>
                                <div class="form-footer" >  
	                              <div  align="center">	                                  
	                                  <a href="${urlImovel}/avancarCadastroImovelMapa" class="btn btn-success"><spring:message code="lbl.btn.confirmar.dados.geral"/></a>
	                              </div>
	                          </div><!-- /.form-footer -->  
                           </div>
                       </div>              
                   </div>
                   
                   <div class="row">  		
                   	   <div class="col-xs-5 col-sm-4">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.galeria.foto.imovel.selecao.fotos"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                      <div class="form-body">
                                      
											<form:form id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/incluirFotoGaleriaCadastroImovel" class="form-horizontal mt-10" enctype="multipart/form-data" method="POST">
												<form:hidden path="id" value="${imovelForm.id}"/>
												<div class="form-group">
	                                                <label class="control-label"></label>
	                                                <div class="fileinput fileinput-new" data-provides="fileinput">
	                                                    <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px;"></div>
	                                                    <div>
	                                                        <span class="btn btn-info btn-file"><span class="fileinput-new">Selecionar Foto</span><span class="fileinput-exists">Selecionar Foto</span>	                                                        
	                                                        <input type="text" name="name"/>
															<input type="file" name="file"/>
	                                                        
	                                                        </span>
	                                                        <a href="#" class="btn btn-danger fileinput-exists" data-dismiss="fileinput">Remover</a>
	                                                    </div>
	                                                </div>
	                                            </div><!-- /.form-group -->
	                                            
	                                            <button type="submit" class="btn btn-primary btn-block"><spring:message code="lbl.btn.incluir.foto"/></button>    
											</form:form>
                                      </div>
                               </div>
                           </div>
                       </div>	                		                               
                    
                   	   <div class="col-lg-8 col-md-9 col-sm-9">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.galeria.foto.imovel.nova"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                      <div class="form-body">                                      
											
											  <div class="table-responsive" style="margin-top: -1px;">
												  <c:if test="${ not empty imovelForm.listaImovelFotos }">
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
						                                                	<img class="img-circle img-bordered-primary" src="${context}${imovel.imagemArquivo}" style="width: 80px; height: 80px; " alt="Tol Lee">
						                                               	</td>
						                                                <td class="text-center"><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></td>
						                                                <td class="text-center">                                                    
						                                                    <a href="${urlImovel}/excluirFotoGaleria/${imovel.id}/${imovel.idImovel}" class="btn btn-danger btn-xs" data-toggle="tooltip" data-placement="top" data-original-title="Excluir"><i class="fa fa-times"></i></a>
						                                                </td>
						                                            </tr>
					                                            </c:forEach>				                                            
				                                            </tbody>
				                                        </table>
			                                        </c:if>
			                                        <c:if test="${ empty imovelForm.listaImovelFotos }">
			                                        	<spring:message code="msg.nenhuma.foto.cadastrada"/>
			                                        </c:if>
		                                    </div><!-- /.table-responsive -->
                                      </div>
                               </div>
                           </div>
                       </div>	
                		                               
                    </div><!-- /.row -->
                    
                </div><!-- /.body-content -->
         
            </section><!-- /#page-content -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>


</html>