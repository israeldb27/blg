[]<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/usuario/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/mensagem" var="urlMensagem"/>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>   
 
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
		$("#buscarUsuariosForm").submit();      
	 });	

   function limpaComboLinha(comboLinha) {
	    $(comboLinha).empty();  
	    $(comboLinha).append('<option value="-1" >' + "<spring:message code='opcao.selecao.uma.opcao'/>" + '</option>');
	    $(comboLinha).trigger("chosen:updated");
	}
});	

function recuperaCidades(){
    var parametro1 = $("#idEstado").val();
    $.get("${urlBuscarCidades}/"+parametro1, function(data){
        jQuery.each(data, function(key, value) {
            if(value.label == null){            	
                return;
            }            
            $("#idCidade").append("<option value='"+value.key+"'>"+value.label+"</option>");
        });
    });
}

function recuperaBairros(){
    var parametro1 = $("#idCidade").val();
    $.get("${urlBuscarBairros}/"+parametro1, function(data){
        jQuery.each(data, function(key, value) {
            if(value.label == null){            	
                return;
            }            
            $("#idBairro").append("<option value='"+value.key+"'>"+value.label+"</option>");
        });
    });
}

function enviarConvite(id) {
	
	var parametro1 = id;
    $.ajax({        
        url: '${urlContato}/enviarConvite/' + parametro1,        
        success: function(){
        	$("#idConvite_"+id).hide();    	
        	$("#idConvidado_"+id).show();      
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
    });	    
  }
  
function mostrarModal(id){
	
	if (id == 0){    			
		$('#msgModalFunc').html("<spring:message code='msg.aba.foto.principal.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.alterar.foto.usuario.principal'/>");
	}
	else if ( id == 1){        			
		$('#msgModalFunc').html("<spring:message code='msg.aba.informacoes.basicas.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.cad.informacoes.basicas'/>");
	}
	else if ( id == 2){  
		$('#msgModalFunc').html("<spring:message code='msg.aba.informacoes.password.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.password'/>");
	}
	else if ( id == 3){
		$('#msgModalFunc').html("<spring:message code='msg.aba.informacoes.localizacao.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.localizacao.usuario'/>");
	}
	else if ( id == 4){
		$('#msgModalFunc').html("<spring:message code='msg.aba.informacoes.localizacao.contato'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.contato.pessoal'/>");
	}
	else if ( id == 5){
		$('#msgModalFunc').html("<spring:message code='msg.aba.informacoes.permissoes.usuario'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.permissao.busca.usuario'/>");
	}
	
	$("#idModalItem").modal("show");
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
            <section id="page-content">

            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.link.editar.usuario"/> </h2>  
                </div><!-- /.header-content -->
                
                <c:if test="${msgSucesso != null }">
                	 <div class="alert alert-success">
                           <strong><spring:message code="msg.edicao.usuario.sucesso"/></strong> 
                      </div>    
               </c:if>   
               <c:if test="${msgErro != null }">
               		 <div class="alert alert-danger">
                             <strong><spring:message code="msg.edicao.usuario.erro"/></strong> 
                      </div>         
               </c:if>	
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                   <form:form id="usuarioForm" modelAttribute="usuarioForm" action="${urlUsuario}/editarUsuario" class="form-horizontal mt-10">
                     <div class="row">
                     	
                     	<!--/ INICIO ABA FOTO PRINCIPAL -->
                     	<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.alterar.foto.perfil.atual"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);"><i class="fa fa-question" ></i></a> 
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                      <div class="form-body">
                                      	<div class="form-group">                                              
                                              <div class="col-sm-12" align="center">
                                                  <ul class="list-unstyled">
				                                        <li class="text-center">	
				                                            <a href="#aboutModal" data-toggle="modal" data-target="#idModalFoto"> <img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${usuarioForm.imagemArquivo}" style="width: 200px; height: 200px; " alt="Foto Principal"> </a>
				                                        </li>
				                                        <li class="text-center">
				                                        	<br>
				                                        	<em><spring:message code="lbl.clique.editar.foto"/></em> 
				                                        </li>
				                                   </ul>     	
                                              </div>
                                          </div><!-- /.form-group -->
                                      </div>
                               </div>
                           </div>
                       </div>  
                       <!--/ FIM ABA FOTO PRINCIPAL -->	
                     	
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
											
											<div class="form-group">
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
                                            
                                              <div class="form-group">
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
													 <form:errors id="sexo" path="sexo" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                                  
                                            <c:if test="${(usuarioForm.perfil != 'imobiliaria')}">                                          
	                                            <div class="form-group">
	                                                <label for="cnpj" class="col-sm-3 control-label"><spring:message code="lbl.cpf"/></label>
	                                                <div class="col-sm-7">                                                    
	                                                    <spring:message code="lbl.hint.usuario.cpf" var="hintCpf"/>                                              
                                                    	<form:input  id="cpf" path="cpf" class="form-control" title="${hintCpf}"/>
	                                                    <form:errors id="cpf" path="cpf" cssClass="errorEntrada"  />
	                                                </div>
	                                            </div><!-- /.form-group -->
                                            </c:if>
                                            
                                            <c:if test="${(usuarioForm.perfil == 'I')}">
	                                            <div class="form-group">
	                                                <label for="cnpj" class="col-sm-3 control-label"><spring:message code="lbl.cnpj"/></label>
	                                                <div class="col-sm-7">                                                    
	                                                    <spring:message code="lbl.hint.usuario.cnpj" var="hintCnpj"/>                                                   
                                                    	<form:input  id="cnpj" path="cnpj" class="form-control" title="${hintCnpj}"/>
	                                                    <form:errors id="cnpj" path="cpf" cssClass="errorEntrada"  />
	                                                </div>
	                                            </div><!-- /.form-group -->
                                            </c:if>
                                            
                                            <c:if test="${(usuarioForm.perfil == 'C')}">
	                                             <div class="form-group">
	                                                <label for="creci" class="col-sm-3 control-label"><spring:message code="lbl.creci"/></label>
	                                                <div class="col-sm-7">                                                    
	                                                    <spring:message code="lbl.hint.usuario.creci" var="hintCreci"/>                                                 
                                                    	<form:input id="creci" path="creci" class="form-control" title="${hintCreci}"/>
	                                                    <form:errors id="creci" path="creci" cssClass="errorEntrada"  />
	                                                </div>
	                                            </div><!-- /.form-group -->
                                            </c:if>
                                            
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
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        <!--/ FIM ABA CONTATO PESSOAL -->
                        
                        <!--/ INICIO ABA PERMISSOES -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.permissoes.usuario"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(5);"><i class="fa fa-question" ></i></a>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="idPermBuscaUsuario" class="col-sm-3 control-label"><spring:message code="lbl.permissao.busca.usuario"/></label>
                                                <div class="col-sm-7">
                                                     <spring:message code="lbl.hint.usuario.habilitaBusca" var="hintHabilitaBusca"/>
                                                     <form:select id="habilitaBusca" path="habilitaBusca" class="form-control" title="${hintHabilitaBusca}">                                
								                    	<form:option value="" > <spring:message code="opcao.selecao.uma.opcao"/> </form:option>
														<form:option value="S"> <spring:message code="lbl.sim"/></form:option>
														<form:option value="N"> <spring:message code="lbl.nao"/></form:option>														
									                 </form:select>
									                 <form:errors id="habilitaBusca" path="habilitaBusca" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="habilitaDetalhesInfoUsuario" class="col-sm-3 control-label"><spring:message code="lbl.permissao.exibir.detalhes.usuario"/></label>
                                                <div class="col-sm-7">
                                                     <spring:message code="lbl.hint.usuario.habilitaDetalhesInfo" var="hintDetalhesInfo"/>
                                                     <form:select id="habilitaDetalhesInfoUsuario" path="habilitaDetalhesInfoUsuario" class="form-control" title="${hintDetalhesInfo}">                                
								                    	<form:option value="" > <spring:message code="opcao.selecao.uma.opcao"/> </form:option>
														<form:option value="S"> <spring:message code="lbl.sim"/></form:option>
														<form:option value="N"> <spring:message code="lbl.nao"/></form:option>														
									                 </form:select>
									                 <form:errors id="habilitaDetalhesInfoUsuario" path="habilitaDetalhesInfoUsuario" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                              <div class="form-group">
                                                <label for="habilitaRecebeSeguidor" class="col-sm-3 control-label"><spring:message code="lbl.permissao.receber.seguidor"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.usuario.habilitaRecSeguidor" var="hintRecSeguidor"/>
                                                     <form:select id="habilitaRecebeSeguidor" path="habilitaRecebeSeguidor" class="form-control" title="${hintRecSeguidor}">
														<form:option value="S"> <spring:message code="lbl.sim"/></form:option>
														<form:option value="N"> <spring:message code="lbl.nao"/></form:option>														
									                 </form:select>
									                 <form:errors id="habilitaRecebeSeguidor" path="habilitaRecebeSeguidor" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                        </div><!-- /.form-body -->     
                                         
                                        <div class="form-footer">  
                                            <div class="col-sm-offset-3">
                                            	<spring:message code="lbl.hint.confirmar.geral" var="hintConfirmarDados"/>
                                                <button type="submit" class="btn btn-success" title="${hintConfirmarDados}"><spring:message code="lbl.btn.confirmar.dados.geral"/></button>
                                            </div>
                                        </div><!-- /.form-footer -->                        

                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        
                        <!--/ FIM ABA PERMISSOES -->
                        </div><!-- /.row -->
                       </form:form> 
                            
                </div><!-- /.body-content -->
                <!--/ End body content -->        
         
            </section><!-- /#page-content -->
          
				<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
				<!-- End content  modal Ajuda - funcionalidade -->
				
			 <!-- Start optional size modal element - item 1 -->
            <div id="idModalFoto" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <form:form id="usuarioForm" modelAttribute="usuarioForm" action="${urlUsuario}/editarFotoPrincipal" class="form-horizontal mt-5" enctype="multipart/form-data" >
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><div id="msgModal"  ></h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                            	
                                    <label class="control-label"></label>
                                    <div class="fileinput fileinput-new" data-provides="fileinput">
                                        <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px; margin-left: 330px;"></div>
                                        <div style="margin-left: 330px;">
                                            <span class="btn btn-info btn-file"><span class="fileinput-new"><spring:message code="lbl.selecionar.foto.imovel"/></span><span class="fileinput-exists"><spring:message code="lbl.selecionar.foto.imovel"/></span>	                                                        
                                            <input type="text" name="name" id="name"/>
											<input type="file" name="file" id="file" />                                            
                                            </span>
                                            <a href="#" class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><spring:message code="lbl.remover.foto.imovel"/></a>
                                        </div>
                                    </div>
                                
                              </div><!-- /.form-group -->	
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success" ><spring:message code="lbl.btn.editar.geral"/></button> 
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>                                                                                  
                        </div>
						 </form:form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
				
			    <!-- Start optional size modal element - item 1 -->
            <div id="idModalItem" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				          <h4 class="modal-title"><div id="msgModalFuncionalidade" > </div> </h4>
				        </div>
				        <div class="modal-body">  
				       	   <strong> <spring:message code="lbl.descricao.geral"/>:  </strong> <div id="msgModalFunc" > </div>
				        </div>
				        <div class="modal-footer">			          
	                      <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>
				        </div>
				      </div>
				    </div>
				</div>
            </div><!-- /.modal -->

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

</html>