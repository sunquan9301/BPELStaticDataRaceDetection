package com.bpel.constraint;


public class ConstraintConstruct {

	public void startConstraintConstruct(String resultFileName)
	{
		//��������ж���
		BasicActDefine bad=new BasicActDefine();
		bad.DefineBasicActivityInfo();
		//�ƫ���й�ϵ
		OrderConstraint ao=new OrderConstraint();
		ao.getActHBOrder();
		//System.out.println("******************8");
		//��Լ��
		LockConstraint lc=new LockConstraint();
		lc.processLock();
		//mayRace
		MayRaceConstraint mrc=new MayRaceConstraint();
		mrc.startProcess();
		DecMapDimension.DecHBOrder(OrderConstraint.actHBOrder);
		DecMapDimension.DecMayRaceOrder(MayRaceConstraint.mayRaceActivityOrder);
		//writeFile
		Z3FileWrite zfw=new Z3FileWrite();
		zfw.writeZ3File(resultFileName);
	}
}
