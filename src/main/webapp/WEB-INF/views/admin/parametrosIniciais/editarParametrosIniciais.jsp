<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/admin" var="urlAdmin"/>


    <!-- START @HEAD -->
 <script type="text/javascript">

$(document).ready(function() {

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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.edita.parametros.iniciais"/> </h2>                                                      
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
									<form:form class="form-horizontal mt-12" method="POST" id="parametrosIniciaisForm" modelAttribute="parametrosIniciaisForm" action="${urlAdmin}/editarParametrosInicial" >
									<form:hidden  id="id" path="id" />
                                      <div class="form-body">
                                        <div class="form-group">                                        	
                                        	
	                                        <div class="form-group">
                                                <label for="nome" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.nome"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="nome" path="nome" class="form-control"/>
                                                    <form:errors id="nome" path="nome" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="label" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.label"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="label" path="label" class="form-control"/>
                                                    <form:errors id="label" path="label" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="tipoParam" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.tipo"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:select id="tipoParam" path="tipoParam" class="form-control">                                
															<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>   
															<form:option value="N"> <spring:message code="lbl.admin.param.iniciais.tipo.numerico"/></form:option>
															<form:option value="C"> <spring:message code="lbl.admin.param.iniciais.tipo.caractere"/></form:option>														
													</form:select>
													<form:errors id="tipoParam" path="tipoParam" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->  
                                            
                                            
                                            <div class="form-group">
                                                <label for="valorQuantidade" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.valor.quantidade"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="valorQuantidade" path="valorQuantidade" class="form-control"/>
                                                    <form:errors id="valorQuantidade" path="valorQuantidade" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="valorHabilita" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.valor.habilita"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:select id="valorHabilita" path="valorHabilita" class="form-control">                                
															<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>   
															<form:option value="S"> <spring:message code="lbl.sim"/></form:option>
															<form:option value="N"> <spring:message code="lbl.nao"/></form:option>														
													</form:select>
													<form:errors id="valorHabilita" path="valorHabilita" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->  
                                            
                                            
                                            <div class="form-group">
                                                <label for="observacao" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.observacao"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="observacao" path="observacao" class="form-control"/>
                                                </div>
                                            </div><!-- /.form-group --> 
                                            
                                             <div class="form-group">             			
	                                			 <label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
	                                            <div class="col-sm-7">
	                                            	<br>
	                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 40%;"><spring:message code="lbl.btn.editar.geral"/></button>
	                                            </div>
	                                       </div>                                           
                                            
                                            
                                        </div><!-- /.form-group -->
                                       </div> 
                                    </form:form>                                    
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->
                            
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
       
  		<c:import url="../layout-admin/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>