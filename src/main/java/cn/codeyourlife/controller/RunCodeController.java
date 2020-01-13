package cn.codeyourlife.controller;

import cn.codeyourlife.server.annotation.*;
import cn.codeyourlife.server.io.ResponseEntity;
import cn.codeyourlife.service.ExecuteStringSourceService;
import com.alibaba.fastjson.JSONObject;

/**
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/8
 */

@RestController
@RequestMapping("/ide")
public class RunCodeController {
    public ExecuteStringSourceService executeStringSourceService = new ExecuteStringSourceService();
    private static final String defaultSource = "public class Run {\n"
            + "    public static void main(String[] args) {\n"
            + "        \n"
            + "    }\n"
            + "}";

    @GetMapping("")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok().build("hello world");
    }


    @GetMapping("/lastSource")
    public ResponseEntity<?> lastSource() {
        JSONObject res = new JSONObject();
        res.put("lastSource", defaultSource);
        return ResponseEntity.ok().build(res);
    }

    @PostMapping("/run")
    public ResponseEntity<?> runCode(@RequestBody String body) {
        JSONObject json = JSONObject.parseObject(body);
        // source code
        String source = json.getString("source");
        // standard in;
        String systemIn = json.getString("systemIn");
        String runResult = executeStringSourceService.execute(source, systemIn);
        // 处理html中换行的问题
        runResult = runResult.replaceAll(System.lineSeparator(), "<br/>");

        JSONObject res = new JSONObject();
        res.put("lastSource", source);
        res.put("lastSystemIn", systemIn);
        res.put("runResult", runResult);
        return ResponseEntity.ok().build(res);
    }
}
