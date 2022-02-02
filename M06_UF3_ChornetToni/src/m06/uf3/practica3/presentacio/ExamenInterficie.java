package m06.uf3.practica3.presentacio;

import m06.uf3.practica3_2.negoci.Practica3;

import java.util.Scanner;

public class ExamenInterficie {

    static Scanner s = new Scanner(System.in);

    public void Header () {

        StringBuilder header = new StringBuilder();

        header.append("####################################################################\n");
        header.append("************PROGRAMA DE CONSULTA DEL FITXER factbook.xml************\n");
        header.append("***************Versio 1. BD incrustada en el programa***************\n");
        header.append("####################################################################\n");
        header.append("\n\n");
        header.append("## 1. Consulta a Base de dades incrustada.\n");
        header.append("## 2. Consulta a Base de dades al servidor.\n");
        header.append("## 3. Sortir.\n");
        System.out.print(header);


    }

    public void Llistat () {

        String llistat = " _-_-_-_-_-_-_-_-_ La llista actual de paisos es: _-_-_-_-_-_-_-_-_" + "\n";

        System.out.println(llistat);

    }

    public String demanaCarrera() {
        System.out.print("\n\nSiusplau introdueix una carrera: ");
        return s.nextLine();
    }

    public void Resultat () {
        StringBuilder resultat = new StringBuilder();
        resultat.append("\n\n____________________________________\n");
        resultat.append("| Les dades del pais escollit son: |\n");
        resultat.append("____________________________________\n\n");

        System.out.println(resultat);

    }


    public void mostrarMissatge(String missatge) {
        System.out.print(missatge);
    }


}
