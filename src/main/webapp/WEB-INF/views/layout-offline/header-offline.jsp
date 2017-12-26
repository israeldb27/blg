<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>
<%@page import="com.busqueumlugar.util.ParametrosUtils"%>

<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<spring:url var="urlContato" value="/contato"/>
<spring:url var="urlNotificacao" value="/notificacao"/>
<spring:url var="urlMensagem" value="/mensagem"/>
<spring:url var="urlUsuario" value="/usuario"/>
<spring:url var="urlImovel" value="/imovel"/>		
<spring:url var="urlMensagemAdmin" value="/mensagemAdmin"/>
<spring:url var="urlAjuda" value="/ajuda"/>
<spring:url var="urlRecomendacao" value="/recomendacao"/>
<spring:url var="urlImovelComparativo" value="/imovelComparativo"/>
<spring:url var="urlAdmin" value="/admin"/>

<c:set var="context" value="<%= request.getContextPath()%>"/>
<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	
	$('.my-tooltip').tooltip({
		  
	});
});

function pesquisarTudo(){ 

	var parametro1=document.getElementById("valorPesquisa");
	alert(parametro1.value);
	$.post("${urlUsuario}/pesquisarTudo/"+ parametro1.value , {}, function() {		
		 
    }); 
} 

function adicionarComparativo(id) {    		
	var parametro1 = id;
    $.ajax({        
		 url: '${urlImovelComparativo}/adicionarImovelComparativo/' + parametro1,
		 dataType: 'json',
		 success: function(data){				 
			 if ( data == 'ok') {
				 $('#msgModalComparativo').html("<spring:message code='lbl.msg.sucesso.add.comparativo'/>");
				 $("#idModalConfirmarComparativo").modal("show");	
       	 }
       	 else  {
       		$("#idModalConfirmarComparativo").modal("show"); 
       		$('#msgModalComparativo').html(data);
	         }     	    
		 },
		 error: function(jqXHR, textStatus, errorThrown) {				 
			 $('#msgModalComparativo').html("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
		 }
	 });
}
</script>

<header id="header">

                <!-- Start header left -->
                <div class="header-left">
                    <!-- Start offcanvas left: This menu will take position a the top of template header (mobile only). Make sure that only #header have the `position: relative`, or it may cause unwanted behavior -->
                    <div class="navbar-minimize-mobile left">
                        <i class="fa fa-bars"></i>
                    </div>
                    <!--/ End offcanvas left -->

                    <!-- Start navbar header -->
                    <div class="navbar-header">

                        <!-- Start brand -->                                           
                          <img src="${context}/images/logo_bul.png"  style="width: 175px; height: 50px; " alt="brand logo"> 
                        <!--/ End brand -->

                    </div><!-- /.navbar-header -->
                    <!--/ End navbar header -->
                   
                    <div class="navbar-minimize-mobile right">
                        <i class="fa fa-cog"></i>
                    </div>
                    <!--/ End offcanvas right -->

                    <div class="clearfix"></div>
                </div><!-- /.header-left -->
                <!--/ End header left -->

                <!-- Start header right -->
                <div class="header-right">
                    <!-- Start navbar toolbar -->
                    <div class="navbar navbar-toolbar">

                        <!-- Start left navigation -->
                        <ul class="nav navbar-nav navbar-left">

                            <!-- Start sidebar shrink -->
                            <li class="navbar-minimize">
                                <a href="javascript:void(0);" title="Minimize sidebar">
                                    <i class="fa fa-bars"></i>
                                </a>
                            </li>
                            <!--/ End sidebar shrink -->
                       
                            
                        </ul><!-- /.nav navbar-nav navbar-left -->
                        <!--/ End left navigation -->
                        
                        <!-- Start right navigation -->
                        <ul class="nav navbar-nav navbar-right"><!-- /.nav navbar-nav navbar-right -->                        
                                            

                        <!-- Start profile -->
                        <li class="dropdown navbar-profile">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <span class="meta">                                    
                                                                      
                                </span>
                            </a>
                            <!-- Start dropdown menu -->
                        
                            <!--/ End dropdown menu -->
                        </li><!-- /.dropdown navbar-profile -->
                        <!--/ End profile -->
                   						
                        </ul>
                        <!--/ End right navigation -->
			</div><!-- /.navbar-toolbar -->
			<!--/ End navbar toolbar -->
		</div><!-- /.header-right -->
		<!--/ End header left -->

	</header> <!-- /#header -->
	
	<!-- Start optional size modal element - comparativo de imoveis -->
            <div id="idModalConfirmarComparativo" class="modal fade bs-example-modal-lg-comparativo" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><spring:message code="lbl.modal.comparativo"/></h4>
                        </div>
                        <div class="modal-body">
                            <p><div id="msgModalComparativo" cssClass="errorEntrada"  ></div>   </p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.btn.fechar.geral"/></button>                                                        
                        </div>						
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->