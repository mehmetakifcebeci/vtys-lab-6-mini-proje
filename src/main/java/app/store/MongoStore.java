package app.store;

import com.mongodb.client.*;
import org.bson.Document;
import app.model.Student;
import com.google.gson.Gson;
import java.util.List;

public class MongoStore {
    static MongoClient client;
    static MongoCollection<Document> collection;
    static Gson gson = new Gson();

    public static void init(List<Student> students) {
        client = MongoClients.create("mongodb://localhost:27017");
        collection = client.getDatabase("nosqllab").getCollection("students");
        collection.drop();
        for (Student s : students) {
            collection.insertOne(Document.parse(gson.toJson(s)));
        }
    }

    public static Student get(String id) {
        Document doc = collection.find(new Document("student_no", id)).first();
        return doc != null ? gson.fromJson(doc.toJson(), Student.class) : null;
    }
}
