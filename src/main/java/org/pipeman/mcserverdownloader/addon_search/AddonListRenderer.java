package org.pipeman.mcserverdownloader.addon_search;

import org.pipeman.mcserverdownloader.util.TerminalUtil;
import org.pipeman.mcserverdownloader.util.TerminalUtil.Colors;
import org.pipeman.mcserverdownloader.util.api.DownloadInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddonListRenderer {
    private final IAddonSearch search;
    private final String mcVersion;
    private final String installDir;
    private final List<DownloadInfo> toInstall = new ArrayList<>();
    private List<IAddon> lastResults = new ArrayList<>();

    public AddonListRenderer(IAddonSearch search, String mcVersion, String installDir) {
        this.search = search;
        System.out.print(
                Colors.BOLD + "Searching " + search.whatAreWeSearching() + " for Minecraft " + mcVersion + "\n" + Colors.RESET +
                "Enter a search query or the index of a result to get it's download versions. \n" +
                "Use 'e' to exit and (optional) download the selected mods.\n" +
                "> "
        );
        this.mcVersion = mcVersion;
        this.installDir = installDir;
    }

    public void startLoop() throws IOException {
        MainLoop:
        while (true) {
            String query = TerminalUtil.readLine();
            if (query.equalsIgnoreCase("e")) {
                if (exit()) break;
                System.out.print("> ");
                continue;
            }

            try {
                int idx = Integer.parseInt(query);
                if (idx > 0 && idx <= lastResults.size()) {
                    IAddon selectedAddon = lastResults.get(idx - 1);
                    List<DownloadInfo> versions = selectedAddon.versions();

                    System.out.println("Versions for " + selectedAddon.name() + ". Choose a version to download or " +
                                       "enter a new search query.");
                    TerminalUtil.printList(versions, o -> System.out.println(((DownloadInfo) o).title()));
                    System.out.print("> ");

                    while (true) {
                        String line = TerminalUtil.readLine();
                        try {
                            int chosenVersion = Integer.parseInt(line);
                            if (chosenVersion > 0 && chosenVersion <= versions.size()) {
                                toInstall.add(versions.get(chosenVersion - 1));
                                System.out.print("Added '" + versions.get(chosenVersion - 1).title() + "' to the " +
                                                 "download list.\n> ");
                                continue MainLoop;
                            } else {
                                System.out.print("> ");
                            }
                        } catch (Exception ignored) {
                            query = line;
                            break;
                        }
                    }

                } else {
                    System.out.print("> ");
                    continue;
                }

            } catch (Exception ignored) {
            }

            if (!query.isEmpty()) searchAndDraw(query, mcVersion);
            System.out.print("> ");
        }
    }

    private boolean exit() {
        if (toInstall.size() == 0) return true;

        System.out.print("You are about to exit the addon installer but you selected " + toInstall.size() + " " +
                           "addons.\nExit and discard them? (y/n) > ");
        if (TerminalUtil.readYesNo()) {
            return true;
        } else {
            System.out.println("You selected these addons: ");
            TerminalUtil.printList(toInstall, o -> System.out.println(((DownloadInfo) o).title()));
            System.out.print("Install? (y/n) > ");
            if (TerminalUtil.readYesNo()) {
                for (DownloadInfo info : toInstall) {
                    try {
                        info.download(installDir);
                        System.out.println();
                    } catch (Exception e) {
                        System.out.println("Downloading for mod " + info.title() + " failed: " + e.getMessage());
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    public void searchAndDraw(String query, String mcVersion) throws IOException {
        List<IAddon> addons = search.search(query, mcVersion);
        if (addons.size() == 0) System.out.println(Colors.BOLD + "Nothing found." + Colors.RESET);
        else {
            for (int i = 0; i < addons.size(); i++) {
                drawAddon(i + 1, addons.get(i));
                System.out.println("================================");
            }
        }
        lastResults = addons;
    }

    private void drawAddon(int idx, IAddon addon) {
        String out = idx + ": " + Colors.BOLD + addon.name() + Colors.RESET + " by " + addon.author() + "\n" +
                     addon.description();
        System.out.println(out);
    }
}
