package fr.eni.enchere.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class ServletModificationProfil
 */
@WebServlet("/ServletModificationProfil")
public class ServletModificationProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur connectedUser = (Utilisateur) request.getSession().getAttribute("userConnected");
		request.setAttribute("user", connectedUser);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifier_profil.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur connectedUser = (Utilisateur) request.getSession().getAttribute("userConnected");
		Integer noUtilisateur = connectedUser.getNoUtilisateur();
		Utilisateur user = new Utilisateur( request.getParameter("pseudo"),
											request.getParameter("nom"),
											request.getParameter("prenom"),
											request.getParameter("email"),
											request.getParameter("telephone"),
											request.getParameter("rue"), 
											request.getParameter("codePostal"),
											request.getParameter("ville"),
											request.getParameter("motDePasse"));
		
		user.setNoUtilisateur(noUtilisateur);
		
		try {
			UtilisateurManager.getInstance().update(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//response.sendRedirect("mon_profil");
		request.setAttribute("user", user);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/mon_profil.jsp");
		rd.forward(request, response);																	
		
	}

}
