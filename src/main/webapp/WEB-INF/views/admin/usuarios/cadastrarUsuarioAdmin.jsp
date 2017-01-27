<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/admin" var="urlAdmin"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>
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
    		    $(comboLinha).trigger("chosen:updated");
    		}
    	});	

    	
    	function recuperaCidades(){
    	    var parametro1 = $("#idEstado").val();
    	    $.ajax({
                type: 'GET',
                url: '${urlBuscarCidades}/' + parametro1,
                dataType: 'json',
                success: function(json){
                    var options = "";
                    $.each(json, function(key, value){
                       $("#idCidade").append("<option value='"+value.key+"'>"+value.label+"</option>");
                    }); 
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                }
            });    	   
    	}

    	function recuperaBairros(){   
    	    var parametro1 = $("#idCidade").val();
    	    $.ajax({
                type: 'GET',
                url: '${urlBuscarBairros}/' + parametro1,
                dataType: 'json',
                success: function(json){
                    var options = "";
                    $.each(json, function(key, value){
                    	$("#idBairro").append("<option value='"+value.key+"'>"+value.label+"</option>");
                    });  
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
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
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.cadastro.usuario.admin"/> </h2>                                                                        
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
					<form:form id="usuarioForm" modelAttribute="usuarioForm" action="${urlAdmin}/cadastrarUsuarioAdmin" class="form-horizontal mt-10" enctype="multipart/form-data">
                     	
                     <div class="row"> 	
                     	<c:if test="${msgErro != null }">
		               		 <div class="panel panel-danger">
		                          <div class="panel-heading">
		                              <h3 class="panel-title"><spring:message code="msg.cadastro.usuario.erro"/></h3>
		                          </div><!-- /.panel-heading -->		                          
		                      </div><!-- /.panel -->                      
		                </c:if>	
		                
		                <c:if test="${msgSucesso != null }">
			               		 <div class="panel panel-success">
			                          <div class="panel-heading">
			                              <h3 class="panel-title"><spring:message code="msg.cadastro.usuario.sucesso"/></h3>
			                          </div><!-- /.panel-heading -->			                                                    
			                      </div><!-- /.panel -->                      
			                </c:if>	
			                
		                
                     	<!--/ INICIO ABA FOTO PRINCIPAL IMOVEL -->                                 
         					<c:if test="${msgSucesso == null }"> 	          		
		                   	   <div class="col-md-12" align="center">
		                            <!-- Start horizontal form -->
		                            <div class="panel rounded shadow">
		                                <div class="panel-heading">  
		                                    <div class="pull-left">
		                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.alterar.foto.usuario.principal"/> <code></code></h3>
		                                    </div>
		                                    <div class="pull-right">
		                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
		                                    </div>
		                                    <div class="clearfix"></div>
		                                </div><!-- /.panel-heading -->
		                                <div class="panel-body no-padding">
		                                      <div class="form-body">                                      
													
												<form:hidden path="id" value="${usuarioForm.id}"/>
												<div class="form-group">
		                                               <label class="control-label"></label>
		                                               <div class="fileinput fileinput-new" data-provides="fileinput">
		                                                   <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px;"></div>
		                                                   <div>
		                                                       <span class="btn btn-info btn-file"><span class="fileinput-new">Selecionar Foto</span><span class="fileinput-exists">Selecionar Foto</span>	                                                        
		                                                       <input type="text" name="name"/>
															<input type="file" name="file"/>
		                                                       
		                                                       </span>
		                                                       <a href="#" class="btn btn-danger fileinput-exists" data-dismiss="fileinput">Remover</a>
		                                                   </div>
		                                               </div>
		                                               <form:errors id="fotoPrincipal" path="fotoPrincipal" cssClass="errorEntrada"  />
		                                           </div><!-- /.form-group -->
		
		                                      </div>
		                               </div>
		                           </div>
		                       </div>
		                       <!--/ FIM ABA FOTO PRINCIPAL IMOVEL -->
                    	
                    	<!--/ INICIO ABA INFORMACOES BASICAS -->
                    	<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.cad.informacoes.basicas"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="nome" class="col-sm-3 control-label"><spring:message code="lbl.nome.usuario"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input id="nome" path="nome" class="form-control"/>
                                                    <form:errors id="nome" path="nome" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="login" class="col-sm-3 control-label"><spring:message code="lbl.login"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input id="login" path="login" class="form-control" />
                                                    <form:errors id="login" path="login" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for=sexo class="col-sm-3 control-label"><spring:message code="lbl.sexo"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:select id="sexo" path="sexo" class="form-control">                                
														<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                       
														<form:option value="M"><spring:message code="lbl.sexo.masculino"/></form:option>                       
														<form:option value="F"><spring:message code="lbl.sexo.feminino"/></form:option>
													 </form:select>	
													 <form:errors id="sexo" path="sexo" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for=diaNascimento class="col-sm-3 control-label"><spring:message code="lbl.data.nascimento"/></label>
                                                <div class="col-sm-7">
	                                                	<div class="col-sm-2">  
															  <form:select id="diaNascimento" path="diaNascimento" class="form-control">										                 
												                 <form:options items="${usuarioForm.listaDiaNascimento}" itemValue="key" itemLabel="label"/>
													          </form:select>
													    </div>
												    	<div class="col-sm-3">      
												          <form:select id="mesNascimento" path="mesNascimento" class="form-control">										                 
											                 <form:options items="${usuarioForm.listaMesNascimento}" itemValue="key" itemLabel="label"/>
												          </form:select>												          
												    	</div>      
												    	<div class="col-sm-2">      
												          <form:select id="anoNascimento" path="anoNascimento" class="form-control">										                 
											                 <form:options items="${usuarioForm.listaAnoNascimento}" itemValue="key" itemLabel="label"/>
												          </form:select>												          
												     	</div>
												    <form:errors id="dataNascimento" path="dataNascimento" cssClass="errorEntrada"  /> 
                                                </div>
                                            </div><!-- /.form-group -->
                                                                                              
                                                                                      
                                            <div id="lblCPF" class="form-group">
                                                <label for=cpf class="col-sm-3 control-label"><spring:message code="lbl.cpf"/></label>
                                                <div class="col-sm-7">                                                    
                                                    <form:input  id="cpf" path="cpf" class="form-control"/>
                                                    <form:errors id="cpf" path="cpf" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->                                                                                      
                                            
                                        </div><!-- /.form-body -->   

                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
						<!--/ FIM ABA INFORMACOES BASICAS -->
						
						<!--/ INICIO ABA PASSWORD -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.password"/>  <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                			<div class="form-group">
                                                <label for="password" class="col-sm-3 control-label"><spring:message code="lbl.password"/> </label>
                                                <div class="col-sm-7">                                                    
                                                    <form:password id="password" path="password" class="form-control"/>
                                                    <form:errors id="password" path="password" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="password" class="col-sm-3 control-label"><spring:message code="lbl.confirma.password"/> </label>
                                                <div class="col-sm-7">                                                    
                                                    <form:password id="confirmaPassword" path="confirmaPassword" class="form-control"/>
                                                    <form:errors id="confirmaPassword" path="confirmaPassword" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->                        

                                		</div><!-- /.panel-body -->
                            	</div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        
                        <!--/ FIM ABA PASSWORD -->
						
						<!--/ INICIO ABA LOCALIZACAO -->
						<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.localizacao.usuario"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="idEstado" class="col-sm-3 control-label"><spring:message code="lbl.estado"/></label>
                                                <div class="col-sm-7">
                                                    <form:select id="idEstado" path="idEstado" class="form-control">
										                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${usuarioForm.listaEstados}" itemValue="key" itemLabel="label"/>
											        </form:select>
											        <form:errors id="idEstado" path="idEstado" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="idCidade" class="col-sm-3 control-label"><spring:message code="lbl.cidade"/></label>
                                                <div class="col-sm-7">
                                                    <form:select id="idCidade" path="idCidade" class="form-control">
										                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${usuarioForm.listaCidades}" itemValue="key" itemLabel="label"/>
											        </form:select>
											        <form:errors id="idCidade" path="idCidade" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="idBairro" class="col-sm-3 control-label"><spring:message code="lbl.bairro"/></label>
                                                <div class="col-sm-7">
                                                     <form:select id="idBairro" path="idBairro" class="form-control">
										                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${usuarioForm.listaBairros}" itemValue="key" itemLabel="label"/>
											        </form:select>
											        <form:errors id="idBairro" path="idBairro" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->               
                                        </div><!-- /.form-body -->
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        <!--/ FIM ABA LOCALIZACAO -->
                        
                        <!--/ INICIO ABA CONTATO PESSOAL -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.contato.pessoal"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="email" class="col-sm-3 control-label"><spring:message code="lbl.email"/></label>
                                                <div class="col-sm-7">
                                                    <form:input id="email" path="email" class="form-control"/>
                                                    <form:errors id="email" path="email" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="telefone" class="col-sm-3 control-label"><spring:message code="lbl.telefone"/></label>
                                                <div class="col-sm-7">
                                                    <form:input id="telefone" path="telefone" class="form-control"/>
                                                </div>
                                            </div><!-- /.form-group -->
                                                      
                                        </div><!-- /.form-body -->   
                                        
                                        <div class="form-footer">  
                                            <div class="col-sm-offset-3">
                                                <button type="submit" class="btn btn-success"><spring:message code="lbl.btn.confirmar.dados.geral"/></button>
                                            </div>
                                        </div><!-- /.form-footer -->    
                                         
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        <!--/ FIM ABA CONTATO PESSOAL -->
                        </c:if>                        
                        </div><!-- /.row -->
                        </form:form> 
                      </div>
                   

                </div><!-- /.body-content -->
                <!--/ End body content -->
 				
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../layout-admin/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>