<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>

<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>

<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
    
    	<script type="text/javascript">
    	$(document).ready(function() {   
    		
    		$('#idEstado').change(function () {    			
    	        var comboPai = '#idEstado';
    	        var comboFilha = '#idCidade';
    	        var comboFilha2 = '#idBairro';
    	        limpaComboLinha(comboFilha);
    	        limpaComboLinha(comboFilha2);
    	        recuperaCidades();
    	    });
    		
    		$('#idCidade').change(function () {
    			var comboPai   = '#idCidade';
    			var comboFilha = '#idBairro';
    			limpaComboLinha(comboFilha);
    			recuperaBairros();		
    		 });    		

    		$('#opcaoOrdenacao').change(function () {				
    			$("#meusImoveisForm").submit();      
    		 });

    	    function limpaComboLinha(comboLinha) {
    	        $(comboLinha).empty();
    	        $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');        
    	    }
    	});	

    	
    	function mostrarModal(id) {    		
    		if (id == 0){
    			$('#msgModal').html("Descrição resumida sobre o imóvel");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.descricao.imovel'/>");
    		}
    		else if ( id == 1){
    			$('#msgModal').html("Repasse suas informações básicas");
    		}
    		else if ( id == 2){
    			$('#msgModal').html("Informe o seu password");
    		}
    		else if ( id == 3){
    			$('#msgModal').html("Repasse informações de sua localização");
    		}
    		else if ( id == 4){
    			$('#msgModal').html("Repasse suas informações de contato");
    		}
    		else if ( id == 5){
    			$('#msgModal').html("Selecione suas permissões sobre sua conta");
    		}
    		
    		$("#idModalItem").modal("show");
    	}
    	
		</script>
		
        <c:import url="../../layout/cadastroUsuario/head-layout.jsp"></c:import>

    <!--/ END HEAD -->
   
    <body>

        <!-- INICIO - Cadastro Usuario -->
            
            <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.link.esqueceu.senha"/> </h2>                                                                        
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                	  
                   <form:form id="usuarioForm" modelAttribute="usuarioForm" action="${urlUsuario}/enviarEmailTemporario" class="form-horizontal mt-10" enctype="multipart/form-data">
                     	
                     <div class="row"> 	
                     
		               <c:if test="${msgErro != null }">
		               		 <div class="alert alert-danger">
		                            <strong>${msgErro}</strong> 
		                      </div>         
		               </c:if>	
                    	
						<!--/ INICIO ABA INFORMAR EMAIL -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
	                                <div class="panel-heading">  
	                                    <div class="pull-left">
	                                        <h3 class="panel-title"><spring:message code="lbl.title.informe.senha"/>  <code></code></h3>
	                                    </div>
	                                    <div class="pull-right">
	                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);"><i class="fa fa-question" ></i></a>                                        
	                                    </div>
	                                    <div class="clearfix"></div>
	                                </div><!-- /.panel-heading -->
	                                <div class="panel-body no-padding">
	
	                                        <div class="form-body">
	                                        
	                                			<div class="form-group">
	                                                <label for="emailEsqueceu" class="col-sm-3 control-label"><spring:message code="lbl.email"/> </label>
	                                                <div class="col-sm-4">                       
	                                                    <form:input id="emailEsqueceu" path="emailEsqueceu" class="form-control" />
	                                                    <form:errors id="emailEsqueceu" path="emailEsqueceu" cssClass="errorEntrada"  />
	                                                </div>
	                                            </div><!-- /.form-group -->
	                                            
	                                            <div class="form-group">
	                                                <label for="cpfCnpjEsqueceuSenha" class="col-sm-3 control-label"><spring:message code="lbl.cpf.cnpj.esqueceu.senha"/> </label>
	                                                <div class="col-sm-4">                       
	                                                    <form:input id="cpfCnpjEsqueceuSenha"  path="cpfCnpjEsqueceuSenha" class="form-control" />
	                                                    <form:errors id="cpfCnpjEsqueceuSenha" path="cpfCnpjEsqueceuSenha" cssClass="errorEntrada"  />
	                                                </div>
	                                            </div><!-- /.form-group -->                                                
	
	                                		</div><!-- /.panel-body -->
	                                		
	                                		<div class="form-footer">  
				                              <div class="col-sm-offset-3">                              	
				                              	<spring:message code="lbl.hint.confirmar.geral" var="hintConfirmaDados"/>
				                                  <button type="submit" class="btn btn-success" title="${hintConfirmaDados}"><spring:message code="lbl.btn.confirmar.dados.geral"/></button>
				                              </div>
				                          </div><!-- /.form-footer -->
	                            	</div><!-- /.panel -->
	                          
	                            <!--/ End horizontal form -->	                            
	                        </div>	                        
                        </div>
                        <!--/ FIM ABA INFORMAR EMAIL -->   
                          
                      </div>
                   </form:form> 
                            
                </div><!-- /.body-content -->
                <!--/ End body content -->
             
			 	<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
				<!-- End content  modal Ajuda - funcionalidade -->			
				
		<!-- Start optional size modal element - item 1 -->
            <div id="idModalItem" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				          <h4 class="modal-title"><div id="msgModalFuncionalidade" > </div> </h4>
				        </div>
				        <div class="modal-body">  
				       	   <strong> <spring:message code="lbl.descricao.geral"/>:  </strong> <div id="msgModal" > </div>
				        </div>
				        <div class="modal-footer">			          
	                      <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>
				        </div>
				      </div>
				    </div>
				</div>
            </div><!-- /.modal -->
			
         <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->
    </body>

</html>