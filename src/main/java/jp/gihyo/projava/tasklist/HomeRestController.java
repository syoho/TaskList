//@RestControllerアノテーションでコントローラを作成する

package jp.gihyo.projava.tasklist;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController//@RestControllerアノテーションはユーザーインタフェースとしてのHTMLを返さないWEBアプリケーションを作る場合に使われます
public class HomeRestController{
    @RequestMapping("/resthello")//リクエストを処理するメソッドであることを表す
    String hello(){
        return """
                Hello.
                It works!
                現在時刻は％sです。
                """.formatted(LocalDateTime.now());
    }
}


