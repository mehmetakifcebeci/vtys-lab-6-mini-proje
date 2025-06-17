package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.store.*;
import app.model.Student;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        port(8080);
        Gson gson = new Gson();

        // 10.000 Türkçe ve rastgele öğrenci oluştur
        List<String> isimler = Arrays.asList("Ahmet", "Mehmet", "Ayşe", "Fatma", "Zeynep", "Ali", "Veli", "Elif", "Deniz", "Can");
        List<String> soyadlar = Arrays.asList("Yılmaz", "Kaya", "Demir", "Çelik", "Şahin", "Koç", "Aydın", "Öztürk", "Arslan", "Doğan");
        List<String> bolumler = Arrays.asList("Bilgisayar Mühendisliği", "Elektrik-Elektronik Mühendisliği", "Makine Mühendisliği", "İşletme", "Matematik", "Fizik", "Kimya", "Türk Dili ve Edebiyatı", "Psikoloji", "Mimarlık");
        Random rnd = new Random();
        List<Student> ogrenciler = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            String id = "2025" + String.format("%06d", i);
            String isim = isimler.get(rnd.nextInt(isimler.size()));
            String soyad = soyadlar.get(rnd.nextInt(soyadlar.size()));
            String adSoyad = isim + " " + soyad;
            String bolum = bolumler.get(rnd.nextInt(bolumler.size()));
            ogrenciler.add(new Student(id, adSoyad, bolum));
        }

        // Aynı öğrenci listesini üç veritabanına da ekle
        RedisStore.init(ogrenciler);
        HazelcastStore.init(ogrenciler);
        MongoStore.init(ogrenciler);

        get("/nosql-lab-rd/:id", (req, res) ->
            gson.toJson(RedisStore.get(req.params(":id"))));

        get("/nosql-lab-hz/:id", (req, res) ->
            gson.toJson(HazelcastStore.get(req.params(":id"))));

        get("/nosql-lab-mon/:id", (req, res) ->
            gson.toJson(MongoStore.get(req.params(":id"))));
    }
}
