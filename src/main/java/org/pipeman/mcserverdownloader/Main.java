package org.pipeman.mcserverdownloader;

import org.pipeman.mcserverdownloader.addon_search.AddonListRenderer;
import org.pipeman.mcserverdownloader.addon_search.ModrinthImpl;
import org.pipeman.mcserverdownloader.installer.ServerInstaller;
import org.pipeman.mcserverdownloader.questions.*;
import org.pipeman.mcserverdownloader.util.TerminalUtil.Colors;
import org.pipeman.mcserverdownloader.util.Utils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.print(Colors.RESET)));

        System.out.println("This program is running in: " + System.getProperty("user.dir"));

        Answer[] answers = new QuestionBuilder()
                .addQuestion(new ChoiceQuestion<>(
                        "",
                        null,
                        Utils.listOf(
                                "Install a new server",
                                "Install plugins/mods"
                        )
                ))
                .addQuestion(new DirectoryQuestion(
                        "Enter the path where the addons should be installed",
                        ctx -> ctx.answer(0).getAsInt() == 2
                ))
                .addQuestion(new StringQuestion(
                        "Enter the minecraft version to search addons for",
                        ctx -> ctx.answer(0).getAsInt() == 2
                ))
                .addQuestion(new ChoiceQuestion<>(
                        "Choose the addon-platform to download from",
                        ctx -> ctx.answer(0).getAsInt() == 2,
                        Utils.listOf("Modrinth")
                )).done();

        switch (answers[0].getAsInt()) {
            case 1: {
                ServerInstaller.installServer();
                break;
            }
            case 2: {
                new AddonListRenderer(
                        new ModrinthImpl(),
                        answers[2].getAsString(),
                        answers[1].getAsString()
                ).startLoop();
            }
        }
    }
}
