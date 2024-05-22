import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class TestChartPanel extends JPanel {
    private List<test> tests;

    public TestChartPanel(List<test> tests) {
        this.tests = tests;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tests == null || tests.isEmpty()) {
            g.drawString("No data to display",getWidth()/2-50,getHeight()/2);
            return;
        }

        int width=getWidth();
        int height=getHeight();
        int barWidth=width/(tests.size()*3);
        int maxHeight=height-50;

        int maxScore=0;
        int maxTime=0;
        for (test t : tests) {
            if (t.score>maxScore) 
                maxScore=(int)t.score;
            if (t.time>maxTime) 
                maxTime = t.time;
        }

        int x=10;
        for (test t:tests) {
            int scoreHeight=(int) ((t.score/maxScore)*maxHeight);
            int timeHeight=(int) ((t.time/(double) maxTime)*maxHeight);

            g.setColor(Color.BLUE);
            g.fillRect(x,height-scoreHeight-30,barWidth,scoreHeight);
            g.drawString("Score:"+t.score,x,height-scoreHeight-35);

            g.setColor(Color.RED);
            g.fillRect(x+barWidth+10,height-timeHeight-30,barWidth,timeHeight);
            g.drawString("Time:"+t.time,x+barWidth+10,height-timeHeight-35);

            // Draw the ID below the bars
            g.setColor(Color.BLACK);
            g.drawString("ID:"+t.id,x,height-10);

            x+=2*(barWidth+20);
        }
    }
}


class test{
    int id;
    float score;
    int time;
    test(int id,float score,int time)
    {
        this.time=time;
        this.id=id;
        this.score=score;
    }
    @Override
    public String toString()
    {
        return "id:"+id+" score:"+score+" time:"+time;
    }
}

public class MainFrame extends JFrame {
    final private Font mainFont = new Font("HK Grotesk Regular", Font.BOLD, 18);
    JLabel lbWelcome;


