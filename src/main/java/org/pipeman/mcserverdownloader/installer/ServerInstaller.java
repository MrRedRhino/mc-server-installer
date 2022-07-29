package org.pipeman.mcserverdownloader.installer;

import org.pipeman.mcserverdownloader.util.ServerType;
import org.pipeman.mcserverdownloader.util.Files;
import org.pipeman.mcserverdownloader.util.TerminalUtil;
import org.pipeman.mcserverdownloader.util.api.DownloadInfo;
import org.pipeman.mcserverdownloader.util.api.IApi;
import org.pipeman.mcserverdownloader.util.api.Requests;

import java.io.File;
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

        settings.installDirectory = getInstallDir(serverType != ServerType.VELOCITY);

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
            settings.startScriptContent += (line == null || line.isEmpty() ? "java" : line) + " -jar ";

            // TODO Fancy RAM options
            if (serverType != ServerType.VELOCITY) {
                System.out.print("Start.sh: Should the server start headless? (y/n) ");
                settings.noGui = TerminalUtil.readYesNo();
            }
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

    private static String getInstallDir(boolean isServer) {
        String out = "";
        boolean isCorrect = false;
        while (!isCorrect) {
            System.out.print("Enter the directory to install the " + (isServer ? "server in. " : "proxy in. ") +
                    "\nLeave empty to use the directory this program runs in.\n> ");

            String line = TerminalUtil.readLine();
            if (line == null || line.isEmpty() || line.equals(".")) {
                line = System.getProperty("user.dir");
            }

            out = new File(line).getAbsoluteFile().getPath() + "/";

            File dir = new File(out);
            if (!dir.exists()) {
                System.out.print(out + " does not exist. Create missing directories? (y/n) ");
                if (TerminalUtil.readYesNo()) {
                    if (!dir.mkdirs()) {
                        System.out.println("Creating the directories failed. Choose a different path.");
                    } else {
                        isCorrect = true;
                    }
                }
            } else if (dir.isFile()) {
                System.out.println("This is a file!");
            } else {
                File[] files = dir.listFiles(File::isFile);
                if (files != null && files.length > 0) {
                    System.out.print("The directory is not empty. Continue anyway? (y/n) ");
                    isCorrect = TerminalUtil.readYesNo();
                } else {
                    isCorrect = true;
                }
            }
        }
        return out;
    }
}
