//Esta clase almacena la información necesaria para el funcionamiento de la aplicación.
//Es la unión entre los datos y la presentación.

package controlador;

import modelo.Centro;
import modelo.CentrosLista;
import modelo.Departamento;
import modelo.DepartamentosLista;
import modelo.Elemento;
import modelo.Puesto;
import modelo.PuestosLista;
import modelo.Usuario;
import modelo.UsuariosLista;

public class Controller {

    public static CentrosLista Centros;
    public static DepartamentosLista Departamentos;
    public static PuestosLista Puestos;
    public static UsuariosLista Usuarios;
    
    public Controller() {
        Centros = Datos.GetCentros();
        Departamentos = Datos.GetDepartamentos();
        Puestos = Datos.GetPuestos();
        Usuarios = Datos.GetUsuarios(Centros, Departamentos, Puestos);
        
        for(Usuario u:Usuarios.getUsuarios())
            Datos.SetFichajes(u);

        Centros.addListDataListener(new ListaListener());
        Departamentos.addListDataListener(new ListaListener());
        Puestos.addListDataListener(new ListaListener());
        Usuarios.addListDataListener(new ListaListener());               
    }
   
    public static Usuario GetUsuario(String email, String clave) {
        return Usuarios.getUsuario(email, clave);
    }
    
    public static boolean EsPosibleEliminar(Elemento elemento) {
        var clase = elemento.getClass();

        var usuarios = Usuarios.getUsuarios();
        
        if (clase == Centro.class) {
            for(Usuario u:usuarios)
                if (u.Centro.equals(elemento))
                    return false;
        }

        if (clase == Departamento.class) {
            for(Usuario u:usuarios)
                if (u.Departamento.equals((elemento)))
                    return false;
        }

        if (clase == Puesto.class) {
            for(Usuario u:usuarios)
                if (u.Puesto.equals(elemento))
                    return false;
        }
        
        return true;
    }
    
    public static void RegistrarTiempo(Usuario usuario, boolean esEntrada) {
        Datos.RegistrarFichaje(usuario, esEntrada);
    }
}
