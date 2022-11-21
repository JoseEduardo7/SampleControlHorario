//Esta clase controla las inserciones, eliminaciones y actualizaciones de los distintos tipos de elementos.

package controlador;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import modelo.Centro;
import modelo.Departamento;
import modelo.Elemento;
import modelo.Puesto;
import modelo.Usuario;

public class ListaListener implements ListDataListener {

    @Override
    public void intervalAdded(ListDataEvent e) {
        Elemento x = (Elemento)e.getSource();

        if (tipoCentro(x))
            Datos.Insertar((Centro)x);

        if (tipoDepartamento(x))
            Datos.Insertar((Departamento)x);

        if (tipoPuesto(x))
            Datos.Insertar((Puesto)x);

        if (tipoUsuario(x)) 
            Datos.Insertar((Usuario)x);
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
        Elemento x = (Elemento)e.getSource();

        if (tipoCentro(x))
            Datos.Eliminar((Centro)x);

        if (tipoDepartamento(x))
            Datos.Eliminar((Departamento)x);

        if (tipoPuesto(x))
            Datos.Eliminar((Puesto)x);
        
        if (tipoUsuario(x))
            Datos.Eliminar((Usuario)x);
    }

    @Override
    public void contentsChanged(ListDataEvent e) {
        Elemento x = (Elemento)e.getSource();

        if (tipoCentro(x))
            Datos.Actualizar((Centro)x);

        if (tipoDepartamento(x))
            Datos.Actualizar((Departamento)x);

        if (tipoPuesto(x))
            Datos.Actualizar((Puesto)x);
        
        if (tipoUsuario(x)) 
            Datos.Actualizar((Usuario)x);
    }
    
    
    private boolean tipoUsuario(Elemento elemento) {
        return elemento.getClass() == Usuario.class;
    }
    private boolean tipoCentro(Elemento elemento) {
        return elemento.getClass() == Centro.class;
    }
    private boolean tipoDepartamento(Elemento elemento) {
        return elemento.getClass() == Departamento.class;
    }
    private boolean tipoPuesto(Elemento elemento) {
        return elemento.getClass() == Puesto.class;
    }
}