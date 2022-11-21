//Esta clase gestiona todas las comunicaciones con la base de datos y es la única que tiene acceso a ella.
//Recupera datos, inserta, actualiza y elimina.

package controlador;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import modelo.Centro;
import modelo.CentrosLista;
import modelo.Departamento;
import modelo.DepartamentosLista;
import modelo.Elemento;
import modelo.Fichaje;
import modelo.Puesto;
import modelo.PuestosLista;
import modelo.Usuario;
import modelo.UsuariosLista;

public class Datos {
    
    public static CentrosLista GetCentros() {
        CentrosLista centros = new CentrosLista();
        
        try {
            ResultSet r = getResultSet("SELECT id, nombre FROM centros;");

            while (r.next())
                centros.add(new Centro(r.getInt("id"), r.getString("nombre")));
            
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        
        return centros;
    }
 
    public static DepartamentosLista GetDepartamentos() {
        DepartamentosLista departamentos = new DepartamentosLista();
        
        try {
            ResultSet r = getResultSet("SELECT id, nombre FROM departamentos;");

            while (r.next())
                departamentos.add(new Departamento(r.getInt("id"), r.getString("nombre")));
            
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        
        return departamentos;
    }
    
    public static PuestosLista GetPuestos() {
        PuestosLista puestos = new PuestosLista();
        
        try {
            ResultSet r = getResultSet("SELECT id, nombre FROM puestos;");

            while (r.next())
                puestos.add(new Puesto(r.getInt("id"), r.getString("nombre")));
            
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        
        return puestos;
    }
    
    public static UsuariosLista GetUsuarios(CentrosLista centros, DepartamentosLista departamentos, PuestosLista puestos) {
        UsuariosLista usuarios = new UsuariosLista();

        String sql =    "SELECT t1.id, t1.nombre, t1.apellidos, t1.clave, " +
                                "t1.idDepartamento, t1.idPuesto, t1.idCentro, t1.administrador, " +
                                "t2.fechaInicio, t2.fechaFinal, " +
                                "t1.email " +
                            "FROM usuarios t1 INNER JOIN vacaciones t2 ON t1.id = t2.idUsuario;";
        try {
            ResultSet r = getResultSet(sql);
            
            while (r.next()) {
                Usuario u = new Usuario(r.getInt("id"));
                
                u.Nombre = r.getString("nombre");
                u.Apellidos = r.getString("apellidos");
                u.Clave = r.getString("clave");
                u.EsAdministrador = r.getBoolean("administrador");
                
                u.Centro = centros.getCentro(r.getInt("idCentro"));
                u.Puesto = puestos.getPuesto(r.getInt("idPuesto"));
                u.Departamento = departamentos.getDepartamento(r.getInt("idDepartamento"));
                u.Email = r.getString("email");
                
                String iniVacaciones = r.getString("fechaInicio");
                if (iniVacaciones != null) 
                    u.InicioVacaciones = LocalDate.parse(iniVacaciones, DateTimeFormatter.ofPattern(helpers.Const.FECHA));
                
                String finVacaciones = r.getString("fechaFinal");
                if (finVacaciones != null) 
                    u.FinalVacaciones = LocalDate.parse(finVacaciones, DateTimeFormatter.ofPattern(helpers.Const.FECHA));
                
                usuarios.add(u);
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        
        return usuarios;
    }
    
    public static void SetFichajes(Usuario usuario) {
        usuario.Fichajes = new ArrayList<>();
        
        try {
            ResultSet r = getResultSet("SELECT tiempo, tipo FROM fichajes WHERE idUsuario = " + usuario.Id + ";");
            
            while (r.next()) {
                LocalDateTime tiempo = LocalDateTime.parse(r.getString("tiempo"), DateTimeFormatter.ofPattern(helpers.Const.FECHAHORA));
                String tipo = r.getString("tipo");
                usuario.Fichajes.add(new Fichaje(tiempo, tipo));
            }
            r.close();
            
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
    
    
    public static void Insertar(Centro centro) {
        insertar(centro, "centros");
    }
    
    public static void Insertar(Departamento departamento) {
        insertar(departamento, "departamentos");
    }
    
    public static void Insertar(Puesto puesto) {
        insertar(puesto, "puestos");
    }
    
    public static void Insertar(Usuario usuario) {
        try {
            String sql = "INSERT INTO usuarios "
                            + "(nombre, apellidos, clave, idCentro, idDepartamento, idPuesto, email) VALUES (" 
                            + str(usuario.Nombre) + ", " + str(usuario.Apellidos) + ", "
                            + str(usuario.Clave) + ", "
                            + str(usuario.Centro.Id) + ", "
                            + str(usuario.Departamento.Id) + ", "
                            + str(usuario.Puesto.Id) + ", "
                            + str(usuario.Email)
                            + ");";
            conexion().createStatement().execute(sql);
            
            sql = "SELECT MAX(id) FROM usuarios;";
            ResultSet r = conexion().createStatement().executeQuery(sql);

            int id = r.getInt("MAX(id)");
            usuario.SetId(id);
            
            sql = "INSERT INTO vacaciones (idUsuario) VALUES (" + str(usuario.Id) + ");";
            conexion().createStatement().execute(sql);
            
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
  
    private static void insertar(Elemento elemento, String tabla) {
        
        try {
            String sql = "INSERT INTO " + tabla + " (nombre) VALUES (" + str(elemento) + ")";
            conexion().createStatement().execute(sql);
            
            sql = "SELECT MAX(id) FROM " + tabla + ";";
            ResultSet r = conexion().createStatement().executeQuery(sql);
            
            int id = r.getInt("MAX(id)");
            elemento.SetId(id);
            
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
    
    
    public static void Actualizar(Centro centro) {
        actualizar(centro, "centros");
    }
    
    public static void Actualizar(Departamento departamento) {
        actualizar(departamento, "departamentos");
    }
    
    public static void Actualizar(Puesto puesto) {
        actualizar(puesto, "puestos");
    }
    
    public static void Actualizar(Usuario usuario) {
        try {
            String sql = "UPDATE usuarios SET " +
                                "nombre = " + str(usuario.Nombre) + ", " +
                                "apellidos = " + str(usuario.Apellidos) + ", " +
                                "clave = " + str(usuario.Clave) + ", " +
                                "idCentro = " + str(usuario.Centro.Id) + ", " +
                                "idDepartamento = " + str(usuario.Departamento.Id) + ", " +
                                "idPuesto = " + str(usuario.Puesto.Id) + ", " +
                                "email = " + str(usuario.Email) + " " +
                            "WHERE id = " + str(usuario.Id) + ";";
            conexion().createStatement().execute(sql);
            
            sql = "UPDATE vacaciones SET "
                    + "fechaInicio = " + str(usuario.InicioVacaciones) + ", "
                    + "fechaFinal = " + str(usuario.FinalVacaciones) + " "
                    + "WHERE idUsuario = " + str(usuario.Id) + ";";
            conexion().createStatement().execute(sql);
            
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
    
    private static void actualizar(Elemento elemento, String tabla) {
        try {
            String sql = "UPDATE " + tabla +
                            " SET nombre = " + str(elemento) +
                            " WHERE id = " + str(elemento.Id) + ";";
            conexion().createStatement().execute(sql);
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
        
    
    public static void Eliminar(Centro centro) {
        eliminar("centros", centro.Id);
    }
    
    public static void Eliminar(Departamento departamento) {
        eliminar("departamentos", departamento.Id);
    }
    
    public static void Eliminar(Puesto puesto) {
        eliminar("puestos", puesto.Id);
    }
    
    public static void Eliminar(Usuario usuario) {
        eliminar("usuarios", usuario.Id);
        eliminarRegistrosUsuario(usuario);
    }
    
    private static void eliminar(String tabla, int id) {
        try {
            String sql = "DELETE FROM " + tabla + " WHERE id = " + str(id) + ";";
            conexion().createStatement().execute(sql);
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
    
    private static void eliminarRegistrosUsuario(Usuario usuario) {
        try {
            String sql = "DELETE FROM vacaciones WHERE idUsuario = " + str(usuario.Id) + ";";
            conexion().createStatement().execute(sql);
            
            sql = "DELETE FROM fichajes WHERE idUsuario = " + str(usuario.Id) + ";";
            conexion().createStatement().execute(sql);
            
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
    
    
    public static void RegistrarFichaje(Usuario usuario, boolean esEntrada) {
        String tipo = helpers.Const.SALIDA;
        if (esEntrada) tipo = helpers.Const.ENTRADA;
        
        LocalDateTime tiempo = LocalDateTime.now();
        
        String sql = "INSERT INTO fichajes (idUsuario, tiempo, tipo) "
                        + "VALUES ("
                        + str(usuario.Id) + ", "
                        + str(tiempo) + ", "
                        + str(tipo) + ");";
        try {
            conexion().createStatement().execute(sql);
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        
        
        usuario.Fichajes.add(new Fichaje(tiempo, tipo));
    }
    
    
    private static String str(Elemento e) {
        if (e == null)
            return "NULL";
        else
            return "'" + e.Nombre + "'";
    }
    
    private static String str(int i) {
        return "'" + Integer.toString(i) + "'";
    }
    
    private static String str(String s) {
        if (s == null)
            return "NULL";
        else
           return "'" + s + "'"; 
    }

    private static String str(LocalDate d) {
        if (d == null)
            return "NULL";
        else
            return "'" + d.format(DateTimeFormatter.ofPattern(helpers.Const.FECHA)) + "'";
    }
    
    private static String str(LocalDateTime l) {
        if (l == null)
            return "NULL";
        else
            return "'" + l.format(DateTimeFormatter.ofPattern(helpers.Const.FECHAHORA)) + "'";
    }
    
    private static Connection conexion() {
        if (_cx == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                _cx = DriverManager.getConnection("jdbc:sqlite:bd.db");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return _cx;
    }
    private static Connection _cx;
    
    private static ResultSet getResultSet(String sql) {
        ResultSet r = null;
        try {
            r = conexion().createStatement().executeQuery(sql);
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return r;
    }

}
