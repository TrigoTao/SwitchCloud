package bupt.sc.heartbeat.vm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bupt.sc.heartbeat.setting.VMsetting;

public class VMEnd {
	private static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) {
		try(ServerSocket serversocket = new ServerSocket(VMsetting.InitialLISTENPORT);){
			String line, nodeIP;
			while(true){
				Socket incoming = serversocket.accept();
				try ( BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream())); 
						PrintWriter out = new PrintWriter(incoming.getOutputStream(),true)){
					boolean connected = false;
					while(!connected && (line = in.readLine()) != null){
						// Step 1
						if(line.equals(VMsetting.InitialWORD)){
							out.println(VMsetting.RequestIP);
						}
						// Step 2
						if(line.contains(VMsetting.NodeIPINFO)){
							int start = line.indexOf("#");
							nodeIP = line.substring(start+1);
							logger.info(nodeIP);
							connected = true;
							// Connection & Authentication OK
							// VMTest dies and passes work to VMDaemon
							new VMDaemon(nodeIP).start();
						}
						Thread.sleep(5000);
					}						
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
			}		
		} catch (IOException e1) {
			logger.error(e1);
		}
	}
}