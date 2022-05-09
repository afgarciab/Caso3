import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 */

/**
 * @author EQUIPO
 *
 */
public class ProtocoloServidor {

	public static void procesar(BufferedReader pIn, PrintWriter pOut) throws IOException{
		// TODO Auto-generated method stub
		String inputLine;
		String outputLine;

		//lee del flujo de entrada
		inputLine= pIn.readLine();
		System.out.println("Entrada a procesar: " + inputLine);

		//procesa la entrada
		outputLine=inputLine;
		if(inputLine.equals("ok"))
		{
			outputLine="adios";
		}

		//escribe en el flujo de salida
		pOut.println(outputLine);
		System.out.println("salida procesada: "+ outputLine);
	}

}
