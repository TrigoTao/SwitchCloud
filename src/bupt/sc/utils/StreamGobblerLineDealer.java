package bupt.sc.utils;

import java.util.concurrent.BlockingQueue;

public interface StreamGobblerLineDealer {
	public void deal(String line, BlockingQueue<String> resultQueue);
}
