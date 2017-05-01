<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/contato" var="urlContato"/>
<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/mensagem" var="urlMensagem"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/admin" var="urlAdmin"/>
<spring:url value="/mensagemAdmin" var="urlMensagemAdmin"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
   
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>
 
<script type="text/javascript">

$(document).ready(function() {

});	

function mostrarModal(id){	
	if (id == 0){
		$('#msgModal').html("Informação resumida sobre o usuário");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.sobre.mim'/>");		
	}
	else if ( id == 1){
		$('#msgModal').html("Lista de notas sobre ações do usuário");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.notas.usuario'/>");		
	}
	else if ( id == 2){
		$('#msgModal').html("Lista de preferências de imóveis do usuário");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.pref.imoveis'/>");
	}
	else if ( id == 3){
		$('#msgModal').html("Lista de contatos do usuário");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.contatos'/>");
	}
	else if ( id == 4){
		$('#msgModal').html("Lista de seguidores do usuário");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.lista.seguidores.detalhes.usuario'/>");
	}
	else if ( id == 5){
		$('#msgModal').html("Lista de recomendações feitas ao usuário");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.recomendacoes.detalhe.usuario'/>");
	}
	else if ( id == 6){
		$('#msgModal').html("Lista de serviços relacionados ao usuário");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.admin.lista.servicos.concedidos.revogados'/>");
	}
	else if ( id == 7){
		$('#msgModal').html("Lista de planos relacionados ao usuário");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.admin.lista.planos.concedidos.revogados'/>");
	}
	
	$("#idModalItem").modal("show");
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
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.detalhes.usuario.admin"/> </h2>                                                                        
                </div><!-- /.header-content -->
                                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                		<div class="row">
								<!-- START  -->
								<div class="col-lg-9 col-md-9 col-sm-13">
									<div class="panel rounded shadow">
										<div class="panel-heading" align="center">
		    								<span class="label pull-left"  style="font-size: 18px;margin-bottom:10px;background-color: black;font-size: 100%;">${usuarioForm.perfilFmt}</span>                                    
		    								<span class="label pull-right" style="margin-left: 14px;color:gray; font-size: 12px;"><spring:message code="lbl.data.cadastro.usuario"/> <fmt:formatDate value='${usuarioForm.dataCadastro}' pattern='dd/MM/yyyy'/></span>
											<h2 class="text-bold" style="margin-top: 0px;margin-bottom: 5px;width: 50%;">${usuarioForm.nome}</h2>
											<h5 style="margin-top: 4px;margin-bottom: 0px;width: 50%;">${usuarioForm.cidade} - ${usuarioForm.estado}</h5>
											<br>
		                                    <div class="pull-right">		                                     
													&nbsp;<a href="${urlAdmin}/visualizarImoveisPerfilUsuarioAdmin/${usuarioForm.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.visualizar.imoveis.perfil.usuario"/>' ><i class="fa fa-home pull-right" style="color:gray"><font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"> <spring:message code="lbl.visualizar.imoveis.perfil.usuario"/> </font> &nbsp; &nbsp; </i>  </a>
													
													&nbsp;<a href="${urlMensagemAdmin}/prepararNovaMensagemPorAdmin/${usuarioForm.id}" style="font-size:x-large; color: rgb(99, 110, 123);" class="meta-action" title='<spring:message code="lbl.enviar.mensagem"/>' ><i class="fa fa-envelope-o pull-right" style="color:gray"> <font style="color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;"><spring:message code="lbl.enviar.mensagem"/> </font>&nbsp;&nbsp; </i> </a>  
		                                    </div>
		                                    <br> <br>                                                                        
		
										</div>
										<div class="panel-body">
											<div class="pull-left">
												<div class="col-lg-6 col-md-6 col-sm-6"> 
			                                        <div id="owl-demo" class="owl-carousel owl-theme">                                             
			                                            <img class="img-circle" src="${context}/${usuarioForm.imagemArquivo}" style="margin-left:30px; width: 240px; height: 240px; ">	                                            	                                            	                                                                                        
			                                        </div>
			                                    </div>
		                                    </div>
		                                	
		                                	<div class="pull-right">
		                                		 <table class="table table-striped" style="margin-top:10px; font-size: 13px;">
		                                         <tbody>
		                                         
		                                         <c:choose>
		                                         	<c:when test="${((usuarioForm.perfil == 'P') || (usuarioForm.perfil == 'C')) }">
		                                         		  <tr>
			                                                 <td class="text-left"><spring:message code="lbl.cpf"/></td>
			                                                 <td class="text-right">${usuario.cpf}</td>
			                                              </tr>                                                     
		                                         	</c:when>
		                                         	
		                                        	<c:when test="${usuarioForm.perfil == 'I'}">
		                                       			<tr>
		                                                  <td class="text-left"><spring:message code="lbl.cnpj"/></td>
		                                                  <td class="text-right">${usuario.cpf}</td>
		                                               </tr>  	
		                                         	</c:when>
		                                         	
		                                         	<c:when test="${usuarioForm.perfil == 'C'}">
		                                         		<tr>
		                                                   <td class="text-left"><spring:message code="lbl.creci"/></td>
		                                                   <td class="text-right">${usuarioForm.creci}</td>
		                                              	</tr>
		                                         	</c:when>                                                                              
		                                         </c:choose>                     
		                                         
		                                             <tr>
		                                                 <td class="text-left"><spring:message code="lbl.total.imoveis"/></td>
		                                                 <td class="text-right">${usuarioForm.quantTotalImoveis}</td>
		                                             </tr>
		                                             
		                                             <tr>
		                                                 <td class="text-left"><spring:message code="lbl.total.contato"/></td>
		                                                 <td class="text-right">${usuarioForm.quantTotalContatos}</td>
		                                             </tr>
		                                             <tr>
		                                                 <td class="text-left"><spring:message code="lbl.total.parcerias"/></td>
		                                                 <td class="text-right">${usuarioForm.quantTotalParcerias}</td>
		                                             </tr>
		                                             <tr>
		                                                 <td class="text-left"><spring:message code="lbl.total.interessados"/></td>
		                                                 <td class="text-right">${usuarioForm.quantTotalInteressadosImoveis}</td>
		                                             </tr>
		                                             <tr>
		                                                 <td class="text-left"><spring:message code="lbl.total.intermediacoes" /></td>
		                                                 <td class="text-right">${usuarioForm.quantTotalIntermediacoes}</td>
		                                             </tr>
		                                             <tr>
		                                                 <td class="text-left"><spring:message code="lbl.total.visualizacoes" /></td>
		                                                 <td class="text-right">${usuarioForm.quantTotalVisitasImoveis}</td>
		                                             </tr>
		                                             <tr>
		                                                 <td class="text-left"><spring:message code="lbl.total.aprovacoes.usuario" /></td>
		                                                 <td class="text-right">${usuarioForm.quantTotalAprovacoesUsuario}</td>
		                                             </tr>
		                                         </tbody>
		                                     </table>	
		                                	</div>       	
									</div><!-- /.panel -->
								</div>
							</div>
							<div class="row">
								<div class="col-lg-9 col-md-9 col-sm-13">
								
									<!-- /.START Descricao -->
									<div class="panel rounded shadow">
										<div class="panel-heading">
											<h3 class="panel-title">
												<spring:message code="lbl.sobre.mim"/>
												<a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);" style="margin-left: 670px;"><i style="font-size: 18px" class="fa fa-question" ></i></a>
											</h3>
										</div>
										<div class="panel-body">
		                                    <p>${usuarioForm.descSobreMim}"</p>
										</div>
									</div>
									<!-- /.END Descricao -->
									
								<!-- /.START Imóveis Usuario -->
									<div class="panel panel-scrollable rounded shadow">
		                                <div class="panel-heading">
		                                	<div align="left" style="margin-top: 5px;">                                
		                              			<h3 class="panel-title"> <spring:message code="lbl.imoveis.usuario"/> &nbsp;
		                              				<c:if test="${not empty usuarioForm.listaImoveisUsuario}">
		                              					<a href="${urlImovel}/visualizarImoveisPerfilUsuario/${usuarioForm.id}" style="font-size:x-large; margin-left: 530px;" class="meta-action" title='<spring:message code="lbl.visualizar.imoveis.perfil.usuario"/>' ><i class="fa fa-home" style="color:gray"></i><font style="font-size: 12px; color: black;" >&nbsp; Filtrar Imóveis </font></a>
		                              				</c:if> 
		                              			</h3>
		                              			
		                              				<c:if test="${usuarioForm.quantTotalImoveis > 0}">                              					
		                              					 &nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.quant.total.imoveis"/> </strong>: (${usuarioForm.quantTotalImoveis}) </label>
		                              				</c:if> 
		                              		</div>                           	
		                                </div>
		                                
		                                <c:choose>
		                                	<c:when test="${ empty usuarioForm.listaImoveisUsuario }">
		                                		<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
						                                 <div class="media-list list-search">
						                                 	<div class="callout callout-warning">
							                                    <strong><spring:message code="msg.nenhum.imovel.detalhes.usuario"/></strong>				                                    
							                                </div>   
						                                </div>
				                                    <br/>
				                                </div>
		                                	</c:when>
		                                	
		                                	<c:when test="${not empty usuarioForm.listaImoveisUsuario }">
		                                			<div class="panel-body panel panel-scrollable rounded shadow" style="height: 455px;">
							                                   <div class="media-list list-search">
							                                    <c:forEach var="imovel" items="${usuarioForm.listaImoveisUsuario}" varStatus="item">
							                                        <div class="media rounded shadow no-overflow">
							                                        	<c:if test="${imovel.destaque == 'S'}">
							                                                	<div class="ribbon-wrapper top-left">
										                                            <div class="ribbon ribbon-shadow">Destaque</div>
										                                        </div>
							                                            </c:if>
							                                            <div class="media-left">
							                                                <a href="${urlImovel}/detalhesImovel/${imovel.id}" >
							                                                   <span class="meta-provider ${imovel.classePorAcao}" style="font-size:19px;">${imovel.acaoFmt} <br>
							                                                   							<strong>  R$<fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></strong>
							                                                   </span><br>                                                   
							                                                    <img src="${context}${imovel.imagemArquivo}" class="img-responsive" style="width: 260px; height: 195px; alt="admin"/>
							                                                </a>
							                                            </div>
							                                            <div class="media-body">
							                                                <span class="label pull-right" style="background-color: #9d2428; font-size: 12px">${imovel.tipoImovelFmt}</span>			                                                 
							                                                <h4 class="media-heading" style="margin-bottom:20px;"><a href="${urlImovel}/detalhesImovel/${imovel.id}" style="color : #9d2428;">${imovel.titulo}</a></h4>
							                                                <h5 class="media-heading" style="margin-bottom:12px;"><i class="fa fa-map-marker"></i> ${imovel.endereco} - ${imovel.bairro} - ${imovel.cidade} -${imovel.uf} </h1>
							                                                
							                                                <div class="col-md-5" >                                                    
							                                                    <br/> <br/> <br/> <br/> 
							                                                    
							                                                </div>
							                                                
							                                                <div class="col-md-7">
							                                                    <table class="table table-condensed">
							                                                        <tbody style="font-size: 13px;">
							                                                        	<tr>
							                                                                <td class="text-left"><spring:message code="lbl.area.m2.resum"/></td>
							                                                                <td class="text-right"><fmt:formatNumber value="${imovel.area}" pattern="#,##0;-0"/>m<sup>2</sup></td>
							                                                            </tr>
							                                                            <tr>
							                                                                <td class="text-left"><spring:message code="lbl.quartos.dormitorios.resum"/></td>
							                                                                <td class="text-right">${imovel.quantQuartos}</td>
							                                                            </tr>
							                                                            
							                                                            <tr>
							                                                                <td class="text-left"><spring:message code="lbl.buscar.imovel.banheiros"/></td>
							                                                                <td class="text-right">${imovel.quantBanheiro}</td>
							                                                            </tr>
							                                                            
							                                                            <tr>
							                                                                <td class="text-left"><spring:message code="lbl.suites"/></td>
							                                                                <td class="text-right">${imovel.quantSuites}</td>
							                                                            </tr>
							                                                            <tr>
							                                                                <td class="text-left"><spring:message code="lbl.vagas.garagem.resum"/></td>
							                                                                <td class="text-right">${imovel.quantGaragem} <spring:message code="lbl.num.vagas"/></td>
							                                                            </tr>
							                                                        </tbody>
							                                                    </table>
							                                                </div>
							                                            </div>
							                                        </div>
							                                    </c:forEach>
							                                </div>
					                                    <br/>
					                                </div>
		                                	</c:when>
		                                </c:choose>
		                            </div>                      
		                         <!-- /.END Imóveis Usuário  --> 
		 
		 						<!-- /.START Notas Usuario  -->  
		 						 <div class="panel panel-scrollable rounded shadow">
		                                <div class="panel-heading">			                                	
		                                    <h3 class="panel-title">
		                                    	<spring:message code="lbl.notas.usuario"/>
		                                    	<a href="#a" class="btn btn-sm" onClick="mostrarModal(1);" style="margin-left: 620px;"><i style="font-size: 18px" class="fa fa-question" ></i></a>
		                                    </h3>
		                                   		&nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.notas"/> </strong>: (${usuarioForm.quantTotalNotas}) </label>
		                                   	
		                                </div><!-- /.panel-heading -->
		                                
		                                <c:choose>
		                                	<c:when test="${ empty usuarioForm.listaNotasUsuario }">
		                                		<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
						                                 <div class="media-list list-search">
						                                 	<div class="callout callout-warning">
							                                    <strong><spring:message code="lbl.nenhuma.nota"/></strong>				                                    
							                                </div>   
						                                </div>
				                                    <br/>
				                                </div>
		                                	</c:when>
		                                	<c:when test="${not empty usuarioForm.listaNotasUsuario }">
		                                		<div class="panel-body panel panel-info rounded shadow">
		                                    
												  <c:forEach var="nota" items="${usuarioForm.listaNotasUsuario}"> 		                                	
															<div class="media inner-all">
								                                  <div class="pull-left">
								                                         <span class="fa fa-stack fa-2x">
								                                         	<c:if test="${((nota.acao == 'parceria') || (nota.acao == 'preferencia') || (nota.acao == 'usuario') )}">                                         	
								                                              	<img class="img-circle img-bordered-success" src="${context}/${nota.usuario.imagemArquivo}" style="width: 60px; height: 60px; " alt="admin"/>
								                                            </c:if>                                               	 
								                                            <c:if test="${(nota.acao == 'imovel')}">
								                                            	<img src="${context}/${nota.imovel.imagemArquivo}" style="width: 60px; height: 60px; " alt="admin"/>
								                                            </c:if>
								                                         </span>
								                                  </div><!-- /.pull-left -->
								                                  <div class="media-body">
								                                  	<c:choose>
																    <c:when test="${nota.acao == 'parceria'}">
																    	<a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" class="h4"><spring:message code="lbl.nota.parceria"/></a>
																    	
																    	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a></small>												    			    	
																    </c:when>
																    
																    <c:when test="${nota.acao == 'preferencia'}">
																    	<a href="#" class="h4"><spring:message code="lbl.nota.preferencia"/></a>
																    	
																    	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao}</small>
																    </c:when>
																    
																    <c:when test="${nota.acao == 'usuario'}">
																    	<a href="${urlUsuario}/meuPerfil" class="h4"><spring:message code="lbl.nota.info.usuario"/></a>
																    	
																    	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} </small>
																    </c:when>
																    
																    <c:when test="${nota.acao == 'imovel'}">
																    	<a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" class="h4"><spring:message code="lbl.nota.imovel"/></a>
																    	
																    	<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a></small>
																    </c:when>
																    
																  </c:choose>  															
							                                  	  
							                                      <em class="text-xs text-muted"><spring:message code="lbl.data.nota"/> <span class="text-danger"><fmt:formatDate value='${nota.dataNota}' pattern='dd/MM/yyyy'/></span></em>
								                                  </div><!-- /.media-body -->
								                              </div><!-- /.media -->
						                              		  <div class="line"></div>
						                              	  </c:forEach>
				                                	</div><!-- /.panel-body -->
		                                	</c:when>
		                                </c:choose>       
		                            </div><!-- /.panel -->
		 						
		 						<!-- /.END Notas Usuario -->  						
		 						
		 						  <c:if test="${usuarioForm.perfil == 'P'}">
				                      <!-- /.START PREFERENCIAS DO USUARIO -->		                      	   
					                      		<!-- Start scrollable panel -->
						                            <div class="panel panel-scrollable  rounded shadow">
						                                <div class="panel-heading">			                                	
						                                    <h3 class="panel-title">
						                                    	<spring:message code="lbl.pref.imoveis"/>
						                                    	<a href="#a" class="btn btn-sm" onClick="mostrarModal(2);" style="margin-left: 590px;"><i style="font-size: 18px" class="fa fa-question" ></i></a>
						                                    </h3>
						                                    &nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.pref.imoveis"/> </strong>: (${usuarioForm.quantTotalPrefImoveis}) </label>
						                                </div><!-- /.panel-heading -->
						                                
						                                <c:choose>
						                                	<c:when test="${not empty usuarioForm.listaPreferenciaImoveis}">
						                                		<div class="panel-body panel rounded shadow">
								                                    <table class="table table-striped" >
							                                            <thead>
							                                            <tr>
							                                                <th class="text-center"><spring:message code="lbl.tipo.imovel"/></th>
							                                                <th class="text-center"><spring:message code="lbl.acao.imovel"/></th>
							                                                <th class="text-center"><spring:message code="lbl.estado"/></th>
							                                                <th class="text-center"><spring:message code="lbl.cidade"/></th>
							                                                <th class="text-center"><spring:message code="lbl.bairro"/></th>
							                                            </tr>
							                                            </thead>
							                                            <tbody>
							                                            <c:forEach var="pref" items="${usuarioForm.listaPreferenciaImoveis}" >
								                                            <tr>
								                                                <td class="text-center"> ${pref.tipoImovelFmt}</td>
								                                                <td class="text-center">${pref.acaoFmt}</td>
								                                                <td class="text-center">${pref.nomeEstado}</td>
								                                                <td class="text-center">${pref.nomeCidade}</td>
								                                                <td class="text-center">${pref.nomeBairro}</td>			                                              
								                                            </tr>
							                                            </c:forEach>
							                                            </tbody>
							                                        </table>
								                                </div><!-- /.panel-body -->
						                                	</c:when>
						                                	
						                                	<c:when test="${ empty usuarioForm.listaPreferenciaImoveis}">
						                                		<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
										                                 <div class="media-list list-search">
										                                 	<div class="callout callout-warning">
											                                    <strong><spring:message code="msg.nenhuma.pref.imovel.cadastrada"/></strong>				                                    
											                                </div>   
										                                </div>
								                                    <br/>
								                                </div>			
						                                	</c:when>
						                                </c:choose>
						                             
						                            </div><!-- /.panel -->
						                        <!--/ End scrollable panel --> 			                       	                      
				                      <!-- /.END PREFERENCIAS DO USUARIO -->	
			                      </c:if>	 	
			                      
			                         <!-- /.START CONTATOS DO USUARIO -->	                       
				                            <div class="panel">
				                                <div class="panel-heading">			                                	
				                                    <h3 class="panel-title"><spring:message code="lbl.contatos"/> &nbsp;		                                   
				                             			<a href="#a" class="btn btn-sm" onClick="mostrarModal(3);" style="margin-left: 650px;"><i style="font-size: 18px" class="fa fa-question" ></i></a>
			                             			</h3>
			                             			&nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.contato"/> </strong>: (${usuarioForm.quantTotalContatos}) </label>
			                             			
				                                </div><!-- /.panel-heading -->
				                                	<c:choose>
				                                		<c:when test="${ not empty usuarioForm.listaContatosUsuario }">
				                                			<div class="panel-body panel " style="height: 400px;">	                                    
										                    	<c:forEach var="contato" items="${usuarioForm.listaContatosUsuario}">
											                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
											                            <div class="panel">
											                                <div class="panel-body" style="height: 220px;">						                                   
											                                    <ul class="">
											                                    	<c:choose>
											                                    		<c:when test="${contato.usuarioHost.id != usuarioForm.id}">
											                                    				<li class="text-center">
														                                        	<a href="${urlUsuario}/detalhesUsuario/${contato.usuarioHost.id}">
														                                            	<img class="img-circle img-bordered-success" src="${context}${contato.usuarioHost.imagemArquivo}" style="width: 100px; height: 130px; ">
														                                            </a>	
														                                        </li>
														                                        <li class="text-center">
														                                            <h4 class="text-capitalize">
														                                            	<a href="${urlUsuario}/detalhesUsuario/${contato.usuarioHost.id}">
														                                            		${contato.usuarioHost.nome}
														                                            	</a>
														                                            </h4>
														                                            <p class="text-muted text-capitalize">${contato.usuarioHost.perfilFmt} </p>
														                                        </li>		
											                                    		</c:when>
											                                    		
											                                    		<c:when test="${contato.usuarioConvidado.id != usuarioForm.id}">
											                                    			<li class="text-center">
													                                        	<a href="${urlUsuario}/detalhesUsuario/${contato.usuarioConvidado.id}">
													                                            	<img class="img-circle img-bordered-success" src="${context}${contato.usuarioConvidado.imagemArquivo}" style="width: 100px; height: 130px; ">
													                                            </a>	
													                                        </li>
													                                        <li class="text-center">
													                                            <h4 class="text-capitalize">
													                                            	<a href="${urlUsuario}/detalhesUsuario/${contato.usuarioConvidado.id}">
													                                            		${contato.usuarioConvidado.nome}
													                                            	</a>
													                                            </h4>
													                                            <p class="text-muted text-capitalize">${contato.usuarioConvidado.perfilFmt} </p>
													                                        </li>	
											                                    		</c:when>
											                                    	</c:choose>											                                    					                                        
											                                        
											                                    </ul><!-- /.list-unstyled -->
											                                </div><!-- /.panel-body -->
											                            </div><!-- /.panel -->		
											                         </div>   				                        
										                        </c:forEach>
										                     </div>		
				                                		</c:when>
				                                		
				                                		<c:when test="${ empty usuarioForm.listaContatosUsuario }">
				                                			<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
									                                 <div class="media-list list-search">
									                                 	<div class="callout callout-warning">
										                                    <strong><spring:message code="lbl.nenhum.contato.retornado"/></strong>				                                    
										                                </div>   
									                                </div>
							                                    <br/>
							                                </div>		
				                                		</c:when> 
				                                		
				                                	</c:choose>	                                     
				                            </div><!-- /.panel -->
				                       <!-- /.END CONTATOS DO USUARIO -->
			                       
			                        <!-- /.START SEGUIDORES DO USUARIO -->	                       
			                            <div class="panel panel-scrollable rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title"><spring:message code="lbl.title.lista.seguidores.detalhes.usuario"/> &nbsp;
			                             			<a href="#a" class="btn btn-sm" onClick="mostrarModal(4);" style="margin-left: 570px;"><i style="font-size: 18px" class="fa fa-question" ></i></a>
		                             			</h3>
		                             			&nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.seguidores"/> </strong>: (${usuarioForm.quantTotalSeguidores}) </label>
		                             			
			                                </div><!-- /.panel-heading -->
			                                	<c:choose>
			                                		<c:when test="${ not empty usuarioForm.listaSeguidores }">
		                                				<div class="panel-body panel " style="height: 400px;">	                                    
									                    	<c:forEach var="usuarioSeguidor" items="${usuarioForm.listaSeguidores}">
										                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
										                            <div class="panel">
										                                <div class="panel-body" style="height: 220px;">						                                   
										                                    <ul class="">
										                                        <li class="text-center">
										                                        	<a href="${urlUsuario}/detalhesUsuario/${usuarioSeguidor.usuario.id}">
										                                            	<img class="img-circle img-bordered-success" src="${context}${usuarioSeguidor.usuario.imagemArquivo}" style="width: 100px; height: 130px; ">
										                                            </a>	
										                                        </li>
										                                        <li class="text-center">
										                                            <h4 class="text-capitalize">
										                                            	<a href="${urlUsuario}/detalhesUsuario/${usuarioSeguidor.usuario.id}">
										                                            		${usuarioSeguidor.usuario.nome}
										                                            	</a>
										                                            </h4>
										                                            <p class="text-muted text-capitalize">${usuarioSeguidor.usuario.perfilFmt} </p>
										                                        </li>						                                        
										                                        
										                                    </ul><!-- /.list-unstyled -->
										                                </div><!-- /.panel-body -->
										                            </div><!-- /.panel -->		
										                         </div>   				                        
									                        </c:forEach>
									                        </div>
			                                		</c:when>
			                                		<c:when test="${empty usuarioForm.listaSeguidores }">
			                                			<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
								                                 <div class="media-list list-search">
								                                 	<div class="callout callout-warning">
									                                    <strong><spring:message code="lbl.nenhum.usuario.seguindo"/></strong>				                                    
									                                </div>   
								                                </div>
						                                    <br/>
						                                </div>	
			                                		</c:when>
			                                	</c:choose>	
			                                     
			                            </div><!-- /.panel -->
			                       <!-- /.END SEGUIDORES DO USUARIO -->	                       
			                       
			                      <!-- /.START Recomendacoes -->	
																	
									<div class="panel rounded shadow">
		                                <div class="panel-heading">
		                                    <h3 class="panel-title">
		                                    	<spring:message code="lbl.title.aba.recomendacoes.detalhe.usuario"/>
		                                    	<a href="#a" class="btn btn-sm" onClick="mostrarModal(5);" style="margin-left: 625px;"><i style="font-size: 18px" class="fa fa-question" ></i></a>
		                                    </h3>
		                                    &nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.recomendacoes"/> </strong>: (${usuarioForm.quantTotalRecomendacoes}) </label>
		                                </div>
		                                
		                                <c:choose>
		                                	<c:when test="${not  empty usuarioForm.listaRecomendacoes }">
		                                		<div class="panel-body">
				                                    <ul class="media-list comment-list">
														<c:forEach var="recomendacao" items="${usuarioForm.listaRecomendacoes}">
															<li class="media">		                                            
					                                            <c:choose>		                                            	
					                                            	<c:when test="${((recomendacao.isStatusEnviado()) && (recomendacao.usuario.id == usuario.id))}">
					                                            		<div class="media-left">
							                                                <a href="#">
							                                                    <img class="media-object thumbnail" src="${context}${recomendacao.usuario.imagemArquivo}" style="width: 50px; height: 60px;"  />
							                                                </a>
							                                            </div>
					                                            		<div class="media-body">
					                                            			<span class="label pull-right" style="background-color: #9d2428; font-size: 12px">Aguardando Aprovação</span>
							                                                <h4>${recomendacao.usuario.nome}</h4>
							                                                <small class="text-muted"><fmt:formatDate value='${recomendacao.dataResposta}' pattern='dd/MM/yyyy HH:mm:ss'/></small>
							                                                <p>${recomendacao.descricao}</p>
							                                            </div>	
					                                            	</c:when>
					                                            	
					                                            	<c:when test="${(recomendacao.isStatusAceito())}">
					                                            		<div class="media-left">
							                                                <a href="#">
							                                                    <img class="media-object thumbnail" src="${context}${recomendacao.usuario.imagemArquivo}" style="width: 50px; height: 60px;"  />
							                                                </a>
							                                            </div>
					                                            		<div class="media-body">		                                            			
							                                                <h4>${recomendacao.usuario.nome}</h4>
							                                                <small class="text-muted"><fmt:formatDate value='${recomendacao.dataResposta}' pattern='dd/MM/yyyy HH:mm:ss'/></small>
							                                                <p>${recomendacao.descricao}</p>
							                                            </div>	
					                                            	</c:when>
					                                            	
					                                            	<c:when test="${((recomendacao.isStatusEnviado()) && (recomendacao.usuarioRecomendado.id == usuario.id))}">
					                                            		<div class="media-left">
							                                                <a href="#">
							                                                    <img class="media-object thumbnail" src="${context}${recomendacao.usuario.imagemArquivo}" style="width: 50px; height: 60px;"  />
							                                                </a>
							                                            </div>
					                                            		<div class="media-body">	
					                                            			<span class="label pull-right" style="background-color: #9d2428; font-size: 12px">Aguardando Minha Aprovação</span>	                                            			
							                                                <h4>${recomendacao.usuario.nome}</h4>
							                                                <small class="text-muted"><fmt:formatDate value='${recomendacao.dataResposta}' pattern='dd/MM/yyyy HH:mm:ss'/></small>
							                                                <p>${recomendacao.descricao}</p>
							                                                
						                                                    <spring:message code="lbl.acao.aceita.recomendacao" var="mensagemAceitaRecomendacao"/>
											                                <a href="#a" onClick="aceitarRecomendacao(${recomendacao.id})" id="idAceitaRecomendacao_${recomendacao.id}" style="font-size:x-large; "  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemAceitaRecomendacao}" ><i class="fa fa-check"></i></a>
							                                                                                                    
							                                                <spring:message code="lbl.acao.recusar.recomendacao" var="mensagemRecusarRecomendacao"/>	
							                                                <a href="#a" onClick="recusarRecomendacao(${recomendacao.id})" id="idRecusaRecomendacao_${recomendacao.id}" style="font-size:x-large; "  class="dropdown-toggle my-tooltip" data-toggle="tooltip" data-placement="right"  data-original-title="${mensagemRecusarRecomendacao}" ><i class="fa fa-times"></i></a>
							                                                
							                                                
							                                                <div id="idMsgAceitarRecomendacao_${recomendacao.id}"  class="panel panel-success" style="display: none;">
														                          <div class="panel-heading">
														                              <h3 class="panel-title"><spring:message code="msg.recomendacao.aceita"/></h3>
														                          </div><!-- /.panel-heading -->		                          
															                </div><!-- /.panel -->                      
															                
														               		 <div id="idMsgRecusarRecomendacao_${recomendacao.id}" class="panel panel-danger" style="display: none;">
														                          <div class="panel-heading">
														                              <h3 class="panel-title"><spring:message code="msg.recomendacao.recusado"/></h3>
														                          </div><!-- /.panel-heading -->			                                                    
														                      </div><!-- /.panel -->   
							                                            </div>
					                                            	</c:when>
					                                            </c:choose>
					                                            
					                                        </li><!-- media -->											
														</c:forEach>
				                                    </ul>
				                                    <br/>
				
				                                    <ul class="media-list comment-list">
				                                        <li class="media">                                                                                        
				                                            <div class="mb-20"></div>
				                                            <form class="form-horizontal mb-20" role="form">	
					                                            <div class="form-group">
					                                               <input type="button" class="btn btn-primary" onClick="prepararModalAddRecomendacao();" value='<spring:message code="btn.modal.adicionar.recomendacao"/> '>
					                                            </div>
							                                      
				                                            </form>
				                                        </li>
				                                    </ul>
				                                </div>	
		                                	</c:when>
		                                	
		                                	<c:when test="${empty usuarioForm.listaRecomendacoes }">
		                                		<div class="panel-body">
				                                	 <div class="callout callout-warning">
						                                    <strong><spring:message code="msg.nenhuma.recomendacao.recebida"/></strong>		                                    
						                              </div>				                                  
			                                	</div>		
		                                	</c:when>
		                                </c:choose>                                                                 
		                            </div>
		                         <!-- /.END Recomendacoes --> 
		                         
		                         <!-- /.START Serviços Concedidos/Revogados -->
		                         
				                         <div class="panel panel-scrollable  rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title">
			                                    	<spring:message code="lbl.admin.lista.servicos.concedidos.revogados"/>
			                                    	<a href="#a" class="btn btn-sm" onClick="mostrarModal(6);" style="margin-left: 500px;"><i style="font-size: 18px" class="fa fa-question" ></i></a>
			                                    </h3>
			                                    &nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.admin.opcao.rel.total.servicos"/> </strong>: (${usuarioForm.quantTotalPrefImoveis}) </label>
			                                </div><!-- /.panel-heading -->
			                                
			                                <c:choose>
			                                	<c:when test="${not empty usuarioForm.listaServicos}">
			                                		<div class="panel-body panel rounded shadow">
					                                    <table class="table table-striped" >
				                                            <thead>
				                                            <tr>
				                                                <th class="text-center"><spring:message code="lbl.table.nome.servico"/></th>
																<th class="text-center"><spring:message code="lbl.table.status.servico"/></th>
																<th class="text-center"><spring:message code="lbl.table.data.sol.servico"/></th>
																<th class="text-center"><spring:message code="lbl.table.data.pagto.servico"/></th>                                                
																<th class="text-center"><spring:message code="lbl.table.concedido.revogado.nome.usuario"/></th> 
				                                            </tr>
				                                            </thead>
				                                            <tbody>
				                                            <c:forEach var="pref" items="${usuarioForm.listaServicos}" >
					                                            <tr>
					                                            	<td class="text-center">
					                                            		<c:if test="${(servico.descServico == 'relatorioCorretor' || servico.descServico == 'relatorioCliente' || servico.descServico == 'relatorioImobiliaria')}" >
																			<spring:message code="lbl.table.result.relatorio"/>
																		</c:if>
																		<c:if test="${(servico.descServico == 'assinaturaCorretor' || servico.descServico== 'assinaturaPadrao' || servico.descServico == 'assinaturaImobiliaria')}" >
																			<spring:message code="lbl.table.result.assinatura"/>
																		</c:if>				
																		 <c:if test="${(servico.tipoServicoComum == 'N')}" >
																				${servico.labelServico}
																		</c:if>
					                                            	</td>
					                                            	<td class="text-center">${servico.statusPgtoFmt} </td>
																	<td class="text-center"><fmt:formatDate value='${servico.dataSolicitacao}' pattern='dd/MM/yyyy'/></td>
																	<td class="text-center"><fmt:formatDate value='${servico.dataPagto}' pattern='dd/MM/yyyy'/></td>		                                   
																	<td class="text-center">
																		 <a href="#">                                            
																			<img src="${context}/${servico.imagemArquivo}" style="width: 100px; height: 80px; " alt="admin"/>	                                            
																		</a>
																	</td>
					                                                		                                              
					                                            </tr>
				                                            </c:forEach>
				                                            </tbody>
				                                        </table>
					                                </div><!-- /.panel-body -->
			                                	</c:when>
			                                	
			                                	<c:when test="${ empty usuarioForm.listaServicos}">
			                                		<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
							                                 <div class="media-list list-search">
							                                 	<div class="callout callout-warning">
								                                    <strong><spring:message code="msg.lista.servicos.vazio.concedido"/></strong>				                                    
								                                </div>   
							                                </div>
					                                    <br/>
					                                </div>			
			                                	</c:when>
			                                </c:choose>
			                             
			                            </div><!-- /.panel -->
		                         
		                         <!-- /.END   Serviços Concedidos/Revogados -->
		                         
		                         <!-- /.START lista dos planos concedidos/revogados e seus respectivos usuarios envolvidos dados por este usuario administrador -->
		                         
		                         <div class="panel panel-scrollable  rounded shadow">
			                                <div class="panel-heading">			                                	
			                                    <h3 class="panel-title">
			                                    	<spring:message code="lbl.admin.lista.planos.concedidos.revogados"/>
			                                    	<a href="#a" class="btn btn-sm" onClick="mostrarModal(7);" style="margin-left: 510px;"><i style="font-size: 18px" class="fa fa-question" ></i></a>
			                                    </h3>
			                                    &nbsp;<label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.admin.total.planos"/> </strong>: (${usuarioForm.quantTotalPrefImoveis}) </label>
			                                </div><!-- /.panel-heading -->
			                                
			                                <c:choose>
			                                	<c:when test="${not empty usuarioForm.listaPlanos}">
			                                		<div class="panel-body panel rounded shadow">
					                                    <table class="table table-striped" >
				                                            <thead>
				                                            <tr>
				                                                 <th class="text-center"><spring:message code="lbl.table.nome.servico"/></th>
																 <th class="text-center"><spring:message code="lbl.table.status.servico"/></th>
																 <th class="text-center"><spring:message code="lbl.table.data.sol.servico"/></th>
																 <th class="text-center"><spring:message code="lbl.table.data.pagto.servico"/></th>                                                
																 <th class="text-center"><spring:message code="lbl.table.concedido.revogado.nome.usuario"/></th> 
				                                            </tr>
				                                            </thead>
				                                            <tbody>
					                                              <c:if test="${not  empty usuarioForm.listaPlanos }">
																		<c:forEach var="plano" items="${usuarioForm.listaPlanos}" >
																			<tr>
																				<td class="text-center">${plano.nomePlano}</td>
																				<td class="text-center">${plano.statusFmt}</td>
																				<td class="text-center"><fmt:formatDate value='${plano.dataSolicitacao}' pattern='dd/MM/yyyy'/></td>
																				<td class="text-center"><fmt:formatDate value='${plano.dataPagto}' pattern='dd/MM/yyyy'/></td>
																				<td class="text-center">																		  
																					   <a href="#">                                            
																							<img src="${context}/${servico.imagemArquivo}" style="width: 100px; height: 80px; " alt="admin"/>	                                            
																						</a>	
																				</td>
																			</tr>
																		</c:forEach>
																	</c:if>	
				                                            </tbody>
				                                        </table>
					                                </div><!-- /.panel-body -->
			                                	</c:when>
			                                	
			                                	<c:when test="${ empty usuarioForm.listaPlanos}">
			                                		<div class="panel-body panel panel-scrollable rounded shadow" style="height: 120px;">
							                                 <div class="media-list list-search">
							                                 	<div class="callout callout-warning">
								                                    <strong><spring:message code="msg.lista.planos.vazio.concedido"/></strong>				                                    
								                                </div>   
							                                </div>
					                                    <br/>
					                                </div>			
			                                	</c:when>
			                                </c:choose>
			                             
			                            </div><!-- /.panel -->
		                         <!-- /.END lista dos planos concedidos/revogados e seus respectivos usuarios envolvidos dados por este usuario administrador -->					
			                      
							</div>
		                </div><!-- /.body-content -->
					</div>    	
	            </div>
                 <!-- END  Painel de Informações -->

            </section><!-- /#page-content -->

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->
        
        <!-- Start optional size modal element - item 1 -->
            <div id="idModalItem" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				          <h4 class="modal-title"><div id="msgModalFuncionalidade" > </div> </h4>
				        </div>
				        <div class="modal-body">
				       	   <strong> Descrição:  </strong> <div id="msgModal" > </div>
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
  			<c:import url="../layout-admin/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>