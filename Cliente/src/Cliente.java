import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

	public static final int PUERTO = 3400;
	public static final String SERVIDOR= "localhost";

	public static void main(String[] args) throws IOException {
		Socket socket=null;
		PrintWriter escritor=null;
		BufferedReader lector=null;
		System.out.println("cliente..");
		int cont=0;
		boolean salir=false;

		try {
			//se crean el socket en el  lado del cliente
			socket = new Socket(SERVIDOR,PUERTO);
			//se conectan los flujos tanto de salida como de entrada
			escritor = new PrintWriter(socket.getOutputStream(),true);
			lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(IOException e){
			e.printStackTrace();
			System.exit(-1);	
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

}
