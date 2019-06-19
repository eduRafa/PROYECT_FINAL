/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.imageManagers;

import view.UI;

/**
 *
 * @author rafa0
 */
public class AddSuspectImageManager extends ImageManager {

    public AddSuspectImageManager(UI parent, boolean modal) {
        super(parent, modal);
    }

    @Override
    public void setLabels() {
        safeDesc();
        parent.jLabel30.setText(getInsertedPhotos() + " / "
                + NPHOTOS);
    }

    @Override
    protected void resetLabels() {
        parent.jLabel30.setText(getInsertedPhotos() + " / "
                + NPHOTOS);
    }
}
