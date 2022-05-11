import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author EQUIPO
 *
 */
public class Main {

	private final static String ALGORITMO = "RSA";

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

		System.out.println("Empezamos el programa");

		System.out.println("Por favor introduzca una cadena por teclado:");

		String entradaTeclado = "";

		Scanner entradaEscaner = new Scanner(System.in); // Creaci�n de un objeto Scanner

		entradaTeclado = entradaEscaner.nextLine(); // Invocamos un m�todo sobre un objeto Scanner

		System.out.println("Entrada recibida por teclado es: \"" + entradaTeclado + "\"");

		byte[] arregloBytes = entradaTeclado.getBytes();

		imprimir(arregloBytes);
		System.out.println("//////////////////////////////////////////////////////////");
		KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITMO);
		generator.initialize(1024);
		KeyPair keyPair = generator.generateKeyPair();
		PublicKey llavePublica = keyPair.getPublic();
		PrivateKey llavePrivada = keyPair.getPrivate();

		// Genere una llave secreta y gu�rdela en un archivo
		try {
			File publicKFile = new File("./../data/publicK.txt");
			// Si el archivo no existe es creado
			if (!publicKFile.exists()) {
				publicKFile.createNewFile();
			}
			FileOutputStream publicK = new FileOutputStream(publicKFile, false);
			ObjectOutputStream oosPublicK = new ObjectOutputStream(publicK);

			File privateKFile = new File("./../data/privateK.txt");
			// Si el archivo no existe es creado
			if (!privateKFile.exists()) {
				privateKFile.createNewFile();
			}
			FileOutputStream privateK = new FileOutputStream(privateKFile, false);
			ObjectOutputStream oosPrivateK = new ObjectOutputStream(privateK);

			oosPublicK.writeObject(llavePublica);
			oosPublicK.close();

			oosPrivateK.writeObject(llavePrivada);
			oosPrivateK.close();

			/*-------------------------------------------------------------------------*/

			byte[] arregloBytesCifrado = Asimetrico.cifrar(llavePublica, ALGORITMO, entradaTeclado);
			imprimir(arregloBytesCifrado);

			// Cifre un mensaje de entrada. Almacene el texto cifrado en un archivo.
			publicK = new FileOutputStream("./../data/publicK.txt");
			oosPublicK = new ObjectOutputStream(publicK);

			oosPublicK.writeObject(new String(arregloBytesCifrado));
			oosPublicK.close();

			System.out.println("//////////////////////////////////////////////////////////");

			byte[] arregloBytesDecifrado = Asimetrico.descifrar(llavePrivada, ALGORITMO, arregloBytesCifrado);
			imprimir(arregloBytesDecifrado);

			System.out.println("texto decifrado: " + new String(arregloBytesDecifrado));
		} catch (FileNotFoundException ex) {
			System.out
					.println(ex.getMessage() + " in the specified directory.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void imprimir(byte[] contenido) {
		int i = 0;
		for (; i < contenido.length - 1; i++) {
			System.out.println(contenido[i] + " ");
		}
		System.out.println(contenido[i] + " ");
	}

	public static File crearArchivo(String contenidoArchivo) {
		try {
			String ruta = "./ruta/archivo.txt";
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
