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
           
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.planos"/> </h2>                                                      
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                   <div class="body-content animated fadeIn container limit-form" style="width:1100px; min-height:300px;">

                    <div class="row">
                        <div class="col-md-12">                        		

                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <div class="pull-left">  
                                        <h3 class="panel-title"><spring:message code="lbl.title.formulario"/></h3>
                                    </div><!-- /.pull-left -->
                                    <div class="pull-right">
                                        
                                    </div><!-- /.pull-right -->                                    
                                    
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">                                    
                                    <form:form method="POST" class="form-horizontal mt-12" id="planoForm" modelAttribute="planoForm" action="${urlAdmin}/atualizarPlano" >
                                    <form:hidden  id="id" path="id"/>
                                      <div class="form-body">
                                        <div class="form-group">                                        	
                                        	
	                                        <div class="form-group">
                                                <label for="nome" class="col-sm-3 control-label"><spring:message code="lbl.admin.nome.plano"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="nome" path="nome" class="form-control"/>
                                                    <form:errors id="nome" path="nome" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="duracaoPlano" class="col-sm-3 control-label"><spring:message code="lbl.admin.duracao.plano"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="duracaoPlano" path="duracaoPlano" class="form-control"/>
                                                    <form:errors id="duracaoPlano" path="duracaoPlano" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->  
                                            
                                            <div class="form-group">
                                                <label for="valorPlano" class="col-sm-3 control-label"><spring:message code="lbl.admin.valor.plano"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="valorPlano" path="valorPlano" class="form-control"/>
                                                    <form:errors id="valorPlano" path="valorPlano" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            
                                            <div class="form-group">
                                                <label for="quantCadastroImoveis" class="col-sm-3 control-label"><spring:message code="lbl.admin.quant.imoveis.plano"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="quantCadastroImoveis" path="quantCadastroImoveis" class="form-control"/>
                                                    <form:errors id="quantCadastroImoveis" path="quantCadastroImoveis" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                             <div class="form-group">
                                                <label for="quantFotos" class="col-sm-3 control-label"><spring:message code="lbl.admin.quant.fotos.imoveis.plano"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="quantFotos" path="quantFotos" class="form-control"/>
                                                    <form:errors id="quantFotos" path="quantFotos" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="quantImoveisIndicacao" class="col-sm-3 control-label"><spring:message code="lbl.admin.quant.indicacoes.plano"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="quantImoveisIndicacao" path="quantImoveisIndicacao" class="form-control"/>
                                                    <form:errors id="quantImoveisIndicacao" path="quantImoveisIndicacao" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group --> 
                                            
                                            <div class="form-group">
                                                <label for="quantEmailsPorImovel" class="col-sm-3 control-label"><spring:message code="lbl.admin.quant.emails.indicacoes.plano"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="quantEmailsPorImovel" path="quantEmailsPorImovel" class="form-control"/>
                                                    <form:errors id="quantEmailsPorImovel" path="quantEmailsPorImovel" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group --> 
                                            
                                            <div class="form-group">
                                                <label for="habilitaEnvioMensagens" class="col-sm-3 control-label"><spring:message code="lbl.admin.habilita.sol.envio.msgs"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:select id="habilitaEnvioMensagens" path="habilitaEnvioMensagens" class="form-control">                                
								                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>   
														<form:option value="S"> <spring:message code="lbl.sim"/></form:option>
														<form:option value="N"> <spring:message code="lbl.nao"/></form:option>														
								                	</form:select>
								                	<form:errors id="habilitaEnvioMensagens" path="habilitaEnvioMensagens" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            
                                          <div class="form-group">             			
                                			 <label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
                                            <div class="col-sm-7">
                                            	<br>
                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 40%;" ><spring:message code="lbl.btn.editar.geral"/></button>
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