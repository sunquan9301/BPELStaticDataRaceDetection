package com.bpel.constraint;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Z3FileWrite {
	public void writeZ3File(String resultFileName)
	{
		File file=new File(resultFileName);
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//–¥◊¢ Õ
		FileWriter fw=null;
		BufferedWriter bw=null;
		try {
			fw = new FileWriter(file);
			bw=new BufferedWriter(fw);
			bw.write(";orderBook.bpel\r\n");
			bw.flush();
			
			//–¥∂®“Â–Ú¡–
			Object[] keys=BasicActDefine.ActGIDMapZ3Order.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				String str="(declare-const "+BasicActDefine.ActGIDMapZ3Order.get(keys[i].toString())+" Int)\r\n";
				bw.write(str);
				bw.flush();
			}
			
			//–¥HB–Ú¡–
			bw.write("\r\n");
			bw.write("\r\n");
			Object[] keysHB=OrderConstraint.actHBOrder.keySet().toArray();
			for (int i = 0; i < keysHB.length; i++) {
				String front=keysHB[i].toString();
				ArrayList<String> temp=OrderConstraint.actHBOrder.get(front);
				for (int j = 0; j < temp.size(); j++) {
					String later=temp.get(j);
					String str="(assert(< "+BasicActDefine.ActGIDMapZ3Order.get(front)+" "+BasicActDefine.ActGIDMapZ3Order.get(later)+"))\r\n";
					bw.write(str);
					bw.flush();
				}
			}
			
			//–¥isolapeÀ¯‘º ¯–Ú¡–°£
			bw.write("\r\n");
			bw.write("\r\n");
			if(LockConstraint.isolateScope!=null)
			{
				for (int i = 0; i < LockConstraint.isolateScope.size(); i++) {
					String firstGID=LockConstraint.isolateScope.get(i);
					for (int j = i; j < LockConstraint.isolateScope.size(); j++) {
						String secondGID=LockConstraint.isolateScope.get(j);
						if(firstGID!=secondGID)
						{
							String str="(assert (or (< "+firstGID+" "+secondGID+") (> "+firstGID+" "+secondGID+")))\r\n";
							bw.write(str);
							bw.flush();
						}
					}
				}
			}
			
			Object[] csKeys=LockConstraint.correlationSet.keySet().toArray();
			for (int i = 0; i < csKeys.length; i++) {
				String csKey=csKeys[i].toString();
				ArrayList<String> temp=LockConstraint.correlationSet.get(csKey);
				for (int q = 0; q < temp.size(); q++) {
					String firstGID=temp.get(q);
					for (int j = q; j < temp.size(); j++) {
						String secondGID=temp.get(j);
						if(firstGID!=secondGID)
						{
							String str="(assert (or (< "+firstGID+" "+secondGID+") (> "+firstGID+" "+secondGID+")))\r\n";
							bw.write(str);
							bw.flush();
						}
					}
				}
			}
			
			//mayRace
			bw.write("\r\n");
			bw.write("\r\n");
			Object[] keysRace=DecMapDimension.mayRace.keySet().toArray();
			for (int i = 0; i < keysRace.length; i++) {
				String front=keysRace[i].toString();
				ArrayList<String> temp=DecMapDimension.mayRace.get(front);
				for (int j = 0; j < temp.size(); j++) {
					String later=temp.get(j);
					bw.write("(push)\r\n");
					bw.flush();
					bw.write("(check-sat)\r\n");
					bw.flush();
					String str="(assert(= "+BasicActDefine.ActGIDMapZ3Order.get(front)+" "+BasicActDefine.ActGIDMapZ3Order.get(later)+"))\r\n";
					bw.flush();
					bw.write(str);
					bw.flush();
					bw.write("(get-model)\r\n");
					bw.flush();
					bw.write("(pop)\r\n");
					bw.flush();
					bw.write("\r\n");
					bw.write("\r\n");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
