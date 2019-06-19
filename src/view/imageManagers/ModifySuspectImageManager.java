/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.imageManagers;

import controller.Controller;
import model.Images;
import view.PrintComponents;
import view.UI;

/**
 *
 * @author rafa0
 */
public class ModifySuspectImageManager extends ImageManager {

    private Images[] preModifiedSuspectImages;

    public ModifySuspectImageManager(UI parent, boolean modal) {
        super(parent, modal);
    }

    public void setImages(Integer codeNewSuspectBeenModified) {
        setImageManager(Controller.getInstance().getPhotos(codeNewSuspectBeenModified));
    }

    @Override
    public void setLabels() {
        safeDesc();
        suspectImages = preModifiedSuspectImages;
        parent.jLabel51.setText(getInsertedPhotos() + " / "
                + NPHOTOS);
    }

    @Override
    protected void resetLabels() {
        parent.jLabel51.setText(getInsertedPhotos() + " / "
                + NPHOTOS);
    }
}
