package org.pipeman.mcserverdownloader;

import org.pipeman.mcserverdownloader.addon_search.AddonListRenderer;
import org.pipeman.mcserverdownloader.addon_search.ModrinthImpl;
import org.pipeman.mcserverdownloader.installer.ServerInstaller;
import org.pipeman.mcserverdownloader.util.ServerType;
import org.pipeman.mcserverdownloader.util.TerminalUtil;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.print("Install (s) serversoftware or (a) addons?\n> ");
        while (true) {
            String line = TerminalUtil.readLine();
            if (line.equalsIgnoreCase("s")) {
                break;
            } else if (line.equalsIgnoreCase("a")) {
                doAddonChoosing();
                System.exit(0);
            }
        }

        System.out.println("Which serversoftware should be installed?");
        System.out.println("  1: Fabric (1.14+)");
        System.out.println("  3: Paper (1.8.8+)");
        System.out.println("  4: Purpur (1.14.1+)");
        System.out.println("  5: Vanilla (1.8.9+)");
        System.out.println("  6: Velocity-proxy (Full support by this programm 1.13+)");
        // TODO Forge, Pufferfisch

        int sel = TerminalUtil.readRange(1, 5);

        switch (sel) {
            case 1: {
                ServerInstaller.installServer(ServerType.FABRIC);
                break;
            }
            case 2: {
                ServerInstaller.installServer(ServerType.PAPER);
                break;
            }
            case 3: {
                ServerInstaller.installServer(ServerType.PURPUR);
                break;
            }
            case 4: {
                ServerInstaller.installServer(ServerType.VANILLA);
                break;
            }
            case 5: {
                ServerInstaller.installServer(ServerType.VELOCITY);
                break;
            }
        }

        System.out.print(TerminalUtil.Colors.RESET);
        System.out.print("Install addons? (y/n) ");
        if (TerminalUtil.readYesNo()) doAddonChoosing();
    }

    public static void doAddonChoosing() throws IOException {
        String directory = TerminalUtil.getInstallDir("addons");

        System.out.print("Enter the minecraft version to search addons for: ");
        String mcVersion = TerminalUtil.readLine();

        ArrayList<String> downloads = new ArrayList<>();
        downloads.add("Modrinth");

        System.out.println("Choose an addon-website to download from: ");
        int what = TerminalUtil.readRange(downloads);

        switch (what) {
            case 1: {
                new AddonListRenderer(new ModrinthImpl(), mcVersion, directory).startLoop();
            }
        }
    }
}