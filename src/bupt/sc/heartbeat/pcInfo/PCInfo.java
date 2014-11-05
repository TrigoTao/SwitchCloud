package bupt.sc.heartbeat.pcInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.util.StringTokenizer;
import com.sun.management.OperatingSystemMXBean;
/**
 * @author Administrator
 *
 */
public class PCInfo {
	private static final int CPUTIME=2000;
	private static final int PERCENT=100;
	/**
	 * return the total size of disk in G
	 * @return
	 */
	public static String getTotalDisk(){
        File[] roots = File.listRoots();
        int temp = 0;
        String str = "";
        for (File file : roots) {
			if (file.getFreeSpace() != 0) {
				temp +=file.getTotalSpace()/1024/1024/1024;
			}
        }
        str = str+temp;
        return str;
	}
	
	/**
	 * return the total size of physical Memory in M
	 * @return
	 */
	public static String getTotalMemory(){
		String str = "";
		OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	    str = str+osmb.getTotalPhysicalMemorySize()/1024/1024;
	    
	    return str;
	}
	
	/**
	 * return the NO. of CPUs
	 * @return
	 */
	public static String getCPUs(){
		String str = "";
		OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	    str =str+osmb.getAvailableProcessors();
	    
	    return str;
	}

	/**
	 * 
	 *
	public static double[] cpuCurrent(){
		int num = Integer.parseInt(getCPUs());
		double[] temp = new double[num];
		//OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		//锟斤拷没锟斤拷实锟斤拷~~
		return temp;
		
	}*/
	
	
	public double cpuCurrent()
	{
		String osName=System.getProperty("os.name").toLowerCase();
		
		if(osName.startsWith("windows"))
		{
			return this.getCpuRatioForWindows();
		}
		return this.getCpuRatioForLinux();
		
	}
	
