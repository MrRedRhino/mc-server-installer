package org.pipeman.mcserverdownloader.util.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requests {
    public static String get(String url) throws IOException {
        URL urlAsUrl = new URL(url);

        HttpURLConnection httpConnection = (HttpURLConnection) (urlAsUrl.openConnection());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpConnection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        httpConnection.disconnect();
        return content.toString();
    }

    public static void downloadFile(URL url,String filename, boolean verbose) throws IOException {
        HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
        long fileSize = httpConnection.getContentLength();
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        InputStream is = url.openStream();

        int nRead;
        byte[] data = new byte[16384];
        float bytes = 0;
        int counter = 0;

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            bytes += nRead;
            fileOutputStream.write(data, 0, nRead);
            if (counter++ >= 10 && verbose) {
                int progress = (int) ((bytes / fileSize) * 10);
                String msg = "Downloading server.jar [" +
                        repeatString(progress, "=") +
                        repeatString(10 - progress, "-") +
                        "] " + progress * 10 + "%\r";
                System.out.print(msg);
                counter = 0;
            }
        }
        fileOutputStream.close();
        httpConnection.disconnect();
    }

    private static String repeatString(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }

}
