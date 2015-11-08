package sample;

/**
 * Created by benjamintoofer on 11/2/15.
 */
public class RunListManager {

    private UserInterface uiView;
    private ClassListController classListController;
    private ClassListModel classListModel;
    private SubjectTreeController subjectTreeController;
    private SubjectTreeModel subjectTreeModel;
    private AssociationModel associationModel;
    private AssociationController associationController;


    /*
        Called from main
        Sets up all models,controllers, and views
     */
    public RunListManager(){

        init();
    }

    private void init(){

        uiView = new UserInterface(1200,900);

        classListController = new ClassListController();
        classListModel = new ClassListModel();

        associationModel = new AssociationModel();
        associationController = new AssociationController();

        classListController.addClassListModel(classListModel);
        classListController.addAssociationModel(associationModel);
        classListController.addClassListView(uiView.getClassListView());


        subjectTreeController = new SubjectTreeController();
        subjectTreeModel = new SubjectTreeModel();

        subjectTreeController.addSubjectTreeModel(subjectTreeModel);
        subjectTreeController.addAssociationModel(associationModel);
        subjectTreeController.addSubjectTreeView(uiView.getSubjectTreeView());

        associationController.addAssociationModel((associationModel));
        associationController.addSubjectTreeModel(subjectTreeModel);
        associationController.addClassListView((uiView.getClassListView()));
        associationController.addSubjectTreeView(uiView.getSubjectTreeView());

        uiView.addClassListController(classListController);
        uiView.addClassListModel(classListModel);

        uiView.addSubjectTreeController(subjectTreeController);
        uiView.addSubjectTreeModel(subjectTreeModel);

        uiView.addAssociationController(associationController);
        uiView.addAssociationModel(associationModel);

        //Adding observers
        classListModel.addObserver(uiView);
        subjectTreeModel.addObserver(uiView);
        associationModel.addObserver(uiView);


    }

    public UserInterface getUIView(){

        return uiView;
    }
}
