package controllers;

import models.Folder;
import models.Media;

import java.util.List;

/**
 * Created by meichris on 10.12.14.
 */
public class MediaController {

    /*
    Creates a media with the delivered name

    @param argName The delivered media name
    @param argFoldersID The delivered Folder ID

    @return The ID of the created media
    */
    public static Long createMedia(String argName, Long argFolderID) {
        Media newMedia = new Media();
        if (allowToCreate(argName,argFolderID)) {
            newMedia.name = argName;
            newMedia.inFolder = Folder.findById(argFolderID);
            newMedia.create();
        }
        return newMedia.id;
    }
    /*
    Checks whether it is allowed or not to create a new media in the specified folder

    @param argName The medias name
    @param argParentId The folders ID

    @return true if there isn't allready a media with the same name, false if there is.
    */
    private static boolean allowToCreate(String argName, Long argfolderId) {
        boolean allow = true;
        Folder f = Folder.findById(argfolderId);
        List<Media> files = f.files;
        if (!files.isEmpty()) {
            for ( Media m: files)
                if (m.name.equals(argName))
                    allow = false;
        }
        return allow;
    }
}
