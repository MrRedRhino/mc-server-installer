package org.pipeman.mcserverdownloader;

import org.pipeman.mcserverdownloader.meta.ServerMeta;
import org.pipeman.mcserverdownloader.util.TerminalUtil;
import org.pipeman.mcserverdownloader.util.api.DownloadInfo;
import org.pipeman.mcserverdownloader.util.api.IApi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import static org.pipeman.mcserverdownloader.util.Files.copyDirectoryRecursively;

public class Updater {
    public static void updateServer(ServerMeta meta) throws IOException {
        IApi api = IApi.of(meta.serverType());
        if (api == null) {
            System.out.println("Unknown server type.");
            return;
        }

        System.out.println("Choose the version to install:");
        System.out.print(TerminalUtil.Colors.GREEN + "Fetching available versions..." + TerminalUtil.Colors.RESET + "\r");

        ArrayList<String> versions = api.getVersions();
        System.out.print("                                   \r");
        String version = versions.get(TerminalUtil.readChoice(versions) - 1);


        createBackup();

        DownloadInfo downloadInfo = api.getDownloadInfo(version);
        downloadInfo.download("");
    }

    private static void createBackup() throws IOException {
        System.out.println("Creating a backup of your world...");
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Paths.get("server.properties")));

        String levelName = (String) properties.get("level-name");
        boolean backup = true;
        if (levelName == null) {
            System.out.print("Could not find your world folder. Continue without backup? (y/n) ");
            if (TerminalUtil.readYesNo()) backup = false;
            else System.exit(0);
        }

        if (backup) {
            System.out.println("Creating backup...");
            copyDirectoryRecursively(new File(levelName), getBackupFolder());
        }
    }

    private static File getBackupFolder() {
        File backupFolder = new File("backups");
        backupFolder.mkdir();

        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        String date = format.format(new Date());

        int id = 0;
        while (new File(backupFolder, date + "-" + id).exists()) {
            id++;
        }
        return new File(backupFolder, date + "-" + id);
    }
}
