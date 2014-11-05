package bupt.sc.heartbeat.pcInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** return the ip of localhost by browsering http://www.ip138.com/ip2city.asp */
/**
 * When browser http://www.ip138.com/ip2city.asp, localhost ip address is returned
 * @author Administrator
 *
 */
public class ExternalAddressFetcher {
	
    private String externalIpProviderUrl;
    private String myExternalIpAddress;

    public ExternalAddressFetcher(String externalIpProviderUrl) {
        this.externalIpProviderUrl = externalIpProviderUrl;

        String returnedhtml = fetchExternalIpProviderHTML(this.externalIpProviderUrl);
        
        parse(returnedhtml);
    }

    /**
     * @param externalIpProviderUrl
     * @return
     */
    private String fetchExternalIpProviderHTML(String externalIpProviderUrl) {
        InputStream in = null;
        
        HttpURLConnection httpConn = null;

        try {
            URL url = new URL(externalIpProviderUrl);
            httpConn = (HttpURLConnection) url.openConnection();
                        
            HttpURLConnection.setFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");

            in = httpConn.getInputStream();
            byte[] bytes=new byte[1024];
            
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                   && (numRead=in.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
                 
            String receivedString=new String(bytes,"UTF-8");
           
            return receivedString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                httpConn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * @param html
     */
    private void parse(String html){
        Pattern pattern=Pattern.compile("(\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})", Pattern.CASE_INSENSITIVE);    
        Matcher matcher=pattern.matcher(html);        
        while(matcher.find()){
            myExternalIpAddress=matcher.group(0);
        }    
    }    

    /**
     * @return
     */
    public String getMyExternalIpAddress() {
        return myExternalIpAddress;
    }
    
    public static void main(String[] args){
        ExternalAddressFetcher fetcher=new ExternalAddressFetcher("http://www.ip138.com/ip2city.asp");
        
        System.out.println(fetcher.getMyExternalIpAddress());
    }
}
