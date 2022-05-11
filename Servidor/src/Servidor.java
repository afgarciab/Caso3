import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

// de aqui sacamos como hacer el rsa https://www.baeldung.com/java-rsa
public class Servidor {


	private Paquetes[] paquetes;


	public static final int PUERTO = 3400;

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

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
