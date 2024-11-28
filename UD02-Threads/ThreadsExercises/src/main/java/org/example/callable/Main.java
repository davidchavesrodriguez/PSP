package org.example.callable;

import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de nuestra tarea Callable
        MyCallableTask task = new MyCallableTask();

        // Crear un hilo que ejecutará la tarea Callable
        Thread thread = new Thread(() -> {
            try {
                // Ejecutar la tarea y obtener el resultado
                String result = task.call();
                System.out.println("Resultado de la tarea: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Iniciar el hilo
        thread.start();

        // Esperar a que termine
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Definir la clase que implementa Callable
    static class MyCallableTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            return "Resultado de la tarea";
        }
    }
}
