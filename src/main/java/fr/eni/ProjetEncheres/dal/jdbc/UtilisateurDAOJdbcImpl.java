package fr.eni.ProjetEncheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.ProjetEncheres.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
	
	private static final String sqlSelectPseudoEtMDP = "SELECT pseudo,mot_de_passe FROM UTILISATEURS WHERE pseudo = ? or email = ? and mot_de_passe=?";
	

	@Override
	public void insert(Utilisateur utilisateur) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Utilisateur utilisateur) {
		// TODO Auto-generated method stub

	}
	
	public boolean VerificationPseudoEtMDP(Utilisateur utilisateur) {
		
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean pseudo = false;
		
		try {
			cnx = ConnectionBDD.getConnection();
			stmt =  cnx.prepareStatement(sqlSelectPseudoEtMDP);
			stmt.setString(1,utilisateur.getPseudo());
			stmt.setString(2, utilisateur.getEmail());
			stmt.setString(3, utilisateur.getMotDePasse());
			rs = stmt.executeQuery();
			if (rs.next()) {
				System.out.println("pseudo");
				System.out.println("mail");
				pseudo = true;
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (cnx != null) {
				try {
					cnx.close();
				} catch (SQLException e) { 
					e.printStackTrace();
				}
			}
		
		}
		
		return pseudo;
	}
	
	

	

}
