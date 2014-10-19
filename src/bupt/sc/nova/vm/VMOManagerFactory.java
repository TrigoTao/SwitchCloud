package bupt.sc.nova.vm;

import java.io.FileNotFoundException;

import bupt.sc.utils.CloudConfig;
import bupt.sc.utils.ConfigInstance;

public abstract class VMOManagerFactory {

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
