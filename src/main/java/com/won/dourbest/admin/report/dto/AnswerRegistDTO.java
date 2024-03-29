package com.won.dourbest.admin.report.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRegistDTO {

    // 신고 답변
    private String answerContent;
    // 신고 당한 펀딩의 이름
    private String choiceValue;
    // 내가 선택한 펀딩
    private int fundingCode;
}
