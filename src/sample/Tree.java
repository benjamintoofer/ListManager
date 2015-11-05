package sample;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by benjamintoofer on 11/4/15.
 */
public class Tree<T> {

    private Node rootNode;

    public Tree(T root){

        ((Subject)root).setPosition(0);
        rootNode = new Node(root);
        rootNode.children = new ArrayList<Node<T>>();
        int rootPosition = ((Subject)rootNode.getData()).getPosition();
        ((Subject)rootNode.getData()).setPath("."+rootPosition);
    }
    public Node getRootElement() {

        return this.rootNode;
    }

    public T addElement(T element, T newChildToAdd){

        Node<T> childToFind = findNode(element);
        Node<T> addNode = new Node<T>(newChildToAdd);

        childToFind.addChild(addNode);

        return newChildToAdd;
    }

    public void removeElement(T element){

        Node<T> childToFind = findNode(element);

        if(childToFind.getParent() != null){

            childToFind.getParent().getChildren().remove(childToFind);
        }

    }
    public Node<T> findNode(T lookForNode){
        String path = ((Subject)lookForNode).getPath();

        return traverseTree(path,rootNode);
    }

    private Node<T> traverseTree(String path,Node<T> node){

        int index = path.lastIndexOf(".");
        String newString = path.substring(index+1,path.length());
        String subString = path.substring(0,index);

        if (subString.equals("")){
            return node;
        }else{

            int num = Integer.parseInt(newString);
            //if(node.getChildren() != null ){
            for(Node<T> n : node.getChildren()){
                if(((Subject)n.getData()).getPosition() == num){
                    return traverseTree(subString,n);
                }

            }
        }
        return null;
    }

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
            string.append("\n"+tab+node.toString()+" "+((Subject)node.getData()).getPath());
        }else{
            string.append("\n"+tab+node.toString()+" "+((Subject)node.getData()).getPath());

            for(Node<T> n: node.getChildren()){

                returnString= printTree(string, (Node<T>)n,depth+1);
            }
        }
        //System.out.println(string.toString());
        return returnString;
    }






    /*
        Node Class
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

        public void setChildren(ArrayList<Node<T>> list){
            children = list;
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
            //Set the path of the new child with the parents plus the childs new position in the list
            ((Subject)child.getData()).setPath("." + (this.childIndex)+((Subject) this.getData()).getPath());
            ((Subject)child.getData()).setPosition(this.childIndex);
            this.childIndex++;
            children.add(child);
        }

        public void insertChildAt(int index, Node child) throws IndexOutOfBoundsException {
            if (index == getNumberOfChildren()) {
                // this is really an append
                addChild(child);
                return;
            } else {
                children.get(index); //just to throw the exception, and stop here
                children.add(index, child);
            }
        }

        public void removeChildAt(int index) throws IndexOutOfBoundsException {
            children.remove(index);
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
