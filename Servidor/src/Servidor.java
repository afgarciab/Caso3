


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ProtocolException;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

import javax.crypto.*;



// de aqui sacamos como hacer el rsa https://www.baeldung.com/java-rsa
public class Servidor {


	private Paquetes[] paquetes;
	
	private static PublicKey llavePublica;
	
	private static PrivateKey llavePrivada;


	public static final int PUERTO = 3400;

	public static void main(String[] args) throws Exception {

		int numeroThreads=0;
		ServerSocket ss=null;
		boolean continuar = true;

		System.out.println("Main server...");

		final String ALGORITMO = "RSA";

		getLlavesAsimetricas( ALGORITMO);

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

			ThreadServidor thread = new ThreadServidor(llavePublica, llavePrivada,socket, numeroThreads);
			numeroThreads++;

			//start
			thread.start();
		}
		ss.close();
	}

	public static void getLlavesAsimetricas( String ALGORITMO) throws Exception {

		try {

			KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITMO);
			generator.initialize(1024);
			KeyPair keyPair = generator.generateKeyPair();
			llavePublica = keyPair.getPublic();
			llavePrivada = keyPair.getPrivate();

			File publicKFile = new File("./data/publicK.txt");
			// Si el archivo no existe es creado
			if (!publicKFile.exists()) {
				publicKFile.createNewFile();
				FileOutputStream publicK = new FileOutputStream(publicKFile, false);
				ObjectOutputStream oosPublicK = new ObjectOutputStream(publicK);
				oosPublicK.writeObject(llavePublica);
				oosPublicK.close();
			}else {
				llavePublica = getPublicKey("./data/publicK.txt");
			}
			
			
			File privateKFile = new File("./data/privateK.txt");
			// Si el archivo no existe es creado
			if (!privateKFile.exists()) {
				privateKFile.createNewFile();
				FileOutputStream privateK = new FileOutputStream(privateKFile, false);
				ObjectOutputStream oosPrivateK = new ObjectOutputStream(privateK);
				
				oosPrivateK.writeObject(llavePrivada);
				oosPrivateK.close();
			}else {
				llavePrivada= getPrivateKey("./data/privateK.txt");
			}
			
			System.out.println(llavePublica);

		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage() + " in the specified directory.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
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
	 * retorna true o false si el cliente est� o no
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
	 * retorna true o false si el cliente est� o no
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
	
	public static PublicKey getPublicKey(String filename) throws Exception {
		PublicKey llave = null;	
		FileInputStream	file = new FileInputStream(filename);
		ObjectInputStream ois = new ObjectInputStream(file);

		llave = (PublicKey) ois.readObject();
		ois.close();
		return llave;
	}
	
	public static PrivateKey getPrivateKey(String filename) throws Exception {
		PrivateKey llave = null;	
		FileInputStream	file = new FileInputStream(filename);
		ObjectInputStream ois = new ObjectInputStream(file);

		llave = (PrivateKey) ois.readObject();
		ois.close();
		return llave;
	}



}

