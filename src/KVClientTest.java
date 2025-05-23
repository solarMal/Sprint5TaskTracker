import httpclient.KVTaskClient;

public class KVClientTest {
    public static void main(String[] args) {
        KVTaskClient client = new KVTaskClient("http://localhost:8080");

        client.put("task1", "первая задача");
        client.put("task2", "вторая задача");

        // Загружаем значения
        String task1 = client.load("task1");
        String task2 = client.load("task2");

        System.out.println("task1 = " + task1);
        System.out.println("task2 = " + task2);

        // Перезаписываем task1
        client.put("task1", "обновлённая задача");

        // Проверяем, что task1 изменился
        String updatedTask1 = client.load("task1");
        System.out.println("обновлённый task1 = " + updatedTask1);
    }
}
