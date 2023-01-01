package org.pipeman.mcserverdownloader.util.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Requests {
    public static String get(String url) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Response.connectTo(url).transferBodyTo(out);
        return out.toString();
    }

    public static void downloadFile(URL url, String filename, String displayName, boolean verbose) throws IOException {
        Response response = Response.connectTo(url);

        Path path = Paths.get(filename);
        Files.createDirectories(path.getParent());

        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            try (InputStream in = response.body()) {
                long fileSize = response.contentLength();

                int nRead;
                byte[] data = new byte[16384];
                float bytes = 0;
                int counter = 0;

                while ((nRead = in.read(data, 0, data.length)) != -1) {
                    bytes += nRead;
                    fileOutputStream.write(data, 0, nRead);

                    if (fileSize < 1) {
                        System.out.print("Downloading " + displayName + ". Server returned an invalid filesize.\r");
                    } else {
                        if (counter++ >= 10 && verbose) {
                            int progress = (int) ((bytes / fileSize) * 10);
                            sendUpdateMessage(displayName, progress);
                            counter = 0;
                        }
                    }
                }
                sendUpdateMessage(displayName, 10);
            }
        }
    }

    private static void sendUpdateMessage(String filename, int progress) {
        String msg = "Downloading " + filename + " [" +
                     repeatString(progress, "=") +
                     repeatString(10 - progress, "-") +
                     "] " + progress * 10 + "%\r";
        System.out.print(msg);
    }

    private static String repeatString(int count, String with) {
        StringBuilder builder = new StringBuilder(with.length() * count);
        for (int i = 0; i < count; i++) builder.append(with);
        return builder.toString();
    }

    private static class Response {
        private final InputStream body;
        private final long contentLength;

        private Response(InputStream body, long contentLength) {
            this.body = body;
            this.contentLength = contentLength;
        }

        public InputStream body() {
            return body;
        }

        public long contentLength() {
            return contentLength;
        }

        public static Response connectTo(String url) throws IOException {
            return connectTo(new URL(url));
        }

        public static Response connectTo(URL url) throws IOException {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream stream = conn.getResponseCode() > 200 ? conn.getErrorStream() : conn.getInputStream();
            return new Response(stream, conn.getContentLengthLong());
        }

        public void transferBodyTo(OutputStream out) throws IOException {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = body.read(buffer, 0, 8192)) >= 0) {
                out.write(buffer, 0, read);
            }
        }
    }
}
