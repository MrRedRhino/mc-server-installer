package org.pipeman.mcserverdownloader.util.api;

import java.net.URL;

public class DownloadInfo {
    private final URL download;
    private final String fileName;

    public DownloadInfo(URL download, String fileName) {
        this.download = download;
        this.fileName = fileName;
    }


    public String fileName() {
        return fileName;
    }

    public URL download() {
        return download;
    }
}
