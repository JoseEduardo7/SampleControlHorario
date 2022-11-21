//Esta clase permite enviar emails a los usuarios.

package helpers;

import controlador.Controller;
import java.awt.Desktop;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelo.Usuario;

public class Email {
    
    public static void EnviarEmailFichajesIncorrectos(Usuario usuario, LocalDate fecha) {
        String subject = "Fichajes%20incorrectos";
        
        try {
            Desktop desktop;
            if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
              URI mailto = new URI("mailto:" + usuario.Email + "?subject=" + subject + "&body=" + Cuerpo(usuario.Nombre, fecha));
              desktop.mail(mailto);
            } else {
              throw new RuntimeException("Envío de email no soportado.)");
            }
        } catch (Exception x) {
            System.out.println(x);
        }
    }
    
    public static String Cuerpo(String nombre, LocalDate fecha) {
        String _fecha = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String x = "Hola " + nombre + ",_"
                    + "tus fichajes del día " + _fecha + " parecen incorrectos._"
                    + "Por favor, contesta a este correo para gestionar la incidencia.__"
                    + "Saludos.__";
        
        x = x.replace(" ", "%20").replace("_", "%0A");
        return x;
    }
    
    public static boolean EmailValido(String email) {
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patron.matcher(email);
        return matcher.find();
    }
    
    public static boolean EmailUtilizado(int UsuarioId, String email) {
        for (Usuario u : Controller.Usuarios.getUsuarios()) {
            if (u.Id != UsuarioId) {
                if (u.Email.equals(email)) return true;
            }
        }
        return false;
    }
}
