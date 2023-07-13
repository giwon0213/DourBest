package com.won.dourbest.seller.controller;

import com.won.dourbest.seller.dto.QuestionDTO;
import com.won.dourbest.seller.service.WritingService;
import com.won.dourbest.seller.service.WritingServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/noticeWriting")
public class NoticeWritingController {

    private final WritingServiceImpl service;

    public NoticeWritingController(WritingServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/writing")
    public String writing() {

        return "/seller/giwon_seller/writing";
    }

    @PostMapping("/writing")
    @ResponseBody
    public QuestionDTO writing(@RequestParam("title") String title, @RequestParam("text") String text, @RequestParam("selectBox") String selectBox) {
        QuestionDTO question = new QuestionDTO();
        question.setInquiryTitle(title); // 입력한 제목
        question.setInquiryContent(text); // 입력한 내용

        service.registNoticeWriting(question);

        return question;
    }
}
