import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class analogClock {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Analog Clock");
        ClockPanel clockPanel = new ClockPanel();
        frame.add(clockPanel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clockPanel.repaint();
            }
        });
        timer.start();
    }

    static class ClockPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Get the current time
            Date currentTime = new Date();

            // Format the time using SimpleDateFormat
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            String formattedTime = dateFormat.format(currentTime);

            // Get the current hour, minute, and second
            int hour = Integer.parseInt(formattedTime.substring(0, 2));
            int minute = Integer.parseInt(formattedTime.substring(3, 5));
            int second = Integer.parseInt(formattedTime.substring(6, 8));

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = Math.min(getWidth(), getHeight()) / 2 - 10;

            // Draw the clock face
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

            // Draw the clock face numbers
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            for (int i = 1; i <= 12; i++) {
                double angle = Math.toRadians((i - 3) * 30);
                int numberX = (int) (centerX + (radius - 20) * Math.cos(angle));
                int numberY = (int) (centerY + (radius - 20) * Math.sin(angle));
                g2d.drawString(Integer.toString(i), numberX, numberY);
            }

            // Draw the second hand marks
            g2d.setColor(Color.BLACK);
            for (int i = 0; i < 60; i++) {
                double angle = Math.toRadians(i * 6);
                int startX = (int) (centerX + (radius - 10) * Math.cos(angle));
                int startY = (int) (centerY + (radius - 10) * Math.sin(angle));
                int endX = (int) (centerX + radius * Math.cos(angle));
                int endY = (int) (centerY + radius * Math.sin(angle));
                g2d.drawLine(startX, startY, endX, endY);
            }

            // Draw the hour hand
            double hourAngle = Math.toRadians((hour % 12) * 30 + (minute / 60.0) * 30);
            int hourHandLength = radius * 2 / 3;
            int hourHandX = (int) (centerX + hourHandLength * Math.sin(hourAngle));
            int hourHandY = (int) (centerY - hourHandLength * Math.cos(hourAngle));
            g2d.setColor(Color.BLACK);
            g2d.drawLine(centerX, centerY, hourHandX, hourHandY);

            // Draw the minute hand
            double minuteAngle = Math.toRadians(minute * 6 + (second / 60.0) * 6);
            int minuteHandLength = radius * 9 / 10;
            int minuteHandX = (int) (centerX + minuteHandLength * Math.sin(minuteAngle));
            int minuteHandY = (int) (centerY - minuteHandLength * Math.cos(minuteAngle));
            g2d.setColor(Color.BLACK);
            g2d.drawLine(centerX, centerY, minuteHandX, minuteHandY);

            // Draw the second hand
            double secondAngle = Math.toRadians(second * 6);
            int secondHandLength = radius - 10;
            int secondHandX = (int) (centerX + secondHandLength * Math.sin(secondAngle));
            int secondHandY = (int) (centerY - secondHandLength * Math.cos(secondAngle));
            g2d.setColor(Color.BLUE);
            g2d.drawLine(centerX, centerY, secondHandX, secondHandY);
        }
    }
}

