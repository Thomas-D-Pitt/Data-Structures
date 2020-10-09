import java.io.*;
import java.util.*;

public class LinkedList<T>
{
	private Node<T> head;  // pointer to the front (first) element of the list

	public LinkedList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// COPY ALL NODES FROM OTHER LIST INTO THIS LIST. WHEN COMPLETED THIS LIST IDENTICAL TO OTHER
	public LinkedList( LinkedList<T> other )
	{
		Node<T> pos = other.head;
		while (pos != null){
			//insertAtTail(pos.getData());
			//pos = pos.getNext();
		}
	}

	// LOAD LINKED LIST FROM INCOMING FILE
	@SuppressWarnings("unchecked")
	public LinkedList( String fileName ) 
	{
		try 
		{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{  
				insertAtTail( (T)infile.readLine() );  
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

		for (Node<T> curr = head; curr != null; curr = curr.getNext())
		{
			toString += curr.getData();		// WE ASSUME OUR T TYPE HAS toString() DEFINED
			if (curr.getNext() != null)
				toString += " -> ";
		}

		return toString + "\n";
	}

	// ########################## Y O U   W R I T E    T H E S E    M E T H O D S ########################

	// TACK A NEW NODE (CABOOSE) ONTO THE END OF THE LIST
	public void insertAtTail(T data)
	{
		//IF THERE ARE NO NODES IN LIST TACK THIS ONE RIGHT ONTO THE HEAD
		//ELSE GET A REF TO THE VERY LAST NODE AND HANG IT OFF THE LAST NODE'S NEXT REF

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

	// OF COURSE MORE EFFICIENT TO KEEP INTERNAL COUNTER BUT YOU COMPUTE IT DYNAMICALLY WITH A TRAVERSAL LOOP
	public int size()
	{	
		int count = 0;
		Node<T> pos = head;
		while (pos != null){
			pos = pos.getNext();
			count++;
		}
		return count;
	}
	
	// MUST CALL SEARCH AND IF SEARCH RETURNS NULL, THIS METHOD RETURNS FALSE, OTHERWIASE RETURN TRUE
	public boolean contains( T key )
	{
		return search(key) != null;
	}

	// TRAVERSE LIST FRONT TO BACK LOOKING FOR THIS DATA VALUE.
	// RETURN REF TO THE FIRST NODE THAT CONTAINS THIS KEY. DO -NOT- RETURN REF TO KEY ISIDE NODE
	// RETURN NULL IF NOT FOUND
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

} //EOF