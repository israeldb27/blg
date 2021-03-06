<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.busqueumlugar.enumerador.TipoImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.AcaoImovelSemCompraEnum"%>
<%@page import="com.busqueumlugar.enumerador.StatusImovelEnum"%>
<%@page import="com.busqueumlugar.enumerador.QuemPodeEnviarSolicitacoesEnum"%>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<spring:url value="/imovel/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/imovel/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.maskMoney.js"></script>

<c:set var="listaTipoImovel" value="<%= TipoImovelEnum.values() %>"/>
<c:set var="listaAcaoImovel" value="<%= AcaoImovelSemCompraEnum.values() %>"/>
<c:set var="listaStatusImovel" value="<%= StatusImovelEnum.values() %>"/>
<c:set var="listaQuemPodeEnviarSolicitacoesEnum" value="<%= QuemPodeEnviarSolicitacoesEnum.values() %>"/>

<script>
	function mascara(o,f){   
          v_obj=o   
          v_fun=f   
          setTimeout("execmascara()",1)   
      }   

      function execmascara(){   
          v_obj.value=v_fun(v_obj.value)   
      }   

      function moeda(v){   
          v=v.replace(/\D/g,""); // permite digitar apenas numero   
          v=v.replace(/(\d{1})(\d{15})$/,"$1.$2") // coloca ponto antes dos ultimos digitos   
          v=v.replace(/(\d{1})(\d{11})$/,"$1.$2") // coloca ponto antes dos ultimos 11 digitos   
          v=v.replace(/(\d{1})(\d{8})$/,"$1.$2") // coloca ponto antes dos ultimos 8 digitos   
          v=v.replace(/(\d{1})(\d{5})$/,"$1.$2") // coloca ponto antes dos ultimos 5 digitos   
          v=v.replace(/(\d{1})(\d{1,2})$/,"$1,$2") // coloca virgula antes dos ultimos 2 digitos   
          return v;   
      } 
