package RedBlackTree;

import java.util.Queue;
import java.util.LinkedList;

public class RedBlackTree 
{
	Node root;

	public RedBlackTree()
	{
		// TODO Auto-generated constructor stub
		root = null;
	}

	public void insert(int data)
	{
		Node tmp = new Node(data);
		insert(tmp);
	}

	private void insert(Node nd)
	{
		this.root = insert(this.root, nd);
	}

	private Node insert(Node cur, Node nd)
	{
		if(cur == null)
		{
			//nd = new Node(data);
			cur = nd;
		}
		else
		{
			if(nd.data <= cur.data)
			{
				cur.left = insert(cur.left, nd);
				cur.left.parent = cur;
			}
			else
			{
				cur.right = insert(cur.right, nd);
				cur.right.parent = cur;
			}
		}

		return cur;
	}

	public void printLevelOrder()
	{
		if(this.root == null) return;

		Queue<Node> nodeQueue = new LinkedList<Node>();
		int countNodeCurLevel = 1;
		int countNodeNextLevel = 0;
		nodeQueue.add(this.root);

		while(nodeQueue.peek() != null)
		{
			Node cur = nodeQueue.remove();
			countNodeCurLevel--;

			if(cur.left != null)
			{
				nodeQueue.add(cur.left);
				countNodeNextLevel++;
			}

			if(cur.right != null)
			{
				nodeQueue.add(cur.right);
				countNodeNextLevel++;
			}

			//print data
			//System.out.print(cur.data+"\t");
			System.out.print(cur+"\t");
			if(countNodeCurLevel == 0)
			{
				System.out.println();
				countNodeCurLevel = countNodeNextLevel;
				countNodeNextLevel = 0;
			}
		}//end of nodeQueue while

	}

	public Node getParent(int nd)
	{
		return getParent(this.root, nd);
	}

	private Node getParent(Node tree, int nd)
	{
		if(tree == null) return null;

		if(tree.data == nd) return tree.parent;

		if(tree.data > nd) return (getParent(tree.left, nd));

		return (getParent(tree.right, nd));

	}

	/*
	 * minTreeNode
	 * get the smallest node element in the tree
	 * 
	 * **/

	public Node minTreeNode()
	{
		return (minTreeNode(this.root));
	}

	private Node minTreeNode(Node nd)
	{
		if(nd == null) return null;

		while(nd.left != null)
		{
			nd = nd.left;
		}

		return nd;

	}
	
	/*
	 * maxTreeNode
	 * get the smallest node element in the tree
	 * 
	 * **/

	public Node maxTreeNode()
	{
		return (maxTreeNode(this.root));
	}

	private Node maxTreeNode(Node nd)
	{
		if(nd == null) return null;

		while(nd.right != null)
		{
			nd = nd.right;
		}

		return nd;

	}

	/**
	 * rightRotate
	 *     5
	 * 4		7
	 * */

	public void rightRotate(Node nd)
	{
		if(nd == null) return;

		if(nd.left == null) return;

		Node leftChild = nd.left;

		if(nd == this.root)//rotate on root root changes
			this.root = leftChild;

		nd.left = leftChild.right;
		if(nd.left != null)
			nd.left.parent = nd;

		leftChild.right = nd;
		leftChild.parent = nd.parent;
		nd.parent = leftChild;

		Node parent = leftChild.parent;

		if(parent != null)
		{
			if(parent.left == nd)
				parent.left = leftChild;
			else
				parent.right = leftChild;
		}
	}

	/**
	 * leftRotate()
	 * */
	public void leftRotate(Node nd)
	{
		if(nd == null) return;

		if(nd.right == null) return;

		Node rightChild = nd.right;

		if(nd == this.root) //update the root
			this.root = rightChild;

		nd.right = rightChild.left;
		if(nd.right != null)
			nd.right.parent = nd;
		rightChild.left = nd;
		rightChild.parent = nd.parent;
		nd.parent = rightChild;
		Node parent = rightChild.parent;
		if(parent != null)
		{
			if(parent.left == nd)
				parent.left = rightChild;
			else
				parent.right = rightChild;
		}

	}


