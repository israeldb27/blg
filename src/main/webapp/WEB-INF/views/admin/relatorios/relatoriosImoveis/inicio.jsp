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
             
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.relatorios.imoveis"/> </h2>                                                      
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
              <div class="body-content animated fadeIn container limit-form" style="width:1200px;">

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
                                    <form:form class="form-horizontal mt-12" method="POST" id="administracaoForm" modelAttribute="administracaoForm" action="${urlAdmin}/selecionarRelatorioImoveis" >
                                      <div class="form-body">
                                        <div class="form-group">   
                                        
                                        	<div class="form-group">   
	                                            <label for="relatorioSelecionado" class="col-sm-4 control-label"><spring:message code="lbl.admin.opcao.nome.rel"/>:</label>
		                                        <div class="col-sm-7">
		                                            <form:select id="relatorioSelecionado" path="relatorioSelecionado" class="form-control">                                
									                    <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
														<form:option value="quantTotal"><spring:message code="lbl.admin.opcao.rel.total.imoveis"/></form:option>
														<form:option value="maisInteressados"><spring:message code="lbl.admin.opcao.rel.mais.interessados"/></form:option>
														<form:option value="maisPropostas"><spring:message code="lbl.admin.opcao.rel.mais.propostas"/></form:option>
														<form:option value="maisComentarios"><spring:message code="lbl.admin.opcao.rel.mais.comentarios"/></form:option>
														<form:option value="maisVisitas"><spring:message code="lbl.admin.opcao.rel.mais.visualizados"/></form:option>
														<form:option value="maisIndicacoes"><spring:message code="lbl.admin.opcao.rel.mais.indicados"/></form:option>
														<form:option value="maisSolParceria"><spring:message code="lbl.admin.opcao.rel.mais.sol.parceria"/></form:option>
														<form:option value="maisSolIntermediacao"><spring:message code="lbl.admin.opcao.rel.mais.sol.intermediacao"/></form:option>
									                </form:select>
									                 <form:errors id="relatorioSelecionado" path="relatorioSelecionado" cssClass="errorEntrada"  />
	                                                 <input type="text" class="hide" id="acao"/>
	                                            </div>
	                                        </div>                                                  
                                            
                                           <div class="form-group">             			
	                                			 <label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
	                                            <div class="col-sm-7">
	                                            	<br>	                                            	
	                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 40%;"><spring:message code="lbl.btn.selecionar.geral"/></button>
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
       
  		<c:import url="../../layout-admin/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>