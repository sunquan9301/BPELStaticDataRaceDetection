package com.bpel.constraint;

import java.util.ArrayList;
import java.util.HashMap;

public class DecMapDimension {
	public static HashMap<String,ArrayList<String>> mayRace=new HashMap<String, ArrayList<String>>();
	//public static HashMap<String,ArrayList<String>> decActHBOrder=new HashMap<String, ArrayList<String>>();
//	public void decHBOrder()
//	{
//		Object[] keys=OrderConstraint.actHBOrder.keySet().toArray();
//		for (int i = 0; i < keys.length; i++) {
//			ArrayList<String> temp=new ArrayList<String>();
//			temp.add(OrderConstraint.actHBOrder.get(keys[i].toString()).get(0));
//			decActHBOrder.put(keys[i].toString(), temp);
//		}
//		for (int i = 0; i < keys.length; i++) {
//			String source=keys[i].toString();
//			ArrayList<String> temp=OrderConstraint.actHBOrder.get(source);
//			for (int j = 0; j < temp.size(); j++) {
//				String target=temp.get(j);
//				if(!checkIsPathInMap(source,target))
//				{
//					if(decActHBOrder.get(source)==null)
//					{
//						ArrayList<String> temp1=new ArrayList<String>();
//						temp1.add(target);
//						decActHBOrder.put(source, temp1);
//					}else{
//						decActHBOrder.get(source).add(target);
//					}
//				}
//			}
//		}
//	}
//	private boolean checkIsPathInMap(String source, String target) {
//		// TODO Auto-generated method stub
//		boolean result=false;
//		if(decActHBOrder.get(source)!=null)
//		{
//			ArrayList<String> temp=decActHBOrder.get(source);
//			for (int i = 0; i < temp.size(); i++) {
//				if(temp.get(i)==target)
//				{
//					return true;
//				}
//				result=checkIsPathInMap(temp.get(i), target);
//				if(result)
//				{
//					return true;
//				}
//			}
//		}
//		return result;
//	}
	public static void DecHBOrder(HashMap<String, ArrayList<String>> actHBOrder)
	{
		Object[] keys=actHBOrder.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			String key=keys[i].toString();
			ArrayList<String> temp=actHBOrder.get(key);
			ProcessHashMap(key,temp,actHBOrder);
		}
	}
	private static void ProcessHashMap(String key, ArrayList<String> temp,
			HashMap<String, ArrayList<String>> actHBOrder) {
		// TODO Auto-generated method stub
		for (int i = 0; i < temp.size(); i++) {
			String secondKey=temp.get(i);
			if(actHBOrder.get(secondKey)!=null)
			{
				ArrayList<String> temp1=actHBOrder.get(secondKey);
				for (int j = 0; j < temp1.size(); j++) {
					String thirdKey=temp1.get(j);
					if(temp.contains(thirdKey))
					{
						temp.remove(thirdKey);
						i=0;
					}
				}
			}
		}
	}
	public static void DecMayRaceOrder(
			HashMap<String, ArrayList<String>> mayRaceActivityOrder) {
		// TODO Auto-generated method stub
		Object[] keys=mayRaceActivityOrder.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			String key=keys[i].toString();
			ArrayList<String> temp=mayRaceActivityOrder.get(key);
			for (int j = 0; j < temp.size(); j++) {
				String value=temp.get(j);
				if(!checkInMap(key,value))
				{
					if(mayRace.get(key)==null)
					{
						ArrayList<String> temp1=new ArrayList<String>();
						temp1.add(value);
						mayRace.put(key, temp1);
					}else{
						mayRace.get(key).add(value);
					}
				}
			}
		}
	}
	private static boolean checkInMap(String key, String value) {
		// TODO Auto-generated method stub
		if(mayRace.get(key)!=null&&mayRace.get(key).contains(value)||mayRace.get(value)!=null&&mayRace.get(value).contains(key))
		{
			return true;
		}
		return false;
	}
}
