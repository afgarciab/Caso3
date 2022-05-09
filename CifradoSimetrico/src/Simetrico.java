import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * 
 */

/**
 * @author EQUIPO
 *
 */
public class Simetrico {


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
