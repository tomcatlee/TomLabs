/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qsdown.component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
/**
 *
 * @author Administrator
 */
public class Recorder {

    private DLTask dlTask;

    public Recorder(DLTask dlTask) {
        this.dlTask = dlTask;
    }

    public void record() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(dlTask.getFilename() + ".tsk"));
            out.writeObject(dlTask);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

    }
}
