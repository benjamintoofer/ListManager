package sample;


import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.*;
import java.io.Serializable;

/**
 * Created by benjamintoofer on 11/2/15.
 */
public class SubjectTreeView extends VBox implements Serializable{

    private transient TreeView<Subject> treeView;
    private transient TreeItem<Subject> rootNode = new TreeItem<>();
    private SubjectTreeModel model;
    private transient DialogBox addDialogBox;
    private transient final ContextMenu contextMenu = new ContextMenu();

    public SubjectTreeView(){

        init();
    }

    //Instantiate Objects
    private void init(){

        rootNode.setExpanded(true);
        Subject root = new Subject("Root","Root Desc");
        root.setPosition(0);
        root.setPath(".0");
        rootNode.setValue(root);
        treeView = new TreeView<>((TreeItem<Subject>) rootNode);
        treeView.setPrefSize(475, 900);
        treeView.setEditable(true);
        treeView.setCellFactory((TreeView<Subject> p) -> new CustomTreeCell());
        addDialogBox = new DialogBox("Subject",true);
        this.getChildren().add(treeView);

    }

    public ContextMenu getContextMenu(){

        return contextMenu;
    }

    public Subject getSelectedTreeItem(){

        if(treeView.getSelectionModel().getSelectedItem() != null){

            return treeView.getSelectionModel().getSelectedItem().getValue();

        }else{


            return null;
        }

    }

    public TreeItem<Subject> getTreeItem(){
        return treeView.getSelectionModel().getSelectedItem();
    }



    public TreeView<Subject> getTreeView(){
        return treeView;
    }

