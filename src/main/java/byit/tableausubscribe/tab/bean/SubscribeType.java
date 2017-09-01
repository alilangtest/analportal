package byit.tableausubscribe.tab.bean;

public enum SubscribeType {

	//day,month;
	once("仅一次","1"),day("每天","2"),week("每周","3"),month("每月","4"),year("每年","5"),hour("每小时","6");
	private SubscribeType(String name,String index){
		this.name = name;
        this.index = index;
	}
	public static String getName(String index){
		if(index.equals(SubscribeType.once.getIndex())){
			return SubscribeType.once.getName();
		}else if(index.equals(SubscribeType.day.getIndex())){
			return SubscribeType.day.getName();
			
		}else if(index.equals(SubscribeType.week.getIndex())){
			return SubscribeType.week.getName();
		}else if(index.equals(SubscribeType.month.getIndex())){
			return SubscribeType.month.getName();
			
		}else if(index.equals(SubscribeType.year.getIndex())){
			return SubscribeType.year.getName();
		}else if(index.equals(SubscribeType.hour.getIndex())){
			return SubscribeType.hour.getName();
		}
		return index;
	}
	private String name;
    private String index;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
    
    public static void main(String args[]){
    }
}
