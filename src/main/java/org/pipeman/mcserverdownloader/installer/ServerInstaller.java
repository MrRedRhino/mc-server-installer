package org.pipeman.mcserverdownloader.installer;

import org.pipeman.mcserverdownloader.util.ServerType;
import org.pipeman.mcserverdownloader.util.Files;
import org.pipeman.mcserverdownloader.util.TerminalUtil;
import org.pipeman.mcserverdownloader.util.api.DownloadInfo;
import org.pipeman.mcserverdownloader.util.api.IApi;
import org.pipeman.mcserverdownloader.util.api.Requests;

import java.util.ArrayList;

public class ServerInstaller {
    public static void installServer(ServerType serverType) {
        InstallerSettings settings = new InstallerSettings();

        IApi api = IApi.of(serverType);
        if (api == null) {
            System.out.println("... How");
            return;
        }

        System.out.println("Choose the version to install:");
        System.out.print(TerminalUtil.Colors.GREEN + "Fetching available versions..." + TerminalUtil.Colors.RESET + "\r");

        ArrayList<String> versions = api.getVersions();
        System.out.print("                                   \r");
        settings.version = versions.get(TerminalUtil.readRange(versions) - 1);

        settings.installDirectory = TerminalUtil.getInstallDir(serverType == ServerType.VELOCITY ? "proxy" : "server");

        if (serverType != ServerType.VELOCITY) {
            System.out.println("Do you agree to Mojang's eula? (https://account.mojang.com/documents/minecraft_eula)");
            System.out.print("If not, you will have to agree after the first launch of the server. (y/n) ");
            settings.eula = TerminalUtil.readYesNo();
        }

        System.out.print("Create start.sh file? (y/n) ");
        if (TerminalUtil.readYesNo()) {
            settings.startScriptContent = "cd \"${0%/*}\"\n";
            System.out.print("Start.sh: Enter the command to start your Java VM (Leave empty to use 'java'): ");
            String line = TerminalUtil.readLine();
            settings.startScriptContent += (line == null || line.isEmpty() ? "java" : line) + " ";

            // TODO Fancy RAM options
            if (serverType != ServerType.VELOCITY) {
                System.out.print("Start.sh: Should the server start without a gui? (y/n) ");
                settings.noGui = TerminalUtil.readYesNo();

                // how much ram? use aikar?
                System.out.println("Start.sh: How much RAM should be allocated to your server in MB (e.g. 2048 (2GB), 4096 (4GB))? ");
                int ram = TerminalUtil.readInt();
                boolean aikarFlags = false;
                if (AikarFlags.isSupportedBy(serverType)) {
                    System.out.print("Start.sh: Use Aikar flags? (y/n)");
                    if (TerminalUtil.readYesNo()) aikarFlags = true;
                }
                if (aikarFlags) settings.startScriptContent += AikarFlags.createFlags(ram);
                else settings.startScriptContent += "-Xms" + ram + "M -Xmx" + ram + "M";
            }

            settings.startScriptContent += " -jar ";
        }

        // TODO velocity and normal server settings

        System.out.println(settings.generateSummary(serverType));
        System.out.print("Install? (y/n) ");
        if (TerminalUtil.readYesNo()) {
            try {
                // Download server jar
                DownloadInfo dlInfo = api.getDownloadInfo(settings.version);
                Requests.downloadFile(dlInfo.url(), settings.installDirectory + dlInfo.fileName(),
                        dlInfo.fileName(), true);
                System.out.println();

                // create eula file
                if (settings.eula) {
                    Files.makeFile(settings.installDirectory + "eula.txt", "eula=true");
                }

                // generate start script
                if (settings.startScriptContent != null) {
                    Files.makeFile(settings.installDirectory + "start.sh",
                            settings.startScriptContent + dlInfo.fileName() + (settings.noGui ? " nogui" : ""));
                }
            } catch (Exception e) {
                System.out.println(TerminalUtil.Colors.RED + TerminalUtil.Colors.BOLD + "Installation failed:");
                System.out.print(TerminalUtil.Colors.RESET);
                e.printStackTrace();
                return;
            }

            System.out.println(TerminalUtil.Colors.GREEN + TerminalUtil.Colors.BOLD + "Installation done!");
        } else {
            System.out.println(TerminalUtil.Colors.WARNING + "Aborting.");
        }
    }
}
