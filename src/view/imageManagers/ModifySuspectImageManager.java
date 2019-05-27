/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.imageManagers;

import model.Images;
import model.Suspect;
import view.UI;

/**
 *
 * @author rafa0
 */
public class ModifySuspectImageManager extends ImageManager {

    Suspect oldSuspectBeenModified;

    public ModifySuspectImageManager(UI parent, boolean modal) {
        super(parent, modal);
    }

    public void setImages(Suspect newSuspectBeenModified, Images[] suspectImages) {
        if (oldSuspectBeenModified != null) {
            if (newSuspectBeenModified.getCodeSuspect().intValue() != newSuspectBeenModified
                    .getCodeSuspect().intValue()) {
                setImageManager(suspectImages);
            }
        } else {
            setImageManager(suspectImages);
        }
        oldSuspectBeenModified = newSuspectBeenModified;
    }
}
