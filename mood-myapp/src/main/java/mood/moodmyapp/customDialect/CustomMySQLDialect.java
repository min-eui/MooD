package mood.moodmyapp.customDialect;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomMySQLDialect  extends MySQL57Dialect {
    public CustomMySQLDialect() {
        registerFunction("GROUP_CONCAT", new StandardSQLFunction("GROUP_CONCAT", StandardBasicTypes.STRING));
    }

}
