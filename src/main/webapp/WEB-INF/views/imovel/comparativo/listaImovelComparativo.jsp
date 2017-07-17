<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url var="urlImovelFavoritos" value="/imovelFavoritos"/>
<spring:url var="urlImovelComparativo" value="/imovelComparativo"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   
  
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

    	<script type="text/javascript">
    	$(document).ready(function() {    		
    		
    	});	
    	
    	function carregaDetalhesImovel(id){    		
    		var parametro1 = id;    		 
      	    $.ajax({
                  type: 'GET',
                  url: '${urlImovel}/carregaModalDetalhesImovel/' + parametro1,
                  dataType: 'json',
                  success: function(data){                	  	
                	  $("#myModal").modal("show");
                	  $("#modTituloImovel").val(data.titulo);
                	  $("#modEstado").val(data.estado);
                	  $("#modCidade").val(data.cidade);
                	  $("#modBairro").val(data.bairro);
                	  $("#modDataCadastroImovel").val(data.dataCadastroFmt);
                	  $("#modTipoImovel").val(data.tipoImovelFmt);
                	  $("#modAcaoImovel").val(data.acaoFmt);                	  
                  },
                  error: function(jqXHR, textStatus, errorThrown) {
                      alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                  }
              });  
    	}
    	
    	
    	function preparaConfirmaExclusao(id) {
    		$("#idModalConfirmarExcComparativo").modal("show");    
    		$("#modIdImovel").val(id);
    	}    	
    	 
   		function confirmarExclusao() {
    		
    	    var parametro1 = $("#modIdImovel").val();
    	    alert($("#modIdImovel").val());
      	    $.ajax({
                  type: 'GET',
                  url: '${urlImovelComparativo}/excluirImovelComparativo/' + parametro1,
                  dataType: 'json',
                  success: function(data){
                	  if ( data == 'ok') {
        	        		 location.reload();
                	  } 		 
                  },
                  error: function(jqXHR, textStatus, errorThrown) {
                      alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                  }
              });  
   		}    
    

    	function adicionarInteresse(id) {    	
    		var parametro1 = id;
    	    $.ajax({                
                url: '${urlImovelFavoritos}/adicionarFavoritos/' + parametro1,                
                success: function(){
                	$("#idMeInteressei_"+id).hide();
	    	    	$("#idInteressado_"+id).show();
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });   
    	}
    	
    	function adicionarComparativo(id) {    	
    		var parametro1 = id;
    	    $.ajax({                
                url: '${urlImovelComparativo}/adicionarImovelComparativo/' + parametro1,                
                success: function(){
                	
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });   
    	}
    	        	
		</script>
		
        <c:import url="../../layout/head-layout.jsp"></c:import>
   
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../../layout/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../../layout/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">                                   
					
					  <h2>
                    	<i class="fa fa-pencil"></i> <spring:message code="lbl.aba.title.comparativo"/> 
                    	 <div class="pull-right">	                                                            
	                    	 <a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" style="font-size: 15px;"></i></a>	
	                     </div>	
                    </h2>
				
                </div><!-- /.header-content -->
                <!--/ End header content -->   
                
                <c:choose>
                	<c:when test="${ empty listaMeusComparativos }">
                	<br>
              	 		 <div class="alert alert-success" align="center" >
                               <strong><spring:message code="msg.lista.comparativo.vazio"/></strong>   
                          </div>	
                	</c:when>
                	
                	<c:when test="${ not empty listaMeusComparativos }">
                		   <div class="body-content animated fadeIn">
			                    <div class="row">
			                        <div class=col-md-19"> 
			                        	<div class="table-responsive mb-20">
			                                <table class="table table-hover">
			                                    <thead>
			                                        <tr>  
			                                            <th class="text-center"></th>                                            
			                                            <th class="text-center"><spring:message code="lbl.comparativo.col.tipo.imovel"/></th>
			                                            <th class="text-center"><spring:message code="lbl.comparativo.col.acao.imovel"/></th>
			                                            <th class="text-center"><spring:message code="lbl.comparativo.col.valor.imovel"/></th>
			                                            <th class="text-center"><spring:message code="lbl.comparativo.col.area.imovel"/> m<sup>2</sup></th>
			                                            <th class="text-center"><spring:message code="lbl.comparativo.col.status.imovel"/></th>
			                                            <th class="text-center"><spring:message code="lbl.comparativo.col.quartos.imovel"/></th>
			                                            <th class="text-center"><spring:message code="lbl.comparativo.col.garagem.imovel"/></th>			                                            
			                                            <th class="text-center"><spring:message code="lbl.comparativo.col.banheiro.imovel"/></th>
			                                            <th></th>			                                            
			                                        </tr>
			                                    </thead>
			                                    <tbody>
			                                    	<c:forEach var="imovel" items="${listaMeusComparativos}" varStatus="item">
			                                    		<tr>
				                                            <td class="text-center">
				                                            	<a href="${urlImovel}/detalhesImovel/${imovel.id}" >
				                                            		<img src="data:image/jpeg;base64,${imovel.imagemArquivo}"  style="width: 80px; height: 60px; " />
				                                            	</a>
				                                            </td>	                                            
				                                            <td class="text-center">${imovel.tipoImovelFmt} </td>
				                                            <td class="text-center">${imovel.acaoFmt} </td>
															<td class="text-center"><fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></td>
															<td class="text-center"><fmt:formatNumber value="${imovel.area}" pattern="##00;-0"/></td>
															<td class="text-center">${imovel.perfilImovelFmt} </td>												
															<td class="text-center">${imovel.quantQuartos} </td>
															<td class="text-center">${imovel.quantGaragem} </td>
															<td class="text-center">${imovel.quantBanheiro} </td>
															<td class="text-center"><button type="button" id="btnModal2" onClick="preparaConfirmaExclusao(${imovel.id})"  class="btn btn-danger btn-xs" data-toggle="modal" > <i class="fa fa-times"></i>  </button></td>
				                                        </tr>	                                        
			                                    	</c:forEach>                                    
			                                    </tbody>
			                                </table>
			                            </div><!-- /.table-responsive -->
			                            <!--/ End basic table -->
			
			                        </div>			                                                        
		                        </div>  		                                              
		                    </div><!-- /.row -->
                	</c:when>                	
                </c:choose>             

            </section><!-- /#page-content -->
            
                 <!-- Start content modal Usuario Detalhes-->
			<c:import url="../../ajuda/imovelDetalhesModal.jsp"></c:import>																				
		<!-- End content  modal Usuario Detalhes -->
			
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
		
        </section><!-- /#wrapper -->
  
             <!-- Start optional size modal element - comparativo de imoveis -->            
            <div id="idModalConfirmarExcComparativo" class="modal fade bs-example-modal-lg-comparativo" tabindex="-1" role="dialog" aria-hidden="true">
				<div class="modal-dialog">
			      <div class="modal-content">
			        <div class="modal-header">
			          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			          <h4 class="modal-title">Confirmação</h4>
			        </div>
			        <div class="modal-body">
			          <spring:message code="lbl.modal.excluir.imovel.comparativo"/>
			        </div>
			        <div class="modal-footer">
			          <button type="button" onClick="confirmarExclusao();" class="btn" data-dismiss="modal"><spring:message code="lbl.btn.confirmar.geral"/></button>
                      <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>
			        </div>
			      </div>
			    </div>
			</div>
        

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body> 
