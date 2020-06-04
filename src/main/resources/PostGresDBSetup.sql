-- Exported from QuickDBD: https://www.quickdatabasediagrams.com/
-- Link to schema: https://app.quickdatabasediagrams.com/#/d/oB6cIK
-- NOTE! If you have used non-SQL datatypes in your design, you will have to change these here.

-- Modify this code to update the DB schema diagram.
-- To reset the sample schema, replace everything with
-- two dots ('..' - without quotes).

CREATE TABLE "User" (
    "id" SERIAL   NOT NULL,
    "email" string   NOT NULL,
    "name" string   NOT NULL,
    "authToken" string   NOT NULL,
    CONSTRAINT "pk_User" PRIMARY KEY (
        "id"
     )
);

CREATE TABLE "Profile" (
    "id" SERIAL   NOT NULL,
    "name" string   NOT NULL,
    CONSTRAINT "pk_Profile" PRIMARY KEY (
        "id"
     )
);

CREATE TABLE "EmploymentType" (
    "id" SERIAL   NOT NULL,
    "name" string   NOT NULL,
    CONSTRAINT "pk_EmploymentType" PRIMARY KEY (
        "id"
     )
);

CREATE TABLE "SkillSet" (
    "id" SERIAL   NOT NULL,
    "name" string   NOT NULL,
    CONSTRAINT "pk_SkillSet" PRIMARY KEY (
        "id"
     )
);

CREATE TABLE "HiringManager" (
    "id" SERIAL   NOT NULL,
    "name" string   NOT NULL,
    CONSTRAINT "pk_HiringManager" PRIMARY KEY (
        "id"
     )
);

CREATE TABLE "LocationDetails" (
    "id" SERIAL   NOT NULL,
    "name" string   NOT NULL,
    CONSTRAINT "pk_LocationDetails" PRIMARY KEY (
        "id"
     )
);

CREATE TABLE "JobDescription" (
    "id" SERIAL   NOT NULL,
    "profile" int   NOT NULL,
    "location" int   NOT NULL,
    "openings" int   NOT NULL,
    "hiringManager" int   NOT NULL,
    "employmentType" int   NOT NULL,
    "postedOn" Date   NOT NULL,
    "postedBy" int   NOT NULL,
    "lastUpdatedOn" Date   ,
    "lastUpdatedBy" int  ,
    "isActive" boolean  DEFAULT true NOT NULL,
    CONSTRAINT "pk_JobDescription" PRIMARY KEY (
        "id"
     )
);

CREATE TABLE "OutdatedJobDescription" (
    "entryId" SERIAL   NOT NULL,
    "id" int   NOT NULL,
    "profile" int   NOT NULL,
    "location" int   NOT NULL,
    "openings" int   NOT NULL,
    "hiringManager" int   NOT NULL,
    "employmentType" int   NOT NULL,
    "postedOn" Date   NOT NULL,
    "postedBy" int   NOT NULL,
    "lastUpdatedOn" Date   ,
    "lastUpdatedBy" int   ,
    "isActive" boolean  DEFAULT true NOT NULL,
    CONSTRAINT "pk_OutdatedJobDescription" PRIMARY KEY (
        "entryId"
     )
);

CREATE TABLE "JobPostSkillSet" (
    "jobId" int   NOT NULL,
    "skillId" int   NOT NULL,
    "isActive" boolean  DEFAULT true NOT NULL
);

CREATE TABLE "OutdatedJobPostSkillSet" (
    "jobId" int   NOT NULL,
    "skillId" int   NOT NULL,
    "isActive" boolean  DEFAULT true NOT NULL
);

ALTER TABLE "JobDescription" ADD CONSTRAINT "fk_JobDescription_profile" FOREIGN KEY("profile")
REFERENCES "Profile" ("id");

ALTER TABLE "JobDescription" ADD CONSTRAINT "fk_JobDescription_location" FOREIGN KEY("location")
REFERENCES "LocationDetails" ("id");

ALTER TABLE "JobDescription" ADD CONSTRAINT "fk_JobDescription_hiringManager" FOREIGN KEY("hiringManager")
REFERENCES "HiringManager" ("id");

ALTER TABLE "JobDescription" ADD CONSTRAINT "fk_JobDescription_employmentType" FOREIGN KEY("employmentType")
REFERENCES "EmploymentType" ("id");

ALTER TABLE "JobDescription" ADD CONSTRAINT "fk_JobDescription_postedBy" FOREIGN KEY("postedBy")
REFERENCES "User" ("id");

