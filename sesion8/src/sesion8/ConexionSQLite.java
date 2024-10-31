package sesion8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ConexionSQLite {
    public Connection conectar() {
        Connection conexion = null;
        try {
            String url = "jdbc:sqlite:C:/Users/kimberly/Desktop/sistema_notas.db";
            conexion = DriverManager.getConnection(url);
            System.out.println("Conexión establecida con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public void insertarNota(Connection conexion, String estudiante, String materia, double nota) {
        String sql = "INSERT INTO notas (estudiante, materia, nota) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, estudiante);
            pstmt.setString(2, materia);
            pstmt.setDouble(3, nota);
            pstmt.executeUpdate();
            System.out.println("Nota insertada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar la nota: " + e.getMessage());
        }
    }

    public void recuperarNotas(Connection conexion) {
        String sql = "SELECT * FROM notas";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String estudiante = rs.getString("estudiante");
                String materia = rs.getString("materia");
                double nota = rs.getDouble("nota");
                System.out.println("ID: " + id + ", Estudiante: " + estudiante + ", Materia: " + materia + ", Nota: " + nota);
            }
        } catch (SQLException e) {
            System.out.println("Error al recuperar notas: " + e.getMessage());
        }
    }

    public void actualizarNota(Connection conexion, int id, String estudiante, String materia, double nota) {
        String sql = "UPDATE notas SET estudiante = ?, materia = ?, nota = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, estudiante);
            pstmt.setString(2, materia);
            pstmt.setDouble(3, nota);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Nota actualizada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar la nota: " + e.getMessage());
        }
    }

    public void borrarNota(Connection conexion, int id) {
        String sql = "DELETE FROM notas WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Nota borrada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al borrar la nota: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ConexionSQLite conexionSQLite = new ConexionSQLite();
        Connection conn = conexionSQLite.conectar();

        if (conn != null) {
            try {
                conn.setAutoCommit(false);

                Scanner scanner = new Scanner(System.in);

                System.out.print("Ingrese el nombre del estudiante: ");
                String estudiante = scanner.nextLine();

                System.out.print("Ingrese la materia: ");
                String materia = scanner.nextLine();

                System.out.print("Ingrese la nota: ");
                double nota = scanner.nextDouble();

                conexionSQLite.insertarNota(conn, estudiante, materia, nota);

                conexionSQLite.recuperarNotas(conn);

                System.out.print("Ingrese el ID de la nota que desea actualizar: ");
                int idActualizar = scanner.nextInt();
                scanner.nextLine(); 

                System.out.print("Ingrese el nuevo nombre del estudiante: ");
                String nuevoEstudiante = scanner.nextLine();

                System.out.print("Ingrese la nueva materia: ");
                String nuevaMateria = scanner.nextLine();

                System.out.print("Ingrese la nueva nota: ");
                double nuevaNota = scanner.nextDouble();

                conexionSQLite.actualizarNota(conn, idActualizar, nuevoEstudiante, nuevaMateria, nuevaNota);

                System.out.println("Notas después de la actualización:");
                conexionSQLite.recuperarNotas(conn);

                System.out.print("Ingrese el ID de la nota que desea borrar: ");
                int idBorrar = scanner.nextInt();

                conexionSQLite.borrarNota(conn, idBorrar);

                conn.commit();

                System.out.println("Notas después de borrar:");
                conexionSQLite.recuperarNotas(conn);

            } catch (SQLException e) {
                System.out.println("Error durante la transacción: " + e.getMessage());
                try {
                    conn.rollback(); 
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            } finally {
                try {
                    conn.setAutoCommit(true); 
                    conn.close(); 
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        } else {
            System.out.println("La conexión falló.");
        }
    }
}
