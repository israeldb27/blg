<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ include file="./layout/head-login.jsp"%>
<body class="home">
<div class="all">
    <div class="header">
        <div class="content">
            <h1 class="logo">Busque um Lugar</h1> 
            <div class="smart-forms login">
                <form:form id="usuarioForm" modelAttribute="usuarioForm" method="POST" action="${urlUsuario}/submitLogin">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <div class="form-body">
                        <div class="frm-row">  
                            <div class="section colm colm5 spacer-b5">
                                <span> <spring:message code="lbl.email.novo"/> </span>
                            </div><!-- end section -->

                            <div class="section colm colm5 spacer-b5">
                                <span> <spring:message code="lbl.senha"/> </span>
                            </div><!-- end section -->
                        </div>
                        <div class="frm-row">
                            <div class="section colm colm5 spacer-b5">                            	
                            	<spring:message code="lbl.digite.login" var="msgDigiteLogin"/>
                                <form:input path="login" class="gui-input" placeholder="${msgDigiteLogin}" />
                            </div><!-- end section -->

                            <div class="section colm colm5 spacer-b5">
                            	<spring:message code="lbl.digite.senha" var="msgDigiteSenha"/>
                                <form:password path="password" class="gui-input" placeholder="${msgDigiteSenha}"/>
                            </div><!-- end section -->
                            <div class="section colm colm2 spacer-b5">  
                                <button type="submit" class="button btn-primary"><spring:message code="lbl.btn.entrar.geral"/></button>
                            </div>                            
                        </div><!-- end .frm-row section -->
                        <div class="frm-row">
                        	<c:if test="${msgError != null }">
                        		<div class="section colm colm5 spacer-b0">
	                                <div class="option-group field">
	                                    <label class="option block">
	                                        <p><c:out value="${msgError}" /> </p>
	                                    </label>
	
	                                </div>
	                            </div><!-- end section -->
                        	</c:if>                            

                            <div class="section colm colm5 spacer-b0">  
                                <a href="${urlUsuario}/prepararEsqueceuSenha" ><spring:message code="lbl.esqueceu.sua.senha"/></a>
                            </div><!-- end section -->
                        </div><!-- end .frm-row section -->
                    </div><!-- end .form-body section -->
                            
                </form:form>

            </div>
        </div>
        
    </div>
    <div class="main">
        <div class="content">  
            <div class="col col-left">
                <h2><spring:message code="msg.texto.1.apresentacao.tela.login"/></h2>
                <h3><spring:message code="msg.texto.2.apresentacao.tela.login"/></h3>
                <div class="info-txt">
                    <i class="fa fa-search fa-2x"></i>
                    <h4><spring:message code="msg.texto.3.apresentacao.tela.login"/></h4>
                    <p><spring:message code="msg.texto.4.apresentacao.tela.login"/><br>
                       <spring:message code="msg.texto.5.apresentacao.tela.login"/></p>
                </div>
                <div class="info-txt">
                    <i class="fa fa-dollar fa-2x"></i>
                    <h4><spring:message code="msg.texto.6.apresentacao.tela.login"/></h4>
                    <p><spring:message code="msg.texto.7.apresentacao.tela.login"/></p>
                </div>
                <div class="info-txt">
                    <i class="fa fa-users fa-2x"></i>
                    <h4><spring:message code="msg.texto.8.apresentacao.tela.login"/></h4>
                    <p><spring:message code="msg.texto.9.apresentacao.tela.login"/><br>
                       <spring:message code="msg.texto.10.apresentacao.tela.login"/></p>
                </div>
            </div>
            <div class="col col-right">
            	<h2><spring:message code="msg.texto.11.apresentacao.tela.login"/></h2>
                <h3><spring:message code="msg.texto.12.apresentacao.tela.login"/></h3>
                <br> </br> 
                
                <div class="smart-forms">
                	<form:form id="usuarioForm" name="usuarioForm" path="usuarioForm" method="POST" action="${urlUsuario}/prepararCadastroUsuario">
                		<div class="section colm colm2 spacer-b5">
                              <button type="submit" class="button btn-primary"><spring:message code="msg.texto.13.apresentacao.tela.login"/></button>
                          </div>     
                	</form:form>
                </div>	
                    <br> </br> <br> </br> 
            </div>            
  
        </div>          
    </div>
</div>

<div class="smart-wrap">
    <!-- end .smart-forms section -->
</div><!-- end .smart-wrap section -->

<div></div><!-- end section -->
