<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/imovel" var="urlImovel"/>
<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${context}/js/jquery-ui.js"></script>

<div class="row">                            
                            
         <c:forEach var="imovel" items="${listaSugestaoImoveis}">         
	         <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
	              <div class="mini-stat-type-2 shadow border-primary">
	                  <h3 class="text-center text-thin" style="font-size: 18px"><spring:message code="lbl.title.sugestao.imoveis"/></h3>           
	                  <ul class="inner-all list-unstyled">
	                       <li class="text-center">
	                           <img class="img-circle img-bordered-success" src="${context}${imovel.imagemArquivo}" style="width: 100px; height: 100px; ">
	                       </li>
	                       <li class="text-center">
	                           <h4 class="text-capitalize" style="font-size: 15px">${imovel.tipoImovelFmt}</h4>                                
	                           <p class="text-muted text-capitalize">${imovel.acaoFmt}</p>
	                           <p class="text-muted text-capitalize">R$ <fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/></p>                                 
	                           <p class="text-muted text-capitalize" style="font-size: 11px"><spring:message code="lbl.quartos.dormitorios.resum"/>: ${imovel.quantQuartos} | <spring:message code="lbl.area.m2.resum"/>: ${imovel.area}</p>
	                       </li>  
	                       <li>  
	                           <a href="${urlImovel}/detalhesImovel/${imovel.id}" class="btn btn-primary  text-center btn-block"><spring:message code="lbl.btn.ver.imovel.geral"/></a>
	                       </li>
	                   </ul><!-- /.list-unstyled -->
	              </div>
	          </div>         
		</c:forEach>            		                        
 </div>