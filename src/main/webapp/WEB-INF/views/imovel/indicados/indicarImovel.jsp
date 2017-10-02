<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/imovelIndicado" var="urlImovelIndicado"/>
<c:set var="context" value="<%= request.getContextPath()%>"/>
<%@page import="com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum"%>
<c:set var="listaPerfilUsuario" value="<%= PerfilUsuarioNormalEnum.values() %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/usuario" var="urlUsuario"/>
 
<script type="text/javascript">

$(document).ready(function() {
	$("#idMsgSelIndicacao").hide();
	
	$('#opcaoFiltroPerfil').change(function () {				
		$("#imovelIndicadoSelPerfilForm").submit();      
	 });  
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

function mostrarModal(id){
	
	if (id == 0){
		$('#msgModalFunc').html("<spring:message code='msg.modal.title.aba.indicar'/>");
		$('#msgModalFuncionalidade').html("<spring:message code='lbl.title.aba.indicar'/>");
	}		
	$("#idModalItem").modal("show");
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
                    <h2><i class="fa fa-pencil"></i><spring:message code="lbl.title.aba.indicar"/> 
                    	<div class="pull-right">
                             <a href="#a" class="btn btn-sm"  onClick="mostrarModal(0);"><i class="fa fa-question" style="font-size: 12px;"></i></a>                                        
                         </div>
                    </h2> 
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
		                                        	 <a href="${urlImovel}/detalhesImovel/${imovel.id}" >
		                                        		<img class="img-circle img-bordered-primary" src="data:image/jpeg;base64,${imovel.imagemArquivo}"  style="width: 200px; height: 200px; "> 
		                                        	 </a>	
		                                        </li>
		                                        <li class="text-center">
		                                        	<a href="${urlImovel}/detalhesImovel/${imovel.id}" >
		                                        		<h4 class="text-capitalize">${imovel.titulo}</h4>
		                                        	</a>		                                            
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
			                   		<div class="alert alert-success">
                                         <strong>${msgSucessoEnviadoEmail}</strong> 
                                     </div>	                    
				                </c:if>
			                   
			                    <div class="panel-body col-lg-9 col-md-9 col-sm-9">
			                    	<form:form method="POST" id="imovelIndicadoForm" modelAttribute="imovelIndicadoForm" action="${urlImovelIndicado}/indicarImovelPorEmail" >
                                       <div class="tab-content">
                                           <div class="tab-pane fade in active" id="tab4-1">
                                           		 
                                           		 <div class="input-group">
                                           		 		<form:input  id="emailIndicado" path="emailIndicado" class="form-control" placeholder="Indique este imóvel para uma pessoa digitando aqui o email"/>
                                                       
                                                        <div class="input-group-btn">
                                                            <button type="submit" class="btn btn-primary btn-stroke" style="font-size: 11px;"> <i class="fa fa-envelope-o"></i> &nbsp; Indicar por Email </button>
                                                        </div>
                                                    </div> 	                                            
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
					                       <div class="callout callout-danger">
				                             <strong>${msgErro}</strong>
				                          </div>                    
					                </c:if>
					                
					                <c:if test="${ultrapassouLimiteIndicacoes != null }">
					                	 <div class="callout callout-danger">
				                             <strong>${ultrapassouLimiteIndicacoes}</strong>
				                         </div>						                      
					                </c:if>
	                                			<div class="panel">
					                                <div class="panel-heading">
					                                    <div class="pull-left">
					                                    		<h3 class="panel-title"><spring:message code="lbl.title.aba.indicar.contatos.usuario"/></h3>
					                                    </div>
					                                    <div class="pull-right">
					                                    	<form:form method="POST" id="imovelIndicadoSelPerfilForm" modelAttribute="imovelIndicadoForm" action="${urlImovelIndicado}/selecionarContatosPorPerfil" >
					                                    		<form:select id="opcaoFiltroPerfil" path="opcaoFiltroPerfil" class="form-control"  >                                
												                    <form:option value="" ><spring:message code="lbl.opcao.selecione.perfil.indicacao"/></form:option>
												   					<form:options items="${listaPerfilUsuario}" itemValue="identificador" itemLabel="rotulo" /> 
												   					<form:option value="T" ><spring:message code="lbl.perfil.usuario.todos"/></form:option>   
												                </form:select>    
												             </form:form>                                    
					                                    </div>
					                                    <div class="clearfix"></div>
					                                </div><!-- /.panel-heading -->
					                                
					                                <c:choose>
	                                					<c:when test = "${not empty imovelIndicadoForm.listaTodosContatos}">
							                                <div class="panel-body">		                                
							                                	
							                                			<div class="table-responsive" style="margin-top: -1px;">
									                                        <table class="table table-bordred table-striped" >
									                                            <thead >		                                            
									                                            	<tr >
									                                            		<th class="text-center" ></th>		                                                
										                                            	<th class="text-center" ></th>
										                                                <th class="text-center" ><strong><spring:message code="lbl.nome.usuario.indicado"/></strong></th>
										                                                <th class="text-center" ><strong><spring:message code="lbl.perfil.usuario.indicado"/></strong></th>
										                                                <th class="text-center" "></th>			                                                
										                                            </tr>	
									                                            </thead>
									                                            <tbody>
									                                           <form:form method="POST" id="imovelIndicadoForm" modelAttribute="imovelIndicadoForm" action="${urlImovelIndicado}/indicarImovelSelecionados" >
									                                            <div class="section colm colm2 spacer-b5">
																                      
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
											                                                	<a href="${urlUsuario}/detalhesUsuario/${usuarioContato.id}" >                                                      
																	                     			<img src="data:image/jpeg;base64,${usuarioContato.imagemArquivo}" style="width: 60px; height: 50px; " />
																	                     		</a>		                				
											                                                </td>			                                                
											                                                <td class="text-center">
											                                                	<a href="${urlUsuario}/detalhesUsuario/${usuarioContato.id}" > 
											                                                		${usuarioContato.nome}	
											                                                	</a>
											                                                </td>
											                                                <td class="text-center">${usuarioContato.perfilFmt} </td>
																							<td>
																								<c:if test="${usuarioContato.indicadoImovel == 'N'}">
																									<a href="#a" id="idNaoIndicado_${usuarioContato.id}" class="meta-action" onClick="indicarImovel(${usuarioContato.id}, ${imovel.id})" ><i class="fa fa-share-alt" style="color:gray"></i> Indicar</a>
																              					
																              						<a href="#a" style="display: none;"  id="idIndicado_${usuarioContato.id}" class="meta-action" ><i class="fa fa-share-alt" style="color:gray"></i> Indicado </button>
																								</c:if>
																								<c:if test="${usuarioContato.indicadoImovel == 'S'}">
																									<a href="#a" class="meta-action" ><i class="fa fa-share-alt" style="color:gray"></i> Indicado </button>
																								</c:if>
																              					
																              				</td>			            			                                                			                                              	                                            
											                                            </tr>			                                            
										                                            </c:forEach>
									                                            </form:form>
									                                            </tbody>
									                                        </table>
									                                    </div>		                                    
							                                </div>
							                          </c:when>
					                                <c:when test = "${empty imovelIndicadoForm.listaTodosContatos}">
					                                <br>
			                                			<div class="callout callout-warning">
						                                    <strong><spring:message code="lbl.nenhum.contato.indicar.imovel"/></strong>
						                                </div>
			                                		</c:when>		                                		
		                            			</c:choose>					                                
			                            </div><!-- /.panel -->
		                       </div>		  
	                    </div>                   		
                		                               
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
         
            </section><!-- /#page-content -->
			
			<!-- Start content modal Ajuda - funcionalidade -->
				<c:import url="../../ajuda/contentMenuModal.jsp"></c:import>																				
			<!-- End content  modal Ajuda - funcionalidade -->
			
				    <!-- Start optional size modal element - item 1 -->
            <div id="idModalItem" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				          <h4 class="modal-title"> <div id="msgModalFuncionalidade" > </div>  </h4>
				        </div>
				        <div class="modal-body">  
				       	   <strong> <spring:message code="lbl.descricao.geral"/>:  </strong> <div id="msgModalFunc" > </div>
				        </div>
				        <div class="modal-footer">			          
	                      <button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>
				        </div>
				      </div>
				    </div>
				</div>

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