ALTER TABLE project
    ADD CONSTRAINT project_identifier_unique UNIQUE (project_identifier);