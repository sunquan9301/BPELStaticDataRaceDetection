package com.bpel.constraint;


public class ConstraintConstruct {

	public void startConstraintConstruct(String resultFileName)
	{
		//基础活动序列定义
		BasicActDefine bad=new BasicActDefine();
		bad.DefineBasicActivityInfo();
		//活动偏序列关系
		OrderConstraint ao=new OrderConstraint();
		ao.getActHBOrder();
		//System.out.println("******************8");
		//锁约束
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
