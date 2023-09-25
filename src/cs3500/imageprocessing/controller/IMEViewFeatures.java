package cs3500.imageprocessing.controller;

import cs3500.imageprocessing.model.IMGModel;
import cs3500.imageprocessing.model.IModel;
import cs3500.imageprocessing.view.IMEView;
import cs3500.imageprocessing.view.IView;


/**
 * Main class for instantiating a GUI and Controller for functional use.
 */
public class IMEViewFeatures {

  /**
   * Main method, that will instantiate the GUI.
   * @param args potential parameters.
   */
  public static void main(String[] args) {
    IModel model = new IMGModel();
    IView view = new IMEView();
    GUIController controller = new GUIController(model, view);
  }

}
