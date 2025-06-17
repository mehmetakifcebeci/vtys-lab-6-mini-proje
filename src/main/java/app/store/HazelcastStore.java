package app.store;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import app.model.Student;
import java.util.List;

public class HazelcastStore {
    static HazelcastInstance hz;
    static IMap<String, Student> map;

    public static void init(List<Student> students) {
        hz = HazelcastClient.newHazelcastClient();
        map = hz.getMap("students");
        for (Student s : students) {
            map.put(s.student_no, s);
        }
    }

    public static Student get(String id) {
        return map.get(id);
    }
}
