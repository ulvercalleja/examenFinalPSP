package UDP.Multicast.PruebaMulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

public class PruebaMulticastCliente {
    public static void main(String[] args) {
		try {

			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();

			for (NetworkInterface netint : Collections.list(nets)) {
				System.out.println(netint);
			}

			Scanner in = new Scanner(System.in);
            
			System.out.println("Especifica el nombre del interfaz");
			String iName = in.nextLine();
			NetworkInterface netIf = NetworkInterface.getByName(iName);
			System.out.println(netIf);

			
			String msg = " Aprobado general ya de ya ";
			int puerto = 1234;

			InetAddress ip = InetAddress.getByName("239.0.0.1");
			InetSocketAddress group = new InetSocketAddress(ip, puerto);
			MulticastSocket s = new MulticastSocket(puerto);

			s.joinGroup(group, netIf);

			/* Código de envío */
			byte[] buffer = msg.getBytes();
			DatagramPacket hi = new DatagramPacket(buffer, buffer.length, group);
			s.send(hi);

			// Lo dejo cuando quiera
			s.leaveGroup(group, netIf);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
