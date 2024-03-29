package com.won.dourbest.seller.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FundingStatusListDTO {

    private int fundingStatusHistoryCode;
    private Date changeDate;
    private int fundingStatusCode;
    private int fundingCode;
}
