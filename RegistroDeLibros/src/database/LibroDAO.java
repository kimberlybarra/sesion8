package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Libro;

public class LibroDAO {
	
	public void agregarLibroConConfirmacion(Libro libro) {
        String sql = "INSERT INTO libro (titulo, autor_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.connect()) {
            conn.setAutoCommit(false); 
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, libro.getTitulo());
                pstmt.setInt(2, libro.getAutorId());
                pstmt.executeUpdate();

                conn.commit();
                System.out.println("Libro agregado exitosamente.");
            } catch (SQLException e) {
                conn.rollback(); // Revertir si hay error
                System.out.println("Error al agregar libro: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
    public void agregarLibro(Libro libro) {
        String sql = "INSERT INTO libro (titulo, autor_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setInt(2, libro.getAutorId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Libro> obtenerLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libro";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                int autorId = rs.getInt("autor_id");
                libros.add(new Libro(id, titulo, autorId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return libros;
    }
}

