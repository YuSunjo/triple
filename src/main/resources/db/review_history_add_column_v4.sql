alter table review_history
    add is_content_point bit not null;

alter table review_history
    add is_attached_photo_point bit not null;

alter table review_history
    add is_first_review_point bit not null;