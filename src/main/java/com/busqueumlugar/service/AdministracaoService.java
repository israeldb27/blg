package com.busqueumlugar.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.FiltroRelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Usuario;

public interface AdministracaoService {
	
	 List<Usuario> consultaUsuarios(String valorBusca, UsuarioForm usuarioForm);
	 
	 UsuarioForm detalhesUsuario(Usuario usuario);

	 List<Imovel> buscarImovel(AdministracaoForm admForm);
	 
	 void carregaInfoDadosUsuarioAdmin(UsuarioForm user, HttpSession session);

	 void gerarRelatorioPlanos(AdministracaoForm form, String tipoRelatorio);

	 void gerarRelatorioServicos(AdministracaoForm form, String tipoRelatorio);

	 void gerarRelatorioUsuarios(AdministracaoForm form, String tipoRelatorio);

	 void gerarRelatorioAssinaturas(AdministracaoForm form, String tipoRelatorio);

	 void gerarRelatorioImoveis(AdministracaoForm form, String tipoRelatorio);
	 
	 List<FiltroRelatorioForm> checarFiltrosUtilizados(AdministracaoForm form);

	 boolean validarGerarRelatorios(AdministracaoForm form, String tipoRelatorio , BindingResult result); 
}
