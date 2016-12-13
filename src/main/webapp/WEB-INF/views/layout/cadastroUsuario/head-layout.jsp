<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>
  
  <!-- START @META SECTION -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="description" content="Blankon is a theme fullpack admin template powered by Twitter bootstrap 3 front-end framework. Included are multiple example pages, elements styles, and javascript widgets to get your project started.">
        <meta name="keywords" content="admin, admin template, bootstrap3, clean, fontawesome4, good documentation, lightweight admin, responsive dashboard, webapp">
        <meta name="author" content="Djava UI">
        <title>Busque um Lugar</title>
        <!--/ END META SECTION -->


        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
        <!-- START @CORE PLUGINS -->
        <script src="${context}/assets/global/plugins/bower_components/jquery/dist/jquery.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/jquery-cookie/jquery.cookie.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/typehead.js/dist/handlebars.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/typehead.js/dist/typeahead.bundle.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/jquery-nicescroll/jquery.nicescroll.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/jquery.sparkline.min/index.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/jquery-easing-original/jquery.easing.1.3.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/ionsound/js/ion.sound.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/bootbox/bootbox.js"></script>        
        <script src="${context}/assets/global/plugins/bower_components/bootstrap-fileupload/js/bootstrap-fileupload.min.js"></script>
        <!--/ END CORE PLUGINS -->

        <!-- START @PAGE LEVEL PLUGINS -->
        <script src="${context}/assets/global/plugins/bower_components/bootstrap-session-timeout/dist/bootstrap-session-timeout.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/raphael/raphael-min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/flot/jquery.flot.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/flot/jquery.flot.spline.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/flot/jquery.flot.categories.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/flot/jquery.flot.tooltip.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/flot/jquery.flot.resize.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/flot/jquery.flot.pie.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/morrisjs/morris.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/jquery.gritter/js/jquery.gritter.min.js"></script>
        <script src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
        <script src="${context}/assets/global/plugins/bower_components/gmap3/dist/gmap3.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/bootstrap-datepicker-vitalets/js/bootstrap-datepicker.js"></script>
        
        <script src="${context}/assets/global/plugins/bower_components/moment/min/moment.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/jquery-validation/dist/jquery.validate.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/twitter-bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
        <script src="${context}/assets/admin/js/pages/blankon.form.wizard.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/jquery-autosize/jquery.autosize.min.js"></script>
        <script src="${context}/assets/admin/js/pages/blankon.search.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/chosen_v1.2.0/chosen.jquery.min.js"></script>
        
        <script src="${context}/assets/global/plugins/bower_components/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/jasny-bootstrap-fileinput/js/jasny-bootstrap.fileinput.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/holderjs/holder.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/bootstrap-maxlength/bootstrap-maxlength.min.js"></script>
        
        <!--/ END PAGE LEVEL PLUGINS -->

        <!-- START @PAGE LEVEL SCRIPTS -->
        <script src="${context}/assets/admin/js/apps.js"></script>
        <script src="${context}/assets/admin/js/pages/blankon.dashboard.ecommerce.js"></script>
        <script src="${context}/assets/admin/js/pages/blankon.form.picker.js"></script>
        <script src="${context}/assets/admin/js/demo.js"></script>
        <script src="${context}/assets/admin/js/pages/blankon.form.element.js"></script>
        <!--/ END PAGE LEVEL SCRIPTS -->
        <!--/ END JAVASCRIPT SECTION -->
        
        <!-- INICIO -->
         <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700" rel="stylesheet">
        <link href="http://fonts.googleapis.com/css?family=Oswald:700,400" rel="stylesheet">
        <!--/ END FONT STYLES -->

        <!-- START @GLOBAL MANDATORY STYLES -->
        <link href="${context}/assets/global/plugins/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
        <!--/ END GLOBAL MANDATORY STYLES -->

        <!-- START @PAGE LEVEL STYLES -->
        <link href="${context}/assets/global/plugins/bower_components/fontawesome/css/font-awesome.min.css" rel="stylesheet">        
        <link href="${context}/assets/global/plugins/bower_components/animate.css/animate.min.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/chosen_v1.2.0/chosen.min.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/bootstrap-datepicker-vitalets/css/datepicker.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/mjolnic-bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/bootstrap-fileupload/css/bootstrap-fileupload.min.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/jasny-bootstrap-fileinput/css/jasny-bootstrap-fileinput.min.css" rel="stylesheet">
                
        <!--/ END PAGE LEVEL STYLES -->

        <!-- START @THEME STYLES -->
        <link href="${context}/assets/admin/css/reset.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/layout.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/components.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/plugins.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/themes/default.theme.css" rel="stylesheet" id="theme">
        <link href="${context}/assets/admin/css/pages/search.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/pages/sign.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/custom.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/pages/gallery.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/components/form.css" rel="stylesheet">        
        <!-- FIM -->

        <!-- START @IE SUPPORT -->
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${context}/assets/global/plugins/bower_components/html5shiv/dist/html5shiv.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/respond-minmax/dest/respond.min.js"></script>
        <![endif]-->
        <!--/ END IE SUPPORT -->