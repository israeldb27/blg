<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/>
<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/imovelVisualizado" var="urlImovelVisualizado"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/contato" var="urlContato"/>
<spring:url value="/usuario" var="urlUsuario"/>

<script type="text/javascript">

$(document).ready(function() {
	
   $('#opcaoFiltro1').change(function () {				
		$("#contatoFiltroForm").submit();      
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.contatos"/> </h2>    	
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                    	<% if ( request.getSession().getAttribute("acessoValido").equals("N") ) {%>
							<c:import url="../avisoRenovacaoAssinatura.jsp"></c:import>
                        <% } %>
                        
                        <div class="col-md-12">
                        	<c:if test="${ not empty listaContatos }">
                        		<!-- Start inline form -->
		                            <div class="panel rounded shadow">     
		                                <div class="panel-body no-padding">
		                                  <div class="panel-heading">
		                                    <div class="pull-left">
		                                        <label><strong> <spring:message code="lbl.quant.total.usuarios"/> </strong>: (${quantTotalContatos}) </label>
		                                    </div>
		                                                                           
		                                    <div class="pull-right" >
		                                    	<spring:message code="lbl.hint.usuario.perfil.contato" var="hintFiltrar"/> 
		                                        <form:form method="POST" id="contatoFiltroForm" modelAttribute="contatoForm" action="${urlContato}/filtrarContato" >
		                                        	<form:select id="opcaoFiltro1" path="opcaoFiltro" class="form-control" title="${hintFiltrar}">                                
									                        <form:option value="" disabled="true"><spring:message code="opcao.selecao.uma.opcao"/></form:option>
									                        <form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" />
															<form:option value="todos"><spring:message code="lbl.perfil.usuario.todos"/></form:option>
									                  </form:select>  
		                                        </form:form> 
		                                    </div>
		                                    
		                                    <div class="clearfix"></div>
		                                </div><!-- /.panel-heading -->
		
		                                </div><!-- /.panel-body -->
		                            </div><!-- /.panel -->
		                            <!--/ End inline form -->
                        	</c:if>
                        </div>	               
                    </div><!-- /.row -->
                    
                    <div class="row">
                    	<c:choose>
                    			<c:when test="${ not empty listaContatos }">
                    				<c:forEach var="usuarioContato" items="${listaContatos}">
				                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
				                            <div class="panel rounded shadow">
				                                <div class="panel-body ">
				                                    <div class="ribbon-wrapper">
				                                        
				                                    </div><!-- /.ribbon-wrapper -->
				                                    <ul class="inner-all list-unstyled">
				                                    	<c:choose>
				                                    		<c:when test="${usuarioContato.usuarioConvidado.id == usuario.id}">
				                                    			<li class="text-center">				                                        
						                                        	<a href="${urlUsuario}/detalhesUsuario/${usuarioContato.usuarioHost.id}" >
						                                            	<img class="img-circle img-bordered-success" src="${context}${usuarioContato.usuarioHost.imagemArquivo}" style="width: 100px; height: 100px; ">
						                                            </a>	
						                                        </li>
						                                        <li class="text-center">
						                                        	<a href="${urlUsuario}/detalhesUsuario/${usuarioContato.usuarioHost.id}" >
						                                            	${usuarioContato.usuarioHost.nome}
						                                            </a>	
						                                            <p class="text-muted text-capitalize">${usuarioContato.usuarioHost.perfilFmt} </p>
						                                        </li>	
				                                    		</c:when>
				                                    	
				                                    		<c:when test="${usuarioContato.usuarioHost.id == usuario.id}">
				                                    			<li class="text-center">				                                        
						                                        	<a href="${urlUsuario}/detalhesUsuario/${usuarioContato.usuarioConvidado.id}" >
						                                            	<img class="img-circle img-bordered-success" src="${context}${usuarioContato.usuarioConvidado.imagemArquivo}" style="width: 100px; height: 100px; ">
						                                            </a>	
						                                        </li>
						                                        <li class="text-center">
						                                        	<a href="${urlUsuario}/detalhesUsuario/${usuarioContato.usuarioConvidado.id}" >
						                                            	${usuarioContato.usuarioConvidado.nome}
						                                            </a>	
						                                            <p class="text-muted text-capitalize">${usuarioContato.usuarioConvidado.perfilFmt} </p>
						                                        </li>	
				                                    		</c:when>	
				                                    					                                    		
				                                    	</c:choose>
				                                    </ul><!-- /.list-unstyled -->
				                                </div><!-- /.panel-body -->
				                            </div><!-- /.panel -->
				                        </div>
			                        </c:forEach>	
                    			</c:when>
                    			
                    			<c:when test="${ empty listaContatos }">
                    				<div class="panel">
                    						<div class="alert alert-primary">
			                                      <strong><spring:message code="lbl.nenhum.contato.retornado"/></strong> 
			                                 </div>             				
		                            </div>     			
                    			</c:when>                    	
                    	</c:choose>
                    </div>

                </div><!-- /.body-content -->
                <!--/ End body content -->
         
            </section><!-- /#page-content -->
        </section><!-- /#wrapper -->

		            
           	<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->

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