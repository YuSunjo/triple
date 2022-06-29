alter table review_history
    modify exist_content_point int not null comment '컨텐츠 존재하면 부여하는 포인트';

alter table review_history
    modify content_point int not null comment '나의 컨텐츠 포인트';

alter table review_history
    modify is_content_point int not null comment '컨텐츠 포인트를 받았는지 유/무';

alter table review_history
    modify exist_attached_photo_point int not null comment '사진이 존재하면 부여하는 포인트';

alter table review_history
    modify attached_photo_point int not null comment '나의 사진 포인트';

alter table review_history
    modify is_attached_photo_point int not null comment '사진 포인트를 받았는지 유/무';

alter table review_history
    modify exist_first_review_point int not null comment '첫번째 리뷰이면 부여하는 포인트';

alter table review_history
    modify first_review_point int not null comment '나의 첫번째 리뷰 포인트';

alter table review_history
    modify is_first_review_point int not null comment '첫번째 리뷰 포인트를 받았는지 유/무';