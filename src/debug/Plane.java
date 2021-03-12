package debug;
public class Plane {
	private String planeNo; // 飞机唯一编号
	private String planeType; // 机型，例如A350,B787,C929
	private int seatsNum; // 座位数
	private double planeAge;// 机龄

	@Override
	public boolean equals(Object another) {
		if (another == null)
			return false;
		if (!(another instanceof Plane))
			return false;
		Plane ap = (Plane) another;
		if (ap.getPlaneNo() .equals( this.getPlaneNo()))//字符串比较要用equals
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return planeNo.hashCode();
	}
	//此处要更改，equals的对象hashcode一定要相同

	public String getPlaneNo() {
		return planeNo;
	}

	public void setPlaneNo(String planeNo) {
		this.planeNo = planeNo;
	}

	public String getPlaneType() {
		return planeType;
	}

	public void setPlaneType(String planeType) {
		this.planeType = planeType;
	}

	public int getSeatsNum() {
		return seatsNum;
	}

	public void setSeatsNum(int seatsNum) {
		this.seatsNum = seatsNum;
	}

	public double getPlaneAge() {
		return planeAge;
	}

	public void setPlaneAge(double planeAge) {
		this.planeAge = planeAge;
	}
}
