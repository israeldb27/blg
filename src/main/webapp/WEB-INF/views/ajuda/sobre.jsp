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
                    <h2><i class="fa fa-comments"></i><spring:message code="lbl.title.link.ajuda.sobre"/><span></span></h2>                   
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
                                                <spring:message code="lbl.title.aba.menu.sobre"/> <div class="cbp-filter-counter"></div>
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
                                                <i class="fa fa-search"></i> <spring:message code="lbl.title.aba.sobre"/>
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
								                                               <spring:message code="msg.sobre.objetivo.1"/>
								                                            </a>
								                                        </h4>
								                                    </div><!-- /.panel-heading -->
								                                    <div id="collapse1-1" class="panel-collapse collapse in">
								                                        <div class="panel-body">
																			   <spring:message code="msg.sobre.objetivo.resposta.1"/>																			
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