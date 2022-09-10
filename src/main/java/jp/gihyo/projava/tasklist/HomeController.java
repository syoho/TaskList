package jp.gihyo.projava.tasklist;


import org.springframework.beans.factory.annotation.Autowired;
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



    //フィールド宣言する
    //-フィールド
    //-field/字段
    //-オブジェクトに関する情報を保持する
    private final TaskListDao dao;




    //タスクを表すTaskItemレコードと、それを格納するためのtaskItemsフィールドを宣言する
    record TaskItem(String id, String task,String deadline,boolean done){}
    private List<TaskItem>taskItems = new ArrayList<>();



    //初期化する
    //TaskListDaoクラスのコンストラクタ
    @Autowired
    HomeController(TaskListDao dao){
        this.dao = dao;
    }



    //タスク登録のためのエンドポイント
    //addItemメソッドは”/add”パスに紐づける（ひもづける）
    @GetMapping("/add")
    String addItem(@RequestParam("task")String task,
                   @RequestParam("deadline")String deadline) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, task, deadline, false);
        dao.add(item);
        return "redirect:/list";
    }
/*    @GetMapping("/add")
    String addItem(@RequestParam("task")String task,
        @RequestParam("deadline")String deadline){
        String id = UUID.randomUUID().toString().substring(0,8);
        TaskItem item = new TaskItem(id, task, deadline, false);
        taskItems.add(item);
        return "redirect:/list";
    }*/



    //タスクを一覧表示するエンドポイント
    //findAll()メゾットはTaskItem要素（ようそ）に持つListオブジェクトを返してくれる
    @GetMapping("/list")
    String listItems(Model model){
        List<TaskItem>taskItems = dao.findAll();
        model.addAttribute("tasklist",taskItems);
        return "home";
    }
/*
    @GetMapping("/list")
    String listItems(Model model){
        model.addAttribute("tasklist",taskItems);
        return "home";
    }
*/



    //タスク情報を削除するエンドポイント
    @GetMapping("/delete")
    String deleteItem(@RequestParam("id")String id){
        dao.delete(id);
        return "redirect:/list";
    }



    //タスク情報を更新するエンドポイント
    @GetMapping("/update")
    String updateItem(@RequestParam("id")String id,
                      @RequestParam("task")String task,
                      @RequestParam("deadline")String deadline,
                      @RequestParam("done")boolean done){
        TaskItem taskItem = new TaskItem(id,task,deadline,done);
        dao.update(taskItem);
        return "redirect:/list";
    }




    @RequestMapping(value="/hello")
    String hello(Model model){
        model.addAttribute("time",LocalDateTime.now());
        return "hello";
    }






}
