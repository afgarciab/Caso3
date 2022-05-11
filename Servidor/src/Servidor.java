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


	private Paquetes[] paquetes;


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


	}

	public void GenerarPaquetesBase()
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
	 * retorna true o false si el cliente está o no
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
	 * retorna true o false si el cliente está o no
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
