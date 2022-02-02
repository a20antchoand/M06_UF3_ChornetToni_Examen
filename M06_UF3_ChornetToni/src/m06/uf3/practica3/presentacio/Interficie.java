package m06.uf3.practica3.presentacio;

import m06.uf3.practica3.negoci.Practica3;

import java.util.Scanner;

public class Interficie {

    static Scanner s = new Scanner(System.in);

    public void Header () {

        StringBuilder header = new StringBuilder();

        header.append("####################################################################\n");
        header.append("************PROGRAMA DE CONSULTA DEL FITXER factbook.xml************\n");
        header.append("***************Versio 1. BD incrustada en el programa***************\n");
        header.append("####################################################################\n");

        System.out.println(header);

    }

    public void Llistat () {

        String llistat = "/\\/\\/\\/\\/\\/\\/\\La llista actual de paisos es: /\\/\\/\\/\\/\\/\\/\\" + "\n";

        System.out.println(llistat);

    }

    public void DemanaPais() {
        System.out.print("\n\nSiusplau introdueix un pais: ");
        Practica3.pais = s.nextLine();
    }

    public void Resultat () {
        StringBuilder resultat = new StringBuilder();
        resultat.append("\n\n____________________________________\n");
        resultat.append("| Les dades del pais escollit son: |\n");
        resultat.append("____________________________________\n\n");

        System.out.println(resultat);

    }


}
