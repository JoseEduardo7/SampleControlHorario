package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario extends Elemento {

    public String Apellidos;
    public String Clave;
    
    public Centro Centro;
    public Departamento Departamento;
    public Puesto Puesto;
    
    public boolean EsAdministrador;
    
    public ArrayList<Fichaje> Fichajes;
    
    public LocalDate InicioVacaciones;
    public LocalDate FinalVacaciones;
    
    public String Email;
        
    public Usuario(int id) {
        super(id);
        
        if (id == -1)
            Clave = "0000";
    }
    
    public boolean DeVacaciones() {
        if (InicioVacaciones == null || FinalVacaciones == null)
            return false;
        
        LocalDate hoy = LocalDate.now();
        
        if (hoy.equals(InicioVacaciones) || hoy.equals(InicioVacaciones))
            return true;
        else
            return (hoy.isAfter(InicioVacaciones) && hoy.isBefore(FinalVacaciones));
    }
    
    @Override
    public String toString() {
        return Nombre + " " + Apellidos;
    }
}
