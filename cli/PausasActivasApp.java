import java.util.Timer;
import java.util.TimerTask;

public class PausasActivasApp {
    private Timer timer;
    private int intervaloPausa = 10 * 1000; // Intervalo de pausa en milisegundos (10 segundos por defecto)
    private int duracionPausa = 15 * 1000; // Duración de la pausa en milisegundos (15 segundos por defecto)
    private boolean pausaActiva = false;

    public PausasActivasApp() {
        iniciarPausas();
    }

    private void iniciarPausas() {
        // Esperar 10 segundos antes de iniciar la primera pausa activa
        try {
            Thread.sleep(intervaloPausa);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Iniciar la primera pausa activa
        ejecutarPausaActiva();

        // Espera para permitir la ejecución de las pausas activas
        try {
            Thread.sleep(duracionPausa + 1000); // Espera hasta que se complete la pausa activa
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pausaActiva = false;
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
                    System.out.println("Estado: Pausa finalizada");
                    emitirBeep(); // Emitir beep al finalizar la pausa
                    timer.cancel();
                    pausaActiva = false;
                } else {
                    System.out.println("Estado: Pausa activa (" + contador + " segundos restantes)");
                    if (contador % 5 == 0 && ejercicioIndex < ejercicios.length) {
                        System.out.println("Acción recomendada: " + ejercicios[ejercicioIndex]);
                        ejercicioIndex++;
                    }
                    contador--;
                }
            }
        }, 0, 1000); // Ejecutar cada 1 segundo
    }

    // Método para emitir un beep (simulado)
    private void emitirBeep() {
        System.out.println("\u0007"); // Código de escape para emitir un beep en la consola
    }

    public static void main(String[] args) {
        new PausasActivasApp();
    }
}
