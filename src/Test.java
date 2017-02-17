import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

public class Test {
	@Autowired
	MongoOperations mongo;
	public  void test() {
      mongo.getCollection("order").count();
	}
	public static void main(String[] args){
		Test t=new Test();
		t.test();
	}
}
