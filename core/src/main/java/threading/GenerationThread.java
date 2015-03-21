package threading;

import data.GraphElement;
import data.Vertex;
import view.*;
import view.Window;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * Classe de génération d'éléments. Elle les génère en sous-traitant dans un thread.
 */
public class GenerationThread extends JDialog implements Runnable
{
    private JProgressBar progress;
    private int value;
    private int numberOfElements;
    private ArrayList<GraphElement> elements;
    private Dimension parentSize;

    public GenerationThread(Component parent, int generationNumber)
    {
        elements = new ArrayList<>();

        numberOfElements = generationNumber;
        this.value = 0;
        this.progress = new JProgressBar (0, 100);
        this.progress.setStringPainted (true);

        this.setTitle("Generation en cours ...");
        this.getContentPane().add(this.progress);
        this.setLocationRelativeTo(parent);
        this.pack();
        Window tmp = (Window) parent;
        parentSize=tmp.getCurrentSheetViewPort().getViewSize();
        launchGeneration();

        this.setModal(true);
        this.setVisible (true);
    }

    /**
     * Méthode lançant la génération des éléments. Elle appelle la méthode start qui va lancer un nouveau thread à
     * partir de cette classe.
     */
    public void launchGeneration()
    {
        Thread t = new Thread (this);
        t.start ();

        //On a crée un nouveau thread, maintenant on récupère le principal...
    }

    /**
     * Méthode de l'interface {@link java.lang.Runnable}, méthode d'execution du thread crée, c'est elle
     * qui lance à proprement parler la génération.
     */
    public void run (){

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
    public void generation(){

        Rectangle viewRectangle = new Rectangle(new Point(0,0), parentSize);
        Random r = new Random();
        for (int i = 0; i < numberOfElements; i++)
        {
            elements.add(new Vertex("vertex", Color.BLACK, new Point(r.nextInt(viewRectangle.width) + viewRectangle.x, r.nextInt(viewRectangle.height) + viewRectangle.y), 15, Vertex.Shape.SQUARE));
            //Mise à jour de la barre de progression
            this.majProgress();
        }
    }

    /**
     * Méthode mettant à jour la barre de progression par le processus de gestion des événements.
     * Pour plus d'ingormations, se référer à http://gfx.developpez.com/tutoriel/java/swing/swing-threading/
     **/
    public void majProgress (){

        //Si l'on peut update maintenant
        if (SwingUtilities.isEventDispatchThread()){

            value +=1;
            int set = (100*value)/numberOfElements;
            progress.setValue(set);
        }else{
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
