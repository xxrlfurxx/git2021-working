function Modal() {

  return (
    <div
      className="modal d-block"
      style={{ backgroundColor: "rgba(0, 0, 0, 0.5)" }}
      onClick={() => {

      }}
    >
      <div className="modal-dialog">
        <div className="modal-content" onClick={(e) => e.stopPropagation()}>
          <div className="modal-header">
            <h5 className="modal-title">프로젝트 생성</h5>
            <button
              type="button"
              className="btn-close"
              aria-label="Close"
              onClick={() => {

              }}
            ></button>
          </div>
          <div className="modal-body">
            <input

            />
          </div>
          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={() => {

              }}
            >
              닫기
            </button>
            <button
              type="button"
              className="btn btn-primary"
              onClick={() => {

              }}
            >
              저장
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
export default Modal;