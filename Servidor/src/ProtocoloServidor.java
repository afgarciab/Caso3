import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * 
 */

/**
 * @author EQUIPO
 *
 */
public class ProtocoloServidor {

	public static void procesar(PublicKey pLlavePublica, PrivateKey pLlavePrivada,BufferedReader pIn, PrintWriter pOut, int idProceso) throws IOException{
		// TODO Auto-generated method stub
		String inputLine;
		String outputLine;

		//lee del flujo de entrada
		inputLine= pIn.readLine();
		System.out.println("Entrada a procesar: " + inputLine);

		//procesa la entrada
		outputLine=inputLine;
		//El cliente pide iniciar la sesi�n y espera un mensaje de confirmaci�n del servidor (�ACK�)
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
			//cifro con la llave privada
			byte[] arregloCifrado= Asimetrico.cifrarPrivada(pLlavePrivada, "RSA", inputLine);
			//lo convierto en string
			outputLine= byte2str(arregloCifrado);
			pOut.println(outputLine);
			System.out.println("salida procesada: "+ outputLine);
		}
		else if(idProceso==2)
		{
			outputLine= "ok";
			pOut.println("ok");
			System.out.println("salida procesada: "+ outputLine);
		}

		//escribe en el flujo de salida

	}

	public static String byte2str( byte[] b )
	{
		// Encapsulamiento con hexadecimales
		String ret = "";
		for (int i = 0 ; i < b.length ; i++) {
			String g = Integer.toHexString(((char)b[i])&0x00ff);
			ret += (g.length()==1?"0":"") + g;
		}
		return ret;
	}
	public static byte[] str2byte( String ss)
	{
		// Encapsulamiento con hexadecimales
		byte[] ret = new byte[ss.length()/2];
		for (int i = 0 ; i < ret.length ; i++) {
			ret[i] = (byte) Integer.parseInt(ss.substring(i*2,(i+1)*2), 16);
		}
		return ret;
	}


	
	
	
	static class Asimetrico {
		public static byte[] cifrarPublica(PublicKey llave, String algoritmo, String texto) {
			long tiempoInicial = System.nanoTime();
			byte[] textoCifrado;
			try {
				Cipher cifrador = Cipher.getInstance(algoritmo);
				byte[] textoClaro = texto.getBytes();
				cifrador.init(Cipher.ENCRYPT_MODE, llave);
				textoCifrado = cifrador.doFinal(textoClaro);
				long tiempoFinal = System.nanoTime();
				System.out.println(tiempoFinal-tiempoInicial);
				return textoCifrado;
			} catch (Exception e) {
				System.out.println("Excepcion: "  + e.getMessage());
				return null;
			}
		}

		public static byte [] descifrarPrivada(PrivateKey llave, String algoritmo, byte[] texto) {
			long tiempoInicial = System.nanoTime();
			byte[] textoClaro;
			try {
				Cipher cifrador = Cipher.getInstance(algoritmo);
				cifrador.init(Cipher.DECRYPT_MODE, llave);
				textoClaro = cifrador.doFinal(texto);
			} catch (Exception e) {
				System.out.println("Excepcion: " + e.getMessage());
				return null;
			}
			long tiempoFinal = System.nanoTime();
			System.out.println(tiempoFinal-tiempoInicial);
			return textoClaro;
		}

		public static byte[] cifrarPrivada(PrivateKey llave, String algoritmo, String texto) {
			long tiempoInicial = System.nanoTime();
			byte[] textoCifrado;
			try {
				Cipher cifrador = Cipher.getInstance(algoritmo);
				byte[] textoClaro = texto.getBytes();
				cifrador.init(Cipher.ENCRYPT_MODE, llave);
				textoCifrado = cifrador.doFinal(textoClaro);
				long tiempoFinal = System.nanoTime();
				System.out.println(tiempoFinal-tiempoInicial);
				return textoCifrado;
			 } catch (Exception e) {
				System.out.println("Excepcion: "  + e.getMessage());
				return null;
			}
		}
		public static byte [] descifrarPublica(PublicKey llave, String algoritmo, byte[] texto) {
			long tiempoInicial = System.nanoTime();
			byte[] textoClaro;
			try {
				Cipher cifrador = Cipher.getInstance(algoritmo);
				cifrador.init(Cipher.DECRYPT_MODE, llave);
				textoClaro = cifrador.doFinal(texto);
			} catch (Exception e) {
				System.out.println("Excepcion: " + e.getMessage());
				return null;
			}
			long tiempoFinal = System.nanoTime();
			System.out.println(tiempoFinal-tiempoInicial);
			return textoClaro;
		}

	}


}
