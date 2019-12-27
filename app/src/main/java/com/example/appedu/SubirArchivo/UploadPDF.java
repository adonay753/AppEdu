package com.example.appedu.SubirArchivo;

class UploadPDF {
    String url;
    String nombre;
    String tarea;

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
