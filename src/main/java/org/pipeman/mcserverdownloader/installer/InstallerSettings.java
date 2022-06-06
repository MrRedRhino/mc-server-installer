package org.pipeman.mcserverdownloader.installer;

import org.pipeman.mcserverdownloader.util.ServerType;

public class InstallerSettings {
    public boolean eula = false;
    public String startScriptContent;
    public boolean noGui;
    public String version;
    public String installDirectory;

    public String generateSummary(ServerType serverType) {
        String out = "==== This will: ====";
        out += "\n  - Download " + serverType + " (" + version + ") into: " + installDirectory;
        if (eula) out += "\n  - Automatically agree to Mojang's eula";
        if (startScriptContent != null) {
            out += "\n  - Create a start-script";
            out += "\n    - Start-command: " +
                    startScriptContent.replaceAll("\n", " ") + serverType.executableJarName;
        }

        return out;
    }
}
