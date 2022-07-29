package org.pipeman.mcserverdownloader;

import org.pipeman.mcserverdownloader.installer.ServerInstaller;
import org.pipeman.mcserverdownloader.util.ServerType;
import org.pipeman.mcserverdownloader.util.TerminalUtil;

public class Main {
    public static void main(String[] args) {

//        Inspector.inspectFolder(new File("test-server"));
//        System.exit(0);

        System.out.println("Which serversoftware should be installed?");
        System.out.println("  1: Fabric (1.14+)");
        System.out.println("  2: Paper (1.8.8+)");
        System.out.println("  3: Purpur (1.14.1+)");
        System.out.println("  4: Vanilla (1.8.9+)");
        System.out.println("  5: Velocity-proxy (Full support by this programm 1.13+)");
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
    }
}