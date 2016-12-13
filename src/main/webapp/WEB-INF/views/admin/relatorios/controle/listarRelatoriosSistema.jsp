<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- A principio os Relatorios Sistema serão carregados por script no banco de dados e não mais via aplicação -->

<!-- esta funcionalidade pode ser usada para editar se o Relatorio será cobrado ou qual será o tipo do relatorio (Basico ou Premium) -->

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/admin" var="urlAdmin"/>


    <!-- START @HEAD -->
 <script type="text/javascript">

$(document).ready(function() {

});

</script>		
 
<c:import url="../../layout-admin/head-layout.jsp"></c:import>
   
    <body>

        <!-- START @WRAPPER -->
        <section id="wrapper">

            <!-- START @HEADER -->            	
            	<c:import url="../../layout-admin/header.jsp"></c:import>
            <!--/ END HEADER -->

            <!-- START @SIDEBAR LEFT            -->
           		<c:import url="../../layout-admin/sidebar-left.jsp"></c:import>
            <!--/ END SIDEBAR LEFT -->

            <!-- START @PAGE CONTENT -->
            <section id="page-content">
            
            	<!-- Inicio - Meus Favoritos -->
            
            	 <!-- Start header content -->
                <div class="header-content">
                    <h2><i class="fa fa-pencil"></i> <spring:message code="lbl.admin.link.relatorios.sistema"/> </h2>                                                      
                </div><!-- /.header-content -->
                <!--/ End header content -->
                
                <!-- Start body content -->
                <div class="body-content animated fadeIn">

                    <div class="row">
                        <div class="col-md-12">                       		
                      
                      		<c:if test="${ empty listaRelatorioSistema }">
                                 	
                                 	<div class="panel" align="center">	                                
		                                <div class="panel-body">
		                                    <spring:message code="msg.lista.rel.sistema.vazio"/>
		                                </div><!-- /.panel-body -->
		                            </div><!-- /.panel -->
                             </c:if>
                      
                            <!-- Start basic color table -->
                            
                           <c:if test="${ not empty listaRelatorioSistema }"> 
                            <div class="panel">
                                <div class="panel-heading">
                                    <div class="pull-left">
                                        <h3 class="panel-title"><spring:message code="lbl.admin.lista.rel.sistema"/></h3>
                                    </div>
                                    <div class="pull-right">
                                        
                                    </div>
                                    <div class="clearfix"></div>
                                </div><!-- /.panel-heading -->
                                 
                                <div class="panel-body no-padding">                                 	
                                 
                                    <div class="table-responsive" style="margin-top: -1px;">
                                        <table class="table table-striped table-primary">
                                            <thead>
                                            <tr>  
                                            	<th class="text-center"><spring:message code="lbl.admin.data.cadastro.rel.sistema"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.nome.rel.sistema.resumido"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.tipo.rel.sistema.resumido"/></th>
                                                <th class="text-center"><spring:message code="lbl.admin.havera.cobranca.rel.sistema"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="relatorio" items="${listaRelatorioSistema}" >
	                                            <tr>
	                                                <td class="text-center"> <fmt:formatDate value='${relatorio.dataCriacao}' pattern='dd/MM/yyyy'/></td>
	                                                <td class="text-center">${relatorio.nome}</td>	                                                
	                                                <td class="text-center"><c:if test="${relatorio.tipo == 'T'}">
																				<spring:message code="lbl.rel.tipo.basico"/>
																			</c:if>
																			<c:if test="${relatorio.tipo == 'A'}">
																				<spring:message code="lbl.rel.tipo.premium"/>
																			</c:if>	</td>
	                                                <td class="text-center"> <c:if test="${relatorio.cobranca == 'S'}">
																				<spring:message code="lbl.sim"/>
																			 </c:if>
																			 <c:if test="${relatorio.cobranca == 'N'}">
																				<spring:message code="lbl.nao"/>
																		 	 </c:if></td>													
													<td class="text-center">				                      											                      											                      
														  <a href="${urlAdmin}/prepararPermissionamentoRelatorioSistema/${relatorio.id}" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> <spring:message code="lbl.admin.link.permissionar.rel.sistema"/></a> 
														  <a href="${urlAdmin}/prepararManterRelatorioSistema/${relatorio.id}/atualizar" class="btn btn-sm btn-primary btn-xs btn-push"><i class="fa fa-eye"></i> <spring:message code="lbl.admin.link.atualizar.rel.sistema"/></a>  
													</td>	                                            
	                                            </tr>  
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div><!-- /.table-responsive -->
                                    
                                </div><!-- /.panel-body -->                               
                            </div><!-- /.panel -->
                            <!--/ End basic color table -->
							</c:if>
                        </div><!-- /.col-md-9 -->
                                        
                    </div><!-- /.row -->

                </div><!-- /.body-content -->
                <!--/ End body content -->
                
                <!-- Fim Meus Imoveis -->
         
            </section><!-- /#page-content -->
            <!--/ END PAGE CONTENT -->
          
        </section><!-- /#wrapper -->
        <!--/ END WRAPPER -->

        <div id="back-top" class="animated pulse circle">
            <i class="fa fa-angle-up"></i>
        </div><!-- /#back-top -->
       
  		<c:import url="../../layout-admin/head-bootstrap.jsp"></c:import> 

    </body>
    <!--/ END BODY -->

</html>