</script>
 
 <script type="text/javascript">
    	$(document).ready(function() {
    		
    		var autorizacao = document.getElementById("autorizacaoOutroUsuario").value; 
    		if ( autorizacao == 'S'){
    			$('#labelCondParceria').show();
        		$('#labelCondIntermediacao').show();
    		}
    		else {
    			$('#labelCondParceria').hide();
        		$('#labelCondIntermediacao').hide();
    		}
    		
    		$('#autorizacaoOutroUsuario').change(function () {				
    			var autorizacaoOutroUsuario = document.getElementById("autorizacaoOutroUsuario").value; 
    		
    			if ( autorizacaoOutroUsuario == 'S'){
        			$('#labelCondParceria').show();
            		$('#labelCondIntermediacao').show();
        		}
        		else {
        			$('#labelCondParceria').hide();
            		$('#labelCondIntermediacao').hide();
        		}
    		 });
    		
    		$('.spinner .btn:first-of-type').on('click', function() {
  		      var btn = $(this);
  		      var input = btn.closest('.spinner').find('input');
  		      if (input.attr('max') == undefined || parseInt(input.val()) < parseInt(input.attr('max'))) {    
  		        input.val(parseInt(input.val(), 10) + 1);
  		      } else {
  		        btn.next("disabled", true);
  		      }
  		    });
  		    $('.spinner .btn:last-of-type').on('click', function() {
  		      var btn = $(this);
  		      var input = btn.closest('.spinner').find('input');
  		      if (input.attr('min') == undefined || parseInt(input.val()) > parseInt(input.attr('min'))) {    
  		        input.val(parseInt(input.val(), 10) - 1);
  		      } else {
  		        btn.prev("disabled", true);
  		      }
  		    });
    		
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
    	
    	function mostrarModal(id){
    		
    		if (id == 0){    			
    			$('#msgModal').html("<spring:message code='lbl.desc.seleciona.foto.principal.imovel'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.alterar.foto.imovel.nova'/>");
    		}
    		else if ( id == 1){
    			$('#msgModal').html("<spring:message code='lbl.desc.localizacao.imovel'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.imovel.localizacao'/>");    			
    		}
    		else if ( id == 2){
    			$('#msgModal').html("<spring:message code='lbl.desc.informacoes.basica.imovel'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.imovel.info.basica'/>");  
    		}
    		else if ( id == 3){
    			$('#msgModal').html("<spring:message code='lbl.desc.informacoes.caracteristica.imovel'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.imovel.caracteristicas'/>");  
    		}
    		else if ( id == 4){
    			$('#msgModal').html("<spring:message code='lbl.desc.informacoes.permissoes.imovel'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.imovel.permissoes'/>");  
    		}    		
    		
    		else if ( id == 5){
    			$('#msgModal').html("<spring:message code='lbl.permissao.imovel.destaque.msg.desc'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.permissao.imovel.destaque'/>");  
    		}

    		else if ( id == 6){
    			$('#msgModal').html("<spring:message code='lbl.permissao.aceita.financiamento.msg.desc'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.permissao.aceita.financiamento'/>");  
    		}

    		else if ( id == 7){
    			$('#msgModal').html("<spring:message code='lbl.permissao.aceita.parceria.msg.desc'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.permissao.aceita.parceria'/>");  
    		}
			
    		else if ( id == 8){
    			$('#msgModal').html("<spring:message code='lbl.condicao.parceria.msg.desc'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.condicao.parceria'/>");  
    		}
					
    		else if ( id == 9){
    			$('#msgModal').html("<spring:message code='lbl.permissao.aceita.intermediacao.msg.desc'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.permissao.aceita.intermediacao'/>");  
    		}
			
    		else if ( id == 10){
    			$('#msgModal').html("<spring:message code='lbl.condicao.intermediacao.msg.desc'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.condicao.intermediacao'/>");  
    		}
    		else if ( id == 11 ){
    			$('#msgModal').html("<spring:message code='lbl.permissao.quem.pode.solicitacoes.parceria.msg.desc'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.permissao.quem.pode.solicitacoes'/>");  
    		}
    		else if ( id == 12 ){
    			$('#msgModal').html("<spring:message code='lbl.permissao.quem.pode.solicitacoes.intermediacao.msg.desc'/>");
    			$('#msgModalFuncionalidade').html("<spring:message code='lbl.permissao.quem.pode.solicitacoes'/>");  
    		}  
    		
    		$("#idModalItem").modal("show");
    	}
    	
		</script>
		
        <c:import url="../../layout/head-layout.jsp"></c:import>
        
        <style type="text/css">
.spinner input {
  text-align: left;
}

.input-group-btn-vertical {
  position: relative;
  white-space: nowrap;
  width: 2%;
  vertical-align: middle;
  display: table-cell;
}

.input-group-btn-vertical > .btn {
  display: block;
  float: none;
  width: 100%;
  max-width: 100%;
  padding: 8px;
  margin-left: -1px;
  position: relative;
  border-radius: 0;
}

.input-group-btn-vertical > .btn:first-child {
  border-top-right-radius: 4px;
}

.input-group-btn-vertical > .btn:last-child {
  margin-top: -2px;
  border-bottom-right-radius: 4px;
}

.input-group-btn-vertical i {
  position: absolute;
  top: 0;
  left: 4px;
}
</style>
   
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
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.cadastro.imovel"/> </h2>                                                                        
							
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
               <div class="body-content animated fadeIn container limit-form" style="width:800px;">

                   <form:form id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/avancarCadastroImovel" class="form-horizontal mt-10" enctype="multipart/form-data">
                     <div class="row"> 	
                     	 <!--/ INICIO ABA FOTO PRINCIPAL IMOVEL -->   
                   	 		  <c:if test="${msgErro != null }">			               		 
			                       <div class="alert alert-danger">
                                          <strong><spring:message code="msg.cadastro.imovel.erro"/></strong> 
                                   </div>                 
			                  </c:if>                              
                   		
		                   	   <div class="col-md-12" align="center">
		                            <!-- Start horizontal form -->
		                            <div class="panel rounded shadow">
		                                <div class="panel-heading">  
		                                    <div class="pull-left">
		                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.alterar.foto.imovel.nova"/> <code></code></h3>
		                                    </div>
		                                    <div class="pull-right">
		                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);"><i class="fa fa-question" ></i></a>                                        
		                                    </div>
		                                    <div class="clearfix"></div>
		                                </div><!-- /.panel-heading -->
		                                <div class="panel-body no-padding">
		                                      <div class="form-body">                                      
													
												<form:hidden path="id" value="${imovelForm.id}"/>
												<div class="form-group">
		                                               <label class="control-label"></label>
		                                               <div class="fileinput fileinput-new" data-provides="fileinput">
		                                                   <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 190px; height: 130px;"></div>
		                                                   <div>  
		                                                       <span class="btn btn-info btn-file"><span class="fileinput-new"><spring:message code="lbl.selecionar.foto.imovel"/></span><span class="fileinput-exists"><spring:message code="lbl.selecionar.foto.imovel"/></span>	                                                        
		                                                       <input type="text" name="name"/>
															   <input type="file" name="file"/>
		                                                       
		                                                       </span>
		                                                       <a href="#" class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><spring:message code="lbl.remover.foto.imovel"/></a>
		                                                   </div>
		                                               </div>
		                                               <form:errors id="fotoPrincipal" path="fotoPrincipal" cssClass="errorEntrada"  />
		                                           </div><!-- /.form-group -->		
		                                      </div>
		                               </div>
		                           </div>
		                       </div>
		                       <!--/ FIM ABA FOTO PRINCIPAL IMOVEL -->
                     
                    	<!--/ INICIO ABA LOCALIZACAO -->
                    	<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.imovel.localizacao"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(1);"><i class="fa fa-question" ></i></a>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                            <div class="form-group">                                            	
                                                <label for="idEstado" class="col-sm-3 control-label"><spring:message code="lbl.estado"/></label>
                                                <div class="col-sm-7">
                                                <spring:message code="lbl.hint.imovel.estado" var="hintEstado"/>                                                    
                                                     <form:select id="idEstado" path="idEstado" class="form-control" title="${hintEstado}">
										                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${imovelForm.listaEstados}" itemValue="key" itemLabel="label"/>										                
											        </form:select>
											        <form:errors id="idEstado" path="idEstado" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="idCidade" class="col-sm-3 control-label"><spring:message code="lbl.cidade"/></label>
                                                <div class="col-sm-7">                                                    
                                                <spring:message code="lbl.hint.imovel.cidade" var="hintCidade"/>
                                                    <form:select id="idCidade" path="idCidade" class="form-control" title="${hintCidade}">
										                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${imovelForm.listaCidades}" itemValue="key" itemLabel="label"/>
										            </form:select>
										            <form:errors id="idCidade" path="idCidade" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                                                                      
                                            <div class="form-group">
                                                <label for="idBairro" class="col-sm-3 control-label"><spring:message code="lbl.bairro"/></label>
                                                <div class="col-sm-7">
                                                <spring:message code="lbl.hint.imovel.bairro" var="hintBairro"/>
                                                    <form:select id="idBairro" path="idBairro" class="form-control" title="${hintBairro}">
										                <form:option value="-1" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
										                <form:options items="${imovelForm.listaBairros}" itemValue="key" itemLabel="label"/>
										            </form:select>
										            <form:errors id="idBairro" path="idBairro" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">                                            	
                                                <label for="endereco" class="col-sm-3 control-label"><spring:message code="lbl.endereco"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.imovel.endereco" var="hintEndereco"/>                                                    
                                                    <form:input  id="endereco" path="endereco" class="form-control" title="${hintEndereco}"/>
                                                </div>
                                            </div><!-- /.form-group --> 
                                        </div><!-- /.form-body -->   
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
						<!--/ FIM ABA LOCALIZACAO -->
						
						<!--/ INICIO ABA INFORMACOES BASICAS -->
						<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.imovel.info.basica"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">                                        
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(2);"><i class="fa fa-question" ></i></a>
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                        <div class="form-body">
                                        
                                             <div class="form-group">
                                                <label for="titulo" class="col-sm-3 control-label"><spring:message code="lbl.titulo.imovel"/></label>
                                                <div class="col-sm-7">                                                    
                                                	<spring:message code="lbl.hint.imovel.titulo" var="hintTitulo"/>
                                                    <form:input  id="titulo" path="titulo" class="form-control" title="${hintTitulo}"/>
                                                    <form:errors id="titulo" path="titulo" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->   
                                            
                                            <div class="form-group">
                                                <label for="tipoImovel" class="col-sm-3 control-label"><spring:message code="lbl.tipo.imovel"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.imovel.tipo.imovel" var="hintTipoImovel"/>
                                                     <form:select id="tipoImovel" path="tipoImovel" class="form-control" title="${hintTipoImovel}">
										                <form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>	                        
														<form:options items="${listaTipoImovel}" itemValue="identificador" itemLabel="rotulo" />
										            </form:select>
										            <form:errors id="tipoImovel" path="tipoImovel" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="acao" class="col-sm-3 control-label"><spring:message code="lbl.acao.imovel"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.imovel.acao.imovel" var="hintAcaoImovel"/>
                                                	   <form:select id="acao" path="acao" class="form-control" title="${hintAcaoImovel}">	
															<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>
															<form:options items="${listaAcaoImovel}" itemValue="identificador" itemLabel="rotulo" />
														</form:select>
														<form:errors id="acao" path="acao" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="valorImovel" class="col-sm-3 control-label"><spring:message code="lbl.valor.imovel"/></label>
                                                <div class="col-sm-7">
                                                		<spring:message code="lbl.hint.imovel.valor" var="hintValorImovel"/>
                                                    	<form:input  id="valorImovelFmt" path="valorImovelFmt" onkeypress="formatarMoeda(this);" class="form-control" title="${hintValorImovel}"/>
                                                    	<form:errors id="valorImovelFmt" path="valorImovelFmt" cssClass="errorEntrada"  />                                                      	
                                                </div>
                                            </div><!-- /.form-group -->                                  
											
											<div class="form-group">
                                                <label for="valorCondominioFmt" class="col-sm-3 control-label"><spring:message code="lbl.valor.condominio"/></label>
                                                <div class="col-sm-7">
                                                		<spring:message code="lbl.hint.imovel.valor.condominio" var="hintValorCondominio"/>
	                                                    <form:input  id="valorCondominioFmt" path="valorCondominioFmt" onkeypress="formatarMoeda(this);" class="form-control" title="${hintValorCondominio}"/>
	                                                    <form:errors id="valorCondominioFmt" path="valorCondominioFmt" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group --> 
                                            
											<div class="form-group">
                                                <label for="valorIptuFmt" class="col-sm-3 control-label"><spring:message code="lbl.valor.iptu"/></label>
                                                <div class="col-sm-7">                                                	
                                                		<spring:message code="lbl.hint.imovel.valor.iptu" var="hintValorIptu"/>
	                                                    <form:input  id="valorIptuFmt" path="valorIptuFmt" class="form-control" onkeypress="formatarMoeda(this);" title="${hintValorIptu}"/>                                                    	
	                                                    <form:errors id="valorIptuFmt" path="valorIptuFmt" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group --> 
                                                            
                                            <div class="form-group">
                                                <label for="descricao" class="col-sm-3 control-label"><spring:message code="lbl.descricao.imovel"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.imovel.descricao" var="hintDescricao"/>                                                    
                                                    <form:textarea  id="descricao" path="descricao" class="form-control" rows="5" title="${hintDescricao}"/>
                                                    <form:errors id="descricao" path="descricao" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->  
                                                       
                                        </div><!-- /.form-body -->
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        <!--/ FIM ABA INFORMAÇÕES BASICAS -->
                        
                        <!--/ INICIO ABA CARACTERISTISCAS IMOVEL -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.imovel.caracteristicas"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(3);"><i class="fa fa-question" ></i></a>
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="perfilImovel" class="col-sm-3 control-label"><spring:message code="lbl.status.imovel"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.imovel.perfil.imovel" var="hintPerfilImovel"/>
                                                	   <form:select id="perfilImovel" path="perfilImovel" class="form-control" title="${hintPerfilImovel}">                                
									                    	<form:option value="" ><spring:message code="opcao.selecao.uma.opcao"/></form:option>   
															<form:options items="${listaStatusImovel}" itemValue="identificador" itemLabel="rotulo" />
										               </form:select>
										               <form:errors id="perfilImovel" path="perfilImovel" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                                <label for="area" class="col-sm-3 control-label"><spring:message code="lbl.area.m2.resum"/></label>
                                                <div class="col-sm-7">
                                                <spring:message code="lbl.hint.imovel.area" var="hintArea"/>
                                                    <form:input id="area" path="area" class="form-control" title="${hintArea}"/>
                                                    <form:errors id="area" path="area" cssClass="errorEntrada"  />
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                              <div class="form-group">
                                                <label for="quantQuartos" class="col-sm-3 control-label"><spring:message code="lbl.quartos.dormitorios"/></label>
                                                <div class="col-sm-7">   
                                                	<div class="input-group spinner">
                                                			<form:input id="quantQuartos" path="quantQuartos" class="form-control" min="0" max="20"/>                                                    
														    <div class="input-group-btn-vertical">
														      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>
														      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>
														    </div>                                                	
                                                	</div> 
                                                </div>
                                            </div><!-- /.form-group -->	 
                                            
                                            <div class="form-group">
                                                <label for="quantGaragem" class="col-sm-3 control-label"><spring:message code="lbl.vagas.garagem"/></label>
                                                <div class="col-sm-7">   
                                                	<div class="input-group spinner">
                                                			<form:input id="quantGaragem" path="quantGaragem" class="form-control" min="0" max="20"/>                                                    
														    <div class="input-group-btn-vertical">
														      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>
														      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>
														    </div>                                                	
                                                	</div> 
                                                </div>
                                            </div><!-- /.form-group -->	  
                                            
                                             <div class="form-group">
                                                <label for="quantBanheiro" class="col-sm-3 control-label"><spring:message code="lbl.banheiros"/></label>
                                                <div class="col-sm-7">   
                                                	<div class="input-group spinner">
                                                			<form:input id="quantBanheiro" path="quantBanheiro" class="form-control" min="0" max="20"/>                                                    
														    <div class="input-group-btn-vertical">
														      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>
														      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>
														    </div>                                                	
                                                	</div> 
                                                </div>
                                            </div><!-- /.form-group -->	  
                                            
                                            <div class="form-group">
                                                <label for="quantSuites" class="col-sm-3 control-label"><spring:message code="lbl.suites"/></label>
                                                <div class="col-sm-7">   
                                                	<div class="input-group spinner">
                                                			<form:input id="quantSuites" path="quantSuites" class="form-control" min="0" max="5"/>
                                                    
														    <div class="input-group-btn-vertical">
														      <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>
														      <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>
														    </div>
                                                	
                                                	</div>                                                 
                                                    
                                                </div>
                                            </div><!-- /.form-group -->	
                                                      
                                        </div><!-- /.form-body -->    
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                        <!--/ FIM ABA CARACTERISTISCAS IMOVEL -->
                        
                        <!--/ INICIO ABA PERMISSOES -->
                        <div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.imovel.permissoes"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <a href="#a" class="btn btn-sm"  onClick="mostrarModal(4);"><i class="fa fa-question" ></i></a>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">                                        
                                            <div class="form-group">
                                                <label for="destaque" class="col-sm-3 control-label"><spring:message code="lbl.permissao.imovel.destaque"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.imovel.destaque" var="hintImovelDestaque"/>
                                                     <form:select id="destaque" path="destaque" class="form-control" title="${hintImovelDestaque}">
														<form:option value="S"> <spring:message code="lbl.sim"/></form:option>
														<form:option value="N"> <spring:message code="lbl.nao"/></form:option>
									                 </form:select>
									                 <form:errors id="destaque" path="destaque" cssClass="errorEntrada"  />
                                                </div>
                                                
                                                   <a href="#a" class="btn btn-sm"  onClick="mostrarModal(5);" ><i class="fa fa-question-circle" ></i></a>
                                            </div><!-- /.form-group -->
                                                                                        
                                             <div class="form-group">
                                                <label for="aceitaFinanciamento" class="col-sm-3 control-label"><spring:message code="lbl.permissao.aceita.financiamento"/></label>
                                                <div class="col-sm-7">
                                                	<spring:message code="lbl.hint.imovel.aceita.financiamento" var="hintAceitaFinaciamento"/>
                                                     <form:select id="aceitaFinanciamento" path="aceitaFinanciamento" class="form-control" title="${hintAceitaFinaciamento}">
														<form:option value="S"> <spring:message code="lbl.sim"/></form:option>
														<form:option value="N"> <spring:message code="lbl.nao"/></form:option>														
									                 </form:select>
									                 <form:errors id="aceitaFinanciamento" path="aceitaFinanciamento" cssClass="errorEntrada"  />
                                                </div>
                                                   <a href="#a" class="btn btn-sm"  onClick="mostrarModal(6);" ><i class="fa fa-question-circle" ></i></a>
                                            </div><!-- /.form-group -->
                                            
                                            <c:if test="${(usuario.perfil != 'P')}">
	                                             <div class="form-group">
	                                                <label for="autorizacaoOutroUsuario" class="col-sm-3 control-label"><spring:message code="lbl.permissao.aceita.parceria"/></label>
	                                                <div class="col-sm-7">
	                                                	<spring:message code="lbl.hint.imovel.autoriza.parceria" var="hintAceitaParceria"/>
	                                                     <form:select id="autorizacaoOutroUsuario" path="autorizacaoOutroUsuario" class="form-control" title="${hintAceitaParceria}">
															<form:option value="S"> <spring:message code="lbl.sim"/></form:option>
															<form:option value="N"> <spring:message code="lbl.nao"/></form:option>														
										                 </form:select>
										                 <form:errors id="autorizacaoOutroUsuario" path="autorizacaoOutroUsuario" cssClass="errorEntrada"  />
	                                                </div>
	                                                   <a href="#a" class="btn btn-sm"  onClick="mostrarModal(7);" ><i class="fa fa-question-circle" ></i></a>
	                                            </div><!-- /.form-group -->
	                                            
	                                             <div class="form-group" id="labelCondParceria">
	                                                <label for="descAceitaCorretagemParceria" class="col-sm-3 control-label"><spring:message code="lbl.condicao.parceria"/></label>
	                                                <div class="col-sm-7">  
	                                                	<form:textarea rows="5" cols="20" id="descAceitaCorretagemParceria" path="descAceitaCorretagemParceria" class="form-control" />
                                                        <form:errors id="descAceitaCorretagemParceria" path="descAceitaCorretagemParceria" cssClass="errorEntrada"  />     
	                                                </div>
	                                                <a href="#a" class="btn btn-sm"  onClick="mostrarModal(8);" ><i class="fa fa-question-circle" ></i></a>
	                                            </div><!-- /.form-group -->
	                                            
	                                            <div class="form-group">
	                                                <label for="quemPodeEnviarSolicitacoes" class="col-sm-3 control-label"><spring:message code="lbl.permissao.quem.pode.solicitacoes"/></label>
	                                                <div class="col-sm-7">	                                                     
	                                                     <form:select id="quemPodeEnviarSolicitacoes" path="quemPodeEnviarSolicitacoes" class="form-control" >
											           		<form:options items="${listaQuemPodeEnviarSolicitacoesEnum}" itemValue="identificador" itemLabel="rotulo" />
											            </form:select>									                 
	                                                </div>
	                                                <form:errors id="quemPodeEnviarSolicitacoes" path="quemPodeEnviarSolicitacoes" cssClass="errorEntrada"  />
	                                                
	                                                <a href="#a" class="btn btn-sm"  onClick="mostrarModal(11);" ><i class="fa fa-question-circle" ></i></a>
	                                            </div><!-- /.form-group -->
                                            </c:if>
                                            
                                            <c:if test="${(usuario.perfil == 'P')}">
	                                             <div class="form-group">
	                                                <label for="autorizacaoOutroUsuario" class="col-sm-3 control-label"><spring:message code="lbl.permissao.aceita.intermediacao"/></label>
	                                                <div class="col-sm-7">
	                                                	<spring:message code="lbl.hint.imovel.autoriza.intermediacao" var="hintAceitaIntermediacao"/>
	                                                     <form:select id="autorizacaoOutroUsuario" path="autorizacaoOutroUsuario" class="form-control" title="${hintAceitaIntermediacao}">
															<form:option value="S"> <spring:message code="lbl.sim"/></form:option>
															<form:option value="N"> <spring:message code="lbl.nao"/></form:option>														
										                 </form:select>
										                 <form:errors id="autorizacaoOutroUsuario" path="autorizacaoOutroUsuario" cssClass="errorEntrada"  />
	                                                </div>
	                                                   <a href="#a" class="btn btn-sm"  onClick="mostrarModal(9);" ><i class="fa fa-question-circle" ></i></a>
	                                            </div><!-- /.form-group -->
	                                            
	                                             <div class="form-group" id="labelCondIntermediacao">
	                                                <label for="descAceitaCorretagemParceria" class="col-sm-3 control-label"><spring:message code="lbl.condicao.intermediacao"/></label>
	                                                <div class="col-sm-7">  
	                                                	<form:textarea rows="5" cols="20" id="descAceitaCorretagemParceria" path="descAceitaCorretagemParceria" class="form-control" />
                                                        <form:errors id="descAceitaCorretagemParceria" path="descAceitaCorretagemParceria" cssClass="errorEntrada"  />     
	                                                </div>
	                                                   <a href="#a" class="btn btn-sm"  onClick="mostrarModal(10);" ><i class="fa fa-question-circle" ></i></a>
	                                            </div><!-- /.form-group -->
	                                            
	                                            <div class="form-group">
	                                                <label for="quemPodeEnviarSolicitacoes" class="col-sm-3 control-label"><spring:message code="lbl.permissao.quem.pode.solicitacoes"/></label>
	                                                <div class="col-sm-7">	                                                     
	                                                     <form:select id="quemPodeEnviarSolicitacoes" path="quemPodeEnviarSolicitacoes" class="form-control" >
											           		<form:options items="${listaQuemPodeEnviarSolicitacoesEnum}" itemValue="identificador" itemLabel="rotulo" />
											            </form:select>									                 
	                                                </div>
	                                                <form:errors id="quemPodeEnviarSolicitacoes" path="quemPodeEnviarSolicitacoes" cssClass="errorEntrada"  />
	                                                
	                                                <a href="#a" class="btn btn-sm"  onClick="mostrarModal(12);" ><i class="fa fa-question-circle" ></i></a>
	                                            </div><!-- /.form-group -->
                                            </c:if>                                 
                                            
                                        </div><!-- /.form-body -->   
                                      
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
                       
                         <div class="form-footer">  
                              <div class="col-sm-offset-3">                              	
                              	<spring:message code="lbl.hint.confirmar.geral" var="hintConfirmaDados"/>
                                  <button type="submit" class="button btn-primary" title="${hintConfirmaDados}"><spring:message code="lbl.btn.confirmar.dados.geral"/></button>
                              </div>
                          </div><!-- /.form-footer -->   
                        
                        <!--/ FIM ABA CARACTERISTISCAS IMOVEL -->
                        
                        <!--/ FIM ABA PERMISSOES -->
                        </div><!-- /.row -->
                       </form:form> 
                            
                </div><!-- /.body-content -->
                <!--/ End body content -->
                
                <!-- Fim Editar Usuario -->          
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->    

				<!-- Start content modal Ajuda - funcionalidade -->
					<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
				<!-- End content  modal Ajuda - funcionalidade -->	

        </section><!-- /#wrapper -->

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
    <!--/ END BODY -->

</html>