	/*
	 * getNode() - returns node ref with given integer
	 * */
	public Node getNode(int data)
	{
		return (getNode(this.root, data));
	}

	private Node getNode(Node nd, int data)
	{
		if(nd == null) return null;

		if( data == nd.data) return nd;

		if(data < nd.data) 
			return getNode(nd.left, data);

		//else
		return getNode(nd.right, data);
	}

	/*
	 * getSuccessor(Node nd)
	 * 
	 * get node successor
	 * */

	public Node getSuccessor(Node nd)
	{
		if(nd == null) return null;

		if(nd.right != null)
		{
			return (minTreeNode(nd.right));
		}

		Node parent = nd.parent;
		while(parent != null && parent.left != nd)
		{
			nd = parent;
			parent = nd.parent;
		}

		if(nd == this.root) return null;

		return parent;
	}

	/*
	 * RB-Insert - red black insert
	 * 
	 * 			C								C
	 * 		A		D leftRotate 			B		D		
	 * 			B						A
	 * 
	 * 			C								C
	 * 		D		A right rotate 			D		B
	 * 			B										A
	 * */
	public void RB_Insert(int data)
	{
		Node newNode = new Node(data);
		RB_Insert(newNode);
	}
	private void RB_Insert(Node x)
	{
		if(x == null) return;
		insert(x);//insert(this.root, x)
		/*
		 * first 2 levels initially all black
		 * This is choice - this if not absolutely required to maintain RB property
		 * */
		if(x.parent == null || x.parent.parent == null)
			return;

		x.color = Color.RED;
		while(x != this.root && x.color == Color.RED)
		{
			/*
			 * Exit the while loop if
			 * parent is null or parent color is black or there is no grand parent
			 * */
			if(x.parent == null || x.parent.color == Color.BLACK || x.parent.parent == null) break;

			if(x.parent.parent.left == x.parent)
			{
				//uncle is right child of grand parent
				Node y = x.parent.parent.right;
				if(y != null && y.color == Color.RED)
				{
					//case 1 - recoloring
					x.parent.color = Color.BLACK;
					y.color        = Color.BLACK;
					x.parent.parent.color = Color.RED;
					x = x.parent.parent;
				}
				else
				{
					if(x == x.parent.right)
					{
						//case 2 - x is right child of its parent
						//right rotate
						x = x.parent;
						leftRotate(x);				
					}
					//case 3 - x is left child of its parent
					x.parent.color = Color.BLACK;
					x.parent.parent.color = Color.RED;
					rightRotate(x.parent.parent);
					//Tree must be balanced
				}	
			}//end of left subtree if
			else
			{
				//uncle is left child of grand parent
				Node y = x.parent.parent.left;
				if(y != null && y.color == Color.RED)
				{
					//case 1 - recoloring
					x.parent.color = Color.BLACK;
					y.color        = Color.BLACK;
					x.parent.parent.color = Color.RED;
					x = x.parent.parent;
				}//case 1 if
				else
				{
					if(x == x.parent.left)
					{
						//case 2
						x = x.parent;
						rightRotate(x);
					}
					//case 3
					x.parent.color = Color.BLACK;
					x.parent.parent.color = Color.RED;
					leftRotate(x.parent.parent);
				}//case 2 & 3 else
			}//end of right subtree else
		}// while ends
		this.root.color = Color.BLACK;
	}//RB_Insert ends

	/*
	 * BST delete tree node
	 * */
	
