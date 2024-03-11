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

public class PruebaMulticastServer {
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

			int puerto = 1234;

			InetAddress mcastaddr = InetAddress.getByName("239.0.0.1");
			InetSocketAddress group = new InetSocketAddress(mcastaddr, puerto);
			MulticastSocket socket = new MulticastSocket(puerto);
            while (true) {
                
            
            socket.joinGroup(group, netIf);

			/* Código de lectura */
			byte[] buffer = new byte[1000];
			DatagramPacket recv = new DatagramPacket(buffer, buffer.length);
			socket.receive(recv);
			System.out.println(new String(recv.getData(), 0, recv.getLength()));

			// Lo dejo cuando quiera
			socket.leaveGroup(group, netIf);
        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
