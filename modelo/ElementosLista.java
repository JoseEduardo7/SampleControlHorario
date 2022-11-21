//Clase abstracta para gestionar las listas de elementos.
//De ella derivan CentrosLista, DepartamentosLista, PuestosLista y UsuariosLista.

package modelo;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

public abstract class ElementosLista extends AbstractListModel {

    protected ArrayList lista = new ArrayList<>();
    
    @Override
    public int getSize() {
        return lista.size();
    }

    @Override
    public Object getElementAt(int index) {
        return (Elemento)lista.get(index);
    }
    
    public void add(Elemento elemento) {
        lista.add(elemento);
        this.fireIntervalAdded(elemento, getSize(), getSize()+1);
    }
    
    public void remove(Elemento elemento) {
        lista.remove(elemento);        
        this.fireIntervalRemoved(elemento, getSize(), getSize()+1);
    }
        
    public void update(Elemento elemento, String nuevoNombre) {
        elemento.Nombre = nuevoNombre;
        this.fireContentsChanged(elemento, getSize(), getSize()+1);
    }
    
    public void update(Usuario usuario) {
        this.fireContentsChanged(usuario, getSize(), getSize()+1);
    }
    
    public ArrayList<Elemento> getElementos() {
        ArrayList<Elemento> elementos = new ArrayList<>();
        
        for(Object e:lista)
            elementos.add((Elemento)e);

        return elementos;
    }
}