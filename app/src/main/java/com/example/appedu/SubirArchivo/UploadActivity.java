package com.example.appedu.SubirArchivo;

public class UploadActivity {
    private String titulo;
    private String descripcion;

    public UploadActivity(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
