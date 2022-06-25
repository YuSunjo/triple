-- 적립 포인트
create table point
(
    id      varchar(255) not null
        primary key,
    created_date      datetime     null,
    delete_date       datetime     null,
    updated_date      datetime     null,
    user_id varchar(255) not null
);

create index point_user_index
    on point (user_id);

-- 리뷰
create table review
(
    id       varchar(255) not null
        primary key,
    created_date      datetime     null,
    delete_date       datetime     null,
    updated_date      datetime     null,
    content  varchar(255) null,
    place_id varchar(255) not null,
    title    varchar(255) not null
);

create index review_place_index
    on review (place_id);

-- 리뷰 히스토리의 이미지
create table history_image
(
    id                varchar(255) not null
        primary key,
    created_date      datetime     null,
    delete_date       datetime     null,
    updated_date      datetime     null,
    review_history_id varchar(255) not null,
    constraint history_image_review_history_fk
        foreign key (review_history_id) references review_history (id)
);

create index history_image_review_history_index
    on history_image (review_history_id);

-- 리뷰 히스토리
create table review_history
(
    id         varchar(255) not null
        primary key,
    created_date      datetime     null,
    delete_date       datetime     null,
    updated_date      datetime     null,
    action     varchar(255) not null,
    content    varchar(255) null,
    event_type varchar(255) not null,
    place_id   varchar(255) not null,
    review_id  varchar(255) not null,
    user_id    varchar(255) not null
);
