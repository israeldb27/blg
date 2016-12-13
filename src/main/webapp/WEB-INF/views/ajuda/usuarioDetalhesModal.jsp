<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="<%= request.getContextPath()%>"/>

<script type="text/javascript" src="${context}/js/jquery-1.9.1.min.js"></script>

<spring:url value="/usuario" var="urlUsuario"/>

<script type="text/javascript">
function carregaDetalhesUsuario(id){		
		var parametro1 = id;		
  	    $.ajax({  	    	
              type: 'GET',
              url: '${urlUsuario}/carregaModalDetalhesUsuario/' + parametro1,
              dataType: 'json',
              success: function(data){
            	  $("#myModalUsuario").modal("show");
            	  $("#modNomeUsuario").val(data.nome);
            	  $("#modPerfilUsuario").val(data.perfilFmt);
            	  $("#modIdUsuario").val(data.id);
            	  $("#modEstado").val(data.estado);
            	  $("#modSobreMim").val(data.descSobreMim);            	                  	  
              },
              error: function(jqXHR, textStatus, errorThrown) {
                  alert("OPSSSS!" + textStatus + "-" + errorThrown + "-"+jqXHR);
              }
          });  
	}
</script>
 
<!-- START Modal - Detalhes Usuario -->             
            <div id="myModalUsuario" class="modal fade bs-example-modal-detalhes-imovel" tabindex="-1" role="dialog" aria-hidden="true">              
                <div class="modal-dialog modal-lg modal-photo-viewer">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title"><spring:message code="lbl.detalhes.usuario"/></h4> 
                        </div>
                        <div class="modal-body no-padding">
                            <form name="userForm" class="form-horizontal form-bordered" role="form" action="${urlUsuario}/detalhesUsuario">
                            	<input type="hidden" id="modIdUsuario" readonly="readonly" name="modIdUsuario">
                                <div class="form-body">
                                
                                	<div class="form-group">                                	
                                        <label for="idTituloImovelSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.nome.usuario"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modNomeUsuario" readonly="readonly" name="modNomeUsuario">                                            
                                        </div>
                                    </div><!-- /.form-group -->
                                    
                                    <div class="form-group">
                                        <label for="idTipoImovel" class="col-sm-3 control-label"><spring:message code="lbl.perfil.usuario"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modPerfilUsuario" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->                                    
                                	
                                    <div class="form-group">
                                        <label for="idEstadoSelecionado" class="col-sm-3 control-label"><spring:message code="lbl.estado"/>: </label>
                                        <div class="col-sm-7">
                                            <input type="text" class="form-control input-sm" id="modEstado" readonly="readonly">
                                        </div>
                                    </div><!-- /.form-group -->
                                                                    
                               
                                     <div class="form-group">
                                        <label for="idDataCadastro" class="col-sm-3 control-label"><spring:message code="lbl.sobre.mim"/>: </label>
                                        <div class="col-sm-7">                                        	 
                                            	<input type="text" class="form-control input-sm" id="modSobreMim" readonly="readonly">                                            	
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
            <!-- END Modal - Detalhes Usuario -->