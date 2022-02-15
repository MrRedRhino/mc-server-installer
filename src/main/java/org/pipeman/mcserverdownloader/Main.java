package org.pipeman.mcserverdownloader;

import org.pipeman.mcserverdownloader.installer.ServerInstaller;
import org.pipeman.mcserverdownloader.installers.velocity.Velocity;
import org.pipeman.mcserverdownloader.util.ServerType;
import org.pipeman.mcserverdownloader.util.TerminalUtil;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Which serversoftware should be installed?");
        System.out.println("  1: Vanilla (1.8.9+)");
        System.out.println("  2: Paper (1.8+)");
        System.out.println("  3: Velocity-proxy (full support of this programm 1.13+)");
        // TODO Fabric, Forge, Pufferfish, Purpur

        int sel = TerminalUtil.readRange(1, 3);

        switch (sel) {
            case 1: {
                ServerInstaller.installServer(ServerType.VANILLA);
                break;
            }
            case 2: {
                ServerInstaller.installServer(ServerType.PAPER);
                break;
            }
            case 3: {
                Velocity.installVelocity();
            }
        }
    }
}