package com.triple.point.domain.history;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewHistory {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private Action action;

    private String reviewId;

    private String content;

    private String userId;

    private String placeId;

    @OneToMany(mappedBy = "reviewHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<HistoryImage> historyImageList = new ArrayList<>();

}
