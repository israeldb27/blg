<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/mensagem" var="urlMensagem"/>
<spring:url var="urlMensagemAdmin" value="/mensagemAdmin"/>


<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>
<c:set var="context" value="<%= request.getContextPath()%>"/>
   
<html> 

    <!-- START @HEAD -->
    <head> 
    	<script>
			$(document).ready(function() {
				$("editButton").click( function() {
					 $("firstname-1").val('teste');
				 });					
			});
			
			function populaModal(){
				alert('chegou');				
				$("firstname-1").val("testando");
				$("resposta").html('teste');				
			}
		</script>
		
        <c:import url="../../layout/head-layout.jsp"></c:import>
    </head>
   
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
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.title.link.mensagens"/>  </h2>                                                      
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
					<div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-success shadow">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.lista.mensagens.admin"/></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                    <!-- Start list message -->
                                    <div class="media-list">
										<c:forEach var="mensagem" items="${listaMinhasMensagens}">											
										        <a href="${urlMensagemAdmin}/prepararMaisMensagensAdmin/${mensagem.id}" class="media">
										                                        
	                                            <div class="pull-left"> </div>
	                                            <div class="media-body">
	                                                <span class="media-heading">${mensagem.tipoMensagemFmt}</span>
	                                                <span class="media-text">${mensagem.descricaoUltimaMensagem} </span>
	                                                <!-- Start meta icon -->
	                                                <span class="media-meta"></span>	                                                
	                                                <span class="media-meta"><i class="fa fa-plus-square"></i> <spring:message code="lbl.mais.mensagens"/></span>
	                                                
	                                                <span class="media-meta pull-right"><fmt:formatDate value="${mensagem.dataUltimaMensagem}" pattern='dd/MM/yyyy'/></span>
	                                                <!--/ End meta icon -->
	                                            </div><!-- /.media-body -->
	                                        </a><!-- /.media -->
                                        </c:forEach>
                                        
                                    </div>
                                    <!--/ End list message -->

                                </div><!-- /.panel-body -->
                            </div>
                        </div>
                </div>       
       		</div>               
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
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>