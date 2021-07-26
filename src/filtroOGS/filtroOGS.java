package filtroOGS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Esta clase define toda la funcionalidad para el filtrado de archivos por su
 * nombre, e igual por su contenido(de acuerdo a unos parametrizables)
 *
 * @author andgarces
 * @version 18/06/2021/A
 */
public class filtroOGS {

    /**
     * Método que ejecuta el filtro de los archivos por nombre respecto a las
     * nomenclaturas parametrizados
     *
     * @throws java.io.FileNotFoundException
     */
    public static void ejecutarPrimeraEtapa() throws FileNotFoundException, IOException {

        File carpeta = new File(traerRutasParametrizadas()[0]);
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;
        } else {
            for (int i = 0; i < listado.length; i++) {
                boolean paso = false;
                String cadena;
                FileReader f = new FileReader(traerRutasParametrizadas()[4]);
                BufferedReader b = new BufferedReader(f);

                while ((cadena = b.readLine()) != null) {
                    if (listado[i].substring(4, 7).equals(cadena)) {
                        paso = true;
                        //moverArchivos(traerRutasParametrizadas()[0] + listado[i], traerRutasParametrizadas()[1] + listado[i]);
                         //moverunArchivoFinal(traerRutasParametrizadas()[0] + listado[i], traerRutasParametrizadas()[1]);
                         moverunArchivoOtro(traerRutasParametrizadas()[0] + listado[i], traerRutasParametrizadas()[1]);
                        break;
                    } else {
                        paso = false;
                    }
                }
                b.close();

                if (paso == true) {
                    System.out.println("Es decir q paso");
                } else {
                    //moverArchivos(traerRutasParametrizadas()[0] + listado[i], traerRutasParametrizadas()[2] + listado[i]);
                    //moverunArchivoFinal(traerRutasParametrizadas()[0] + listado[i], traerRutasParametrizadas()[2]);
                    moverunArchivoOtro(traerRutasParametrizadas()[0] + listado[i], traerRutasParametrizadas()[2]);
                }
            }
        }
    }

    /**
     * Método que se encarga de mover los archivos entre el origen y el destino
     * especificado
     *
     * @param origen El parámetro origen define la carperta DESDE donde se va a
     * mover el archivo
     * @param destino El parámetro destino define la carperta donde se va a
     * mover el archivo
     */
    public static void moverArchivos(String origen, String destino) throws FileNotFoundException, IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File archivoOriginal = new File(origen);
            File archivoCopia = new File(destino);
            inputStream = new FileInputStream(archivoOriginal);
            outputStream = new FileOutputStream(archivoCopia);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
            System.out.println("Archivo copiado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public static void moverunArchivoFinal(String desde, String hasta) {

        /**Path origen = Path.of("C:/Users/57314/Documents/proyectoHeinsohn/CarpetaOrigen/1.txt");
        Path destino = Path.of("C:/Users/57314/Documents/proyectoHeinsohn/mover/");*/
        Path origen = Path.of(desde);
        Path destino = Path.of(hasta);

        try {
            Path mover = Files.move(origen, destino.resolve(origen.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Se movió el archivo: " + mover);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public static void moverunArchivoOtro(String origen, String destino){
    Path origenPath = FileSystems.getDefault().getPath(origen);
    Path destinoPath = FileSystems.getDefault().getPath(destino);

    try {
        Files.move(origenPath, destinoPath.resolve(origenPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
        System.err.println(e);
    }
}
    

    /**
     * Método que ejecuta el filtro de los archivos por su contenido respecto a
     * los codigos parametrizados
     *
     * @throws java.io.FileNotFoundException
     */
    public static void ejecutarSegundaEtapa() throws FileNotFoundException, IOException {

        File carpeta = new File(traerRutasParametrizadas()[1]);
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;
        } else {
            boolean paso = true;
            for (int i = 0; i < listado.length; i++) {
                String linea;
                FileReader f = new FileReader(traerRutasParametrizadas()[1] + listado[i]);
                BufferedReader b = new BufferedReader(f);

                while ((linea = b.readLine()) != null) {
                    String codigo;
                    FileReader f2 = new FileReader(traerRutasParametrizadas()[5]);
                    BufferedReader b2 = new BufferedReader(f2);

                    while ((codigo = b2.readLine()) != null) {
                        if (linea.length() > 400) {
                            System.out.println("LONGITUD CORRECTA");
                        } else {
                            paso = true;
                            System.out.println("LONGITUD INCORRECTA");
                            break;
                        }

                        if (linea.substring(23, 26).equals(codigo)) {
                            paso = true;
                            break;
                        } else {
                            paso = false;
                        }
                    }

                    if (paso == true) {
                        System.out.println("Bueno, paso, siguee recorriendo");
                    } else {
                        break;
                    }
                }

                if (paso == true) {
                    moverArchivos(traerRutasParametrizadas()[1] + listado[i], traerRutasParametrizadas()[3] + listado[i]);
                    continue;
                } else {
                    System.out.println("NO PASOOOO");
                }

            }
        }
    }

    /**
     * Método que devuelve un array con todas las rutas parametrizadas en el
     * archivo indicado
     *
     * @return Arreglo de rutas que estan incluidas en el archivo
     */
    public static String[] traerRutasParametrizadas() throws FileNotFoundException, IOException {

        String ruta;
        String rutas[] = new String[6];
        int a = 0;
        FileReader f = new FileReader("C:/Users/57314/Documents/proyectoHeinsohn/rutasJava.txt");
        BufferedReader b = new BufferedReader(f);
        while ((ruta = b.readLine()) != null) {
            rutas[a] = ruta;
            a++;
        }
        b.close();
        return rutas;
    }

    /**
     * Método de ejecución
     */
    public static void main(String[] args) throws IOException {
        
       // moverunArchivoFinal("C:/Users/57314/Documents/proyectoHeinsohn/CarpetaOrigen/1.txt", "C:/Users/57314/Documents/proyectoHeinsohn/mover/");

        //ejecutarPrimeraEtapa();
        ejecutarSegundaEtapa();

        //moverunArchivoFinal();
    }
}
