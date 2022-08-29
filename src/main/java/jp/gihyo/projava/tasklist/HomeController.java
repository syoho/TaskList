package jp.gihyo.projava.tasklist;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {

    @RequestMapping(value="/hello")
    String hello(Model model){
        model.addAttribute("time",LocalDateTime.now());
        return "hello";
    }


    //タスクを表すTaskItemレコードと、それを格納するためのtaskItemsフィールドを宣言する
    record TaskItem(String id, String task,String deadline,boolean done){}
    private List<TaskItem>taskItems = new ArrayList<>();


    //タスクを一覧表示するエンドポイント
    @GetMapping("/list")
    String listItems(Model model){
        model.addAttribute("tasklist",taskItems);
        return "home";
    }

    //タスク登録のためのエンドポイント
    @GetMapping("/add")
    String addItem(@RequestParam("task")String task,
        @RequestParam("deadline")String deadline){
        String id = UUID.randomUUID().toString().substring(0,8);
        TaskItem item = new TaskItem(id, task, deadline, false);
        taskItems.add(item);
        return "redirect:/list";
    }



/*@RequestMapping(value = "/hello")
    @ResponseBody
    String hello(){
        return """
                <html>
                 <head><title>Hello</title></head>
                 <body>
                 <h1>Hello</h1>
                 It works!<br>
                 現在の時刻は%sです。
                 </body>
                </html>
                """.formatted(LocalDateTime.now());
    }*/
}
