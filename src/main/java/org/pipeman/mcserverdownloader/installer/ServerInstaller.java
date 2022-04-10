package org.pipeman.mcserverdownloader.installer;

import org.pipeman.mcserverdownloader.util.ServerType;
import org.pipeman.mcserverdownloader.util.Files;
import org.pipeman.mcserverdownloader.util.TerminalUtil;
import org.pipeman.mcserverdownloader.util.api.ApiManager;
import org.pipeman.mcserverdownloader.util.api.IApi;
import org.pipeman.mcserverdownloader.Requests;

import java.io.IOException;
import java.util.ArrayList;

public class ServerInstaller {
    public static void installServer(ServerType serverType) throws IOException {
        if (serverType == ServerType.VELOCITY) {
            System.out.println("Velocity is not supported yet.");
            return;
        }

        InstallerSettings settings = new InstallerSettings();

        IApi api = ApiManager.createNewApiInstance(serverType);
        if (api == null) {
            System.out.println(serverType.toString().toLowerCase() + " is not supported yet.");
            return;
        }

        System.out.println("Choose the version to install:");
        System.out.print(TerminalUtil.Colors.GREEN + "Fetching available versions..." + TerminalUtil.Colors.RESET + "\r");

        ArrayList<String> versions = api.getVersions();
        settings.version = versions.get(TerminalUtil.readRange(versions) - 1);

        // Manage install directory
//        System.out.print("Enter the directory to install the server in "
//                + "or \".\" to install it in the directory this script runs in\n> ");
//        settings.installDirectory = TerminalUtil.readLine();
//        if (settings.installDirectory == null || settings.installDirectory.equals(".")) {
//            settings.installDirectory = System.getProperty("user.dir") + "/";
//        }
//        if (!settings.installDirectory.endsWith("/")) {
//            settings.installDirectory += "/";
//        }

        settings.installDirectory = System.getProperty("user.dir") + "/";

        System.out.println("Do you agree to Mojang's eula (https://account.mojang.com/documents/minecraft_eula)?");
        System.out.print("If not, you will have to agree after the first launch of the server. (y/n) ");
        settings.eula = TerminalUtil.readYesNo();


        System.out.print("Create start.sh file? (y/n) ");
        if (TerminalUtil.readYesNo()) {
            settings.startScriptContent = "";
            System.out.print("Start.sh: Enter the path to your java installation: ");
            settings.startScriptContent += TerminalUtil.readLine();
            // TODO Fancy RAM options
            System.out.print("Start.sh: Should the server start headless? (y/n) ");
            settings.startScriptContent
                    += " -jar " + serverType.executableJarName + (TerminalUtil.readYesNo() ? " nogui" : "");
        }
        System.out.println(settings.generateSummary(serverType));
        System.out.print("Install? (y/n) ");
        if (TerminalUtil.readYesNo()) {
            try {

                // Download server jars
                Requests.downloadFile(api.getDownloadURL(settings.version),
                        settings.installDirectory + serverType.executableJarName, true);
                System.out.println("Download done.");

                // create eula file
                if (settings.eula) {
                    Files.makeFile("eula.txt", "eula=true");
                }

                // generate start script
                if (settings.startScriptContent != null) {
                    Files.makeFile("start.sh", settings.startScriptContent);
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
