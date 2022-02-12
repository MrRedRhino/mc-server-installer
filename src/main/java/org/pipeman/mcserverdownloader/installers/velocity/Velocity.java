package org.pipeman.mcserverdownloader.installers.velocity;

import org.pipeman.mcserverdownloader.util.StartScripts;
import org.pipeman.mcserverdownloader.util.TerminalUtil;
import org.pipeman.mcserverdownloader.util.api.Requests;
import org.pipeman.mcserverdownloader.util.api.VelocityAPI;

import static org.pipeman.mcserverdownloader.util.TerminalUtil.Colors;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Velocity {
    private static void downloadJar() throws IOException {
        Requests.downloadFile(
                new URL("https://papermc.io/api/v2/projects/velocity/"), "velocity.jar", true);
    }

    public static void installVelocity() throws IOException {
        VelocityConfig cfg = null;
        boolean makeStartScript = false;
        String javaPath = "";
        String velocityVersion = "";

        System.out.println("Choose the version to install:");
        ArrayList<String> versions = VelocityAPI.getVersions();
        int i = 0;
        for (String v : versions) {
            i++;
            System.out.println("  " + i + ": " + v);
        }
        System.out.print("> ");

        int sel = 0;
        while (sel < 1 || sel > versions.size()) {
            sel = TerminalUtil.readInt();
        }
        velocityVersion = versions.get(sel - 1);

        System.out.print("Create a ready-to-use velocity.toml file? (y/n) ");
        if (TerminalUtil.readYesNo()) {
            cfg = new VelocityConfig();
            System.out.print("Config: visible max player count: ");
            cfg.slotCount = TerminalUtil.readInt();
        }
        System.out.print("Create start.sh file? (y/n) ");
        if (TerminalUtil.readYesNo()) {
            makeStartScript = true;
            System.out.print("Enter the path to your java installation: ");
            javaPath = TerminalUtil.readLine();
        }

        String summary = "==== This will: ====";
        summary += "\n  -Download velocity (" + velocityVersion + ") into: " + System.getProperty("user.dir");
        if (makeStartScript) {
            summary += "\n  -Create a start-script";
        }
        if (cfg != null) {
            summary += "\n  -Create a velocity config-file:";
            summary += "\n    -Slots: " + cfg.slotCount;
            summary += "\n    -Forwarding secret: " + cfg.key;
        }
        summary += "\nInstall? (y/n) ";

        System.out.print(summary);

        if (TerminalUtil.readYesNo()) {
            try {
                Requests.downloadFile(VelocityAPI.getDownloadURL(velocityVersion), "velocity.jar", true);
                System.out.println("Download done.");
                if (cfg != null) {
                    cfg.writeConfig();
                }

                if (makeStartScript) {
                    StartScripts.makeVelocityStartScript(javaPath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Colors.GREEN + Colors.BOLD + "Installation done!");
        } else {
            System.out.println(Colors.WARNING + "Aborting.");
        }
    }
}
