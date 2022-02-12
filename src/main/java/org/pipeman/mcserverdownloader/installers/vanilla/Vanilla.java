package org.pipeman.mcserverdownloader.installers.vanilla;

import org.pipeman.mcserverdownloader.util.StartScripts;
import org.pipeman.mcserverdownloader.util.TerminalUtil;
import org.pipeman.mcserverdownloader.util.api.Requests;
import org.pipeman.mcserverdownloader.util.api.VanillaAPI;
import org.pipeman.mcserverdownloader.util.api.VanillaVersion;

import java.io.IOException;
import java.util.ArrayList;

public class Vanilla {
    public static void installVanilla() throws IOException {
        VanillaVersion version;
        boolean eula;

        boolean makeStartScript = false;
        String javaPath = "";
        String javaArgs = "";
        boolean headless = false;

        System.out.println("Choose the version to install:");
        ArrayList<VanillaVersion> versions = VanillaAPI.getVersions();
        int i = 0;
        for (VanillaVersion v : versions) {
            i++;
            System.out.println("  " + i + ": " + v.id);
        }
        System.out.print("> ");

        int sel = 0;
        while (sel < 1 || sel > versions.size()) {
            sel = TerminalUtil.readInt();
        }
        version = versions.get(sel - 1);

        System.out.println("Do you agree to Mojang's eula (https://account.mojang.com/documents/minecraft_eula)?");
        System.out.print("If not, you will have to agree after the first launch of the server. (y/n) ");
        eula = TerminalUtil.readYesNo();

        System.out.print("Create start.sh file? (y/n) ");
        if (TerminalUtil.readYesNo()) {
            makeStartScript = true;
            System.out.print("Start.sh: Enter the path to your java installation: ");
            javaPath = TerminalUtil.readLine();
            System.out.print("Start.sh: Enter java arguments: ");
            javaArgs = TerminalUtil.readLine();
            System.out.print("Start.sh: Should the server start headless? (y/n) ");
            headless = TerminalUtil.readYesNo();
        }

        String summary = "==== This will: ====";
        summary += "\n  -Download vanilla (" + version.id + ") into: " + System.getProperty("user.dir");
        if (makeStartScript) {
            summary += "\n  -Create a start-script";
            summary += "\n    -Start-command: " + javaPath + " " + javaArgs + " -jar server.jar " + (headless ? "nogui" : "");
        }

        summary += "\nInstall? (y/n) ";

        System.out.print(summary);

        if (TerminalUtil.readYesNo()) {
            try {
                Requests.downloadFile(VanillaAPI.getDownloadURL(version), "server.jar", true);
                System.out.println("Download done.");
                if (eula) {
                    StartScripts.makeEulaFile();
                }
                if (makeStartScript) {
                    StartScripts.makeVanillaStartScript(javaPath, javaArgs, headless);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(TerminalUtil.Colors.GREEN + TerminalUtil.Colors.BOLD + "Installation done!");
        } else {
            System.out.println(TerminalUtil.Colors.WARNING + "Aborting.");
        }
    }
}
