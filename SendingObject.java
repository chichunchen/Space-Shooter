
import java.io.Serializable;

public class SendingObject implements Serializable
{
	private int stone;
	private int eCraft;
	private int item;

	public SendingObject( int s, int e, int i )
	{
		stone = s;
		eCraft = e;
		item = i;
	}

	public int getStoneNum()
	{
		return stone;
	}

	public int getECraftNum()
	{
		return eCraft;
	}

	public int getItemNum()
	{
		return item;
	}
}