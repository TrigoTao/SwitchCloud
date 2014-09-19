package bupt.sc.nova.vm;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bupt.sc.nova.model.VNNodeInfo;
import bupt.sc.nova.service.VNNodeInfoService;
import bupt.sc.nova.service.VRDEPortService;
import bupt.sc.nova.statistic.VMInfo;
import bupt.sc.utils.SCPath;


/**
 * Use CLI VBoxManage to handle VirtualBox
 * 
 * @author chenzhuo
 * 
 */
public class VBoxManager implements VMOperationManager {
	private final Logger logger = LogManager.getLogger(VMOperationManager.class.getName()); 
	
	private VRDEPortService vrdePortService;
	private VNNodeInfoService vnNodeInfoService;
	
	public VRDEPortService getVrdePortService() {
		return vrdePortService;
	}

	public void setVrdePortService(VRDEPortService vrdePortService) {
		this.vrdePortService = vrdePortService;
	}
	

	public VNNodeInfoService getVnNodeInfoService() {
		return vnNodeInfoService;
	}

	public void setVnNodeInfoService(VNNodeInfoService vnNodeInfoService) {
		this.vnNodeInfoService = vnNodeInfoService;
	}
	

	/**
	 * 
	 * @return all vdi uuid
	 */
	public List<String> listVDIID() {
		LinkedList<String> vdis = new LinkedList<String>();
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "sudo VBoxManage list hdds|grep UUID" };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return null;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			if ((line = reader.readLine()) == null)
				System.out.println("xiexie~~");
			while ((line = reader.readLine()) != null) {
				if (line.contains("Usage"))
					continue;
				if (line.contains("Parent")) {
					continue;
				}
				if (isVDIBase(line))
					vdis.add(line.substring(13));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return vdis;
	}

	/**
	 * New Added, return all vm name Fragile
	 * 
	 * @return
	 */
	public List<String> listVMNames() {
		int start = 0;
		int end = 0;
		ArrayList<String> vmNames = new ArrayList<String>();
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage list vms " };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return null;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			while ((line = reader.readLine()) != null) {
				start = line.indexOf("\"");
				if (start < 0)
					continue;
				end = line.indexOf("{");
				line = line.substring(start + 1, end - 2);
				vmNames.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return vmNames;
	}

	/**
	 * New added, return all vmid
	 * 
	 * @return
	 */
	public List<String> listVMIDs() {
		int start = 0;
		int end = 0;
		ArrayList<String> vmids = new ArrayList<String>();
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage list vms " };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return null;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			while ((line = reader.readLine()) != null) {
				start = line.indexOf("{");
				if (start < 0)
					continue;
				end = line.indexOf("}");

				// System.out.println("Start:" + start + "End:" + end);
				line = line.substring(start + 1, end);
				vmids.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return vmids;
	}

	public String getVMMacbyVMID(String VM_UUID) {
		String vmmac = null;
		int start = 0;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage guestproperty get " + VM_UUID + " /VirtualBox/GuestInfo/Net/0/MAC" };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return null;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			while ((line = reader.readLine()) != null) {
				start = line.indexOf(":");
				vmmac = line.substring(start + 2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return vmmac;
	}

	/**
	 * Get VMIP using GuestAddition
	 * 
	 * @param VM_UUID
	 * @return
	 */
	public String getVMIPbyVMID(String VM_UUID) {
		String vmip = null;

		int start = 0;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage guestproperty get " + VM_UUID + " /VirtualBox/GuestInfo/Net/0/V4/IP" };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return null;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.equals("No value set!"))
					return null;
				start = line.indexOf(":");
				vmip = line.substring(start + 2);
				vmip = vmip.trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return vmip;
	}

	public String getVMNamebyVMID(String VM_UUID) {
		String vmname = null;

		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage list vms | grep " + VM_UUID };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return null;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = reader.readLine();
			if (line == null)
				return null;
			int end = line.indexOf("{") - 2;
			vmname = line.substring(1, end);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return vmname;
	}

	public int getCPUsbyVMID(String VM_UUID) {
		int cpus = 0;
		String num = null;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showvminfo " + VM_UUID + "|grep CPUs" };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return cpus;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = reader.readLine();
			if (line == null)
				return cpus;
			num = line.substring(line.length() - 1);
			cpus = Integer.parseInt(num);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return cpus;
	}

	public int getMEMbyVMID(String VM_UUID) {
		int mem = 0;
		String num = null;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showvminfo " + VM_UUID + "|grep Memory" };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return mem;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = reader.readLine();
			if (line == null)
				return mem;
			int start = line.indexOf(":");
			num = line.substring(start + 6, line.length() - 2);
			mem = Integer.parseInt(num);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return mem;
	}

	public int getVRDEportbyVMID(String VM_UUID) {
		int port = 0;
		String num = null;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showvminfo " + VM_UUID + "|grep \"VRDE port\"" };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return port;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = reader.readLine();
			if (line == null)
				return port;
			num = line.substring(line.length() - 4);
			port = Integer.parseInt(num);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return port;
	}

