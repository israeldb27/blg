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

function prepararModalDetalhesPlano(id){
	var parametro1 = id;
	 $.ajax({
         type: 'GET',         
         url: '${urlAdmin}/carregaModalDetalhesPlano/' + parametro1,         
         dataType: 'json',
         success: function(data){       	  	
	       	  $("#modalDetalhesPlano").modal("show");
	       	  $("#modNome").val(data.nome);
	       	  $("#modDuracaoPlano").val(data.duracaoPlano);
	       	  $("#modValorPlano").val(data.valorPlano);	       	  
	       	  $("#modQuantCadastroImoveis").val(data.quantCadastroImoveis);
	       	  $("#modQuantFotos").val(data.quantFotos);
	       	  $("#modQuantImoveisIndicacao").val(data.quantImoveisIndicacao);
	       	  $("#modQuantEmailsPorImovel").val(data.quantEmailsPorImovel);
	       	  $("#modHabilitaEnvioMensagens").val(data.habilitaEnvioMensagens);
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
			 url: '${urlAdmin}/confirmarExcluirPlano/' + parametro.value,			 
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.planos"/> </h2>                                                      
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
                      
                      		<c:if test="${ empty listaPlanos }">
                                 	
                                <div class="panel" align="center">	                                
		                                <div class="panel-body">
		                                    <spring:message code="msg.lista.servicos.vazio"/>
		                                </div><!-- /.panel-body -->
		                         </div><!-- /.panel -->
                             </c:if>
                      
                            <!-- Start basic color table -->
                            
                           <c:if test="${ not empty listaPlanos }"> 
                            <div class="panel">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.admin.link.lista.planos"/></h3>
                                    </div>
                                    <div class="pull-right">
                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                 
                                <div class="panel-body no-padding">                                 	
                                 
                                    <div class="table-responsive" style="margin-top: -1px;">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>                                            	
                                                <th class="text-center"><spring:message code="lbl.admin.nome.plano.resum"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.duracao.plano"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.valor.plano"/></th>                                                                                                
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="plano" items="${listaPlanos}" >
	                                            <tr>
	                                                <td class="text-center"> ${plano.nome}</td>
	                                                <td class="text-center">${plano.duracaoPlano}</td>	                         
	                                                <td class="text-center"><fmt:formatNumber value="${plano.valorPlano}" pattern="#,##0.00;-0"/></td>
	                                                
													<td class="text-center">
														  <a href="#" onClick="prepararModalDetalhesPlano(${plano.id})" ><i class="fa fa-eye"></i> </a> &nbsp;&nbsp;
														  <a href="${urlAdmin}/manterPlano/atualizar/${plano.id}" ><i class="fa fa-pencil-square-o"></i> </a>														  
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
            <div id="modalDetalhesPlano" class="modal fade bs-example-modal-detalhes-plano" role="dialog" aria-hidden="true" >              
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.admin.detalhes.plano"/></h4> 
                        </div>
                        
                        <div class="modal-body no-padding">
                            <form name="planoForm" class="form-horizontal form-bordered" role="form" action="${urlAdmin}/adicionarParametrosInicial">                            
                            	  <div class="form-body">
                                
                                	<div class="form-group">                                	
                                        <label for="modNome" class="col-sm-3 control-label"><spring:message code="lbl.admin.nome.plano"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modNome" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                    
                                    <div class="form-group">
                                        <label for="modDuracaoPlano" class="col-sm-3 control-label"><spring:message code="lbl.admin.duracao.plano"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modDuracaoPlano" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                	
                                    <div class="form-group">
                                        <label for="modValorPlano" class="col-sm-3 control-label"><spring:message code="lbl.admin.valor.plano"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modValorPlano" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                                                     
                                    <div class="form-group">
                                        <label for="modQuantCadastroImoveis" class="col-sm-3 control-label"><spring:message code="lbl.admin.quant.imoveis.plano"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modQuantCadastroImoveis" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                    
                                    <div class="form-group">
                                        <label for="modQuantFotos" class="col-sm-3 control-label"><spring:message code="lbl.admin.quant.fotos.imoveis.plano"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modQuantFotos" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                    
                                    
                                    <div class="form-group">
                                        <label for="modQuantImoveisIndicacao" class="col-sm-3 control-label"><spring:message code="lbl.admin.quant.indicacoes.plano"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modQuantImoveisIndicacao" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                    
                                     <div class="form-group">
                                        <label for="modQuantEmailsPorImovel" class="col-sm-3 control-label"><spring:message code="lbl.admin.quant.emails.indicacoes.plano"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modQuantEmailsPorImovel" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                    
                                     <div class="form-group">
                                        <label for="modHabilitaEnvioMensagens" class="col-sm-3 control-label"><spring:message code="lbl.admin.habilita.sol.envio.msgs"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modHabilitaEnvioMensagens" readonly="readonly">
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
            <!-- END Modal - Detalhes Imoveis -->

        <!-- Start optional size modal element - confirmacao exclusao parametro inicial -->
            <div id="idModalConfirmacaoExclusaoParam" class="modal fade bs-example-modal-lg-confirmacao-exclusao-parametro-inicial" tabindex="-1" role="dialog" aria-hidden="true">
	            <input type="hidden" id="modIdParametro" readonly="readonly" name="modIdParametro">
	                <div class="modal-dialog modal-lg">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                            <h4 class="modal-title"><spring:message code="lbl.modal.confirmar.exclusao.plano"/></h4>
	                        </div>
	                        <div class="modal-body">
	                            <p><spring:message code="lbl.modal.pergunta.confirma.exclusao.plano"/></p>
	                        </div>
	                        <div class="modal-footer">
	                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.nao"/></button>
	                            <button type="button" class="btn btn-theme" onClick="confirmarExclusaoParametro();"><spring:message code="lbl.sim"/></button>                            
	                        </div>
							
							<div id="msgRetornoConfirmExclusaoParamErro" cssClass="errorEntrada"  ></div>   
							
	                    </div><!-- /.modal-content -->
	                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
         <!-- End optional size modal element - confirmacao exclusao parametro inicial -->  

        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
       
  		<c:import url="../layout-admin/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>