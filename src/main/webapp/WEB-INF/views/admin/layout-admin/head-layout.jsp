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


        <!-- START @FONT STYLES -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700" rel="stylesheet">
        <link href="http://fonts.googleapis.com/css?family=Oswald:700,400" rel="stylesheet">
        <!--/ END FONT STYLES -->

        <!-- START @GLOBAL MANDATORY STYLES -->
        <link href="${context}/assets/global/plugins/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
        <!--/ END GLOBAL MANDATORY STYLES -->

        <!-- START @PAGE LEVEL STYLES -->
        <link href="${context}/assets/global/plugins/bower_components/fontawesome/css/font-awesome.min.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/animate.css/animate.min.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/dropzone/downloads/css/dropzone.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/jquery.gritter/css/jquery.gritter.css" rel="stylesheet">
        <link href="${context}/assets/global/plugins/bower_components/simple-line-icons/css/simple-line-icons.css" rel="stylesheet">
        <!--/ END PAGE LEVEL STYLES -->

        <!-- START @THEME STYLES -->
        <link href="${context}/assets/admin/css/reset.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/layout.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/components.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/plugins.css" rel="stylesheet">
        <link href="${context}/assets/admin/css/themes/default.theme.css" rel="stylesheet" id="theme">
        <link href="${context}/assets/admin/css/custom.css" rel="stylesheet">
        <!--/ END THEME STYLES -->

        <!-- START @IE SUPPORT -->
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${context}/assets/global/plugins/bower_components/html5shiv/dist/html5shiv.min.js"></script>
        <script src="${context}/assets/global/plugins/bower_components/respond-minmax/dest/respond.min.js"></script>
        <![endif]-->
        <!--/ END IE SUPPORT -->