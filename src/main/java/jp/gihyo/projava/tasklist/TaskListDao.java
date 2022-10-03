
//データベース操作用のクラスを作成する

//-DAO
//-Data Access Object
//-データベースにアクセスするための窓口となるオブジェクト
//-TaskListDaoクラス

//JDBC（Java DataBase Connectivity）
//JavaのプログラムからRDBMSにアクセスしてデータの読み書きを行う
//API
//JDBCに関連するクラスやインタフェースはjava.sqlパッケージに実装されている

package jp.gihyo.projava.tasklist;

import jp.gihyo.projava.tasklist.HomeController.TaskItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;



@Service
public class TaskListDao {

    //JdbcTemplateはSpring JDBCに用意されているタスク・データベースを活用するための機能を提供している
    //変数はfinalになっているので、一度値をセットしたら変更できません
    private final JdbcTemplate jdbcTemplate;



    //コンストラクタ・constructor・构造器
    //new创建对象；constructor完成对新对象的初始化
    //特点：1）方法名与类名相同；2）没有返回值；3）系统自动调用该类构造期完成对象的初始化
    //フィールド値の初期化・Field
    //@Autowiredアノテーションを付けることで、SpringBootはコンストラクタを呼び出す際に適切なオブジェクトを作成して渡してくれるようになります
    //DI・Dependency Injection・依存性の注入（ちゅうにゅう）
    @Autowired
    TaskListDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }



    public void add(TaskItem taskItem){
        SqlParameterSource param = new BeanPropertySqlParameterSource(taskItem);
        SimpleJdbcInsert insert =
                new SimpleJdbcInsert(jdbcTemplate).withTableName("tasklist");
        insert.execute(param);
    }



    public List<TaskItem> findAll(){
        String query = "SELECT * FROM tasklist";

        List<Map<String,Object>>result = jdbcTemplate.queryForList(query);
        List<TaskItem>taskItems = result.stream().map((Map<String,Object> row) -> new TaskItem(
                row.get("id").toString(),
                row.get("task").toString(),
                row.get("deadline").toString(),
                (Boolean)row.get("done")))
                .toList();
        return taskItems;
    }



    //指定したレコードを削除するdeleteメソードを追加する
    public int delete(String id){
        int number = jdbcTemplate.update("DELETE FROM tasklist WHERE id =?",id);
        return number;
    }



    //指定したレコードの登録内容を更新するためのupdateメソッドを追加する
    public int update(TaskItem taskItem){
        int number = jdbcTemplate.update(
                "UPDATE tasklist SET task = ?,deadline = ?,done = ? WHERE id = ?",//注意：間違いを犯す可能性が高い
                taskItem.task(),
                taskItem.deadline(),
                taskItem.done(),
                taskItem.id());
        return number;
    }
}
