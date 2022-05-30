package fr.eni.ProjetEncheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ProjetEncheres.bll.BusinessException;
import fr.eni.ProjetEncheres.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
	
	private static final String sqlSelectPseudoEtMDP = "SELECT pseudo,mot_de_passe FROM UTILISATEURS WHERE pseudo = ? or email = ? ";
	private static final String sqlSelectPseudo = "SELECT pseudo,nom,prenom,email FROM UTILISATEURS WHERE pseudo like ? or nom like ? or prenom like ? ";
	private static final String sqlInsert =  "INSERT INTO utilisateurs(pseudo,nom,prenom,email,telephone,rue,code_postal,ville, mot_de_passe, credit, administrateur) values(?,?,?,?,?,?,?,?,?,?,?)";
	private static final String sqlDelete = "DELETE from utilisateurs WHERE no_utilisateur=?";
	
	// Insertion d'un utilisateur dans la base de données avec no_utilisateur ajouté automatiquement
	@Override
	public void insert(Utilisateur utilisateur) throws BusinessException {
		if(utilisateur==null)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatsDAL.INSERT_UTIL_NULL);
			throw businessException;
		}
		
		try (Connection cnx = ConnectionBDD.getConnection() ){

				PreparedStatement stmt = cnx.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);
				int index = 1;
				stmt.setString(index++, utilisateur.getPseudo());
				stmt.setString(index++, utilisateur.getNom());
				stmt.setString(index++, utilisateur.getPrenom());
				stmt.setString(index++, utilisateur.getEmail());
				stmt.setString(index++, utilisateur.getTelephone());
				stmt.setString(index++, utilisateur.getRue());
				stmt.setString(index++, utilisateur.getCodePostal());
				stmt.setString(index++, utilisateur.getVille());
				stmt.setString(index++, utilisateur.getMotDePasse());
				stmt.setInt(index++, utilisateur.getCredit());
				stmt.setBoolean(index++, utilisateur.isAdmnistrateur());
			
				stmt.executeUpdate();
				
				stmt.getGeneratedKeys();
				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next()) {
					int no_utilisateur = rs.getInt(1); 
					utilisateur.setNoUtilisateur(no_utilisateur);
					System.out.println("Clé générée automatiquement pour l'id: " + no_utilisateur);
				}
				System.out.println("Utilisateur inséré.");
			} catch (Exception e) 
		{
				e.printStackTrace();
		}
	}

	// Suppression d'un utilisateur par id (no_utilisateur)
	@Override
	public void deleteById(Integer id) throws BusinessException {
		if(id == null)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatsDAL.SUPPRESS_UTIL_NULL);
			throw businessException;
		}
		
		try (Connection cnx = ConnectionBDD.getConnection())
		{
					PreparedStatement stmt = cnx.prepareStatement(sqlDelete);
					stmt.setInt(1, id);
					stmt.executeUpdate();
					System.out.println("Utilisateur supprimé.");
				} catch (Exception e) 
		{
					e.printStackTrace();
		}

	}
	
public boolean VerificationPseudoEtMDP(Utilisateur utilisateur) {
		
		Connection cnx = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean pseudo = false;
		
		try {
			cnx = ConnectionBDD.getConnection();
			stmt =  cnx.prepareStatement(sqlSelectPseudoEtMDP);
			stmt.setString(1, utilisateur.getPseudo() );
			stmt.setString(2,  utilisateur.getEmail() );
			rs = stmt.executeQuery();
			if (rs.next() && rs.getString(2).equals( utilisateur.getMotDePasse())) {
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
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

public List<Utilisateur> getInfos(Utilisateur utilisateur) {
	List<Utilisateur> liste = new ArrayList<>();
	Connection cnx = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	Utilisateur user = null;
	
	try {
		cnx = ConnectionBDD.getConnection();
		stmt =  cnx.prepareStatement(sqlSelectPseudo);
		stmt.setString(1,"%" +utilisateur.getPseudo() + "%");
		stmt.setString(2,"%" + utilisateur.getNom() + "%");
		stmt.setString(3,"%" + utilisateur.getPrenom() + "%");
		rs = stmt.executeQuery();
		while (rs.next()) {
			 user = new Utilisateur(	 23, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					"", "", "", "", "", false);
			liste.add(user);
			
			
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
	
	return liste;
}
	

	

}