	public String getVMStatebyVMID(String VM_UUID) {
		String state = null;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showvminfo " + VM_UUID + "|grep State" };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return state;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = reader.readLine();
			if (line == null)
				return state;
			int start = line.indexOf(":") + 12;
			state = line.substring(start);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return state;
	}

	public VMInfo getVMStatbyVMID(String id) {
		VMInfo result = new VMInfo();
		result.setIpAddress(getVMIPbyVMID(id));
		result.setMacAddress(getVMMacbyVMID(id));
		result.setHostName(getVMNamebyVMID(id));
		result.setCpu(getCPUsbyVMID(id));
		result.setMemoryInM(getMEMbyVMID(id));
		result.setVmstatus(getVMStatebyVMID(id));
		return result;
	}

	public String getVMIDbyVMMac(String vmmac) {
		String result = null;
		String line = null;
		Process process = null;
		int start = 0;
		List<String> vmids = listVMIDs();
		try {
			for (String vmid : vmids) {
				String[] command = { "/bin/sh", "-c", "VBoxManage guestproperty get " + vmid + " /VirtualBox/GuestInfo/Net/0/MAC" };
				process = Runtime.getRuntime().exec(command);
				if (process == null)
					return null;
				InputStreamReader input = new InputStreamReader(process.getInputStream());
				LineNumberReader reader = new LineNumberReader(input);
				while ((line = reader.readLine()) != null) {
					start = line.indexOf(":");
					result = line.substring(start + 2);
					if (result.equals(vmmac))
						return vmid;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getVMIDbyVMIP(String vmip) {
		String result = null;
		String line = null;
		Process process = null;
		int start = 0;
		List<String> vmids = listVMIDs();
		try {
			for (String vmid : vmids) {
				String[] command = { "/bin/sh", "-c", "VBoxManage guestproperty get " + vmid + " /VirtualBox/GuestInfo/Net/0/V4/IP" };
				process = Runtime.getRuntime().exec(command);
				if (process == null)
					return null;
				InputStreamReader input = new InputStreamReader(process.getInputStream());
				LineNumberReader reader = new LineNumberReader(input);
				while ((line = reader.readLine()) != null) {
					start = line.indexOf(":");
					result = line.substring(start + 2);
					if (result.equals(vmip))
						return vmid;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method is usually invoked to check if a Normal or WriteThrough VDI
	 * is attached to a single VM instance.
	 * 
	 * For other VDI types, it might only return null Use listVMSbyImg instead
	 * 
	 * @param VDI_UUID
	 * @return VM_UUID
	 */
	public String getVMIDbyVDIID(String VDI_UUID) {
		String VM_UUID = null;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showhdinfo " + VDI_UUID + "|grep VMs|grep UUID" };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return null;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			int front;
			while ((line = reader.readLine()) != null) {
				front = line.indexOf("UUID:");
				VM_UUID = line.substring(front + 6, front + 6 + 36);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return VM_UUID;
	}

	/**
	 * @param VM_UUID
	 * @return VDI_UUID
	 */
	public String getVDIIDbyVMID(String VM_UUID) {
		String VDI_UUID = null;
		Process process = null;
		String vmid = formatVMID(VM_UUID);
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showvminfo " + vmid + "|grep .vdi|grep UUID" };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return null;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			int front;
			while ((line = reader.readLine()) != null) {
				front = line.indexOf("UUID:");
				VDI_UUID = line.substring(front + 6, front + 6 + 36);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return VDI_UUID;
	}

	/**
	 * @param VDI_UUID
	 * @return VDI_Type, Normal,Immutable,WriteThrough,Sharable,MultiAttach
	 */
	public String getVDIType(String VDI_UUID) {
		// Default normal
		String type = "Normal";
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showhdinfo " + VDI_UUID + "|grep Type" };
			process = Runtime.getRuntime().exec(command);
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			while ((line = reader.readLine()) != null) {
				int otherIndex;
				int[] typeIndex = new int[4];
				typeIndex[0] = line.indexOf("immutable");
				typeIndex[1] = line.indexOf("writethrough");
				typeIndex[2] = line.indexOf("sharable");
				typeIndex[3] = line.indexOf("multiattach");

				// Check Type
				if (typeIndex[0] > 0) {
					type = "Immutable";
				} else {
					otherIndex = line.indexOf("differencing");
					if (otherIndex > 0)
						type = "children";
				}

				if (typeIndex[1] > 0) {
					type = "Writethrough";
					continue;
				} else {

				}

				if (typeIndex[2] > 0) {
					type = "Sharable";
					continue;
				} else {

				}
				if (typeIndex[3] > 0) {
					type = "Multiattach";
				} else {

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return type;
	}

	public String getLocationbyVMID(String VM_UUID) {
		String location = null;
		Process process = null;
		String vmid = formatVMID(VM_UUID);
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showvminfo " + vmid + "|grep Snapshot|grep folder" };
			process = Runtime.getRuntime().exec(command);
			if (process == null)
				return null;
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			int front1, end1;
			int front2, end2;
			while ((line = reader.readLine()) != null) {
				front1 = line.indexOf("/home");
				end1 = line.indexOf(" VMs");
				front2 = line.indexOf("VMs");
				end2 = line.indexOf("/Snapshots");
				location = line.substring(front1, end1) + "\\ " + line.substring(front2, end2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return location;
	}

	/**
	 * To see if VDI_UUID is registered
	 * 
	 * @param VDI_UUID
	 * @return
	 */
	public boolean isVDIExist(String VDI_UUID) {
		Process p = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showhdinfo " + VDI_UUID };
			p = Runtime.getRuntime().exec(command);
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inRead = new BufferedReader(new InputStreamReader(in));
			if( inRead.readLine() != null ) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
		return false;
	}

	/**
	 * @param VDI_UUID
	 * @return
	 */
	public boolean isVDIBase(String VDI_UUID) {
		boolean isBase = true;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showhdinfo " + VDI_UUID + "|grep Type" };
			process = Runtime.getRuntime().exec(command);
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			int index;
			while ((line = reader.readLine()) != null) {
				index = line.indexOf("differencing");
				if (index > 0)
					isBase = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return isBase;
	}

	/**
	 * @param VDI_UUID
	 * @return
	 */
	public boolean isVDIImmutable(String VDI_UUID) {
		boolean isImmu = false;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showhdinfo " + VDI_UUID + "|grep Type" };
			process = Runtime.getRuntime().exec(command);
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			int index;
			while ((line = reader.readLine()) != null) {
				index = line.indexOf("immutable");
				if (index > 0)
					isImmu = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return isImmu;
	}

	public boolean isVDINormal(String VDI_UUID) {
		Boolean isNormal = false;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showhdinfo " + VDI_UUID + "|grep Type" };
			process = Runtime.getRuntime().exec(command);
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			int index;
			while ((line = reader.readLine()) != null) {
				index = line.indexOf("normal");
				if (index > 0)
					isNormal = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return isNormal;
	}

	public boolean isVDIWritethrough(String VDI_UUID) {
		Boolean isWritethrough = false;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage showhdinfo " + VDI_UUID + "|grep Type" };
			process = Runtime.getRuntime().exec(command);
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			String line = null;
			int index;
			while ((line = reader.readLine()) != null) {
				index = line.indexOf("writethrough");
				if (index > 0)
					isWritethrough = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return isWritethrough;
	}

	/**
	 * Invoke this method before start a VM instance
	 * 
	 * @param VDI_UUID
	 * @return
	 */
	// This need to be modified, cause new vdi type
	public boolean isVDIUsable(String VDI_UUID) {
		boolean isUsable = true;
		// For VDI type Normal & WriteThrough
		if (isVDINormal(VDI_UUID)) {
			String attachVM = getVMIDbyVDIID(VDI_UUID);
			if (attachVM != null)
				isUsable = false;
			return isUsable;
		}
		if (isVDIWritethrough(VDI_UUID)) {
			String attachVM = getVMIDbyVDIID(VDI_UUID);
			if (attachVM != null)
				isUsable = false;
			return isUsable;
		}
		// No limitations for VDI type
		// Sharable or MultiAttach or Immutable
		return isUsable;
	}

	/**
	 * @param VM_UUID
	 * @return
	 */
	public boolean isVMHealthy(String VM_UUID) {
		boolean isVMHealthy = false;
		Process process = null;
		String vmid = formatVMID(VM_UUID);
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage list runningvms |grep " + vmid };
			process = Runtime.getRuntime().exec(command);
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			LineNumberReader reader = new LineNumberReader(input);
			if( reader.readLine() != null) {
				isVMHealthy = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (process != null)
				process.destroy();
		}
		return isVMHealthy;
	}

	/**
	 * Type change into Immutable
	 * 
	 * @param UUID
	 * @return
	 */
	public boolean immuVDI(String VDI_UUID) {
		//String scHome = SCPath.getHome();
		Process p = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage modifyhd " + VDI_UUID + " --type immutable" };
			p = Runtime.getRuntime().exec(command);
			String line = null;
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
			while ((line = errBr.readLine()) != null) {
				System.out.println("[ERROR] " + line);
				return false;
			}

			while ((line = inBr.readLine()) != null) {
				if (line.contains("ERROR") || line.contains("error")) {
					System.out.println("[ERROR] " + line);
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
		return true;
	}

	/**
	 * Type change into Normal
	 * 
	 * @param UUID
	 * @return
	 */
	public boolean normVDI(String VDI_UUID) {
		//String scHome = SCPath.getHome();
		Process p = null;
		try {
			// p = Runtime.getRuntime().exec(
			// scHome +File.separatorChar + "scripts/unshareVDI " + UUID);
			String[] command = { "/bin/sh", "-c", "VBoxManage modifyhd " + VDI_UUID + " --type normal" };
			p = Runtime.getRuntime().exec(command);
			String line = null;
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
			while ((line = errBr.readLine()) != null) {
				System.out.println("[ERROR] " + line);
				return false;
			}
			while ((line = inBr.readLine()) != null) {
				if (line.contains("ERROR") || line.contains("error")) {
					System.out.println("[ERROR] " + line);
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
		return true;
	}

	/**
	 * Type change into Shareable
	 * 
	 * @param UUID
	 * @return
	 */
	public boolean shareVDI(String VDI_UUID) {
		//String scHome = SCPath.getHome();
		Process p = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage modifyhd " + VDI_UUID + " --type shareable" };
			p = Runtime.getRuntime().exec(command);
			String line = null;
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
			while ((line = errBr.readLine()) != null) {
				System.out.println("[ERROR] " + line);
				return false;
			}
			while ((line = inBr.readLine()) != null) {
				if (line.contains("ERROR") || line.contains("error")) {
					System.out.println("[ERROR] " + line);
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
		return true;
	}

	/**
	 * Type change into WriteThrough
	 * 
	 * @param UUID
	 * @return
	 */
	public boolean writethroughVDI(String VDI_UUID) {
		//String scHome = SCPath.getHome();
		Process p = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage modifyhd " + VDI_UUID + " --type writethrough" };
			p = Runtime.getRuntime().exec(command);
			String line = null;
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
			while ((line = errBr.readLine()) != null) {
				System.out.println("[ERROR] " + line);
				return false;
			}
			while ((line = inBr.readLine()) != null) {
				if (line.contains("ERROR") || line.contains("error")) {
					System.out.println("[ERROR] " + line);
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
		return true;
	}

	/**
	 * Type change into MultiAttach
	 * 
	 * @param UUID
	 * @return
	 */
	public boolean multiattachVDI(String VDI_UUID) {
		//String scHome = SCPath.getHome();
		Process p = null;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage modifyhd " + VDI_UUID + " --type multiattach" };
			p = Runtime.getRuntime().exec(command);
			String line = null;
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
			while ((line = errBr.readLine()) != null) {
				System.out.println("[ERROR] " + line);
				return false;
			}
			while ((line = inBr.readLine()) != null) {
				if (line.contains("ERROR") || line.contains("error")) {
					System.out.println("[ERROR] " + line);
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
		return true;
	}

	/**
	 * This method might be abandon in the future Not often invoked by others
	 * 
	 * @param VDI_UUID
	 * @return
	 */
	public boolean unRegisterVDI(String VDI_UUID) {
		Process p = null;
		try {
			String line = null;
			String[] command = { "/bin/sh", "-c", "VBoxManage closemedium disk " + VDI_UUID };
			p = Runtime.getRuntime().exec(command);
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader inRead = new BufferedReader(new InputStreamReader(in));
			BufferedReader errRead = new BufferedReader(new InputStreamReader(err));
			while ((line = inRead.readLine()) != null) {
				System.out.println("[Error] " + line);
				return false;
			}
			while ((line = errRead.readLine()) != null) {
				if (line.contains("ERROR") || line.contains("error") || line.contains("Error")) {
					System.out.println("[Error] " + line);
					return false;
				}
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
		return false;
	}

	/**
	 * @param UUID
	 * @return
	 */
	public boolean deleteVDI(String VDI_UUID) {
		if (!isVDIExist(VDI_UUID)) {
			System.out.println("[INFO] Cannot find VDI_UUID " + VDI_UUID);
			return false;
		}
		Process p = null;
		String line = null;
		Boolean result = false;
		try {
			String[] command = { "/bin/sh", "-c", "VBoxManage closemedium disk " + VDI_UUID + " --delete" };
			p = Runtime.getRuntime().exec(command);
			// Flag: These stream might not be useful
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			BufferedReader errBr = new BufferedReader(new InputStreamReader(err));

			while ((line = errBr.readLine()) != null) {
				System.out.println("[ERROR] " + line);
			}
			while ((line = inBr.readLine()) != null) {
				if (line.contains("ERROR") || line.contains("error")) {
					System.out.println("[ERROR] " + line);
				}
			}
			// Flag: These stream might not be useful
			if (!isVDIExist(VDI_UUID))
				result = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
		return result;
	}

	/**
	 * @param name
	 * @param MemInM
	 * @param cpu
	 * @param vdiinfo
	 * @return
	 */
	public String startVM(String name, int MemInM, int cpu, String VDI_UUID) {
		int vrdeport = 0;
		String vm_uuid = null;
		String scHome = SCPath.getHome();
		//scHome = "/home/jiaohuan/iaas";

		Process p = null;
		String line = null;
		try {
			// Request a unique vrde port
			vrdeport = vrdePortService.leasePort();
			/**
			 * Create, Register and Start a VM
			 */
			p = Runtime.getRuntime().exec(scHome + File.separatorChar + "scripts/startVM " + name + " " + MemInM + " " + cpu + " " + VDI_UUID + " " + vrdeport);
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
			StringBuffer buff = new StringBuffer();
			while ((line = errBr.readLine()) != null) {
				System.out.println("[ERROR][VM Creating] " + line);
				vrdePortService.releasePort(vrdeport);
				// FileOperationImpl.releasePort(scHome, "vrdp.pool",
				// vrdeport);
				return null;
			}
			while ((line = inBr.readLine()) != null) {
				System.out.println("[INFO] " + line);
				if (line.contains("UUID")) {
					vm_uuid = line.substring(5).trim();
				}
				buff.append(line);
			}
			String result = buff.toString();

			if (result.contains("ERROR") || result.contains("error")) {
				System.out.println("[ERROR][VM Creating] " + result);
				// FileOperationImpl.releasePort(scHome, "vrdp.pool",
				// vrdeport);
				vrdePortService.releasePort(vrdeport);
				return null;
			}
			if (vm_uuid != null) {
				vrdePortService.setPortVmid(vm_uuid, vrdeport);
				return vm_uuid + "#" + vrdeport;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();

		}
		return null;
	}

	/**
	 * This function is added by XIEZHIWEI,To start a vm using static ip;
	 * 
	 * @param name
	 * @param MemInM
	 * @param cpu
	 * @param VDI_UUID
	 * @param StaticIp
	 * @return
	 */
	public String startVM(String name, int MemInM, int cpu, String VDI_UUID, String StaticIp, String nodeType) {
		int vrdeport = 0;
		String vm_uuid = null;
		String scHome = SCPath.getHome();
		//scHome = "/home/jiaohuan/iaas";

		Process p = null;
		String line = null;
		try {
			// Request a unique vrde port
			vrdeport = vrdePortService.leasePort();
			/**
			 * Create, Register and Start a VM
			 */
			if (nodeType.equals("MS")) {
				p = Runtime.getRuntime().exec(scHome + File.separatorChar + "scripts/startVMwin7 " + name + " " + MemInM + " " + cpu + " " + VDI_UUID + " " + vrdeport + " " + StaticIp);
			} else {
				p = Runtime.getRuntime().exec(scHome + File.separatorChar + "scripts/startVM " + name + " " + MemInM + " " + cpu + " " + VDI_UUID + " " + vrdeport + " " + StaticIp);
			}
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
			StringBuffer buff = new StringBuffer();
			while ((line = errBr.readLine()) != null) {
				System.out.println("[ERROR][VM Creating] " + line);
				vrdePortService.releasePort(vrdeport);
				// FileOperationImpl.releasePort(scHome, "vrdp.pool",
				// vrdeport);
				return null;
			}
			while ((line = inBr.readLine()) != null) {
				System.out.println("[INFO] " + line);
				if (line.contains("UUID")) {
					vm_uuid = line.substring(5).trim();
				}
				buff.append(line);
			}
			String result = buff.toString();

			if (result.contains("ERROR") || result.contains("error")) {
				System.out.println("[ERROR][VM Creating] " + result);
				// FileOperationImpl.releasePort(scHome, "vrdp.pool",
				// vrdeport);
				vrdePortService.releasePort(vrdeport);
				return null;
			}
			if (vm_uuid != null) {
				vrdePortService.setPortVmid(vm_uuid, vrdeport);
				return vm_uuid + "#" + vrdeport;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();

		}
		return null;
	}

	/**
	 * @param ID
	 */
	public void stopVM(String UUID_VM) {
		String scHome = SCPath.getHome();
		String vmid = formatVMID(UUID_VM);
		vrdePortService.releasePortbyVMID(vmid);

		System.out.println("[INFO][Stop VM UUID: ]" + vmid);
		/* Get VM's attach VDI */
		String vdi_uuid = getVDIIDbyVMID(vmid);
		String location = getLocationbyVMID(vmid);
		Process p = null;
		try {
			// stop VM
			p = Runtime.getRuntime().exec(scHome + File.separatorChar + "scripts/stopVM " + vmid);
			// delete VM
			p.waitFor();
			p = Runtime.getRuntime().exec(scHome + File.separatorChar + "scripts/deleteVM " + vmid + " " + vdi_uuid + " " + location);
			// if Children delete VDI
			p.waitFor();
			if (getVDIType(vdi_uuid).equals("children")) {
				Runtime.getRuntime().exec(scHome + File.separatorChar + "scripts/deleteVDI " + vdi_uuid);
			}
		} catch (Exception e) {
			System.out.println("[ERROR][Runing scripts stopVM deletVM deletVDI]");
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
	}

	/**
	 * @param ID
	 */
	public void pauseVM(String UUID_VM) {
		//String scHome = SCPath.getHome();
		//scHome = "/home/jiaohuan/iaas/";
		String vmid = formatVMID(UUID_VM);
		System.out.println("[INFO][Pause VM UUID: ]" + vmid);
		Process p = null;
		try {

			String[] command = { "/bin/sh", "-c", "VBoxManage controlvm " + vmid + " pause" };
			p = Runtime.getRuntime().exec(command);
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader errBr = new BufferedReader(new InputStreamReader(err));

			String errInfo = null;
			while ((errInfo = errBr.readLine()) != null) {
				System.out.println(errInfo);
			}
		} catch (Exception e) {
			System.out.println("[ERROR] Pausing VM errors");
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
	}

	/**
	 *to poweroff a virtual node in virtual net.
	 */
	public String poweroffVM(String UUID_VM) {
		String poweroff_result = new String();
		poweroff_result = "success";
		//String scHome = SCPath.getHome();
		//scHome = "/home/jiaohuan/iaas/";
		String vmid = formatVMID(UUID_VM);
		System.out.println("[INFO][Poweroff VM UUID: ]" + vmid);
		Process p = null;
		try {

			String[] command = { "/bin/sh", "-c", "VBoxManage controlvm " + vmid + " savestate" };
			p = Runtime.getRuntime().exec(command);
			BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
			BufferedReader errBr = new BufferedReader(new InputStreamReader(err));

			String errInfo = null;
			while ((errInfo = errBr.readLine()) != null) {
				System.out.println(errInfo);
				if (errInfo.contains("error")) {
					return "VM was closed!";
				}
			}
		} catch (Exception e) {
			System.out.println("[ERROR] Poweroff VM errors");
			poweroff_result = "VM was closed!";
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
		return poweroff_result;
	}

	/**
	 * to start a closed virtual node in virtual net.
	 */
	public String startClosedVM(String UUID_VM) {
		String startClosedVM_result = new String();
		startClosedVM_result = "success";
		String scHome = SCPath.getHome();
		//scHome = "/home/jiaohuan/iaas/";
		String vmid = formatVMID(UUID_VM);
		logger.info("[INFO][Start closed VM UUID: ]" + vmid);
		Process p = null;
		try {
			VNNodeInfo vnNodeInfo = vnNodeInfoService.getVNNodeInfo(vmid);
			if(null != vnNodeInfo){
				String nodeType = vnNodeInfo.getNodeType();
				String ipAddr = vnNodeInfo.getIpAddr();
				if(nodeType.equals("MS")){
					p = Runtime.getRuntime().exec(scHome + File.separatorChar + "scripts/startClosedVMwin7 " + vmid + " " + ipAddr);
				}else{
					p = Runtime.getRuntime().exec(scHome + File.separatorChar + "scripts/startClosedVM " + vmid);
				}
				BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
				BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
	
				String errInfo = null;
				while ((errInfo = errBr.readLine()) != null) {
					System.out.println(errInfo);
					if (errInfo.contains("error")) {
						return "VM was running!";
					}
				}
			}else {
				return "VM not found!";
			}
		} catch (Exception e) {
			System.out.println("[ERROR] Start closed VM errors");
			startClosedVM_result = "VM was running!";
			e.printStackTrace();
		} finally {
			if (p != null)
				p.destroy();
		}
		return startClosedVM_result;
	}

	/**
	 * Start a NodeManager by CloudManager
	 */
	public void HMReportIP() {
		String scHome = SCPath.getHome();
		try {
			Runtime.getRuntime().exec(scHome + File.separatorChar + "HMReportIP");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Not implemented
	 * 
	 * This method returns all VM instance attached to VDI regardless of VDI
	 * type
	 * 
	 * @param VDI_UUID
	 * @return
	 */
	public List<String> listVMSbyImg(String VDI_UUID) {
		ArrayList<String> VMids = new ArrayList<String>();

		return VMids;
	}

	/**
	 * Added by chenzhuo
	 * 
	 * @param args
	 */

	/**
	 * This method is used to extract true vm_uuid from "vmid#vrdeport"
	 * 
	 * @param UUID_VM
	 * @return
	 */
	public String formatVMID(String UUID_VM) {
		if (!UUID_VM.contains("#"))
			return UUID_VM;
		int start = UUID_VM.indexOf("#");
		String vmid = UUID_VM.substring(0, start);
		return vmid;
	}

	public int formatVrdeport(String UUID_VM) {
		int port = 0;
		if (!UUID_VM.contains("#"))
			return port; // default vrde port 0 == 3389
		try {
			int start = UUID_VM.indexOf("#");
			port = Integer.parseInt(UUID_VM.substring(start + 1));
		} catch (NumberFormatException e) {
			System.out.println("[ERROR][Format vmid][Cannot get vrdeport]");
			e.printStackTrace();
		}
		return port;
	}

	public static void main(String[] args) {
		VMOperationManager vmom = new VBoxManager();
		//String vmid = "b6a66ac6-6bf8-41c5-8b37-f7601c3d0bf2";
		//String vdiid = "c15b2c85-416c-4c5a-b199-356fd707209d";
		// System.out.println(getCPUsbyVMID("7f200ab5-5d5b-4d68-b52e-0e6de805bbfc"));
		// System.out.println(getVMStatbyVMID(vmid));
		String snapshot = "Snapshot folder: /home/jiaohuan/VirtualBox VMs/node000001/Snapshots";
		int front1 = snapshot.indexOf("/home");
		int end1 = snapshot.indexOf(" VMs");
		int front2 = snapshot.indexOf("VMs");
		int end2 = snapshot.indexOf("/Snapshots");
		System.out.println(snapshot.substring(front1, end1) + "\\ " + snapshot.substring(front2, end2));
		// System.out.println(getVRDEportbyVMID("a1240224-0917-48f0-8078-d675b805a370"));
		vmom.listVDIID();
	}
}
