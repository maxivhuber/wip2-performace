package Performancetester;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tester {

    public static void main(String... args) throws IOException {
        //process request with 5 Threads
        ExecutorService exec = Executors.newFixedThreadPool(5);

        //watch all videos from commandline
        for (String arg : args) {
            String url = "http://localhost/video/mediaplaylist?videoid=" + arg + "&quality=1920x1080&codec=mp4";
            try {
                URL target = new URL(url);
                URLConnection conn = target.openConnection();
                //execute task
                exec.execute(new Videohandler(conn));

            } catch (MalformedURLException er) {
                throw new MalformedURLException("Bad URL");
            } catch (IOException er) {
                throw new IOException("Connection cannot be established");
            }
        }
        //shutdown after videos are watched
        exec.shutdown();

    }
}
