/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qsdown.component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author RexCJ
 */
public class DLThread extends Thread implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3317849201046281359L;
	private static int BUFFER_SIZE = 8096;
    transient private DLTask dlTask;
    private int id;
    private URL url;
    private long startPos;
    private long endPos;
    private long curPos;
    private long readByte;
    transient private File file;
    private boolean finished;
    private boolean isNewThread;

    public DLThread(DLTask dlTask, int id, long startPos, long endPos) {
        this.dlTask = dlTask;
        this.id = id;
        this.url = dlTask.getUrl();
        this.curPos = this.startPos = startPos;
        this.endPos = endPos;
        this.file = dlTask.getFile();
        finished = false;
        readByte = 0;
    }

    public void run() {
        System.out.println("线程" + id + "启动......");
        BufferedInputStream bis = null;
        RandomAccessFile fos = null;
        byte[] buf = new byte[BUFFER_SIZE];
        URLConnection con = null;
        try {
            con = url.openConnection();
            con.setAllowUserInteraction(true);
            if (isNewThread) {
                con.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
                fos = new RandomAccessFile(file, "rw");
                fos.seek(startPos);
            } else {
                con.setRequestProperty("Range", "bytes=" + curPos + "-" + endPos);
                fos = new RandomAccessFile(dlTask.getFile(), "rw");
                fos.seek(curPos);
            }
            bis = new BufferedInputStream(con.getInputStream());
            while (curPos < endPos) {
                int len = bis.read(buf, 0, BUFFER_SIZE);
                if (len == -1) {
                    break;
                }
                fos.write(buf, 0, len);
                curPos = curPos + len;
                if (curPos > endPos) {
                    readByte += len - (curPos - endPos) + 1; //获取正确读取的字节数
                } else {
                    readByte += len;
                }
            }
            System.out.println("线程" + id + "已经下载完毕。");
            this.finished = true;
            bis.close();
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public DLTask getBelongDLIns() {
        return dlTask;
    }

    public void setBelongDLIns(DLTask belongDLIns) {
        this.dlTask = belongDLIns;
    }

    public boolean isFinished() {
        return finished;
    }

    public long getReadByte() {
        return readByte;
    }

    public void setDlTask(DLTask dlTask) {
        this.dlTask = dlTask;
    }
}
