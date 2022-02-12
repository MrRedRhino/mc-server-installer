package org.pipeman.mcserverdownloader;

import org.pipeman.mcserverdownloader.installers.velocity.Velocity;
import org.pipeman.mcserverdownloader.util.TerminalUtil;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Which serversoftware should be installed?");
        System.out.println("  1: Velocity-proxy (full support 1.13+)");
        System.out.println("  2: Vanilla (1.16.5, 1.17.1, 1.18.1)");
        System.out.println("  3: Paper (1.16.5, 1.17.1, 1.18.1)");
        System.out.print("> ");
        int sel = 0;
        while (sel < 1 || sel > 3) {
            sel = TerminalUtil.readInt();
        }

        switch (sel) {
            case 1: {
                Velocity.installVelocity();
            }
        }
    }
}