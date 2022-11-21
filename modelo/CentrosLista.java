package modelo;

public class CentrosLista extends ElementosLista {

    public Centro getCentro(int id) {
        for (Object c:lista)
            if (((Centro)c).Id == id) return (Centro) c;
        return null;
    }
}
