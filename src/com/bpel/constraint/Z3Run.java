package com.bpel.constraint;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Z3Run {
	private File file=null;
	
	public Z3Run(File file) {
		super();
		this.file = file;
	}
	public void startRun()
	{
		// String cmd = "z3 "+file.getAbsolutePath();  
       //  String cmd="z3 C:\\z3-test-demo.stm2";
    	 String cmd="C:\\Users\\Sun\\Desktop\\��ع����Ϳγ�\\bpel\\z3-4.3.2-x64-win\\bin\\z3 "+file.getAbsolutePath(); 

	        //linux  
//	      String cmd = "./fork_wait";  
//	      String cmd = "ls -l";  
//	      String[] cmd=new String[3];  
//	      cmd[0]="/bin/sh";  
//	      cmd[1]="-c";  
//	      cmd[2]="ls -l ./";  
	        Runtime run = Runtime.getRuntime();//�����뵱ǰ Java Ӧ�ó�����ص�����ʱ����  
	        try {  
	            Process p = run.exec(cmd);// ������һ��������ִ������  
	            BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
	            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
	            String lineStr;  
	            while ((lineStr = inBr.readLine()) != null)  
	                //�������ִ�к��ڿ���̨�������Ϣ  
	                System.out.println(lineStr);// ��ӡ�����Ϣ  
	            //��������Ƿ�ִ��ʧ�ܡ�  
	            if (p.waitFor() != 0) {  
	                if (p.exitValue() == 1)//p.exitValue()==0��ʾ����������1������������  
	                    System.err.println("����ִ��ʧ��!");  
	            }  
	            inBr.close();  
	            in.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	}
}
