package com.bpel.preprocess;
/**
 * 存放bpel文件相关信息的类
 */
import java.io.File;

public class BPELFiles {
	private static String BPEL_PATH_DIR="BPELBenchmark";
	public static File[] files=null;
	public BPELFiles() {
		files=(new File(BPEL_PATH_DIR)).listFiles();
	}
}
