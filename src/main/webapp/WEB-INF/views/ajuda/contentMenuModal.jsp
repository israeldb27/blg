<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.busqueumlugar.util.UsuarioInterface"%>
<%@page import="com.busqueumlugar.service.UsuarioService"%>
<%@page import="com.busqueumlugar.form.UsuarioForm"%>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<c:set var="func" value="<%= (String)request.getSession().getAttribute(UsuarioInterface.FUNCIONALIDADE) %>"/>
<c:set var="usuario" value="<%= (UsuarioForm)request.getSession().getAttribute(UsuarioInterface.USUARIO_SESSAO) %>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<!--/ Start - modal Ajuda - informações funcionalidade -->            	
            <div class="modal fade bs-modal-ajuda-informacoes" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="panel panel-tab panel-tab-double">
                            <!-- Start tabs heading -->
                            <div class="panel-heading no-padding">
                                <ul class="nav nav-tabs">
                                    <li class="active">
                                        <a href="#tab2-1" data-toggle="tab">
                                            <i class="fa fa-file-text"></i>
                                            <div>
                                                <span class="text-strong"><spring:message code="lbl.title.modal.descricao"/></span>                                                
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#tab2-2" data-toggle="tab">
                                            <i class="fa fa-file-text"></i>
                                            <div>
                                                <span class="text-strong"><spring:message code="lbl.title.modal.objetivo"/></span>                                                
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#tab2-3" data-toggle="tab">
                                            <i class="fa fa-file-text"></i>
                                            <div>
                                            	<span class="text-strong"><spring:message code="lbl.title.modal.regras.basicas"/></span>
                                                                                                
                                            </div>
                                        </a>
                                    </li>                                    
                                </ul>
                            </div>
                            <!--/ End tabs heading -->

                            <!-- Start tabs content -->
                            <div class="panel-body">
                                <div class="tab-content">
                                    <div class="tab-pane fade in active" id="tab2-1">
                                        <h4><spring:message code="lbl.title.modal.descricao"/></h4>
                                        
                                        <c:choose>
                                        
										    <c:when test="${func == 'buscarImoveis'}" >
										       <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.buscarImoveis"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'buscarImoveisMapa'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.buscarImoveisMapa"/></p>
										    </c:when>										    
										   
										    
										    <c:when test="${func == 'listarMeusImoveis'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.listarMeusImoveis"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'buscarUsuarios'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.buscarUsuarios"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'buscarUsuariosPreferenciaImoveis'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.buscarUsuariosPreferenciaImoveis"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarImoveisComparativos'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.listarImoveisComparativos"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarPlanosUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.listarPlanosUsuario"/></p>
										    </c:when>		
										    
										    <c:when test="${func == 'listarServicosUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.listarServicosUsuario"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarImovelFavoritosMeusInteresse'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.listarImovelFavoritosMeusInteresse"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarImovelFavoritosUsuarioInteressado'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.listarImovelFavoritosUsuarioInteressado"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'meusImoveisVisitados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.meusImoveisVisualizados"/></p>
										    </c:when>
										    
										     <c:when test="${func == 'minhasVisitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.minhasVisualizacoes"/></p>
										    </c:when>										    
										    
										    <c:when test="${func == 'intermediacaoAceita'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.intermediacaoAceita"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'intermediacaoSolRecebida'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.intermediacaoSolRecebida"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'intermediacaoMinhasSol'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.intermediacaoMinhasSol"/></p>
										    </c:when>
										    										    
										    <c:when test="${func == 'parceriaAceita'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.parceriaAceita"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'parceriaSolRecebida'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.parceriaSolRecebida"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'parceriaMinhasSol'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.parceriaMinhasSol"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'minhasNotas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.minhasNotas"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'notasContato'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.notasContato"/></p>
										    </c:when>										    
										    
										    <c:when test="${func == 'PropostasRecebidas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.propostasRecebidas"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'PropostasLancadas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.propostasLancadas"/></p>
										    </c:when>
										    
										     <c:when test="${func == 'indicado'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.indicado"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'indicacoes'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.indicacoes"/></p>
										    </c:when>
										    
										     <c:when test="${func == 'comentariosSobreMeusImoveis'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.comentariosSobreMeusImoveis"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'meusComentarios'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.meusComentarios"/></p>
										    </c:when>													
											
											<c:when test="${func == 'listarMinhasNotificacoes'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.listarMinhasNotificacoes"/></p>
										    </c:when>
											
											<c:when test="${func == 'preferenciasImoveis'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.preferenciasImoveis"/></p>
										    </c:when>
											
											<c:when test="${func == 'meuPerfilUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.meuPerfilUsuario"/></p>
										    </c:when>
											
											<c:when test="${func == 'detalhesUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.detalhesUsuario"/></p>
										    </c:when>
											
											<c:when test="${func == 'cadastroUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.cadastroUsuario"/></p>
										    </c:when>	
											
											<c:when test="${func == 'alterarFotoUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.alterarFotoUsuario"/></p>
										    </c:when>	
											
											<c:when test="${func == 'editarUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.editarUsuario"/></p>
										    </c:when>	
											
											<c:when test="${func == 'relatorio'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.relatorio"/></p>
										    </c:when>											
											
											<c:when test="${func == 'cadastroImovel'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.cadastroImovel"/></p>
										    </c:when>
											
											<c:when test="${func == 'editarImovel'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.editarImovel"/></p>
										    </c:when>
										    
										    
										    
										    
										    <c:when test="${func == 'tipoImoveisMaisProcuradoPorLocalizacao'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.tipoImoveisMaisProcuradoPorLocalizacao"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'variacaoPrecosPorTipoImovelPorLocalizacao'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.variacaoPrecosPorTipoImovelPorLocalizacao"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'sobreEstados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.sobreEstados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'sobreCidades'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.sobreCidades"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'sobreBairros'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.sobreBairros"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'quantImoveisPorLocalizacaoAcaoTipoImovel'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.quantImoveisPorLocalizacaoAcaoTipoImovel"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisVisualizados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.imoveisMaisVisualizados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisPropostados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.imoveisMaisPropostados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisComentados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.imoveisMaisComentados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisAdotadosInteressados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.imoveisMaisAdotadosInteressados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'corretoresMaisIntermediacoesAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.corretoresMaisIntermediacoesAceitas"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'corretoresMaisParceriasAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.corretoresMaisParceriasAceitas"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imobiliariaMaisIntermediacoesAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.imobiliariaMaisIntermediacoesAceitas"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imobiliariasMaisParceriasAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.descricao.rel.imobiliariasMaisParceriasAceitas"/></p>
										    </c:when>
											
										</c:choose>
								        
                                    </div>
                                    
                                    <div class="tab-pane fade" id="tab2-2">
                                        <h4><spring:message code="lbl.title.modal.objetivo"/></h4>
                                        
                                         <c:choose>
                                        	<c:when test="${func == 'buscarImoveis'}" >
										       <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.buscarImoveis"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'buscarImoveisMapa'}" >
										       <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.buscarImoveisMapa"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarMeusImoveis'}" >
										       <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.listarMeusImoveis"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'buscarUsuarios'}" >
										       <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.buscarUsuarios"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'buscarUsuariosPreferenciaImoveis'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.buscarUsuariosPreferenciaImoveis"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarImoveisComparativos'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.listarImoveisComparativos"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarPlanosUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.listarPlanosUsuario"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarServicosUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.listarServicosUsuario"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarImovelFavoritosMeusInteresse'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.listarImovelFavoritosMeusInteresse"/></p>
										    </c:when>	
										    
										     <c:when test="${func == 'listarImovelFavoritosUsuarioInteressado'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.listarImovelFavoritosUsuarioInteressado"/></p>
										    </c:when>	
										    
										    <c:when test="${func == 'meusImoveisVisitados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.meusImoveisVisualizados"/></p>
										    </c:when>		
										    
										    <c:when test="${func == 'minhasVisitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.minhasVisualizacoes"/></p>
										    </c:when>	
										    
										    <c:when test="${func == 'intermediacaoAceita'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.intermediacaoAceita"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'intermediacaoSolRecebida'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.intermediacaoSolRecebida"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'intermediacaoMinhasSol'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.intermediacaoMinhasSol"/></p>
										    </c:when>
										    
										      <c:when test="${func == 'parceriaAceita'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.parceriaAceita"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'parceriaSolRecebida'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.parceriaSolRecebida"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'parceriaMinhasSol'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.parceriaMinhasSol"/></p>
										    </c:when>	
										    
										     <c:when test="${func == 'minhasNotas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.minhasNotas"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'notasContato'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.notasContato"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'PropostasRecebidas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.propostasRecebidas"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'PropostasLancadas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.propostasLancadas"/></p>
										    </c:when>
										    
										     <c:when test="${func == 'indicado'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.indicado"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'indicacoes'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.indicacoes"/></p>
										    </c:when>	
										    
										      <c:when test="${func == 'comentariosSobreMeusImoveis'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.comentariosSobreMeusImoveis"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'meusComentarios'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.meusComentarios"/></p>
										    </c:when>

											<c:when test="${func == 'listarMinhasNotificacoes'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.listarMinhasNotificacoes"/></p>
										    </c:when>

											<c:when test="${func == 'preferenciasImoveis'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.preferenciasImoveis"/></p>
										    </c:when>

											<c:when test="${func == 'meuPerfilUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.meuPerfilUsuario"/></p>
										    </c:when>		

											<c:when test="${func == 'detalhesUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.detalhesUsuario"/></p>
										    </c:when>	
											
											<c:when test="${func == 'cadastroUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.cadastroUsuario"/></p>
										    </c:when>	
											
											<c:when test="${func == 'alterarFotoUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.alterarFotoUsuario"/></p>
										    </c:when>	
											
											<c:when test="${func == 'editarUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.editarUsuario"/></p>
										    </c:when>
											
											<c:when test="${func == 'relatorio'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.relatorio"/></p>
										    </c:when>
											
											<c:when test="${func == 'cadastroImovel'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.cadastroImovel"/></p>
										    </c:when>
											
											<c:when test="${func == 'editarImovel'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.editarImovel"/></p>
										    </c:when>
										    
										    
										    
										    
										    
										     <c:when test="${func == 'tipoImoveisMaisProcuradoPorLocalizacao'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.tipoImoveisMaisProcuradoPorLocalizacao"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'variacaoPrecosPorTipoImovelPorLocalizacao'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.variacaoPrecosPorTipoImovelPorLocalizacao"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'sobreEstados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.sobreEstados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'sobreCidades'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.sobreCidades"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'sobreBairros'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.sobreBairros"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'quantImoveisPorLocalizacaoAcaoTipoImovel'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.quantImoveisPorLocalizacaoAcaoTipoImovel"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisVisualizados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.imoveisMaisVisualizados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisPropostados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.imoveisMaisPropostados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisComentados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.imoveisMaisComentados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisAdotadosInteressados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.imoveisMaisAdotadosInteressados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'corretoresMaisIntermediacoesAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.corretoresMaisIntermediacoesAceitas"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'corretoresMaisParceriasAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.corretoresMaisParceriasAceitas"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imobiliariaMaisIntermediacoesAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.imobiliariaMaisIntermediacoesAceitas"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imobiliariasMaisParceriasAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.objetivos.rel.imobiliariasMaisParceriasAceitas"/></p>
										    </c:when>
											
										 </c:choose>
                                        
                                    </div>    
                                    
                                    <div class="tab-pane fade" id="tab2-3">
                                        <h4><spring:message code="lbl.title.modal.regras.basicas"/></h4>
                                        
                                        <c:choose>
                                        	<c:when test="${func == 'buscarImoveis'}" >
										       <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.buscarImoveis"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'buscarImoveisMapa'}" >
										       <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.buscarImoveisMapa"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarMeusImoveis'}" >
										       <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.listarMeusImoveis"/></p>
										    </c:when>
										    
										     <c:when test="${func == 'buscarUsuarios'}" >
										       <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.buscarUsuarios"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'buscarUsuariosPreferenciaImoveis'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.buscarUsuariosPreferenciaImoveis"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarImoveisComparativos'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.listarImoveisComparativos"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarPlanosUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.listarPlanosUsuario"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'listarServicosUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.listarServicosUsuario"/></p>
										    </c:when>		
										    
										    <c:when test="${func == 'listarImovelFavoritosMeusInteresse'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.listarImovelFavoritosMeusInteresse"/></p>
										    </c:when>	
										    
										    <c:when test="${func == 'listarImovelFavoritosUsuarioInteressado'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.listarImovelFavoritosUsuarioInteressado"/></p>
										    </c:when>		
										    
										    <c:when test="${func == 'meusImoveisVisitados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.meusImoveisVisualizados"/></p>
										    </c:when>		
										    
										    <c:when test="${func == 'minhasVisitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.minhasVisualizacoes"/></p>
										    </c:when>
										    
										    
										    <c:when test="${func == 'intermediacaoAceita'}">
										    	<c:if test="${usuario != 'padrao'}" >
											   		<p><spring:message code="msg.ajuda.modal.funcionalidades.regras.intermediacaoAceita"/></p>
											   	</c:if>	
											   	
											   	<c:if test="${usuario == 'P'}" >
													<p><spring:message code="msg.ajuda.modal.funcionalidades.regras.intermediacaoAceita.cliente"/></p>   	
											   	</c:if>
											</c:when>

										    
										    <c:when test="${func == 'intermediacaoSolRecebida'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.intermediacaoSolRecebida"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'intermediacaoMinhasSol'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.intermediacaoMinhasSol"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'parceriaAceita'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.parceriaAceita"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'parceriaSolRecebida'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.parceriaSolRecebida"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'parceriaMinhasSol'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.parceriaMinhasSol"/></p>
										    </c:when>
										    
										      <c:when test="${func == 'minhasNotas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.minhasNotas"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'notasContato'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.notasContato"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'PropostasRecebidas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.propostasRecebidas"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'PropostasLancadas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.propostasLancadas"/></p>
										    </c:when>	
										    
										     <c:when test="${func == 'indicado'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.indicado"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'indicacoes'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.indicacoes"/></p>
										    </c:when>	
										    
										    <c:when test="${func == 'comentariosSobreMeusImoveis'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.comentariosSobreMeusImoveis"/></p>
										    </c:when>					
										    
										    <c:when test="${func == 'meusComentarios'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.meusComentarios"/></p>
										    </c:when>

											<c:when test="${func == 'listarMinhasNotificacoes'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.listarMinhasNotificacoes"/></p>
										    </c:when>

											<c:when test="${func == 'preferenciasImoveis'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.preferenciasImoveis"/></p>
										    </c:when>	

											<c:when test="${func == 'meuPerfilUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.meuPerfilUsuario"/></p>
										    </c:when>

											<c:when test="${func == 'detalhesUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.detalhesUsuario"/></p>
										    </c:when>

											<c:when test="${func == 'cadastroUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.cadastroUsuario"/></p>
										    </c:when>

											<c:when test="${func == 'alterarFotoUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.alterarFotoUsuario"/></p>
										    </c:when>	
											
											<c:when test="${func == 'editarUsuario'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.editarUsuario"/></p>
										    </c:when>	

											<c:when test="${func == 'relatorio'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.relatorio"/></p>
										    </c:when>
											
											<c:when test="${func == 'cadastroImovel'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.cadastroImovel"/></p>
										    </c:when>
											
											<c:when test="${func == 'editarImovel'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.editarImovel"/></p>
										    </c:when>
										    
										    
										    
										    <c:when test="${func == 'tipoImoveisMaisProcuradoPorLocalizacao'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.tipoImoveisMaisProcuradoPorLocalizacao"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'variacaoPrecosPorTipoImovelPorLocalizacao'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.variacaoPrecosPorTipoImovelPorLocalizacao"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'sobreEstados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.sobreEstados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'sobreCidades'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.sobreCidades"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'sobreBairros'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.sobreBairros"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'quantImoveisPorLocalizacaoAcaoTipoImovel'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.quantImoveisPorLocalizacaoAcaoTipoImovel"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisVisualizados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.imoveisMaisVisualizados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisPropostados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.imoveisMaisPropostados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisComentados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.imoveisMaisComentados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imoveisMaisAdotadosInteressados'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.imoveisMaisAdotadosInteressados"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'corretoresMaisIntermediacoesAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.corretoresMaisIntermediacoesAceitas"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'corretoresMaisParceriasAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.corretoresMaisParceriasAceitas"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imobiliariaMaisIntermediacoesAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.imobiliariaMaisIntermediacoesAceitas"/></p>
										    </c:when>
										    
										    <c:when test="${func == 'imobiliariasMaisParceriasAceitas'}">
										        <p><spring:message code="msg.ajuda.modal.funcionalidades.regras.rel.imobiliariasMaisParceriasAceitas"/></p>
										    </c:when>
                                        
                                        </c:choose>
                                    </div>
                                                                    
                                </div>
                            </div>
                            <!--/ End tabs content -->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="lbl.title.modal.fechar"/></button>                            
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->            
			<!--/ End - modal Ajuda - informações funcionalidade -->