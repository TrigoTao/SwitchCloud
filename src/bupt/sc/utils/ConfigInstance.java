package bupt.sc.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

public class ConfigInstance{
	private final static String confRoot = File.separator + "conf";
	private static CloudConfig cloudConfig = null;
	
	public static CloudConfig getCloudConfig() throws FileNotFoundException{
		if(null == cloudConfig){
			Yaml yaml = new Yaml(); 
			//System.out.println(ConfigInstance.class.getResource(confRoot+"/ini.yaml"));
			InputStream input = ConfigInstance.class.getResourceAsStream(confRoot+File.separator+"ini.yaml");
			cloudConfig = yaml.loadAs( input, CloudConfig.class );
		}
		return cloudConfig;
	}
	
	public static void main(String args[]){
		try {
			CloudConfig cc = ConfigInstance.getCloudConfig();
			System.out.println(cc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
