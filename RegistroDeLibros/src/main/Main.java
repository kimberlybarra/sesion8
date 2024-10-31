package main;
import java.util.List;
import java.util.Scanner;
import database.AutorDAO;
import database.DatabaseConnection;
import database.LibroDAO;
import model.Autor;
import model.Libro;

public class Main {
    private static final String CLAVE_CORRECTA = "1234";

    public static void main(String[] args) {
        DatabaseConnection.createTables();

        AutorDAO autorDAO = new AutorDAO();
        LibroDAO libroDAO = new LibroDAO();
        Gestor gestor = new Gestor(); // Crear una instancia de Gestor

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Agregar Autor");
            System.out.println("2. Agregar Libro");
            System.out.println("3. Mostrar Autores");
            System.out.println("4. Mostrar Libros");
            System.out.println("5. Consultar Autores");
            System.out.println("6. Consultar Libros");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del Autor: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Ingrese la clave para confirmar: ");
                    String clave = scanner.nextLine();

                    if (CLAVE_CORRECTA.equals(clave)) {
                        autorDAO.agregarAutorConConfirmacion(new Autor(0, nombre));
                    } else {
                        System.out.println("Clave incorrecta. Operación cancelada.");
                    }
                    break;

                case 2:
                    System.out.print("Título del Libro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("ID del Autor: ");
                    int autorId = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.print("Ingrese la clave para confirmar: ");
                    clave = scanner.nextLine();

                    if (CLAVE_CORRECTA.equals(clave)) {
                        libroDAO.agregarLibroConConfirmacion(new Libro(0, titulo, autorId));
                    } else {
                        System.out.println("Clave incorrecta. Operación cancelada.");
                    }
                    break;

                case 3:
                    List<Autor> autores = autorDAO.obtenerAutores();
                    for (Autor a : autores) {
                        System.out.println("Autor: " + a.getNombre());
                    }
                    break;

                case 4:
                    List<Libro> libros = libroDAO.obtenerLibros();
                    for (Libro l : libros) {
                        System.out.println("Libro: " + l.getTitulo() + ", Autor ID: " + l.getAutorId());
                    }
                    break;
                    
                case 5:
                    System.out.print("Campo a mostrar (nombre): ");
                    String campoAutor = scanner.nextLine();
                    System.out.print("Condición (dejar vacío si no): ");
                    String condicionAutor = scanner.nextLine();
                    System.out.print("Orden (asc/desc): ");
                    String ordenAutor = scanner.nextLine();
                    System.out.print("Limitar cantidad de registros (0 para todos): ");
                    int limiteAutor = scanner.nextInt();

                    List<Autor> autoresResult = gestor.consultarAutores(campoAutor, condicionAutor, ordenAutor, limiteAutor);
                    for (Autor a : autoresResult) {
                        System.out.println("Autor: " + a.getNombre());
                    }
                    break;

                case 6:
                    System.out.print("Campo a mostrar (titulo): ");
                    String campoLibro = scanner.nextLine();
                    System.out.print("Condición (dejar vacío si no): ");
                    String condicionLibro = scanner.nextLine();
                    System.out.print("Orden (asc/desc): ");
                    String ordenLibro = scanner.nextLine();
                    System.out.print("Limitar cantidad de registros (0 para todos): ");
                    int limiteLibro = scanner.nextInt();

                    List<Libro> librosResult = gestor.consultarLibros(campoLibro, condicionLibro, ordenLibro, limiteLibro);
                    for (Libro l : librosResult) {
                        System.out.println("Libro: " + l.getTitulo() + ", Autor ID: " + l.getAutorId());
                    }
                    break;

                case 7:
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
