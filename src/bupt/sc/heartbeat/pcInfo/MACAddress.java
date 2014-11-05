package bupt.sc.heartbeat.pcInfo;

/*  
 * Created on 2005-6-5  
 * Author stephen  
 * Email zhoujianqiang AT gmail DOT com  
 * CopyRight(C)2005-2008 , All rights reserved.  
 */  
import java.io.BufferedReader;   
import java.io.IOException;   
import java.io.InputStreamReader;   
  
/**  
 *   
 * @author stephen  
 * @version 1.0.0  
 */  
public class MACAddress {   
  
    /**  

     */  
    public static String getOSName() {   
        return System.getProperty("os.name").toLowerCase();   
    }   

    public static String getUnixMACAddress() {   
        String mac = null;   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ifconfig");
            
            bufferedReader = new BufferedReader(new InputStreamReader(process   
                    .getInputStream()));   
            String line = null;   
            int index = -1;   
            while ((line = bufferedReader.readLine()) != null) {   
                index = line.toLowerCase().indexOf("hwaddr");
                
                if (index >= 0) {
                	
                    mac = line.substring(index +"hwaddr".length()+ 1).trim();
                     
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
  

    public static String getWindowsMACAddress() {   
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
            	//System.out.println(line);
                //index = line.toLowerCase().indexOf("physical address");
                index = line.toLowerCase().indexOf("�����ַ");
                
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
            process = null;   
        }   
  
        return mac;   
    }   

    public static void main(String[] argc) {   
        String os = getOSName();   
        System.out.println(os);   
        if(os.startsWith("windows")){   
            String mac = getWindowsMACAddress(); 
            mac = mac.replaceAll("-", ":");
            System.out.println(mac);   
        }else{   
            String mac = getUnixMACAddress();   
            System.out.println(mac);   
        }   
    }   
}  

