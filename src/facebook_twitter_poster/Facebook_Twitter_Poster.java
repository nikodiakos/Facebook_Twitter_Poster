package facebook_twitter_poster;

import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author ND
 */
public class Facebook_Twitter_Poster {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        
  
    }
     public static Map<String, String> fb_auth(String email,String pass) throws Exception{
        	
        	Connection.Response loginForm = Jsoup.connect("http://www.facebook.com/index.php")
                    .method(Connection.Method.GET)
                    .execute();
                String prelogin = loginForm.parse().toString().substring(loginForm.parse().toString().lastIndexOf("datr")).toString();
                String datr = prelogin.substring(prelogin.indexOf(",")+1,prelogin.indexOf(",", prelogin.indexOf(",") + 1)).replace("\"", "");
                
               	   
               	Response response = Jsoup.connect("https://www.facebook.com/login.php")
               		.method(Method.POST)
                        .data("email", email)
                 	.data("pass", pass)
                        .cookie("datr", datr)
                        .execute();
                        Map<String, String> cookies = response.cookies();
                        return cookies;
        }
     public static boolean fb_isConnected(Map<String, String> cookies) throws Exception{
         Document document = Jsoup.connect("https://www.facebook.com/")
       			    .cookies(cookies)
       			    .get();
         
       		
                
                try {
                    String info = document.select("div#mainContainer").first().text();
                    return true;
                    
                }catch (NullPointerException e){
                    return false;
                }
         
     }
     public static Map<String, String> tw_auth(String email,String pass) throws Exception{
        	
        	Connection.Response loginForm = Jsoup.connect("http://www.twitter.com/login")
                    .method(Connection.Method.GET)
                    .execute();
                String authenticity_token = loginForm.parse().select("input[name=authenticity_token]").attr("value");
               
               	   
               	Response response = Jsoup.connect("https://twitter.com/sessions")
               		.method(Method.POST)
                        .userAgent("Chrome")
                        .header("Host", "twitter.com")
                        .header("Content-Length", "166")
                        .header("Origin", "https://twitter.com")
                        .header("Upgrade-Insecure-Requests", "1")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept", "text/html")
                        .header("Referer", "https://twitter.com/login")
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .header("Accept-Language", "en;")
                        .data("session[username_or_email]", email)
                 	.data("session[password]", pass)
                        .data("authenticity_token", authenticity_token)
                        .cookies(loginForm.cookies())
                        .execute();
                        Map<String, String> cookies = response.cookies();
                        //System.out.println(response.parse());
                        return cookies;
        }
     public static boolean tw_isConnected(Map<String, String> cookies) throws Exception{
         Document document = Jsoup.connect("https://www.twitter.com/")
       			    .cookies(cookies)
       			    .get();
         
       		
                
                if(document.title().equals("Twitter")){
                    return true;
                }else {
                    return false;
                }
         
     }
}
