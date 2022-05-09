import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
//https://www.aprenderaprogramar.com/index.php?option=com_content&view=article&id=623:pedir-datos-en-java-por-consola-teclado-escape-backslash-systemoutprint-salto-de-linea-n-cu00657b&catid=68&Itemid=188
public class Main {

	private final static String ALGORITMO ="AES";
	
	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
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
