public class CounterTest {

    private static final int THREADS = 4;
    private static final int OPERACOES = 1_000_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Threads concorrentes: " + (THREADS * 2));
        System.out.println("Operacoes por thread: " + OPERACOES);
        System.out.println("Valor esperado ao final: 0");
        System.out.println();

        System.out.println("=== Counter (sem synchronized) ===");
        for (int rodada = 1; rodada <= 5; rodada++) {
            System.out.println("Rodada " + rodada + " -> valor final = " + executarSemSincronizacao());
        }

        System.out.println();
        System.out.println("=== SynchronizedCounter (com synchronized) ===");
        for (int rodada = 1; rodada <= 5; rodada++) {
            System.out.println("Rodada " + rodada + " -> valor final = " + executarComSincronizacao());
        }
    }

    private static int executarSemSincronizacao() throws InterruptedException {
        Counter counter = new Counter();
        Thread[] threads = new Thread[THREADS * 2];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < OPERACOES; j++) {
                    counter.increment();
                }
            });
            threads[THREADS + i] = new Thread(() -> {
                for (int j = 0; j < OPERACOES; j++) {
                    counter.decrement();
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        return counter.value();
    }

    private static int executarComSincronizacao() throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        Thread[] threads = new Thread[THREADS * 2];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < OPERACOES; j++) {
                    counter.increment();
                }
            });
            threads[THREADS + i] = new Thread(() -> {
                for (int j = 0; j < OPERACOES; j++) {
                    counter.decrement();
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        return counter.value();
    }
}
