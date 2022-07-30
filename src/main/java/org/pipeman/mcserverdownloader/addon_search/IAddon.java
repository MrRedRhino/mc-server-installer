package org.pipeman.mcserverdownloader.addon_search;

import org.pipeman.mcserverdownloader.util.api.DownloadInfo;

import java.util.List;

public abstract class IAddon {

    public abstract String name();

    public abstract String description();

    public abstract String author();

    public abstract List<DownloadInfo> versions();
}
