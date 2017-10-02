<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/relatorio/buscarRelatorio" var="urlBuscarRelatorios"/>
<spring:url value="/relatorio" var="urlRelatorio"/>

<script type="text/javascript">

$(document).ready(function() {
	$('#tipo').change(function () {		
        var comboPai = '#tipo';
        var comboFilha = '#nome';        
        limpaComboLinha(comboFilha);        
        recuperaRelatorios();
    });	

    function limpaComboLinha(comboLinha) {
        $(comboLinha).empty();
        $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');        
    }
});	

function recuperaRelatorios(){
    var parametro1 = $("#tipo").val();
    $.get("${urlBuscarRelatorios}/"+parametro1, function(data){
        jQuery.each(data, function(key, value) {
            if(value.label == null){            	
                return;            }            
            $("#nome").append("<option value='"+value.key+"'>"+value.label+"</option>");
        });
    });
}


</script>		
        <c:import url="../layout/head-layout.jsp"></c:import>   
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../layout/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../layout/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content" style="background-color:#e9eaed;">

            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.relatorios"/></h2>			
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                 <div class="body-content animated fadeIn container limit-form" style="width:900px; min-height:200px;">

                    <div class="row">
                    	<% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
							<c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        <div class="col-md-12">
                            <div class="panel rounded shadow">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.filtro.geral"/></h3>
                                    </div><!-- /.pull-left -->
                                    <div class="pull-right">
                                        <a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>
                                    </div><!-- /.pull-right -->                                    
                                    
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                    <form:form method="POST" class="form-horizontal mt-10" id="relatorioForm" modelAttribute="relatorioForm" action="${urlRelatorio}/selecionarRelatorio" >
                                      <div class="form-body">
                                        <div class="form-group">
                                        	<label for="tipo" class="col-sm-4 control-label"><spring:message code="lbl.rel.tipo"/> :</label>
                                        	<div class="col-sm-7">
											  <form:select id="tipo" path="tipo"  class="form-control" >                                
							                        <form:option value="" disabled="true" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                          
							                        <form:option value="B" ><spring:message code="lbl.rel.tipo.basico"/></form:option>
													<form:option value="P" ><spring:message code="lbl.rel.tipo.premium"/></form:option>							                      	                        
							                  </form:select> 							                  
							                  <form:errors id="tipo" path="tipo" cssClass="errorEntrada"  />
	                                        </div>
	                                        
	                                        <br> <br> <br>
	                                        
	                                        <label for="nome" class="col-sm-4 control-label"><spring:message code="lbl.rel.nome"/>: </label>
	                                        <div class="col-sm-7">  
		                                            <form:select id="nome" path="id" class="form-control" >
								                 		  <form:option value="-1" disabled="true"><spring:message code="opcao.selecao.uma.opcao"/></form:option>                   			                 		
								                 	</form:select>
								                 	<form:errors id="id" path="id" cssClass="errorEntrada"  />		                  
	                                        </div>                                             
                                            
                                            <label for="btnSubmitAdd" class="col-sm-4 control-label"></label>
                                            <div class="col-sm-7">
                                            	<br>
                                            	<button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 30%;"><spring:message code="lbl.btn.selecionar.geral"/></button>
                                            </div>
                                            
                                        </div><!-- /.form-group -->
                                       </div> 
                                    </form:form> 
                                    
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->
                        </div>  
                                             
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->

         
            </section><!-- /#page-content -->           
                     

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->