	public void deleteTreeNode(Node nd)
	{
		if(nd == null) return;

		Node delNode = null;
		if(nd.left == null || nd.right == null)
		{
			delNode = nd;
		}
		else
		{
			delNode = this.getSuccessor(nd);			
		}

		/*Now delNode have atleast one null child*/
		Node delNodeChild = null;
		if(delNode.left != null)
			delNodeChild = delNode.left;
		else
			delNodeChild = delNode.right;

		if(delNodeChild != null)
			delNodeChild.parent = delNode.parent;

		if(delNode.parent != null)
		{
			Node pa = delNode.parent;
			if(pa.left == delNode)
				pa.left = delNodeChild;
			else
				pa.right = delNodeChild;
		}
		else
		{
			this.root = delNodeChild;
		}

		if(delNode != nd)
		{
			nd.copyData(delNode);
		}

		delNode.releaseNode();
	}


	/*********************************/
	/* test methods*/
	public static void testInsert()
	{
		System.out.println("Insert testing");
		RedBlackTree t1 = new RedBlackTree();
		t1.insert(5);
		t1.insert(7);
		t1.insert(6);
		t1.insert(1);
		t1.insert(3);
		t1.insert(11);
		t1.insert(15);
		t1.printLevelOrder();
		System.out.println("Parent of 15: "+t1.getParent(15).data);
		System.out.println("Parent of 3: "+t1.getParent(3).data);
		System.out.println("Parent of root: "+t1.getParent(t1.root.data));
	}

	public static void testRotate()
	{
		System.out.println("RightRotate Test cases");
		System.out.println("Test1: Rotate on root");
		RedBlackTree t1 = new RedBlackTree();
		t1.insert(5);
		t1.insert(4);
		t1.insert(7);
		t1.insert(10);
		System.out.println("Before Right rotate: ");
		t1.printLevelOrder();
		Node nd = t1.root;
		t1.rightRotate(nd);
		System.out.println("After right rotate: ");
		t1.printLevelOrder();
		System.out.println("After left Rotate");
		t1.leftRotate(t1.root);
		t1.printLevelOrder();
		System.out.println("Test2: Left child is null");		
		System.out.println("Before Right rotate: ");
		t1.printLevelOrder();
		Node nd7 = t1.getNode(7);
		t1.rightRotate(nd7);
		System.out.println("After right rotate: ");
		t1.printLevelOrder();

		System.out.println("Test3: Left child is not null");		
		System.out.println("Before Right rotate: ");
		t1.insert(6);
		t1.printLevelOrder();		
		t1.rightRotate(nd7);
		System.out.println("After right rotate: ");
		t1.printLevelOrder();
	}

	public static void testRBInsert()
	{
		System.out.println("--------- RBInsert -----------");
		RedBlackTree t1 = new RedBlackTree();
		System.out.println("Test1: null tree insert");
		t1.RB_Insert(7);
		t1.printLevelOrder();
		System.out.println("--------------------");
		System.out.println("Test2: null parent inserts");
		//t1.RB_Insert(10);
		t1.RB_Insert(3);		
		t1.RB_Insert(18);
		t1.printLevelOrder();
		System.out.println("--------------------");
		System.out.println("Test3: second level below inserts");
		t1.RB_Insert(22);
		t1.RB_Insert(10);
		//t1.printLevelOrder();
		t1.RB_Insert(8);
		t1.RB_Insert(11);
		t1.RB_Insert(26);
		t1.printLevelOrder();
		System.out.println("--------------------");
		System.out.println("Test 4: insert enabling case B23");
		t1.RB_Insert(15);
		t1.printLevelOrder();
		System.out.println("--------------------");
		System.out.println("Test 5: insert when uncle is null");
		RedBlackTree t2 = new RedBlackTree();
		t2.RB_Insert(7);
		t2.RB_Insert(3);
		t2.RB_Insert(10);
		t2.RB_Insert(6);
		System.out.println("Orig:");
		t2.printLevelOrder();
		System.out.println("after insert");
		t2.RB_Insert(8);
		t2.RB_Insert(9);
		t2.RB_Insert(5);
		t2.printLevelOrder();
	}

