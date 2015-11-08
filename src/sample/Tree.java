package sample;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjamintoofer on 11/4/15.
 */

/*
    Tree Class contains Node Class
    Tree Class is used in the SubjectTreeeView Class
    Its meant to represent a model of the SubjectTreeView
 */
public class Tree<T> {

    private Node rootNode;

    public Tree(T root){

        /*
            Set the roots nodes position and path
            initialize its children list
         */

        ((Subject)root).setPosition(0);
        ((Subject) root).setCurrentColor(null);
        rootNode = new Node(root);
        rootNode.children = new ArrayList<Node<T>>();
        int rootPosition = ((Subject)rootNode.getData()).getPosition();
        ((Subject)rootNode.getData()).setPath("."+rootPosition);

    }


    /*
        Get root node of the tree
     */
    public Node getRootElement() {

        return this.rootNode;
    }
    public ArrayList<T> getChildrenFromObject(T obj){

        Node<T> node = findNode(obj);
        ArrayList<T> list = new ArrayList<T>();

        return getChildrenFromObject(node,list);

    }

    private ArrayList<T> getChildrenFromObject(Node<T> node, ArrayList<T> list){



        for(Node<T> n : node.getChildren()){
            getChildrenFromObject(n,list);
        }
        list.add(node.getData());

        return list;
    }

    public ArrayList<T> getLeavesFromObject(T obj){

        Node<T> node = findNode(obj);
        ArrayList<T> list = new ArrayList<T>();

        return getLeavesFromObject(node, list);
    }

    private ArrayList<T> getLeavesFromObject(Node<T> node, ArrayList<T> list){

        if(((Subject)node.getData()).isLeaf()){
            list.add(node.getData());
            //return list;
        }else{

            for(Node<T> n : node.getChildren()){
                getChildrenFromObject(n,list);
            }
        }
        return list;
    }

    public void updateParentAssociations(T t){

        Node<T> node = findNode(t);
        updateParentAssociations(node.getParent());
    }

    private void updateParentAssociations(Node<T> node){

        if(node  != null){

            int counter = 0;
            for(Node<T> n : node.getChildren()){
                if(!((Subject)n.getData()).isLeaf()){
                    if(((Subject)n.getData()).getNumberOfChildrenAssociated() == ((Subject)n.getData()).getNumberOfChildren()){
                        counter++;
                    }
                }else{
                    if(((Subject)n.getData()).isAssociated()){
                        counter++;
                    }

                }

            }
            ((Subject)node.getData()).setNumberOfChildrenAssociated(counter);
            System.out.println("Node Name: "+((Subject)node.getData()).getName()+" "+((Subject)node.getData()).getNumberOfChildrenAssociated());
            updateParentAssociations(node.getParent());
        }
    }
    ////////////////////////////////////////////////////
    /*
        Add elements from the tree

        1. Finds the passed in element in the tree
        2. Adds the new child to te found element
     */
    public T addElement(T element, T newChildToAdd){

        Node<T> childToFind = findNode(element);
        Node<T> addNode = new Node<T>(newChildToAdd);

        ((Subject)childToFind.getData()).setLeaf(false);
        childToFind.addChild(addNode);

        return newChildToAdd;
    }
    ////////////////////////////////////////////////////
    /*
       Removes elements from the tree

       1. Finds the passed in element in the tree
       2. If the found element's parent is not null,
          then the element will be removed
    */
    public void removeElement(T element){

        Node<T> childToFind = findNode(element);


        if(childToFind.getParent() != null){

            //If parent has 1 child then set it as a leaf since last child is about to be removed
            if(childToFind.getParent().getNumberOfChildren() == 1){

                ((Subject)childToFind.getParent().getData()).setLeaf(true);
            }
            childToFind.getParent().getChildren().remove(childToFind);
            System.out.println(((Subject)childToFind.getData()).getName());
            ((Subject)childToFind.getParent().getData()).decrementNumberOfChildren();
        }

    }
    ////////////////////////////////////////////////////
    /*
        Starts the search for the passed in node

        1.gets the path of the lookForNode
        2. pass the path and rootNode to start the search
     */
    public Node<T> findNode(T lookForNode){
        String path = ((Subject)lookForNode).getPath();

        return traverseTree(path,rootNode);
    }