    public void initialize() {
        // WELCOME LABEL:
        lbWelcome = new JLabel("Welcome! Select from one of the options bellow what you would like to do.");
        lbWelcome.setFont((new Font("Vercetti Regular Font", Font.BOLD, 20)));
        lbWelcome.setVerticalAlignment(JLabel.TOP);
        lbWelcome.setHorizontalAlignment(JLabel.CENTER);

        ImageIcon cpuIcon=new ImageIcon(new ImageIcon("pi.jpg").getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
        ImageIcon ramIcon=new ImageIcon(new ImageIcon("search.jpg").getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
        ImageIcon databaseIcon=new ImageIcon(new ImageIcon("database.jpg").getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));

        JLabel cpu_image=new JLabel(cpuIcon);
        JLabel ram_image=new JLabel(ramIcon);
        JLabel database_image=new JLabel(databaseIcon);
        

        JScrollPane scrollPane = new JScrollPane(lbWelcome);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setBackground(new Color(255,255,255));
        scrollPane.setPreferredSize(new Dimension(500,300));

        // BUTTON PANEL:
        JButton btnTestCPU = new JButton("Test CPU");
        btnTestCPU.setFont(mainFont);
        btnTestCPU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user to input the number of digits
                String input = JOptionPane.showInputDialog("Enter the number of digits of pi to compute:");
                if (input != null && !input.isEmpty()) {
                    try {
                        int numDigits = Integer.parseInt(input);
                        // Execute CPU testing code here
                        BigDecimal pi = DigitsOfPi.computePi(numDigits);

                        // Calculate execution time
                        long startTime = System.nanoTime();
                        DigitsOfPi.computePi(numDigits);
                        long endTime = System.nanoTime();
                        long duration = endTime - startTime;
                        double comp_time=duration/1_000_000_000.0;
                        
                        BigDecimal numArithmeticOperations = new BigDecimal(numDigits * 9); // Each iteration involves 9 arithmetic operations
                        double cpuScore = DigitsOfPi.calculateCPUScore(numDigits, numArithmeticOperations, comp_time);
                        // System.out.println("CPU Score: " + cpuScore);

                        // Display results
                        lbWelcome.setText("<html>Execution time: " + comp_time + " seconds<br/>Pi: " + pi + "<br/>Score:"+ cpuScore +"</html>");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                    }
                }
            }
        });

        JButton btnTestRAM = new JButton("Test RAM");
        btnTestRAM.setFont(mainFont);
        btnTestRAM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] array = SearchTheNumber.generateRandomArray(70, 10, 100);

                // Prompt user if they want to print the generated array
                int option = JOptionPane.showConfirmDialog(null, "Do you want to print the generated array?", "Print Array", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Generated Array: " + Arrays.toString(array));
                }

                // Prompt the user to enter a number to search for
                String input = JOptionPane.showInputDialog("Enter the number you want to search:");
                if (input != null && !input.isEmpty()) {
                    try {
                        int target=Integer.parseInt(input);

                        // Perform linear search and measure execution time
                        long startTime = System.nanoTime();
                        int index = SearchTheNumber.linearSearch(array, target);
                        long endTime = System.nanoTime();
                        long duration = endTime - startTime;

                        double duration_cool=duration/1_000_000_000.0;
                        double memoryUtilization = (double) array.length / Runtime.getRuntime().totalMemory();

                        // Calculate RAM Score using the new method
                        double ramScore = SearchTheNumber.calculateRAMScore(array.length, memoryUtilization, duration_cool);
                        String mod_dur=String.format("%.9f", duration_cool);
                        // Print the result
                        if (index != -1) {
                            lbWelcome.setText("<html>Number " + target + " found at index " + index +"<br/>Execution time: " + mod_dur + " seconds"+"<br/>RAM Score:"+ramScore+"</html>");
                        } else {
                            lbWelcome.setText("<html>Number " + target + " not found in array. "+"<br/>Execution time: " + mod_dur + " seconds"+"<br/>RAM Score:"+ramScore+"</html>");
                        }

                        

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                    }
                }
            }
        });

        JButton btnGetDatabase = new JButton("Get database");
        btnGetDatabase.setFont(mainFont);
        //array list of "test" objects.
        List<test> arr=adinaFaFunctia();

        // List<test> arr=new ArrayList<>();
        // arr.add(new test(1, 75.5f, 120));
        // arr.add(new test(2, 85.0f, 150));
        // arr.add(new test(3, 90.2f, 180));
        // arr.add(new test(4, 60.3f, 200));

        if(arr.isEmpty())
        {
            System.out.println("Array gol");
        }
        
        btnGetDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s="";
                for(test t:arr)
                {
                    s+=t.toString()+"<br/>";
                }
                if(s.equals(""))
                {
                    lbWelcome.setText("No content in the database");
                }
                else
                {
                    lbWelcome.setText("<html>"+s+"<html>");
                }
                // Prompt user if they want to see a chart depicting the parsed data.
                int option = JOptionPane.showConfirmDialog(null, "Do you want to see a chart for the data?", "See chart", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    JFrame chartFrame = new JFrame("Test Data Chart");
                    chartFrame.add(new TestChartPanel(arr));
                    chartFrame.setSize(800, 600);
                    chartFrame.setVisible(true);
                }
            }
        });

        JPanel imagePanel=new JPanel();
        imagePanel.setLayout(new GridLayout(1,3,5,5));
        imagePanel.add(cpu_image);
        imagePanel.add(ram_image);
        imagePanel.add(database_image);
        imagePanel.setBackground(new Color(255,255,255));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3, 5, 0));
        buttonsPanel.add(btnTestCPU);
        buttonsPanel.add(btnTestRAM);
        buttonsPanel.add(btnGetDatabase);

        JPanel imagesAndButtons=new JPanel();
        imagesAndButtons.setLayout(new GridLayout(2,1,5,20));
        imagesAndButtons.add(imagePanel,BorderLayout.NORTH);
        imagesAndButtons.add(buttonsPanel,BorderLayout.SOUTH);
        imagesAndButtons.setBackground(new Color(255,255,255));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(imagesAndButtons, BorderLayout.SOUTH);

        add(mainPanel);

        setTitle("CPU and RAM Tester");
        setSize(800, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame myFrame = new MainFrame();
        myFrame.initialize();
    }
}
