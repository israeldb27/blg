<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/imovel" var="urlImovel"/>

<script type="text/javascript">
function carregaDetalhesImovel(id){
    		var parametro1 = id;
      	    $.ajax({
                  type: 'GET',
                  url: '${urlImovel}/carregaModalDetalhesImovel/' + parametro1,
                  dataType: 'json',
                  success: function(data){
                	  	
                	  $("#myModal").modal("show");
                	  $("#modIdImovel").val(data.id);
                	  $("#modTituloImovel").val(data.titulo);
                	  $("#modEstado").val(data.estado);
                	  $("#modCidade").val(data.cidade);
                	  $("#modBairro").val(data.bairro);
                	  $("#modDataCadastroImovel").val(data.dataCadastroFmt);
                	  $("#modTipoImovel").val(data.tipoImovelFmt);
                	  $("#modAcaoImovel").val(data.acaoFmt);                	  
                  },
                  error: function(jqXHR, textStatus, errorThrown) {
                      alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
                  }
              });  
    	}
</script>

 <!-- START Modal - Detalhes Imoveis -->             
            <div id="myModal" class="modal fade bs-example-modal-detalhes-imovel" role="dialog" aria-hidden="true" >              
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.detalhes.imovel"/></h4> 
                        </div>
                        <div class="modal-body no-padding">
                            <form name="userForm" class="form-horizontal form-bordered" role="form" action="${urlImovel}/detalhesImovel">
                            <input type="hidden" id="modIdImovel" readonly="readonly" name="modIdImovel">
                                <div class="form-body">
                                	<div class="form-group">                                	
                                        <label for="idTituloImovelSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.titulo.imovel"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modTituloImovel" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="idTipoImovel" class="col-sm-3 control-label"><spring:message code="lbl.tipo.imovel"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modTipoImovel" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                	
                                    <div class="form-group">
                                        <label for="idEstadoSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.estado"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modEstado" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="idCidadeSelecionada" class="col-sm-3 control-label"><spring:message code="lbl.cidade"/>: </label>
                                        <div class="col-sm-7">
                                        	<input type="text" class="form-control input-sm" id="modCidade" readonly="readonly">                                            
                                        </div>
                                    </div><!-- /.form-group -->                                    
                                    <div class="form-group">
                                        <label for="idBairroSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.bairro"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modBairro" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                     <div class="form-group">
                                        <label for="idDataCadastro" class="col-sm-3 control-label"><spring:message code="lbl.data.cadastro.imovel"/>: </label>
                                        <div class="col-sm-7">                                        	 
                                            	<input type="text" class="form-control input-sm" id="modDataCadastroImovel" readonly="readonly">                                            	
                                        </div>
                                    </div><!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="idAcao" class="col-sm-3 control-label"><spring:message code="lbl.acao.imovel"/>: </label>
                                        <div class="col-sm-7">
                                             <input type="text" class="form-control input-sm" id="modAcaoImovel" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                </div><!-- /.form-body -->
                                <div class="form-footer">
                                    <div class="col-sm-offset-3">
                                    	<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                                        <button type="submit" class="btn btn-success" >Mais Detalhes</button>
                                    </div>
                                </div><!-- /.form-footer -->
                            </form>
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->               
            </div><!-- /.modal -->            
            <!-- END Modal - Detalhes Imoveis -->



