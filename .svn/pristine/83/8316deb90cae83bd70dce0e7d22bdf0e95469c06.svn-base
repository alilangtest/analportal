package byit.tableausubscribe.tab.bean;

public enum SubscribeExcelType {

	//day,month;
	once("仅一次","1"),day("每天","2"),week("每周","3"),month("每月","4"),year("每年","5");
	private SubscribeExcelType(String name,String index){
		this.name = name;
        this.index = index;
	}
	public static String getName(String index){
		if(index.equals(SubscribeExcelType.once.getIndex())){
			return SubscribeExcelType.once.getName();
		}else if(index.equals(SubscribeExcelType.day.getIndex())){
			return SubscribeExcelType.day.getName();
			
		}else if(index.equals(SubscribeExcelType.week.getIndex())){
			return SubscribeExcelType.week.getName();
		}else if(index.equals(SubscribeExcelType.month.getIndex())){
			return SubscribeExcelType.month.getName();
			
		}else if(index.equals(SubscribeExcelType.year.getIndex())){
			return SubscribeExcelType.year.getName();
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
