package m06.uf3.practica3.negoci;

import m06.uf3.practica3.presentacio.Interficie;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;

import java.io.File;

public class Practica3 {

    private static Context context = new Context();
    private static Interficie interficie = new Interficie();
    public static String pais;

    public static void main(String[] args) throws BaseXException {

        //VARIABLES
        String nom = "";
        String inflacio = "";
        String religio = "";

        try {

            //Obrim o creem la BBDD
            comprovarBBDD();

            //Mostrem part de la interficie
            interficie.Header();
            interficie.Llistat();

            //Mostrem els paisos actuals, hi han alguns que tenen mes d'un nom per tant
            //o limitem amb name[1] per agafar tant sols el primer.
            System.out.println(query("for $p in mondial return $p/country/name[1]/text()"));

            //Seguim mostrant la interficie
            interficie.DemanaPais();

            //Agafem el nom del pais i el comprovem
            nom = query("data(//country[@name='" + pais + "']/name)");

            //Si el pais existeix comprovem inflacio i religio, si no existeix ho avisem.
            if (!nom.equals("")) {

                interficie.Resultat();

                //Mostrem el nom del pais
                System.out.println("Nom: " + nom);

                //Emmagatzemem inflacio i religio
                inflacio = query("data(//country[@name='" + pais + "']/@inflation)");
                religio = query("data(//country[@name='" + pais + "']/religions)");

                //Comprovem inflacio i religio
                if (!inflacio.equals("")) {
                    System.out.println("La inflacio es de " + inflacio + "%.");
                }

                if (!religio.equals("")) {
                    System.out.println("Les religions del pais son:");
                    System.out.println(religio);
                }
            // Avisem de que no es un pais valid
            } else {

                System.out.println(pais + " no es un pais.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                //Tanquem la BBDD
                context.close();

            } catch (Exception e) {

                //Avisem de que hi ha un error al tancar la BBDD
                System.out.println("No se ha podido cerrar la DB");

            }

        }

    }

    private static void comprovarBBDD() {

        try {

            //Intentem obrir la BBDD
            new Open("factbook").execute(context);

        } catch (Exception e) {

            try {

                //Creem la BBDD
                System.out.println("Create a new databae.");
                new CreateDB("factbook", "src" + File.separator + "M06_UF3_ChornetToni/factbook.xml").execute(context);
                System.out.println("S'ha creat la BD");

            } catch (Exception ex) {

                //Avisem de que no s'ha creat la BBDD
                System.out.println("No se la creado la DB");
            }

        }

    }

    //Executem la query i tornem el resultat
    static public String query(final String query) throws BaseXException {
        return new XQuery(query).execute(context);
    }

    //GETTERS I SETTERS
    public static Context getContext() {
        return context;
    }

    public static String getPais() {
        return pais;
    }

}
