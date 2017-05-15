<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/imovelComparativo" var="urlImovelComparativo"/>
<spring:url var="urlImovelComentario" value="/imovelComentario"/>
<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url var="urlImovel" value="/imovel"/>
<spring:url value="/usuario" var="urlUsuario"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
 
<script type="text/javascript">

$(document).ready(function() {

});	

function desmarcarCheck(id) {
    $.post("${urlImovelComentario}/desmarcarCheck", {'idImovelcomentario' : id}, function() {  
      $("#idCheckImovelDiv_"+id).hide();
    	$("#idCheckImovel_"+id).hide();
    });
  }

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
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.link.comentarios.imoveis"/> </h2>                                                                        
					
					 <!-- Start header modal Ajuda - funcionalidade -->
						<c:import url="../../ajuda/headerMenuModal.jsp"></c:import>																				
					<!-- End header  modal Ajuda - funcionalidade -->
					
                </div><!-- /.header-content -->                
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
        
                   		<!-- START Foto Perfil e Informações Contato -->
                   			<div class="col-lg-3 col-md-3 col-sm-4">

		                        <div class="panel rounded shadow">
		                            <div class="panel-body">
		                                <div class="inner-all">
		                                    <ul class="list-unstyled">
		                                        <li class="text-center">	
													<a href="${urlImovel}/detalhesImovel/${imovel.id}" ><img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${imovel.imagemArquivo}"  style="width: 200px; height: 200px; "></a>		                                            
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize"><a href="${urlImovel}/detalhesImovel/${imovel.id}" >${imovel.titulo}</a></h4>
		                                            <p class="text-muted text-capitalize">${imovel.tipoImovelFmt} </p>
		                                        </li>		                                    
		                                        <li><br/></li>		                                      
		                                    </ul>
		                                </div>
		                            </div>
		                        </div><!-- /.panel -->				                        
		
		                    </div>
                   		<!-- END Foto Perfil e Informações Contato-->
                   		
                   		<!-- START Painel de Informações -->
                   			<div class="col-lg-9 col-md-9 col-sm-8">

			                    <div class="profile-cover">
			                         <!-- Start vertical tabs with pagination -->
		                            <div id="basic-wizard-vertical">
		                                <div class="panel panel-tab panel-tab-double panel-tab-vertical row no-margin mb-15 rounded shadow">
		                                    <!-- Start tabs heading -->
		                                    <div class="panel-heading no-padding col-lg-3 col-md-3 col-sm-3">
		                                        <ul class="nav nav-tabs">
		                                            <li class="active">
		                                                <a href="#tab4-1" data-toggle="tab">
		                                                    <i class="fa fa-home"></i>
		                                                    <div>
		                                                        <span class="text-strong"><spring:message code="lbl.todas.propostas.info.imovel"/></span>                                                        
		                                                    </div>
		                                                </a>
		                                            </li>
		                                                                                       
		                                        </ul>
		                                    </div><!-- /.panel-heading -->
		                                    <!--/ End tabs heading -->
		
		                                    <!-- Start tabs content -->
		                                    <div class="panel-body col-lg-9 col-md-9 col-sm-9">
		                                        <div class="tab-content">
		                                            <div class="tab-pane fade in active" id="tab4-1">
		                                            	 
		                                            	  <div class="form-group">
		                                                		<strong class="col-sm-5"><spring:message code="lbl.valor.imovel"/></strong> <fmt:formatNumber value="${imovel.valorImovel}" pattern="#,##0.00;-0"/>
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            		                                            	 
		                                            	 <div class="form-group"> 
		                                                		<strong class="col-sm-5"><spring:message code="lbl.localidade"/> </strong> ${imovel.bairro} - ${imovel.cidade}, ${imovel.uf}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	  <div class="line"></div>
		                                            		                                            	 
		                                            	 <div class="form-group"> 
		                                                		<strong class="col-sm-5"><spring:message code="lbl.acao.imovel"/> </strong> ${imovel.acaoFmt} 
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            		                                            	 
		                                            	 <div class="form-group"> 
		                                                		<strong class="col-sm-5"><spring:message code="lbl.status.imovel"/> </strong> ${imovel.perfilImovelFmt} 
		                                            	 </div><!-- /.form-group -->		
		                                            	 
		                                            	 <div class="line"></div>		                                            	                        	 
		                                            	 
		                                            	 <div class="form-group">																				            
							                              		<strong class="col-sm-5"><spring:message code="lbl.data.ultima.imovel.atualizacao.resum"/> </strong> <fmt:formatDate value='${imovel.dataUltimaAtualizacao}' pattern='dd/MM/yyyy'/>
							                             </div><!-- /.form-group -->	
		                                            	 
		                                            	 <div class="line"></div>		                                            	 
		                                            	 
		                                            	 <div class="form-group">																				            
							                              		<strong class="col-sm-5"><spring:message code="lbl.data.cadastro.imovel"/> </strong> <fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/>
							                             </div><!-- /.form-group -->
		                                            </div>                          
		                                        </div>                                       
		                                    </div><!-- /.panel-body -->
		                                    <!--/ End tabs content -->
		                                </div><!-- /.panel -->
		                            </div>
		                         
			                    </div>                   
			                    <div class="divider"></div>	
	                    
	                      	   <div class="row">
		                      		<div class="panel">
		                                <div class="panel-heading">
		                                    <div class="pull-left">		                                    			                                    	
		                                    		<h3 class="panel-title"><spring:message code="lbl.todos.comentarios"/></h3>
		                                    		&nbsp; <label style="font-size: 12px; font-style: italic;"><strong> <spring:message code="lbl.total.comentarios"/> </strong>: (${quantTotalComentarios}) </label>		                                    			                                        
		                                    </div>
		                                    <div class="pull-right">
		                                    	<a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>		                                    	
		                                    </div>		                                    
		                                    
		                                    <div class="clearfix"></div>
		                                </div><!-- /.panel-heading -->
		                                <div class="panel-body no-padding">
		                                    <div id="popular" class="blog-list tab-pane active">
			                                 	 <c:forEach var="imovel" items="${listaTodosComentarios}">
			                                		<div class="media">
			                                			   <c:if test="${imovel.status == 'N' }">
																<div id="idCheckImovelDiv_${imovel.id}"   class="ribbon-wrapper">
							                                        <div class="ribbon ribbon-danger">Novo</div>
							                                   </div>
							                                   		
															   <div class="media-left">
							                                        <input id="idCheckImovel_${imovel.id}"  checked="checked" type="checkbox" onclick="desmarcarCheck(${imovel.id});">
							                                    </div>
															</c:if>	
			                                		
				                                        <a class="pull-left" href="${urlUsuario}/detalhesUsuario/${imovel.usuarioComentario.id}">
				                                            <img src="data:image/jpeg;base64,${imovel.usuario.imagemArquivo}" style="width: 60px; height: 50px;"  class="img-responsive img-thumbnail"/>								                                            
				                                        </a><!-- /.pull-left -->
				                                        <div class="media-body">
				                                            <h4 class="media-heading"><a href="${urlUsuario}/detalhesUsuario/${imovel.usuarioComentario.id}">${imovel.usuario.nome}</a></h4>
				                                            <small class="media-desc">${imovel.comentario}</small> <br>
				                                            <em class="text-xs text-muted"><spring:message code="lbl.data.comentario"/> <span class="text-teal"><fmt:formatDate value='${imovel.dataComentario}' pattern='dd/MM/yyyy'/></span></em>
				                                        </div><!-- /.media-body -->
				                                    </div><!-- /.media --> 	 
			                                 	 </c:forEach>
		                                 	 </div>
		                                </div>
                            		</div><!-- /.panel -->
		                       </div>		  
	                    </div>                   		
                		                               
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
         
            </section><!-- /#page-content -->
			
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
       
  			<c:import url="../../layout/head-bootstrap.jsp"></c:import> 
    </body>
    <!--/ END BODY -->
</html>