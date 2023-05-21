import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MatrixAnalyzer extends JFrame {
    private JTextField dimensionField;
    private JButton analyzeButton;
    private JTable matrixTable;
    private DefaultTableModel tableModel;

    public MatrixAnalyzer() {
        setTitle("Matrix Analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель введення розмірності матриці
        JPanel inputPanel = new JPanel();
        JLabel dimensionLabel = new JLabel("Розмірність матриці:");
        dimensionField = new JTextField(10);
        inputPanel.add(dimensionLabel);
        inputPanel.add(dimensionField);

        // Панель кнопки аналізу та таблиці матриці
        JPanel buttonPanel = new JPanel();
        analyzeButton = new JButton("Аналізувати");
        buttonPanel.add(analyzeButton);

        // Таблиця матриці
        tableModel = new DefaultTableModel();
        matrixTable = new JTable(tableModel);

        // Додавання компонентів до контейнера
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(matrixTable), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Вирівнювання по центру екрану

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzeMatrix();
            }
        });
    }

    private void analyzeMatrix() {
        int n = Integer.parseInt(dimensionField.getText());
        int[][] x = new int[n][n];
        boolean[] l = new boolean[n];

        try {
            // Відкриття файлу для читання вхідних даних
            File inputFile = new File("matrix_input.txt");
            Scanner scanner = new Scanner(inputFile);

            // Читання елементів матриці
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (scanner.hasNextInt()) {
                        x[i][j] = scanner.nextInt();
                    } else {
                        throw new FileNotFoundException("Невірний формат вхідних даних");
                    }
                }
            }

            // Аналіз матриці
            for (int i = 0; i < n; i++) {
                int positiveCount = 0;
                int negativeCount = 0;
                for (int j = 0; j < n; j++) {
                    if (x[i][j] > 0) {
                        positiveCount++;
                    } else if (x[i][j] < 0) {
                        negativeCount++;
                    }
                }
                l[i] = negativeCount > positiveCount;
            }

            // Відображення результатів
            displayResults(l);

            scanner.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Помилка: Файл не знайдено", "Помилка", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Помилка: Невірний формат розмірності матриці", "Помилка", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Помилка: " + ex.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayResults(boolean[] l) {
        // Очищення таблиці
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);

        // Додавання стовпців до таблиці
        for (int i = 0; i < l.length; i++) {
            tableModel.addColumn("Елемент " + (i + 1));
        }

        // Додавання рядків до таблиці
        Object[] row = new Object[l.length];
        for (int i = 0; i < l.length; i++) {
            row[i] = l[i];
        }
        tableModel.addRow(row);

        // Оновлення відображення таблиці
        tableModel.fireTableDataChanged();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MatrixAnalyzer matrixAnalyzer = new MatrixAnalyzer();
                matrixAnalyzer.setVisible(true);
            }
        });
    }
}
