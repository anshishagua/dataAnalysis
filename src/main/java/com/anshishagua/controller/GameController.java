package com.anshishagua.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: lixiao
 * Date: 2018/6/2
 * Time: 上午11:20
 */

@Controller
@RequestMapping("/game")
public class GameController {
    @RequestMapping("")
    public String index() {
        return "game/index";
    }
}
