package modelo;

import java.time.LocalDateTime;

public class Fichaje {
    public final LocalDateTime Tiempo;
    public final String Tipo;
    
    public Fichaje(LocalDateTime tiempo, String tipo) {
        this.Tiempo = tiempo;
        this.Tipo = tipo;
    }
}
