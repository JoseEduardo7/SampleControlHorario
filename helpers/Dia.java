//Esta clase almacena los fichajes de un día de un usuario, determina si son correctos y, si es así, calcula el total de horas.

package helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import modelo.Fichaje;

public class Dia {

    public final LocalDate Fecha;
    public final ArrayList<Fichaje> Fichajes;

    public Dia(LocalDate fecha) {
        this.Fecha = fecha;
        Fichajes = new ArrayList<>();
    }

    public boolean FichajesCorrectos() {

        if (Fichajes.isEmpty()) return false;
        if (Fichajes.size() % 2 != 0) return false;
        if (Fichajes.get(0).Tipo.equals(helpers.Const.SALIDA)) return false;
        if (Fichajes.get(Fichajes.size() - 1).Tipo.equals(helpers.Const.ENTRADA)) return false;

        String anterior = null;

        for (Fichaje f : Fichajes) {
            if (anterior != null && anterior.equals(f.Tipo)) return false;
            anterior = f.Tipo;
        }
        
        return true;
    }
    
    public Double Horas() {
        
        if (!FichajesCorrectos())
            return null;
        
        double cont = 0;
        LocalDateTime tiempoAnterior = null;
        
        for (Fichaje f: Fichajes) {
         
            if (tiempoAnterior != null && f.Tipo.equals(helpers.Const.SALIDA))
                cont += ChronoUnit.SECONDS.between(tiempoAnterior, f.Tiempo) / 3600d;
                
            tiempoAnterior = f.Tiempo;
        }
       
        return cont;
    }
    
}