    public void addChildToTreeView(Subject child){

        treeView.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<Subject>(child));
        System.out.println("Parent that is adding "+treeView.getSelectionModel().getSelectedItem().getValue().getName()+"Child being added: "+child.getName());

    }

    public void removeChildFromTreeView(Subject child){

        TreeItem<Subject> temp = treeView.getSelectionModel().getSelectedItem();

        if(treeView.getSelectionModel().getSelectedItem().getParent() != null){
            treeView.getSelectionModel().getSelectedItem().getParent().getChildren().remove(temp);
        }else{
            System.err.println("Error Node:"+treeView.getSelectionModel().getSelectedItem().getValue().getName()+" has null parent and cannot be removed");
        }


    }

    public void modifyChildFromTreeView(Subject child){

        TreeItem<Subject> temp = treeView.getSelectionModel().getSelectedItem();
        System.out.println(temp+"\n"+child);
        temp.getValue().setName(child.getName());
        temp.getValue().setDescription(child.getDescription());
        //this.treeView.edit(temp);



    }

    public void updateTree(){

        treeView.refresh();
    }



    public void addModel(SubjectTreeModel model){
        this.model = model;

    }

    public void loadViewFromModel(SubjectTreeModel model){

        Queue<TreeItem<Subject>> queue = new LinkedList<TreeItem<Subject>>();
        Subject currentSubject = model.getRootNode();
        int numChildren = currentSubject.getNumberOfChildren();
        int childIndex = 1;
        int index = 0;

        ArrayList<Subject> childrenList = model.getChildrenFromNodeBFS(currentSubject);
        TreeItem<Subject> root = new TreeItem<Subject>(currentSubject);
        //treeView = new TreeView<>(root);
        treeView.setRoot(root);
        TreeItem<Subject> currentTreeItem = root;
        queue.add(currentTreeItem);
        //treeView.setRoot(currentTreeItem);

        System.out.println("LOADED LIST");

        while(!queue.isEmpty()){

            /*int level = treeView.getTreeItemLevel(currentTreeItem);
            currentTreeItem = treeView.getTreeItem(level);*/
            numChildren = childrenList.get(index).getNumberOfChildren();
            currentTreeItem = queue.poll();

            //Add children to Queue
            if(numChildren != 0){
                for(int i = 0; i < numChildren; i++){

                    Subject subjectToAdd = childrenList.get(childIndex);
                    TreeItem<Subject >newTreeItem = new TreeItem<Subject>(subjectToAdd);
                    queue.add(newTreeItem);
                    currentTreeItem.getChildren().add(newTreeItem);
                    childIndex++;
                }

            }else{

                model.updateTreeColorAssociation(currentTreeItem.getValue());
            }

            index++;

        }



        treeView.refresh();
        //treeView.setRoot();
    }

    public void expandAssociatedNodes(boolean showAssociated){

        ArrayList<Subject> list;
        Queue<TreeItem<Subject>> queue = new LinkedList<TreeItem<Subject>>();
        TreeItem<Subject> treeItem = treeView.getRoot();
        queue.add(treeItem);


        if(!showAssociated){

            list = model.getChildrenFromNodeBFS(model.getRootNode());
            for(Subject s : list){

                treeItem = queue.poll();

                if(s.getNumberOfChildren() > 0 && s.getNumberOfChildrenAssociated() < s.getNumberOfChildren()){

                    treeItem.setExpanded(true);
                }else{
                    treeItem.setExpanded(false);
                }

                if(!treeItem.getChildren().isEmpty()){

                    for(TreeItem<Subject> t : treeItem.getChildren()){

                        queue.add(t);
                    }
                }

            }
        }else{

            //Reset Tree
            collapseTree();
            list = model.getLeavesFromNode(model.getRootNode());
            TreeItem<Subject> node = treeView.getRoot();

            for(Subject s : list){

                if(s.isAssociated()){

                    TreeItem<Subject> temp = findTreeItem(s);

                    while(temp != null){

                        temp.setExpanded(true);
                        temp = temp.getParent();
                    }

                }

            }
        }
    }

    public void expandRequestedNodes(ArrayList<Subject> list){

        collapseTree();

        for(Subject s: list){

            TreeItem<Subject> currentItem = findTreeItem(s);

            while(currentItem != null){

                currentItem.setExpanded(true);
                currentItem = currentItem.getParent();
            }
        }
    }

    private TreeItem<Subject> findTreeItem(Subject subjectToFind){

        String path = subjectToFind.getPath();
        TreeItem<Subject> start = treeView.getRoot();

        return traverseTree(path,start);
    }

    private TreeItem<Subject> traverseTree(String path,TreeItem<Subject> treeItem){

        int index = path.lastIndexOf(".");
        String currentPosition = path.substring(index+1,path.length());
        String subPath = path.substring(0,index);
        int num = Integer.parseInt(currentPosition);
        TreeItem<Subject> itemToReturn = null;

        if (subPath.equals("")){
            if(treeItem.getValue().getPosition() == num){
                itemToReturn =  treeItem;
            }

        }else{

            if(treeItem.getValue().getPosition() == num){
                for(TreeItem<Subject> t : treeItem.getChildren()){
                    itemToReturn =  traverseTree(subPath, t);

                    if(itemToReturn != null){
                        return itemToReturn;
                    }
                }
            }
        }
        return itemToReturn;
    }

    public void collapseTree(){

        TreeItem<Subject> node = treeView.getRoot();
        Queue<TreeItem<Subject>> queue = new LinkedList<TreeItem<Subject>>();
        queue.add(node);

        while(!queue.isEmpty()){

            TreeItem<Subject> temp = queue.poll();

            for(TreeItem<Subject> t : temp.getChildren()){
                queue.add(t);
            }

            temp.setExpanded(false);

        }
    }

    private class CustomTreeCell extends TreeCell<Subject> {//MUST BE OF TYPE SUBJECT



        public CustomTreeCell(){

            //Initialize
            init();
        }


        private void init(){

            //Add items to ContextMenu and set ID's for items
            if(contextMenu.getItems().isEmpty()) {
                MenuItem addItem = new MenuItem("Add");
                addItem.setId("add_menu_item");
                MenuItem deleteItem = new MenuItem("Delete");
                deleteItem.setId("delete_menu_item");

                contextMenu.getItems().addAll(addItem, deleteItem);
            }

        }
        @Override
        public void startEdit(){

            super.startEdit();

             System.out.println("Start Edit");
            String oldName = getText();
            String newName = null;
            String newDesc = null;
            addDialogBox.setClassTextField(oldName);
            addDialogBox.setDescTextField(model.getDesc(this.getItem()));
            Optional<ButtonType> result = addDialogBox.showAndWait();

            if(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){

                newName = addDialogBox.getClassName();
                newDesc = addDialogBox.getDesc();
                this.getItem().setName(newName);
                this.getItem().setDescription(newDesc);

                model.modifyItem(this.getItem(), newName, newDesc);

                Circle circle = new Circle(10);
                Text text;
                if(this.getItem().isLeaf()){

                    if(this.getItem().getNumberOfAssociationsMade() == 0)
                        text = new Text("");
                    else
                        text = new Text(this.getItem().getNumberOfAssociationsMade()+"");

                }else{

                    text = new Text (this.getItem().getNumberOfChildrenAssociated()+"/"+this.getItem().getNumberOfChildren());
                }


                text.setBoundsType(TextBoundsType.VISUAL);
                StackPane stack = new StackPane();
                stack.getChildren().add(circle);
                stack.getChildren().add(text);
                circle.setFill(this.getTreeItem().getValue().getCurrentColor());

                if(!this.getTreeView().getRoot().getValue().equals(this.getItem())){
                    setGraphic(stack);
                }


            }else{

                this.cancelEdit();
            }
        }

        @Override
        public void cancelEdit(){

            super.cancelEdit();


        }

        @Override
        public void updateItem(Subject item, boolean empty){

            super.updateItem(item,empty);

            if(empty){
                setText(null);
                setGraphic(null);
            }else{
                if(isEditing()){

                    setText(null);
                    Circle circle = new Circle(10);

                    circle.setFill(this.getTreeItem().getValue().getCurrentColor());


                    setGraphic(circle);
                }else{
                    setText(getString());


                    Circle circle = new Circle(10);
                    Text text;
                    if(this.getItem().isLeaf()){

                        if(this.getItem().getNumberOfAssociationsMade() == 0)
                            text = new Text("");
                        else
                            text = new Text(this.getItem().getNumberOfAssociationsMade()+"");

                    }else{
                        text = new Text (this.getItem().getNumberOfChildrenAssociated()+"/"+this.getItem().getNumberOfChildren());
                    }

                    text.setBoundsType(TextBoundsType.VISUAL);
                    StackPane stack = new StackPane();
                    stack.getChildren().add(circle);
                    stack.getChildren().add(text);
                    circle.setFill(this.getTreeItem().getValue().getCurrentColor());

                    if(!this.getTreeView().getRoot().getValue().equals(this.getItem())){
                        setGraphic(stack);
                    }else{
                        setGraphic(null);
                    }


                    setContextMenu(contextMenu);
                }
            }
        }

        private String getString(){
            return (getItem() == null ? "" : getItem().getName());
        }
    }

}
