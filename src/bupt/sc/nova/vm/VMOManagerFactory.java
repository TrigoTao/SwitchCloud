package bupt.sc.nova.vm;

import java.io.FileNotFoundException;

import bupt.sc.utils.CloudConfig;
import bupt.sc.utils.ConfigInstance;

public abstract class VMOManagerFactory {
//	private static VMOperationManager vmOperationManager;
//	
//	static {
//		try {
//			CloudConfig cc = ConfigInstance.getCloudConfig();
//			if( cc.getVmType().equals("vBox") ){
//				vmOperationManager = new VBoxManager();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static VMOperationManager getVMOperationManager(){
//		return vmOperationManager;
//	}
//	
//	public static void main(String[] args) {
//		VMOManagerFactory.getVMOperationManager();
//	}

	public VMOperationManager getVmOperationManager(){
		VMOperationManager vmOperationManager = null;
		try {
			CloudConfig cc = ConfigInstance.getCloudConfig();
			switch( cc.getVmType()){
			case "vBox":
				vmOperationManager = getVBoxManager();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return vmOperationManager;
	}
	
	protected abstract VBoxManager getVBoxManager();
}
