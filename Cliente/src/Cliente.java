import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

public class Cliente {

	public static final int PUERTO = 3400;
	public static final String SERVIDOR = "localhost";

	public static void main(String[] args) throws IOException {
		Socket socket=null;
		PrintWriter escritor=null;
		BufferedReader lector=null;
		System.out.println("cliente..");

		PublicKey llavePublica = null;

		int cont=0;
		boolean salir=false;

		try {
			//se crean el socket en el  lado del cliente
			socket = new Socket(SERVIDOR,PUERTO);
			//se conectan los flujos tanto de salida como de entrada
			escritor = new PrintWriter(socket.getOutputStream(),true);
			lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			llavePublica = getPublicKey("./data/publicK.txt");
		}
		catch(IOException e){
			e.printStackTrace();
			System.exit(-1);	
		} catch (Exception e) {
			e.printStackTrace();
		}

		// crear flujo para leer lo que escribe el cliente por el teclado
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		//ejecuto hasta que escriba adios

		while(cont<2&&salir==false) {
			//se ejecuta el protocolo en el lado del cliente

			//primero verifica verifica para iniciar sesion
			if (cont==0) {
				String respuesta =ProtocoloCliente.procesar(stdIn,lector, escritor,cont);
				if(respuesta.equals("DESCONOCIDO"))
				{
					salir=true;
				}

			}else if (cont==1) {
				System.out.println("paso2");
			}
			cont++;

		}
		//se cierran los flujos y el socket
		stdIn.close();
		escritor.close();
		lector.close();
		socket.close();

	}

	public static PublicKey getPublicKey(String filename)
			throws Exception {

		byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}
}
