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

	public static void procesar(BufferedReader pIn, PrintWriter pOut, int idProceso) throws IOException{
		// TODO Auto-generated method stub
		String inputLine;
		String outputLine;

		//lee del flujo de entrada
		inputLine= pIn.readLine();
		System.out.println("Entrada a procesar: " + inputLine);

		//procesa la entrada
		outputLine=inputLine;
		//El cliente pide iniciar la sesión y espera un mensaje de confirmación del servidor (¨ACK¨)
		if(idProceso==0) {
			if(inputLine.equals("INICIO"))
			{
				outputLine="ACK";
			}
			else {
				outputLine="DESCONOCIDO";
			}
			pOut.println(outputLine);
			System.out.println("salida procesada: "+ outputLine);
		}
		//El servidor responde con el reto cifrado con su llave privada.
		else if(idProceso==1)
		{
			outputLine= inputLine;
			pOut.println(Long.parseLong(inputLine));
			System.out.println("salida procesada: "+ outputLine);
		}

		//escribe en el flujo de salida
		
	}



}
