-- 적립 포인트
create table point
(
    id      binary(16)   not null
        primary key,
    user_id varchar(255) null
);

create index point_user_index
    on point (user_id);


-- 리뷰
create table review
(
    id       binary(16)   not null
        primary key,
    content  varchar(255) null,
    place_id varchar(255) null,
    title    varchar(255) null
);

create index review_place_index
    on review (place_id);

-- 리뷰 히스토리 남기기
create table history_image
(
    id                binary(16) not null
        primary key,
    review_history_id binary(16) not null,
    constraint history_image_review_history_fk
        foreign key (review_history_id) references review_history (id)
);

create index history_image_review_history_index
    on history_image (review_history_id);



create table review_history
(
    id         binary(16)   not null
        primary key,
    action     varchar(255) null,
    content    varchar(255) null,
    event_type varchar(255) null,
    place_id   varchar(255) null,
    review_id  varchar(255) null,
    user_id    varchar(255) null
);
