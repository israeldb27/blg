<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<spring:url value="/usuario" var="urlUsuario"/>


<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${context}/js/jquery-ui.js"></script>

<div class="row">                            
        <c:forEach var="usuario" items="${listaFiqueDeOlho}" begin="1" end="4">	                                                    
            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">                            	 
                <div class="panel rounded shadow">
                    <div class="panel-body bg-twitter">
                    	<h4 class="text-center text-thin"><spring:message code="lbl.title.fique.olho"/></h4>		                                    
                        <ul class="inner-all list-unstyled">
                            <li class="text-center">
                                <img class="img-circle img-bordered-success" src="${context}${usuario.imagemArquivo}" style="width: 100px; height: 100px; ">
                            </li>
                            <li class="text-center">
                                <h4 class="text-capitalize">${usuario.nome}</h4>
                                <p class="text-muted text-capitalize">${usuario.perfilFmt}</p>
                            </li>
                            <li>
                                <a href="${urlUsuario}/detalhesUsuario/${usuario.id}" class="btn btn-success text-center btn-block"><spring:message code="lbl.btn.ver.usuario"/></a>
                            </li>
                        </ul><!-- /.list-unstyled -->
                    </div><!-- /.panel-body -->
                </div><!-- /.panel -->
            </div>
     	</c:forEach>
            		                        
 </div>