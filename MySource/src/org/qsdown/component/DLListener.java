/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qsdown.component;

import java.io.File;
import java.math.BigDecimal;

import org.qsdown.util.QSDownUtils;

/**
 * 
 * @author Administrator
 */
public class DLListener extends Thread {

	private DLTask dlTask;
	private Recorder recoder;

	DLListener(DLTask dlTask) {
		this.dlTask = dlTask;
		this.recoder = new Recorder(dlTask);
	}

	@Override
	public void run() {

		int i = 0;
		BigDecimal completeTot = null;
		long start = System.currentTimeMillis();
		long end = start;

		while (!dlTask.isComplete()) {
			i++;
			String percent = dlTask.getCurPercent();

			completeTot = new BigDecimal(dlTask.getCompletedTot());

			end = System.currentTimeMillis();
			if (end - start > 1000) {
				BigDecimal pos = new BigDecimal(((end - start) / 1000) * 1024);
				System.out.println("Speed :"
						+ completeTot
								.divide(pos, 0, BigDecimal.ROUND_HALF_EVEN)
						+ "k/s   " + percent + "% completed. ");
			}
			recoder.record();
			try {
				sleep(3000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}

		}
		int costTime =+ (int)((System.currentTimeMillis() - start) / 1000);
		dlTask.setCostTime(costTime);
		String time = QSDownUtils.changeSecToHMS(costTime);
		
		dlTask.getFile().renameTo(new File(dlTask.getFilename()));
		System.out.println("Download finished. " + time);
	}
}
