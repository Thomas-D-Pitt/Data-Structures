import java.io.*;
import java.util.*;

public class LinkedList<T>
{
	private Node<T> head;  // pointer to the front (first) element of the list

	public LinkedList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE
	@SuppressWarnings("unchecked")	
	public LinkedList( String fileName, boolean orderedFlag )
	{
		head=null;
		try
		{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{
				if (orderedFlag)
					insertInOrder( (T)infile.readLine() );  // WILL INSERT EACH ELEM INTO IT'S SORTED POSITION
				else
					insertAtTail( (T)infile.readLine() );  // TACK EVERY NEWELEM ONTO END OF LIST. ORIGINAL ORDER PRESERVED
			}
			infile.close();
		}
		catch( Exception e )
		{
			System.out.println( "FATAL ERROR CAUGHT IN C'TOR: " + e );
			System.exit(0);
		}
	}

	//-------------------------------------------------------------

	// inserts new elem at front of list - pushing old elements back one place
	public void insertAtFront(T data)
	{
		head = new Node<T>(data,head);
	}

	// we use toString as our print


	public String toString()
	{
		String toString = "";

		for (Node curr = head; curr != null; curr = curr.getNext())
		{
			toString += curr.getData();		// WE ASSUME OUR T TYPE HAS toString() DEFINED
			if (curr.getNext() != null)
				toString += " ";
		}

		return toString;
	}

	// ########################## Y O U   W R I T E    T H E S E    M E T H O D S ########################

	
	public int size() // OF COURSE MORE EFFICIENT TO MAINTAIN COUNTER. BUT YOU WRITE LOOP!
	{
		int count = 0;
		Node<T> pos = head;
		while (pos != null){
			pos = pos.getNext();
			count++;
		}
		return count;
	}


	public boolean empty()
	{
		return (size() == 0); // CHANGE TO YOUR CODE
	}

	
	public boolean contains( T key )
	{
		return search(key) != null;
	}

	
	public Node<T> search( T key )
	{
		Node<T> pos = head;
		while (pos != null){
			if (pos.getData().equals(key)){
				return pos;
			}
			pos = pos.getNext();
		}
		return null;
	}

	
	public void insertAtTail(T data) // TACK A NEW NODE (CABOOSE) ONTO THE END OF THE LIST
	{
		if (head == null){
			head = new Node<T>(data);
			return;
		}

		Node<T> pos = head;
		while (pos.getNext()!= null){
			pos = pos.getNext();
		}
		pos.setNext(new Node<T>(data));
	}

	@SuppressWarnings("unchecked")
	public void insertInOrder(T  data) // PLACE NODE IN LIST AT ITS SORTED ORDER POSTIOPN
	{
		Comparable cData = (Comparable)data;
		
		if (this.contains(data))
			return;

		if (head == null || cData.compareTo(head.getData()) < 0){
			insertAtFront(data);
			return;
		}
			
		Node<T> pos = head;
		while (pos.getNext() != null && cData.compareTo(pos.getNext().getData()) > 0){
			pos = pos.getNext();

			
		}
		pos.setNext(new Node<T>(data, pos.getNext()));
	}
	
	
	public boolean remove(T key) // FIND/REMOVE 1st OCCURANCE OF A NODE CONTAINING KEY
	{
		Node<T> pos = head;

		if (head != null) {

			if( head.getData().equals(key)){
				
				removeAtFront();
				return true;
			}
			while (pos.getNext() != null){
				if (pos.getNext().getData().equals(key)){
					pos.setNext(pos.getNext().getNext());
					return true;
				}
				pos = pos.getNext();
			}	

			
		}
		return false;
	}

	
	public boolean removeAtTail()	// RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if (head == null){
			return false;
		}
		else if (head.getNext() == null){
			head = null;
			return true;
		}

		Node<T> pos = head;
		while (pos.getNext().getNext() != null){
			pos = pos.getNext();
		}
		pos.setNext(null);
		return true;
	}

	
	public boolean removeAtFront() // RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if (head != null){
			head = head.getNext();
			return true;
		}
		return false;
	}


	public LinkedList<T> union( LinkedList<T> other )
	{
		LinkedList<T> result = new LinkedList<T>();

		Node<T> pos = this.head;
		while (pos != null){
			result.insertInOrder(pos.getData());
			pos = pos.getNext();
		}

		pos = other.head;
		while (pos != null){
			result.insertInOrder(pos.getData());
			pos = pos.getNext();
		}

		return result;
	}
	
	
	public LinkedList<T> inter( LinkedList<T> other )
	{
		LinkedList<T> result = new LinkedList<T>();

		Node<T> pos = this.head;
		while (pos != null){
			if (other.contains(pos.getData()))
				result.insertInOrder(pos.getData());
			pos = pos.getNext();

			
		}
		return result;
	}


	public LinkedList<T> diff( LinkedList<T> other )
	{
		LinkedList<T> result = new LinkedList<T>();

		Node<T> pos = this.head;
		while (pos != null){
			if (!other.contains(pos.getData()))
				result.insertInOrder(pos.getData());
			pos = pos.getNext();

			
		}
		return result;
	}

	
	public LinkedList<T> xor( LinkedList<T> other )
	{
		return  this.union(other).diff(this.inter(other)); // CHANGE TO YOUR CODE
	}

} //END LINKEDLIST CLASS

