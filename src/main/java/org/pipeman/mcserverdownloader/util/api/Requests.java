package org.pipeman.mcserverdownloader.util.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Requests {
    static OkHttpClient client = new OkHttpClient();

    public static String get(String url) throws IOException {
        Request r = new Request.Builder()
                .url(new URL(url))
                .build();

        try (Response response = client.newCall(r).execute()) {
            ResponseBody body = response.body();
            return body == null ? "" : body.string();
        }
    }

    public static void downloadFile(URL url, String filename, String displayName, boolean verbose) throws IOException {
        HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
        long fileSize = httpConnection.getContentLength();
        boolean hasSentThatFileSizeIsInvalid = false;

        Path path = Paths.get(filename);
        Files.createDirectories(path.getParent());

        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        InputStream is = url.openStream();

        int nRead;
        byte[] data = new byte[16384];
        float bytes = 0;
        int counter = 0;

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            bytes += nRead;
            fileOutputStream.write(data, 0, nRead);

            if (fileSize < 1) {
                if (!hasSentThatFileSizeIsInvalid) {
                    System.out.print("Downloading " + displayName + "; Content length invalid.\r");
                    hasSentThatFileSizeIsInvalid = true;
                }
            } else {
                if (counter++ >= 10 && verbose) {
                    int progress = (int) ((bytes / fileSize) * 10);
                    sendUpdateMessage(displayName, progress);
                    counter = 0;
                }
            }
        }
        sendUpdateMessage(displayName, 10);

        fileOutputStream.close();
        httpConnection.disconnect();
    }

    private static void sendUpdateMessage(String filename, int progress) {
        String msg = "Downloading " + filename + " [" +
                     repeatString(progress, "=") +
                     repeatString(10 - progress, "-") +
                     "] " + progress * 10 + "%\r";
        System.out.print(msg);
    }

    private static String repeatString(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }
}
