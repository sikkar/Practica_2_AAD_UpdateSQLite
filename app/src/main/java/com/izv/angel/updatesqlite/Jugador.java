package com.izv.angel.updatesqlite;



import java.io.Serializable;

//java beans,pojo
public class Jugador implements Serializable , Comparable <Jugador> {

    private long id;
    private String nombre;
    private String telefono;
    private String fnac;

    //1º Constructor predeterminado (constructor vacio)
    //2º Constructor completo
    //3º todos los getter y setter
    //4º equals, hashCode -> usando la clave principal de la tabla
    //5º compareTo
    //6º toString

    public Jugador() {
        this(0,"","","");
    }

    public Jugador(long id, String nombre, String telefono, String fnac) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fnac = fnac;
    }

    public Jugador(String nombre, String telefono, String fnac){
        this.id=0;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fnac = fnac;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFnac() {
        return fnac;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }

    @Override
    public int compareTo(Jugador jugador) {
        if (this.nombre.compareTo(jugador.nombre)!=0){
            return this.nombre.compareTo(jugador.nombre);
        }else{
            return this.fnac.compareTo(jugador.fnac);
        }
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fnac='" + fnac + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Jugador.class != o.getClass()) return false;

        Jugador jugador = (Jugador) o;

        if (id != jugador.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
