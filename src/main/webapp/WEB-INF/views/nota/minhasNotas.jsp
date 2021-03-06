<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/nota" var="urlNota"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>
   
   	<script type="text/javascript">
		$(document).ready(function() {
			
			$('#opcaoOrdenacao').change(function () {				
    			$("#notaOrdenacaoForm").submit();      
    		 });
			
			$('#opcaoFiltro1').change(function () {				
    			$("#notaFiltroForm").submit();      
    		 });
			
			$('#opcaoPaginacao').change(function () {				
				$("#notaOrdenacaoPageForm").submit();      
			 });
		});
		
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.notas"/> <span><spring:message code="lbl.title.link.minhas.notas"/></span> </h2>		
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

					<div class="row">
						<% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
						<c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
						<% } %>

						<div class="col-lg-8 col-md-10 col-sm-8">
							<div class="panel rounded shadow">
								<div class="panel-heading" style="background: #eeeeef; border-style: none;" >
									<div class="pull-left">
										<form:form method="POST" id="notaFiltroForm" modelAttribute="notaForm" action="${urlNota}/filtrarMinhasNotas" >
											<form:select id="opcaoFiltro1" path="opcaoFiltro" class="form-control">
												<form:option value="" disabled="true"><spring:message code="lbl.opcao.filtrar"/></form:option>
												<form:option value="I" ><spring:message code="lbl.nota.filtro.imovel"/></form:option>
												<form:option value="U" ><spring:message code="lbl.nota.filtro.usuario"/></form:option>	
												<form:option value="E" ><spring:message code="lbl.nota.filtro.pessoal"/></form:option>
												<c:choose>
													<c:when test="${usuario.perfil != 'P'}">
														<form:option value="P" ><spring:message code="lbl.nota.filtro.parceria"/></form:option>
													</c:when>

													<c:when test="${usuario.perfil == 'P'}">
														<form:option value="R" ><spring:message code="lbl.nota.filtro.preferencia"/></form:option>
														<form:option value="T" ><spring:message code="lbl.nota.filtro.intermediacoes"/></form:option>
													</c:when>
												</c:choose>
												<form:option value="" ><spring:message code="lbl.nota.filtro.todos"/></form:option>
											</form:select>
										</form:form>
									</div><!-- /.pull-left -->
									<div class="pull-right">
										<form:form method="POST" id="notaOrdenacaoForm" modelAttribute="notaForm" action="${urlNota}/ordenarMinhasNotas" >
											<form:select id="opcaoOrdenacao" path="opcaoOrdenacao" class="form-control">
												<form:option value="" disabled="true"><spring:message code="lbl.opcao.ordenar"/></form:option>
												<form:option value="maiorDataNota" ><spring:message code="lbl.nota.ordenacao.mais.recente"/></form:option>
												<form:option value="menorDataNota" ><spring:message code="lbl.nota.ordenacao.menos.recente"/></form:option>
											</form:select>
										</form:form>
									</div><!-- /.pull-right -->
									
									<c:if test="${notaForm.isVisible() }">
										<div class="pull-right" style="padding-right:20px;">
											<form:form method="POST" id="notaOrdenacaoPageForm" modelAttribute="notaForm" action="${urlNota}/filtrarMinhasNotas" >
												<spring:message code="lbl.hint.opcao.paginacao" var="hintPaginacao"/>
												<form:select id="opcaoPaginacao" path="opcaoPaginacao" class="form-control" title="${hintPaginacao}">
													<form:option value="" disabled="true"><spring:message code="lbl.opcao.paginacao"/></form:option>
													<form:options items="${notaForm.listaPaginas}" itemValue="key" itemLabel="label"/>
												</form:select>
											</form:form>
										</div><!-- /.pull-right -->
									</c:if>

									<div class="clearfix"></div>
								</div><!-- /.panel-heading -->

								<div class="panel-body" style="background: #eeeeef" >
									<div class="panel-body no-padding">
										<form:form method="POST" id="notaEscreveForm" modelAttribute="notaForm" action="${urlNota}/escreverMinhaNota" >
											<div class="form-body">
												<div class="form-group">													
													<form:textarea id="escreverNota" path="escreverNota" class="form-control"  rows="5" cols="30"/>
												</div><!-- /.form-group -->
											</div>
										
												<div align="center">
													<button id="btnSubmitAdd" type="submit" class="btn btn-primary"><spring:message code="btn.escrever.nota"/></button>
												</div>
												<div class="clearfix"></div>
											
										</form:form>
									</div>
									<br>									
									<div class="profile-body" >
										<c:choose>
                                			<c:when test="${not empty listaMinhasNotas}">
                                				<div class="timeline">
                                					<c:forEach var="nota" items="${listaMinhasNotas}"> 
                                						<div class="timeline-item last-timeline" >
															<div class="timeline-badge" >
																  <img class="timeline-badge-userpic" src="data:image/jpeg;base64,${nota.usuario.imagemArquivo}" style="width: 80px; height: 90px; " >
															</div>															
															<div class="timeline-body" style="width: 800px;" >
																<div class="timeline-body-arrow">
																</div>
																
																<div class="timeline-body-head">
																	<div class="timeline-body-head-caption">
																		<a href="${urlUsuario}/detalhesUsuario/${usuario.id}"  class="timeline-body-title font-blue-madison"> ${nota.usuario.nome}</a>
																		<span class="timeline-body-time font-grey-cascade"> </span>
																	</div>
																	<span class="label pull-right">  <a href="#a" class="btn btn-sm"  onClick="mostrarModal(6);" ><i class="fa fa-question" ></i></a>  </span>
																</div>
																<div class="timeline-body-content" >
																	<p>
																		<c:choose>
																			<c:when test="${nota.acao == 'E'}">
																				<small class="block text-muted"> <font size="3px;"> ${nota.descricao} </font></small>
																			</c:when>
																			
																			<c:when test="${nota.acao == 'I'}">
																				<small class="block text-muted"> <font size="3px;"> <spring:message code="lbl.minha.nota.informacoes.imovel.p1"/> <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a> <spring:message code="lbl.minha.nota.informacoes.imovel.p2"/> </font></small>
																			</c:when>
																			
																			<c:when test="${nota.acao == 'U'}">
																				<small class="block text-muted"> <font size="3px;"> <spring:message code="lbl.minha.nota.informacoes.pessoais"/> </font></small>
																			</c:when>
																			
																			<c:when test="${nota.acao == 'R'}">
																				<small class="block text-muted"> <font size="3px;"> <spring:message code="lbl.minha.nota.add.preferencia.imovel"/> </font></small>
																			</c:when>	
																			
																			<c:when test="${nota.acao == 'T'}">
																				<small class="block text-muted"> <font size="3px;"> <spring:message code="lbl.minhas.notas.contato.intermediacao"/> <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a></font></small>
																			</c:when>
																			
																			<c:when test="${nota.acao == 'P'}">
																				<small class="block text-muted"> <font size="3px;"> <spring:message code="lbl.minhas.notas.contato.parceria"/> <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a> </font></small>
																			</c:when>
																																					
																		</c:choose>																		
																	</p>																				
																</div>	
																 </br>																			  
															     <em class="text-xs text-muted"><spring:message code="lbl.data.nota"/>: <span class="text-danger"><fmt:formatDate value='${nota.dataNota}' pattern='dd/MM/yyyy'/></span></em>
															
															</div>
														</div>
                                					</c:forEach>								
												</div>
                                			</c:when>
                                			
                                			<c:when test="${ empty listaMinhasNotas }">
	                                			 <div class="callout callout-warning">
				                                    <strong><spring:message code="lbl.nenhuma.nota"/></strong>			                                    
				                                </div>                                		
	                                		</c:when>
	                                		
                                		</c:choose>		
									</div>
								</div>
							</div>
						</div>
						<!--  START SIDEBAR RIGHT -->
						<div class="col-md-3">
							<c:import url="../layout/sidebar-right.jsp"></c:import>
						</div>
						<!--  END SIDEBAR RIGHT -->
					</div>
                    

                </div><!-- /.body-content -->                
         
            </section><!-- /#page-content -->
			
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
			
		    <!-- Start content modal Imovel Detalhes-->
				<c:import url="../ajuda/imovelDetalhesModal.jsp"></c:import>																				
		    <!-- End content  modal Imovel Detalhes -->
			
        </section><!-- /#wrapper -->

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