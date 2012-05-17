/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qsdown.component;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

/**
 * 本类对应一个下载任务，每个下载任务包含多个下载线程，默认最多包含十个下载线程
 * @author RexCJ
 */
public class DLTask extends Thread implements Serializable {

	private static final long serialVersionUID = 126148287461276024L;
	private final static int MAX_DLTHREAD_QUT = 10;  //最大下载线程数量
	/**
	 * 下载临时文件后缀，下载完成后将自动被删除
	 */
    public final static String FILE_POSTFIX = ".tmp";
    private URL url;									
    private File file;
    private String filename;
    private int id;
    private int Level;
    private int threadQut;								//下载线程数量，用户可定制							
    private int contentLen;							//下载文件长度
    private long completedTot;							//当前下载完成总数
    private int costTime;								//下载时间计数，记录下载耗费的时间
    private String curPercent;							//下载百分比
    private boolean isNewTask;						//是否新建下载任务，可能是断点续传任务
    
    private DLThread[] dlThreads;						//保存当前任务的线程

    transient private DLListener listener;			//当前任务的监听器，用于即时获取相关下载信息

    public DLTask(int threadQut, String url, String filename) {
        this.threadQut = threadQut;
        this.filename = filename;
        costTime = 0;
        curPercent = "0";
        isNewTask = true;
        this.dlThreads = new DLThread[threadQut];
        this.listener = new DLListener(this);
        try {
            this.url = new URL(url);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        if (isNewTask) {
            newTask();
            return;
        }
        resumeTask();
    }

    /**
     * 恢复任务时被调用，用于断点续传时恢复各个线程。
     */
    private void resumeTask() {
        listener = new DLListener(this);
        file = new File(filename + FILE_POSTFIX);
        for (int i = 0; i < threadQut; i++) {
            dlThreads[i].setDlTask(this);
            QSEngine.pool.execute(dlThreads[i]);
        }
        QSEngine.pool.execute(listener);
    }

    /**
     * 新建任务时被调用，通过连接资源获取资源相关信息，并根据具体长度创建线程块，
     * 线程创建完毕后，即刻通过线程池进行调度
     * @throws RuntimeException
     */
    private void newTask() throws RuntimeException {
        try {
            isNewTask = false;
            URLConnection con = url.openConnection();
            Map map = con.getHeaderFields();
            Set<String> set = map.keySet();
            for(String key : set){
            	System.out.println(key + " : " + map.get(key));
            }
            contentLen = con.getContentLength();
            if (contentLen <= 0) {
                System.out.println("无法获取资源长度，中断下载进程");
                return;
            }
            file = new File(filename + FILE_POSTFIX);
            int fileCnt = 1;
            while (file.exists()) {
                file = new File(filename += (fileCnt + FILE_POSTFIX));
                fileCnt++;
            }
//            long freespace = file.getFreeSpace();
//            if (contentLen < freespace) {
//                System.out.println("磁盘空间不够。");
//                return;
//            }
            long subLen = contentLen / threadQut;

            for (int i = 0; i < threadQut; i++) {
                DLThread thread = new DLThread(this, i + 1, subLen * i, subLen * (i + 1) - 1);
                dlThreads[i] = thread;
                QSEngine.pool.execute(dlThreads[i]);
            }

            QSEngine.pool.execute(listener);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

//    private String percent() {
//    	this.completeTot();
//        curPercent = new BigDecimal(completedTot).divide(new BigDecimal(this.contentLen), 2, BigDecimal.ROUND_HALF_EVEN).divide(new BigDecimal(0.01), 0, BigDecimal.ROUND_HALF_EVEN).toString();
//        return curPercent;
//    }
    
    /**
     * 计算当前已经完成的长度并返回下载百分比的字符串表示，目前百分比均为整数
     * @return
     */
    public String getCurPercent() {
    	this.completeTot();
        curPercent = new BigDecimal(completedTot).divide(new BigDecimal(this.contentLen), 2, BigDecimal.ROUND_HALF_EVEN).divide(new BigDecimal(0.01), 0, BigDecimal.ROUND_HALF_EVEN).toString();
        return curPercent;
    }
    
    private void completeTot(){
    	completedTot = 0;
        for (DLThread t : dlThreads) {
            completedTot += t.getReadByte();
        }
    }

    /**
     * 判断全部线程是否已经下载完成，如果完成则返回true，相反则返回false
     * @return
     */
    public boolean isComplete() {
        boolean completed = true;
        for (DLThread t : dlThreads) {
            completed = t.isFinished();
            if (!completed) {
                break;
            }
        }
        return completed;
    }

//    public boolean percentChanged() {
//        percent();
//        if (curPercent.equals(prevPercent)) {
//            return false;
//        }
//        prevPercent = curPercent;
//        return true;
//    }
    
    public void rename(){
        this.file.renameTo(new File(filename));
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    public DLThread[] getDlThreads() {
        return dlThreads;
    }

    public void setDlThreads(DLThread[] dlThreads) {
        this.dlThreads = dlThreads;
    }

    public int getTaskId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public URL getUrl() {
        return url;
    }

    public int getContentLen() {
        return contentLen;
    }

    public String getFilename() {
        return filename;
    }


    public int getThreadQut() {
        return threadQut;
    }

	public long getCompletedTot() {
		return completedTot;
	}

	public int getCostTime() {
		return costTime;
	}

	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}
   
}
