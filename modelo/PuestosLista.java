package modelo;

public class PuestosLista extends ElementosLista {
    
    public Puesto getPuesto(int id) {
        for (Object p:lista)
            if (((Puesto)p).Id == id) return (Puesto) p;
        return null;
    }
}

