import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class ProtocoloCliente {

	private final static String ALGORITMO ="AES";

	private SecretKey llaveSimetrica;



	public static String procesar(BufferedReader stdIn, BufferedReader pIn, PrintWriter pOut, int idProceso, PublicKey llavePublicaServidor) throws IOException, NoSuchAlgorithmException {
		String fromServer="";

		long random;
		//primero verifica verifica para iniciar sesion
		if(idProceso==0) {
			//lee el teclado
			System.out.println("escriba el mensaje para enviar");
			String fromUser = stdIn.readLine();

			//envia por red
			pOut.println(fromUser);


			//lee lo que llega por red
			//si lo que llega del servidor no es null
			//observe la asignacion luego la condicion
			if((fromServer = pIn.readLine())!=null)
			{
				System.out.println("Respuesta del servidor: "+ fromServer);
			}
		}
		//Envia el numero de 24 digitos y verifica
		if(idProceso==1) {
			//lee el teclado
			System.out.println("oprima Enter");
			String fromUser = stdIn.readLine();

			//envia por red
			random=generarNum();
			System.out.println("el numero es: " +random);
			pOut.println(Long.toString(random));


			//lee lo que llega por red
			//si lo que llega del servidor no es null
			//observe la asignacion luego la condicion
			if((fromServer = pIn.readLine())!=null)
			{
				//verificar que el reto sea el correcto.
				System.out.println(fromServer);
				byte[] resultado= Asimetrico.descifrarPublica(llavePublicaServidor, "RSA" , str2byte(fromServer));
				System.out.println("Respuesta del servidor: "+ byte2str(resultado));
			}
		}
		//aqui tengo que generar llave sincronica
		
		if(idProceso==2)
		{
			//lee el teclado
			System.out.println("oprima Enter");
			String fromUser = stdIn.readLine();

			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			SecretKey secretKey = keygen.generateKey();
			
			//sacado de https://self-learning-java-tutorial.blogspot.com/2015/07/convert-string-to-secret-key-in-java.html
			byte[] encoded = secretKey.getEncoded();
			String encodedKey = Base64.getEncoder().encodeToString(encoded);
			
			byte[] resultado= Asimetrico.cifrarPublica(llavePublicaServidor, "RSA" , encodedKey);
			//envia por red
			pOut.println();


			//lee lo que llega por red
			//si lo que llega del servidor no es null
			//observe la asignacion luego la condicion
			if((fromServer = pIn.readLine())!=null)
			{
				
				System.out.println("sera null? "+fromServer);

				System.out.println("Respuesta del servidor: "+ fromServer);
			}
		}
		return fromServer;


	}
	public static long generarNum()
	{
		double random= Math.random();
		return (long) (1000000000000000000000000.0*random);
	}

	public void GenerarLlaveSimetrica()
	{
		KeyGenerator keygen;
		try {
			keygen = KeyGenerator.getInstance(ALGORITMO);
			llaveSimetrica = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}

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

	public static class Simetrico {


		private final static String PADDING = "AES/ECB/PKCS5Padding";

		private final static String ALGORITMO ="AES";


		public static byte[] cifrar(SecretKey llave, String texto)
		{
			long tiempoInicial = System.nanoTime();

			byte[] textoCifrado;

			try {
				Cipher cifrador = Cipher.getInstance(PADDING);
				byte[] textoClaro = texto.getBytes();

				cifrador.init(Cipher.ENCRYPT_MODE,llave);
				textoCifrado = cifrador.doFinal(textoClaro);
				long tiempoFinal = System.nanoTime();
				System.out.println(tiempoFinal-tiempoInicial);
				return textoCifrado;
			} catch (Exception e) {
				System.out.println("Exception: "+ e.getMessage());
				return null;
			}
		}

		public static byte[] descifrar(SecretKey llave, byte[] texto)
		{
			long tiempoInicial = System.nanoTime();
			byte[] textoClaro;

			try {
				Cipher cifrador = Cipher.getInstance(PADDING);
				cifrador.init(Cipher.DECRYPT_MODE, llave);
				textoClaro = cifrador.doFinal(texto);
			} catch (Exception e) {
				System.out.println("Excepcion: "+e.getMessage());
				return null;
			}
			long tiempoFinal = System.nanoTime();
			System.out.println(tiempoFinal-tiempoInicial);
			return textoClaro;
		}

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
				System.out.println("el tiempo es: "+(tiempoFinal-tiempoInicial));
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
