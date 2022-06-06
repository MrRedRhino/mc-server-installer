package org.pipeman.mcserverdownloader.util.api;

import java.util.ArrayList;

public interface IApi {
    ArrayList<String> getVersions();

    DownloadInfo getDownloadInfo(String version);
}
