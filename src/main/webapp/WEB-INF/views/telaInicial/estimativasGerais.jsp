<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:url var="urlImovelFavoritos" value="/imovelFavoritos"/>
<spring:url var="urlImovelVisualizado" value="/imovelVisualizado"/>
<spring:url var="urlImovelComentario" value="/imovelComentario"/>
<spring:url var="urlImovelPropostas" value="/imovelPropostas"/>

<div class="row">
          <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
              <div class="mini-stat-type-2 shadow border-danger">
                  <h3 class="text-center text-thin"><spring:message code="lbl.title.propostas.estimativas.gerais"/> </h3>
                  <p class="text-center">
                      <span class="overview-icon bg-danger"><i class="icon-arrow-up"></i></span>
                  </p>
                  <p>
                      <b><%= request.getSession().getAttribute("quantTotalImoveisPropostas") %> <spring:message code="lbl.title.propostas.recebidas.estimativas.gerais"/></b>
                  </p>
                  <p>    
                      <b><%= request.getSession().getAttribute("quantNovasImoveisPropostas") %> <spring:message code="lbl.title.novas.propostas.recebidas.estimativas.gerais"/></b>  
                  </p>
                  <p class="text-muted">
                  	  <a href="${urlImovelPropostas}/listarImoveisPropostas/propostasRecebidas">
                      	<spring:message code="lbl.title.visualizar.propostas.estimativas.gerais"/>
                      </a>	
                  </p>
              </div>
          </div>
          <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
              <div class="mini-stat-type-2 shadow border-success">		  	
                  <h3 class="text-center text-thin"><spring:message code="lbl.title.visualizacoes.estimativas.gerais"/></h3>
                  <p class="text-center">
                      <span class="overview-icon bg-success"><i class="icon-arrow-up"></i></span>
                  </p>
                  <p>
                      <b> <%= request.getSession().getAttribute("quantTotalVisitantes") %> <spring:message code="lbl.title.visualizacoes.recebidas.estimativas.gerais"/></b> 
                  </p>
                  <p>    
                      <b> <%= request.getSession().getAttribute("quantNovosVisitantes") %> <spring:message code="lbl.title.novas.visualizacoes.recebidas.estimativas.gerais"/></b>  
                  </p>
                  <p class="text-muted">
                  	  <a href="${urlImovelVisualizado}/listarUsuariosVisitantes/meusImoveisVisitados">	
                  	  	<spring:message code="lbl.title.visualizar.visualizacoes.estimativas.gerais"/>
                  	  </a>	
                  </p>
              </div>
          </div>
          
           <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
              <div class="mini-stat-type-2 shadow border-success">
                  <h3 class="text-center text-thin"><spring:message code="lbl.title.comentarios.estimativas.gerais"/></h3>
                  <p class="text-center">
                      <span class="overview-icon bg-success"><i class="icon-arrow-up"></i></span>
                  </p>
                  <p>
                      <b><%= request.getSession().getAttribute("quantTotalComentarios") %>  <spring:message code="lbl.title.comentarios.recebidas.estimativas.gerais"/></b> 
                  </p>
                  <p>    
                      <b><%= request.getSession().getAttribute("quantNovoComentario") %> <spring:message code="lbl.title.novas.comentarios.recebidas.estimativas.gerais"/></b>  
                  </p>
                  <p class="text-muted">
	                  	<a href="${urlImovelComentario}/listarMeusComentarios/comentariosSobreMeusImoveis" >
	                  		<spring:message code="lbl.title.visualizar.comentarios.estimativas.gerais"/>
	                  	</a>
                  </p>
              </div>
          </div>
          
           <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
              <div class="mini-stat-type-2 shadow border-success">
                  <h3 class="text-center text-thin"><spring:message code="lbl.title.lista.Favoritos.estimativas.gerais"/></h3>
                  <p class="text-center">
                      <span class="overview-icon bg-success"><i class="icon-arrow-up"></i></span>
                  </p>
                  <p>
                      <b><%= request.getSession().getAttribute("quantTotalUsuarioInteressado") %>  <spring:message code="lbl.title.lista.Favoritos.recebidas.estimativas.gerais"/></b> 
                  </p>
                  <p>    
                      <b><%= request.getSession().getAttribute("quantNovoUsuarioInteressado") %>  <spring:message code="lbl.title.novas.lista.Favoritos.recebidas.estimativas.gerais"/></b>  
                  </p>
                  <p class="text-muted">
                  	  <a href="${urlImovelFavoritos}/listarInteresse/usuariosInteressados">
                  	  		<spring:message code="lbl.title.visualizar.lista.Favoritos.estimativas.gerais"/>
                  	  </a>		
                  </p>
              </div>
          </div> 
          
                                        
      </div>