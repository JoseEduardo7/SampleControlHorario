package modelo;

public class DepartamentosLista extends ElementosLista {
    
    public Departamento getDepartamento(int id) {
        for (Object d:lista)
            if (((Departamento)d).Id == id) return (Departamento) d;
        return null;
    }
}
