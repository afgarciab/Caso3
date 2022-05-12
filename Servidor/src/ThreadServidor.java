import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.*;


public class ThreadServidor extends Thread {

	private Socket socket;

	private int numeroThreads;
	
	private PublicKey llavePublica;
	
	private PrivateKey llavePrivada;

	public ThreadServidor(PublicKey pLlavePublica, PrivateKey pLlavePrivada, Socket socket, int numeroThreads) {
		this.socket = socket;
		this.numeroThreads = numeroThreads;
		llavePublica=pLlavePublica;
		llavePrivada=pLlavePrivada;
	}

	public void run()
	{

		try {

			//se conectan los flujos tanto de salida como de entrada
			PrintWriter escritor = new PrintWriter(socket.getOutputStream(),true);
			BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//se ejecuta el protocolo en el lado del servidor
			//se ejecuta hasta que escriba ok
			int cont=0;
			while(cont<3) {
				if(cont==0)
				{
					
				}
				ProtocoloServidor.procesar(llavePublica,llavePrivada,lector, escritor,cont);
				cont++;
			}

			//se cierran los flujos del socket

			escritor.close();
			lector.close();
			socket.close();


		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}


}
