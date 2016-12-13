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
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                 <div class="header-content">
                    <h2><i class="fa fa-comments"></i><spring:message code="lbl.title.link.ajuda.conhecendo.funcionalidades"/><span></span></h2>                   
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
                                                <spring:message code="lbl.title.aba.menu.esquerdo"/>  <div class="cbp-filter-counter"></div>
                                            </div>
                                            <div data-filter=".superior" class="cbp-filter-item">
                                                <spring:message code="lbl.title.aba.menu.superior"/> <div class="cbp-filter-counter"></div>
                                            </div>                                          
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body">

                                <div id="grid-container" class="cbp cbp-l-grid-faq">
                                    <div class="cbp-item esquerdo">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-search"></i> <spring:message code="lbl.title.link.consultas"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                   <div class="row">
								                        <div class="col-md-12">
								
								                            <!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion1">
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion1" href="#collapse1-1">
								                                               <spring:message code="lbl.title.link.meus.imoveis"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse1-1" class="panel-collapse collapse in">
								                                        <div class="panel-body">
																			<spring:message code="msg.conhecendo.funcionalidades.meus.imoveis"/>																			
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion1" href="#collapse1-2">
								                                                <spring:message code="lbl.title.link.busca.imoveis"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse1-2" class="panel-collapse collapse">
								                                        <div class="panel-body">
																			<spring:message code="msg.conhecendo.funcionalidades.busca.imoveis"/>	
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion1" href="#collapse1-3">
								                                                <spring:message code="lbl.title.link.busca.usuarios"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse1-3" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="msg.conhecendo.funcionalidades.busca.usuarios"/>	
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                								                 
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion1" href="#collapse1-5">
								                                               <spring:message code="lbl.title.link.relatorios"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse1-5" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="msg.conhecendo.funcionalidades.relatorios"/>	
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion1" href="#collapse1-6">
								                                               <spring:message code="lbl.title.link.comparativo"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse1-6" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="msg.conhecendo.funcionalidades.comparativo"/>	
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                                 <% if ( request.getSession().getAttribute("habilitaFuncPlanos").equals("S") ) {%>
										                        	<div class="panel panel-default">
									                                    <div class="panel-heading no-border">
									                                        <h4 class="panel-title">
									                                            <a data-toggle="collapse" data-parent="#accordion1" href="#collapse1-7">
									                                               <spring:message code="lbl.title.link.planos"/>
									                                            </a>
									                                        </h4>
									                                    </div><!-- /.panel-heading -->
									                                    <div id="collapse1-7" class="panel-collapse collapse">
									                                        <div class="panel-body">
									                                            <spring:message code="msg.conhecendo.funcionalidades.planos"/>	
									                                        </div>
									                                    </div>
									                                </div><!-- /.panel -->										                        	
										                         <% }  %>
										                         
										                         <% if ( request.getSession().getAttribute("habilitaFuncServicos").equals("S") ) {%>
										                        	<div class="panel panel-default">
									                                    <div class="panel-heading no-border">
									                                        <h4 class="panel-title">
									                                            <a data-toggle="collapse" data-parent="#accordion1" href="#collapse1-8">
									                                               <spring:message code="lbl.title.link.servicos"/>
									                                            </a>
									                                        </h4>
									                                    </div><!-- /.panel-heading -->
									                                    <div id="collapse1-8" class="panel-collapse collapse">
									                                        <div class="panel-body">
									                                            <spring:message code="msg.conhecendo.funcionalidades.servicos"/>	
									                                        </div>
									                                    </div>
									                                </div><!-- /.panel -->	
										                        <% }  %>  
								                                
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->
                                                </div>
                                            </div>                                            
                                        
                                        </div>
                                    </div>
                                    <div class="cbp-item esquerdo">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-television"></i> <spring:message code="lbl.title.link.lista.Favoritos"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                     <div class="row">
								                        <div class="col-md-12">
								
								                            <!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion2">
								                                
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion2" href="#collapse2-1">
								                                                <spring:message code="lbl.title.link.lista.Favoritos.usuarios.interessados"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse2-1" class="panel-collapse collapse in">
								                                        <div class="panel-body">
								                                            <spring:message code="msg.conhecendo.funcionalidades.lista.Favoritos.usuarios.interessados"/>	
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion2" href="#collapse2-2">
								                                                <spring:message code="lbl.title.link.lista.Favoritos.meus.Favoritos"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse2-2" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="msg.conhecendo.funcionalidades.lista.Favoritos.meus.Favoritos"/>	
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                         
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->
								
								                        </div>
								                    </div><!-- /.row -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="cbp-item esquerdo">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-user"></i> <spring:message code="lbl.title.link.imoveis.visualizacoes"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                     <div class="row">
								                        <div class="col-md-12">
								
								                            <!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion3">
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion3" href="#collapse3-1">
								                                               <spring:message code="lbl.title.link.imoveis.visualizacoes.recebidas"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse3-1" class="panel-collapse collapse in">
								                                        <div class="panel-body">
								                                            <spring:message code="msg.conhecendo.funcionalidades.usuarios.visualizacoes.recebidas"/>	
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion3" href="#collapse3-2">
								                                               <spring:message code="lbl.title.link.imoveis.visualizacoes.minhas"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse3-2" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="msg.conhecendo.funcionalidades.usuarios.visitantes.minhas.visitas"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="cbp-item esquerdo">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-industry"></i> <spring:message code="lbl.title.link.intermediacoes"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                     <!-- Start accordion -->
							                            <div class="panel-group rounded shadow" id="accordion4">
							                                <div class="panel panel-default">
							                                    <div class="panel-heading no-border">
							                                        <h4 class="panel-title">
							                                            <a data-toggle="collapse" data-parent="#accordion4" href="#collapse4-1">
							                                               <spring:message code="lbl.title.link.aceita"/>
							                                            </a>
							                                        </h4>
							                                    </div><!-- /.panel-heading -->
							                                    <div id="collapse4-1" class="panel-collapse collapse in">
							                                        <div class="panel-body">
																		<c:if test="${usuario.perfil == 'cliente'}">
																			<spring:message code="lbl.conhecendo.funcionalidades.intermediacoes.aceitas.cliente"/>
																		</c:if>
																		<c:if test="${usuario.perfil != 'cliente'}">
																			<spring:message code="lbl.conhecendo.funcionalidades.intermediacoes.aceitas"/>
																		</c:if>
							                                        </div>
							                                    </div>
							                                </div><!-- /.panel -->
							                                
							                                <div class="panel panel-default">
							                                    <div class="panel-heading no-border">
							                                        <h4 class="panel-title">
							                                            <a data-toggle="collapse" data-parent="#accordion4" href="#collapse4-2">
							                                               <spring:message code="lbl.title.link.solicitada"/>
							                                            </a>
							                                        </h4>
							                                    </div><!-- /.panel-heading -->
							                                    <div id="collapse4-2" class="panel-collapse collapse">
							                                        <div class="panel-body">
																		<spring:message code="lbl.title.link.solicitacoes.recebidas"/>							                                            
							                                        </div>
							                                    </div>
							                                </div><!-- /.panel -->
							                                
							                                 <div class="panel panel-default">
							                                    <div class="panel-heading no-border">
							                                        <h4 class="panel-title">
							                                            <a data-toggle="collapse" data-parent="#accordion4" href="#collapse4-3">
							                                               <spring:message code="lbl.title.link.solicitacoes.enviadas"/>
							                                            </a>
							                                        </h4>
							                                    </div><!-- /.panel-heading -->
							                                    <div id="collapse4-3" class="panel-collapse collapse">
							                                        <div class="panel-body">
							                                            <spring:message code="lbl.conhecendo.funcionalidades.intermediacoes.enviadas"/>							                                            
							                                        </div>
							                                    </div>
							                                </div><!-- /.panel -->
							                                
							                            </div><!-- /.panel-group -->
							                            <!--/ End accordion -->		
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <c:if test="${usuario.perfil != 'P'}">
	                                    <div class="cbp-item esquerdo">
	                                        <div class="cbp-caption">
	                                            <div class="cbp-caption-defaultWrap">
	                                                <i class="fa fa-users""></i> <spring:message code="lbl.title.link.parcerias"/>
	                                            </div>
	                                            <div class="cbp-caption-activeWrap">
	                                                <div class="cbp-l-caption-body">
	                                                     <!-- Start accordion -->
							                            <div class="panel-group rounded shadow" id="accordion5">
							                                <div class="panel panel-default">
							                                    <div class="panel-heading no-border">
							                                        <h4 class="panel-title">
							                                            <a data-toggle="collapse" data-parent="#accordion5" href="#collapse5-1">
							                                              	<spring:message code="lbl.title.link.aceita"/>
							                                            </a>
							                                        </h4>
							                                    </div><!-- /.panel-heading -->
							                                    <div id="collapse5-1" class="panel-collapse collapse in">
							                                        <div class="panel-body">							                                            
																			<spring:message code="lbl.conhecendo.funcionalidades.parcerias.aceitas"/>																		
							                                        </div>
							                                    </div>
							                                </div><!-- /.panel -->
							                                
							                                <div class="panel panel-default">
							                                    <div class="panel-heading no-border">
							                                        <h4 class="panel-title">
							                                            <a data-toggle="collapse" data-parent="#accordion5" href="#collapse5-2">
							                                              <spring:message code="lbl.title.link.solicitacoes.recebidas"/>
							                                            </a>
							                                        </h4>
							                                    </div><!-- /.panel-heading -->
							                                    <div id="collapse5-2" class="panel-collapse collapse">
							                                        <div class="panel-body">
							                                            <spring:message code="lbl.conhecendo.funcionalidades.parcerias.solicitadas"/>
							                                        </div>
							                                    </div>
							                                </div><!-- /.panel -->
							                                
							                                 <div class="panel panel-default">
							                                    <div class="panel-heading no-border">
							                                        <h4 class="panel-title">
							                                            <a data-toggle="collapse" data-parent="#accordion5" href="#collapse5-3">
							                                               <spring:message code="lbl.title.link.solicitacoes.enviadas"/>
							                                            </a>
							                                        </h4>
							                                    </div><!-- /.panel-heading -->
							                                    <div id="collapse5-3" class="panel-collapse collapse">
							                                        <div class="panel-body">
							                                            <spring:message code="lbl.conhecendo.funcionalidades.parcerias.enviadas"/>	
							                                        </div>
							                                    </div>
							                                </div><!-- /.panel -->
							                                
							                            </div><!-- /.panel-group -->
							                            <!--/ End accordion -->		
	                                                </div>
	                                            </div>
	                                        </div>
	                                    </div>
                                    </c:if>
                                    <div class="cbp-item esquerdo">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-sticky-note-o"></i> <spring:message code="lbl.title.link.notas"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                    <div class="row">
								                        <div class="col-md-12">
								
								                            <!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion6">
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion6" href="#collapse6-1">
								                                               <spring:message code="lbl.title.link.minhas.notas"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse6-1" class="panel-collapse collapse in">
								                                        <div class="panel-body">
																			<spring:message code="lbl.conhecendo.funcionalidades.minhas.notas"/>								                                            
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion6" href="#collapse6-2">
								                                               <spring:message code="lbl.title.link.notas.contatos"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse6-2" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.notas.contatos"/>								                                            
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="cbp-item esquerdo">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-money"></i> <spring:message code="lbl.title.link.propostas.imoveis"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                    <div class="row">
								                        <div class="col-md-12">
								
								                            <!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion7">
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion7" href="#collapse7-1">
								                                               <spring:message code="lbl.title.link.propostas.imoveis.recebidas"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse7-1" class="panel-collapse collapse in">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.propostas.imoveis.recebidas"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion7" href="#collapse7-2">
								                                               <spring:message code="lbl.title.link.propostas.imoveis.enviadas"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse7-2" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.propostas.imoveis.enviadas"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="cbp-item esquerdo">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-arrows"></i> <spring:message code="lbl.title.link.imoveis.indicacoes"/> 
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                    <div class="row">
								                        <div class="col-md-12">
								
								                            <!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion8">
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion8" href="#collapse8-1">
								                                               <spring:message code="lbl.title.link.imoveis.indicacoes.recebidas"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse8-1" class="panel-collapse collapse in">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.indicacoes.recebidas"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion8" href="#collapse8-2">
								                                              	<spring:message code="lbl.title.link.imoveis.indicacoes.enviadas"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse8-2" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.indicacoes.enviadas"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>                                    
                                  
                                    <div class="cbp-item esquerdo">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-commenting"></i> <spring:message code="lbl.title.link.comentarios.imoveis"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                    <div class="row">
								                        <div class="col-md-12">
								
								                            <!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion9">
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion9" href="#collapse9-1">
								                                               <spring:message code="lbl.title.link.comentarios.imoveis.recebidos"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse9-1" class="panel-collapse collapse in">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.comentarios.imoveis.recebidos"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->								                                								                                								                              
								                                
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->								                    
                                                </div>
                                            </div>
                                        </div>
                                    </div>                                    
                                    
                                     <div class="cbp-item superior">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-user"></i> <spring:message code="lbl.contatos"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                    <div class="row">
								                        <div class="col-md-12">
								
								                            <!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion10">
															
								                                 <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion10" href="#collapse10-1">
								                                              	<spring:message code="lbl.title.link.convites"/> 
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse10-1" class="panel-collapse collapse in">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.convites"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->								                              
								                                
								                                 <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion10" href="#collapse10-2">
								                                              	<spring:message code="lbl.contatos"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse10-2" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.contatos"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->	
																
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->
								                    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="cbp-item superior">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-envelope-o"></i> <spring:message code="lbl.title.link.mensagens"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                    <div class="row">
								                        <div class="col-md-12">									                            
														
														<!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion13">
								                                 <div class="panel panel-default">					
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.mensagens"/>
								                                        </div>
								                                </div><!-- /.panel -->
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->
								                    
                                                </div>
                                            </div>
                                        </div>
                                    </div> 
                                    
                                    <div class="cbp-item superior">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-bell-o"></i> <spring:message code="lbl.title.link.notificacoes"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                    <div class="row">
								                        <div class="col-md-12">														
														<!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion12">
								                                 <div class="panel panel-default">
							                                        <div class="panel-body">
							                                            <spring:message code="lbl.conhecendo.funcionalidades.notificacoes"/>
							                                        </div>								                                    
								                                </div><!-- /.panel -->								                              
								                                
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>                                    
                                   
                                    <div class="cbp-item superior">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-envelope-square"></i> <spring:message code="lbl.title.link.mensagens.admin"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                    <div class="row">
								                        <div class="col-md-12">														
														
															<!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion13">
								                                <div class="panel panel-default">
							                                        <div class="panel-body">
							                                            <spring:message code="lbl.conhecendo.funcionalidades.mensagens.admin"/>
							                                        </div>
								                                </div><!-- /.panel -->
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->
								                    
                                                </div>
                                            </div>
                                        </div>
                                    </div> 
                                    
                                    <div class="cbp-item superior">
                                        <div class="cbp-caption">
                                            <div class="cbp-caption-defaultWrap">
                                                <i class="fa fa-user"></i> <spring:message code="lbl.title.conta"/>
                                            </div>
                                            <div class="cbp-caption-activeWrap">
                                                <div class="cbp-l-caption-body">
                                                    <div class="row">
								                        <div class="col-md-12">
														
														  <!-- Start accordion -->
								                            <div class="panel-group rounded shadow" id="accordion14">
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion14" href="#collapse1-1">
								                                              <spring:message code="lbl.title.link.perfil"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse14-1" class="panel-collapse collapse in">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.perfil"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->	
								                                
								                                 <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion14" href="#collapse14-2">
								                                              	<spring:message code="lbl.title.aba.editar.perfil"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse14-2" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.editar.perfil"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion14" href="#collapse14-3">
								                                              	<spring:message code="lbl.title.aba.alterar.foto.perfil"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse14-3" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.foto.perfil"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->
								                                
								                                <div class="panel panel-default">
								                                    <div class="panel-heading no-border">
								                                        <h4 class="panel-title">
								                                            <a data-toggle="collapse" data-parent="#accordion14" href="#collapse14-4">
								                                              	<spring:message code="lbl.title.logout"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse14-4" class="panel-collapse collapse">
								                                        <div class="panel-body">
								                                            <spring:message code="lbl.conhecendo.funcionalidades.logout"/>
								                                        </div>
								                                    </div>
								                                </div><!-- /.panel -->			                              
								                                
								                            </div><!-- /.panel-group -->
								                            <!--/ End accordion -->								
								                        </div>
								                    </div><!-- /.row -->
								                    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>

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