    /*
        Recursively traverse through the tree with the given path
        (Recursively done in Depth First Search)

        - currentPosition = current position of the path we are looking for
        - subPath =  rest of the path
        -num = the numerical conversion of the currentPosition

        1. path is split by the "." into currentPosition and subPath
            Ex: Let path be 1.2.0.0
            currentPosition = 0
            subPath = 1.2.0

        2. Check if subPath is empty meaning we have reached
        the end of the path and compare the currentPosition value
        to the current nodes position

        3. Else if it did not reach the end of the path, still
        check currentPosition to the current nodes position

            If true, then traverse through the current nodes
            children with the the subPath

     */
    private Node<T> traverseTree(String path,Node<T> node){

        int index = path.lastIndexOf(".");
        String currentPosition = path.substring(index+1,path.length());
        String subPath = path.substring(0,index);
        int num = Integer.parseInt(currentPosition);
        Node<T> nodeToReturn = null;

        if (subPath.equals("")){
            if(((Subject)node.getData()).getPosition() == num){
                nodeToReturn =  node;
            }

        }else{

            if(((Subject)node.getData()).getPosition() == num){
                for(Node<T> n : node.getChildren()){
                    nodeToReturn =  traverseTree(subPath,n);
                    if(nodeToReturn != null){
                        return nodeToReturn;
                    }
                }
            }
        }
        return nodeToReturn;
    }
    ////////////////////////////////////////////////////
    /*
        ********printTree is used for Debugging********

        Prints the current state of the tree

        - newString = buffered string that is appended to

        Public printTree starts it off by calling the private
        printTree from the rootNodes children and passes newString

     */
    public String printTree(){

        StringBuilder newString = new StringBuilder();
        newString.append(rootNode.toString());
        ArrayList<Node<T>> list = (ArrayList<Node<T>>) rootNode.getChildren();
        for(Object n : rootNode.getChildren()){

            newString.append(printTree(newString,(Node<T>)n,1));
        }
        //System.out.println(newString.toString());
        return newString.toString();
    }

    private String printTree(StringBuilder string,Node<T> node,int depth){

        String tab = "";
        String returnString = "";
        for(int i =0; i < depth; i++){
            tab = tab+"\t";
        }
        if(node.getNumberOfChildren() == 0){
            string.append("\n"+tab+"- "+node.toString()+" "+((Subject)node.getData()).getNumberOfChildrenAssociated()+"/"+((Subject)node.getData()).getNumberOfChildren()+" "+((Subject)node.getData()).isAssociated());
        }else{
            string.append("\n"+tab+"- "+node.toString()+" "+((Subject)node.getData()).getNumberOfChildrenAssociated()+"/"+((Subject)node.getData()).getNumberOfChildren()+" "+((Subject)node.getData()).isAssociated());

            for(Node<T> n: node.getChildren()){

                returnString= printTree(string, (Node<T>)n,depth+1);
            }
        }
        //System.out.println(string.toString());
        return returnString;
    }
    ////////////////////////////////////////////////////





    /*
        Node Class

        - data = Subject object
        - parent = the parent node to this node
        - children = children nodes of this node
        - childIndex = index of the position of the last child added
     */
    public static class Node<T>{

        private T data;
        private Node parent;
        private ArrayList<Node<T>> children;
        private int childIndex = 0;

        public Node(T data){
            this.data = data;
            children = new ArrayList<Node<T>>();
        }

        public List<Node<T>> getChildren(){

            if(children == null){
                children = new ArrayList<Node<T>>();
            }
            return children;
        }

        public int getNumberOfChildren(){

            if(children == null){
                return 0;
            }
            return children.size();
        }

        public void addChild(Node child){

            if(children == null){
                children = new ArrayList<Node<T>>();
            }
            child.setParent(this);
            ((Subject)child.getData()).setPath("." + (this.childIndex)+((Subject) this.getData()).getPath());
            ((Subject)child.getData()).setPosition(this.childIndex);
            ((Subject)this.getData()).incrementNumberOfChildren();
            this.childIndex++;
            children.add(child);
        }

        public T getData() {
            return this.data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getParent()
        {
            return parent;
        }

        public void setParent(Node parent)
        {
            this.parent = parent;
        }

        public String toString(){
            return ((Subject)data).getName();
        }
    }
}
