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


function prepararModalDetalhesParametro(id){
	var parametro1 = id;
	 $.ajax({
         type: 'GET',         
         url: '${urlAdmin}/carregaModalDetalhesParametroInicial/' + parametro1,         
         dataType: 'json',
         success: function(data){       	  	
	       	  $("#modalDetalhesParametro").modal("show");
	       	  $("#modNome").val(data.nome);
	       	  $("#modLabel").val(data.label);
	       	  $("#modTipoParam").val(data.tipoParam);	       	  
	       	  $("#modValorQuantidade").val(data.valorQuantidade);
	       	  $("#modValorHabilita").val(data.valorHabilita);         	  
         },
         error: function(jqXHR, textStatus, errorThrown) {
             alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
         }
     });  	
}

function prepararModalConfirmaExclusao(id){
	$("#modIdParametro").val(id);
	$('#msgRetornoConfirmExclusaoParamErro').html("");	
	$("#idModalConfirmacaoExclusaoParam").modal("show");	
}

function confirmarExclusaoParametro(){	
	var parametro = document.getElementById("modIdParametro");	
	$.ajax({
			 url: '${urlAdmin}/confirmarExcluirParametroInicial/' + parametro.value,			 
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.parametros.iniciais"/> </h2>                                                      
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
                            
                             <c:if test="${msgSucesso != null }">
			               		 <div class="panel panel-success">
			                          <div class="panel-heading">
			                              <h3 class="panel-title">${msgSucesso}</h3>
			                          </div><!-- /.panel-heading -->			                                                    
			                      </div><!-- /.panel -->                      
			                </c:if>				                
                         
                      
                      		<c:if test="${ empty listaParametrosIniciais }">
                                 	
                                 	<div class="panel" align="center">	                                
		                                <div class="panel-body">
		                                    <spring:message code="msg.lista.param.iniciais.vazio"/>
		                                </div><!-- /.panel-body -->
		                            </div><!-- /.panel -->
                             </c:if>
                      
                            <!-- Start basic color table -->
                            
                           <c:if test="${ not empty listaParametrosIniciais }"> 
                            <div class="panel">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.admin.lista.param.iniciais"/></h3>
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
                                            	<th class="text-center"><spring:message code="lbl.admin.param.iniciais.data.cadastro"/></th>                                                
                                                <th class="text-center"><spring:message code="lbl.admin.param.iniciais.nome"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.param.iniciais.label"/></th>
												<th class="text-center"><spring:message code="lbl.admin.param.iniciais.tipo"/></th>												                                                
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="parametro" items="${listaParametrosIniciais}" >
	                                            <tr>
	                                                <td class="text-center"> <fmt:formatDate value='${parametro.dataCadastro}' pattern='dd/MM/yyyy'/></td>	                                                
	                                                <td class="text-center">${parametro.nome}</td>	                                                
	                                                <td class="text-center">${parametro.label}</td>
	                                                <td class="text-center"><c:if test="${parametro.tipoParam == 'N'}">
																				<spring:message code="lbl.admin.param.iniciais.tipo.numerico"/>
																			</c:if>
																			<c:if test="${parametro.tipoParam == 'C'}">
																				<spring:message code="lbl.admin.param.iniciais.tipo.caractere"/>
																			</c:if>
													</td>	                                                
													
													<td class="text-center"> 
														  <a href="#" onClick="prepararModalDetalhesParametro(${parametro.id})" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> <spring:message code="lbl.admin.link.ver.param.inicial"/></a>														  
														  <a href="${urlAdmin}/prepararEditarParametrosInicial/${parametro.id}" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> <spring:message code="lbl.btn.editar.geral"/></a>														  
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
        
        <!-- START Modal - Detalhes Parametro Inicial -->             
            <div id="modalDetalhesParametro" class="modal fade bs-example-modal-detalhes-parametro-inicial" role="dialog" aria-hidden="true" >              
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.admin.detalhes.param.iniciais"/></h4> 
                        </div>
                        
                        <div class="modal-body no-padding">
                            <form name="parametrosIniciaisForm" class="form-horizontal form-bordered" role="form" action="${urlAdmin}/adicionarParametrosInicial">                            
                            	  <div class="form-body">
                                
                                	<div class="form-group">                                	
                                        <label for="modNome" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.nome"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modNome" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                    
                                    <div class="form-group">
                                        <label for="modLabel" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.label"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modLabel" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                	
                                    <div class="form-group">
                                        <label for="modTipoParam" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.tipo"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modTipoParam" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                                                     
                                    <div class="form-group">
                                        <label for="modValorQuantidade" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.valor.quantidade"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modValorQuantidade" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                    
                                    <div class="form-group">
                                        <label for="modValorHabilita" class="col-sm-3 control-label"><spring:message code="lbl.admin.param.iniciais.valor.habilita"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modValorHabilita" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                  
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>                                        
                                    </div>
                                </div><!-- /.form-footer -->  
                            </form>
                                                          
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->               
            </div><!-- /.modal -->              

        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
       
  		<c:import url="../layout-admin/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>