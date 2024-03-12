package ma.ac.esi.referentielCompetences.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {
    private String url = "jdbc:mysql://localhost:3306/competency_framework"; 
    private String username = "root";  
    private String password = "";  

    private Connection getConnection() throws SQLException { 
        return DriverManager.getConnection(url, username, password); 
    } 

    // Méthode pour vérifier si la connexion à la base de données est réussie
    public boolean testConnection() {
        try {
            // Essayez de vous connecter à la base de données
            getConnection().close(); // Essayez simplement de récupérer une connexion
            System.out.println("Connexion à la base de données réussie.");
            return true; // Retourne true si la connexion réussit
        } catch (SQLException e) {
            // En cas d'échec de la connexion, affichez l'erreur et retournez false
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            return false;
        }
    }

    public boolean addSkill(Skill skill) { 
        String sql = "INSERT INTO skills (name, description, domain, level) VALUES (?, ?, ?, ?)"; 
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setString(1, skill.getName()); 
            pstmt.setString(2, skill.getDescription()); 
            pstmt.setString(3, skill.getDomain()); 
            pstmt.setString(4, skill.getLevel()); 
            pstmt.executeUpdate(); 
            int rowsAffected=pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false;
        } 
    } 
    public List<Skill> getAllSkills() {
    	 List<Skill> skills = new ArrayList<>();
    	 String sql = "SELECT * FROM Skills";
    	 try (Connection conn = getConnection();
    	 Statement stmt = conn.createStatement();

    	 ResultSet rs = stmt.executeQuery(sql)) {
    	 while (rs.next()) {
    	 Skill skill = new Skill();
    	 skill.setId(rs.getInt("id"));
    	 skill.setName(rs.getString("name"));
    	 skill.setDescription(rs.getString("description"));
    	 skill.setDomain(rs.getString("domain"));
    	 skill.setLevel(rs.getString("level"));
    	 skills.add(skill);
    	 }
    	 } catch (SQLException e) {
    	 e.printStackTrace();
    	 }
    	 return skills;
    	 }
}