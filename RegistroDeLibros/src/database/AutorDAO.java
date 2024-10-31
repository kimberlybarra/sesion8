package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Autor;

public class AutorDAO {
	
	public void agregarAutorConConfirmacion(Autor autor) {
        String sql = "INSERT INTO autor (nombre) VALUES (?)";

        try (Connection conn = DatabaseConnection.connect()) {
            conn.setAutoCommit(false); // Iniciar transacción manualmente
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, autor.getNombre());
                pstmt.executeUpdate();

                conn.commit();
                System.out.println("Autor agregado exitosamente.");
            } catch (SQLException e) {
                conn.rollback(); // Revertir si hay un error
                System.out.println("Error al agregar autor: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
	public void agregarAutor(Autor autor) {
	    String sql = "INSERT INTO autor (nombre) VALUES (?)";
	    try (Connection conn = DatabaseConnection.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, autor.getNombre());
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}


    public List<Autor> obtenerAutores() {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM autor";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                autores.add(new Autor(id, nombre));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return autores;
    }
}
