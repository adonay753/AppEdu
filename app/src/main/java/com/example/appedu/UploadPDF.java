package com.example.appedu;

class UploadPDF {
    String url;
    String nombre;
    public UploadPDF(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }
}
