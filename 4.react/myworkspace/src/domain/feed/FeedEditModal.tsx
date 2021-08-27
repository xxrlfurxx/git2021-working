import { useRef } from "react";
import { FeedState } from "./type/index2";


// { 함수속성 }
// 함수속성의 타입: (매개변수타입) => 리턴타입
// 함수(ex. 부모state제어)를 부모 컴포넌트로 부터 매개변수(prop)를 받음
// 자식컴포넌트에서 이벤트가 발생하면 prop으로 받은 함수를 실행

interface ModalProp {
  item: FeedState;
  onClose: () => void; // 콜백함수
  onSave: (editItem: FeedState) => void; // 콜백함수
}

const FeedEditModal = ({ item, onClose, onSave }: ModalProp) => {
  const textRef = useRef<HTMLTextAreaElement>(null);
  const fileRef = useRef<HTMLInputElement>(null);

  const save = () => {
    const feed: FeedState = {
      id: item.id,
      fileType: fileRef.current?.value,
      content: textRef.current?.value, // 수정된 입력값
      createTime: item.createTime,
    };

    onSave(feed);
  };

  return (
    <div
      className="modal d-block"
      style={{ backgroundColor: "rgba(0, 0, 0, 0.5)" }}
      onClick={() => {
        onClose();
      }}
    >
      <div className="modal-dialog">
        <div className="modal-content" onClick={(e) => e.stopPropagation()}>
          <div className="modal-header">
            <h5 className="modal-title">EDIT FEED</h5>
            <button
              type="button"
              className="btn-close"
              aria-label="Close"
              onClick={() => {
                onClose();
              }}
            ></button>
          </div>
          <div className="modal-body">

            <input
              type="file"
              defaultValue={item.fileType}
              className="w-100"
              ref={fileRef}
            />
            <textarea
              defaultValue={item.content}
              className="w-100"
              ref={textRef}
            />
          </div>

          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={() => {
                onClose();
              }}
            >
              닫기
            </button>
            <button
              type="button"
              className="btn btn-primary"
              onClick={() => {
                save();
              }}
            >
              저장
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeedEditModal;