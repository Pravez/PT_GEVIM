package controller.listeners;

import controller.ActionController;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Admin on 08/03/2015.
 */
public class KeyActionListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(KeyEvent.getKeyText(e.getKeyCode()));
        if (e.isControlDown()) {
            switch(KeyEvent.getKeyText(e.getKeyCode())) {
                case "C" :
                    ActionController.copy();
                    break;
                case "V" :
                    ActionController.paste(null);
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
                case "W" :
                    ActionController.closeTab();
                    break;
                case "O" :
                    ActionController.openFromGraphml(); // à modifier
                    break;
                case "S" :
                    ActionController.saveToGraphml(); // à modifier aussi
                    break;
                case "Retour arrière" :
                    //ActionController.
                    break;
                case "Supprimer" :
                    break;
                default:
                    break;
            }
        }
    }
}
