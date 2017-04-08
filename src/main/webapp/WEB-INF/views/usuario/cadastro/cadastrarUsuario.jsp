<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>

<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>

<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
    
    	<script type="text/javascript">
    	$(document).ready(function() {
    		
    		var perfilSel = document.getElementById("perfil").value; 
    		
    		if ( perfilSel == ''){
    			$('#cpf').hide();
        		$('#lblCPF').hide();	
        		$('#lblCNPJ').hide();
        		$('#creci').hide();	
        		$('#lblCRECI').hide();	
        		$('#lblSexo').hide();
        		$('#lblFaixaSalarial').hide();
        		$('#lblDataNascimento').hide();        		
    		}
    		else if ( perfilSel == 'P' ){
				$('#cpf').show();
				$('#lblCPF').show();			
				$('#lblCNPJ').hide();
				$('#creci').hide();
				$('#lblCRECI').hide();	
				$('#lblSexo').show();
	    		$('#lblFaixaSalarial').show();
	    		$('#lblDataNascimento').show();
			}
			else if (perfilSel == 'C'){
				$('#cpf').show();
				$('#lblCPF').show();			
				$('#lblCNPJ').hide();
				$('#creci').show();
				$('#lblCRECI').show();
				$('#lblSexo').show();
	    		$('#lblFaixaSalarial').show();
	    		$('#lblDataNascimento').show();
			}
			else if (perfilSel == 'I'){
				$('#cpf').hide();
				$('#lblCPF').hide();			
				$('#lblCNPJ').show();
				$('#creci').hide();
				$('#lblCRECI').hide();		
				$('#lblSexo').hide();
	    		$('#lblFaixaSalarial').hide();
	    		$('#lblDataNascimento').hide();
			}		
    		
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
    		
    		$('#perfil').change(function () {				
    			var perfil = document.getElementById("perfil").value;
    			if ( perfil == 'P' ){
    				$('#cpf').show();
    				$('#lblCPF').show();			
    				$('#lblCNPJ').hide();
    				$('#creci').hide();
    				$('#lblCRECI').hide();	
    				$('#lblSexo').show();
    	    		$('#lblFaixaSalarial').show();
    	    		$('#lblDataNascimento').show();
    			}
    			else if (perfil == 'C'){
    				$('#cpf').show();
    				$('#lblCPF').show();			
    				$('#lblCNPJ').hide();
    				$('#creci').show();
    				$('#lblCRECI').show();
    				$('#lblSexo').show();
    	    		$('#lblFaixaSalarial').show();
    	    		$('#lblDataNascimento').show();
    			}
    			else if (perfil == 'I'){
    				$('#cpf').hide();
    				$('#lblCPF').hide();			
    				$('#lblCNPJ').show();
    				$('#creci').hide();
    				$('#lblCRECI').hide();		
    				$('#lblSexo').hide();
    	    		$('#lblFaixaSalarial').hide();
    	    		$('#lblDataNascimento').hide();
    			}
    			else{
    				$('#cpf').hide();
    	    		$('#lblCPF').hide();	
    	    		$('#lblCNPJ').hide();
    	    		$('#creci').hide();	
    	    		$('#lblCRECI').hide();	
    	    		$('#lblDataNascimento').hide();
    			}
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
    	
    	function mostrarModal(id){
    		
    		if (id == 0){    			
    			$('#msgModal').html("<spring:message code='msg.aba.foto.principal.usuario'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.alterar.foto.usuario.principal'/>");
    		}
    		else if ( id == 1){        			
    			$('#msgModal').html("<spring:message code='msg.aba.informacoes.basicas.usuario'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.cad.informacoes.basicas'/>");
    		}
    		else if ( id == 2){  
    			$('#msgModal').html("<spring:message code='msg.aba.informacoes.password.usuario'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.password'/>");
    		}
    		else if ( id == 3){
    			$('#msgModal').html("<spring:message code='msg.aba.informacoes.localizacao.usuario'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.localizacao.usuario'/>");
    		}
    		else if ( id == 4){
    			$('#msgModal').html("<spring:message code='msg.aba.informacoes.localizacao.contato'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.contato.pessoal'/>");
    		}
    		else if ( id == 5){
    			$('#msgModal').html("<spring:message code='msg.aba.informacoes.permissoes.usuario'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.permissao.busca.usuario'/>");
    		}
    		
    		$("#idModalItem").modal("show");
    	}
    	
		</script>
		
        <c:import url="../../layout/cadastroUsuario/head-layout.jsp"></c:import>
	<%@ include file="/WEB-INF/views/layout/head-login.jsp"%>
    <!--/ END HEAD -->
   
    <body class="home">
    
	<div class="all">
		<div class="header">   
		    <div class="content">
			<h1 class="logo"><spring:message code="lbl.title.busque.um.lugar"/></h1>
		    </div>
		</div>
    	</div>
        <!-- INICIO - Cadastro Usuario -->
            
            <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.cadastro.usuario"/> </h2>
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                	  
                   <form:form id="usuarioForm" modelAttribute="usuarioForm" action="${urlUsuario}/avancarCadastro" class="form-horizontal mt-10" enctype="multipart/form-data">
                     	
                     <div class="row"> 	
                     
                     	 <c:if test="${msgErro != null }">
		               		 <div class="alert alert-danger">
		                            <strong><spring:message code="msg.cadastro.usuario.erro"/></strong> 
		                      </div>         
		                 </c:if>	
		                 
		                 <c:if test="${msgSucesso != null }">
                    	 		<div class="alert alert-success">
                                     <strong><spring:message code="msg.cadastro.usuario.sucesso"/></strong> 
                                </div>	                     	 
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
		                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);"><i class="fa fa-question" ></i></a>                                        
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
		                                                       <span class="btn btn-info btn-file"><span class="fileinput-new"><spring:message code="lbl.selecionar.foto.usuario"/></span><span class="fileinput-exists"><spring:message code="lbl.selecionar.foto.usuario"/></span>	                                                        
		                                                       <input type="text" name="name"/>
															<input type="file" name="file"/>
		                                                       
		                                                       </span>
		                                                       <a href="#" class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><spring:message code="lbl.remover.foto.usuario"/></a>
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
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(1);"><i class="fa fa-question" ></i></a>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="nome" class="col-sm-3 control-label"><spring:message code="lbl.nome.usuario"/></label>
                                                <div class="col-sm-7">   
                                                	<spring:message code="lbl.hint.usuario.nome" var="hintNome"/>                                                                                                 
                                                    <form:input id="nome" path="nome" class="form-control" title="${hintNome}"/>
                                                    <form:errors id="nome" path="nome" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="login" class="col-sm-3 control-label"><spring:message code="lbl.login"/></label>
                                                <div class="col-sm-7">                                           
                                                	<spring:message code="lbl.hint.usuario.login" var="hintLogin"/>         
                                                    <form:input id="login" path="login" class="form-control" title="${hintLogin}"/>
                                                    <form:errors id="login" path="login" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="perfil" class="col-sm-3 control-label"><spring:message code="lbl.perfil.usuario"/></label>
                                                <div class="col-sm-7">  
                                                	<spring:message code="lbl.hint.usuario.perfil" var="hintPerfil"/>                                                  
                                                    <form:select id="perfil" path="perfil" class="form-control" title="${hintPerfil}">                                
														<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                       
														<form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
													 </form:select>	
													 <form:errors id="perfil" path="perfil" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            
                                            <div id="lblSexo" class="form-group">
                                                <label for=sexo class="col-sm-3 control-label"><spring:message code="lbl.sexo"/></label>
                                                <div class="col-sm-7">   
                                                	<spring:message code="lbl.hint.usuario.sexo" var="hintSexo"/>                                                 
                                                    <form:select id="sexo" path="sexo" class="form-control" title="${hintSexo}">                                
														<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                       
														<form:option value="M"><spring:message code="lbl.sexo.masculino"/></form:option>                       
														<form:option value="F"><spring:message code="lbl.sexo.feminino"/></form:option>
													 </form:select>	
													 <form:errors id="sexo" path="sexo" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div id="lblFaixaSalarial" class="form-group">
                                                <label for=faixaSalarial class="col-sm-3 control-label"><spring:message code="lbl.faixa.salarial"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.usuario.faixa.salarial" var="hintFaixaSalarial"/>                                                    
                                                    <form:select id="faixaSalarial" path="faixaSalarial" class="form-control" title="${hintFaixaSalarial}">                                
														<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>                       
														<form:option value="1"><spring:message code="lbl.faixa.salarial.1"/></form:option>                       
														<form:option value="2"><spring:message code="lbl.faixa.salarial.2"/></form:option>
														<form:option value="3"><spring:message code="lbl.faixa.salarial.3"/></form:option>
														<form:option value="4"><spring:message code="lbl.faixa.salarial.4"/></form:option>
														<form:option value="5"><spring:message code="lbl.faixa.salarial.5"/></form:option>
														<form:option value="6"><spring:message code="lbl.faixa.salarial.6"/></form:option>
														<form:option value="7"><spring:message code="lbl.faixa.salarial.7"/></form:option>
														<form:option value="8"><spring:message code="lbl.faixa.salarial.8"/></form:option>														
													 </form:select>	
													 <form:errors id="faixaSalarial" path="faixaSalarial" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div id="lblDataNascimento" class="form-group">
                                                <label for=diaNascimento class="col-sm-3 control-label"><spring:message code="lbl.data.nascimento"/></label>
                                                <div class="col-sm-7">
                                                		<spring:message code="lbl.hint.usuario.data.nascimento" var="hintDataNascimento"/>
	                                                	<div class="col-sm-2">  
															  <form:select id="diaNascimento" path="diaNascimento" class="form-control" title="${hintDataNascimento}">										                 
												                 <form:options items="${usuarioForm.listaDiaNascimento}" itemValue="key" itemLabel="label"/>
													          </form:select>
													    </div>
												    	<div class="col-sm-3">      
												          <form:select id="mesNascimento" path="mesNascimento" class="form-control" title="${hintDataNascimento}">										                 
											                 <form:options items="${usuarioForm.listaMesNascimento}" itemValue="key" itemLabel="label"/>
												          </form:select>												          
												    	</div>      
												    	<div class="col-sm-2">      
												          <form:select id="anoNascimento" path="anoNascimento" class="form-control" title="${hintDataNascimento}">										                 
											                 <form:options items="${usuarioForm.listaAnoNascimento}" itemValue="key" itemLabel="label"/>
												          </form:select>												          
												     	</div>
												    <form:errors id="dataNascimento" path="dataNascimento" cssClass="errorEntrada"  /> 
                                                </div>
                                            </div><!-- /.form-group -->
                                                                                              
                                                                                      
                                            <div id="lblCPF" class="form-group">
                                                <label for=cpf class="col-sm-3 control-label"><spring:message code="lbl.cpf"/></label>
                                                <div class="col-sm-7">      
                                                	<spring:message code="lbl.hint.usuario.cpf" var="hintCpf"/>                                              
                                                    <form:input  id="cpf" path="cpf" class="form-control" title="${hintCpf}"/>
                                                    <form:errors id="cpf" path="cpf" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                           
                                            <div id="lblCNPJ" class="form-group">
                                                <label for="cnpj" class="col-sm-3 control-label"><spring:message code="lbl.cnpj"/></label>
                                                <div class="col-sm-7"> 
                                                	<spring:message code="lbl.hint.usuario.cnpj" var="hintCnpj"/>                                                   
                                                    <form:input  id="cnpj" path="cnpj" class="form-control" title="${hintCnpj}"/>
                                                    <form:errors id="cnpj" path="cnpj" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->     
                                           
                                             <div id="lblCRECI" class="form-group">
                                                <label for="creci" class="col-sm-3 control-label"><spring:message code="lbl.creci"/></label>
                                                <div class="col-sm-7">   
                                                	<spring:message code="lbl.hint.usuario.creci" var="hintCreci"/>                                                 
                                                    <form:input id="creci" path="creci" class="form-control" title="${hintCreci}"/>
                                                    <form:errors id="creci" path="creci" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                             <div class="form-group">
                                                <label for="descSobreMim" class="col-sm-3 control-label"><spring:message code="lbl.sobre.mim"/></label>
                                                <div class="col-sm-7">  
                                                	<spring:message code="lbl.hint.usuario.sobre.mim" var="hintSobreMim"/>  
                                                    <form:textarea  id="descSobreMim" path="descSobreMim" class="form-control" rows="5" title="${hintSobreMim}"/>
                                                    <form:errors id="descSobreMim" path="descSobreMim" cssClass="errorEntrada"  />
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
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(2);"><i class="fa fa-question" ></i></a>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                			<div class="form-group">
                                                <label for="password" class="col-sm-3 control-label"><spring:message code="lbl.password"/> </label>
                                                <div class="col-sm-7">    
                                                	<spring:message code="lbl.hint.usuario.password" var="hintPassword"/>                                                
                                                    <form:password id="password" path="password" class="form-control" title="${hintPassword}"/>
                                                    <form:errors id="password" path="password" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="password" class="col-sm-3 control-label"><spring:message code="lbl.confirma.password"/> </label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.usuario.confirm.password" var="hintConfirmPassword"/>                                                    
                                                    <form:password id="confirmaPassword" path="confirmaPassword" class="form-control" title="${hintConfirmPassword}"/>
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
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(3);"><i class="fa fa-question" ></i></a>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="idEstado" class="col-sm-3 control-label"><spring:message code="lbl.estado"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.usuario.estado" var="hintEstado"/>
                                                    <form:select id="idEstado" path="idEstado" class="form-control" title="${hintEstado}">
										                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${usuarioForm.listaEstados}" itemValue="key" itemLabel="label"/>
											        </form:select>
											        <form:errors id="idEstado" path="idEstado" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="idCidade" class="col-sm-3 control-label"><spring:message code="lbl.cidade"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.usuario.cidade" var="hintCidade"/>
                                                    <form:select id="idCidade" path="idCidade" class="form-control" title="${hintCidade}">
										                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${usuarioForm.listaCidades}" itemValue="key" itemLabel="label"/>
											        </form:select>
											        <form:errors id="idCidade" path="idCidade" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="idBairro" class="col-sm-3 control-label"><spring:message code="lbl.bairro"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.usuario.bairro" var="hintBairro"/>
                                                     <form:select id="idBairro" path="idBairro" class="form-control" title="${hintBairro}">
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
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(4);"><i class="fa fa-question" ></i></a>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="email" class="col-sm-3 control-label"><spring:message code="lbl.email"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.usuario.email" var="hintEmail"/>
                                                    <form:input id="email" path="email" class="form-control" title="${hintEmail}"/>
                                                    <form:errors id="email" path="email" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="telefone" class="col-sm-3 control-label"><spring:message code="lbl.telefone"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.usuario.telefone" var="hintTelefone"/>
                                                    <form:input id="telefone" path="telefone" class="form-control" title="${hintTelefone}"/>
                                                </div>
                                            </div><!-- /.form-group -->
                                                      
                                        </div><!-- /.form-body --> 
                                        
                                        
                                        <div class="form-footer">  
                                            <div class="col-sm-offset-3">
                                            	
                                                <button id="btnSubmitAdd" type="submit" class="btn btn-primary btn-block" style="width: 30%;"><spring:message code="lbl.btn.confirmar.dados.geral"/></button>
                                            </div>
                                        </div><!-- /.form-footer --> 
                                           
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        <!--/ FIM ABA CONTATO PESSOAL -->
                   
                        </c:if>                   
                        </div><!-- /.row -->
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