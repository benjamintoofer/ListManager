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


    public RunListManager(){

        init();
    }

    private void init(){

        uiView = new UserInterface(1200,900);

        classListController = new ClassListController();
        classListModel = new ClassListModel();

        classListModel.addObserver(uiView);
        classListController.addModel(classListModel);
        classListController.addView(uiView.getClassListView());

        subjectTreeController = new SubjectTreeController();
        subjectTreeModel = new SubjectTreeModel();

        subjectTreeController.addModel(subjectTreeModel);
        subjectTreeController.addView(uiView.getSubjectTreeView());

        uiView.addClassListController(classListController);
        uiView.addClassListModel(classListModel);

        uiView.addSubjectTreeController(subjectTreeController);
        uiView.addSubjectTreeModel(subjectTreeModel);


    }

    public UserInterface getUIView(){

        return uiView;
    }
}
