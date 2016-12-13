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

function prepararModalConfirmaExclusao(id){
	$("#modIdParametro").val(id);
	$('#msgRetornoConfirmExclusaoParamErro').html("");	
	$("#idModalConfirmacaoExclusaoParam").modal("show");	
}

function confirmarExclusaoFormaPagamento(){	
	var parametro = document.getElementById("modIdParametro");	
	$.ajax({
			 url: '${urlAdmin}/excluirFormasPagto/' + parametro.value,			 
			 success: function(){				 
				 location.reload();     	    
			 },
			 error: function(jqXHR, textStatus, errorThrown) {				 
				 $('#msgRetornoConfirmExclusaoParamErro').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
			 }
		 });
}

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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.formas.pagto"/> </h2>                                                      
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                        <div class="col-md-12">                        		

                            <div class="panel rounded shadow">
                                
                                <div class="panel-body no-padding">                                    
                                                                       
                                </div><!-- /.panel-body -->                                
                            </div><!-- /.panel -->                         
                      
                      		<c:if test="${ empty listaFormasPagto }">
                                 	
                                 	<div class="panel" align="center">	                                
		                                <div class="panel-body">
		                                    <spring:message code="msg.lista.forma.pagto.vazio"/>
		                                </div><!-- /.panel-body -->
		                            </div><!-- /.panel -->
                             </c:if>
                      
                            <!-- Start basic color table -->
                            
                           <c:if test="${ not empty listaFormasPagto }"> 
                            <div class="panel">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.admin.lista.forma.pagto"/></h3>
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
                                            	<th class="text-center"><spring:message code="lbl.admin.data.cadastro.forma.pagto"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.nome.forma.pagamento.resumido"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.label.pagamento.resumido"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.taxa.pagamento"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.desc.pagamento"/></th>                                               
                                                
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="parametro" items="${listaFormasPagto}" >
	                                            <tr>
	                                                <td class="text-center"> <fmt:formatDate value='${parametro.dataCriacao}' pattern='dd/MM/yyyy'/></td>
	                                                <td class="text-center">${parametro.nome}</td>	                                                
	                                                <td class="text-center">${parametro.label}</td>
	                                                <td class="text-center"> <fmt:formatNumber value='${parametro.taxaValor}' pattern="#,##0.00;-0"/></td>
													<td class="text-center">${parametro.descricao}</td>
													
													<td class="text-center">
														  <a href="${urlAdmin}/prepararAtualizacaoFormasPagto/${parametro.id}" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> <spring:message code="lbl.admin.link.atualizar.forma.pagto"/></a>
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
         
            </section><!-- /#page-content -->
          
        </section><!-- /#wrapper -->
        
         <!-- Start optional size modal element - confirmacao exclusao forma pagamento-->
            <div id="idModalConfirmacaoExclusaoParam" class="modal fade bs-example-modal-lg-confirmacao-exclusao-parametro-inicial" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdParametro" readonly="readonly" name="modIdParametro">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.exclusao.forma.pagto"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.exclusao.forma.pagto"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="confirmarExclusaoFormaPagamento();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoConfirmExclusaoParamErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao exclusao forma pagamento -->   


        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
       
  		<c:import url="../layout-admin/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>