package com.example.appedu;

public class CardTask {
    private String nombre;
    private String tarea;

    public CardTask() {
    }

    public CardTask(String nombre, String tarea) {
        this.nombre = nombre;
        this.tarea = tarea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }
}
