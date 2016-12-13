<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>
<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/localidade/buscarCidades" var="urlBuscarCidades"/>
<spring:url value="/localidade/buscarBairros" var="urlBuscarBairros"/>
<spring:url value="/imovel" var="urlImovel"/>
 
<script type="text/javascript">

$(document).ready(function() {
	$("#idMsgSelIndicacao").hide();
});	

function indicarImovel(idUsuario, idImovel) {    	
	var parametro1 = idUsuario;
	var parametro2 = idImovel;	
    $.ajax({                
        url: '${urlImovelIndicado}/indicarImovel/' + parametro1 + '/' + parametro2,                
        success: function(data){
        	
        	 if ( data == 'ok') {
        		 $("#idNaoIndicado_"+idUsuario).hide();
             	 $("#idNaoIndicadoCheck_"+idUsuario).hide();
     	    	 $("#idIndicado_"+idUsuario).show();
     	    	 $("#idMsgSelIndicacao").hide();
        	 }
        	 else  {
        		 $("#idMsgSelIndicacao").show();
	         }	
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
        }
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
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.aba.indicar"/> </h2>    
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
		                                            <img class="img-circle img-bordered-primary" src="${context}${imovel.imagemArquivo}" style="width: 200px; height: 200px; ">
		                                        </li>
		                                        <li class="text-center">
		                                            <h4 class="text-capitalize">${imovel.titulo}</h4>
		                                            <p class="text-muted text-capitalize"> ${imovel.tipoImovelFmt} </p>
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
		                                                    <i class="fa fa-user"></i>
		                                                    <div>
		                                                        <span class="text-strong"><spring:message code="lbl.indicar.info.basicas.imovel"/></span>                                                        
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
		                                                		<strong class="col-sm-3"><spring:message code="lbl.titulo.imovel"/></strong> ${imovel.titulo}
		                                            	 </div><!-- /.form-group -->
		                                            
		                                            	 <div class="line"></div>
		                                            	 
		                                            	 <div class="form-group">
		                                                		<strong class="col-sm-3"><spring:message code="lbl.estado"/></strong> ${imovel.estado}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            	 
		                                            	 <div class="form-group">
		                                                		<strong class="col-sm-3"><spring:message code="lbl.cidade"/></strong> ${imovel.cidade}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            	 
		                                            	 <div class="form-group">
		                                                		<strong class="col-sm-3"><spring:message code="lbl.bairro"/></strong> ${imovel.bairro}
		                                            	 </div><!-- /.form-group -->
		                                            	 
		                                            	 <div class="line"></div>
		                                            	 
		                                            	 <div class="form-group">																				            
							                              		<strong class="col-sm-3"><spring:message code="lbl.data.cadastro.imovel"/> </strong> <fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/>
							                             </div><!-- /.form-group -->
		                                            </div>                          
		                                        </div>                                       
		                                    </div><!-- /.panel-body -->
		                                    <!--/ End tabs content -->
		                                </div><!-- /.panel -->
		                            </div>
		                         
			                    </div>      
			                   <div class="row">
			                   
			                    <c:if test="${msgErroEnviadoEmail != null }">
				               		 <div class="panel panel-danger">
				                          <div class="panel-heading">
				                              <h3 class="panel-title">${msgErroEnviadoEmail}</h3>
				                          </div><!-- /.panel-heading -->		                          
				                      </div><!-- /.panel -->                      
				                </c:if>
			                   
			                   <c:if test="${msgSucessoEnviadoEmail != null }">
				               		 <div class="panel panel-danger">
				                          <div class="panel-heading">
				                              <h3 class="panel-title">${msgSucessoEnviadoEmail}</h3>
				                          </div><!-- /.panel-heading -->		                          
				                      </div><!-- /.panel -->                      
				                </c:if>
			                   
			                    <div class="panel-body col-lg-9 col-md-9 col-sm-9">
			                    	<form:form method="POST" id="imovelIndicadoForm" modelAttribute="imovelIndicadoForm" action="${urlImovelIndicado}/indicarImovelPorEmail" >
                                       <div class="tab-content">
                                           <div class="tab-pane fade in active" id="tab4-1">
                                           		 
                                           		 <div class="form-group">
	                                                <label for="endereco" class="col-sm-3 control-label">Email</label>
	                                                <div class="col-sm-4">                                                    
	                                                    <form:input  id="emailIndicado" path="emailIndicado" class="form-control"/>
	                                                </div>
	                                            </div><!-- /.form-group -->
	                                             
	                                             <div class="form-footer">  
						                              <div class="col-sm-offset-3">
						                                  <button type="submit" class="btn btn-success"> Enviar Email</button>
						                              </div>
						                          </div><!-- /.form-footer -->   	                                            
                                           </div>
                                       </div>
                                     </form:form>  
		                        </div>           
			                   </div>              
			                    <div class="divider"></div>	
			                    
	                      	   <div class="row">
	                      	   		<div id="idMsgSelIndicacao" class="panel panel-danger" hidden="true">
				                          <div class="panel-heading">
				                              <h3 class="panel-title"><spring:message code="msg.erro.indicacoes.quant.usuarios.selecionados"/></h3>
				                          </div><!-- /.panel-heading -->		                          
				                      </div><!-- /.panel -->  
	                      	   
	                      	   
	                      	   		<c:if test="${msgErro != null }">
					               		 <div class="panel panel-danger">
					                          <div class="panel-heading">
					                              <h3 class="panel-title">${msgErro}</h3>
					                          </div><!-- /.panel-heading -->		                          
					                      </div><!-- /.panel -->                      
					                </c:if>
					                
					                <c:if test="${ultrapassouLimiteIndicacoes != null }">
					               		 <div class="panel panel-danger">
					                          <div class="panel-heading">
					                              <h3 class="panel-title">${ultrapassouLimiteIndicacoes}</h3>
					                          </div><!-- /.panel-heading -->		                          
					                      </div><!-- /.panel -->                      
					                </c:if>
					                	
					                <c:choose>
	                                		<c:when test = "${not empty imovelIndicadoForm.listaTodosContatos}">
	                                			<div class="panel">
					                                <div class="panel-heading">
					                                    <div class="pull-left">
					                                    		<h3 class="panel-title"><spring:message code="lbl.title.aba.indicar.contatos.usuario"/></h3>
					                                    </div>
					                                    <div class="pull-right">
					                                    		<a href="#a" class="btn btn-sm"  data-toggle="modal" data-target=".bs-modal-ajuda-informacoes" style=""><i class="fa fa-question" ></i></a>                                        
					                                    </div>
					                                    <div class="clearfix"></div>
					                                </div><!-- /.panel-heading -->
					                                <div class="panel-body no-padding">		                                
					                                	
					                                			<div class="table-responsive" style="margin-top: -1px;">
							                                        <table class="table table-striped table-primary">
							                                            <thead>		                                            
							                                            	<tr>
							                                            		<th class="text-center"></th>		                                                
								                                            	<th class="text-center"></th>
								                                                <th class="text-center"><spring:message code="lbl.nome.usuario.indicado"/></th>
								                                                <th class="text-center"><spring:message code="lbl.perfil.usuario.indicado"/></th>
								                                                <th class="text-center"></th>			                                                
								                                            </tr>	
							                                            </thead>
							                                            <tbody>
							                                           <form:form method="POST" id="imovelIndicadoForm" modelAttribute="imovelIndicadoForm" action="${urlImovelIndicado}/indicarImovelSelecionados" >
							                                            <div class="section colm colm2 spacer-b5">
														                      <button type="submit" class="button btn-primary"><spring:message code="lbl.lista.usuarios.indicados"/></button>
														                  </div>
								                                            <c:forEach var="usuarioContato" items="${imovelIndicadoForm.listaTodosContatos}" >			                                            
									                                            <tr>
									                                            	<c:if test="${usuarioContato.indicadoImovel == 'N'}">
									                                            		<td> <form:checkbox id="idNaoIndicadoCheck_${usuarioContato.id}"  path="idUsuariosSelecionados" value="${usuarioContato.id}"  /> </td>
									                                            	</c:if>
									                                            	<c:if test="${usuarioContato.indicadoImovel == 'S'}">
									                                            		<td> </td>
									                                            	</c:if>
									                                                <td class="text-center">	                                                    
															                     		<img src="${context}/${usuarioContato.imagemArquivo}" style="width: 60px; height: 50px; " />	                				
									                                                </td>			                                                
									                                                <td class="text-center">${usuarioContato.nome}</td>
									                                                <td class="text-center">${usuarioContato.perfilFmt} </td>
																					<td>
																						<c:if test="${usuarioContato.indicadoImovel == 'N'}">
																							<button type="button" id="idNaoIndicado_${usuarioContato.id}" class="btn btn-sm btn-primary btn-xs btn-push" onClick="indicarImovel(${usuarioContato.id}, ${imovel.id})" ><i class="fa fa-eye"></i> Indicar</button>
														              					
														              						<button type="button"  style="display: none;"  id="idIndicado_${usuarioContato.id}" class="btn btn-sm btn-primary btn-xs btn-push" ><i class="fa fa-eye"></i> Indicado </button>
																						</c:if>
																						<c:if test="${usuarioContato.indicadoImovel == 'S'}">
																							<button type="button" class="btn btn-sm btn-primary btn-xs btn-push" ><i class="fa fa-eye"></i> Indicado </button>
																						</c:if>
														              					
														              				</td>			            			                                                			                                              	                                            
									                                            </tr>			                                            
								                                            </c:forEach>
							                                            </form:form>
							                                            </tbody>
							                                        </table>
							                                    </div>		                                    
					                                </div>
			                            		</div><!-- /.panel -->
	                                		</c:when>
	                                		
	                                		<c:when test = "${empty imovelIndicadoForm.listaTodosContatos}">
	                                			<div class="panel">
				                    						<div class="alert alert-primary">
							                                      <strong><spring:message code="lbl.nenhum.contato.indicar.imovel"/></strong> 
							                                 </div>             				
						                            </div> 
	                                		</c:when>		                                		
		                            </c:choose>    	
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