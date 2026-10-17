ALTER TABLE sub_category
    ADD status BOOLEAN;

ALTER TABLE sub_category
    ALTER COLUMN status SET NOT NULL;