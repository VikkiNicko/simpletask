package com.simpletask.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/info")
public class InfoController {

    @GetMapping
    public ResponseEntity<String> getInfo() {
        String mainInfo = "Welcome to SimpleTask!\n" +
                "You can create new task by sending request:\n" +
                "POST http://localhost:8080/api/task/create\n" +
                "Example:\n" +
                "{\n" +
                "    \"text\": \"new EASY task\",\n" +
                "    \"level\":\"EASY\"\n" +
                "}\n" +
                "There are 4 levels of task's difficulty: DAILY, EASY, MEDIUM and HARD \n" +
                "If you want to attach reward to your task, you can specify reward within creation process by using rewardText field:\n" +
                " {\n" +
                "    \"text\": \"new EASY task\",\n" +
                "    \"level\":\"EASY\",\n" +
                "    \"rewardText\" : \"new reward text\"\n" +
                "}\n" +
                "reward with the same level of difficulty will be created.\n" +
                "Also you can attach reward later. First you need to create it by sending request:\n" +
                "POST http://localhost:8080/api/reward/create\n" +
                "Example:\n" +
                "{\n" +
                "    \"text\": \"new reward for EASY task\",\n" +
                "    \"level\": \"EASY\"\n" +
                "}\n" +
                "and then you can send request\n" +
                "PUT http://localhost:8080/api/task/:taskId/setReward/:rewardId\n" +
                "to attach new reward.\n" +
                "You can only attach reward with the same level of difficulty!\n" +
                "To view all suitable rewards for task send request:\n" +
                "GET http://localhost:8080/api/task/:taskId/findReward\n" +
                "To view all tasks and rewards use respectively:\n" +
                "GET http://localhost:8080/api/task/active\n" +
                "GET http://localhost:8080/api/reward/all\n" +
                "To edit task or reward use respectively:\n" +
                "PUT http://localhost:8080/api/task/edit\n" +
                "PUT http://localhost:8080/api/reward/edit\n" +
                "\n" +
                "When you have completed task you can mark it done by using:\n" +
                "PUT http://localhost:8080/api/task/:taskId/markDone\n" +
                "\n" +
                "If you had a reward attached to the task, you can take by using:\n" +
                "PUT http://localhost:8080/api/task/:taskId/takeReward\n" +
                "\n" +
                "When you took the reward and completely sure that task is finished you can finish it by using:\n" +
                "PUT http://localhost:8080/api/task/:taskId/finish\n" +
                "\n" +
                "If you want to save the info about that task tou can send it to archive by using:\n" +
                "PUT http://localhost:8080/api/task/:taskId/sendToArchive\n" +
                "\n" +
                "If you want to remove task completely use:\n" +
                "DELETE http://localhost:8080/api/task/:taskId/delete\n" +
                "\n" +
                "The same way you can also delete a reward:\n" +
                "DELETE http://localhost:8080/api/reward/:rewardId/delete";
        return new ResponseEntity<>(mainInfo, HttpStatus.OK);
    }

    @GetMapping("/start")
    public ResponseEntity<String> getStarted() {
        String startInfo = "Welcome to SimpleTask!\n" +
                "To use app you need to registered. You can register by sending request:\n" +
                "POST http://localhost:8080/api/auth/register\n" +
                "Example:\n" +
                "{\n" +
                "    \"username\":\"test_user\",\n" +
                "    \"password\":\"test\",\n" +
                "\t\"confirmPassword\":\"test\",\n" +
                "    \"nickname\":\"mrTestik\"\n" +
                "}\n" +
                "You will receive token, which you should use in every following request.\n" +
                "If you have account and your token expired - to get a new token please send request:\n" +
                "POST http://localhost:8080/api/auth/login ";
        return new ResponseEntity<>(startInfo, HttpStatus.OK);
    }

}
