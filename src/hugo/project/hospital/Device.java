package hugo.project.hospital;

public abstract class Device {
	private String type;
	private int amount;
	
	public Device(String type, int initialAmount) {
		this.type = type;
		this.amount = initialAmount;
	}

	public void reduceAmount(int reduceNum){
		if(reduceNum >= 0 && reduceNum <= amount) amount -= reduceNum;
	}

	public void addAmount(int addNum){
		if(addNum >= 0) amount += addNum;
	}
	
	public String toString() {
		return type;
	}
	
	public String getType() {
		return toString();
	}
	
	public int getAmount() {
		return amount;
	}
	
}
