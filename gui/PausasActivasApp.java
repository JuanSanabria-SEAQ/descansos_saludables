import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class PausasActivasApp {
    private Timer timer;
    private int intervaloPausa = 10 * 1000; // Intervalo de pausa en milisegundos (10 segundos por defecto)
    private int duracionPausa = 15 * 1000; // Duración de la pausa en milisegundos (15 segundos por defecto)
    private boolean pausaActiva = false;

    private JLabel estadoLabel;
    private JLabel ejercicioLabel;
    private JFrame frame;

    public PausasActivasApp() {
        frame = new JFrame("Pausas Activas App");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1));

        estadoLabel = new JLabel("Esperando para iniciar...", SwingConstants.CENTER);
        ejercicioLabel = new JLabel("", SwingConstants.CENTER);

        frame.add(estadoLabel);
        frame.add(ejercicioLabel);

        frame.setVisible(true);

        // Iniciar pausas activas después de abrir la aplicación
        iniciarPausas();
    }

    private void iniciarPausas() {
        // Esperar 10 segundos antes de iniciar la primera pausa activa
        Timer inicioTimer = new Timer();
        inicioTimer.schedule(new TimerTask() {
            public void run() {
                // Iniciar la primera pausa activa
                ejecutarPausaActiva();
            }
        }, intervaloPausa);
    }

    private void ejecutarPausaActiva() {
        pausaActiva = true;
        String[] ejercicios = {
            "Estiramiento de brazos.",
            "Giros de cuello.",
            "Flexiones de piernas.",
            "Estiramiento de espalda."
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private int contador = duracionPausa / 1000;
            private int ejercicioIndex = 0;

            public void run() {
                if (contador == 0) {
                    estadoLabel.setText("Estado: Pausa finalizada");
                    emitirBeep(); // Emitir beep al finalizar la pausa
                    ejercicioLabel.setText("");
                    timer.cancel();
                    pausaActiva = false;
                    // Cerrar la interfaz gráfica
                    frame.dispose();
                } else {
                    estadoLabel.setText("Estado: Pausa activa (" + contador + " segundos restantes)");
                    if (contador % 5 == 0 && ejercicioIndex < ejercicios.length) {
                        ejercicioLabel.setText("Acción recomendada: " + ejercicios[ejercicioIndex]);
                        ejercicioIndex++;
                    }
                    contador--;
                }
            }
        }, 0, 1000); // Ejecutar cada 1 segundo
    }

    // Método para emitir un beep (simulado)
    private void emitirBeep() {
        Toolkit.getDefaultToolkit().beep(); // Emite un sonido beep
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PausasActivasApp();
            }
        });
    }
}
