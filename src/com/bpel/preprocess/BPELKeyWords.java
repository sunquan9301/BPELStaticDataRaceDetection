package com.bpel.preprocess;
/**
 * bpel文件 keywords 类
 */
public class BPELKeyWords {

	//特殊关键字
	public final static String VARIABLE="variable";
	public final static String PARTNER_LINK="partnerLink";
	public final static String LINK="link";
	//结构化关键字
	public final static String FLOW="flow";
	public final static String SCOPE="scope";
	public final static String ASSIGN="assign";
	public final static String SEQUENCE="sequence";
	public final static String SWITCH="switch";
	//基本关键字
	public final static String CASE="case";
	public final static String INVOKE="invoke";
	public final static String RECEIVE="receive";
	public final static String REPLY="reply";
	public final static String COPY="copy";
	public final static String IF="if";
	public final static String FROM="from";
	public final static String TO="to";
	public final static String SOURCE="source";
	public final static String TARGET="target";
	public final static String CORRELATION="correlation";
	public final static String BASE="base";
	//结构化活动开始结束结点关键字
	public final static String END_FLOW="endFlow";
	public final static String END_SWITH="endSwithc";
	public final static String NO_ACTIVITY="noActivity";
	public final static String SEQUENCE_START="sequenceStart";
	public final static String SEQUENCE_END="sequenceEnd";
	public final static String SCOPE_START="scopeStart";
	public final static String SCOPE_END="scopeEnd";
	//HB图类型标签
	public final static String INTRA_ONLY_BASE="intraOnlyBase";
	public final static String INTRA_SEQUENCE="intraSequence";
	public final static String INTRA_SCOPE="intraScope";
	public final static String INTRA_SEQUENCE_SCOPE="interSequenceScope";
	public final static String INTER_LINK="interLink";
	//link类型关键字
	public final static String SOURCE_LINK="sourceLink";
	public final static String TARGET_LINK="targetLink";
	//共享变量关键字
	public final static String OUT_VARIABLE="outer_Variable";
	public final static String IN_VARIABLE="inter_Variable";
	
}
