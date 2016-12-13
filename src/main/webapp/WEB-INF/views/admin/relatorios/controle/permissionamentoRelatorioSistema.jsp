<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioEnum"%>

<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioEnum.values() %>"/>
<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/admin" var="urlAdmin"/>


    <!-- START @HEAD -->
 <script type="text/javascript">

$(document).ready(function() {

});

</script>		
 
<c:import url="../../layout-admin/head-layout.jsp"></c:import>
   
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../../layout-admin/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../../layout-admin/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.permissionamento.rel.sistema"/> </h2>                                                      
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                        <div class="col-md-12">                        		

                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <div class="pull-left">  
                                        <h3 class="panel-title">Formulário</h3>
                                    </div><!-- /.pull-left -->
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>
                                    </div><!-- /.pull-right -->                                    
                                    
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">                                                                        
									<form:form class="form-horizontal mt-12" method="POST" id="relatorioForm" modelAttribute="relatorioForm" action="${urlAdmin}/adicionarPermissaoRelatorio" >
									<form:hidden path="idRelatorioSelecionado"/>
                                      <div class="form-body">
                                        <div class="form-group">                                        	
                                        	
	                                        <div class="form-group">
                                                <label for="nome" class="col-sm-3 control-label"><spring:message code="lbl.admin.opcao.nome.rel"/></label>
                                                <div class="col-sm-7">                                                                                                        
													<input value="${relatorioForm.nome}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="tipo" class="col-sm-3 control-label"><spring:message code="lbl.rel.tipo"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <c:if test="${relatorioForm.tipo == 'B'}">
														<input value='<spring:message code="lbl.rel.tipo.basico"/>' class="form-control" readonly="readonly" type="text">
													</c:if>
													<c:if test="${relatorioForm.tipo == 'P'}">				                     			
														<input value='<spring:message code="lbl.rel.tipo.premium"/>' class="form-control" readonly="readonly" type="text">															
													</c:if>
                                                </div>
                                            </div><!-- /.form-group -->  
											
											<div class="form-group">   
												<label for="cobranca" class="col-sm-3 control-label"><spring:message code="lbl.admin.havera.cobranca.servico"/></label>
												<div class="col-sm-7">
													 <c:if test="${relatorioForm.cobranca == 'S'}">
														<input value='<spring:message code="lbl.sim"/>' class="form-control" readonly="readonly" type="text">
													</c:if>
													<c:if test="${relatorioForm.cobranca == 'N'}">				                     			
														<input value='<spring:message code="lbl.nao"/>' class="form-control" readonly="readonly" type="text">															
													</c:if>
												</div>
											</div>  
                                            
                                            <div class="form-group">
                                                <label for="descricao" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.servico.descricao"/></label>
                                                <div class="col-sm-7">                                                                                                        
													<input value="${relatorioForm.descricao}" class="form-control" readonly="readonly" type="text">
                                                </div>
                                            </div><!-- /.form-group -->
											
											<div class="form-group">
                                                <label for="item" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.servico.nome.item"/></label>
                                                <div class="col-sm-7">                                                    
													<input value="${relatorioForm.item}" class="form-control" readonly="readonly" type="text">                                                    
                                                </div>
                                            </div><!-- /.form-group -->
											
											<div class="form-group">   
												<label for="perfilSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.admin.opcao.permissao.perfil.selecionado"/></label>
												<div class="col-sm-7">
													 <form:select id="perfilSelecionado" path="perfilSelecionado" class="form-control">                                
															<form:option value="todos" ><spring:message code="lbl.admin.opcao.permissao.rel.sistema.todos"/></form:option>   
															<form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />															
													</form:select>
												</div>
											</div> 
                                            
                                              <div class="form-group">             			
	                                			 <label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
	                                            <div class="col-sm-7">
	                                            	<br>
	                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 40%;"><spring:message code="lbl.btn.adicionar.geral"/></button>
	                                            </div>
	                                       </div>  
	                                       
                                        </div><!-- /.form-group -->
                                       </div> 
                                    </form:form>                                    
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->
                            <c:if test="${msgValidacaoListaMeusServicos != null }">
                             	<div class="alert alert-danger">
                                      <strong>${msgValidacaoListaMeusServicos}</strong> 
                                  </div>
                            </c:if>      
                      
                      		<c:if test="${ empty listaPermissoes }">
                                 	
                                 	<div class="panel" align="center">	                                
		                                <div class="panel-body">
		                                    <spring:message code="msg.lista.permissao.rel.sistema.vazio"/>
		                                </div><!-- /.panel-body -->
		                            </div><!-- /.panel -->
                             </c:if>
                      
                            <!-- Start basic color table -->
                            
                           <c:if test="${ not empty listaPermissoes }"> 
                            <div class="panel">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.admin.lista.permissoes.rel.sistema"/></h3>
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
                                            	<th class="text-center"><spring:message code="lbl.admin.opcao.permissao.data.cadastro"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.opcao.permissao.perfil"/></th>                                                
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="permissao" items="${listaPermissoes}" >
	                                            <tr>
	                                                <td class="text-center"> <fmt:formatDate value='${permissao.dataAcesso}' pattern='dd/MM/yyyy'/></td>
	                                                <td class="text-center"><c:if test="${permissao.perfilUsuario == 'P'}" >
																				 <spring:message code="lbl.perfil.usuario.cliente"/>
																			</c:if>
																			<c:if test="${permissao.perfilUsuario == 'C'}" >
																				 <spring:message code="lbl.perfil.usuario.corretor"/>
																			</c:if>
																			<c:if test="${permissao.perfilUsuario == 'I'}" >
																				 <spring:message code="lbl.perfil.usuario.imobiliaria"/>
																			</c:if></td>
													
													<td class="text-center">				                      											                      											                      
														  <a href="${urlAdmin}/excluirPermissionamento/${permissao.id}" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> <spring:message code="lbl.admin.link.excluir.perm.rel.sistema"/></a> 														  
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

                </div><!-- /.body-content -->
                <!--/ End body content -->
                
                <!-- Fim Meus Imoveis -->
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->
          
        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
       
  		<c:import url="../../layout-admin/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>