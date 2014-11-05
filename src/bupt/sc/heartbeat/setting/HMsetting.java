package bupt.sc.heartbeat.setting;

public class HMsetting {
	/**Useful*/
	
	public static int HM_CC_HB_PORT = 8818;
	public static int HM_CC_INFO_PORT = 8819;
	public static int HM_VM_INITIAL_PORT = 8821;
	public static int VM_HM_HB_PORT = 8824;
	
	public static int HM_CC_TIMEOUT = 5000;
	public static int HM_HB_INTERVAL = 10000;
	
	public static int MAXNOHBTIME = 10000;
	
	
	public static int HM_VMINFO_INTERVAL = 10000;
	
	public static int MAXVMHBTHREAD = 50;
	

	
	
	// Might not be use
	public static int HM_VM_CLEANER_INTERVAL = 60000;
	public static int HM_HB_CLEANER_INTERVAL = 75000;
	public static int HM_VM_INITIALTIMEOUT = 5000;
	

	public static int LENGTH_OF_MAC_PREFIX = 6;
	public static int CC_HM_PORT = 8822;
	public static int CC_HM_STATE_QUERY_PORT = 8823;
	public static int CC_HM_NOTIFY_NEW_VM = 8825;

	
	public static String InitialWORD = "IAMYOURNODE";
	public static String RequestIP = "TELLMEYOURIP";
	public static String NodeIPINFO = "MYIPIS";
	public static String ConfirmWORD = "YOUAREMYNODE";
	
	
	//public static int CHECKNEWVMINTERVAL = 5000;
	public static int CHECKNEWVMINTERVAL = 2000;

	
}
