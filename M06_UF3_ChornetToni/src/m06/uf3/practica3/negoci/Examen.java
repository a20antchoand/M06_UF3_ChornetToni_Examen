package m06.uf3.practica3.negoci;

import m06.uf3.practica3.presentacio.ExamenInterficie;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;

import java.io.File;
import java.util.Scanner;

public class Examen {

    private static Scanner s = new Scanner(System.in);
    private static ExamenInterficie interficie = new ExamenInterficie();
    private static Context context;
    
    public static void main(String[] args) {

        int opcio = -1;
        String carrera;
        //Es mostra el Header
        interficie.Header();

        do {
            
            try {


                
                //Demanem la opcio fins que sigui valida
                do {
                    interficie.mostrarMissatge("\nIndica l'opcio a executar: ");
                    opcio = s.nextInt();
                } while (opcio < 1 || opcio > 3);

                
                //Mirem quina funcionalitta hem d'executar
                if (opcio == 1) {

                    //Es crea la BBDD
                    crearBBDD();

                    //Mostrem carreres universitaries
                    mostrarCarreras();

                    //Demanar Carrera al usuari
                    carrera = interficie.demanaCarrera();

                    //Buscar centre estudiantil
                    mostrarCentre(carrera);
                    
                } else if (opcio == 2) {

                    
                    
                }
                
            } catch (Exception e) {
                
                //Errors
                e.printStackTrace();

            } finally {
                
                //Tancar BBDD
                try {

                    //Tanquem la BBDD
                    context.close();

                } catch (Exception e) {

                    //Avisem de que hi ha un error al tancar la BBDD
                    System.out.println("No se ha podido cerrar la DB");

                }
            }
            
        } while (opcio != 3);
        
    }

    private static void mostrarCarreras() throws BaseXException {

        interficie.mostrarMissatge(query("data(for $carrera in universidad//carreras return $carrera//carrera//nombre)\n\n"));

    }

    private static void mostrarCentre(String carrera) throws BaseXException {

        String aux = "";
        aux = query("data(//carrera[nombre='" + carrera + "']/centro)\n\n");

        if (!aux.equals("")) interficie.mostrarMissatge("Facultat: " + aux);
        else interficie.mostrarMissatge("La carrera no existeix.");

    }

    private static void crearBBDD() {

        context = new Context();

        try {

            //Intentem obrir la BBDD
            new Open("universitats").execute(context);
            interficie.mostrarMissatge("S'ha oberta la BBDD\n\n");

        } catch (Exception e) {

            try {

                //Creem la BBDD
                interficie.mostrarMissatge("Create a new databae.\n\n");
                new CreateDB("universitats", "universitats-v2.xml").execute(context);
                interficie.mostrarMissatge("S'ha creat la BD\n\n");

            } catch (Exception ex) {

                //Avisem de que no s'ha creat la BBDD
                interficie.mostrarMissatge("No s'ha creat la DB\n\n");
            }

        }

    }

    //Executem la query i tornem el resultat
    static public String query(final String query) throws BaseXException {
        return new XQuery(query).execute(context);
    }

}
