-- schedule 테이블 생성
CREATE TABLE schedules
(   id					INTEGER				NOT NULL,
    user_name    			VARCHAR(20)			NOT NULL,
    title				VARCHAR(20)         		NULL,
    content				VARCHAR(225)			NULL,
    password				VARCHAR(20)			NULL,
    created_at				DATE				NULL,
    updated_at				DATE				NULL,
    PRIMARY KEY (id)                  );
 
-- 일정 생성하기 
INSERT INTO schedules (id, user_name, title, content, password, created_at, updated_at)
     VALUES 	(1, '정가현', '공부하기', '스프링공부하기','1234', CURRENT_DATE, CURRENT_DATE); 
     
-- 전체 일정 조회하기
SELECT id, user_name, title, content, updated_at
from schedules
where user_name = '정가현' or updated_at = 'YYYY-MM-DD'

-- 선택 일정 조회하기
SELECT id, user_name, title, content, updated_at
from schedules
where id = '1'

-- 선택 일정 수정하기
UPDATE schedules
SET id = '1',
	user_name = '정동수',
	title = '청소하기',
    content = '고양이 화장실 청소하기',
    created_at = 'YYYY-MM-DD',
    updated_at = CURRENT_DATE
WHERE id = '1'
    

-- 선택 일정 삭제하기 
DELETE FROM schedules
WHERE id = '1'
