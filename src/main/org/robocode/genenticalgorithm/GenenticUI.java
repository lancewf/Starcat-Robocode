package org.robocode.genenticalgorithm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.robocode.genenticalgorithm.fitnesstest.IFitnessTest;

public class GenenticUI extends JFrame implements Runnable
{
   // --------------------------------------------------------------------------
   // #region Private Data
   // --------------------------------------------------------------------------

   private static final long serialVersionUID = 3286354597317648794L;

   private JPanel jPanel1 = new JPanel();

   private JPanel jPanel2 = new JPanel();

   private JPanel southjPanel = new JPanel();

   private JProgressBar progressBar1 = new JProgressBar();

   private boolean running = false;

   private BorderLayout borderLayout1 = new BorderLayout();

   private JButton startJButton = new JButton();

   private JButton pauseJButton = new JButton();

   private JLabel generationStringjLabel = new JLabel();

   private JLabel generationCountjLabel = new JLabel();

   private FlowLayout flowLayout1 = new FlowLayout();

   private GenerationRunner generationRunner;
   
   private IFitnessTest fitnessTest;

   // #endregion

   // --------------------------------------------------------------------------
   // #region Constructor
   // --------------------------------------------------------------------------

   public GenenticUI(GenerationRunner generationRunner,
                     IFitnessTest fitnessTest)
   {
      try
      {
         jbInit();

         this.generationRunner = generationRunner;
         this.fitnessTest = fitnessTest;

         SetWindowLocationSize();
         setName("Genectic UI");
         setTitle("Genectic UI");

      }
      catch (Exception exception)
      {
         exception.printStackTrace();
      }
   }

   // #endregion

   // --------------------------------------------------------------------------
   // #region Public Members
   // --------------------------------------------------------------------------

   public IProgress getProgressBar()
   {
      return new Progress(progressBar1);
   }

   public void startJButton_mouseClicked(MouseEvent e)
   {
      if (!running)
      {
         startJButton.setEnabled(false);
         pauseJButton.setEnabled(true);
         running = true;
         new Thread(this).start();
      }
      else
      {
         generationRunner.unPause();
         startJButton.setEnabled(false);
         pauseJButton.setEnabled(true);
      }
   }

   public void pauseJButton_mouseClicked(MouseEvent e)
   {
      generationRunner.pause();
      startJButton.setEnabled(true);
      pauseJButton.setEnabled(false);
   }

   public void run()
   {
      while (running)
      {
         generationRunner.run();
         generationCountjLabel.setText(generationRunner
            .getNumberOfGenerationsRan()
               + "");
      }
   }

   // #endregion

   // --------------------------------------------------------------------------
   // #region Private Members
   // --------------------------------------------------------------------------

   private void SetWindowLocationSize()
   {
      int windowWidth = 256;// dim.width / 4;
      int windowHeight = 150;// dim.height / 4;
      int windowX = windowWidth / 4;
      int windowY = windowHeight / 4;

      this.setSize(windowWidth, windowHeight);
      this.setLocation(windowX, windowY);
      this.setResizable(false);
   }

