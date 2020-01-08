package cn.codeyourlife.controller;

/**
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/8
 */
public class RunCodeController {
    private static final String defaultSource = "public class Run {\n"
            + "    public static void main(String[] args) {\n"
            + "        \n"
            + "    }\n"
            + "}";

//    public String entry(Model model) {
//        model.addAttribute("lastSource", defaultSource);
//        return "ide";
//    }
//
//    public String runCode(@RequestParam("source") String source,
//                          @RequestParam("systemIn") String systemIn, Model model) {
//        String runResult = executeStringSourceService.execute(source, systemIn);
//        runResult = runResult.replaceAll(System.lineSeparator(), "<br/>"); // 处理html中换行的问题
//
//        model.addAttribute("lastSource", source);
//        model.addAttribute("lastSystemIn", systemIn);
//        model.addAttribute("runResult", runResult);
//        return "ide";
//    }
}
