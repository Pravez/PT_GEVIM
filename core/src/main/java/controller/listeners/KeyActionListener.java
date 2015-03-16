package controller.listeners;

import controller.ActionController;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Admin on 08/03/2015.
 */
public class KeyActionListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown()) {
            switch(KeyEvent.getKeyText(e.getKeyCode())) {
                case "A" :
                    ActionController.selectAll();
                    break;
                case "C" :
                    ActionController.copy();
                    break;
                case "V" :
                    ActionController.paste(null);
                    break;
                case "X" :
                    ActionController.cut();
                    break;
                case "Z" :
                    ActionController.undo();
                    break;
                case "Y" :
                    ActionController.redo();
                    break;
                case "N" :
                    ActionController.newTab();
                    break;
                case "T" :
                    ActionController.newTab();
                    break;
                case "W" :
                    ActionController.closeTab();
                    break;
                case "O" :
                    ActionController.openGraph();
                    break;
                case "S" :
                    ActionController.saveGraph();
                    break;
                default:
                    break;
            }
        } else if (KeyEvent.getKeyText(e.getKeyCode()) == "Retour arrière" || KeyEvent.getKeyText(e.getKeyCode()) == "Supprimer") {
            ActionController.delete();
        }
    }
}
