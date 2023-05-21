import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CircleBounce extends JPanel {
    private int circleX = 100; // Початкове положення кола по осі X
    private int circleY = 100; // Початкове положення кола по осі Y
    private int circleRadius = 50; // Радіус кола
    private int dx = 5; // Зміна по осі X при кожному оновленні
    private int dy = 5; // Зміна по осі Y при кожному оновленні

    public CircleBounce() {
        // Додати обробник подій миші до панелі
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Перевірити, чи миша знаходиться всередині кола
                int mouseX = e.getX();
                int mouseY = e.getY();
                if (isInsideCircle(mouseX, mouseY)) {
                    // Змінити напрямок руху кола при торканні границі фрейму
                    dx = -dx;
                    dy = -dy;
                }
            }
        });
    }

    private boolean isInsideCircle(int x, int y) {
        // Перевірити, чи координати (x, y) знаходяться всередині кола
        int centerX = circleX + circleRadius;
        int centerY = circleY + circleRadius;
        int distanceSquared = (x - centerX) * (x - centerX) + (y - centerY) * (y - centerY);
        int radiusSquared = circleRadius * circleRadius;
        return distanceSquared <= radiusSquared;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Намалювати коло
        g.setColor(Color.RED);
        g.fillOval(circleX, circleY, circleRadius * 2, circleRadius * 2);
    }

    public void animate() {
        while (true) {
            // Оновити положення кола
            circleX += dx;
            circleY += dy;

            // Перевірити зіткнення з границями фрейму
            if (circleX < 0 || circleX + circleRadius * 2 > getWidth()) {
                dx = -dx; // Змінити напрямок руху по осі X
            }
            if (circleY < 0 || circleY + circleRadius * 2 > getHeight()) {
                dy = -dy; // Змінити напрямок руху по осі Y
            }

            // Оновити відображення
            repaint();

            try {
                Thread.sleep(10); // Затримка для анімації
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circle Bounce");
        CircleBounce circleBounce = new CircleBounce();
        frame.add(circleBounce);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        circleBounce.animate();
    }
}
