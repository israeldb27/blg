<div class="row">                            
        <c:forEach var="imovel" items="${listaSugestaoImoveisIntermediacao}">	                                
            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">                            	 
                <div class="panel rounded shadow">
                    <div class="panel-body bg-twitter">
                    	<h4 class="text-center text-thin">Sugestão para Intermediação</h4>		                                    
                        <ul class="inner-all list-unstyled">
                            <li class="text-center">
                                <img class="img-circle img-bordered-success" src="${context}${imovel.imagemArquivo}" style="width: 100px; height: 100px; ">
                            </li>
                            <li class="text-center">
                                <h4 class="text-capitalize">${imovel.titulo}</h4>
                                <p class="text-muted text-capitalize">${imovel.tipoImovelFmt}</p>
                            </li>
                            <li>
                                <a href="${urlImovel}/detalhesImovel/${imovel.id}" class="btn btn-success text-center btn-block">Ver</a>
                            </li>
                        </ul><!-- /.list-unstyled -->
                    </div><!-- /.panel-body -->
                </div><!-- /.panel -->
            </div>
		</c:forEach>
			
</div>