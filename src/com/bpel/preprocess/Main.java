package com.bpel.preprocess;

import java.io.File;

import com.bpel.constraint.BasicActDefine;
import com.bpel.constraint.ConstraintConstruct;
import com.bpel.constraint.DecMapDimension;
import com.bpel.constraint.OrderConstraint;
import com.bpel.constraint.Z3Run;
import com.bpel.hbconstruct.HBCreat;
import com.bpel.hbconstruct.InterNodeHB;
import com.bpel.hbconstruct.ProcessSharedVariable;

public class Main {
	public static void main(String[] args)
	{
		//		BPELFiles bpelFiles=new BPELFiles();
		//		for (int i = 0; i < bpelFiles.files.length; i++) {
		//			File file=bpelFiles.files[i];
		//			System.out.println(file.getName());
		//			//startProject(file);
		//		}
		File file=new File("benchmark\\MetaSearchBPEL2.bpel");
		long m=System.currentTimeMillis();
		startProject(file);
		long n=System.currentTimeMillis();
		System.out.println(n-m);

	}
	public static void startProject(File sourceFile)
	{
		//----------------�����ļ�����¼��Ҫ��¼��ֵ-----------------------------------------
		//SaxBPELProcess saxBPELProcess = new SaxBPELProcess("benchmark\\Loan-approval.bpel");
		String fileName="benchmark\\"+sourceFile.getName();
		SaxBPELProcess saxBPELProcess = new SaxBPELProcess(fileName);
		saxBPELProcess.startParseBPEL();
		saxBPELProcess.processCopyToAssignActivitys();
		//saxBPELProcess.printAllActivity();
		//----------------��ȡ�������-----------------------------------------------------
		//�����
		ProcessSharedVariable processSharedVariable=new ProcessSharedVariable();
		processSharedVariable.getSharedVarListAndVarReadAndWriteList();
		//				PrintInformation.printShareVariableInformation(ProcessSharedVariable.sharedVariableList);
		//PrintInformation.printShareVariableReadAndWriteList(ProcessSharedVariable.varReadAndWriteList);
		//----------------��д���ϴ���-----------------------------------------------------
		//�����
		//��ƶԱ����Ķ�д� base���� catch���ϡ�
		//----------------����HBͼ Intra OR Inter------------------------------------------
		//�����
		HBCreat hbCreat=new HBCreat();
		hbCreat.startCreatHB();
		//				for (int i = 0; i < HBCreat.intraHBGraphs.size(); i++) {
		//					System.out.println("***************HBGraph"+i+"*************************");
		//					NodeHB hbGraph = HBCreat.intraHBGraphs.get(i);
		//					PrintInformation.printHBGraph(hbGraph);
		//				}
		//				


//		for (int i = 0; i < HBCreat.interHBGraphs.size(); i++) {
//			System.out.println("***************InterHBGraph"+i+"*************************");
//			InterNodeHB hbGraph = HBCreat.interHBGraphs.get(i);
//			PrintInformation.printHBInterGraph(hbGraph);
//		}
		//---------------��Լ������--------------------------------------------------------
		//��ȡHBƫ�򼯺�
		ConstraintConstruct constraintConstruct=new ConstraintConstruct();
		constraintConstruct.startConstraintConstruct(getResultFileName(sourceFile));
		//PrintInformation.printActGIDZ3Order(BasicActDefine.ActGIDMapZ3Order);
		//PrintInformation.printActHBOrder(OrderConstraint.actHBOrder);
		//				DecMapDimension dmd=new DecMapDimension();
		//				dmd.decHBOrder();

		//PrintInformation.printActHBOrder(OrderConstraint.actHBOrder);
		//PrintInformation.printMayRace(MayRaceConstraint.mayRaceActivityOrder);

		//PrintInformation.printMayRace(DecMapDimension.mayRace);


		//���н���ļ�
		File solvefile=new File(getResultFileName(sourceFile));
		Z3Run z3Run=new Z3Run(solvefile);
		z3Run.startRun();
	}
	private static String getResultFileName(File sourceFile) {
		// TODO Auto-generated method stub

		System.out.println(sourceFile.getName());
		String[] strs=sourceFile.getName().split("\\.");
		//System.out.println(strs.length);
		String result=strs[0]+".stm2";
		return result;
	}

}
