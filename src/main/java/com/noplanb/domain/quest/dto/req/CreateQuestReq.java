package com.noplanb.domain.quest.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateQuestReq {
    @Schema(type = "String", example = "청소하기", description = "퀘스트 내용")
    private String contents;
    @Schema(type = "Long", example = "5", description = "퀘스트 경험치")
    private Long exp;
}
