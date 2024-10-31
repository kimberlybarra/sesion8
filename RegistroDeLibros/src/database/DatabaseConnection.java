package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
   private static final String URL = "jdbc:sqlite:C:/Users/kimberly/Desktop/registro_libros.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Conexión a la base de datos establecida.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public static void createTables() {
        String sqlAutor = "CREATE TABLE IF NOT EXISTS autor (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)";
        String sqlLibro = "CREATE TABLE IF NOT EXISTS libro (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT NOT NULL, autor_id INTEGER, FOREIGN KEY (autor_id) REFERENCES autor (id))";

        try (Connection conn = connect();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute(sqlAutor);
            stmt.execute(sqlLibro);
            System.out.println("Tablas creadas o ya existen.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
