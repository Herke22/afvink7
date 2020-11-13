import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class afvink7 extends JFrame implements ActionListener {
    // alle onderdelen van de gui worden gemaakt
    private final JFileChooser fc = new JFileChooser();
    private JButton b1;
    private JTextField tf1;
    private JTextArea t1;
    private JPanel p1;

    public static void main(String[] args) {
        afvink7 f = new afvink7();
        f.setSize(800, 800);
        f.createGUI();
        f.setVisible(true);
    }

    void createGUI() {
        /**
         * De gui word gemaakt
         */
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());
        tf1 = new JTextField();
        tf1.setPreferredSize(new Dimension(300, 25));
        b1 = new JButton("selecteer bestand");
        b1.setPreferredSize(new Dimension(300, 25));
        t1 = new JTextArea();
        t1.setPreferredSize(new Dimension(800, 500));
        p1 = new JPanel();
        p1.setPreferredSize(new Dimension(700, 200));
        window.add(tf1);
        window.add(b1);
        window.add(t1);
        window.add(p1);
        // actionlistener voor de button
        b1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * de button opent een JFileChooser
         * het bestand word naar de Stringbuilder fileInhoud gestuurd
         * de return van de stringbuilder word naar de check functie gestuurd
         */
        if (e.getSource() == b1) {
            int returnVal = fc.showOpenDialog(afvink7.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    StringBuilder fileInhoud = readFile();
                    tf1.setText(fc.getCurrentDirectory().getAbsolutePath());
                    t1.setText(fileInhoud.toString());
                    check(fileInhoud);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        }
    }

    public StringBuilder readFile() throws FileNotFoundException {
        /**
         * de inhoud van de file word uit de file gehaald
         * @output: fileinhoud
         */
        StringBuilder fileInhoud = new StringBuilder();
        try (Scanner sc = new Scanner(fc.getSelectedFile())) {
            while (sc.hasNextLine()) {
                fileInhoud.append(sc.nextLine().strip());

            }
        }
        return fileInhoud;

    }

    public void check(StringBuilder fileinhoud) {
        /**
         * deze functie kijkt met regex of wat voor sequentie het is
         * en voert daarvoor de goede functie uit
         */
        String seq = fileinhoud.toString();
        if (!seq.matches(".*[^ATGC].*")) {
            t1.setText(seq);
            tekenenDNA(seq);
        } else if (!seq.matches(".*[^AUGC].*")) {
            t1.setText(seq);
            tekenenRNA(seq);
        } else {
            t1.setText(seq);
            tekenenPRO(seq);
        }
    }

    public void tekenenDNA(String dna) {
        /**
         * deze functie itereerd door de sequentie en tekent de correcte visualisatie erbij
         */
        Graphics paper = p1.getGraphics();
        paper.clearRect(0, 0, 700, 700);
        int x = 50;
        int y = 650 / dna.length();
        for (int i = 0, n = dna.length(); i < n; i++) {
            char c = dna.charAt(i);
            String v = String.valueOf(c);
            if (v.equals("G") | v.equals("C")) {
                System.out.println(v);
                paper.setColor(Color.RED);
                paper.fillRect(x, 10, y, 25);
                x = x + y;
            } else if (v.equals("A") | v.equals("T")) {
                System.out.println(v);
                paper.setColor(Color.YELLOW);
                paper.fillRect(x, 10, y, 25);
                x = x + y;
            }
        }
    }

    public void tekenenRNA(String rna) {
        /**
         * deze functie itereerd door de sequentie en tekent de correcte visualisatie erbij
         */
        Graphics paper = p1.getGraphics();
        paper.clearRect(0, 0, 700, 700);
        int x = 50;
        int y = 650 / rna.length();
        for (int i = 0, n = rna.length(); i < n; i++) {
            char c = rna.charAt(i);
            String v = String.valueOf(c);
            if (v.equals("G") | v.equals("C")) {
                System.out.println(v);
                paper.setColor(Color.RED);
                paper.fillRect(x, 10, y, 25);
                x = x + y;
            } else if (v.equals("A") | v.equals("u")) {
                System.out.println(v);
                paper.setColor(Color.BLUE);
                paper.fillRect(x, 10, y, 25);
                x = x + y;
            }
        }
    }

    public void tekenenPRO(String eiwit) {
        /**
         * deze functie itereerd door de sequentie en tekent de correcte visualisatie erbij
         */
        String apolair = "AFILMPWV";
        String polair = "RNDCQEGHKSTY";
        String AA = "RNDCQEGHKSTYAFILMPWV";
        Graphics paper = p1.getGraphics();
        paper.clearRect(0, 0, 700, 700);
        int x = 50;
        int y = 650 / eiwit.length();
        try {
            for (int z = 0; z < eiwit.length(); z++) {
                char w = eiwit.charAt(z);
                String v = String.valueOf(w);
                if (polair.indexOf(v) != -1) {
                    System.out.println(v);
                    paper.setColor(Color.BLUE);
                    paper.fillRect(x, 10, y, 25);
                    x = x + y;
                } else if (apolair.indexOf(v) != -1) {
                    System.out.println(v);
                    paper.setColor(Color.RED);
                    paper.fillRect(x, 10, y, 25);
                    x = x + y;
                } else if (AA.indexOf(v) != -1) {
                    System.out.println(v);
                    paper.setColor(Color.GRAY);
                    paper.fillRect(x, 10, y, 25);
                    x = x + y;
                } else {
                    throw new NotValidProteinCode();
                }
            }
        } catch (NotValidProteinCode notValidProteinCode) {
            notValidProteinCode.printStackTrace();
        }
    }
}

