<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/imovel/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/imovel/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/gmap" var="urlGmap"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<style type="text/css">
div#map_container{
	width:100%;
	height:350px;
}
</style>
<script type="text/javascript"    src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>		

<script type="text/javascript">

$( document ).ready(function() {	
	
});

  function loadMap() {	  
    var latlng = new google.maps.LatLng(document.getElementById("latitude").value, document.getElementById("longitude").value);
    document.getElementById("latitudeFmt").value = document.getElementById("latitude").value;
	document.getElementById("longitudeFmt").value = document.getElementById("longitude").value;
    var myOptions = {
      zoom: 16,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map"),myOptions);
	
    var marker = new google.maps.Marker({
      position: latlng, 
      map: map, 
      title:"my hometown, Malim Nawar!"
    }); 
  
  }
  
  function geocoder() {
	   var address = document.getElementById("address").value;	   
	   var geocoder = new google.maps.Geocoder();
	   //alert("ads:"+address);
	     
	   geocoder.geocode({
		  'address' : address
	   }, function(results, status) {
		  if (status == google.maps.GeocoderStatus.OK) {
			 // Calls the remoteCommand "rmtCommandGeocoder", 
			 // passing the coordinates to map of parameters
			document.getElementById("latitudeFmt").value = results[0].geometry.location.lat();
			document.getElementById("longitudeFmt").value = results[0].geometry.location.lng();
   		
			var latlng = new google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng());
			 var myOptions = {
				      zoom: 16,
				      center: latlng,
				      mapTypeId: google.maps.MapTypeId.ROADMAP
				    };
				    var map = new google.maps.Map(document.getElementById("map"),myOptions);
					
				    var marker = new google.maps.Marker({
				      position: latlng, 
				      map: map, 
				      title:"my hometown, Malim Nawar!"
				    }); 
		  } 
	   });
	}	
	
</script>
		
        <c:import url="../../layout/head-layout.jsp"></c:import>

    <body onload="loadMap()">

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
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.cadastrar.imovel.mapa"/>  </h2>                                                                        
					
					<!-- Start header modal Ajuda - funcionalidade -->
						<c:import url="../../ajuda/headerMenuModal.jsp"></c:import>																				
					<!-- End header  modal Ajuda - funcionalidade -->
					
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">   	

                   <form:form id="imovelForm" modelAttribute="imovelForm" action="${urlImovel}/cadastrarMapaImovel" class="form-horizontal mt-10">
                   	<form:hidden path="latitude" id="latitude"/>
    				<form:hidden path="longitude" id="longitude"/>
                     <div class="row"> 	
                    	<!--/ INICIO ABA LOCALIZACAO -->
                    	<div class="col-md-12">
                            <!-- Start horizontal form -->
                            <div class="panel rounded shadow">
                                <div class="panel-heading">  
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.title.aba.imovel.localizacao"/> <code></code></h3>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-sm" data-action="collapse" data-container="body" data-toggle="tooltip" data-placement="top" data-title="Collapse"><i class="fa fa-angle-up"></i></button>
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                <div class="panel-body no-padding">

                                        <div class="form-body">
                                        
                                            <div class="form-group">
                                                <label for="endereco" class="col-sm-3 control-label">Informe o seu endereço</label>
                                                <div class="col-sm-9">                                                    
                                                     <form:input id="address" path="endereco" />
                    								 <button type="button" id="btnaddress" onClick="geocoder()" class="btn btn-success">Localizar Mapa</button> 
                                                </div>
                                            </div><!-- /.form-group -->
                                            
                                            <div class="form-group">
                                              <div id="result"></div>
											  <br/>
											  <div style="width:900px;height:400px" id="map"></div>
											</div>
											
											<div class="form-group">
                                            	<form:input path="longitudeFmt"  id="longitudeFmt"/>
												<form:input path="latitudeFmt"  id="latitudeFmt"/>
											</div>   
                                            
                                        </div><!-- /.form-body -->
                                        
                                        <div class="form-footer">  
                                            <div class="col-sm-offset-3">
                                                <button type="subtmi" id="btnaddress" class="btn btn-success">Salvar Mapa</button>
                                            </div>
                                        </div><!-- /.form-footer -->    
                                        
                                </div><!-- /.panel-body -->
                                
                                 <div class="form-footer" >  
	                              <div  align="center">	                                  
	                                  <a href="${urlImovel}/confirmarCadastroImovel/${imovelForm.id}" class="btn btn-success"><spring:message code="lbl.btn.confirmar.dados.geral"/></a>
	                              </div>
	                          </div><!-- /.form-footer --> 
                                
                                
                            </div><!-- /.panel -->
                            <!--/ End horizontal form -->
                        </div>
						<!--/ FIM ABA LOCALIZACAO -->
                     
                        </div><!-- /.row -->
                       </form:form> 
                            
                </div><!-- /.body-content -->
                <!--/ End body content -->
                
                <!-- Fim Editar Usuario -->          
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->     
			
			

			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->

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

       

    </body>
    <!--/ END BODY -->

</html>