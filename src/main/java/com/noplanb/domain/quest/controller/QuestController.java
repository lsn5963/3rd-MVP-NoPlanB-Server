package com.noplanb.domain.quest.controller;


import com.noplanb.domain.quest.application.QuestService;
import com.noplanb.domain.quest.dto.req.CreateQuestReq;
import com.noplanb.domain.quest.dto.req.ModifyQuestReq;
import com.noplanb.domain.quest.dto.res.RetrieveCompleteQuest;
import com.noplanb.domain.quest.dto.res.RetrieveLevelAndTodayExpRes;
import com.noplanb.domain.quest.dto.res.RetrieveQuestRes;
import com.noplanb.global.config.security.token.CurrentUser;
import com.noplanb.global.config.security.token.UserPrincipal;
import com.noplanb.global.payload.ErrorResponse;
import com.noplanb.global.payload.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/quest")
@Tag(name = "Quest", description = "퀘스트 관련 API")
public class QuestController {
    private final QuestService questService;

    @PostMapping
    @Operation(summary = "퀘스트생성", description = "퀘스트를 생성할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 작성 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "퀘스트 작성 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<?> createQuest(
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "퀘스트생성 dto Req입니다.", required = true) @RequestBody CreateQuestReq createQuestReq){

        return questService.createQuest(createQuestReq, userPrincipal);
    }
    @GetMapping("/cache/{date}/{userId}")
    @Operation(summary = "퀘스트조회", description = "퀘스트를 조회할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RetrieveQuestRes.class))}),
            @ApiResponse(responseCode = "400", description = "퀘스트 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<?> yesretrieveQuest(
            @Parameter(example = "2024-08-09", description = "날짜를 입력해주세요", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @PathVariable Long userId){
        return ResponseEntity.ok(createApiResponse(questService.cacheretrieveQuest(date,userId)));
    }
    @GetMapping("/nocache/{date}/{userId}")
    @Operation(summary = "퀘스트조회", description = "퀘스트를 조회할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RetrieveQuestRes.class))}),
            @ApiResponse(responseCode = "400", description = "퀘스트 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<?> noretrieveQuest(
            @Parameter(example = "2024-08-09", description = "날짜를 입력해주세요", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @PathVariable Long userId){
        return ResponseEntity.ok(createApiResponse(questService.nocacheretrieveQuest(date,userId)));
    }
    @GetMapping
    @Operation(summary = "메인페이지 상당부분", description = "메인페이지 상당부부을 조회할 떄 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메인페이지 상당부분 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RetrieveLevelAndTodayExpRes.class))}),
            @ApiResponse(responseCode = "400", description = "메인페이지 상당부분 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<?> retrieveLevelAndTodayExp(
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal){
        return questService.retrieveLevelAndTodayExp(userPrincipal);
    }
    @PostMapping("/{id}")
    @Operation(summary = "퀘스트 완료", description = "퀘스트 완료할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 완료 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RetrieveCompleteQuest.class))}),
            @ApiResponse(responseCode = "400", description = "퀘스트 완료 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<?> completeQuest(
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "완료할 퀘스트 아이디를 입력해주세요.", required = true) @PathVariable Long id){
        return questService.completeQuest(userPrincipal,id);
    }

    @PatchMapping
    @Operation(summary = "퀘스트 수정", description = "퀘스트 수정할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 수정 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "퀘스트 수정 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<?> modifyQuest(
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "퀘스트수정 dto Req입니다.", required = true) @RequestBody ModifyQuestReq modifyQuestReq) {
        return questService.modifyQuest(userPrincipal, modifyQuestReq);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "퀘스트 삭제", description = "퀘스트 삭제할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 삭제 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "퀘스트 삭제 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<?> deleteQuest(
            @Parameter(description = "Access Token을 입력해주세요.", required = true)  UserPrincipal userPrincipal,
            @Parameter(description = "삭제할 퀘스트 아이디를 입력해주세요.", required = true) @PathVariable Long id){
        return questService.deleteQuest(userPrincipal,id);
    }

    private <T> ResponseEntity<com.noplanb.global.payload.ApiResponse> createApiResponse(T information) {
        com.noplanb.global.payload.ApiResponse apiResponse = com.noplanb.global.payload.ApiResponse.builder()
                .check(true)
                .information(information)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}