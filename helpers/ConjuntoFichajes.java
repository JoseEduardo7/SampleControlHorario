//Esta clase organiza los fichajes de un determinado usuario por días para facilitar la visualización de los mismos.

package helpers;

import java.util.ArrayList;
import modelo.Fichaje;

public class ConjuntoFichajes {

    public final ArrayList<Dia> Dias; //Fichajes agrupados por día
    
    public ConjuntoFichajes(ArrayList<Fichaje> fichajes) {
        Dias = getDias(fichajes);
    }
    
    private ArrayList<Dia> getDias(ArrayList<Fichaje> fichajes) {
        ArrayList<Dia> dias = new ArrayList<>();
        
        for (Fichaje f : fichajes) {
            Dia ultimoDia = dias.isEmpty() ? null : dias.get(dias.size() - 1);
            
            if (ultimoDia == null || !ultimoDia.Fecha.equals(f.Tiempo.toLocalDate())) {
                Dia dia = new Dia(f.Tiempo.toLocalDate());
                dias.add(dia);
                ultimoDia = dia;
            }
            
            ultimoDia.Fichajes.add(f);
        }

        return dias;
    }
}