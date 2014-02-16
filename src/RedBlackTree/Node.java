package RedBlackTree;

public class Node 
{
	int data;
	Node left;
	Node right;
	Node parent;	
	Color color;
	Node(int newData)
	{
		data = newData;
		left = right = parent = null;
		color = Color.BLACK;
	}
	
	public String toString()
	{
		String str = "("+data+" Color: "+color+")";
		return str;
	}
	
	public void copyData(Node other)
	{
		if(other == null) return;
		
		this.data = other.data;
	}
	
	public void releaseNode()
	{
		this.parent = null;
		this.left = null;
		this.right = null;		
	}
}
