package sample;

import javafx.stage.Stage;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by benjamintoofer on 11/2/15.
 */
public class RunListManager implements Serializable,Observer{

    private UserInterface uiView;
    private ClassListController classListController;
    private ClassListModel classListModel;
    private SubjectTreeController subjectTreeController;
    private SubjectTreeModel subjectTreeModel;
    private AssociationModel associationModel;
    private AssociationController associationController;
    private IOController ioController;
    private transient Stage stage;


    /*
        Called from main
        Sets up all models,controllers, and views
     */
    public RunListManager(Stage stage){

        this.stage = stage;
        init();
        setUpMVC();
    }

    private void init(){

        /*
            Instantiate Objects
         */
        uiView = new UserInterface(1200,900);

        ioController = new IOController(stage);

        classListController = new ClassListController();
        classListModel = new ClassListModel();

        associationModel = new AssociationModel();
        associationController = new AssociationController();

        subjectTreeController = new SubjectTreeController();
        subjectTreeModel = new SubjectTreeModel();


    }

    private void setUpMVC(){

        classListController.addClassListModel(classListModel);
        classListController.addAssociationModel(associationModel);
        classListController.addSubjectTreeModel(subjectTreeModel);
        classListController.addSubjectTreeView(uiView.getSubjectTreeView());
        classListController.addClassListView(uiView.getClassListView());


        subjectTreeController.addSubjectTreeModel(subjectTreeModel);
        subjectTreeController.addAssociationModel(associationModel);
        subjectTreeController.addSubjectTreeView(uiView.getSubjectTreeView());

        associationController.addAssociationModel((associationModel));
        associationController.addSubjectTreeModel(subjectTreeModel);
        associationController.addClassListView((uiView.getClassListView()));
        associationController.addSubjectTreeView(uiView.getSubjectTreeView());


        //Add controllers, models, and views to UI
        uiView.addStage(stage);

        uiView.addIOControllerToFileMenu(ioController);

        uiView.addClassListController(classListController);
        uiView.addClassListModel(classListModel);

        uiView.addSubjectTreeController(subjectTreeController);
        uiView.addSubjectTreeModel(subjectTreeModel);

        uiView.addAssociationController(associationController);
        uiView.addAssociationModel(associationModel);

        //Add controllers, models, and views to IOController
        ioController.addRunListManager(this);
        ioController.addSubjectTreeView(uiView.getSubjectTreeView());
        ioController.addClassListView(uiView.getClassListView());
        ioController.addClassListModel(classListModel);
        ioController.addSubjectTreeModel(subjectTreeModel);

        //Adding observers
        classListModel.addObserver(uiView);
        subjectTreeModel.addObserver(uiView);
        associationModel.addObserver(uiView);

    }

    public UserInterface getUIView(){

        return uiView;
    }

    public void updateView(){

        //uiView.getSubjectTreeView().loadViewFromModel(subjectTreeModel);
        System.out.println("FUCKKKK "+subjectTreeModel.printTree());
        uiView.getClassListView().addModel(classListModel);
        uiView.updateView();
    }

    public IOController getIoController(){

        return ioController;
    }

    public SubjectTreeModel getSubjectTreeModel(){

        return subjectTreeModel;
    }

    public AssociationModel getAssociationModel(){

        return associationModel;
    }

    public ClassListModel getClassListModel(){

        return classListModel;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println("UPDATING RLM SBJECT MODEL");
        subjectTreeModel = ioController.getSubjectTreeModel();
        classListModel = ioController.getClassListModel();
        associationModel = ioController.getAssociationModel();
        setUpMVC();
        //uiView
        //associationController.addSubjectTreeModel(subjectTreeModel);
    }
}
