package org.pipeman.mcserverdownloader.installer;

import org.pipeman.mcserverdownloader.questions.*;
import org.pipeman.mcserverdownloader.util.*;
import org.pipeman.mcserverdownloader.util.api.DownloadInfo;
import org.pipeman.mcserverdownloader.util.api.IApi;
import org.pipeman.mcserverdownloader.util.api.Requests;

import java.text.MessageFormat;
import java.util.ArrayList;

public class ServerInstaller {
    public static void installServer() {
        InstallerSettings settings = new InstallerSettings();
        ServerType serverType = askForServertype();

        IApi api = IApi.of(serverType);

        System.out.println("Choose the version to install:");
        System.out.print(TerminalUtil.Colors.GREEN + "Fetching available versions..." + TerminalUtil.Colors.RESET + "\r");

        ArrayList<String> versions = api.getVersions();
        System.out.print("                                   \r");
        settings.version = versions.get(TerminalUtil.readChoice(versions) - 1);

        QuestionBuilder builder = new QuestionBuilder()
                .addQuestion(new DirectoryQuestion(
                        "Enter the direcory where the server should be installed",
                        System.getProperty("user.dir"),
                        null
                ))

                .addQuestion(new BooleanQuestion(
                        "Do you agree to Mojang's eula? (https://account.mojang.com/documents/minecraft_eula)\n" +
                        "If not, you will have to agree after the first launch of the server. (y/n) ",
                        ctx -> serverType != ServerType.VELOCITY
                ))
                .addQuestion(new BooleanQuestion(
                        "Create a start script? (y/n) ",
                        null
                ))
                .addQuestion(new StringQuestion(
                        "Start.sh: Enter the command to start your Java VM (Leave empty to use 'java'): ",
                        "java",
                        ctx -> ctx.answer(2).getAsBoolean()
                ))
                .addQuestion(new BooleanQuestion(
                        "Start.sh: Should the server start without a gui? (y/n) ",
                        ctx -> serverType != ServerType.VELOCITY && ctx.answer(2).getAsBoolean()
                ))
                .addQuestion(new IntegerQuestion(
                        "Start.sh: How much RAM should be allocated to your server in MB (e.g. 2048 (2GB), 4096 (4GB))?",
                        ctx -> ctx.answer(2).getAsBoolean()
                ))
                .addQuestion(new BooleanQuestion(
                        "Start.sh: Use Aikar flags?",
                        ctx -> ctx.answer(2).getAsBoolean() && AikarFlags.isSupportedBy(serverType)
                ));

        Answer[] answers = builder.done();
        settings.eula = answers[1].getAsBoolean();
        if (answers[2].getAsBoolean()) {
            settings.startScriptContent = "cd \"${0%/*}\"\n";

            String normalRam = MessageFormat.format("-Xms{0}M -Xmx{0}M", answers[5].getAsInt());
            String aikarFlags = answers[6].getAsBoolean() ? AikarFlags.createFlags(answers[5].getAsInt()) : normalRam;
            settings.startScriptContent = "cd \"${0%/*}\"\n" + answers[3].getAsString() + " " + aikarFlags + " ";
            settings.noGui = answers[4].getAsBoolean();
        }
        settings.installationDirectory = answers[0].getAsString();

        System.out.println(settings.generateSummary(serverType));
        System.out.print("Install? (y/n) ");
        if (TerminalUtil.readYesNo()) {
            try {
                DownloadInfo dlInfo = api.getDownloadInfo(settings.version);
                Requests.downloadFile(dlInfo.url(), answers[0].getAsString() + dlInfo.fileName(), dlInfo.fileName(), true);
                System.out.println();

                if (settings.eula) Files.makeFile(answers[0].getAsString() + "eula.txt", "eula=true");

                if (settings.startScriptContent != null) {
                    Files.makeFile(answers[0].getAsString() + "start.sh",
                            settings.startScriptContent + dlInfo.fileName() + (settings.noGui ? "nogui" : ""),
                            true
                    );
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

    private static ServerType askForServertype() {
        QuestionBuilder builder = new QuestionBuilder()
                .addQuestion(new ChoiceQuestion<>(
                        "Which serversoftware should be installed?",
                        null,
                        Utils.listOf(ServerType.values())
                ));

        return ServerType.values()[(builder.done()[0].getAsInt()) - 1];
    }
}
