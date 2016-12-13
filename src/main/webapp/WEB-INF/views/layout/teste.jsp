 <!-- Start messages -->
                        <li class="dropdown navbar-message">
							<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasMensagens").toString()) > 0l ) {%>
								<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope-o"></i><span class="count label label-danger rounded"><%= request.getSession().getAttribute("quantNovasMensagens") %></span></a>
							<% } else { %>
								<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope-o"></i></a>							
							<% }  %>                            

                            <!-- Start dropdown menu -->
                            <div class="dropdown-menu animated flipInX">
                                <div class="dropdown-header">  
                                	<% if ( Long.parseLong(request.getSession().getAttribute("quantNovasMensagens").toString()) > 0l ) {%>
                                		<a href="${urlMensagem}/minhasMensagens" class="media" >
	                                    	<span class="title"><spring:message code="lbl.title.link.mensagens"/> <strong>(<%= request.getSession().getAttribute("quantNovasMensagens") %>)</strong></span>
	                                    </a>
                                	<% } else { %>
		                            	<a href="#" class="media" >
	                                    	<span class="title"><spring:message code="lbl.nenhuma.mensagem.recebida"/> <strong></strong></span>
	                                    </a>
		                             <% }  %>
                                	
                                    <span class="option text-right"><a href="#">+ Mais Mensagens</a></span>
                                </div> 
                                <div class="dropdown-body">

                                    <!-- Start message search -->
                                    <form class="form-horizontal" action="#">
                                       
                                    </form>
                                    <!--/ End message search -->

                                    <!-- Start message list -->
                                    <div class="media-list niceScroll">
                                    	<c:if test="${!empty sessionScope.listaMensagens}">
                                    		<c:forEach var="mensagem" items="${sessionScope.listaMensagens}">                                    		
	                                    		<a href="page-messages.html" class="media">
		                                            <div class="pull-left"><img src="${context}/${mensagem.imagemUsuario}" style="width: 50px; height: 50px; "  class="media-object img-circle" alt="..."/></div>
		                                            <div class="media-body">
		                                                <span class="media-heading"><c:if test="${mensagem.idUsuarioDe == usuario.id}">
													                    	       ${mensagem.nomeUsuarioPara}												                    	  	
													                    	    </c:if>
													                    	    <c:if test="${mensagem.idUsuarioPara == usuario.id}">
													                    	   	   ${mensagem.nomeUsuarioDe}
													                    	    </c:if></span>
		                                                <span class="media-text">${mensagem.descricao}</span>		                                                		                                                
		                                                <span class="media-meta pull-right"><fmt:formatDate value="${mensagem.dataMensagem}" pattern='dd/MM/yyyy'/></span>		                                                
		                                            </div><!-- /.media-body -->
		                                        </a><!-- /.media -->
                                    		</c:forEach>		
                                    	</c:if>
                                              <!-- Start message indicator -->
                                        <a href="#" class="media indicator inline">
                                            <span class="spinner">Load more messages...</span>
                                        </a>
                                        <!--/ End message indicator -->

                                    </div>
                                    <!--/ End message list -->

                                </div>
                                <div class="dropdown-footer"> 
                                    <a href="page-messages.html"><spring:message code="lbl.title.see.all"/></a>
                                </div>
                            </div>
                            <!--/ End dropdown menu -->

                        </li><!-- /.dropdown navbar-message -->
                        <!--/ End messages -->