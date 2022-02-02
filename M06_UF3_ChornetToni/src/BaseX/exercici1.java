package BaseX;

import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.Close;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.DropDB;
import org.basex.core.cmd.InfoDB;
import org.basex.core.cmd.Open;

public class exercici1 {

	public static void main(String[] args) {

		Context context = new Context();

		System.out.println("Create a new databae.");

		try {
			new CreateDB("DBExemple", "universitats-v2.xml").execute(context);

			System.out.println("Close and reopen database.");

			new Close().execute(context);
			new Open("DBExemple").execute(context);

			System.out.println("Show database information.");

			System.out.println(new InfoDB().execute(context));

			System.out.println("Drop de database.");

			new DropDB("DBExemple").execute(context);

		} catch (BaseXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

}
