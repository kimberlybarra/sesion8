package main;

import model.Autor;
import model.Libro;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Gestor {
    private List<Autor> autores;
    private List<Libro> libros;

    public Gestor() {
        this.autores = new ArrayList<>();
        this.libros = new ArrayList<>();
    }

    public void agregarAutor(Autor autor) {
        autores.add(autor);
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public List<Autor> consultarAutores(String campo, String condicion, String orden, int limite) {
        List<Autor> resultado = new ArrayList<>(autores);

        if (condicion != null && !condicion.isEmpty()) {
            resultado.removeIf(autor -> !cumpleCondicion(autor, campo, condicion));
        }

        if ("asc".equalsIgnoreCase(orden)) {
            resultado.sort(Comparator.comparing(Autor::getNombre));
        } else if ("desc".equalsIgnoreCase(orden)) {
            resultado.sort(Comparator.comparing(Autor::getNombre).reversed());
        }

        if (limite > 0 && limite < resultado.size()) {
            return resultado.subList(0, limite);
        }

        return resultado;
    }

    public List<Libro> consultarLibros(String campo, String condicion, String orden, int limite) {
        List<Libro> resultado = new ArrayList<>(libros);

        if (condicion != null && !condicion.isEmpty()) {
            resultado.removeIf(libro -> !cumpleCondicion(libro, campo, condicion));
        }

        if ("asc".equalsIgnoreCase(orden)) {
            resultado.sort(Comparator.comparing(Libro::getTitulo));
        } else if ("desc".equalsIgnoreCase(orden)) {
            resultado.sort(Comparator.comparing(Libro::getTitulo).reversed());
        }

        if (limite > 0 && limite < resultado.size()) {
            return resultado.subList(0, limite);
        }

        return resultado;
    }

    private boolean cumpleCondicion(Autor autor, String campo, String condicion) {
        switch (campo.toLowerCase()) {
            case "nombre":
                return autor.getNombre().contains(condicion);
            default:
                return true; 
        }
    }

    private boolean cumpleCondicion(Libro libro, String campo, String condicion) {
        switch (campo.toLowerCase()) {
            case "titulo":
                return libro.getTitulo().contains(condicion);
            case "autor_id":
                return String.valueOf(libro.getAutorId()).equals(condicion);
            default:
                return true; 
        }
    }
}
