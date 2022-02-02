package BaseX;

import org.basex.core.BaseXException;
import org.basex.core.Context;
//import org.basex.core.cmd.Close;
import org.basex.core.cmd.CreateDB;
//import org.basex.core.cmd.DropDB;
//import org.basex.core.cmd.InfoDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;
import org.basex.query.QueryException;
import org.basex.query.QueryProcessor;
import org.basex.query.iter.Iter;
import org.basex.query.value.item.Item;

public class exercici2 {

	static Context context = new Context();

	public static void main(String[] args) {

		String query = 
				"for $al in //alumno return $al/nombre/data()";

		try {
			
			
			new Open("DBExemple").execute(context);
			System.out.println("BD Oberta");

			try {

				System.out.println("Primera consulta amb un objecte XQUERY.");
				System.out.println("\n");
				query(query);
				System.out.println("\n");
				System.out.println("segona consulta amb un objecte QueryProcessor");
				System.out.println("\n");
				iterate(query);

			} catch (BaseXException e) {
				System.out.println("Error en la XQUERY");
			} catch (QueryException q) {
				System.out.println("Error en la query ITERADA");
			}
		} catch (Exception e) {
			try {
				System.out.println("Create a new databae.");
				new CreateDB("DBExemple", "universitats-v2.xml").execute(context);
				System.out.println("S'ha creat la BD");
			} catch (Exception ex) {
				System.out.println("No se la creado la DB");
			}
		} finally {
			try {
				context.close();
				System.out.println("\nDB CERRADA");
			} catch (Exception e) {
				System.out.println("No se ha podido cerrar la DB");
			}
		}
	}

	static public void query(final String query) throws BaseXException {
		System.out.println(new XQuery(query).execute(context));
	}

	static public void iterate(final String query) throws QueryException {
		try (QueryProcessor proc = new QueryProcessor(query, context)) {
			Iter iter = proc.iter();

			for (Item item; (item = iter.next()) != null;) {
				System.out.println(item.toJava());
			}
		}
	}
}
