package sample;

/**
 * Created by benjamintoofer on 11/2/15.
 */
public class RunListManager {

    private UserInterface uiView;
    private ClassListController classListController;
    private ClassListModel classListModel;


    public RunListManager(){

        init();
    }

    private void init(){

        uiView = new UserInterface(1200,900);
        classListController = new ClassListController();
        classListModel = new ClassListModel();

        uiView.addClassListController(classListController);
        uiView.addClassListModel(classListModel);
        classListModel.addObserver(uiView);
        classListController.addModel(classListModel);
        classListController.addView(uiView.getClassListView());
    }

    public UserInterface getUIView(){

        return uiView;
    }
}
