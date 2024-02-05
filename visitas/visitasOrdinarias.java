/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visitas;

import java.time.LocalDateTime;

/**
 *
 * @author Gerardo Sánchez Nilo
 */

/*Clase que sirve de modelo para almacenar los datos de las visitas rescatadas
* del archivo CSV
 */
public class visitasOrdinarias {

    public visitasOrdinarias(String Nombre_parroquia, String Descripcion, String Decanato) {
        this.Nombre_parroquia = Nombre_parroquia;
        this.Descripcion = Descripcion;
        this.Decanato = Decanato;
    }

    public visitasOrdinarias() {

    }

    private int id;
    private String Nombre_parroquia;
    private String Descripcion;
    private String DescO;
    private String Direccion;
    private String Decanato;
    private LocalDateTime Fecha;

    private String img;
    private boolean tieneImg;

    private static int num_parroquias;
    private static boolean tiempo_visitas;

    public String getDescO() {
        return DescO;
    }

    public void setDescO(String DescO) {
        this.DescO = DescO;
    }

    public static boolean isTiempo_visitas() {
        return tiempo_visitas;
    }

    public static void setTiempo_visitas(boolean tiempo_visitas) {
        visitasOrdinarias.tiempo_visitas = tiempo_visitas;
    }

    public static int getNum_parroquias() {
        return num_parroquias;
    }

    public static void setNum_parroquias(int num_parroquias) {
        visitasOrdinarias.num_parroquias = num_parroquias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre_parroquia(String Nombre_parroquia) {
        this.Nombre_parroquia = Nombre_parroquia;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public void setFecha(LocalDateTime Fecha) {
        this.Fecha = Fecha;
    }

    public void setDecanato(String Decanato) {
        this.Decanato = Decanato;
    }

    public String getNombre_parroquia() {
        return Nombre_parroquia;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public LocalDateTime getFecha() {
        return Fecha;
    }

    public String getDecanato() {
        return Decanato;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isTieneImg() {
        return tieneImg;
    }

    public void setTieneImg(boolean tieneImg) {
        this.tieneImg = tieneImg;
    }

}
