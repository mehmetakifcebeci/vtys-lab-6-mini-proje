package app.store;

import redis.clients.jedis.Jedis;
import app.model.Student;
import com.google.gson.Gson;
import java.util.List;

public class RedisStore {
    static Jedis jedis;
    static Gson gson = new Gson();

    public static void init(List<Student> students) {
        jedis = new Jedis("localhost", 6379);
        for (Student s : students) {
            jedis.set(s.student_no, gson.toJson(s));
        }
    }

    public static Student get(String id) {
        String json = jedis.get(id);
        return gson.fromJson(json, Student.class);
    }
}
