package org.pipeman.mcserverdownloader;

import org.pipeman.mcserverdownloader.installer.ServerInstaller;
import org.pipeman.mcserverdownloader.meta.ServerMeta;
import org.pipeman.mcserverdownloader.util.Files;
import org.pipeman.mcserverdownloader.util.ServerType;
import org.pipeman.mcserverdownloader.util.TerminalUtil;
import org.pipeman.mcserverdownloader.util.TerminalUtil.Colors;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.print(Colors.RESET)));
        // new AddonListRenderer(new ModrinthImpl(), mcVersion, directory).startLoop();

        System.out.println("This program is running in: " + System.getProperty("user.dir"));
        String serverMetaFile = Files.readFile(new File(System.getProperty("user.dir"), "mcsi-meta.json"));
        if (serverMetaFile.isEmpty()) {
            System.out.println(Colors.RED + "Could not find a mcsi-meta.json file." + Colors.RESET);

            List<String> options = Arrays.asList(
                    "Restart the program in another directory",
                    "Create a meta file",
                    "Install a new server in this directory"
            );

            switch (TerminalUtil.readRange(options)) {
                case 1: {
                    System.exit(0);
                    break;
                }
                case 2: {
                    createMetaFile();
                    break;
                }
                case 3: {
                    ServerType serverType = askForServertype("Which serversoftware should be installed?");
                    ServerInstaller.installServer(serverType);
                    break;
                }
            }
        } else {
            System.out.println("Server meta file found.");
        }
    }

    private static void createMetaFile() {
        ServerType type = askForServertype("Which server software is installed in this directory?");

        System.out.print("Which minecraft version is installed?\n> ");
        String mcVersion = TerminalUtil.readLine();
        new ServerMeta(type, mcVersion).save();
        System.out.println(Colors.GREEN + "Done!");
        System.exit(0);
    }

    private static ServerType askForServertype(String prompt) {
        // TODO Forge, Pufferfisch
        System.out.println(prompt);
        ServerType serverType = null;
        switch (TerminalUtil.readRange(Arrays.asList("Fabric", "Paper", "Purpur", "Vanilla", "Velocity-proxy"))) {
            case 1: {
                serverType = ServerType.FABRIC;
                break;
            }
            case 2: {
                serverType = ServerType.PAPER;
                break;
            }
            case 3: {
                serverType = ServerType.PURPUR;
                break;
            }
            case 4: {
                serverType = ServerType.VANILLA;
                break;
            }
            case 5: {
                serverType = ServerType.VELOCITY;
                break;
            }
        }
        return serverType;
    }
}
