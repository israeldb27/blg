<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

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
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                   <form:form id="usuarioForm" modelAttribute="usuarioForm" action="${urlUsuario}/cadastrarUsuario" class="form-horizontal mt-10">
                     <div class="row">
                     		 <c:if test="${msgSucesso != null }">
                     	 		 <div class="alert alert-success">
                                      <strong><spring:message code="msg.cadastro.usuario.sucesso"/></strong> 
                                 </div>	                     	 
			               </c:if>  
                     	    
			                <c:if test="${msgSucesso == null }">
                     		<!--/ INICIO ABA FOTO PRINCIPAL IMOVEL -->                                 
                   		
		                   	   <div class="col-md-12" align="center">
		                            <!-- Start horizontal form -->
		                            <div class="panel rounded shadow">
		                                <div class="panel-heading">  
		                                    <div class="pull-left">
		                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.alterar.foto.usuario.principal"/> <code></code></h3>
		                                    </div>
		                                    <div class="pull-right">
		                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
		                                    </div>
		                                    <div class="clearfix"></div>
		                                </div><!-- /.panel-heading -->
		                                <div class="panel-body no-padding">
		                                      <div class="form-body">                                      
													
												<form:hidden path="id" value="${usuarioForm.id}"/>
												<div class="form-group">
		                                               <ul class="list-unstyled">
					                                        <li class="text-center">				                                        
					                                            <img class="img-circle img-bordered-primary" src="${context}${usuarioForm.imagemArquivo}" style="width: 200px; height: 200px; " alt="Foto Principal">
					                                        </li>
					                                   </ul>  
		                                        </div><!-- /.form-group -->
		
		                                      </div>
		                               </div>
		                           </div>
		                       </div>
		                <!--/ FIM ABA FOTO PRINCIPAL IMOVEL --> 	
                    	
                    	<!--/ INICIO ABA INFORMACOES BASICAS -->
                    	<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.cad.informacoes.basicas"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="nome" class="col-sm-3 control-label"><spring:message code="lbl.nome.usuario"/></label>
                                                <div class="col-sm-7">
                                                    <input value="${usuarioForm.nome}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="login" class="col-sm-3 control-label"><spring:message code="lbl.login"/></label>
                                                <div class="col-sm-7">
                                                    <input value="${usuarioForm.login}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="perfil" class="col-sm-3 control-label"><spring:message code="lbl.perfil.usuario"/></label>
                                                <div class="col-sm-7">                                                 	
                                                	
                                                	<c:if test="${usuarioForm.perfil == 'P'}">
                                                		<input value='<spring:message code="lbl.perfil.usuario.cliente"/>' class="form-control" readonly="readonly" type="text">
                                                	</c:if>
                                                	<c:if test="${usuarioForm.perfil == 'C'}">
                                                		<input value='<spring:message code="lbl.perfil.usuario.corretor"/>' class="form-control" readonly="readonly" type="text">
                                                	</c:if>
                                                	<c:if test="${usuarioForm.perfil == 'I'}">
                                                		<input value='<spring:message code="lbl.perfil.usuario.imobiliaria"/>' class="form-control" readonly="readonly" type="text">
                                                	</c:if>
                                                   
                                                </div>
                                            </div><!-- /.form-group -->                                                  
                                                       
                                           <c:if test="${usuarioForm.perfil != 'imobiliaria'}">
                                           		<div class="form-group">
	                                                <label for=cpf class="col-sm-3 control-label"><spring:message code="lbl.cpf"/></label>
	                                                <div class="col-sm-7">                                                    
	                                                    <input value="${usuarioForm.cpf}" class="form-control" readonly="readonly" type="text">
	                                                </div>
	                                            </div><!-- /.form-group -->
                                           </c:if>
                                           
                                            <c:if test="${usuarioForm.perfil == 'I'}">
                                           		<div  class="form-group">
	                                                <label for=cpf class="col-sm-3 control-label"><spring:message code="lbl.cpf"/></label>
	                                                <div class="col-sm-7">                                                    
	                                                    <input value="${usuarioForm.cnpj}" class="form-control" readonly="readonly" type="text">
	                                                </div>
	                                            </div><!-- /.form-group -->
                                           </c:if>                                            
                                              
                                           <c:if test="${usuarioForm.perfil == 'C'}">
	                                             <div class="form-group">
	                                                <label for="creci" class="col-sm-3 control-label"><spring:message code="lbl.creci"/></label>
	                                                <div class="col-sm-7">                                                    
	                                                  	<input value="${usuarioForm.creci}" class="form-control" readonly="readonly" type="text">
	                                                </div>
	                                            </div><!-- /.form-group -->            
                                            </c:if>                                
                                            
                                        </div><!-- /.form-body -->   

                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
						<!--/ FIM ABA INFORMACOES BASICAS -->
						
						<!--/ INICIO ABA LOCALIZACAO -->
						<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.localizacao.usuario"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="idEstado" class="col-sm-3 control-label"><spring:message code="lbl.estado"/></label>
                                                <div class="col-sm-7">
                                                    <input value="${usuarioForm.estado}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="idCidade" class="col-sm-3 control-label"><spring:message code="lbl.cidade"/></label>
                                                <div class="col-sm-7">
                                                    <input value="${usuarioForm.cidade}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="idBairro" class="col-sm-3 control-label"><spring:message code="lbl.bairro"/></label>
                                                <div class="col-sm-7">
                                                     <input value="${usuarioForm.bairro}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->               
                                        </div><!-- /.form-body -->
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        <!--/ FIM ABA LOCALIZACAO -->
                        
                        <!--/ INICIO ABA CONTATO PESSOAL -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.contato.pessoal"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="email" class="col-sm-3 control-label"><spring:message code="lbl.email"/></label>
                                                <div class="col-sm-7">
                                                    <input value="${usuarioForm.email}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="telefone" class="col-sm-3 control-label"><spring:message code="lbl.telefone"/></label>
                                                <div class="col-sm-7">
                                                    <input value="${usuarioForm.telefone}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                                      
                                        </div><!-- /.form-body -->    
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        <!--/ FIM ABA CONTATO PESSOAL -->
                        
                        <!--/ INICIO ABA PERMISSOES -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.permissoes.usuario"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="idPermBuscaUsuario" class="col-sm-3 control-label"><spring:message code="lbl.permissao.busca.usuario"/></label>
                                                <div class="col-sm-7">                                                    
									                 <c:if test="${usuarioForm.habilitaBusca == 'S'}">
									                 	<input value='<spring:message code="lbl.sim"/>' class="form-control" readonly="readonly" type="text">
									                 </c:if>
									                 <c:if test="${usuarioForm.habilitaBusca == 'N'}">
									                 	<input value='<spring:message code="lbl.nao"/>' class="form-control" readonly="readonly" type="text">
									                 </c:if>
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="habilitaDetalhesInfoUsuario" class="col-sm-3 control-label"><spring:message code="lbl.permissao.exibir.detalhes.usuario"/></label>
                                                <div class="col-sm-7">                                                   
									                 <c:if test="${usuarioForm.habilitaDetalhesInfoUsuario == 'S'}">
									                 	<input value='<spring:message code="lbl.sim"/>' class="form-control" readonly="readonly" type="text">
									                 </c:if>
									                 <c:if test="${usuarioForm.habilitaDetalhesInfoUsuario == 'N'}">
									                 	<input value='<spring:message code="lbl.nao"/>' class="form-control" readonly="readonly" type="text">
									                 </c:if>
                                                </div>
                                            </div><!-- /.form-group -->
                                        </div><!-- /.form-body -->     
                                         
                                        <div class="form-footer">  
                                            <div class="col-sm-offset-3">
                                            	<spring:message code="lbl.hint.confirmar.geral" var="hintConfirmaDados"/>
                                                <button type="submit" class="btn btn-success" title="${hintConfirmaDados}"><spring:message code="lbl.btn.confirmar.dados.geral"/></button>
                                            </div>
                                        </div><!-- /.form-footer -->                        

                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        
                        <!--/ FIM ABA PERMISSOES -->
                        
                        </c:if>
                        </div><!-- /.row -->
                      
                   </form:form> 
                            
                </div><!-- /.body-content -->
                <!--/ End body content -->
                
             <!-- FIM - Cadastro Usuario -->       
         
        
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