	public static void testMinTreeNode()
	{
		System.out.println("Test min Tree Node");
		RedBlackTree t1 = new RedBlackTree();
		t1.insert(7);
		System.out.println("Test1: single node");
		System.out.println("min Node: "+t1.minTreeNode());
		t1.insert(3);
		t1.insert(10);
		System.out.println("Test2: 3 node tree");
		System.out.println("min Node: "+t1.minTreeNode());
		t1.insert(9);
		t1.insert(6);
		System.out.println("Test3: right subtree node");
		t1.printLevelOrder();
		System.out.println("min Node: "+t1.minTreeNode(t1.root.right));
		System.out.println("");
	}

	public static void testSuccessor()
	{
		System.out.println("**** Test successors *****");
		RedBlackTree t1 = new RedBlackTree();
		t1.insert(7);
		t1.insert(3);
		t1.insert(9);
		t1.insert(10);
		t1.insert(5);
		t1.insert(4);
		System.out.println("Original Tree: ");
		t1.printLevelOrder();
		System.out.println("Test1: successor of 3 should be 4: "+t1.getSuccessor(t1.getNode(3)));
		System.out.println("Test2: successor of 4 should be 5: "+t1.getSuccessor(t1.getNode(4)));
		System.out.println("Test3: successor of 5 should be 7: "+t1.getSuccessor(t1.getNode(5)));
		System.out.println("Test4: successor of 9 should be 10: "+t1.getSuccessor(t1.getNode(9)));
		System.out.println("Test1: successor of 10 should be null: "+t1.getSuccessor(t1.getNode(10)));
	}
	
	public static void testBstDelete()
	{
		System.out.println("Deletete tests");
		RedBlackTree t1 = new RedBlackTree();
		t1.insert(6);
		t1.insert(3);
		t1.insert(9);
		t1.insert(1);
		t1.insert(0);
		t1.insert(2);
		t1.insert(7);
		t1.insert(8);
		t1.insert(15);
		t1.insert(10);
		t1.insert(11);
		t1.printLevelOrder();
		System.out.println("-----------------------");
		System.out.println("Test1: delete leaf node 2" );
		t1.deleteTreeNode(t1.getNode(2));
		t1.printLevelOrder();
		System.out.println("-----------------------");
		t1.insert(2);
		System.out.println("Test2: delete right child null node with left child subtree: node 3 ");
		t1.deleteTreeNode(t1.getNode(3));
		t1.printLevelOrder();
		System.out.println("-----------------------");
		System.out.println("Test3: delete left child null node with right child subtree: node 7 ");
		t1.deleteTreeNode(t1.getNode(7));
		t1.printLevelOrder();
		System.out.println("-----------------------");
		System.out.println("Test4: delete both childs valid subtree: node 9 ");
		t1.deleteTreeNode(t1.getNode(9));
		t1.printLevelOrder();
		System.out.println("-----------------------");
		System.out.println("Test5: delete root with both childs ");
		t1.deleteTreeNode(t1.root);
		t1.printLevelOrder();
		System.out.println("-----------------------");
		System.out.println("Test6: delete root with only left childs ");
		t1.deleteTreeNode(t1.getNode(10));
		t1.deleteTreeNode(t1.getNode(15));
		t1.deleteTreeNode(t1.getNode(11));
		t1.deleteTreeNode(t1.root);
		t1.printLevelOrder();
		System.out.println("-----------------------");
		System.out.println("Test7: delete root with right child ");
		t1.deleteTreeNode(t1.getNode(0));
		t1.insert(5);
		t1.insert(4);
		t1.insert(6);
		t1.deleteTreeNode(t1.root);
		t1.printLevelOrder();
		System.out.println("-----------------------");
		System.out.println("Test8: delete root with both childs are null tree becomes null");
		RedBlackTree t2 = new RedBlackTree();
		t2.insert(10);
		t2.deleteTreeNode(t2.root);
		t2.printLevelOrder();
	}
	/*********************************/
	public static void main(String args[])
	{

		//testInsert();
		//testRotate();
		//testRBInsert();
		//testMinTreeNode();
		//testSuccessor();
		testBstDelete();
	}
}
