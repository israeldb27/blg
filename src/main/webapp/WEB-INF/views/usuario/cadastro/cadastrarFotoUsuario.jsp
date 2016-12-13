<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/preferencia" var="urlPreferencia"/>
    
<script type="text/javascript">
$(document).ready(function() {
	
});	
 	
</script>

<c:import url="../../layout/cadastroUsuario/head-layout.jsp"></c:import>
   
    <body>

        <!-- INICIO - Cadastro Usuario -->
            
            <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.cadastro.usuario"/> </h2>                                                                        
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                 <div class="panel panel-success">
                     <div class="panel-heading">
                         <h3 class="panel-title"></h3>
                     </div><!-- /.panel-heading -->
                     <div class="panel rounded shadow">
                     		<div class="panel-body">
                       	<div align="center" class="inner-all">                       		
                       			<a href="${urlPreferencia}/inicioCadastroUsuarioPreferenciaImoveis/${usuarioForm.id}"  class="btn btn-primary btn-lg btn-slideright" type="submit" >Avançar </a>                     		
                         </div>                           
                       </div><!-- /.panel-body -->
                     </div>
                     
                 </div><!-- /.panel -->  
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                   
                     	<div class="row"> 	
                    			<div class="col-md-6">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.alterar.foto.imovel.atual"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                      <div class="form-body">
                                      	<div class="form-group">                                              
                                              <div class="col-sm-7">
                                                  <ul class="list-unstyled">
				                                        <li class="text-center">				                                        
				                                            <img class="img-circle img-bordered-primary" src="${context}/${usuarioForm.imagemArquivo}" style="width: 200px; height: 200px; " alt="Tol Lee">
				                                        </li>
				                                   </ul>     	
                                              </div>
                                          </div><!-- /.form-group -->
                                      </div>
                               </div>
                           </div>
                       </div>              
                   		
                   	   <div class="col-md-6">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.alterar.foto.imovel.nova"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                      <div class="form-body">
                                      
											<form:form id="usuarioForm" modelAttribute="usuarioForm" action="${urlUsuario}/cadastrarFotoUsuario" class="form-horizontal mt-10" enctype="multipart/form-data" method="POST">
												<form:hidden path="id" value="${usuarioForm.id}"/>
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
	                                            
	                                            <button type="submit" class="btn btn-primary btn-block">Salvar Foto</button>    
											</form:form>

                                      </div>
                               </div>
                           </div>
                       </div>                    	
                        
                   </div><!-- /.row -->
                </div><!-- /.body-content -->
                <!--/ End body content -->
                
             <!-- FIM - Cadastro Usuario -->       
         
		 
			<!--/ Start - modal Ajuda - informações funcionalidade -->            	
            <div class="modal fade bs-modal-ajuda-informacoes" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="panel panel-tab panel-tab-double">
                            <!-- Start tabs heading -->
                            <div class="panel-heading no-padding">
                                <ul class="nav nav-tabs">
                                    <li class="active">
                                        <a href="#tab2-1" data-toggle="tab">
                                            <i class="fa fa-file-text"></i>
                                            <div>
                                                <span class="text-strong"><spring:message code="lbl.title.modal.descricao"/></span>                                                
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#tab2-2" data-toggle="tab">
                                            <i class="fa fa-file-text"></i>
                                            <div>
                                                <span class="text-strong"><spring:message code="lbl.title.modal.regras.basicas"/></span>                                                
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#tab2-3" data-toggle="tab">
                                            <i class="fa fa-file-text"></i>
                                            <div>
                                                <span class="text-strong"><spring:message code="lbl.title.modal.objetivo"/></span>                                                
                                            </div>
                                        </a>
                                    </li>                                    
                                </ul>
                            </div>
                            <!--/ End tabs heading -->

                            <!-- Start tabs content -->
                            <div class="panel-body">
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="tab2-1">
                                        <h4><spring:message code="lbl.title.modal.descricao"/></h4>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    </div>
                                    <div class="tab-pane fade" id="tab2-2">
                                        <h4><spring:message code="lbl.title.modal.regras.basicas"/></h4>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    </div>
                                    <div class="tab-pane fade" id="tab2-3">
                                        <h4><spring:message code="lbl.title.modal.objetivo"/></h4>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    </div>                                    
                                </div>
                            </div>
                            <!--/ End tabs content -->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.title.modal.fechar"/></button>                            
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->            
			<!--/ End - modal Ajuda - informações funcionalidade -->
        
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