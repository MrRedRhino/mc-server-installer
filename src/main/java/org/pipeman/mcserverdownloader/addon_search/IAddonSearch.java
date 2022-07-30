package org.pipeman.mcserverdownloader.addon_search;

import java.io.IOException;
import java.util.List;

public interface IAddonSearch {
    List<IAddon> search(String query, String mcVersion) throws IOException;

    String whatAreWeSearching();
}
