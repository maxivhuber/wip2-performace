package Performancetester;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

public class Videohandler implements Runnable {

    private URLConnection conn;

    public Videohandler(URLConnection conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        //storage for segment URL's
        ArrayList<String> segments = new ArrayList<>();

        //get all segments from video
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.matches("\\/video\\/segment\\?videoid=\\d+\\w+\\&quality=\\d+x\\d+&part=\\d+&codec=mp4")) {
                    segments.add("http://" + "localhost/" + line);
                }
            }
        } catch (IOException er) {
            er.printStackTrace();
        }

        Iterator<String> iter = segments.iterator();
        //watch video
        while (iter.hasNext()) {
            URL req;
            URLConnection segment;
            try {
                req = new URL(iter.next());
                segment = req.openConnection();
                spammer(segment);
            } catch (IOException er) {
                er.printStackTrace();
            }
            iter.remove();


        }
        System.out.println("finished thread" + Thread.currentThread().getName());
    }

    private final void spammer(URLConnection segment) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(segment.getInputStream())) {
            int tmp;
            while ((tmp = in.read()) != -1);
        }
    }
}
