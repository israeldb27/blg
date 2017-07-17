<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<spring:url value="/ajuda" var="urlAjuda"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.maskMoney.js"></script>
 
 <script type="text/javascript">
    	$(document).ready(function() {    	
    		
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
                    <h2><i class="fa fa-comments"></i><spring:message code="lbl.title.link.ajuda.faq"/><span></span></h2>                   
                </div><!-- /.header-content -->
                <!--/ End header content -->
            	
            	  <div class="body-content animated fadeIn">

                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel shadow panel-primary">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"></h3>
                                    </div>
                                    <div class="pull-right">
                                        <div id="filters-container" class="cbp-l-filters-underline cbp-l-filters-text no-margin">
                                            <div data-filter=".esquerdo" class="cbp-filter-item-active cbp-filter-item">
                                                <spring:message code="lbl.title.aba.duvidas.frequentes"/> 
                                            </div>                                       
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body" style="font-size: 11px;">
                                     <div class="row">                                                   		
				                        <div class="col-md-12">
				                        <br><br>														
				                            <!-- Start accordion -->
				                            <div class="panel-group rounded shadow" id="accordion1">
				                                <div class="panel panel-default">
				                                    <div class="panel-heading no-border active">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-1">
				                                               <spring:message code="msg.faq.pergunta.1"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-1" class="panel-collapse collapse in">
				                                        <div class="panel-body" style="font-size: 11px;">
															   <spring:message code="msg.faq.resposta.1"/>																			
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
				                                
				                                <div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-2">
				                                                <spring:message code="msg.faq.pergunta.2"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-2" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
															    <spring:message code="msg.faq.resposta.2"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
				                                
				                                <div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-3">
				                                                <spring:message code="msg.faq.pergunta.3"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-3" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.3"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-4">
				                                                <spring:message code="msg.faq.pergunta.4"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-4" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.4"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-5">
				                                                <spring:message code="msg.faq.pergunta.5"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-5" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.5"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-6">
				                                                <spring:message code="msg.faq.pergunta.6"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-6" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.6"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-7">
				                                                <spring:message code="msg.faq.pergunta.7"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-7" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.7"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-8">
				                                                <spring:message code="msg.faq.pergunta.8"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-8" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.8"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-9">
				                                                <spring:message code="msg.faq.pergunta.9"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-9" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.9"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-10">
				                                                <spring:message code="msg.faq.pergunta.10"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-10" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.10"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-11">
				                                                <spring:message code="msg.faq.pergunta.imoveis.visualizados"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-11" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.imoveis.visualizados"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-12">
				                                                <spring:message code="msg.faq.pergunta.propostas.imoveis"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-12" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.propostas.imoveis"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-13">
				                                                <spring:message code="msg.faq.pergunta.lista.interesse"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-13" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.lista.interesse"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-14">
				                                                <spring:message code="msg.faq.pergunta.indicacoes"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-14" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.indicacoes"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-15">
				                                                <spring:message code="msg.faq.pergunta.comentarios"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-15" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.comentarios"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-16">
				                                                <spring:message code="msg.faq.pergunta.notas"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-16" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.notas"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-17">
				                                                <spring:message code="msg.faq.pergunta.notificacoes"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-17" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.notificacoes"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-18">
				                                                <spring:message code="msg.faq.pergunta.meus.imoveis"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-18" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.meus.imoveis"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-19">
				                                                <spring:message code="msg.faq.pergunta.consulta.imoveis"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-19" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.consulta.imoveis"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-20">
				                                                <spring:message code="msg.faq.pergunta.consulta.usuarios"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-20" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.consulta.usuarios"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-21">
				                                                <spring:message code="msg.faq.pergunta.buscar.usuarios.pref"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-21" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.buscar.usuarios.pref"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-22">
				                                                <spring:message code="msg.faq.pergunta.pref.localidades"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-22" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.pref.localidades"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<div class="panel panel-default">
				                                    <div class="panel-heading no-border">
				                                        <h4 class="panel-title">
				                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-23">
				                                                <spring:message code="msg.faq.pergunta.comparativo"/>
				                                            </a>
				                                        </h4>
				                                    </div><!-- /.panel-heading -->
				                                    <div id="collapse1-23" class="panel-collapse collapse">
				                                        <div class="panel-body" style="font-size: 11px;">
				                                            <spring:message code="msg.faq.resposta.comparativo"/>	
				                                        </div>
				                                    </div>
				                                </div><!-- /.panel -->
												
												<% if ( request.getSession().getAttribute("habilitaFuncPlanos").equals("S") ) {%>
													<div class="panel panel-default">
					                                    <div class="panel-heading no-border">
					                                        <h4 class="panel-title">
					                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-24">
					                                                <spring:message code="msg.faq.pergunta.planos"/>
					                                            </a>
					                                        </h4>
					                                    </div><!-- /.panel-heading -->
					                                    <div id="collapse1-24" class="panel-collapse collapse">
					                                        <div class="panel-body" style="font-size: 11px;">
					                                            <spring:message code="msg.faq.resposta.planos"/>	
					                                        </div>
					                                    </div>
					                                </div><!-- /.panel -->
				                                 <% }  %>
												
												<% if ( request.getSession().getAttribute("habilitaFuncServicos").equals("S") ) {%>
													<div class="panel panel-default">
					                                    <div class="panel-heading no-border">
					                                        <h4 class="panel-title">
					                                            <a data-toggle="collapse" data-parent="#accordion1"  style="font-size: 13px;" href="#collapse1-25">
					                                                <spring:message code="msg.faq.pergunta.servicos"/>
					                                            </a>
					                                        </h4>
					                                    </div><!-- /.panel-heading -->
					                                    <div id="collapse1-25" class="panel-collapse collapse">
					                                        <div class="panel-body" style="font-size: 11px;">
					                                            <spring:message code="msg.faq.resposta.servicos"/>	
					                                        </div>
					                                    </div>
					                                </div><!-- /.panel -->
				                                 <% }  %>
				                                
				                            </div><!-- /.panel-group -->
				                            <!--/ End accordion -->								
				                        </div>
				                    </div><!-- /.row -->
                                 
                                </div><!-- /.panel-body -->
                            </div>
                        </div>
                    </div><!-- /.row -->

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
  			<c:import url="../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->
    </body>
    <!--/ END BODY -->

</html>