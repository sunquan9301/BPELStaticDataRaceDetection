package com.bpel.preprocess;
/**
 * ���bpel�ļ������Ϣ����
 */
import java.io.File;

public class BPELFiles {
	private static String BPEL_PATH_DIR="BPELBenchmark";
	public static File[] files=null;
	public BPELFiles() {
		files=(new File(BPEL_PATH_DIR)).listFiles();
	}
}
