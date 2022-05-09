import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ProtocoloCliente {

	public static void procesar(BufferedReader stdIn, BufferedReader pIn, PrintWriter pOut) throws IOException {

		//lee el teclado
		System.out.println("escriba el mensaje para enviar");
		String fromUser = stdIn.readLine();

		//envia por red
		pOut.println(fromUser);

		String fromServer="";
		//lee lo que llega por red
		//si lo que llega del servidor no es null
		//observe la asignacion luego la condicion
		if((fromServer = pIn.readLine())!=null)
		{
			System.out.println("Respuesta del servidor: "+ fromServer);
		}


	}

}
