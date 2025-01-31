package com.noplanb.domain.quest.domain;

import com.noplanb.domain.character.domain.Character;
import com.noplanb.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Quest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String contents;
    private Long exp;
    private Boolean isComplete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;
    private LocalDateTime createdTime;


    public void updateCharacter(Character character) {
        this.character = character;
    }
    public void updateContents(String contents) {
        this.contents = contents;
    }
    public void updateIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }
}
