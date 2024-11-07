--테이블 생성
CREATE TABLE `schedules` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '일정 식별자',
                             `user_name` varchar(20) NOT NULL COMMENT '사용자명',
                             `title` varchar(20) DEFAULT NULL COMMENT '제목',
                             `contents` varchar(225) DEFAULT NULL COMMENT '내용',
                             `password` varchar(20) DEFAULT NULL COMMENT '비밀번호',
                             `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '일정 등록일',
                             `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '일정 수정일',
                             PRIMARY KEY (`id`)
)

--일정 생성하기
INSERT INTO schedules (id, user_name, title, contents, password)
    VALUES (1, '사용자명', '제목', '내용', '비밀번호');

--전체 일정 조회하기
SELECT id, user_name, title, contents, schedules.updated_at
FROM schedules
WHERE user_name = '사용자명' OR updated_at = 'YYYY-MM-DD' ORDER BY updated_at DESC

--선택 일정 조회하기
SELECT id, user_name, title, contents, updated_at
FROM schedules
WHERE id = 1

--선택 일정 수정하기
UPDATE schedules
SET user_name = '수정된 사용자명',
    title = '수정된 제목',
    contents = '수정된 내용'
WHERE id = 1

--선택 일정 삭제하기
DELETE FROM schedules
WHERE id = 1
