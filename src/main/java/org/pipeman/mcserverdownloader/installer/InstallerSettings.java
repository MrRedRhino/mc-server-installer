package org.pipeman.mcserverdownloader.installer;

import org.pipeman.mcserverdownloader.util.ServerType;

public class InstallerSettings {
    public boolean eula = false;
    public String startScriptContent;
    public boolean noGui;
    public String version;
    public String installationDirectory;

    public String generateSummary(ServerType serverType) {
        String out = "==== This program will: ====";
        out += "\n  - Download " + serverType + " (" + version + ") into: " + installationDirectory;
        if (eula) out += "\n  - Automatically agree to Mojang's eula";
        if (startScriptContent != null) {
            out += "\n  - Create a start-script";
            out += "\n    - Start-command: " +
                    startScriptContent.replaceAll("\n", " ") + serverType.executableJarName;
            out += noGui ? " nogui" : "";
        }

        return out;
    }
}
