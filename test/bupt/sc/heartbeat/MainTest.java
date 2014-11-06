package bupt.sc.heartbeat;

import org.junit.Test;

import bupt.sc.heartbeat.cloud.CCMonitor;
import bupt.sc.heartbeat.host.ReportHMIP;

public class MainTest {
	@Test
	public void testCCMonitor(){
		CCMonitor.main(null);
	}
	
	@Test
	public void testReportHMIP(){
		ReportHMIP.main(null);
	}
	
	public static void main(String[] args) {
		
	}
}
