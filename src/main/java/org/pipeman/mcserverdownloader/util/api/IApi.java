package org.pipeman.mcserverdownloader.util.api;

import org.pipeman.mcserverdownloader.util.ServerType;
import org.pipeman.mcserverdownloader.util.api.impl.*;

import java.util.ArrayList;

public interface IApi {
    ArrayList<String> getVersions();

    DownloadInfo getDownloadInfo(String version);

    static IApi of(ServerType type) {
        switch (type) {
            case PAPER: {
                return new PaperMCAPI();
            }
            case FABRIC: {
                return new FabricAPI();
            }
            case PURPUR: {
                return new PurpurAPI();
            }
            case VANILLA: {
                return new VanillaAPI();
            }
            case VELOCITY: {
                return new VelocityAPI();
            }
            default: {
                return null;
            }
        }
    }
}
