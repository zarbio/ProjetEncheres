package fr.eni.ProjetEncheres;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ProjetEncheres.bll.UtilisateurManager;
import fr.eni.ProjetEncheres.bo.Utilisateur;
import fr.eni.ProjetEncheres.CodesResultatsServlets;

/**
 * Servlet implementation class ServletInscription
 */
@WebServlet("/ServletInscription")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Inscription.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String pseudo;
		String email;
		String motdepasse;
		String nom;
		String prenom;
		String adresse;
		String codepostal;
		String ville; 
		String confirmation;
		String telephone;
		
		try {
			pseudo = request.getParameter("pseudo");
			motdepasse = request.getParameter("motdepasse");
			email = request.getParameter("email");
			nom = request.getParameter("Nom");
			prenom = request.getParameter("Prenom");
			adresse = request.getParameter("Adresse");
			codepostal = request.getParameter("Cp");
			ville = request.getParameter("Ville");
			email = request.getParameter("email");
			confirmation = request.getParameter("Confirm");
			telephone = request.getParameter("Telephone");
			
			Utilisateur utilisateurTest; 
			Utilisateur nouveauxUtilisateur = null;
			
				
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			
			utilisateurTest = new Utilisateur(0, pseudo, nom, prenom, email, telephone, adresse, codepostal, 
					ville, motdepasse, false);
			
			// Le pseudo n'accepte que des caractères alphanumériques. 
			// Vérifier que la base de données ne contient pas déjà le pseudo et l'email
			if (pseudo.matches("^[a-zA-Z0-9@.]{2,10}$") 
					&& !utilisateurManager.VerificationPseudoEtMDP(utilisateurTest)) {
				
				
			// BLL : UtilisateurManager (avec méthode ajouter).
			nouveauxUtilisateur= (Utilisateur) utilisateurManager.ajouter(0, pseudo, nom, prenom, email, telephone, adresse, codepostal, 
					ville, motdepasse, false);
			
 			}
			
			request.setAttribute("nouveauxUtilisateur", nouveauxUtilisateur);
			session.setAttribute("connecte", true);
			session.setAttribute("pseudosession", nouveauxUtilisateur.getPseudo());
		}
		catch (Exception e)
		{
			List<Integer> listeCodesErreur=new ArrayList<>();
			listeCodesErreur.add(CodesResultatsServlets.FORMAT_PSEUDO_UTILISATEUR_ERREUR);
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			
		}
			
			// Si c'est bon redirection vers page d'accueil 
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Accueil.jsp");
			rd.forward(request, response);
	}		

}
