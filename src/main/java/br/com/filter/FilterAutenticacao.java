package br.com.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;

@WebFilter(urlPatterns = { "/*" })
public class FilterAutenticacao implements Filter {

	@Inject
	private JPAUtil jpaUtil;
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
		
		String url = req.getServletPath();
		
		if (!url.equalsIgnoreCase("index.jsf") && usuarioLogado == null) {

			if(url.equalsIgnoreCase("/novoUsuario.jsf")) {
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/novoUsuario.jsf");
				dispatcher.forward(request, response);
				return;
				
			}else {
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
				dispatcher.forward(request, response);
				return;
			}
			
		} else {
			//Se estiver tudo certo e o usuário já logado, ai executa  as ações do request e response
			chain.doFilter(request, response);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void init(FilterConfig arg0) throws ServletException {

		jpaUtil.getEntityManager();
	}
}
