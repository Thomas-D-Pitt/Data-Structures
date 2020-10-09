public class BSTesterL5
{
	public static void main( String[] args ) throws Exception
	{
		BSTreeL5<String> tree1 = new BSTreeL5<String>( args[0] );
		System.out.format( "\ntree1 loaded from %s contains %d nodes:\n", args[0], tree1.countNodes() ); 
		System.out.print("INORDER print of tree1: ");
		tree1.printInOrder();
		System.out.print("PRE ORDER print of tree1: ");
		tree1.printPreOrder();
		System.out.print("POST ORDER print of tree1: ");
		tree1.printPostOrder();
		System.out.print("LEVEL ORDER print of tree1: ");
		tree1.printLevelOrder();		
	}
}