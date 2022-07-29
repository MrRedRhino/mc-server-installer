package org.pipeman.mcserverdownloader.util.api;

import java.io.IOException;
import java.net.URL;

public class DownloadInfo {
    private final URL download;
    private final String fileName;
    private String title = "";

    public DownloadInfo(URL download, String fileName) {
        this.download = download;
        this.fileName = fileName;
    }

    public DownloadInfo(URL download, String fileName, String title) {
        this.download = download;
        this.fileName = fileName;
        this.title = title;
    }


    public String fileName() {
        return fileName;
    }

    public URL url() {
        return download;
    }

    public void download() throws IOException {
        Requests.downloadFile(url(), fileName(), fileName(), true);
    }

    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
               "download=" + download +
               ", fileName='" + fileName + '\'' +
               ", title='" + title + '\'' +
               '}';
    }
}
