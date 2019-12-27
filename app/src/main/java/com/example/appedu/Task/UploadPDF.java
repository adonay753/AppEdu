package com.example.appedu.Task;

public class UploadPDF {
    private String url;
    private String nombre;
    private String tarea;

    public UploadPDF(String nombre, String tarea, String url) {
        this.nombre = nombre;
        this.tarea=tarea;

        this.url = url;
    }

    public String getTarea() {
        return tarea;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }
}
