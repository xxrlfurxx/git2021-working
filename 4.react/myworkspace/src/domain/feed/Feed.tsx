import { useRef, useState } from "react";

import produce from "immer";
// import { lorem, penguin, robot } from "../common/data";
// import { getTimeString } from "../common/lib/string";
import FeedEditModal from "./FeedEditModal";
import { FeedState } from "./type/index2";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import style from "../profile/Profile.module.scss";

const getTimeString = (unixtime: number) => {
  const now = new Date(); // 현재날짜-시간객체
  // 1초: 1000
  // 1분: 60 * 1000
  // 1시간: 60 * 60 * 1000
  // 1일: 24 * 60 * 60 * 1000
  const day = 24 * 60 * 60 * 1000

  // Locale: timezone, currency 등
  // js에서는 브라우저의 정보를 이용함
  const dateTime = new Date(unixtime);

  // 현재시간보다 24시간 이전이면 날짜를 보여주고
  // 현재시간보다 24시간 미만이면 시간을 보여줌
  return unixtime - now.getTime() >= day
    ? dateTime.toLocaleDateString()
    : dateTime.toLocaleTimeString()
};

const Feed = () => {
  const profile = useSelector((state: RootState) => state.profile);

  const [feedList, setFeedList] = useState<FeedState[]>([]);

  const [isEdit, setIsEdit] = useState(false);

  const textRef = useRef<HTMLTextAreaElement>(null);
  const fileRef = useRef<HTMLInputElement>(null);
  const formRef = useRef<HTMLFormElement>(null);

  const add = (e: React.KeyboardEvent<HTMLInputElement> | null) => {
    if (e) {
      if (e.code !== "Enter") return;
    }

    if (fileRef.current?.files?.length) {
      const file = fileRef.current?.files[0];
      const reader = new FileReader();
      reader.readAsDataURL(file);

      reader.onload = () => {
        post(reader.result?.toString(), file.type);
      };
    } else {
      post(undefined, undefined);
    }
  };

  const post = (dataUrl: string | undefined, fileType: string | undefined) => {
    const feed: FeedState = {
      id: feedList.length > 0 ? feedList[0].id + 1 : 1,
      // optional chaning
      content: textRef.current?.value,
      dataUrl: dataUrl,
      fileType: fileType,
      createTime: new Date().getTime(),
    };

    setFeedList([feed, ...feedList]);

    // 입력값 초기화
    formRef.current?.reset();
  };

  const del = (id: number) => {
    setFeedList(feedList.filter((item) => item.id !== id));
  };

  const editItem = useRef<FeedState>({
    id: 0,
    content: "",
    dataUrl: "",
    fileType: "",
    createTime: 0,
  });

  const edit = (item: FeedState) => {
    // 수정할 todo객체
    editItem.current = item;
    // 모달 팝업을 보여주기
    setIsEdit(true);
  };

  const save = (editItem: FeedState) => {
    console.log(editItem);
    setFeedList(
      produce((state) => {
        const item = state.find((item) => item.id === editItem.id);
        if (item) {
          item.content = editItem.content;
        }
      })
    );

    // 모달창 닫기
    setIsEdit(false);
  };

  return (
    <div style={{ width: "40vw" }} className="mx-auto">
      <h2 className="text-center my-5">Feeds</h2>
      {/* isEdit state가 true일 때만 Modal 창이 보임 */}
      {isEdit && (
        <FeedEditModal
          item={editItem.current}
          onClose={() => {
            setIsEdit(false);
          }}
          onSave={(editItem) => {
            save(editItem);
          }}
        />
      )}
      <form
        className="mt-5"
        onSubmit={(e) => {
          e.preventDefault();
        }}
        ref={formRef}
      >
        <textarea
          className="form-control mb-1"
          placeholder="Leave a post here"
          ref={textRef}
          style={{ height: "15vh" }}
        ></textarea>
        <div className="d-flex">
          <input
            type="file"
            className="form-control me-1"
            accept="image/png, image/jpeg, video/mp4"
            ref={fileRef}
          />
          <button
            className="btn btn-primary text-nowrap"
            type="button"
            onClick={() => {
              add(null);
            }}
          >
            입력
          </button>
        </div>
      </form>
      <div className="mt-3">
        {feedList.map((item) => (
          <div className="card mt-1" key={item.id}>
            <div className="card-header">
              <img src={item.content} className={`${style["thumb"]}`} alt={profile.username} />
              <span className="mt-2">{profile.username}</span>
            </div>
            {item.fileType &&
              (item.fileType?.includes("image") ? (
                <img
                  src={item.dataUrl}
                  className="card-img-top"
                  alt={item.content}
                />
              ) : (
                <video className="card-img-top" controls src={item.dataUrl} />
              ))}
            <div className="card-body">
              <p className="card-text">{item.content}</p>
              <div className="d-flex">
                <div className="w-100">
                  <span className="text-secondary">
                    {getTimeString(
                      item.modifyTime ? item.modifyTime : item.createTime
                    )}
                  </span>
                </div>
                <a
                  href="#!"
                  onClick={(e) => {
                    e.preventDefault();
                    edit(item);
                  }}
                  className="link-secondary fs-6 text-nowrap "
                  style={{ marginRight: "10px" }}
                >
                  수정
                </a>
                <a
                  href="#!"
                  onClick={(e) => {
                    e.preventDefault(); // 기본 동작 방지
                    del(item.id);
                  }}
                  className="link-secondary fs-6 text-nowrap"
                >
                  삭제
                </a>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Feed;