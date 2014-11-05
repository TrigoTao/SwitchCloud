package bupt.sc.heartbeat.pcInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IPAddress {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

        String os = MACAddress.getOSName();  
        String ip = "";
        if(os.startsWith("windows")){   
            ip = IPAddress.getWindowsIPAddress();   
        }else{    
            ip = IPAddress.getUnixIPAddress();    
        } 
		System.out.println(os+ip);
	}
	
	
    public static String getOSName() {   
        return System.getProperty("os.name").toLowerCase();   
    }
    
	public static String getWindowsIPAddress() {   
        String mac = null;   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process   
                    .getInputStream()));   
            String line = null;   
            int index = -1;   
            while ((line = bufferedReader.readLine()) != null) { 
            	index = line.toLowerCase().indexOf("ipv4"); 
            	//index = line.toLowerCase().indexOf("ip address");  
                if (index >= 0) { 
                    index = line.indexOf(":"); 
                    if (index>=0) {   
                        mac = line.substring(index + 1).trim();
                    }   
                    break;   
                }   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
            } catch (IOException e1) {   
                e1.printStackTrace();   
            }   
            bufferedReader = null;   
            if(process !=null)
            	process.destroy();   
        }   
  
        return mac;   
    }   
  

	public static String getUnixIPAddress() {   
        String mac = null;   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ifconfig");
            bufferedReader = new BufferedReader(new InputStreamReader(process   
                    .getInputStream()));   
            String line = null;   
            int index = -1; 
            int index2 = -1; 
            while ((line = bufferedReader.readLine()) != null) {   
                index = line.toLowerCase().indexOf("inet addr:");
                index2=line.toLowerCase().indexOf("bcast");
                if (index >= 0) { 
                	mac = line.substring(index +"inet addr:".length(),index2-2).trim();//  ȡ��mac��ַ��ȥ��2�߿ո�   
                    break;   
                }   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
            } catch (IOException e1) {   
                e1.printStackTrace();   
            }   
            bufferedReader = null;   
            process = null;   
        }   
  
        return mac;   
    } 
}