	private double getCpuRatioForLinux()
	{
		double cpuUsed=0;
		Runtime runtime=Runtime.getRuntime();
		Process process=null;
		BufferedReader reader=null;
		StringTokenizer tokenizer=null;
		String str=null;
		String Cpu=null;
		double idelCpu=0;
		try{
			process=runtime.exec("top -b -n 1");
			reader=new BufferedReader(new InputStreamReader(process.getInputStream()));

			int line=0;
			
			while(line++<5&&(str=reader.readLine())!=null)
			{
				if(str.startsWith("Cpu")==false)
				{
					continue;
				}
				tokenizer=new StringTokenizer(str);
				
				while(tokenizer.hasMoreTokens())
				{
					Cpu=tokenizer.nextToken();
					if(Cpu.contains("id"))
					{
						idelCpu=Double.parseDouble(Cpu.substring(0,Cpu.indexOf("%")));
					}
				}
			}
		    
			reader.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		cpuUsed=100-idelCpu;
		return cpuUsed;
	}
	
	private double getCpuRatioForWindows()
	{
		try
		{
			String procCmd=System.getenv("windir")+"\\system32\\wbem\\wmic.exe process get " +
					"Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount," +
					"UserModeTime,WriteOperationCount";
			
			long[] c0=readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(CPUTIME);
			long[] c1=readCpu(Runtime.getRuntime().exec(procCmd));
			if(c0!=null&&c1!=null)
			{
				long ideltime=c1[0]-c0[0];
				long busytime=c1[1]-c0[1];
				return Double.valueOf(PERCENT*(busytime)/(busytime+ideltime)).doubleValue();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	private long[] readCpu(final Process proc)
	{
		long[] result=new long[2];
		try{
			proc.getOutputStream().close();
			InputStreamReader isr=new InputStreamReader(proc.getInputStream());
			LineNumberReader input=new LineNumberReader(isr);
			String line=input.readLine();
			if(line==null)
			{
				return null;
			}
			
			int capidx=line.indexOf("Caption");
			int cmdidx=line.indexOf("CommandLine");
			int kmtidx=line.indexOf("KernelModeTime");
			int rocidx=line.indexOf("ReadOperationCount");
			int umtidx=line.indexOf("UserModeTime");
			int wocidx=line.indexOf("WriteOperationCount");
			long ideltime=0;
			long kneltime=0;
			long usertime=0;
			
			while((line=input.readLine())!=null)
			{
				if(line.length()<wocidx)
				{
					continue;
				}
				String caption=this.subString(line,capidx, cmdidx).trim();
				String cmd=this.subString(line,cmdidx, kmtidx).trim();
				if(cmd.indexOf("wmic.exe")>=0)
				{
					continue;
				}
				
				String kmtime=this.subString(line, kmtidx,rocidx).trim();
				String umtime=this.subString(line,umtidx, wocidx).trim();
				if(caption.equals("System Idle Process")||caption.equals("System"))
				{
					if(kmtime.length()>0)
						ideltime+=Long.valueOf(kmtime).longValue();
					if(umtime.length()>0)
						ideltime+=Long.valueOf(umtime).longValue();
				}
				else
				{
					if(kmtime.length()>0)
						kneltime+=Long.valueOf(kmtime).longValue();
					if(umtime.length()>0)
						usertime+=Long.valueOf(umtime).longValue();
				}
				
			}
			result[0]=ideltime;
			result[1]=kneltime+usertime;
			return result;
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try{
				proc.getInputStream().close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
		
		            
	}
	
	
	private String subString(String s,int start_index,int end_index)
	{
		byte[] bytes=s.getBytes();
		String subS="";
		for(int i=start_index;i<end_index;i++)
		{
			subS+=(char)bytes[i];
		}
		return subS;
	}
	
	/**
	 * return the Free size of Disk in G 
	 * @return
	 */
	public static String getFreeDisk(){
        File[] roots = File.listRoots();
        long temp = 0;
        String str = "";
        for (File file : roots) {
			if (file.getFreeSpace() != 0) {
				temp +=file.getFreeSpace()/1024/1024/1024;
			}
        }
        str = str+temp;
        return str;
	}
	
	/**
	 * return the Free size of Disk in M
	 * @return
	 */
	public static String getFreeMemory(){
		String str = "";
		OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	    str = str+ osmb.getFreePhysicalMemorySize()/1024/1024;
	    return str;
	}
	
	/**
	 * return the External IP Address of the PC or VM
	 * @return
	 */
	public static String getIP(){
        String os = MACAddress.getOSName();  
        String ip = "";
        if(os.startsWith("windows")){   
            ip = IPAddress.getWindowsIPAddress();   
        }else{   
            ip = IPAddress.getUnixIPAddress();    
        }  
        return ip;
	}
	
	/**
	 * return the mac address of PC or VM
	 * @return
	 */
	public static String getMAC(){
		
        String os = MACAddress.getOSName();
        //System.out.println(os);
        String mac = "";
        if(os.startsWith("windows")){     
            mac = MACAddress.getWindowsMACAddress(); 
            //System.out.println(mac);
        }else{   
            mac = MACAddress.getUnixMACAddress();    
        }  
        
        mac = mac.replaceAll("-", ":");
        mac = mac.replaceAll(":", "");
        return mac;
	}
	
	
	public static String getCurrentDisk(){
		int total = Integer.parseInt(PCInfo.getTotalDisk());
		int free = Integer.parseInt(PCInfo.getFreeDisk());
		int Inuse = total-free;
		
		return ""+Inuse;
	}
	
	
	public static String getCurrentMemory(){
		int total = Integer.parseInt(PCInfo.getTotalMemory());
		int free = Integer.parseInt(PCInfo.getFreeMemory());
		int Inuse = total-free;
		
		return ""+Inuse;
	}
	public static void main(String...strings){
		System.out.println("My ip is " + getIP() );
		System.out.println("My mac is " + getMAC() );
		
	}
	
}