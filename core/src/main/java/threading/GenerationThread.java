package threading;

import controller.Controller;
import data.GraphElement;
import data.Vertex;
import view.Window;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * Classe de génération d'éléments. Elle les génère en sous-traitant dans un thread.
 */
public class GenerationThread extends JDialog implements Runnable {
    private JProgressBar            progress;
    private int                     value;
    private int                     numberOfElements;
    private ArrayList<GraphElement> elements;
    private Dimension               parentSize;
    private Controller              controller;

    public GenerationThread(Controller controller, int generationNumber) {
        this.elements         = new ArrayList<>();
        this.controller       = controller;
        this.numberOfElements = generationNumber;
        this.value            = 0;
        this.parentSize       = this.controller.getWindow().getCurrentSheetViewPort().getViewSize();
        this.progress         = new JProgressBar (0, 100);
        this.progress.setStringPainted (true);

        this.setTitle("Generation en cours ...");
        this.setPreferredSize(new Dimension(300, 60));
        this.getContentPane().add(this.progress);
        this.setLocationRelativeTo(this.controller.getWindow());
        this.pack();

        launchGeneration();

        this.setModal(true);
        this.setVisible (true);
    }

    /**
     * Méthode lançant la génération des éléments. Elle appelle la méthode start qui va lancer un nouveau thread à
     * partir de cette classe.
     */
    public void launchGeneration() {
        Thread t = new Thread (this);
        t.start();
        //On a crée un nouveau thread, maintenant on récupère le principal...
    }

    /**
     * Méthode de l'interface {@link java.lang.Runnable}, méthode d'execution du thread crée, c'est elle
     * qui lance à proprement parler la génération.
     */
    public void run() {
        this.generation();
        onEnd();
    }

    /**
     * Méthode appellée à la fin du traitement
     */
    private void onEnd() {
        dispose();
    }

    /**
     * Methode executee par le nouveau thread, elle génére les éléments.
     */
    public void generation() {
        Rectangle viewRectangle = new Rectangle(new Point(0,0), parentSize);
        Random    r             = new Random();

        Color     color         = this.controller.getWindow().getCurrentSheet().getDefaultVerticesColor();
        int       size          = this.controller.getWindow().getCurrentSheet().getDefaultVerticesSize();
        Vertex.Shape shape      = this.controller.getWindow().getCurrentSheet().getDefaultVerticesShape();

        for (int i = 0; i < this.numberOfElements; i++) {
            this.elements.add(new Vertex("vertex", color, new Point(r.nextInt(viewRectangle.width) + viewRectangle.x, r.nextInt(viewRectangle.height) + viewRectangle.y), size, shape));
            this.majProgress(); //Mise à jour de la barre de progression
        }
    }

    /**
     * Méthode mettant à jour la barre de progression par le processus de gestion des événements.
     * Pour plus d'ingormations, se référer à http://gfx.developpez.com/tutoriel/java/swing/swing-threading/
     **/
    public void majProgress() {
        //Si l'on peut update maintenant
        if (SwingUtilities.isEventDispatchThread()) {
            this.value ++;
            this.progress.setValue((100 * this.value)/this.numberOfElements);
        } else {
            //Sinon, on le met en attente et sera appelé plus tard
            Runnable callMAJ = new Runnable(){
                public void run (){
                    majProgress ();
                }
            };
            SwingUtilities.invokeLater (callMAJ);
        }
    }

    public ArrayList<GraphElement> getElements() {
        return elements;
    }
}
