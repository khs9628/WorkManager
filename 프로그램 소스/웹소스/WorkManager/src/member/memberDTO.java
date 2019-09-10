package member;

/**
 * @author Ands00
 *
 */
public class memberDTO {
	/*EMPLOYEE*/
	String EMP_ID;
	String EMP_PW;
	String EMP_NAME;
	String EMP_EMAIL;
	int EMP_NO;
	String EMP_STAT;
	
	/*HIREINFO*/
	String workPlace;
	String salType;
	int STD_Salary;
	String joinDate;
	String START_TIME;
	String END_TIME;
	String WORK_DAY;
	String REST_DAY;
	String HOLIDAY;
	int ISMOREFIVE;
	int REST_TIME;
	String SALDATE;
	 
	/*WORKTIME*/
	String A_TIME;
	String E_TIME;
	String KIND_DAY;
	int BEFORE_WEEKLY;
	int WORKTIME_ID;
	
	/*HOLIDAY*/
	String HOLI_DATE;
	String HOLI_DESC;
	
	/*REQUEST*/
	int REQ_ID;
	String REQ_TYPE;
	String REQ_TITLE;
	String REQ_CONTENT;
	int REQ_EMP_NO;
	String REQ_DATE;
	String REQ_A_TIME;
	String REQ_E_TIME;
	
	
	
	
	
	public String getEMP_ID() {
		return EMP_ID;
	}
	public void setEMP_ID(String eMP_ID) {
		EMP_ID = eMP_ID;
	}
	public String getEMP_PW() {
		return EMP_PW;
	}
	public void setEMP_PW(String eMP_PW) {
		EMP_PW = eMP_PW;
	}
	public String getEMP_NAME() {
		return EMP_NAME;
	}
	public void setEMP_NAME(String eMP_NAME) {
		EMP_NAME = eMP_NAME;
	}
	public String getEMP_EMAIL() {
		return EMP_EMAIL;
	}
	public void setEMP_EMAIL(String eMP_EMAIL) {
		EMP_EMAIL = eMP_EMAIL;
	}
	public int getEMP_NO() {
		return EMP_NO;
	}
	public void setEMP_NO(int eMP_NO) {
		EMP_NO = eMP_NO;
	}
	public String getEMP_STAT() {
		return EMP_STAT;
	}
	public void setEMP_STAT(String eMP_STAT) {
		EMP_STAT = eMP_STAT;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getSalType() {
		return salType;
	}
	public void setSalType(String salType) {
		this.salType = salType;
	}
	public int getSTD_Salary() {
		return STD_Salary;
	}
	public void setSTD_Salary(int sTD_Salary) {
		STD_Salary = sTD_Salary;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	public String getEND_TIME() {
		return END_TIME;
	}
	public void setEND_TIME(String eND_TIME) {
		END_TIME = eND_TIME;
	}
	public String getWORK_DAY() {
		return WORK_DAY;
	}
	public void setWORK_DAY(String wORK_DAY) {
		WORK_DAY = wORK_DAY;
	}
	public String getREST_DAY() {
		return REST_DAY;
	}
	public void setREST_DAY(String rEST_DAY) {
		REST_DAY = rEST_DAY;
	}
	public String getHOLIDAY() {
		return HOLIDAY;
	}
	public void setHOLIDAY(String hOLIDAY) {
		HOLIDAY = hOLIDAY;
	}
	public int getISMOREFIVE() {
		return ISMOREFIVE;
	}
	public void setISMOREFIVE(int iSMOREFIVE) {
		ISMOREFIVE = iSMOREFIVE;
	}
	
	public String getA_TIME() {
		return A_TIME;
	}
	public void setA_TIME(String a_TIME) {
		A_TIME = a_TIME == null ? "--:--" : a_TIME ;
	}
	public String getE_TIME() {
		return E_TIME;
	}
	public void setE_TIME(String e_TIME) {
		E_TIME = e_TIME == null ? "--:--" : e_TIME;
	}
	public String getKIND_DAY() {
		return KIND_DAY;
	}
	public void setKIND_DAY(String kIND_DAY) {
		KIND_DAY = kIND_DAY;
	}
	public int getBEFORE_WEEKLY() {
		return BEFORE_WEEKLY;
	}
	public void setBEFORE_WEEKLY(int bEFORE_WEEKLY) {
		BEFORE_WEEKLY = bEFORE_WEEKLY;
	}
	public int getWORKTIME_ID() {
		return WORKTIME_ID;
	}
	public void setWORKTIME_ID(int wORKTIME_ID) {
		WORKTIME_ID = wORKTIME_ID;
	}
	public String getHOLI_DATE() {
		return HOLI_DATE;
	}
	public void setHOLI_DATE(String hOLI_DATE) {
		HOLI_DATE = hOLI_DATE;
	}
	public String getHOLI_DESC() {
		return HOLI_DESC;
	}
	public void setHOLI_DESC(String hOLI_DESC) {
		HOLI_DESC = hOLI_DESC;
	}
	public int getREST_TIME() {
		return REST_TIME;
	}
	public void setREST_TIME(int rEST_TIME) {
		REST_TIME = rEST_TIME;
	}
	public String getSALDATE() {
		return SALDATE;
	}
	public void setSALDATE(String sALDATE) {
		SALDATE = sALDATE;
	}
	public int getREQ_ID() {
		return REQ_ID;
	}
	public void setREQ_ID(int rEQ_ID) {
		REQ_ID = rEQ_ID;
	}
	public String getREQ_TYPE() {
		return REQ_TYPE;
	}
	public void setREQ_TYPE(String rEQ_TYPE) {
		REQ_TYPE = rEQ_TYPE;
	}
	public String getREQ_TITLE() {
		return REQ_TITLE;
	}
	public void setREQ_TITLE(String rEQ_TITLE) {
		REQ_TITLE = rEQ_TITLE;
	}
	public String getREQ_CONTENT() {
		return REQ_CONTENT;
	}
	public void setREQ_CONTENT(String rEQ_CONTENT) {
		REQ_CONTENT = rEQ_CONTENT;
	}
	
	public int getREQ_EMP_NO() {
		return REQ_EMP_NO;
	}
	public void setREQ_EMP_NO(int rEQ_EMP_NO) {
		REQ_EMP_NO = rEQ_EMP_NO;
	}
	public String getREQ_DATE() {
		return REQ_DATE;
	}
	public void setREQ_DATE(String rEQ_DATE) {
		REQ_DATE = rEQ_DATE;
	}
	public String getREQ_A_TIME() {
		return REQ_A_TIME;
	}
	public void setREQ_A_TIME(String rEQ_A_TIME) {
		REQ_A_TIME = rEQ_A_TIME;
	}
	public String getREQ_E_TIME() {
		return REQ_E_TIME;
	}
	public void setREQ_E_TIME(String rEQ_E_TIME) {
		REQ_E_TIME = rEQ_E_TIME;
	}

	
	
}
