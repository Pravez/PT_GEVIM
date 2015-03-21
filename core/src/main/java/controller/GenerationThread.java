package controller;

import data.GraphElement;
import data.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by paubreton on 21/03/15.
 */
public class GenerationThread extends JDialog implements Runnable
{
    private JProgressBar progress;
    private int value;
    private int numberOfElements;
    private ArrayList<GraphElement> elements;

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

        for (int i = 0; i < numberOfElements; i++)
        {
            elements.add(new Vertex("vertex", Color.BLACK, new Point(0,0), 15, Vertex.Shape.SQUARE));

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
