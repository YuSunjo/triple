### 정책
* 사용자들의 장소에 리뷰를 작성할 때 포인트 부여
* 전체 / 개인에 대한 포인트 부여 히스토리와 개인별 누적 포인트 관리

* 리뷰 작성이 이뤄질때마다 리뷰 작성 이벤트 발생, 아래 API로 이벤트를 전달

POST /events
```json
{
  "type": "REVIEW",
  "action": "ADD",
  "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
  "content": "좋아요!",
  "attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"],
  "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
  "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```
* type: 미리 정의된 string 값을 가지고 있습니다. 리뷰 이벤트의 경우 "REVIEW" 로 옵니다.
* action: 리뷰 생성 이벤트의 경우 "ADD" , 수정 이벤트는 "MOD" , 삭제 이벤트는 "DELETE" 값
* 을 가지고 있습니다.
* reviewId: UUID 포맷의 review id입니다. 어떤 리뷰에 대한 이벤트인지 가리키는 값입니다.
* content: 리뷰의 내용입니다.
* attachedPhotoIds: 리뷰에 첨부된 이미지들의 id 배열입니다.
* userId: 리뷰의 작성자 id입니다.
* placeId: 리뷰가 작성된 장소의 id입니다.

* 한 사용자는 장소마다 리뷰 1개만 작성, 리뷰 수정 또는 삭제 가능
* 리뷰 작성 보상 점수
    * 내용
        * 1자 이상 텍스트 작성: 1점
        * 1장 이상 사진 첨부: 1점
    * 보너스
        * 특정 장소에 첫 리뷰 작성: 1점