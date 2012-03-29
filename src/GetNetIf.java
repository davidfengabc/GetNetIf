
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class GetNetIf {
	public static void main(String[] args) {
		String[] command = {"ps", "-e"};
		try {
			Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
			for(NetworkInterface iface : Collections.list(ifaces)) {
				String textAddrs = " ";
				String ifaceName = iface.getDisplayName();
				if ((ifaceName.equals("lo")) || (ifaceName.equals("wlan0")))
					continue;
				Enumeration<InetAddress> addrs= iface.getInetAddresses();
				for(InetAddress addr : Collections.list(addrs)) {
					textAddrs = textAddrs + " " + addr.getHostAddress();
				}
				System.out.println(iface.getDisplayName() + textAddrs);
			}
			
			ProcessBuilder pb = new ProcessBuilder(command);
			Process proc = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader (proc.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				if((line.indexOf("hostapd") != -1) || line.indexOf("dnsmasq") != -1) {
					System.out.println(line);
					break;
				}
	        }
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}