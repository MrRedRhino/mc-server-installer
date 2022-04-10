package org.pipeman.mcserverdownloader.util.api;

import org.pipeman.mcserverdownloader.util.ServerType;

public class ApiManager {


    public static IApi createNewApiInstance(ServerType type) {
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
