package modelo;

import java.util.ArrayList;

public class UsuariosLista extends ElementosLista {
    
    public Usuario getUsuario(int id) {
        for(Object u:lista)
            if (((Usuario)u).Id == id) return (Usuario) u;
        return null;
    }
    
    public Usuario getUsuario(String email, String clave) {
        for (Object u:lista) {
            Usuario usuario = (Usuario)u;
            if (usuario.Email.equals(email) && usuario.Clave.equals(clave))
                return usuario;
        }
        return null;
    }
    
    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        for(Object u:lista)
            usuarios.add((Usuario)u);

        return usuarios;
    }
}
