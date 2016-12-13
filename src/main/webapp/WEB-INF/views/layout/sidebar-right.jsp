<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<spring:url value="/imovel" var="urlImovel"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

	<div class="recent-activity">
		<div align="center">	
        	<h3><spring:message code="lbl.title.anuncios"/></h3>
        </div>
     <c:if test="${ not empty sessionScope.listaImovelAnuncioDestaque }">
        <c:forEach var="imovel" items="${sessionScope.listaImovelAnuncioDestaque}">
			<!-- START Exibicao dos Imoveis Destaque - Anuncio-->
			<!-- Start recent activity item -->
		        <div class="recent-activity-item recent-activity-primary">
		            <div class="recent-activity-badge">
		                <span class="recent-activity-badge-userpic"></span>
		            </div>
		            <div class="recent-activity-body">
		                <div class="recent-activity-body-head">
		                    <div class="recent-activity-body-head-caption" align="center">
		                        <h3 class="recent-activity-body-title">
		                        	<a href="${urlImovel}/detalhesImovel/${imovel.id}">
		                        		<img class="img-circle img-bordered-primary" src="${context}${imovel.imagemArquivo}" style="width: 150px; height: 90px; ">
		                        	</a>	
		                        </h3>
		                    </div>
		                </div>
		                <div class="recent-activity-body-content" >
		                    <p align="center">
		                        <a href="${urlImovel}/detalhesImovel/${imovel.id}">${imovel.titulo}</a>   
		                    </p>		                    
		                </div>
		            </div>
		        </div>
		        <br> </br>
        <!-- End recent activity item -->
		</c:forEach>
     </c:if>			
		<!-- End recent activity item -->
	


 
    </div>