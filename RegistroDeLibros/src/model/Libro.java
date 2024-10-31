package model;

public class Libro {
    private int id;
    private String titulo;
    private int autorId;

    public Libro(int id, String titulo, int autorId) {
        this.id = id;
        this.titulo = titulo;
        this.autorId = autorId;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAutorId() {
        return autorId;
    }
}
