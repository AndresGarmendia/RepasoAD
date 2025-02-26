package CrearBaseDatos;

// Importa las funciones necesarias para manejar la base de datos
import static CrearBaseDatos.Funciones.crearBD;
import static CrearBaseDatos.Funciones.crearTabla;
import static CrearBaseDatos.Funciones.crearTipo;
import static CrearBaseDatos.Funciones.insertarRegistros;
import static CrearBaseDatos.Funciones.mostrarTabla;

public class Main {

    // Definición de las variables de conexión a la base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/"; // URL para la conexión a la base de datos PostgreSQL
    private static final String BD = "Deporte"; // Nombre de la base de datos
    private static final String URL_BD = URL + BD; // URL completa de la base de datos
    private static final String Usuario = "dam"; // Indica el usuario de gestor de base de datos.
    private static final String Contrasena = "dam"; // Contraseña correcta de tu usuario.

    public static void main(String[] args) {

        // Llamada a la función para crear la base de datos
        crearBD(URL, BD, Usuario, Contrasena);

        // Llamada a la función para crear un tipo de datos personalizado en la base de datos
        crearTipo(URL_BD, Usuario, Contrasena, "DeportePelotaTYPE");

        // Llamada a la función para crear una tabla que utilice el tipo de datos previamente definido
        crearTabla(URL_BD, Usuario, Contrasena, "DeportesDePelota", "DeportePelotaTYPE");

        // Llamada a la función para insertar registros en la tabla
        insertarRegistros(URL_BD, Usuario, Contrasena, "DeportesDePelota");

        // Llamada a la función para mostrar los registros de la tabla
        mostrarTabla(URL_BD, Usuario, Contrasena, "DeportesDePelota");
    }
}
