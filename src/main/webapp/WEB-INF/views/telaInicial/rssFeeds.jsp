<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/imovel" var="urlImovel"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${context}/js/jquery-ui.js"></script>
<script type="text/javascript" src="${context}/js/jquery.rss.js"></script>

<script>
      jQuery(function($) {
       
    	 var op1 =  "http://www.infomoney.com.br/imoveis/rss";
    	 var op2 =  "http://feeds.feedburner.com/EXAME-imoveis";
    	 var op3 =  "http://feeds.feedburner.com/EXAME-Mercados?format=xml";
    	 var op4 =  "http://www.infomoney.com.br/imoveis/investimentos-imobiliarios/rss";
    	 var op5 =  "http://www.infomoney.com.br/imoveis/fundos-imobiliarios/rss";
    	 var op6 =  "http://www.infomoney.com.br/imoveis/rss";
    	 var op7 =  "http://www.infomoney.com.br/blogs/imoveis-e-renda/rss";    	 
    	 var op8 =  "http://www.valor.com.br/financas/divida-mobiliaria/rss";
    	 
    	 
    	 var sel = "";
    	 var number = 1 + Math.floor(Math.random() * 4);
    	 if (number == 1){
    		 sel = op1;
    	 }	 
    	 else if (number == 2){
    		 sel = op2;
    	 }
    	 else if (number == 3){
    		 sel = op3;
    	 }
    	 else if (number == 4){
    		 sel = op4;
    	 }    	 
    	 else if (number == 5){
    		 sel = op5;
    	 }
    	 else if (number == 6){
    		 sel = op6;
    	 }
    	 else if (number == 7){
    		 sel = op7;
    	 }
    	 else if (number == 8){
    		 sel = op8;
    	 }

        $("#rss-feeds").rss( sel , {
          limit: 10,
          effect: 'slideFastSynced'
        })
      })
    </script>

<div class="row">                            
                            
        <div class="col-lg-9 col-md-12 col-sm-9"> 
			<div class="panel rounded shadow">                         
			  <div class="panel-heading">
					<div class="pull-left"> 
						<h3 class="panel-title"><spring:message code="lbl.title.ultimas.noticias"/></h3>
					</div><!-- /.pull-left -->
					<div class="pull-right">
						 
					</div><!-- /.pull-right -->
					<div class="clearfix"></div>
				</div><!-- /.panel-heading -->
				<div class="panel-body no-padding">
						
					<div id="rss-feeds"></div>
										            
				</div><!-- /.panel-body -->				
			</div>                                                                                
        </div>
            		                        
 </div>