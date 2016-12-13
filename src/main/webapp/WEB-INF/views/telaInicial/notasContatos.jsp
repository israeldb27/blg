<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<spring:url value="/usuario" var="urlUsuario"/>
<spring:url value="/imovel" var="urlImovel"/>
<spring:url value="/nota" var="urlNota"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${context}/js/jquery-ui.js"></script>

<div class="row">                            
                            
        <div class="col-lg-9 col-md-12 col-sm-9"> 
			<div class="panel rounded shadow">                         
			  <div class="panel-heading">
					<div class="pull-left">
						<h3 class="panel-title">Notas Contatos</h3>
					</div><!-- /.pull-left -->
					<div class="pull-right">
						 
					</div><!-- /.pull-right -->
					<div class="clearfix"></div>
				</div><!-- /.panel-heading -->
				<div class="panel-body no-padding">
					<c:choose>
						<c:when test="${ not empty listaNotasContatos }">
							  <c:forEach var="nota" items="${listaNotasContatos}"> 		                                	
									<div class="media inner-all">
										  <div class="pull-left">
												 <span class="fa fa-stack fa-2x">
													<c:if test="${((nota.acao == 'preferencia') || (nota.acao == 'usuario') )}">                                         	
														<img class="img-circle img-bordered-success" src="${context}${nota.usuario.imagemArquivo}" style="width: 60px; height: 60px; " alt="admin"/>
													</c:if>                                               	 
													<c:if test="${((nota.acao == 'imovel') || (nota.acao == 'parceria'))}">
														<img src="${context}${nota.imovel.imagemArquivo}" style="width: 60px; height: 60px; " alt="admin"/>
													</c:if>
												 </span>
										  </div><!-- /.pull-left -->
										  <div class="media-body">
											  <c:choose>
												<c:when test="${nota.acao == 'parceria'}">
													<a href="${urlAdmin}/detalhesImovel/${imovel.id}" class="h4"><spring:message code="lbl.nota.parceria"/></a>		
												</c:when>
												
												<c:when test="${nota.acao == 'preferencia'}">
													<a href="#" class="h4"><spring:message code="lbl.nota.preferencia"/></a>
												</c:when>
												
												<c:when test="${nota.acao == 'usuario'}">
													<a href="${urlUsuario}/detalhesUsuario/${nota.usuario.id}" class="h4"><spring:message code="lbl.nota.info.usuario"/></a>
												</c:when>
												
												<c:when test="${nota.acao == 'imovel'}">
													<a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" class="h4"><spring:message code="lbl.nota.imovel"/></a>
												</c:when>
												
											  </c:choose>  				           
											  
											  <c:choose>
												<c:when test="${nota.acao == 'parceria'}">
													<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a></small>
												</c:when>
												
												<c:when test="${nota.acao == 'imovel'}">
													<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="${urlImovel}/detalhesImovel/${nota.imovel.id}" ><strong>${nota.imovel.titulo} </strong></a></small>
												</c:when>
												
												<c:when test="${nota.acao == 'usuario'}">
													<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao} <a href="${urlUsuario}/detalhesUsuario/${nota.usuario.id}" ><strong>${nota.usuario.nome} </strong></a></small>
												</c:when>												    
												<c:when test="${nota.acao == 'preferencia'}">
													<small class="block text-muted"><label> <spring:message code="lbl.descricao.nota"/>: </label>  ${nota.descricao}</small>
												</c:when>
												
											</c:choose>			                                      
											  
											  <em class="text-xs text-muted"><spring:message code="lbl.data.nota"/> <span class="text-danger"><fmt:formatDate value='${nota.dataNota}' pattern='dd/MM/yyyy'/></span></em>
											  
										  </div><!-- /.media-body -->
									  </div><!-- /.media -->
									  <div class="line"></div>
								  </c:forEach>	
								  <a href="${urlNota}/notasContatos" class="btn btn-primary btn-block"><spring:message code="lbl.btn.mais.notas.geral"/></a>  	
						</c:when>
						
						<c:when test="${ empty listaNotasContatos }">
							   <div class="callout callout-warning">
                                    <strong><spring:message code="lbl.nenhuma.nota"/></strong>                                    
                                </div>						
						</c:when>
					</c:choose>
											            
				</div><!-- /.panel-body -->				
			</div>                                                                                
        </div>
            		                        
 </div>