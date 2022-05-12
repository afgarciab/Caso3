import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 */

/**
 * @author EQUIPO
 *
 */
public class ProtocoloServidor {

	private static Paquetes[] paquetes;
	
	public static void procesar(PublicKey pLlavePublica, PrivateKey pLlavePrivada,BufferedReader pIn, PrintWriter pOut, int idProceso) throws IOException{
		// TODO Auto-generated method stub
		String inputLine;
		String outputLine;
		SecretKey llaveSincronica;

		//lee del flujo de entrada
		inputLine= pIn.readLine();
		System.out.println("Entrada a procesar: " + inputLine);


		
		//El cliente pide iniciar la sesión y espera un mensaje de confirmación del servidor (¨ACK¨)

		//procesa la entrada
		outputLine=inputLine;
		//El cliente pide iniciar la sesiï¿½n y espera un mensaje de confirmaciï¿½n del servidor (ï¿½ACKï¿½)

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
			System.out.println("salida procesada: "+ new String(outputLine));
		}
		else if(idProceso==2)
		{
			//sacado de https://stackoverflow.com/questions/5355466/converting-secret-key-into-a-string-and-vice-versa
			//decibra con la privada la llave simetrica
			
			byte[] llave =Asimetrico.descifrarPrivada(pLlavePrivada, "RSA", str2byte(inputLine) );
			byte[] decodedKey = Base64.getDecoder().decode(byte2str(llave));
			SecretKey originalKey = new SecretKeySpec(decodedKey, 0, llave.length, "AES");
			llaveSincronica= originalKey;
			outputLine= "ACK";
			pOut.println("ACK");
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
				byte[] textoClaro = texto.getBytes(StandardCharsets.UTF_8);
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
				byte[] textoClaro = texto.getBytes(StandardCharsets.UTF_8);
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
		
		public static void GenerarPaquetesBase()
		{
			paquetes=new Paquetes[31];
			int i =0;
			for (;i<5;i++)
			{
				paquetes[i]=new Paquetes("a", i, "PKT_EN_OFICINA");
			}
			for (;i<10;i++)
			{
				paquetes[i]=new Paquetes("b", i, "PKT_RECOGIDO");
			}
			for (;i<15;i++)
			{
				paquetes[i]=new Paquetes("c", i, "PKT_EN_CLASIFICACION");
			}
			for (;i<20;i++)
			{
				paquetes[i]=new Paquetes("d", i, "PKT_DESPACHADO");
			}
			for (;i<25;i++)
			{
				paquetes[i]=new Paquetes("a", i, "PKT_EN_ENTREGA");
			}
			for (;i<30;i++)
			{
				paquetes[i]=new Paquetes("b", i, "PKT_ENTREGADO");
			}
			for (;i<32;i++)
			{
				paquetes[i]=new Paquetes("c", i, "PKT_DESCONOCIDO");
			}

		}

		/**
		 * retorna true o false si el cliente estï¿½ o no
		 * @param nombreCliente
		 * @param idPaquete
		 * @return
		 */
		public boolean buscarClienteConPaquete(String nombreCliente,int idPaquete)
		{
			for (int i =0;i<32;i++)
			{
				if((nombreCliente.equals(paquetes[i].getNombreUsuario())&&(idPaquete==paquetes[i].getIdPaquete()))) {
					return true;
				}
			}
			return false;
		}

		/**
		 * retorna true o false si el cliente estï¿½ o no
		 * @param nombreCliente
		 * @param idPaquete
		 * @return
		 */
		public boolean buscarCliente(String nombreCliente)
		{
			for (int i =0;i<32;i++)
			{
				if((nombreCliente.equals(paquetes[i].getNombreUsuario()))) {
					return true;
				}
			}
			return false;
		}

	}


}
