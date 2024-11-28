package gal.classexercises.producerconsumer.pizza;

public class LaunchProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        ResourcePizzaTray resourceTray = new ResourcePizzaTray();

        ProducerCook producerCook = new ProducerCook(resourceTray);
        ConsumerDeliverer consumerDeliverer = new ConsumerDeliverer(resourceTray);

        Thread threadProducer = new Thread(producerCook);
        Thread threadConsumer = new Thread(consumerDeliverer);

        threadProducer.start();
        threadConsumer.start();

        threadProducer.join();
        threadConsumer.join();
    }
}
