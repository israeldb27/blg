<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:url value="/servico" var="urlServico"/> 	
 	<div class="col-md-12">
        <div class="panel-body no-padding panel-teal">                                   
			<div class="callout callout-info mb-20">												
	            <p><spring:message code="lbl.aviso.renovacao.assinatura"/></p>
	            <a href="${urlServico}/prepararRenovacaoAssinatura">Clique aqui Renovar Assinatura</a>
	        </div>
	        
	        
        </div><!-- /.panel-body -->
    </div>  