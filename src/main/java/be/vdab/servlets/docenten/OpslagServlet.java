package be.vdab.servlets.docenten;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.DocentService;

@WebServlet("/docenten/opslag.htm")
public class OpslagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/docenten/opslag.jsp";
	private static final String REDIRECT_URL = "%s/docenten/zoeken.htm?id=%d";
	private final transient DocentService docentService = new DocentService();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> fouten = new HashMap<>();
		try {
			BigDecimal percentage = new BigDecimal(
					request.getParameter("percentage"));
			if (percentage.compareTo(BigDecimal.ZERO) <= 0) {
				fouten.put("percentage", " tik een positief getal");
			} else {
				long id = Long.parseLong(request.getParameter("id"));
				docentService.opslag(id, percentage);
				response.sendRedirect(response.encodeRedirectURL(String.format(
						REDIRECT_URL, request.getContextPath(), id)));
			}
		} catch (NumberFormatException ex) {
			fouten.put("percentage", "tik een positief getal");
		}
		if (!fouten.isEmpty()) {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}

}
