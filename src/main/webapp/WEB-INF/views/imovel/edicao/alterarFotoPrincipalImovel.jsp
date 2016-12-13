<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>   

    <!-- START @HEAD -->
 
<script type="text/javascript">

$(document).ready(function() {

});	

</script>
		
<c:import url="../../layout/head-layout.jsp"></c:import>

    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../../layout/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../../layout/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.aba.alterar.foto.imovel"/> </h2>                                                                        
                </div><!-- /.header-content -->                
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">                   	
                   		
                   		<div class="col-md-6">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.alterar.foto.imovel.atual"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                      <div class="form-body">
                                      	<div class="form-group">                                              
                                              <div class="col-sm-7">
                                                  <ul class="list-unstyled">
				                                        <li class="text-center">				                                        
				                                            <img class="img-circle img-bordered-primary" src="${context}${imovelForm.imagemArquivo}" style="width: 200px; height: 200px; " alt="Tol Lee">
				                                        </li>
				                                   </ul>     	
                                              </div>
                                          </div><!-- /.form-group -->
                                      </div>
                               </div>
                           </div>
                       </div>              
                   		
                   	   <div class="col-md-6">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.alterar.foto.imovel.nova"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">
                                      <div class="form-body">
                                      
											<form:form id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/alterarFotoPrincipalImovel" class="form-horizontal mt-10" enctype="multipart/form-data" method="POST">
												<form:hidden path="id" value="${imovelForm.id}"/>
												<div class="form-group">
	                                                <label class="control-label"></label>
	                                                <div class="fileinput fileinput-new" data-provides="fileinput">
	                                                    <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px;"></div>
	                                                    <div>
	                                                        <span class="btn btn-info btn-file"><span class="fileinput-new">Selecionar Foto1</span><span class="fileinput-exists">Selecionar Foto2</span>	                                                        
	                                                        <input type="text" name="name"/>
															<input type="file" name="file"/>
	                                                        
	                                                        </span>
	                                                        <a href="#" class="btn btn-danger fileinput-exists" data-dismiss="fileinput">Remover</a>
	                                                    </div>
	                                                </div>
	                                            </div><!-- /.form-group -->
	                                            
	                                            <button type="submit" class="btn btn-primary btn-block">Salvar Foto</button>    
											</form:form>

                                      </div>
                               </div>
                           </div>
                       </div>	
                		                               
                    </div><!-- /.row -->
                    
                </div><!-- /.body-content -->
         
            </section><!-- /#page-content -->
            
            

        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <!-- START @BACK TOP -->
        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
        <!--/ END BACK TOP -->

        <!-- START JAVASCRIPT SECTION (Load javascripts at bottom to reduce load time) -->
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
        <!--/ END JAVASCRIPT SECTION -->

        <!-- START GOOGLE ANALYTICS -->
        <script>
            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-55892530-1', 'auto');
            ga('send', 'pageview');

        </script>
        <!--/ END GOOGLE ANALYTICS -->

    </body>
    <!--/ END BODY -->

</html>