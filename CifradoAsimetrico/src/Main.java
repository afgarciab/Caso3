import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.security.auth.callback.TextOutputCallback;

/**
 * 
 */

/**
 * @author EQUIPO
 *
 */
public class Main3 {


	private final static String ALGORITMO ="RSA";

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

		System.out.println ("Empezamos el programa");

		System.out.println ("Por favor introduzca una cadena por teclado:");

		String entradaTeclado = "";

		Scanner entradaEscaner = new Scanner(System.in); //Creaci�n de un objeto Scanner

		entradaTeclado = entradaEscaner.nextLine (); //Invocamos un m�todo sobre un objeto Scanner

		System.out.println ("Entrada recibida por teclado es: \"" + entradaTeclado +"\"");


		byte[] arregloBytes =entradaTeclado.getBytes();

		imprimir(arregloBytes);
		System.out.println("//////////////////////////////////////////////////////////");
		KeyGenerator keygen = KeyGenerator.getInstance(ALGORITMO);
		SecretKey secretKey = keygen.generateKey();

		//Genere una llave secreta y gu�rdela en un archivo
		FileOutputStream archivo = new FileOutputStream("./data/ejemplo.txt");
		ObjectOutputStream oos = new ObjectOutputStream(archivo);

		oos.writeObject(secretKey);
		oos.close();






		byte[] arregloBytesCifrado= Asimetrico.cifrar(secretKey, entradaTeclado);
		imprimir(arregloBytesCifrado);

		//Cifre un mensaje de entrada. Almacene el texto cifrado en un archivo.
		archivo = new FileOutputStream("./data/ejemplo.txt");
		oos =  new ObjectOutputStream(archivo);

		oos.writeObject(new String (arregloBytesCifrado));
		oos.close();

		System.out.println("//////////////////////////////////////////////////////////");
		
		
		byte[] arregloBytesDeCifrado=Asimetrico.descifrar(secretKey, arregloBytesCifrado);
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

	public static File crearArchivo(String contenidoArchivo)
	{
		try {
			String ruta = "/ruta/archivo.txt";
			String contenido = contenidoArchivo;
			File file = new File(ruta);
			// Si el archivo no existe es creado
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(contenido);
			bw.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
