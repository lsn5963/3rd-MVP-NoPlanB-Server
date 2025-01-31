package com.noplanb.domain.quest.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetrieveQuestRes {
    @Schema(type = "Long", example = "1", description = "퀘스트 아이디")
    private Long id;
    @Schema(type = "String", example = "청소하기", description = "퀘스트 내용")
    private String contents;
    @Schema(type = "Long", example = "5", description = "퀘스트 경험치")
    private Long exp;
    @Schema(type = "Boolean", example = "true", description = "퀘스트 완료 여부")
    private Boolean isComplete;
}
