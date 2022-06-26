alter table review_history
    add exist_content_point int not null;

alter table review_history
    add content_point int not null;

alter table review_history
    add exist_attached_photo_point int not null;

alter table review_history
    add attached_photo_point int not null;

alter table review_history
    add exist_first_review_point int not null;

alter table review_history
    add first_review_point int not null;