package org.pipeman.mcserverdownloader;

import org.pipeman.mcserverdownloader.installers.vanilla.Vanilla;
import org.pipeman.mcserverdownloader.installers.velocity.Velocity;
import org.pipeman.mcserverdownloader.util.TerminalUtil;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Which serversoftware should be installed?");
        System.out.println("  1: Vanilla (1.8.9+)");
        System.out.println("  2: Velocity-proxy (full support of this programm 1.13+)");
        System.out.print("> ");
        int sel = 0;
        while (sel < 1 || sel > 2) {
            sel = TerminalUtil.readInt();
        }

        switch (sel) {
            case 1: {
                Vanilla.installVanilla();
                break;
            }
            case 2: {
                Velocity.installVelocity();
                break;
            }
        }
    }
}