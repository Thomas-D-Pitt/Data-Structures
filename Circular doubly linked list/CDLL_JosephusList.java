import java.io.*;
import java.util.*;

public class CDLL_JosephusList<T>
{
	private CDLL_Node<T> head;  // pointer to the front (first) element of the list
	private int count=0;
	// private Scanner kbd = new Scanner(System.in); // FOR DEBUGGING. See executeRitual() method 
	public CDLL_JosephusList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE
	
	public CDLL_JosephusList( String infileName ) throws Exception
	{
		BufferedReader infile = new BufferedReader( new FileReader( infileName ) );	
		while ( infile.ready() )
		{	@SuppressWarnings("unchecked") 
			T data = (T) infile.readLine(); // CAST CUASES WARNING (WHICH WE CONVENIENTLY SUPPRESS)
			insertAtTail( data ); 
		}
		infile.close();
	}
	

	
	// ########################## Y O U   W R I T E / F I L L   I N   T H E S E   M E T H O D S ########################
	
	// TACK ON NEW NODE AT END OF LIST
	@SuppressWarnings("unchecked")
	public void insertAtTail(T data)
	{
		CDLL_Node<T> newNode = new CDLL_Node( data,null,null);
		if (head==null)
		{
			newNode.next=newNode;
			newNode.prev=newNode;
			head = newNode;
			return;
		}
		head.prev.next = newNode;
		newNode.prev = head.prev;
		head.prev = newNode;
		newNode.next = head;
	}

	
	public int size()
	{	
		if (head == null)
			return 0;

		int count = 0;
		CDLL_Node<T> cur = head;
		do{

			count++;
			cur = cur.next;

		}while (cur != head);

		return count;
	}
	
	// RETURN REF TO THE FIRST NODE CONTAINING  KEY. ELSE RETURN NULL
	public CDLL_Node<T> search( T key )
	{	
		if (head == null)
			return null;

		CDLL_Node<T> cur = head;
		do{

			if (cur.data.equals(key))
				return cur;

			cur = cur.next;

		}while (cur != head);

		return null;
	}
	
	// RETURNS CONATENATION OF CLOCKWISE TRAVERSAL
	@SuppressWarnings("unchecked")
	public String toString()
	{
		if (head == null)
			return "";

		CDLL_Node<T> cur = head;
		String s = "" + cur.data;
		cur = cur.next;

		while (cur != head){

			s += "<=>" + cur.data;
			cur = cur.next;

		};

		return s;
		
	}
	
	void removeNode( CDLL_Node<T> deadNode )
	{
		deadNode.prev.next = deadNode.next;
		deadNode.next.prev = deadNode.prev;
	}
	
	public void executeRitual( T first2Bdeleted, int skipCount )
	{
		int size = size();
		if (size < 1 ) return;
		CDLL_Node<T> cur = search( first2Bdeleted );
		if ( cur==null ) return;
		
		// OK THERE ARE AT LEAST 2 NODES AND CURR IS SITING ON first2Bdeleted
		do
		{
			System.out.println( "stopping on "+cur.data+" to delete "+cur.data);
			
			removeNode(cur);
			size -= 1;
			if (head == cur){
				if (skipCount > 0)
					head = cur.next;
				else
					head = cur.prev;
			}

			System.out.println("deleted. list now:   " + toString());
			
			if (size <= 1)
				break;
			
			if (skipCount > 0)
				cur = cur.next;
			else
				cur = cur.prev;
			
			System.out.println("resuming at "+cur.data+", skipping "+cur.data+" + "+(Math.abs(skipCount) - 1)
				+" nodes "+(skipCount>0 ? "CLOCKWISE" : "COUNTER_CLOCKWISE")+" after");
			
			for (int i = 0; i < Math.abs(skipCount); i++){
				if (skipCount > 0)
					cur = cur.next;
				else
					cur = cur.prev;
			}
			
			// String junk = kbd.nextLine();  <= MIGHT FIND THis HELPFUL. FOR DEBUGGING. WAITS FOR YOU TO HIT RETUN KEY
			
		}
		while (size() > 1 );

	}
	
} // END CDLL_LIST CLASS

class CDLL_Node<T>
{
  T data; // DONT DEFINE MEMBERS AS PUBLIC OR PRIVATE
  CDLL_Node<T> prev, next; //
  CDLL_Node() 		{ this( null, null, null ); }
  CDLL_Node(T data) { this( data, null, null);  }
  CDLL_Node(T data, CDLL_Node<T> prev, CDLL_Node<T> next)
  {	this.data=data; this.prev=prev; this.next=next;
  }
  public String toString() // TOSTRING MUST BE PUBLIC
  {	return ""+data;
  }
} //END NODE CLASS