   private void jbInit() throws Exception
   {
      getContentPane().setLayout(borderLayout1);

      // jPanel1
      jPanel1.setBackground(SystemColor.inactiveCaption);
      jPanel1.setLayout(flowLayout1);

      // jPanel2
      jPanel2.setBackground(SystemColor.activeCaption);
      jPanel2.setLayout(flowLayout1);

      // southjPanel
      southjPanel.setBackground(SystemColor.inactiveCaption);
      southjPanel.setLayout(flowLayout1);

      // Menu bar
      // Create the menu bar
      JMenuBar menuBar = new JMenuBar();

      JMenu toolsMenu = createToolsMenu();
      JMenu performanceMenu = createPerformanceMenu();

      menuBar.add(toolsMenu);
      menuBar.add(performanceMenu);
      
      // Install the menu bar in the frame
      setJMenuBar(menuBar);

      // generationStringjLabel
      generationStringjLabel.setText("Generations:");
      southjPanel.add(generationStringjLabel, null);

      // generationCountjLabel
      generationCountjLabel.setText("0");
      southjPanel.add(generationCountjLabel, null);

      // progressBar1
      progressBar1.setValue(0);
      progressBar1.setStringPainted(true);
      jPanel2.add(progressBar1, null);

      // Start button
      startJButton.setText("Start");
      startJButton.addMouseListener(new GenenticUI_startJButton_mouseAdapter(
         this));
      jPanel1.add(startJButton, null);

      // Stop button
      pauseJButton.setText("Pause");
      pauseJButton.addMouseListener(new GenenticUI_pauseJButton_mouseAdapter(
         this));
      pauseJButton.setEnabled(false);
      jPanel1.add(pauseJButton, null);

      this.getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);
      this.getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
      this.getContentPane().add(southjPanel, java.awt.BorderLayout.SOUTH);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   private JMenu createPerformanceMenu()
   {
      // Create a menu
      JMenu toolsMenu = new JMenu("Performance");

      // Create a menu item
      JMenuItem item = new JMenuItem("Show Individual");
      item.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            JFileChooser chooser = new JFileChooser();

            // only allows the user to choose a directory
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            // opens the browse window
            int returnVal = chooser.showOpenDialog(GenenticUI.this);

            // check if the user choose a directory
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
               // get the folder chosen
               File file = chooser.getSelectedFile();

               Chromosome chromosome = new BotcatChromosome(file);
               final Individual individaul = new Individual(chromosome);
               
               GenenticUI.this.setEnabled(false);
               
               Thread thread = new Thread()
               {
                  public void run()
                  {
                     fitnessTest.run(individaul);
                     
                     EventQueue.invokeLater(new Runnable()
                     {
                        public void run()
                        {
                           GenenticUI.this.setEnabled(true);                           
                        }
                     });
                  }
               };
               
               thread.start();
            }
         }
      });

      toolsMenu.add(item);
      
      return toolsMenu;
   }
   
   private JMenu createToolsMenu()
   {
      // Create a menu
      JMenu toolsMenu = new JMenu("Tools");

      // Create a menu item
      JMenuItem item = new JMenuItem("Save All");
      item.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            JFileChooser chooser = new JFileChooser();

            // only allows the user to choose a directory
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            // opens the browse window
            int returnVal = chooser.showOpenDialog(GenenticUI.this);

            // check if the user choose a directory
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
               // get the folder chosen
               File file = chooser.getSelectedFile();

               generationRunner.saveAll(file);
            }
         }
      });

      toolsMenu.add(item);

      JMenuItem item1 = new JMenuItem("Load");
      item1.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            JFileChooser chooser = new JFileChooser();

            // only allows the user to choose a directory
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            // opens the browse window
            int returnVal = chooser.showOpenDialog(GenenticUI.this);

            // check if the user choose a directory
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
               // get the folder chosen
               File file = chooser.getSelectedFile();

               generationRunner.load(file);
               
               generationCountjLabel.setText(generationRunner
                  .getNumberOfGenerationsRan()
                     + "");
            }
         }
      });

      toolsMenu.add(item1);
      
      return toolsMenu;
   }
}
   // --------------------------------------------------------------------------
   // Local Classes
   // --------------------------------------------------------------------------

class GenenticUI_startJButton_mouseAdapter extends MouseAdapter
{
   private GenenticUI adaptee;

   GenenticUI_startJButton_mouseAdapter(GenenticUI adaptee)
   {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e)
   {
      adaptee.startJButton_mouseClicked(e);
   }
}

class GenenticUI_pauseJButton_mouseAdapter extends MouseAdapter
{
   private GenenticUI adaptee;

   GenenticUI_pauseJButton_mouseAdapter(GenenticUI adaptee)
   {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e)
   {
      adaptee.pauseJButton_mouseClicked(e);
   }
}