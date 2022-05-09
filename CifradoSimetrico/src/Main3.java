import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 
 */

/**
 * @author EQUIPO
 *
 */
public class Main3 {


	private final static String ALGORITMO ="AES";

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

		System.out.println ("Empezamos el programa");

		System.out.println ("Por favor introduzca una cadena por teclado:");

		String entradaTeclado = "";

		Scanner entradaEscaner = new Scanner(System.in); //Creación de un objeto Scanner

		entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner

		System.out.println ("Entrada recibida por teclado es: \"" + entradaTeclado +"\"");

		byte[] arregloBytes =entradaTeclado.getBytes();

		imprimir(arregloBytes);
		System.out.println("//////////////////////////////////////////////////////////");
		KeyGenerator keygen = KeyGenerator.getInstance(ALGORITMO);
		SecretKey secretKey = keygen.generateKey();
		
		//Genere una llave secreta y guárdela en un archivo
		FileOutputStream archivo = new FileOutputStream("nombreArchivo");
		ObjectOutputStream oos = new ObjectOutputStream(archivo);
		
		oos.writeObject(secretKey);
		oos.close();
		
		
		
		
		
		
		
		byte[] arregloBytesCifrado= Simetrico.cifrar(secretKey, entradaTeclado);
		imprimir(arregloBytesCifrado);
		System.out.println("//////////////////////////////////////////////////////////");
		byte[] arregloBytesDeCifrado=Simetrico.descifrar(secretKey, arregloBytesCifrado);
		imprimir(arregloBytesDeCifrado);

		System.out.println("texto decifrado: "+ new String(arregloBytesDeCifrado) );
	}

	public static void imprimir(byte[] contenido) {
		int i=0;
		for(;i<contenido.length-1;i++)
		{
			System.out.println(contenido[i] + " ");
		}
		System.out.println(contenido[i]+ " ");
	}

}
