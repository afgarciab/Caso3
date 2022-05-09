import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ProtocolException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;

// de aqui sacamos como hacer el rsa https://www.baeldung.com/java-rsa

public class Servidor {

	

	public static final int PUERTO = 3400;

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		//			KeyPairGenerator generator;
		//	
		//			//generamos par de llaves en RSA con 256 bits
		//			generator = KeyPairGenerator.getInstance("RSA");
		//			generator.initialize(256);
		//			KeyPair pair = generator.generateKeyPair();
		//	
		//			//extraemos la llave privada y publica
		//			PrivateKey privateKey = pair.getPrivate();
		//			PublicKey publicKey = pair.getPublic();
		//	
		//	
		//			//almacenar claves en un archivo
		//			//Para guardar una clave en un archivo, podemos usar el método getEncoded 
		//			//, que devuelve el contenido de la clave en su formato de codificación principal
		//			try (FileOutputStream fos = new FileOutputStream("public.key")) {
		//				fos.write(publicKey.getEncoded());
		//			}
		//			//Para leer la clave de un archivo, primero debemos cargar el contenido como una matriz de bytes
		//			File publicKeyFile = new File("public.key");
		//			byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		//			
		//			//KeyFactory para recrear la instancia real
		//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		//			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		//			keyFactory.generatePublic(publicKeySpec);
		//	

















		int numeroThreads=0;
		ServerSocket ss=null;
		boolean continuar = true;

		System.out.println("Main server...");

		try {
			ss= new ServerSocket(PUERTO);
		}catch(IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		while(continuar)
		{
			//crea el socket en el lado del servidor
			//queda bloqueado esperando a que llegue el cliente
			Socket socket = ss.accept();

			ThreadServidor thread = new ThreadServidor(socket, numeroThreads);
			numeroThreads++;

			//start
			thread.start();
		}
		ss.close();
		// TODO Auto-generated method stub

	}
	
	


}
