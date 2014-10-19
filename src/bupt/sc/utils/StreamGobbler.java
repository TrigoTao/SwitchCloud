package bupt.sc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.Logger;

public class StreamGobbler extends Thread{
	private InputStream is;
	private String type;
	private Logger logger;
	private StreamGobblerLineDealer lineDealer;
	private BlockingQueue<String> resultQueue;

	public StreamGobbler(InputStream is, String type){
		this(is, type, null);
	}
	
	public StreamGobbler(InputStream is, String type, Logger logger){
		this.is = is;	this.type = type; this.logger = logger;
	}

	public void setLineDealer(StreamGobblerLineDealer lineDealer) {
		this.lineDealer = lineDealer;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public StreamGobbler setResultQueue(BlockingQueue<String> resultQueue) {
		this.resultQueue = resultQueue;
		return this;
	}

	public BlockingQueue<String> getResultQueue() {
		return resultQueue;
	}

	public void run(){
		try(InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);) {
			String line = null;
			while( (line = br.readLine()) != null ){
				if(lineDealer != null){
					lineDealer.deal(line,resultQueue);
				}
				if(null != logger){
					switch(type){
					case "err":
						logger.error(line);
						break;
					case "info":
						logger.info(line);
						break;
					default:
						logger.info("[unknown Stream Type] " + line);
					}
				}else{
					System.out.println(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
