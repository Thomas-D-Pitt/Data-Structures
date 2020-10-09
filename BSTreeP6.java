import java.io.*;
import java.util.*;

///////////////////////////////////////////////////////////////////////////////
class BSTNode<T>
{	T key;
	BSTNode<T> left,right;
	BSTNode( T key, BSTNode<T> left, BSTNode<T> right )
	{	this.key = key;
		this.left = left;
		this.right = right;
	}
}
///////////////////////////////////////////////////////////////////////////////////////
class Queue<T>
{	LinkedList<BSTNode<T>> queue;
	Queue() { queue =  new LinkedList<BSTNode<T>>(); }
	boolean empty() { return queue.size() == 0; }
	void enqueue( BSTNode<T>  node ) { queue.addLast( node ); }
	BSTNode<T>  dequeue() { return queue.removeFirst(); }
	// THROWS NO SUCH ELEMENT EXCEPTION IF Q EMPTY
}
////////////////////////////////////////////////////////////////////////////////
class BSTreeP6<T>
{
	private BSTNode<T> root;
	private int nodeCount;
	private boolean addAttemptWasDupe=false;

	public BSTreeP6()
	{
		nodeCount = 0;
		root=null;
	}

	@SuppressWarnings("unchecked")
	public BSTreeP6( String infileName ) throws Exception
	{
		nodeCount = 0;
		root=null;
		Scanner infile = new Scanner( new File( infileName ) );
		while ( infile.hasNext() )
			add( (T) infile.next() ); // THIS CAST RPODUCES THE WARNING
		infile.close();
	}

	// DUPES BOUNCE OFF & RETURN FALSE ELSE INCR COUNT & RETURN TRUE
	@SuppressWarnings("unchecked")
	public boolean add( T key )
	{	addAttemptWasDupe=false;
		root = addHelper( this.root, key );
		return !addAttemptWasDupe;
	}
	@SuppressWarnings("unchecked")
	private BSTNode<T> addHelper( BSTNode<T> root, T key )
	{
		if (root == null) return new BSTNode<T>(key,null,null);
		int comp = ((Comparable)key).compareTo( root.key );
		if ( comp == 0 )
			{ addAttemptWasDupe=true; return root; }
		else if (comp < 0)
			root.left = addHelper( root.left, key );
		else
			root.right = addHelper( root.right, key );

		return root;
  } // END addHelper

	public int size()
	{
		return nodeCount; // LOCAL VAR KEEPING COUNT
	}

	public int countNodes() // DYNAMIC COUNT ON THE FLY TRAVERSES TREE
	{
		return countNodes( this.root );
	}
	private int countNodes( BSTNode<T> root )
	{
		if (root==null) return 0;
		return 1 + countNodes( root.left ) + countNodes( root.right );
	}

	// INORDER TRAVERSAL REQUIRES RECURSION
	public void printInOrder()
	{
		printInOrder( this.root );
		System.out.println();
	}
	private void printInOrder( BSTNode<T> root )
	{
		if (root == null) return;
		printInOrder( root.left );
		System.out.print( root.key + " " );
		printInOrder( root.right );
	}

	public void printLevelOrder()
	{
		if (this.root == null) return;
		Queue<T> q = new Queue<T>();
		q.enqueue( this.root ); // this. just for emphasis/clarity
		while ( !q.empty() )
		{	BSTNode<T> n = q.dequeue();
			System.out.print( n.key + " " );
			if ( n.left  != null ) q.enqueue( n.left );
			if ( n.right != null ) q.enqueue( n.right );
		}
	}

  public int countLevels()
  {
    return countLevels( root );
  }
  private int countLevels( BSTNode root)
  {
    if (root==null) return 0;
    return 1 + Math.max( countLevels(root.left), countLevels(root.right) );
  }

  public int[] calcLevelCounts()
  {
    int levelCounts[] = new int[countLevels()];
    calcLevelCounts( root, levelCounts, 0 );
    return levelCounts;
  }
  private void calcLevelCounts( BSTNode root, int levelCounts[], int level )
  {
    if (root==null)return;
    ++levelCounts[level];
    calcLevelCounts( root.left, levelCounts, level+1 );
    calcLevelCounts( root.right, levelCounts, level+1 );
  }

	//////////////////////////////////////////////////////////////////////////////////////
	// # # # #   WRITE THE REMOVE METHOD AND ALL HELPERS / SUPPORTING METHODS   # # # # #

	// return true only if it finds/removes the node
	public boolean remove( T key2remove )
	{
		if (this.root == null)
			return false;
		
		if (this.root.key.equals(key2remove)){
			if (this.root.left == null && this.root.right == null){
				this.root = null;
				return true; 
			}
			else if (this.root.left == null){
				this.root = this.root.right;
				return true; 
			}
			else if (this.root.right == null){
				this.root = this.root.left;
				return true; 
			}
			else{
				return removeNodeWithChildren(this.root);
			}
		}
		

		BSTNode<T> parent = getParent(key2remove);
		if (parent == null)
			return false;

		if (parent.left != null && parent.left.key.equals(key2remove)){
			if (parent.left.left == null && parent.left.right == null){
				parent.left = null;
				return true; 
			}
			else if (parent.left.left == null){
				parent.left = parent.left.right;
				return true; 
			}
			else if (parent.left.right == null){
				parent.left = parent.left.left;
				return true; 
			}
			else{
				return removeNodeWithChildren(parent.left);
			}
				
		}
		else if (parent.right != null && parent.right.key.equals(key2remove)){
			if (parent.right.left == null && parent.right.right == null){
				parent.right = null;
				return true;
			}
			else if (parent.right.left == null){
				parent.right = parent.right.right;
				return true; 
			}
			else if (parent.right.right == null){
				parent.right = parent.right.left;
				return true; 
			}
			else{
				return removeNodeWithChildren(parent.right);
			}
				
		}

		return false;

	}

	private boolean removeNodeWithChildren(BSTNode<T> node){
		BSTNode<T> swapNode = node.left;
		while (swapNode.right != null)
			swapNode = swapNode.right;

		remove(swapNode.key);
		node.key = swapNode.key;
		return true;
	}

	@SuppressWarnings("unchecked")
	private BSTNode<T> getParent(T key2remove){

		BSTNode<T> cur = this.root;
		while ((cur.left == null || !cur.left.key.equals(key2remove)) && (cur.right == null || !cur.right.key.equals(key2remove))){

			Comparable cKey = (Comparable)cur.key;
			if (cKey.compareTo(key2remove) > 0)
				cur = cur.left;
			else
				cur = cur.right;

			if (cur == null || (cur.left == null && cur.right == null))
				return null;

		}

		return cur;
	}

} // END BSTREEP6 CLASS