ALTER TABLE "JobDescription" ADD CONSTRAINT "fk_JobDescription_lastUpdatedBy" FOREIGN KEY("lastUpdatedBy")
REFERENCES "User" ("id");

ALTER TABLE "OutdatedJobDescription" ADD CONSTRAINT "fk_OutdatedJobDescription_id" FOREIGN KEY("id")
REFERENCES "JobDescription" ("id");

ALTER TABLE "OutdatedJobDescription" ADD CONSTRAINT "fk_OutdatedJobDescription_profile" FOREIGN KEY("profile")
REFERENCES "Profile" ("id");

ALTER TABLE "OutdatedJobDescription" ADD CONSTRAINT "fk_OutdatedJobDescription_location" FOREIGN KEY("location")
REFERENCES "LocationDetails" ("id");

ALTER TABLE "OutdatedJobDescription" ADD CONSTRAINT "fk_OutdatedJobDescription_hiringManager" FOREIGN KEY("hiringManager")
REFERENCES "HiringManager" ("id");

ALTER TABLE "OutdatedJobDescription" ADD CONSTRAINT "fk_OutdatedJobDescription_employmentType" FOREIGN KEY("employmentType")
REFERENCES "EmploymentType" ("id");

ALTER TABLE "OutdatedJobDescription" ADD CONSTRAINT "fk_OutdatedJobDescription_postedBy" FOREIGN KEY("postedBy")
REFERENCES "User" ("id");

ALTER TABLE "OutdatedJobDescription" ADD CONSTRAINT "fk_OutdatedJobDescription_lastUpdatedBy" FOREIGN KEY("lastUpdatedBy")
REFERENCES "User" ("id");

ALTER TABLE "JobPostSkillSet" ADD CONSTRAINT "fk_JobPostSkillSet_jobId" FOREIGN KEY("jobId")
REFERENCES "JobDescription" ("id");

ALTER TABLE "JobPostSkillSet" ADD CONSTRAINT "fk_JobPostSkillSet_skillId" FOREIGN KEY("skillId")
REFERENCES "SkillSet" ("id");

ALTER TABLE "OutdatedJobPostSkillSet" ADD CONSTRAINT "fk_OutdatedJobPostSkillSet_jobId" FOREIGN KEY("jobId")
REFERENCES "OutdatedJobDescription" ("entryId");

ALTER TABLE "OutdatedJobPostSkillSet" ADD CONSTRAINT "fk_OutdatedJobPostSkillSet_skillId" FOREIGN KEY("skillId")
REFERENCES "SkillSet" ("id");

insert into hiringManager (name) values('Aravind Loganathan');
insert into hiringManager (name) values('Darshan Patil');
insert into hiringManager (name) values('Abhigyan Nayak');
insert into hiringManager (name) values('Ida Gonsalves');

insert into locationDetails (name) values('Mumbai');
insert into locationDetails (name) values('Bengaluru');
insert into locationDetails (name) values('Gurugram');
insert into locationDetails (name) values('Delhi');
insert into locationDetails (name) values('Seattle, US');
insert into locationDetails (name) values('Vancouver, CA');
insert into locationDetails (name) values('Dallas, TX');
insert into locationDetails (name) values('Chennai');

insert into employmentType (name) values('Contract');
insert into employmentType (name) values('Full Time');
insert into employmentType (name) values('Part Time');
insert into employmentType (name) values('Internship');

insert into skillSet (name) values('Git');
insert into skillSet (name) values('Angular');
insert into skillSet (name) values('AngularJS');
insert into skillSet (name) values('React');
insert into skillSet (name) values('React Native');
insert into skillSet (name) values('MySQL');
insert into skillSet (name) values('PostgreSQL');
insert into skillSet (name) values('DevOps');
insert into skillSet (name) values('Networking');
insert into skillSet (name) values('Android');
insert into skillSet (name) values('Swift');
insert into skillSet (name) values('C++');
insert into skillSet (name) values('Data Structures');
insert into skillSet (name) values('Project Management');
insert into skillSet (name) values('Python');
insert into skillSet (name) values('Machine Learning');

insert into profile (name) values('SDE II');
insert into profile (name) values('Tester');
insert into profile (name) values('Technical Analyst');
insert into profile (name) values('SDE');
insert into profile (name) values('IT Personnel');
insert into profile (name) values('Project Manager');
insert into profile (name) values('Hiring Manager');
insert into profile (name) values('Technical Delivery Manager');
insert into profile (name) values('HR Personnel');
insert into profile (name) values('CTO');
insert into profile (name) values('CEO');