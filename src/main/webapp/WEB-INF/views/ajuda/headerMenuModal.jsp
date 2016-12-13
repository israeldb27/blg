<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

 
 <div class="breadcrumb-wrapper hidden-xs">                        
	<ol class="breadcrumb">
		<li>                            	
			<button type="button" class="btn btn-default" data-toggle="modal" data-target=".bs-modal-ajuda-informacoes"><i style="font-size: 18px" class="fa fa-question-circle"></i></button>                                                                
		</li>
		<li class="active"></li>
	</ol>
</div><!-- /.breadcrumb-wrapper -->   