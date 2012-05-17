/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qsdown.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * QSDown engine
 * @author RexCJ
 */
public class QSEngine {

    private static final int MAX_DLINSTANCE_QUT = 10;
    private DLTask[] dlTask;
    public static ExecutorService pool = Executors.newCachedThreadPool();

    public DLTask[] getDlTask() {
        return dlTask;
    }

    public void setDlTask(DLTask[] dlInstance) {
        this.dlTask = dlInstance;
    }

    public void createDLTask(int threadQut, String url, String path, String filename) {
    	
        DLTask task = new DLTask(threadQut, url, path.endsWith("/") || path.endsWith("\\") ? path + filename : path + "/" + filename);
        pool.execute(task);
    }

    public void resumeDLTask(int threadQut, String url, String path, String filename) {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(path.endsWith("/") || path.endsWith("\\") ? 
                path + filename + ".tsk" : path + "/" + filename + ".tsk"));
            DLTask task = (DLTask)in.readObject();
            pool.execute(task);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QSEngine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(QSEngine.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(QSEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String args[]) {
        QSEngine engine = new QSEngine();
        engine.createDLTask(10, "http://hiphotos.baidu.com/dazhu1115/pic/item/8b9e93807299048a6d81195d.jpg", "D:", "bbb.jpg");
//        engine.resumeDLTask(10, "http://ardownload.adobe.com/pub/adobe/reader/win/8.x/8.1.2/chs/AdbeRdr812_zh_CN.exe", "D:", "AdbeRdr812_zh_CN.exe");
    }
}
