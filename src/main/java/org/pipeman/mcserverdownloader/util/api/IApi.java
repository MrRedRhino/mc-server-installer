package org.pipeman.mcserverdownloader.util.api;

import java.net.URL;
import java.util.ArrayList;

public interface IApi {
    ArrayList<String> getVersions();

    URL getDownloadURL(String version);
}
