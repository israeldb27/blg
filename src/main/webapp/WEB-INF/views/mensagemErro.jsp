<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

    <!-- START @HEAD -->
    
    	<script type="text/javascript">
    	$(document).ready(function() {
    		
    	});	
		</script>
		
        <c:import url="./layout/head-layout.jsp"></c:import>
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="./layout/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="./layout/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">
            
            	 <!-- Start header content --> 
                <div class="header-content">
                	<c:choose>
                		<c:when test="${mensagemErro != null }">
                			<h2><i class="fa fa-exclamation-triangle"></i><spring:message code="lbl.mensagem.erro.nao.autorizado"/> </h2>                		
                		</c:when>
                		
                		<c:when test="${mensagemErroGeral != null }">                		
                			<h2><i class="fa fa-exclamation-triangle"></i><spring:message code="lbl.mensagem.erro.generico"/> </h2>
                		</c:when>
                	</c:choose>
                
                
                                                                                             
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">
                
                	<c:choose>
                		<c:when test="${mensagemErro != null }">
                			<div class="callout callout-danger">
	                            <strong>${mensagemErro}</strong>                              
	                        </div>                		
                		</c:when>
                		
                		<c:when test="${mensagemErroGeral != null }">                		
                			<div class="callout callout-danger">
	                            <strong><spring:message code="lbl.desc.mensagem.erro.aplicacao"/></strong>                              
	                        </div>                		
                		</c:when>
                	</c:choose>
                	           
                </div><!-- /.body-content -->              
         
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
  			<c:import url="./layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

    </body>
    <!--/ END BODY -->

</html>