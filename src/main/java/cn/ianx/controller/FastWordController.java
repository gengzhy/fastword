package cn.ianx.controller;

import cn.ianx.basic.word.annotation.WordField;
import cn.ianx.basic.word.annotation.WordType;
import cn.ianx.basic.word.engine.DefaultTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.Clock;
import java.time.LocalDateTime;

@RestController
public class FastWordController {

    @Autowired
    private DefaultTemplate template;

    @RequestMapping(value = "generate/resume")
    public String generateResume() throws IOException {
        Resume resume = new Resume();
        resume.name = "耿";
        resume.gender = "男";
        resume.birthday = "1993-10-5";
        resume.age = 28;
        resume.ts = LocalDateTime.now(Clock.systemDefaultZone());

        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/word/resume.docx");
        String newFileName = "耿的简历.docx";
        template.writeWord(file, newFileName, resume);
        return "简历生成成功";
    }

    @WordType
    static class Resume implements Serializable {
        @WordField
        String name;
        @WordField
        String gender;
        @WordField
        String birthday;
        @WordField
        int age;
        @WordField
        LocalDateTime ts;
    }
}
