import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class ProtocoloCliente {
	
	private final static String ALGORITMO ="AES";
	
	private SecretKey llaveSimetrica;

	public static String procesar(BufferedReader stdIn, BufferedReader pIn, PrintWriter pOut, int idProceso) throws IOException {
		String fromServer="";
		
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
			pOut.println(generarNum());


			//lee lo que llega por red
			//si lo que llega del servidor no es null
			//observe la asignacion luego la condicion
			if((fromServer = pIn.readLine())!=null)
			{
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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


}
