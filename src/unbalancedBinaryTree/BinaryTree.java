package unbalancedBinaryTree;

import java.util.Queue;
import java.util.LinkedList;

public class BinaryTree 
{
	private Node root;
	
	/*
	 * Static inner node class
	 * */
	private class Node
	{
		int data;
		Node left;
		Node right;
		
		Node(int newData)
		{
			data = newData;
			left = null;
			right = null;
		}
	}
	
	/*BinaryTree constructor*/
	public  BinaryTree()
	{
		root = null;
	}
	
	/*
	 * Binary Tree - insert()
	 * */
	public void insert(int data)
	{
		this.root = insert(this.root, data);
		
	}
	private Node insert(Node nd, int data)
	{
		if(nd == null)
		{
			nd = new Node(data);
			return nd;
		}
		
		if(nd.data > data)
		{
			nd.left = insert(nd.left, data);
		}
		else
		{
			nd.right = insert(nd.right, data);
		}
		
		return nd;
	}
	
	/*
	 * BinaryTree - lookup
	 * */
	
	public boolean lookup(int data)
	{
		return (lookup(this.root, data));
	}
	
	private boolean lookup(Node nd, int data)
	{
		if(nd == null) return false;
		
		if(nd.data == data) return true;
		
		if(nd.data < data) return (lookup(nd.left, data));
		
		//else
		return (lookup(nd.right, data));
		
	}
	
	/*
	 * BinaryTree - printInOrder
	 * */
	
	public void printInOrder()
	{
		System.out.print("InOrder:");
		printInOrder(this.root);
		System.out.println();
	}
	
	public void printInOrder(Node nd)
	{
		if(nd == null) return;
		
		printInOrder(nd.left);
		System.out.print(" | "+nd.data);
		printInOrder(nd.right);
	}
	
	/*
	 * BuildOneTwoThree()
	 * */
	public void buildOneTwoThree()
	{
		root = new Node(2);
		root.left = new Node(1);
		root.right = new Node(3);
	}
	
	/*
	 * find tree size
	 * */
	public int size()
	{
		return size(this.root);
	}
	
	private int size(Node nd)
	{
		if(nd == null) return 0;
		
		return (size(nd.left) + size(nd.right)+ 1);
	}
	
	/*
	 * hasPathSum
	 * Given a Tree and sum returns true if there is a path from root down to leaf, such that adding all
	 * values along the path equals to the sum 
	 * 
	 * **/
	
	public boolean hasPathSum(int sum)
	{
		return (hasPathSum(this.root, sum));
	}
	
	private boolean hasPathSum(Node nd, int sum)
	{
		if(nd == null)
		{
			if(sum == 0) return true;
			//else
			return false;
		}
		sum = sum - nd.data;
		
		return(hasPathSum(nd.left, sum) || hasPathSum(nd.right, sum));		
	}
	
	/**
	 * mirror tree
	 *  4
	 * 2	
	 * */
	
	public void mirror()
	{
		mirror(this.root);
	}
	
	private void mirror(Node nd)
	{
		if(nd == null)
			return;
		
		mirror(nd.left);
		mirror(nd.right);
		
		Node tmp = nd.left;
		nd.left = nd.right;
		nd.right = tmp;	
		
	}
	
	/*
	 * printLevelOrder
	 * 			4
	 * 		2		7
	 * Using BFS
	 * Time Complexity - O(n) - number of nodes in the tree
	 * 
	 * curCount =  0
	 * nextCount = 2
	 * nodeQueue = 2, 7
	 * cur = 4
	 * */
	public void printLevelOrder()
	{
		if(this.root == null) return;
		
		Queue<Node> nodeQueue = new LinkedList<Node>();
		int nodeCountCurLevel = 1; //root is first node
		int nodeCountNextLevel = 0;
		nodeQueue.add(root);
		while(nodeQueue.peek() != null)//peek checks heads of the queue without removing
		{
			Node cur = nodeQueue.remove();
			nodeCountCurLevel--;
			if(cur.left != null)
			{
				nodeQueue.add(cur.left);
				nodeCountNextLevel++;
			}
			
			if(cur.right != null)
			{
				nodeQueue.add(cur.right);
				nodeCountNextLevel++;
			}
			
			//print
			System.out.print(cur.data+" \t");
			if(nodeCountCurLevel == 0)
			{
				System.out.println();
				nodeCountCurLevel = nodeCountNextLevel;
				nodeCountNextLevel = 0;
			}
		}//while ends
	}
	/************************************************************************/
	/* test methods*/
	
	public static void testInsertAndInOrder()
	{
		BinaryTree bt1 = new BinaryTree();
		System.out.println("Test1: null tree");
		bt1.printInOrder();
		System.out.println("Test2: Normal tree");
		bt1.buildOneTwoThree();
		System.out.println("PrintInOrder: ");
		bt1.printInOrder();
		System.out.println("Test3: Imbalanced");
		BinaryTree bt2 = new BinaryTree();
		bt2.insert(1);
		bt2.printInOrder();
		bt2.insert(2);
		bt2.printInOrder();
		bt2.insert(3);
		bt2.insert(7);
		bt2.printInOrder();
		System.out.println("Test4: Mixed Treee");
		bt2.insert(0);
		bt2.insert(5);
		bt2.insert(8);
		bt2.printInOrder();		
	}
	
	public static void testSize()
	{
		BinaryTree bt1 = new BinaryTree();
		System.out.println("Test1: Empty Tree 7= "+bt1.size());
		bt1.size();
		bt1.buildOneTwoThree();
		System.out.println("Test2: 3 Node tree = "+bt1.size());
		bt1.insert(5);
		bt1.insert(7);
		bt1.insert(10);
		System.out.println("Test3: 6 Node tree = "+bt1.size());
	}
	
	public static void testHasPathSum()
	{
		System.out.println("Test1: The sum exists");
		BinaryTree bt1 = new BinaryTree();
		bt1.insert(5);
		bt1.insert(4);
		bt1.insert(7);
		bt1.insert(6);
		bt1.insert(8);
		bt1.insert(3);
		bt1.insert(2);
		System.out.println("Tree:");
		bt1.printInOrder();
		System.out.println("bt1 has path sum 12: "+bt1.hasPathSum(12));
		System.out.println("bt1 has path sum 21: "+bt1.hasPathSum(21));
		System.out.println("bt1 has path sum 20: "+bt1.hasPathSum(20));
		BinaryTree bt2 = new BinaryTree();
		System.out.println("bt2 has path sum 20: "+bt2.hasPathSum(20));
	}
	public static void testPrintLevelOrder()
	{
		BinaryTree bt1 = new BinaryTree();
		bt1.buildOneTwoThree();
		bt1.printLevelOrder();
	}
	/* main starts*/
	public static void main(String args[])
	{
		//testInsertAndInOrder();
		//testSize();
		//testHasPathSum();
		testPrintLevelOrder();
	}
}
