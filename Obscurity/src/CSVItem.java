
public class CSVItem {

	private String[] items;
	private int curItem = 0;
	
	public CSVItem(int columns)
	{
		items = new String[columns];
	}
	
	public void addItem (String o) throws Exception
	{
		items[curItem] = o;
		curItem++;
	}
	
	public String getItem(int pos)
	{
		return items[pos];
	}
}
