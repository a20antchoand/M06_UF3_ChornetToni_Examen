package m06.uf3.practica3.negoci;

import m06.uf3.practica3.presentacio.ExamenInterficie;
import org.basex.BaseXServer;
import org.basex.api.client.ClientSession;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Scanner;

public class Examen {

    private static Scanner s = new Scanner(System.in);
    private static ExamenInterficie interficie = new ExamenInterficie();
    private static Context context;
    public static ClientSession session;
    public static BaseXServer server;
    
    public static void main(String[] args) throws IOException {

        int opcio = -1;
        String carrera = "";

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

                } else if (opcio == 2) {

                    //Es crea la BBDD
                    session = crearBBDDServidor();
                    
                }

                if (opcio != 3) {
                    //Mostrem carreras
                    mostrarCarreras(opcio);

                    //Demanar Carrera al usuari
                    carrera = interficie.demanaCarrera();

                    //Buscar centre estudiantil
                    mostrarCentre(carrera, opcio);
                }
            } catch (Exception e) {
                
                //Errors
                e.printStackTrace();

            } finally {

                if (context != null) {
                    //Tancar BBDD
                    try {

                        //Tanquem la BBDD
                        context.close();

                    } catch (Exception e) {

                        //Avisem de que hi ha un error al tancar la BBDD
                        interficie.mostrarMissatge("No s'ha pogut tancar el context");

                    }
                }

                if (session != null) {
                    try {
                        session.close();
                    } catch (Exception e) {
                        interficie.mostrarMissatge("No s'ha pogut tancar la sessio");
                    }
                }

            }
            
        } while (opcio != 3);
        
    }

    private static void mostrarCarreras(int opcio) throws IOException {

        if (opcio == 1) interficie.mostrarMissatge(query("data(for $carrera in universidad//carreras return $carrera//carrera//nombre)\n\n"));
        else interficie.mostrarMissatge(session.query("data(for $carrera in universidad//carreras return $carrera//carrera//nombre)\n\n").execute());
    }

    private static void mostrarCentre(String carrera, int opcio) throws IOException {

        String aux = "";
        if (opcio == 1) aux = query("data(//carrera[nombre='" + carrera + "']/centro)\n\n");
        else  aux = session.query("data(//carrera[nombre='" + carrera + "']/centro)\n\n").execute();

        if (!aux.equals("")) interficie.mostrarMissatge("Facultat: " + aux);
        else interficie.mostrarMissatge("La carrera no existeix.");

    }

    private static void crearBBDD() {

        context = new Context();

        try {

            //Intentem obrir la BBDD
            new Open("universitat").execute(context);
            interficie.mostrarMissatge("S'ha oberta la BBDD\n\n");

        } catch (Exception e) {

            try {

                //Creem la BBDD
                interficie.mostrarMissatge("Create a new databae.\n\n");
                new CreateDB("universitat", "universitats-v2.xml").execute(context);
                interficie.mostrarMissatge("S'ha creat la BD\n\n");

            } catch (Exception ex) {

                //Avisem de que no s'ha creat la BBDD
                interficie.mostrarMissatge("No s'ha creat la DB\n\n");
            }

        }

    }


    private static ClientSession crearBBDDServidor() throws IOException {
        try {
            ClientSession session = new ClientSession("localhost", 1984, "admin", "admin");

            //Intentem obrir la BBDD
            session.execute(new Open("universitat"));
            interficie.mostrarMissatge("S'ha conectat al servidor\n\n");
            return session;
        } catch (ConnectException exception) {
            interficie.mostrarMissatge("No s'ha pogut conectar al servidor\n\n");
        }

        return null;
    }


    //Executem la query i tornem el resultat
    static public String query(final String query) throws BaseXException {
        return new XQuery(query).execute(context);
    }

}
