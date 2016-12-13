<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
   
<html> 

    <head>
        <c:import url="./layout-admin/head-layout.jsp"></c:import>
    </head>

    <body class="page-session page-sound page-header-fixed page-sidebar-fixed demo-dashboard-session">

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->
            	<c:import url="./layout-admin/header.jsp"></c:import>
            <!--/ END HEADER -->

         	<!-- START SIDEBAR LEFT -->
            	<c:import url="./layout-admin/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">

                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                        <div class="col-md-9"> 

                        </div>
                    </div><!-- /.row -->
                </div><!-- /.body-content -->
				
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->

            <!-- START @SIDEBAR RIGHT -->
            <aside id="sidebar-right">

                <div class="panel panel-tab">
                    <div class="panel-heading no-padding">
                        <div class="pull-right">
                           
                        </div>
                        <div class="clearfix"></div>
                    </div>                    
                </div>

            </aside><!-- /#sidebar-right -->
            <!--/ END SIDEBAR RIGHT -->
        </section><!-- /#wrapper -->

       <c:import url="./layout-admin/head-bootstrap.jsp"></c:import>

    </body>
</html>
