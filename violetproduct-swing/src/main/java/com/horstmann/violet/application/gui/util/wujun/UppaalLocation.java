package com.horstmann.violet.application.gui.util.wujun;

public class UppaalLocation {
	Boolean init=false; //是否是初始状态
	Boolean fnal=false; //是否结束状态
	String Name="null"; //名称
	String id;			//状态id
	String R1 = "0";	//资源1占用
	String R2 = "0";	//资源2占用
	String Energe = "0";//单位时间损耗的能源
	String ObjId = "NULL";//对象的EAid
	String ObjName = "";//对象名
	String timeDuration;//时间约束
	
	public String getTimeDuration() {
		return timeDuration;
	}
	public void setTimeDuration(String timeDuration) {
		this.timeDuration = timeDuration;
	}
	public Boolean getInit() {
		return init;
	}
	public void setInit(Boolean init) {
		this.init = init;
	}
	public Boolean getFnal() {
		return fnal;
	}
	public void setFnal(Boolean fnal) {
		this.fnal = fnal;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
//	public int getType() {
//		return Type;
//	}
//	public void setType(int type) {
//		Type = type;
//	}
//	public String getLineEAID() {
//		return lineEAID;
//	}
//	public void setLineEAID(String lineEAID) {
//		this.lineEAID = lineEAID;
//	}
//	public String getInvariant() {
//		return Invariant;
//	}
//	public void setInvariant(String invariant) {
//		Invariant = invariant;
//	}
	public String getR1() {
		return R1;
	}
	public void setR1(String r1) {
		R1 = r1;
	}
	public String getR2() {
		return R2;
	}
	public void setR2(String r2) {
		R2 = r2;
	}
	public String getEnerge() {
		return Energe;
	}
	public void setEnerge(String energe) {
		Energe = energe;
	}
	public String getObjId() {
		return ObjId;
	}
	public void setObjId(String objId) {
		ObjId = objId;
	}
	public String getObjName() {
		return ObjName;
	}
	public void setObjName(String objName) {
		ObjName = objName;
	}
	
	

}
