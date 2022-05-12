import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * 
 */

/**
 * @author EQUIPO
 *
 */
public class Asimetrico {
	public static byte[] cifrar(PrivateKey llave, String algoritmo, String texto) {
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
	
	public static byte [] descifrar(PublicKey llave, String algoritmo, byte[] texto) {
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
